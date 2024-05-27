package Model.ResponseEntities;

public class TokenResponseEntity {

    private int status;
    private String operacao;
    private String token;

    public TokenResponseEntity(int status, String operacao, String token) {
        this.status = status;
        this.operacao = operacao;
        this.token = token;
    }

    public TokenResponseEntity() {
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getoperacao() {
        return operacao;
    }

    public void setoperacao(String operacao) {
        this.operacao = operacao;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "TokenResponseEntity{" +
                "status=" + status +
                ", operacao='" + operacao + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
