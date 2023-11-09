// 綁定修改商品模態框的事件
function bindEditProductModalEvents() {
    // 監聽修改商品按鈕的點擊事件
    document.getElementById('editProduct').addEventListener('click', function () {
        const selectedCheckboxes = document.querySelectorAll('.product-checkbox:checked');
        if (selectedCheckboxes.length === 1) {
            const prodNo = selectedCheckboxes[0].value;
            console.log('Selected product number:', prodNo);

            // 同時加載商品類別和商品狀態
            Promise.all([
                fetch('/productCategories').then(response => response.json()),
                fetch('/productStatuses').then(response => response.json()),
                fetch(`/productById/${prodNo}`).then(response => response.json())
            ])
                .then(([categories, statuses, product]) => {
                    const categorySelect = document.getElementById('editProductCategory');
                    const statusSelect = document.getElementById('editProductStatus');

                    // 清空現有的選項
                    categorySelect.innerHTML = '';
                    statusSelect.innerHTML = '';

                    // 動態添加新的選項到下拉選單
                    categories.forEach(category => {
                        let option = new Option(category, category);
                        categorySelect.add(option);
                    });

                    // 動態添加狀態選項到下拉選單
                    statuses.forEach(status => {
                        let option = new Option(status, status);
                        statusSelect.add(option);
                    });

                    // 選擇與商品匹配的類別和狀態
                    categorySelect.value = product.prodCatName;
                    statusSelect.value = product.prodStatus;

                    // 填充其他表單字段
                    document.getElementById('editProductId').value = product.prodNo;
                    document.getElementById('editProductName').value = product.prodName;
                    document.getElementById('editProductPrice').value = product.prodPrice;
                    document.getElementById('editProductDescription').value = product.prodContent;

                    // 顯示模態框
                    $('#editProductModal').modal('show');
                })
                .catch(error => {
                    console.error('Error:', error);
                });
        } else {
            alert('請選擇一個商品進行編輯。');
        }
    });

    // 處理更新商品表單的提交
    document.getElementById('updateProduct').addEventListener('click', function () {
        var form = document.getElementById('editProductForm');
        if (form.checkValidity()) {
            var formData = new FormData(form);
            var prodNo = document.getElementById('editProductId').value;
            console.log('Updating product number:', prodNo);
            formData.set('prodCatName', document.getElementById('editProductCategory').value);
            formData.set('prodContent', document.getElementById('editProductDescription').value);
            formData.set('prodPrice', document.getElementById('editProductPrice').value);
            formData.set('prodName', document.getElementById('editProductName').value);
            formData.set('prodStatus', document.getElementById('editProductStatus').value);

            var productImageFile = document.getElementById('editProductImage').files[0];
            if (productImageFile) {
                formData.set('prodPhoto', productImageFile);
            }

            fetch(`/updateProduct/${prodNo}`, {
                method: 'PUT',
                body: formData
            })
                .then(response => {
                    if (response.ok) {
                        return response.json();
                    } else {
                        return response.json().then(error => Promise.reject(error));
                    }
                })
                .then(data => {
                    console.log('Product updated:', data);
                    $('#editProductModal').modal('hide');
                    // 更新商品列表
                    // updateProductList();
                })
                .catch(error => {
                    console.error('Error updating product:', error);
                });
        } else {
            form.classList.add('was-validated');
        }
    });
}

// 當文檔加載完成後，加載修改商品模態框並綁定事件
document.addEventListener('DOMContentLoaded', function () {
    fetch('/editModal.html')
        .then(response => response.text())
        .then(html => {
            document.getElementById('editProductModalContainer').innerHTML = html;
            bindEditProductModalEvents(); // 現在這裡調用函數以綁定事件
        });
});
