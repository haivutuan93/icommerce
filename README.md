# iCommerce
## Architecture Overview
This is Architecture Overview Diagram, in here i apply some technology

* **Spring Boot** and **Spring Cloud Zuul** for handle Microservices Architecture.
* **Spring Security** for authenticate request
* **MySql** for store persistence Data, **MongoDB** for filter Product and get detail of Product, request of get Product is a lot so need to have other DB for query, avoid affecting influence on others flow (add Product to Cart, make Order...). Document database like **MongoDB** is the good choice for get and filter
* **RabbitMQ** for handle event when change information of Product(add, update Product) and send Event to store new data of to **MongoDB**

![database-icommerce](https://github.com/haivutuan93/icommerce/blob/master/src/main/resources/image/ArchitectDiagram.png?raw=true)

## Deployment Overview
I applyed CI/CD for Project, use some technology:
* **Github** (https://github.com/haivutuan93/icommerce/tree/master)
* **Jenkins** (http://ec2-54-173-62-36.compute-1.amazonaws.com:8080/job/iCommerce/). For auto build image, push image to AWS ECR and deploy to image to AWS EC2, i installed a Jenkins in an EC2 instance
* **AWS ECR** (121152775641.dkr.ecr.us-east-1.amazonaws.com/icommerce) for store image of iCommerce
* **AWS EC2** (http://ec2-3-94-145-92.compute-1.amazonaws.com:8761/) an server to deploy Project, can check eureka server and what's service running in above url

![deployment-icommerce](https://github.com/haivutuan93/icommerce/blob/master/src/main/resources/image/Deployment.png?raw=true)



## iCommerce Database
MySQL information: jdbc:mysql://ec2-54-173-62-36.compute-1.amazonaws.com:3306/icommerce
User/Password please contact can ask me if need
SQL file: [iCommerce Database](https://github.com/haivutuan93/icommerce/blob/main/src/main/resources/sql/summary.sql)
### Design Diagram

![database-icommerce](https://raw.githubusercontent.com/haivutuan93/icommerce/master/src/main/resources/image/DatabaseDesign.png)

### Design Diagram Explain
In iCommerce database, Product is the central of all functions
* Category map many to many with Product, by table Product_Category
* Colour map many to many with Product, by table Product_Colour
* A Brand can has a lot of Products, a Product just can owner by 1 Brand, Brand map 1 to many with Product
* Cart map many to many with Product, by table Cart_Products
* User can has a lot of Carts, and a Cart have to owner by an User, User map 1 to many with Cart
* User has a list of Shipping_Address(in home or in company), User map 1 to many with Shipping_Address
* User can has some Carts, User map 1 to many with Cart
* A Cart should be have Shipping_Address to easy for order, map between Cart and Shipping_Address
* When completed Cart, create new Purchase_Order for this Cart, map between Cart and Purchase_Order
* Purchase_Order has to owner by an User, User map 1 to many with Purchase_Order
* Purchase_Order should has Order_Shipping_Progress for User can check where is your Order in, map between Purchase_Order and Order_Shipping_Progress

## API Docs
* _API register new User_
```sh
curl --location --request POST 'ec2-3-94-145-92.compute-1.amazonaws.com:8762/auth/register' \
--header 'Content-Type: application/json' \
--data-raw '{
    "username": "hellonab1",
    "password": "12345"
}'
```
* _API Login and Get Token_ - (Get token and Use this Token for other API what need Authen)
  **Note: Token return in Header**
```sh
curl --location --request POST 'ec2-3-94-145-92.compute-1.amazonaws.com:8762/auth' \
--header 'Content-Type: application/json' \
--data-raw '{
    "username": "hellonab",
    "password": "12345"
}'
```
* _API Filter Product By Brand, Category, Colour, Price_ - (Don't need Token)
```sh
curl --location --request GET 'http://ec2-3-94-145-92.compute-1.amazonaws.com:8762/icommerce/product/filter' \
--header 'Content-Type: application/json' \
--data-raw '{
    "brand": "Apple"
}'
```
* _API Create New Product_ - (Need Token, please get another Token from Header API Login)
```sh
curl --location --request POST 'http://ec2-3-94-145-92.compute-1.amazonaws.com:8762/icommerce/product/add' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJuYWIiLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwiaWF0IjoxNjYzODA3MTQ2LCJleHAiOjE2NjM4NDMxNDZ9.ikYo-M94iFlHeCk7r1T70KewkLYDfvx0SusvVFn54hl7YZZErCY1oQa6RILiYUZ13G0MBg06VnOOQ_qQju3RnA' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "Macbook M1",
    "title": "Macbook M1",
    "description": "Macbook M1",
    "brand": "Apple",
    "price": 1500,
    "quantity": 2000,
    "categories": ["Laptop"],
    "colours": ["Orange", "Blue"]
}'
```
* _API add Product to Cart_ - If use cartId in RequestBody, will add to exist Cart, else create new Cart and add Product to that. (Need Token, please get another Token from Header API Login)
```sh
curl --location --request POST 'http://ec2-3-94-145-92.compute-1.amazonaws.com:8762/icommerce/cart/add-product' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJoYWl2dCIsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJpYXQiOjE2NjM4Mjg2NTksImV4cCI6MTY2Mzg2NDY1OX0.A7mQevaeOaq_tSuM5JgysvD0QAkM2Oa_stEFycG8V4EMW523ZjljfahEUNzS7vZB3t6h_Sjpmjs39T2IdO4pWw' \
--header 'Content-Type: application/json' \
--data-raw '{
    "productId": 1
}'
```
* _API Confirm Cart_ - Just can confirm a Cart with status IN_PROGRESS, after that change Cart to COMPLETED and create new Order from this Cart with Order status IN_PROGRESS (Need Token, please get another Token from Header API Login)
```sh
curl --location --request POST 'http://ec2-3-94-145-92.compute-1.amazonaws.com:8762/icommerce/cart/confirm' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJoYWl2dCIsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJpYXQiOjE2NjM4Mjg2NTksImV4cCI6MTY2Mzg2NDY1OX0.A7mQevaeOaq_tSuM5JgysvD0QAkM2Oa_stEFycG8V4EMW523ZjljfahEUNzS7vZB3t6h_Sjpmjs39T2IdO4pWw' \
--header 'Content-Type: application/json' \
--data-raw '{
    "cartId": 3
}'
```

