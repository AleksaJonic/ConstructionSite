
#Tender project

This project is simple project that represents the simulation placing offers for tenders
and accepting these offers using rest api endpoints. 
We have covered few entities in this project:
 - investor(issuer)
 - bidder
 - tender
 - offer
 - user (that is bound for investor or bidder)   

#Technologies used
- java 13 
- Spring Boot
- H2
- DOCKER

#Execution
using Docker container:
```
docker build -t tender . && docker run -p 8080:8080 -it tender
```
this project can be executed from any IDE just running main method in ConstructionSiteApplication class.

#Adds
postman collection for triggering api calls is in postman folder in project root

#CURLS for triggering api calls

- CREATE_INVESTOR: 
```
curl --location --request POST 'http://localhost/construction-site/investors' \
--header 'Content-Type: application/json' \
--data-raw '{
                "name": "Investor 1",
                "description": "Some desc....",
                "users": [
                  {
                    "username": "alexa@example.com",
                    "password": "testPass"
                  }
                  
                ]
              }'
```

- FIND INVESTOR BY ID: 
```
curl --location --request GET 'http://localhost/construction-site/investors/{id}' \
--data-raw ''
```

- CREATE BIDDER: 
```
curl --location --request POST 'http://localhost/construction-site/bidders' \
--header 'Content-Type: application/json' \
--data-raw '{
                "name": "bidder1",
                "workingReference": true,
                "users": [
                  {
                    "username": "bidder1@example.com",
                    "password": "testPass"
                  },
                  {
                    "username": "bidder2@example.com",
                    "password": "testPass2"
                  }
                ]
              }'
```
- FIND BIDDER: 
```
curl --location --request GET 'http://localhost/construction-site/bidders/{id}' \
--data-raw ''
```
- CREATE TENDER: 
```
curl --location --request POST 'http://localhost/construction-site/tenders' \
--header 'Content-Type: application/json' \
--data-raw '{
                "name": "Tender 1",
                "description": "Tender description 1",
                "user": {
                    "id": "bae3d4c6-a788-42e6-9b61-d7ab1b72e6b6"
                }
            }'
```
- FIND TENDER BY ID: 
```
curl --location --request GET 'http://localhost/construction-site/tenders/{id}' \
--data-raw ''
```

- FIND TENDER BY : userId and/or investorId (it can be used as query params)
```
curl --location --request GET 'http://localhost/construction-site/tenders?userId={id}' \
--data-raw ''
```

- PLACE OFFER: (user id must be id of user that represents bidder)
```
curl --location --request POST 'http://localhost/construction-site/offers' \
--header 'Content-Type: application/json' \
--data-raw '{
    "description": "Some description of this offer",
    "amount": 150000,
    "user": {
       "id":{id},    
    },
    "tender":{
        "id":{id},
    }
}'
```

- FIND OFFER BY ID : 
```
curl --location --request GET 'http://localhost/construction-site/offers/{id}' \
--data-raw ''
```

- FIND OFFER BY PARAMS : (userId and/or bidderId and/or tenderId)
```
curl --location --request GET 'http://localhost/construction-site/offers?userId={userId}' \
--data-raw ''
```
- ACCEPT OFFER : 
```
curl --location --request PUT 'http://localhost/construction-site/offers/{id}/accept' \
--header 'Content-Type: application/json' \
--data-raw '{
    "acceptUserId" : {id},
    "forceAccept" : true
}'
```
Accept offer explanation:
    acceptUserId -> must be the user that represents investor
    forceAccept -> not mandatory, but if its empty program will prevent for accepting any offer , 
    it will trigger check to see are there better offers for specific tender.  


#Improvements
- Not all features are covered with tests
- Some authentication is required, and can be implemented using oauth2 or jwt token.
  I started with this idea but didn't finish, so this code is not available.
- Response of some objects have redundant data , this can be patched
- system logs are not available in this code version
