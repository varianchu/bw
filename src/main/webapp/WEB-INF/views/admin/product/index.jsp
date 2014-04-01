<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_SYSTEM_MANAGER', 'CASHIER', 'TIRE_MAGS_PERSON')">

<div>
<h3>PRODUCT INFORMATION</h3>
<hr></hr>
<div class="well span12">

<form:form action = "/admin/product" method = "POST" modelAttribute = "product" enctype="multipart/form-data" class="form-horizontal">
	<form:hidden path="id"/>
	<form:hidden path="inventoryId"/>
	<form:hidden path="tireId"/>
	<form:hidden path="magsId"/>
	<form:hidden path="dataUri"/>
	<c:if test="${product.id != null || ERROR_MESSAGE != null}">
	<legend id="total">
		Total Quantity: <form:input type="text" path="totalQty" readonly="true" class="span4" style="text-align:center"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		Product Type: <c:if test = "${product.magsId != null}">Mag Wheels</c:if> <c:if test = "${product.tireId != null}">Tire</c:if> <c:if test ="${product.magsId eq null && product.tireId eq null}">Generic Product</c:if>
	</legend>
	</c:if>
	<div class="row"></div>
	<div class="span4 container" style="margin-left:-30">
	<div class="control-group">
		<label class="control-label" for="inputProductName" id="productNameLabel">Product Name:</label>
		<div class="controls">
			<form:input type="text" id="inputProductName" path="productName" placeholder="Product Name"/>
			<form:errors path="productName" style="color:red;"/>
		</div>
		<label class="control-label" for="inputUOM" style="margin-top:20">Unit of Measure:</label>
		<div class="controls" style="margin-top:20">
			<form:select id="inputUOM" items="${uoms}" path="unitOfMeasure" class=""/>
			<form:errors path="unitOfMeasure" style="color:red;"/>
		</div>
		<label class="control-label" for="inputSupplier" style="margin-top:20">Supplier:</label>
		<div class="controls" style="margin-top:20">
			<form:select id="inputSupplier" items="${suppliers}" path="supplierId" itemValue="id" onchange="getBrands()"/>
			<form:errors path="supplierId" style="color:red;"/>
		</div>
		<label class="control-label" for="inputSRP" style="margin-top:20; margin-left:10;">Suggest Retail Price: &nbsp; &#8369;</label>
		<div class="controls " style="margin-top:20">
			<form:input type="text" id="inputSRP" path="srp" class=""/>
			<form:errors path="srp" style="color:red;"/>
		</div>
		
		<label class="control-label" for="inputDescription" style="margin-top:20">Description:</label>
		<div class="controls" style="margin-top:20">
			<form:textarea type="text" class="input-xlarge" path="description" id="inputDescription" />
			<form:errors path="description" style="color:red;"/>
		</div>
		
		<c:if test="${product.id != null}">
		
		<div class="controls" style="margin-top:20;">
			<a href="/admin/barcode/${product.id}" class="btn" target="_blank">Generate Barcode</a>
		</div>
		
		</c:if>
		
	</div>
	</div>
	<div class="span3 container" style="margin-left:130">
	<div class="control-group">
		<label class="control-label" for="inputCode">Product Code:</label>
		<div class="controls">
			<form:input type="text" id="inputCode" path="code" placeholder="Code" class=""/>
			<form:errors path="code" style="color:red;"/>
		</div>
		<label class="control-label" for="inputCategory" style="margin-top:20">Category:</label>
		<div class="controls" style="margin-top:20">
			<form:select id="inputCategory" items="${categories}" path="categoryId" itemValue="id" class=""/>
			<form:errors path="categoryId" style="color:red;"/>
		</div>
		<label class="control-label" for="brand" style="margin-top:20">Brand:</label>
		<div class="controls" style="margin-top:20">
			<form:select id="brand" path="brandName" class="">
			<form:option value="0" label="---Brand---"/>
				<c:forEach var="brand" items="${brands}">
					<form:option value="${brand}"></form:option>
				</c:forEach>
			</form:select>
			<form:errors path="brandName" style="color:red;"/>
		</div>
		<label class="control-label" for="inputCost" style="margin-top:20; margin-left:10;">Cost of Good: &nbsp; &#8369;</label>
		<div class="controls" style="margin-top:20">
			<form:input type="text" id="inputCost" path="cost" class=""/>
			<form:errors path="cost" style="color:red;"/>
		</div>
		<c:if test="${(product.id != null) || (ERROR_MESSAGE != null)}">
		<label class="control-label" for="newCost" style="margin-top:20" id ="newCostLabel">New Cost of Good:</label>
		<div class="controls" style="margin-top:20">
			<form:checkbox id="newCost" path="newPrice" value="true" style="margin-top:10" onclick="isChecked(this.checked)"/>
		</div>
		</c:if>
	</div>
	</div>
