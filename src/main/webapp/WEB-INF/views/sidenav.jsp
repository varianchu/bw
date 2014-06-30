<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<sec:authorize access="hasRole('ROLE_ADMIN')">
<div>
	<ul class="nav nav-list well">
		<li class="nav-header">
			Stock Management
		</li>
		<li class="divider"></li>
		<li class="">
			<a href="/admin/category/index">Category Information</a>
			<a href="/admin/supplier/index">Supplier Information</a>
			<a href="/admin/brand/index">Brand Information</a>
            <li class="dropdown-submenu">
                <a tabindex="-1" href="#">Product Options</a>
                	<ul class="dropdown-menu">
                  		<li><a href="/admin/product/index">Add New Product</a></li>
                  		<li><a href="/admin/view-products">View Products</a></li>
                  		<li><a href="/admin/generate/batch-barcode">Generate Batch Barcode</a></li>
                	</ul>
            </li>
            <li class="">
			<a href="/search-tires">Search Tire</a>
			<a href="/view-purchased-tires-today">View Tire Transactions Today</a>
			<a href="/search-mags">Search Mag Wheels</a>
			<a href="/view-purchased-magwheels-today">View Mag Wheel Transactions Today</a>
		</li>
		
		<li class="nav-header">
			Stock Transaction
		</li>
		<li class="divider"></li>
		<li class="">
			<a href="/stocktransaction-out">Stock/Inventory Out</a>
			<a href="/stocktransaction-in">Stock/Inventory In</a>
<!-- 			<a href="/stocktransaction-refund">Refund Item (or Override)</a> -->
			<a href="/admin/view-transactions">View Transactions</a>
			<a href="/admin/view-transactions-user">View Transactions by User</a>
			<a href="/admin/view-product-transaction-history">View Product Transaction History</a>
		</li>
		<li class="nav-header">
			Reports
		</li>
		<li class="divider"></li>
		<li class="">
			<a href="/admin/reports">Generate Report by Filter</a>
		</li>
		<li class="nav-header">
			Customer Management
		</li>
		<li class="divider"></li>
		<li class="">
			<a href="/customer">Customer Information</a>
			<a href="#">Car Information</a>
		</li>
		<li class="nav-header">
			Service Transaction/s
		</li>
		<li class="divider"></li>
		<li class="">
			<a href="/admin/service-transaction/form">Service Transaction Form</a>
			<a href="/admin/view-service-transactions">View Service Transactions</a>
			<a href="/admin/view-service-transactions-user">View Service Transactions by Sales Representative</a>
			<a href="/admin/view-service-transactions-worker">View Service Transactions by Specialist</a>
		</li>
		<li class="nav-header">
			A/R Management
		</li>
		<li class="divider"></li>
		<li class="">
			<a href="/admin/accounts-receivable/form">New Accounts Receivable</a>
			<a href="/admin/accounts-receivable">View Due Accounts Receivable</a>
		</li>
		<li class="nav-header">
			Settings
		</li>
		<li class="divider"></li>
		<li class="">
			<a href="/admin/settings">Stock Settings</a>
			<a href="/admin/user/create">Create User Account</a>
			<a href="/admin/users">User Accounts Management</a>
			<a href="/admin/mechanic-tireman/index">Mechanic/Tire Man Management</a>
		</li>
	</ul>
</div>
</sec:authorize>

<sec:authorize access="hasRole('SALES_PERSON')">
<div>
	<ul class="nav nav-list well">
		<li class="nav-header">
			Stock Management
		</li>
		<li class="divider"></li>
		<li class="">
            <li class="dropdown-submenu">
                <a tabindex="-1" href="#">Product Options</a>
                	<ul class="dropdown-menu">
                  		<li><a href="/admin/view-products">View Products</a></li>
                  		<li><a href="/admin/generate/batch-barcode">Generate Batch Barcode</a></li>
                	</ul>
            </li>
       <li class="">
			<a href="/search-tires">Search Tire</a>
			<a href="/search-mags">Search Mag Wheels</a>
		</li>
		
		<li class="nav-header">
			Stock Transaction
		</li>
		<li class="divider"></li>
		<li class="">
			<a href="/stocktransaction-out">Stock/Inventory Out</a>
			<a href="/stocktransaction-in">Stock/Inventory In</a>
<!-- 			<a href="/stocktransaction-refund">Refund Item (or Override)</a> -->
			<a href="/view-transactions-user">View Transactions by User</a>
		</li>
	</ul>
</div>
</sec:authorize>

<sec:authorize access="hasRole('TIRE_MAGS_PERSON')">
<div>
	<ul class="nav nav-list well">
		<li class="nav-header">
			Stock Management
		</li>
		<li class="divider"></li>
		<li class="">
		<a href="/admin/brand/index">Brand Information</a>
		<li class="">
            <li class="dropdown-submenu">
                <a tabindex="-1" href="#">Product Options</a>
                	<ul class="dropdown-menu">
                		<li><a href="/admin/product/index">Add New Product</a></li>
                  		<li><a href="/admin/view-products">View Products</a></li>
                  		<li><a href="/admin/generate/batch-barcode">Generate Batch Barcode</a></li>
                	</ul>
            </li>
       <li class="">
			<a href="/search-tires">Search Tire</a>
			<a href="/view-purchased-tires-today">View Tire Transactions Today</a>
			<a href="/search-mags">Search Mag Wheels</a>
			<a href="/view-purchased-magwheels-today">View Mag Wheel Transactions Today</a>
		</li>
		
		<li class="nav-header">
			Stock Transaction
		</li>
		<li class="divider"></li>
		<li class="">
			<a href="/stocktransaction-out">Stock/Inventory Out</a>
			<a href="/stocktransaction-in">Stock/Inventory In</a>
