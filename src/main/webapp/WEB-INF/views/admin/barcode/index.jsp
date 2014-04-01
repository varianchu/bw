<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div>
<h3>BATCH BARCODE FILTERING OPTIONS</h3>
<hr></hr>

<div class="well row span12">

Choose Filter Option: <select id="filter" class="span7">
					  	<option value="inCategory">Batch Barcode Generation by Category</option>
					  	<option value="inBrand">Batch Barcode Generation by Brand</option>
					  </select>
					  
					  <input type="button" value="submit" id="filterButton">
</div>

<div class="well row hide span12" id = "categoryDiv" >
	
	<h4>Batch Barcode Generation By Category</h4>
	
	<div class="span4">	
		<select id="inputCategory">
				<c:forEach var="category" items="${categories}">
					<option value="${category.id}">${category.categoryName}</option>
				</c:forEach>
		</select>
		
		<a class="btn btn-primary" onclick="getCategories()" style="margin-top:10px;">Submit</a>
  	</div>
  	
</div>

<div class="well row hide span12" id = "brandDiv" >
	
	<h4>Batch Barcode Generation By Brand</h4>
	
	<div class="span4">	
		<select id="inputBrand">
				<c:forEach var="brand" items="${brands}">
					<option value="${brand.id}">${brand.brandName}</option>
				</c:forEach>
		</select>
		
		<a class="btn btn-primary" onclick="getBrands()" style="margin-top:10px;">Submit</a>
  	</div>
  	
</div>
</div>

<script type="text/javascript">

$('#filterButton').click(function(){
    if($('#filter').val() == 'inCategory') {
        $('#categoryDiv').show(); 
        $('#brandDiv').hide(); 
    } else if($('#filter').val() == 'inBrand'){
    	 $('#categoryDiv').hide(); 
         $('#brandDiv').show(); 
    }
});

function getCategories(){
	window.open("/admin/generate/batch-barcode-category/" + $('#inputCategory').val(),'_newtab');
}

function getBrands(){
	window.open("/admin/generate/batch-barcode-brand/" + $('#inputBrand').val(), '_newtab');
}

</script>
