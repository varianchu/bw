<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div>
<h3>PRODUCT INFORMATION</h3>
<!-- testing -->
<hr></hr>
<div class="well span8 offset2">
<form:form action = "/admin/product" method = "POST" modelAttribute = "product" enctype="multipart/form-data">
	<form:hidden path="id"/>
	<form:hidden path="inventoryId"/>
	<form:hidden path="tireId"/>
	<form:hidden path="magsId"/>
	<label>Product Name:</label><form:input type="text" path="productName"/>
	<form:errors path="productName" style="color:red;"/>
	<label>Product Code:</label><form:input type="text" path="code"/>
	<form:errors path="code" style="color:red;"/>
	<label>Description:</label><form:textarea type="text" class="input-xlarge" path="description"/>
	<label>Unit of Measure:</label><form:select items="${uoms}" path="unitOfMeasure"/>
	<label>Category:</label><form:select items="${categories}" path="categoryId" itemValue="id"/>
	<label>Supplier:</label><form:select id = "supplier" items="${suppliers}" path="supplierId" itemValue="id" onchange="getBrands()"/>
	<label>Brands:</label><form:select id = "brand" path="brandName">
	<form:option value="0" label="---Brand---"/>
		<c:forEach var = "brand" items="${brands}">
			<form:option value="${brand}"></form:option>
		</c:forEach>
	</form:select>
	<label>Total Quantity:</label><form:input type="text" path="totalQty" readonly="true"/>
	<label>Quantity:</label><form:input type="text" path="qty"/>
	<form:errors path="qty" style="color:red;"/>
	<label>Cost:</label><form:input type="text" path="cost"/>
	<form:errors path="cost" style="color:red;"/>
	<label>Suggest Retail Price:</label><form:input type="text" path="srp"/>
	<form:errors path="srp" style="color:red;"/>
	<label>New Price:</label><form:checkbox path="newPrice" value="true"/>
	<form:errors path="newPrice" style="color:red;"/>
	<br />
	<br />
	<label>Upload Image:</label><form:input path = "fileData" type = "file"/>
	<hr></hr>
	<input type="button" value="Tire Fields" class="btn btn-primary" onClick="showTireFields()"/>
	<input type="button" value="MagWheel Fields" class="btn btn-primary" onClick="showMagWheelFields()"/>
	<input type="button" value="Hide Fields" class="btn btn-primary" onClick="hideFields()"/>
	<hr></hr>
	<div class="control-group" id="test">
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
		<label class="control-label">Construction</label>
		<div class ="controls">
			<form:select path="construction" items="${tireconstruction}"></form:select>
		</div>
		<label class="control-label">Diameter</label>
		<div class ="controls">
			<form:input type="text" path="diameter"></form:input>
			<form:errors path = "diameter"/>
		</div>
	</div>
	<div class="control-group" id="test2">
		<label class="control-label">Style</label>
		<div class ="controls">
			<form:input type="text" path="style"></form:input>
			<form:errors path = "style"/>
		</div>
		<label class="control-label">Size</label>
		<div class ="controls">
			<form:input type="text" path="size"></form:input>
			<form:errors path = "size"/>
		</div>
		<label class="control-label">Spokes</label>
		<div class ="controls">
			<form:input path="spokes" type="text"></form:input>
			<form:errors path = "spokes"/>
		</div>
		<label class="control-label">Offset</label>
		<div class ="controls">
			<form:input type="text" path="offset"></form:input>
			<form:errors path = "offset"/>
		</div>
		<label class="control-label">PCD</label>
		<div class ="controls">
			<form:input type="text" path="pcd"></form:input>
			<form:errors path = "pcd"/>
		</div>
		<label class="control-label">Finish</label>
		<div class ="controls">
			<form:input type="text" path="finish"></form:input>
			<form:errors path = "finish"/>
		</div>
	</div>
	<hr></hr>
	<input type="submit" value="Save Product" class="btn btn-primary"/>
</form:form>
</div>
<div class="row span12 alert alert-info">
<table class="table table-striped table-bordered table-condensed data_grid">
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
    		<a href="/admin/product/edit/${product.id}"><img alt="edit" src="/images/update.png">&nbsp; Edit </a>
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

$('#test').hide();
$('#test2').hide();

function showTireFields(){
	$('#test2').hide();
	$('#test').show();
	var url = "/admin/enable-tire"
		
	 	$.ajax({
	 		url: url,
	 		success: function(data) {
	 			    $('.result').html(data);
	 		}
	 	});
}

function showMagWheelFields(){
	$('#test').hide();
	$('#test2').show();
	var url = "/admin/enable-mags"
		
	 	$.ajax({
	 		url: url,
	 		success: function(data) {
	 			    $('.result').html(data);
	 		}
	 	});
}

function hideFields(){
	$('#test').hide();
	var url = "/admin/disable-all"
		
	 	$.ajax({
	 		url: url,
	 		success: function(data) {
	 			    $('.result').html(data);
	 		}
	 	});
}

// function sendBrand(){
// 	var urlString = "/admin/setbrandname/"
// 	var e = document.getElementById("brand");
// 	var text = e.options[e.selectedIndex].text;
	
// 	var url = urlString + text;
	
// 	$.ajax({
// 		url: url,
// 		success: function(data) {
// 			    $('.result').html(data);
// 		}
// 	});
	
// }

function getBrands(){
	
	var urlString = "/admin/product-brands/";
	var supplier = document.getElementById('supplier');
	var supplierId = supplier.options[supplier.selectedIndex].value;
	
	var url = urlString + supplierId;
	
	
	
	$.ajax({
		  url: url,
		  success: function(data) {
			    $('.result').html(data);
// 			    	$('#showProductName').text(data[0]);
					var select = document.getElementById("brand");
					select.options.length = 0;
						select.options[select.options.length] = new Option("---Brand---", 0);
					for(index in data) {
					    select.options[select.options.length] = new Option(data[index], data[index]);
					}

			}
		});
}

$('table.data_grid').dataTable({
	"bJQueryUI": true,
	"sPaginationType": "full_numbers"
});

$('.dropdown-toggle').dropdown();
</script>
