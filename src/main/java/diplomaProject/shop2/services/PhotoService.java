package diplomaProject.shop2.services;

import diplomaProject.shop2.dto.photo.PhotoResultDTO;
import org.springframework.web.multipart.MultipartFile;

public interface PhotoService {
    PhotoResultDTO savePhoto (MultipartFile multipartFile);
}
