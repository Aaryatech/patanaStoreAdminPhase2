<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
<body>

	<c:url var="getPoListByDate" value="/getPoListByDate"></c:url>
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

						<i class="fa fa-file-o"></i>PO List

					</h1>
				</div>
			</div> --><br>
			<!-- END Page Title -->

			<div class="row">
				<div class="col-md-12">

					<div class="box" id="todayslist">
						<div class="box-title">
							<h3>
								<i class="fa fa-table"></i>PO List
							</h3>
							<div class="box-tool">
								<a href="${pageContext.request.contextPath}/addPurchaseOrder">
									Add PO</a> <a data-action="collapse" href="#"><i
									class="fa fa-chevron-up"></i></a>
							</div>

						</div>
			<form action="${pageContext.request.contextPath}/listOfPurachaseOrder"
								class="form-horizontal" id="validation-form" method="get">
						<div class="box-content">

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
							<br><br>
							
							<c:set value="0" var="isEdit"></c:set>
								<c:set value="0" var="isDelete"></c:set>
									<c:forEach items="${sessionScope.newModuleList}" var="allModuleList" >
															<c:choose>
																<c:when test="${allModuleList.moduleId==sessionScope.sessionModuleId}">
																	  <c:forEach items="${allModuleList.subModuleJsonList}" var="subModuleJsonList" >
																	  		<c:choose>
																			  	<c:when test="${subModuleJsonList.subModuleId==sessionScope.sessionSubModuleId}">
																			  		  <c:choose>
																			  		  
																			  				<c:when test="${subModuleJsonList.editReject eq 'visible'}">
																			  				<c:set value="1" var="isEdit"></c:set>
																			  				</c:when>
																			  			</c:choose>
																			  			<c:choose>
																			  				<c:when test="${subModuleJsonList.deleteRejectApprove eq 'visible'}">
																			  				<c:set value="1" var="isDelete"></c:set>
																			  				</c:when> 
																			  			</c:choose>
																			  	</c:when>
																		  	</c:choose>
																	  </c:forEach>
																</c:when> 
															</c:choose>
														 
														</c:forEach> 
							<div class="box-content">

									<div class="col-md-2">Select Category*</div>
									<div class="col-md-3">
										<select class="form-control chosen" name="catId" id="catId"
											required>
											<option value="0">All</option>
											<c:forEach items="${categoryList}" var="categoryList">
											<c:choose>
												<c:when test="${categoryList.catId==catId}">
												<option value="${categoryList.catId}" selected>${categoryList.catDesc}</option> 
												</c:when>
												<c:otherwise>
												<option value="${categoryList.catId}">${categoryList.catDesc}</option> 
												</c:otherwise>
											</c:choose> 
													 
											</c:forEach>
										</select>

									</div>
									<div class="col-md-1"></div>
									<div class="col-md-2">Select Type*</div>
									<div class="col-md-3">
										<select class="form-control chosen" name="typeId" id="typeId"
											required>
											<option value="0">All</option>
											<c:forEach items="${typeList}" var="typeList">
											<c:choose>
												<c:when test="${typeList.typeId==typeId}">
												<option value="${typeList.typeId}" selected>${typeList.typeName}</option> 
												</c:when>
												<c:otherwise>
												<option value="${typeList.typeId}">${typeList.typeName}</option> 
												</c:otherwise>
											</c:choose> 
													 
											</c:forEach>
										</select>

									</div>
									  
								</div><br>
							<br>
							<div class="form-group">
								<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-5">
									<!-- <input type="button" class="btn btn-primary"
										value="Submit" onclick="search()"> -->
										<input type="submit" class="btn btn-primary"
										value="Submit"  >
								</div>
							</div>
							<br>

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
								</label>

							<br /> <br />
							<div class="clearfix"></div>
							<div class="table-responsive" style="border: 0">
								<table class="table table-advance" id="table1">
									<thead>
										<tr class="bgpink">
										<th style="width:2%;"	style="text-align: left; padding: 0px; align-items: left;"
														width="10%"><input type="checkbox" name="name1"
														value="0" />All</th>
										
											<th style="width:2%;">SR</th>
											<th class="col-md-1">Date</th>
											<th style="width:10%;">PO No</th>
											<th class="col-md-1">PO TYPE</th>
											<th class="col-md-3">Vendor Name</th>
											<th style="width:10%;">Indent No</th>
											<th class="col-md-2">Status</th>
											<th class="col-md-1">Action</th>
										</tr>
									</thead>
									<tbody>

										<c:forEach items="${poList}" var="poList" varStatus="count">
											<tr>
											
												<td style="width:2%;" style="text-align: left; padding: 0px; align-items: center; align-content: center;"
															width="10%">&nbsp;&nbsp;<input type="checkbox"
															name="name1" value="${poList.poId}" /></td>
										
												<td style="width:2%;"><c:out value="${count.index+1}" /></td>


												<td class="col-md-1"><c:out value="${poList.poDate}" /></td>
												 
												<td style="width:10%;"><c:out value="${poList.poNo}" /></td>
												<c:set var="type"></c:set>
												<c:forEach items="${typeList}" var="typeList"> 
															<c:choose>
																<c:when test="${typeList.typeId==poList.poType}">
																	<c:set var="type" value="${typeList.typeName}"></c:set>
																</c:when>
																 
															</c:choose> 
														</c:forEach>
												
												<td class="col-md-1"><c:out value="${type}" /></td>
												<td class="col-md-3"><c:out
														value="${poList.vendorName}" /></td>

												<td style="width:10%;"><c:out value="${poList.indNo}" /></td>
												
												<c:set var="poStatus" value=""></c:set>
												<c:choose>
													<c:when test="${poList.poStatus==9}">
													<c:set var="poStatus" value="1st Approve Pending "></c:set>
													</c:when>
													<c:when test="${poList.poStatus==7}">
													<c:set var="poStatus" value="2st Approve Pending "></c:set>
													</c:when>
													<c:when test="${poList.poStatus==0}">
													<c:set var="poStatus" value="Grn Pending"></c:set>
													</c:when>
													<c:when test="${poList.poStatus==1}">
													<c:set var="poStatus" value="Partially Grn "></c:set>
													</c:when>
													<c:when test="${poList.poStatus==2}">
													<c:set var="poStatus" value="Grn Completed"></c:set>
													</c:when>
													
												</c:choose>
												
												<td class="col-md-1"><c:out
														value="${poStatus}" /></td>

												<td><a href="javascript:genPdf(${ poList.poId});"><abbr title="PDF"><i
															class="glyphicon glyphicon glyphicon-file"></i></abbr></a>
														<c:choose>
																<c:when test="${isEdit==1}">	 
													<a href="${pageContext.request.contextPath}/editPurchaseOrder/${poList.poId}"><abbr
														title="Edit"><i class="fa fa-edit"></i></abbr></a>
														</c:when></c:choose> 
														
														<c:choose>
											 					<c:when test="${isDelete==1}">
														<c:choose>
															<c:when test="${poList.poStatus==9 or poList.poStatus==7}">
															<a href="${pageContext.request.contextPath}/deletePurchaseOrder/${poList.poId}"
													onClick="return confirm('Are you sure want to delete this record');"><span
														class="glyphicon glyphicon-remove"></span></a>
															</c:when>
														</c:choose>
														</c:when>
														</c:choose>
														 </td>

											</tr>
										</c:forEach>

									</tbody>

								</table>
