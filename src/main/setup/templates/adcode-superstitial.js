var swfFLAdHeight={HEIGHT_REQUEST_PARAM_KEY};
var swfFLAdWidth={WIDTH_REQUEST_PARAM_KEY};
var myWidth = 0, myHeight = 0;
if(window.self != window.parent){
if( typeof( parent.window.innerWidth ) == 'number' ) {
//Non-IE
myWidth = parent.window.innerWidth;
myHeight = parent.window.innerHeight;
} else if( parent.document.documentElement &&
( parent.document.documentElement.clientWidth || parent.document.documentElement.clientHeight ) ) {
//IE 6+ in 'standards compliant mode'
myWidth = parent.document.documentElement.clientWidth;
myHeight = parent.document.documentElement.clientHeight;
} else if( parent.document.body && ( parent.document.body.clientWidth || parent.document.body.clientHeight ) ) {
//IE 4 compatible
myWidth = parent.document.body.clientWidth;
myHeight = parent.document.body.clientHeight;
}
} else {
if( typeof( window.innerWidth ) == 'number' ) {
//Non-IE
myWidth = window.innerWidth;
myHeight = window.innerHeight;
} else if( document.documentElement &&
( document.documentElement.clientWidth || document.documentElement.clientHeight ) ) {
//IE 6+ in 'standards compliant mode'
myWidth = document.documentElement.clientWidth;
myHeight = document.documentElement.clientHeight;
} else if( document.body && ( document.body.clientWidth || document.body.clientHeight ) ) {
//IE 4 compatible
myWidth = document.body.clientWidth;
myHeight = document.body.clientHeight;
}
}
var FLflashOffsetLeft=myWidth/2-swfFLAdWidth/2;
var FLflashOffsetTop=myHeight/2-swfFLAdHeight/2;
if(FLflashOffsetLeft < 0){
        FLflashOffsetLeft = 350;
}
if(FLflashOffsetTop < 0){
        FLflashOffsetTop = 150;
}


function hide(){
        var mydiv=document.getElementById("FlashDiv");mydiv.style.visibility="hidden";
}

function scrollevent(){
        document.getElementById("FlashDiv").style.top = document.body.scrollTop + FLflashOffsetTop;
};
window.hide=hide;
window.onscroll=scrollevent;

function init(){;}

document.write('<div id="FlashDiv" style="position:absolute; left:' + FLflashOffsetLeft + 'px; top:' + FLflashOffsetTop + 'px; width:' + swfFLAdWidth + 'px; height:' + swfFLAdHeight + 'px; z-index:65535"></div>');



function ShowBanner(){
	var mydiv=document.getElementById("FlashDiv");
	var str='<OBJECT classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" WIDTH='
            + '{WIDTH_REQUEST_PARAM_KEY}'
            + ' HEIGHT='
            + '{HEIGHT_REQUEST_PARAM_KEY}'
            + ' id=tile ALIGN=""><PARAM NAME=movie VALUE='
            + '{ADSOURCE_ID_REQUEST_PARAM_KEY}'
            + '?clickTAG='
            + '{TARGETURL_REQUEST_PARAM_KEY}'
            + '?targetWindow='
            + '{TARGET_WINDOW_REQUEST_PARAM_KEY}'
            + '><PARAM NAME=loop VALUE=true><PARAM NAME=quality VALUE=high><PARAM NAME=wmode VALUE=transparent><PARAM NAME=bgcolor VALUE=#FFFFFF><EMBED src='
            + '{ADSOURCE_ID_REQUEST_PARAM_KEY}'
            +  '?clickTAG='
            + '{TARGETURL_REQUEST_PARAM_KEY}'
            + ' loop=true quality=high wmode=transparent bgcolor=#FFFFFF WIDTH='
            + '{WIDTH_REQUEST_PARAM_KEY}'
            + ' HEIGHT='
            + '{HEIGHT_REQUEST_PARAM_KEY}'
            + ' ALIGN="" TYPE="application/x-shockwave-flash" PLUGINSPAGE="http://www.macromedia.com/go/getflashplayer"></EMBED></OBJECT>';
	mydiv.innerHTML = str;
	mydiv.style.visibility="visible";
}

ShowBanner();
