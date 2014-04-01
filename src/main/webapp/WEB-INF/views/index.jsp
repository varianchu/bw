<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="span12">
<h2 class="title">Dashboard</h2>
<!-- <div id="divForGraph" class="well span12"> -->
<!-- </div> -->
<hr>
<div id="container" style="height:100%;"></div>
<br />
<div id="container2" style="height:100%;"></div>
</div>
<script type="text/javascript">
	var chart;
        chart = new Highcharts.Chart({
            chart: {
                renderTo: 'container',
                type: 'bar'
            },
            title: {
                text: '${title} bar chart'
            },
            xAxis: {
                categories: [ 
                	<c:forEach var="item" items="${bgms}" varStatus="loop">
                		<c:if test = "${not loop.last}">
                			'${item.categoryName}',
                		</c:if>
                		<c:if test = "${loop.last}">
                			'${item.categoryName}']
                		</c:if>
                	</c:forEach>
            },
            yAxis: {
                min: 0,
                title: {
                    text: 'Overview of Products based on ${title}'
                }
            },
            legend: {
                backgroundColor: '#FFFFFF',
                reversed: true
            },
            tooltip: {
                formatter: function() {
                    return ''+
                        this.series.name +': '+ this.y +'';
                }
            },
            plotOptions: {
                series: {
                    stacking: 'normal'
                }
            },
                series: [{
                name: 'Above Ceiling Value',
                data: [
                       	<c:forEach var="item" items="${bgms}" varStatus="loop">
        			   		<c:if test = "${not loop.last}">
    							${item.aboveQty},
    						</c:if>
    						<c:if test = "${loop.last}">
    							${item.aboveQty}]
    						</c:if>
    					</c:forEach>
            }, {
                name: 'Accepted Value ',
                data: [
                      	<c:forEach var="item" items="${bgms}" varStatus="loop">
    			   		<c:if test = "${not loop.last}">
							${item.qty},
						</c:if>
						<c:if test = "${loop.last}">
							${item.qty}]
						</c:if>
					</c:forEach>
            }, {
                name: 'Below Floor Value',
                data: [
                      	<c:forEach var="item" items="${bgms}" varStatus="loop">
    			   		<c:if test = "${not loop.last}">
							${item.belowQty},
						</c:if>
						<c:if test = "${loop.last}">
							${item.belowQty}]
						</c:if>
					</c:forEach>
            }]
        });
//     });
    
// });

	var chart2;
        chart2 = new Highcharts.Chart({
            chart: {
                renderTo: 'container2',
                type: 'area',
                spacingBottom: 30
            },
            title: {
                text: 'Products found in ${title2}'
            },
            legend: {
                layout: 'vertical',
                align: 'left',
                verticalAlign: 'top',
                x: 150,
                y: 100,
                floating: true,
                borderWidth: 1,
                backgroundColor: '#FFFFFF'
            },
            xAxis: {
                categories: [
                    <c:forEach var="item" items="${bgms2}" varStatus="loop">
		   				<c:if test = "${not loop.last}">
							'${item.productName}',
						</c:if>
						<c:if test = "${loop.last}">
							'${item.productName}']
						</c:if>
					</c:forEach>
            },
            yAxis: {
                title: {
                    text: 'Quantity'
                },
                labels: {
                    formatter: function() {
                        return this.value;
                    }
                }
            },
            tooltip: {
                formatter: function() {
                    return '<b>'+ this.series.name +'</b><br/>'+
                    this.x +': '+ this.y;
                }
            },
            plotOptions: {
                area: {
                    fillOpacity: 0.5
                }
            },
            credits: {
                enabled: false
            },
            series: [{
                name: 'Ceiling Value',
                data: 	[<c:forEach var="item" items="${bgms2}" varStatus="loop">
   							<c:if test = "${not loop.last}">
								${item.ceilingValue},
							</c:if>
							<c:if test = "${loop.last}">
								${item.ceilingValue}]
							</c:if>
						</c:forEach>
            }, {
                name: 'Floor Value',
                data: 	[<c:forEach var="item" items="${bgms2}" varStatus="loop">
							<c:if test = "${not loop.last}">
								${item.floorValue},
							</c:if>
							<c:if test = "${loop.last}">
								${item.floorValue}]
							</c:if>
						</c:forEach>
            },{
                name: 'Product Quantity',
                data: 	[<c:forEach var="item" items="${bgms2}" varStatus="loop">
							<c:if test = "${not loop.last}">
								${item.qty},
							</c:if>
							<c:if test = "${loop.last}">
								${item.qty}]
							</c:if>
						</c:forEach>
            }]
        });

</script>