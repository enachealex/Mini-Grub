<!DOCTYPE html>
<!-- Alex Enache // Ben Ungur -->
<html>
    <head>
        <title>Minigrub - Signin</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <header>
        <h1>MiniGrub</h1>
        <p>A simulated food delivery service set up by 4 local restaurants that hate GrubHub.</p>
        <a href="?cmd=signin=${signin}">Log In</a>
        <a href="?cmd=signin=${register}">Register</a>
    </header>
    <body>
       <h2>Sign-in or Register</h2>
       <input type="email" placeholder="Enter Email" name="email" required>
       <input type="password" placeholder="Enter Password" name="password" minLength="8" maxLength="16" required>
       <a id="logIn">Log In</a>
       <a id="register">Register</a>
       <a href="?cmd=signin=${forgotPassword}">Forgot Password</a><br/>
    </body>

    <footer>&copy; 2020 BainBridge Team - Beniamin Ungur, Seth Peterson, Alex Enache</footer>
</html>
