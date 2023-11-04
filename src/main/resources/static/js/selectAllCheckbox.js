// 獲取全選復選框元素並添加事件監聽器
document.addEventListener('DOMContentLoaded', function () {
    const selectAllCheckbox = document.getElementById("selectAll");
    selectAllCheckbox.addEventListener("click", function () {
        const checkboxes = document.querySelectorAll(".product-checkbox");
        checkboxes.forEach((checkbox) => {
            checkbox.checked = selectAllCheckbox.checked;
        });
    });
});
