# iCommerce Database

## _Design Diagram_

![database-icommerce](https://raw.githubusercontent.com/haivutuan93/icommerce/master/src/main/resources/image/DatabaseDesign.png)

## Design Diagram Explain
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