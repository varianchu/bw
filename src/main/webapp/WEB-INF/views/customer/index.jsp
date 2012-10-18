<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div>
<h3>CUSTOMER INFORMATION</h3>
<hr></hr>
<div class="well span8">
<form:form action = "/new-customer" method = "POST" modelAttribute = "customer">
	<form:hidden path="id"/>
	<label>Customer Name:</label><form:input type="text" path="customerName" placeholder="Customer Name"/>
	<form:errors path="customerName" style="color:red;"/>
	<label>Contact Number:</label><form:input type="text" path="contactNumber" placeholder="Contact Number"/>
	<form:errors path="contactNumber" style="color:red;"/>
	<label>City:</label><form:input type="text" path="cityLocation" placeholder="City"/>
	<form:errors path="cityLocation" style="color:red;"/>
	<label>Address:</label><form:input type="text" path="address" placeholder="Address"/>
	<form:errors path="address" style="color:red;"/>
	<label>Email Address:</label><form:input type="text" path="email" placeholder="Email Address"/>
	<form:errors path="email" style="color:red;"/>
	<label>Occupation:</label><form:input type="text" path="occupation" placeholder="Occupation"/>
	<form:errors path="occupation" style="color:red;"/>
	<label>Company:</label><form:input type="text" path="company" placeholder="Company"/>
	<form:errors path="company" style="color:red;"/>
	<label>Remarks:</label><form:textarea type="text" path="remarks" class="input-xlarge" placeholder="Remarks"/>
	<form:errors path="remarks" style="color:red;"/>
	<hr></hr>
	<input type="submit" value="Save Customer" class="btn btn-primary"/>
</form:form>
</div>
</div>