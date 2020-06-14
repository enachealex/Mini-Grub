/* AUTHOR: Seth Peterson
 * BRIEF: This file contains stored procedures for MiniGrub's SQL database.
 *     -User_IsUnique tests to prevent duplicate input errors
 *     -User_Insert takes string inputs, inserts hash
 */
/* BRIEF: This function determines whether user input is valid to insert.
 * (NOTE: THIS DOES NOT FILTER INPUT!!!)
 * UserHash1 - hashed value to find in database.
 * UserHash2 - second value to find in same row as the first.
 * IsUnique -> 1 if output is unique. 0 if it isn't.
 * TODO: Drop procedure if exists
 */
CREATE PROCEDURE User_IsUnique
(
  @UserHash1 BINARY(16),
  @UserHash2 BINARY(16),
  @IsUnique BIT OUTPUT
) AS
BEGIN
  SET @IsUnique=0
  IF NOT EXISTS
    (
      SELECT 1
      FROM Users
      WHERE Users.userid1 = @UserHash1
      AND Users.userid2 = @UserHash2
    )
    BEGIN
      SET @IsUnique=1
    END
RETURN 0
END
go

/* Take user info and hash sensitive data (TODO: Salt username/pw values) */
CREATE PROCEDURE User_Insert
(
  @Username varchar(30),
  @Password varchar(30),
  @Name varchar(30)
)
AS
BEGIN
  DECLARE @uh1 BINARY(16), @uh2 BINARY(16), @Output BIT, @ReturnValue INT
  SET @uh1 = CONVERT(BINARY(16),HASHBYTES('MD5', @Username))
  SET @uh2 = CONVERT(BINARY(16),HASHBYTES('MD5', @Password))
  /*Check to see if the values are unique before inserting */
  EXEC @ReturnValue = User_IsUnique @uh1, @uh2, @Output OUTPUT
  IF ( @Output = 1 )
  BEGIN
    INSERT INTO Users (userid1, userid2, usertype) VALUES (@uh1,@uh2,@Name)
  END
ELSE
  BEGIN
    PRINT N'ERROR: User ID combination already exists.'
  END
  /*PRINT @@ROWCOUNT */
END
go

/* I'm not certain if I should be designing this differently...
 * when I started, I thought it would be easy to make some methods work
 * for different tables, such as the ID checking as it's shared...
 */
CREATE PROCEDURE Account_IsUnique
(
  @UserHash1 BINARY(16),
  @UserHash2 BINARY(16),
  @IsUnique BIT OUTPUT
) AS
BEGIN
  SET @IsUnique=0
  IF NOT EXISTS
    (
      SELECT 1
      FROM Accounts
      WHERE Accounts.userid1 = @UserHash1
      AND Accounts.userid2 = @UserHash2
    )
    BEGIN
      SET @IsUnique=1
    END
RETURN 0
END
go

CREATE PROCEDURE Account_Insert
(
  @Username varchar(30),
  @Password varchar(30),
  @Email varchar(30),
  @Address varchar(30)
)
AS
BEGIN
  DECLARE @uh1 BINARY(16), @uh2 BINARY(16), @Output BIT, @ReturnValue INT
  SET @uh1 = CONVERT(BINARY(16),HASHBYTES('MD5', @Username))
  SET @uh2 = CONVERT(BINARY(16),HASHBYTES('MD5', @Password))
  /*Check to see if the values are unique before inserting */
  EXEC @ReturnValue = Account_IsUnique @uh1, @uh2, @Output OUTPUT
  IF ( @Output = 1 )
  BEGIN
    /* TODO: Check to ensure User entry exists first */
    INSERT INTO Accounts (userid1, userid2, email, address) VALUES (@uh1,@uh2,@Email,@Address)
  END
ELSE
  BEGIN
    PRINT N'ERROR: Account ID combination already exists.'
  END
  /*PRINT @@ROWCOUNT */
END
go
