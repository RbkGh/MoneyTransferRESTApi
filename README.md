# MoneyTransferRESTApi - A dead simple Java REST API(without Spring) <br> to transfer money between accounts <br>
### Shot For 100% Code Coverage
![alt text](https://github.com/RbkGh/MoneyTransferRESTApi/blob/master/demo_test.png)
### Requirements
> Java 8+
### Quick Start - Run
1.In Project Root Directory,type <br/>
```$xslt
 java -jar money-transfer-api-1.0.jar (App will be running on http://localhost:8080 ,if nothing is running on 8080)
```
or
```$xslt
1) ./gradlew build
2) ./gradlew startApp (App will be running on http://localhost:8080 ,if nothing is running on 8080)
```
### Run All Tests[Make sure nothing is running on port 8080]
```$xslt
./gradlew test
```

### Problem
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
### Solution 
> Why do I find this interesting ? The last time I worked on a rest api in java without Spring boot was in somewhere<br>
2015 when I had the opportunity to deploy Ghana TV License payment channel - Jersey with raw java.<br>
It's just fun to try to build without any spring magic,And I get to go low level again,NEW LEARNINGS!! :fire::fire::fire:

>Assumption 1 : Java was chosen. Kotlin would have been sweeter & swifter <br>
but why would I be on the JVM(Jungle Of The Violent Monks) & expect an easy life? :stuck_out_tongue:

>Assumption 2 : Keep it simple . What's simpler than Guice+Spark+Hibernate With Repository Pattern & <br>
a customized MVC style design? Oh,currency and account type and other negligible details that doesn't make money get lost in banking were <br>
taken out intentionally to keep it simple.Couldn't use one of my numerous docker templates :disappointed_relieved:

>Assumption 3: API invoked by multiple systems & services? <br>
Well,I guess we need to keep it stateless.Is that not REST commandment number 1?<br>
Multi-threaded environment? Build for high concurrent requests ?<br>
Did I hear someone say Sparkjava does 200 ThreadPools by default [here](https://stackoverflow.com/a/54132981/7315979) ?

>Assumption 4: Point 5 mentioned in memory,H2 inmemory db was used for this 
There is a code snippet which is worth a shoutout : 
```java

  @Override
    public void updateAccountBalancesAndTransactionLog(AccountEntity updatedSenderAccountBalance,
                                                       AccountEntity updatedRecieverAccountBalance,
                                                       AccountTransactionEntity accountTransactionEntity) {

        entityManager.getTransaction().begin();

        entityManager.persist(updatedSenderAccountBalance);
        entityManager.persist(updatedRecieverAccountBalance);
        entityManager.persist(updateTransactionEntity(accountTransactionEntity,
                TransactionStatus.SUCCESS,
                ""));

        entityManager.getTransaction().commit();
    }

```
> Above code inside AccountEntityRepository ensures that nobody's money is lost anywhere,unless it's crediting my account :runner: :runner: :collision:

> Still curious? Checkout a 2min audio introduction of the codebase :arrow_right: :arrow_right:  [RIGHT HERE...](http://bit.ly/2Zukbgn) 



## Application usage
### Accounts
#### Create an account
The following request creates an account and returns empty body with 201 status code
```
    POST localhost:8080/api/accounts
    { 
    "name":"ace", 
    "emailAddress":"ace@g.com", 
    "accountBalance":5000 
    }
```
Response:
```
    HTTP 201 Created
    <Response body is empty>
```
#### Get All Accounts 
The following request gets all accounts :
```
    GET http://localhost:8080/accounts
    
```
Response:
```
    HTTP 200 OK
   [
     {
       "id": 1,
       "name": "ace",
       "emailAddress": "ace@g.com",
       "accountBalance": 5000
     }
   ]
```

#### Delete an account
The following request deletes an account:
```
    DELETE localhost:8080/api/accounts/0
```
Response:
```
    HTTP 204 No Content
```


#### Create Money Transfer Transaction
Transfer money from one account to another:
```
    POST http://localhost:8080/accounts/1/transactions [Account with id =1 will be debited 1 and recieving account will be credited 1 
    
    { 
    "sendingAccountId":1, 
    "receivingAccountId":2, 
    "transactionAmount":1 
    }
```
Response:
```
    HTTP 201 No Content
```
#### Retrieve all money transfer transactions on specific account id
The folowing request retrieves all money transfer transactions on specific account id
```
    GET http://localhost:8080/accounts/1/transactions
```
Response:
```
    HTTP 200 OK
    [
      {
        "id": 3,
        "sendingAccountId": 1,
        "receivingAccountId": 2,
        "transactionAmount": 1,
        "transactionStatus": "SUCCESS",
        "dateOfTransaction": "Aug 27, 2019",
        "reason": ""
      },
      {
        "id": 4,
        "sendingAccountId": 1,
        "receivingAccountId": 2,
        "transactionAmount": 100000,
        "transactionStatus": "FAILED",
        "dateOfTransaction": "Aug 27, 2019",
        "reason": "Not Enough Balance to initiate transaction"
      }
    ]

```
#### Retrieve one account
The following request retrieves one account in the datastore
```
    GET http://localhost:8080/accounts/1
```
Response:
```
    HTTP 200 OK
{
  "id": 1,
  "name": "ace",
  "emailAddress": "ace@g.com",
  "accountBalance": 4999
}
```
### Delete
#### Delete Account
The following request deletes an account by its id
```
    DELETE http://localhost:8080/accounts/1
   
```
Response:
```
    HTTP 204 No Content
 
```
