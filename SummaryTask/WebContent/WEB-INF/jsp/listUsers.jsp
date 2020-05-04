<%@ include file="/WEB-INF/jspf/head.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="language"
	value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
	scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="ua.nure.razdobudkina.SummaryTask4.i18n.text" />

<html lang="${language}">

<script src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/bootstrap.bundle.min.js"></script>
<script type="text/javascript" src="js/docs.min.js"></script>
<script type="text/javascript" src="js/myJS.js"></script>

<head>
</head>
<body>

	<link rel="stylesheet" href="style/bootstrap.min.css" />
	<c:set var="counter" value="1" />

	<%-- HEADER --%>
	<%@ include file="/WEB-INF/jspf/navbar.jspf"%>
	<%-- HEADER --%>
	<div class="container">

		<form name="form1" action="controller" method="post">
			<c:if test="${preCommand == 'teachers'}">
				<a class="btn btn-success" href="controller?command=addTeacher"
					role="button"><fmt:message key="sortedPanel.button.add" /></a>
			</c:if>
			<table class="table table-striped">
				<thead>
					<tr>
						<th scope="col">#</th>
						<c:if test="${preCommand == 'students'}">
							<th scope="col"><fmt:message key="journal.label.student" /></th>
						</c:if>
						<c:if test="${preCommand == 'teachers'}">
							<th scope="col"><fmt:message key="courses.label.teacher" /></th>
						</c:if>
						<th scope="col"><fmt:message key="users.label.login" /></th>
						<th scope="col"><fmt:message key="users.label.phone" /></th>
						<th scope="col"><fmt:message key="users.label.email" /></th>
						<c:if test="${preCommand == 'students'}">
							<th scope="col"><fmt:message key="users.label.active" /></th>
						</c:if>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="user" items="${listUsers}" varStatus="counter">

						<input type="hidden" name="command" value="changeStatusStudent" />
						<input type="hidden" name="userId" value="${user.getId()}" />
						<tr>
							<th scope="row">${counter.count}</th>
							<td>${user.getFullName()}</td>
							<td>${user.getLogin()}</td>
							<td>${user.getPhone()}</td>
							<td>${user.getEmail()}</td>
							<c:if test="${preCommand == 'students'}">
								<td>
									<div class="custom-control custom-switch">
										<c:if test="${user.isActive()}">
											<input type="checkbox"
												class="custom-control-input custom-control-label"
												name="status" id="customSwitch + ${counter.count}"
												value="${user.getId()}"
												onchange="javascript:document.form1.submit()" checked>
											<label class="custom-control-label"
												for="customSwitch + ${counter.count}"></label>
										</c:if>
										<c:if test="${not user.isActive()}">
											<input type="checkbox"
												class="custom-control-input custom-control-label"
												name="status" id="customSwitch + ${counter.count}"
												value="${user.getId()}"
												onchange="javascript:document.form1.submit()">
											<label class="custom-control-label"
												for="customSwitch + ${counter.count}"></label>
										</c:if>
									</div>
								</td>
							</c:if>
						</tr>
					</c:forEach>

				</tbody>
			</table>
		</form>

	</div>
</body>
</html>