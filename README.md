**Nutrifood API**
===========

##REQUIREMENTS :
    
    MongoDB
    NodeJS

##INSTALLATION :

    1 - Install MongoDB and NodeJS
    2 - Move to server root directory ( src/server )
    3 - Install preject dependency ( npm install )

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
    O - special: String (Muslim, Vegetarian etc..)

## Next endpoints need authorization var in header (Authorization: token_value)

### Get Users :

Request URL :

    http GET http://localhost:3000/api/v1/users

Success :

    HTTP 200 { Tableau d'objects users }

Errors :

    HTTP 500

### Get Specific user

Request URL :

    http GET http://localhost:3000/api/v1/users/<USER_ID>
    http GET http://localhost:3000/api/v1/users/<USERNAME>

Success :

    HTTP 200 { Object user }

Errors :

    HTTP 500

### Update User :

Request URL :

    http PUT http://localhost:3000/api/v1/users/<USER_ID>

PUT Paramters :

    R=Required, O=Optional

    O - password: String (min length 8)
    O - email: String
    O - firstname: String
    O - lastname: String
    O - age: age Number
    O - description: String
    O - allergy: String Array
    O - special: String (Muslim, Vegetarian etc..)

Success :

    http 200 { Object user }

Errors :

    http 500

### New Category

Request URL :

    http POST http://localhost:3000/api/v1/category

POST Parameters :

    name: String

Success :

    http 201

Errors :

    http 400 {name: 'ValidationError', message: 'Missing category name'}
    http 500

### Get Category

Request URL :

    http GET http://localhost:3000/api/v1/category
    http GET http://localhost:3000/api/v1/category/<CATEGORY_ID>

### Delete Category

Request URL :

    http DELETE http://localhost:3000/api/v1/category/<CATEGORY_ID>

### Create Meal

Request URL :

    http POST http://localhost:3000/api/v1/meals

POST Paramters :

    R=Required, O=Optional

    R - author: String
    R - name: String
    R - description: String
    R - instruction: String
    R - difficulty: String
    R - cooktime: Number ('HH:mm')
    R - category: String
    R - ingredients: String Array
    O - nutritionfact: String
    O - video: String (url)
    O - image: File

Success :

    http 201

Errors :

    http 500

### Update Meal

Request URL :

    http PUT http://localhost:3000/api/v1/meals/<MEAL_ID>

POST Paramters :

    R=Required, O=Optional

    O - author: String
    O - name: String
    O - description: String
    O - instruction: String
    O - difficulty: String
    O - cooktime: Number ('HH:mm')
    O - category: String
    O - ingredients: String Array
    O - nutritionfact: String
    O - video: String (url)
    O - image: File

Success :

    http 201

Errors :

    http 500

### Get Meal

Request URL :

    http GET http://localhost:3000/api/v1/meals
    http GET http://localhost:3000/api/v1/meals/<MEAL_ID>
    http GET http://localhost:3000/api/v1/meals/name/<MEAL_NAME>

Success :

    http 200 { Object meal }

    Note : image value is the image id

### Get Meal Image

Request URL :

    http GET http://localhost:3000/api/v1/meals/image/<IMAGE_ID>

Success :

    http 200 <image file>

Errors :

    http 404

