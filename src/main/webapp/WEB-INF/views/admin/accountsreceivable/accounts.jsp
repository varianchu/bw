<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div>
<h3>VIEW ACCOUNTS RECEIVABLE/s</h3>
<!-- testing -->
<hr></hr>
<div class="row-fluid">
	<div class="span6 well">
		<table>
			<tr>
				<td><label>Receipt Number/Reference Number:</label></td>
				<td>&nbsp;&nbsp;<input id="referenceNumber" class="span11"/></td>
			</tr>
			<tr>
				<td><a onclick="goToRefNumber()" class="btn btn-primary">Submit Ref Number</a></td>
			</tr>
		</table>
	</div>
	<div class="span6 well">
		<table>
			<tr>
				<td><label>Customer:</label></td>
				<td>
					<select id="customernames">
    					<c:forEach items="${customers}" var="customer">
            					<option value="${customer.id}">${customer.customerName}</option>
    					</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td><a onclick="goToCustomerAccounts()" class="btn btn-primary">Submit Customer</a></td>
			</tr>
		</table>
	</div>
</div>
<div class="row-fluid">
	<div class="well span6">
		<table>
			<tr>
				<td><label>View All Accounts Receivable:</label></td>
				<td>
					<a onclick="goToAllAccountsReceivable()" class="btn btn-primary">View All Accounts Receivable</a>
				</td>
			</tr>
		</table>
	</div>
	<div class="well span6">
		<table>
			<tr>
				<td><label>View Today's Due Accounts Receivable:</label></td>
				<td>
					<a onclick="goToDueAccountsReceivable()" class="btn btn-primary">View Due Accounts Receivable</a>
				</td>
			</tr>
		</table>
	</div>
</div>
<hr></hr>
<h4>Total Amount of Accounts Receivable: &#8369; <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${totalAmount}" /></h4>
<h4>Customer: ${customer}</h4>
<div class="well span12">
<table class="table table-striped table-bordered table-condensed data_grid alert-info">
  <thead>
  <tr>
   <td>Action</td>	
   <td>Customer Name</td>
   <td>Reference Number</td>
   <td>Purchasing Date</td>
   <td>Expected Date of Payment</td>
   <td>Amount</td>
   <td>Action</td>
  </tr>
  </thead>
  <tbody>
  <c:forEach var="ar" items="${dueAccountsReceivable}">
  <tr>
  	<td><a href="/admin/accounts-receivable/form/${ar.id}">EDIT/VIEW</a></td>
    <td>${ar.customer.customerName}</td>
    <td>${ar.receiptNumber}</td>
    <td>${ar.dateCreated}</td>
    <td>${ar.expectedDateReceivable}</td>
    <td>&#8369;<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${ar.amount}" /></td>
    <td>
    <ul class="nav nav-pills" style="height:0px;">
    	<li class="dropdown" data-dropdown="dropdown">
			<a href="#" class="dropdown-toggle">Paid?</a>
				<ul class="dropdown-menu">
					<li><a href="/admin/accounts-receivable/remove-paid/${ar.id}">Yes</a></li>
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


<script type="text/javascript">

// $('.dropdown-toggle').dropdown();

function goToRefNumber(){
	location.href='/admin/accounts-receivable/receiptnumber/' + $('#referenceNumber').val();
}

function goToCustomerAccounts(){
	location.href='/admin/accounts-receivable/customer/' + $('#customernames').val();
}

function goToAllAccountsReceivable(){
	location.href='/admin/accounts-receivable-all';
}

function goToDueAccountsReceivable(){
	location.href='/admin/accounts-receivable';
}

$('table.data_grid').dataTable({
	"bJQueryUI": true,
	"sPaginationType": "full_numbers"
});

</script>

</div>
