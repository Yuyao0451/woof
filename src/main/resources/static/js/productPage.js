// 當文檔加載完成後，執行內部的函數
document.addEventListener('DOMContentLoaded', function () {
    // 呼叫函數以加載最新的產品數據並更新表格
    fetchProductsAndUpdateTable();
});

// 定義從後端API獲取產品數據並更新表格的函數
function fetchProductsAndUpdateTable() {
    // 使用fetch API從'/products'路徑獲取產品數據
    fetch('/products')
        .then(response => response.json()) // 將響應轉換為JSON
        .then(products => {
            // 遍歷產品數據，為每個產品更新表格行
            products.forEach(product => {
                updateProductRow(product);
            });
            // 數據更新後重新計算分頁
            updateTable();
        });
}

// 定義更新產品行的函數
function updateProductRow(productDto) {
    // 輸出日誌，顯示正在更新的產品資訊
    console.log("Updating row for product:", productDto);
    // 獲取所有的產品行元素
    let rows = document.querySelectorAll('.product-row');
    // 遍歷每一行，尋找與產品編號匹配的行
    rows.forEach(row => {
        // 獲取產品編號單元格
        let prodNoCell = row.querySelector('td:nth-child(2)');
        // 如果該單元格存在且內容與產品編號匹配
        if (prodNoCell && prodNoCell.textContent == productDto.prodNo) {
            // 獲取狀態單元格
            let statusCell = row.querySelector('td:nth-child(8)');
            // 如果狀態單元格存在
            if (statusCell) {
                // 更新狀態單元格的文本內容
                statusCell.textContent = productDto.prodStatus;
                // 根據產品狀態更新單元格的類別，以改變顯示顏色
                statusCell.className = productDto.prodStatus === '銷售中' ? 'text-success' : 'text-danger';
            }
        }
    });
}
