<%@ include file="/WEB-INF/jspf/head.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="language"
	value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
	scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="ua.nure.razdobudkina.SummaryTask4.i18n.text" />

<html lang="${language}">

<title><fmt:message key="title.editCourse" /></title>
<jsp:useBean id="now" class="java.util.Date" />
<script src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/bootstrap.bundle.min.js"></script>
<script type="text/javascript" src="js/docs.min.js"></script>

<head>

</head>
<body>

	<link rel="stylesheet" href="style/bootstrap.min.css" />
	<c:set var="now" value="${course.getCurrentDate()}" />
	<c:set var="startDate" value="${course.getStartDate()}" />
	<c:set var="nowPlusDay" value="${course.getSumDateDays(now, 1)}" />

	<%-- HEADER --%>
	<%@ include file="/WEB-INF/jspf/navbar.jspf"%>
	<%-- HEADER --%>
	<div class="container">

		<form action="controller" method="post">
			<div class="form-row">
				<div class="form-group col-md-4">
					<label for="inputCategory"><fmt:message key="courses.label.topic" /></label> <select id="inputCategory"
						class="form-control" name="category">
						<c:forEach var="category" items="${listCategories}">
							<c:if test="${course.getCategoryId() == category.getId()}">
								<option value="${category.getId()}" selected>${category.getName()}</option>
							</c:if>
							<c:if test="${course.getCategoryId() != category.getId()}">
								<option value="${category.getId()}">${category.getName()}</option>
							</c:if>
						</c:forEach>
					</select>
				</div>
				<div class="form-group col-md-8">
					<label for="inputName"><fmt:message key="courses.label.name" /></label> <input type="text"
						class="form-control" id="inputName" name="name"
						value="${course.getName() }" required>
				</div>
			</div>
			<div class="form-group">
				<label for="inputDescription"><fmt:message key="courses.label.description" /></label>
				<textarea class="form-control" name="description"
					id="inputDescription" rows="3" required>${course.getDescription()}</textarea>
			</div>

			<div class="form-row">
				<div class="form-group col-md-6">
					<label for="inputStartDate"><fmt:message key="courses.label.startDate" /></label>
					<c:if test="${preCommand == 'add' }">
					 <input type="date" min="${nowPlusDay }" class="form-control" id="inputStartDate"
						name="startDate" value="${startDate }" required>
					</c:if>
					<c:if test="${preCommand == 'update' }">
					 <input type="date" class="form-control" id="inputStartDate"
						name="startDate" value="${startDate }" required>
					</c:if>
				</div>
				<div class="form-group col-md-6">
					<label for="inputEndDate"><fmt:message key="courses.label.endDate" /></label> <input type="date" name="endDate"
						class="form-control" id="inputEndDate"
						value="${course.getEndDate() }" required>
				</div>
			</div>
			<div class="form-row">
				<div class="form-group col-md-6">
					<label for="inputTeacher"><fmt:message key="courses.label.teacher" /></label> <select id="inputTeacher"
						class="form-control" name="teacher">
						<c:forEach var="teacher" items="${listTheacheres}">
							<c:if test="${course.getTeacherId() == teacher.getId() }">
								<option selected value="${teacher.getId()}">${teacher.getFullName()}</option>
							</c:if>
							<c:if test="${course.getTeacherId() != teacher.getId() }">
								<option value="${teacher.getId()}">${teacher.getFullName()}</option>
							</c:if>
						</c:forEach>
					</select>
				</div>
				<div class="form-group col-md-6">
					<label for="inputLength"><fmt:message key="courses.label.length" /></label> <input type="number" step=1
						min=1 class="form-control" id="inputLength" name="length"
						value="${course.getLength() }" required>
				</div>
			</div>

			<div class="d-inline p-2 px-auto">

				<input type="hidden" name="command" value="acceptEditCourse" /> <input
					type="hidden" name="courseId" value="${course.getId()}" />
					<input type="hidden" name="preCommand" value="${preCommand}" />
				<button type="submit" class="btn btn-success p-2"
					id="acceptEditCourse"><fmt:message key="courses.button.save" /></button>
			</div>
		</form>
	</div>
	<script>
		$(document).ready(function(){
  			$("#inputStartDate").change(function(){
	 			$("#inputEndDate").attr({
   					"min" : this.value
				});
  			});
		});
	</script>
</body>
</html>