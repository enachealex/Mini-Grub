/* AUTHOR: Seth Peterson
 * BRIEF: This file contains basic tests for sql stored procedures.
 */

/* This section just prints the code of the sproc into the console window.
I haven't tested most of the commented out methods in MySQL, just sqlcmd
sp_helptext User_IsUnique
go

sp_helptext User_Insert
go
*/

USE Grubdata;

/*This is a basic interface for the User_IsUnique procedure */
CALL User_IsUnique(CONVERT(MD5('test1'),BINARY(128)), CONVERT(MD5('test2'),BINARY(128)), @output);
SELECT CONCAT('IsUnique result: ',@output,char(10)) AS 'User_IsUnique Result';

/*This is a basic usage of the insert user procedure */

CALL User_Insert('test1', 'test2', 0);
SELECT * FROM Users;

/* Now I test inserting the user from that procedure to insert its
 * values into the Accounts table too */

CALL User_Insert('testacc1','testacc2',0);
SELECT * FROM Users;
CALL Account_Insert('testacc1', 'testacc2', 'testacc@email.example', '12345 987th Ave','testdisplay');
SELECT * FROM Users INNER JOIN Accounts ON Users.userid1 = Accounts.userid1;
