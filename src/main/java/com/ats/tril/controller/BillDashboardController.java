package com.ats.tril.controller;

import java.awt.Dimension;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidParameterException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.zefer.pd4ml.PD4Constants;
import org.zefer.pd4ml.PD4ML;
import org.zefer.pd4ml.PD4PageMark;

import com.ats.tril.common.Constants;
import com.ats.tril.common.DateConvertor;
import com.ats.tril.model.Vendor;
import com.ats.tril.model.billbook.BillDashData;
import com.ats.tril.model.billbook.BillReceiptDetail;
import com.ats.tril.model.billbook.BillReceiptHeader;
import com.ats.tril.model.login.UserResponse;

@Controller
@Scope("session")
public class BillDashboardController {

	RestTemplate rest = new RestTemplate();

	DecimalFormat df = new DecimalFormat("#.000");

//	@RequestMapping(value = "/showBillDash", method = RequestMethod.GET)
//	public ModelAndView showBillDash(HttpServletRequest request, HttpServletResponse response) {
//
//		ModelAndView model = new ModelAndView("dashboard/billDashboard");
//		try {
//
//			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
//
//			BillDashData[] billDashDataRes = rest.postForObject(Constants.url + "/getPendingBillsForDashboard", map,
//					BillDashData[].class);
//			List<BillDashData> billList = new ArrayList<BillDashData>();
//			billList = new ArrayList<BillDashData>(Arrays.asList(billDashDataRes));
//			System.err.println("BILL LIST - " + billList);
//
//			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
//			Calendar cal = Calendar.getInstance();
//			model.addObject("currDate", sdf.format(cal.getTime()));
//
//			Date now = new Date();
//
//			if (billList != null) {
//
//				for (int i = 0; i < billList.size(); i++) {
//
//					Date expdate = sdf.parse(billList.get(i).getExpiryDate());
//					// System.err.println("NOW - "+now+" EXPIRY - "+expdate);
//
//					int val = 1;
//
//					if (now.equals(expdate) || now.after(expdate)) {
//						val = 0;
//					}
//
//					// System.err.println("VAL = "+val);
//
//					billList.get(i).setChkDate(val);
//
//				}
//				model.addObject("billList", billList);//
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return model;
//	}

