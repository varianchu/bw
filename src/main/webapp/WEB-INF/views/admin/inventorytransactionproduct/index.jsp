<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div>
<h3>STOCK IN/OUT TRANSACTION</h3>
<c:if test="${not empty headsup}">
<div class="well btn-info" id="messageBox">
<c:forEach var="message" items="${headsup}">
	${message}<br />
</c:forEach>
</div>
</c:if>
<hr></hr>

<div class="well span3">
<label>Product ID:</label><input id="productId" onchange="getProductInfo()"/>
<label>Quantity to Add/Subtract:</label><input id="productQty"/>

<hr></hr>
<button class="btn btn-primary" id="addProductForTransaction" type="button">Add Product to Transaction</button>
</div>

<div class="well offset4">

<form:form action = "/admin/stocktransaction" method = "POST" modelAttribute = "transaction">
	<form:hidden path="id"/>
	<label>Transaction Date:</label><form:input type="text" path="dateCreatedValue" id="ButtonCreationDemoInput" value="${getDate}"/><button id="ButtonCreationDemoButton">
    <img src="/images/calendar.png" alt="[calendar icon]"/>
  </button>
	<label>Transaction Type:</label><form:select type="text" path="transactionType" items="${transactionType}"/>
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

<!-- <input type="text" id="ButtonCreationDemoInput"/> -->
<!--   <button id="ButtonCreationDemoButton"> -->
<!--     <img src="calendar.png" alt="[calendar icon]"/> -->
<!--   </button> -->

</div>
<div class="modal hide fade" id="myModal" style="width:300px; margin-top:-90px; margin-left:-160px;">
        <div class="modal-header">
            <a class="close" data-dismiss="modal">x</a>
        		<span class="label label-warning"><b>PRODUCT INFORMATION</b></span>
        </div>
        <br />
        <div class="modal-body alert alert-success">
  			<label>Product Name: </label><label id="showProductName" style="font-weight:bold;"></label><br />
  			<label>Product Code:</label><label id="showProductCode" style="font-weight:bold;"></label><br />
  			<label>Quantity in Stock:</label><label id="showProductQty" style="font-weight:bold;"></label><br />
  			<label>Unit of Measure:</label><label id="showProductUOM" style="font-weight:bold;"></label><br />
        </div>
        <div class="modal-footer">
            
        </div>
	</div>

<script type="text/javascript">

// $.getJSON();

$('#ButtonCreationDemoButton').click(
    	      function(e) {
    	        $('#ButtonCreationDemoInput').AnyTime_noPicker().AnyTime_picker({format: "%Y-%m-%d %H:%i"}).focus();
    	        e.preventDefault();
    	      } );

$('#myModal').modal('hide');

var copyArray;

function getProductInfo(){
	
	var urlString = "/admin/getproductintransaction/";
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
			    	$('#myModal').modal();
			    	$('#productQty').val("1.0");
			    	copyArray = data.slice(0);
			}
		});
}

$("#addProductForTransaction").click(function(){
	
	if(($("#productId").val().length != '') && ($("#productQty").val().length != '')){
//		ajax
		var urlString ="/admin/addtransactionproduct/";
		var productIdString = document.getElementById('productId').value + "/";
		var productIdStringNoSlash = document.getElementById('productId').value;
		var productQtyString = document.getElementById('productQty').value + "/";
		var productQtyStringNoSlash = document.getElementById('productQty').value;
	
		var url = urlString + productIdString + productQtyString;
		
// 		table formation
		var TABLE = document.getElementById('myData');
		var BODY = TABLE.getElementsByTagName('tbody')[0];
		var TR = document.createElement('tr');
		var TD = document.createElement('td');
    	var TD2 = document.createElement('td');
    	var TD3 = document.createElement('td');
    	var TD4 = document.createElement('td');
    	var TD5 = document.createElement('td');
    	var lastRow = TABLE.rows.length;
    	var deleteButton = document.createElement('button');
    		deleteButton.setAttribute("class", "btn btn-danger");
    		deleteButton.setAttribute("type", "button");
    		deleteButton.setAttribute("onclick", "deleteProductFromTransaction(" + productIdStringNoSlash + "," + productQtyStringNoSlash +")");
    	var buttonTxt = document.createTextNode('Delete');
    	deleteButton.appendChild(buttonTxt);
    	
		$.ajax({
			  url: url,
			  success: function(data) {
				    $('.result').html(data);
				    if(copyArray[0]!="NO PRODUCT FOUND!"){
				    	
				    	alert('Load was performed. ' + data);
				    	TR.setAttribute("class", productIdStringNoSlash);
						TD.innerHTML = copyArray[0];
					    TD2.innerHTML = copyArray[1];
					    TD3.innerHTML = document.getElementById('productQty').value;
					    TD4.innerHTML = copyArray[3];
					    TD5.appendChild(deleteButton);
						TR.appendChild(TD);
					    TR.appendChild(TD2);
					    TR.appendChild(TD3);
					    TR.appendChild(TD4);
					    TR.appendChild(TD5);
						BODY.appendChild(TR);
						
						$('#productId').val("");
						$('#productQty').val("");
				    }
				    else{
				    	alert('Unable to Add Product to Transaction');
				    }
				  }
			});
	}
	else{
		alert('Unable to Add Product to Transaction Check Inputs');
	}
		
});

function deleteProductFromTransaction(prodID,qty){
	
	var urlString = "/admin/deleteproductintransaction/";
	var prodIDString = prodID + "/";
	var qtyString = qty + "/";
	var url = urlString + prodIDString + qtyString;
	
	$.ajax({
		  url: url,
		  success: function(data) {
			    $('.result').html(data);
			    $("#myData").find("."+prodID).remove();
			}
		});
		
}

// $(document).ready(function() {
	
//     $('#myModal').modal('hide');
    
//     $('#dateCreated').datetimepicker();
    
    
    
    
// }
// );
</script>

</div>