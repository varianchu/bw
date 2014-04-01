<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div>
<h3>SEARCH TIRE/S</h3>
<!-- testing -->
<hr></hr>
<div class="well span8">
<form:form action = "/search-tires" method = "POST" modelAttribute = "tire">
<%-- 	<form:hidden path="id"/> --%>
<%-- 	<label>Supplier:</label><form:select id = "filter" items="${filters}" path="filterName" onchange="showFields()"/> --%>
	<label>Filter by:</label><form:select id = "filter" path="filterName" onchange="showFields()" class="">
	<form:option value="0" label="---CHOOSE FILTER---"/>
		<c:forEach var = "filtervalue" items="${filters}">
			<form:option value="${filtervalue}"></form:option>
		</c:forEach>
	</form:select>
	<div class="control-group" id="cswpd">
		<label class="control-label">Cross Section Width</label>
		<div class ="controls">
			<form:input type="text" path="crossSectionWidth"></form:input>
			<form:errors path = "crossSectionWidth"/>
		</div>
		<label class="control-label">Profile</label>
		<div class ="controls">
			<form:input type="text" path="profile"></form:input>
			<form:errors path = "profile"/>
		</div>
		<label class="control-label">Diameter</label>
		<div class ="controls">
			<form:input type="text" path="diameter"></form:input>
			<form:errors path = "diameter"/>
		</div>
	</div>
	<div class="control-group" id="brandqty">
		<label class="control-label">Brand</label>
		<div class ="controls">
			<form:select path="brandName" items="${brands}"/>
			<form:errors path = "brandName"/>
		</div>
		<label class="control-label">Greater than Quantity</label>
		<div class ="controls">
			<form:input type="text" path="qty"></form:input>
			<form:errors path = "qty"/>
		</div>
	</div>
	<div class="control-group" id="brand">
		<label class="control-label">Brand</label>
		<div class ="controls">
			<form:select path="brandName2" items="${brands}"/>
			<form:errors path = "brandName2"/>
		</div>
	</div>
	<div class="control-group" id="supplier">
		<label class="control-label">Supplier</label>
		<div class ="controls">
			<form:select path="supplierName" items="${suppliers}"/>
			<form:errors path = "supplierName"/>
		</div>
	</div>
	<div class="control-group" id="cross">
		<label class="control-label">Cross Section Width</label>
		<div class ="controls">
			<form:input type="text" path="crossSectionWidth2"></form:input>
			<form:errors path = "crossSectionWidth2"/>
		</div>
	</div>
	<div class="control-group" id="pro">
		<label class="control-label">Profile</label>
		<div class ="controls">
			<form:input type="text" path="profile2"></form:input>
			<form:errors path = "profile2"/>
		</div>
	</div>
	<div class="control-group" id="dia">
		<label class="control-label">Diameter</label>
		<div class ="controls">
			<form:input type="text" path="diameter2"></form:input>
			<form:errors path = "diameter2"/>
		</div>
	</div>
	<hr></hr>
	<input type="submit" value="Search Tire" class="btn btn-primary"/>
</form:form>
</div>
<div class="row span12 alert alert-info">
<table class="table table-striped table-bordered table-condensed data_grid">
  <thead>
  <tr>
   <td>Product Name</td>
   <td>Brand</td>
   <td>Specific Tire Size</td>
   <td>Quantity</td>
   <td>Action</td>
  </tr>
  </thead>
  <tbody>
  <c:forEach var="tire" items="${tires}">
  <tr>
    <td>${tire.product.productName}</td>
    <td>${tire.product.brand.brandName}</td>
    <td>${tire}</td>
    <td>${tire.product.totalQty}</td>
    <td>
    <ul class="nav nav-pills" style="height:0px;">
    	<li>
    		<a href="/admin/product/edit/${tire.product.id}">View Product</a>
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

$('#cswpd').hide();
$('#brandqty').hide();
$('#brand').hide();
$('#supplier').hide();
$('#cross').hide();
$('#pro').hide();
$('#dia').hide();
$('#filter').val('0').attr('selected',true);


function showFields(){
	var filter = document.getElementById('filter');
	var filtername = filter.options[filter.selectedIndex].innerHTML;
	
	
	if(filtername == "CrossSectionWidth_Profile_Diameter"){
		$('#brandqty').hide();
		$('#brand').hide();
		$('#supplier').hide();
		$('#cross').hide();
		$('#pro').hide();
		$('#dia').hide();
		$('#cswpd').show();
	}
	else if(filtername == "Brand_Qty"){
		$('#cswpd').hide();
		$('#brand').hide();
		$('#supplier').hide();
		$('#cross').hide();
		$('#pro').hide();
		$('#dia').hide();
		$('#brandqty').show();
	}
	else if(filtername == "Brand"){
		$('#brandqty').hide();
		$('#cswpd').hide();
		$('#supplier').hide();
		$('#cross').hide();
		$('#pro').hide();
		$('#dia').hide();
		$('#brand').show();
	}
	else if(filtername == "Supplier"){
		$('#cswpd').hide();
		$('#brandqty').hide();
		$('#brand').hide();
		$('#cross').hide();
		$('#pro').hide();
		$('#dia').hide();
		$('#supplier').show();
	}
	else if(filtername == "CrossSectionWidth"){
		$('#cswpd').hide();
		$('#brandqty').hide();
		$('#brand').hide();
		$('#supplier').hide();
		$('#pro').hide();
		$('#dia').hide();
		$('#cross').show();
	}
	else if(filtername == "Profile"){
		$('#cswpd').hide();
		$('#brandqty').hide();
		$('#brand').hide();
		$('#supplier').hide();
		$('#cross').hide();
		$('#dia').hide();
		$('#pro').show();
	}
	else if(filtername == "Diameter"){
		$('#cswpd').hide();
		$('#brandqty').hide();
		$('#brand').hide();
		$('#supplier').hide();
		$('#cross').hide();
		$('#pro').hide();
		$('#dia').show();
	}
	else{
		$('#cswpd').hide();
		$('#brandqty').hide();
		$('#brand').hide();
		$('#supplier').hide();
		$('#cross').hide();
		$('#pro').hide();
		$('#dia').hide();
		alert('choose an option');
	}
	
	
}

$('table.data_grid').dataTable({
	"bJQueryUI": true,
	"sPaginationType": "full_numbers"
});

</script>
