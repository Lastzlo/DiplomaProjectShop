package diplomaProject.shop2.dto.photo;

import diplomaProject.shop2.model.Photo;

import java.util.Optional;

public class PhotoResultDTO {
    private String description = "Failed to save photo";

    private Optional<Photo> photo = Optional.empty ();

    public PhotoResultDTO () {
    }

    public PhotoResultDTO (String description, Optional<Photo> photo) {
        this.description = description;
        this.photo = photo;
    }

    public String getDescription () {
        return description;
    }

    public void setDescription (String description) {
        this.description = description;
    }

    public Optional<Photo> getPhoto () {
        return photo;
    }

    public void setPhoto (Optional<Photo> photo) {
        this.photo = photo;
    }
}
