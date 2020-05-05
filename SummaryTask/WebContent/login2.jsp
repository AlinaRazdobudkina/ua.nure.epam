<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="language"
	value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
	scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="ua.nure.razdobudkina.SummaryTask4.i18n.text" />

<html lang="${language}">

<title><fmt:message key="title.login" /></title>

<%@ include file="/WEB-INF/jspf/head.jspf"%>
<script src="js/jquery.min.js"></script>
<link rel="stylesheet" href="style/bootstrap.min.css" type="text/css" />
<link href="style/signin.css" rel="stylesheet" type="text/css">



<body class="text-center">

	<c:set var="title" value="Login" />
	<div class="form-signin">
		<form id="login_form" action="controller"
			method="post">
			<input type="hidden" name="command" value="login" />
			<c:if test="${not empty errorMessage}">
				<div class="alert alert-danger" role="alert">
					<c:out value="${errorMessage}" />
				</div>
			</c:if>
			<h1 class="h3 mb-3 font-weight-normal"><fmt:message key="login.h1.signIn" /></h1>
			<label for="inputLogin" class="sr-only"><fmt:message
					key="login.label.username" /></label> <input type="text" id="inputEmail"
				class="form-control" placeholder="Login" name="login" required
				autofocus> <label for="inputPassword" class="sr-only"><fmt:message
					key="login.label.password" /></label> <input type="password"
				id="inputPassword" class="form-control" placeholder="Password"
				name="password" required>
			<button class="btn btn-lg btn-primary btn-block" type="submit">
				<fmt:message key="login.button.submit" />
			</button>
			<br>
		</form>
		<form name="form1" class="form-inline my-2 my-lg-0">
			<select id="language" class="custom-select btn-outline my-2 my-sm-0"
				name="language" onchange="javascript:document.form1.submit()">
				<option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
				<option value="ru" ${language == 'ru' ? 'selected' : ''}>Russian</option>
			</select>
		</form>
	</div>
</body>
</html>