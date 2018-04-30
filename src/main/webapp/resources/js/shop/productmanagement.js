$(function() {
    // 获取此店铺下的商品列表的 url
    var listUrl = '/o2o/shopadmin/getproductlistbyshop?pageIndex=1&pageSize=999';
    // 商品下架 url
    var statusUrl = '/o2o/shopadmin/modifyproduct';
    getList();

    // 获取此店铺下的商品列表
    function getList() {
        $.getJSON(listUrl, function(result) {
            if (result.success) {
                var productList = result.productList;
                var tempHTML = '';
                // 遍历每条商品信息，拼接成一行看是，列信息包括
                // 商品名称，优先级，上架/下架（含productId），编辑按钮（含productId）
                // 预览（含productId）
                productList.map(function(item, inde) {
                    var textOp = '下架';
                    var contraryStatus = 0;
                    if (item.status == 0) {
                        // 若状态值为0，表明是已下架的商品，操作变为上架（即点击按钮上架相关商品）
                        textOp = '上架';
                        contraryStatus = 1;
                    } else {
                        contraryStatus = 0;
                    }
                    // 拼接每件商品的行信息
                    tempHTML += '<div class="row row-product">' +
                                    '<div class="col-33">' + item.name + '</div>' +
                                    '<div class="col-20">' + item.priority + '</div>' + 
                                    '<div class="col-40">' +
                                        '<a href="#" class="edit" data-id="' + item.productId + '">编辑</a>' +
                                        '<a href="#" class="status" data-id="' + item.productId + '" data-status="' + contraryStatus + '">' + textOp + '</a>' +
                                        '<a href="#" class="preview" data-id="' + item.productId + '" data-status="' + item.status + '">预览</a>' +
                                    '</div>' +
                                '</div>';
                })
                // 将拼接好的信息赋值到html控件中
                $('.product-wrap').html(tempHTML);
            }
        })
    }

    // 将 class 为 product-wrap 里面的 a 标签绑定上点击事件
    $('.product-wrap').on('click', 'a', function(e) {
        var target = $(e.currentTarget);
        if (target.hasClass('edit')) {
            // 如果 class 为 edit 则点击就进入店铺信息编辑页面，并带有 productId 参数
            window.location.href = '/o2o/shopadmin/productoperation?productId=' + e.currentTarget.dataset.id;
        } else if(target.hasClass('status')) {
            // 如果 class 为 status 则调用后台功能上/下架相关商品，并带有 productId 参数
            changeItemStatus(e.currentTarget.dataset.id, e.currentTarget.dataset.status);
        } else if (target.hasClass('preview')) {
            window.location.href = '/o2o/frontend/productdetail?productId=' + e.currentTarget.dataset.id;
        }
    })
    function changeItemStatus(id, status) {
        // 定义 product json 对象并添加 productId 以及状态（上架/下架）
        var product = {};
        product.productId = id;
        product.status = status;
        $.confirm('确定么?', function() {
            // 上下架相关商品
            $.ajax({
                url: statusUrl,
                type: 'POST',
                data: {
                    productStr: JSON.stringify(product),
                    statusChange: true
                },
                dataType: 'json',
                success: function(result) {
                    if (result.success) {
                        $.toast('操作成功');
                        getList()
                    } else {
                        $.toast('操作失败');
                    }
                }
            })
        })
    }
})