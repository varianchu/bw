<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div class="hero-unit">
	<div class="grid_6" style="margin: auto;">
	<h1>Bionic Wheels System Login</h1>
	<br/>
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