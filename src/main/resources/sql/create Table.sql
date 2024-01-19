CREATE TABLE Person(
    PersonID int PRIMARY KEY,
    name varchar(255),
    mail varchar(255),
    dateOfBirth DATE,
    address varchar(255)
);

CREATE TABLE Employee(
    PersonID int PRIMARY KEY,
    contractStart DATE,
    role varchar(255),
    FOREIGN KEY (PersonID) REFERENCES Person(PersonID)
);

CREATE TABLE Customer(
    PersonID int PRIMARY KEY,
    joiningDate DATE,
    FOREIGN KEY (PersonID) REFERENCES Person(PersonID)

);

CREATE TABLE Room(
     RoomID int PRIMARY KEY,
     type VARCHAR(255),
     number int,
     price float
);

CREATE TABLE FoodDish(
    DishID int PRIMARY KEY,
    name VARCHAR(255),
    dishType VARCHAR(255),
    description VARCHAR(255),
    price float
);

CREATE TABLE BookingRoom(
    bookingID int PRIMARY KEY,
    start DATE,
    end DATE,
    customer int,
    room int,
    FOREIGN KEY(customer) REFERENCES Customer(PersonID),
    FOREIGN KEY(room) REFERENCES Room(RoomID)
);

CREATE TABLE BookingRestaurant(
      bookingID int PRIMARY KEY,
      customer int,
      dish int,
      FOREIGN KEY(customer) REFERENCES Customer(PersonID),
      FOREIGN KEY(dish) REFERENCES FoodDish(DishID)
);

