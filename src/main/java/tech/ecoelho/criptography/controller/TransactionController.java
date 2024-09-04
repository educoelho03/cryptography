package tech.ecoelho.criptography.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.ecoelho.criptography.domain.dto.CreateTransactionRequest;
import tech.ecoelho.criptography.domain.dto.TransactionResponse;
import tech.ecoelho.criptography.domain.dto.UpdateTransactionRequest;
import tech.ecoelho.criptography.service.TransactionService;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody CreateTransactionRequest request){
        transactionService.create(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Page<TransactionResponse>> listAll(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                        @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        var responseBody = transactionService.listAll(page, pageSize);
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionResponse> listById(@PathVariable Long transactionId){
        var responseBody = transactionService.findById(transactionId);

        return ResponseEntity.ok(responseBody);
    }

    @PutMapping("/update/{transactionId}")
    public ResponseEntity<TransactionResponse> updateById(@PathVariable Long transactionId, @RequestBody UpdateTransactionRequest request){
        transactionService.updateById(transactionId, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/{transactionId}")
    public ResponseEntity<Void> deletedById(@PathVariable Long transactionId){
        transactionService.deleteById(transactionId);

        return ResponseEntity.noContent().build();
    }
}
