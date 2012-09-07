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
	<label>Product Name:</label><form:input type="text" path="productName"/>
	<form:errors path="productName" style="color:red;"/>
	<label>Product Code:</label><form:input type="text" path="code"/>
	<form:errors path="code" style="color:red;"/>
	<label>Description:</label><form:textarea type="text" class="input-xlarge" path="description"/>
	<label>Unit of Measure:</label><form:select items="${uoms}" path="unitOfMeasure"/>
	<label>Category:</label><form:select items="${categories}" path="categoryId" itemValue="id"/>
	<label>Supplier:</label><form:select id = "supplier" items="${suppliers}" path="supplierId" itemValue="id" onchange="getBrands()"/>
	<label>Brands:</label><form:select id = "brand" onchange="sendBrand()" path="brandName">
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
function sendBrand(){
	var urlString = "/admin/setbrandname/"
	var e = document.getElementById("brand");
	var text = e.options[e.selectedIndex].text;
	
	var url = urlString + text;
	
	$.ajax({
		url: url,
		success: function(data) {
			    $('.result').html(data);
		}
	});
	
}

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
