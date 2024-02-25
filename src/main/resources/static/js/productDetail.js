$(document).ready(function() {
    // 從URL獲取商品ID
    var prodNo = new URLSearchParams(window.location.search).get('prodNo');


    if (prodNo) {
        $.ajax({
            url: `/productById/${prodNo}`,
            type: 'GET',
            success: function(product) {
                $('#product-image').attr('src', `/productImage/${product.prodPhoto}`)
                                   .attr('onerror', "this.onerror=null; this.src='/image/未有照片之圖片.png';");
                $('#product-name').text(product.prodName);
                $('#product-description').text(product.prodContent);

                if (product.promotion && product.promoId) {
                    $('#product-price').hide();
                    // 如果是促銷商品，顯示促銷價格和原價
                    $('#product-original-price').text(`原價: $${product.prodPrice}`).css('text-decoration', 'line-through').show();
                    $('#product-promo-price').text(`促銷價: $${product.promoPrice}`).addClass('text-danger');

                    // 獲取促銷活動的詳細信息
                    $.ajax({
                        url: `/promotion/${product.promoId}`,
                        type: 'GET',
                        success: function(promotion) {
                            $('#promo-details').html(`促銷活動: ${promotion.paName}<br>內容: ${promotion.paContent}`);
                        }
                    });
                } else {
                    $('#product-price').text(`價格: $${product.prodPrice}`).show();
                    // 非促銷商品，隱藏促銷信息
                    $('#product-original-price, #product-promo-price, #promo-details').hide();
                }

                $('.add-to-cart').attr({
                    'data-id': product.prodNo,
                    'data-name': product.prodName,
                    'data-price': product.promotion ? product.promoPrice : product.prodPrice,
                });
            },
            error: function(error) {
                console.error("錯誤信息:", error);
            }
        });
    }

    // 數量選擇器的事件綁定
    $('#increase-quantity').click(function() {
        var value = parseInt($('#product-quantity').val());
        if (value < 20) {
            $('#product-quantity').val(value + 1);
        }
    });

    $('#decrease-quantity').click(function() {
        var value = parseInt($('#product-quantity').val());
        if (value > 1) {
            $('#product-quantity').val(value - 1);
        }
    });
});
