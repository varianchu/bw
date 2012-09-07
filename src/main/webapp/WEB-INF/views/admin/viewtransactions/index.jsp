<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div>
<h3>VIEW TRANSACTIONS</h3>

${fail}

<hr></hr>

<div class="well span5 offset3">
	<form:form action = "/admin/view_transactions" method = "POST" modelAttribute = "transactionDates">
		<label>Transaction Start Date:</label><form:input type="text" id="ButtonCreationDemoInput1" path="date1"/><button id="ButtonCreationDemoButton1" type="button">
    		<img src="/images/calendar.png" alt="[calendar icon]"/>
  		</button>
		<label>Transaction End Date:</label><form:input type="text" id="ButtonCreationDemoInput2" path="date2"/><button id="ButtonCreationDemoButton2" type="button">
    		<img src="/images/calendar.png" alt="[calendar icon]"/>
  		</button>
  		<input type="submit" value="Get Transactions" class="btn btn-primary"/>
	</form:form>
  	
</div>
	
<div class="row alert alert-info span9 offset1">
	<table class="table table-striped table-bordered table-condensed data_grid">
  		<thead>
  			<tr>
  				<td>Reference Number</td>
   				<td>Transaction Date</td>
   				<td>Transaction Type</td>
   			 </tr>
  		</thead>
  		<tbody>
  		<c:forEach var = "transaction" items="${transactions}">
  			<tr>
  				<td><a href = "/admin/view_transaction/${transaction.id}">${transaction.referenceNumber}</a></td>
    			<td>${transaction.dateCreated}</td>
    			<td>${transaction.transactionType}</td>
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

</div>