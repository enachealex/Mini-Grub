<!DOCTYPE html>
<!-- Alex Enache // Ben Ungur -->
<html>
    <head>
        <title>Minigrub - ${restaurantName}</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <header>
        <h1>${restaurantName}</h1>
        <p>${restaurantDesc}</p>
        <a href="?cmd=signin">Log In</a>
        <a href="?cmd=register">Register</a>
    </header>
    <body>
        <fieldset>
            <legend>Add Food</legend>
            <form name="menu" action="add" method="post">
                <#list restaurantMenu?keys as item> <#-- Possibly make "restaurants" into RestaurantPojo -->
                        <p>${item}: ${restaurantMenu[item]} Quantity : <input type="number" name="quantity" /></p><br/>
                                                            <#-- Possibly make "restaurants" 
                                                            into RestaurantPojo and putting description 
                                                            for each food in RestaurantPojo as well -->
                   <#-- Possible Drink option ///////////////////////
                   <tr>
                        <td>${RestaurantPojo.drink}</td>
                        <td>${restaurants.description}</td>
                    </tr> -->
                </#list>
                
                <a href="?cmd=checkout"><button type="submit">Add to Cart</button></a>
            </form>
        </fieldset>
        
    </body>
    <footer>&copy; 2020 BainBridge Team - Beniamin Ungur, Seth Peterson, Alex Enache</footer>
</html>