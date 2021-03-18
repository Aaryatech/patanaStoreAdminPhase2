<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/assets/bootstrap-datepicker/css/datepicker.css" />


<style>
.tooltip {
	position: relative;
	display: inline-block;
	border-bottom: 1px dotted black;
}

.tooltip .tooltiptext {
	visibility: hidden;
	width: 120px;
	background-color: black;
	color: #fff;
	text-align: center;
	border-radius: 6px;
	padding: 5px 0;
	/* Position the tooltip */
	position: absolute;
	z-index: 1;
}

.tooltip:hover .tooltiptext {
	visibility: visible;
}
</style>
<style>
body {
	font-family: Arial, Helvetica, sans-serif;
}

/* The Modal (background) */
.modal {
	display: none; /* Hidden by default */
	position: fixed; /* Stay in place */
	z-index: 1; /* Sit on top */
	padding-top: 20px; /* Location of the box */
	left: 0;
	top: 0;
	width: 100%; /* Full width */
	height: 100%; /* Full height */
	overflow: auto; /* Enable scroll if needed */
	background-color: rgb(0, 0, 0); /* Fallback color */
	background-color: rgba(0, 0, 0, 0.4); /* Black w/ opacity */
}

/* Modal Content */
.modal-content {
	background-color: #fefefe;
	margin: auto;
	padding: 20px;
	border: 1px solid #888;
	width: 100%;
	height: 100%;
}

/* The Close Button */
.close {
	color: #aaaaaa;
	float: right;
	font-size: 28px;
	font-weight: bold;
}

.close:hover, .close:focus {
	color: #000;
	text-decoration: none;
	cursor: pointer;
}

#overlay {
	position: fixed;
	display: none;
	width: 100%;
	height: 100%;
	top: 0;
	left: 0;
	right: 0;
	bottom: 0;
	background-color: rgba(101, 113, 119, 0.5);
	z-index: 2;
	cursor: pointer;
}

#text {
	position: absolute;
	top: 50%;
	left: 50%;
	font-size: 25px;
	color: white;
	transform: translate(-50%, -50%);
	-ms-transform: translate(-50%, -50%);
}

.bg-overlay {
	background: linear-gradient(rgba(0, 0, 0, .7), rgba(0, 0, 0, .7)),
		url("${pageContext.request.contextPath}/resources/images/smart.jpeg");
	background-repeat: no-repeat;
	background-size: cover;
	background-position: center center;
	color: #fff;
	height: auto;
	width: auto;
	padding-top: 10px;
	padding-left: 20px;
}

.th {
	text-align: center;
	position: sticky;
	top: 0;
	z-index: 5;
	background: #f95d64;
	color: white !important;
}
</style>

<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
<body>

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

		<!-- BEGIN Main Content -->
		<div class="box">
			<div class="box-title">
				<h3>
					<i class="fa fa-bars"></i>Bill Book Dashboard
				</h3>
			</div>

			<div class=" box-content">

				<div>

					<div class=" box-content">
						<div class="row">
							<div
								style="overflow: scroll; height: 100%; width: 100%; overflow: auto">
								<table width="100%" border="0"
									class="table table-bordered table-striped fill-head "
									style="width: 100%; font-size: 14px;" id="table_grid2">
									<thead>
										<tr>

											<th class="th" width="2%">SR</th>
											<th class="th">Vendor Name</th>
											<th class="th">Bill No</th>
											<th class="th">Bill Date</th>
											<th class="th">Bill Book No</th>
											<th class="th">Bill Amount</th>
											<th class="th">Expiry Date</th>
											<th class="th">Remaining Days</th>
											<th class="th">Pending Amount</th>
											<th class="th">Paid Amount</th>
											<th class="th">Action</th>


										</tr>
									</thead>
									<tbody>



										<c:forEach items="${billList}" var="bill" varStatus="count">

											<c:choose>

												<c:when test="${bill.chkDate ==0}">

													<tr>

														<td style="width: 2%; background: red; color: white;"><c:out
																value="${count.index+1}" /></td>
														<td style="background: red; color: white;">${bill.vendorName}</td>
														<td style="background: red; color: white;">${bill.billNo}</td>
														<td
															style="text-align: center; background: red; color: white;">${bill.billDate}</td>
														<td style="background: red; color: white;">${bill.billBookNo}</td>
														<td
															style="text-align: right; background: red; color: white;">${bill.totalValue}</td>
														<td
															style="text-align: center; background: red; color: white;">${bill.expiryDate}</td>
														<td
															style="text-align: left; background: red; color: white;">${bill.days}</td>
														<td
															style="text-align: right; background: red; color: white;"><fmt:formatNumber
																type="number" minFractionDigits="2"
																maxFractionDigits="2"
																value="${bill.totalValue-bill.paidAmt}" /></td>
														<td
															style="text-align: right; background: red; color: white;">${bill.paidAmt}</td>
														<td
															style="text-align: center; background: red; color: white;"><a
															href="${pageContext.request.contextPath}/billSettle/${bill.vendorId}"
															title="Bill Settle"><i class="fa fa-inr"
																style="font-size: 20px; color: white;"></a></td>
													</tr>

												</c:when>

												<c:otherwise>

													<tr>

														<td style="width: 2%;"><c:out
																value="${count.index+1}" /></td>
														<td>${bill.vendorName}</td>
														<td>${bill.billNo}</td>
														<td style="text-align: center;">${bill.billDate}</td>
														<td>${bill.billBookNo}</td>
														<td style="text-align: right;">${bill.totalValue}</td>
														<td style="text-align: center;">${bill.expiryDate}</td>
														<td style="text-align: left;">${bill.days}</td>
														<td style="text-align: right;"><fmt:formatNumber
																type="number" minFractionDigits="2"
																maxFractionDigits="2"
																value="${bill.totalValue-bill.paidAmt}" /></td>
														<td style="text-align: right;">${bill.paidAmt}</td>
														<td style="text-align: center;"><a
															href="${pageContext.request.contextPath}/billSettle/${bill.vendorId}"
															title="Bill Settle"><i class="fa fa-inr"
																style="font-size: 20px; color: #f95d64"></a></td>
													</tr>

												</c:otherwise>

											</c:choose>




										</c:forEach>




									</tbody>
								</table>
							</div>
						</div>

					</div>
					<br />


				</div>
			</div>
		</div>
		<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
	</div>
	<!-- END Main Content -->



	<a id="btn-scrollup" class="btn btn-circle btn-lg" href="#"><i
		class="fa fa-chevron-up"></i></a>




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
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/chosen-bootstrap/chosen.jquery.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/bootstrap-inputmask/bootstrap-inputmask.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/jquery-tags-input/jquery.tagsinput.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/jquery-pwstrength/jquery.pwstrength.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/bootstrap-fileupload/bootstrap-fileupload.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/bootstrap-duallistbox/duallistbox/bootstrap-duallistbox.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/dropzone/downloads/dropzone.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/bootstrap-timepicker/js/bootstrap-timepicker.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/clockface/js/clockface.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/bootstrap-colorpicker/js/bootstrap-colorpicker.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/bootstrap-daterangepicker/date.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/bootstrap-daterangepicker/daterangepicker.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/bootstrap-switch/static/js/bootstrap-switch.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/bootstrap-wysihtml5/wysihtml5-0.3.0.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/bootstrap-wysihtml5/bootstrap-wysihtml5.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/ckeditor/ckeditor.js"></script>

	<!--flaty scripts-->
	<script src="${pageContext.request.contextPath}/resources/js/flaty.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/flaty-demo-codes.js"></script>



</body>
</html>