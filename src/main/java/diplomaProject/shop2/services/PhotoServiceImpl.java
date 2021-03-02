package diplomaProject.shop2.services;

import diplomaProject.shop2.dto.amazonS3.S3ServiceResultDTO;
import diplomaProject.shop2.dto.photo.PhotoResultDTO;
import diplomaProject.shop2.dto.results.BadResult;
import diplomaProject.shop2.dto.results.ResultDTO;
import diplomaProject.shop2.dto.results.SuccessResult;
import diplomaProject.shop2.model.AllowedPhotoContentType;
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
import java.util.*;

@Service
public class PhotoServiceImpl implements PhotoService {
    private static final Logger logger = LogManager.getLogger(PhotoService.class);

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private AmazonS3Service amazonS3Service;


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

        if (hasMultipartFileNeededArgumentsNotNull (multipartFile)) {

            if (isAllowedContentType (Objects.requireNonNull (multipartFile.getContentType ()))) {
                Optional<File> optionalFile = convertMultiPartToFile(multipartFile);

                if (optionalFile.isPresent ()){
                    File file = optionalFile.get ();

                    final String fileName = generateFileName(
                            Objects.requireNonNull (multipartFile.getOriginalFilename ()));

                    final S3ServiceResultDTO s3ServiceResult = amazonS3Service.saveFile (file, fileName);

                    file.delete ();

                    if(s3ServiceResult.getFileSrc ().isEmpty ()){
                        return new PhotoResultDTO (s3ServiceResult.getMessage ());
                    } else {
                        String savedFileSrc = s3ServiceResult.getFileSrc ();

                        final Photo photo = new Photo (savedFileSrc);
                        final Photo photoFromDB = photoRepository.save (photo);

                        final String message = "Save new photo to photoRepository complete";
                        logger.info (message);
                        return new PhotoResultDTO (message, Optional.of (photoFromDB));
                    }
                } else {
                    final String message = "Exception while converting MultiPart to File";
                    logger.warn (message);
                    return new PhotoResultDTO (message);
                }
            } else {
                final String message = "multipartFile is not AllowedContentType " +
                        "because multipartFile.ContentType = "+multipartFile.getContentType ();
                logger.warn (message);
                return new PhotoResultDTO (message);
            }
        } else {
            final String message = "multipartFile needed arguments is null";
            logger.warn (message);
            return new PhotoResultDTO (message);
        }
    }

    private boolean hasMultipartFileNeededArgumentsNotNull (MultipartFile multipartFile) {
        try {
            Objects.requireNonNull (multipartFile.getOriginalFilename (), "multipartFile.getOriginalFilename () should be not null");
            Objects.requireNonNull (multipartFile.getContentType (), "multipartFile.getContentType () should be not null");
            return true;
        } catch (NullPointerException e) {
            logger.warn (e);
            return false;
        }
    }

    private Optional<File> convertMultiPartToFile(MultipartFile multipartFile){
        File file = new File(
                Objects.requireNonNull (multipartFile.getOriginalFilename ()));

        try (OutputStream os = new FileOutputStream (file)) {
            os.write(multipartFile.getBytes());

            return Optional.of (file);
        } catch (IOException e) {
            logger.warn (e);
            return Optional.empty ();
        }
    }

    private boolean isAllowedContentType (String contentType){
        return AllowedPhotoContentType.isAllowedPhotoContentType (contentType);
    }

    private String generateFileName (String fileName) {
        return new Date ().getTime() + "-" + fileName.replace(" ", "_");
    }

    @Override
    public ResultDTO deletePhotos (Set<Photo> photos) {

        if(!photos.isEmpty ()){
            Set<String> photoUrls = new HashSet<> ();
            for (Photo photo: photos) {
                photoUrls.add (photo.getSrc ());
            }

            amazonS3Service.deleteFilesByFileUrls (photoUrls.toArray (new String[0]));

            photoRepository.deleteAll (photos);

            final String message = "Photos success removed";

            logger.info (message);
            return new SuccessResult (message);
        } else {
            final String message = "Photos is Empty";

            logger.warn (message);
            return new SuccessResult (message);
        }
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
            Photo photo = optionalPhoto.get ();

            amazonS3Service.deleteFileByFileUrl (photo.getSrc ());

            photoRepository.deleteById (photoId);

            final String message = "Photo with id = "+ photoId + " success removed";

            logger.info (message);
            return new SuccessResult (message);
        } else {
            final String message = "Not found photo with id = "+ photoId;

            logger.warn (message);
            return new BadResult (message);
        }
    }
}
