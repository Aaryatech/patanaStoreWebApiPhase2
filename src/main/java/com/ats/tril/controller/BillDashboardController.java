package com.ats.tril.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ats.tril.model.billbook.BillDashData;
import com.ats.tril.repository.billbook.BillDashDataRepo;


@RestController
public class BillDashboardController {

	@Autowired
	BillDashDataRepo billDashDataRepo;

	@RequestMapping(value = { "/getPendingBillsForDashboard" }, method = RequestMethod.POST)
	public @ResponseBody List<BillDashData> getPendingBillsForDashboard() {

		List<BillDashData> list = new ArrayList<BillDashData>();

		try {

			list = billDashDataRepo.getPendingBillsForDashboard();

		} catch (Exception e) {

			e.printStackTrace();

		}
		return list;

	}
	
	@RequestMapping(value = { "/getBillsForSettlementByVendorAndStatus" }, method = RequestMethod.POST)
	public @ResponseBody List<BillDashData> getBillsForSettlementByVendorAndStatus(@RequestParam("vendId") int vendId) {

		List<BillDashData> list = new ArrayList<BillDashData>();

		try {

			list = billDashDataRepo.getBillsByVendAndStatus(vendId);

		} catch (Exception e) {

			e.printStackTrace();

		}
		return list;

	}

}
