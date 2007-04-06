function newImage(arg) {
	if (document.images) {
		rslt = new Image();
		rslt.src = arg;
		return rslt;
	}
}

function changeImages() {
	if (document.images && (preloadFlag == true)) {
		for (var i=0; i<changeImages.arguments.length; i+=2) {
			document[changeImages.arguments[i]].src = changeImages.arguments[i+1];
		}
	}
}

var preloadFlag = false;
function preloadImages() {
	if (document.images) {
		bg_home_over = newImage("images/bg-home_over.gif");
		bg_products_over = newImage("images/bg-products_over.gif");
		bg_support_over = newImage("images/bg-support_over.gif");
		bg_contact_over = newImage("images/bg-contact_over.gif");
		preloadFlag = true;
	}
}

function getURL (loc) {
	if (loc=="home"){window.location.href = "index.jsp";}
	if (loc=="products"){window.location.href = "products.jsp";}
	if (loc=="support"){window.location.href = "support.jsp";}
	if (loc=="contact"){window.location.href = "contact.jsp";}
}