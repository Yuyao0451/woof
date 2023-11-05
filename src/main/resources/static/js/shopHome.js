// 定義loadProducts函數，用於加載特定類別的商品
function loadProducts(category) {
    // 根據類別構建請求URL
    var url = category === 'all' ? '/products' : `/productsByCategory/${category}`;
    $.ajax({
        url: url,
        type: 'GET',
        success: function(products) {
            // 清空商品展示區域
            $('#products-row').empty();
            // 遍歷商品數據，並動態生成商品卡片
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
                                        <button type="button" class="btn btn-sm btn-outline-secondary">加入購物車</button>
                                    </div>
                                    <small class="text-muted">$${product.prodPrice}</small>
                                </div>
                            </div>
                        </div>
                    </div>
                `;
                // 將商品卡片插入到頁面中
                $('#products-row').append(productCard);
            });

            // 綁定查看詳情按鈕的點擊事件
            $('#products-row').on('click', '.view-details', function() {
                var prodNo = $(this).data('prodno');
                window.location.href = `/productDetail.html?prodNo=${prodNo}`; // 確保這個路徑是正確的
            });

        },
        error: function(error) {
            // 請求失敗時在控制台打印錯誤信息
            console.log('Error fetching products:', error);
        }
    });
}

$(document).ready(function() {
    // 當文檔加載完成後執行
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
});
