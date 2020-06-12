/* AUTHOR: Seth Peterson
 * BRIEF: This is a series of commands to input into the sql command prompt
 *   in order to create the database from scratch.
 *   TODO: Ensure that this function properly initializes the database-
 *   sqlcmd exec ?????
 */
CREATE DATABASE Grubdata
go

sp_databases
go

USE Grubdata
go

CREATE TABLE Userdb
(
  userid1 BINARY(16),
  userid2 BINARY(16),
  usertype INT,
  PRIMARY KEY(userid1,userid2)
)
go

SELECT * FROM INFORMATION_SCHEMA.TABLES
go

/*Note - remove apostrophes if using code directly in the console window
 * below are some sample values to try and insert with stored procedure
 * For the stored procedures, I've pasted them from sql_sproc.sql into 
 * the command line individually, then 'go' and checking with the following:
 */

/*paste code*/
go
sp_helptext sp_IsIDUnique
go

/*paste code*/
go
sp_helptext sp_InsertUser
go

/* At this point I start testing the stored procedures with instructions
 * found in sql_testvalues.sql
 */


