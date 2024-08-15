# INTRODUCTION
This application is backend program that built using Java as the programming language and using Spring Boot Framework. For database management this app using PostgreSQL and also implements JWT Security for user authentication and generating token.

Developed by : Nabila Suci Syabani - Batch #17 Offline Jakarta

### AUTHENTICATION
#### REGISTER
```http
  POST /api/register  
```
##### INPUT
```json
{
  "email" : "user5@email.com",
  "username" : "user123",
  "password" : "userpass123"
}
```
##### OUTPUT
![img.png](img.png)

#### LOGIN
```http
  POST /api/login  
```
##### INPUT
```json
{
  "username" : "user123",
  "password" : "userpass123"
}
```
##### OUTPUT
![img_1.png](img_1.png)![alt text](image-1.png)


### CATEGORY
#### CREATE CATEGORY (AUTHENTICATED)
```http
  POST /api/categories
```
##### INPUT
````
Authorization (Add Login Token)
Type - Bearer Token
Token = {add token when login}
````
![img_2.png](img_2.png)
```json
{
  "name" : "Food and Drink"
}
```
##### OUTPUT
![img_3.png](img_3.png)

#### GETALL CATEGORY (AUTHENTICATED)
```http
  GET /category-products
```
##### INPUT
````
Authorization (Add Login Token)
Type - Bearer Token
Token = {add token when login}
````
![img_2.png](img_2.png)

##### OUTPUT
![img_7.png](img_7.png)


#### UPDATE BY ID CATEGORY (AUTHENTICATED)
```http
  PUT /categories/{id}
```
##### INPUT
````
Authorization (Add Login Token)
Type - Bearer Token
Token = {add token when login}
````
![img_2.png](img_2.png)
```json
{
  "name" : "Saving and Pension Fund"
}
```
##### OUTPUT
![img_8.png](img_8.png)



### EXPENSES
#### CREATE EXPENSES (AUTHENTICATED)
```http
  POST /api/expenses
```
##### INPUT
````
Authorization (Add Login Token)
Type - Bearer Token
Token = {add token when login}
````
![img_2.png](img_2.png)
```json
{
  "description": "Chatime Chocolate Hazelnut Large",
  "amount" : "33000",
  "date": "2024-08-15",
  "category_id" : "2"
}
```
##### OUTPUT
![img_4.png](img_4.png)

#### GET EXPENSES (AUTHENTICATED)
```http
  GET /api/expenses
```
##### INPUT
````
Authorization (Add Login Token)
Type - Bearer Token
Token = {add token when login}
````
![img_2.png](img_2.png)
##### OUTPUT
![img_5.png](img_5.png)


#### UPDATE BY ID EXPENSES (AUTHENTICATED)
```http
  PUT /api/expenses/{id}
```
##### INPUT
````
Authorization (Add Login Token)
Type - Bearer Token
Token = {add token when login}
````
![img_2.png](img_2.png)
```json
{
  "description": "Nabung Reksadana Obligasi Bibit (Update)",
  "amount" : "400000",
  "date": "2024-08-16",
  "category_id" : "1"
}
```
##### OUTPUT
![img_6.png](img_6.png)
