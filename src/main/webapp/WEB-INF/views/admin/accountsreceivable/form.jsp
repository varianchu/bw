<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div>
<h3>ACCOUNTS RECEIVABLE INFORMATION</h3>
<hr></hr>
<div class="well span8">
<form:form action = "/admin/accounts-receivable/form" method = "POST" modelAttribute = "accountsReceivable">
	<form:hidden path="id"/>
	<label>Customer:</label><form:select items="${customers}" path="customerId" itemValue="id"/>
	<form:errors path="customerId" style="color:red;"/>
	<label>Receipt Number/Reference Number:</label><form:input type="text" path="receiptNumber" placeholder="Receipt Number"/>
	<form:errors path="receiptNumber" style="color:red;"/>
	<label>Amount:</label><form:input type="text" path="amount" placeholder="Amount"/>
	<form:errors path="amount" style="color:red;"/>
	<label>Transaction Date:</label><form:input class="span5" type="text" path="dateCreatedValue" id="ButtonCreationDemoInput" value="${date}"/><button id="ButtonCreationDemoButton" type="button">
    	<img src="/images/calendar.png" alt="[calendar icon]"/>
  	</button>
	<label>Terms:</label><form:select items="${terms}" path="expectedDateReceivableValue"/>
	<form:errors path="expectedDateReceivableValue" style="color:red;"/>
	<hr></hr>
	<input type="submit" value="Save Accounts Receivable" class="btn btn-primary"/>
</form:form>
</div>
</div>

<script type="text/javascript">

$('#ButtonCreationDemoButton').click(
    	      function(e) {
    	        $('#ButtonCreationDemoInput').AnyTime_noPicker().AnyTime_picker({format: "%Y-%m-%d %H:%i"}).focus();
    	        e.preventDefault();
    	      } );
    	      
</script>