<div class="row"></div>
	<hr></hr>
	<input type="button" value="Tire Fields" class="btn btn-primary" onClick="showTireFields()"/>
	<input type="button" value="MagWheel Fields" class="btn btn-primary" onClick="showMagWheelFields()"/>
	<input type="button" value="Hide Fields" class="btn btn-primary" onClick="hideFields()"/>
	<hr></hr>
	<div class="control-group" id="tires">
		<label class="control-label">Cross Section Width</label>
		<div class ="controls">
			<form:input type="text" path="crossSectionWidth"></form:input>
			<form:errors path = "crossSectionWidth" style="color:red;"/>
		</div>
		<label class="control-label">Profile</label>
		<div class ="controls">
			<form:input type="text" path="profile"></form:input>
			<form:errors path = "profile" style="color:red;"/>
		</div>
		<label class="control-label">Construction</label>
		<div class ="controls">
			<form:select path="construction" items="${tireconstruction}"></form:select>
		</div>
		<label class="control-label">Diameter</label>
		<div class ="controls">
			<form:input type="text" path="diameter"></form:input>
			<form:errors path = "diameter" style="color:red;"/>
		</div>
	</div>
	<div class="control-group" id="mags">
		<label class="control-label">Style</label>
		<div class ="controls">
			<form:input type="text" path="style"></form:input>
			<form:errors path = "style" style="color:red;"/>
		</div>
		<label class="control-label">Size</label>
		<div class ="controls">
			<form:input type="text" path="size"></form:input>
			<form:errors path = "size" style="color:red;"/>
		</div>
		<label class="control-label">Spokes</label>
		<div class ="controls">
			<form:input path="spokes" type="text"></form:input>
			<form:errors path = "spokes" style="color:red;"/>
		</div>
		<label class="control-label">Offset</label>
		<div class ="controls">
			<form:input type="text" path="offset"></form:input>
			<form:errors path = "offset" style="color:red;"/>
		</div>
		<label class="control-label">PCD</label>
		<div class ="controls">
			<form:input type="text" path="pcd"></form:input>
			<form:errors path = "pcd" style="color:red;"/>
		</div>
		<label class="control-label">Finish</label>
		<div class ="controls">
			<form:input type="text" path="finish"></form:input>
			<form:errors path = "finish" style="color:red;"/>
		</div>
		<label class="control-label">Upload Image</label>
		<div class ="controls">
			<form:input type="file" path="fileData" style="margin-top:10"></form:input>
			<form:errors path = "finish"/>
		</div>
	</div>

	<hr></hr>
	<input type="submit" value="Save Product" class="btn btn-primary"/>
</form:form>
</div>
<c:if test="${product.id != null}">
<legend>Inventory/ies of Product</legend>
<table class="table table-striped table-bordered table-condensed alert-info">
  <thead>
  <tr>
   <td>Date</td>
   <td>Quantity</td>
   <td>Cost of Good</td>
   <td>Suggested Retail Price</td>
  </tr>
  </thead>
  <c:forEach var="inventory" items="${productInventories}">
  <tr>
    <td>${inventory.date}</td>
    <td>${inventory.qty}</td>
    <td>${inventory.cost}</td>
    <td>${inventory.srp}</td>
  </tr>
  </c:forEach>
</table>
</c:if>
</div>

<script type="text/javascript">

var isChecked = $('#newCost').is(':checked');
var previousQty = $('#inputQuantity').val();
var previousCost = $('#inputCost').val();
var previousSrp = $('#inputSRP').val();

$('#newCost').click(function() {
    if( $(this).is(':checked')) {
       $('#inputQuantity').val('0.0');
       $('#inputCost').val('0.0');
       $('#inputSRP').val('0.0');
       alert('You are trying to change the cost of the good. Uncheck to return to previous state.');
    } else {
    	$('#inputQuantity').val(previousQty);
    	$('#inputCost').val(previousCost);
    	$('#inputSRP').val(previousSrp);
    	alert('Returned to previous state.');
    }
}); 

