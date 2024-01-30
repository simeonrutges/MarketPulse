package com.example.MarketPulse.service;

import com.example.MarketPulse.dto.TransactionDto;
import com.example.MarketPulse.exceptions.ResourceNotFoundException;
import com.example.MarketPulse.model.Order;
import com.example.MarketPulse.model.Transaction;
import com.example.MarketPulse.model.User;
import com.example.MarketPulse.repository.OrderRepository;
import com.example.MarketPulse.repository.TransactionRepository;
import com.example.MarketPulse.repository.UserRepository;
import org.hibernate.mapping.Map;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final DtoMapperService dtoMapperService;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public TransactionService(TransactionRepository transactionRepository, DtoMapperService dtoMapperService, UserRepository userRepository, OrderRepository orderRepository) {
        this.transactionRepository = transactionRepository;
        this.dtoMapperService = dtoMapperService;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    public TransactionDto createTransaction(TransactionDto transactionDto) {
        Transaction createdTransaction = dtoMapperService.transactionDtoToTransaction(transactionDto);
        Transaction savedTransaction = transactionRepository.save(createdTransaction);

        return dtoMapperService.transactionToDto(savedTransaction);
    }


    public List<TransactionDto> getAllTransactions() {
        List <Transaction> transactions = transactionRepository.findAll();
        return transactions.stream()
                        .map(dtoMapperService::transactionToDto)
                .collect(Collectors.toList());
    }

    public List<TransactionDto> getTransactionsByUserId(Long userId) {
    List <Transaction> transactions = transactionRepository.findByUserId(userId);

    return transactions.stream()
                    .map(dtoMapperService :: transactionToDto)
            .collect(Collectors.toList());
    }

    public TransactionDto getTransactionById(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Transactie niet gevonden met ID: " + transactionId));
        return dtoMapperService.transactionToDto(transaction);
    }

    public TransactionDto updateTransaction(Long transactionId, TransactionDto transactionDto) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Transactie niet gevonden met ID: " + transactionId));

        // Voer gedeeltelijke update uit op basis van de aanwezige velden in transactionDto
        if (transactionDto.getAmount() != null) {
            transaction.setAmount(transactionDto.getAmount());
        }
        if (transactionDto.getTransactionDate() != null) {
            transaction.setTransactionDate(transactionDto.getTransactionDate());
        }
        if (transactionDto.getStatus() != null) {
            transaction.setStatus(transactionDto.getStatus());
        }
        if (transactionDto.getUserId() != null) {
            // Zoek de gebruiker op basis van userId en update de relatie
            User user = userRepository.findById(transactionDto.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("Gebruiker niet gevonden met ID: " + transactionDto.getUserId()));
            transaction.setUser(user);
        }
        if (transactionDto.getOrderId() != null) {
            // Zoek de order op basis van orderId en update de relatie
            Order order = orderRepository.findById(transactionDto.getOrderId())
                    .orElseThrow(() -> new ResourceNotFoundException("Order niet gevonden met ID: " + transactionDto.getOrderId()));
            transaction.setOrder(order);
        }

        Transaction updatedTransaction = transactionRepository.save(transaction);
        return dtoMapperService.transactionToDto(updatedTransaction);
    }

    public void deleteTransaction(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(()-> new ResourceNotFoundException("Transactie niet gevonden met ID: " + transactionId));
        transactionRepository.delete(transaction);
    }


}
