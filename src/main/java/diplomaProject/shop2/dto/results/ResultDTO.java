package diplomaProject.shop2.dto.results;

public abstract class ResultDTO {
    protected String message = "OK";

    public ResultDTO() {
    }

    public ResultDTO(String message) {
        this.message = message;
    }

    public String getMessage () {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
