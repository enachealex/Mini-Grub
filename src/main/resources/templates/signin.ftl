<!DOCTYPE html>
<!-- Alex Enache // Ben Ungur -->
<html>
    <head>
        <title>Minigrub - Sign In</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <header>
        <h1>MiniGrub</h1>
    </header>
    <body>
       <h2>Sign In:</h2>
       <#if loggedIn>
            You are already signed in.<br />
            <a href="?cmd=index">Find restaurants</a>
       <#else>
            <form action="?cmd=signin" method="post">
                <input type="email" placeholder="Enter Email" name="email" required><br />
                <input type="password" placeholder="Enter Password" name="password" minLength="8" maxLength="16" required><br />
                <input type="submit" value="Submit"/>
                <input class="button" type="button" onclick="window.location.replace('/hub/?cmd=index')" value="Cancel" /><br />
                <p>Done' have an account? <a href="?cmd=register">Register</a></p>
            </form><br />
        </#if>
    </body>

    <footer>&copy; 2020 BainBridge Team - Beniamin Ungur, Seth Peterson, Alex Enache</footer>
</html>
