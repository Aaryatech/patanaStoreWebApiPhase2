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

import com.ats.tril.model.ErrorMessage;
import com.ats.tril.model.GetItem;
import com.ats.tril.model.PoDetail;
import com.ats.tril.model.indent.DashIndentDetails;
import com.ats.tril.model.indent.GetIndents;
import com.ats.tril.model.mrn.GetMrnDetail;
import com.ats.tril.model.mrn.GetMrnDetailRej;
import com.ats.tril.model.mrn.GetMrnHeader;
import com.ats.tril.model.mrn.GetMrnHeaderRej;
import com.ats.tril.model.mrn.MrnDetail;
import com.ats.tril.model.mrn.MrnHeader;
import com.ats.tril.model.mrn.MrnReport;
import com.ats.tril.model.mrn.OfficeMrnDetail;
import com.ats.tril.model.mrn.OfficeMrnHeader;
import com.ats.tril.model.mrn.PoItemForMrnEdit;
import com.ats.tril.model.rejection.RejectionMemo;
import com.ats.tril.model.rejection.RejectionMemoDetail;
import com.ats.tril.model.rejection.repo.GetMrnDetailRejRepo;
import com.ats.tril.model.rejection.repo.GetMrnHeaderRejRepo;
import com.ats.tril.repository.GetItemRepository;
import com.ats.tril.repository.PoDetailRepository;
import com.ats.tril.repository.PoHeaderRepository;
import com.ats.tril.repository.mrn.GetMrnDetailRepository;
import com.ats.tril.repository.mrn.GetMrnHeaderRepository;
import com.ats.tril.repository.mrn.MrnDetailRepo;
import com.ats.tril.repository.mrn.MrnHeaderRepository;
import com.ats.tril.repository.mrn.MrnReportRepo;
import com.ats.tril.repository.mrn.OfficeMrnDetailRepo;
import com.ats.tril.repository.mrn.OfficeMrnHeaderRepository;
import com.ats.tril.repository.mrn.PoItemForMrnEditRepo;
import com.sun.org.apache.bcel.internal.util.SyntheticRepository;

@RestController
public class MrnApiController {

	@Autowired
	MrnHeaderRepository mrnHeaderRepository;

	@Autowired
	GetMrnHeaderRejRepo getMrnHeaderRejRepo;

	@Autowired
	GetMrnDetailRejRepo getMrnDetailRejRepo;

	@Autowired
	MrnDetailRepo mrnDetailRepo;

	@Autowired
	GetMrnHeaderRepository getMrnHeaderRepository;

	@Autowired
	GetMrnDetailRepository getMrnDetailRepository;

	@Autowired
	PoDetailRepository poDetailRepo;
	@Autowired
	PoHeaderRepository poHeaderRepository;

	@Autowired
	GetItemRepository getItemRepository;

	@Autowired
	PoItemForMrnEditRepo getPoItemForMrnEditRepo;
	
	@Autowired 
	OfficeMrnDetailRepo officMrnDtlRepo;
	
	@Autowired 
	OfficeMrnHeaderRepository officeMrnHeadRepo;

	@RequestMapping(value = { "/getOneMrnHeader" }, method = RequestMethod.POST)
	public @ResponseBody MrnHeader getMrnHeaderByMrnId(@RequestParam("mrnId") int mrnId) {
		System.err.println("inside web api save saveMrnHeadAndDetail");
		MrnHeader res = new MrnHeader();

		try {
			res = mrnHeaderRepository.findByMrnId(mrnId);

		} catch (Exception e) {

			System.err.println("Exce in /getMrnHeader @MrnApi  " + e.getMessage());
			e.printStackTrace();
		}

		return res;
	}

	@RequestMapping(value = { "/getPoItemForMrnEdit" }, method = RequestMethod.POST)
	public @ResponseBody List<PoItemForMrnEdit> getPoItemForMrnEdit(@RequestParam("mrnId") int mrnId) {

		List<PoItemForMrnEdit> poItemMrnEditList = new ArrayList<PoItemForMrnEdit>();
		try {

			poItemMrnEditList = getPoItemForMrnEditRepo.getPoItemForMrnEdit(mrnId);

		} catch (Exception e) {

			System.err.println("Exce in /getPoItemForMrnEdit @MrnApi  " + e.getMessage());
			e.printStackTrace();
		}

		return poItemMrnEditList;

	}

