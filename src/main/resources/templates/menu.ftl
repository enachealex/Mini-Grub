<!DOCTYPE html>
<!-- Alex Enache // Ben Ungur -->
<html>
    <head>
        <title>Minigrub - Menu</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <header>
        <h1>MiniGrub</h1>
        <p>A simulated food delivery service set up by 4 local restaurants that hate GrubHub.</p>
        <a href="?cmd=signin">Log In</a>
        <a href="?cmd=signin">Register</a>
    </header>
    <body>
        <fieldset>
            <legend>Add Food</legend>
            <form name="menu" action="add" method="post">
                <#list restaurants["menu"] as menu> <#-- Possibly make "restaurants" into RestaurantPojo -->
                    <tr>
                        <td>${RestaurantPojo.food}</td>
                        <td>${restaurants.description}</td>   <#-- Possibly make "restaurants" 
                                                            into RestaurantPojo and putting description 
                                                            for each food in RestaurantPojo as well -->
                    </tr>

                   <#-- Possible Drink option ///////////////////////
                   <tr>
                        <td>${RestaurantPojo.drink}</td>
                        <td>${restaurants.description}</td>
                    </tr> -->
                </#list>
                Quantity : <input type="number" name="quantity" /><br/>
                <button type="submit">Add to Cart</button>
            </form>
        </fieldset>
        
    </body>
    <footer>&copy; 2020 BainBridge Team - Beniamin Ungur, Seth Peterson, Alex Enache</footer>
</html>