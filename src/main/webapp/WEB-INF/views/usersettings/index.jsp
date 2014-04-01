<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div>
<h3>USER ACCOUNTS</h3>
<hr></hr>
<div class="well span11">
	<table class="table table-striped table-bordered table-condensed alert-info">
  		<thead>
  			<tr>
   				<td>First Name</td>
   				<td>Last Name</td>
   				<td>Email</td>
   				<td>Role</td>
   				<td>Action</td>
  			</tr>
  		</thead>
  		<c:forEach var="user" items="${users}">
  			<tr>
    			<td>${user.firstName}</td>
    			<td>${user.lastName}</td>
    			<td>${user.email}</td>
    			<td>${user.role}</td>
    			<td>
    			<ul class="nav nav-pills" style="height:0px;">
    				<li>
    					<a href="/admin/user/edit/${user.id}"><img alt="edit" src="/images/update.png">&nbsp; Edit </a>
    				</li>
    				<li class="dropdown" data-dropdown="dropdown">
						<a href="#" class="dropdown-toggle"><img alt="delete" src="/images/delete.png">&nbsp; Remove</a>
							<ul class="dropdown-menu">
								<li><a href="/admin/user/remove/${user.id}">Delete it!</a></li>
								<li><a href="#">Cancel</a></li>
							</ul>
					</li>
				</ul>
				</td>
  			</tr>
  		</c:forEach>
	</table>
</div>

</div>