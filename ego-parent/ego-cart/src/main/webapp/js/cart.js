var TTCart = {
    load: function () { // 加载购物车数据

    },
    itemNumChange: function () {
        $(".increment").click(function () {//＋
            var _thisInput = $(this).siblings("input");
            _thisInput.val(eval(_thisInput.val()) + 1);
            $.post("/cart/update/num/" + _thisInput.attr("itemId") + "/" + _thisInput.val() + ".action", function (data) {
                if (data.status == 200) {
                    $("#stock").html("<h4>有货</h4>")
                    $("#toSettlement").attr("href", "http://localhost:8086/order/order-cart.html")
                    TTCart.refreshTotalPrice();
                } else if (data.status == 300) {
                    $("#stock").html("<h4 style='color:red'>无货</h4>")
                    $("#toSettlement").attr("href", "javascript:return false;")
                }
            });
        });
        $(".decrement").click(function () {//-
            var _thisInput = $(this).siblings("input");
            if (eval(_thisInput.val()) == 1) {
                return;
            }
            _thisInput.val(eval(_thisInput.val()) - 1);
            $.post("/cart/update/num/" + _thisInput.attr("itemId") + "/" + _thisInput.val() + ".action", function (data) {
                if (data.status == 200) {
                    $("#stock").html("<h4>有货</h4>")
                    $("#toSettlement").attr("href", "http://localhost:8086/order/order-cart.html")
                    TTCart.refreshTotalPrice();
                } else if (data.status == 300) {
                    $("#stock").html("<h4 style='color:red'>无货</h4>")
                    $("#toSettlement").attr("href", "javascript:return false;")
                }
            });
        });
        $(".quantity-form .quantity-text").rnumber(1);//限制只能输入数字
        $(".quantity-form .quantity-text").change(function () {
            var _thisInput = $(this);
            $.post("/service/cart/update/num/" + _thisInput.attr("itemId") + "/" + _thisInput.val() + ".action", function (data) {
                if (data.status == 200) {
                    $("#stock").html("<h4>有货</h4>")
                    $("#toSettlement").attr("href", "http://localhost:8086/order/order-cart.html")
                    TTCart.refreshTotalPrice();
                } else if (data.status == 300) {
                    $("#stock").html("<h4 style='color:red'>无货</h4>")
                    $("#toSettlement").attr("href", "javascript:return false;")
                }
            });
        });
    },
    // refreshTotalPrice: function () { //重新计算总价
    //     var total = 0;
    //     $(".quantity-form .quantity-text").each(function (i, e) {
    //         var _this = $(e);
    //         total += (eval(_this.attr("itemPrice")) * 10000 * eval(_this.val())) / 10000;
    //     });
    //     $(".totalSkuPrice").html(new Number(total / 100).toFixed(2)).priceFormat({ //价格格式化插件
    //         prefix: '￥',
    //         thousandsSeparator: ',',
    //         centsLimit: 2
    //     });
    // }

    refreshTotalPrice: function () { //重新计算总价
        var total = 0;
        $(".quantity-form .quantity-text").each(function (i, e) {
            var _this = $(e);
            var c = _this.parent().parent().parent().children("#parent_checkbox").children("input[name='checkItem']")
            if (c.prop("checked") == false) {
                total += 0;
            } else {
                total += (eval(_this.attr("itemPrice")) * 10000 * eval(_this.val())) / 10000;
            }
        });
        $(".totalSkuPrice").html(new Number(total / 100).toFixed(2)).priceFormat({ //价格格式化插件
            prefix: '￥',
            thousandsSeparator: ',',
            centsLimit: 2
        });
    }
};

$(function () {
    TTCart.load();
    TTCart.itemNumChange();

    // 获得上面的复选框
    var $selectAll = $("#toggle-checkboxes_up");
    $selectAll.live("click", function () {
        if ($selectAll.prop("checked") == true) {
            // 上面的复选框已被选中
            $(":checkbox[name='checkItem']").prop("checked", true);
            TTCart.refreshTotalPrice();
        } else {
            // 上面的复选框没被选中
            $(":checkbox[name='checkItem']").prop("checked", false);
            TTCart.refreshTotalPrice();
        }
    });

    $(":checkbox[name='checkItem']").live("click", function () {
        if ($(this).prop("checked") == false) {
            $selectAll.prop("checked", false)
            TTCart.refreshTotalPrice();
        } else {
            //遍历未被选中的checkbox
            $selectAll.prop("checked", true)
            $("input[name='checkItem']:not(:checked)").each(function () {
                if ($(this) != null) {
                    //说明单选框没被选全
                    $selectAll.prop("checked", false)
                }
            })
            TTCart.refreshTotalPrice();
        }
    })

    $(".cart-remove").click(function () {
        var href = $(this).attr("href");
        var $c = $(this)
        $.ajax({
            url: href,
            success: function (data) {
                if (data.status == 200) {
                    $c.parent().parent().parent().remove();
                    TTCart.refreshTotalPrice();
                }
            }
        })
        return false;
    })

    //结算点击事件
    $(".checkout").live("click", function () {
        // alert($(this).prop("href"))
        var checkout = $(this);
        var href = checkout.prop("href");
        href = href + "?"
        $(".quantity-form .quantity-text").each(function (i, e) {
            var _this = $(e);
            var c = _this.parent().parent().parent().children("#parent_checkbox").children("input[name='checkItem']")
            if (c.prop("checked") == true) {
                href = href + "id=" + _this.attr("itemId") + "&";
            }
        });
        href = href.substring(0, href.length - 1)
        // alert(href)
        checkout.attr("href", href)
        // alert(checkout.prop("href"));
        location = href
        return false;
    })
    //测试
    $("#remove-batch").click(function () {
        var a = $("#stock")
        a.html("<h4 style='color:red'>无货</h4>")
        alert(a.html());
        $("#toSettlement").attr("href", "javascript:return false;")
        alert("打开了")
        $("#toSettlement").attr("href", "http://localhost:8086/order/order-cart.html")

    })
});