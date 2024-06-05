package Model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

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
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "vaga_id")
    private List<CompetenciaExperiencia> competencias = new ArrayList<>();

    public Vaga(String token, String nome, Double faixaSalarial, String descricao) {
        this.token = token;
        this.nome = nome;
        this.faixaSalarial = faixaSalarial;
        this.descricao = descricao;
    }

    public Vaga() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getFaixaSalarial() {
        return faixaSalarial;
    }

    public void setFaixaSalarial(Double faixaSalarial) {
        this.faixaSalarial = faixaSalarial;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<CompetenciaExperiencia> getCompetencias() {
        return competencias;
    }

    public void setCompetencias(List<CompetenciaExperiencia> competencias) {
        this.competencias = competencias;
    }

    @Override
    public String toString() {
        return "Vaga{" +
                "token='" + token + '\'' +
                ", nome='" + nome + '\'' +
                ", faixaSalarial=" + faixaSalarial +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
