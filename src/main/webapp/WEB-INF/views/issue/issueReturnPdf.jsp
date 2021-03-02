<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Bill wise Purchase Report</title>

<style type="text/css">
table {
	border-collapse: collapse;
	width: 100%;
}

th, td {
	text-align: left;
	padding: 2px;
	font-size: 10;
}

th {
	background-color: #EA3291;
	color: white;
}
</style>
</head>
<body>
	<h4 align="center">Issue Return List</h4>
	<div align="center">
		<h6>From &nbsp; ${fromDate}
			&nbsp;To &nbsp; ${toDate}</h6>
	</div>
	<table align="center" border="1" cellspacing="0" cellpadding="1"
		cellpadding="0" id="table_grid" class="table table-bordered">
		<thead>
			<tr class="bgpink">

				<th style="text-align: center;">Sr.No.</th>				
				<th style="text-align: center;">Return Date</th>
				<th style="text-align: center;">Item Name</th>
				<th style="text-align: center;">UOM</th>
				<th style="text-align: center;">Qty.</th>
			</tr>
		</thead>

		<tbody>
		<tbody>
			
			 <c:forEach items="${getIssueReturnList}" var="issue" varStatus="count">
				<tr>
					<td align="center"><c:out value="${count.index+1}" /></td>
					<td style="text-align: center;"><c:out value="${issue.returnDate}" /></td>					
					<td style="text-align: left;"><c:out value="${issue.itemDesc}-${issue.itemCode}" /></td>
					<td style="text-align: left;"><c:out value="${issue.uom}" /></td>					
					<td style="text-align: right;"><c:out value="${issue.returnQty}" /></td>
				</tr>
			</c:forEach>
			
		</tbody>
	</table>


</body>
</html>