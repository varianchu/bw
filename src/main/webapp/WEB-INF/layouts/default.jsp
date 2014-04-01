<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
<!-- 		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" /> -->
<!-- 		<meta name="viewport" content="width=device-width, initial-scale=1.0"> -->

<!-- 			newcomment -->
<!-- 		<meta http-equiv="X-UA-Compatible" content="IE=8" /> -->
		<link type="text/css" media="screen" rel="stylesheet" href="/css/bootstrap.css"/>
		<link type="text/css" media="screen" rel="stylesheet" href="/css/bootstrap-responsive.css"/>
		
		<meta charset="utf-8">
    	<title>Bionic Wheels System</title>
    	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    	<meta name="description" content="Bionic Wheels Inventory System">
    	<meta name="author" content="Varian Chu">

    <!-- Le styles -->
<!--     	<link href="../assets/css/bootstrap.css" rel="stylesheet"> -->
    	<style type="text/css">
      		body {
        		padding-top: 60px;
        		padding-bottom: 40px;
      		}
    	</style>
    	
<!--     	<link href="../assets/css/bootstrap-responsive.css" rel="stylesheet"> -->
		
<!-- 		<link type="text/css" media="screen" rel="stylesheet" href="/css/demo_table_jui.css"/> -->
<!-- 		<link type="text/css" media="screen" rel="stylesheet" href="/css/jquery.ui.datetime.css"/> -->
<!-- 		<link type="text/css" media="screen" rel="stylesheet" href="/css/jquery-ui-1.8.16.custom.css"/> -->
		<link type="text/css" media="screen" rel="stylesheet" href="/css/core.css"/>
		<link type="text/css" media="screen" rel="stylesheet" href="/css/anytime.css"/>
		<link type="text/css" media="screen" rel="stylesheet" href="/css/DT_bootstrap.css"/>
<!-- 		<link type="text/css" media="screen" rel="stylesheet" href="/css/basic.css"/> -->
<!-- 		<link type="text/css" media="screen" rel="stylesheet" href="/css/black.css"/> -->
<!-- 		<link type="text/css" media="screen" rel="stylesheet" href="/css/galleriffic-5.css"/> -->
<!-- 		<link type="text/css" media="screen" rel="stylesheet" href="/css/timepicker.css"/> -->
		
<!-- 		<script type="text/javascript" src="/js/jquery-1.6.4.js"></script> -->
			<script type="text/javascript" src="/js/jquery-1.7.1.js"></script>
		
		<!-- 			newcomment -->
<!-- 		<script type="text/javascript" src="/js/bootstrap-dropdown.js"></script> -->
<!-- 		<script type="text/javascript" src="/js/bootstrap-alerts.js"></script> -->
<!-- 		<script type="text/javascript" src="/js/bootstrap-buttons.js"></script> -->
<!-- 		<script type="text/javascript" src="/js/bootstrap-modal.js"></script> -->
<!-- 		<script type="text/javascript" src="/js/bootstrap-popover.js"></script> -->
<!-- 		<script type="text/javascript" src="/js/bootstrap-scrollspy.js"></script> -->
<!-- 		<script type="text/javascript" src="/js/bootstrap-tabs.js"></script> -->
<!-- 		<script type="text/javascript" src="/js/bootstrap-twipsy.js"></script> -->
		
		
		<script type="text/javascript" src="/js/anytime.js"></script>
		<script type="text/javascript" src="/js/bootstrap-timepicker.js"></script>
		<script type="text/javascript" src="/js/jquery.dataTables.js"></script>
		
<!-- 		<script type="text/javascript" src="/js/jqBarGraph.js"></script> -->
<!-- 		<script type="text/javascript" src="/js/jqBarGraph.1.1.js"></script> -->
		<script type="text/javascript" src="/js/highcharts.js"></script>
		<script type="text/javascript" src="/js/exporting.js"></script>
		<script type="text/javascript" src="/js/grid.js"></script>
<!-- 		<script type="text/javascript" src="/js/jquery.galleriffic.js"></script> -->
		<script type="text/javascript" src="/js/jquery.history.js"></script>
		
		
<!-- 		<script type="text/javascript" src="/js/jquery.opacityrollover.js"></script> -->
<!-- 		<script type="text/javascript" src="/js/jquery.DT_bootstrap.js"></script> -->
<!-- 		<script type="text/javascript" src="/js/jquery.ui.datetime.min.js"></script> -->
<!-- 		<script type="text/javascript" src="/js/jquery.ui.timepicker.js"></script> -->
<!-- 		<script type="text/javascript" src="/js/jquery.ui.core.js"></script> -->
<!-- 		<script type="text/javascript" src="/js/jquery.ui.widget.js"></script> -->
<!-- 		<script type="text/javascript" src="/js/jquery.ui.datepicker.js"></script> -->
<!-- 		<script type="text/javascript" src="/js/jquery-ui-timepicker-addon.js"></script> -->
<!-- 		<script type="text/javascript" src="/js/jquery-ui-sliderAccess.js"></script> -->
<!-- 		<script type="text/javascript" src="/js/DateTimePicker.js"></script> -->
<!-- 		<script type="text/javascript" src="/js/jquery-ui-1.8.16.custom.min.js"></script> -->

		<link rel="shortcut icon" href="/assets/ico/favicon.ico">
		
