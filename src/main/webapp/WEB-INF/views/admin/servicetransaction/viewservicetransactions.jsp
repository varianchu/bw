<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div>
<h3 id="transactionTitle">VIEW SERVICE TRANSACTIONS</h3>
<hr></hr>

<div class="row well span12">

	<div class="span4">
		<form:form action = "/admin/view-service-transactions" method = "POST" modelAttribute = "serviceTransactionForm">
			<label>Transaction Start Date:</label><form:input type="text" id="ButtonCreationDemoInput1" path="startDate"/><button id="ButtonCreationDemoButton1" type="button">
	    		<img src="/images/calendar.png" alt="[calendar icon]"/>
	  		</button>
			<label>Transaction End Date:</label><form:input type="text" id="ButtonCreationDemoInput2" path="endDate"/><button id="ButtonCreationDemoButton2" type="button">
	    		<img src="/images/calendar.png" alt="[calendar icon]"/>
	  		</button>
<!-- 	  		<br /> -->
<!-- 	  		<br /> -->
<%-- 	  		<form:select path="userId" itemValue="id" items="${users}" id="selectUser"></form:select> --%>
<!-- 	  		<br /> -->
<!-- 	  		<br /> -->
	  		<input type="submit" value="Get Transactions" class="btn btn-primary" style="margin-top:10px;"/>
		</form:form>
  </div>
  <div class="span8">
  	<table class="table table-striped table-bordered table-condensed">
  		<tr>
  			<td>Total Sales: </td>
			<td><b style="font-size:30px;">Php ${totalSRP}</b></td>
  		</tr>
  		<tr>
  			<td>Total Profit: </td>
  			<td><b style="font-size:30px;">Php ${totalProfit}</b></td>
  		</tr>
  	</table>
  </div>	
</div>
	
<div class="row alert alert-info span12">
	<table class="table table-striped table-bordered table-condensed data_grid">
  		<thead>
  			<tr>
  				<td>Reference Number</td>
   				<td>Transaction Date</td>
   				<td>Customer - Car</td>
   				<td>Sales Representative</td>
   				<td>Total Sale</td>
   				<td>Total Profit</td>
   			 </tr>
  		</thead>
  		<tbody>
  		<c:forEach var = "transaction" items="${serviceTransactions}">
<%--   		<c:if test="${(transaction.totalTransactionCost != 0.0) && (transaction.totalTransactionSale != 0.0)}"> --%>
  			<tr>
  				<td><a href = "/admin/view-service-transactions/${transaction.id}">${transaction.id}</a></td>
    			<td>${transaction.serviceDate}</td>
    			<td>${transaction.customerName} - ${transaction.car}</td>
    			<td>${transaction.user.username}</td>
    			<td>&#8369; ${transaction.totalServiceTransactionSale}</td>
    			<td>&#8369; ${transaction.totalServiceTransactionProfit}</td>
  			</tr>
<%--   		</c:if> --%>
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