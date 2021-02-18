package com.ats.tril.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.tril.model.IssueAndMrnGroupWise;

public interface IssueAndMrnGroupWiseRepository extends JpaRepository<IssueAndMrnGroupWise, Integer>{

	
	@Query(value=("SELECT\r\n" + 
			"        m_item_group.grp_id,\r\n" + 
			"        concat(m_item_group.grp_code,\"  \",m_item_group.grp_desc) as grp_code , \r\n" + 
			"        coalesce((Select\r\n" + 
			"            SUM(t_mrn_detail.approve_qty)                     \r\n" + 
			"        FROM\r\n" + 
			"            t_mrn_detail,\r\n" + 
			"            t_mrn_header,\r\n" + 
			"            m_item                     \r\n" + 
			"        where\r\n" + 
			"            t_mrn_header.mrn_date between :fromDate and :toDate                             \r\n" + 
			"            AND t_mrn_header.mrn_id=t_mrn_detail.mrn_id                             \r\n" + 
			"            AND m_item.item_id=t_mrn_detail.item_id                             \r\n" + 
			"            and t_mrn_header.del_status=1                             \r\n" + 
			"            and t_mrn_detail.del_status=1               \r\n" + 
			"            and m_item.grp_id=m_item_group.grp_id AND t_mrn_detail.mrn_detail_status = 4),\r\n" + 
			"        0) AS approve_qty,\r\n" + 
			"        coalesce((Select\r\n" + 
			"            SUM(po_detail.item_rate*t_mrn_detail.approve_qty)                     \r\n" + 
			"        FROM\r\n" + 
			"            t_mrn_detail,\r\n" + 
			"            t_mrn_header,\r\n" + 
			"            po_detail,\r\n" + 
			"            m_item                      \r\n" + 
			"        where\r\n" + 
			"            t_mrn_header.mrn_date between :fromDate and :toDate                          \r\n" + 
			"            AND t_mrn_header.mrn_id=t_mrn_detail.mrn_id                             \r\n" + 
			"            AND m_item.item_id=t_mrn_detail.item_id                             \r\n" + 
			"            and t_mrn_header.del_status=1                             \r\n" + 
			"            and t_mrn_detail.del_status=1                             \r\n" + 
			"            and po_detail.po_detail_id=t_mrn_detail.po_detail_id               \r\n" + 
			"            and m_item.grp_id=m_item_group.grp_id AND t_mrn_detail.mrn_detail_status = 4),\r\n" + 
			"        0) AS approved_qty_value,\r\n" + 
			"        coalesce((Select\r\n" + 
			"            SUM((po_detail.landing_cost/po_detail.item_qty)*t_mrn_detail.approve_qty)                     \r\n" + 
			"        FROM\r\n" + 
			"            t_mrn_detail,\r\n" + 
			"            t_mrn_header,\r\n" + 
			"            po_detail,\r\n" + 
			"            m_item                       \r\n" + 
			"        where\r\n" + 
			"            t_mrn_header.mrn_date between :fromDate and :toDate                            \r\n" + 
			"            AND t_mrn_header.mrn_id=t_mrn_detail.mrn_id                             \r\n" + 
			"            AND m_item.item_id=t_mrn_detail.item_id                             \r\n" + 
			"            and t_mrn_header.del_status=1                             \r\n" + 
			"            and t_mrn_detail.del_status=1                             \r\n" + 
			"            and po_detail.po_detail_id=t_mrn_detail.po_detail_id               \r\n" + 
			"            and m_item.grp_id=m_item_group.grp_id AND t_mrn_detail.mrn_detail_status = 4),\r\n" + 
			"        0) AS approved_landing_value,\r\n" + 
			"        coalesce((Select\r\n" + 
			"            SUM(item_issue_detail.item_issue_qty)                     \r\n" + 
			"        FROM\r\n" + 
			"            item_issue_header,\r\n" + 
			"            item_issue_detail,\r\n" + 
			"            m_item                        \r\n" + 
			"        WHERE\r\n" + 
			"            item_issue_header.issue_date between :fromDate and :toDate                            \r\n" + 
			"            AND item_issue_header.issue_id=item_issue_detail.issue_id                             \r\n" + 
			"            AND m_item.item_id=item_issue_detail.item_id                             \r\n" + 
			"            and item_issue_header.delete_status=1                             \r\n" + 
			"            and item_issue_detail.del_status=1               \r\n" + 
			"            and m_item.grp_id=m_item_group.grp_id AND item_issue_detail.status = 2),\r\n" + 
			"        0) AS issue_qty,\r\n" + 
			"        coalesce((Select\r\n" + 
			"            SUM(item_issue_detail.item_issue_qty*po_detail.item_rate)                     \r\n" + 
			"        FROM\r\n" + 
			"            item_issue_header,\r\n" + 
			"            item_issue_detail,\r\n" + 
			"            t_mrn_detail,\r\n" + 
			"            po_detail,\r\n" + 
			"            m_item                        \r\n" + 
			"        WHERE\r\n" + 
			"            item_issue_header.issue_date between :fromDate and :toDate                             \r\n" + 
			"            AND item_issue_header.issue_id=item_issue_detail.issue_id                             \r\n" + 
			"            AND m_item.item_id=item_issue_detail.item_id                             \r\n" + 
			"            and item_issue_header.delete_status=1                             \r\n" + 
			"            and item_issue_detail.del_status=1                             \r\n" + 
			"            and item_issue_detail.mrn_detail_id=t_mrn_detail.mrn_detail_id                             \r\n" + 
			"            and po_detail.po_detail_id=t_mrn_detail.po_detail_id               \r\n" + 
			"            and m_item.grp_id=m_item_group.grp_id AND item_issue_detail.status = 2),\r\n" + 
			"        0) AS issue_qty_value,\r\n" + 
			"        coalesce((Select\r\n" + 
			"            SUM((po_detail.landing_cost/po_detail.item_qty)*item_issue_detail.item_issue_qty)                     \r\n" + 
			"        FROM\r\n" + 
			"            item_issue_header,\r\n" + 
			"            item_issue_detail,\r\n" + 
			"            t_mrn_detail,\r\n" + 
			"            po_detail,\r\n" + 
			"            m_item                        \r\n" + 
			"        WHERE\r\n" + 
			"            item_issue_header.issue_date between :fromDate and :toDate                             \r\n" + 
			"            AND item_issue_header.issue_id=item_issue_detail.issue_id                             \r\n" + 
			"            AND m_item.item_id=item_issue_detail.item_id                             \r\n" + 
			"            and item_issue_header.delete_status=1                             \r\n" + 
			"            and item_issue_detail.del_status=1                             \r\n" + 
			"            and item_issue_detail.mrn_detail_id=t_mrn_detail.mrn_detail_id                             \r\n" + 
			"            and po_detail.po_detail_id=t_mrn_detail.po_detail_id               \r\n" + 
			"            and m_item.grp_id=m_item_group.grp_id AND item_issue_detail.status = 2),\r\n" + 
			"        0) AS issue_landing_value               \r\n" + 
			"    FROM\r\n" + 
			"        m_item_group             \r\n" + 
			"    where\r\n" + 
			"        m_item_group.is_used=1\r\n" + 
			"        and m_item_group.cat_id=:catId"),nativeQuery=true)
	List<IssueAndMrnGroupWise> issueAndMrnGroupWisReportByCatId(@Param("fromDate")String fromDate,@Param("toDate") String toDate,
			@Param("catId") int catId);

	
	@Query(value=("SELECT\r\n" + 
			"         m_item_group.grp_id,\r\n" + 
			"        concat(m_item_group.grp_code,\"  \",m_item_group.grp_desc) as grp_code , \r\n" + 
			"        coalesce((Select\r\n" + 
			"            SUM(t_mrn_detail.approve_qty)                     \r\n" + 
			"        FROM\r\n" + 
			"            t_mrn_detail,\r\n" + 
			"            t_mrn_header,\r\n" + 
			"            po_header,\r\n" + 
			"            indent,\r\n" + 
			"            m_item                     \r\n" + 
			"        where\r\n" + 
			"            t_mrn_header.mrn_date between :fromDate and :toDate                            \r\n" + 
			"            AND t_mrn_header.mrn_id=t_mrn_detail.mrn_id                             \r\n" + 
			"            AND m_item.item_id=t_mrn_detail.item_id                             \r\n" + 
			"            and t_mrn_header.del_status=1                             \r\n" + 
			"            and t_mrn_detail.del_status=1               \r\n" + 
			"            and m_item.grp_id=m_item_group.grp_id              \r\n" + 
			"            and t_mrn_header.mrn_type=:typeId \r\n" + 
			"            and t_mrn_detail.po_id=po_header.po_id \r\n" + 
			"            and po_header.ind_id=indent.ind_m_id              \r\n" + 
			"            and indent.ind_isdev=:isDev AND t_mrn_detail.mrn_detail_status = 4),\r\n" + 
			"        0) AS approve_qty,\r\n" + 
			"        coalesce((Select\r\n" + 
			"            SUM(po_detail.item_rate*t_mrn_detail.approve_qty)                     \r\n" + 
			"        FROM\r\n" + 
			"            t_mrn_detail,\r\n" + 
			"            t_mrn_header,\r\n" + 
			"            po_detail,\r\n" + 
			"            po_header,\r\n" + 
			"            indent,\r\n" + 
			"            m_item                      \r\n" + 
			"        where\r\n" + 
			"            t_mrn_header.mrn_date between :fromDate and :toDate                            \r\n" + 
			"            AND t_mrn_header.mrn_id=t_mrn_detail.mrn_id                             \r\n" + 
			"            AND m_item.item_id=t_mrn_detail.item_id                             \r\n" + 
			"            and t_mrn_header.del_status=1                             \r\n" + 
			"            and t_mrn_detail.del_status=1                             \r\n" + 
			"            and po_detail.po_detail_id=t_mrn_detail.po_detail_id               \r\n" + 
			"            and m_item.grp_id=m_item_group.grp_id              \r\n" + 
			"            and t_mrn_header.mrn_type=:typeId\r\n" + 
			"            and t_mrn_detail.po_id=po_header.po_id \r\n" + 
			"            and po_header.ind_id=indent.ind_m_id              \r\n" + 
			"            and indent.ind_isdev=:isDev AND t_mrn_detail.mrn_detail_status = 4),\r\n" + 
			"        0) AS approved_qty_value,\r\n" + 
			"        coalesce((Select\r\n" + 
			"            SUM((po_detail.landing_cost/po_detail.item_qty)*t_mrn_detail.approve_qty)                     \r\n" + 
			"        FROM\r\n" + 
			"            t_mrn_detail,\r\n" + 
			"            t_mrn_header,\r\n" + 
			"            po_detail,\r\n" + 
			"            po_header,\r\n" + 
			"            indent,\r\n" + 
			"            m_item                       \r\n" + 
			"        where\r\n" + 
			"            t_mrn_header.mrn_date between :fromDate and :toDate                           \r\n" + 
			"            AND t_mrn_header.mrn_id=t_mrn_detail.mrn_id                             \r\n" + 
			"            AND m_item.item_id=t_mrn_detail.item_id                             \r\n" + 
			"            and t_mrn_header.del_status=1                             \r\n" + 
			"            and t_mrn_detail.del_status=1                             \r\n" + 
			"            and po_detail.po_detail_id=t_mrn_detail.po_detail_id               \r\n" + 
			"            and m_item.grp_id=m_item_group.grp_id              \r\n" + 
			"            and t_mrn_header.mrn_type=:typeId\r\n" + 
			"            and t_mrn_detail.po_id=po_header.po_id \r\n" + 
			"            and po_header.ind_id=indent.ind_m_id              \r\n" + 
			"            and indent.ind_isdev=:isDev AND t_mrn_detail.mrn_detail_status = 4),\r\n" + 
			"        0) AS approved_landing_value,\r\n" + 
			"        coalesce((Select\r\n" + 
			"            SUM(item_issue_detail.item_issue_qty)                     \r\n" + 
			"        FROM\r\n" + 
			"            item_issue_header,\r\n" + 
			"            item_issue_detail,\r\n" + 
			"            t_mrn_detail,\r\n" + 
			"            po_header,\r\n" + 
			"            indent,\r\n" + 
			"            m_item                        \r\n" + 
			"        WHERE\r\n" + 
			"            item_issue_header.issue_date between :fromDate and :toDate                            \r\n" + 
			"            AND item_issue_header.issue_id=item_issue_detail.issue_id                             \r\n" + 
			"            AND m_item.item_id=item_issue_detail.item_id                             \r\n" + 
			"            and item_issue_header.delete_status=1                             \r\n" + 
			"            and item_issue_detail.del_status=1               \r\n" + 
			"            and m_item.grp_id=m_item_group.grp_id               \r\n" + 
			"            and item_issue_header.item_category=:typeId \r\n" + 
			"            and item_issue_detail.mrn_detail_id=t_mrn_detail.mrn_detail_id                             \r\n" + 
			"            and po_header.po_id=t_mrn_detail.po_id \r\n" + 
			"            and po_header.ind_id=indent.ind_m_id \r\n" + 
			"            and indent.ind_isdev=:isDev AND item_issue_detail.status = 2),\r\n" + 
			"        0) AS issue_qty,\r\n" + 
			"        coalesce((Select\r\n" + 
			"            SUM(item_issue_detail.item_issue_qty*po_detail.item_rate)                     \r\n" + 
			"        FROM\r\n" + 
			"            item_issue_header,\r\n" + 
			"            item_issue_detail,\r\n" + 
			"            t_mrn_detail,\r\n" + 
			"            po_detail,\r\n" + 
			"            po_header,\r\n" + 
			"            indent,\r\n" + 
			"            m_item                        \r\n" + 
			"        WHERE\r\n" + 
			"            item_issue_header.issue_date between :fromDate and :toDate                             \r\n" + 
			"            AND item_issue_header.issue_id=item_issue_detail.issue_id                             \r\n" + 
			"            AND m_item.item_id=item_issue_detail.item_id                             \r\n" + 
			"            and item_issue_header.delete_status=1                             \r\n" + 
			"            and item_issue_detail.del_status=1                             \r\n" + 
			"            and item_issue_detail.mrn_detail_id=t_mrn_detail.mrn_detail_id                             \r\n" + 
			"            and po_detail.po_detail_id=t_mrn_detail.po_detail_id               \r\n" + 
			"            and m_item.grp_id=m_item_group.grp_id               \r\n" + 
			"            and item_issue_header.item_category=:typeId              \r\n" + 
			"            and po_header.po_id=t_mrn_detail.po_id \r\n" + 
			"            and po_header.ind_id=indent.ind_m_id \r\n" + 
			"            and indent.ind_isdev=:isDev AND item_issue_detail.status = 2),\r\n" + 
			"        0) AS issue_qty_value,\r\n" + 
			"        coalesce((Select\r\n" + 
			"            SUM((po_detail.landing_cost/po_detail.item_qty)*item_issue_detail.item_issue_qty)                     \r\n" + 
			"        FROM\r\n" + 
			"            item_issue_header,\r\n" + 
			"            item_issue_detail,\r\n" + 
			"            t_mrn_detail,\r\n" + 
			"            po_detail,\r\n" + 
			"            po_header,\r\n" + 
			"            indent,\r\n" + 
			"            m_item                        \r\n" + 
			"        WHERE\r\n" + 
			"            item_issue_header.issue_date between :fromDate and :toDate                             \r\n" + 
			"            AND item_issue_header.issue_id=item_issue_detail.issue_id                             \r\n" + 
			"            AND m_item.item_id=item_issue_detail.item_id                             \r\n" + 
			"            and item_issue_header.delete_status=1                             \r\n" + 
			"            and item_issue_detail.del_status=1                             \r\n" + 
			"            and item_issue_detail.mrn_detail_id=t_mrn_detail.mrn_detail_id                             \r\n" + 
			"            and po_detail.po_detail_id=t_mrn_detail.po_detail_id               \r\n" + 
			"            and m_item.grp_id=m_item_group.grp_id               \r\n" + 
			"            and item_issue_header.item_category=:typeId              \r\n" + 
			"            and po_header.po_id=t_mrn_detail.po_id \r\n" + 
			"            and po_header.ind_id=indent.ind_m_id \r\n" + 
			"            and indent.ind_isdev=:isDev AND item_issue_detail.status = 2),\r\n" + 
			"        0) AS issue_landing_value               \r\n" + 
			"    FROM\r\n" + 
			"         m_item_group             \r\n" + 
			"    where\r\n" + 
			"        m_item_group.is_used=1\r\n" + 
			"        and m_item_group.cat_id=:catId"),nativeQuery=true)
	List<IssueAndMrnGroupWise> issueAndMrnGroupWisReportByCatId(@Param("fromDate")String fromDate,@Param("toDate") String toDate, 
			@Param("typeId") int typeId, @Param("isDev") int isDev, @Param("catId") int catId);


