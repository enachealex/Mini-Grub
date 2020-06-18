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
<<<<<<< HEAD
            <legend>Add Food</legend>
            <form name="menu" action="?cmd=checkout" method="post">
                <#list restaurantMenu?keys as item>
                        <p>${item}: $${restaurantMenu[item]} Quantity : <input type="number" name="quantity" /></p><br/>
                </#list>
                
                <input type="submit" value="Add to Cart"/>
=======
            <legend>Select Food</legend>
            <form name="menu" action="add" method="post">
                <#list restaurantMenu?keys as item> <#-- Possibly make "restaurants" into RestaurantPojo -->
                        <p>${item}: ${restaurantMenu[item]} <br/> 
                        Quantity : <input type="number" name="quantity" minLength="0" value="0"required/>
                        <button type="submit" id="cart">Add to Cart</button></p><br/>
                                                            <#-- Possibly make "restaurants" 
                                                            into RestaurantPojo and putting description 
                                                            for each food in RestaurantPojo as well -->
                   <#-- Possible Drink option ///////////////////////
                   <tr>
                        <td>${RestaurantPojo.drink}</td>
                        <td>${restaurants.description}</td>
                    </tr> -->
                </#list>
                <a href="?cmd=checkout" id="checkout"><button>Checkout</button></a>
>>>>>>> 4ab60016811378f365a36a9618b9fe6da78bb457
            </form>
        </fieldset>
        
    </body>
    <footer>&copy; 2020 BainBridge Team - Beniamin Ungur, Seth Peterson, Alex Enache</footer>
</html>