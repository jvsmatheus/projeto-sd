package Model.ResponseEntities;

public class ResponseEntity {

    private int status;
    private String operacao;

    public ResponseEntity(int status, String operacao) {
        this.status = status;
        this.operacao = operacao;
    }

    public ResponseEntity() {
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }

    @Override
    public String toString() {
        return "ResponseEntity{" +
                "status=" + status +
                ", operacao='" + operacao + '\'' +
                '}';
    }
}
