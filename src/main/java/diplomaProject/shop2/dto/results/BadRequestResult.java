package diplomaProject.shop2.dto.results;

public class BadRequestResult extends ResultDTO {
    public BadRequestResult() {
        super("JSON deserialization failed");
    }
}
