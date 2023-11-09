document.addEventListener('DOMContentLoaded', function () {
    // 呼叫函數以加載最新的產品數據並更新表格
    fetchProductsAndUpdateTable();
});

function fetchProductsAndUpdateTable() {
    fetch('/products')
        .then(response => response.json())
        .then(products => {
            const tableBody = document.getElementById('productTableBody');
            tableBody.innerHTML = ''; // 清空現有的表格內容
            products.forEach(product => {
                const row = createProductRow(product);
                tableBody.appendChild(row);
            });
            // 數據更新後重新計算分頁
            updateTable();
        });
}

function createProductRow(product) {
    const row = document.createElement('tr');
    row.classList.add('product-row');
    row.innerHTML = `
        <td><input type="checkbox" value="${product.prodNo}" class="product-checkbox"></td>
        <td>${product.prodNo}</td>
        <td><img src="/productImage/${product.prodNo}" alt="Product Photo" style="width: 100px; height: 100px;"></td>
        <td>${product.prodCatName}</td>
        <td>${product.prodName}</td>
        <td>${product.prodContent}</td>
        <td>${product.prodPrice}</td>
        <td class="${product.prodStatus === '銷售中' ? 'text-success' : 'text-danger'}">${product.prodStatus}</td>
    `;
    return row;
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
        if (prodNoCell && prodNoCell.textContent == productDto.prodNo) {
            // 獲取狀態單元格
            let statusCell = row.querySelector('td:nth-child(8)');
            if (statusCell) {
                // 更新狀態單元格的文本內容
                statusCell.textContent = productDto.prodStatus;
                // 根據產品狀態更新單元格的類別，以改變顯示顏色
                statusCell.className = productDto.prodStatus === '銷售中' ? 'text-success' : 'text-danger';
            }
        }
    });
}