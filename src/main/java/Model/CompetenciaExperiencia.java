package Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "competencia_experienca")
public class CompetenciaExperiencia {

    @Id
    @Column(name = "id", length = 36, nullable = false)
    private String id;

    @Column(name = "titulo", length = 50, nullable = false)
    private String titulo;

    @Column(name = "experiencia", nullable = false)
    private String experiencia;

    public CompetenciaExperiencia(String id, String titulo, String experiencia) {
        this.id = id;
        this.titulo = titulo;
        this.experiencia = experiencia;
    }

    public CompetenciaExperiencia() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(String experiencia) {
        this.experiencia = experiencia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompetenciaExperiencia that = (CompetenciaExperiencia) o;
        return Objects.equals(id, that.id) && Objects.equals(titulo, that.titulo) && Objects.equals(experiencia, that.experiencia);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, titulo, experiencia);
    }

    @Override
    public String toString() {
        return "CompetenciaExperiencia{" +
                "id='" + id + '\'' +
                ", titulo='" + titulo + '\'' +
                ", experiencia='" + experiencia + '\'' +
                '}';
    }
}
