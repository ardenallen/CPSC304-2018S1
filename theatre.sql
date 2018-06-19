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
    censor varchar(3),
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
    name  varchar(30),
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
    name varchar(30),
    SIN int,
    phone char(10),
    PRIMARY KEY(eID),
    CONSTRAINT check_phone
    CHECK (REGEXP_LIKE (phone, '^\d{10}$')),
    CONSTRAINT check_length
    CHECK (LENGTH(name) >= 3)
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
    payment_method varchar(20),
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
INSERT INTO Showtime1 values('2018-06-22 12:15:00', 'Incredibles 2', 10);
INSERT INTO Showtime1 values('2018-06-22 15:25:00', 'Incredibles 2', 10);
INSERT INTO Showtime1 values('2018-06-22 18:30:00', 'Incredibles 2', 10);
INSERT INTO Showtime1 values('2018-06-22 21:15:00', 'Incredibles 2', 10);
INSERT INTO Showtime1 values('2018-06-23 11:45:00', 'Incredibles 2', 10);
INSERT INTO Showtime1 values('2018-06-23 15:00:00', 'Incredibles 2', 10);
INSERT INTO Showtime1 values('2018-06-23 17:50:00', 'Incredibles 2', 10);
INSERT INTO Showtime1 values('2018-06-23 20:35:00', 'Incredibles 2', 10);
INSERT INTO Showtime1 values('2018-06-22 12:55:00', 'Deadpool 2', 9);
INSERT INTO Showtime1 values('2018-06-22 16:15:00', 'Deadpool 2', 9);
INSERT INTO Showtime1 values('2018-06-22 19:30:00', 'Deadpool 2', 9);
INSERT INTO Showtime1 values('2018-06-22 22:15:00', 'Deadpool 2', 9);
INSERT INTO Showtime1 values('2018-06-23 12:25:00', 'Deadpool 2', 9);
INSERT INTO Showtime1 values('2018-06-23 16:00:00', 'Deadpool 2', 9);
INSERT INTO Showtime1 values('2018-06-23 19:10:00', 'Deadpool 2', 9);
INSERT INTO Showtime1 values('2018-06-23 22:00:00', 'Deadpool 2', 9);
INSERT INTO Showtime1 values('2018-06-22 12:15:00','Solo: A Stars Wars Story',1);
INSERT INTO Showtime1 values('2018-06-22 15:25:00', 'Solo: A Stars Wars Story', 1);
INSERT INTO Showtime1 values('2018-06-22 18:30:00', 'Solo: A Stars Wars Story', 1);
INSERT INTO Showtime1 values('2018-06-22 21:15:00', 'Solo: A Stars Wars Story', 1);
INSERT INTO Showtime1 values('2018-06-23 11:45:00', 'Solo: A Stars Wars Story', 1);
INSERT INTO Showtime1 values('2018-06-23 15:00:00', 'Solo: A Stars Wars Story', 1);
INSERT INTO Showtime1 values('2018-06-23 17:50:00', 'Solo: A Stars Wars Story', 1);
INSERT INTO Showtime1 values('2018-06-23 20:35:00', 'Solo: A Stars Wars Story', 1);
INSERT INTO Showtime1 values('2018-06-22 12:15:00', 'Avengers: Infinity War', 4);
INSERT INTO Showtime1 values('2018-06-22 15:25:00', 'Avengers: Infinity War', 4);
INSERT INTO Showtime1 values('2018-06-22 18:30:00', 'Avengers: Infinity War', 4);
INSERT INTO Showtime1 values('2018-06-22 21:15:00', 'Avengers: Infinity War', 4);
INSERT INTO Showtime1 values('2018-06-23 11:45:00', 'Avengers: Infinity War', 4);
INSERT INTO Showtime1 values('2018-06-23 15:00:00', 'Wont you be my neighbor?', 3);
INSERT INTO Showtime1 values('2018-06-23 17:50:00', 'Wont you be my neighbor?', 3);
INSERT INTO Showtime1 values('2018-06-22 12:15:00', 'Disobedience', 2);
INSERT INTO Showtime1 values('2018-06-22 15:25:00', 'Disobedience', 2);
INSERT INTO Showtime1 values('2018-06-22 18:30:00', 'Disobedience', 2);
INSERT INTO Showtime1 values('2018-06-23 11:50:00', 'Hereditary', 7);
INSERT INTO Showtime1 values('2018-06-22 19:00:00', 'Hereditary', 7);
INSERT INTO Showtime1 values('2018-06-22 22:00:00', 'Hereditary', 7);
INSERT INTO Showtime1 values('2018-06-22 22:15:00', 'I Feel Pretty', 5);
INSERT INTO Showtime1 values('2018-06-23 22:15:00', 'I Feel Pretty', 5);
INSERT INTO Showtime1 values('2018-06-22 13:00:00', 'Book Club', 6);
INSERT INTO Showtime1 values('2018-06-22 18:30:00', 'Book Club', 6);
INSERT INTO Showtime1 values('2018-06-22 16:15:00', 'On Chesil Beach', 5);
INSERT INTO Showtime1 values('2018-06-22 22:30:00', 'On Chesil Beach', 5);
INSERT INTO Showtime1 values('2018-06-23 11:45:00', 'Tag', 8);
INSERT INTO Showtime1 values('2018-06-22 18:30:00', 'Tag', 8);
INSERT INTO Showtime1 values('2018-06-22 21:15:00', 'Tag', 8);

