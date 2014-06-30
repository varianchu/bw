<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div>
<h3>Override Inventory Transaction Product ID: ${inventoryTransactionProduct.id}</h3>
<h4>From Transaction ID: ${inventoryTransactionProduct.inventoryTransaction.id}</h4>
<h4>Transaction Type: ${inventoryTransactionProduct.inventoryTransaction.transactionType}</h4>
<c:if test="${inventoryTransactionProduct.inventoryTransaction.transactionType eq 'INVENTORY_OUT'}"><div class="alert">NOTE: REVISED QUANTITY SHOULD BE LESS THAN THE CURRENT QUANTITY IN THE LINE ITEM. IF YOU WANT TO INCREASE THE PULLED OUT QUANTITY, KINDLY GO TO THE INVENTORY OUT/REFUND TRANSACTION MENU.</div></c:if>
<c:if test="${inventoryTransactionProduct.inventoryTransaction.transactionType eq 'REFUND'}"><div class="alert">NOTE: NOTE: KINDLY CHANGE THE PRODUCT COST/PRODUCT SALE TO ITS APPROPRIATE AMOUNT TO HAVE CORRECT THE TOTALS IN REPORTS</div></c:if>
<hr>
<div class="well span8">
<form:form action = "/post/inventory-transaction-product" method = "POST" modelAttribute = "inventoryTransactionProduct">
	<form:hidden path="id"/>
	<form:hidden path="productId"/>
	<form:hidden path="categoryId"/>
	<form:hidden path="inventoryTransactionId"/>
	<c:if test="${inventoryTransactionProduct.inventoryTransaction.transactionType != 'REFUND'}">
	<form:hidden path="productCost"/>
	<label>Product Name:</label><form:input type="text" path="productName" placeholder="Product Name"/>
	<form:errors path="productName" style="color:red;"/>
	<label>Quantity:</label><form:input type="text" path="qty" placeholder="Quantity"/>
	<form:errors path="qty" style="color:red;"/>
	<label>Product Cost: <br/> <b>Php ${inventoryTransactionProduct.productCost}</b></label>
	<label>Product Sale:</label><form:input type="text" path="productSale" placeholder="Product Sale"/>
	<form:errors path="productSale" style="color:red;"/>
	<hr></hr>
	<input type="submit" value="Update Inventory Transaction Product" class="btn btn-primary"/>
	</c:if>
	<c:if test="${inventoryTransactionProduct.inventoryTransaction.transactionType eq 'REFUND'}">
	<label>Product Name:</label><form:input type="text" path="productName" placeholder="Product Name"/>
	<form:errors path="productName" style="color:red;"/>
	<label>Quantity:</label><form:input type="text" path="qty" placeholder="Quantity"/>
	<form:errors path="qty" style="color:red;"/>
	<label>Cost of Refunded Product: </label><form:input type="text" path="productCost" placeholder="Product Cost"/>
	<form:errors path="productCost" style="color:red;"/>
	<label>Cost of Refunded Sale:</label><form:input type="text" path="productSale" placeholder="Product Sale"/>
	<form:errors path="productSale" style="color:red;"/>
	<hr></hr>
	<input type="submit" value="Update Inventory Transaction Product" class="btn btn-primary"/>
	</c:if>
</form:form>
</div>
</div>