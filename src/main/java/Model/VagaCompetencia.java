package Model;

import javax.persistence.*;

@Entity
@Table(name = "vaga_competencia")
public class VagaCompetencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vaga_id")
    private long vagaId;

    @Column(name = "competencia", nullable = false, length = 50)
    private String competencia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vaga_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Vaga vaga;

	public VagaCompetencia() {
		super();
	}

	public long getVagaId() {
		return vagaId;
	}

	public void setVagaId(long vagaId) {
		this.vagaId = vagaId;
	}

	public String getCompetencia() {
		return competencia;
	}

	public void setCompetencia(String competencia) {
		this.competencia = competencia;
	}

	public Vaga getVaga() {
		return vaga;
	}

	public void setVaga(Vaga vaga) {
		this.vaga = vaga;
	}

	@Override
	public String toString() {
		return "VagaCompetencia [vagaId=" + vagaId + ", competencia=" + competencia + ", vaga=" + vaga + "]";
	}
    
    
    
}