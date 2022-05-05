$("#logout-admin").click(function(e) {
    e.preventDefault();
    $("#logout-admin-form").submit();
});

$("#dashboard").click(function(e) {
    location.reload();
});

$("#add-product-container").click(function(e) {
    e.preventDefault();
    $.ajax({
        type: "GET",
        url: "/backoffice/get-product/",
        contentType: 'text/html',
        success: function(result) {
            $("#admin-panel-content").html(result);
        },
        error: function(result) {
        }
    });
});


$(document).on('click', '#add-product', function(e) {
    e.preventDefault();
    var form = $("#file-upload-form");
    var formData = new FormData(form[0]);
        $.ajax({
            type: "POST",
            url: "/backoffice/add-product/",
            processData: false,
            contentType: false,
            data: formData,
            /*data: JSON.stringify({
                "code": $("#code").val(),
                "description": $("#description").val(),
                "name": $("#name").val(),
                "price": $("#price").val(),
                "imageFile": $("#imageFile").files[0],
                "warehouse": $("#warehouse").val(),
                "quantity": $("#quantity").val(),
            }),*/
            success: function(result) {
                $("#admin-panel-content").html(result);
            },
            error: function(result) {
            }
        });
});