-- INSERT Closed Captioning Information
INSERT INTO Showtime2 values('1', 'Solo: A Stars Wars Story', 1);
INSERT INTO Showtime2 values('0', 'Disobedience', 2);
INSERT INTO Showtime2 values('0', 'Wont you be my neighbor?', 3);
INSERT INTO Showtime2 values('1', 'Avengers: Infinity War', 4);
INSERT INTO Showtime2 values('1', 'On Chesil Beach', 5);
INSERT INTO Showtime2 values('1', 'Book Club', 6);
INSERT INTO Showtime2 values('1', 'Hereditary', 7);
INSERT INTO Showtime2 values('1', 'Tag', 8);
INSERT INTO Showtime2 values('0', 'Deadpool 2', 9);
INSERT INTO Showtime2 values('1', 'Incredibles 2', 10);


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
INSERT INTO Customer VALUES (11,'Kennan Wheeler');
INSERT INTO Customer VALUES (12,'Plato Chavez');
INSERT INTO Customer VALUES (13,'Tyrone Orr');
INSERT INTO Customer VALUES (14,'Amaya Witt');
INSERT INTO Customer VALUES (15,'Zelda Gay');
INSERT INTO Customer VALUES (16,'Maite Moses');
INSERT INTO Customer VALUES (17,'Athena Whitney');
INSERT INTO Customer VALUES (18,'Grant Luna');
INSERT INTO Customer VALUES (19,'Barbara Boyle');
INSERT INTO Customer VALUES (20,'Sawyer Clarke');
INSERT INTO Customer VALUES (21,'Zelenia Dorsey');
INSERT INTO Customer VALUES (22,'Leandra Riddle');
INSERT INTO Customer VALUES (23,'Desirae Blackburn');
INSERT INTO Customer VALUES (24,'Rebekah Velez');
INSERT INTO Customer VALUES (25,'Sebastian Carrillo');
INSERT INTO Customer VALUES (26,'Cooper Murray');
INSERT INTO Customer VALUES (27,'Linda Bullock');
INSERT INTO Customer VALUES (28,'Flavia Frederick');
INSERT INTO Customer VALUES (29,'Hedley Howell');
INSERT INTO Customer VALUES (30,'Mannix Ramos');
INSERT INTO Customer VALUES (31,'Matthew Harrington');
INSERT INTO Customer VALUES (32,'Bree Golden');
INSERT INTO Customer VALUES (33,'Russell Herman');
INSERT INTO Customer VALUES (34,'Shelby Frost');
INSERT INTO Customer VALUES (35,'Mechelle Mueller');
INSERT INTO Customer VALUES (36,'Carissa Valentine');
INSERT INTO Customer VALUES (37,'Xander Knox');
INSERT INTO Customer VALUES (38,'Phillip Lowe');
INSERT INTO Customer VALUES (39,'Chelsea Burgess');
INSERT INTO Customer VALUES (40,'Shad Drake');
INSERT INTO Customer VALUES (41,'TaShya Alford');
INSERT INTO Customer VALUES (42,'Oleg Kent');
INSERT INTO Customer VALUES (43,'Hector Salazar');
INSERT INTO Customer VALUES (44,'Colleen Lara');
INSERT INTO Customer VALUES (45,'Elvis Guerrero');
INSERT INTO Customer VALUES (46,'Barbara Wise');
INSERT INTO Customer VALUES (47,'Wade Vega');
INSERT INTO Customer VALUES (48,'Benedict Lindsey');
INSERT INTO Customer VALUES (49,'Karyn Preston');
INSERT INTO Customer VALUES (50,'Gillian Emerson');
INSERT INTO Customer VALUES (51,'Oscar Joyner');
INSERT INTO Customer VALUES (52,'Xavier Sanders');
INSERT INTO Customer VALUES (53,'Britanney Fowler');
INSERT INTO Customer VALUES (54,'Camden Salazar');
INSERT INTO Customer VALUES (55,'Lance Obrien');
INSERT INTO Customer VALUES (56,'Gage Cox');
INSERT INTO Customer VALUES (57,'Cathleen Byers');
INSERT INTO Customer VALUES (58,'Quentin Kirk');
INSERT INTO Customer VALUES (59,'Samuel Thomas');
INSERT INTO Customer VALUES (60,'Rajah Conner');
INSERT INTO Customer VALUES (61,'Hayfa Rasmussen');
INSERT INTO Customer VALUES (62,'Amir Mckinney');
INSERT INTO Customer VALUES (63,'Damian Phelps');
INSERT INTO Customer VALUES (64,'Whitney Collins');
INSERT INTO Customer VALUES (65,'Dale Wilkins');
INSERT INTO Customer VALUES (66,'Hayfa Mcmillan');
INSERT INTO Customer VALUES (67,'Lavinia Gross');
INSERT INTO Customer VALUES (68,'Herman Fields');
INSERT INTO Customer VALUES (69,'Yasir Bray');
INSERT INTO Customer VALUES (70,'Tamara Pope');
INSERT INTO Customer VALUES (71,'Lance Campos');
INSERT INTO Customer VALUES (72,'Harrison Gomez');
INSERT INTO Customer VALUES (73,'Teegan Marsh');
INSERT INTO Customer VALUES (74,'Thane Pittman');
INSERT INTO Customer VALUES (75,'Lani Guerrero');
INSERT INTO Customer VALUES (76,'Risa Mccray');
INSERT INTO Customer VALUES (77,'Colette Dodson');
INSERT INTO Customer VALUES (78,'Cedric Duran');
INSERT INTO Customer VALUES (79,'Oleg Williamson');
INSERT INTO Customer VALUES (80,'Stacy Jarvis');
INSERT INTO Customer VALUES (81,'Zena Potter');
INSERT INTO Customer VALUES (82,'Rashad Kirkland');
INSERT INTO Customer VALUES (83,'Axel Joseph');
INSERT INTO Customer VALUES (84,'Vernon Valencia');
INSERT INTO Customer VALUES (85,'Burke Roy');
INSERT INTO Customer VALUES (86,'Wing Bowen');
INSERT INTO Customer VALUES (87,'Phillip Clarke');
INSERT INTO Customer VALUES (88,'Mufutau Richardson');
INSERT INTO Customer VALUES (89,'May Preston');
INSERT INTO Customer VALUES (90,'Neve Nguyen');
INSERT INTO Customer VALUES (91,'Cally Carpenter');
INSERT INTO Customer VALUES (92,'Haviva Herrera');
INSERT INTO Customer VALUES (93,'Aphrodite Rocha');
INSERT INTO Customer VALUES (94,'Chaney Albert');
INSERT INTO Customer VALUES (95,'Lillith Melendez');
INSERT INTO Customer VALUES (96,'Cora Hawkins');
INSERT INTO Customer VALUES (97,'Clark Phelps');
INSERT INTO Customer VALUES (98,'Declan Hester');
INSERT INTO Customer VALUES (99,'Driscoll Mcpherson');
INSERT INTO Customer VALUES (100,'Cynthia Benson');
INSERT INTO Customer VALUES (101,'Bruno Diaz');
INSERT INTO Customer VALUES (102,'Teagan Leach');
INSERT INTO Customer VALUES (103,'Micah Finley');
INSERT INTO Customer VALUES (104,'Kelsey Mcleod');
INSERT INTO Customer VALUES (105,'Caleb Rodriquez');
INSERT INTO Customer VALUES (106,'Rinah Herring');
INSERT INTO Customer VALUES (107,'Leroy Berg');
INSERT INTO Customer VALUES (108,'Gavin Prince');
INSERT INTO Customer VALUES (109,'Damon Hutchinson');
INSERT INTO Customer VALUES (110,'Armando Mccarty');
INSERT INTO Customer VALUES (111,'Gail Pollard');
INSERT INTO Customer VALUES (112,'Kendall Gaines');
INSERT INTO Customer VALUES (113,'John Ellis');
INSERT INTO Customer VALUES (114,'Kessie Chambers');
INSERT INTO Customer VALUES (115,'Imani Ratliff');
INSERT INTO Customer VALUES (116,'Cruz Dennis');
INSERT INTO Customer VALUES (117,'Phyllis Young');
INSERT INTO Customer VALUES (118,'Upton Schmidt');
INSERT INTO Customer VALUES (119,'Kiona Harding');
INSERT INTO Customer VALUES (120,'Veda Garner');
INSERT INTO Customer VALUES (121,'Lacy Bailey');
INSERT INTO Customer VALUES (122,'Demetrius Blankenship');
INSERT INTO Customer VALUES (123,'Wayne Greene');
INSERT INTO Customer VALUES (124,'Kasimir Silva');
INSERT INTO Customer VALUES (125,'Ray Patrick');
INSERT INTO Customer VALUES (126,'Eleanor Espinoza');
INSERT INTO Customer VALUES (127,'Prescott Garner');
INSERT INTO Customer VALUES (128,'Brock Whitfield');
INSERT INTO Customer VALUES (129,'Acton Sellers');
INSERT INTO Customer VALUES (130,'Roanna Bailey');
INSERT INTO Customer VALUES (131,'Salvador Gallegos');
INSERT INTO Customer VALUES (132,'Willa Lyons');
INSERT INTO Customer VALUES (133,'April David');
INSERT INTO Customer VALUES (134,'Constance Watkins');
INSERT INTO Customer VALUES (135,'Grant Stewart');
INSERT INTO Customer VALUES (136,'Cyrus Wiggins');
INSERT INTO Customer VALUES (137,'Brock Banks');
INSERT INTO Customer VALUES (138,'Maris Byrd');
INSERT INTO Customer VALUES (139,'Mikayla Rose');
INSERT INTO Customer VALUES (140,'Lane Beck');
INSERT INTO Customer VALUES (141,'Rae Wynn');
INSERT INTO Customer VALUES (142,'Kirsten Abbott');
INSERT INTO Customer VALUES (143,'Andrew Christian');
INSERT INTO Customer VALUES (144,'Daquan Mosley');
INSERT INTO Customer VALUES (145,'Edward Bentley');
INSERT INTO Customer VALUES (146,'Addison Weeks');
INSERT INTO Customer VALUES (147,'Tiger Abbott');
INSERT INTO Customer VALUES (148,'Hayden Hutchinson');
INSERT INTO Customer VALUES (149,'Donovan Knowles');
INSERT INTO Customer VALUES (150,'Gregory Cruz');
INSERT INTO Customer VALUES (151,'Octavia Carter');
INSERT INTO Customer VALUES (152,'Yoshio Simpson');
INSERT INTO Customer VALUES (153,'Keaton Wong');
INSERT INTO Customer VALUES (154,'Madeson Velazquez');
INSERT INTO Customer VALUES (155,'Omar Merritt');
INSERT INTO Customer VALUES (156,'Vernon Lynn');
INSERT INTO Customer VALUES (157,'Margaret Vance');
INSERT INTO Customer VALUES (158,'Bertha Frederick');
INSERT INTO Customer VALUES (159,'Magee Lynch');
INSERT INTO Customer VALUES (160,'Alden House');
INSERT INTO Customer VALUES (161,'Clarke Sears');
INSERT INTO Customer VALUES (162,'Driscoll Marshall');
INSERT INTO Customer VALUES (163,'Graham Morrison');
INSERT INTO Customer VALUES (164,'Kathleen Kent');
INSERT INTO Customer VALUES (165,'Eleanor Donovan');
INSERT INTO Customer VALUES (166,'Joel Spence');
INSERT INTO Customer VALUES (167,'September Armstrong');
INSERT INTO Customer VALUES (168,'Hermione Gay');
INSERT INTO Customer VALUES (169,'Ginger Romero');
INSERT INTO Customer VALUES (170,'Marsden Melton');
INSERT INTO Customer VALUES (171,'Gannon Dodson');
INSERT INTO Customer VALUES (172,'Uriah Daugherty');
INSERT INTO Customer VALUES (173,'Jonas Fisher');
INSERT INTO Customer VALUES (174,'Farrah Gamble');
INSERT INTO Customer VALUES (175,'Cherokee Gay');
INSERT INTO Customer VALUES (176,'Nelle Roberts');
INSERT INTO Customer VALUES (177,'Buffy Huffman');
INSERT INTO Customer VALUES (178,'Colette Vaughan');
INSERT INTO Customer VALUES (179,'Selma Burgess');
INSERT INTO Customer VALUES (180,'Uriel Aguilar');
INSERT INTO Customer VALUES (181,'Myra Graves');
INSERT INTO Customer VALUES (182,'Donna Newman');
INSERT INTO Customer VALUES (183,'Winifred Kinney');
INSERT INTO Customer VALUES (184,'Eaton Goff');
INSERT INTO Customer VALUES (185,'Gail Greene');
INSERT INTO Customer VALUES (186,'Avram Sutton');
INSERT INTO Customer VALUES (187,'Leo Higgins');
INSERT INTO Customer VALUES (188,'Kerry Dickson');
INSERT INTO Customer VALUES (189,'Michael Chambers');
INSERT INTO Customer VALUES (190,'Quemby Marshall');
INSERT INTO Customer VALUES (191,'Imogene Livingston');
INSERT INTO Customer VALUES (192,'Mollie Langley');
INSERT INTO Customer VALUES (193,'Chaney Lloyd');
INSERT INTO Customer VALUES (194,'Ria Wood');
INSERT INTO Customer VALUES (195,'Hayes Craig');
INSERT INTO Customer VALUES (196,'Lysandra Lang');
INSERT INTO Customer VALUES (197,'Kane Elliott');
INSERT INTO Customer VALUES (198,'Chloe Howard');
INSERT INTO Customer VALUES (199,'Alan Sweet');
INSERT INTO Customer VALUES (200,'Hammett Kirkland');
INSERT INTO Customer VALUES (201,'Barry Rivas');
INSERT INTO Customer VALUES (202,'Kylie Holmes');
INSERT INTO Customer VALUES (203,'Tanek Cooley');
INSERT INTO Customer VALUES (204,'Kalia Small');
INSERT INTO Customer VALUES (205,'Madaline Jones');
INSERT INTO Customer VALUES (206,'Chaim Palmer');
INSERT INTO Customer VALUES (207,'Shana Page');
INSERT INTO Customer VALUES (208,'Blaine Hopper');
INSERT INTO Customer VALUES (209,'Ann Bender');
INSERT INTO Customer VALUES (210,'Jarrod Sampson');
INSERT INTO Customer VALUES (211,'Stacey Burch');
INSERT INTO Customer VALUES (212,'Aphrodite Kinney');
INSERT INTO Customer VALUES (213,'Ezra Sharp');
INSERT INTO Customer VALUES (214,'Oprah Craig');
INSERT INTO Customer VALUES (215,'Quentin Stuart');
INSERT INTO Customer VALUES (216,'Otto Guthrie');
INSERT INTO Customer VALUES (217,'Ahmed Mendez');
INSERT INTO Customer VALUES (218,'Derek Bauer');
INSERT INTO Customer VALUES (219,'Camilla Coleman');
INSERT INTO Customer VALUES (220,'Francis Valencia');
INSERT INTO Customer VALUES (221,'Lynn Miles');
INSERT INTO Customer VALUES (222,'Kathleen Holmes');
INSERT INTO Customer VALUES (223,'Mark Fowler');
INSERT INTO Customer VALUES (224,'Geoffrey Diaz');
INSERT INTO Customer VALUES (225,'Maggy Austin');
INSERT INTO Customer VALUES (226,'Germaine Ortiz');
INSERT INTO Customer VALUES (227,'Henry Dunn');
INSERT INTO Customer VALUES (228,'Quincy Strong');
INSERT INTO Customer VALUES (229,'Keiko Avery');
INSERT INTO Customer VALUES (230,'Edward Mills');
INSERT INTO Customer VALUES (231,'Henry Frost');
INSERT INTO Customer VALUES (232,'Roanna Barry');
INSERT INTO Customer VALUES (233,'Octavius Underwood');
INSERT INTO Customer VALUES (234,'Thane Moon');
INSERT INTO Customer VALUES (235,'Matthew Cooper');
INSERT INTO Customer VALUES (236,'Baxter Potts');
INSERT INTO Customer VALUES (237,'Eric Ochoa');
INSERT INTO Customer VALUES (238,'Logan Cherry');
INSERT INTO Customer VALUES (239,'Quemby Dalton');
INSERT INTO Customer VALUES (240,'Kaye Dalton');
INSERT INTO Customer VALUES (241,'Quynn Thornton');
INSERT INTO Customer VALUES (242,'Delilah Gaines');
INSERT INTO Customer VALUES (243,'Christopher Conway');
INSERT INTO Customer VALUES (244,'Nero Blake');
INSERT INTO Customer VALUES (245,'Noah Russell');
INSERT INTO Customer VALUES (246,'Felix Sutton');
INSERT INTO Customer VALUES (247,'Nola Baird');
INSERT INTO Customer VALUES (248,'Burton Butler');
INSERT INTO Customer VALUES (249,'Ocean Christian');
INSERT INTO Customer VALUES (250,'Cally Stewart');

