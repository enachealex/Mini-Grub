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
    </header>
    <body>
        <table border="1">
            <form name="cart"><#-- possibly a get method? -->
                <#list restaurants["cart"] as cart>
                    <tr border="0.5">
                        <td>${RestaurantPojo.food}</td>
                        Quantity : <input type="number" name="quantity" /><br/>
                    </tr>
                </#list>
                <button type="submit" id="checkout">Pay Order</button>
            </form>
        </fieldset>
        
    </body>

    <footer>&copy; 2020 BainBridge Team - Beniamin Ungur, Seth Peterson, Alex Enache</footer>
</html>
