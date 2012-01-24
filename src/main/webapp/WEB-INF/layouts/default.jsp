<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=8" />
		<link type="text/css" media="screen" rel="stylesheet" href="/css/bootstrap.css"/>

		<script type="text/javascript" src="/js/jquery-1.6.4.js"></script>
		<script type="text/javascript" src="/js/bootstrap-dropdown.js"></script>
	</head>

  	<body style="padding-top: 60px;">
	  	<div class="topbar">
			<div class="topbar-inner">
				<div class="container-fluid">
					<a class="brand" href="">Bionic Wheels System</a>
					<tiles:insertAttribute name="menu"/>
					<p class="pull-right" style="color: white;">Logged in as <span id="core_username"><sec:authentication property="principal.username" /></span> |
					<a class="" href="/j_spring_security_logout">&nbsp;Log out</a>
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
		          <p>&copy; Bionic Wheels 2012 | Powered by AltoStratus Inc.</p>
		        </footer>
		    </div>
	    </div>
	</body>
</html>