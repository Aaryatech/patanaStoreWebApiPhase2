package com.ats.tril.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.ats.tril.model.UomConversion;

public interface UomConversionRepository extends JpaRepository<UomConversion, Integer>{
 
	UomConversion findByConvertId(int id);

	@Transactional
	@Modifying
	@Query("UPDATE UomConversion SET del_stuatus=0  WHERE convert_id=:id")
	int deleteUomConversion(int id);

	List<UomConversion> findAllByDelStuatus(int i);

}
