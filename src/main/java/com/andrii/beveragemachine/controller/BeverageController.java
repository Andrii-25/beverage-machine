package com.andrii.beveragemachine.controller;

import com.andrii.beveragemachine.dto.Stats;
import com.andrii.beveragemachine.utils.Bucket;
import com.andrii.beveragemachine.entity.Cash;
import com.andrii.beveragemachine.entity.Product;
import com.andrii.beveragemachine.service.BeverageMachineImp;
import com.andrii.beveragemachine.utils.Status;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BeverageController {

    BeverageMachineImp beverageMachine = new BeverageMachineImp();

    @RequestMapping(method = RequestMethod.POST, value = "/init")
    public ResponseEntity<Status> initMachine() {
        try {
            beverageMachine.initialize();
            return ResponseEntity.ok().body(new Status("Success!", "Successfully initialized!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new Status("Failed!", e.getMessage()));
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/product")
    public ResponseEntity<Bucket<Product, List<Cash>>> getProductAndChange() {
        try {
            return ResponseEntity.ok().body(beverageMachine.collectProductAndChange());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/cash/{cash}")
    public ResponseEntity<Status> insertCash(@PathVariable("cash") Cash cash) {
        try {

            beverageMachine.insertCash(cash);
            return ResponseEntity.ok().body(new Status("Success!", "Successfully inserted: " + cash.getDenomination()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new Status("Failed!", e.getMessage()));
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/product/{product}")
    public ResponseEntity<Status> selectProduct(@PathVariable("product") Product product) {
        try {
            beverageMachine.selectProduct(product);
            return ResponseEntity.ok().body(new Status("Success!", "Successfully selected: " + product.getName()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new Status("Failed!", e.getMessage()));
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/reset")
    public ResponseEntity<Status> reset() {
        try {
            beverageMachine.reset();
            return ResponseEntity.ok().body(new Status("Success!", "Successfully reset!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new Status("Failed!", e.getMessage()));
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/stats")
    public ResponseEntity<Stats> getStats() {
        try {
            return ResponseEntity.ok(beverageMachine.getStats());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/refund")
    public ResponseEntity<List<Cash>> refund() {
        try {
            return ResponseEntity.ok().body(beverageMachine.refund());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