	@Query(value=("SELECT\r\n" + 
			"        m_item_group.grp_id,\r\n" + 
			"        concat(m_item_group.grp_code,\"  \",m_item_group.grp_desc) as grp_code , \r\n" + 
			"        coalesce((Select\r\n" + 
			"            SUM(t_mrn_detail.approve_qty) \r\n" + 
			"        FROM\r\n" + 
			"            t_mrn_detail,\r\n" + 
			"            t_mrn_header,\r\n" + 
			"            m_item \r\n" + 
			"        where\r\n" + 
			"            t_mrn_header.mrn_date between :fromDate and :toDate \r\n" + 
			"            AND t_mrn_header.mrn_id=t_mrn_detail.mrn_id \r\n" + 
			"            AND m_item.item_id=t_mrn_detail.item_id \r\n" + 
			"            and t_mrn_header.del_status=1 \r\n" + 
			"            and t_mrn_detail.del_status=1 \r\n" + 
			"            and m_item.grp_id=m_item_group.grp_id \r\n" + 
			"            and t_mrn_header.mrn_type=:typeId AND t_mrn_detail.mrn_detail_status = 4),\r\n" + 
			"        0) AS approve_qty,\r\n" + 
			"        coalesce((Select\r\n" + 
			"            SUM(po_detail.item_rate*t_mrn_detail.approve_qty) \r\n" + 
			"        FROM\r\n" + 
			"            t_mrn_detail,\r\n" + 
			"            t_mrn_header,\r\n" + 
			"            po_detail,\r\n" + 
			"            m_item \r\n" + 
			"        where\r\n" + 
			"            t_mrn_header.mrn_date between :fromDate and :toDate\r\n" + 
			"            AND t_mrn_header.mrn_id=t_mrn_detail.mrn_id \r\n" + 
			"            AND m_item.item_id=t_mrn_detail.item_id \r\n" + 
			"            and t_mrn_header.del_status=1 \r\n" + 
			"            and t_mrn_detail.del_status=1 \r\n" + 
			"            and po_detail.po_detail_id=t_mrn_detail.po_detail_id \r\n" + 
			"            and m_item.grp_id=m_item_group.grp_id \r\n" + 
			"            and t_mrn_header.mrn_type=:typeId AND t_mrn_detail.mrn_detail_status = 4),\r\n" + 
			"        0) AS approved_qty_value,\r\n" + 
			"        coalesce((Select\r\n" + 
			"            SUM((po_detail.landing_cost/po_detail.item_qty)*t_mrn_detail.approve_qty) \r\n" + 
			"        FROM\r\n" + 
			"            t_mrn_detail,\r\n" + 
			"            t_mrn_header,\r\n" + 
			"            po_detail,\r\n" + 
			"            m_item \r\n" + 
			"        where\r\n" + 
			"            t_mrn_header.mrn_date between :fromDate and :toDate\r\n" + 
			"            AND t_mrn_header.mrn_id=t_mrn_detail.mrn_id \r\n" + 
			"            AND m_item.item_id=t_mrn_detail.item_id \r\n" + 
			"            and t_mrn_header.del_status=1 \r\n" + 
			"            and t_mrn_detail.del_status=1 \r\n" + 
			"            and po_detail.po_detail_id=t_mrn_detail.po_detail_id \r\n" + 
			"            and m_item.grp_id=m_item_group.grp_id\r\n" + 
			"            and t_mrn_header.mrn_type=:typeId AND t_mrn_detail.mrn_detail_status = 4),\r\n" + 
			"        0) AS approved_landing_value,\r\n" + 
			"        coalesce((Select\r\n" + 
			"            SUM(item_issue_detail.item_issue_qty) \r\n" + 
			"        FROM\r\n" + 
			"            item_issue_header,\r\n" + 
			"            item_issue_detail,\r\n" + 
			"            m_item  \r\n" + 
			"        WHERE\r\n" + 
			"            item_issue_header.issue_date between :fromDate and :toDate \r\n" + 
			"            AND item_issue_header.issue_id=item_issue_detail.issue_id \r\n" + 
			"            AND m_item.item_id=item_issue_detail.item_id \r\n" + 
			"            and item_issue_header.delete_status=1 \r\n" + 
			"            and item_issue_detail.del_status=1 \r\n" + 
			"            and m_item.grp_id=m_item_group.grp_id \r\n" + 
			"            and item_issue_header.item_category=:typeId AND item_issue_detail.status = 2),\r\n" + 
			"        0) AS issue_qty,\r\n" + 
			"        coalesce((Select\r\n" + 
			"            SUM(item_issue_detail.item_issue_qty*po_detail.item_rate) \r\n" + 
			"        FROM\r\n" + 
			"            item_issue_header,\r\n" + 
			"            item_issue_detail,\r\n" + 
			"            t_mrn_detail,\r\n" + 
			"            po_detail,\r\n" + 
			"            m_item \r\n" + 
			"        WHERE\r\n" + 
			"            item_issue_header.issue_date between :fromDate and :toDate \r\n" + 
			"            AND item_issue_header.issue_id=item_issue_detail.issue_id \r\n" + 
			"            AND m_item.item_id=item_issue_detail.item_id \r\n" + 
			"            and item_issue_header.delete_status=1 \r\n" + 
			"            and item_issue_detail.del_status=1 \r\n" + 
			"            and item_issue_detail.mrn_detail_id=t_mrn_detail.mrn_detail_id \r\n" + 
			"            and po_detail.po_detail_id=t_mrn_detail.po_detail_id \r\n" + 
			"            and m_item.grp_id=m_item_group.grp_id \r\n" + 
			"            and item_issue_header.item_category=:typeId AND item_issue_detail.status = 2),\r\n" + 
			"        0) AS issue_qty_value,\r\n" + 
			"        coalesce((Select\r\n" + 
			"            SUM((po_detail.landing_cost/po_detail.item_qty)*item_issue_detail.item_issue_qty) \r\n" + 
			"        FROM\r\n" + 
			"            item_issue_header,\r\n" + 
			"            item_issue_detail,\r\n" + 
			"            t_mrn_detail,\r\n" + 
			"            po_detail,\r\n" + 
			"            m_item \r\n" + 
			"        WHERE\r\n" + 
			"            item_issue_header.issue_date between :fromDate and :toDate \r\n" + 
			"            AND item_issue_header.issue_id=item_issue_detail.issue_id \r\n" + 
			"            AND m_item.item_id=item_issue_detail.item_id \r\n" + 
			"            and item_issue_header.delete_status=1 \r\n" + 
			"            and item_issue_detail.del_status=1 \r\n" + 
			"            and item_issue_detail.mrn_detail_id=t_mrn_detail.mrn_detail_id \r\n" + 
			"            and po_detail.po_detail_id=t_mrn_detail.po_detail_id \r\n" + 
			"            and m_item.grp_id=m_item_group.grp_id\r\n" + 
			"            and item_issue_header.item_category=:typeId AND item_issue_detail.status = 2),\r\n" + 
			"        0) AS issue_landing_value \r\n" + 
			"    FROM\r\n" + 
			"        m_item_group             \r\n" + 
			"    where\r\n" + 
			"        m_item_group.is_used=1\r\n" + 
			"        and m_item_group.cat_id=:catId"),nativeQuery=true)
	List<IssueAndMrnGroupWise> issueAndMrnGroupWisReportByCatIdWithAllDevelopment(@Param("fromDate")String fromDate,@Param("toDate") String toDate, 
			@Param("typeId") int typeId, @Param("catId") int catId);


