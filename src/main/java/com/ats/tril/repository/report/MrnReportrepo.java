package com.ats.tril.repository.report;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.tril.model.report.MrnReport;

public interface MrnReportrepo extends JpaRepository<MrnReport, Integer> {

	@Query(value = "SELECT h.*,v.vendor_name,v.vendor_code,v.vendor_add1 FROM t_mrn_header h,m_vendor v  WHERE h.del_status=1 AND h.mrn_id IN(:mrnIdList) AND h.vendor_id=v.vendor_id", nativeQuery = true)
	List<MrnReport> getHeaderMrnReportList(@Param("mrnIdList") List<Integer> mrnIdList);

	@Query(value = "SELECT h.*,v.vendor_name,v.vendor_code,v.vendor_add1 FROM t_office_mrn_header h,m_vendor v  WHERE h.del_status=1 AND h.mrn_id IN(:mrnIdList) AND h.vendor_id=v.vendor_id", nativeQuery = true)
	List<MrnReport> getOfficeHeaderMrnReportList(@Param("mrnIdList") List<Integer> mrnIdList);

}
