package com.example.MarketPulse.service;

import com.example.MarketPulse.model.Order;
import com.example.MarketPulse.model.Transaction;
import com.example.MarketPulse.repository.OrderRepository;
import com.example.MarketPulse.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionStatusUpdateService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private OrderRepository orderRepository;

    private final static Logger log = LoggerFactory.getLogger(TransactionStatusUpdateService.class);

// alleen Transaction scheduled
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

    //  Transaction + Order scheduled
//@Scheduled(fixedDelay = 10000) // 10 seconden
//public void updatePendingTransactions() {
//    List<Transaction> pendingTransactions = transactionRepository.findByStatus("Pending");
//
//    for (Transaction transaction : pendingTransactions) {
//        String newStatus = Math.random() < 0.5 ? "Completed" : "Failed";
//        transaction.setStatus(newStatus);
//        transactionRepository.save(transaction);
//
//        // Log de update van de transactie
//        log.info("Updating transaction ID: " + transaction.getId() + " to status: " + newStatus);
//
//        // Update de gerelateerde order als de transactie is voltooid
//        if ("Completed".equals(newStatus)) {
//            Order relatedOrder = transaction.getOrder();
//            if (relatedOrder != null) {
//                relatedOrder.setStatus("Processing"); // Of een andere passende status
//                orderRepository.save(relatedOrder);
//
//                // Log de update van de gerelateerde order
//                log.info("Updating related order ID: " + relatedOrder.getId() + " to status: Processing");
//            }
//        }
//    }
//}

}
