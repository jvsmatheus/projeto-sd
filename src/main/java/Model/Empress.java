package Model;

import Auth.JwtService;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "empresa")
public class Empress {

    @Id
    @Column(name = "id", length = 36, nullable = false)
    private String id;
    @Column(name = "razao_social", nullable = false)
    private String razaoSocial;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "cnpj", nullable = false)
    private String cnpj;
    @Column(name = "senha", nullable = false)
    private String senha;
    @Column(name = "descricao", nullable = false)
    private String descricao;
    @Column(name = "ramo", nullable = false)
    private String ramo;
    @Column(name = "logado", nullable = false)
    private boolean logado;

    public Empress(String razaoSocial, String email, String cnpj, String senha, String descricao, String ramo) {
        this.id = String.valueOf(UUID.randomUUID());
        this.razaoSocial = razaoSocial;
        this.email = email;
        this.cnpj = cnpj;
        this.senha = JwtService.hashPassword(senha);
        this.descricao = descricao;
        this.ramo = ramo;
        this.logado = false;
    }

    public Empress() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getRamo() {
        return ramo;
    }

    public void setRamo(String ramo) {
        this.ramo = ramo;
    }

    public boolean isLogado() {
        return logado;
    }

    public void setLogado(boolean logado) {
        this.logado = logado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Empress empress = (Empress) o;
        return logado == empress.logado && Objects.equals(id, empress.id) && Objects.equals(razaoSocial, empress.razaoSocial) && Objects.equals(email, empress.email) && Objects.equals(cnpj, empress.cnpj) && Objects.equals(senha, empress.senha) && Objects.equals(descricao, empress.descricao) && Objects.equals(ramo, empress.ramo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, razaoSocial, email, cnpj, senha, descricao, ramo, logado);
    }

    @Override
    public String toString() {
        return "Empress{" +
                "id='" + id + '\'' +
                ", razaoSocial='" + razaoSocial + '\'' +
                ", email='" + email + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", senha='" + senha + '\'' +
                ", descricao='" + descricao + '\'' +
                ", ramo='" + ramo + '\'' +
                ", logado=" + logado +
                '}';
    }
}