	@RequestMapping(value = { "/saveMrnHeadAndDetail" }, method = RequestMethod.POST)
	public @ResponseBody MrnHeader saveMrnHeadAndDetail(@RequestBody MrnHeader mrnHeader) {
		System.err.println("inside web api save saveMrnHeadAndDetail");
		MrnHeader res = new MrnHeader();

		try {

			res = mrnHeaderRepository.saveAndFlush(mrnHeader);

			List<MrnDetail> mrnDetailList = mrnHeader.getMrnDetailList();

			int mrnId = res.getMrnId();
			String mrnNo = res.getMrnNo();
			String batchNo;

			for (int i = 0; i < mrnDetailList.size(); i++) {

				MrnDetail detail = mrnDetailList.get(i);

				GetItem item = getItemRepository.getItemByItemId(detail.getItemId());
				batchNo = new String();
				batchNo = mrnNo + "-" + item.getItemCode();

				int mrnOfcDtlId = detail.getMrnDetailId();
				
				System.out.println("Ofice MRN Detail Id : "+mrnOfcDtlId);
				
				detail.setMrnDetailId(0);
				detail.setBatchNo(batchNo);

				detail.setMrnId(mrnId);
				MrnDetail mrnDetailRes = mrnDetailRepo.save(detail);
				
				PoDetail poDetail = poDetailRepo.findByPoDetailId(mrnDetailRes.getPoDetailId());

				if (mrnDetailRes != null) {
					float remainingQty = 0;

					if (detail.getMrnQtyBeforeEdit() == -1) {

						System.err.println("Inside mrn qty before Edit ==-1");

						remainingQty = poDetail.getPendingQty() - mrnDetailRes.getMrnQty();

					} else {

						System.err.println("Inside mrn qty before edit is greater than 0");
						remainingQty = poDetail.getPendingQty()
								- (mrnDetailRes.getMrnQty()-detail.getMrnQtyBeforeEdit());

					}

					poDetail.setPendingQty(remainingQty);
					int status = 1;

					if (remainingQty <= 0) {
						System.err.println("Pending qty =0 keeping status=2");
						status = 2;
					}

					poDetail.setStatus(status);
					PoDetail poDetailStatusUpdate = poDetailRepo.save(poDetail);
					 
					List<Integer> stss = new ArrayList<Integer>();
					stss.add(2);
					stss.add(9);
					stss.add(7);
					List<PoDetail> poDetailsList = poDetailRepo.findAllByStatusNotInAndPoId(stss, mrnDetailList.get(i).getPoId());

					if (poDetailsList.isEmpty()) {

						System.err.println("Po Detail list is Empty so Update po Header Status for POId "
								+ mrnDetailList.get(i).getPoId());

						int updatePoHeaderStatus = poHeaderRepository.updateResponsePoHead(2, mrnDetailList.get(i).getPoId());

					}
					else {
						List<Integer> sts = new ArrayList<Integer>();
						sts.add(0);
						sts.add(1);
							List<PoDetail> details=poDetailRepo.findAllByPoIdAndStatusNotIn(mrnDetailList.get(i).getPoId(),sts);
							poDetailsList=new ArrayList<PoDetail>();
							
							poDetailsList = poDetailRepo.findAllByStatusAndPoId(1, mrnDetailList.get(i).getPoId());
		
						if(poDetailsList.isEmpty()) {
		
							int updatePoHeaderStatus = poHeaderRepository.updateResponsePoHead(0, mrnDetailList.get(i).getPoId());
		
						}
						else {
							int updatePoHeaderStatus = poHeaderRepository.updateResponsePoHead(1, mrnDetailList.get(i).getPoId());
						}
					}

				}

			}

		} catch (Exception e) {

			System.err.println("Exception in saving Mrn Header and Detail  " + e.getMessage());
			e.printStackTrace();

		}

		return res;
	}
	
	
	
	@RequestMapping(value = { "/saveOffictMrnToMrnHeadAndDetail" }, method = RequestMethod.POST)
	public @ResponseBody MrnHeader saveOffictMrnToMrnHeadAndDetail(@RequestBody MrnHeader mrnHeader) {
		System.err.println("inside web api save saveOffictMrnToMrnHeadAndDetail");
		MrnHeader res = new MrnHeader();

		try {

			res = mrnHeaderRepository.saveAndFlush(mrnHeader);

			List<MrnDetail> mrnDetailList = mrnHeader.getMrnDetailList();

			int mrnId = res.getMrnId();
			String mrnNo = res.getMrnNo();
			String batchNo;
			int ofcMrnHeadId = 0;
			for (int i = 0; i < mrnDetailList.size(); i++) {

				MrnDetail detail = mrnDetailList.get(i);

				GetItem item = getItemRepository.getItemByItemId(detail.getItemId());
				batchNo = new String();
				batchNo = mrnNo + "-" + item.getItemCode();

				int mrnOfcDtlId = detail.getMrnDetailId();
				
				System.out.println("Ofice MRN Detail Id : "+mrnOfcDtlId);
				
				detail.setMrnDetailId(0);
				detail.setBatchNo(batchNo);

				detail.setMrnId(mrnId);
				MrnDetail mrnDetailRes = mrnDetailRepo.save(detail);
				if(mrnDetailRes.getMrnDetailId()>0) {
					
					OfficeMrnDetail officeMrn = officMrnDtlRepo.findMrnQtyByMrnDetailId(mrnOfcDtlId);
					ofcMrnHeadId = officeMrn.getMrnId();
					
					if(officeMrn!=null) {
						float remQty = officeMrn.getRemainingQty()-detail.getMrnQty();
						float aprvQty =  detail.getMrnQty()+officeMrn.getApproveQty();
						int status = 0;
						if(remQty==0) {
							status = 2;
						}else {
							status = 1;
						}
						int updtofcMrnDtl = officMrnDtlRepo.updateRemMrnQty(aprvQty,remQty, status, mrnOfcDtlId);						
					}					
				}
				

				/*
				 * PoDetail poDetail =
				 * poDetailRepo.findByPoDetailId(mrnDetailRes.getPoDetailId());
				 * 
				 * if (mrnDetailRes != null) { float remainingQty = 0;
				 * 
				 * if (detail.getMrnQtyBeforeEdit() == -1) {
				 * 
				 * System.err.println("Inside mrn qty before Edit ==-1");
				 * 
				 * remainingQty = poDetail.getPendingQty() - mrnDetailRes.getMrnQty();
				 * 
				 * } else {
				 * 
				 * System.err.println("Inside mrn qty before edit is greater than 0");
				 * remainingQty = poDetail.getPendingQty() -
				 * (mrnDetailRes.getMrnQty()-detail.getMrnQtyBeforeEdit());
				 * 
				 * }
				 * 
				 * poDetail.setPendingQty(remainingQty); int status = 1;
				 * 
				 * if (remainingQty <= 0) {
				 * System.err.println("Pending qty =0 keeping status=2"); status = 2; }
				 * 
				 * poDetail.setStatus(status); PoDetail poDetailStatusUpdate =
				 * poDetailRepo.save(poDetail);
				 * 
				 * List<Integer> stss = new ArrayList<Integer>(); stss.add(2); stss.add(9);
				 * stss.add(7); List<PoDetail> poDetailsList =
				 * poDetailRepo.findAllByStatusNotInAndPoId(stss,
				 * mrnDetailList.get(i).getPoId());
				 * 
				 * if (poDetailsList.isEmpty()) {
				 * 
				 * System.err.
				 * println("Po Detail list is Empty so Update po Header Status for POId " +
				 * mrnDetailList.get(i).getPoId());
				 * 
				 * int updatePoHeaderStatus = poHeaderRepository.updateResponsePoHead(2,
				 * mrnDetailList.get(i).getPoId());
				 * 
				 * } else { List<Integer> sts = new ArrayList<Integer>(); sts.add(0);
				 * sts.add(1); List<PoDetail>
				 * details=poDetailRepo.findAllByPoIdAndStatusNotIn(mrnDetailList.get(i).getPoId
				 * (),sts); poDetailsList=new ArrayList<PoDetail>();
				 * 
				 * poDetailsList = poDetailRepo.findAllByStatusAndPoId(1,
				 * mrnDetailList.get(i).getPoId());
				 * 
				 * if(poDetailsList.isEmpty()) {
				 * 
				 * int updatePoHeaderStatus = poHeaderRepository.updateResponsePoHead(0,
				 * mrnDetailList.get(i).getPoId());
				 * 
				 * } else { int updatePoHeaderStatus =
				 * poHeaderRepository.updateResponsePoHead(1, mrnDetailList.get(i).getPoId()); }
				 * }
				 * 
				 * }
				 */

			}

		} catch (Exception e) {

			System.err.println("Exception in saving Mrn Header and Detail  " + e.getMessage());
			e.printStackTrace();

		}

		return res;
	}
	
	
	@RequestMapping(value = { "/updateMrnHeadStatus" }, method = RequestMethod.POST)
	public @ResponseBody ErrorMessage updateMrnDetailList(@RequestParam int mrnId, @RequestParam int status) {
		 
		ErrorMessage res = new ErrorMessage();

		try {				
				int updateheaderStatus = officeMrnHeadRepo.updateheaderStatus(mrnId, status);
				if(updateheaderStatus>0) {
					res.setMessage("Success");
					res.setError(false);
				}else {
					res.setMessage("Fail");
					res.setError(true);
				}
			}catch (Exception e) {
				// TODO: handle exception
			}
		return res;
	}
	
	
	
