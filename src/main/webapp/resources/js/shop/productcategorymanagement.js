$(function() {
    var listUrl = "/o2o/shopadmin/getproductcategorylist";
    var addUrl = "/o2o/shopadmin/addproductcategory";
    var deleteUrl = "/o2o/shopadmin/removeproductcategory";

    getList();

    function getList() {
        $.getJSON(listUrl, function(res) {
                if (res.success) {
                    var dataList = res.data;
                    $('#category_wrap').html('');
                    var tempHTML = '';
                    dataList.map(function(item, index) {
                        tempHTML += '<div class="row row-product-category now">' + 
                                        '<div class="col-33 product-category-name">' + item.name + '</div>' +
                                        '<div class="col-33 product-category_priority">' + item.priority + '</div>' + 
                                        '<div class="col-33"><a href="" class="button delete button-danger" data-id="' + item.productCategoryId + '">删除</a></div>' +
                                    '</div>';
                    })
                    $('#category_wrap').html(tempHTML);
                }
            }
        )
    }

    $('#new').click(function() {
        var tempHTML = '<div class="row row-product-category temp">' + 
                            '<div class="col-33 product-category-name"><input class="category-input category" type="text" placeholder="分类名"></div>' +
                            '<div class="col-33 product-category-priority"><input class="priority-input priority" type="text" placeholder="优先级"></div>' +
                            '<div class="col-33"><a href="" class="button delete button-danger">删除</a></div>' +
                        '</div>';
        $('#category_wrap').append(tempHTML);
    })

    $('#submit').click(function() {
        var tempArr = $('.temp');
        var productCategoryList = [];
        tempArr.map(function(index, item) {
            var tempObj = {};
            tempObj.name = $(item).find('.category').val();
            tempObj.priority = $(item).find('.priority').val();
            if (tempObj.name && tempObj.priority) {
                productCategoryList.push(tempObj);
            }
        })
        $.ajax({
            url: addUrl,
            type: 'POST',
            data: JSON.stringify(productCategoryList),
            contentType: 'application/json',
            success: function(res) {
                if (res.success) {
                    $.toast('提交成功');
                    getList()
                } else {
                    $.toast('提交失败');
                }
            }
        })
    })

   $('#category_wrap').on('click', '.row-product-category.temp .delete', function(e) {
       $(this).parents('.row-product-category').remove();
   })

   $('#category_wrap').on('click', '.row-product-category.now .delete', function(e) {
       var target = e.currentTarget;
       $.confirm('确定删除吗', function() {
           $.ajax({
               url: deleteUrl,
               type: 'POST',
               data: {
                   productCategoryId: target.dataset.id
               },
               dataType: 'json',
               success: function(res) {
                   if (res.success) {
                       $.toast('删除成功');
                       getList()
                   } else {
                       $.toast('删除失败');
                   }
               }
           })
       })
   })
})