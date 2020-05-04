$(document).ready(function() {

	var urlParams = new URLSearchParams(window.location.search);
	var result = urlParams.get('result');
	var alert = $("#success-alert");
	if (result == null)
		result = "";
	if (result == "") {
		alert.hide();
	}

	
	 alert.each(function() {
		 urlParams.delete('result');
		    var that = $(this);
		    setTimeout(function() {
		      that.alert('close');
		    }, 5000);
		  });
});
