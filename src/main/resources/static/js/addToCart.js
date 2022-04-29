$("#abstract-order-entry button").click(function(e) {
    var pCode = $(this).closest('#abstract-order-entry').find("#product-code").text();
    var pQuantity = $(this).closest('#abstract-order-entry').find("#quantity").val();
    e.preventDefault();
        $.ajax({
            type: "POST",
            url: "/addToCart",
            data: {
                code: pCode,
                quantity: pQuantity
            },
            success: function(result) {
               window.location = "http://localhost:8080/cart";
            },
            error: function(result) {
            }
        });
});