	@RequestMapping(value = { "/getOfficeMrnDtlByHeadId" }, method = RequestMethod.POST)
	public @ResponseBody List<OfficeMrnDetail> getOfficeMrnDtlyHeadId(@RequestParam int ofcMrnHeadId) {
		 
		 List<OfficeMrnDetail> res = new ArrayList<OfficeMrnDetail>();

		try {				
			res = officMrnDtlRepo.getMrnDtlByHeadId(ofcMrnHeadId);
				
			}catch (Exception e) {
				// TODO: handle exception
			}
		return res;
	}
	
	
	@RequestMapping(value = { "/getMrnHeaderList" }, method = RequestMethod.POST)
	public @ResponseBody List<GetMrnHeader> getMrnHeaderList(@RequestParam("venId") List<Integer> venId,
			@RequestParam("status") List<Integer> status) {

		List<GetMrnHeader> mrnHeaderList = new ArrayList<GetMrnHeader>();

		try {
			if (venId.contains(0) && !status.contains(-1)) {
				mrnHeaderList = getMrnHeaderRepository.getMrnHeaderListByStatus(status);
				System.err.println("a");
			} else if (!venId.contains(0) && status.contains(-1)) {
				System.err.println("b");
				mrnHeaderList = getMrnHeaderRepository.getMrnHeaderListByVenId(venId);

			} else if (!venId.contains(0) && !status.contains(-1)) {
				System.err.println("c");
				mrnHeaderList = getMrnHeaderRepository.getMrnHeaderListByVenIdAndStatus(venId, status);
			} else if (venId.contains(0) && status.contains(-1)) {
				System.err.println("d");
				mrnHeaderList = getMrnHeaderRepository.getAllMrnHeaderList();
			}
			if (mrnHeaderList.size() > 0) {
				for (int i = 0; i < mrnHeaderList.size(); i++) {
					List<GetMrnDetail> getMrnDetailList = getMrnDetailRepository
							.getMrnDetailList(mrnHeaderList.get(i).getMrnId(), 0);

					mrnHeaderList.get(i).setGetMrnDetailList(getMrnDetailList);
				}
			}
		} catch (Exception e) {

			System.err.println("Exception in getIndents Indent  " + e.getMessage());

			e.printStackTrace();

		}

		return mrnHeaderList;

	}

	@RequestMapping(value = { "/getMrnHeader" }, method = RequestMethod.POST)
	public @ResponseBody GetMrnHeader getMrnHeader(@RequestParam("mrnId") int mrnId) {

		GetMrnHeader mrnHeader = new GetMrnHeader();

		try {
			mrnHeader = getMrnHeaderRepository.findByMrnId(mrnId);
			if (mrnHeader != null) {

				List<GetMrnDetail> getMrnDetailList = getMrnDetailRepository.getMrnDetailList(mrnId, 0);

				mrnHeader.setGetMrnDetailList(getMrnDetailList);

			}
		} catch (Exception e) {

			System.err.println("Exception in getIndents Indent  " + e.getMessage());

			e.printStackTrace();

		}

		return mrnHeader;

	}
	
