function loadProducts(category) {
    var url = category === 'all' ? '/products' : `/productsByCategory/${category}`;
    $.ajax({
        url: url,
        type: 'GET',
        success: function(products) {
            console.log(products);
            // 清空商品展示區域
            $('#products-row').empty();
            //已新增上架商品才能顯示
            products.forEach(function(product) {
                console.log(product.prodName, product.promotion, product.prodStatus);
                if (!product.promotion && product.prodStatus === '銷售中') {
                    var productCard = `
                    <div class="col-md-3">
                        <div class="card mb-3 box-shadow">
                            <img class="card-img-top product-img" src="/productImage/${product.prodNo}" alt="${product.prodName}">
                            <div class="card-body">
                                <p class="card-text">${product.prodName}</p>
                                <div class="d-flex justify-content-between align-items-center">
                                    <div class="btn-group">
                                        <button type="button" class="btn btn-sm btn-outline-secondary view-details" data-prodno="${product.prodNo}">查看</button>
<!--                                        <button type="button" class="btn btn-sm btn-outline-secondary add-to-cart" data-id="${product.prodNo}" data-name="${product.prodName}" data-price="${product.prodPrice}">加入購物車</button>-->
                                    </div>
                                    <small class="text-muted">$${product.prodPrice}</small>
                                </div>
                            </div>
                        </div>
                    </div>
                `;
                    // 將商品卡片插入到頁面中
                    $('#products-row').append(productCard);
                }
            });

            // 綁定查看詳情按鈕的點擊事件
            $('#products-row').on('click', '.view-details', function() {
                var prodNo = $(this).data('prodno');
                window.location.href = `/productDetail.html?prodNo=${prodNo}`;
            });

        },
        error: function(error) {
            console.log('Error fetching products:', error);
        }
    });
}



$(document).ready(function() {
    // 加載類別
    loadCategories();

    // 頁面加載時預設加載所有商品
    loadProducts('all');
    // 加載促銷商品
    loadPromotionProducts();
    $('#products-row').on('click', '.view-details', function() {
        var prodNo = $(this).data('prodno');
        window.location.href = `/productDetail.html?prodNo=${prodNo}`;
    });
});

function loadCategories() {
    $.ajax({
        url: '/productCategories',
        type: 'GET',
        success: function(categories) {
            // 清空類別行
            $('#category-row').empty();

            // 生成類別的HTML
            categories.forEach(function(category, index) {
                var categoryHtml = `
                <div class="col-md-3">
                    <div class="category-item" data-category="${category}">
                        <img src="/image/${category}.png" alt="${category}" style="width: 50px; height: 50px;">
                        <p>${category}</p>
                    </div>
                </div>
                `;
                $('#category-row').append(categoryHtml);

                if ((index + 1) % 4 === 0) {
                    $('#category-row').append('<div class="w-100"></div>');
                }
            });

            // 綁定類別點擊事件
            $('#category-row').on('click', '.category-item', function() {
                var category = $(this).data('category');
                loadProducts(category);
            });
        },
        error: function(error) {
            console.log('Error fetching categories:', error);
        }
    });
}

function loadPromotionProducts() {
    $.ajax({
        url: '/promotionProducts',
        type: 'GET',
        success: function (products) {
            if (products.length === 0) {
                // 如果沒有促銷商品，隱藏促銷商品區塊
                $('#promotion-products-row').hide();
            } else {
                // 顯示並加載促銷商品
                $('#promotion-products-row').show().empty();
                let forEach = products.forEach(function (product) {
                    var productCard = `
                    <div class="col-md-3">
                        <div class="card mb-3 box-shadow">
                            <img class="card-img-top product-img" src="/productImage/${product.prodNo}" alt="${product.prodName}">
                            <div class="card-body">
                                <p class="card-text">${product.prodName}</p>
                                <div class="d-flex justify-content-between align-items-center">
                                    <div class="btn-group">
                                        <button type="button" class="btn btn-sm btn-outline-secondary view-details" data-prodno="${product.prodNo}">查看</button>
<!--                                        <button type="button" class="btn btn-sm btn-outline-secondary add-to-cart" data-id="${product.prodNo}" data-name="${product.prodName}" data-price="${product.prodPrice}">加入購物車</button>-->
                                    </div>
                                    <small class="text-muted"><s>$${product.prodPrice}</s></small>
                                    <small class="text-danger">$${product.promoPrice}</small> <!-- 顯示促銷價格 -->
                                </div>
                            </div>
                        </div>
                    </div>
                `;
                    $('#promotion-products-row').append(productCard);
                });
            }
        }
    })
}
