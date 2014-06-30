<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div>
<h3>VIEW SERVICE TRANSACTION FOR ID: ${serviceTransaction.id}</h3>
<h4>Date: ${serviceTransaction.serviceDate}</h4>
<h4>Service Transaction Total Sale: &#8369; <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${serviceTransaction.totalServiceTransactionSale}" /></h4>
<h4>Service Transaction Total Profit: &#8369; <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${serviceTransaction.totalServiceTransactionProfit}" /></h4>
<h4>Customer/Car: ${serviceTransaction.customerName} - ${serviceTransaction.car}</h4>
<h4>Worker: ${serviceTransaction.mechanicTireMan}</h4>
<hr></hr>
	<table class="table table-striped table-bordered table-condensed well">
  		<thead>
  			<tr>
   				<td>Service Made</td>
   				<td>Service Price</td>
   				<td>Service Profit</td>
  			</tr>
  		</thead>
  		<tbody>
  			<c:forEach var="item" items="${serviceTransactionItems}">
  			<tr>
  				<td>${item.serviceMadePart}</td>
  				<td>&#8369; <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${item.servicePrice}" /></td>
  				<td>&#8369; <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${item.serviceProfit}" /></td>
  			</tr>
  			</c:forEach>
  		</tbody>
  	</table>	
</div>