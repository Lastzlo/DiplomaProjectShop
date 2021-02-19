package diplomaProject.shop2.dto.results;

public class SuccessResult extends ResultDTO {
    public SuccessResult () {
        super ("OK");
    }

    public SuccessResult (String description) {
        super (description);
    }
}
