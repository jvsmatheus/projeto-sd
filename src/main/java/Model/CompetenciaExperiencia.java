package Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "competencia_experiencia")
public class CompetenciaExperiencia {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "competencia")
    private String competencia;

    @Column(name = "experiencia")
    private int experiencia;

    @ManyToOne
    @JoinColumn(name = "emailCandidato", nullable = false, referencedColumnName = "email")
    private Candidato candidato;

    // Getters e Setters
    public void setId(String id) {
        this.id = Long.valueOf(String.valueOf(Long.valueOf(id)));
    }

    public Long getId() {
        return id;
    }

    public String getCompetencia() {
        return competencia;
    }

    public void setCompetencia(String competencia) {
        this.competencia = competencia;
    }

    public int getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(int experiencia) {
        this.experiencia = experiencia;
    }

    public Candidato getCandidato() {
        return candidato;
    }

    public void setCandidato(Candidato candidato) {
        this.candidato = candidato;
    }
}
