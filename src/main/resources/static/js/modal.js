// 當DOM加載完成後處理模態框相關的功能
document.addEventListener('DOMContentLoaded', function () {
    // 當新增產品按鈕被點擊時，顯示模態框
    document.getElementById('addProduct').addEventListener('click', function () {
        $('#addProductModal').modal('show');
    });

    // 當模態框打開時，加載商品類別
    document.getElementById('addProduct').addEventListener('click', function () {
        fetch('/productCategories')
            .then(response => response.json())
            .then(categories => {
                const categorySelect = document.getElementById('productCategory');
                // 清空現有的選項
                categorySelect.innerHTML = '';
                // 動態添加新的選項到下拉選單
                categories.forEach(category => {
                    let option = new Option(category, category);
                    categorySelect.add(option);
                });
            })
            .catch(error => {
                console.error('無法加載商品類別', error);
            });
    });

    // 處理新增產品表單的提交
    document.getElementById('saveProduct').addEventListener('click', function () {
        var form = document.getElementById('addProductForm');
        if (form.checkValidity()) {
            // 創建 FormData 對象
            var formData = new FormData(form);

            // 添加參數到 FormData 對象
            formData.set('prodCatName', document.getElementById('productCategory').value);
            formData.set('prodContent', document.getElementById('productDescription').value);
            formData.set('prodPrice', document.getElementById('productPrice').value);
            formData.set('prodName', document.getElementById('productName').value);

            // 根據選擇的上下架狀態設置 prodStatus
            var productStatusValue = document.getElementById('productStatus').value;
            formData.set('prodStatus', productStatusValue === '銷售中' ? '銷售中' : '下架中');

            // 添加商品照片
            var productImageFile = document.getElementById('productImage').files[0];
            formData.set('prodPhoto', productImageFile);

            // 確保文件上傳的名稱與後端預期的名稱匹配
            var fileInput = document.getElementById('productImage');
            if (fileInput.files.length > 0) {
                formData.set('prodPhoto', fileInput.files[0]);
            }

            // 使用 fetch API 發送數據到服務器
            fetch('/addProduct', {
                method: 'POST',
                body: formData
            })
                .then(response => {
                    if (response.ok) {
                        return response.json();
                    } else {
                        // 如果服務器響應不是 ok，拋出錯誤
                        return response.json().then(error => Promise.reject(error));
                    }
                })
                .then(data => {
                    console.log('Product added:', data);
                    // 處理成功響應，例如更新 UI 或通知用戶
                    // 這裡可以添加代碼來更新 UI 或通知用戶
                })
                .catch(error => {
                    console.error('There has been a problem with your fetch operation:', error);
                    // 處理錯誤情況，這裡可以添加代碼來通知用戶
                });

            // 隱藏模態框
            $('#addProductModal').modal('hide');
        } else {
            // 如果表單不合法，顯示錯誤信息
            form.classList.add('was-validated');
        }
    });


});
