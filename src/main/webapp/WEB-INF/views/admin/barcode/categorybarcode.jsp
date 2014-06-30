<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div>
<iframe style="border: 0; position:fixed; top:0; left:0; right:0; bottom:0; width:100%; height:100%"></iframe>

<%-- <c:forEach var="product" items="${products}" varStatus="status"> --%>
<%-- 	-------------------------------------------------<img src="${product.dataUri}"/> <br /> --%>
<%-- 	BRAND: ${product.brandName} <br /> --%>
<%-- 	PRODUCT NAME: ${product.productName} <br /> --%>
<%-- 	CODE: ${product.description} <br /> --%>
<%-- 	SRP: Php ${product.srp} <br /> --%>
<%-- </c:forEach> --%>

<script type="text/javascript">

$(document).ready(function() {

	document.title = "Batch Barcode for Category: ${category}";
	
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
				<c:if test="${product.pcd != null}">
					doc.addImage('${product.dataUri}','JPEG',2,i + 3,20,15);
					doc.text(25,i + 4, 'BRAND: ${product.brandName}');
// 					doc.text(25, i + 9, 'PRODUCT NAME: ${product.productName}');
					doc.setFontSize(10);
					doc.text(25, i + 9, 'PRODUCT NAME: ${product.productName}');
					doc.text(25, i + 14, 'HOLES/PCD: ${product.pcd}');
					doc.setFontSize(15);
					doc.text(25, i + 19, 'CODE: ${product.description}');
					doc.setFontSize(8);
				</c:if>
				<c:if test="${product.treadName != null}">
					doc.addImage('${product.dataUri}','JPEG',2,i + 3,20,15);
					doc.text(25,i + 4, 'BRAND: ${product.brandName}');
	//					doc.text(25, i + 9, 'PRODUCT NAME: ${product.productName}');
					doc.setFontSize(10);
					doc.text(25, i + 9, 'PRODUCT NAME: ${product.productName}');
					doc.text(25, i + 14, 'TREAD NAME: ${product.treadName}');
					doc.setFontSize(15);
					doc.text(25, i + 19, 'CODE: ${product.description}');
					doc.setFontSize(8);
				</c:if>
				<c:if test="${product.pcd == null && product.treadName == null}">
					doc.addImage('${product.dataUri}','JPEG',2,i + 3,20,15);
					doc.text(25,i + 4, 'BRAND: ${product.brandName}');
// 					doc.text(25, i + 9, 'PRODUCT NAME: ${product.productName}');
					doc.setFontSize(8);
					doc.text(25, i + 9, 'PRODUCT NAME: ${product.productName}');
					doc.setFontSize(15);
					doc.text(25, i + 14, 'CODE: ${product.description}');
					doc.setFontSize(8);
				</c:if>
			}
			else{
				<c:if test="${product.pcd != null}">
					doc.addImage('${product.dataUri}','JPEG',102,j + 3,20,15);
					doc.text(125,j + 4, 'BRAND: ${product.brandName}');
					doc.setFontSize(10);
					doc.text(125, j + 9, 'PRODUCT NAME: ${product.productName}');
					doc.text(125, j + 14, 'HOLES/PCD: ${product.pcd}');
					doc.setFontSize(15);
					doc.text(125, j + 19, 'CODE: ${product.description}');
					doc.setFontSize(8);
				</c:if>
				<c:if test="${product.treadName != null}">
					doc.addImage('${product.dataUri}','JPEG',102,j + 3,20,15);
					doc.text(125,j + 4, 'BRAND: ${product.brandName}');
					doc.setFontSize(10);
					doc.text(125, j + 9, 'PRODUCT NAME: ${product.productName}');
					doc.text(125, j + 14, 'TREAD NAME: ${product.treadName}');
					doc.setFontSize(15);
					doc.text(125, j + 19, 'CODE: ${product.description}');
					doc.setFontSize(8);
				</c:if>
				<c:if test="${product.pcd == null && product.treadName == null}">
					doc.addImage('${product.dataUri}','JPEG',102,j + 3,20,15);
					doc.text(125,j + 4, 'BRAND: ${product.brandName}');
					doc.setFontSize(8);
					doc.text(125,j + 9, 'PRODUCT NAME: ${product.productName}');
					doc.setFontSize(15);
					doc.text(125,j + 14, 'CODE: ${product.description}');
	//					doc.text(125,j + 19, 'SRP: Php ${product.srp}');
					doc.setFontSize(8);
				</c:if>
			}
		}else{
			<c:if test="${product.pcd != null}">
				doc.addImage('${product.dataUri}','JPEG',102,j + 3,20,15);
				doc.text(125,j + 4, 'BRAND: ${product.brandName}');
				doc.setFontSize(10);
				doc.text(125, j + 9, 'PRODUCT NAME: ${product.productName}');
				doc.text(125, j + 14, 'HOLES/PCD: ${product.pcd}');
				doc.setFontSize(15);
				doc.text(125, j + 19, 'CODE: ${product.description}');
				doc.setFontSize(8);
				doc.addPage();
			</c:if>
			<c:if test="${product.treadName != null}">
				doc.addImage('${product.dataUri}','JPEG',102,j + 3,20,15);
				doc.text(125,j + 4, 'BRAND: ${product.brandName}');
				doc.setFontSize(10);
				doc.text(125, j + 9, 'PRODUCT NAME: ${product.productName}');
				doc.text(125, j + 14, 'TREAD NAME: ${product.treadName}');
				doc.setFontSize(15);
				doc.text(125, j + 19, 'CODE: ${product.description}');
				doc.setFontSize(8);
				doc.addPage();
			</c:if>
			<c:if test="${product.pcd == null && product.treadName == null}">
				doc.addImage('${product.dataUri}','JPEG',102,j + 3,20,15);
				doc.text(125,j + 4, 'BRAND: ${product.brandName}');
				doc.setFontSize(8);
				doc.text(125,j + 9, 'PRODUCT NAME: ${product.productName}');
				doc.setFontSize(15);
				doc.text(125,j + 14, 'CODE: ${product.description}');
	// 			doc.text(125,j + 19, 'SRP: Php ${product.srp}');
				doc.setFontSize(8);
				doc.addPage();
			</c:if>
			
		}
		
	</c:forEach>

	var string = doc.output('datauristring');

	$('iframe').attr('src', string);	
});

</script>

</div>