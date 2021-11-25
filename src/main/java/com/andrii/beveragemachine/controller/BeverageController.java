package com.andrii.beveragemachine.controller;

import com.andrii.beveragemachine.dto.Stats;
import com.andrii.beveragemachine.entity.Coin;
import com.andrii.beveragemachine.entity.Money;
import com.andrii.beveragemachine.utils.Bucket;
import com.andrii.beveragemachine.entity.Banknote;
import com.andrii.beveragemachine.entity.Product;
import com.andrii.beveragemachine.service.BeverageMachineImp;
import com.andrii.beveragemachine.utils.Status;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/machine")
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
    public ResponseEntity<Bucket<Product, List<Money>>> getProductAndChange() {
        try {
            return ResponseEntity.ok().body(beverageMachine.collectProductAndRemainder());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/money")
    public ResponseEntity<Status> insertCash(@RequestParam("banknote") Banknote banknote,
                                             @RequestParam(value = "coin", required = false) Coin coin) {
        try {
            beverageMachine.insertCash(banknote, coin);
            return ResponseEntity.ok().body(new Status("Success!", "Successfully inserted!"));
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
    public ResponseEntity<List<Money>> refund() {
        try {
            return ResponseEntity.ok().body(beverageMachine.refund());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
