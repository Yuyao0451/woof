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
                    <div class="col-md-4">
                        <div class="card mb-3 box-shadow">
                            <img class="card-img-top product-img" src="/productImage/${product.prodNo}" alt="${product.prodName}">
                            <div class="card-body">
                                <p class="card-text">${product.prodName}</p>
                                <div class="d-flex justify-content-between align-items-center">
                                    <div class="btn-group">
                                        <button type="button" class="btn btn-sm btn-outline-secondary view-details" data-prodno="${product.prodNo}">查看</button>
                                        <button type="button" class="btn btn-sm btn-outline-secondary add-to-cart" data-id="${product.prodNo}" data-name="${product.prodName}" data-price="${product.prodPrice}">加入購物車</button>
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

function loadPromotionProducts() {
    $.ajax({
        url: '/promotionProducts',
        type: 'GET',
        success: function(products) {
            $('#promotion-products-row').empty(); // 清空促銷商品展示區域
            products.forEach(function(product) {
                var productCard = `
                    <div class="col-md-4">
                        <div class="card mb-3 box-shadow">
                            <img class="card-img-top product-img" src="/productImage/${product.prodNo}" alt="${product.prodName}">
                            <div class="card-body">
                                <p class="card-text">${product.prodName}</p>
                                <div class="d-flex justify-content-between align-items-center">
                                    <div class="btn-group">
                                        <button type="button" class="btn btn-sm btn-outline-secondary view-details" data-prodno="${product.prodNo}">查看</button>
                                        <button type="button" class="btn btn-sm btn-outline-secondary add-to-cart" data-id="${product.prodNo}" data-name="${product.prodName}" data-price="${product.prodPrice}">加入購物車</button>
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
        },
        error: function(error) {
            console.log('Error fetching promotion products:', error);
        }
    });
}

$(document).ready(function() {
    // 發送請求加載商品類別
    $.ajax({
        url: '/productCategories',
        type: 'GET',
        success: function(categories) {
            // 清空側邊欄並添加“全部”選項作為第一個選項
            $('#category-list').empty().append('<a href="#" class="list-group-item list-group-item-action active" data-category="all">全部</a>');

            // 遍歷並添加從伺服器加載的其他類別
            categories.forEach(function(category) {
                // 排除英文的“all”
                if (category.toLowerCase() !== 'all') {
                    $('#category-list').append(`<a href="#" class="list-group-item list-group-item-action" data-category="${category}">${category}</a>`);
                }
            });
        },
        error: function(error) {
            // 請求失敗時在控制台打印錯誤信息
            console.log('Error fetching categories:', error);
        }
    });

    // 為側邊欄中的每個類別項目添加點擊事件處理器
    $('#category-list').on('click', '.list-group-item', function(e) {
        e.preventDefault();
        // 獲取被點擊的類別
        var category = $(this).data('category');
        // 移除其他項目的active類別，並為當前項目添加active類別
        $('.list-group-item').removeClass('active');
        $(this).addClass('active');
        // 加載並顯示選定類別的商品
        loadProducts(category);
    });

    // 頁面加載時預設加載所有商品
    loadProducts('all');
    // 加載促銷商品
    loadPromotionProducts();
});