	@RequestMapping(value = { "/getMrnHeaderForApprove" }, method = RequestMethod.POST)
	public @ResponseBody GetMrnHeader getMrnHeaderForApprove(@RequestParam("mrnId") int mrnId) {

		GetMrnHeader mrnHeader = new GetMrnHeader();

		try {
			mrnHeader = getMrnHeaderRepository.findByMrnId(mrnId);
			if (mrnHeader != null) {

				List<GetMrnDetail> getMrnDetailList = getMrnDetailRepository.getMrnDetailListForApprove(mrnId);

				mrnHeader.setGetMrnDetailList(getMrnDetailList);

			}
		} catch (Exception e) {

			System.err.println("Exception in getIndents Indent  " + e.getMessage());

			e.printStackTrace();

		}

		return mrnHeader;

	}

	@RequestMapping(value = { "/saveMrnData" }, method = RequestMethod.POST)
	public @ResponseBody List<MrnDetail> saveMrnData(@RequestBody List<MrnDetail> getMrnDetailList) {
		List<MrnDetail> res = new ArrayList<>();

		try {

			int mrnId = getMrnDetailList.get(0).getMrnId();
			boolean flag = false;
			for (int i = 0; i < getMrnDetailList.size(); i++) {

				MrnDetail mrnDetail = new MrnDetail();
				mrnDetail.setMrnDetailId(getMrnDetailList.get(i).getMrnDetailId());
				mrnDetail.setApproveQty(getMrnDetailList.get(i).getApproveQty());
				mrnDetail.setBatchNo(getMrnDetailList.get(i).getBatchNo());
				mrnDetail.setDelStatus(getMrnDetailList.get(i).getDelStatus());
				mrnDetail.setIndentQty(getMrnDetailList.get(i).getIndentQty());
				mrnDetail.setIssueQty(getMrnDetailList.get(i).getIssueQty());
				mrnDetail.setItemId(getMrnDetailList.get(i).getItemId());
				mrnDetail.setMrnDetailStatus(getMrnDetailList.get(i).getMrnDetailStatus());
				mrnDetail.setMrnId(getMrnDetailList.get(i).getMrnId());
				mrnDetail.setMrnQty(getMrnDetailList.get(i).getMrnQty());
				mrnDetail.setPoId(getMrnDetailList.get(i).getPoId());
				mrnDetail.setPoNo(getMrnDetailList.get(i).getPoNo());
				mrnDetail.setPoQty(getMrnDetailList.get(i).getPoQty());
				mrnDetail.setRejectQty(getMrnDetailList.get(i).getRejectQty());
				mrnDetail.setRejectRemark(getMrnDetailList.get(i).getRejectRemark());
				mrnDetail.setRemainingQty(getMrnDetailList.get(i).getRemainingQty());
				mrnDetail.setMrnDetailStatus(1);
				mrnDetail.setPoDetailId(getMrnDetailList.get(i).getPoDetailId());
				mrnDetail.setChalanQty(getMrnDetailList.get(i).getChalanQty());
				mrnDetail.setMrnId(getMrnDetailList.get(i).getMrnId());
				MrnDetail transRes = mrnDetailRepo.save(mrnDetail);
				flag = true;
				res.add(transRes);
			}
			if (flag == true) {
				int isUpdated = mrnHeaderRepository.updateMrnStatusAsPartial(mrnId);
			}
			int count = mrnDetailRepo.getDetailCount(mrnId);
			System.err.println(count);
			if (count == 0) {
				int isUpdated = mrnHeaderRepository.updateMrnStatus(mrnId);
			}

		} catch (Exception e) {

			System.err.println("Exception in saving Mrn Header and Detail  " + e.getMessage());
			e.printStackTrace();

		}

		return res;
	}

	@RequestMapping(value = { "/getMrnHeaderByDate" }, method = RequestMethod.POST)
	public @ResponseBody List<GetMrnHeader> getMrnHeaderByDate(@RequestParam("grnType")int grnType,@RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate) {

		List<GetMrnHeader> mrnHeaderList = new ArrayList<GetMrnHeader>();

		try {
			
			if(grnType==-1) {
				System.err.println("Grn type ==-1 on page load call");
				
				mrnHeaderList = getMrnHeaderRepository.getMrnHeaderByDate(fromDate, toDate);

			}else {
			mrnHeaderList = getMrnHeaderRepository.getMrnHeaderByDate(grnType,fromDate, toDate);
			}
			System.err.println("mrn Head List by Date =  " + mrnHeaderList.toString());

		} catch (Exception e) {

			System.err.println("Exception in getMrnHeaderByDate Mrn  " + e.getMessage());

			e.printStackTrace();

		}

		return mrnHeaderList;

	}
	
	@RequestMapping(value = { "/getOfficeMrnHeaderByDate" }, method = RequestMethod.POST)
	public @ResponseBody List<GetMrnHeader> getOfficeMrnHeaderByDate(@RequestParam("grnType")int grnType,@RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate) {

		List<GetMrnHeader> mrnHeaderList = new ArrayList<GetMrnHeader>();

		try {
			
			if(grnType==-1) {
				System.err.println("Grn type ==-1 on page load call");
				
				mrnHeaderList = getMrnHeaderRepository.getOfficeMrnHeaderByDate(fromDate, toDate);

			}else {
			mrnHeaderList = getMrnHeaderRepository.getOfficeMrnHeaderByDate(grnType,fromDate, toDate);
			}
			System.err.println("mrn Head List by Date =  " + mrnHeaderList.toString());

		} catch (Exception e) {

			System.err.println("Exception in getMrnHeaderByDate Mrn  " + e.getMessage());

			e.printStackTrace();

		}

		return mrnHeaderList;

	}

