package com.example.MarketPulse.service;

import com.example.MarketPulse.model.Transaction;
import com.example.MarketPulse.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionStatusUpdateService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Scheduled(fixedDelay = 10000) //  10 seconden
    public void updatePendingTransactions() {
        List<Transaction> pendingTransactions = transactionRepository.findByStatus("Pending");

        for (Transaction transaction : pendingTransactions) {
            // Logica om te bepalen of een transactie naar "Completed" of "Failed" gaat
            // Dit kan gebaseerd zijn op tijd, willekeurige selectie, enz.
            transaction.setStatus(Math.random() < 0.5 ? "Completed" : "Failed");
            transactionRepository.save(transaction);
        }
    }
}
