package Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "competencia")
public class Competencia {

    @Id
    @Column(name = "id", length = 36, nullable = false)
    private String id;

    @Column(name = "id", length = 50, nullable = false)
    private String titulo;

    public Competencia(String id, String titulo) {
        this.id = id;
        this.titulo = titulo;
    }

    public Competencia() {
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

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Competencia that = (Competencia) object;
        return Objects.equals(id, that.id) && Objects.equals(titulo, that.titulo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, titulo);
    }

    @Override
    public String toString() {
        return "Competencia{" +
                "id='" + id + '\'' +
                ", titulo='" + titulo + '\'' +
                '}';
    }
}
