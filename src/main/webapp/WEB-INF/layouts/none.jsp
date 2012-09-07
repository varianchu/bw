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
		
		
		<script type="text/javascript" src="/js/jquery-1.6.4.js"></script>
		<script type="text/javascript" src="/js/bootstrap-dropdown.js"></script>
		<script type="text/javascript" src="/js/bootstrap-alerts.js"></script>
		<script type="text/javascript" src="/js/bootstrap-buttons.js"></script>
		<script type="text/javascript" src="/js/bootstrap-modal.js"></script>
		<script type="text/javascript" src="/js/bootstrap-popover.js"></script>
		<script type="text/javascript" src="/js/bootstrap-scrollspy.js"></script>
		<script type="text/javascript" src="/js/bootstrap-tabs.js"></script>
		<script type="text/javascript" src="/js/bootstrap-twipsy.js"></script>
		<script type="text/javascript" src="/js/jquery.galleriffic.js"></script>
		<script type="text/javascript" src="/js/jquery.history.js"></script>
		<script type="text/javascript" src="/js/jquery.opacityrollover.js"></script>
		
		<script type="text/javascript">
			document.write('<style>.noscript { display: none; }</style>');
		</script>
		</head>
	</head>
	<body>
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
				<tiles:insertAttribute name="body" />
			</div>
		</div>
	</body>
</html>