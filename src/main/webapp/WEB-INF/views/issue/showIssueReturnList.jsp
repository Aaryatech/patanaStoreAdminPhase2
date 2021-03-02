<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
<body>

	<c:url var="getIssueDateBetweenDate" value="/getIssueDateBetweenDate"></c:url>
	<c:url var="getMixingAllListWithDate" value="/getMixingAllListWithDate"></c:url>


	<div class="container" id="main-container">

		<!-- BEGIN Sidebar -->
		<div id="sidebar" class="navbar-collapse collapse">

			<jsp:include page="/WEB-INF/views/include/navigation.jsp"></jsp:include>

			<div id="sidebar-collapse" class="visible-lg">
				<i class="fa fa-angle-double-left"></i>
			</div>
			<!-- END Sidebar Collapse Button -->
		</div>
		<!-- END Sidebar -->

		<!-- BEGIN Content -->
		<div id="main-content">
			<!-- BEGIN Page Title -->
			<!-- <div class="page-title">
				<div>
					<h1>

						<i class="fa fa-file-o"></i>Issue List

					</h1>
				</div>
			</div> -->
			<br>
			<!-- END Page Title -->

			<div class="row">
				<div class="col-md-12">

					<div class="box" id="todayslist">
						<div class="box-title">
							<h3>
								<i class="fa fa-table"></i>Issue List
							</h3>
							<div class="box-tool">
								<a href="${pageContext.request.contextPath}/addIssueItem">
									Add Issue </a> <a data-action="collapse" href="#"><i
									class="fa fa-chevron-up"></i></a>
							</div>

						</div>

						<div class="box-content">
							<form
								action="${pageContext.request.contextPath}/getIssueReturnList"
								class="form-horizontal" id="validation-form" method="get">
								<div class="box-content">

									<div class="col-md-2">From Date*</div>
									<div class="col-md-3">
										<input id="fromDate" class="form-control date-picker"
											placeholder="From Date" value="${fromDate}" name="fromDate"
											type="text" required>


									</div>
									<div class="col-md-1"></div>
									<div class="col-md-2">To Date*</div>
									<div class="col-md-3">
										<input id="toDate" class="form-control date-picker"
											placeholder="To Date" value="${toDate}" name="toDate"
											type="text" required>


									</div>


								</div>
								<br> <br>


								<div class="form-group">
									<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-5">
										<input type="submit" class="btn btn-primary" value="Submit">
									</div>
								</div>
								<br>
							</form>
							<div align="center" id="loader" style="display: none">

								<span>
									<h4>
										<font color="#343690">Loading</font>
									</h4>
								</span> <span class="l-1"></span> <span class="l-2"></span> <span
									class="l-3"></span> <span class="l-4"></span> <span class="l-5"></span>
								<span class="l-6"></span>
							</div>

							<div class="col-md-9"></div>
							<label for="search" class="col-md-3" id="search"> <i
								class="fa fa-search" style="font-size: 20px"></i> <input
								type="text" id="myInput" onkeyup="myFunction()"
								placeholder="Search.." title="Type in a name">
							</label> <br /> <br />
							<div class="clearfix"></div>
							<div class="table-responsive" style="border: 0">
								<table class="table table-advance" id="table1">
									<thead>
										<tr class="bgpink">

											<th style="width: 5%; text-align: center;">Sr no.</th>
											<th class="col-md-1" style="text-align: center;">Return
												Date</th>
											<th style="text-align: center;">Item name</th>
											<th style="text-align: center;" class="col-md-1">UOM</th>
											<th style="text-align: center;" class="col-md-1">QTY</th>
										</tr>
									</thead>
									<tbody>

										<c:forEach items="${getIssueReturnList}"
											var="getIssueReturnList" varStatus="count">
											<tr>

												<td><c:out value="${count.index+1}" /></td>


												<td><c:out value="${getIssueReturnList.returnDate}" /></td>

												<td><c:out value="${getIssueReturnList.itemDesc}" /></td>

												<td><c:out value="${getIssueReturnList.uom}" /></td>

												<td style="text-align: right;"><c:out
														value="${getIssueReturnList.returnQty}" /></td>


											</tr>
										</c:forEach>

									</tbody>

								</table>
								<div class="form-group" id="range">



									<div class="col-sm-3  controls">
										<input type="button" id="expExcel" class="btn btn-primary"
											disabled="disabled" value="EXPORT TO Excel"
											onclick="exportToExcel();">
									</div>
								</div>
								<button class="btn btn-primary" value="PDF" id="PDFButton"
									onclick="genPdf()">PDF</button>

								<br> <br>
								<!-- <button
									style="background-color: #008CBA; border: none; color: white; text-align: center; text-decoration: none; display: block; font-size: 12px; cursor: pointer; width: 50px; height: 30px; margin: auto;"
									onclick="commonPdf()">PDF</button> -->



							</div>
						</div>



					</div>

				</div>

			</div>
			<footer>
				<p>2019 © MONGINIS</p>
			</footer>
		</div>


	</div>

	<!-- END Content -->

	<!-- END Container -->

	<!--basic scripts-->
	<script
		src="//ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
	<script>
		window.jQuery
				|| document
						.write('<script src="${pageContext.request.contextPath}/resources/assets/jquery/jquery-2.0.3.min.js"><\/script>')
	</script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/bootstrap/js/bootstrap.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/jquery-slimscroll/jquery.slimscroll.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/jquery-cookie/jquery.cookie.js"></script>

	<!--page specific plugin scripts-->
	<script
		src="${pageContext.request.contextPath}/resources/assets/flot/jquery.flot.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/flot/jquery.flot.resize.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/flot/jquery.flot.pie.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/flot/jquery.flot.stack.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/flot/jquery.flot.crosshair.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/flot/jquery.flot.tooltip.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/sparkline/jquery.sparkline.min.js"></script>


	<!--page specific plugin scripts-->
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/jquery-validation/dist/jquery.validate.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/jquery-validation/dist/additional-methods.min.js"></script>





	<!--flaty scripts-->
	<script src="${pageContext.request.contextPath}/resources/js/flaty.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/flaty-demo-codes.js"></script>
	<!--page specific plugin scripts-->
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/bootstrap-fileupload/bootstrap-fileupload.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/chosen-bootstrap/chosen.jquery.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/clockface/js/clockface.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/bootstrap-timepicker/js/bootstrap-timepicker.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/bootstrap-colorpicker/js/bootstrap-colorpicker.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/bootstrap-daterangepicker/date.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/bootstrap-daterangepicker/daterangepicker.js"></script>
<script type="text/javascript">
		function exportToExcel() {

			window.open("${pageContext.request.contextPath}/exportToExcel");
			document.getElementById("expExcel").disabled = true;
		}
	</script>

	<script type="text/javascript">
		function genPdf() {
			
			var fromDate = document.getElementById("fromDate").value;
			var toDate = document.getElementById("toDate").value;

			window.open('${pageContext.request.contextPath}/getIssueReturnPdf/'
					+ fromDate + '/' + toDate);
			document.getElementById("expExcel").disabled = false;

		}
	</script>

	<script type="text/javascript">
		function search() {

			var fromDate = $("#fromDate").val();
			var toDate = $("#toDate").val();

			if (fromDate == "" || fromDate == null)
				alert("Select From Date");
			else if (toDate == "" || toDate == null)
				alert("Select To Date");

			$('#loader').show();

			$
					.getJSON(
							'${getIssueDateBetweenDate}',

							{

								fromDate : fromDate,
								toDate : toDate,
								ajax : 'true'

							},
							function(data) {

								$('#table1 td').remove();
								$('#loader').hide();

								if (data == "") {
									alert("No records found !!");

								}

								$
										.each(
												data,
												function(key, itemList) {

													var tr = $('<tr></tr>');
													tr
															.append($(
																	'<td></td>')
																	.html(
																			'<input type="checkbox"  name="name1" value="'+ itemList.issueId +'"/>'));

													tr.append($('<td></td>')
															.html(key + 1));
													tr
															.append($(
																	'<td></td>')
																	.html(
																			itemList.issueNo));
													tr
															.append($(
																	'<td></td>')
																	.html(
																			itemList.issueDate));
													tr
															.append($(
																	'<td></td>')
																	.html(
																			itemList.deptCode));
													tr
															.append($(
																	'<td></td>')
																	.html(
																			itemList.subDeptCode));
													tr
															.append($(
																	'<td></td>')
																	.html(
																			'<a href="javascript:genPdf('
																					+ itemList.issueId
																					+ ');"><abbr'+
													'title="PDF"><i class="glyphicon glyphicon glyphicon-file"></i></abbr></a>	<a href="${pageContext.request.contextPath}/editIssueHeader/'+itemList.issueId+'"><abbr'+
													'title="Edit"><i class="fa fa-edit"></i></abbr></a> <a href="${pageContext.request.contextPath}/deleteIssueHeader/'
																					+ itemList.issueId
																					+ '"'
																					+ 'onClick="return confirm("Are you sure want to delete this record");"><span class="glyphicon glyphicon-remove"></span></a>'));
													$('#table1 tbody').append(
															tr);
												})

							});
		}
	</script>


	<script>
		function myFunction() {
			var input, filter, table, tr, td, td1, td2, td3, i;
			input = document.getElementById("myInput");
			filter = input.value.toUpperCase();
			table = document.getElementById("table1");
			tr = table.getElementsByTagName("tr");
			for (i = 0; i < tr.length; i++) {
				td = tr[i].getElementsByTagName("td")[2];
				td1 = tr[i].getElementsByTagName("td")[3];
				td2 = tr[i].getElementsByTagName("td")[4];
				if (td || td1) {

					if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {
						tr[i].style.display = "";
					} else if (td1.innerHTML.toUpperCase().indexOf(filter) > -1) {
						tr[i].style.display = "";
					} else if (td2.innerHTML.toUpperCase().indexOf(filter) > -1) {
						tr[i].style.display = "";
					} else {
						tr[i].style.display = "none";
					}

				}

			}
		}
	</script>



	<!-- <script type="text/javascript">
		function genPdf(id) {
			//alert(id);

			window.open('pdfForReport?url=/pdf/issueListDoc/' + id);

		}

		function commonPdf() {

			var list = [];

			$("input:checkbox[name=name1]:checked").each(function() {
				list.push($(this).val());
			});

			window.open('pdfForReport?url=/pdf/issueListDoc/' + list);

		}
	</script> -->


</body>
</html>