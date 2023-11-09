let pathName = window.document.location.pathname;
let projectName = pathName.substring(0, pathName.substring(1).indexOf("/") + 1);


$(function() {
    getCartTotalQuantity();
});

//自動重新刷新會載入總數
function getCartTotalQuantity() {
    let memNo = "member1"; // 這裡應該從session會話中獲取真實的會員編號
    $.ajax({
        type: "POST",
        url: `${projectName}/cart`,
        data: {
            action: "getTotalQuantity",
            memNo: memNo
        },
        success: function(response) {
            // 假設後端返回的 response 是一個物件，包含 totalQuantity 屬性
            $("#cart-count").text(response.totalQuantity);
        },
        error: function(xhr, status, error) {
            // 處理錯誤
            console.error("獲取購物車總數量時出錯", status, error);
        }
    });
}


// 添加商品到購物車的 AJAX 請求
// 動態泡沫事件
$(document).on("click",".add-to-cart", function() {

    let prodNo = $(this).data("id");
    let prodName = $(this).data("name");
    let prodPrice = $(this).data("price");

    console.log(prodNo);
    console.log(prodName);
    console.log(prodPrice);

    $.ajax({
        type: "POST",
        url: `${projectName}/cart`,
        data: {
            action: "add",
            prodNo: prodNo,
            prodName: prodName,
            prodPrice: prodPrice
        },
        success: function(data) {
            console.log("AJAX call made");
            console.log(data);

            // 更新前端jsp中的數字
            let totalQuantity = data.totalQuantity;
            $("#cart-count").text(totalQuantity);

        }

    });
});



// 購物車圖標點擊事件
$("#cart-icon, #cart-count").on("click", function() {
    // 假設用戶已經登入，並且會員編號已經存儲在會話中
    let memNo = "member1"; // 這裡應該從會話中獲取真實的會員編號

    $.ajax({
        type: "POST",
        url: `${projectName}/cartlist`,
        data: {
            action: "getCart",
            memNo: memNo
        },
        success: function(cartJson) {

            let html = "";
            let totalAmount = 0;

            // 跑購物車清單
            cartJson.forEach(item => {
                let itemTotal = item.quantity * item.prodPrice; // 計算每個項目的總金額
                totalAmount += itemTotal; // 計算購物車總金額

                html += `<tr>
                        <td>${item.prodNo}</td>
                        <td>${item.prodName}</td>
                        <td>${item.quantity}</td>
                        <td>NT$${itemTotal}</td>
                    </tr>`;
            });

            $("#cart-items-list").html(html);
            $("#cart-total-amount").text(`NT$${totalAmount}`);

            //	           doucument.getElementId("cart-items-list").innerHTML = html;
            // 顯示購物車模態框
            $('#cartModal').modal('show');
        }
    });
});


//結帳
$(document).ready(function() {

    $('#checkout').click(function() {

        console.log("11111");
        window.location.href = '<%=request.getContextPath()%>/checkout.jsp';
    });
});