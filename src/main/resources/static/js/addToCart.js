$("#abstract-order-entry button").click(function(e) {
    var pCode = $(this).closest('#abstract-order-entry').find("#product-code").val();
    var pQuantity = $(this).closest('#abstract-order-entry').find("#quantity").val();
    var buttonId = $(this).attr('id');
    $(this).parent().addClass("hide");
    $(this).parent().parent().find(".view-cart-details").removeClass("hide");
    e.preventDefault();
        $.ajax({
            type: "POST",
            url: "/addToCart",
            data: {
                code: pCode,
                quantity: pQuantity
            },
            success: function(result) {

            },
            error: function(result) {
            }
        });
});

$("#search-box-icon").click(function(e) {
    e.preventDefault();
    $("#search-box-form").submit();
});

$(".view-cart-details").click(function(e) {
    e.preventDefault();
    window.location.href="/cart";
});

