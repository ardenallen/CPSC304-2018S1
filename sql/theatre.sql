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

 commit;

--
-- Adding in data
--

-- INSERT Movie data
INSERT INTO Movie values('Incredibles 2', 'Family', 125, 'PG');
INSERT INTO Movie values('Deadpool 2', 'Action/Comedy', 119, '18A');
INSERT INTO Movie values('Solo: A Stars Wars Story', 'Action/Sci-Fi', 135, 'PG');
INSERT INTO Movie values('Hotel Artemis', 'Action/Sci-Fi/Suspense', 94, '18A');
INSERT INTO Movie values('Avengers: Infinity War', 'Action/Adventure/Fantasy', 150, 'PG');
INSERT INTO Movie values('Wont you be my neighbor?', 'Documentary', 95, 'PG');
INSERT INTO Movie values('Show Dogs', 'Family', 92, 'PG');
INSERT INTO Movie values('Disobedience', 'Drama/Romance', 114, '14A');
INSERT INTO Movie values('Hereditary', 'Horror', 128, '14A');
INSERT INTO Movie values('I Feel Pretty', 'Comedy', 110, 'PG');
INSERT INTO Movie values('Book Club', 'Comedy', 104, 'PG');
INSERT INTO Movie values('On Chesil Beach', 'Drama', 110, '14A');
INSERT INTO Movie values('Tag', 'Comedy', 100, '14A');

-- INSERT Auditorium data
INSERT INTO Auditorium values(1, 120);
INSERT INTO Auditorium values(2, 112);
INSERT INTO Auditorium values(3, 112);
INSERT INTO Auditorium values(4, 112);
INSERT INTO Auditorium values(5, 112);
INSERT INTO Auditorium values(6, 180);
INSERT INTO Auditorium values(7, 180);
INSERT INTO Auditorium values(8, 180);
INSERT INTO Auditorium values(9, 307);
INSERT INTO Auditorium values(10, 307);

-- INSERT Showtime data
INSERT INTO Showtime1 values('2018-06-15 12:15:00', 'Incredibles 2', 10);
INSERT INTO Showtime1 values('2018-06-15 15:25:00', 'Incredibles 2', 10);
INSERT INTO Showtime1 values('2018-06-15 18:30:00', 'Incredibles 2', 10);
INSERT INTO Showtime1 values('2018-06-15 21:15:00', 'Incredibles 2', 10);

commit work;