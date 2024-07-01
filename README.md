It's a JDBC Project. CarShowRoom Management System Project using Curd Operation.
To create this project I have use Eclipse and MYSQL Command Line Client

ER Diagram in detail:-

Three entities: Cars, Customers, and Sales.
~The Cars entity has four attributes: car_id (primary key), model, make, price, and year.
~The Customers entity has three attributes: cus_id (primary key), name, email, and phone.
~The Sales entity has three attributes: sale_id (primary key), car_id (foreign key referencing Cars), cus_id (foreign key referencing Customers), and sale_date.

Relation between entities:
~The relationship between Cars and Sales is many-to-one, as one car can be sold multiple times, but each sale involves only one car.
~The relationship between Customers and Sales is many-to-one, as one customer can make multiple purchases, but each sale involves only one customer.
~The relationship between Cars and Customers is many-to-many, as one car can be sold to multiple customers, and one customer can purchase multiple cars. This relationship is represented by the Sales entity, which has foreign keys referencing both Cars and Customers.
