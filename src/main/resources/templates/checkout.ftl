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
        <p>A simulated food delivery service set up by 4 local restaurants that hate GrubHub.</p>
        <a href="?cmd=signin=${signin}">Log In</a>
        <a href="?cmd=signin=${register}">Register</a>
    </header>
    <body>
        <table border="1">
            <form name="cart" action="show" method="post"><#-- possibly a get method? -->
                <#list restaurants["cart"] as cart>
                    <tr border="0.5">
                        <td>${RestaurantPojo.food}</td>
                        Quantity : <input type="number" name="quantity" /><br/>
                    </tr>
                </#list>
                <button type="submit" id="checkout">Pay Order</button>
            </form>
        </table>

        <a href="?cmd=show&index=${remove}">Remove</a> &nbsp; &nbsp;
        
    </body>

    <footer>&copy; 2020 BainBridge Team - Beniamin Ungur, Seth Peterson, Alex Enache</footer>
</html>
