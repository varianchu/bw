<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div>
<h3>VIEW TRANSACTION FOR REFERENCE NUMBER: ${transaction.referenceNumber}</h3>
<h4>Date: ${transaction.dateCreated}</h4>
<h4>Transaction Type: ${transaction.transactionType}</h4>
<hr></hr>

	<table class="table table-striped table-bordered table-condensed">
  		<thead>
  			<tr>
   				<td>Product Name</td>
   				<td>Quantity</td>
   				<td>Notes</td>
  			</tr>
  		</thead>
  		<tbody>
  			<c:forEach var="transactionProduct" items="${transactionProducts}">
  			<tr>
  				<td>${transactionProduct.productName}</td>
  				<td>${transactionProduct.qty}</td>
  				<td><c:if test="${transactionProduct.product==null}"> Product is already Deleted</c:if></td>
  			</tr>
  			</c:forEach>
  		</tbody>
  	</table>
	
</div>

<script type="text/javascript">

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