# Perfume E-Commerce Automation Guide

## Overview
This document provides a comprehensive guide for automating the Perfume E-Commerce website using Selenium WebDriver. It includes all selectors, custom events, test hooks, and expected values needed for writing robust automated tests.

**Important:** All prices are displayed in ILS (Israeli Shekel - ₪). The currency symbol is defined as a constant (`CURRENCY = '₪'`) in `js/global.js:4`, ensuring clean, consistent rendering across all pages for easy parsing in test assertions.

---

## Table of Contents
1. [Test Selectors (data-testid)](#test-selectors-data-testid)
2. [Custom Events for Wait Strategies](#custom-events-for-wait-strategies)
3. [State Management & Test Hooks](#state-management--test-hooks)
4. [Data-Driven Testing](#data-driven-testing)
5. [Expected Values & Constants](#expected-values--constants)
6. [Page Object Model Reference](#page-object-model-reference)

---

## 1. Test Selectors (data-testid)

### Header (partials/header.html)
| Element | data-testid | Description |
|---------|-------------|-------------|
| Header section | `header` | Main header container |
| Logo link | `logo-link` | Link to home page |
| Navigation bar | `navbar` | Main navigation |
| Home link | `nav-home` | Navigation to index.html |
| Shop link | `nav-shop` | Navigation to shop.html |
| About link | `nav-about` | Navigation to about.html |
| Contact link | `nav-contact` | Navigation to contact.html |
| Cart link (desktop) | `nav-cart-link` | Navigation to cart.html |
| Cart count badge | `cart-count` | Displays number of items in cart |
| Mobile cart link | `mobile-cart-link` | Cart link for mobile view |
| Mobile menu toggle | `mobile-menu-toggle` | Opens mobile menu |
| Mobile close button | `mobile-close-btn` | Closes mobile menu |

### Home Page (index.html)
| Element | data-testid | Description |
|---------|-------------|-------------|
| Hero section | `hero-section` | Main hero banner |
| Shop Now button | `hero-shop-now-btn` | CTA button in hero |
| Featured section | `featured-section` | Featured products section |
| Featured container | `featured-products-container` | Container for featured products |
| Banner section | `banner-section` | Promotional banner |
| Explore button | `banner-explore-btn` | Banner CTA (no functionality) |
| New arrivals section | `new-arrivals-section` | New arrivals section |
| New arrivals container | `new-arrivals-container` | Container for new arrivals |

### Shop Page (shop.html)
| Element | data-testid | Description |
|---------|-------------|-------------|
| Shop controls | `shop-controls` | Search and sort container |
| Search input | `search-input` | Product search field |
| Sort dropdown | `sort-select` | Sort products dropdown |
| Shop section | `shop-section` | Main shop section |
| Products container | `shop-products-container` | Container for all shop products |

### Product Detail Page (product-detail.html)
| Element | data-testid | Description |
|---------|-------------|-------------|
| Detail section | `product-detail-section` | Main product detail section |
| Image container | `product-image-container` | Product image container |
| Info container | `product-info-container` | Product info container |
| Product name | `product-detail-name` | H2 with product name |
| Product brand | `product-detail-brand` | H3 with brand info |
| Rating stars | `product-detail-rating` | Star rating display |
| Original price | `product-detail-original-price` | Strike-through price |
| Discounted price | `product-detail-discounted-price` | Current price (with discount) |
| Description | `product-detail-description` | Product description |
| Add to Cart button | `product-detail-add-to-cart-btn` | Main CTA button |

### Cart Page (cart.html)
| Element | data-testid | Description |
|---------|-------------|-------------|
| Cart items section | `cart-items-section` | Main cart container |
| Cart item row | `cart-item-row` | Individual cart item (dynamically generated) |
| Item image | `cart-item-image` | Product image in cart |
| Item link | `cart-item-link` | Link to product detail |
| Item price | `cart-item-price` | Price per unit |
| Item quantity | `cart-item-qty` | Current quantity |
| Qty minus button | `qty-minus-btn` | Decrease quantity |
| Qty plus button | `qty-plus-btn` | Increase quantity |
| Remove button | `cart-remove-btn` | Remove item from cart |
| Subtotal | `cart-subtotal` | Cart subtotal amount |
| Shipping cost | `cart-shipping` | Fixed shipping cost (₪‎5.00) |
| Total | `cart-total` | Total amount |
| Checkout button | `checkout-btn` | Proceed to checkout |
| Checkout message | `checkout-message` | Success message after checkout |
| **Dev Tools** | | |
| Reset Cart button | `reset-cart-btn` | Clear cart (testing) |
| Seed Cart button | `seed-cart-btn` | Add test data to cart |

### Contact Page (contact.html)
| Element | data-testid | Description |
|---------|-------------|-------------|
| Contact form | `contact-form` | Main form element |
| Name input | `contact-name-input` | Name field (required, minlength=2) |
| Email input | `contact-email-input` | Email field (type=email, required) |
| Subject input | `contact-subject-input` | Subject field (required, minlength=3) |
| Message textarea | `contact-message-input` | Message field (required, minlength=10) |
| Submit button | `contact-submit-btn` | Form submission |
| Name error | `name-error` | Error message for name |
| Email error | `email-error` | Error message for email |
| Subject error | `subject-error` | Error message for subject |
| Message error | `message-error` | Error message for message |
| Form feedback | `form-feedback` | Success/failure feedback |

### Product Cards (Dynamically Generated)
| Element | data-testid | Description |
|---------|-------------|-------------|
| Product card | `product-card` | Individual product container |
| Product link | `product-link-{productId}` | Link with dynamic ID |
| Product image | `product-image-{productId}` | Image with dynamic ID |
| Discount badge | `discount-badge` | "70% OFF" badge |
| Product brand | `product-brand` | Brand name span |
| Product name link | `product-name-link` | Product name clickable |
| Product rating | `product-rating` | Star rating container |
| Original price | `original-price` | Strike-through price |
| Discounted price | `discounted-price` | Current selling price |
| Add to Cart button | `add-to-cart-btn-{productId}` | Cart button with dynamic ID |

### Footer (partials/footer.html)
| Element | data-testid | Description |
|---------|-------------|-------------|
| About link | `footer-about-link` | Footer about us link |
| Delivery link | `footer-delivery-link` | Delivery information (no href) |
| Privacy link | `footer-privacy-link` | Privacy policy (no href) |
| Terms link | `footer-terms-link` | Terms & conditions (no href) |
| Contact link | `footer-contact-link` | Footer contact link |
| Cart notification | `cart-notification` | Toast notification for cart actions |

---

## 2. Custom Events for Wait Strategies

Instead of using arbitrary sleep/waits, listen for these custom events:

### Event: `headerLoaded`
- **Dispatched by:** `js/global.js:35`
- **When:** After header HTML is injected and initialized
- **Usage:** Wait for navigation elements to be ready
- **Hidden marker:** `#header-loaded[data-ready="true"]`

```java
// Example: Wait for header
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
wait.until(driver ->
    ((JavascriptExecutor) driver).executeScript(
        "return document.getElementById('header-loaded') !== null"
    )
);
```

### Event: `footerLoaded`
- **Dispatched by:** `js/global.js:59`
- **When:** After footer HTML is injected
- **Hidden marker:** `#footer-loaded[data-ready="true"]`

### Event: `productsLoaded`
- **Dispatched by:**
  - `js/home.js:49` (Featured & New Arrivals)
  - `js/shop.js:21` (All shop products)
- **When:** After products are fetched, rendered, and Add to Cart handlers bound
- **Hidden marker:** `#products-loaded[data-ready="true"]`

```java
// Example: Wait for products to load
wait.until(driver ->
    ((JavascriptExecutor) driver).executeScript(
        "return document.getElementById('products-loaded') !== null"
    )
);
```

### Event: `productLoaded`
- **Dispatched by:** `js/product-detail.js:45`
- **When:** After product detail page is fully rendered
- **Hidden marker:** `#product-loaded[data-ready="true"]`

### Event: `cartUpdated`
- **Dispatched by:** `js/cart.js:108`
- **When:** After cart display is refreshed (on load, add, remove, update qty)
- **Usage:** Wait for cart operations to complete before assertions

```java
// Example: Wait for cart update
JavascriptExecutor js = (JavascriptExecutor) driver;
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
wait.until(driver -> {
    return js.executeScript(
        "return window.cartUpdateEventFired === true"
    );
});
```

**JavaScript Event Listener Example:**
```javascript
// In your Selenium test setup
driver.executeScript(
    "window.cartUpdateEventFired = false;" +
    "document.addEventListener('cartUpdated', () => {" +
    "    window.cartUpdateEventFired = true;" +
    "});"
);
```

---

## 3. State Management & Test Hooks

### Reset Cart (Clear State)
```java
// Option 1: Use dev button
driver.findElement(By.cssSelector("[data-testid='reset-cart-btn']")).click();

// Option 2: Execute JavaScript directly
((JavascriptExecutor) driver).executeScript(
    "localStorage.removeItem('cart');" +
    "location.reload();"
);
```

### Seed Cart (Populate with Test Data)
```java
// Use dev button to add 3 sample products
driver.findElement(By.cssSelector("[data-testid='seed-cart-btn']")).click();
// Cart will contain:
// - F1: Eternal Bloom Perfume (qty: 2)
// - N1: Future Essence Perfume (qty: 1)
// - P1: Eternal Bloom Perfume (qty: 3)
```

### Access Cart Directly via JavaScript
```java
JavascriptExecutor js = (JavascriptExecutor) driver;

// Get current cart
String cartJson = (String) js.executeScript(
    "return localStorage.getItem('cart');"
);

// Set custom cart state
js.executeScript(
    "localStorage.setItem('cart', '" + customCartJson + "');" +
    "location.reload();"
);
```

---

## 4. Data-Driven Testing

### Using Alternative Data Sources

The application supports loading different product data files for data-driven tests.

#### Method 1: URL Parameter
```java
driver.get("http://localhost:8000/shop.html?dataSource=test-products.json");
```

#### Method 2: LocalStorage (Environment Simulation)
```java
JavascriptExecutor js = (JavascriptExecutor) driver;
js.executeScript("localStorage.setItem('DATA_SOURCE', 'test-products.json');");
driver.navigate().refresh();
```

#### Method 3: Config API
```java
// Set data source
js.executeScript("AppConfig.setDataSource('test-products.json');");
driver.navigate().refresh();

// Reset to default
js.executeScript("AppConfig.resetDataSource();");
driver.navigate().refresh();
```

### Creating Test Data Files
Create JSON files matching the structure of `products.json`:

```json
{
  "featured": [
    {
      "id": "TEST1",
      "image": "image/test/product1.jpg",
      "brand": "Test Brand",
      "name": "Test Product",
      "price": 1000,
      "rating": 5
    }
  ],
  "newArrivals": [],
  "products": []
}
```

Place files in the project root and reference them:
- `test-products.json`
- `minimal-products.json`
- `invalid-products.json` (for negative testing)

---

## 5. Expected Values & Constants

### Pricing
| Constant | Value | Location |
|----------|-------|----------|
| Discount Rate | 70% (0.7) | `js/global.js:2` |
| Shipping Cost | 5.00 | `js/global.js:3` |
| Currency Symbol | ₪ (ILS) | `js/global.js:4` (CURRENCY constant) |

**Price Calculation:**
```
Discounted Price = Original Price * (1 - 0.7) = Original Price * 0.3
Total = Subtotal + 5.00
Currency Display = CURRENCY + amount (e.g., "₪750.00")
```

### Product IDs
| Category | IDs | Notes |
|----------|-----|-------|
| Featured | F1-F8 | 8 products |
| New Arrivals | N1-N10 | 10 products |
| Shop Products | P1-P18 | 18 products (renamed from duplicates) |

**Important:** Product IDs are now unique across the entire dataset.

### Form Validation

#### Contact Form Rules
| Field | Type | Required | Validation | Error Messages |
|-------|------|----------|------------|----------------|
| Name | text | Yes | minlength=2 | "Name is required." |
| Email | email | Yes | Email pattern + HTML5 | "Email is required." / "Please enter a valid email address." |
| Subject | text | Yes | minlength=3 | "Subject is required." |
| Message | textarea | Yes | minlength=10 | "Message is required." |

**Email Regex Pattern:**
```javascript
/^[^\s@]+@[^\s@]+\.[^\s@]+$/
```

### Search & Sort Options

#### Search (shop.html)
- **Type:** Case-insensitive substring match on product name
- **Real-time:** Updates on each keystroke

#### Sort Options
| Value | Label | Behavior |
|-------|-------|----------|
| "" (empty) | "Sort By" | No sorting (default order) |
| "nameAsc" | "Name (A-Z)" | Alphabetical ascending |
| "nameDesc" | "Name (Z-A)" | Alphabetical descending |
| "priceAsc" | "Price (Low-High)" | Price ascending |
| "priceDesc" | "Price (High-Low)" | Price descending |

---

## 6. Page Object Model Reference

### Suggested Page Objects

#### 1. BasePage
```java
public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    // Common selectors
    By headerSelector = By.cssSelector("[data-testid='header']");
    By cartCountSelector = By.cssSelector("[data-testid='cart-count']");
    By navHomeSelector = By.cssSelector("[data-testid='nav-home']");
    By navShopSelector = By.cssSelector("[data-testid='nav-shop']");
    By navCartSelector = By.cssSelector("[data-testid='nav-cart-link']");

    // Wait for header to load
    public void waitForHeaderLoaded() {
        wait.until(driver ->
            ((JavascriptExecutor) driver).executeScript(
                "return document.getElementById('header-loaded') !== null"
            )
        );
    }

    // Get cart count
    public int getCartCount() {
        String count = driver.findElement(cartCountSelector).getText();
        return Integer.parseInt(count);
    }
}
```

#### 2. HomePage extends BasePage
```java
public class HomePage extends BasePage {
    // Selectors
    By heroButtonSelector = By.cssSelector("[data-testid='hero-shop-now-btn']");
    By featuredContainerSelector = By.cssSelector("[data-testid='featured-products-container']");
    By newArrivalsContainerSelector = By.cssSelector("[data-testid='new-arrivals-container']");

    // Wait for products to load
    public void waitForProductsLoaded() {
        wait.until(driver ->
            ((JavascriptExecutor) driver).executeScript(
                "return document.getElementById('products-loaded') !== null"
            )
        );
    }

    // Get featured products
    public List<WebElement> getFeaturedProducts() {
        WebElement container = driver.findElement(featuredContainerSelector);
        return container.findElements(By.cssSelector("[data-testid='product-card']"));
    }
}
```

#### 3. ShopPage extends BasePage
```java
public class ShopPage extends BasePage {
    // Selectors
    By searchInputSelector = By.cssSelector("[data-testid='search-input']");
    By sortSelectSelector = By.cssSelector("[data-testid='sort-select']");
    By productsContainerSelector = By.cssSelector("[data-testid='shop-products-container']");

    // Actions
    public void searchProducts(String query) {
        driver.findElement(searchInputSelector).sendKeys(query);
        // Search is real-time, wait for products to update
        waitForProductsLoaded();
    }

    public void sortBy(String sortValue) {
        new Select(driver.findElement(sortSelectSelector)).selectByValue(sortValue);
        waitForProductsLoaded();
    }

    public void addProductToCartById(String productId) {
        String selector = String.format("[data-testid='add-to-cart-btn-%s']", productId);
        driver.findElement(By.cssSelector(selector)).click();
        // Wait for cart notification or count update
        Thread.sleep(500); // Or implement better wait
    }
}
```

#### 4. CartPage extends BasePage
```java
public class CartPage extends BasePage {
    // Selectors
    By cartItemsSelector = By.cssSelector("[data-testid='cart-items-section']");
    By cartItemRowSelector = By.cssSelector("[data-testid='cart-item-row']");
    By subtotalSelector = By.cssSelector("[data-testid='cart-subtotal']");
    By totalSelector = By.cssSelector("[data-testid='cart-total']");
    By checkoutBtnSelector = By.cssSelector("[data-testid='checkout-btn']");
    By resetCartBtnSelector = By.cssSelector("[data-testid='reset-cart-btn']");
    By seedCartBtnSelector = By.cssSelector("[data-testid='seed-cart-btn']");

    // Actions
    public void waitForCartUpdated() {
        // Wait for cartUpdated event
        wait.until(driver -> {
            return ((JavascriptExecutor) driver).executeScript(
                "return window.cartUpdateEventFired === true"
            ) != null;
        });
    }

    public double getSubtotal() {
        String text = driver.findElement(subtotalSelector).getText();
        return parseILSPrice(text);
    }

    public double getTotal() {
        String text = driver.findElement(totalSelector).getText();
        return parseILSPrice(text);
    }

    public void resetCart() {
        driver.findElement(resetCartBtnSelector).click();
        waitForCartUpdated();
    }

    private double parseILSPrice(String text) {
        // Remove "₪" currency symbol and parse
        return Double.parseDouble(text.replace("₪", "").trim());
    }
}
```

#### 5. ContactPage extends BasePage
```java
public class ContactPage extends BasePage {
    // Selectors
    By contactFormSelector = By.cssSelector("[data-testid='contact-form']");
    By nameInputSelector = By.cssSelector("[data-testid='contact-name-input']");
    By emailInputSelector = By.cssSelector("[data-testid='contact-email-input']");
    By subjectInputSelector = By.cssSelector("[data-testid='contact-subject-input']");
    By messageInputSelector = By.cssSelector("[data-testid='contact-message-input']");
    By submitBtnSelector = By.cssSelector("[data-testid='contact-submit-btn']");
    By formFeedbackSelector = By.cssSelector("[data-testid='form-feedback']");
    By nameErrorSelector = By.cssSelector("[data-testid='name-error']");
    By emailErrorSelector = By.cssSelector("[data-testid='email-error']");

    // Actions
    public void fillForm(String name, String email, String subject, String message) {
        driver.findElement(nameInputSelector).sendKeys(name);
        driver.findElement(emailInputSelector).sendKeys(email);
        driver.findElement(subjectInputSelector).sendKeys(subject);
        driver.findElement(messageInputSelector).sendKeys(message);
    }

    public void submitForm() {
        driver.findElement(submitBtnSelector).click();
    }

    public String getSuccessMessage() {
        return driver.findElement(formFeedbackSelector).getText();
    }

    public String getNameError() {
        return driver.findElement(nameErrorSelector).getText();
    }
}
```

---

## Sample Test Scenarios

### Scenario 1: Add Product to Cart
```gherkin
Feature: Shopping Cart
  Scenario: Add a featured product to cart
    Given I am on the home page
    When I wait for products to load
    And I click Add to Cart on product "F1"
    Then the cart count should be "1"
    When I navigate to the cart page
    Then the cart should contain "F1"
    And the subtotal should be "₪750.00" (2500 * 0.3)
```

### Scenario 2: Search and Filter
```gherkin
Feature: Shop Page
  Scenario: Search for products by name
    Given I am on the shop page
    When I search for "Eternal"
    Then I should see products containing "Eternal" in the name
```

### Scenario 3: Form Validation
```gherkin
Feature: Contact Form
  Scenario: Submit form with invalid email
    Given I am on the contact page
    When I fill in "Name" with "John Doe"
    And I fill in "Email" with "invalid-email"
    And I click Submit
    Then I should see email error "Please enter a valid email address."
```

---

## Notes for Test Automation

1. **Always wait for custom events** instead of using fixed sleeps
2. **Use data-testid selectors** for stability (they won't change with CSS updates)
3. **Reset cart state** before each test using the Reset Cart button or localStorage
4. **Use Seed Cart** for tests that require pre-populated cart
5. **Currency is ILS (₪)** - clean symbol, easy to parse in assertions (defined as CURRENCY constant in global.js)
6. **Discount is always 70%** - calculate expected prices accordingly
7. **Product IDs are unique** - P1-P18 were renamed from duplicates
8. **Email validation** - tests both HTML5 and custom JavaScript validation
9. **Data-driven tests** - use alternative JSON files via config.js

---

## File Structure
```
WebSWER353/
├── index.html
├── shop.html
├── product-detail.html
├── cart.html
├── contact.html
├── about.html
├── products.json (default data source)
├── js/
│   ├── config.js (data source configuration)
│   ├── global.js (common functions, header/footer loading)
│   ├── home.js (home page logic)
│   ├── shop.js (shop page logic)
│   ├── product-detail.js (product detail logic)
│   ├── cart.js (cart management)
│   └── contact.js (contact form validation)
├── partials/
│   ├── header.html
│   └── footer.html
└── AUTOMATION.md (this file)
```

---

## Support & Questions

For questions about test automation hooks or selectors, refer to the source code locations mentioned in this document. All `data-testid` attributes and custom events have been specifically added to support automated testing with Selenium WebDriver.

**Last Updated:** 2025-11-14
**Version:** 1.0
