/* AUTHOR: Seth Peterson
 * BRIEF: This is a series of commands to input into the sql command prompt
 *   in order to create the database from scratch.
 */
CREATE DATABASE Grubdata;

USE Grubdata;

/* Minimum information possible */
CREATE TABLE Users
(
  userid1 BINARY(128),
  userid2 BINARY(128),
  usertype INT,
  PRIMARY KEY(userid1,userid2)
);

CREATE TABLE Accounts
(
  userid1 BINARY(128),
  userid2 BINARY(128),
  email VARCHAR(30),
  address VARCHAR(30),
  displayname VARCHAR(30),
  PRIMARY KEY(userid1,userid2)
);

CREATE TABLE Carts
(
  userid1 BINARY(128),
  userid2 BINARY(128),
  serviceid1 BINARY(128),
  serviceid2 BINARY(128),
  itemid BINARY(128),
  quantity INT,
  PRIMARY KEY(userid1,userid2)
);

CREATE TABLE Menus
(
  serviceid1 BINARY(128),
  serviceid2 BINARY(128),
  itemid BINARY(128),
  maximum INT,
  PRIMARY KEY(serviceid1,serviceid2)
);
/* This prints out the current database lists.
sp_databases
go
*/

/* This prints the tables into the console so we can see we've made it
 * correctly.
SELECT * FROM INFORMATION_SCHEMA.TABLES
go
*/

/* At this point I start testing the stored procedures with instructions
 * found in sql_testvalues.sql
 */
