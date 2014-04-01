<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div>
<h3>SERVICE TRANSACTION</h3>
<hr></hr>

<div class="row-fluid" style="margin-top:10px;">
	<div class="span6">
		<form:form action = "/admin/service-transaction/form" method = "POST" modelAttribute = "serviceTransactionForm">
			<form:hidden path="id"/>
			<label>Customer Name:</label><form:input path="customerName" placeholder="Customer Name"/>
			<form:errors path="customerName" style="color:red;"/>
			<label>Car:</label><form:input path="car" placeholder="Car"/>
			<form:errors path="car" style="color:red;"/>
			<label class="control-label" for="job" style="margin-top:20">Worker/Specialist:</label>
			<div class="controls">
			<form:select id="job" items="${workers}" path="mechanicTireMan" class="" itemValue="mechanicTireManName"/>
			<form:errors path="mechanicTireMan" style="color:red;"/>
		</div>
			<label>Sales Service Representative:</label><form:select path="userId" itemValue="id" items="${users}" id="selectUser"></form:select>
			<div class="alert" style="margin-bottom:-10px; margin-top:10px;">***NOTE: Submission of this transaction means that this transaction was made today</div>
			<br />
		<input type="submit" value="Save Service Transaction" class="btn btn-primary"/>
		</form:form>
	</div>
	<div class="span6">
		<form:form id="serviceTransactionItem" action="/admin/service-transaction-item" method="POST" modelAttribute = "serviceTransactionItem">
			<label>Service Made or Part/s:</label><form:input id="serviceMade" path="serviceMadePart" placeholder="Service Made/Part(s)"/>
			<label>Service Price:</label><form:input id="servicePrice" path="servicePrice" placeholder="Service Price"/>
			<label>Service Profit:</label><form:input id="serviceProfit" path="serviceProfit" placeholder="Service Profit"/>
			<br />
			<input type = "submit" value = "Add Service Item" class="btn btn-primary" style="margin-top:10px;"/>
		</form:form>
	</div>
</div>

<div class="row-fluid well">

	<table class="table table-bordered table-condensed" id="myData">
		<thead>
			<tr>
				<td>Service Made or Part/s</td>
				<td>Service Item Price</td>
				<td>Service Item Profit</td>
			</tr>
		</thead>
		<tbody>
			<tr>
			</tr>
		</tbody>
	</table>

</div>



<script type="text/javascript">

var frm = $('#serviceTransactionItem');
frm.submit(function (ev) {
	
	var TABLE = document.getElementById('myData');
	
    $.ajax({
        type: frm.attr('method'),
        url: frm.attr('action'),
        data: frm.serialize(),
        success: function (data) {
        	
        	if(data=="successful"){
	        	var BODY = TABLE.getElementsByTagName('tbody')[0];
				var TR = document.createElement('tr');
				var TD = document.createElement('td');
		    	var TD2 = document.createElement('td');
		    	var TD3 = document.createElement('td');
		    	
		    	TD.innerHTML = $('#serviceMade').val();
			    TD2.innerHTML = 'Php ' + $('#servicePrice').val();
			    TD3.innerHTML = 'Php '+ $('#serviceProfit').val();
			    
			    TR.appendChild(TD);
			    TR.appendChild(TD2);
			    TR.appendChild(TD3);
			    
			    BODY.appendChild(TR);
			    
			    $('#serviceMade').val("");
				$('#servicePrice').val("");
				$('#serviceProfit').val("");
        	}
        	else{
        		alert(data);
        	}
        }
    });

    ev.preventDefault();
});

</script>
</div>