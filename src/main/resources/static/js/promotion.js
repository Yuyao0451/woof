$(document).ready(function() {
    loadPromotionActivities();

    $('#addPromotionBtn').on('click', function() {
        prepareModalForCreate();
        $('#promotionModal').modal('show');
    });

    $('#promotionModal .btn-primary').on('click', function() {
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
    });
});

function loadPromotionActivities() {
    $.get('/promotion', function(activities) {
        $('#promotionActivitiesTable tbody').empty();
        activities.forEach(function(activity) {
            var statusClass = activity.paStatus ? 'status-on' : 'status-off';
            var row = `<tr>
                <td>${activity.paNo}</td>
                <td>${activity.paName}</td>
                <td>${activity.paDiscount}</td>
                <td>${activity.paContent}</td>
                <td>${formatDate(activity.paStart)}</td>
                <td>${formatDate(activity.paEnd)}</td>
                <td class="${statusClass}">${activity.paStatus ? '上架' : '下架'}</td>
                <td><button class="btn btn-secondary editBtn" data-id="${activity.paNo}">編輯</button></td>
            </tr>`;
            $('#promotionActivitiesTable tbody').append(row);
        });

        // 綁定編輯按鈕事件
        $('.editBtn').on('click', function() {
            var id = $(this).data('id');
            loadPromotionActivityForEdit(id);
        });
    });
}


function loadPromotionActivityForEdit(id) {
    $.get('/promotion/' + id, function(activity) {
        $('#paName').val(activity.paName);
        $('#paDiscount').val(activity.paDiscount);
        $('#paContent').val(activity.paContent);
        $('#paStart').val(formatDate(activity.paStart));
        $('#paEnd').val(formatDate(activity.paEnd));
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
}

function updatePromotionActivity(id, activity) {
    $.ajax({
        url: '/promotion/' + id,
        method: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify(activity),
        success: function(response) {
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
        success: function(response) {
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
    $('#paStatusSelect').val('true'); // 預設為 '上架'
}

function formatDate(dateString) {
    var date = new Date(dateString);
    return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
}
