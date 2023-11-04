// 分頁功能的變數
const rowsPerPage = 5; // 每頁顯示的行數
let currentPage = 1; // 當前頁碼

// 更新表格的函數
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

// 為分頁控制項添加事件監聽器
document.addEventListener('DOMContentLoaded', function () {
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

    // 初始化表格
    updateTable();
});

