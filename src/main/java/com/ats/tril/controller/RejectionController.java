package com.ats.tril.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.ats.tril.common.Constants;
import com.ats.tril.common.DateConvertor;
import com.ats.tril.model.Category;
import com.ats.tril.model.Damage;
import com.ats.tril.model.ErrorMessage;
import com.ats.tril.model.ExportToExcel;
import com.ats.tril.model.GetDamage;
import com.ats.tril.model.GetEnquiryDetail;
import com.ats.tril.model.GetEnquiryHeader;
import com.ats.tril.model.GetItemGroup;
import com.ats.tril.model.GetMrnDetailRej;
import com.ats.tril.model.GetMrnHeaderRej;
import com.ats.tril.model.GetpassDetail;
import com.ats.tril.model.GetpassHeader;
import com.ats.tril.model.GetpassReturnVendor;
import com.ats.tril.model.MrnItemList;
import com.ats.tril.model.Vendor;
import com.ats.tril.model.doc.DocumentBean;
import com.ats.tril.model.doc.SubDocument;
import com.ats.tril.model.item.GetItem;
import com.ats.tril.model.item.ItemList;
import com.ats.tril.model.mrn.GetMrnDetail;
import com.ats.tril.model.mrn.GetMrnHeader;
import com.ats.tril.model.mrn.MrnHeader;
import com.ats.tril.model.rejection.GetRejectionMemo;
import com.ats.tril.model.rejection.GetRejectionMemoDetail;
import com.ats.tril.model.rejection.RejectionMemo;
import com.ats.tril.model.rejection.RejectionMemoDetail;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Controller

public class RejectionController {

	RestTemplate rest = new RestTemplate();
	List<RejectionMemoDetail> rejectionMemoDetailList = new ArrayList<RejectionMemoDetail>();
	List<GetMrnHeaderRej> getMrnList = new ArrayList<GetMrnHeaderRej>();
	List<GetDamage> getDamagelist = null;

