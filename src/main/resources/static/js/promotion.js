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
            paStatus: $('#paStatus').is(':checked')
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
            </tr>`;
            $('#promotionActivitiesTable tbody').append(row);
        });
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
    // 清空模態框中的輸入欄位
}

function formatDate(dateString) {
    var date = new Date(dateString);
    return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
}
