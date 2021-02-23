package diplomaProject.shop2.services;

import diplomaProject.shop2.dto.photo.PhotoResultDTO;
import diplomaProject.shop2.dto.results.ResultDTO;
import diplomaProject.shop2.model.Photo;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

public interface PhotoService {
    PhotoResultDTO savePhoto (MultipartFile multipartFile);

    ResultDTO deletePhotos (Set<Photo> photos);
}
