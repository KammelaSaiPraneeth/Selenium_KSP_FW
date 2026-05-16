# ProjectSPK — Selenium + Java Test Automation Framework

> A production-grade, architect-level test automation framework built with Selenium WebDriver, Java, and TestNG.
> Designed for scalability, parallel execution, and CI/CD integration.

---

## 👨‍💻 Author
**Sai** | QA Lead → QA Architect  
Framework: `Selenium + Java + TestNG + ExtentReports + Log4j2`

---

## 📋 Table of Contents
- [Framework Overview](#framework-overview)
- [Tech Stack](#tech-stack)
- [Folder Structure](#folder-structure)
- [Framework Features](#framework-features)
- [Prerequisites](#prerequisites)
- [Setup & Installation](#setup--installation)
- [How to Run Tests](#how-to-run-tests)
- [Configuration](#configuration)
- [Test Data Management](#test-data-management)
- [Reporting](#reporting)
- [Parallel Execution](#parallel-execution)
- [CI/CD Integration](#cicd-integration)
- [Design Patterns Used](#design-patterns-used)
- [Contributing](#contributing)

---

## 🏗️ Framework Overview

```
ProjectSPK is a full-stack test automation framework that supports:
  ✅ UI Testing        — Selenium WebDriver
  ✅ API Testing       — RestAssured
  ✅ DB Validation     — JDBC
  ✅ Parallel Execution — ThreadLocal WebDriver
  ✅ Data Driven       — Excel + JSON
  ✅ Multi-Environment — dev / test / staging
  ✅ Custom Reporting  — ExtentReports HTML
  ✅ Logging           — Log4j2
```

---

## 🛠️ Tech Stack

| Tool / Library         | Version     | Purpose                          |
|------------------------|-------------|----------------------------------|
| Java                   | 11+         | Programming language             |
| Selenium WebDriver     | 4.x         | Browser automation               |
| TestNG                 | 7.x         | Test execution framework         |
| ExtentReports          | 5.x         | Custom HTML reporting            |
| Log4j2                 | 2.x         | Logging framework                |
| Apache POI             | 5.x         | Excel data handling              |
| Gson / Jackson         | Latest      | JSON data handling               |
| RestAssured            | 5.x         | API testing                      |
| JDBC                   | Built-in    | Database validation              |
| Maven                  | 3.8+        | Build & dependency management    |
| WebDriverManager       | Custom      | Singleton + ThreadLocal driver   |
-----------------------------------------------------------------------------


## 📁 Folder Structure

```
ProjectSPK/
│
├── src/
│   ├── main/
│   │   └── java/
│   │       └── org/
│   │           ├── BaseTestLayer/
│   │           │   ├── BaseTestClass.java         # @Before/@After lifecycle
│   │           │   └── WebDriverManager.java      # Singleton + ThreadLocal driver
│   │           │
│   │           ├── commonUtilities/
│   │           │   ├── DbUtils.java               # JDBC DB validation
│   │           │   ├── ExcelAndJsonUtils.java      # Excel + JSON data reading
│   │           │   ├── utilitiesClass.java         # Common helper methods
│   │           │   └── waitsUtil.java              # Centralized wait strategies
│   │           │
│   │           ├── config/
│   │           │   └── ConfigManager.java          # Singleton config reader
│   │           │
│   │           ├── Listerners/
│   │           │   ├── listenerClassUtility.java   # ITestListener implementation
│   │           │   └── testRetryAnalyserListerner.java # Retry failed tests
│   │           │
│   │           ├── PageLayer/
│   │           │   └── loginPage.java              # Page Object — Login
│   │           │
│   │           └── TestNGUtilities/
│   │               └── ExtentReportManager.java    # Extent report singleton
│   │
│   └── test/
│       ├── java/
│       │   └── com/
│       │       ├── APITest/                        # API test classes
│       │       ├── TestExecution/                  # Test execution classes
│       │       └── TestLayer/                      # UI test classes
│       │
│       └── resources/
│           ├── config/
│           │   ├── dev.properties                  # Dev environment config
│           │   └── test.properties                 # Test environment config
│           │
│           ├── suites/
│           │   ├── regression.xml                  # Full regression suite
│           │   ├── smoke.xml                       # Smoke test suite
│           │   └── sanity.xml                      # Sanity test suite
│           │
│           ├── testdata/
│           │   ├── excel/                          # Excel test data files
│           │   └── json/                           # JSON test data files
│           │
│           └── log4j2.xml                          # Log4j2 configuration
│
├── reports/                                        # Auto-generated (gitignored)
├── Screenshots/                                    # Auto-generated (gitignored)
├── .gitignore
├── pom.xml
└── README.md
```

---

## ✨ Framework Features

### 1. 🔒 Thread-Safe Parallel Execution
- `ThreadLocal<WebDriver>` ensures each test thread gets its own browser instance
- Zero interference between parallel test runs
- Configurable thread count via TestNG XML

### 2. 🏭 Singleton Design Pattern
- `WebDriverManager` — single instance manages all driver operations
- `ConfigManager` — single instance reads all environment configs
- `ExtentReportManager` — single instance manages reporting

### 3. 📄 Data Driven Testing
- Excel support via Apache POI
- JSON support via Gson/Jackson
- Execution flag per test case — enable/disable tests from Excel sheet

### 4. 🌍 Multi-Environment Support
- Switch environments via Maven `-Denv` flag
- Separate `.properties` files per environment
- No code changes needed to switch environments

### 5. 📊 Custom Extent Reporting
- HTML report with pass/fail/skip summary
- Custom table with test execution percentage
- System info (suite name, environment, browser)
- Auto-attached screenshots on failure

### 6. 🔄 Smart Retry Mechanism
- Failed tests auto-retry configurable number of times
- Implemented via `IRetryAnalyzer`
- Retry count configurable from properties file

### 7. 📝 Log4j2 Logging
- No `System.out.println` anywhere in the framework
- Separate log files for all logs and error logs
- Rolling file appender — logs rotate automatically

### 8. ⏳ Centralized Wait Utility
- Explicit waits — visible, clickable, presence
- Fluent wait — for dynamic/AJAX elements
- Page load wait — JavaScript readyState check
- No `Thread.sleep()` in test code

### 9. 🗄️ Database Validation
- JDBC-based DB utility
- Validate data after UI/API actions
- End-to-end flow: UI → API → DB

### 10. 🔌 API Testing Layer
- RestAssured integration
- GET / POST / PUT / DELETE support
- Response validation utilities

---

## ✅ Prerequisites
Make sure the following are installed before running the framework:
```bash
# Java 11 or higher
java -version

# Maven 3.8 or higher
mvn -version

# Git
git --version
```
------------------------------------------------------------------------------------
## 🎨 Design Patterns Used ##

| Pattern                    | Where Used                          | Purpose                              |
|----------------------------|-------------------------------------|--------------------------------------|
| **Singleton**              | `WebDriverManager`, `ConfigManager` | Single instance across framework     |
| **ThreadLocal**            | `WebDriverManager.tdriver`          | Thread-safe parallel execution       |
| **Page Object Model (POM)**| `PageLayer/`                        | Separate locators from test logic    |
| **Factory**                | `WebDriverManager.initDriver()`     | Create browser instances dynamically |
| **Dependency Injection**   | `BasePage(WebDriver driver)`        | Pass driver without tight coupling   |
| **Composition**            | `BasePage HAS-A WaitsUtil`          | Prefer composition over inheritance  |
|---------------------------------------------------------------------------------------------------------|