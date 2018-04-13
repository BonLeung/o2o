/**
 * 
 */
$(function() {
    var initUrl = '/o2o/shopadmin/getshopinitinfo';
    var registerShopUrl = '/o2o/shopadmin/registershop';
    var shopId = getQueryString('shopId');
    var isEdit = shopId ? true : flase;
    var shopInfoUrl = "/o2o/shopadmin/getshopbyid?shopId=" + shopId;
    var editShopUrl = "/o2o/shopadmin/modifyshop";

    if (!isEdit) {
        getShopInitInfo();
    } else {
        getShopInfo(shopId);
    }

    function getShopInfo(shopId) {
        $.getJSON(shopInfoUrl, function(data) {
            if (data.success) {
                var shop = data.shop;
                $('#name').val(shop.name);
                $('#addr').val(shop.addr);
                $('#phone').val(shop.phone);
                $('#desc').val(shop.desc);
                var shopCategory = '<option data-id="' + shop.shopCategory.shopCategoryId + '" selected>' + shop.shopCategory.name + '</option>';
                var tempAreaHTML = '';
                data.areaList.map(function(item, index) {
                    tempAreaHTML += '<option data-id="' + item.areaId + '">' + item.areaName + '</option>';
                })
                $('#category').html(shopCategory);
                $('#category').attr('disabled', 'disabled');
                $('#area').html(tempAreaHTML);
                $('#area option[data-id="' + shop.area.areaId + '"]').attr('selected', "selected");
            }
        })
    }

    function getShopInitInfo() {
        $.getJSON(initUrl, function(data) {
            if (data.success) {
                var tempHTML = '';
                var tempAreaHTML = '';
                data.shopCategoryList.map(function(item, index) {
                    tempHTML += '<option data-id="' + item.shopCategoryId + '">' + item.name + '</option>';
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
        if (isEdit) {
            shop.shopId = shopId;
        }
        shop.name = $('#name').val();
        shop.addr = $('#addr').val()
        shop.phone = $('#phone').val();
        shop.desc = $('#desc').val();
        shop.shopCategory = {
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
        var verifyCodeActual = $('#j_captcha').val();
        if (!verifyCodeActual) {
            $.toast('请输入验证码');
            return
        }
        formData.append('verifyCodeActual', verifyCodeActual);

        $.ajax({
            url: (isEdit ? editShopUrl : registerShopUrl),
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
                $('#captcha_img').click();
            } 
        })
    })

})