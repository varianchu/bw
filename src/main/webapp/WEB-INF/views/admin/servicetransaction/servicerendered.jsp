<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div>
<h3>SERVICE RENDERED INFORMATION</h3>
<hr></hr>
<div class="well span8">
<form:form action = "/admin/service-rendered/form" method = "POST" modelAttribute = "serviceRenderedForm">
	<form:hidden path="id"/>
	<label>Service Made/Part Name:</label><form:input type="text" path="serviceMadePart" placeholder="Service Rendered"/>
	<form:errors path="serviceMadePart" style="color:red;"/>
	<hr></hr>
	<input type="submit" value="Save Service Rendered" class="btn btn-primary"/>
</form:form>
</div>

<table class="table table-striped table-bordered table-condensed alert-info">
  <thead>
  <tr>
   <td>Service Rendered ID</td>
   <td>Service Made/Part</td>
   <td>Action</td>
  </tr>
  </thead>
  <c:forEach var="serviceRendered" items="${servicesRendered}">
  <tr>
    <td>${serviceRendered.id}</td>
    <td>${serviceRendered.serviceMadePart}</td>
    <td>
    <ul class="nav nav-pills" style="height:0px;">
    	<li>
    		<a href="/admin/service-rendered/edit/${serviceRendered.id}"><img alt="edit" src="/images/update.png">&nbsp; Edit </a>
    	</li>
		<li class="dropdown">
			<a href="#" class="dropdown-toggle" data-toggle="dropdown">Remove <b class="caret"></b></a>
            	<ul class="dropdown-menu">
					<li><a href="/admin/service-rendered/remove/${serviceRendered.id}">Delete it!</a></li>
					<li><a href="#">Cancel</a></li>
				</ul>
       	</li>
	</ul>
	</td>
  </tr>
  </c:forEach>
</table>
</div>