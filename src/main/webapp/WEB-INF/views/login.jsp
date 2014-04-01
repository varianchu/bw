<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div class="hero-unit">
	<div class="grid_6" style="margin: auto;">
	<h1>Bionic Wheels System Login</h1>
	<br/>
	<form id="barcode_form" name="f" action="<c:url value="j_spring_security_check"/>" method="POST" class="form-stacked">
		<label>Barcode:</label><input type="password" onchange="barcodeChange()" id="barcodeEnter" class="span6" autofocus/>
		<input type="hidden" name="j_username" id="usernameBarcode">
		<input type="hidden" name="j_password" id="passwordBarcode"/>
		<br/>
		<br/>
<!-- 		<input type="submit" value="Login" class="btn primary"/> -->
		<br/>
		<br/>
		<c:if test="${not empty param.error}">
			  <div class="alert alert-error">
			    Your login attempt was not successful, try again.<br/>
			    Reason: <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>.
			  </div>
		</c:if>
	</form>
	
	<a class="btn btn-primary" onclick="showUserName()">ACCESS VIA USERNAME</a>
	<br />
	<br />
	<form id="login_form" name="f" action="<c:url value="j_spring_security_check"/>" method="POST" class="form-stacked">
		<label>Username:</label><input type="text" name="j_username">
		<label>Password:</label><input type="password" name="j_password" />
		<br/>
		<br/>
		<input type="submit" value="Login" class="btn primary"/>
		<br/>
		<br/>
		<c:if test="${not empty param.error}">
			  <div class="alert alert-error">
			    Your login attempt was not successful, try again.<br/>
			    Reason: <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>.
			  </div>
		</c:if>
	</form>
	</div>
	<div class="clear"></div>
</div>
<div class="clear"></div>

<script type="text/javascript">

$('#login_form').hide();

function showUserName(){
	$('#login_form').show();
}

var frm = $('#barcode_form');

function barcodeChange(){
		
		var barcodeString = ($('#barcodeEnter').val()).split('-');
		var username = barcodeString[0];
		var password = barcodeString[1];
		
		$('#usernameBarcode').val(username);
		$('#passwordBarcode').val(password);
		
	    $.ajax({
	        type: frm.attr('method'),
	        url: frm.attr('action'),
	        data: frm.serialize(),
	        success: function (data) {
	        	window.location.href = '/';
// 	        	var chr = String.fromCharCode(96+27);
// 	        	alert(chr);
	        }
	    });
}

</script>