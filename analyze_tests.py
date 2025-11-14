import xml.etree.ElementTree as ET
from collections import defaultdict, Counter

# Parse the XML
tree = ET.parse('selenium-perfume-tests/target/surefire-reports/testng-results.xml')
root = tree.getroot()

failures = []
feature_failures = defaultdict(list)
error_counter = Counter()

# Extract all failures
for test in root.findall('.//test-method[@status="FAIL"]'):
    params = test.findall('params/param')
    scenario = params[0].find('value').text.strip('"') if params else "Unknown"
    feature = params[1].find('value').text.strip('"') if len(params) > 1 else "Unknown"
    
    exception = test.find('exception')
    exc_type = exception.get('class', '').split('.')[-1] if exception is not None else "Unknown"
    message_elem = exception.find('message') if exception is not None else None
    message = message_elem.text if message_elem is not None and message_elem.text else "No message"
    
    # Clean CDATA
    if message.startswith('CDATA['):
        message = message[6:].split('\n')[0] if '\n' in message else message[6:-2]
    
    error_counter[exc_type] += 1
    failures.append({
        'scenario': scenario,
        'feature': feature,
        'error': exc_type,
        'message': message[:150]
    })
    
    if feature not in feature_failures:
        feature_failures[feature] = []
    feature_failures[feature].append({'scenario': scenario, 'error': exc_type, 'message': message[:150]})

print("=" * 100)
print("COMPREHENSIVE FAILURE ANALYSIS - 44 Test Failures")
print("=" * 100)

print("\n1. FAILURE BREAKDOWN BY EXCEPTION TYPE:")
print("-" * 100)
for exc_type, count in error_counter.most_common():
    print(f"   {exc_type}: {count} failures ({count*100//44}%)")

print("\n2. FAILURES BY FEATURE FILE:")
print("-" * 100)
for feature in sorted(feature_failures.keys()):
    count = len(feature_failures[feature])
    print(f"\n   {feature}: {count} failures ({count*100//44}%)")
    
    # Group by error type within feature
    error_types = Counter()
    for test in feature_failures[feature]:
        error_types[test['error']] += 1
    
    for error, cnt in error_types.most_common():
        print(f"      - {error}: {cnt}")

print("\n3. MOST COMMON ERROR MESSAGES (Top 10):")
print("-" * 100)
msg_counter = Counter([f['message'][:80] for f in failures])
for msg, count in msg_counter.most_common(10):
    print(f"   [{count}] {msg}...")

print("\n4. SAMPLE FAILURES BY TYPE:")
print("-" * 100)

# TimeoutExceptions
timeout_failures = [f for f in failures if f['error'] == 'TimeoutException']
if timeout_failures:
    print(f"\n   TIMEOUT EXCEPTIONS ({len(timeout_failures)} failures):")
    seen = set()
    for f in timeout_failures[:5]:
        if f['message'] not in seen:
            print(f"      - {f['scenario']}: {f['message'][:90]}")
            seen.add(f['message'])

# AssertionErrors
assertion_failures = [f for f in failures if f['error'] == 'AssertionError']
if assertion_failures:
    print(f"\n   ASSERTION ERRORS ({len(assertion_failures)} failures):")
    for f in assertion_failures[:8]:
        print(f"      - {f['scenario']}: {f['message'][:90]}")

# NoSuchElementException
nosuch_failures = [f for f in failures if f['error'] == 'NoSuchElementException']
if nosuch_failures:
    print(f"\n   NO SUCH ELEMENT EXCEPTIONS ({len(nosuch_failures)} failures):")
    for f in nosuch_failures[:5]:
        print(f"      - {f['scenario']}: {f['message'][:90]}")

print("\n" + "=" * 100)
