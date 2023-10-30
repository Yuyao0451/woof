package com.woof.returnlist.dao;

import java.util.List;
import java.util.Map;

import com.woof.returnlist.entity.ReturnList;

public interface ReturnListDAO {
	int insert(ReturnList returnList);

	int update(ReturnList returnList);

	int delete(Integer reExchId);

	ReturnList findByReExchId(Integer reExchId);

	List<ReturnList> getAll();

	List<ReturnList> getByCompositeQuery(Map<String, String> map);

	List<ReturnList> getAll(int currentPage);

	long getTotal();
}

