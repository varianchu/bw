<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div>
<h3>VIEW TIRE TRANSACTIONS: ${dateToday}</h3>

<hr></hr>
	<table class="table table-striped table-bordered table-condensed well">
  		<thead>
  			<tr>
   				<td>Product Name</td>
   				<td>Product Code</td>
   				<td>Brand</td>
   				<td>Quantity</td>
<!--    				<td>Cost</td> -->
   				<td>SRP</td>
   				<td>Notes</td>
  			</tr>
  		</thead>
  		<tbody>
  			<c:forEach var="transactionProduct" items="${itps}">
  			<tr>
  				<td>${transactionProduct.productName}</td>
  				<td><c:if test="${transactionProduct.product==null}"> Product is already Deleted</c:if><c:if test="${transactionProduct.product!=null}"> ${transactionProduct.product.code}</c:if></td>
  				<td><c:if test="${transactionProduct.product==null}"> Product is already Deleted</c:if><c:if test="${transactionProduct.product!=null}"> ${transactionProduct.product.brand.brandName}</c:if></td>
  				<td>${transactionProduct.qty}</td>
<%--   				<td>${transactionProduct.productCost}</td> --%>
  				<td>${transactionProduct.productSale}</td>
  				<td><c:if test="${transactionProduct.product==null}"> Product is already Deleted</c:if></td>
  			</tr>
  			</c:forEach>
  		</tbody>
  	</table>	
</div>