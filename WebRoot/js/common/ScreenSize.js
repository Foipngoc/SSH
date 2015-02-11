var CLINETWIDTH= 0;//屏幕宽度
var CLINEHEIGHT= 0;//屏幕高度
function screenResize(){
    if(typeof( $$(window).innerWidth()) == 'number' ) {
        //Non-IE
        CLINETWIDTH = $$(window).innerWidth();
        CLINEHEIGHT = $$(window).innerHeight();
    } 
    else if(document.documentElement && ( document.documentElement.clientWidth || document.documentElement.clientHeight)){
        //IE 6+ in 'standards compliant mode'
        CLINETWIDTH = document.documentElement.clientWidth;
        CLINEHEIGHT = document.documentElement.clientHeight;  
    }
    window.onresize = function () 
    { 
        if( typeof( $$(window).innerWidth() ) == 'number' ) {
            //Non-IE
            CLINETWIDTH = window.innerWidth;
            CLINEHEIGHT = window.innerHeight;
        } 
        else if(document.documentElement && ( document.documentElement.clientWidth || document.documentElement.clientHeight)){
            //IE 6+ in 'standards compliant mode'
            CLINETWIDTH = document.documentElement.clientWidth;
           CLINEHEIGHT = document.documentElement.clientHeight;  
        }
    } 
}
