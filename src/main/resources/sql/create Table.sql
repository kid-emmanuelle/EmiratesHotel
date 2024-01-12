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

CREATE TABLE Command(
    OrderID int PRIMARY KEY,
    payment DATE,
    customer int,
    FOREIGN KEY (customer) REFERENCES Customer(PersonID)
);

CREATE TABLE BookingRoom(
    bookingID int PRIMARY KEY ,
    description varchar(255),
    start DATE,
    end DATE,
    employee int,
    FOREIGN KEY(employee) REFERENCES Employee(PersonID)
);

CREATE TABLE Room(
    RoomID int PRIMARY KEY,
    type VARCHAR(255),
    number int,
    price float
);

CREATE TABLE ListRooms(
    RoomID int,
    bookingID int,
    PRIMARY KEY (RoomID, bookingID),
    FOREIGN KEY (RoomID) REFERENCES Room(RoomID),
    FOREIGN KEY (bookingID) REFERENCES BookingRoom(bookingID)
);

CREATE TABLE BookingRestaurant(
    bookingID int PRIMARY KEY,
    employee int,
    price float,
    FOREIGN KEY(employee) REFERENCES Employee(PersonID)
);

CREATE TABLE FoodDish(
    DishID int PRIMARY KEY,
    name VARCHAR(255),
    dishType VARCHAR(255),
    description VARCHAR(255),
    price float,
    bookingID int,
    FOREIGN KEY (bookingID) REFERENCES BookingRestaurant(bookingID)
);