-- INSERT Loyalty_member data
INSERT INTO Loyalty_member VALUES (1,2718);
INSERT INTO Loyalty_member VALUES (3,8275);
INSERT INTO Loyalty_member VALUES (5,6082);
INSERT INTO Loyalty_member VALUES (7,1037);
INSERT INTO Loyalty_member VALUES (9,7692);
INSERT INTO Loyalty_member VALUES (11,4855);
INSERT INTO Loyalty_member VALUES (13,7678);
INSERT INTO Loyalty_member VALUES (15,2290);
INSERT INTO Loyalty_member VALUES (17,5133);
INSERT INTO Loyalty_member VALUES (19,9991);
INSERT INTO Loyalty_member VALUES (21,2976);
INSERT INTO Loyalty_member VALUES (23,5749);
INSERT INTO Loyalty_member VALUES (25,4010);
INSERT INTO Loyalty_member VALUES (27,1102);
INSERT INTO Loyalty_member VALUES (29,3617);
INSERT INTO Loyalty_member VALUES (31,9439);
INSERT INTO Loyalty_member VALUES (33,3317);
INSERT INTO Loyalty_member VALUES (35,4455);
INSERT INTO Loyalty_member VALUES (37,8828);
INSERT INTO Loyalty_member VALUES (39,8863);
INSERT INTO Loyalty_member VALUES (41,6832);
INSERT INTO Loyalty_member VALUES (43,6059);
INSERT INTO Loyalty_member VALUES (45,6246);
INSERT INTO Loyalty_member VALUES (47,2340);
INSERT INTO Loyalty_member VALUES (49,9854);
INSERT INTO Loyalty_member VALUES (51,8626);
INSERT INTO Loyalty_member VALUES (53,3349);
INSERT INTO Loyalty_member VALUES (55,5907);
INSERT INTO Loyalty_member VALUES (57,5809);
INSERT INTO Loyalty_member VALUES (59,5272);
INSERT INTO Loyalty_member VALUES (61,9046);
INSERT INTO Loyalty_member VALUES (63,9209);
INSERT INTO Loyalty_member VALUES (65,3111);
INSERT INTO Loyalty_member VALUES (67,5318);
INSERT INTO Loyalty_member VALUES (69,731);
INSERT INTO Loyalty_member VALUES (71,3155);
INSERT INTO Loyalty_member VALUES (73,2097);
INSERT INTO Loyalty_member VALUES (75,1998);
INSERT INTO Loyalty_member VALUES (77,689);
INSERT INTO Loyalty_member VALUES (79,5435);
INSERT INTO Loyalty_member VALUES (81,6782);
INSERT INTO Loyalty_member VALUES (83,4210);
INSERT INTO Loyalty_member VALUES (85,1504);
INSERT INTO Loyalty_member VALUES (87,8824);
INSERT INTO Loyalty_member VALUES (89,1628);
INSERT INTO Loyalty_member VALUES (91,1498);
INSERT INTO Loyalty_member VALUES (93,1284);
INSERT INTO Loyalty_member VALUES (95,3699);
INSERT INTO Loyalty_member VALUES (97,7659);
INSERT INTO Loyalty_member VALUES (99,1090);
INSERT INTO Loyalty_member VALUES (101,8974);
INSERT INTO Loyalty_member VALUES (103,4827);
INSERT INTO Loyalty_member VALUES (105,4534);
INSERT INTO Loyalty_member VALUES (107,7388);
INSERT INTO Loyalty_member VALUES (109,4214);
INSERT INTO Loyalty_member VALUES (111,2663);
INSERT INTO Loyalty_member VALUES (113,9800);
INSERT INTO Loyalty_member VALUES (115,4791);
INSERT INTO Loyalty_member VALUES (117,4663);
INSERT INTO Loyalty_member VALUES (119,3132);
INSERT INTO Loyalty_member VALUES (121,9038);
INSERT INTO Loyalty_member VALUES (123,4828);
INSERT INTO Loyalty_member VALUES (125,9106);
INSERT INTO Loyalty_member VALUES (127,5210);
INSERT INTO Loyalty_member VALUES (129,3500);
INSERT INTO Loyalty_member VALUES (131,2390);
INSERT INTO Loyalty_member VALUES (133,4547);
INSERT INTO Loyalty_member VALUES (135,2285);
INSERT INTO Loyalty_member VALUES (137,3862);
INSERT INTO Loyalty_member VALUES (139,1138);
INSERT INTO Loyalty_member VALUES (141,5182);
INSERT INTO Loyalty_member VALUES (143,1432);
INSERT INTO Loyalty_member VALUES (145,9823);
INSERT INTO Loyalty_member VALUES (147,6683);
INSERT INTO Loyalty_member VALUES (149,3962);
INSERT INTO Loyalty_member VALUES (151,3754);
INSERT INTO Loyalty_member VALUES (153,6460);
INSERT INTO Loyalty_member VALUES (155,3339);
INSERT INTO Loyalty_member VALUES (157,8787);
INSERT INTO Loyalty_member VALUES (159,2083);
INSERT INTO Loyalty_member VALUES (161,7175);
INSERT INTO Loyalty_member VALUES (163,3742);
INSERT INTO Loyalty_member VALUES (165,7409);
INSERT INTO Loyalty_member VALUES (167,6405);
INSERT INTO Loyalty_member VALUES (169,8897);
INSERT INTO Loyalty_member VALUES (171,786);
INSERT INTO Loyalty_member VALUES (173,2143);
INSERT INTO Loyalty_member VALUES (175,9257);
INSERT INTO Loyalty_member VALUES (177,9981);
INSERT INTO Loyalty_member VALUES (179,2122);
INSERT INTO Loyalty_member VALUES (181,867);
INSERT INTO Loyalty_member VALUES (183,5827);
INSERT INTO Loyalty_member VALUES (185,6612);
INSERT INTO Loyalty_member VALUES (187,7854);
INSERT INTO Loyalty_member VALUES (189,928);
INSERT INTO Loyalty_member VALUES (191,6890);
INSERT INTO Loyalty_member VALUES (193,2820);
INSERT INTO Loyalty_member VALUES (195,7015);
INSERT INTO Loyalty_member VALUES (197,9323);
INSERT INTO Loyalty_member VALUES (199,6415);

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
insert into Employee values (11, 'Cara Benedettini', 815067238, '6044315162');
insert into Employee values (12, 'Archibald Emberton', 818980252, '6045711238');
insert into Employee values (13, 'Joya Roscow', 275439739, '6048479497');
insert into Employee values (14, 'Jocelin Beames', 595329805, '6049837888');
insert into Employee values (15, 'Manny Rivalant', 881927717, '6049745936');
insert into Employee values (16, 'Bentley Crichton', 612240602, '6046046329');
insert into Employee values (17, 'Lew Shepcutt', 728757827, '6040174315');
insert into Employee values (18, 'Karlyn Eagle', 445482462, '6046885452');
insert into Employee values (19, 'Cirilo Halahan', 340680944, '6041588453');
insert into Employee values (20, 'Bertram Skepper', 083770740, '6048711819');
insert into Employee values (21, 'Lothario Bellwood', 332206076, '6042939614');
insert into Employee values (22, 'Margie Gorrissen', 336374311, '6042881066');
insert into Employee values (23, 'Audre Vannoort', 927572212, '6049519848');
insert into Employee values (24, 'Tudor Jurn', 082880299, '6041553534');
insert into Employee values (25, 'Meade Rushsorth', 152242064, '6046359868');
insert into Employee values (26, 'Gaylor Culley', 185309567, '6041968792');
insert into Employee values (27, 'Leonard Daverin', 347380899, '6043193707');
insert into Employee values (28, 'Raychel Hoggan', 534855631, '6041889809');
insert into Employee values (29, 'Carney Brownlee', 764903735, '6049128306');
insert into Employee values (30, 'Fidelio Livezey', 684062539, '6041788820');
insert into Employee values (31, 'Franny McCarver', 800988420, '6040849269');
insert into Employee values (32, 'Caralie Kibard', 330304477, '6046264883');
insert into Employee values (33, 'Oralla Boome', 642150898, '6040270462');
insert into Employee values (34, 'Fey Light', 881951340, '6043198058');
insert into Employee values (35, 'Armando Rotherham', 321094648, '6040311780');
insert into Employee values (36, 'Antonia Peschke', 455984698, '6041414828');
insert into Employee values (37, 'Romeo Bickerstaffe', 776250179, '6047260384');
insert into Employee values (38, 'Anna-diane Hollows', 188458605, '6044421055');
insert into Employee values (39, 'Noell Thurber', 374944867, '6042999561');
insert into Employee values (40, 'Vaughn Giannoni', 896042648, '6041696811');
insert into Employee values (41, 'Mikkel Stanyer', 484194731, '6047723277');
insert into Employee values (42, 'Thorsten Tomson', 004791261, '6046889280');
insert into Employee values (43, 'Kimmi Kinnier', 352628534, '6047632068');
insert into Employee values (44, 'Merrielle Dupree', 670960678, '6040706839');
insert into Employee values (45, 'Barty Seyler', 268132488, '6048458214');
insert into Employee values (46, 'Pierette Gatus', 379957718, '6049494192');
insert into Employee values (47, 'Cecily Walkington',056824819, '6041042344');
insert into Employee values (48, 'Emory Murt', 162151111, '6046534175');
insert into Employee values (49, 'Sharron Bartalucci', 052567912, '6040995623');
insert into Employee values (50, 'Corrina McEachern', 171033603, '6042826650');

