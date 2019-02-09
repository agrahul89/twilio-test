<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>TwilioTester</title>
<base href="${pageContext.request.requestURL}">
<script type="text/javascript" src="https://code.jquery.com/jquery-1.12.4.js"></script>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
	<div class="header">
		<h3 style="margin-left: 1em;">
			Rest Service Deployed in OpenShift | <label style="color: #e51c23">TwilioTester</label>
		</h3>
	</div>
	<div class="body">
		<div class="navigation">
			<div class="menu-tab"><a href="sms.jsp">Test SMS</a></div>
			<div class="menu-tab"><a href="call.jsp">Test CALL</a></div>
			<div class="menu-tab"><a href="snoop.jsp">View JVM Statistics</a></div>
		</div>
		<div class="content"></div>
	</div>
	<div class="footer"></div>
</body>
<script type="text/javascript">
	$("a").click(function(event) {
		event.preventDefault();
		var url = $(this).attr('href');
		$("div.content").load(url);
	});
</script>
</html>
