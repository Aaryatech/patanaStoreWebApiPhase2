package com.ats.tril.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ats.tril.model.BillInfo;
import com.ats.tril.model.PurchaseInvoices;
import com.ats.tril.model.TallyPurchaseGroupByInvoices;
import com.ats.tril.model.TallySyncData;
import com.ats.tril.repository.TallySyncDataRepo;


@RestController
public class TallyController {

	@Autowired
	TallySyncDataRepo tallySyncDataRepo;

	// TALLY SYNC CREDIT NOTE
	@RequestMapping(value = { "/getPurchaseTallySyncGroupBy" }, method = RequestMethod.GET)
	public @ResponseBody TallyPurchaseGroupByInvoices getTallyPurchaseData(@RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate) {

		TallyPurchaseGroupByInvoices res = new TallyPurchaseGroupByInvoices();

		List<TallySyncData> tallyList = new ArrayList<>();
		tallyList = tallySyncDataRepo.getTallySyncData(fromDate, toDate);

		List<PurchaseInvoices> purchaseInvoices = new ArrayList<>();

		if (tallyList != null) {

			Set<String> invoiceSet = new HashSet<String>();
			for (TallySyncData bills : tallyList) {
				invoiceSet.add(bills.getBillBookNo());
			}

			List<String> invList = new ArrayList<>();
			invList.addAll(invoiceSet);

			// Collections.sort(invList);

			for (String invoice : invList) {

				PurchaseInvoices purchaseModel = new PurchaseInvoices();
				purchaseModel.setBillBookNo(invoice);

				List<BillInfo> billList = new ArrayList<>();

				for (TallySyncData bills : tallyList) {
					if (invoice.equalsIgnoreCase(bills.getBillBookNo())) {

						BillInfo bill = new BillInfo(bills.getBillDetailId(), bills.getBillId(), bills.getVendor(),
								bills.getVendorCreditDays(), bills.getBillBookNo(), bills.getDate(), bills.getBillNo(),
								bills.getBillDate(), bills.getProductName(), bills.getUom(), bills.getRate(),
								bills.getQty(), bills.getRateTotal(), bills.getCgst(), bills.getSgst(), bills.getIgst(),
								bills.getOtherCharges(), bills.getDiscValue(), bills.getTotalAmount());
						billList.add(bill);

					}
				}
				purchaseModel.setBillInfo(billList);
				purchaseInvoices.add(purchaseModel);
			}

			res.setPurchaseInvoices(purchaseInvoices);
		}

		System.err.println("Resp Of Api-->"+res);
		return res;
	}

}
