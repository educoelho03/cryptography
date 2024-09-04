package tech.ecoelho.criptography.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tech.ecoelho.criptography.domain.dto.CreateTransactionRequest;
import tech.ecoelho.criptography.domain.dto.TransactionResponse;
import tech.ecoelho.criptography.domain.dto.UpdateTransactionRequest;
import tech.ecoelho.criptography.domain.entity.TransactionEntity;
import tech.ecoelho.criptography.repository.TransactionRepository;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void create(CreateTransactionRequest request){
        var entity = new TransactionEntity();
        entity.setRawUserDocument(request.userDocument());
        entity.setRawCreditCardToken(request.creditCardToken());
        entity.setValue(request.value());

        transactionRepository.save(entity);
    }

    public Page<TransactionResponse> listAll(int page, int pageSize){
        var content = transactionRepository.findAll(PageRequest.of(page, pageSize));

        return content.map(TransactionResponse::fromEntity);
    }

    public TransactionResponse findById(Long transactionId){
        var entity = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return TransactionResponse.fromEntity(entity);
    }

    public TransactionResponse updateById(Long transactionId, UpdateTransactionRequest request){
        var entity = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        entity.setValue(request.value());
        transactionRepository.save(entity);

        return TransactionResponse.fromEntity(entity);
    }

    public void deleteById(Long transactionId){
        var entity = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        transactionRepository.deleteById(entity.getId());
    }
}
