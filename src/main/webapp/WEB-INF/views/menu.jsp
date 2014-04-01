<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!-- <ul class="nav"> -->
<!-- 	<li class="dropdown" data-dropdown="dropdown"> -->
<!-- 		<a href="#" class="dropdown-toggle">Stock Management</a> -->
<!-- 		<ul class="dropdown-menu"> -->
<!-- 			<li><a href="/admin/category">Category</a></li> -->
<!-- 			<li><a href="/admin/supplier">Supplier Information</a></li> -->
<!-- 			<li><a href="/admin/brand">Brand Information</a></li> -->
<!-- 			<li><a href="/admin/product">Product Information</a></li> -->
<!-- 			<li><a href="/admin/search-tires">Search Tire</a></li> -->
<!-- 			<li><a href="/admin/search-mags">Search Mag Wheels</a></li> -->
<!-- 		</ul> -->
<!-- 	</li> -->
<!-- 	<li class="dropdown" data-dropdown="dropdown"> -->
<!-- 		<a href="#" class="dropdown-toggle">Stock Transaction</a> -->
<!-- 		<ul class="dropdown-menu"> -->
<!-- 			<li><a href="/admin/stocktransaction">Inventory-In</a></li> -->
<!-- 			<li><a href="#">Inventory-Out</a></li> -->
<!-- 			<li><a href="/admin/view_transactions">View Transactions</a></li> -->
<!-- 		</ul> -->
<!-- 	</li> -->
<!-- 	<li class="dropdown" data-dropdown="dropdown"> -->
<!-- 		<a href="#" class="dropdown-toggle">Customer Management</a> -->
<!-- 		<ul class="dropdown-menu"> -->
<!-- 			<li><a href="#">Customer's Car</a></li> -->
<!-- 			<li><a href="/customer">Customer Information</a></li> -->
<!-- 		</ul> -->
<!-- 	</li> -->
<!-- 	<li class="dropdown" data-dropdown="dropdown"> -->
<!-- 		<a href="#" class="dropdown-toggle">Reports</a> -->
<!-- 		<ul class="dropdown-menu"> -->
<!-- 			<li><a href="/inventory-report">Download All Inventory Count</a></li> -->
<!-- 			<li><a href="#">Generate Report By Filter</a></li> -->
<!-- 		</ul> -->
<!-- 	</li> -->
<!-- 	<li><a href="/admin/view_products">Gallery</a></li> -->
<!-- 	<li class="dropdown" data-dropdown="dropdown"> -->
<!-- 		<a href="#" class="dropdown-toggle">Settings</a> -->
<!-- 		<ul class="dropdown-menu"> -->
<!-- 			<li><a href="#">Sales Lady</a></li> -->
<!-- 			<li><a href="#">User Management</a></li> -->
<!-- 			<li><a href="/admin/settings">Stock Settings</a> -->
<!-- 		</ul> -->
<!-- 	</li> -->
<!-- </ul> -->

<!-- <script type="text/javascript"> -->
<!-- $('#topbar').dropdown() -->
<!-- </script> -->
<div class="navbar navbar-fixed-top">
	<div class="navbar-inner">
		<div class="container">
			<a class="brand" href="${home}">Bionic Wheels System</a>
			<div class="pull-right" style="padding-top: 10px;">
					<p class="pull-right" style="color: white;">Logged in as <span id="core_username"><sec:authentication property="principal.firstname" /></span> |
					<a class="" href="/j_spring_security_logout" style="color: white"><b>Log out</b></a>
			</div>
<!-- 			<div class="nav-collapse"> -->
<!-- 			  <ul class="nav"> -->
<!-- 			    <li><a  href="/" style="color: black">Home</a></li> -->
<!-- 			    <li><a  href="/" style="color: black">Public Map</a></li> -->
<!-- 			    <li><a  href="/logs" style="color: black">Logs</a></li> -->
<!-- 			  </ul> -->
<!-- 			</div> -->
		</div>
	</div>
</div>