	@Autowired
	MrnReportRepo mrnReportrRepo;

	@RequestMapping(value = { "/getMrnHeadReport" }, method = RequestMethod.POST)
	public @ResponseBody List<MrnReport> getMrnHeadReport(@RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate, @RequestParam("grnTypeList") List<String> grnTypeList,
			@RequestParam("vendorIdList") List<String> vendorIdList,
			@RequestParam("statusList") List<String> statusList) {

		List<MrnReport> mrnHeaderList = new ArrayList<MrnReport>();

		try {
			System.err.println("Input getMrnHeadReport  grnTypeList " + grnTypeList + "vendorIdList " + vendorIdList
					+ "statusList " + statusList);

			if (vendorIdList.contains("-1")) {
				System.err.println("Vendor Id List Contain -1");

			}

			if (grnTypeList.contains("-1")) {
				System.err.println("grnTypeList  List Contain -1");

			}

			if (statusList.contains("-1")) {
				System.err.println("status   List Contain -1");
			}

			if (vendorIdList.contains("-1")) {

				System.err.println("All Vendor Selected");
				mrnHeaderList = mrnReportrRepo.getMrnReportAllVendor(fromDate, toDate, grnTypeList, statusList);

			}

			else {
				System.err.println("few Vendor Selected ");
				mrnHeaderList = mrnReportrRepo.getMrnReport(vendorIdList, grnTypeList, statusList, fromDate, toDate);

			}

			System.err.println("mrn Head List Report  Size=  " + mrnHeaderList.size());

			System.err.println("mrn Head List Report =  " + mrnHeaderList.toString());

		} catch (Exception e) {

			System.err.println("Exception in getMrnHeaderReport Mrn  " + e.getMessage());

			e.printStackTrace();

		}

		return mrnHeaderList;

	}

	@RequestMapping(value = { "/getMrnDetailByMrnId" }, method = RequestMethod.POST)
	public @ResponseBody List<GetMrnDetail> getMrnDetailByMrnId(@RequestParam("mrnId") int mrnId) {

		List<GetMrnDetail> mrnDetailList = new ArrayList<GetMrnDetail>();

		try {
			mrnDetailList = getMrnDetailRepository.getMrnDetailByMrnId(mrnId);
			System.err.println("mrn mrnDetailList List by MrnId =  " + mrnDetailList.toString());

		} catch (Exception e) {

			System.err.println("Exception in getMrnDetailByMrnId Mrn  " + e.getMessage());

			e.printStackTrace();

		}

		return mrnDetailList;

	}

	@RequestMapping(value = { "/getMrnList" }, method = RequestMethod.GET)
	public @ResponseBody List<MrnHeader> getMrnList() {

		List<MrnHeader> mrnHeaderList = new ArrayList<MrnHeader>();

		try {

			mrnHeaderList = mrnHeaderRepository.findAllByDelStatus(1);

		} catch (Exception e) {

			System.err.println("Exception in getIndents Indent  " + e.getMessage());

			e.printStackTrace();

		}

		return mrnHeaderList;

	}
	
	@RequestMapping(value = { "/getMrnListByVendorIdForRejectionMemo" }, method = RequestMethod.POST)
	public @ResponseBody List<MrnHeader> getMrnListByVendorIdForRejectionMemo(@RequestParam("vendId") int vendId) {

		List<MrnHeader> mrnHeaderList = new ArrayList<MrnHeader>();

		try {

			mrnHeaderList = mrnHeaderRepository.getMrnListByVendorIdForRejectionMemo(vendId);

		} catch (Exception e) {

			System.err.println("Exception in getIndents Indent  " + e.getMessage());

			e.printStackTrace();

		}

		return mrnHeaderList;

	}

	// Neha
	@RequestMapping(value = { "/getMrnHeaderDetail" }, method = RequestMethod.POST)
	public @ResponseBody List<GetMrnHeaderRej> getMrnHeaderDetail(@RequestParam("status") List<Integer> status) {

		List<GetMrnHeaderRej> mrnHeaderList = new ArrayList<GetMrnHeaderRej>();

		try {

			mrnHeaderList = getMrnHeaderRejRepo.getMrnHeaderByList(status);
			//List<GetMrnDetailRej> getMrnDetailList = getMrnDetailRejRepo.getMrnDetailByList(status);

			for (int i = 0; i < mrnHeaderList.size(); i++) {
 
				int mrnId = mrnHeaderList.get(i).getMrnId();
				
				List<GetMrnDetailRej> getMrnDetailList = getMrnDetailRejRepo.getMrnDetailByList(mrnId);

				mrnHeaderList.get(i).setGetMrnDetailRejList(getMrnDetailList);

			}

		} catch (Exception e) {

			e.printStackTrace();

		}
		return mrnHeaderList;

	}
	
	@RequestMapping(value = { "/getMrnListByVendorIdForRejectionMemoForPune" }, method = RequestMethod.POST)
	public @ResponseBody List<MrnHeader> getMrnListByVendorIdForRejectionMemo(@RequestParam("vendId") int vendId,@RequestParam("itemId") int itemId) {

		List<MrnHeader> mrnHeaderList = new ArrayList<MrnHeader>();

		try {

			mrnHeaderList = mrnHeaderRepository.getMrnListByVendorIdForRejectionMemo(vendId,itemId);

		} catch (Exception e) {

			System.err.println("Exception in getIndents Indent  " + e.getMessage());

			e.printStackTrace();

		}

		return mrnHeaderList;

	}

