CREATE TABLE Hotel (
  hotel_id serial NOT NULL,
  hotel_name varchar(100),
  hotel_address varchar(200),
  PRIMARY KEY (hotel_id),
  UNIQUE (hotel_name, hotel_address)
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
password varchar (50) NOT NULL,
name varchar (50),
surname varchar (50),
money_spent integer,
salt varchar(20),
primary key (email)
);

CREATE TABLE Guest (
  hotel_id integer NOT NULL,
  guest_id serial NOT NULL,
  email VARCHAR(200) not null,
  identification_type varchar (20),
  identification_number varchar (30),
  address varchar (40),
  name varchar (50),
  surname varchar (50),
  home_phone_number varchar (20),
  mobile_phone_number varchar (20),
  primary key (hotel_id, guest_id),
  foreign key (hotel_id) references Hotel ON UPDATE CASCADE ON DELETE CASCADE,
  foreign key (email) references Users(email) on update cascade on delete cascade,
  unique (email),
  UNIQUE (guest_id)
);

CREATE TABLE Reservation (
  id serial,
  hotel_id integer NOT NULL,
  room_number integer NOT NULL,
  guest_id integer NOT NULL,
  check_in date,
  check_out date,
  day_of_week DAYZ,
  total_price integer,
  check (total_price > 0), 
  primary key (id),
  foreign key (hotel_id) references Hotel (hotel_id) ON UPDATE CASCADE ON DELETE CASCADE,
  foreign key (room_number) references Room (room_number) ON UPDATE CASCADE ON DELETE CASCADE,
  foreign key (guest_id) references Guest (guest_id) ON UPDATE CASCADE ON DELETE CASCADE,
  UNIQUE (check_in, check_out)
);

CREATE TABLE Employee (
  hotel_id integer NOT NULL,
  employee_id integer,
  email varchar(200),
  rolle int,
  name varchar(30),
  surname varchar(40),
  position varchar(15),
  salary integer,
  primary key (employee_id),
  foreign key (hotel_id) references Hotel(hotel_id) ON DELETE CASCADE,
  foreign key (email) references Users(email) on delete cascade,
  check (salary > 0)
);

CREATE TABLE WorkingSchedule(
id serial,
hotel_id integer NOT NULL,
employee_id integer NOT NULL,
froom time,
too time,
primary key (id),
foreign key (hotel_id) references Hotel ON DELETE CASCADE,
foreign key (employee_id) references Employee ON DELETE CASCADE
);


INSERT INTO Hotel(hotel_name, hotel_address) VALUES
('FIZMAT Aqtau', 'KZ-Aqtau-Barsakelmes-21'),
('FIZMAT Alakol', 'KZ-Aqchi-Kelmesbarsa-3');




INSERT INTO HotelPhoneNumber (hotel_id, h_phone_number)  VALUES 
(1, '87775491193'),
(1, '87275491194'),
(1, '87775491195'),
(2, '87478312256'),
(2, '87278312255'),
(2, '87478312257');

INSERT INTO Room (hotel_id, room_number, flr, room_type, isempty, cleaned)  VALUES
(1, 101, 1, 'single', 'no', 'no'),
(1, 201, 2, 'double', 'yes', 'yes'),
(2, 202, 1, 'double', 'yes', 'no'),
(2, 112, 1, 'single', 'yes', 'no');

INSERT INTO RoomType (hotel_id, room_type, size, capacity)  VALUES
(1, 'single', 20, 1),
(1, 'double', 50, 2),
(2, 'single', 30, 1),
(2, 'double', 60, 2);

INSERT INTO RoomPrice (hotel_id, day_of_week, room_type, price) VALUES
 (1, 'MON', 'single', 120),
 (1, 'TUE', 'single', 120),
 (1, 'WED', 'single', 120),
 (1, 'THU', 'single', 130),
 (1, 'FRI', 'single', 130),
 (1, 'SAT', 'single', 200),
 (1, 'SUN', 'single', 200),
 (1, 'MON', 'double', 220),
 (1, 'TUE', 'double', 220),
 (1, 'WED', 'double', 220),
 (1, 'THU', 'double', 230),
 (1, 'FRI', 'double', 230),
 (1, 'SAT', 'double', 400),
 (1, 'SUN', 'double', 400),
 (2, 'MON', 'single', 100),
 (2, 'TUE', 'single', 100),
 (2, 'WED', 'single', 100),
 (2, 'THU', 'single', 110),
 (2, 'FRI', 'single', 110),
 (2, 'SAT', 'single', 180),
 (2, 'SUN', 'single', 180),
 (2, 'MON', 'double', 200),
 (2, 'TUE', 'double', 200),
 (2, 'WED', 'double', 200),
 (2, 'THU', 'double', 250),
 (2, 'FRI', 'double', 250),
 (2, 'SAT', 'double', 380),
 (2, 'SUN', 'double', 380);

