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

	
	<%-- HEADER --%>
	<%@ include file="/WEB-INF/jspf/navbar.jspf"%>
	<%-- HEADER --%>
	<div class="container">

		<nav aria-label="breadcrumb">
			<ol class="breadcrumb">
				<div class="dropdown">
					<a class="btn btn-secondary dropdown-toggle float-right" href="#"
						role="button" id="dropdownMenuLink" data-toggle="dropdown"
						aria-haspopup="true" aria-expanded="false"> Status </a>

					<div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
						<a class="dropdown-item" href="controller?command=listCoursesForStudent&status=-1">Not started</a> 
						<a class="dropdown-item" href="controller?command=listCoursesForStudent&status=0">In progress</a> 
						<a class="dropdown-item" href="controller?command=listCoursesForStudent&status=1">Finished</a>
					</div>
				</div>
			</ol>
		</nav>


		<c:forEach var="entryInJournal" items="${listCoursesStudent}">
		<c:set var="pgogress" value="${entryInJournal.getProgressCourseInPercent()}" />
		<c:set var="status" value="${entryInJournal.getStatus()}" />
			<div class="card text-center">
				<div class="card-header">${entryInJournal.getCourse().getCategoryName()}</div>
				<div class="card-body">
				<c:if test="${status eq 0}">
					<div class="badge badge-success text-wrap float-right" style="width: 6rem;">
  						<fmt:message key="courses.label.statusProgress" />
					</div>
				</c:if>
				<c:if test="${status eq 1}">
					<div class="badge badge-danger text-wrap float-right" style="width: 6rem;">
  						<fmt:message key="courses.label.statusFinished" />
					</div>
				</c:if>
				<c:if test="${status eq -1}">
					<div class="badge badge-warning text-wrap float-right" style="width: 6rem;">
  						<fmt:message key="courses.label.statusNotStarted" />
					</div>
				</c:if>
					<h5 class="card-title">${entryInJournal.getCourse().getName()}</h5>
					<p class="card-text">${entryInJournal.getCourse().getDescription()}</p>
					<br>
					<table class="table table-borderless">
						<tr>
							<td scope="col"><label for="exampleInputEmail1"><fmt:message key="courses.label.startDate" />
							</label> <input type="date" class="form-control"
								id="exampleInputEmail1" value=${entryInJournal.getCourse().getStartDate() } readonly>
							</td>
							<td scope="col"><label for="exampleInputEmail1"><fmt:message key="courses.label.endDate" />
							</label> <input type="date" class="form-control"
								id="exampleInputEmail1" value=${entryInJournal.getCourse().getEndDate() } readonly></td>
						</tr>
						<tr>
							<td scope="col"><label for="exampleInputEmail1"><fmt:message key="courses.label.teacher" /></label>
								<input type="text" class="form-control"
								value=${entryInJournal.getCourse().getTeacherName() } readonly></td>
							<td scope="col"><label for="exampleInputEmail1"> <fmt:message key="courses.label.length" /></label>
							 <input type="text" class="form-control"
								value=${entryInJournal.getCourse().getLength() } readonly></td>
						</tr>
					</table>
					<br>
					<c:if test="${not empty user}">
						<form>
							<c:if test="${status ne 1}">
								<div class="progress">
									<div class="progress-bar bg-success" role="progressbar"
										style="width: ${pgogress}%" aria-valuenow=${course.getProgressCourseInPercent()} aria-valuemin="0"
										aria-valuemax="100">${pgogress}%</div>
								</div>
							</c:if>
							<c:if test="${status eq 1}">
								<div class="progress-bar bg-danger" role="progressbar"
									style="width: 100%" aria-valuenow="100" aria-valuemin="0"
									aria-valuemax="100">100%</div>
								<div class="float-right">
									<label for="exampleInputEmail1"><fmt:message key="courses.label.rating" /></label> <input
										type="text" class="form-control" value=${entryInJournal.getRating() } readonly>
								</div>
							</c:if>
						</form>
					</c:if>
				</div>
			</div>
		</c:forEach>
	</div>
</body>
</html>