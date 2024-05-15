package Model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "usuarios_logados")
public class LogedUSer {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "email", length = 100, nullable = false)
    private String email;

    public LogedUSer() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        LogedUSer logedUSer = (LogedUSer) object;
        return Objects.equals(id, logedUSer.id) && Objects.equals(email, logedUSer.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }

    @Override
    public String toString() {
        return "LogedUSer{" +
                "id=" + id +
                ", email='" + email + '\'' +
                '}';
    }
}