	//akshay
	@RequestMapping(value = { "/getMrnHeaderDetailForPune" }, method = RequestMethod.POST)
	public @ResponseBody List<GetMrnHeaderRej> getMrnHeaderDetail(@RequestParam("status") List<Integer> mrnIds,@RequestParam("itemId") int itemId) {

		List<GetMrnHeaderRej> mrnHeaderList = new ArrayList<GetMrnHeaderRej>();

		try {

			mrnHeaderList = getMrnHeaderRejRepo.getMrnHeaderByList(mrnIds);
			//List<GetMrnDetailRej> getMrnDetailList = getMrnDetailRejRepo.getMrnDetailByList(status);

			for (int i = 0; i < mrnHeaderList.size(); i++) {
 
				int mrnId = mrnHeaderList.get(i).getMrnId();
				
				List<GetMrnDetailRej> getMrnDetailList = getMrnDetailRejRepo.getMrnDetailByList(mrnId,itemId);

				mrnHeaderList.get(i).setGetMrnDetailRejList(getMrnDetailList);

			}

		} catch (Exception e) {

			e.printStackTrace();

		}
		return mrnHeaderList;

	}

	@RequestMapping(value = { "/deleteMrnHeader" }, method = RequestMethod.POST)
	public @ResponseBody ErrorMessage deleteMrnHeader(@RequestParam("mrnId") int mrnId) {
		System.err.println("inside web api  deleteMrnHeader");
		ErrorMessage errMsg = new ErrorMessage();

		try {

			int delRes = mrnHeaderRepository.deleteMrnHeader(mrnId);
			List<MrnDetail> mrnDetailList = mrnDetailRepo.findByMrnIdAndDelStatus(mrnId,1);
			
			System.err.println("Mrn detail in deleteMrnHeader " +mrnDetailList.toString() );
			

			for (int i = 0; i < mrnDetailList.size(); i++) {

				PoDetail poDetail = poDetailRepo.findByPoDetailId(mrnDetailList.get(i).getPoDetailId());
				//

				float remainingQty = 0;

				remainingQty = poDetail.getPendingQty() + mrnDetailList.get(i).getMrnQty();

				poDetail.setPendingQty(remainingQty);
				
				int status = 1;

				if (remainingQty <= 0) {
					System.err.println("Pending qty =0 keeping status=2");
					status = 2;
				}else  if(remainingQty==poDetail.getItemQty()){
					
					status=0;
				}
 
				poDetail.setStatus(status);
				PoDetail poDetailStatusUpdate = poDetailRepo.save(poDetail);
				List<Integer> stss = new ArrayList<Integer>();
				stss.add(2);
				stss.add(9);
				stss.add(7);
				List<PoDetail> poDetailsList = poDetailRepo.findAllByStatusNotInAndPoId(stss, mrnDetailList.get(i).getPoId());

				if (poDetailsList.isEmpty()) {

					System.err.println("Po Detail list is Empty so Update po Header Status for POId "
							+ mrnDetailList.get(i).getPoId());

					int updatePoHeaderStatus = poHeaderRepository.updateResponsePoHead(2, mrnDetailList.get(i).getPoId());

				}
				else {
					List<Integer> sts = new ArrayList<Integer>();
					sts.add(0);
					sts.add(1);
						List<PoDetail> details=poDetailRepo.findAllByPoIdAndStatusNotIn(mrnDetailList.get(i).getPoId(),sts);
						poDetailsList=new ArrayList<PoDetail>();
						
						poDetailsList = poDetailRepo.findAllByStatusAndPoId(1, mrnDetailList.get(i).getPoId());
	
					if(poDetailsList.isEmpty()) {
	
						int updatePoHeaderStatus = poHeaderRepository.updateResponsePoHead(0, mrnDetailList.get(i).getPoId());
	
					}
					else {
						int updatePoHeaderStatus = poHeaderRepository.updateResponsePoHead(1, mrnDetailList.get(i).getPoId());
					}
				}
				
					

				//

			}

			if (delRes > 0) {
				errMsg.setError(false);
				errMsg.setMessage("Mrn Header deleted Successfull");
			} else {
				errMsg.setError(true);
				errMsg.setMessage("Mrn Header delete failed ");
			}

		} catch (Exception e) {

			System.err.println("Exception in deleteMrnHeader  " + e.getMessage());
			e.printStackTrace();

		}

		return errMsg;
	}

