$("#guest-next").click(function(e) {
    e.preventDefault();
    $.ajax({
        type: "POST",
        url: "/checkout/guest/add/",
        contentType: 'application/json',
        data: JSON.stringify({
            "email": $("#guest-details #email").val(),
            "phone": $("#guest-details #phone").val()
        }),
        success: function(result) {
            $("#guest-details").addClass("hide");
            $("#shipping-details").removeClass("hide");
        },
        error: function(result) {
        }
    });
});

$("#shipping-next").click(function(e) {
    e.preventDefault();
    $.ajax({
        type: "POST",
        url: "/checkout/address/add/",
        contentType: 'application/json',
        data: JSON.stringify({
            "name": $("#shipping-details #shipping-user-name").val(),
            "line1": $("#shipping-details #shipping-address").val(),
            "line2": $("#shipping-details #shipping-address-landmark").val(),
            "city": $("#shipping-details #shipping-city").val(),
            "state": $("#shipping-details #shipping-state").val(),
             "zip": $("#shipping-details #shipping-zip-code").val(),
             "phone": $("#shipping-details #shipping-phone").val()
        }),
        success: function(result) {
            $("#shipping-details").addClass("hide");
            $("#payment-details").removeClass("hide");
        },
        error: function(result) {
        }
    });
});

$("#payment-next").click(function(e) {
    e.preventDefault();
    $.ajax({
        type: "POST",
        url: "/checkout/payment/add/",
        contentType: 'application/json',
        success: function(result) {
            window.location = "http://localhost:8080/checkout/success";
        },
        error: function(result) {
        }
    });
});