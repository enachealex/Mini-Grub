/* AUTHOR: Seth Peterson
 * BRIEF: THIS IS A SINGLE ACTION DATABASE SETUP FILE, currently working:
 * sqlcmd -i [directory to sqlcmd]
 * mysql -u username -p password < master.sql
 * To reset the database and use this again, just use this:
 *   DROP DATABASE Grubdata
 *   go
 * While inside the console window.
 */

:r C:\Users\sethp\Desktop\sql\init\database_init.sql
:r C:\Users\sethp\Desktop\sql\init\stored_procedures.sql
