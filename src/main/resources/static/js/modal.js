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
            // 在這裡添加代碼來處理表單數據，例如使用AJAX發送數據到服務器
            // ...

            // 隱藏模態框
            $('#addProductModal').modal('hide');
        } else {
            // 如果表單不合法，顯示錯誤信息
            form.classList.add('was-validated');
        }
    });
});