<!-- 			<a href="/stocktransaction-refund">Refund Item (or Override)</a> -->
			<a href="/view-transactions-user">View Transactions by User</a>
		</li>
	</ul>
</div>
</sec:authorize>

<sec:authorize access="hasRole('ROLE_SYSTEM_MANAGER')">
<div>
	<ul class="nav nav-list well">
		<li class="nav-header">
			Stock Management
		</li>
		<li class="divider"></li>
		<li class="">
			<a href="/admin/category/index">Category Information</a>
			<a href="/admin/supplier/index">Supplier Information</a>
			<a href="/admin/brand/index">Brand Information</a>
            <li class="dropdown-submenu">
                <a tabindex="-1" href="#">Product Options</a>
                	<ul class="dropdown-menu">
                  		<li><a href="/admin/product/index">Add New Product</a></li>
                  		<li><a href="/admin/view-products">View Products</a></li>
                  		<li><a href="/admin/generate/batch-barcode">Generate Barcode by Category</a></li>
                	</ul>
            </li>
            <li class="">
			<a href="/admin/search-tires">Search Tire</a>
			<a href="/admin/search-mags">Search Mag Wheels</a>
		</li>
		
		<li class="nav-header">
			Stock Transaction
		</li>
		<li class="divider"></li>
		<li class="">
			<a href="/stocktransaction-out">Stock/Inventory Out</a>
			<a href="/stocktransaction-in">Stock/Inventory In</a>
<!-- 			<a href="/stocktransaction-refund">Refund Item (or Override)</a> -->
			<a href="/view-transactions-user">View Transactions by User</a>
			<a href="/admin/view-product-transaction-history">View Product Transaction History</a>
		</li>
		<li class="nav-header">
			A/R Management
		</li>
		<li class="divider"></li>
		<li class="">
			<a href="/admin/accounts-receivable/form">New Accounts Receivable</a>
			<a href="/admin/accounts-receivable">View Due Accounts Receivable</a>
		</li>
		<li class="nav-header">
			Settings
		</li>
		<li class="divider"></li>
		<li class="">
			<a href="/admin/mechanic-tireman/index">Mechanic/Tire Man Management</a>
		</li>
	</ul>
</div>
</sec:authorize>

<sec:authorize access="hasRole('CASHIER')">
<div>
	<ul class="nav nav-list well">
		<li class="nav-header">
			Stock Management
		</li>
		<li class="divider"></li>
		<li class="">
			<a href="/admin/category/index">Category Information</a>
			<a href="/admin/supplier/index">Supplier Information</a>
			<a href="/admin/brand/index">Brand Information</a>
            <li class="dropdown-submenu">
                <a tabindex="-1" href="#">Product Options</a>
                	<ul class="dropdown-menu">
                  		<li><a href="/admin/product/index">Add New Product</a></li>
                  		<li><a href="/admin/view-products">View Products</a></li>
                  		<li><a href="/admin/generate/batch-barcode">Generate Barcode by Category</a></li>
                	</ul>
            </li>
            <li class="">
			<a href="/admin/search-tires">Search Tire</a>
			<a href="/admin/search-mags">Search Mag Wheels</a>
		</li>
		
		<li class="nav-header">
			Stock Transaction
		</li>
		<li class="divider"></li>
		<li class="">
			<a href="/stocktransaction-out">Stock/Inventory Out</a>
			<a href="/stocktransaction-in">Stock/Inventory In</a>
<!-- 			<a href="/stocktransaction-refund">Refund Item (or Override)</a> -->
			<a href="/view-transactions-user">View Transactions by User</a>
			<a href="/admin/view-product-transaction-history">View Product Transaction History</a>
		</li>
		<li class="nav-header">
			A/R Management
		</li>
		<li class="divider"></li>
		<li class="">
			<a href="/admin/accounts-receivable/form">New Accounts Receivable</a>
			<a href="/admin/accounts-receivable">View Due Accounts Receivable</a>
		</li>
		<li class="nav-header">
			Customer Management
		</li>
		<li class="divider"></li>
		<li class="">
			<a href="/customer">Customer Information</a>
			<a href="#">Car Information</a>
		</li>
		<li class="nav-header">
			Service Transaction/s
		</li>
		<li class="divider"></li>
		<li class="">
			<a href="/admin/service-transaction/form">Service Transaction Form</a>
			<a href="/admin/view-service-transactions">View Service Transactions</a>
			<a href="/admin/view-service-transactions-user">View Service Transactions by Sales Representative</a>
			<a href="/admin/view-service-transactions-worker">View Service Transactions by Specialist</a>
		</li>
	</ul>
</div>
</sec:authorize>

<sec:authorize access="hasRole('SERVICE_MANAGER')">
<div>
	<ul class="nav nav-list well">
		<li class="nav-header">
			Service Transaction/s
		</li>
		<li class="divider"></li>
		<li class="">
			<a href="/service-transaction/form">Service Transaction Form</a>
			<a href="/admin/view-service-transactions">View Service Transactions</a>
			<a href="/view-service-transactions-user">View Service Transactions by Sales Representative</a>
			<a href="/admin/view-service-transactions-worker">View Service Transactions by Specialist</a>
		</li>
	</ul>
</div>
</sec:authorize>
