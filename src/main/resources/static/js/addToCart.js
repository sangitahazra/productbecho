$("#addToCart").click(function(e) {
    alert('called');
    e.preventDefault();
    $.ajax({
        type: "POST",
        url: "/addToCart",
        data: {
            code: $("#product-code").text(),
            quantity: 1
        },
        success: function(result) {
         alert('ok, redirecting to cart page');
           window.location = "http://localhost:8080/cart";
        },
        error: function(result) {
         alert('error');
        }
    });
});
