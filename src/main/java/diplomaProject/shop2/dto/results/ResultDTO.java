package diplomaProject.shop2.dto.results;

public abstract class ResultDTO {
    private String message = "OK";

    private boolean isSuccessResult = true;

    public ResultDTO() {
    }

    public boolean isSuccess () {
        return isSuccessResult;
    }

    public ResultDTO(String message) {
        this.message = message;
    }

    public ResultDTO (String message, boolean isSuccessResult) {
        this.message = message;
        this.isSuccessResult = isSuccessResult;
    }

    public String getMessage () {
        return message;
    }
}