<br> <br>
										<button
											style="background-color: #008CBA; border: none; color: white; text-align: center; text-decoration: none; display: block; font-size: 12px; cursor: pointer; width: 50px; height: 30px; margin: auto;"
											onclick="commonPdf()">PDF</button>

								
							</div>
						</div>

</form>

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
		  
		
		var fromDate = $("#fromDate").val();
		var toDate = $("#toDate").val();
		
		if(fromDate=="" || fromDate == null)
			alert("Select From Date");
		else if (toDate=="" || toDate == null)
			alert("Select To Date");
		 
		$('#loader').show();

		$
				.getJSON(
						'${getPoListByDate}',

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
						 

						  $.each(
										data,
										function(key, itemList) {
										

											var tr = $('<tr></tr>');
											
											 tr.append($('<td style="width:2%;"></td>')
														.html('<input type="checkbox"  name="name1" value="'+itemList.poId +'"/>'));
											
										  	tr.append($('<td></td>').html(key+1));
										  	tr.append($('<td></td>').html(itemList.poDate));
										  	tr.append($('<td></td>').html(itemList.poNo));
										  	
										  	var type;
										  	
										  	if(itemList.poType==1){
										  		
										  		type="Regular";
										  		
										  	}
										  	else if(itemList.poType==2){
										  		type="Job Work";
										  	}
											else if(itemList.poType==3){
												type="General";
										  	}
											else{
												type="Other";
										  	}
										  	tr.append($('<td></td>').html(type));
										  	tr.append($('<td></td>').html(itemList.vendorName));
										  	tr.append($('<td></td>').html(itemList.indNo));
										  	if(itemList.poStatus==0) {
										  		tr.append($('<td></td>').html('<a href="javascript:genPdf('+itemList.poId+');"><abbr'+
														'title="PDF"><i class="glyphicon glyphicon glyphicon-file"></i></abbr></a> <a href="${pageContext.request.contextPath}/editPurchaseOrder/'+itemList.poId+'"><abbr'+
														'title="Edit"><i class="fa fa-edit"></i></abbr></a> <a href="${pageContext.request.contextPath}/deletePurchaseOrder/'+itemList.poId+'"'+
														'onClick="return confirm("Are you sure want to delete this record");"><span class="glyphicon glyphicon-remove"></span></a>'));
										  		}
										  	else
										  		{
										  		tr.append($('<td></td>').html('<a href="javascript:genPdf('+itemList.poId+');"><abbr'+
														'title="PDF"><i class="glyphicon glyphicon glyphicon-file"></i></abbr></a> <a href="${pageContext.request.contextPath}/editPurchaseOrder/'+itemList.poId+'"><abbr'+
														'title="Edit"><i class="fa fa-edit"></i></abbr></a> '));
										  		}
										  	
										    $('#table1 tbody').append(tr); 
										})  
										
							 
						}); 
}
	</script>


