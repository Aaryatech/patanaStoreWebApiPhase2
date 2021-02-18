package com.ats.tril.repository.doc;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ats.tril.model.doc.SubDocument;
@Repository
public interface SubDocumentRepository extends JpaRepository<SubDocument, Integer>{

	SubDocument findByMIdAndCatIdAndDelStatus(int id, int catId, int i);
	
	SubDocument findByMIdAndCatIdAndDelStatusAndDocId(int id, int catId, int i, int typeId);

}
