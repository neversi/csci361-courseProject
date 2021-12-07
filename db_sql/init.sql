\c postgres;
CREATE TABLE users (
    id serial,
    name varchar(100),
    surname varchar(100),
    username varchar(40) unique not null,
    password varchar(250),
    salt varchar(10),
    primary key (id, username)
);

-- \c postgres;        

-- CREATE TABLE Role(
-- RoleID SERIAL PRIMARY KEY,
-- RoleName varchar(50),
-- );    
-- insert into Role(RoleName)
-- values ('Admin'),('User');
