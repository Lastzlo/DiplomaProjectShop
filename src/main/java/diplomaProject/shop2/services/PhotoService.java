package diplomaProject.shop2.services;

import diplomaProject.shop2.model.Photo;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface PhotoService {
    Optional<Photo> savePhoto (MultipartFile multipartFile);
}
