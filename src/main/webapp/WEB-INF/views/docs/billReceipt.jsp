<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>BILL RECEIPT PDF</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- Place favicon.ico and apple-touch-icon.png in the root directory -->


<style type="text/css">
table {
	border-color: black;
	font-size: 12;
	width: 100%;
	page-break-inside: auto !important;
}

p {
	color: black;
	font-family: arial;
	font-size: 70%;
	margin-top: 0;
	padding: 0;
	font-weight: bold;
}

.pn {
	color: black;
	font-family: arial;
	font-size: 10%;
	margin-top: 0;
	padding: 0;
	font-weight: normal;
}

h4 {
	color: black;
	font-family: sans-serif;
	font-size: 100%;
	font-weight: bold;
	padding-bottom: 10px;
	margin: 0;
}

h5 {
	color: black;
	font-family: sans-serif;
	font-size: 70%;
	font-weight: normal;
	margin: 0;
}

h6 {
	color: black;
	font-family: arial;
	font-size: 60%;
	font-weight: normal;
	margin: 10%;
}

th {
	color: black;
}

hr {
	height: 1px;
	border: none;
	color: rgb(60, 90, 180);
	background-color: rgb(60, 90, 180);
}

.invoice-box table tr.information table td {
	padding-bottom: 0px;
	align-content: center;
}

.set-height td {
	position: relative;
	overflow: hidden;
	height: 2em;
}

.set-height t {
	position: relative;
	overflow: hidden;
	height: 2em;
}

.set-height p {
	position: absolute;
	margin: .1em;
	left: 0;
	top: 0;
}
</style>

