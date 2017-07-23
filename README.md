# twitter-test
Twitter public APIs testing project

*** Project structure ***
Current project is Maven project, written on Java using diferent frameworks and libraries.
In scope of current project, two test classes have been implemented:
- TwitterAPITests;
- TwitterUITests.

Each test class implements next test methods in accordance to the test task:
- getHomeTimeline;
- updateStatus;
- duplicateStatus;
- destroyStatus.

Twitter authorization informtion contains:
- for API tests: in twitter4j.properties file under src/resources directory;
- for UI tests: in TestBase.class which implements twitter login method.
Al the aforementioned credentials have been created for testing purpose and don't contain any author's personal information.

Each test class implements flow that executes test methods sequentially by it's output.

*** Resources used ***
Test framework used: TestNG http://testng.org/doc/
Logger used: https://logging.apache.org/log4j/2.x/
TwitterAPITests class uses: Twitter4J library for Twitter APIs on Java http://twitter4j.org/en/
TwitterUITests class uses: Selenide framework based on Selenium WebDriver for easy-to-write UI tests http://selenide.org/

*** How to run tests ***
To run tests, just run target class. It shouldn't require any additional settings, such as user credentials or authorization
information.
