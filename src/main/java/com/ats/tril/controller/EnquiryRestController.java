package com.ats.tril.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ats.tril.common.DateConvertor;
import com.ats.tril.model.EnquiryDetail;
import com.ats.tril.model.EnquiryHeader;
import com.ats.tril.model.ErrorMessage;
import com.ats.tril.model.GetEnquiryDetail;
import com.ats.tril.model.GetEnquiryHeader;
import com.ats.tril.model.GetQuatationDetail;
import com.ats.tril.model.GetQuatationHeader;
import com.ats.tril.model.QuatationDetail;
import com.ats.tril.model.QuatationHeader;
import com.ats.tril.repository.EnquiryDetailRepository;
import com.ats.tril.repository.EnquiryHeaderRepository;
import com.ats.tril.repository.GetEnquiryDetailRepository;
import com.ats.tril.repository.GetEnquiryHeaderRepository;
import com.ats.tril.repository.GetQuatationDetailRepository;
import com.ats.tril.repository.GetQuatationHeaderRepository;
import com.ats.tril.repository.QuatationDetailRepository;
import com.ats.tril.repository.QuatationHeaderRepository;

@RestController
public class EnquiryRestController {

	@Autowired
	EnquiryHeaderRepository enquiryHeaderRepository;

	@Autowired
	EnquiryDetailRepository enquiryDetailRepository;

	@Autowired
	GetEnquiryHeaderRepository getEnquiryHeaderRepository;

	@Autowired
	GetEnquiryDetailRepository getEnquiryDetailRepository;
	
	@Autowired
	QuatationHeaderRepository quatationHeaderRepository;
	
	@Autowired
	QuatationDetailRepository quatationDetailRepository;
	
	@Autowired
	GetQuatationHeaderRepository getQuatationHeaderRepository;
	
	@Autowired
	GetQuatationDetailRepository getQuatationDetailRepository;

	@RequestMapping(value = { "/saveEnquiryHeaderAndDetail" }, method = RequestMethod.POST)
	public @ResponseBody ErrorMessage saveEnquiryHeaderAndDetail(@RequestBody List<EnquiryHeader> enquiryHeaderList) {

		ErrorMessage errorMessage = new ErrorMessage();

		try {

			for (int i = 0; i < enquiryHeaderList.size(); i++) {
				EnquiryHeader enquiryHeader = enquiryHeaderRepository.save(enquiryHeaderList.get(i));

				for (int j = 0; j < enquiryHeaderList.get(i).getEnquiryDetailList().size(); j++) {
					enquiryHeaderList.get(i).getEnquiryDetailList().get(j).setEnqId(enquiryHeader.getEnqId());
					enquiryHeaderList.get(i).getEnquiryDetailList().get(j).setVendId(enquiryHeader.getVendId());
					enquiryHeaderList.get(i).getEnquiryDetailList().get(j).setEnqDetailDate(DateConvertor
							.convertToYMD(enquiryHeaderList.get(i).getEnquiryDetailList().get(j).getEnqDetailDate()));
				}

				List<EnquiryDetail> enquiryDetailsList = enquiryDetailRepository
						.saveAll(enquiryHeaderList.get(i).getEnquiryDetailList());
			}

			errorMessage.setError(false);
			errorMessage.setMessage("successfully Saved ");

		} catch (Exception e) {

			e.printStackTrace();
			errorMessage.setError(true);
			errorMessage.setMessage("failed to Save ");

		}
		return errorMessage;

	}

	@RequestMapping(value = { "/getEnquiryHeaderList" }, method = RequestMethod.GET)
	public @ResponseBody List<GetEnquiryHeader> getEnquiryHeaderList() {

		List<GetEnquiryHeader> enquiryHeaderList = new ArrayList<GetEnquiryHeader>();

		try {

			enquiryHeaderList = getEnquiryHeaderRepository.getEnquiryHeaderList();

		} catch (Exception e) {

			e.printStackTrace();

		}
		return enquiryHeaderList;

	}