	@RequestMapping(value = "/showRejectionMemo", method = RequestMethod.GET)
	public ModelAndView showRejectionMemo(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("rejection/addRejectionMemo");
		try {

			Category[] category = rest.getForObject(Constants.url + "/getAllCategoryByIsUsed", Category[].class);
			List<Category> categoryList = new ArrayList<Category>(Arrays.asList(category));
			model.addObject("categoryList", categoryList);

			Vendor[] vendorRes = rest.getForObject(Constants.url + "/getAllVendorByIsUsed", Vendor[].class);
			List<Vendor> vendorList = new ArrayList<Vendor>(Arrays.asList(vendorRes));

			model.addObject("vendorList", vendorList);

			Date date = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy");
			model.addObject("date", sf.format(date));

			/*
			 * MrnHeader[] mrnHeaderList = rest.getForObject(Constants.url + "/getMrnList",
			 * MrnHeader[].class); List<MrnHeader> mrnList = new
			 * ArrayList<MrnHeader>(Arrays.asList(mrnHeaderList));
			 * 
			 * model.addObject("mrnList", mrnList);
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = "/getMrnListByVendorIdForRejectionMemo", method = RequestMethod.GET)
	@ResponseBody
	public List<MrnHeader> getMrnListByVendorIdForRejectionMemo(HttpServletRequest request,
			HttpServletResponse response) {

		List<MrnHeader> mrnList = new ArrayList<MrnHeader>();

		try {

			int vendId = Integer.parseInt(request.getParameter("vendId"));
			int itemId = Integer.parseInt(request.getParameter("itemId"));

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("vendId", vendId);
			map.add("itemId", itemId);
			MrnHeader[] mrnHeaderList = rest.postForObject(
					Constants.url + "/getMrnListByVendorIdForRejectionMemoForPune", map, MrnHeader[].class);
			mrnList = new ArrayList<MrnHeader>(Arrays.asList(mrnHeaderList));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return mrnList;
	}

	@RequestMapping(value = "/getMrnListByMrnId", method = RequestMethod.GET)
	@ResponseBody
	public List<GetMrnHeaderRej> getMrnListByMrnId(HttpServletRequest request, HttpServletResponse response) {

		try {

			int mrnIdList = Integer.parseInt(request.getParameter("mrnId"));
			int itemId = Integer.parseInt(request.getParameter("itemId"));
			System.out.println("mrn Td" + mrnIdList);

			/*
			 * StringBuilder sb = new StringBuilder();
			 * 
			 * for (int i = 0; i < mrnIdList.length; i++) { sb = sb.append(mrnIdList[i] +
			 * ",");
			 * 
			 * } String items = sb.toString(); items = items.substring(0, items.length() -
			 * 1);
			 */

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

			map.add("status", mrnIdList);
			map.add("itemId", itemId);
			// getMrnList = rest.postForObject(Constants.url + "getMrnHeaderDetail", map,
			// List.class);

			ParameterizedTypeReference<List<GetMrnHeaderRej>> typeRef = new ParameterizedTypeReference<List<GetMrnHeaderRej>>() {
			};
			ResponseEntity<List<GetMrnHeaderRej>> responseEntity = rest.exchange(
					Constants.url + "getMrnHeaderDetailForPune", HttpMethod.POST, new HttpEntity<>(map), typeRef);
			getMrnList = responseEntity.getBody();

			System.out.println("getMrnList" + getMrnList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return getMrnList;
	}

	@RequestMapping(value = "/insertRejectionMemo", method = RequestMethod.POST)
	public String insertRejectionMemo(HttpServletRequest request, HttpServletResponse response) {

		try {

			List<Damage> rejectionMemoList = new ArrayList<Damage>();

			int vendId = Integer.parseInt(request.getParameter("vendId"));

			String rejectionDate = request.getParameter("rejectionDate");

			int mrnId = Integer.parseInt(request.getParameter("mrnId"));
			String remark = request.getParameter("remark");
			int typeId = Integer.parseInt(request.getParameter("typeId"));

			Damage rejectionMemo = new Damage();
			DocumentBean docBean = null;
			try {

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
				map.add("docId", 9);
				map.add("catId", 1);
				map.add("date", DateConvertor.convertToYMD(rejectionDate));
				map.add("typeId", 1);
				RestTemplate restTemplate = new RestTemplate();

				docBean = restTemplate.postForObject(Constants.url + "getDocumentData", map, DocumentBean.class);
				String indMNo = docBean.getSubDocument().getCategoryPrefix() + "";
				int counter = docBean.getSubDocument().getCounter();
				int counterLenth = String.valueOf(counter).length();
				counterLenth = 4 - counterLenth;
				StringBuilder code = new StringBuilder(indMNo + "");

				for (int i = 0; i < counterLenth; i++) {
					String j = "0";
					code.append(j);
				}
				code.append(String.valueOf(counter));

				rejectionMemo.setDamageNo("" + code);

				docBean.getSubDocument().setCounter(docBean.getSubDocument().getCounter() + 1);
			} catch (Exception e) {
				e.printStackTrace();
			}

			List<MrnItemList> list = new ArrayList<>();

			// for (int i = 0; i < mrnIdList.length; i++) {
			// System.out.println(" \n current mrn Id" + mrnIdList[i].toString());

			rejectionMemoDetailList = new ArrayList<RejectionMemoDetail>();

			for (int j = 0; j < getMrnList.size(); j++) {

				if (mrnId == getMrnList.get(j).getMrnId()) {

					for (int k = 0; k < getMrnList.get(j).getGetMrnDetailRejList().size(); k++) {

						MrnItemList mrnItemList = new MrnItemList();

						GetMrnDetailRej getMrnDetail = getMrnList.get(j).getGetMrnDetailRejList().get(k);

						if (Float.parseFloat(request.getParameter("memoQty" + getMrnDetail.getMrnDetailId())) > 0) {

							rejectionMemo.setItemId(getMrnDetail.getItemId());
							rejectionMemo.setDelStatus(1);
							rejectionMemo.setMrnId(mrnId);
							rejectionMemo.setDate(DateConvertor.convertToYMD(rejectionDate));
							rejectionMemo.setReason(remark);
							rejectionMemo.setExtra1(vendId);
							rejectionMemo.setExtra2(typeId);
							rejectionMemo.setMrnDetailId(getMrnDetail.getMrnDetailId());
							rejectionMemo.setMrnId(mrnId);
							rejectionMemo.setValue(getMrnDetail.getChalanQty());
							rejectionMemo.setFloat1(getMrnDetail.getIndentQty());
							rejectionMemo.setQty(
									Float.parseFloat(request.getParameter("memoQty" + getMrnDetail.getMrnDetailId())));
							rejectionMemo.setVar1(getMrnList.get(j).getMrnNo());
							rejectionMemoList.add(rejectionMemo);

							mrnItemList.setMrnDetailedId(getMrnDetail.getMrnDetailId());
							mrnItemList.setMrnId(getMrnDetail.getMrnId());
							mrnItemList.setItemId(getMrnDetail.getItemId());
							mrnItemList.setPendingQty(getMrnDetail.getRemainingQty());
							mrnItemList.setReturnQty(rejectionMemo.getQty());
							list.add(mrnItemList);

						}
					}

				}
			}

			// }
			System.out.println("rejectionMemoList" + rejectionMemoList);
			if (rejectionMemoList.size() > 0) {
				ErrorMessage res = rest.postForObject(Constants.url + "/saveDamage", rejectionMemoList,
						ErrorMessage.class);
				System.out.println("response:" + res);
				if (res.isError() == false) {

					SubDocument subDocRes = rest.postForObject(Constants.url + "/saveSubDoc", docBean.getSubDocument(),
							SubDocument.class);
					ErrorMessage errorMessage = rest.postForObject(
							Constants.url + "/updateApprovedQtyWhileReturnProcess", list, ErrorMessage.class);
					System.out.println("response errorMessage : " + errorMessage);

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/listOfRejectionMemo";
	}

	@RequestMapping(value = "/getListOfRejectionMemo", method = RequestMethod.GET)
	@ResponseBody
	public List<GetRejectionMemo> getListOfRejectionMemo(HttpServletRequest request, HttpServletResponse response) {

		List<GetRejectionMemo> list = new ArrayList<GetRejectionMemo>();
		try {

			String fromDate = request.getParameter("fromDate");
			String toDate = request.getParameter("toDate");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("fromDate", DateConvertor.convertToYMD(fromDate));
			map.add("toDate", DateConvertor.convertToYMD(toDate));

			GetRejectionMemo[] getlist = rest.postForObject(Constants.url + "/getRejectionMemoByDate", map,
					GetRejectionMemo[].class);
			list = new ArrayList<GetRejectionMemo>(Arrays.asList(getlist));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	@RequestMapping(value = "/listOfRejectionMemo", method = RequestMethod.GET)
	public ModelAndView listOfRejectionMemo(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("rejection/listOfRejectionMemo");
		try {

			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat show = new SimpleDateFormat("dd-MM-yyyy");

			Date date = new Date();

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			if (request.getParameter("fromDate") == null || request.getParameter("toDate") == null) {

				map.add("fromDate", sf.format(date));
				map.add("toDate", sf.format(date));
				map.add("typeId", "0,1");
				model.addObject("fromDate", show.format(date));
				model.addObject("toDate", show.format(date));
				model.addObject("typeId", -1);
			} else {

				String fromDate = request.getParameter("fromDate");
				String toDate = request.getParameter("toDate");
				int typeId = Integer.parseInt(request.getParameter("typeId"));
				map.add("fromDate", DateConvertor.convertToYMD(fromDate));
				map.add("toDate", DateConvertor.convertToYMD(toDate));
				if (typeId == -1) {
					map.add("typeId", "0,1");
				} else {
					map.add("typeId", typeId);
				}
				model.addObject("fromDate", fromDate);
				model.addObject("toDate", toDate);
				model.addObject("typeId", typeId);
			}

			GetDamage[] getDamage = rest.postForObject(Constants.url + "/getDamageList", map, GetDamage[].class);
			getDamagelist = new ArrayList<GetDamage>(Arrays.asList(getDamage));
			model.addObject("getDamagelist", getDamagelist);

			// ----------------exel-------------------------
			DecimalFormat df = new DecimalFormat("####0.00");

			List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

			ExportToExcel expoExcel = new ExportToExcel();
			List<String> rowData = new ArrayList<String>();

			rowData.add("SR. No");
			rowData.add("Rejection Date");
			rowData.add("Rejection No.");
			rowData.add("Vendor Name");
			rowData.add("Item Name");
			rowData.add("Type");

			rowData.add("Qty");
			rowData.add("MRN No");
			rowData.add("Remark");

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);
			for (int i = 0; i < getDamagelist.size(); i++) {
				expoExcel = new ExportToExcel();
				rowData = new ArrayList<String>();

				rowData.add((i + 1) + "");

				rowData.add(getDamagelist.get(i).getDate());
				rowData.add(getDamagelist.get(i).getDamageNo());
				rowData.add(getDamagelist.get(i).getVendorName());

				rowData.add(getDamagelist.get(i).getItemDesc());
				String statement = null;

				if (getDamagelist.get(i).getExtra2() == 0) {
					statement = "Rejected";
				} else {
					statement = "Damege";
				}
				rowData.add(statement);
				rowData.add(df.format(getDamagelist.get(i).getQty()));
				rowData.add(getDamagelist.get(i).getVar1());
				rowData.add(getDamagelist.get(i).getReason());

				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);

			}

			HttpSession session = request.getSession();
			session.setAttribute("exportExcelList", exportToExcelList);
			session.setAttribute("excelName", "GetDamage");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = "/rejectionListPdf", method = RequestMethod.GET)
	public void rejectionListPdf(HttpServletRequest request, HttpServletResponse response)
			throws FileNotFoundException {
		BufferedOutputStream outStream = null;
		System.out.println("Inside Pdf rejectionListPdf");

		// moneyOutList = prodPlanDetailList;
		Document document = new Document(PageSize.A4);
		// ByteArrayOutputStream out = new ByteArrayOutputStream();

		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		Calendar cal = Calendar.getInstance();

		System.out.println("time in Gen Bill PDF ==" + dateFormat.format(cal.getTime()));
		String FILE_PATH = Constants.REPORT_SAVE;
		File file = new File(FILE_PATH);

		PdfWriter writer = null;

		FileOutputStream out = new FileOutputStream(FILE_PATH);
		try {
			writer = PdfWriter.getInstance(document, out);
		} catch (DocumentException e) {

			e.printStackTrace();
		}

		PdfPTable table = new PdfPTable(9);
		try {
			System.out.println("Inside PDF Table try");
			table.setWidthPercentage(100);
			table.setWidths(new float[] { 1.4f, 2.0f, 3.0f, 3.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f });
			Font headFont = new Font(FontFamily.TIMES_ROMAN, 10, Font.NORMAL, BaseColor.BLACK);
			Font headFont1 = new Font(FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.BLACK);
			headFont1.setColor(BaseColor.BLACK);
			Font f = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLUE);

			PdfPCell hcell = new PdfPCell();
			hcell.setBackgroundColor(BaseColor.PINK);

			hcell.setPadding(3);
			hcell = new PdfPCell(new Phrase("Sr.No.", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Rejection Date", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Rejection No.", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Vendor Name", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Item Name", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Type", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Qty", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("MRN No.", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Remark", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			int index = 0;
			for (GetDamage item : getDamagelist) {
				index++;
				PdfPCell cell;

				cell = new PdfPCell(new Phrase(String.valueOf(index), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(item.getDate(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(item.getDamageNo(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + item.getVendorName(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(item.getItemCode() + " - " + item.getItemDesc(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				String statement = null;

				if (item.getExtra2() == 0) {
					statement = "Rejected";

				} else {
					statement = "Damage";

				}

				cell = new PdfPCell(new Phrase("" + statement, headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(" " + item.getQty(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(" " + item.getVar1(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(" " + item.getReason(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

			}
			document.open();
			Paragraph name = new Paragraph("Rejection List\n", f);
			name.setAlignment(Element.ALIGN_CENTER);
			document.add(name);
			document.add(new Paragraph(" "));
			Paragraph company = new Paragraph("Rejection List Report\n", f);
			company.setAlignment(Element.ALIGN_CENTER);
			document.add(company);
			document.add(new Paragraph(" "));

			DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");

			document.add(new Paragraph("\n"));
			table.setHeaderRows(1);
			document.add(table);

			int totalPages = writer.getPageNumber();

			System.out.println("Page no " + totalPages);

			document.close();
			// Atul Sir code to open a Pdf File
			if (file != null) {

				String mimeType = URLConnection.guessContentTypeFromName(file.getName());

				if (mimeType == null) {

					mimeType = "application/pdf";

				}

				response.setContentType(mimeType);

				response.addHeader("content-disposition", String.format("inline; filename=\"%s\"", file.getName()));

				response.setContentLength((int) file.length());

				InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

				try {
					FileCopyUtils.copy(inputStream, response.getOutputStream());
				} catch (IOException e) {
					System.out.println("Excep in Opening a Pdf File");
					e.printStackTrace();
				}
			}

		} catch (DocumentException ex) {

			System.out.println("Pdf Generation Error: BOm Prod  View Prod" + ex.getMessage());

			ex.printStackTrace();

		}

	}

	@RequestMapping(value = "/deleteRejectionMemo/{damageId}", method = RequestMethod.GET)
	public String deleteRejectionMemo(@PathVariable int damageId, HttpServletRequest request,
			HttpServletResponse response) {

		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("damageId", damageId);

			ErrorMessage errorMessage = rest.postForObject(Constants.url + "/deleteDamage", map, ErrorMessage.class);
			System.out.println(errorMessage);

			if (errorMessage.isError() == false) {
				List<MrnItemList> list = new ArrayList<>();

				map = new LinkedMultiValueMap<String, Object>();
				map.add("damageId", damageId);
				GetDamage editDamage = rest.postForObject(Constants.url + "/getDamageById", map, GetDamage.class);

				MrnItemList mrnItemList = new MrnItemList();
				mrnItemList.setMrnDetailedId(editDamage.getMrnDetailId());
				mrnItemList.setReturnQty(editDamage.getQty());
				list.add(mrnItemList);
				ErrorMessage res = rest.postForObject(Constants.url + "/updatePendingQtyWhileDeleteReturnProcess", list,
						ErrorMessage.class);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/listOfRejectionMemo";
	}

	List<GetRejectionMemoDetail> getRejectionMemoDetailList = new ArrayList<GetRejectionMemoDetail>();
	GetRejectionMemo editRejection = new GetRejectionMemo();

	@RequestMapping(value = "/editRejectionMemo/{rejectionId}", method = RequestMethod.GET)
	public ModelAndView editRejectionMemo(@PathVariable int rejectionId, HttpServletRequest request,
			HttpServletResponse response) {

		ModelAndView model = new ModelAndView("rejection/editERejMemo");
		try {

			/*
			 * Vendor[] vendorRes = rest.getForObject(Constants.url +
			 * "/getAllVendorByIsUsed", Vendor[].class); List<Vendor> vendorList = new
			 * ArrayList<Vendor>(Arrays.asList(vendorRes)); model.addObject("vendorList",
			 * vendorList);
			 * 
			 * MrnHeader[] mrnHeaderList = rest.getForObject(Constants.url + "/getMrnList",
			 * MrnHeader[].class); List<MrnHeader> mrnList = new
			 * ArrayList<MrnHeader>(Arrays.asList(mrnHeaderList));
			 * model.addObject("mrnList", mrnList);
			 */

			getRejectionMemoDetailList = new ArrayList<GetRejectionMemoDetail>();

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("rejectionId", rejectionId);

			editRejection = rest.postForObject(Constants.url + "/getRejectionHeaderAndDetail", map,
					GetRejectionMemo.class);
			getRejectionMemoDetailList = editRejection.getGetRejectionMemoDetail();

			model.addObject("editRejection", editRejection);
			model.addObject("editRejectionDetailList", getRejectionMemoDetailList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = { "/editMemoQty" }, method = RequestMethod.GET)
	public @ResponseBody List<GetRejectionMemoDetail> editMemoQty(HttpServletRequest request,
			HttpServletResponse response) {

		try {

			float memoQty = Float.parseFloat(request.getParameter("memoQty"));

			int rejDetailId = Integer.parseInt(request.getParameter("rejDetailId"));

			if (getRejectionMemoDetailList.size() > 0) {

				System.err.println("Inside getRejectionMemoDetailList.size >0 ");

				for (int i = 0; i < getRejectionMemoDetailList.size(); i++) {

					if (getRejectionMemoDetailList.get(i).getRejDetailId() == rejDetailId) {
						System.err.println("Inside rejDetailId matched  ");

						getRejectionMemoDetailList.get(i).setMemoQty(memoQty);

					} else {

						System.err.println("getRejectionMemoDetailList no match");
					}
				}

			}

			System.err.println(
					"getRejectionMemoDetailList  List Using Ajax Call  " + getRejectionMemoDetailList.toString());

		} catch (Exception e) {

			System.err.println(
					"Exception in getting getRejectionMemoDetailList @editMemoQty By Ajax Call " + e.getMessage());
			e.printStackTrace();
		}

		return getRejectionMemoDetailList;

	}

	@RequestMapping(value = "/submitEditRejectionMemo", method = RequestMethod.POST)
	public String submitEditRejectionMemo(HttpServletRequest request, HttpServletResponse response) {

		try {

			List<RejectionMemo> rejectionMemoList = new ArrayList<RejectionMemo>();

			int vendId = Integer.parseInt(request.getParameter("vendId"));

			String rejectionNo = request.getParameter("rejectionNo");

			String rejectionDate = request.getParameter("rejectionDate");

			String docDate = request.getParameter("docDate");

			String mrnIdList = request.getParameter("mrnId[]");
			String remark = request.getParameter("remark");

			String remark1 = request.getParameter("remark1");

			int docNo = Integer.parseInt(request.getParameter("docNo"));

			String rejDate = DateConvertor.convertToYMD(rejectionDate);

			String docuDate = DateConvertor.convertToYMD(docDate);

			RejectionMemo rejectionMemo = new RejectionMemo();
			rejectionMemo = new RejectionMemo();
			rejectionMemo.setDcoDate(docuDate);
			rejectionMemo.setDcoId(docNo);
			rejectionMemo.setIsUsed(1);
			rejectionMemo.setMrnId(editRejection.getMrnId());
			rejectionMemo.setRejectionDate(rejDate);
			rejectionMemo.setRejectionNo(rejectionNo);
			rejectionMemo.setRejectionRemark(remark);
			rejectionMemo.setRejectionRemark1(remark1);
			rejectionMemo.setStatus(1);
			rejectionMemo.setVendorId(vendId);
			rejectionMemo.setMrnNo(mrnIdList);

			rejectionMemo.setRejectionId(editRejection.getRejectionId());

			rejectionMemoDetailList = new ArrayList<RejectionMemoDetail>();

			for (GetRejectionMemoDetail detail : getRejectionMemoDetailList) {

				RejectionMemoDetail rejectionMemoDetail = new RejectionMemoDetail();

				rejectionMemoDetail.setRejDetailId(detail.getRejDetailId());
				rejectionMemoDetail.setMemoQty(detail.getMemoQty());

				rejectionMemoDetail.setIsUsed(detail.getIsUsed());
				rejectionMemoDetail.setItemId(detail.getItemId());
				rejectionMemoDetail.setMrnDate(DateConvertor.convertToYMD(detail.getMrnDate()));
				rejectionMemoDetail.setMrnNo(detail.getMrnNo());

				rejectionMemoDetail.setRejectionId(detail.getRejectionId());
				rejectionMemoDetail.setRejectionQty(detail.getRejectionQty());
				rejectionMemoDetail.setStatus(detail.getStatus());

				rejectionMemoDetailList.add(rejectionMemoDetail);

			}

			rejectionMemo.setRejectionMemoDetailList(rejectionMemoDetailList);
			rejectionMemoList.add(rejectionMemo);

			System.out.println("rejectionMemoList" + rejectionMemoList);
			List<RejectionMemo> res = rest.postForObject(Constants.url + "/saveRejectionMemoHeaderDetail",
					rejectionMemoList, List.class);
			System.out.println("edit rejectionMemoList response:" + res);

		} catch (Exception e) {

			System.err.println("Exception in submitEditRejectionMemo @Rejec Controller ");
			e.printStackTrace();
		}

		return "redirect:/showRejectionMemo";
	}

}
