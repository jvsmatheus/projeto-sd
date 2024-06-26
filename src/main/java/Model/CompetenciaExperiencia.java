package Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    @Column(name = "email_candidato")
    private String email;
    
    

    public CompetenciaExperiencia() {
	}

	public CompetenciaExperiencia(String competencia, int experiencia, String email) {
		this.competencia = competencia;
		this.experiencia = experiencia;
		this.email = email;
	}

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "CompetenciaExperiencia [id=" + id + ", competencia=" + competencia + ", experiencia=" + experiencia
				+ ", email=" + email + "]";
	}

	
    
}
