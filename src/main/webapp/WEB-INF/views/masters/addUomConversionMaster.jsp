<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
<body onload="FocusOnInput()">

	<c:url var="getMixingListWithDate" value="/getMixingListWithDate"></c:url>
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

						<i class="fa fa-file-o"></i>Add Account Head

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
								<i class="fa fa-table"></i>Add UOM Conversion
							</h3>
							<div class="box-tool">
								<a
									href="${pageContext.request.contextPath}/addUomConversionMaster">
									Add UOM Conversion</a> <a data-action="collapse" href="#"><i
									class="fa fa-chevron-up"></i></a>
							</div>

						</div>

						<div class=" box-content">
							<form id="addSupplier"
								action="${pageContext.request.contextPath}/insertConversionMaster"
								onsubmit="return confirm('Do you really want to submit the form?');"
								method="post">
								<div class="box-content">

									<div class="col-md-2">MRN UOM*</div>
									<div class="col-md-3">

										<select class="form-control chosen" name="uom" id="uom"
											required>

											<c:forEach items="${uomList}" var="uomList">

												<c:choose>
													<c:when test="${editUomConversion.uom1==uomList.uomId}">
														<option value="${uomList.uomId}" selected>${uomList.uom}</option>
													</c:when>
													<c:otherwise>
														<option value="${uomList.uomId}">${uomList.uom}</option>
													</c:otherwise>
												</c:choose>


											</c:forEach>

										</select>


									</div>
									<div class="col-md-1"></div>
									<div class="col-md-2">Issue UOM*</div>
									<div class="col-md-3">
										<select class="form-control chosen" name="uom2" id="uom2"
											required>

											<c:forEach items="${uomList}" var="uomList">

												<c:choose>
													<c:when test="${editUomConversion.uom2==uomList.uomId}">
														<option value="${uomList.uomId}" selected>${uomList.uom}</option>
													</c:when>
													<c:otherwise>
														<option value="${uomList.uomId}">${uomList.uom}</option>
													</c:otherwise>
												</c:choose>


											</c:forEach>

										</select> <input id="itemDate" class="form-control date-picker"
											placeholder="Item Date" value="${date}" name="itemDate"
											type="hidden" readonly>


									</div>

								</div>
								<br>
								<div class="box-content">

									<div class="col-md-2">MRN Ratio*</div>
									<div class="col-md-3">

										<input id="uomratio" class="form-control"
											placeholder="MRN Ratio" name="uomratio" value="1"
											style="text-align: left;" pattern="[+-]?([0-9]*[.])?[0-9]+"
											title="Enter in Number Formate" type="text" required
											readonly="readonly">


									</div>
									<div class="col-md-1"></div>
									<div class="col-md-2">Issue Ratio*</div>
									<div class="col-md-3">
										<input id="uom2ratio" class="form-control"
											placeholder="Issue Ratio" name="uom2ratio"
											value="${editUomConversion.ration2}"
											style="text-align: left;" pattern="[+-]?([0-9]*[.])?[0-9]+"
											title="Enter in Number Formate" type="text" required>
									</div>

								</div>
								<br> <br>
								<div class=" box-content">
									<div class="col-md-12" style="text-align: center">
										<input type="submit" class="btn btn-info" value="Submit"
											id="submit"> <input id="convertId"
											class="form-control" name="convertId"
											value="${editUomConversion.convertId}" type="hidden">


									</div>
								</div>


							</form>
							<div class="box-content">

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
												<th class="col-sm-1">Sr no.</th>
												<th class="col-md-2">UOM</th>
												<th class="col-md-2">Ratio</th>
												<th class="col-md-1">Action</th>
											</tr>
										</thead>
										<tbody>

											<c:forEach items="${uomConversionList}"
												var="uomConversionList" varStatus="count">
												<tr>
													<td class="col-md-1"><c:out value="${count.index+1}" /></td>
													<c:set value="-" var="fromUom"></c:set>
													<c:set value="-" var="toUom"></c:set>
													<c:forEach items="${uomList}" var="uomList">

														<c:if test="${uomList.uomId==uomConversionList.uom1}">
															<c:set value="${uomList.uom}" var="fromUom"></c:set>
														</c:if>
														<c:if test="${uomList.uomId==uomConversionList.uom2}">
															<c:set value="${uomList.uom}" var="toUom"></c:set>
														</c:if>


													</c:forEach>
													<td class="col-md-5">${fromUom}&nbsp;To&nbsp;${toUom}</td>
													<td class="col-md-5">${uomConversionList.ration1}&nbsp;To&nbsp;${uomConversionList.ration2}</td>
													<td class="col-md-1"><a
														href="${pageContext.request.contextPath}/editUomConversion/${uomConversionList.convertId}"><abbr
															title="Edit"><i class="fa fa-edit"></i></abbr></a>&nbsp;&nbsp;
														<a
														href="${pageContext.request.contextPath}/deleteUomConversion/${uomConversionList.convertId}"
														onClick="return confirm('Are you sure want to delete this record');"><span
															class="glyphicon glyphicon-remove"></span></a></td>

												</tr>
											</c:forEach>

										</tbody>

									</table>

								</div>
							</div>

						</div>
					</div>


				</div>
			</div>

			<!-- END Main Content -->
			<footer>
				<p>2019 © MONGINIS</p>
			</footer>

			<a id="btn-scrollup" class="btn btn-circle btn-lg" href="#"><i
				class="fa fa-chevron-up"></i></a>
		</div>
		<!-- END Content -->
	</div>
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
		function passwordValidation() {

			var pass = document.getElementById("password").value;
			var pass1 = document.getElementById("rePassword").value;

			if (pass != "" && pass1 != "") {
				if (pass != pass1) {
					alert("Password Not Matched ");
					document.getElementById("submit").disabled = true;
				} else {
					document.getElementById("submit").disabled = false;

				}

			}
		}
	</script>
	<script>
		function myFunction() {
			var input, filter, table, tr, td1, td, i;
			input = document.getElementById("myInput");
			filter = input.value.toUpperCase();
			table = document.getElementById("table1");
			tr = table.getElementsByTagName("tr");
			for (i = 0; i < tr.length; i++) {
				td = tr[i].getElementsByTagName("td")[1];
				td1 = tr[i].getElementsByTagName("td")[2];
				if (td || td1) {

					if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {
						tr[i].style.display = "";
					} else if (td1.innerHTML.toUpperCase().indexOf(filter) > -1) {
						tr[i].style.display = "";
					} else {
						tr[i].style.display = "none";
					}

				}
			}
		}
		function FocusOnInput() {
			document.getElementById("uom").focus();
		}
	</script>

</body>
</html>