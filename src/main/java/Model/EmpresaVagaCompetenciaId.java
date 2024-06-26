package Model;

import java.io.Serializable;
import java.util.Objects;

public class EmpresaVagaCompetenciaId implements Serializable {
    private int empresa;
    private long vaga;

    public EmpresaVagaCompetenciaId() {}

    public EmpresaVagaCompetenciaId(int empresa, long vaga) {
        this.empresa = empresa;
        this.vaga = vaga;
    }

    // hashCode and equals
    @Override
    public int hashCode() {
        return Objects.hash(empresa, vaga);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmpresaVagaCompetenciaId that = (EmpresaVagaCompetenciaId) o;
        return empresa == that.empresa && vaga == that.vaga;
    }
}
