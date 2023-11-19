// 定義全域變數
const rowsPerPage = 5; // 每頁顯示的行數
let currentPage = 1; // 當前頁碼

document.addEventListener('DOMContentLoaded', function () {
    // 初始化產品數據表格
    fetchProductsAndUpdateTable();

    // 為上架和下架按鈕添加事件監聽器
    document.getElementById("activateProduct").addEventListener("click", function () {
        toggleProductStatus(1); // 上架的狀態代碼
    });

    document.getElementById("deactivateProduct").addEventListener("click", function () {
        toggleProductStatus(0); // 下架的狀態代碼
    });

    // 為全選復選框添加事件監聽器
    const selectAllCheckbox = document.getElementById("selectAll");
    selectAllCheckbox.addEventListener("click", function () {
        const checkboxes = document.querySelectorAll(".product-checkbox");
        checkboxes.forEach((checkbox) => {
            checkbox.checked = selectAllCheckbox.checked;
        });
    });

    // 為分頁控制項添加事件監聽器
    document.getElementById('prevPage').addEventListener('click', function () {
        if (currentPage > 1) {
            currentPage--;
            updateTable();
        }
    });

    document.getElementById('nextPage').addEventListener('click', function () {
        const totalPages = Math.ceil(document.querySelectorAll('.product-row').length / rowsPerPage);
        if (currentPage < totalPages) {
            currentPage++;
            updateTable();
        }
    });
});

function fetchProductsAndUpdateTable(callback) {
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
            if(callback) {
                callback();
            }
        });
}

function createProductRow(product) {
    const row = document.createElement('tr');
    row.classList.add('product-row');
    row.setAttribute('data-prodno', product.prodNo);
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

function updateProductRow(productDto) {
    console.log("Updating row for product:", productDto);
    let rows = document.querySelectorAll('.product-row');
    rows.forEach(row => {
        let prodNoCell = row.querySelector('td:nth-child(2)');
        if (prodNoCell && prodNoCell.textContent == productDto.prodNo) {
            let statusCell = row.querySelector('td:nth-child(8)');
            if (statusCell) {
                statusCell.textContent = productDto.prodStatus;
                statusCell.className = productDto.prodStatus === '銷售中' ? 'text-success' : 'text-danger';
            }
        }
    });
}

function highlightNewRow(prodNo) {
    // 滾動到新行（如果在當前頁）
    let newRow = document.querySelector(`tr[data-prodno='${prodNo}']`);
    if (newRow) {
        newRow.scrollIntoView({ behavior: 'smooth', block: 'center' });
        newRow.classList.add('highlighted-row');
    }
}

function toggleProductStatus(status) {
    let selectedProdNos = getSelectedProdNos();
    selectedProdNos.forEach(prodNo => {
        fetch(`/toggleStatus/${prodNo}`, {
            method: 'PUT'
        }).then(response => response.json())
            .then(data => {
                console.log("Received product status:", data.prodStatus);
                updateProductRow(data);
            });
    });
}

function getSelectedProdNos() {
    let selectedProdNos = [];
    let checkboxes = document.querySelectorAll('.product-checkbox');
    checkboxes.forEach(checkbox => {
        if (checkbox.checked) {
            selectedProdNos.push(checkbox.value);
        }
    });
    return selectedProdNos;
}

function updateTable() {
    const tableRows = document.querySelectorAll('.product-row');
    const totalPages = Math.ceil(tableRows.length / rowsPerPage);
    tableRows.forEach(row => row.style.display = 'none');
    for (let i = (currentPage - 1) * rowsPerPage; i < currentPage * rowsPerPage; i++) {
        if (tableRows[i]) {
            tableRows[i].style.display = '';
        }
    }
    document.getElementById('currentPage').textContent = currentPage;
    document.getElementById('totalPages').textContent = totalPages;
}
