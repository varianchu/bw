<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta http-equiv="X-UA-Compatible" content="IE=8" />
		<link type="text/css" media="screen" rel="stylesheet" href="/css/bootstrap.css"/>
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
		
		<script type="text/javascript" src="/js/jquery-1.6.4.js"></script>
		<script type="text/javascript" src="/js/bootstrap-dropdown.js"></script>
		<script type="text/javascript" src="/js/bootstrap-alerts.js"></script>
		<script type="text/javascript" src="/js/bootstrap-buttons.js"></script>
		<script type="text/javascript" src="/js/bootstrap-modal.js"></script>
		<script type="text/javascript" src="/js/bootstrap-popover.js"></script>
		<script type="text/javascript" src="/js/bootstrap-scrollspy.js"></script>
		<script type="text/javascript" src="/js/bootstrap-tabs.js"></script>
		<script type="text/javascript" src="/js/bootstrap-twipsy.js"></script>
		<script type="text/javascript" src="/js/anytime.js"></script>
		<script type="text/javascript" src="/js/bootstrap-timepicker.js"></script>
		<script type="text/javascript" src="/js/jquery.dataTables.js"></script>
<!-- 		<script type="text/javascript" src="/js/jqBarGraph.js"></script> -->
		<script type="text/javascript" src="/js/jqBarGraph.1.1.js"></script>
		<script type="text/javascript" src="/js/highcharts.js"></script>
		<script type="text/javascript" src="/js/exporting.js"></script>
		<script type="text/javascript" src="/js/grid.js"></script>
<!-- 		<script type="text/javascript" src="/js/jquery.galleriffic.js"></script> -->
<!-- 		<script type="text/javascript" src="/js/jquery.history.js"></script> -->
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
		
	</head>

  	<body style="padding-top: 60px;">
	  	<div class="navbar navbar-fixed-top">
			<div class="navbar-inner">
				<div class="container-fluid">
					<a class="brand" href="/">Bionic Wheels System</a>
					<tiles:insertAttribute name="menu"/>
					<p class="pull-right" style="color: white;">Logged in as <span id="core_username"><sec:authentication property="principal.username" /></span> |
					<a class="" href="/j_spring_security_logout" style="color: white"><b>Log out</b></a>
					</p>
				</div>
			</div>
	    </div>

	    <div class="container">
		    <div class="content">
		    	<div class="row">
		    		<tiles:insertAttribute name="body"/>
		    	</div>
			    <footer>
			    <hr></hr>
		          <p>&copy; Bionic Wheels 2012 | Powered by Altostratus Technologies Co.</p>
		        </footer>
		    </div>
	    </div>
	</body>
</html>