	@RequestMapping(value = { "/deleteMrnDetail" }, method = RequestMethod.POST)
	public @ResponseBody ErrorMessage deleteMrnDetail(@RequestParam("mrnDetailId") int mrnDetailId) {
		System.err.println("inside web api  deleteMrnDetail");
		ErrorMessage errMsg = new ErrorMessage();

		try {

			int delRes = mrnDetailRepo.deleteMrnDetail(mrnDetailId);

			
			MrnDetail mrnDetailRes=mrnDetailRepo.findByMrnDetailIdAndDelStatus(mrnDetailId,0);
			
			
			//
			
			//MrnDetail mrnDetailRes = mrnDetailRepo.save(detail);

			PoDetail poDetail = poDetailRepo.findByPoDetailId(mrnDetailRes.getPoDetailId());

			if (mrnDetailRes != null) {
				float remainingQty = 0;
					System.err.println("Inside mrn qty before edit is greater than 0");
					remainingQty = poDetail.getPendingQty()
							+ mrnDetailRes.getMrnQty();

				poDetail.setPendingQty(remainingQty);
				int status = 1;

				if (remainingQty <= 0) {
					System.err.println("Pending qty =0 keeping status=2");
					status = 2;
				}else  if(remainingQty==poDetail.getItemQty()){
					
					status=0;
				}

				poDetail.setStatus(status);
				PoDetail poDetailStatusUpdate = poDetailRepo.save(poDetail);
				 
				List<Integer> stss = new ArrayList<Integer>();
				stss.add(2);
				stss.add(9);
				stss.add(7);
				List<PoDetail> poDetailsList = poDetailRepo.findAllByStatusNotInAndPoId(stss, mrnDetailRes.getPoId());

				if (poDetailsList.isEmpty()) {

					System.err.println("Po Detail list is Empty so Update po Header Status for POId "
							+ mrnDetailRes.getPoId());

					int updatePoHeaderStatus = poHeaderRepository.updateResponsePoHead(2, mrnDetailRes.getPoId());

				}
				else {
					List<Integer> sts = new ArrayList<Integer>();
					sts.add(0);
					sts.add(1);
						List<PoDetail> details=poDetailRepo.findAllByPoIdAndStatusNotIn(mrnDetailRes.getPoId(),sts);
						poDetailsList=new ArrayList<PoDetail>();
						
						poDetailsList = poDetailRepo.findAllByStatusAndPoId(1, mrnDetailRes.getPoId());
	
					if(poDetailsList.isEmpty()) {
	
						int updatePoHeaderStatus = poHeaderRepository.updateResponsePoHead(0, mrnDetailRes.getPoId());
	
					}
					else {
						int updatePoHeaderStatus = poHeaderRepository.updateResponsePoHead(1, mrnDetailRes.getPoId());
					}
				}

			}
			
			//
			
			if (delRes > 0) {
				errMsg.setError(false);
				errMsg.setMessage("Mrn Detail deleted Successfull");
			} else {
				errMsg.setError(true);
				errMsg.setMessage("Mrn Detail delete failed ");
			}

		} catch (Exception e) {

			System.err.println("Exception in deleteMrnHeader  " + e.getMessage());
			e.printStackTrace();

		}

		return errMsg;
	}
	
	@RequestMapping(value = { "/updateStatusWhileMrnApprov" }, method = RequestMethod.POST)
	public @ResponseBody ErrorMessage updateStatusWhileMrnApprov(@RequestParam("mrnId") int mrnId,
			@RequestParam("mrnDetalId") List<Integer> mrnDetalId,@RequestParam("status") int status) {

		ErrorMessage errorMessage = new ErrorMessage();

		try {

			int update = mrnHeaderRepository.updateStatusWhileApprov(mrnId,status); 
			
			/*for(int i=0 ; i<poDetalId.size() : i++)
			{
				
			}*/
			int updateDetail = mrnDetailRepo.updateStatusWhileApprov(mrnDetalId,status);
			
			errorMessage.setError(false);
			errorMessage.setMessage("approved");
			 
		} catch (Exception e) {

			e.printStackTrace();
			errorMessage.setError(true);
			errorMessage.setMessage("failed");

		}
		return errorMessage;

	}
	
	@RequestMapping(value = { "/getDeptAndSubDeptFromIndentByPoId" }, method = RequestMethod.POST)
	public @ResponseBody ErrorMessage updateStatusWhileMrnApprov(@RequestParam("poId") int poId) {

		ErrorMessage errorMessage = new ErrorMessage();

		try {

			String deptId = mrnHeaderRepository.getDeptId(poId); 
			 
			String suDeptId = mrnHeaderRepository.getSubDept(poId);
			
			String accHead = mrnHeaderRepository.getAccHead(poId);
			
			errorMessage.setError(false);
			errorMessage.setMessage(deptId+","+suDeptId+","+accHead);
			 
		} catch (Exception e) {

			e.printStackTrace();
			errorMessage.setError(true);
			errorMessage.setMessage("failed");

		}
		return errorMessage;

	}
	
	@RequestMapping(value = { "/saveOfficeMrnHeadAndDetail" }, method = RequestMethod.POST)
	public @ResponseBody OfficeMrnHeader saveOfficeMrnHeadAndDetail(@RequestBody OfficeMrnHeader mrnHeader) {
		System.err.println("inside web api save saveMrnHeadAndDetail");
		OfficeMrnHeader res = new OfficeMrnHeader();

		try {

			res = officeMrnHeadRepo.saveAndFlush(mrnHeader);

			List<OfficeMrnDetail> mrnDetailList = mrnHeader.getMrnDetailList();

			int mrnId = res.getMrnId();
			String mrnNo = res.getMrnNo();
			String batchNo;

			for (int i = 0; i < mrnDetailList.size(); i++) {

				OfficeMrnDetail detail = mrnDetailList.get(i);

				GetItem item = getItemRepository.getItemByItemId(detail.getItemId());
				batchNo = new String();
				batchNo = mrnNo + "-" + item.getItemCode();

				detail.setBatchNo(batchNo);

				detail.setMrnId(mrnId);
				OfficeMrnDetail mrnDetailRes = officMrnDtlRepo.save(detail);

				PoDetail poDetail = poDetailRepo.findByPoDetailId(mrnDetailRes.getPoDetailId());

				if (mrnDetailRes != null) {
					float remainingQty = 0;

					if (detail.getMrnQtyBeforeEdit() == -1) {

						System.err.println("Inside mrn qty before Edit ==-1");

						remainingQty = poDetail.getPendingQty() - mrnDetailRes.getMrnQty();

					} else {

						System.err.println("Inside mrn qty before edit is greater than 0");
						remainingQty = poDetail.getPendingQty()
								- (mrnDetailRes.getMrnQty()-detail.getMrnQtyBeforeEdit());

					}

					poDetail.setPendingQty(remainingQty);
					int status = 1;

					if (remainingQty <= 0) {
						System.err.println("Pending qty =0 keeping status=2");
						status = 2;
					}

					poDetail.setStatus(status);
					PoDetail poDetailStatusUpdate = poDetailRepo.save(poDetail);
					 
					List<Integer> stss = new ArrayList<Integer>();
					stss.add(2);
					stss.add(9);
					stss.add(7);
					List<PoDetail> poDetailsList = poDetailRepo.findAllByStatusNotInAndPoId(stss, mrnDetailList.get(i).getPoId());

					if (poDetailsList.isEmpty()) {

						System.err.println("Po Detail list is Empty so Update po Header Status for POId "
								+ mrnDetailList.get(i).getPoId());

						int updatePoHeaderStatus = poHeaderRepository.updateResponsePoHead(2, mrnDetailList.get(i).getPoId());

					}
					else {
						List<Integer> sts = new ArrayList<Integer>();
						sts.add(0);
						sts.add(1);
							List<PoDetail> details=poDetailRepo.findAllByPoIdAndStatusNotIn(mrnDetailList.get(i).getPoId(),sts);
							poDetailsList=new ArrayList<PoDetail>();
							
							poDetailsList = poDetailRepo.findAllByStatusAndPoId(1, mrnDetailList.get(i).getPoId());
		
						if(poDetailsList.isEmpty()) {
		
							int updatePoHeaderStatus = poHeaderRepository.updateResponsePoHead(0, mrnDetailList.get(i).getPoId());
		
						}
						else {
							int updatePoHeaderStatus = poHeaderRepository.updateResponsePoHead(1, mrnDetailList.get(i).getPoId());
						}
					}

				}

			}

		} catch (Exception e) {

			System.err.println("Exception in saving Office Mrn Header and Detail  " + e.getMessage());
			e.printStackTrace();

		}

		return res;
	}

