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

$("#searchtextboxButton").click(function(e) {
    var key = $("#searchtextbox").val();
    e.preventDefault();
        $.ajax({
            type: "GET",
            url: "/findProduct",
            data: {
                key: key
            },
            success: function(result) {
                window.location = "http://localhost:8080/searchResultsPage";
            },
            error: function(result) {
            }
        });
});
