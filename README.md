# 🔧 LambdaTest Demo Repository

## 📌 Fork This Repository
Start by forking this repository into your GitHub account.

## 🔐 Set Up Your LambdaTest Credentials
Before running any tests, make sure to add your LambdaTest credentials as GitHub repository secrets:
- `LT_USERNAME`
- `LT_ACCESS_KEY`

You can add these under **Settings > Secrets and variables > Actions** in your GitHub repo.

## 🚀 Running the Tests

### ▶️ Via GitHub Actions
1. Navigate to the **Actions** tab of your forked repository
2. Select the **Run Tests** workflow
3. Click **Run workflow** and choose the specific test suite you'd like to run from the dropdown

### 💻 Via Command Line
You can also run tests locally using Maven or Node.js commands. Below are the available test suites:

## 🧪 Web Browser Automation

```bash
# Single test
mvn test -D suite=single.xml

# Accessibility test
mvn test -D suite=accessibility.xml

# Parallel test
mvn test -D suite=parallel.xml
```

## 📱 Mobile Browser Automation

```bash
mvn test -D suite=mobile.xml
```

## 🤖 Android App Automation

```bash
# Single test
mvn test -D suite=android_single.xml

# Parallel test
mvn test -D suite=android_parallel.xml
```

## 🍏 iOS App Automation

```bash
# Single test
mvn test -D suite=iOS_single.xml

# Parallel test
mvn test -D suite=iOS_parallel.xml
```

## 🧪 Cypress Tests

```bash
npm test
```

## 🎭 Playwright Tests

```bash
# Single-threaded
node playwright_single.js

# Parallel with 3 threads
node playwright_parallel.js
```
