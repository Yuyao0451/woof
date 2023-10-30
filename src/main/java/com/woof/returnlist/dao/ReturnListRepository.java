package com.woof.returnlist.dao;

import com.woof.returnlist.entity.ReturnList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReturnListRepository extends JpaRepository<ReturnList, Integer> {
}