	@RequestMapping(value = { "/getEnquiryHeaderListBetweenDate" }, method = RequestMethod.POST)
	public @ResponseBody List<GetEnquiryHeader> getEnquiryHeaderListBetweenDate(
			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate) {

		List<GetEnquiryHeader> enquiryHeaderList = new ArrayList<GetEnquiryHeader>();

		try {

			enquiryHeaderList = getEnquiryHeaderRepository.getEnquiryHeaderListBetweenDate(fromDate, toDate);

		} catch (Exception e) {

			e.printStackTrace();

		}
		return enquiryHeaderList;

	}

	@RequestMapping(value = { "/getEnquiryHeaderAndDetail" }, method = RequestMethod.POST)
	public @ResponseBody GetEnquiryHeader getEnquiryHeaderAndDetail(@RequestParam("enqId") int enqId) {

		GetEnquiryHeader enquiryHeader = new GetEnquiryHeader();

		try {

			enquiryHeader = getEnquiryHeaderRepository.getEnquiryHeader(enqId);

			List<GetEnquiryDetail> enquiryDetailList = getEnquiryDetailRepository.getEnquiryDetail(enqId);

			enquiryHeader.setEnquiryDetailList(enquiryDetailList);

		} catch (Exception e) {

			e.printStackTrace();

		}
		return enquiryHeader;

	}
	
	
	@RequestMapping(value = { "/getEnquiryHeaderAndDetailForPdf" }, method = RequestMethod.POST)
	public @ResponseBody GetEnquiryHeader getEnquiryHeaderAndDetailForPdf(@RequestParam("enqId") int enqId) {

		GetEnquiryHeader enquiryHeader = new GetEnquiryHeader();

		try {

			enquiryHeader = getEnquiryHeaderRepository.getEnquiryHeaderforPdf(enqId);

			List<GetEnquiryDetail> enquiryDetailList = getEnquiryDetailRepository.getEnquiryDetailforPdf(enqId);

			enquiryHeader.setEnquiryDetailList(enquiryDetailList);

		} catch (Exception e) {

			e.printStackTrace();

		}
		return enquiryHeader;

	}

	@RequestMapping(value = { "/deleteEnquiryHeader" }, method = RequestMethod.POST)
	public @ResponseBody ErrorMessage deleteEnquiryHeader(@RequestParam("enqId") int enqId) {

		ErrorMessage errorMessage = new ErrorMessage();

		try {

			int update = enquiryHeaderRepository.delete(enqId);

			if (update == 0) {
				errorMessage.setError(true);
				errorMessage.setMessage("failed to Delete ");
			} else {
				errorMessage.setError(false);
				errorMessage.setMessage("  Deleted ");
			}

		} catch (Exception e) {

			e.printStackTrace();
			errorMessage.setError(true);
			errorMessage.setMessage("failed to Delete ");

		}
		return errorMessage;

	}

	@RequestMapping(value = { "/getEnqHeaderListBetweenDate" }, method = RequestMethod.POST)
	public @ResponseBody List<GetEnquiryHeader> getEnqHeaderListBetweenDate(@RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate, @RequestParam("status") int status) {

		List<GetEnquiryHeader> enquiryHeaderList = new ArrayList<GetEnquiryHeader>();

		try {

			if (status == 2) {
				enquiryHeaderList = getEnquiryHeaderRepository.getEnquiryHeaderListBetweenDate(fromDate, toDate);

			} else
          if(fromDate.equals("1") && toDate.equals("1"))
          {
				enquiryHeaderList = getEnquiryHeaderRepository.getEnqHeaderListByStatus(status);
            
				for(int i=0;i<enquiryHeaderList.size();i++)
				{
					List<GetEnquiryDetail> enquiryDetailList = getEnquiryDetailRepository.getEnquiryDetail(enquiryHeaderList.get(i).getEnqId());
					enquiryHeaderList.get(i).setEnquiryDetailList(enquiryDetailList);
				}
          }
          else {

				enquiryHeaderList = getEnquiryHeaderRepository.getEnqHeaderListBetweenDate(fromDate, toDate, status);
			}
		} catch (Exception e) {

			e.printStackTrace();

		}
		return enquiryHeaderList;

	}
	