-- INSERT Manager Data
insert into Manager (eID) values (24);
insert into Manager (eID) values (7);
insert into Manager (eID) values (50);
insert into Manager (eID) values (49);
insert into Manager (eID) values (20);
insert into Manager (eID) values (22);
insert into Manager (eID) values (21);
insert into Manager (eID) values (12);

-- INSERT Booking Data

INSERT INTO Booking VALUES ('45685176799','Credit','5531584671644409',32,59);
INSERT INTO Booking VALUES ('86744569999','Credit','5232898086761711',28,29);
INSERT INTO Booking VALUES ('78623470399','Credit','5240507079195551',15,88);
INSERT INTO Booking VALUES ('10975926999','Redeem',NULL,5,7);
INSERT INTO Booking VALUES ('97381224999','Credit','5541002112513032',14,221);
INSERT INTO Booking VALUES ('25304596099','Credit','5529371810918587',1,133);
INSERT INTO Booking VALUES ('68323328499','Credit','5255662466721519',25,164);
INSERT INTO Booking VALUES ('25617400799','Credit','5443894359924818',26,174);
INSERT INTO Booking VALUES ('92942241199','Credit','5362084571088553',8,214);
INSERT INTO Booking VALUES ('74448910299','Credit','5466065393712699',15,139);
INSERT INTO Booking VALUES ('13451810899','Credit','5272745282145259',34,128);
INSERT INTO Booking VALUES ('10896758299','Credit','5191794445233592',18,98);
INSERT INTO Booking VALUES ('23372498999','Credit','5236083024557879',13,66);
INSERT INTO Booking VALUES ('59356227799','Credit','5422900632135026',11,182);
INSERT INTO Booking VALUES ('23403642099','Credit','5423672085984486',15,19);
INSERT INTO Booking VALUES ('09897690799','Credit','5575311016617818',26,231);
INSERT INTO Booking VALUES ('05271653799','Credit','5133307454818228',6,110);
INSERT INTO Booking VALUES ('81744444499','Credit','5328369488263750',5,84);
INSERT INTO Booking VALUES ('03705767999','Credit','5560284199205270',3,176);
INSERT INTO Booking VALUES ('82714400799','Credit','5266162348178597',5,69);
INSERT INTO Booking VALUES ('37689653199','Credit','5404151956563631',27,187);
INSERT INTO Booking VALUES ('38592831799','Credit','5106263607293434',29,148);
INSERT INTO Booking VALUES ('36884455699','Credit','5355902059446317',32,75);
INSERT INTO Booking VALUES ('35764394799','Credit','5493245213945300',33,195);
INSERT INTO Booking VALUES ('53289465699','Credit','5560593677573078',21,111);
INSERT INTO Booking VALUES ('48498601599','Debit','5261819646782108',27,171);
INSERT INTO Booking VALUES ('69586112699','Debit','5226591020519299',6,44);
INSERT INTO Booking VALUES ('20653249899','Debit','5378729054753881',6,74);
INSERT INTO Booking VALUES ('96844892899','Debit','5580944519176115',4,82);
INSERT INTO Booking VALUES ('84319962999','Debit','5103434329772137',22,189);
INSERT INTO Booking VALUES ('09719583599','Debit','5118257951264252',14,14);
INSERT INTO Booking VALUES ('01658294399','Debit','5186841297079388',16,101);
INSERT INTO Booking VALUES ('73850564199','Debit','5264980015496436',29,17);
INSERT INTO Booking VALUES ('00514570399','Debit','5204625523808423',29,164);
INSERT INTO Booking VALUES ('98324769999','Debit','5227078993856477',28,227);
INSERT INTO Booking VALUES ('08353387299','Debit','5219751196738136',21,228);
INSERT INTO Booking VALUES ('97266286899','Debit','5216609670738803',30,169);
INSERT INTO Booking VALUES ('00372188199','Debit','5477217448107915',2,125);
INSERT INTO Booking VALUES ('87337749299','Debit','5440520863341727',11,99);
INSERT INTO Booking VALUES ('25784932499','Debit','5174908872765386',33,143);
INSERT INTO Booking VALUES ('07519075199','Debit','5142314900073308',28,217);
INSERT INTO Booking VALUES ('61254062499','Debit','5331072202243984',23,7);
INSERT INTO Booking VALUES ('39729133499','Debit','5366107945306678',18,103);
INSERT INTO Booking VALUES ('61994682599','Debit','5566048121203305',8,214);
INSERT INTO Booking VALUES ('70312452199','Debit','5112048550591401',32,238);
INSERT INTO Booking VALUES ('49503615599','Debit','5171990030384873',9,211);
INSERT INTO Booking VALUES ('76681917699','Debit','5413629739643597',29,68);
INSERT INTO Booking VALUES ('30806108099','Debit','5511752817617142',9,138);
INSERT INTO Booking VALUES ('66442004299','Debit','5145673643931685',25,75);
INSERT INTO Booking VALUES ('03832281399','Debit','5470170352835509',29,32);
INSERT INTO Booking VALUES ('05504410899','Cash',NULL,31,218);
INSERT INTO Booking VALUES ('98652593599','Cash',NULL,29,54);
INSERT INTO Booking VALUES ('34790296299','Cash',NULL,21,169);
INSERT INTO Booking VALUES ('71435298599','Cash',NULL,15,75);
INSERT INTO Booking VALUES ('92517384999','Cash',NULL,22,31);
INSERT INTO Booking VALUES ('96936178699','Cash',NULL,15,19);
INSERT INTO Booking VALUES ('33477163399','Cash',NULL,30,60);
INSERT INTO Booking VALUES ('22738421599','Cash',NULL,24,24);
INSERT INTO Booking VALUES ('83551840499','Cash',NULL,39,124);
INSERT INTO Booking VALUES ('67726749299','Cash',NULL,12,211);
INSERT INTO Booking VALUES ('36451717699','Cash',NULL,25,188);
INSERT INTO Booking VALUES ('28519354399','Cash',NULL,12,61);
INSERT INTO Booking VALUES ('32409321399','Cash',NULL,12,60);
INSERT INTO Booking VALUES ('12883987099','Cash',NULL,40,198);
INSERT INTO Booking VALUES ('65609349999','Cash',NULL,30,42);
INSERT INTO Booking VALUES ('04859922899','Cash',NULL,30,2);
INSERT INTO Booking VALUES ('46250937299','Cash',NULL,2,140);
INSERT INTO Booking VALUES ('83608509099','Cash',NULL,6,83);
INSERT INTO Booking VALUES ('10608811499','Cash',NULL,39,52);
INSERT INTO Booking VALUES ('84753130699','Cash',NULL,39,60);
INSERT INTO Booking VALUES ('01444751299','Cash',NULL,5,80);
INSERT INTO Booking VALUES ('55805740399','Cash',NULL,24,159);
INSERT INTO Booking VALUES ('15990075899','Cash',NULL,13,71);
INSERT INTO Booking VALUES ('82998295199','Cash',NULL,31,192);
INSERT INTO Booking VALUES ('10363122199','Redeem', NULL,39,249);