INSERT INTO Users (email, password, name, surname, money_spent, salt)  VALUES
('daniyel@mail.ru', 'dc43e1f7374c2f73f6af13333e54e2', 'Daniyel', 'Zhumankulov', 0, 'default'),
('anar@mail.ru', 'd47c72f2128d96569f91fb865eb59fe', 'Anar', 'Kuatzhan', 0, 'default'),
('moldir@mail.ru', '24f26dc9f27bc7ffc49f3bb410cdf971', 'Moldir', 'Ayazbay', 0, 'default'),
('abdr@mail.ru', 'dc43e1f7374c2f73f6af13333e54e2', 'Abdr', 'Ayazbay', 0, 'default'),
('bauka@mail.ru', 'dc43e1f7374c2f73f6af13333e54e2', 'Bauka', 'Uzumaki', 0, 'default'),
('shamauka@mail.ru', 'dc43e1f7374c2f73f6af13333e54e2', 'Shamauka', 'Ayazbay', 0, 'default'),
('tamauka@mail.ru', 'dc43e1f7374c2f73f6af13333e54e2', 'Tamauka', 'Ayazbay', 0, 'default'),
('askar@mail.ru', '45b1782d372e17d057d4812f1d65e9', 'Askar', 'Batzhanov', 0, 'default');

INSERT INTO Guest (hotel_id, guest_id, identification_type, identification_number, address, name, surname, home_phone_number, mobile_phone_number, email)  VALUES
(1, 01, 'identity card', 020405068, 'Taraz', 'Daniyel', 'Zhumankulov', '05-07-03', '87074505689', 'daniyel@mail.ru'),
(2, 02, 'US passport', 070608596, 'Almaty', 'Anar', 'Kuatzhan', '06-08-06', '87470458857', 'anar@mail.ru');

INSERT INTO Reservation (hotel_id, room_number, guest_id, check_in, check_out, day_of_week, total_price)  VALUES
(1, 101, 01, '2021-11-30', '2021-12-13', 'TUE', 1680),
(1, 101, 01, '2021-12-13', '2021-12-20', 'WED', 1680),
(2, 112, 02, '2021-12-08', '2021-12-21', 'MON', 1400);

INSERT INTO Employee (hotel_id, employee_id, name, surname, position, salary, email) VALUES
(1, 01, 'A1', 'B1', 'manager', 400000, 'bauka@mail.ru'),
(1, 02, 'A2', 'B2', 'employee', 200000, 'abdr@mail.ru'),
(1, 03, 'A3', 'B3', 'employee', 200000, 'shamauka@mail.ru'),
(1, 04, 'A4', 'B4', 'desk-clerk', 350000, 'tamauka@mail.ru'),
-- (1, 05, 'A5', 'B5', 'desk-clerk', 400000, ''),
-- (2, 11, 'AA1', 'BB1', 'employee', 250000, ''),
-- (2, 12, 'AA2', 'BB2', 'employee', 200000, ''),
-- (2, 13, 'AA3', 'BB3', 'manager', 500000,''),
-- (2, 14, 'AA4', 'BB4', 'desk-clerk', 400000, ''),
(2, 15, 'AA5', 'BB5', 'desk-clerk', 350000, 'askar@mail.ru');

INSERT INTO WorkingSchedule (hotel_id, employee_id, froom, too)  VALUES
(1, 01, '08:00:00', '19:00:00'),
(1, 02, '08:00:00', '17:00:00'),
(1, 03, '08:00:00', '17:00:00'),
(1, 04, '08:00:00', '19:00:00'),
-- (1, 05, '19:00:00', '08:00:00'),
-- (2, 11, '08:00:00', '18:00:00'),
-- (2, 12, '08:00:00', '17:00:00'),
-- (2, 13, '08:00:00', '19:00:00'),
-- (2, 14, '08:00:00', '19:00:00'),
(2, 15, '19:00:00', '08:00:00');