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
								<i class="fa fa-table"></i>Issue Return Process
							</h3>
						</div>

						<div class="box-content">
							<form id="addSupplier"
								action="${pageContext.request.contextPath}/submitIssueReturnProcess"
								onsubmit="return confirm('Do you really want to submit the form?');"
								method="post">
								<div class="box-content">

									<div class="col-md-2">Select Date*</div>
									<div class="col-md-3">
										<input id="issueDate" class="form-control date-picker"
											placeholder="Select Date" value="${fromDate}"
											name="issueDate" type="text" required>


									</div>
									<div class="col-md-1"></div>
									<div class="col-md-2">Select Items*</div>
									<div class="col-md-3">
										<select class="form-control chosen" name="itemIds"
											id="itemIds" multiple="multiple">
											<c:forEach items="${itemList}" var="itemList">
												<option value="${itemList.itemId}">${itemList.itemDesc}</option>
											</c:forEach>
										</select>
									</div>


								</div>
								<br> <br>

								<div class="form-group">
									<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-5">
										<input type="button" class="btn btn-primary" value="Search"
											onclick="search()">
									</div>
								</div>
								<br>

								<div align="center" id="loader" style="display: none">

									<span>
										<h4>
											<font color="#343690">Loading</font>
										</h4>
									</span> <span class="l-1"></span> <span class="l-2"></span> <span
										class="l-3"></span> <span class="l-4"></span> <span
										class="l-5"></span> <span class="l-6"></span>
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

												<th style="width: 2%;">Sr no.</th>
												<th class="col-md-2">Issue No</th>
												<th>Item Name</th>
												<th class="col-md-1">Issue UOM</th>
												<th class="col-md-1">Issue QTY</th>
												<th class="col-md-1">Return QTY</th>
											</tr>
										</thead>
										<tbody>



										</tbody>

									</table>


									<br> <br>
									<div class=" box-content">
										<div class="col-md-12" style="text-align: center">


											<input type="submit" class="btn btn-info" value="Submit"
												id="submit">

										</div>
									</div>
								</div>
							</form>
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
		function search() {

			var issueDate = $("#issueDate").val();
			var itemIds = $("#itemIds").val();

			$('#loader').show();
			var fd = new FormData();
			fd.append('date', issueDate);
			fd.append('itemIds', itemIds);
			$
					.ajax({
						url : '${pageContext.request.contextPath}/getIssueItemList',
						type : 'post',
						dataType : 'json',
						data : fd,
						contentType : false,
						processData : false,
						success : function(response) {

							$('#loader').hide();

							$('#table1 td').remove();

							$
									.each(
											response,
											function(key, itemList) {

												var tr = $('<tr></tr>');
												tr.append($('<td></td>').html(
														key + 1));
												tr.append($('<td></td>').html(
														itemList.issueNo));
												tr.append($('<td></td>').html(
														itemList.itemDesc));
												tr.append($('<td></td>').html(
														itemList.uom));
												tr
														.append($('<td></td>')
																.html(
																		itemList.itemRequestQty));
												tr
														.append($('<td></td>')
																.html(
																		'<input id="returnQty'
																				+ itemList.issueDetailId
																				+ '" class="form-control date-picker" '
																				+ 'placeholder="Enter QTY" value="0" name="returnQty'
																				+ itemList.issueDetailId
																				+ '" type="text"'
																				+ ' data-orignalqty="'
																				+ itemList.itemRequestQty
																				+ '" onchange="qtyValidation('
																				+ itemList.issueDetailId
																				+ ')">'));
												$('#table1 tbody').append(tr);
											})
						},
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
				td3 = tr[i].getElementsByTagName("td")[5];
				if (td || td1) {

					if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {
						tr[i].style.display = "";
					} else if (td1.innerHTML.toUpperCase().indexOf(filter) > -1) {
						tr[i].style.display = "";
					} else if (td2.innerHTML.toUpperCase().indexOf(filter) > -1) {
						tr[i].style.display = "";
					} else if (td3.innerHTML.toUpperCase().indexOf(filter) > -1) {
						tr[i].style.display = "";
					} else {
						tr[i].style.display = "none";
					}

				}

			}
		}
	</script>



	<script type="text/javascript">
		function genPdf(id) {
			//alert(id);

			window.open('pdfForReport?url=/pdf/issueListDoc/' + id);

		}

		function qtyValidation(id) {
			var orignalOty = parseFloat($("#returnQty" + id).data("orignalqty"));
			var qty = parseFloat($("#returnQty" + id).val());

			if (qty > orignalOty || isNaN(qty)) {
				$("#returnQty" + id).val(0)
			}
		}

		function commonPdf() {

			var list = [];

			$("input:checkbox[name=name1]:checked").each(function() {
				list.push($(this).val());
			});

			window.open('pdfForReport?url=/pdf/issueListDoc/' + list);

		}
	</script>


</body>
</html>