/* AUTHOR: Seth Peterson
 * BRIEF: This file contains stored procedures for MiniGrub's SQL database.
 *     -sp_IsIDUnique tests to prevent duplicate input errors
 *     -sp_InsertUser takes string inputs, inserts hash
 */
/* BRIEF: This function determines whether user input is valid to insert.
 * (NOTE: THIS DOES NOT FILTER INPUT!!!)
 * UserHash1 - hashed value to find in database.
 * UserHash2 - second value to find in same row as the first.
 * IsUnique -> 1 if output is unique. 0 if it isn't.
 */
CREATE PROCEDURE sp_IsIDUnique
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
      FROM Userdb 
      WHERE Userdb.userid1 = @UserHash1 
      AND Userdb.userid2 = @UserHash2
    )
    BEGIN
      SET @IsUnique=1
    END
RETURN 0
END

/* Take user info and hash sensitive data (TODO: Salt username/pw values) */
CREATE PROCEDURE sp_InsertUser
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
  EXEC @ReturnValue = sp_IsIDUnique @uh1, @uh2, @Output OUTPUT
  IF ( @Output = 1 )
  BEGIN
    INSERT INTO Userdb (userid1, userid2, usertype) VALUES (@uh1,@uh2,@Name)
  END
ELSE
  BEGIN
    PRINT N'ERROR: User ID combination already exists.'
  END
  /*PRINT @@ROWCOUNT */
END