$('#mags').hide();
$('#tires').hide();

<c:if test = "${product.magsId != null}">
	$('#mags').show();
</c:if>

<c:if test = "${product.tireId != null}">
	$('#tires').show();
</c:if>

function showTireFields(){
	$('#mags').hide();
	$('#tires').show();
	var url = "/admin/enable-tire"
		
	 	$.ajax({
	 		url: url,
	 		success: function(data) {
	 			   $('.result').html(data);
	 			   $('#inputProductName').hide();
	 			   $('#productNameLabel').hide();
	 		}
	 	});

}

function showMagWheelFields(){
	$('#tires').hide();
	$('#mags').show();
	var url = "/admin/enable-mags"
		
	 	$.ajax({
	 		url: url,
	 		success: function(data) {
	 			   $('.result').html(data);
	 			   $('#inputProductName').show();
	 			   $('#productNameLabel').show();
	 		}
	 	});
	
}

function hideFields(){
	$('#mags').hide();
	$('#tires').hide();
	var url = "/admin/disable-all"
		
	 	$.ajax({
	 		url: url,
	 		success: function(data) {
	 			    $('.result').html(data);
	 			    $('#inputProductName').show();
	 			    $('#productNameLabel').show();
	 		}
	 	});
	
}

function getBrands(){
	
	var urlString = "/admin/product-brands/";
	var supplier = document.getElementById('inputSupplier');
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

</script>

</sec:authorize>

<sec:authorize access="hasAnyRole('SALES_PERSON')">

<div>
<h3>PRODUCT INFORMATION</h3>
<hr></hr>
<div class="well span12">

<form:form action = "#" modelAttribute = "product" class="form-horizontal">
	<form:hidden path="id"/>
	<form:hidden path="inventoryId"/>
	<form:hidden path="tireId"/>
	<form:hidden path="magsId"/>
	<c:if test="${product.id != null || ERROR_MESSAGE != null}">
	<legend id="total">
		Total Quantity: <form:input type="text" path="totalQty" readonly="true" class="span4" style="text-align:center"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		Product Type: <c:if test = "${product.magsId != null}">Mag Wheels</c:if> <c:if test = "${product.tireId != null}">Tire</c:if> <c:if test ="${product.magsId eq null && product.tireId eq null}">Generic Product</c:if>
	</legend>
	</c:if>
	<div class="row"></div>
	<div class="span4 container" style="margin-left:-30">
	<div class="control-group">
		<label class="control-label" for="inputProductName" id="productNameLabel">Product Name:</label>
		<div class="controls">
			<form:input type="text" id="inputProductName" path="productName" placeholder="Product Name" readonly="true"/>
			<form:errors path="productName" style="color:red;"/>
		</div>
		<label class="control-label" for="inputUOM" style="margin-top:20">Unit of Measure:</label>
		<div class="controls" style="margin-top:20">
			<form:input type="text" id="inputUOM" readonly="true" path="unitOfMeasure" class=""/>
			<form:errors path="unitOfMeasure" style="color:red;"/>
		</div>
		<label class="control-label" for="inputSupplier" style="margin-top:20">Supplier:</label>
		<div class="controls" style="margin-top:20">
			<form:select disabled="true" id="inputSupplier" items="${suppliers}" path="supplierId" itemValue="id" onchange="getBrands()"/>
			<form:errors path="supplierId" style="color:red;"/>
		</div>
		<label class="control-label" for="inputSRP" style="margin-top:20; margin-left:10;">Suggest Retail Price: &nbsp; &#8369;</label>
		<div class="controls " style="margin-top:20">
			<form:input readonly="true" type="text" id="inputSRP" path="srp" class=""/>
			<form:errors path="srp" style="color:red;"/>
		</div>
		
		<label class="control-label" for="inputDescription" style="margin-top:20">Description:</label>
		<div class="controls" style="margin-top:20">
			<form:textarea readonly="true" type="text" class="input-xlarge" path="description" id="inputDescription" />
			<form:errors path="description" style="color:red;"/>
		</div>
		
		<c:if test="${product.id != null}">
		
		<div class="controls" style="margin-top:20;">
			<a href="/admin/barcode/${product.id}" class="btn" target="_blank">Generate Barcode</a>
		</div>
		
		</c:if>
		
	</div>
	</div>
	<div class="span3 container" style="margin-left:130">
	<div class="control-group">
		<label class="control-label" for="inputCode">Unique Identifier:</label>
		<div class="controls">
			<form:input type="text" readonly="true" id="inputCode" path="code" placeholder="Code" class=""/>
			<form:errors path="code" style="color:red;"/>
		</div>
		<label class="control-label" for="inputCategory" style="margin-top:20">Category:</label>
		<div class="controls" style="margin-top:20">
			<form:select disabled="true" id="inputCategory" items="${categories}" path="categoryId" itemValue="id" class=""/>
			<form:errors path="categoryId" style="color:red;"/>
		</div>
		<label class="control-label" for="brand" style="margin-top:20">Brand:</label>
		<div class="controls" style="margin-top:20">
			<form:input type="text" readonly="true" id="brand" path="brandName" class=""/>
			<form:errors path="brandName" style="color:red;"/>
		</div>
		<label class="control-label" for="inputCost" style="margin-top:20; margin-left:10;">Code:</label>
		<div class="controls" style="margin-top:20">
			<form:input readonly="true" type="text" id="inputCost" path="convertCode" class=""/>
			<form:errors path="convertCode" style="color:red;"/>
		</div>
	</div>
	</div>
<div class="row"></div>
	<hr></hr>
	<div class="control-group" id="tires">
		<label class="control-label">Cross Section Width</label>
		<div class ="controls">
			<form:input type="text" path="crossSectionWidth"></form:input>
			<form:errors path = "crossSectionWidth" style="color:red;"/>
		</div>
		<label class="control-label">Profile</label>
		<div class ="controls">
			<form:input type="text" path="profile"></form:input>
			<form:errors path = "profile" style="color:red;"/>
		</div>
		<label class="control-label">Construction</label>
		<div class ="controls">
			<form:select path="construction" items="${tireconstruction}"></form:select>
		</div>
		<label class="control-label">Diameter</label>
		<div class ="controls">
			<form:input type="text" path="diameter"></form:input>
			<form:errors path = "diameter" style="color:red;"/>
		</div>
	</div>
	<div class="control-group" id="mags">
		<label class="control-label">Style</label>
		<div class ="controls">
			<form:input type="text" path="style"></form:input>
			<form:errors path = "style" style="color:red;"/>
		</div>
		<label class="control-label">Size</label>
		<div class ="controls">
			<form:input type="text" path="size"></form:input>
			<form:errors path = "size" style="color:red;"/>
		</div>
		<label class="control-label">Spokes</label>
		<div class ="controls">
			<form:input path="spokes" type="text"></form:input>
			<form:errors path = "spokes" style="color:red;"/>
		</div>
		<label class="control-label">Offset</label>
		<div class ="controls">
			<form:input type="text" path="offset"></form:input>
			<form:errors path = "offset" style="color:red;"/>
		</div>
		<label class="control-label">PCD</label>
		<div class ="controls">
			<form:input type="text" path="pcd"></form:input>
			<form:errors path = "pcd" style="color:red;"/>
		</div>
		<label class="control-label">Finish</label>
		<div class ="controls">
			<form:input type="text" path="finish"></form:input>
			<form:errors path = "finish" style="color:red;"/>
		</div>
		<label class="control-label">Upload Image</label>
		<div class ="controls">
			<form:input type="file" path="fileData" style="margin-top:10"></form:input>
			<form:errors path = "finish"/>
		</div>
	</div>
	<hr></hr>
</form:form>
</div>
<c:if test="${product.id != null}">
<legend>Inventory/ies of Product</legend>
<table class="table table-striped table-bordered table-condensed alert-info">
  <thead>
  <tr>
   <td>Date</td>
   <td>Quantity</td>
   <td>Cost of Good</td>
   <td>Suggested Retail Price</td>
  </tr>
  </thead>
  <c:forEach var="inventory" items="${productInventories}">
  <tr>
    <td>${inventory.date}</td>
    <td>${inventory.qty}</td>
    <td>${inventory.cost}</td>
    <td>${inventory.srp}</td>
  </tr>
  </c:forEach>
</table>
</c:if>
</div>

<script type="text/javascript">

$('#mags').hide();
$('#tires').hide();

<c:if test = "${product.magsId != null}">
	$('#mags').show();
</c:if>

<c:if test = "${product.tireId != null}">
	$('#tires').show();
</c:if>

</script>

</sec:authorize>
