# MoneyTransferRESTApi - A dead simple Java REST API(without Spring) <br> to transfer money between accounts <br>

### Quick Start - Run
1.<br>
```$xslt
./gradlew build
```
2.<br>
```$xslt
./gradlew startApp (App will be running on http://localhost:8080)
```
### Run All Tests[Make sure nothing is running on port 8080]
```$xslt
./gradlew test
```

#Problem
```text
Design and implement a RESTful API (including data model and the backing implementation)
for money transfers between accounts.
Explicit requirements:
1. You can use Java, Scala or Kotlin.
2. Keep it simple and to the point (e.g. no need to implement any authentication).
3. Assume the API is invoked by multiple systems and services on behalf of end users.
4. You can use frameworks/libraries if you like (except Spring), but don't forget about
requirement #2 – keep it simple and avoid heavy frameworks.
5. The datastore should run in-memory for the sake of this test.
6. The final result should be executable as a standalone program (should not require
a pre-installed container/server).
7. Demonstrate with tests that the API works as expected.
Implicit requirements:
1. The code produced by you is expected to be of high quality.
2. There are no detailed requirements, use common sense.
```
#Solution 
```text
Assumption 1 : Java was chosen. Kotlin would have been sweeter & swifter <br>
but why would I be on the JVM(Jungle Of The Violent Monks) & expect an easy life? :stuck_out_tongue:

Assumption 2 : Keep it simple . What's simpler than Guice+Spark+Hibernate With Repository Pattern & <br>
a customized MVC style design? Oh,currency and account type and other minute details in banking were <br>
were taken out intentionally to keep it simple.Couldn't use one of my numerous docker templates :disappointed_relieved:

Assumption 3: API invoked by multiple systems & services? <br>
Well,I guess we need to keep it stateless.Is that not REST commandment number 1?<br>
Multi-threaded environment? Build for high concurrent load ? Kinda in those

```
