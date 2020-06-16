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
 USE Grubdata;
 DELIMITER //
CREATE PROCEDURE User_IsUnique
(
  In UserHash1 BINARY(128),
  In UserHash2 BINARY(128),
  Out IsUnique BIT
)
BEGIN
  SET IsUnique=0;
  IF NOT EXISTS
    (
      SELECT 1
      FROM Users
      WHERE Users.userid1 = UserHash1
      AND Users.userid2 = UserHash2
    ) THEN
      SET IsUnique=1;
	END IF;
#RETURN 0;
END //

/* Take user info and hash sensitive data (TODO: Salt username/pw values) */
CREATE PROCEDURE User_Insert
(
  In Username varchar(30),
  In Pword varchar(30),
  In Utype varchar(30)
)
BEGIN
  #DECLARE @uh1 BINARY(16), @uh2 BINARY(16), @Output BIT, @ReturnValue INT;
  SET @uh1 = CONVERT(MD5(Username),BINARY(128));
  SET @uh2 = CONVERT(MD5(Pword),BINARY(128));
  /*Check to see if the values are unique before inserting */
  CALL User_IsUnique(@uh1, @uh2, @IsUnique);
  IF ( @IsUnique = 1 ) THEN
    INSERT INTO Users (userid1, userid2, usertype) VALUES (@uh1,@uh2,Utype);
  ELSE
    SELECT 'ERROR: User ID combination already exists.' AS '';
  END IF;
  /*PRINT @@ROWCOUNT */
END //

/* I'm not certain if I should be designing this differently...
 * when I started, I thought it would be easy to make some methods work
 * for different tables, such as the ID checking as it's shared...
 */
CREATE PROCEDURE Account_IsUnique
(
  IN UserHash1 BINARY(128),
  IN UserHash2 BINARY(128),
  OUT IsUnique BIT
)
BEGIN
  SET IsUnique=0;
  IF NOT EXISTS
    (
      SELECT 1
      FROM Accounts
      WHERE Accounts.userid1 = @UserHash1
      AND Accounts.userid2 = @UserHash2
    ) THEN
      SET IsUnique=1;
    END IF;
END //

CREATE PROCEDURE Account_Insert
(
  IN Username varchar(30),
  IN Pword varchar(30),
  IN Email varchar(30),
  IN Address varchar(30),
  IN Displayname varchar(30)
)
BEGIN
  SET @uh1 = CONVERT(MD5(Username),BINARY(128));
  SET @uh2 = CONVERT(MD5(Pword),BINARY(128));
  /*Check to see if the values are unique before inserting */
  SET @Output = 0;
  CALL Account_IsUnique(@uh1, @uh2, @Output);
  IF ( @Output = 1 ) THEN
    /* TODO: Check to ensure User entry exists first */
    INSERT INTO Accounts (userid1, userid2, email, address, displayname) VALUES (@uh1,@uh2,Email,Address,Displayname);
  ELSE
    SELECT 'ERROR: Account ID combination already exists.' AS '';
  END IF;
  /*PRINT @@ROWCOUNT */
END //
DELIMITER ;
