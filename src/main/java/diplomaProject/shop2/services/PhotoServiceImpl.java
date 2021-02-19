package diplomaProject.shop2.services;

import diplomaProject.shop2.dto.photo.PhotoResultDTO;
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
import java.util.Optional;

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
            return new PhotoResultDTO ("multipartFile is not AllowedContentType " +
                    "because multipartFile.ContentType = "+multipartFile.getContentType ());
        } else {
            Optional<File> optionalFile = convertMultiPartToFile(multipartFile);

            if (optionalFile.isPresent ()){
                File file = optionalFile.get ();

                String fileName = generateFileName(multipartFile.getOriginalFilename ());

                //code checkpoint


                return new PhotoResultDTO ();

            } else {
                return new PhotoResultDTO ("Error while converting MultiPart to File");
            }
        }
    }

    private Optional<File> convertMultiPartToFile(MultipartFile multipartFile){
        File file = new File(multipartFile.getOriginalFilename ());

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



}
