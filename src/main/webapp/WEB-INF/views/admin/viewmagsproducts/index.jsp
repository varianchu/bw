<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div>
<h3>SEARCH MAG WHEEL/S</h3>
<!-- testing -->
<hr></hr>
<div class="well span8">
<form:form action = "/admin/search-mags" method = "POST" modelAttribute = "mags">
	<label>Filter by:</label><form:select id = "filter" path="filterName" onchange="showFields()" class="span2">
	<form:option value="0" label="---CHOOSE FILTER---"/>
		<c:forEach var = "filtervalue" items="${filters}">
			<form:option value="${filtervalue}"></form:option>
		</c:forEach>
	</form:select>
	<div class="control-group" id="sizeAndPcd">
		<label class="control-label">Size</label>
		<div class ="controls">
			<form:input type="text" path="size"></form:input>
			<form:errors path = "size"/>
		</div>
		<label class="control-label">PCD</label>
		<div class ="controls">
			<form:input type="text" path="pcd"></form:input>
			<form:errors path = "pcd"/>
		</div>
	</div>
	<div class="control-group" id="sizeId">
		<label class="control-label">Size</label>
		<div class ="controls">
			<form:input type="text" path="size2"></form:input>
			<form:errors path = "size2"/>
		</div>
	</div>
	<div class="control-group" id="brandId">
		<label class="control-label">Brand</label>
		<div class ="controls">
			<form:select path="brandName" items="${brands}"/>
			<form:errors path = "brandName"/>
		</div>
	</div>
	<div class="control-group" id="supplier">
		<label class="control-label">Supplier</label>
		<div class ="controls">
			<form:select path="supplierName" items="${suppliers}"/>
			<form:errors path = "supplierName"/>
		</div>
	</div>
	<div class="control-group" id="pcdId">
		<label class="control-label">PCD</label>
		<div class ="controls">
			<form:input type="text" path="pcd2"></form:input>
			<form:errors path = "pcd2"/>
		</div>
	</div>
	<hr></hr>
	<input type="submit" value="Search Mag Wheels" class="btn btn-primary"/>
</form:form>
</div>
<div class="row span8 alert alert-info">
<table class="table table-striped table-bordered table-condensed data_grid">
  <thead>
  <tr>
   <td>Product Name</td>
   <td>Brand</td>
   <td>Size and PCD</td>
   <td>Quantity</td>
   <td>Finish</td>
   <td>Action</td>
  </tr>
  </thead>
  <tbody>
  <c:forEach var="mags" items="${magwheels}">
  <tr>
    <td>${mags.product.productName}</td>
    <td>${mags.product.brand.brandName}</td>
    <td>${mags}</td>
    <td>${mags.product.totalQty}</td>
    <td>${mags.finish}</td>
    <td>
    <ul class="nav nav-pills" style="height:0px;">
    	<li>
    		<a href="#">View Product</a>
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

$('#sizeAndPcd').hide();
$('#sizeId').hide();
$('#brandId').hide();
$('#supplier').hide();
$('#pcdId').hide();
$('#filter').val('0').attr('selected',true);

function showFields(){
	var filter = document.getElementById('filter');
	var filtername = filter.options[filter.selectedIndex].innerHTML;
	
	
	if(filtername == "SIZE_PCD"){
		$('#sizeId').hide();
		$('#brandId').hide();
		$('#supplier').hide();
		$('#pcdId').hide();
		$('#sizeAndPcd').show();
	}
	else if(filtername == "SIZE"){
		$('#brandId').hide();
		$('#supplier').hide();
		$('#pcdId').hide();
		$('#sizeAndPcd').hide();
		$('#sizeId').show();

	}
	else if(filtername == "BRAND"){
		$('#sizeAndPcd').hide();
		$('#sizeId').hide();	
		$('#supplier').hide();
		$('#pcdId').hide();
		$('#brandId').show();
	}
	else if(filtername == "SUPPLIER"){
		$('#sizeAndPcd').hide();
		$('#sizeId').hide();
		$('#brandId').hide();		
		$('#pcdId').hide();
		$('#supplier').show();
	}
	else if(filtername == "PCD"){
		$('#sizeAndPcd').hide();
		$('#sizeId').hide();
		$('#brandId').hide();
		$('#supplier').hide();
		$('#pcdId').show();
	}
	else{
		$('#sizeAndPcd').hide();
		$('#sizeId').hide();
		$('#brandId').hide();
		$('#supplier').hide();
		$('#pcdId').hide();
		alert('choose an option');
	}
	
	
}

$('table.data_grid').dataTable({
	"bJQueryUI": true,
	"sPaginationType": "full_numbers"
});

</script>
