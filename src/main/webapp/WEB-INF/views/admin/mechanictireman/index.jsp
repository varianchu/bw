<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div>
<h3>MECHANIC/TIRE MAN INFORMATION</h3>
<hr></hr>
<div class="well span8">
<form:form action = "/admin/mechanic-tireman" method = "POST" modelAttribute = "mechanicTireMan">
	<form:hidden path="id"/>
	<label>Mechanic or Tire Man Name:</label><form:input type="text" path="mechanicTireManName" placeholder="Full Name"/>
	<form:errors path="mechanicTireManName" style="color:red;"/>
	<label class="control-label" for="job" style="margin-top:20">Specialization:</label>
		<div class="controls" style="margin-top:20">
			<form:select id="job" items="${jobDescription}" path="jobDescription" class=""/>
			<form:errors path="jobDescription" style="color:red;"/>
		</div>
	<hr></hr>
	<input type="submit" value="Save Worker" class="btn btn-primary"/>
</form:form>
</div>

<table class="table table-striped table-bordered table-condensed alert-info">
  <thead>
  <tr>
   <td>Worker Name</td>
   <td>Specialization</td>
   <td>Action</td>
  </tr>
  </thead>
  <c:forEach var="mt" items="${mechanicTireManAll}">
  <tr>
    <td>${mt.mechanicTireManName}</td>
    <td>${mt.jobDescription}</td>
    <td>
    <ul class="nav nav-pills" style="height:0px;">
    	<li>
    		<a href="/admin/mechanic-tireman/edit/${mt.id}"><img alt="edit" src="/images/update.png">&nbsp; Edit </a>
    	</li>
		<li class="dropdown">
			<a href="#" class="dropdown-toggle" data-toggle="dropdown">Remove <b class="caret"></b></a>
            	<ul class="dropdown-menu">
					<li><a href="/admin/mechanic-tireman/remove/${mt.id}">Delete it!</a></li>
					<li><a href="#">Cancel</a></li>
				</ul>
       	</li>
	</ul>
	</td>
  </tr>
  </c:forEach>
</table>
</div>