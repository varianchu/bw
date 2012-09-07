<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div>
<h3>SETTINGS</h3>
<hr></hr>
<div class="well span8 offset2">
<form:form action = "/admin/settings" method = "POST" modelAttribute = "setting">
	<form:hidden path="id"/>
	<label>Stock Process:</label><form:select items="${stock}" path="stockProcess"/>
	<form:errors path="stockProcess" style="color:red;"/>
	<label>Ceiling Value:</label><form:input type="text" path="ceilingValue"/>
	<form:errors path="ceilingValue" style="color:red;"/>
	<label>Floor Value:</label><form:input type="text" path="floorValue"/>
	<hr></hr>
	<input type="submit" value="Save Settings" class="btn btn-primary"/>
</form:form>
</div>
</div>