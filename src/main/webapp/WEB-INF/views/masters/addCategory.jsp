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

						<i class="fa fa-file-o"></i>Add Category

					</h1>
				</div>
			</div> --><br>
			<!-- END Page Title -->

			<div class="row">
				<div class="col-md-12">

					<div class="box" id="todayslist">
						<div class="box-title">
							<h3>
								<i class="fa fa-table"></i>Add Category
							</h3>
							<div class="box-tool">
								<a href="${pageContext.request.contextPath}/addCategory">
									Add Category</a> <a data-action="collapse" href="#"><i
									class="fa fa-chevron-up"></i></a>
							</div>

						</div>

						<div class=" box-content">
							<form id="addSupplier"
								action="${pageContext.request.contextPath}/insertCategory"
								onsubmit="return confirm('Do you really want to submit the form?');" method="post">
								<div class="box-content">

									<div class="col-md-2">Category Description*</div>
									<div class="col-md-3">
										<input id="catDesc" class="form-control"
								placeholder="Category Description" value="${editCategory.catDesc}"  style="text-align: left;" name="catDesc" type="text" required>
								<input id="catId" class="form-control"
								  name="catId" value="${editCategory.catId}" type="hidden" >
								  
									</div>
									<div class="col-md-1"></div>
									<div class="col-md-2">Category Prefix*</div>
									<div class="col-md-3">
										<input id="catPrefix" class="form-control"
								placeholder="Category Prefix" value="${editCategory.catPrefix}"  style="text-align: left;" name="catPrefix" type="text" required>
								 
								  
									</div>
									 
								</div>
								<br>
								<div class="box-content">

									<div class="col-md-2">Monthly Limit*</div>
									<div class="col-md-3">
										<input id="monthlyLimit" class="form-control"
								placeholder="Monthly Limit" value="${editCategory.monthlyLimit}"  style="text-align: left;" name="monthlyLimit" type="text" required>
								  
									</div>
									<div class="col-md-1"></div>
									<div class="col-md-2">Yearly Limit*</div>
									<div class="col-md-3">
										<input id="yearlyLimit" class="form-control"
								placeholder="Yearly Limit" value="${editCategory.yearlyLimit}"  style="text-align: left;" name="yearlyLimit" type="text" required>
								 
								  
									</div>
									 
								</div>
								<br> <br>
								 
					<br>
								<div class=" box-content">
									<div class="col-md-12" style="text-align: center">
										<input type="submit" class="btn btn-info" value="Submit"
											id="submit">



									</div>
								</div>
								
								<div class="box-content">

					<br /> <br />
					<div class="clearfix"></div>
					<div class="table-responsive" style="border: 0">
						<table class="table table-advance" id="table1">  
									<thead>
												<tr class="bgpink">
													<th class="col-sm-1">Sr no.</th> 
													<th class="col-md-1">Category Description</th> 
													<th class="col-md-1">Action</th>
												</tr>
											</thead>
											<tbody>
											
											  <c:forEach items="${categoryList}" var="categoryList"
									varStatus="count">
									<tr>
										 <td class="col-md-1"><c:out value="${count.index+1}" /></td>
										  
											 
										 <td class="col-md-1"><c:out value="${categoryList.catDesc}" /></td> 
									 <td>
									 <a href="${pageContext.request.contextPath}/editCategory/${categoryList.catId}"><abbr title="Edit"><i  class="fa fa-edit"></i></abbr></a> 
									  <a href="${pageContext.request.contextPath}/deleteCategory/${categoryList.catId}" onClick="return confirm('Are you sure want to delete this record');"><span
												class="glyphicon glyphicon-remove"></span></a>
									 </td>
										 
									</tr>
								</c:forEach>  

											</tbody>

								</table>
  
					</div>
				</div>
							</form>


						</div>
					</div>


				</div>
			</div>


			<div class=" box-content">

				

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
		function FocusOnInput()
		 {
		 document.getElementById("catDesc").focus();
		 }
	</script>

</body>
</html>