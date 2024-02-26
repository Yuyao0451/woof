$(document).ready(function () {
    loadPromotionActivities();

    $('#addPromotionBtn').on('click', function () {
        prepareModalForCreate();
    });

    $('#promotionModal .btn-primary').on('click', function () {
        var isValid = validateForm();
        if (isValid) {
            var promotionActivity = {
                paName: $('#paName').val(),
                paDiscount: $('#paDiscount').val(),
                paContent: $('#paContent').val(),
                paStart: $('#paStart').val(),
                paEnd: $('#paEnd').val(),
                paStatus: $('#paStatusSelect').val() === 'true'
            };

            var id = $('#promotionModal').data('id');
            if (id) {
                updatePromotionActivity(id, promotionActivity);
            } else {
                createPromotionActivity(promotionActivity);
            }
        }
    });

    let totalPage = 0;
    let currentPage = 0;
    const pageSize = 10;

    $('.pagination .page-link').on('click', function (e) {
        e.preventDefault();
        const action = $(this).text().trim();

        if (action === '上一頁' && currentPage > 0) {
            currentPage--;
            loadPromotionActivities(currentPage, pageSize);
        } else if (action === '下一頁' && currentPage < totalPage - 1) {
            currentPage++;
            loadPromotionActivities(currentPage, pageSize);
        }
    });

    function loadPromotionActivities(page = 0, size = 10) {
        $.get(`/promotion?page=${page}&size=${size}`, function (response) {
            totalPage = response.totalPages;
            $('#promotionActivitiesTable tbody').empty();
            if (response && response.content) {
                response.content.forEach(function (activity) {
                    var statusClass = activity.paStatus ? 'status-on' : 'status-off';
                    var disabled = activity.paStatus ? '' : 'disabled="disabled"';//新增未上架活動無法開啟選擇商品
                    var row = `<tr>
                <td>${activity.paNo}</td>
                <td>${activity.paName}</td>
                <td>${activity.paDiscount}</td>
                <td>${activity.paContent}</td>
                <td>${formatDateTime(activity.paStart)}</td>
                <td>${formatDateTime(activity.paEnd)}</td>
                <td class="${statusClass}">${activity.paStatus ? '上架' : '下架'}</td>
                <td><button class="btn btn-secondary editBtn" data-id="${activity.paNo}">編輯</button></td>
                <td><button class="btn btn-secondary selectProductsBtn" data-id="${activity.paNo}" ${disabled}>選擇商品</button></td>
            </tr>`;
                    $('#promotionActivitiesTable tbody').append(row);
                });

                $('.editBtn').on('click', function () {
                    var id = $(this).data('id');
                    loadPromotionActivityForEdit(id);
                });
            }
        });
    }


    function loadPromotionActivityForEdit(id) {
        $.get('/promotion/' + id, function (activity) {
            $('#paName').val(activity.paName);
            $('#paDiscount').val(activity.paDiscount);
            $('#paContent').val(activity.paContent);
            $('#paStart').val(formatDateTime(activity.paStart));
            $('#paEnd').val(formatDateTime(activity.paEnd));
            $('#paStatusSelect').val(activity.paStatus ? 'true' : 'false');

            $('#promotionModal').data('id', id);
            $('#promotionModalLabel').text('編輯促銷活動');
            $('#promotionModal').modal('show');
        });
    }

    function prepareModalForCreate() {
        $('#promotionModal').data('id', null);
        $('#promotionModalLabel').text('新增促銷活動');
        clearModalFields();
        clearErrorMessages();
        $('#promotionModal').modal('show');
    }

    function clearErrorMessages() {
        $('.error-message').text(''); // 清空所有錯誤訊息
    }

    function updatePromotionActivity(id, activity) {
        $.ajax({
            url: '/promotion/' + id,
            method: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify(activity),
            success: function (response) {
                $('#promotionModal').modal('hide');
                loadPromotionActivities();
                if (!activity.paStatus) { // 如果活動狀態為下架
                    resetPromotionProducts(id);
                }
                Swal.fire({
                    icon: "success",
                    title: "促銷活動已更新",
                });
            }
        });
    }

    function resetPromotionProducts(promoId) {
        $.ajax({
            url: '/resetPromoId',
            method: 'PUT',
            success: function () {
                console.log('Promotion products reset for promoId: ' + promoId);
            },
            error: function (xhr, status, error) {
                console.log('Error resetting promotion products: ' + error);
            }
        });
    }


    function createPromotionActivity(activity) {
        $.ajax({
            url: '/promotion',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(activity),
            success: function (response) {
                $('#promotionModal').modal('hide');
                loadPromotionActivities();
                Swal.fire({
                    icon: "success",
                    title: "成功新增促銷活動",
                });
            }
        });
    }

    function clearModalFields() {
        $('#paName').val('');
        $('#paDiscount').val('');
        $('#paContent').val('');
        $('#paStart').val('');
        $('#paEnd').val('');
        $('#paStatusSelect').val('true');
        $('.error-message').text(''); // 清空所有錯誤訊息
    }

    function formatDateTime(timestamp) {
        var date = new Date(timestamp);
        var year = date.getFullYear();
        var month = (date.getMonth() + 1).toString().padStart(2, '0');
        var day = date.getDate().toString().padStart(2, '0');
        var hours = date.getHours().toString().padStart(2, '0');
        var minutes = date.getMinutes().toString().padStart(2, '0');
        var seconds = date.getSeconds().toString().padStart(2, '0');
        return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
    }

    function validateField(inputId, errorMessage, checkEmpty = true, isNumber = false) {
        var inputElement = $('#' + inputId);
        var value = inputElement.val();
        var errorDiv = $('#error-' + inputId);

        if (errorDiv.length === 0) {
            inputElement.after('<div id="error-' + inputId + '" class="text-danger"></div>');
            errorDiv = $('#error-' + inputId);
        }

        // 對於數字欄位，首先檢查是否為 null 或空字串
        if (isNumber && (value === null || value.trim() === '')) {
            errorDiv.text(errorMessage);
            return false;
        }

        if (checkEmpty && !value.trim()) {
            errorDiv.text(errorMessage);
            return false;
        }

        errorDiv.text('');  // 清除錯誤訊息
        return true;
    }


    function validateForm() {
        var isValid = true;

        isValid &= validateField('paName', '請輸入活動名稱', true);
        isValid &= validateField('paDiscount', '請輸入折扣', true, true);
        isValid &= validateField('paContent', '請輸入活動內容', true);
        isValid &= validateField('paStart', '請輸入開始日期', true);
        isValid &= validateField('paEnd', '請輸入結束日期', true);

        return isValid;
    }

    let productTotalPage = 0;
    let productCurrentPage = 0;
    const productPageSize = 5;

    // 加載商品列表
    function loadProducts(page = 0, promoId) {
        $.get(`/productsPaged?page=${page}&size=${productPageSize}`, function (response) {
            productTotalPage = response.totalPages;
            productCurrentPage = response.number;

            var productsTableBody = $('#selectProductsModal #productsTableBody');
            productsTableBody.empty();

            response.content.forEach(function (product) {
                var isChecked = product.promoId === promoId;
                var productRow = `
                <tr>
                    <td><input type="checkbox" class="form-check-input product-checkbox" value="${product.prodNo}" ${isChecked ? 'checked' : ''} id="product-${product.prodNo}"></td>
                    <td>${product.prodNo}</td>
                    <td><img src="https://cha103-09.s3.ap-northeast-1.amazonaws.com/productImage/${product.prodPhoto}" 
                    onerror="this.onerror=null; this.src='/image/未有照片之圖片.png';"
                    alt="Product Photo" style="width: 100px; height: 100px;"></td>
                    <td>${product.prodName}</td>
                    <td class="${product.prodStatus === '銷售中' ? 'text-success' : 'text-danger'}">${product.prodStatus}</td>
                </tr>
            `;
                productsTableBody.append(productRow);
            });

            updatePaginationControls(productTotalPage, productCurrentPage);
        });
    }

    function updatePaginationControls(totalPages, currentPage) {
        var paginationContainer = $('#productPagination');
        paginationContainer.empty();

        // 添加「上一頁」按鈕
        paginationContainer.append(`<li class="page-item ${currentPage === 0 ? 'disabled' : ''}"><a class="page-link" href="#" data-page="${currentPage - 1}">上一頁</a></li>`);

        // 根據頁數添加按鈕
        for (let i = 0; i < totalPages; i++) {
            paginationContainer.append(`<li class="page-item ${i === currentPage ? 'active' : ''}"><a class="page-link" href="#" data-page="${i}">${i + 1}</a></li>`);
        }

        // 添加「下一頁」按鈕
        paginationContainer.append(`<li class="page-item ${currentPage === totalPages - 1 ? 'disabled' : ''}"><a class="page-link" href="#" data-page="${currentPage + 1}">下一頁</a></li>`);
    }


    //分頁按鈕處理
    $('#productPagination').on('click', '.page-link', function (e) {
        e.preventDefault();
        var newPage = $(this).data('page');
        var promoId = $('#selectProductsModal').data('promoId'); // 獲取當前促銷活動的 promoId
        loadProducts(newPage, promoId);
    });

    // 更新商品的促銷活動ID
    function updateProductPromoId(prodNo, promoId) {
        var data = {
        promoId: promoId,
        updatePromoId: true
    };
        $.ajax({
            url: '/updateProduct/' + prodNo,
            method: 'PUT',
            contentType: 'application/x-www-form-urlencoded',
            data: $.param(data),
            success: function (response) {
                console.log('Product updated', response);
                // 如果 promoId 為 null，則不需要重新加載整個商品列表
                // 只有在添加商品到促銷活動時才重新加載
                if (promoId !== null) {
                    var currentPage = $('#selectProductsModal').data('currentPage') || 0;
                    loadProducts(currentPage, $('#selectProductsModal').data('promoId'));
                }
            },
            error: function (xhr, status, error) {
                console.log('Update error', status, error);
            }
        });
    }

    let selectedProducts = {}; // 儲存商品選擇狀態
    // 當用戶勾選或取消勾選商品時的事件處理
    $('#selectProductsModal #productsTableBody').on('change', '.product-checkbox', function () {
        var prodNo = $(this).val();
        var isChecked = $(this).is(':checked');
        selectedProducts[prodNo] = isChecked;
    });

    $('#promotionActivitiesTable').on('click', '.selectProductsBtn', function () {
        var promoId = $(this).data('id');

        $('#selectProductsModal').data('promoId', promoId);
        $('#selectProductsModal').modal('show');
    });

    // 當用戶提交選擇的商品時的處理函數
    $('#saveSelectedProducts').on('click', function () {
        var promoId = $('#selectProductsModal').data('promoId');
        for (var prodNo in selectedProducts) {
            if (selectedProducts.hasOwnProperty(prodNo)) {
                updateProductPromoId(prodNo, selectedProducts[prodNo] ? promoId : null);
            }
        }
        $('#selectProductsModal').modal('hide');
        Swal.fire({
            icon: "success",
            title: "商品選擇已保存",
        });
        selectedProducts = {}; // 重置 selectedProducts
    });



    // 在打開模態框時加載商品列表
    $('#selectProductsModal').on('show.bs.modal', function () {
        var promoId = $(this).data('promoId'); // 獲取當前促銷活動的 promoId
        loadProducts(0, promoId); // 加載第一頁
    });

});


