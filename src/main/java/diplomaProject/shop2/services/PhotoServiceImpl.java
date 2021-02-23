package diplomaProject.shop2.services;

import diplomaProject.shop2.dto.photo.PhotoResultDTO;
import diplomaProject.shop2.dto.results.BadResult;
import diplomaProject.shop2.dto.results.ResultDTO;
import diplomaProject.shop2.model.Photo;
import diplomaProject.shop2.repos.PhotoRepository;
import diplomaProject.shop2.s3.AmazonS3Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
public class PhotoServiceImpl implements PhotoService {
    private static final Logger logger = LogManager.getLogger(PhotoService.class);

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private AmazonS3Service amazonS3Service;

    private final String ALLOWED_CONTENT_TYPE = "image/png";


//    @Override
//    public Optional<Photo> savePhoto (MultipartFile multipartFile) {
//        if(isAllowedContentType (multipartFile.getContentType ())){
//
//
//        } else {
//
//        }
//        return Optional.empty ();
//    }


    @Override
    public PhotoResultDTO savePhoto (MultipartFile multipartFile) {
        if (!isAllowedContentType (multipartFile.getContentType ())) {
            final String message = "multipartFile is not AllowedContentType " +
                    "because multipartFile.ContentType = "+multipartFile.getContentType ();
            logger.info (message);
            return new PhotoResultDTO (message);
        } else {
            Optional<File> optionalFile = convertMultiPartToFile(multipartFile);

            if (optionalFile.isPresent ()){
                File file = optionalFile.get ();

                final String fileName = generateFileName(
                        Objects.requireNonNull (multipartFile.getOriginalFilename ()));

                final String savedFileSrc = amazonS3Service.saveFile (file, fileName);

                file.delete ();

                final Photo photo = new Photo (savedFileSrc);
                final Photo photoFromDB = photoRepository.save (photo);

                final String message = "Save new photo to photoRepository complete";
                logger.info (message);
                return new PhotoResultDTO (message, Optional.of (photoFromDB));
            } else {
                final String message = "Error while converting MultiPart to File";
                logger.info (message);
                return new PhotoResultDTO (message);
            }
        }
    }

    private Optional<File> convertMultiPartToFile(MultipartFile multipartFile){
        File file = new File(
                Objects.requireNonNull (multipartFile.getOriginalFilename ()));

        try (OutputStream os = new FileOutputStream (file)) {
            os.write(multipartFile.getBytes());

            return Optional.of (file);
        } catch (IOException e) {
            e.printStackTrace ();
            return Optional.empty ();
        }
    }

    private boolean isAllowedContentType (String contentType){
        return contentType.equals (ALLOWED_CONTENT_TYPE);
    }

    private String generateFileName (String fileName) {
        return new Date ().getTime() + "-" + fileName.replace(" ", "_");
    }

    @Override
    public ResultDTO deletePhotos (Set<Photo> photos) {
        return null;
    }

//    @Override
//    public Optional<Photo> savePhoto (MultipartFile multipartFile) {
//        Optional<File> optionalFile = FilesUtils.convertMultipartFileToFile (multipartFile);
//
//        if(optionalFile.isPresent ()){
//            File file = optionalFile.get ();
//
//            final String newFileName = FilesUtils.generateFileName(file.getName ());
//
//            boolean resultOfSaveFile = amazonS3Service.saveFile(file, newFileName);
//
//            if(resultOfSaveFile){
//                final String amazonS3FilePath = amazonS3Service
//                        .getAbsolutPathByFileName(newFileName);
//
//                Photo photo = new Photo (){{ setSrc (amazonS3FilePath);}};
//                photo = photoRepository.save (photo);
//
//                return Optional.of (photo);
//            } else {
//                logger.info ("resultOfSaveFile is "+resultOfSaveFile);
//                return Optional.empty ();
//            }
//        } else {
//            logger.info ("optionalFile is "+optionalFile);
//            return Optional.empty ();
//        }
//    }


    @Override
    public ResultDTO deletePhotoById (Long photoId) {
        Optional<Photo> optionalPhoto = photoRepository.findById (photoId);

        if(optionalPhoto.isPresent ()){
            return null;
        } else {
            final String message = "Not found photo with id = "+ photoId;

            logger.info (message);
            return new BadResult (message);
        }
    }
}
