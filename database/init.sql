DROP TABLE if exists Calendar;
DROP TABLE if exists Person;
DROP TABLE if exists Room;
DROP TABLE if exists Carpark;
DROP TABLE if exists Employee;
DROP TABLE if exists Reservation;


CREATE TABLE Person(
    personid int,
    name text,
    firstname text,
    mail text,
    PRIMARY KEY (personid)
);

CREATE TABLE Employee(
    employeeid int,
    personid int,
    role text,
    PRIMARY KEY (employeeid),
    FOREIGN KEY (personid) references Person(personid)
);

CREATE TABLE Room(
     roomid int,

     PRIMARY KEY (roomid)

);

CREATE TABLE Reservation(
    roomid,
    personid,

    FOREIGN KEY (Roomid) references Room(roomid),
    FOREIGN KEY (personid) references Person(personid),
    PRIMARY KEY (roomid, personid)
);




CREATE TABLE Carpark(
    carparkid int,

    PRIMARY KEY (carparkid)

);


CREATE TABLE Calendar(
    calendarid int,
    customerid int,

    PRIMARY KEY (calendarid),
    FOREIGN KEY (customerid) references Person(personid)
)