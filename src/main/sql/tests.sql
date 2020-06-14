/* AUTHOR: Seth Peterson
 * BRIEF: This file contains basic tests for sql stored procedures.
 */

/* This section just prints the code of the sproc into the console window.
sp_helptext User_IsUnique
go

sp_helptext User_Insert
go
*/

USE Grubdata
go

/*This is a basic interface for the User_IsUnique procedure */
DECLARE @test1 BINARY(16), @test2 BINARY(16), @output BIT
SET @test1 = CONVERT(BINARY(16),HASHBYTES('MD5','test1'))
SET @test2 = CONVERT(BINARY(16),HASHBYTES('MD5','test2'))
EXEC User_IsUnique @test1, @test2, @output OUTPUT
PRINT CONCAT('IsUnique result: ',@output,char(10))
go

/*This is a basic usage of the insert user procedure */
User_Insert 'test1', 'test2', 0
go
SELECT * FROM Users
go

/* Now I test inserting the user from that procedure to insert its
 * values into the Accounts table too */

User_Insert 'testacc1','testacc2',0
go
SELECT * FROM Users
go
Account_Insert 'testacc1', 'testacc2', 'testacc@email.example', '12345 987th Ave'
go
SELECT * FROM Users INNER JOIN Accounts ON Users.userid1 = Accounts.userid1
go
