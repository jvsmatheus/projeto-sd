package Model;

import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "vaga")
public class Vaga {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "nome")
    private String nome;
    @Column(name = "faixa_salarial")
    private double faixaSalarial;
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "estado")
    private String estado;

//    @ManyToMany
//    @JoinTable(
//        name = "vaga_competencia",
//        joinColumns = @JoinColumn(name = "vaga_id"),
//        inverseJoinColumns = @JoinColumn(name = "competencia")
//    )
//    private List<VagaCompetencia> competencias;
//
//    @ManyToOne
//    @JoinTable(
//            name = "empresa_vaga_competencia",
//            joinColumns = @JoinColumn(name = "vaga_id"),
//            inverseJoinColumns = @JoinColumn(name = "competencia")
//        )
//    @JoinColumn(name = "empresa_id", nullable = false)
//    private Empresa empresa;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getFaixaSalarial() {
		return faixaSalarial;
	}

	public void setFaixaSalarial(double faixaSalarial) {
		this.faixaSalarial = faixaSalarial;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

//	public List<VagaCompetencia> getCompetencias() {
//		return competencias;
//	}
//
//	public void setCompetencias(List<VagaCompetencia> competencias) {
//		this.competencias = competencias;
//	}
//
//	public Empresa getEmpresa() {
//		return empresa;
//	}
//
//	public void setEmpresa(Empresa empresa) {
//		this.empresa = empresa;
//	}

	@Override
	public String toString() {
		return "Vaga [id=" + id + ", nome=" + nome + ", faixaSalarial=" + faixaSalarial + ", descricao=" + descricao
				+ ", estado=" + estado + "]";
	}

    
}
