<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div>
<h3>CATEGORY INFORMATION</h3>
<hr></hr>
<div class="well span8 offset2">
<form:form action = "/admin/category" method = "POST" modelAttribute = "category">
	<form:hidden path="id"/>
	<label>Category Name:</label><form:input type="text" path="categoryName"/>
	<form:errors path="categoryName" style="color:red;"/>
	<label>Category Code:</label><form:input type="text" path="code"/>
	<form:errors path="code" style="color:red;"/>
	<label>Description:</label><form:textarea type="text" class="input-xlarge" path="description"/>
	<hr></hr>
	<input type="submit" value="Save Category" class="btn btn-primary"/>
</form:form>
</div>

<table class="table table-striped table-bordered table-condensed">
  <thead>
  <tr>
   <td>Category Name</td>
   <td>Category Code</td>
   <td>Description</td>
   <td>Action</td>
  </tr>
  </thead>
  <c:forEach var="category" items="${categories}">
  <tr>
    <td>${category.categoryName}</td>
    <td>${category.code}</td>
    <td>${category.description}</td>
    <td>
    <ul class="nav nav-pills" style="height:0px;">
    	<li>
    		<a href="/admin/category/edit/${category.id}"><img alt="edit" src="/images/update.png">&nbsp; Edit </a>
    	</li>
    	<li class="dropdown" data-dropdown="dropdown">
			<a href="#" class="dropdown-toggle"><img alt="delete" src="/images/delete.png">&nbsp; Remove</a>
				<ul class="dropdown-menu">
					<li><a href="/admin/category/remove/${category.id}">Delete it!</a></li>
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