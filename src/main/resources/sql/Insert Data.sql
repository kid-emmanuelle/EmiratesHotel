INSERT INTO person(PersonID, name, mail, dateOfBirth, address) VALUES (1,'jack','jack@gmail.com','2021-12-01 14:30:15','Lyon');
INSERT INTO person(PersonID, name, mail, dateOfBirth, address) VALUES (2,'bob','bob@gmail.com','1980-05-05 12:00:00','Lille');
INSERT INTO person(PersonID, name, mail, dateOfBirth, address) VALUES (3,'emma','emma@gmail.com','1980-05-05 12:00:00','Paris');
INSERT INTO person(PersonID, name, mail, dateOfBirth, address) VALUES (4,'gilbert','gilbert@gmail.com','1980-05-05 12:00:00','Lyon');

INSERT INTO employee(PersonID, contractStart, role) VALUES (1,'2023-05-05 12:00:00','Groom');
INSERT INTO employee(PersonID, contractStart, role) VALUES (4,'2023-05-05 12:00:00','Groom');

INSERT INTO customer(PersonID, joiningDate) VALUES (2, '2021-12-01 12:00:00');
INSERT INTO customer(PersonID, joiningDate) VALUES (3, '2024-12-01 12:00:00');

INSERT INTO bookingroom(bookingID, start, end, customer, room) VALUES (1,'2024-12-08 12:00:00','2024-12-10 12:00:00',2, 1);
INSERT INTO bookingroom(bookingID, start, end, customer, room) VALUES (2,'2024-12-11 12:00:00','2024-12-14 12:00:00',2, 1);

INSERT INTO room(RoomID, type, number, price) VALUES (1, 'Triple',100, 50);
INSERT INTO room(RoomID, type, number, price) VALUES (2, 'Single',101, 50);
INSERT INTO room(RoomID, type, number, price) VALUES (3, 'Double',102, 50);
