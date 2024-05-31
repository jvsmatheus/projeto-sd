package Model.ResponseEntities;

public class MessageResponseEntity {

    private int status;
    private String operacao;
    private String mensagem;

    public MessageResponseEntity(int status, String operacao, String mensagem) {
        this.status = status;
        this.operacao = operacao;
        this.mensagem = mensagem;
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

    public String getmensagem() {
        return mensagem;
    }

    public void setmensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    @Override
    public String toString() {
        return "{" +
                "status=" + status +
                ", operacao='" + operacao + '\'' +
                ", mensagem='" + mensagem + '\'' +
                '}';
    }
}
