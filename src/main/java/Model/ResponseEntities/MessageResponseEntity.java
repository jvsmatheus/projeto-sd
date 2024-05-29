package Model.ResponseEntities;

public class MessageResponseEntity {

    private int status;
    private String operacao;
    private String menssagem;

    public MessageResponseEntity(int status, String operacao, String menssagem) {
        this.status = status;
        this.operacao = operacao;
        this.menssagem = menssagem;
    }

    public MessageResponseEntity() {
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

    public String getmenssagem() {
        return menssagem;
    }

    public void setmenssagem(String menssagem) {
        this.menssagem = menssagem;
    }

    @Override
    public String toString() {
        return "{" +
                "status=" + status +
                ", operacao='" + operacao + '\'' +
                ", menssagem='" + menssagem + '\'' +
                '}';
    }
}
