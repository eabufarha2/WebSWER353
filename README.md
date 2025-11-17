# Perfume E-Commerce Testing Project

Complete E-Commerce website with automated Selenium test suite using BDD (Cucumber) and Page Object Model.

## Project Structure

```
perfume-ecommerce-project/
├── web-app/                    # E-Commerce Web Application
│   ├── index.html             # Home page
│   ├── shop.html              # Product catalog
│   ├── product-detail.html    # Product details
│   ├── cart.html              # Shopping cart
│   ├── contact.html           # Contact form
│   ├── about.html             # About page
│   ├── css/                   # Stylesheets
│   ├── js/                    # JavaScript files
│   ├── image/                 # Product images
│   ├── partials/              # Reusable HTML components (header, footer)
│   └── products.json          # Product data
│
├── selenium-perfume-tests/    # Automated Test Suite
│   ├── src/
│   │   ├── main/java/         # Page Objects & Utilities
│   │   └── test/
│   │       ├── java/          # Step Definitions & Test Runners
│   │       └── resources/
│   │           └── features/  # BDD Feature Files (15 scenarios)
│   ├── pom.xml                # Maven dependencies
│   └── testng.xml             # TestNG configuration
│
└── Selenium Project.pdf       # Project requirements
```

## Web Application

A fully functional perfume e-commerce website with:
- Product catalog with search and sorting
- Product detail pages with pricing and discounts
- Shopping cart management
- Contact form with validation
- Responsive design

**To run:** Open `web-app/index.html` in a browser

## Selenium Test Suite

Automated testing framework using:
- **BDD Framework:** Cucumber with Gherkin
- **Design Pattern:** Page Object Model (POM)
- **Test Framework:** TestNG
- **Build Tool:** Maven
- **Wait Strategy:** 100% Explicit Waits (no Thread.sleep or implicit waits)
- **Cross-Browser:** Chrome, Firefox, Edge
- **Parallel Execution:** Supported

### Test Coverage (15 Scenarios)

- **Home Page** (2 scenarios): Product discovery, add to cart
- **Shop Page** (3 scenarios): Search, sort, edge cases
- **Product Detail** (3 scenarios): Information display, pricing, data-driven tests
- **Shopping Cart** (4 scenarios): Empty state, persistence, calculations, operations
- **Contact Form** (3 scenarios): Valid submission, validation, required fields

### Running Tests

```bash
cd selenium-perfume-tests
mvn clean test
```

### Test Results
- **Total Tests:** 21 (from 15 scenarios with data-driven examples)
- **Pass Rate:** 100%
- **Execution Time:** ~24 seconds

## Key Features

✅ **Modern Selenium Best Practices**
- Explicit waits only (no Thread.sleep)
- Page Object Model for maintainability
- BDD for readable test scenarios
- Data-driven testing with Scenario Outlines

✅ **Optimized Performance**
- 43% faster test execution after explicit wait migration
- No implicit waits
- Efficient wait strategies

✅ **Comprehensive Coverage**
- E2E user journeys
- Critical functionality tests
- Edge cases and negative tests
- Cross-browser compatibility

## Technologies Used

### Web Application
- HTML5, CSS3, JavaScript
- Bootstrap for styling
- Local Storage for cart persistence

### Test Automation
- Selenium WebDriver 4.27.0
- Cucumber 7.15.0
- TestNG 7.8.0
- Maven 3.x
- Java 11

## Author

SWER312 Software Testing - Fall 2025 Project

## License

Educational Project
