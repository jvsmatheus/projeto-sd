package Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "vaga")
public class Vaga {

    @Id
    @Column(name = "id", length = 36, nullable = false)
    private String token;
    @Column(name = "nome", length = 200, nullable = false)
    private String nome;
    @Column(name = "faixa_salarial", nullable = false)
    private Double faixaSalarial;
    @Column(name = "id", length = 36, nullable = false)

}
