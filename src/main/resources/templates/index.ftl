<!DOCTYPE html>
<!-- Alex Enache -->
<html>
    <head>
        <title>Minigrub - Home</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <header>
        <h1>MiniGrub</h1>
        <p>A simulated food delivery service set up by 4 local restaurants that hate GrubHub.</p>
        <a href="?cmd=signin">Log In</a>
        <a href="?cmd=register">Register</a>
    </header>
    <body>
       <h2>Select a restaurant:</h2>
       <#list restaurants?keys as k>
             <a href="?cmd=menu&id=${restaurants[k]?int?c}" type="submit"><button>${k}</button></a>
       </#list>
       <#--<a href="?cmd=menu"><button id=1000>Pizzeria</button></a>
       <a href="?cmd=menu"><button id=1003>Burger Joint</button></a>
       <a href="?cmd=menu"><button id=1001>Taco Del Goodness</button></a>
       <a href="?cmd=menu"><button id=1002>Rice is Nice</button></a>-->
    </body>

    <footer>&copy; 2020 BainBridge Team - Beniamin Ungur, Seth Peterson, Alex Enache</footer>
</html>
