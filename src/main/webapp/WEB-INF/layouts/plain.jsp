<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
	<head>
		<link type="text/css" media="screen" rel="stylesheet" href="/css/bootstrap.css"/>	
		
		<script type="text/javascript" src="/js/jquery-1.6.4.js"></script>
		<script type="text/javascript" src="/js/bootstrap-dropdown.js"></script>
		<script type="text/javascript" src="/js/bootstrap-alerts.js"></script>
		<script type="text/javascript" src="/js/bootstrap-buttons.js"></script>
		<script type="text/javascript" src="/js/bootstrap-modal.js"></script>
		<script type="text/javascript" src="/js/bootstrap-popover.js"></script>
		<script type="text/javascript" src="/js/bootstrap-scrollspy.js"></script>
		<script type="text/javascript" src="/js/bootstrap-tabs.js"></script>
		<script type="text/javascript" src="/js/bootstrap-twipsy.js"></script>
		
		<!-- 		JSPDF FILES -->
		
		<script type="text/javascript" src="/js/jspdf.js"></script>
		<script type="text/javascript" src="/js/FileSaver.min.js"></script>
		<script type="text/javascript" src="/js/BlobBuilder.js"></script>

		<script type="text/javascript" src="/js/jspdf.plugin.addimage.js"></script>

		<script type="text/javascript" src="/js/jspdf.plugin.standard_fonts_metrics.js"></script>
		<script type="text/javascript" src="/js/jspdf.plugin.split_text_to_size.js"></script>
		<script type="text/javascript" src="/js/jspdf.plugin.from_html.js"></script>
		
	</head>
	<body>
		<div class="container">
			<div class="content">
				<tiles:insertAttribute name="body" />
			</div>
		</div>
	</body>
</html>