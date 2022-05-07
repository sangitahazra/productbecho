$("#abstract-order-entry button").click(function(e) {
    var pCode = $(this).closest('#abstract-order-entry').find("#product-code").text();
    var pQuantity = $(this).closest('#abstract-order-entry').find("#quantity").val();
    var buttonId = $(this).attr('id');
    e.preventDefault();
        $.ajax({
            type: "POST",
            url: "/addToCart",
            data: {
                code: pCode,
                quantity: pQuantity
            },
            success: function(result) {
              $("#" + buttonId).hide();
               $("#target" + buttonId).show();
            },
            error: function(result) {
            }
        });
});
