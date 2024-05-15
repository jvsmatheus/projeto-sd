package Model;

import Auth.JwtService;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "candidato")
public class User implements Serializable {

    @Id
    @Column(name = "id", length = 36, nullable = false)
    private String id;
    @Column(name = "nome", length = 100, nullable = false)
    private String nome;
    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email;
    @Column(name = "senha", length = 100, nullable = false)
    private String senha;



    public User(String nome, String email, String senha) {
        this.id = String.valueOf(UUID.randomUUID());
        this.nome = nome;
        this.email = email;
        this.senha = JwtService.hashPassword(senha);
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = JwtService.hashPassword(senha);
    }


    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(nome, user.nome) && Objects.equals(email, user.email) && Objects.equals(senha, user.senha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, email, senha);
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                '}';
    }
}
