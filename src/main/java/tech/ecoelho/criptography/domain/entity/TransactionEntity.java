package tech.ecoelho.criptography.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.ecoelho.criptography.service.CryptoService;

@Entity
@Table(name = "tb_transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long id;

    @Column(name = "user_document")
    private String encryptedUserDocument;

    @Column(name = "credit_card_token")
    private String encryptedCreditCardToken;

    @Column(name = "transaction_value")
    private Long value;

    @Transient // anotação para campos que nao sao persistidos no banco de dados
    private String rawUserDocument;

    @Transient
    private String rawCreditCardToken;

    @PrePersist // antes de persistir vai realizar a criptografia, para inserir os dados criptografados no banco.
    public void prePersist(){
        this.encryptedUserDocument = CryptoService.encryptPassword(rawUserDocument);
        this.encryptedCreditCardToken = CryptoService.encryptPassword(rawCreditCardToken);
    }

    @PostLoad // vai pegar o dado criptografado e vai retorna ele descriptografado para nós
    public void postLoad(){
        this.rawUserDocument = CryptoService.decryptPassword(encryptedUserDocument);
        this.rawCreditCardToken = CryptoService.decryptPassword(encryptedCreditCardToken);
    }
}
