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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
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
import com.ats.tril.model.ApproveBy;
import com.ats.tril.model.Category;
import com.ats.tril.model.Company;
import com.ats.tril.model.ErrorMessage;
import com.ats.tril.model.FinancialYears;
import com.ats.tril.model.GetItemGroup;
import com.ats.tril.model.GetItemSubGrp;
import com.ats.tril.model.GetpassItem;
import com.ats.tril.model.ItemGroup;
import com.ats.tril.model.ItemSubGroup;
import com.ats.tril.model.PaymentTerms;
import com.ats.tril.model.SettingValue;
import com.ats.tril.model.State;
import com.ats.tril.model.TaxForm;
import com.ats.tril.model.Vendor;
import com.ats.tril.model.doc.DocumentBean;
import com.ats.tril.model.doc.IndentReport;
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
@Scope("session")
public class MastersController {

	RestTemplate rest = new RestTemplate();
	List<GetItemGroup> itemGroupList = new ArrayList<>();
	List<Vendor> vendorList = new ArrayList<>();

	@RequestMapping(value = "/addFinancialYear", method = RequestMethod.GET)
	public ModelAndView addFinancialYear(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("masters/addFinancialYear");
		try {

			FinancialYears[] finYearList = rest.getForObject(Constants.url + "/getAllFinancialYears",
					FinancialYears[].class);
			model.addObject("finYearList", finYearList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = "/insertFinancialYear", method = RequestMethod.POST)
	public String insertFinancialYear(HttpServletRequest request, HttpServletResponse response) {

		try {
			String finYearId = request.getParameter("finYearId");

			System.out.println("finYearId" + finYearId);
			String finYear = request.getParameter("finYear");

			FinancialYears financialYears = new FinancialYears();
			if (finYearId == "" || finYearId == null)
				financialYears.setFinYearId(0);
			else
				financialYears.setFinYearId(Integer.parseInt(finYearId));
			financialYears.setFinYear(finYear);

			financialYears.setIsUsed(1);

			FinancialYears res = rest.postForObject(Constants.url + "/saveFinancialYears", financialYears,
					FinancialYears.class);

			System.out.println("res " + res);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/addFinancialYear";
	}

	@RequestMapping(value = "/editFinacialYear/{finYearId}", method = RequestMethod.GET)
	public ModelAndView editFinacialYear(@PathVariable int finYearId, HttpServletRequest request,
			HttpServletResponse response) {

		ModelAndView model = new ModelAndView("masters/addFinancialYear");
		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("finYearId", finYearId);
			FinancialYears editFinancialYears = rest.postForObject(Constants.url + "/getFinancialYearsByFYId", map,
					FinancialYears.class);
			model.addObject("editYear", editFinancialYears);

			FinancialYears[] finYearList = rest.getForObject(Constants.url + "/getAllFinancialYears",
					FinancialYears[].class);
			model.addObject("finYearList", finYearList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = "/deleteFinancialYear/{finYearId}", method = RequestMethod.GET)
	public String deleteFinancialYear(@PathVariable int finYearId, HttpServletRequest request,
			HttpServletResponse response) {

		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("finYearId", finYearId);
			ErrorMessage delete = rest.postForObject(Constants.url + "/deleteFinancialYears", map, ErrorMessage.class);
			System.out.println(delete);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/addFinancialYear";
	}

	@RequestMapping(value = "/addTaxForm", method = RequestMethod.GET)
	public ModelAndView addTaxForm(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("masters/addTaxForm");
		try {

			TaxForm[] taxFormList = rest.getForObject(Constants.url + "/getAllTaxForms", TaxForm[].class);
			model.addObject("taxFormList", taxFormList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = "/insertTaxForm", method = RequestMethod.POST)
	public String insertTaxForm(HttpServletRequest request, HttpServletResponse response) {

		try {
			String taxId = request.getParameter("taxId");

			System.out.println("taxId" + taxId);
			String taxPer = request.getParameter("taxPer");
			String taxDesc = request.getParameter("taxDesc");
			float sgst = Float.parseFloat(request.getParameter("sgst"));
			float cgst = Float.parseFloat(request.getParameter("cgst"));
			float igst = Float.parseFloat(request.getParameter("igst"));

			TaxForm taxForm = new TaxForm();
			if (taxId == "" || taxId == null)
				taxForm.setTaxId(0);
			else
				taxForm.setTaxId(Integer.parseInt(taxId));
			taxForm.setTaxDesc(taxDesc);
			taxForm.setTaxPer(Float.parseFloat(taxPer));
			taxForm.setSgstPer(sgst);
			taxForm.setCgstPer(cgst);
			taxForm.setIgstPer(igst);
			taxForm.setIsUsed(1);
			taxForm.setCreatedIn(1);
			taxForm.setDeletedIn(0);

			TaxForm res = rest.postForObject(Constants.url + "/saveTaxForm", taxForm, TaxForm.class);

			System.out.println("res " + res);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/addTaxForm";
	}

	@RequestMapping(value = "/editTaxForm/{taxId}", method = RequestMethod.GET)
	public ModelAndView editTaxForm(@PathVariable int taxId, HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("masters/addTaxForm");
		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("taxId", taxId);
			TaxForm editTaxForm = rest.postForObject(Constants.url + "/getTaxFormByTaxId", map, TaxForm.class);
			model.addObject("editTaxForm", editTaxForm);

			TaxForm[] taxFormList = rest.getForObject(Constants.url + "/getAllTaxForms", TaxForm[].class);
			model.addObject("taxFormList", taxFormList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = "/deleteTaxForm/{taxId}", method = RequestMethod.GET)
	public String deleteTaxForm(@PathVariable int taxId, HttpServletRequest request, HttpServletResponse response) {

		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("taxId", taxId);
			map.add("deletedIn", 0);
			ErrorMessage delete = rest.postForObject(Constants.url + "/deleteTaxForm", map, ErrorMessage.class);
			System.out.println(delete);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/addTaxForm";
	}

	@RequestMapping(value = "/addPaymentTerm", method = RequestMethod.GET)
	public ModelAndView addPaymentTerm(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("masters/addPaymentTerm");
		try {

			PaymentTerms[] paymentTermsLists = rest.getForObject(Constants.url + "/getAllPaymentTermsByIsUsed",
					PaymentTerms[].class);
			model.addObject("paymentTermsList", paymentTermsLists);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = "/insertPaymentTerm", method = RequestMethod.POST)
	public String insertPaymentTerm(HttpServletRequest request, HttpServletResponse response) {

		try {
			String pymtTermId = request.getParameter("pymtTermId");

			String pymtDesc = request.getParameter("pymtDesc");
			int days = Integer.parseInt(request.getParameter("days"));

			PaymentTerms paymentTerms = new PaymentTerms();
			if (pymtTermId == "" || pymtTermId == null)
				paymentTerms.setPymtTermId(0);
			else
				paymentTerms.setPymtTermId(Integer.parseInt(pymtTermId));
			paymentTerms.setPymtDesc(pymtDesc);
			paymentTerms.setDays(days);
			paymentTerms.setIsUsed(1);
			paymentTerms.setCreatedIn(1);
			paymentTerms.setDeletedIn(0);

			PaymentTerms res = rest.postForObject(Constants.url + "/savePaymentTerms", paymentTerms,
					PaymentTerms.class);

			System.out.println("res " + res);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/addPaymentTerm";
	}

	@RequestMapping(value = "/editPaymentTerm/{pymtTermId}", method = RequestMethod.GET)
	public ModelAndView editPaymentTerm(@PathVariable int pymtTermId, HttpServletRequest request,
			HttpServletResponse response) {

		ModelAndView model = new ModelAndView("masters/addPaymentTerm");
		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("pymtTermId", pymtTermId);
			PaymentTerms editPaymentTerms = rest.postForObject(Constants.url + "/getPaymetTermsByPaymentTermId", map,
					PaymentTerms.class);
			model.addObject("editPaymentTerms", editPaymentTerms);

			PaymentTerms[] paymentTermsList = rest.getForObject(Constants.url + "/getAllPaymentTermsByIsUsed",
					PaymentTerms[].class);
			model.addObject("paymentTermsList", paymentTermsList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = "/deletePaymentTerm/{pymtTermId}", method = RequestMethod.GET)
	public String deletePaymentTerm(@PathVariable int pymtTermId, HttpServletRequest request,
			HttpServletResponse response) {

		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("pymtTermId", pymtTermId);
			map.add("deletedIn", 0);
			ErrorMessage delete = rest.postForObject(Constants.url + "/deletePaymentTerms", map, ErrorMessage.class);
			System.out.println(delete);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/addPaymentTerm";
	}

	@RequestMapping(value = "/addItemGroup", method = RequestMethod.GET)
	public ModelAndView addItemGroup(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("masters/addItemGroup");
		try {

			GetItemGroup[] getItemGroup = rest.getForObject(Constants.url + "/getAllItemGroupByIsUsed",
					GetItemGroup[].class);
			itemGroupList = new ArrayList<GetItemGroup>(Arrays.asList(getItemGroup));
			model.addObject("itemGroupList", itemGroupList);

			Category[] category = rest.getForObject(Constants.url + "/getAllCategoryByIsUsed", Category[].class);
			List<Category> categoryList = new ArrayList<Category>(Arrays.asList(category));

			model.addObject("categoryList", categoryList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = "/checkGroupCodeExist", method = RequestMethod.GET)
	@ResponseBody
	public int checkGroupCodeExist(HttpServletRequest request, HttpServletResponse response) {

		int exist = 0;

		try {

			String grpCode = request.getParameter("grpCode");
			System.out.println("grpCode " + grpCode);

			for (int i = 0; i < itemGroupList.size(); i++) {
				if (itemGroupList.get(i).getGrpCode().equals(grpCode.trim())) {
					exist = 1;
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return exist;
	}

	@RequestMapping(value = "/insertItemGroup", method = RequestMethod.POST)
	public String insertItemGroup(HttpServletRequest request, HttpServletResponse response) {

		try {
			String grpId = request.getParameter("grpId");

			System.out.println("grpId" + grpId);

			String catId = request.getParameter("catId");
			String grpCode = request.getParameter("grpCode");
			String grpDesc = request.getParameter("grpDesc");

			String grpValueyn = request.getParameter("grpValueyn");

			ItemGroup itemGroup = new ItemGroup();
			if (grpId == "" || grpId == null)
				itemGroup.setGrpId(0);
			else
				itemGroup.setGrpId(Integer.parseInt(grpId));
			itemGroup.setCatId(Integer.parseInt(catId));
			itemGroup.setGrpCode(grpCode);
			itemGroup.setGrpValueyn(grpValueyn);
			itemGroup.setGrpDesc(grpDesc);
			itemGroup.setIsUsed(1);
			itemGroup.setCreatedIn(1);
			itemGroup.setDeletedIn(0);

			ItemGroup res = rest.postForObject(Constants.url + "/saveItemGroup", itemGroup, ItemGroup.class);

			System.out.println("res " + res);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/addItemGroup";
	}

	@RequestMapping(value = "/editItemGroup/{grpId}", method = RequestMethod.GET)
	public ModelAndView editItemGroup(@PathVariable int grpId, HttpServletRequest request,
			HttpServletResponse response) {

		ModelAndView model = new ModelAndView("masters/addItemGroup");
		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("grpId", grpId);
			ItemGroup editItemGroup = rest.postForObject(Constants.url + "/getItemGroupByGrpId", map, ItemGroup.class);
			model.addObject("editItemGroup", editItemGroup);

			GetItemGroup[] itemGroupList = rest.getForObject(Constants.url + "/getAllItemGroupByIsUsed",
					GetItemGroup[].class);
			model.addObject("itemGroupList", itemGroupList);

			Category[] category = rest.getForObject(Constants.url + "/getAllCategoryByIsUsed", Category[].class);
			List<Category> categoryList = new ArrayList<Category>(Arrays.asList(category));

			model.addObject("categoryList", categoryList);
			model.addObject("isEdit", 1);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = "/deleteItemGroup/{grpId}", method = RequestMethod.GET)
	public String deleteItemGroup(@PathVariable int grpId, HttpServletRequest request, HttpServletResponse response) {

		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("grpId", grpId);
			map.add("deletedIn", 0);
			ErrorMessage delete = rest.postForObject(Constants.url + "/deleteItemGroup", map, ErrorMessage.class);
			System.out.println(delete);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/addItemGroup";
	}

	@RequestMapping(value = "/addItemSubGroup", method = RequestMethod.GET)
	public ModelAndView addItemSubGroup(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("masters/addItemSubGroup");
		try {
			GetItemSubGrp[] itemSubGroupList = rest.getForObject(Constants.url + "/getAllItemSubGroup",
					GetItemSubGrp[].class);
			model.addObject("itemSubGroupList", itemSubGroupList);

			GetItemGroup[] itemGroupList = rest.getForObject(Constants.url + "/getAllItemGroupByIsUsed",
					GetItemGroup[].class);
			model.addObject("itemGroupList", itemGroupList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = "/insertItemSubGroup", method = RequestMethod.POST)
	public String insertItemSubGroup(HttpServletRequest request, HttpServletResponse response) {

		try {
			String subgrpId = request.getParameter("subgrpId");

			System.out.println("subgrpId" + subgrpId);

			String subGrpDesc = request.getParameter("subGrpDesc");
			String grpId = request.getParameter("grpId");

			ItemSubGroup itemSubGroup = new ItemSubGroup();

			if (subgrpId == "" || subgrpId == null)
				itemSubGroup.setSubgrpId(0);
			else
				itemSubGroup.setSubgrpId(Integer.parseInt(subgrpId));

			itemSubGroup.setSubgrpDesc(subGrpDesc);
			itemSubGroup.setGrpId(Integer.parseInt(grpId));
			itemSubGroup.setIsUsed(1);
			itemSubGroup.setCreatedIn(1);
			itemSubGroup.setDeletedIn(0);

			ItemSubGroup res = rest.postForObject(Constants.url + "/saveItemSubGroup", itemSubGroup,
					ItemSubGroup.class);

			System.out.println("res " + res);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/addItemSubGroup";
	}

	@RequestMapping(value = "/editItemSubGroup/{subgrpId}", method = RequestMethod.GET)
	public ModelAndView editItemSubGroup(@PathVariable int subgrpId, HttpServletRequest request,
			HttpServletResponse response) {

		ModelAndView model = new ModelAndView("masters/addItemSubGroup");
		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("subgrpId", subgrpId);
			GetItemSubGrp editItemSubGroup = rest.postForObject(Constants.url + "/getItemSubGrpBysubgrpId", map,
					GetItemSubGrp.class);
			model.addObject("editItemSubGroup", editItemSubGroup);

			GetItemSubGrp[] itemSubGroupList = rest.getForObject(Constants.url + "/getAllItemSubGroup",
					GetItemSubGrp[].class);
			model.addObject("itemSubGroupList", itemSubGroupList);

			GetItemGroup[] itemGroupList = rest.getForObject(Constants.url + "/getAllItemGroupByIsUsed",
					GetItemGroup[].class);
			model.addObject("itemGroupList", itemGroupList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = "/deleteItemSubGroup/{subgrpId}", method = RequestMethod.GET)
	public String deleteItemSubGroup(@PathVariable int subgrpId, HttpServletRequest request,
			HttpServletResponse response) {

		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("subgrpId", subgrpId);
			map.add("deletedIn", 0);
			ErrorMessage delete = rest.postForObject(Constants.url + "/deleteItemSubGroup", map, ErrorMessage.class);
			System.out.println(delete);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/addItemSubGroup";
	}

	@RequestMapping(value = "/addVendor", method = RequestMethod.GET)
	public ModelAndView addVendor(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("masters/addVendor");
		try {
			State[] stateList = rest.getForObject(Constants.url + "/getAllStates", State[].class);
			model.addObject("stateList", stateList);

			Vendor[] vendorRes = rest.getForObject(Constants.url + "/getAllVendorByIsUsed", Vendor[].class);
			vendorList = new ArrayList<Vendor>(Arrays.asList(vendorRes));

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("name", "defaultState"); 
			SettingValue settingValue = rest.postForObject(Constants.url + "/getSettingValue", map, SettingValue.class);
			
			map = new LinkedMultiValueMap<String, Object>();
			map.add("stateCode", settingValue.getValue()); 
			State state = rest.postForObject(Constants.url + "/getStateByStateCode", map, State.class);
			
			map = new LinkedMultiValueMap<String, Object>();
			map.add("name", "approvByName"); 
			SettingValue selectApprovBy = rest.postForObject(Constants.url + "/getSettingValue", map, SettingValue.class);
			
			if(Integer.parseInt(selectApprovBy.getValue())==1) {
				ApproveBy[] aprvRes = rest.getForObject(Constants.url + "/getApproveByList", ApproveBy[].class);
				List<ApproveBy> apprvByList = new ArrayList<ApproveBy>(Arrays.asList(aprvRes));

				model.addObject("apprvByList", apprvByList);
			}
			model.addObject("selectApprovBy", selectApprovBy.getValue());			

			Vendor vendor = new Vendor();
			
			vendor.setVendorContactPerson("-");
			vendor.setVendorMobile("1234567890");
			vendor.setVendorEmail("test@mongi.com");
			vendor.setVendorPhone("1234567890");
			vendor.setVendorGstNo("1234567890");
			vendor.setVendorAdd4("1234567890");
			vendor.setVendorItem("-");
			vendor.setVendorAdd3("-");
			vendor.setCreatedIn(1);
			vendor.setVendorStateId(state.getStateId());			
			
			model.addObject("editVendor", vendor);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = "/checkVendCodeExist", method = RequestMethod.GET)
	@ResponseBody
	public ErrorMessage checkVendCodeExist(HttpServletRequest request, HttpServletResponse response) {

		ErrorMessage errorMessage = new ErrorMessage();
		try {

			String vendorCode = request.getParameter("vendorCode");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("str", vendorCode);
			errorMessage = rest.postForObject(Constants.url + "/getNextVendorNo", map, ErrorMessage.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return errorMessage;
	}

	/*
	 * @RequestMapping(value = "/checkVendCodeExist", method = RequestMethod.GET)
	 * 
	 * @ResponseBody public int checkVendCodeExist(HttpServletRequest request,
	 * HttpServletResponse response) {
	 * 
	 * int exist = 0 ;
	 * 
	 * try {
	 * 
	 * String vendorCode = request.getParameter("vendorCode");
	 * 
	 * for(int i = 0 ; i < vendorList.size() ; i++) {
	 * if(vendorList.get(i).getVendorCode().equals(vendorCode.trim())) { exist = 1;
	 * break; } } } catch (Exception e) { e.printStackTrace(); }
	 * 
	 * return exist; }
	 */

	@RequestMapping(value = "/insertVendor", method = RequestMethod.POST)
	public String insertVendor(HttpServletRequest request, HttpServletResponse response) {

		try {
			String vendorId = request.getParameter("vendorId");

			System.out.println("vendor" + vendorId);

			String stateId = request.getParameter("stateId");
			String vendorCode = request.getParameter("vendorCode");
			String vendorName = request.getParameter("vendorName");
			String vendorAdd1 = request.getParameter("vendorAdd1");
			String vendorAdd2 = request.getParameter("vendorAdd2");
			String vendorAdd3 = request.getParameter("vendorAdd3");
			String vendorAdd4 = request.getParameter("vendorAdd4");
			String vendorState = request.getParameter("stateName");
			String vendorCity = request.getParameter("vendorCity");
			String vendorContactPerson = request.getParameter("vendorContactPerson");
			String vendorMobile = request.getParameter("vendorMobile");
			String vendorType = request.getParameter("vendorType");
			String vendorPhone = request.getParameter("vendorPhone");
			String approvedBy = request.getParameter("approvedBy");

			String vendorEmail = request.getParameter("vendorEmail");

			String vendorGstNo = request.getParameter("vendorGstNo");

			String vendorItem = request.getParameter("vendorItem");
			String vendorDate = request.getParameter("vendorDate");
			
			int creditDays = Integer.parseInt(request.getParameter("crditDays"));
			
//			int active = Integer.parseInt(request.getParameter("active"));
			Vendor vendor = new Vendor();
			if (vendorId == "" || vendorId == null)
				vendor.setVendorId(0);
			else
				vendor.setVendorId(Integer.parseInt(vendorId));

			vendor.setVendorCode(vendorCode);
			vendor.setVendorName(vendorName);

			vendor.setVendorAdd1(vendorAdd1);
			vendor.setVendorAdd2(vendorAdd2);
			vendor.setVendorAdd3(vendorAdd3);
			vendor.setVendorAdd4(vendorAdd4);

			vendor.setVendorStateId(Integer.parseInt(stateId));
			vendor.setVendorState(vendorState);

			vendor.setVendorCity(vendorCity);
			vendor.setVendorContactPerson(vendorContactPerson);
			vendor.setVendorPhone(vendorPhone);
			vendor.setVendorEmail(vendorEmail);
			vendor.setVendorMobile(vendorMobile);

			vendor.setVendorItem(vendorItem);
			vendor.setVendorGstNo(vendorGstNo);
			Date date = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			vendor.setVendorDate(sf.format(date));

			vendor.setVendorApprvBy("113");
			vendor.setVendorType(Integer.parseInt(vendorType));
			vendor.setIsUsed(1);
			vendor.setCreatedIn(creditDays);
			vendor.setDeletedIn(0);
			vendor.setVendorApprvBy(approvedBy);

			Vendor res = rest.postForObject(Constants.url + "/saveVendor", vendor, Vendor.class);

			System.out.println("res " + res);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/vendorList";
	}

	@RequestMapping(value = "/vendorList", method = RequestMethod.GET)
	public ModelAndView vendorList(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("masters/vendorList");
		try {
			Vendor[] vendorRes = rest.getForObject(Constants.url + "/getAllVendorByIsUsed", Vendor[].class);
			vendorList = new ArrayList<Vendor>(Arrays.asList(vendorRes));

			model.addObject("vendorList", vendorList);
			for (int i = 0; i < vendorList.size(); i++) {
				vendorList.get(i).setVendorDate(DateConvertor.convertToDMY(vendorList.get(i).getVendorDate()));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = "/vendorDetailPrint", method = RequestMethod.GET)
	public ModelAndView showIndentDocs(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("docs/vendorDetailPrint");

		try {

			int vendId = Integer.parseInt(request.getParameter("vendId"));

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("vendorId", vendId);
			Vendor vendorList = rest.postForObject(Constants.url + "/getVendorByVendorId", map, Vendor.class);
			vendorList.setVendorDate(DateConvertor.convertToDMY(vendorList.getVendorDate()));
			model.addObject("vendorDetail", vendorList);

			State[] stateList = rest.getForObject(Constants.url + "/getAllStates", State[].class);
			model.addObject("stateList", stateList);
			
			Company company = rest.getForObject(Constants.url + "getCompanyDetails",
					Company.class);
			model.addObject("company", company);
			
		} catch (Exception e) {

			e.printStackTrace();
			// TODO: handle exception
		}
		return model;
	}

	@RequestMapping(value = "/vendorListPdf", method = RequestMethod.GET)
	public void vendorListPdf(HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException {
		BufferedOutputStream outStream = null;

		List<Vendor> getVendorList = vendorList;
		System.out.println(getVendorList);
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

		PdfPTable table = new PdfPTable(10);
		try {
			System.out.println("Inside PDF Table try");
			table.setWidthPercentage(100);
			table.setWidths(new float[] { 1.4f, 3.7f, 3.7f, 3.7f, 3.7f, 3.7f, 3.2f, 3.2f, 4f, 3.7f });
			Font headFont = new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.BLACK);
			Font headFont1 = new Font(FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);
			headFont1.setColor(BaseColor.WHITE);
			Font f = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLUE);

			PdfPCell hcell = new PdfPCell();
			hcell.setBackgroundColor(BaseColor.PINK);

			hcell.setPadding(3);
			hcell = new PdfPCell(new Phrase("Sr.No.", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Item Code", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Name", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("City", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("State", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Email", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Gst No", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Item", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Mobile No", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Date", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			int index = 0;
			for (int i = 0; i < getVendorList.size(); i++) {

				Vendor vendor = getVendorList.get(i);

				index++;
				PdfPCell cell;

				cell = new PdfPCell(new Phrase(String.valueOf(index), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(vendor.getVendorCode(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(vendor.getVendorName(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(vendor.getVendorCity(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + vendor.getVendorState(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + vendor.getVendorEmail(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + vendor.getVendorGstNo(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + vendor.getVendorItem(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + vendor.getVendorMobile(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + vendor.getVendorDate(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

			}
			document.open();
			Paragraph name = new Paragraph("Item List\n", f);
			name.setAlignment(Element.ALIGN_CENTER);
			document.add(name);
			document.add(new Paragraph(" "));
			Paragraph company = new Paragraph("Item List Report\n", f);
			company.setAlignment(Element.ALIGN_CENTER);
			document.add(company);
			document.add(new Paragraph(" "));

			DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");

			document.add(new Paragraph("\n"));
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

	@RequestMapping(value = "/editVendor/{vendorId}", method = RequestMethod.GET)
	public ModelAndView editVendor(@PathVariable int vendorId, HttpServletRequest request,
			HttpServletResponse response) {

		ModelAndView model = new ModelAndView("masters/addVendor");
		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("vendorId", vendorId);
			Vendor vendorList = rest.postForObject(Constants.url + "/getVendorByVendorId", map, Vendor.class);
			vendorList.setVendorDate(DateConvertor.convertToDMY(vendorList.getVendorDate()));
			model.addObject("editVendor", vendorList);

			State[] stateList = rest.getForObject(Constants.url + "/getAllStates", State[].class);
			model.addObject("stateList", stateList);
			
			
			map = new LinkedMultiValueMap<String, Object>();
			map.add("name", "approvByName"); 
			SettingValue selectApprovBy = rest.postForObject(Constants.url + "/getSettingValue", map, SettingValue.class);
			
			if(Integer.parseInt(selectApprovBy.getValue())==1) {
				ApproveBy[] aprvRes = rest.getForObject(Constants.url + "/getApproveByList", ApproveBy[].class);
				List<ApproveBy> apprvByList = new ArrayList<ApproveBy>(Arrays.asList(aprvRes));

				model.addObject("apprvByList", apprvByList);
			}
			model.addObject("selectApprovBy", selectApprovBy.getValue());

			model.addObject("isEdit", 1);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = "/deleteVendor/{vendorId}", method = RequestMethod.GET)
	public String deleteVendor(@PathVariable int vendorId, HttpServletRequest request, HttpServletResponse response) {

		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("vendorId", vendorId);
			map.add("deletedIn", 0);
			ErrorMessage delete = rest.postForObject(Constants.url + "/deleteVendor", map, ErrorMessage.class);
			System.out.println(delete);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/vendorList";
	}

	@RequestMapping(value = "/addGetpassItem", method = RequestMethod.GET)
	public ModelAndView addGetpassItem(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("masters/getpassItem");
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = "/insertGetpassItem", method = RequestMethod.POST)
	public String insertGetpassItem(HttpServletRequest request, HttpServletResponse response) {

		try {
			String gpItemId = request.getParameter("gpItemId");

			System.out.println("gpItemId" + gpItemId);
			String itemName = request.getParameter("itemName");
			String itemDesc = request.getParameter("itemDesc");
			String itemCost = request.getParameter("itemCost");
			String warrantyDate = request.getParameter("warrantyDate");

			GetpassItem getpassItem = new GetpassItem();
			if (gpItemId == "" || gpItemId == null)
				getpassItem.setGpItemId(0);
			else
				getpassItem.setGpItemId(Integer.parseInt(gpItemId));
			getpassItem.setIsUsed(1);

			getpassItem.setItemCost(Float.parseFloat(itemCost));
			getpassItem.setItemDesc(itemDesc);
			getpassItem.setItemName(itemName);
			getpassItem.setWarrantyDate(DateConvertor.convertToYMD(warrantyDate));

			GetpassItem res = rest.postForObject(Constants.url + "/saveGatePass", getpassItem, GetpassItem.class);

			System.out.println("res " + res);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/addGetpassItem";
	}

	@RequestMapping(value = "/editGetpassItem/{gpItemId}", method = RequestMethod.GET)
	public ModelAndView editGetpassItem(@PathVariable int gpItemId, HttpServletRequest request,
			HttpServletResponse response) {

		ModelAndView model = new ModelAndView("masters/getpassItem");
		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("gpItemId", gpItemId);
			GetpassItem editGetPassItem = rest.postForObject(Constants.url + "/getGpItemByGetPassId", map,
					GetpassItem.class);
			System.out.println("editGetPassItem" + editGetPassItem.toString());
			model.addObject("date", DateConvertor.convertToDMY(editGetPassItem.getWarrantyDate()));
			model.addObject("editItem", editGetPassItem);

			GetpassItem[] itemList = rest.getForObject(Constants.url + "/getAllGpItemByIsUsed", GetpassItem[].class);
			model.addObject("itemList", itemList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = "/getpassItemList", method = RequestMethod.GET)
	public ModelAndView getpassItemList(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("masters/getpassItemList");
		try {
			GetpassItem[] getpassItemRes = rest.getForObject(Constants.url + "/getAllGpItemByIsUsed",
					GetpassItem[].class);
			List<GetpassItem> getpassItemList = new ArrayList<GetpassItem>(Arrays.asList(getpassItemRes));

			model.addObject("getpassItemList", getpassItemList);
			for (int i = 0; i < getpassItemList.size(); i++) {
				getpassItemList.get(i)
						.setWarrantyDate(DateConvertor.convertToDMY(getpassItemList.get(i).getWarrantyDate()));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = "/deleteGetpassItem/{gpItemId}", method = RequestMethod.GET)
	public String deleteGetpassItem(@PathVariable int gpItemId, HttpServletRequest request,
			HttpServletResponse response) {

		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("gpItemId", gpItemId);

			ErrorMessage delete = rest.postForObject(Constants.url + "/deleteGetpassItem", map, ErrorMessage.class);
			System.out.println(delete);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/getpassItemList";
	}
	
	
	@RequestMapping(value = "/addVendorApproveBy", method = RequestMethod.GET)
	public ModelAndView addVendorApproveBy(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("masters/addVndrApprovBy");
		try {			

			ApproveBy approve = new ApproveBy();			
			
			model.addObject("approve", approve);
			model.addObject("title", "Add Vendor Approve By");
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}
	
	@RequestMapping(value = "/insertApproveBy", method = RequestMethod.POST)
	public String insertApproveBy(HttpServletRequest request, HttpServletResponse response) {

		try {
			ApproveBy approve = new ApproveBy();
			approve.setApprovById(Integer.parseInt(request.getParameter("approveById")));
			approve.setApproveByName(request.getParameter("aprvByName"));
			approve.setDelStatus(0);
			approve.setExInt1(0);
			approve.setExInt2(0);
			approve.setExVar1("NA");
			approve.setExVar2("NA");
			
			ApproveBy res = rest.postForObject(Constants.url + "/saveVendorApproveBy", approve, ApproveBy.class);

			System.out.println("res " + res);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/approveByList";
	}

	@RequestMapping(value = "/approveByList", method = RequestMethod.GET)
	public ModelAndView approveByList(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("masters/approveByList");
		try {
			ApproveBy[] aprvRes = rest.getForObject(Constants.url + "/getApproveByList", ApproveBy[].class);
			List<ApproveBy> apprvByList = new ArrayList<ApproveBy>(Arrays.asList(aprvRes));

			model.addObject("apprvByList", apprvByList);
			

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}
	
	@RequestMapping(value = "/editApproveBy", method = RequestMethod.GET)
	public ModelAndView editApproveBy(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("masters/addVndrApprovBy");
		try {
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("apprvId", Integer.parseInt(request.getParameter("approveById")));
			
			ApproveBy approve = rest.postForObject(Constants.url + "/getApproveByInfoById", map,  ApproveBy.class);

			model.addObject("approve", approve);
			model.addObject("title", "Edit Vendor Approve By");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}
	
	
	@RequestMapping(value = "/deleteApproveBy", method = RequestMethod.GET)
	public String deleteApproveBy(HttpServletRequest request,
			HttpServletResponse response) {
		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("apprvId", Integer.parseInt(request.getParameter("approveById")));

			ErrorMessage delete = rest.postForObject(Constants.url + "/deleteApproveBy", map, ErrorMessage.class);
			System.out.println(delete);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/approveByList";
	}

}
