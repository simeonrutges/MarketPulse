package com.example.MarketPulse.service;

import com.example.MarketPulse.dto.TransactionDto;
import com.example.MarketPulse.model.Transaction;
import com.example.MarketPulse.repository.TransactionRepository;
import org.hibernate.mapping.Map;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final DtoMapperService dtoMapperService;

    public TransactionService(TransactionRepository transactionRepository, DtoMapperService dtoMapperService) {
        this.transactionRepository = transactionRepository;
        this.dtoMapperService = dtoMapperService;
    }

    public TransactionDto createTransaction(TransactionDto transactionDto) {
        Transaction createdTransaction = dtoMapperService.transactionDtoToTransaction(transactionDto);
        Transaction savedTransaction = transactionRepository.save(createdTransaction);

        return dtoMapperService.transactionToDto(savedTransaction);
    }


//    public List<TransactionDto> getAllTransactions() {
//    }
//
//    public List<TransactionDto> getTransactionsByUserId(Long userId) {
//    }
//
//    public TransactionDto getTransactionById(Long transactionId) {
//    }
//
//    public TransactionDto updateTransaction(Long transactionId, TransactionDto transactionDto) {
//    }
//
//    public void deleteTransaction(Long transactionId) {
//    }


}
