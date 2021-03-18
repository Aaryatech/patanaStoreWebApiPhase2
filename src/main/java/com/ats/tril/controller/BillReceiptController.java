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

import com.ats.tril.model.billbook.BillReceiptDetail;
import com.ats.tril.model.billbook.BillReceiptHeader;
import com.ats.tril.model.billbook.BillReceiptHeaderDisplay;
import com.ats.tril.repository.billbook.BillBookHeaderRepo;
import com.ats.tril.repository.billbook.BillReceiptDetailRepo;
import com.ats.tril.repository.billbook.BillReceiptHeaderDisplayRepo;
import com.ats.tril.repository.billbook.BillReceiptHeaderRepo;


@RestController
public class BillReceiptController {

	@Autowired
	BillReceiptHeaderRepo billReceiptHeaderRepo;

	@Autowired
	BillReceiptDetailRepo billReceiptDetailRepo;

	@Autowired
	BillBookHeaderRepo billBookHeaderRepo;

	@Autowired
	BillReceiptHeaderDisplayRepo billReceiptHeaderDisplayRepo;

	@RequestMapping(value = { "/saveBillReceiptHeaderAndDetail" }, method = RequestMethod.POST)
	public @ResponseBody BillReceiptHeader saveBillReceiptHeaderAndDetail(
			@RequestBody BillReceiptHeader billReceiptHeader) {

		BillReceiptHeader save = new BillReceiptHeader();

		try {

			int count = billReceiptHeaderRepo.getBillReceiptListCount() + 1;

			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy--");
			Calendar cal = Calendar.getInstance();
			String brNo = "BR-" + sdf.format(cal.getTimeInMillis()) + String.format("%04d", count);

			billReceiptHeader.setBrNo(brNo);

			save = billReceiptHeaderRepo.save(billReceiptHeader);
			System.err.println("BILL RECEIPT SAVE - " + save);

			for (int i = 0; i < billReceiptHeader.getBillReceiptDetailList().size(); i++) {

				BillReceiptDetail model = billReceiptHeader.getBillReceiptDetailList().get(i);
				model.setBrId(save.getBrId());
				float billTotal = model.getBillTotal();

				float totPaid = billReceiptDetailRepo.getBillTotalPaidAmount(model.getBillId());
				// System.err.println("PAID TOTAL =
				// "+billReceiptDetailRepo.getBillTotalPaidAmount(model.getBillId()));

				float pending = billTotal - totPaid;
				model.setBillPending(pending);

				BillReceiptDetail res = billReceiptDetailRepo.save(model);

				if (res != null) {

					int status = 0;
					if (res.getBillTotal() == res.getBillPaid()) {
						status = 2;
					} else if (res.getBillPaid() == 0) {
						status = 0;
					} else {
						status = 1;
					}
					int update = billBookHeaderRepo.updateStatus(res.getBillId(), status);

				}

				System.err.println("DETAIL  " + (i + 1) + " = " + model);
			}

		} catch (Exception e) {

			e.printStackTrace();

		}
		return save;

	}

	@RequestMapping(value = { "/getSettledBillReceipt" }, method = RequestMethod.POST)
	public @ResponseBody List<BillReceiptHeaderDisplay> getSettledBillReceipt(@RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate, @RequestParam("vendId") List<String> vendId) {

		List<BillReceiptHeaderDisplay> list = null;

		try {

			list = billReceiptHeaderDisplayRepo.getSettledBillReceiptList(fromDate, toDate, vendId);
			if (list == null) {
				list = new ArrayList<BillReceiptHeaderDisplay>();
			}

		} catch (Exception e) {

			e.printStackTrace();

		}
		return list;

	}

	@RequestMapping(value = { "/getSettledBillReceiptById" }, method = RequestMethod.POST)
	public @ResponseBody BillReceiptHeaderDisplay getSettledBillReceiptById(@RequestParam("brId") int brId) {

		BillReceiptHeaderDisplay header = null;

		try {

			header = billReceiptHeaderDisplayRepo.getSettledBillReceipt(brId);

			if (header != null) {

				List<BillReceiptDetail> detailList = billReceiptDetailRepo.getBillReceiptDetailListByBrId(brId);
				header.setBillReceiptDetailList(detailList);
			}

		} catch (Exception e) {

			e.printStackTrace();

		}
		return header;

	}

}
