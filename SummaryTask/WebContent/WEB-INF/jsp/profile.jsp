<%@ include file="/WEB-INF/jspf/head.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="language"
	value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
	scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="ua.nure.razdobudkina.SummaryTask4.i18n.text" />

<html lang="${language}">

<jsp:useBean id="now" class="java.util.Date" />	
	<script src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/bootstrap.bundle.min.js"></script>
<script type="text/javascript" src="js/docs.min.js"></script>

<head>

</head>
<body>

	<link rel="stylesheet" href="style/bootstrap.min.css" />

	<%@ include file="/WEB-INF/jspf/navbar.jspf"%>

	<div class="container">

			<div class="container">
				<br>
				<div class="media">
					<img src="img/user.png" class="mr-4" alt="">
					<div class="media-body">
						<div class="form-group">
							<label for="login"><fmt:message key="users.label.login" /></label> <input type="text"
								class="form-control w-50 p-3" id="login"
								value="${user.getLogin()}" readonly>
						</div>
						<div class="form-group">
							<label for="firstName"><fmt:message key="users.label.firstName" /></label> <input type="text"
								class="form-control w-50 p-3" id="login"
								value="${user.getFirstName()}" readonly>
						</div>
						<div class="form-group">
							<label for="lastName"><fmt:message key="users.label.lastName" /></label> <input type="text"
								class="form-control w-50 p-3" id="login"
								value="${user.getLastName()}" readonly>
						</div>
						<div class="form-group">
							<label for="login"><fmt:message key="users.label.phone" /></label> <input type="tel"
								class="form-control w-50 p-3" id="login"
								value="${user.getPhone()}" readonly>
						</div>
						<div class="form-group">
							<label for="email"><fmt:message key="users.label.email" /></label> <input type="email"
								class="form-control w-50 p-3" id="email"
								value="${user.getEmail()}" readonly>
						</div>
						<form name="form1" class="form-inline my-2 my-lg-0">
							<input type="hidden" name="command" value="changeLanguage" /> <select
								id="language" class="custom-select btn-outline my-2 my-sm-0"
								name="language" onchange="javascript:document.form1.submit()">
								<option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
								<option value="ru" ${language == 'ru' ? 'selected' : ''}>Russian</option>
							</select>
						</form>

					</div>
				</div>
			</div>
	</div>

</body>
</html>