	@RequestMapping(value = { "/saveQuatationHeaderAndDetail" }, method = RequestMethod.POST)
	public @ResponseBody QuatationHeader saveQuatationHeaderAndDetail(@RequestBody QuatationHeader quatationHeader) {

		QuatationHeader save = new QuatationHeader();

		try {

			 
			 save = quatationHeaderRepository.save(quatationHeader);
 
			 for(int i=0 ; i<quatationHeader.getQuatationDetailList().size() ; i++) {
				 quatationHeader.getQuatationDetailList().get(i).setEnqId(save.getEnqId());
				 quatationHeader.getQuatationDetailList().get(i).setIndNo(save.getEnqNo());
			 }

				List<QuatationDetail> quatationDetailList = quatationDetailRepository
						.saveAll(quatationHeader.getQuatationDetailList());
			 
				save.setQuatationDetailList(quatationDetailList);

		} catch (Exception e) {
 
		}
		return save;

	}
	
	@RequestMapping(value = { "/getQuatationHeaderAndDetail" }, method = RequestMethod.POST)
	public @ResponseBody GetQuatationHeader getQuatationHeaderAndDetail(@RequestParam("enqId") int enqId) {

		GetQuatationHeader enquiryHeader = new GetQuatationHeader();

		try {

			enquiryHeader = getQuatationHeaderRepository.getQuatationHeader(enqId);

			List<GetQuatationDetail> enquiryDetailList = getQuatationDetailRepository.getQuatationDetail(enqId);

			enquiryHeader.setQuatationDetailList(enquiryDetailList);
			
		} catch (Exception e) {

			e.printStackTrace();

		}
		return enquiryHeader;

	}
	
	@RequestMapping(value = { "/deleteQuatationHeader" }, method = RequestMethod.POST)
	public @ResponseBody ErrorMessage deleteQuatationHeader(@RequestParam("enqId") int enqId) {

		ErrorMessage errorMessage = new ErrorMessage();

		try {

			int update = quatationHeaderRepository.delete(enqId);

			if (update == 0) {
				errorMessage.setError(true);
				errorMessage.setMessage("failed to Delete ");
			} else {
				errorMessage.setError(false);
				errorMessage.setMessage("  Deleted ");
			}

		} catch (Exception e) {

			e.printStackTrace();
			errorMessage.setError(true);
			errorMessage.setMessage("failed to Delete ");

		}
		return errorMessage;

	}
	
	@RequestMapping(value = { "/getQuatationHeaderListBetweenDate" }, method = RequestMethod.POST)
	public @ResponseBody List<GetQuatationHeader> getQuatationHeaderListBetweenDate(
			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate) {

		List<GetQuatationHeader> getQuatationHeaderList = new ArrayList<GetQuatationHeader>();

		try {

			getQuatationHeaderList = getQuatationHeaderRepository.getQuatationHeaderListBetweenDate(fromDate, toDate);

		} catch (Exception e) {

			e.printStackTrace();

		}
		return getQuatationHeaderList;

	}
	
	@RequestMapping(value = { "/getQuotationListForEnquiry" }, method = RequestMethod.GET)
	public @ResponseBody List<GetQuatationHeader> getQuotationListForEnquiry() {

		List<GetQuatationHeader> getQuatationHeaderList = new ArrayList<GetQuatationHeader>();

		try {

			getQuatationHeaderList = getQuatationHeaderRepository.getQuotationListForEnquiry();

		} catch (Exception e) {

			e.printStackTrace();

		}
		return getQuatationHeaderList;

	}
	
	@RequestMapping(value = { "/getQuotationDetailByQutId" }, method = RequestMethod.POST)
	public @ResponseBody List<GetQuatationDetail> getQuotationDetailByQutId(@RequestParam("qutId") int qutId) {

		List<GetQuatationDetail> getQuatationDetailList = new ArrayList<GetQuatationDetail>();

		try {

			getQuatationDetailList = getQuatationDetailRepository.getQuatationDetail(qutId);

		} catch (Exception e) {

			e.printStackTrace();

		}
		return getQuatationDetailList;

	}

}
