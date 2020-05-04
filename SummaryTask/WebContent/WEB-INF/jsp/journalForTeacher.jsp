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
<script type="text/javascript" src="js/myJS.js"></script>

<head>
</head>
<body>

	<link rel="stylesheet" href="style/bootstrap.min.css" />


	<%-- HEADER --%>
	<%@ include file="/WEB-INF/jspf/navbar.jspf"%>
	<%-- HEADER --%>
	<div class="container">

		<div class="d-inline p-2">

			<div class="alert alert-success  p-2" role="alert" id="success-alert">
				<button type="button" class="close" data-dismiss="alert"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<strong>Success!</strong> You have been saved in successfully!
			</div>
		</div>
		<div class="d-inline p-2 float-right">

			<label for="EndDate"><fmt:message key="courses.label.endDate" /></br> <input type="date"
				class="float-right p-2" id="EndDate" value=${EndDate } readonly></label>


		</div>

		<form name="form1" action="controller" method="post">

 		<input type="hidden" name="result" value="" />
			<table class="table table-striped">
				<thead>
					<tr>
						<th scope="col">#</th>
						<th scope="col"><fmt:message key="journal.label.student" /></th>
						<th scope="col"><fmt:message key="users.label.phone" /></th>
						<th scope="col"><fmt:message key="users.label.email" /></th>
						<th scope="col"><fmt:message key="courses.label.rating" /></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="entryInJournal" items="${journalForTeacher}">

						<input type="hidden" name="command" value="saveJournal" />
						<input type="hidden" name="user_id"
							value="${entryInJournal.getUser().getId()}" />
						<input type="hidden" name="courseId" value="${courseId }" />
						<tr>
							<th scope="row">1</th>
							<td>${entryInJournal.getUser().getFullName()}</td>
							<td>${entryInJournal.getUser().getPhone()}</td>
							<td>${entryInJournal.getUser().getEmail()}</td>

							<td>
								<div class="changeRating">
									<input type="number" id="rating" name="rating"
										onchange="javascript:document.form1.submit()" min=1 max=5
										step=1 class="form-control"
										value=${entryInJournal.getRating() }>
								</div>
							</td>
						</tr>

					</c:forEach>

				</tbody>
			</table>
		</form>

	</div>
</body>
</html>