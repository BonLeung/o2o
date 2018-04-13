$(function() {
    getList();

    function getList(e) {
        $.ajax({
            url: '/o2o/shopadmin/getshoplist',
            type: 'GET',
            dataType: 'json',
            success: function(data) {
                if (data.success) {
                    handleList(data.shopList);
                    handleUser(data.user);
                }
            }
        })
    }

    function handleList(shopList) {
        var html = '';
        shopList.map(function(item, index) {
            html += '<div class="row row-shop">' +
                        '<div class="col-40">' + item.name + '</div>' +
                        '<div class="col-40">' + getShopStatus(item.status) + '</div>' +
                        '<div class="col-20">' + goShop(item.status, item.shopId) + '</div>' +
                    '</div>';
        })
        $('#shop_wrap').html(html);
    }

    function handleUser(user) {
        $('#user_name').text(user.name);
    }

    function getShopStatus(status) {
        switch(status) {
            case 0:
                return "审核中";
            case -1:
                return "店铺非法";
            default:
                return "审核通过";
        }
    }

    function goShop(status, id) {
        if (status == 1) {
            return '<a href="/o2o/shopadmin/shopmanage?shopId=' + id +'" external>进入</a>';
        } else {
            return '';
        }
    }
})