	@Query(value=("SELECT\r\n" + 
			"        m_item_group.grp_id,\r\n" + 
			"        concat(m_item_group.grp_code,\"  \",m_item_group.grp_desc) as grp_code ,  \r\n" + 
			"        coalesce((Select\r\n" + 
			"            SUM(t_mrn_detail.approve_qty)                     \r\n" + 
			"        FROM\r\n" + 
			"            t_mrn_detail,\r\n" + 
			"            t_mrn_header,\r\n" + 
			"            po_header,\r\n" + 
			"            indent,\r\n" + 
			"            m_item                     \r\n" + 
			"        where\r\n" + 
			"            t_mrn_header.mrn_date between :fromDate and :toDate                            \r\n" + 
			"            AND t_mrn_header.mrn_id=t_mrn_detail.mrn_id                             \r\n" + 
			"            AND m_item.item_id=t_mrn_detail.item_id                             \r\n" + 
			"            and t_mrn_header.del_status=1                             \r\n" + 
			"            and t_mrn_detail.del_status=1               \r\n" + 
			"            and m_item.grp_id=m_item_group.grp_id\r\n" + 
			"            and t_mrn_detail.po_id=po_header.po_id \r\n" + 
			"            and po_header.ind_id=indent.ind_m_id              \r\n" + 
			"            and indent.ind_isdev=:isDev AND t_mrn_detail.mrn_detail_status = 4),\r\n" + 
			"        0) AS approve_qty,\r\n" + 
			"        coalesce((Select\r\n" + 
			"            SUM(po_detail.item_rate*t_mrn_detail.approve_qty)                     \r\n" + 
			"        FROM\r\n" + 
			"            t_mrn_detail,\r\n" + 
			"            t_mrn_header,\r\n" + 
			"            po_detail,\r\n" + 
			"            po_header,\r\n" + 
			"            indent,\r\n" + 
			"            m_item                      \r\n" + 
			"        where\r\n" + 
			"            t_mrn_header.mrn_date between :fromDate and :toDate                           \r\n" + 
			"            AND t_mrn_header.mrn_id=t_mrn_detail.mrn_id                             \r\n" + 
			"            AND m_item.item_id=t_mrn_detail.item_id                             \r\n" + 
			"            and t_mrn_header.del_status=1                             \r\n" + 
			"            and t_mrn_detail.del_status=1                             \r\n" + 
			"            and po_detail.po_detail_id=t_mrn_detail.po_detail_id               \r\n" + 
			"            and m_item.grp_id=m_item_group.grp_id  \r\n" + 
			"            and t_mrn_detail.po_id=po_header.po_id \r\n" + 
			"            and po_header.ind_id=indent.ind_m_id              \r\n" + 
			"            and indent.ind_isdev=:isDev AND t_mrn_detail.mrn_detail_status = 4),\r\n" + 
			"        0) AS approved_qty_value,\r\n" + 
			"        coalesce((Select\r\n" + 
			"            SUM((po_detail.landing_cost/po_detail.item_qty)*t_mrn_detail.approve_qty)                     \r\n" + 
			"        FROM\r\n" + 
			"            t_mrn_detail,\r\n" + 
			"            t_mrn_header,\r\n" + 
			"            po_detail,\r\n" + 
			"            po_header,\r\n" + 
			"            indent,\r\n" + 
			"            m_item                       \r\n" + 
			"        where\r\n" + 
			"            t_mrn_header.mrn_date between :fromDate and :toDate                           \r\n" + 
			"            AND t_mrn_header.mrn_id=t_mrn_detail.mrn_id                             \r\n" + 
			"            AND m_item.item_id=t_mrn_detail.item_id                             \r\n" + 
			"            and t_mrn_header.del_status=1                             \r\n" + 
			"            and t_mrn_detail.del_status=1                             \r\n" + 
			"            and po_detail.po_detail_id=t_mrn_detail.po_detail_id               \r\n" + 
			"            and m_item.grp_id=m_item_group.grp_id  \r\n" + 
			"            and t_mrn_detail.po_id=po_header.po_id \r\n" + 
			"            and po_header.ind_id=indent.ind_m_id              \r\n" + 
			"            and indent.ind_isdev=:isDev AND t_mrn_detail.mrn_detail_status = 4),\r\n" + 
			"        0) AS approved_landing_value,\r\n" + 
			"        coalesce((Select\r\n" + 
			"            SUM(item_issue_detail.item_issue_qty)                     \r\n" + 
			"        FROM\r\n" + 
			"            item_issue_header,\r\n" + 
			"            item_issue_detail,\r\n" + 
			"            t_mrn_detail,\r\n" + 
			"            po_header,\r\n" + 
			"            indent,\r\n" + 
			"            m_item                        \r\n" + 
			"        WHERE\r\n" + 
			"            item_issue_header.issue_date between :fromDate and :toDate                           \r\n" + 
			"            AND item_issue_header.issue_id=item_issue_detail.issue_id                             \r\n" + 
			"            AND m_item.item_id=item_issue_detail.item_id                             \r\n" + 
			"            and item_issue_header.delete_status=1                             \r\n" + 
			"            and item_issue_detail.del_status=1               \r\n" + 
			"            and m_item.grp_id=m_item_group.grp_id \r\n" + 
			"            and item_issue_detail.mrn_detail_id=t_mrn_detail.mrn_detail_id                             \r\n" + 
			"            and po_header.po_id=t_mrn_detail.po_id \r\n" + 
			"            and po_header.ind_id=indent.ind_m_id \r\n" + 
			"            and indent.ind_isdev=:isDev AND item_issue_detail.status = 2),\r\n" + 
			"        0) AS issue_qty,\r\n" + 
			"        coalesce((Select\r\n" + 
			"            SUM(item_issue_detail.item_issue_qty*po_detail.item_rate)                     \r\n" + 
			"        FROM\r\n" + 
			"            item_issue_header,\r\n" + 
			"            item_issue_detail,\r\n" + 
			"            t_mrn_detail,\r\n" + 
			"            po_detail,\r\n" + 
			"            po_header,\r\n" + 
			"            indent,\r\n" + 
			"            m_item                        \r\n" + 
			"        WHERE\r\n" + 
			"            item_issue_header.issue_date between :fromDate and :toDate                            \r\n" + 
			"            AND item_issue_header.issue_id=item_issue_detail.issue_id                             \r\n" + 
			"            AND m_item.item_id=item_issue_detail.item_id                             \r\n" + 
			"            and item_issue_header.delete_status=1                             \r\n" + 
			"            and item_issue_detail.del_status=1                             \r\n" + 
			"            and item_issue_detail.mrn_detail_id=t_mrn_detail.mrn_detail_id                             \r\n" + 
			"            and po_detail.po_detail_id=t_mrn_detail.po_detail_id               \r\n" + 
			"            and m_item.grp_id=m_item_group.grp_id               \r\n" + 
			"            and po_header.po_id=t_mrn_detail.po_id \r\n" + 
			"            and po_header.ind_id=indent.ind_m_id \r\n" + 
			"            and indent.ind_isdev=:isDev AND item_issue_detail.status = 2),\r\n" + 
			"        0) AS issue_qty_value,\r\n" + 
			"        coalesce((Select\r\n" + 
			"            SUM((po_detail.landing_cost/po_detail.item_qty)*item_issue_detail.item_issue_qty)                     \r\n" + 
			"        FROM\r\n" + 
			"            item_issue_header,\r\n" + 
			"            item_issue_detail,\r\n" + 
			"            t_mrn_detail,\r\n" + 
			"            po_detail,\r\n" + 
			"            po_header,\r\n" + 
			"            indent,\r\n" + 
			"            m_item                        \r\n" + 
			"        WHERE\r\n" + 
			"            item_issue_header.issue_date between :fromDate and :toDate                            \r\n" + 
			"            AND item_issue_header.issue_id=item_issue_detail.issue_id                             \r\n" + 
			"            AND m_item.item_id=item_issue_detail.item_id                             \r\n" + 
			"            and item_issue_header.delete_status=1                             \r\n" + 
			"            and item_issue_detail.del_status=1                             \r\n" + 
			"            and item_issue_detail.mrn_detail_id=t_mrn_detail.mrn_detail_id                             \r\n" + 
			"            and po_detail.po_detail_id=t_mrn_detail.po_detail_id               \r\n" + 
			"            and m_item.grp_id=m_item_group.grp_id              \r\n" + 
			"            and po_header.po_id=t_mrn_detail.po_id \r\n" + 
			"            and po_header.ind_id=indent.ind_m_id \r\n" + 
			"            and indent.ind_isdev=:isDev AND item_issue_detail.status = 2),\r\n" + 
			"        0) AS issue_landing_value               \r\n" + 
			"    FROM\r\n" + 
			"        m_item_group             \r\n" + 
			"    where\r\n" + 
			"        m_item_group.is_used=1\r\n" + 
			"        and m_item_group.cat_id=:catId"),nativeQuery=true)
	List<IssueAndMrnGroupWise> issueAndMrnGroupWisReportByCatIdWithAllType(@Param("fromDate")String fromDate,@Param("toDate") String toDate,
			@Param("isDev")int isDev, @Param("catId") int catId);

}
