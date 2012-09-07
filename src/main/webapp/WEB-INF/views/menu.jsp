<ul class="nav">
	<li class="dropdown" data-dropdown="dropdown">
		<a href="#" class="dropdown-toggle">Stock Management</a>
		<ul class="dropdown-menu">
			<li><a href="/admin/category">Category</a></li>
			<li><a href="/admin/supplier">Supplier Information</a></li>
			<li><a href="/admin/brand">Brand Information</a></li>
			<li><a href="/admin/product">Product Information</a></li>
		</ul>
	</li>
	<li class="dropdown" data-dropdown="dropdown">
		<a href="#" class="dropdown-toggle">Stock Transaction</a>
		<ul class="dropdown-menu">
			<li><a href="/admin/stocktransaction">Inventory-In</a></li>
			<li><a href="#">Inventory-Out</a></li>
			<li><a href="/admin/view_transactions">View Transactions</a></li>
		</ul>
	</li>
	<li class="dropdown" data-dropdown="dropdown">
		<a href="#" class="dropdown-toggle">Customer Management</a>
		<ul class="dropdown-menu">
			<li><a href="#">Customer's Car</a></li>
			<li><a href="#">Customer Information</a></li>
		</ul>
	</li>
	<li class="dropdown" data-dropdown="dropdown">
		<a href="#" class="dropdown-toggle">Reports</a>
		<ul class="dropdown-menu">
			<li><a href="#">Download Inventory Count</a></li>
			<li><a href="#">Test</a></li>
		</ul>
	</li>
	<li><a href="admin/view_products">Gallery</a></li>
	<li class="dropdown" data-dropdown="dropdown">
		<a href="#" class="dropdown-toggle">Settings</a>
		<ul class="dropdown-menu">
			<li><a href="#">Sales Lady</a></li>
			<li><a href="#">User Management</a></li>
			<li><a href="/admin/settings">Stock Settings</a>
		</ul>
	</li>
</ul>

<script type="text/javascript">
$('#topbar').dropdown()
</script>