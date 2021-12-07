\c postgres;
CREATE TABLE userss (
    id serial,
    name varchar(100),
    surname varchar(100),
    username varchar(40) unique not null,
    password varchar(250),
    salt varchar(10),
    primary key (id, username)
);

CREATE TABLE Hotel (
  hotel_id integer NOT NULL,
  hotel_name varchar(100),
  hotel_address varchar(200),
  PRIMARY KEY (hotel_id),
  UNIQUE (hotel_name)
);

CREATE DOMAIN RMTYPE varchar(10)
CONSTRAINT TYPEROOM
CHECK (VALUE IN 
('single', 'double'));

CREATE DOMAIN YESNO varchar(10)
CONSTRAINT BOOLCLEAN
CHECK (VALUE IN 
( 'yes', 'no'));

CREATE DOMAIN DAYZ char(3)
CONSTRAINT DAYSOFWEEK
CHECK (VALUE IN 
( 'MON', 'TUE', 'WED', 'THU', 'FRI', 'SAT', 'SUN'));


CREATE TABLE HotelChain (
  hotel_id integer NOT NULL,
  hotel_name varchar(100),
  primary key (hotel_id),
  foreign key (hotel_id) references Hotel ON UPDATE CASCADE ON DELETE CASCADE,
  foreign key (hotel_name) references Hotel (hotel_name) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE HotelPhoneNumber (
  hotel_id integer NOT NULL,
  h_phone_number varchar(20) NOT NULL,
  primary key (hotel_id, h_phone_number),
  foreign key (hotel_id) references Hotel ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Room (
  hotel_id integer NOT NULL,
  room_number integer NOT NULL,
  flr integer,
  room_type RMTYPE,
  isempty YESNO,
  cleaned YESNO,
  primary key (hotel_id, room_number),
  foreign key (hotel_id) references Hotel ON UPDATE CASCADE ON DELETE CASCADE,
  check (flr > 0),
  UNIQUE(room_number)
);

CREATE TABLE RoomType (
  hotel_id integer NOT NULL,
  room_type RMTYPE,
  size integer NOT NULL,
  capacity integer,
  primary key (hotel_id, room_type),
  foreign key (hotel_id) references Hotel ON UPDATE CASCADE ON DELETE CASCADE,
  check (capacity > 0)
);

CREATE TABLE RoomPrice (
  hotel_id integer NOT NULL,
  day_of_week DAYZ NOT NULL,
  room_type RMTYPE,
  price integer,
  check (price > 0),
  primary key (hotel_id, day_of_week, room_type),
  foreign key (hotel_id) references Hotel ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Users (
email varchar (200) NOT NULL,
password varchar (20) NOT NULL,
name varchar (50),
surname varchar (50),
money_spent integer,
primary key (email)
);

CREATE TABLE Guest (
  hotel_id integer NOT NULL,
  guest_id integer NOT NULL,
  identification_type varchar (20),
  identification_number varchar (30),
  address varchar (40),
  name varchar (50),
  surname varchar (50),
  home_phone_number varchar (20),
  mobile_phone_number varchar (20),
  primary key (hotel_id, guest_id),
  foreign key (hotel_id) references Hotel ON UPDATE CASCADE ON DELETE CASCADE,
  UNIQUE (guest_id)
);

CREATE TABLE Reservation (
  hotel_id integer NOT NULL,
  room_number integer NOT NULL,
  guest_id integer NOT NULL,
  check_in date,
  check_out date,
  day_of_week DAYZ,
  total_price integer,
  check (total_price > 0), 
  primary key (hotel_id, room_number, guest_id),
  foreign key (hotel_id) references Hotel (hotel_id) ON UPDATE CASCADE ON DELETE CASCADE,
  foreign key (room_number) references Room (room_number) ON UPDATE CASCADE ON DELETE CASCADE,
  foreign key (guest_id) references Guest (guest_id) ON UPDATE CASCADE ON DELETE CASCADE,
  UNIQUE (check_in, check_out)
);

CREATE TABLE Employee (
  hotel_id integer NOT NULL,
  employee_id integer NOT NULL,
  name varchar(30),
  surname varchar(40),
  position varchar(15),
  salary integer,
  primary key (employee_id),
  foreign key (hotel_id) references Hotel ON UPDATE CASCADE ON DELETE CASCADE,
  check (salary > 0)
);

CREATE TABLE WorkingSchedule(
hotel_id integer NOT NULL,
employee_id integer NOT NULL,
froom time,
too time,
primary key (hotel_id, employee_id),
foreign key (hotel_id) references Hotel ON UPDATE CASCADE ON DELETE CASCADE,
foreign key (employee_id) references Employee ON UPDATE CASCADE ON DELETE CASCADE
);


INSERT INTO Hotel(hotel_id, hotel_name, hotel_address) VALUES
(800, 'FIZMAT Aqtau', 'KZ-Aqtau-Barsakelmes-21'),
(801, 'FIZMAT Alakol', 'KZ-Aqchi-Kelmesbarsa-3');




INSERT INTO HotelPhoneNumber (hotel_id, h_phone_number)  VALUES 
(800, '87775491193'),
(800, '87275491194'),
(800, '87775491195'),
(801, '87478312256'),
(801, '87278312255'),
(801, '87478312257');

INSERT INTO Room (hotel_id, room_number, flr, room_type, isempty, cleaned)  VALUES
(800, 101, 1, 'single', 'no', 'no'),
(800, 201, 2, 'double', 'yes', 'yes'),
(801, 202, 1, 'double', 'yes', 'no'),
(801, 112, 1, 'single', 'yes', 'no');

INSERT INTO RoomType (hotel_id, room_type, size, capacity)  VALUES
(800, 'single', 20, 1),
(800, 'double', 50, 2),
(801, 'single', 30, 1),
(801, 'double', 60, 2);

INSERT INTO RoomPrice (hotel_id, day_of_week, room_type, price) VALUES
 (800, 'MON', 'single', 120),
 (800, 'TUE', 'single', 120),
 (800, 'WED', 'single', 120),
 (800, 'THU', 'single', 130),
 (800, 'FRI', 'single', 130),
 (800, 'SAT', 'single', 200),
 (800, 'SUN', 'single', 200),
 (800, 'MON', 'double', 220),
 (800, 'TUE', 'double', 220),
 (800, 'WED', 'double', 220),
 (800, 'THU', 'double', 230),
 (800, 'FRI', 'double', 230),
 (800, 'SAT', 'double', 400),
 (800, 'SUN', 'double', 400),
 (801, 'MON', 'single', 100),
 (801, 'TUE', 'single', 100),
 (801, 'WED', 'single', 100),
 (801, 'THU', 'single', 110),
 (801, 'FRI', 'single', 110),
 (801, 'SAT', 'single', 180),
 (801, 'SUN', 'single', 180),
 (801, 'MON', 'double', 200),
 (801, 'TUE', 'double', 200),
 (801, 'WED', 'double', 200),
 (801, 'THU', 'double', 250),
 (801, 'FRI', 'double', 250),
 (801, 'SAT', 'double', 380),
 (801, 'SUN', 'double', 380);

INSERT INTO Users (email, password, name, surname, money_spent)  VALUES
('daniyel@mail.ru', 'dancho', 'Daniyel', 'Zhumankulov', 0),
('anar@mail.ru', 'an2000', 'Anar', 'Kuatzhan', 0),
('moldir@mail.ru', 'mol', 'Moldir', 'Ayazbay', 0),
('askar@mail.ru', 'ask200', 'Askar', 'Batzhanov', 0);

INSERT INTO Guest (hotel_id, guest_id, identification_type, identification_number, address, name, surname, home_phone_number, mobile_phone_number)  VALUES
(800, 01, 'identity card', 020405068, 'Taraz', 'Torgyn', 'Aidarbek', '05-07-03', '87074505689'),
(801, 02, 'US passport', 070608596, 'Almaty', 'John', 'Peter', '06-08-06', '87470458857');

INSERT INTO Reservation (hotel_id, room_number, guest_id, check_in, check_out, day_of_week, total_price)  VALUES
(800, 101, 01, '2021-11-30', '2021-12-13', 'TUE', 1680),
(801, 112, 02, '2021-12-08', '2021-12-21', 'MON', 1400);

INSERT INTO Employee (hotel_id, employee_id, name, surname, position, salary) VALUES
(800, 01, 'A1', 'B1', 'manager', 400000),
(800, 02, 'A2', 'B2', 'employee', 200000),
(800, 03, 'A3', 'B3', 'employee', 200000),
(800, 04, 'A4', 'B4', 'desk-clerk', 350000),
(800, 05, 'A5', 'B5', 'desk-clerk', 400000),
(801, 11, 'AA1', 'BB1', 'employee', 250000),
(801, 12, 'AA2', 'BB2', 'employee', 200000),
(801, 13, 'AA3', 'BB3', 'manager', 500000),
(801, 14, 'AA4', 'BB4', 'desk-clerk', 400000),
(801, 15, 'AA5', 'BB5', 'desk-clerk', 350000);

INSERT INTO WorkingSchedule (hotel_id, employee_id, froom, too)  VALUES
(800, 01, '08:00:00', '19:00:00'),
(800, 02, '08:00:00', '17:00:00'),
(800, 03, '08:00:00', '17:00:00'),
(800, 04, '08:00:00', '19:00:00'),
(800, 05, '19:00:00', '08:00:00'),
(801, 11, '08:00:00', '18:00:00'),
(801, 12, '08:00:00', '17:00:00'),
(801, 13, '08:00:00', '19:00:00'),
(801, 14, '08:00:00', '19:00:00'),
(801, 15, '19:00:00', '08:00:00');