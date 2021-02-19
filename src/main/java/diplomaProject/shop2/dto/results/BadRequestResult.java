package diplomaProject.shop2.dto.results;

public class BadRequestResult extends ResultDTO {
    public BadRequestResult() {
        super("BAD REQUEST");
    }

    public BadRequestResult (String description) {
        super (description);
    }
}
