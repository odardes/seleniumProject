# Insider Careers Automation Test

This project implements an automated test case for the Insider website's career page functionality using Java, Selenium WebDriver, and TestNG framework following the Page Object Model (POM) design pattern.

## Test Scenario

The automation test covers the following 5 steps:

1. **Homepage Verification**: Navigate to `https://useinsider.com/` and verify the homepage loads correctly
2. **Career Page Navigation**: Navigate through Company → Careers menu and verify the career page with its sections (Locations, Teams, Life at Insider)
3. **Job Search**: Go to QA careers page, filter jobs by Istanbul location and QA department, verify job listings
4. **Data Validation**: Check all jobs contain "Quality Assurance" in position and department, and "Istanbul, Turkey" in location
5. **Application Redirect**: Click "View Role" and verify redirection to Application form

## Technology Stack

- **Language**: Java 21
- **Framework**: TestNG 7.10.2
- **Automation**: Selenium WebDriver 4.23.0
- **Build Tool**: Maven
- **Browser**: Chrome (with WebDriverManager 5.8.0)
- **Logging**: Log4j2 2.24.1
- **Design Pattern**: Page Object Model (POM)

## Project Structure

```
sena_guventurk_case/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── insider/
│   │   │           ├── pages/
│   │   │           │   ├── BasePage.java
│   │   │           │   ├── HomePage.java
│   │   │           │   ├── CareersPage.java
│   │   │           │   └── QACareersPage.java
│   │   │           ├── utils/
│   │   │           │   ├── WebDriverFactory.java
│   │   │           │   ├── ConfigManager.java
│   │   │           │   └── LoggerUtil.java
│   │   │           └── constants/
│   │   │               └── Locators.java
│   │   └── resources/
│   │       ├── config.properties
│   │       └── log4j2.xml
│   └── test/
│       ├── java/
│       │   └── com/
│       │       └── insider/
│       │           └── tests/
│       │               └── InsiderCareersTest.java
│       └── resources/
│           └── testng.xml
├── pom.xml
└── README.md
```

## Prerequisites

- Java 21 or higher (Latest LTS recommended)
- Maven 3.6 or higher
- Chrome Browser
- IDE (IntelliJ IDEA or Eclipse)

## Setup Instructions

The test configuration is managed through `src/main/resources/config.properties`:

```properties
# Application URLs
base.url=https://useinsider.com
careers.url=https://useinsider.com/careers/
qa.careers.url=https://useinsider.com/careers/quality-assurance/

# Browser Configuration
browser=chrome
headless=false
window.size=1920,1080

# Test Data
location.filter=Istanbul, Turkey
department.filter=Quality Assurance
expected.position.text=Quality Assurance
expected.department.text=Quality Assurance
expected.location.text=Istanbul, Turkey
```

### 4. Running the Tests

#### Using Maven

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=InsiderCareersTest

# Run with specific TestNG suite
mvn test -DsuiteXmlFile=src/test/resources/testng.xml
```

### Test Methods

The test class contains the following test methods:

1. `testHomePageOpened()` - Verifies Insider home page loads correctly
2. `testCareerPageSections()` - Verifies all career page sections are displayed
3. `testQAJobsFiltering()` - Applies job filters and verifies job list
4. `testJobDataValidation()` - Validates all job data contains expected text
5. `testViewRoleRedirect()` - Verifies redirection to Lever application form

### Test Dependencies

Tests are executed in sequence using TestNG dependencies:
- Each test method depends on the successful completion of the previous one
- If any test fails, subsequent tests will be skipped

### Reporting

Test reports are generated in the following locations:
- **HTML Report**: `target/surefire-reports/index.html`
- **TestNG Report**: `target/testng-reports/index.html`
- **Logs**: `logs/insider-test.log`
- **Screenshots**: `screenshots/` (captured on failures)

## Key Features

### Page Object Model (POM)
- Clean separation between test logic and page interactions
- Reusable page methods
- Centralized element locators

### Robust Error Handling
- Comprehensive try-catch blocks
- Screenshot capture on failures
- Detailed logging for debugging

### Configuration Management
- External configuration file
- Easy parameter updates
- Environment-specific settings

## Best Practices Implemented

- ✅ **Clean Code**: Readable and maintainable code structure
- ✅ **POM Pattern**: Complete Page Object Model implementation
- ✅ **Optimized Selectors**: Efficient CSS and XPath selectors
- ✅ **Comprehensive Assertions**: Proper validation at each step
- ✅ **Error Handling**: Robust exception handling and logging
- ✅ **Configuration**: Externalized configuration management
- ✅ **Reporting**: Detailed test reports and logs

## Requirements Compliance

This implementation fully meets all specified requirements:

- ✅ **Java + Selenium + TestNG**: Preferred technology stack
- ✅ **No BDD Frameworks**: Pure TestNG implementation
- ✅ **Complete POM**: Full Page Object Model architecture
- ✅ **Optimized Selectors**: Efficient XPath and CSS selectors
- ✅ **Comprehensive Assertions**: Validation at each test step
- ✅ **Clean Code**: Readable and maintainable implementation