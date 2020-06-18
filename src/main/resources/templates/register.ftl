<!DOCTYPE html>
<!-- Alex Enache // Ben Ungur -->
<html>
    <head>
        <title>Minigrub - Register</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <header>
        <h1>MiniGrub</h1>
    </header>
    <body>
       <h2>Register:</h2>
       <form action="?cmd=register" method="post"> 
            <input type="email" placeholder="Enter email" name="email" required><br />
            <#--<input type="text" placeholder="Enter Username" name="user" minLength="5" maxLength="20" required>-->
            <input type="password" placeholder="Enter Password" name="password" minLength="8" maxLength="16" required><br />
            <input type="submit" value="Submit"><br />
       </form><br />
       <p>Already registered?<a href="?cmd=signin">Click here to sign in.</a></p><br />
       <a href="?cmd=index">Home</a>
    </body>

    <footer>&copy; 2020 BainBridge Team - Beniamin Ungur, Seth Peterson, Alex Enache</footer>
</html>
