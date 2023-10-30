package com.woof.returnlist.service;

import com.woof.returnlist.entity.ReturnList;
import com.woof.returnlist.dao.ReturnListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReturnListService {

    @Autowired
    private ReturnListRepository repository;

    public List<ReturnList> getAll() {
        return repository.findAll();
    }

    public Optional<ReturnList> getById(Integer id) {
        return repository.findById(id);
    }

    public ReturnList save(ReturnList returnList) {
        return repository.save(returnList);
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}