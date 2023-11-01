document.addEventListener("DOMContentLoaded", function() {
    // 獲取全選復選框元素
    const selectAllCheckbox = document.getElementById("selectAll");

    // 為全選復選框添加點擊事件
    selectAllCheckbox.addEventListener("click", function() {
        // 獲取所有商品的復選框元素
        const checkboxes = document.querySelectorAll(".product-checkbox");

        // 根據全選復選框的狀態來設置所有商品復選框的狀態
        checkboxes.forEach((checkbox) => {
            checkbox.checked = selectAllCheckbox.checked;
        });
    });
});

document.addEventListener('DOMContentLoaded', function() {
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
    document.getElementById('prevPage').addEventListener('click', function() {
        if (currentPage > 1) {
            currentPage--;
            updateTable();
        }
    });

    document.getElementById('nextPage').addEventListener('click', function() {
        const tableRows = document.querySelectorAll('.product-row');
        const totalPages = Math.ceil(tableRows.length / rowsPerPage);

        if (currentPage < totalPages) {
            currentPage++;
            updateTable();
        }
    });
});




