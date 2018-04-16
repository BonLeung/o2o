$(function() {
    var shopId = getQueryString('shopId');
    var shopInfoUrl = '/o2o/shopadmin/getshopmanagementinfo?shopId=' + shopId;
    $.getJSON(shopInfoUrl, function(data) {
        if (data.redirect) {
            window.location.href = redirect;
        } else {
            if (data.shopId != undefined && data.shopId != null) {
                shopId = data.shopId
            }
            $('#shop_info').attr('href', '/o2o/shopadmin/shopoperation?shopId=' + shopId);
            $('#category_manage').attr('href', '/o2o/shopadmin/productcategorymanagement?shopId=' + shopId);
        }
    }) 
})