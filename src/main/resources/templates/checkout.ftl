<!DOCTYPE html>
<!-- Alex Enache // Ben Ungur -->
<html>
    <head>
        <title>Minigrub - Checkout</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <header>
        <h1>MiniGrub</h1>
<<<<<<< HEAD
    </header>
    <body>
        <table border="1">
            <form name="cart"><#-- possibly a get method? -->
                <#list restaurants["cart"] as cart>
                    <tr border="0.5">
                        <td>${RestaurantPojo.food}</td>
                        Quantity : <input type="number" name="quantity" /><br/>
=======
        <p>A simulated food delivery service set up by 4 local restaurants that hate GrubHub.</p>
        <a href="?cmd=signin">Log In</a>
        <a href="?cmd=register">Register</a>
    </header>
    <body>
        <fieldset>
            <legend>Checkout</legend>
            <form name="cart" action="show" method="post">
                <#list cart?keys as item>
                    <tr>
                        <td>${item} : ${cart[item]} <br/>
                        Quantity : <input type="number" name="quantity" minLength="0" required/><br/> <#-- need JAVA to store quantity from previous page to place in this input, but allow changes to input if desired. Maybe if quantity = 0, remove? -->
                        </td>
                        
>>>>>>> 4ab60016811378f365a36a9618b9fe6da78bb457
                    </tr>
                </#list>
                <button type="submit" id="checkout">Pay Order</button>
            </form>
        </fieldset>
        
    </body>

    <footer>&copy; 2020 BainBridge Team - Beniamin Ungur, Seth Peterson, Alex Enache</footer>
</html>
