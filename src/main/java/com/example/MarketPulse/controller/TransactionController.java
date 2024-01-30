package com.example.MarketPulse.controller;

import com.example.MarketPulse.dto.TransactionDto;
import com.example.MarketPulse.model.Transaction;
import com.example.MarketPulse.service.TransactionService;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<TransactionDto> createTransaction (@RequestBody TransactionDto transactionDto){
        TransactionDto createdTransaction = transactionService.createTransaction(transactionDto);
        return new ResponseEntity<>(createdTransaction, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TransactionDto>>getAllTransactions(){
        List <TransactionDto> allTransactions = transactionService.getAllTransactions();
        return ResponseEntity.ok(allTransactions);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<TransactionDto>> getTransactionsByUserId(@PathVariable Long userId) {
        List<TransactionDto> transactionsById = transactionService.getTransactionsByUserId(userId);
        return new ResponseEntity<>(transactionsById, HttpStatus.OK);
    }
    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionDto> getTransactionById(@PathVariable Long transactionId) {
        TransactionDto transaction = transactionService.getTransactionById(transactionId);
        return ResponseEntity.ok(transaction);
    }
    @PatchMapping("/{transactionId}")
    public ResponseEntity<TransactionDto> updateTransaction(@PathVariable Long transactionId, @RequestBody TransactionDto transactionDto) {
        TransactionDto updatedTransaction = transactionService.updateTransaction(transactionId, transactionDto);
        return ResponseEntity.ok(updatedTransaction);
    }

    @DeleteMapping("/{transactionId}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long transactionId) {
        transactionService.deleteTransaction(transactionId);
        return ResponseEntity.noContent().build();
    }


//    @GetMapping("/filter")
//    public ResponseEntity<List<TransactionDto>> getTransactionsByCriteria(@RequestParam Map<String, String> criteria) {
//        List<TransactionDto> filteredTransactions = transactionService.getTransactionsByCriteria(criteria);
//        return ResponseEntity.ok(filteredTransactions);
//    }





}
