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

<head>

</head>
<body>

	<link rel="stylesheet" href="style/bootstrap.min.css" />


	<%@ include file="/WEB-INF/jspf/navbar.jspf"%>

	<div class="container">

		<%@ include file="/WEB-INF/jspf/sortedPanel.jspf"%>

		<c:if test="${not empty result}">
			<div class="alert alert-success" role="alert">
				<c:out value="${result}" />
			</div>
		</c:if>
		<c:if test="${not empty errorMessage}">
			<div class="alert alert-danger" role="alert">
				<c:out value="${errorMessage}" />
			</div>
		</c:if>

		<c:forEach var="course" items="${listCourses}">
		<c:set var="startDate" value="${course.getStartDate()}" />
		<c:set var="endDate" value="${course.getEndDate()}" />
		<c:set var="currentDate" value="${course.getCurrentDate()}" />
		<c:set var="courseId" value="${course.getId()}" />
			<div class="card text-center">
				<div class="card-header">${course.getCategoryName()}</div>
				<div class="card-body">
					<h5 class="card-title">${course.getName()}</h5>
					<p class="card-text">${course.getDescription()}</p>
					<br>
					<table class="table table-borderless">
						<tr>
							<td scope="col"><label for="exampleInputEmail1"><fmt:message key="courses.label.startDate" />
							</label> <input type="date" class="form-control"
								id="exampleInputEmail1" value=${startDate } readonly>
							</td>
							<td scope="col"><label for="exampleInputEmail1"><fmt:message key="courses.label.endDate" />
							</label> <input type="date" class="form-control"
								id="exampleInputEmail1" value=${endDate } readonly></td>
						</tr>
						<tr>
							<td scope="col"><label for="exampleInputEmail1"><fmt:message key="courses.label.teacher" /></label>
								<input type="text" class="form-control"
								value=${course.getTeacherName() } readonly></td>
							<td scope="col"><label for="exampleInputEmail1"><fmt:message key="courses.label.length" />
							</label> <input type="text" class="form-control"
								value=${course.getLength() } readonly></td>
						</tr>
						<tr>
							<td scope="col"><label for="exampleInputEmail1"><fmt:message key="courses.label.countStudents" />
							</label> <input type="number" class="form-control"
								value=${course.getCountOdStudents() } readonly></td>
						</tr>
					</table>
					<br>
					<c:if test="${not empty user}">

						<c:if test="${userRole.name == 'student'}">
							<form action="controller" method="post">
								<input type="hidden" name="command" value="RegisterCourse" /> <input
									type="hidden" name="command" value="openJournal" /> <input
									type="hidden" name="courseId" value="${courseId}" />

								<c:if test="${startDate le currentDate}">
									<span class="d-inline-block" data-toggle="popover"
										data-content="<fmt:message key="courses.message.register" />" data-original-title title>
										<button class="btn btn-primary" style="pointer-events: none;"
											type="button" disabled><fmt:message key="courses.button.register" /></button>
									</span>
								</c:if>
								<c:if test="${startDate gt currentDate}">
									<button class="btn btn-primary" type="submit"
										value="RegisterCourse"><fmt:message key="courses.button.register" /></button>
								</c:if>
							</form>
						</c:if>


						<c:if test="${userRole.name == 'admin'}">
							<div class="m-1">
								<form action="controller" method="post">
									<a
										href="controller?command=editCourse&courseId=${courseId}"
										class="btn btn-primary m-1"><fmt:message key="courses.button.edit" /></a> <a
										href="controller?command=deleteCourse&courseId=${courseId}"
										class="btn btn-danger m-1"><fmt:message key="courses.button.delete" /></a>
								</form>
							</div>
						</c:if>



						<c:if test="${userRole.name == 'teacher'}">

							<form action="controller" method="post">

								<input type="hidden" name="command" value="openJournal" /> <input
									type="hidden" name="courseId" value="${courseId}" />

								<c:if test="${course.getTeacherId() != user.getId()}">
									<span class="d-inline-block" data-toggle="popover"
										data-content="<fmt:message key="courses.message.journal" />">
										<button class="btn btn-primary" style="pointer-events: none;"
											type="button" disabled><fmt:message key="courses.button.journal" /></button>
									</span>
								</c:if>
								<c:if test="${course.getTeacherId() == user.getId()}">
									<button class="btn btn-primary" type="submit"
										value="openJournal"><fmt:message key="courses.button.journal" /></button>
								</c:if>
							</form>
						</c:if>

					</c:if>
				</div>
			</div>
		</c:forEach>
	</div>
</body>
</html>