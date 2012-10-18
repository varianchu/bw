<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
	<head>
		<link type="text/css" media="screen" rel="stylesheet" href="/css/bootstrap.css"/>
<!-- 		<link type="text/css" media="screen" rel="stylesheet" href="/css/basic.css"/> -->
<!-- 		<link type="text/css" media="screen" rel="stylesheet" href="/css/black.css"/> -->
		<link type="text/css" media="screen" rel="stylesheet" href="/css/galleriffic-5.css"/>
<!-- 		<link type="text/css" media="screen" rel="stylesheet" href="/css/galleriffic-3.css"/> -->
		
		
		<script type="text/javascript" src="/js/jquery-1.7.1.js"></script>
<!-- 		<script src="../assets/js/bootstrap-dropdown.js"></script> -->
<!-- 		<script type="text/javascript" src="/js/bootstrap-dropdown.js"></script> -->
<!-- 		<script type="text/javascript" src="/js/bootstrap-alerts.js"></script> -->
<!-- 		<script type="text/javascript" src="/js/bootstrap-buttons.js"></script> -->
<!-- 		<script type="text/javascript" src="/js/bootstrap-modal.js"></script> -->
<!-- 		<script type="text/javascript" src="/js/bootstrap-popover.js"></script> -->
<!-- 		<script type="text/javascript" src="/js/bootstrap-scrollspy.js"></script> -->
<!-- 		<script type="text/javascript" src="/js/bootstrap-tabs.js"></script> -->
<!-- 		<script type="text/javascript" src="/js/bootstrap-twipsy.js"></script> -->
		<script type="text/javascript" src="/js/jquery.galleriffic.js"></script>
		<script type="text/javascript" src="/js/jquery.history.js"></script>
		<script type="text/javascript" src="/js/jquery.opacityrollover.js"></script>
		
<!-- 		<script type="text/javascript"> -->
<!-- 			document.write('<style>.noscript { display: none; }</style>'); -->
<!-- 		</script> -->
		</head>
	</head>
	<body>
	<body style="background-color:#cbeff7;">
 	<div class="navbar navbar-fixed-top">
 		<div class="navbar-inner">
        	<div class="container">
          		<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
            	<span class="icon-bar"></span>
            	<span class="icon-bar"></span>
            	<span class="icon-bar"></span>
          		</a>
          		<a class="brand" href="/"><img src="/images/BIONIC_blue-yellow.png" width="40" height="20" style="margin-top:-10;">Bionic Wheels System</a>
          		<div class="nav-collapse collapse">
            		<ul class="nav">
              			<li><a href="/">Home</a></li>
<!--               			<li><a href="#about">Reports</a></li> -->
              			<li class="dropdown">
                			<a href="#" class="dropdown-toggle" data-toggle="dropdown">Reports <b class="caret"></b></a>
                				<ul class="dropdown-menu">
                  					<li><a href="/inventory-report">Download All Inventory Count</a></li>
                  					<li><a href="#">Generate Report By Filter</a></li>
                				</ul>
              			</li>
              			<li><a href="/admin/view_products">View MagWheels Gallery</a></li>
              			<li class="dropdown">
                			<a href="#" class="dropdown-toggle" data-toggle="dropdown">Settings <b class="caret"></b></a>
                				<ul class="dropdown-menu">
                  					<li><a href="/admin/settings">Stock Settings</a></li>
                  					<li class="divider"></li>
                  					<li class="nav-header">User Management</li>
                  					<li><a href="#">Account Settings</a></li>
                  					<li><a href="#">Accounts Management</a></li>
                				</ul>
              			</li>
            		</ul>
           <p class="pull-right" >Logged in as <span id="core_username"><sec:authentication property="principal.username" /></span> |
					<a class="" href="/j_spring_security_logout" ><b>Log out</b></a>
			</p>
          </div><!--/.nav-collapse -->
        </div>
      </div>
    </div>
	    
		<div class="container">
			<div class="content">
				<tiles:insertAttribute name="body" />
			</div>
		</div>
	</body>
</html>