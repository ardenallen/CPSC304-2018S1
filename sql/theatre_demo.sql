DROP TABLE Ticket;
DROP TABLE Showtime1;
DROP TABLE Showtime2;
DROP TABLE Auditorium;
DROP TABLE Movie;
DROP TABLE Booking;
DROP TABLE Loyalty_member;
DROP TABLE Customer;
DROP TABLE Manager;
DROP TABLE Employee;


CREATE TABLE Movie
  (
    title varchar(50),
    genre varchar(50),
    duration int,
    censor char(3),
    PRIMARY KEY(title)
  );

CREATE TABLE Auditorium
  (
    aID int,
    capacity int,
    PRIMARY KEY(aID)
  );

CREATE TABLE Showtime1
  (
    start_time timestamp,
    title varchar(50),
    aID int NOT NULL,
    UNIQUE(start_time, aID),
    PRIMARY KEY (start_time, title),
    FOREIGN KEY (title) REFERENCES Movie (title) ON DELETE CASCADE,
    FOREIGN KEY (aID) REFERENCES Auditorium (aID)
  );

CREATE TABLE Showtime2
  (
    cc char(1),
    title varchar(50),
    aID int,
    PRIMARY KEY (title, aID),
    FOREIGN KEY (title) REFERENCES Movie (title) ON DELETE CASCADE,
    FOREIGN KEY (aID) REFERENCES Auditorium (aID)
  );

CREATE TABLE Customer
  (
    cID int,
    name  char(30),
    PRIMARY KEY(cID)
  );

CREATE TABLE Loyalty_member
  (
    cID int,
    point_balance int,
    PRIMARY KEY(cID),
    FOREIGN KEY (cID) REFERENCES Customer (cID)
  );


CREATE TABLE Employee
  (
    eID int,
    name  char(30),
    SIN int,
    phone char(10),
    PRIMARY KEY(eID)
  );

CREATE TABLE Manager
  (
    eID int,
    PRIMARY KEY(eID),
    FOREIGN KEY (eID) REFERENCES Employee (eID) ON DELETE CASCADE
  );

CREATE TABLE Booking
  (
    transaction char(11),
    payment_method char(20),
    card_info char(16),
    eID int,
    cID int NOT NULL,
    PRIMARY KEY(transaction),
    FOREIGN KEY (eID) REFERENCES Employee (eID) ON DELETE SET NULL,
    FOREIGN KEY (cID) REFERENCES Customer (cID)
  );

CREATE TABLE Ticket
  (
    transaction char(11) NOT NULL,
    ticket_num int,
    title varchar(50) NOT NULL,
    start_time timestamp NOT NULL,
    price decimal,
    aID int NOT NULL,
    PRIMARY KEY (ticket_num),
    FOREIGN KEY (transaction) REFERENCES Booking (transaction) ON DELETE CASCADE,
    FOREIGN KEY (title) REFERENCES Movie (title) ON DELETE CASCADE,
    FOREIGN KEY (start_time, title) REFERENCES Showtime1 (start_time, title) ON DELETE CASCADE
  );

 commit;

--
-- Adding in data
--

-- INSERT Movie data
INSERT INTO Movie values('Incredibles 2', 'Family', 125, 'PG');
INSERT INTO Movie values('Deadpool 2', 'Action/Comedy', 119, '18A');
INSERT INTO Movie values('Solo: A Stars Wars Story', 'Action/Sci-Fi', 135, 'PG');
INSERT INTO Movie values('Avengers: Infinity War', 'Action/Adventure/Fantasy', 150, 'PG');
INSERT INTO Movie values('Wont you be my neighbor?', 'Documentary', 95, 'PG');

-- INSERT Auditorium data
INSERT INTO Auditorium values(1, 120);
INSERT INTO Auditorium values(2, 112);
INSERT INTO Auditorium values(3, 112);
INSERT INTO Auditorium values(4, 112);
INSERT INTO Auditorium values(5, 112);


