<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div>
<h3>INVENTORY IN TRANSACTION</h3>
<c:if test="${not empty headsup}">
<div class="well btn-info" id="messageBox">
<c:forEach var="message" items="${headsup}">
	${message}<br />
</c:forEach>
</div>
</c:if>
<hr></hr>

<div class="well span4">
<!-- changed to product code from product id -->
<table>
<tr>
	<td><label>Product ID:</label></td>
	<td>&nbsp;&nbsp;<input id="productId" onkeypress="return isNumberKey(event)" onchange="getProductInfo()" class="span10"/ autofocus></td>
</tr>
<tr>
	<td><label>Quantity to Add:</label></td>
	<td>&nbsp;&nbsp;<input id="productQty" onkeypress="return isNumberKeyDecimal(event)" class="span10"/></td>
</tr>
</table>
<hr></hr>
<button class="btn btn-primary" id="addProductForTransaction" type="button">Add Product to Transaction</button>
</div>

<div class="well span7" id="productInfo">
	<h4>Product Information</h4>
	<table class="table table-bordered table-condensed">
		<tr>
			<td><label>Product Name: </label></td>
			<td><label id="showProductName" style="font-weight:bold;"></label></td>
		</tr>
		<tr>
			<td><label>Product Code: </label></td>
			<td><label id="showProductCode" style="font-weight:bold;"></label></td>
		</tr>
		<tr>
			<td><label>Quantity in Stock:</label></td>
			<td><label id="showProductQty" style="font-weight:bold;"></label></td>
		</tr>
  		<tr>
  			<td><label>Unit of Measure:</label></td>
  			<td><label id="showProductUOM" style="font-weight:bold;"></label></td>
  		</tr>
  		<tr>
  			<td><label>Cost of Good: </label></td>
  			<td>Php&nbsp;&nbsp;<input type="text" class="span10" id="showProductCost" readonly="readonly" style="font-weight:bold;"/></td>
  		</tr>
  		<tr>
  			<td><label>Suggested Retail Price: </label></td>
  			<td>Php&nbsp;&nbsp;<input type="text" class="span10" id="showProductSale" readonly="readonly" style="font-weight:bold;"/></td>
  		</tr>
  	</table>
</div>

<div class="row"></div>
<div class="well">

<form:form action = "/stocktransaction-in" method = "POST" modelAttribute = "transaction">
	<form:hidden path="id"/>
	<label>Transaction Date:</label><form:input type="text" path="dateCreatedValue" id="ButtonCreationDemoInput" value="${getDate}"/><button id="ButtonCreationDemoButton" type="button">
    <img src="/images/calendar.png" alt="[calendar icon]"/>
  </button>
<%-- 	<label>Transaction Type:</label><form:select type="text" path="transactionType" id="transactionOption" items="${transactionType}"/> --%>
	<label>Reference Number:</label><form:input type="text" path="referenceNumber" value="${referenceNumber}"/>
	<br />
	<form:errors path="referenceNumber" style="color:red;"/>
	<hr></hr>
	<table class="table table-bordered table-condensed" id="myData">
	<thead>
		<tr>
			<td>Product Name</td>
			<td>Product Code</td>
			<td>Product Quantity</td>
			<td>Unit of Measure</td>
			<td>Price per Stock</td>
			<td>Action</td>
		</tr>
	</thead>
	<tbody>
		<tr>
		</tr>
	</tbody>
	</table>
	<hr></hr>
	<input type="submit" value="Save Transaction" class="btn btn-primary"/>
</form:form>

</div>

<script type="text/javascript">

function isNumberKey(evt)
{
   var charCode = (evt.which) ? evt.which : event.keyCode
   if (charCode > 31 && (charCode < 48 || charCode > 57))
      return false;

   return true;
}

function isNumberKeyDecimal(evt)
{
   var charCode = (evt.which) ? evt.which : event.keyCode;
   if (charCode != 46 && charCode > 31 
     && (charCode < 48 || charCode > 57))
      return false;

   return true;
}

// $.getJSON();

var totalSrp = 0.0;

$('#ButtonCreationDemoButton').click(
    	      function(e) {
    	        $('#ButtonCreationDemoInput').AnyTime_noPicker().AnyTime_picker({format: "%Y-%m-%d %H:%i"}).focus();
    	        e.preventDefault();
    	      } );

