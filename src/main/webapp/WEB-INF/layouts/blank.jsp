<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
	<head>
		<link type="text/css" media="screen" rel="stylesheet" href="/css/bootstrap.css"/>	
	</head>
<div class="row-fluid">
	<div class="span6">
		<h4>Product Name: ${product.productName}</h4>
		<h4>Brand: ${product.brand.brandName}</h4>
		<c:forEach var="inventory" items="${product.inventories}">
			<h4>Date of current inventory cost: ${inventory.date}<br /></h4>
			<h4>Inventory cost: ${inventory.cost}<br /></h4>
		</c:forEach>
		<img src="${barcode}" style="margin-left:150px; margin-bottom:20px;"/>
	</div>
</div>
<hr class="row">
</html>