-- INSERT Showtime data
INSERT INTO Showtime1 values('2018-06-22 12:15:00', 'Incredibles 2', 1);
INSERT INTO Showtime1 values('2018-06-22 15:25:00', 'Incredibles 2', 1);
INSERT INTO Showtime1 values('2018-06-22 18:30:00', 'Incredibles 2', 1);
INSERT INTO Showtime1 values('2018-06-22 12:55:00', 'Deadpool 2', 2);
INSERT INTO Showtime1 values('2018-06-22 16:15:00', 'Deadpool 2', 2);
INSERT INTO Showtime1 values('2018-06-22 19:30:00', 'Deadpool 2', 2);
INSERT INTO Showtime1 values('2018-06-22 12:15:00', 'Solo: A Stars Wars Story',3);
INSERT INTO Showtime1 values('2018-06-22 15:25:00', 'Solo: A Stars Wars Story', 3);
INSERT INTO Showtime1 values('2018-06-22 18:30:00', 'Solo: A Stars Wars Story', 1);
INSERT INTO Showtime1 values('2018-06-22 12:15:00', 'Avengers: Infinity War', 4);
INSERT INTO Showtime1 values('2018-06-22 15:25:00', 'Avengers: Infinity War', 4);
INSERT INTO Showtime1 values('2018-06-22 18:30:00', 'Avengers: Infinity War', 4);
INSERT INTO Showtime1 values('2018-06-23 15:00:00', 'Wont you be my neighbor?', 5);
INSERT INTO Showtime1 values('2018-06-23 17:50:00', 'Wont you be my neighbor?', 5);


-- INSERT Closed Captioning Information
INSERT INTO Showtime2 values('1', 'Incredibles 2', 1);
INSERT INTO Showtime2 values('0', 'Deadpool 2', 2);
INSERT INTO Showtime2 values('1', 'Solo: A Stars Wars Story', 3);
INSERT INTO Showtime2 values('0', 'Wont you be my neighbor?', 4);
INSERT INTO Showtime2 values('1', 'Avengers: Infinity War', 5);


-- INSERT Customer data
INSERT INTO Customer VALUES (1,'Tanek Blair');
INSERT INTO Customer VALUES (2,'Katelyn Austin');
INSERT INTO Customer VALUES (3,'Walter Clay');
INSERT INTO Customer VALUES (4,'Zachery Fry');
INSERT INTO Customer VALUES (5,'Keaton Phelps');
INSERT INTO Customer VALUES (6,'Jaime Robles');
INSERT INTO Customer VALUES (7,'Raya Casey');
INSERT INTO Customer VALUES (8,'Elizabeth Kinney');
INSERT INTO Customer VALUES (9,'Lael Spence');
INSERT INTO Customer VALUES (10,'Andrew Mcdaniel');

-- INSERT Loyalty_member data
INSERT INTO Loyalty_member VALUES (1,2718);
INSERT INTO Loyalty_member VALUES (3,8275);
INSERT INTO Loyalty_member VALUES (5,6082);
INSERT INTO Loyalty_member VALUES (7,1037);
INSERT INTO Loyalty_member VALUES (9,7692);

-- INSERT Employee Data
insert into Employee values (1, 'Francesco Rojel', 622312918, '6044987637');
insert into Employee values (2, 'Annamaria Sturr', 411928208, '6042246474');
insert into Employee values (3, 'Horten Haldon', 904562472, '6041983027');
insert into Employee values (4, 'Sephira Sollner', 586906678, '6049822147');
insert into Employee values (5, 'Galvan Jancso', 853054483, '6048908552');
insert into Employee values (6, 'Joete Hoonahan', 217745879, '6042378620');
insert into Employee values (7, 'Kiah Klezmski', 732985761, '6048038911');
insert into Employee values (8, 'Georas Cohani', 783089560, '6049163108');
insert into Employee values (9, 'Rube Theyer', 235907832, '6048080917');
insert into Employee values (10, 'Daniel Yorath', 244870340, '6041552448');

-- INSERT Manager Data
insert into Manager (eID) values (7);

commit work;