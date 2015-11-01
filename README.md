**Nutrifood API**
===========

##REQUIREMENTS :
    
    MongoDB
    NodeJS

##DEMO :

    node server.js
    
###Access Token :

Request URL :
    http POST http://localhost:4242/api/v1/auth/signin

POST Parameters :
    username: username
    password: password

Request Errors :
    HTTP 400 {message: 'Field username missing'}
    HTTP 400 {message: 'Field password missing'}