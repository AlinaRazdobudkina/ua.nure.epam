<html>
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<jsp:useBean id="now" class="java.util.Date" />
<script src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/bootstrap.bundle.min.js"></script>
<script type="text/javascript" src="js/docs.min.js"></script>

<head>

</head>
<body>

	<link rel="stylesheet" href="style/bootstrap.min.css" />

	<%-- HEADER --%>
	<%@ include file="/WEB-INF/jspf/navbar.jspf"%>
	<%-- HEADER --%>
	<div class="container">

		<c:if test="${not empty errorMessage}">
			<div class="alert alert-danger" role="alert">
				<c:out value="${errorMessage}" />
			</div>
		</c:if>

		<form action="controller" method="post">
			<div class="form-row">
				<div class="form-group col-md-8">
					<label for="inputLogin"><fmt:message key="users.label.login" /></label> <input type="text"
						class="form-control" id="inputLogin" name="login"
						value="${user.getLogin()}" required>
				</div>
				<div class="form-group col-md-4">
					<label for="inputPassword"><fmt:message key="users.label.password" /></label> <input type="password"
						min=6 class="form-control" id="inputPassword" name="password"
						value="${user.getPassword()}" required>
				</div>
			</div>

			<div class="form-row">
				<div class="form-group col-md-6">
					<label for="inputFirstName"><fmt:message key="users.label.firstName" /></label> <input type="text"
						class="form-control" id="inputFirstName" name="firstName"
						value="${user.getFirstName()}" required>
				</div>
				<div class="form-group col-md-6">
					<label for="inputLastName"><fmt:message key="users.label.lastName" /></label> <input type="text"
						class="form-control" id="inputLastName" name="lastName"
						value="${user.getLastName()}" required>
				</div>
			</div>

			<div class="form-row">
				<div class="form-group col-md-6">
					<label for="inputEmail"><fmt:message key="users.label.email" /></label> <input type="email"
						class="form-control" id="inputEmail" name="email"
						value="${user.getEmail()}" required>
				</div>
				<div class="form-group col-md-6">
					<label for="inputPhone">Ph<fmt:message key="users.label.phone" />one</label> <input type="tel"
						class="form-control" id="inputPhone" name="phone"
						value="${user.getPhone()}" required>
				</div>
			</div>


			<div class="d-inline p-2 px-auto">

				<input type="hidden" name="command" value="saveTeacher" />
				<button type="submit" class="btn btn-success p-2" id="saveTeacher"><fmt:message key="courses.button.save" /></button>
			</div>
		</form>
	</div>
</body>
</html>