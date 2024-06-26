package Model;

import javax.persistence.*;

@Entity
@Table(name = "empresa_vaga_competencia")
@IdClass(EmpresaVagaCompetenciaId.class)
public class EmpresaVagaCompetencia {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id", referencedColumnName = "id", nullable = false)
    private Empresa empresa;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vaga_id", referencedColumnName = "id", nullable = false)
    private Vaga vaga;

	public EmpresaVagaCompetencia() {
		super();
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Vaga getVaga() {
		return vaga;
	}

	public void setVaga(Vaga vaga) {
		this.vaga = vaga;
	}

	@Override
	public String toString() {
		return "EmpresaVagaCompetencia [empresa=" + empresa + ", vaga=" + vaga + "]";
	}
    
    
}
