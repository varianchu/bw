<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div>
<h3>VIEW PRODUCTS</h3>
<!-- testing -->
<hr></hr>
<div class="well span8">
<table class="table table-striped table-bordered table-condensed data_grid alert-info">
  <thead>
  <tr>
   <td>Product Name</td>
   <td>Product Code</td>
   <td>Category</td>
   <td>Supplier</td>
<!--    <td>Description</td> -->
   <td>Quantity</td>
   <td>Unit of Measure</td>
   <td>Action</td>
  </tr>
  </thead>
  <tbody>
  <c:forEach var="product" items="${products}">
  <tr>
    <td>${product.productName}</td>
    <td>${product.code}</td>
    <td>${product.category}</td>
    <td>${product.supplier}</td>
<%--     <td>${product.description}</td> --%>
    <td>${product.totalQty}</td>
    <td>${product.unitOfMeasure}</td>
    <td>
    <ul class="nav nav-pills" style="height:0px;">
    	<li>
    		<a href="/admin/product/edit/${product.id}"><img alt="edit" src="/images/update.png">&nbsp; Edit/View </a>
    	</li>
    	<li class="dropdown" data-dropdown="dropdown">
			<a href="#" class="dropdown-toggle"><img alt="delete" src="/images/delete.png">&nbsp; Remove</a>
				<ul class="dropdown-menu">
					<li><a href="/admin/product/remove/${product.id}">Delete it!</a></li>
					<li><a href="#">Cancel</a></li>
				</ul>
		</li>
	</ul>
	</td>
  </tr>
  </c:forEach>
  </tbody>
 </table> 
</div>
</div>

<script type="text/javascript">
$('table.data_grid').dataTable({
	"bJQueryUI": true,
	"sPaginationType": "full_numbers"
});

$('.dropdown-toggle').dropdown();
</script>