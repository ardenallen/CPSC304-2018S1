DROP TABLE Ticket;
DROP TABLE Showtime1;
DROP TABLE Showtime2;
DROP TABLE Seat;
DROP TABLE Auditorium;
DROP TABLE Movie;
DROP TABLE Booking;
DROP TABLE Loyalty_member;
DROP TABLE Customer;
DROP TABLE Manager;
DROP TABLE Employee;


CREATE TABLE Movie
  (
    title char(50),
    genre char(20),
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
    title char(50),
    aID int NOT NULL,
    UNIQUE(start_time, aID),
    PRIMARY KEY (start_time, title),
    FOREIGN KEY (title) REFERENCES Movie (title) ON DELETE CASCADE,
    FOREIGN KEY (aID) REFERENCES Auditorium (aID)
  );

CREATE TABLE Showtime2
  ( 
    cc char(1),
    title char(50),
    aID int,
    PRIMARY KEY (title, aID),
    FOREIGN KEY (title) REFERENCES Movie (title) ON DELETE CASCADE,
    FOREIGN KEY (aID) REFERENCES Auditorium (aID)
  );

CREATE TABLE Seat
  (
    row_num int,
    seat_num int,
    aID int,
    isOccupied char(1),
    PRIMARY KEY (row_num, seat_num, aID),
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
    transaction int,
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
    transaction int NOT NULL,
    ticket_num int,
    title char(50) NOT NULL,
    start_time timestamp NOT NULL,
    price decimal,
    aID int NOT NULL,
    row_num int NOT NULL,
    seat_num int NOT NULL,
    UNIQUE (row_num, seat_num, start_time, aID),
    PRIMARY KEY (ticket_num),
    FOREIGN KEY (transaction) REFERENCES Booking (transaction) ON DELETE CASCADE,
    FOREIGN KEY (title) REFERENCES Movie (title) ON DELETE CASCADE,
    FOREIGN KEY (start_time, title) REFERENCES Showtime1 (start_time, title) ON DELETE CASCADE
  );


--
-- Adding in data
--
