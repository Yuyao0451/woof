var isEditProductEventBound = false;

function bindEditProductModalEvents() {
    if (!isEditProductEventBound) {
        // 監聽修改商品按鈕的點擊事件
        document.getElementById('editProduct').addEventListener('click', function () {
            const selectedCheckboxes = document.querySelectorAll('.product-checkbox:checked');
            if (selectedCheckboxes.length === 1) {
                const prodNo = selectedCheckboxes[0].value;
                // console.log('Selected product number:', prodNo);

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

                        categorySelect.value = product.prodCatName;
                        statusSelect.value = product.prodStatus;

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
                alert('請選擇一個商品進行修改。');
            }
        });

        // 處理更新商品表單的提交
        document.body.addEventListener('click', function (e) {
            if (e.target && e.target.id === 'updateProduct') {
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
                            fetchProductsAndUpdateTable(() => {
                                // 計算修改後的商品應該出現在哪一頁
                                let newPage = calculatePageForProduct(data.prodNo);
                                // 更新當前頁碼
                                currentPage = newPage;
                                updateTable();
                                // 應用特效
                                highlightUpdatedRow(data.prodNo);
                            });
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
            }
        });
        isEditProductEventBound = true;
    }
}

function calculatePageForProduct(prodNo) {
    // 獲取所有商品行
    let allRows = document.querySelectorAll('.product-row');
    let index = Array.from(allRows).findIndex(row => row.getAttribute('data-prodno') == prodNo);
    // 計算該商品應該出現在哪一頁
    return Math.ceil((index + 1) / rowsPerPage);
}

function highlightUpdatedRow(prodNo) {
    setTimeout(() => {
        let updatedRow = document.querySelector(`tr[data-prodno='${prodNo}']`);
        if (updatedRow) {
            updatedRow.scrollIntoView({ behavior: 'smooth', block: 'center' });
            updatedRow.classList.add('highlighted-row');
        }
    }, 100); // 延時100毫秒
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
