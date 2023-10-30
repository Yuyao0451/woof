package com.woof.returnlist.controller;

import com.woof.returnlist.entity.ReturnList;
import com.woof.returnlist.service.ReturnListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/return-lists")
public class ReturnListController {

    @Autowired
    private ReturnListService service;

    @GetMapping
    public List<ReturnList> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReturnList> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ReturnList create(@RequestBody ReturnList returnList) {
        return service.save(returnList);
    }

    @PutMapping("/{id}")
    public ReturnList update(@PathVariable Integer id, @RequestBody ReturnList returnList) {
        returnList.setReExchId(id);
        return service.save(returnList);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.deleteById(id);
    }
}