/**
 * Created by cao on 2017/1/9.
 */

$(function () {
    leftbar = new mdui.Drawer('#drawer');
    var dayOrNightNow = 'day';
    function changeColor() {
        if (dayOrNightNow == 'day') {
            var Color = '#eaeaea';
            var BackgroundColor = '#41535B';
            var mainBackground = '#263339';
            $('.secondColorAndBackgroundColor').css('color', Color);
            $('.secondColorAndBackgroundColor').css('background-color', BackgroundColor);
            $("body").css('background-color', mainBackground);
            dayOrNightNow = 'night';
        } else {
            var Color = '#696969';
            var BackgroundColor = '#ffffff';
            var mainBackground = '#e9ebec';
            $('.secondColorAndBackgroundColor').css('color', Color);
            $('.secondColorAndBackgroundColor').css('background-color', BackgroundColor);
            $("body").css('background-color', mainBackground);
            dayOrNightNow = 'day';
        }
    }


    $(window).resize(function () {
        change(false);
    });
    change(true);
    var lastWidth = 0;
    function change(isFirst) {
        var minWidth = 800;
        var width = $(this).width();
        if (isFirst || !(lastWidth < minWidth & width < minWidth) || !(lastWidth > minWidth & width > minWidth)) {
            if (width < minWidth) {
                $('#top_card_left').removeClass('mdui-col-xs-7');
                $('#top_card_left').addClass('mdui-col-xs-12');
                $('#top_card_right').removeClass('mdui-col-xs-5');
                $('#top_card_right').addClass('mdui-col-xs-12');
            }
            else {
                $('#top_card_left').removeClass('mdui-col-xs-12');
                $('#top_card_left').addClass('mdui-col-xs-7');
                $('#top_card_right').removeClass('mdui-col-xs-12');
                $('#top_card_right').addClass('mdui-col-xs-5');
            }
        }
    }



});
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg); //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}
var leftbar;
function toggle() {
    leftbar.toggle();
}

