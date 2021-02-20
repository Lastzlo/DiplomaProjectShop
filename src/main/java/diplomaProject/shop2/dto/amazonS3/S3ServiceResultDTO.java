package diplomaProject.shop2.dto.amazonS3;

public class S3ServiceResultDTO {

    protected String nameOfResultDTO = "S3Service";

    protected String message = "Error occurred while uploading file";

    protected String fileSrc = "";

    public S3ServiceResultDTO () {
        this.message = generateMessage(this.message);
    }

    public S3ServiceResultDTO (String message) {
        this.message = generateMessage(message);
    }

    public S3ServiceResultDTO (String message, String fileSrc) {
        this.message = generateMessage(message);
        this.fileSrc = fileSrc;
    }

    protected String generateMessage (String message){
        return this.nameOfResultDTO + ": "+ message;
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
