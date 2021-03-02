package diplomaProject.shop2.dto.amazonS3;

public class S3ServiceResultDTO {

    protected String message = "Exception occurred while uploading file to Amazon S3";

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

    public boolean isSuccess (){
        return !fileSrc.isEmpty ();
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