function getProductInfo(){
	
	var urlString = "/getproductintransaction/";
	var productIdString = document.getElementById('productId').value;
	var url = urlString + productIdString;
	
	$.ajax({
		  url: url,
		  success: function(data) {
			    $('.result').html(data);
			    	$('#showProductName').text(data[0]);
			    	$('#showProductCode').text(data[1]);
			    	$('#showProductQty').text(data[2]);
			    	$('#showProductUOM').text(data[3]);
			    	$('#showProductCost').val(data[4]);
			    	$('#showProductSale').val(data[5]);
// 			    	$('#myModal').modal();
			    	$('#productQty').val("1.0");
			}
		});
}

$("#addProductForTransaction").click(function(){
	
	var price = $('#showProductSale').val();
	var cost = $('#showProductCost').text();
	
	if(($("#productId").val().length != '') && ($("#productQty").val().length != '')){
//		ajax
		var urlString ="/addtransactionproduct/";
		var productIdString = document.getElementById('productId').value + "/";
		var productIdStringNoSlash = document.getElementById('productId').value;
		var productQtyString = document.getElementById('productQty').value + "/";
		var productQtyStringNoSlash = document.getElementById('productQty').value;
		var productSaleString = document.getElementById('showProductSale').value + "/";
		var productSaleStringNoSlash = document.getElementById('showProductSale').value;
	
		var url = urlString + productIdString + productQtyString + productSaleString;
		
// 		alert(url);
		
// 		table formation
		var TABLE = document.getElementById('myData');
    	
		$.ajax({
			  url: url,
			  success: function(data) {
				    $('.result').html(data);
// 				    add for loop here to add new rows in the table
				    if(data[0]!="Error"){
						
						//new start
						
						for(var i = 0; i < data.length; i+=5){
							
// 							if(data[1+i]="0.0"){
								
// 							}
// 							else{
							
								var BODY = TABLE.getElementsByTagName('tbody')[0];
								var TR = document.createElement('tr');
								var TD = document.createElement('td');
						    	var TD2 = document.createElement('td');
						    	var TD3 = document.createElement('td');
						    	var TD4 = document.createElement('td');
						    	var TD5 = document.createElement('td');
						    	var TD6 = document.createElement('td')
						    	var lastRow = TABLE.rows.length;
						    	var deleteButton = document.createElement('button');
						    		deleteButton.setAttribute("class", "btn btn-danger");
						    		deleteButton.setAttribute("type", "button");
						    		deleteButton.setAttribute("onclick", "deleteProductFromTransaction(" + productIdStringNoSlash + ")");
						    	var buttonTxt = document.createTextNode('Delete');
						    	deleteButton.appendChild(buttonTxt);
								
								TR.setAttribute("class", productIdStringNoSlash);
								TD.innerHTML = data[0+i];
							    TD2.innerHTML = data[1+i];
							    TD3.innerHTML = data[2+i]; //qty
							    TD4.innerHTML = data[3+i]; 
							    TD5.innerHTML = data[4+i]; //price
// 							    if(data[4+i]!="N/A"){
// 							    	totalSrp = totalSrp + (data[2+i]*data[4+i]);
// 							    	$('#srpText').val('Php ' + totalSrp);
// 							    }
							    TD6.appendChild(deleteButton);
								TR.appendChild(TD);
							    TR.appendChild(TD2);
							    TR.appendChild(TD3);
							    TR.appendChild(TD4);
							    TR.appendChild(TD5);
							    TR.appendChild(TD6);
								BODY.appendChild(TR);
// 							}
						}
						//new end
						
						$('#productId').val("");
						$('#productQty').val("");
						$('#showProductName').text('');
						$('#showProductCode').text('');
						$('#showProductQty').text('');
						$('#showProductUOM').text('');
						$('#showProductCost').val('');
						$('#showProductSale').val('');
				    }
				    else{
				    	alert('Unable to Add Product to Transaction - ' + data[1]);
				    }
				  }
			});
	}
	else{
		alert('Unable to Add Product to Transaction Check Inputs');
	}
		
});

function deleteProductFromTransaction(prodID){
	
	var urlString = "/deleteproductintransaction/";
	var prodIDString = prodID + "/";
// 	var qtyString = qty + "/";
// 	var priceString = price;
	var url = urlString + prodIDString;
	
	alert(url);
	
	$.ajax({
		  url: url,
		  success: function(data) {
			    $('.result').html(data);
			    $("#myData").find("." + prodID).remove();
			}
		});
		
}

</script>

</div>