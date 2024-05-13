package Model;

public class ResponseEntity {

    private int status;
    private String operation;
    private String payload;

    public ResponseEntity(int status, String operation, String payload) {
        this.status = status;
        this.operation = operation;
        this.payload = payload;
    }

    public ResponseEntity() {
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "{" +
                "status=" + status +
                ", operation='" + operation + '\'' +
                ", payload='" + payload + '\'' +
                '}';
    }
}
