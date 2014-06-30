<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div>
<h3>VIEW TRANSACTIONS</h3>
<hr></hr>

<div class="well span12 row">
	
	<div class="span4">
		<form:form action = "/admin/view-transactions" method = "POST" modelAttribute = "transactionDates">
			<label>Transaction Start Date:</label><form:input type="text" id="ButtonCreationDemoInput1" path="date1"/><button id="ButtonCreationDemoButton1" type="button">
	    		<img src="/images/calendar.png" alt="[calendar icon]"/>
	  		</button>
			<label>Transaction End Date:</label><form:input type="text" id="ButtonCreationDemoInput2" path="date2"/><button id="ButtonCreationDemoButton2" type="button">
	    		<img src="/images/calendar.png" alt="[calendar icon]"/>
	  		</button>
	  		<input style="margin-top:5px;" type="submit" value="Get Transactions" class="btn btn-primary"/>
		</form:form>
  	</div>
  	
  	<div class="span8">
  	<table class="table table-striped table-bordered table-condensed">
  		<tr>
  			<td>Total Cost of Goods Sold: </td>
  			<td><b style="font-size:30px;">&#8369; <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${totalCost}" /></b></td>
  		</tr>
  		<tr>
  			<td>Total Sales: </td>
			<td><b style="font-size:30px;">&#8369; <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${totalSRP}" /></b></td>
  		</tr>
  		<tr>
  			<td>Total Profit: </td>
  			<td><b style="font-size:30px;">&#8369; <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${totalProfit}" /></b></td>
  		</tr>
  		<tr>
  			<td>Total Purchases Cost: </td>
  			<td><b style="font-size:30px;">&#8369; <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${totalPurchases}" /></b></td>
  		</tr>
  		<tr>
  			<td>Net Profit: </td>
  			<td><c:if test = "${(totalProfit-totalPurchases)>0}"><b style="color: green; font-size:35px;">&#8369; <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${netProfit}" /></b></c:if>
  				<c:if test = "${(totalProfit-totalPurchases)<=0}"><b style="color: red; font-size:35px;">&#8369; <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${netProfit}" /></b></c:if>
  			</td>
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
   				<td>User</td>
   			 </tr>
  		</thead>
  		<tbody>
  		<c:forEach var = "transaction" items="${transactions}">
  			<tr>
  				<td><a href = "/view-transaction/${transaction.id}">${transaction.referenceNumber}</a></td>
    			<td>${transaction.dateCreated}</td>
    			<td>${transaction.transactionType}</td>
    			<td>&#8369; <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${transaction.totalTransactionCost}" /></td>
    			<td>&#8369; <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${transaction.totalTransactionSale}" /></td>
    			<td>${transaction.user.firstName} ${transaction.user.lastName}</td>
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