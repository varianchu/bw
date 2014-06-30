<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div>
<h3>VIEW TRANSACTION PRODUCT HISTORY</h3>
<hr></hr>

<div class="span4">
		<form:form action = "/admin/view-product-transaction-history" method = "POST" modelAttribute = "transactionDates">
			<label>Product ID: <br /><form:input type="text" path="productId" onkeypress="return isNumberKey(event)" autofocus="autofocus"/></label>
			<label>Transaction Start Date:</label><form:input type="text" id="ButtonCreationDemoInput1" path="date1"/><button id="ButtonCreationDemoButton1" type="button">
	    		<img src="/images/calendar.png" alt="[calendar icon]"/>
	  		</button>
			<label>Transaction End Date:</label><form:input type="text" id="ButtonCreationDemoInput2" path="date2"/><button id="ButtonCreationDemoButton2" type="button">
	    		<img src="/images/calendar.png" alt="[calendar icon]"/>
	  		</button>
	  		<input style="margin-top:5px;" type="submit" value="Get Transactions" class="btn btn-primary"/>
		</form:form>
  	</div>

<div class="row alert alert-info span12">
	<table class="table table-striped table-bordered table-condensed data_grid">
  		<thead>
  			<tr>
  				<td>Transaction Product ID</td>
   				<td>Product Name</td>
   				<td>Quantity</td>
   				<td>Cost</td>
   				<td>SRP</td>
   				<td>Notes</td>
  			</tr>
  		</thead>
  		<tbody>
  			<c:forEach var="transactionProduct" items="${transactionProducts}">
  			<tr>
  				<td><a href="/edit/inventory-transaction-product/${transactionProduct.id}">${transactionProduct.id}</a></td>
  				<td>${transactionProduct.productName}</td>
  				<td>${transactionProduct.qty}</td>
  				<td>${transactionProduct.productCost}</td>
  				<td>${transactionProduct.productSale}</td>
  				<td><c:if test="${transactionProduct.product==null}"> Product is already Deleted</c:if></td>
  			</tr>
  			</c:forEach>
  		</tbody>
  	</table>
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

$('#ButtonCreationDemoButton1').click(
    	      function(e) {
    	        $('#ButtonCreationDemoInput1').AnyTime_noPicker().AnyTime_picker({format: "%Y-%m-%d %H:%i"}).focus();
    	        e.preventDefault();
    	      } );
    	      
$('#ButtonCreationDemoButton2').click(
	      function(e) {
	        $('#ButtonCreationDemoInput2').AnyTime_noPicker().AnyTime_picker({format: "%Y-%m-%d %H:%i"}).focus();
	        e.preventDefault();
	      } );
	      
$('table.data_grid').dataTable({
	"bJQueryUI": true,
	"sPaginationType": "full_numbers"
});


</script>

</div>