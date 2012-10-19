<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<div>
	<ul class="nav nav-list span2 well">
		<li class="nav-header">
			Stock Management
		</li>
		<li class="divider"></li>
		<li class="">
			<a href="/admin/category">Category Information</a>
			<a href="/admin/supplier">Supplier Information</a>
			<a href="/admin/brand">Brand Information</a>
            <li class="dropdown-submenu">
                <a tabindex="-1" href="#">Product Options</a>
                	<ul class="dropdown-menu">
                  		<li><a href="/admin/product">Add New Product</a></li>
                  		<li><a href="/admin/view-products">View Products</a></li>
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
			<a href="/admin/stocktransaction">Stock In/Out</a>
			<a href="/admin/view_transactions">View Transactions</a>
			<a href="/admin/view_transactions_user">View Transactions by User</a>
		</li>
		<li class="nav-header">
			Customer Management
		</li>
		<li class="divider"></li>
		<li class="">
			<a href="/customer">Customer Information</a>
			<a href="/admin/sites/create">Car Information</a>
		</li>
	</ul>
</div>