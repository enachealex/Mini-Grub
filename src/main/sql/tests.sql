/* AUTHOR: Seth Peterson
 * BRIEF: This file contains basic tests for sql stored procedures.
 */
/*This is a basic interface for the sp_IsIDUnique procedure */
DECLARE @test1 BINARY(16), @test2 BINARY(16), @output BIT
SET @test1 = CONVERT(BINARY(16),HASHBYTES('MD5','test1'))
SET @test2 = CONVERT(BINARY(16),HASHBYTES('MD5','test2'))
EXEC sp_IsIDUnique @test1, @test2, @output OUTPUT
PRINT @output
go

/*This is a basic usage of the insert user procedure */
sp_InsertUser 'test1', 'test2', 0
go
SELECT * FROM Userdb
go