<!-- 		<script src="../assets/js/jquery.js"></script> -->
    	<script src="/assets/js/bootstrap-transition.js"></script>
    	<script src="/assets/js/bootstrap-alert.js"></script>
    	<script src="/assets/js/bootstrap-modal.js"></script>
    	<script src="/assets/js/bootstrap-dropdown.js"></script>
    	<script src="/assets/js/bootstrap-scrollspy.js"></script>
    	<script src="/assets/js/bootstrap-tab.js"></script>
    	<script src="/assets/js/bootstrap-tooltip.js"></script>
    	<script src="/assets/js/bootstrap-popover.js"></script>
    	<script src="/assets/js/bootstrap-button.js"></script>
    	<script src="/assets/js/bootstrap-collapse.js"></script>
    	<script src="/assets/js/bootstrap-carousel.js"></script>
    	<script src="/assets/js/bootstrap-typeahead.js"></script>
    	
    	<script type="text/javascript">

			$(document).ready(function(){
				$('.dropdown-toggle').dropdown();
			});
    	
		</script>
    	
	</head>

	<body background="/images/retina_wood.png">
 	<div class="navbar navbar-fixed-top">
 		<div class="navbar-inner">
        	<div class="container">
          		<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
            	<span class="icon-bar"></span>
            	<span class="icon-bar"></span>
            	<span class="icon-bar"></span>
          		</a>
          		<a class="brand" href="/"><img src="/images/BIONIC_blue-yellow.png" width="40" height="20" style="margin-top:-10;">Bionic Wheels System</a>
<!--           		<div class="nav-collapse collapse"> -->
<!--             		<ul class="nav"> -->
<!--               			<li><a href="/">Home</a></li> -->
<!--               			<li><a href="#about">Reports</a></li> -->
<!--               			<li class="dropdown"> -->
<!--                 			<a href="#" class="dropdown-toggle" data-toggle="dropdown">Reports <b class="caret"></b></a> -->
<!--                 				<ul class="dropdown-menu"> -->
<!--                   					<li><a href="/inventory-report">Download All Inventory Count</a></li> -->
<!--                   					<li><a href="/admin/reports">Generate Report By Filter</a></li> -->
<!--                 				</ul> -->
<!--               			</li> -->
<!--               			<li><a href="/admin/view-products-gallery">View MagWheels Gallery</a></li> -->
<!--               			<li class="dropdown"> -->
<!--                 			<a href="#" class="dropdown-toggle" data-toggle="dropdown">Settings <b class="caret"></b></a> -->
<!--                 				<ul class="dropdown-menu"> -->
<!--                   					<li><a href="/admin/settings">Stock Settings</a></li> -->
<!--                   					<li class="divider"></li> -->
<!--                   					<li class="nav-header">User Management</li> -->
<!--                   					<li><a href="/admin/user/create">Create User Account</a></li> -->
<!--                   					<li><a href="/admin/users">Accounts Management</a></li> -->
<!--                 				</ul> -->
<!--               			</li> -->
<!--             		</ul> -->
           <p class="pull-right" >Logged in as <span id="core_username"><sec:authentication property="principal.firstName" /> <sec:authentication property="principal.lastName" /></span> |
					<a class="" href="/j_spring_security_logout" ><b>Log out</b></a>
			</p>
          </div><!--/.nav-collapse -->
        </div>
      </div>
<!--     </div> -->
	
	<div class="container">
		<div></div>
		<c:if test="${SUCCESS_MESSAGE != null}">
			<div class="alert alert-success">${SUCCESS_MESSAGE}</div>
		</c:if>
		<c:if test="${ERROR_MESSAGE != null}">
			<div class="alert alert-error">${ERROR_MESSAGE}</div>
		</c:if>
		<div class="row-fluid">
			<div class="span3" style="margin-left:-90px;">
			   	<tiles:insertAttribute name="sidenav"/>
			</div>
			<div class="span10" style="margin-left: 15px;">
				<tiles:insertAttribute name="body"/>
			</div>
		</div>
		<hr>
		<footer>
        	<p>&copy; Bionic Wheels 2014</p>
      	</footer>
	</div>      
</body>



</html>
