$(document).ready(function () {
    loadPromotionActivities();

    $('#addPromotionBtn').on('click', function () {
        prepareModalForCreate();
    });

    $('#promotionModal .btn-primary').on('click', function () {
        var isValid = validateForm();
        if (isValid) {
            var promotionActivity = {
                paName: $('#paName').val(),
                paDiscount: $('#paDiscount').val(),
                paContent: $('#paContent').val(),
                paStart: $('#paStart').val(),
                paEnd: $('#paEnd').val(),
                paStatus: $('#paStatusSelect').val() === 'true'
            };

            var id = $('#promotionModal').data('id');
            if (id) {
                updatePromotionActivity(id, promotionActivity);
            } else {
                createPromotionActivity(promotionActivity);
            }
        }
    });

    let totalPage = 0;
    let currentPage = 0;
    const pageSize = 10;

    $('.pagination .page-link').on('click', function (e) {
        e.preventDefault();
        const action = $(this).text().trim();

        if (action === '上一頁' && currentPage > 0) {
            currentPage--;
            loadPromotionActivities(currentPage, pageSize);
        } else if (action === '下一頁' && currentPage < totalPage - 1) {
            currentPage++;
            loadPromotionActivities(currentPage, pageSize);
        }
    });
    function loadPromotionActivities(page = 0, size = 10) {
        $.get(`/promotion?page=${page}&size=${size}`, function (response) {
            totalPage = response.totalPages;
            $('#promotionActivitiesTable tbody').empty();
            if (response && response.content) {
                response.content.forEach(function (activity) {
                    var statusClass = activity.paStatus ? 'status-on' : 'status-off';
                    var row = `<tr>
                <td>${activity.paNo}</td>
                <td>${activity.paName}</td>
                <td>${activity.paDiscount}</td>
                <td>${activity.paContent}</td>
                <td>${formatDateTime(activity.paStart)}</td>
                <td>${formatDateTime(activity.paEnd)}</td>
                <td class="${statusClass}">${activity.paStatus ? '上架' : '下架'}</td>
                <td><button class="btn btn-secondary editBtn" data-id="${activity.paNo}">編輯</button></td>
            </tr>`;
                    $('#promotionActivitiesTable tbody').append(row);
                });

                $('.editBtn').on('click', function () {
                    var id = $(this).data('id');
                    loadPromotionActivityForEdit(id);
                });
            }
        });
    }


    function loadPromotionActivityForEdit(id) {
        $.get('/promotion/' + id, function (activity) {
            $('#paName').val(activity.paName);
            $('#paDiscount').val(activity.paDiscount);
            $('#paContent').val(activity.paContent);
            $('#paStart').val(formatDateTime(activity.paStart));
            $('#paEnd').val(formatDateTime(activity.paEnd));
            $('#paStatusSelect').val(activity.paStatus ? 'true' : 'false');

            $('#promotionModal').data('id', id);
            $('#promotionModalLabel').text('編輯促銷活動');
            $('#promotionModal').modal('show');
        });
    }

    function prepareModalForCreate() {
        $('#promotionModal').data('id', null);
        $('#promotionModalLabel').text('新增促銷活動');
        clearModalFields();
        clearErrorMessages();
        $('#promotionModal').modal('show');
    }

    function clearErrorMessages() {
        $('.error-message').text(''); // 清空所有錯誤訊息
    }

    function updatePromotionActivity(id, activity) {
        $.ajax({
            url: '/promotion/' + id,
            method: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify(activity),
            success: function (response) {
                $('#promotionModal').modal('hide');
                loadPromotionActivities();
            }
        });
    }

    function createPromotionActivity(activity) {
        $.ajax({
            url: '/promotion',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(activity),
            success: function (response) {
                $('#promotionModal').modal('hide');
                loadPromotionActivities();
            }
        });
    }

    function clearModalFields() {
        $('#paName').val('');
        $('#paDiscount').val('');
        $('#paContent').val('');
        $('#paStart').val('');
        $('#paEnd').val('');
        $('#paStatusSelect').val('true');
        $('.error-message').text(''); // 清空所有錯誤訊息
    }

    function formatDateTime(timestamp) {
        var date = new Date(timestamp);
        var year = date.getFullYear();
        var month = (date.getMonth() + 1).toString().padStart(2, '0');
        var day = date.getDate().toString().padStart(2, '0');
        var hours = date.getHours().toString().padStart(2, '0');
        var minutes = date.getMinutes().toString().padStart(2, '0');
        var seconds = date.getSeconds().toString().padStart(2, '0');
        return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
    }

    function validateField(inputId, errorMessage, checkEmpty = true, isNumber = false) {
        var inputElement = $('#' + inputId);
        var value = inputElement.val();
        var errorDiv = $('#error-' + inputId);

        if (errorDiv.length === 0) {
            inputElement.after('<div id="error-' + inputId + '" class="text-danger"></div>');
            errorDiv = $('#error-' + inputId);
        }

        // 對於數字欄位，首先檢查是否為 null 或空字串
        if (isNumber && (value === null || value.trim() === '')) {
            errorDiv.text(errorMessage);
            return false;
        }

        if (checkEmpty && !value.trim()) {
            errorDiv.text(errorMessage);
            return false;
        }

        errorDiv.text('');  // 清除錯誤訊息
        return true;
    }


    function validateForm() {
        var isValid = true;

        isValid &= validateField('paName', '請輸入活動名稱', true);
        isValid &= validateField('paDiscount', '請輸入折扣', true, true);
        isValid &= validateField('paContent', '請輸入活動內容', true);
        isValid &= validateField('paStart', '請輸入開始日期', true);
        isValid &= validateField('paEnd', '請輸入結束日期', true);

        return isValid;
    }

});


