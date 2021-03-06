package diplomaProject.shop2.dto.photo;

import diplomaProject.shop2.model.Photo;

import java.util.Optional;

public class PhotoResultDTO {
    private String message = "Failed to save photo";

    private Optional<Photo> photo = Optional.empty ();

    public PhotoResultDTO () {
    }

    public PhotoResultDTO (String message) {
        this.message = message;
    }

    public PhotoResultDTO (String message, Optional<Photo> photo) {
        this.message = message;
        this.photo = photo;
    }

    public boolean isSuccess (){
        return photo.isPresent ();
    }

    public String getMessage () {
        return message;
    }

    public void setMessage (String message) {
        this.message = message;
    }

    public Optional<Photo> getPhoto () {
        return photo;
    }

    public void setPhoto (Optional<Photo> photo) {
        this.photo = photo;
    }
}