</head>
<body>


	<div class="invoice-box">
		<table cellpadding="0" cellspacing="0">

			<table>
				<tr>
					<td width="10%"><img
						src="${pageContext.request.contextPath}/resources/img/Madhvi_Logo(4).jpg"
						width="95" height="60" /></td>

					<td width="80%" valign="top"
						style="font-weight: bold; margin: 0px;" align="center">
						<h4 align="center" style="font-size: 16px;">${company.companyName}</h4>
						<h6 style="font-weight: bold; margin: 0px; font-size: 10px;"
							align="center">Delivery & Billing Addr.:
							${company.factoryAdd}</h6>
						<h6 style="font-weight: normal; margin: 0px; font-size: 10px;"
							align="center">CIN NO : ${company.cinNumber}</h6>
					</td>

					<td width="22.33%" valign="top"
						style="font-weight: bold; margin: 0px;" align="right"></td>

				</tr>

			</table>
		</table>
	</div>
	<br>

	<div class="invoice-box">
		<table cellpadding="0" cellspacing="0">

			<tr class="information">
				<td valign="top">
					<table width="100%">
						<tr>
							<td width="33.33%" valign="top"
								style="font-weight: bold; margin: 0px;" align="left">GST&nbsp;
								NO&nbsp; : ${company.gstNumber} <br>PAN &nbsp;NO&nbsp; :
								${company.panNumber}
							</td>

							<td width="33.33%" valign="top"
								style="font-weight: bold; margin: 0px;" align="center">
								BILL RECEIPT</td>

							<td width="33.33%" valign="top"
								style="font-weight: bold; margin: 0px;" align="right">Bill
								Receipt No.&nbsp; : ${recHeader.brNo}<br> Date &nbsp;:
								${recHeader.brDate}
							</td>

						</tr>

					</table>
				</td>
			</tr>
		</table>
	</div>
	<br>
	<!-- t -->


	<!-- t -->


	<div class="invoice-box">
		<table cellpadding="0" cellspacing="0">

			<tr class="information">
				<td valign="top">
					<table width="1000px">
						<tr>
							<td width="900px	" valign="top"
								style="border-left: 1px solid #313131; border-top: 1px solid #313131; border-bottom: 1px solid #313131; padding: 8px; color: #000; font-size: 12px; font-weight: bold;">To,<br>
								${recHeader.vendorName}
							</td>

							<td width="50%" valign="top"
								style="border-left: 1px solid #313131; border-top: 1px solid #313131; border-bottom: 1px solid #313131; border-right: 1px solid #313131; padding: 8px; color: #000; font-size: 12px;">

								<div class="invoice-box">
									<table cellpadding="0" cellspacing="0">
										<tr class="information">
											<td colspan="2" valign="top">
												<table>
													<tr>

														<c:set value="" var="payMode"></c:set>
														<c:choose>

															<c:when test="${recHeader.payMode == 1}">
																<c:set value="Cash" var="payMode"></c:set>
															</c:when>
															<c:when test="${recHeader.payMode == 2}">
																<c:set value="Cheque" var="payMode"></c:set>
															</c:when>

															<c:when test="${recHeader.payMode == 3}">
																<c:set value="Bank Transfer" var="payMode"></c:set>
															</c:when>

															<c:when test="${recHeader.payMode == 4}">
																<c:set value="E-Payment" var="payMode"></c:set>
															</c:when>

														</c:choose>

														<td width="50%" valign="top">Payment Mode :
															&nbsp;${payMode}</td>



													</tr>
												</table>
											</td>
										</tr>
									</table>
								</div> Payment Desc : &nbsp;&nbsp;${recHeader.payDesc}
							</td>

						</tr>

					</table>
				</td>
			</tr>
		</table>
	</div>


	<br>	<br>
	<!-- <h5>
		DEAR SIR,<br> WE HAVE PLEASURE IN PLACING/CONFIRMING OUR ORDER
		FOR UNDERMENTIONED GOODS.

	</h5>
 -->

	<table align="center" border="1" cellpadding="0" cellspacing="0"
		id="table_grid">
		<thead>
			<tr style="font-size: 15px;">
				<th width="4%">SR.</th>
				<th width=12%>Bill Date</th>
				<th width=20%>Bill No.</th>
				<th width=12%>Expiry Date</th>
				<th width=12%>Bill Total</th>
				<th width=12%>Pending Amount</th>
				<th width=12%>Paid Amount</th>
			</tr>
		</thead>
		<tbody>


			<!-- end of new page -->


			<c:forEach items="${recHeader.billReceiptDetailList}" var="detail"
				varStatus="count">

				<tr style="font-size: 13px;">

					<td style="max-height: 5px" align="center" width="30px"><c:out
							value="${count.index+1}" /></td>

					<td align="center" style="padding: 2px;"><c:out
							value="${detail.billDate}" /></td>

					<td align="left" style="padding: 5px;"><c:out
							value="${detail.billNo}" /></td>

					<td align="center" style="padding: 5px;"><c:out
							value="${detail.exVar1}" /></td>

					<td align="right" style="padding: 5px;"><fmt:formatNumber
							type="number" maxFractionDigits="2" minFractionDigits="2"
							value="${detail.billTotal}" /></td>

					<td align="right" style="padding: 5px;"><fmt:formatNumber
							type="number" maxFractionDigits="2" minFractionDigits="2"
							value="${detail.billPending}" /></td>

					<td align="right" style="padding: 5px;"><fmt:formatNumber
							type="number" maxFractionDigits="2" minFractionDigits="2"
							value="${detail.billPaid}" /></td>




				</tr>
			</c:forEach>


			<tr style="font-size: 13px;">
				<td colspan="6">&nbsp;&nbsp;<c:out value="  Total " /></td>

				<td align="right" style="padding: 5px;"><fmt:formatNumber
						type="number" maxFractionDigits="2" minFractionDigits="2"
						value="${recHeader.amt}" /></td>

			</tr>

		</tbody>
	</table>

	<!-- start of footer -->

	<br>

	<p
		style="color: #000; font-size: 10px; text-align: left; margin: 0px; font-weight: normal;">
		REMARKS IF ANY : ${recHeader.remark}<br>
	</p>

	<br>	<br>	<br>

	<table width="100%" border="0" cellpadding="0" cellspacing="0">

		<tr>
			<td colspan="6" width="50%"
				>
				<p
					style="color: #000; font-size: 10px; text-align: left; margin: 0px; font-weight: normal;">
				</p>
			</td>
			<td colspan="6" width="50%"
				style=" padding: 8px; color: #000; font-size: 12px;">
				<p
					style="color: #000; font-size: 10px; vertical-align: top; text-align: center; margin: 0px; font-weight: normal;">
					For ${company.companyName}<br> 
			</td>
		</tr>


	</table>

	<br>


	<!-- END Main Content -->


	<!-- end of footer -->

	<div style="page-break-after: always;"></div>
</body>
</html>