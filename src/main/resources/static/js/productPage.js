document.addEventListener("DOMContentLoaded", function () {
    // 獲取全選復選框元素
    const selectAllCheckbox = document.getElementById("selectAll");

    // 為全選復選框添加點擊事件
    selectAllCheckbox.addEventListener("click", function () {
        // 獲取所有商品的復選框元素
        const checkboxes = document.querySelectorAll(".product-checkbox");

        // 根據全選復選框的狀態來設置所有商品復選框的狀態
        checkboxes.forEach((checkbox) => {
            checkbox.checked = selectAllCheckbox.checked;
        });
    });
});

document.addEventListener('DOMContentLoaded', function () {
    const rowsPerPage = 5; // 每頁顯示的行數
    let currentPage = 1; // 當前頁碼

    function updateTable() {
        const tableRows = document.querySelectorAll('.product-row');
        const totalPages = Math.ceil(tableRows.length / rowsPerPage);

        // 隱藏所有行
        tableRows.forEach(row => row.style.display = 'none');

        // 只顯示當前頁的行
        for (let i = (currentPage - 1) * rowsPerPage; i < currentPage * rowsPerPage; i++) {
            if (tableRows[i]) {
                tableRows[i].style.display = '';
            }
        }

        // 更新分頁控制項
        document.getElementById('currentPage').textContent = currentPage;
        document.getElementById('totalPages').textContent = totalPages;
    }

    // 初始化表格
    updateTable();

    // 為分頁控制項添加事件監聽器
    document.getElementById('prevPage').addEventListener('click', function () {
        if (currentPage > 1) {
            currentPage--;
            updateTable();
        }
    });

    document.getElementById('nextPage').addEventListener('click', function () {
        const tableRows = document.querySelectorAll('.product-row');
        const totalPages = Math.ceil(tableRows.length / rowsPerPage);

        if (currentPage < totalPages) {
            currentPage++;
            updateTable();
        }
    });
});

document.getElementById("activateProduct").addEventListener("click", function () {
    toggleProductStatus(1); // 上架的狀態代碼
});

document.getElementById("deactivateProduct").addEventListener("click", function () {
    toggleProductStatus(0); // 下架的狀態代碼
});

    function toggleProductStatus(status) {
        // 獲取選中的商品ID
        let selectedProdNos = getSelectedProdNos();
        // 發送AJAX請求到後端
        selectedProdNos.forEach(prodNo => {
            fetch(`/toggleStatus/${prodNo}`, {
                method: 'PUT'
            }).then(response => response.json())
                .then(data => {
                    console.log("Received product status:", data.prodStatus);
                    // 更新前端界面
                    updateProductRow(data);
                });
        });
        console.log("toggleProductStatus called with status:", status);

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

    function updateProductRow(productDto) {
    console.log("Updating row for product:", productDto);
    let rows = document.querySelectorAll('.product-row');
    rows.forEach(row => {
        let prodNoCell = row.querySelector('td:nth-child(2)');
        if (prodNoCell && prodNoCell.textContent == productDto.prodNo) {
            let statusCell = row.querySelector('td:nth-child(7)');//td元素
            if (statusCell) {
                statusCell.textContent = productDto.prodStatus;  // 直接使用 prodStatus 字符串
                statusCell.className = productDto.prodStatus === '銷售中' ? 'text-success' : 'text-red';  // 根據 prodStatus 字符串更新
            }
        }
    });

}


