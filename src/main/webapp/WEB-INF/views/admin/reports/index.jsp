<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div>
<h3>REPORT FILTERING OPTIONS</h3>
<hr></hr>

<div class="well row span12">

Choose Filter Option: <select id="filter" class="span7">
					  	<option value="inCategory">Total purchases per category within dates</option>
					  	<option value="inventoryCategory">Total inventory count per category</option>
					  	<option value="summaryCategory">Cumulative summary per category within dates</option>
					  	<option value="rankInventoryCategory"> Rank most sale-able inventory per category within dates (descending)</option>
					  	<option value="userTransaction">Transaction per user within dates</option>
					  </select>
					  
					  <input type="button" value="submit" id="filterButton">
</div>

<div class="well row hide span12" id = "inventoryIn" >
	
	<h4>Total purchases per category within dates</h4>
	
	<div class="">
		<form:form action = "/admin/reports/report-category-purchases" method = "POST" modelAttribute = "transactionDatesCategoryIn" target="_blank">
			<label>Transaction Start Date:</label><form:input type="text" id="ButtonCreationDemoInput1" path="date1"/><button id="ButtonCreationDemoButton1" type="button">
	    		<img src="/images/calendar.png" alt="[calendar icon]"/>
	  		</button>
			<label>Transaction End Date:</label><form:input type="text" id="ButtonCreationDemoInput2" path="date2"/><button id="ButtonCreationDemoButton2" type="button">
	    		<img src="/images/calendar.png" alt="[calendar icon]"/>
	  		</button>
	  		
	  		<label>Category: </label><form:select id="inputCategory" items="${categories}" path="categoryId" itemValue="id" class=""/>
	  		
	  		<input style="margin-top:5px;" type="submit" value="Get Transactions" class="btn btn-primary"/>
		</form:form>
  	</div>
  	
</div>

<div class="well row hide span12" id = "inventoryCount" >
	
	<h4>Total inventory count per category for the date: ${dateToday}</h4>
	
	<div class="span4">	
		<form:form action = "/admin/reports/report-category-inventory" method = "POST" modelAttribute = "inventoryCount" target="_blank">
	  		<label>Category: </label><form:select id="inputCategory" items="${categories}" path="categoryId" itemValue="id" class=""/>
	  		<input style="margin-top:5px;" type="submit" value="Generate Inventory Count" class="btn btn-primary" />
	  	</form:form>
  	</div>
  	
</div>

<div class="well row hide span12" id = "summary" >
	
	<h4>Cumulative summary per category within dates</h4>
	
	<div class="span4">
		<form:form action = "/admin/reports/report-category-summary" method = "POST" modelAttribute = "transactionDatesSummaryCategory" target="_blank">
			<label>Transaction Start Date:</label><form:input type="text" id="ButtonCreationDemoInput3" path="date1"/><button id="ButtonCreationDemoButton3" type="button">
	    		<img src="/images/calendar.png" alt="[calendar icon]"/>
	  		</button>
			<label>Transaction End Date:</label><form:input type="text" id="ButtonCreationDemoInput4" path="date2"/><button id="ButtonCreationDemoButton4" type="button">
	    		<img src="/images/calendar.png" alt="[calendar icon]"/>
	  		</button>
	  		
	  		<label>Category: </label><form:select id="inputCategory" items="${categories}" path="categoryId" itemValue="id" class=""/>
	  		
	  		<input style="margin-top:5px;" type="submit" value="Get Transactions" class="btn btn-primary"/>
		</form:form>
  	</div>
  	
</div>

<div class="well row hide span12" id = "rank" >
	
	<h4>Total rank per category within dates</h4>
	
	<div class="span4">
		<form:form action = "/admin/reports/report-category-rank" method = "POST" modelAttribute = "transactionDatesRankingInventoryCategory" target="_blank">
			<label>Transaction Start Date:</label><form:input type="text" id="ButtonCreationDemoInput5" path="date1"/><button id="ButtonCreationDemoButton5" type="button">
	    		<img src="/images/calendar.png" alt="[calendar icon]"/>
	  		</button>
			<label>Transaction End Date:</label><form:input type="text" id="ButtonCreationDemoInput6" path="date2"/><button id="ButtonCreationDemoButton6" type="button">
	    		<img src="/images/calendar.png" alt="[calendar icon]"/>
	  		</button>
	  		
	  		<label>Category: </label><form:select id="inputCategory" items="${categories}" path="categoryId" itemValue="id" class=""/>
	  		
	  		<input style="margin-top:5px;" type="submit" value="Get Transactions" class="btn btn-primary"/>
		</form:form>
  	</div>
  	
