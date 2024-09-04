package tech.ecoelho.criptography.domain.dto;

import tech.ecoelho.criptography.domain.entity.TransactionEntity;

public record TransactionResponse(Long id, String userDocument, String creditCardToken, Long value) {

    public static TransactionResponse fromEntity(TransactionEntity entity){
        return new TransactionResponse(entity.getId(), entity.getRawUserDocument(),
                                       entity.getRawCreditCardToken(), entity.getValue());
    }
}
