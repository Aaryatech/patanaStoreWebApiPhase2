package com.ats.tril.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ats.tril.model.ErrorMessage;
import com.ats.tril.model.GetPoHeaderList;
import com.ats.tril.model.Info;
import com.ats.tril.model.PoDetail;
import com.ats.tril.model.PoHeader;
import com.ats.tril.model.billbook.BillBookDetail;
import com.ats.tril.model.billbook.BillBookHeader;
import com.ats.tril.model.billbook.BillSettledList;
import com.ats.tril.model.billbook.GetBillHeader;
import com.ats.tril.repository.billbook.BillBookDetailRepo;
import com.ats.tril.repository.billbook.BillBookHeaderRepo;
import com.ats.tril.repository.billbook.BillSettledListRepo;
import com.ats.tril.repository.billbook.GetBillHeaderRepo;

@RestController
public class BillBookController {

	@Autowired
	BillBookHeaderRepo billBookHeaderRepo;

	@Autowired
	BillBookDetailRepo billBookDetailRepo;

	@Autowired
	GetBillHeaderRepo getBillHeaderRepo;
	
	@Autowired
	BillSettledListRepo billSettledListRepo;
	
	

	@RequestMapping(value = { "/saveBillBookHeaderAndDetail" }, method = RequestMethod.POST)
	public @ResponseBody BillBookHeader saveBillBookHeaderAndDetail(@RequestBody BillBookHeader billBookHeader) {

		BillBookHeader save = new BillBookHeader();

		try {

			save = billBookHeaderRepo.save(billBookHeader);
			System.err.println("BILL SAVE - " + save);

			for (int i = 0; i < billBookHeader.getBillBookDetail().size(); i++) {

				BillBookDetail model = billBookHeader.getBillBookDetail().get(i);
				model.setBillId(save.getBillId());

				billBookDetailRepo.save(model);
				
				System.err.println("DETAIL  "+(i+1)+" = "+model);
			}

		} catch (Exception e) {

			e.printStackTrace();

		}
		return save;

	}

	@RequestMapping(value = { "/getBillHeaderBetDate" }, method = RequestMethod.POST)
	public @ResponseBody List<GetBillHeader> getBillHeaderBetDate(@RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate) {

		List<GetBillHeader> list = new ArrayList<GetBillHeader>();

		try {

			list = getBillHeaderRepo.getBillHeaderListByDate(fromDate, toDate);

		} catch (Exception e) {

			e.printStackTrace();

		}
		return list;

	}

	@RequestMapping(value = { "/getBillNoFoBillBook" }, method = RequestMethod.POST)
	public @ResponseBody Info getBillNoFoBillBook() {

		Info info = new Info();

		try {

			int count = billBookHeaderRepo.getBillListCount() + 1;

			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy--");
			Calendar cal = Calendar.getInstance();
			String billNo = sdf.format(cal.getTimeInMillis());

			info.setMessage(billNo + "" + String.format("%04d", count));

		} catch (Exception e) {

			e.printStackTrace();

		}
		return info;

	}

	@RequestMapping(value = { "/getBillHeaderById" }, method = RequestMethod.POST)
	public @ResponseBody BillBookHeader getBillHeaderById(@RequestParam("billId") int billId) {

		BillBookHeader header = null;

		try {

			header = billBookHeaderRepo.getBillHeaderById(billId);

			if (header == null) {
				header = new BillBookHeader();
			} else {

				List<BillBookDetail> billBookDetailList = new ArrayList<>();
				billBookDetailList = billBookDetailRepo.findAllByBillId(billId);
				header.setBillBookDetail(billBookDetailList);
			}

		} catch (Exception e) {

			e.printStackTrace();

		}
		return header;

	}
	
	
	
	
	@RequestMapping(value = { "/updateBillStatus" }, method = RequestMethod.POST)
	public @ResponseBody ErrorMessage updateBillStatus(@RequestParam("billId") int billId,
			@RequestParam("status") int status) {

		ErrorMessage errorMessage = new ErrorMessage();

		try {

			int update = billBookHeaderRepo.updateStatus(billId,status); 
		
			if(update>0) {
				errorMessage.setError(false);
				errorMessage.setMessage("Approved");
			}else {
				errorMessage.setError(true);
				errorMessage.setMessage("Not Approved");
			}
		
			
			 
		} catch (Exception e) {

			e.printStackTrace();
			errorMessage.setError(true);
			errorMessage.setMessage("Not Approved");

		}
		return errorMessage;

	}
	
	@RequestMapping(value = { "/getSettledBillsById" }, method = RequestMethod.POST)
	public @ResponseBody List<BillSettledList> getSettledBillsById(@RequestParam("billId") int billId) {

		List<BillSettledList> list = new ArrayList<BillSettledList>();

		try {

			list = billSettledListRepo.getBillSettledListById(billId);

		} catch (Exception e) {

			e.printStackTrace();

		}
		return list;

	}
	

}
