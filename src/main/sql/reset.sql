DELETE FROM Users;
DELETE FROM Accounts;

DROP PROCEDURE User_IsUnique;
DROP PROCEDURE User_Insert;
DROP PROCEDURE Account_IsUnique;
DROP PROCEDURE Account_Insert;

DROP DATABASE Grubdata;

/* Allow or disable destructive updates to tables */
SET SQL_SAFE_UPDATES=0;
SET SQL_SAFE_UPDATES=1;