	@RequestMapping(value = "/billSettle/{vendId}", method = RequestMethod.GET)
	public ModelAndView addBillBook(@PathVariable int vendId, HttpServletRequest request,
			HttpServletResponse response) {

		ModelAndView model = new ModelAndView("billBook/vendorWiseBillList");
		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

			Vendor[] vendorRes = rest.postForObject(Constants.url + "/getAllVendorListForDash", map, Vendor[].class);
			List<Vendor> vendorList = new ArrayList<Vendor>();
			vendorList = new ArrayList<Vendor>(Arrays.asList(vendorRes));
			System.err.println("VENDOR LIST - " + vendorList);

			model.addObject("vendorList", vendorList);
			model.addObject("vendId", vendId);
			model.addObject("status", 0);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	List<BillDashData> billDashList = new ArrayList<>();

	// AJAX
	@RequestMapping(value = "/getBillsForSettle", method = RequestMethod.GET)
	@ResponseBody
	public List<BillDashData> getBillsForSettle(HttpServletRequest request, HttpServletResponse response) {

		try {

			billDashList = new ArrayList<>();

			int vendId = Integer.parseInt(request.getParameter("vendId"));

			System.err.println("VEND ID - " + vendId);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("vendId", vendId);

			BillDashData[] billDashDataRes = rest.postForObject(
					Constants.url + "/getBillsForSettlementByVendorAndStatus", map, BillDashData[].class);
			billDashList = new ArrayList<BillDashData>(Arrays.asList(billDashDataRes));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return billDashList;
	}

	// -------------------------

	@RequestMapping(value = "/submitBillReceipt", method = RequestMethod.POST)
	public String submitBillBook(HttpServletRequest request, HttpServletResponse response) {

		try {

			int days = 0;
			int vendId = Integer.parseInt(request.getParameter("vendId"));

			int payMode = Integer.parseInt(request.getParameter("payMode"));
			String payDesc = request.getParameter("payDesc");

			String remark = request.getParameter("remark");
			if (remark == null) {
				remark = "";
			}

			float settleTotal = Float.parseFloat(request.getParameter("enteredAmt"));

			// -----------------------------------------------
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("vendId", vendId);

			Vendor vendor = rest.postForObject(Constants.url + "/getVendorById", map, Vendor.class);
			if (vendor != null) {
				days = vendor.getCreatedIn();
			}

			// -----------------------------------------------

			List<BillReceiptDetail> billRecDetailList = new ArrayList<>();

			if (billDashList != null) {

				for (BillDashData data : billDashList) {

					float amount = Float.parseFloat(request.getParameter("amt" + data.getBillId()));
					System.err.println("AMOUNT = " + amount);

					if (amount > 0) {

						BillReceiptDetail detail = new BillReceiptDetail(0, 0, data.getBillId(), data.getBillNo(),
								DateConvertor.convertToYMD(data.getBillDate()), data.getTotalValue(), 0, amount, 0,
								days, 0, "", "", 0, 0);

						System.err.println("DETAIL = " + detail);

						billRecDetailList.add(detail);
					}
				}

			}

			HttpSession session = request.getSession();
			UserResponse userResponse = (UserResponse) session.getAttribute("UserDetail");
			int userId = userResponse.getUser().getId();

			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

			BillReceiptHeader header = new BillReceiptHeader(0, "", sdf.format(cal.getTime()),
					sdf1.format(cal.getTime()), vendId, settleTotal, payMode, payDesc, remark, userId, 0, 0, 0, "", "",
					0, 0, billRecDetailList);

			System.err.println("BILL HEADER - " + header);

			BillReceiptHeader save = rest.postForObject(Constants.url + "/saveBillReceiptHeaderAndDetail", header,
					BillReceiptHeader.class);
			System.out.println(save);

			System.err.println("SAVE -- " + save);

			if (save != null) {

				// --------------------------MAIL----------------------------------------------

				String url = Constants.ReportURL + "/pdf/billReceiptPdf/" + save.getBrId();
				doConversion(url, Constants.REPORT_SAVE);
				// doConversion(Constants.ReportURL+"/pdf/poPdf/"+poHeaderForApprove.getPoId(),Constants.REPORT_SAVE);

				map = new LinkedMultiValueMap<String, Object>();
				map = new LinkedMultiValueMap<String, Object>();
				map.add("vendorId", vendId);
		
				Vendor vendorList = rest.postForObject(Constants.url + "/getVendorByVendorId", map, Vendor.class);
				vendorList.setVendorEmail("mahendra941213@gmail.com");
				
				final String emailSMTPserver = "smtp.gmail.com";
				final String emailSMTPPort = "587";
				final String mailStoreType = "imaps";
				final String username = Constants.mailId;
				final String password = Constants.mailPwd;

				/*
				 * final String username = "akshaykasar72@gmail.com"; final String password =
				 * "mh151772@123";
				 */

				System.out.println("username" + username);
				System.out.println("password" + password);

				Properties props = new Properties();
				props.put("mail.smtp.host", "smtp.gmail.com");
				props.put("mail.smtp.socketFactory.port", "465");
				props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.port", "587");
				props.put("mail.smtp.starttls.enable", "true");

				Session session1 = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});

				try {
					Store mailStore = session1.getStore(mailStoreType);
					mailStore.connect(emailSMTPserver, username, password);
								
					String subject = " Order For " + vendorList.getVendorName();

					Message mimeMessage = new MimeMessage(session1);
					mimeMessage.setFrom(new InternetAddress(username));
					mimeMessage.setRecipients(Message.RecipientType.TO,
							InternetAddress.parse(vendorList.getVendorEmail()));
					mimeMessage.setSubject(subject);
					mimeMessage.setFileName("Bill Receipt Print");
					
					BodyPart messageBodyPart = new MimeBodyPart();
					
					String msg = "Dear Sir, \n Please find the attachment of bill receipt.";
					messageBodyPart.setText(msg);
					
					Multipart multipart = new MimeMultipart();
					DataSource source = new FileDataSource(Constants.REPORT_SAVE);
					messageBodyPart.setDataHandler(new DataHandler(source));
					messageBodyPart.setFileName(new File(Constants.REPORT_SAVE).getName());
					multipart.addBodyPart(messageBodyPart);
					mimeMessage.setContent(multipart);
					
//					String htmlStr = "<html><body style='color : blue;'>\n"
//					+"Dear Sir, <br> \n" + "	Please find the attachment of bill receipt."+"</body></html>";
//					mimeMessage.setContent(htmlStr, "text/html");

					Transport.send(mimeMessage);
				} catch (Exception e) {
					e.printStackTrace();
				}
				// ----------------------------------------------------------------------------

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/showBillDash";
	}

	private Dimension format = PD4Constants.A4;
	private boolean landscapeValue = false;
	private int topValue = 8;
	private int leftValue = 0;
	private int rightValue = 0;
	private int bottomValue = 8;
	private String unitsValue = "m";
	private String proxyHost = "";
	private int proxyPort = 0;

	private int userSpaceWidth = 750;
	private static int BUFFER_SIZE = 1024;

	public void doConversion(String url, String outputPath)
			throws InvalidParameterException, MalformedURLException, IOException {
		File output = new File(outputPath);
		java.io.FileOutputStream fos = new java.io.FileOutputStream(output);

		PD4ML pd4ml = new PD4ML();

		try {

			PD4PageMark footer = new PD4PageMark();
			footer.setPageNumberTemplate("page $[page] of $[total]");
			footer.setTitleAlignment(PD4PageMark.LEFT_ALIGN);
			footer.setPageNumberAlignment(PD4PageMark.RIGHT_ALIGN);
			footer.setInitialPageNumber(1);
			footer.setFontSize(8);
			footer.setAreaHeight(15);

			pd4ml.setPageFooter(footer);

		} catch (Exception e) {
			System.out.println("Pdf conversion method excep " + e.getMessage());
		}
		try {
			pd4ml.setPageSize(landscapeValue ? pd4ml.changePageOrientation(format) : format);
		} catch (Exception e) {
			System.out.println("Pdf conversion ethod excep " + e.getMessage());
		}

		if (unitsValue.equals("mm")) {
			pd4ml.setPageInsetsMM(new Insets(topValue, leftValue, bottomValue, rightValue));
		} else {
			pd4ml.setPageInsets(new Insets(topValue, leftValue, bottomValue, rightValue));
		}

		pd4ml.setHtmlWidth(userSpaceWidth);

		pd4ml.render(new URL(url), fos);
		fos.close();

		System.out.println(outputPath + "\ndone.");
	}

	List<Vendor> vendorList = new ArrayList<>();

//	@RequestMapping(value = "/showSettledBill", method = RequestMethod.GET)
//	public ModelAndView showSettledBill(HttpServletRequest request, HttpServletResponse response) {
//
//		ModelAndView model = new ModelAndView("billBook/settledBillList");
//		try {
//
//			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
//
//			Vendor[] vendorRes = rest.postForObject(Constants.url + "/getAllVendorListForDash", map, Vendor[].class);
//			vendorList = new ArrayList<Vendor>(Arrays.asList(vendorRes));
//			System.err.println("VENDOR LIST - " + vendorList);
//
//			model.addObject("vendorList", vendorList);
//
//			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
//			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
//			Calendar cal = Calendar.getInstance();
//			model.addObject("date", sdf.format(cal.getTime()));
//
//			String vendId = "";
//			if (vendorList != null) {
//				for (Vendor v : vendorList) {
//					vendId = vendId + "," + v.getVendorId();
//				}
//			}
//
//			map = new LinkedMultiValueMap<>();
//			map.add("fromDate", sdf1.format(cal.getTime()));
//			map.add("toDate", sdf1.format(cal.getTime()));
//			map.add("vendId", vendId.substring(1, vendId.length()));
//
//			System.err.println("VEND ID - " + vendId.substring(1, vendId.length()));
//
//			BillReceiptHeaderDisplay[] res = rest.postForObject(Constants.url + "/getSettledBillReceipt", map,
//					BillReceiptHeaderDisplay[].class);
//			ArrayList<BillReceiptHeaderDisplay> billRecList = new ArrayList<BillReceiptHeaderDisplay>(
//					Arrays.asList(res));
//			model.addObject("billRecList", billRecList);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return model;
//	}
//
//	// AJAX
//	@RequestMapping(value = "/getAllVendorListAjax", method = RequestMethod.GET)
//	@ResponseBody
//	public List<Vendor> getAllVendorListAjax(HttpServletRequest request, HttpServletResponse response) {
//		return vendorList;
//	}
//
//	// AJAX
//	@RequestMapping(value = "/getSettledBillReceiptList", method = RequestMethod.GET)
//	@ResponseBody
//	public List<BillReceiptHeaderDisplay> getSettledBillReceiptList(HttpServletRequest request,
//			HttpServletResponse response) {
//
//		List<BillReceiptHeaderDisplay> billRecList = new ArrayList<BillReceiptHeaderDisplay>();
//		try {
//
//			String fromDate = request.getParameter("fromDate");
//			String toDate = request.getParameter("toDate");
//
//			String vendId = request.getParameter("vendId");
//			vendId = vendId.substring(1, vendId.length() - 1);
//			vendId = vendId.replaceAll("\"", "");
//
//			System.err.println("VEND ID - " + vendId);
//
//			List<String> vendIds = Stream.of(vendId.split(",")).collect(Collectors.toList());
//			System.err.println("VEND ID LIST - " + vendIds);
//
//			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
//			map.add("fromDate", DateConvertor.convertToYMD(fromDate));
//			map.add("toDate", DateConvertor.convertToYMD(toDate));
//			map.add("vendId", vendId);
//
//			BillReceiptHeaderDisplay[] res = rest.postForObject(Constants.url + "/getSettledBillReceipt", map,
//					BillReceiptHeaderDisplay[].class);
//			billRecList = new ArrayList<BillReceiptHeaderDisplay>(Arrays.asList(res));
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return billRecList;
//	}
//
//	// ------------------
//	@RequestMapping(value = "/viewBillDetail/{billId}", method = RequestMethod.GET)
//	public ModelAndView editPurchaseOrder(@PathVariable int billId, HttpServletRequest request,
//			HttpServletResponse response) {
//
//		// ModelAndView model = new ModelAndView("billBook/editBillBook");
//		ModelAndView model = new ModelAndView("billBook/billDetailForDisplay");
//		try {
//
//			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
//			map.add("billId", billId);
//
//			BillBookHeader billHeader = rest.postForObject(Constants.url + "getBillHeaderById", map,
//					BillBookHeader.class);
//			System.err.println("BILL - " + billHeader);
//
//			if (billHeader != null) {
//
//				model.addObject("billHeader", billHeader);
//				model.addObject("billDate", DateConvertor.convertToDMY(billHeader.getBillDate()));
//
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return model;
//	}

}
