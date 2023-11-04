document.addEventListener('DOMContentLoaded', function () {
    document.getElementById("activateProduct").addEventListener("click", function () {
        toggleProductStatus(1); // 上架的狀態代碼
    });

    document.getElementById("deactivateProduct").addEventListener("click", function () {
        toggleProductStatus(0); // 下架的狀態代碼
    });
});

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
