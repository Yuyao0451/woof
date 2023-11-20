function loadProducts(category, page = 1) {
    var url = category === 'all' ? '/products' : `/productsByCategory/${category}`;
    $.ajax({
        url: url,
        type: 'GET',
        success: function(products) {
            // 清空商品展示區域
            $('#products-row').empty();
            var filteredProducts = products.filter(function(product) {
                return (!product.promotion || product.promotion === false) && product.prodStatus === '銷售中';
            });
            // 分頁邏輯
            var productsPerPage = 8;
            var startIndex = (page - 1) * productsPerPage;
            var endIndex = startIndex + productsPerPage;
            var paginatedProducts = filteredProducts.slice(startIndex, endIndex);

            paginatedProducts.forEach(function(product){
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
            });

            updatePaginationControls(filteredProducts.length, productsPerPage, page);
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

function updatePaginationControls(totalProducts, productsPerPage, currentPage) {
    var totalPages = Math.ceil(totalProducts / productsPerPage);
    $('#pagination').empty();

    // 上一頁按鈕
    var prevPage = currentPage > 1 ? currentPage - 1 : 1;
    $('#pagination').append(`<li class="page-item ${currentPage === 1 ? 'disabled' : ''}"><a class="page-link" href="javascript:void(0);" onclick="loadProducts('all', ${prevPage})">上一頁</a></li>`);

    // 頁碼
    for (var i = 1; i <= totalPages; i++) {
        $('#pagination').append(`<li class="page-item ${i === currentPage ? 'active' : ''}"><a class="page-link" href="javascript:void(0);" onclick="loadProducts('all', ${i})">${i}</a></li>`);
    }

    // 下一頁按鈕
    var nextPage = currentPage < totalPages ? currentPage + 1 : totalPages;
    $('#pagination').append(`<li class="page-item ${currentPage === totalPages ? 'disabled' : ''}"><a class="page-link" href="javascript:void(0);" onclick="loadProducts('all', ${nextPage})">下一頁</a></li>`);
}

$(document).ready(function() {
    // 加載類別
    loadCategories();

    // 頁面加載時預設加載所有商品
    loadProducts('all');
    // 加載促銷商品
    loadPromotionProducts();
    $(document).on('click', '.view-details', function() {
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
            $('#promotion-products-row').empty();
            if (products.length > 0) {
                for (let i = 0; i < products.length; i += 4) {
                    var isActive = i === 0 ? 'active' : '';
                    var productCards = products.slice(i, i + 4).map(function(product) {
                        return `
                            <div class="col-md-3">
                                <div class="card mb-3 box-shadow">
                                    <img class="card-img-top product-img" src="/productImage/${product.prodNo}" alt="${product.prodName}">
                                    <div class="card-body">
                                        <p class="card-text">${product.prodName}</p>
                                        <div class="d-flex justify-content-between align-items-center">
                                            <div class="btn-group">
                                                <button type="button" class="btn btn-sm btn-outline-secondary view-details" data-prodno="${product.prodNo}">查看</button>
                                            </div>
                                            <small class="text-muted"><s>$${product.prodPrice}</s></small>
                                            <small class="text-danger">$${product.promoPrice}</small>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        `;
                    }).join('');

                    var carouselItem = `
                        <div class="carousel-item ${isActive}">
                            <div class="row">${productCards}</div>
                        </div>
                    `;
                    $('#promotion-products-row').append(carouselItem);
                }
            }
        }
    })
}