-- INSERT Ticket Data
INSERT INTO Ticket VALUES ('45685176799',1, 'Incredibles 2','2018-06-22 18:30:00', 13.25, 10);
INSERT INTO Ticket VALUES ('86744569999',2, 'Incredibles 2','2018-06-22 18:30:00',13.25, 10);
INSERT INTO Ticket VALUES ('78623470399',3, 'Incredibles 2','2018-06-22 18:30:00',13.25, 10);
INSERT INTO Ticket VALUES ('10975926999',4,'Incredibles 2','2018-06-22 18:30:00',13.25, 10);
INSERT INTO Ticket VALUES ('97381224999',5,'Incredibles 2','2018-06-22 18:30:00',13.25, 10);
INSERT INTO Ticket VALUES ('25304596099',6,'Incredibles 2','2018-06-22 18:30:00',13.25, 10);
INSERT INTO Ticket VALUES ('68323328499',7,'Incredibles 2','2018-06-22 18:30:00',13.25, 10);
INSERT INTO Ticket VALUES ('25617400799',8,'Incredibles 2','2018-06-22 18:30:00',13.25, 10);
INSERT INTO Ticket VALUES ('92942241199',9,'Incredibles 2','2018-06-22 18:30:00',13.25, 10);
INSERT INTO Ticket VALUES ('74448910299',10,'Incredibles 2','2018-06-22 18:30:00',13.25, 10);
INSERT INTO Ticket VALUES ('13451810899',11,'Incredibles 2','2018-06-22 18:30:00',13.25, 10);
INSERT INTO Ticket VALUES ('10896758299',12,'Incredibles 2','2018-06-22 18:30:00',13.25, 10);
INSERT INTO Ticket VALUES ('23372498999',13,'Incredibles 2','2018-06-22 18:30:00',13.25, 10);
INSERT INTO Ticket VALUES ('59356227799',14,'Incredibles 2','2018-06-22 18:30:00',13.25, 10);
INSERT INTO Ticket VALUES ('23403642099',15,'Incredibles 2','2018-06-22 18:30:00',13.25, 10);
INSERT INTO Ticket VALUES ('09897690799',16,'Incredibles 2','2018-06-22 18:30:00',13.25, 10);
INSERT INTO Ticket VALUES ('05271653799',17,'Incredibles 2','2018-06-22 18:30:00',13.25, 10);
INSERT INTO Ticket VALUES ('81744444499',18,'Incredibles 2','2018-06-22 18:30:00',13.25, 10);
INSERT INTO Ticket VALUES ('03705767999',19,'Incredibles 2','2018-06-22 18:30:00',13.25, 10);
INSERT INTO Ticket VALUES ('82714400799',20,'Incredibles 2','2018-06-22 18:30:00',13.25, 10);
INSERT INTO Ticket VALUES ('37689653199',21,'Incredibles 2','2018-06-22 18:30:00',13.25, 10);
INSERT INTO Ticket VALUES ('38592831799',22,'Incredibles 2','2018-06-22 18:30:00',13.25, 10);
INSERT INTO Ticket VALUES ('36884455699',23,'Incredibles 2','2018-06-22 18:30:00',13.25, 10);
INSERT INTO Ticket VALUES ('35764394799',24,'Incredibles 2','2018-06-22 18:30:00',13.25, 10);
INSERT INTO Ticket VALUES ('53289465699',25,'Deadpool 2','2018-06-22 19:30:00',13.25 , 9);
INSERT INTO Ticket VALUES ('48498601599',26,'Deadpool 2','2018-06-22 19:30:00',13.25 , 9);
INSERT INTO Ticket VALUES ('69586112699',27,'Deadpool 2','2018-06-22 19:30:00',13.25 , 9);
INSERT INTO Ticket VALUES ('20653249899',28,'Deadpool 2','2018-06-22 19:30:00',13.25 , 9);
INSERT INTO Ticket VALUES ('96844892899',29,'Deadpool 2','2018-06-22 19:30:00',13.25 , 9);
INSERT INTO Ticket VALUES ('84319962999',30,'Deadpool 2','2018-06-22 19:30:00',13.25 , 9);
INSERT INTO Ticket VALUES ('09719583599',31,'Deadpool 2','2018-06-22 19:30:00',13.25 , 9);
INSERT INTO Ticket VALUES ('01658294399',32,'Deadpool 2','2018-06-22 19:30:00',13.25 , 9);
INSERT INTO Ticket VALUES ('73850564199',33,'Deadpool 2','2018-06-22 19:30:00',13.25 , 9);
INSERT INTO Ticket VALUES ('00514570399',34,'Deadpool 2','2018-06-22 19:30:00',13.25 , 9);
INSERT INTO Ticket VALUES ('98324769999',35,'Deadpool 2','2018-06-22 19:30:00',13.25 , 9);
INSERT INTO Ticket VALUES ('08353387299',36,'Hereditary','2018-06-22 22:00:00', 13.25, 7);
INSERT INTO Ticket VALUES ('97266286899',37,'Hereditary','2018-06-22 22:00:00', 13.25, 7);
INSERT INTO Ticket VALUES ('00372188199',38,'Hereditary','2018-06-22 22:00:00', 13.25, 7);
INSERT INTO Ticket VALUES ('87337749299',39,'Hereditary','2018-06-22 22:00:00', 13.25, 7);
INSERT INTO Ticket VALUES ('25784932499',40,'Hereditary','2018-06-22 22:00:00', 13.25, 7);
INSERT INTO Ticket VALUES ('07519075199',41,'Hereditary','2018-06-22 22:00:00', 13.25, 7);
INSERT INTO Ticket VALUES ('61254062499',42,'Hereditary','2018-06-22 22:00:00', 13.25, 7);
INSERT INTO Ticket VALUES ('39729133499',43,'Hereditary','2018-06-22 22:00:00', 13.25, 7);
INSERT INTO Ticket VALUES ('61994682599',44,'Hereditary','2018-06-22 22:00:00', 13.25, 7);
INSERT INTO Ticket VALUES ('70312452199',45,'Hereditary','2018-06-22 22:00:00', 13.25, 7);
INSERT INTO Ticket VALUES ('49503615599',46,'Hereditary','2018-06-22 22:00:00', 13.25, 7);
INSERT INTO Ticket VALUES ('76681917699',47,'Hereditary','2018-06-22 22:00:00', 13.25, 7);
INSERT INTO Ticket VALUES ('30806108099',48,'Hereditary','2018-06-22 22:00:00', 13.25, 7);
INSERT INTO Ticket VALUES ('66442004299',49,'Hereditary','2018-06-22 22:00:00', 13.25, 7);
INSERT INTO Ticket VALUES ('03832281399',50,'Hereditary','2018-06-22 22:00:00', 13.25, 7);
INSERT INTO Ticket VALUES ('05504410899',51,'Hereditary','2018-06-22 22:00:00', 13.25, 7);
INSERT INTO Ticket VALUES ('98652593599',52,'Hereditary','2018-06-22 22:00:00', 13.25, 7);
INSERT INTO Ticket VALUES ('34790296299',53,'Hereditary','2018-06-22 22:00:00', 13.25, 7);
INSERT INTO Ticket VALUES ('71435298599',54,'Hereditary','2018-06-22 22:00:00', 13.25, 7);
INSERT INTO Ticket VALUES ('92517384999',55,'Solo: A Stars Wars Story','2018-06-22 12:15:00', 13.25 , 1);
INSERT INTO Ticket VALUES ('96936178699',56,'Solo: A Stars Wars Story','2018-06-22 12:15:00', 13.25 , 1);
INSERT INTO Ticket VALUES ('33477163399',57,'Solo: A Stars Wars Story','2018-06-22 12:15:00', 13.25 , 1);
INSERT INTO Ticket VALUES ('22738421599',58,'Solo: A Stars Wars Story','2018-06-22 12:15:00', 13.25 , 1);
INSERT INTO Ticket VALUES ('83551840499',59,'Solo: A Stars Wars Story','2018-06-22 12:15:00', 13.25 , 1);
INSERT INTO Ticket VALUES ('67726749299',60,'Solo: A Stars Wars Story','2018-06-22 12:15:00', 13.25 , 1);
INSERT INTO Ticket VALUES ('36451717699',61,'Solo: A Stars Wars Story','2018-06-22 12:15:00', 13.25 , 1);
INSERT INTO Ticket VALUES ('28519354399',62,'Solo: A Stars Wars Story','2018-06-22 12:15:00', 13.25 , 1);
INSERT INTO Ticket VALUES ('32409321399',63,'Solo: A Stars Wars Story','2018-06-22 12:15:00', 13.25 , 1);
INSERT INTO Ticket VALUES ('12883987099',64,'Avengers: Infinity War','2018-06-22 18:30:00', 16.25, 4);
INSERT INTO Ticket VALUES ('65609349999',65,'Avengers: Infinity War','2018-06-22 18:30:00', 16.25, 4);
INSERT INTO Ticket VALUES ('04859922899',66,'Avengers: Infinity War','2018-06-22 18:30:00', 16.25, 4);
INSERT INTO Ticket VALUES ('46250937299',67,'Avengers: Infinity War','2018-06-22 18:30:00', 16.25, 4);
INSERT INTO Ticket VALUES ('83608509099',68,'Avengers: Infinity War','2018-06-22 18:30:00', 16.25, 4);
INSERT INTO Ticket VALUES ('10608811499',69,'Avengers: Infinity War','2018-06-22 18:30:00', 16.25, 4);
INSERT INTO Ticket VALUES ('84753130699',70,'Avengers: Infinity War','2018-06-22 18:30:00', 16.25, 4);
INSERT INTO Ticket VALUES ('01444751299',71,'Avengers: Infinity War','2018-06-22 18:30:00', 16.25, 4);
INSERT INTO Ticket VALUES ('55805740399',72,'Avengers: Infinity War','2018-06-22 18:30:00', 16.25, 4);
INSERT INTO Ticket VALUES ('15990075899',73,'Avengers: Infinity War','2018-06-22 18:30:00', 16.25, 4);
INSERT INTO Ticket VALUES ('82998295199',74,'Incredibles 2','2018-06-22 18:30:00', 9.99, 10);
INSERT INTO Ticket VALUES ('10363122199',75,'Incredibles 2','2018-06-22 18:30:00', 9.99, 10);
INSERT INTO Ticket VALUES ('55805740399',76,'Incredibles 2','2018-06-22 18:30:00', 9.99, 10);
INSERT INTO Ticket VALUES ('15990075899',77,'Incredibles 2','2018-06-22 18:30:00', 9.99, 10);
INSERT INTO Ticket VALUES ('82998295199',78,'Incredibles 2','2018-06-22 18:30:00', 9.99, 10);
INSERT INTO Ticket VALUES ('10363122199',79,'Incredibles 2','2018-06-22 18:30:00', 9.99, 10);
INSERT INTO Ticket VALUES ('55805740399',80,'Incredibles 2','2018-06-22 18:30:00', 9.99, 10);
INSERT INTO Ticket VALUES ('15990075899',81,'Incredibles 2','2018-06-22 18:30:00', 9.99, 10);
INSERT INTO Ticket VALUES ('82998295199',82,'Incredibles 2','2018-06-22 18:30:00', 9.99, 10);
INSERT INTO Ticket VALUES ('10363122199',83,'Incredibles 2','2018-06-22 18:30:00', 9.99, 10);
INSERT INTO Ticket VALUES ('04859922899',84,'Incredibles 2','2018-06-22 18:30:00', 9.99, 10);
INSERT INTO Ticket VALUES ('04859922899',85,'Solo: A Stars Wars Story','2018-06-22 12:15:00', 9.99, 1);
INSERT INTO Ticket VALUES ('04859922899',86,'Disobedience','2018-06-22 12:15:00', 9.99, 2);
INSERT INTO Ticket VALUES ('04859922899',87,'Hereditary','2018-06-22 19:00:00', 9.99, 7);
INSERT INTO Ticket VALUES ('04859922899',88,'Deadpool 2','2018-06-22 16:15:00', 9.99, 9);
INSERT INTO Ticket VALUES ('04859922899',89,'Avengers: Infinity War','2018-06-22 12:15:00', 9.99, 4);
INSERT INTO Ticket VALUES ('04859922899',90,'Wont you be my neighbor?','2018-06-23 15:00:00', 9.99, 3);
INSERT INTO Ticket VALUES ('04859922899',91,'I Feel Pretty','2018-06-22 22:15:00', 9.99, 5);
INSERT INTO Ticket VALUES ('04859922899',92,'Book Club','2018-06-22 13:00:00', 9.99, 6);
INSERT INTO Ticket VALUES ('04859922899',93,'On Chesil Beach','2018-06-22 16:15:00', 9.99, 5);
INSERT INTO Ticket VALUES ('04859922899',94,'Tag','2018-06-22 21:15:00', 9.99, 8);
INSERT INTO Ticket VALUES ('86744569999',95,'Incredibles 2','2018-06-22 18:30:00', 9.99, 10);
INSERT INTO Ticket VALUES ('86744569999',96,'Solo: A Stars Wars Story','2018-06-22 12:15:00', 9.99, 1);
INSERT INTO Ticket VALUES ('86744569999',97,'Disobedience','2018-06-22 12:15:00', 9.99, 2);
INSERT INTO Ticket VALUES ('86744569999',98,'Hereditary','2018-06-22 19:00:00', 9.99, 7);
INSERT INTO Ticket VALUES ('86744569999',99,'Deadpool 2','2018-06-22 16:15:00', 9.99, 9);
INSERT INTO Ticket VALUES ('86744569999',100,'Avengers: Infinity War','2018-06-22 12:15:00', 9.99, 4);
INSERT INTO Ticket VALUES ('86744569999',101,'Wont you be my neighbor?','2018-06-23 15:00:00', 9.99, 3);
INSERT INTO Ticket VALUES ('86744569999',102,'I Feel Pretty','2018-06-22 22:15:00', 9.99, 5);
INSERT INTO Ticket VALUES ('86744569999',103,'Book Club','2018-06-22 13:00:00', 9.99, 6);
INSERT INTO Ticket VALUES ('86744569999',104,'On Chesil Beach','2018-06-22 16:15:00', 9.99, 5);
INSERT INTO Ticket VALUES ('86744569999',105,'Tag','2018-06-22 21:15:00', 9.99, 8);


commit work;