<script type="text/javascript">

function genPdf(id) {

	window.open('pdfForReport?url=/pdf/poPdf/' + id);

}
			/* function genPdf(id) {
				//alert(id);
		
				window.open('poPdf/'
						+ id );

			} */
			
			function commonPdf() {

				var list = [];

				$("input:checkbox[name=name1]:checked").each(function() {
					list.push($(this).val());
				});

				window.open('pdfForReport?url=/pdf/poPdf/' + list);

			}
			
		</script>

<script>
function myFunction() {
  var input, filter, table, tr, td ,td1,td2, i;
  input = document.getElementById("myInput");
  filter = input.value.toUpperCase();
  table = document.getElementById("table1");
  tr = table.getElementsByTagName("tr");
  for (i = 0; i < tr.length; i++) {
    td = tr[i].getElementsByTagName("td")[5];
    td1 = tr[i].getElementsByTagName("td")[3];
    td2 = tr[i].getElementsByTagName("td")[4];
    if (td || td1 || td2) {
    	
    	 if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {
    	        tr[i].style.display = "";
    	      }else if (td1.innerHTML.toUpperCase().indexOf(filter) > -1) {
    	        tr[i].style.display = "";
    	      }else if (td2.innerHTML.toUpperCase().indexOf(filter) > -1) {
    	        tr[i].style.display = "";
    	      }
    	      else {
    	        tr[i].style.display = "none";
    	      }
       
    }  
    
     
  }
}
 
</script>

</body>
</html>