	@RequestMapping(value = { "/getOfficeMrnDetailByMrnId" }, method = RequestMethod.POST)
	public @ResponseBody List<GetMrnDetail> getOfficeMrnDetailByMrnId(@RequestParam("mrnId") int mrnId) {

		List<GetMrnDetail> mrnDetailList = new ArrayList<GetMrnDetail>();

		try {
			mrnDetailList = getMrnDetailRepository.getOfficeMrnDetailByMrnId(mrnId);
			System.err.println("mrn mrnDetailList List by MrnId =  " + mrnDetailList.toString());

		} catch (Exception e) {

			System.err.println("Exception in getMrnDetailByMrnId Mrn  " + e.getMessage());

			e.printStackTrace();

		}

		return mrnDetailList;

	}
	
	@RequestMapping(value = { "/deleteOfficeMrnHeader" }, method = RequestMethod.POST)
	public @ResponseBody ErrorMessage deleteOfficeMrnHeader(@RequestParam("mrnId") int mrnId) {
		System.err.println("inside web api  deleteOfficeMrnHeader");
		ErrorMessage errMsg = new ErrorMessage();

		try {

			int delRes = officeMrnHeadRepo.deleteMrnHeader(mrnId);
			List<OfficeMrnDetail> mrnDetailList = officMrnDtlRepo.findByMrnIdAndDelStatus(mrnId,1);
			
			System.err.println("Mrn detail in deleteMrnHeader " +mrnDetailList.toString() );
			

			for (int i = 0; i < mrnDetailList.size(); i++) {

				PoDetail poDetail = poDetailRepo.findByPoDetailId(mrnDetailList.get(i).getPoDetailId());
				//

				float remainingQty = 0;

				remainingQty = poDetail.getPendingQty() + mrnDetailList.get(i).getMrnQty();

				poDetail.setPendingQty(remainingQty);
				
				int status = 1;

				if (remainingQty <= 0) {
					System.err.println("Pending qty =0 keeping status=2");
					status = 2;
				}else  if(remainingQty==poDetail.getItemQty()){
					
					status=0;
				}
 
				poDetail.setStatus(status);
				PoDetail poDetailStatusUpdate = poDetailRepo.save(poDetail);
				List<Integer> stss = new ArrayList<Integer>();
				stss.add(2);
				stss.add(9);
				stss.add(7);
				List<PoDetail> poDetailsList = poDetailRepo.findAllByStatusNotInAndPoId(stss, mrnDetailList.get(i).getPoId());

				if (poDetailsList.isEmpty()) {

					System.err.println("Po Detail list is Empty so Update po Header Status for POId "
							+ mrnDetailList.get(i).getPoId());

					int updatePoHeaderStatus = poHeaderRepository.updateResponsePoHead(2, mrnDetailList.get(i).getPoId());

				}
				else {
					List<Integer> sts = new ArrayList<Integer>();
					sts.add(0);
					sts.add(1);
						List<PoDetail> details=poDetailRepo.findAllByPoIdAndStatusNotIn(mrnDetailList.get(i).getPoId(),sts);
						poDetailsList=new ArrayList<PoDetail>();
						
						poDetailsList = poDetailRepo.findAllByStatusAndPoId(1, mrnDetailList.get(i).getPoId());
	
					if(poDetailsList.isEmpty()) {
	
						int updatePoHeaderStatus = poHeaderRepository.updateResponsePoHead(0, mrnDetailList.get(i).getPoId());
	
					}
					else {
						int updatePoHeaderStatus = poHeaderRepository.updateResponsePoHead(1, mrnDetailList.get(i).getPoId());
					}
				}
				
					

				//

			}

			if (delRes > 0) {
				errMsg.setError(false);
				errMsg.setMessage("Mrn Header deleted Successfull");
			} else {
				errMsg.setError(true);
				errMsg.setMessage("Mrn Header delete failed ");
			}

		} catch (Exception e) {

			System.err.println("Exception in deleteMrnHeader  " + e.getMessage());
			e.printStackTrace();

		}

		return errMsg;
	}
}
