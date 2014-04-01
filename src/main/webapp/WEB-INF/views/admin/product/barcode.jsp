<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div>
<iframe style="border: 0; position:fixed; top:0; left:0; right:0; bottom:0; width:100%; height:100%"></iframe>

<script type="text/javascript">

$(document).ready(function() {

	document.title = "Product: ${product.brand.brandName} - ${product.productName}";
	
	var doc = new jsPDF();
	doc.setFontSize(8);
	doc.setFont("courier");

	<c:forEach var="product" items="${products}" varStatus="status">
	
		var checkPage = ${status.count} % 24;
		var column = ${status.index} % 2;
		
		var i = (${status.index}%24) * 12;
		var j = (${status.index-1}%24) * 12;
		
		if(checkPage!=0){
			
			if(column==0){
				doc.addImage('${product.dataUri}','JPEG',2,i + 3,20,15);
				doc.text(25,i + 4, 'BRAND: ${product.brandName}');
				doc.text(25, i + 9, 'PRODUCT NAME: ${product.productName}');
				doc.setFontSize(15);
				doc.text(25, i + 14, 'CODE: ${product.description}');
				doc.text(25, i + 19, 'SRP: Php ${product.srp} ');
				doc.setFontSize(8);
			}
			else{
				doc.addImage('${product.dataUri}','JPEG',102,j + 3,20,15);
				doc.text(125,j + 4, 'BRAND: ${product.brandName}');
				doc.text(125,j + 9, 'PRODUCT NAME: ${product.productName}');
				doc.setFontSize(15);
				doc.text(125,j + 14, 'CODE: ${product.description}');
				doc.text(125,j + 19, 'SRP: Php ${product.srp}');
				doc.setFontSize(8);
			}
		}else{
			doc.addImage('${product.dataUri}','JPEG',102,j + 3,20,15);
			doc.text(125,j + 4, 'BRAND: ${product.brandName}');
			doc.text(125,j + 9, 'PRODUCT NAME: ${product.productName}');
			doc.setFontSize(15);
			doc.text(125,j + 14, 'CODE: ${product.description}');
			doc.text(125,j + 19, 'SRP: Php ${product.srp}');
			doc.setFontSize(8);
			doc.addPage();
		}
		
	</c:forEach>

	var string = doc.output('datauristring');

	$('iframe').attr('src', string);	
});

</script>

</div>