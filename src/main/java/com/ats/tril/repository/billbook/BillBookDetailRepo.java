package com.ats.tril.repository.billbook;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ats.tril.model.billbook.BillBookDetail;


public interface BillBookDetailRepo extends JpaRepository<BillBookDetail, Integer>{

	
	List<BillBookDetail> findAllByBillId(int id);
	
}
