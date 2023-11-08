$(document).ready(function() {
    // 從URL獲取商品ID
    var prodNo = new URLSearchParams(window.location.search).get('prodNo');


    if (prodNo) {
        $.ajax({
            url: `/productById/${prodNo}`,
            type: 'GET',
            success: function(product) {
                // 更新頁面上的商品信息
                $('#product-image').attr('src', `/productImage/${product.prodNo}`);
                $('#product-name').text(product.prodName);
                $('#product-description').text(product.prodContent);
                $('#product-price').text(`$${product.prodPrice}`);

                // 更新加入購物車按鈕的 data-* 屬性
                $('#add-to-cart').data('id', product.prodNo);
                $('#add-to-cart').data('name', product.prodName);
                $('#add-to-cart').data('price', product.prodPrice);
            },
            error: function(error) {
                console.log('Error fetching product details:', error);
            }
        });
    }

    // 數量選擇器的事件綁定
    $('#increase-quantity').click(function() {
        var value = parseInt($('#product-quantity').val());
        $('#product-quantity').val(value + 1);
    });

    $('#decrease-quantity').click(function() {
        var value = parseInt($('#product-quantity').val());
        if (value > 1) {
            $('#product-quantity').val(value - 1);
        }
    });
});
