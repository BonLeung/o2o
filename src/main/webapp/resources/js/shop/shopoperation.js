/**
 * 
 */
$(function() {
    var initUrl = '/o2o/shopadmin/getshopinitinfo';
    var registerShopUrl = '/o2o/shopadmin/registershop';
    function getShopInitInfo() {
        $.getJSON(initUrl, function(data) {
            if (data.success) {
                var tempHTML = '';
                var tempAreaHTML = '';
                data.shopCategoryList.map(function(item, index) {
                    tempHTML += '<option data-id="' + item.shopCategoryId + '">' + item.shopCategoryName + '</option>';
                })
                data.areaList.map(function(item, index) {
                    tempAreaHTML += '<option data-id="' + item.areaId + '">' + item.areaName + '</option>';
                })
            }
            $('#category').html(tempHTML);
            $('#area').html(tempAreaHTML);
        })
    }

    $('#submit').click(function() {
        var shop = {};
        shop.name = $('#name').val();
        shop.addr = $('#addr').val()
        shop.phone = $('#phone').val();
        shop.desc = $('#desc').val();
        shop.category = {
            shopCategoryId: $('#category').find('option').not(function() {
                return !this.selected;
            }).data('id')
        };
        shop.area = {
            areaId: $('#area').find('option').not(function() {
                return !this.selected;
            }).data('id')
        }
        var shopImg = $('#avatar')[0].files[0];
        var formData = new FormData();
        formData.append('shopImg', shopImg);
        formData.append('shopStr', JSON.stringify(shop));

        $.ajax({
            url: registerShopUrl,
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            cache: false,
            success: function(data) {
                if (data.success) {
                    $.toast('提交成功');
                } else {
                    $.toast('提交失败' + data.errmsg);
                }
            } 
        })
    })

    alert(initUrl);
    getShopInitInfo();
})