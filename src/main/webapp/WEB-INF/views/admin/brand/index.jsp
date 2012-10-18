<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div>
<h3>BRAND INFORMATION</h3>
<hr></hr>
<div class="well span8">
<form:form action = "/admin/brand" method = "POST" modelAttribute = "brand">
	<form:hidden path="id"/>
	<label>Brand Name:</label><form:input type="text" path="brandName" placeholder="Brand Name"/>
	<form:errors path="brandName" style="color:red;"/>
	<label>Supplier:</label><form:select items="${suppliers}" path="supplierId" itemValue="id"/>
	<form:errors path="supplier" style="color:red;"/>
	<label>Notes:</label><form:textarea type="text" class="input-xlarge" path="notes"/>
	<hr></hr>
	<input type="submit" value="Save Brand" class="btn btn-primary"/>
</form:form>
</div>

<table class="table table-striped table-bordered table-condensed alert-info">
  <thead>
  <tr>
   <td>Brand Name</td>
   <td>Supplier</td>
   <td>Notes</td>
   <td>Action</td>
  </tr>
  </thead>
  <c:forEach var="brand" items="${brands}">
  <tr>
    <td>${brand.brandName}</td>
    <td>${brand.supplier}</td>
    <td>${brand.notes}</td>
    <td>
    <ul class="nav nav-pills" style="height:0px;">
    	<li>
    		<a href="/admin/brand/edit/${brand.id}"><img alt="edit" src="/images/update.png">&nbsp; Edit </a>
    	</li>
    	<li class="dropdown" data-dropdown="dropdown">
			<a href="#" class="dropdown-toggle"><img alt="delete" src="/images/delete.png">&nbsp; Remove</a>
				<ul class="dropdown-menu">
					<li><a href="/admin/brand/remove/${brand.id}">Delete it!</a></li>
					<li><a href="#">Cancel</a></li>
				</ul>
		</li>
	</ul>
	</td>
  </tr>
  </c:forEach>
</table>
</div>
