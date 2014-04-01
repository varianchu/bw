<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div>
<h3 id="transactionTitle">VIEW TRANSACTIONS BY USER</h3>
<hr></hr>

<div class="row well span12">

	<div class="span4">
		<form:form action = "/view-transactions-user" method = "POST" modelAttribute = "transactionDatesUser">
			<label>Transaction Start Date:</label><form:input type="text" id="ButtonCreationDemoInput1" path="date1"/><button id="ButtonCreationDemoButton1" type="button">
	    		<img src="/images/calendar.png" alt="[calendar icon]"/>
	  		</button>
			<label>Transaction End Date:</label><form:input type="text" id="ButtonCreationDemoInput2" path="date2"/><button id="ButtonCreationDemoButton2" type="button">
	    		<img src="/images/calendar.png" alt="[calendar icon]"/>
	  		</button>
	  		<br />
	  		<br />
	  		<form:select path="userId" itemValue="id" items="${users}" id="selectUser"></form:select>
	  		<br />
	  		<br />
	  		<input type="submit" value="Get Transactions" class="btn btn-primary"/>
		</form:form>
  </div>
  <div class="span8">
  	<table class="table table-striped table-bordered table-condensed">
  		<tr>
  			<td>Total Cost of Goods Sold: </td>
  			<td><b style="font-size:30px;">Php ${totalCost}</b></td>
  		</tr>
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
   				<td>Transaction Type</td>
   				<td>Total Cost of Goods</td>
   				<td>Total Sale of Goods</td>
   			 </tr>
  		</thead>
  		<tbody>
  		<c:forEach var = "transaction" items="${transactions}">
<%--   		<c:if test="${(transaction.totalTransactionCost != 0.0) && (transaction.totalTransactionSale != 0.0)}"> --%>
  			<tr>
  				<td><a href = "/view-transaction/${transaction.id}">${transaction.referenceNumber}</a></td>
    			<td>${transaction.dateCreated}</td>
    			<td>${transaction.transactionType}</td>
    			<td>&#8369; ${transaction.totalTransactionCost}</td>
    			<td>&#8369; ${transaction.totalTransactionSale}</td>
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