document.addEventListener('DOMContentLoaded', function () {
    // 綁定新增商品模態框的事件
    function bindAddProductModalEvents() {
        // 當模態框打開時，加載商品類別
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

        // 處理新增產品表單的提交
        document.getElementById('saveProduct').addEventListener('click', function () {
            var form = document.getElementById('addProductForm');
            if (form.checkValidity()) {
                // 創建 FormData
                var formData = new FormData(form);

                // 添加參數到 FormData
                formData.set('prodCatName', document.getElementById('productCategory').value);
                formData.set('prodContent', document.getElementById('productDescription').value);
                formData.set('prodPrice', document.getElementById('productPrice').value);
                formData.set('prodName', document.getElementById('productName').value);

                var productStatusValue = document.getElementById('productStatus').value;
                formData.set('prodStatus', productStatusValue === '銷售中' ? '銷售中' : '下架中');

                // 添加商品照片
                var fileInput = document.getElementById('productImage');
                if (fileInput.files.length > 0) {
                    formData.set('prodPhoto', fileInput.files[0]);
                }

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
                    })
                    .catch(error => {
                        console.error('There has been a problem with your fetch operation:', error);
                    });

                // 隱藏模態框
                $('#addProductModal').modal('hide');
            } else {
                // 如果表單不合法，顯示錯誤信息
                form.classList.add('was-validated');
            }
        });
    }

    // 當新增產品按鈕被點擊時，顯示模態框並綁定事件
    document.getElementById('addProduct').addEventListener('click', function () {
        $('#addProductModal').modal('show');
        bindAddProductModalEvents(); // 綁定模態框事件的函數
    });

    // 加載新增商品模態框
    fetch('/addModal.html')
        .then(response => response.text())
        .then(html => {
            document.getElementById('addProductModalContainer').innerHTML = html;
        });
});
