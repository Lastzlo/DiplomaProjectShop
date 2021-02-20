package diplomaProject.shop2.dto.amazonS3;

public class S3ServiceResultDTO {

    protected String message = "Error occurred while uploading file";

    protected String fileSrc = "";

    public S3ServiceResultDTO () {
        this.message = message;
    }

    public S3ServiceResultDTO (String message) {
        this.message = message;
    }

    public S3ServiceResultDTO (String message, String fileSrc) {
        this.message = message;
        this.fileSrc = fileSrc;
    }
    

    public String getMessage () {
        return message;
    }

    public void setMessage (String message) {
        this.message = message;
    }

    public String getFileSrc () {
        return fileSrc;
    }

    public void setFileSrc (String fileSrc) {
        this.fileSrc = fileSrc;
    }
}
