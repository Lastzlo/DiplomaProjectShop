package diplomaProject.shop2.services;

import diplomaProject.shop2.model.Photo;
import diplomaProject.shop2.repos.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class PhotoServiceImpl implements PhotoService {
    @Autowired
    private PhotoRepository photoRepository;


    @Override
    public Optional<Photo> savePhoto (MultipartFile multipartFile) {
        return Optional.empty ();
    }
}
