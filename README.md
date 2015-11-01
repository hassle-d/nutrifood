**Nutrifood API**
===========

##REQUIREMENTS :
    
    MongoDB
    NodeJS

##DEMO :

    node server.js
    
### Get Access Token :

Request URL :

    http POST http://localhost:3000/api/v1/auth/signin

POST Parameters :

    username: username
    password: password

Success :

    HTTP 200 { token: 'tokenValue' }

Errors :

    HTTP 400 {message: 'Field username missing'}
    HTTP 400 {message: 'Field password missing'}
    HTTP 400 {message: 'Wrong username or/and password'}

### Access Token Validation :

Errors :

    HTTP 401 {message: 'Missing token'}
    HTTP 401 {message: 'Invalid or expired token'}
    HTTP 401 {message: 'Invalid token'}

### Create User :

Request URL :

    http POST http://localhost:3000/api/v1/users

POST Paramters :

    R=Required, O=Optional

    R - username: String (min length 5)
    R - password: String (min length 8)
    R - email: String
    R - firstname: String
    R - lastname: String
    R - age: age Number
    O - description: String
    O - allergy: String Array
    O - religion: String