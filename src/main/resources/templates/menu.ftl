<!DOCTYPE html>
<!-- Alex Enache -->
<html>
    <head>
        <title>MiniGrub</title>
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
        <fieldset>
            <legend>Add Food</legend>
            <form name="menu" action="add" method="post">
                <#list restaurants["menu"] as menu>
                    <tr>
                        <td>${restaurants.food}</td>
                        <td>${restaurants.description}</td>
                    </tr>
                </#list>
                Quantity : <input type="number" name="quantity" /><br/>
                <button type="submit">Add to Cart</button>
            </form>
        </fieldset>
        
    </body>
    <footer>&copy; 2020 BainBridge Team - Beniamin Ungur, Seth Peterson, Alex Enache</footer>
</html>