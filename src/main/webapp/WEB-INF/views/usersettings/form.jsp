<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div>
<h3>CREATE USER ACCOUNT</h3>
<hr></hr>
<div class="well span8">
<form:form action = "/admin/user" method = "POST" modelAttribute = "user">
	<form:hidden path="id"/>
	<label>Username:</label><form:input type="text" path="username" placeholder="Username"/>
	<form:errors path="username" style="color:red;"/>
	<label>Password:</label><form:input type="password" path="password" placeholder="Password"/>
	<form:errors path="password" style="color:red;"/>
	<label>First Name:</label><form:input type="text" path="firstName" placeholder="First Name"/>
	<form:errors path="firstName" style="color:red;"/>
	<label>Last Name:</label><form:input type="text" path="lastName" placeholder="Last Name"/>
	<form:errors path="lastName" style="color:red;"/>
	<label>Email:</label><form:input type="text" path="email" placeholder="Email"/>
	<form:errors path="email" style="color:red;"/>
	<label>Role:</label><form:select items="${roles}" path="roleId" itemValue="id"/>
	<form:errors path="roleId" style="color:red;"/>
	<hr></hr>
	<input type="submit" value="Save" class="btn btn-primary"/>
</form:form>
</div>
</div>