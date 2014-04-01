<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div>
<h3>SUPPLIER INFORMATION</h3>
<hr></hr>
<div class="well span8">
<form:form action = "/admin/supplier" method = "POST" modelAttribute = "supplier">
	<form:hidden path="id"/>
	<label>Supplier Name:</label><form:input type="text" path="supplierName" placeholder="Supplier Name"/>
	<form:errors path = "supplierName" style="color:red;"></form:errors>
	<label>Supplier Address:</label><form:input type="text" path="address" class="span12" placeholder="Address"/>
	<label>Contact Number:</label><form:input type="text" path="contactNumber" placeholder="Contact Number" class=""/>
	<label>Notes:</label><form:textarea type="text" class="input-xlarge" path="notes"/>
	<hr></hr>
	<input type="submit" value="Save Supplier" class="btn btn-primary"/>
</form:form>
</div>

<table class="table table-striped table-bordered table-condensed alert-info">
  <thead>
  <tr>
   <td>Supplier Name</td>
   <td>Supplier Address</td>
   <td>Contact Number</td>
   <td>Notes</td>
   <td>Action</td>
  </tr>
  </thead>
  <c:forEach var="supplier" items="${suppliers}">
  <tr>
    <td>${supplier.supplierName}</td>
    <td>${supplier.address}</td>
    <td>${supplier.contactNumber}</td>
    <td>${supplier.notes}</td>
    <td>
    <ul class="nav nav-pills" style="height:0px;">
    	<li>
    		<a href="/admin/supplier/edit/${supplier.id}"><img alt="edit" src="/images/update.png">&nbsp; Edit </a>
    	</li>
    	<li class="dropdown">
			<a href="#" class="dropdown-toggle" data-toggle="dropdown">Remove <b class="caret"></b></a>
            	<ul class="dropdown-menu">
					<li><a href="/admin/supplier/remove/${supplier.id}">Delete it!</a></li>
					<li><a href="#">Cancel</a></li>
				</ul>
       	</li>
	</ul>
	</td>
  </tr>
  </c:forEach>
</table>

</div>
<script type="text/javascript">
	$('.dropdown-toggle').dropdown();
</script>