</div>

<div class="well row hide span12" id = "userTransactions" >
	
	<h4>Total purchases per category within dates</h4>
	
	<div class="span4">
		<form:form action = "/admin/reports/report-category-user-transactions" method = "POST" modelAttribute = "transactionDatesUser" target="_blank">
			<label>Transaction Start Date:</label><form:input type="text" id="ButtonCreationDemoInput7" path="date1"/><button id="ButtonCreationDemoButton7" type="button">
	    		<img src="/images/calendar.png" alt="[calendar icon]"/>
	  		</button>
			<label>Transaction End Date:</label><form:input type="text" id="ButtonCreationDemoInput8" path="date2"/><button id="ButtonCreationDemoButton8" type="button">
	    		<img src="/images/calendar.png" alt="[calendar icon]"/>
	  		</button>
	  		
<%-- 	  		<form:select id="inputCategory" items="${categories}" path="categoryId" itemValue="id" class="span2"/> --%>
	  		
	  		<label>User: </label><form:select path="userId" itemValue="id" items="${users}" id="selectUser"></form:select>
	  		
	  		<input style="margin-top:5px;" type="submit" value="Get Transactions" class="btn btn-primary"/>
		</form:form>
  	</div>
  	
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
	      
$('#ButtonCreationDemoButton3').click(
	      function(e) {
	        $('#ButtonCreationDemoInput3').AnyTime_noPicker().AnyTime_picker({format: "%Y-%m-%d %H:%i"}).focus();
	        e.preventDefault();
	      } );
	      
$('#ButtonCreationDemoButton4').click(
    function(e) {
      $('#ButtonCreationDemoInput4').AnyTime_noPicker().AnyTime_picker({format: "%Y-%m-%d %H:%i"}).focus();
      e.preventDefault();
    } );
    
$('#ButtonCreationDemoButton5').click(
	      function(e) {
	        $('#ButtonCreationDemoInput5').AnyTime_noPicker().AnyTime_picker({format: "%Y-%m-%d %H:%i"}).focus();
	        e.preventDefault();
	      } );
	      
$('#ButtonCreationDemoButton6').click(
    function(e) {
      $('#ButtonCreationDemoInput6').AnyTime_noPicker().AnyTime_picker({format: "%Y-%m-%d %H:%i"}).focus();
      e.preventDefault();
    } );
    
$('#ButtonCreationDemoButton7').click(
	      function(e) {
	        $('#ButtonCreationDemoInput7').AnyTime_noPicker().AnyTime_picker({format: "%Y-%m-%d %H:%i"}).focus();
	        e.preventDefault();
	      } );
	      
$('#ButtonCreationDemoButton8').click(
    function(e) {
      $('#ButtonCreationDemoInput8').AnyTime_noPicker().AnyTime_picker({format: "%Y-%m-%d %H:%i"}).focus();
      e.preventDefault();
    } );

$('#filterButton').click(function(){
    if($('#filter').val() == 'inCategory') {
        $('#inventoryIn').show(); 
        $('#inventoryCount').hide(); 
        $('#summary').hide(); 
        $('#rank').hide(); 
        $('#userTransactions').hide(); 
    } else if($('#filter').val() == 'inventoryCategory'){
    	 $('#inventoryIn').hide(); 
         $('#inventoryCount').show(); 
         $('#summary').hide(); 
         $('#rank').hide(); 
         $('#userTransactions').hide();
    } else if($('#filter').val() == 'summaryCategory'){
   	 	 $('#inventoryIn').hide(); 
      	 $('#inventoryCount').hide(); 
     	 $('#summary').show(); 
     	 $('#rank').hide(); 
     	 $('#userTransactions').hide();
	} else if($('#filter').val() == 'rankInventoryCategory'){
   	 	 $('#inventoryIn').hide(); 
      	 $('#inventoryCount').hide(); 
     	 $('#summary').hide(); 
     	 $('#rank').show(); 
     	 $('#userTransactions').hide();
	} else{
		 $('#inventoryIn').hide(); 
     	 $('#inventoryCount').hide(); 
    	 $('#summary').hide(); 
    	 $('#rank').hide(); 
    	 $('#userTransactions').show();
	}
});

</script>

</div>