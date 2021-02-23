package diplomaProject.shop2.services;

import diplomaProject.shop2.dto.photo.PhotoResultDTO;
import diplomaProject.shop2.dto.results.ResultDTO;
import diplomaProject.shop2.model.Photo;
import diplomaProject.shop2.repos.PhotoRepository;
import diplomaProject.shop2.s3.AmazonS3Service;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource("/application-test.properties")
class PhotoServiceImplTest {

    @Autowired
    private PhotoService photoService;

    @MockBean
    private AmazonS3Service amazonS3Service;

    @MockBean
    private PhotoRepository photoRepository;


    @Test
    void savePhoto_whenMultipartFileContentTypeIsNotAllowedContentType_thenPhotoResultDto() {
        // given
        MultipartFile multipartFile = mock (MultipartFile.class);
        when (multipartFile.getContentType ()).thenReturn ("text/plain");

        // when
        PhotoResultDTO resultDTO = photoService.savePhoto (multipartFile);

        // then
        Assertions.assertFalse (resultDTO.isSuccess ());
        Assertions.assertFalse (resultDTO.getMessage ().isEmpty ());
        Assertions.assertFalse (resultDTO.getPhoto ().isPresent ());
    }

//    @Test
//    void savePhoto_whenErrorWhileConvertMultiPartToFile_thenPhotoResultDto() throws IOException {
//        // given
//        MultipartFile multipartFile = mock (MultipartFile.class);
//        when (multipartFile.getContentType ()).thenReturn ("image/png");
//        when (multipartFile.getOriginalFilename ()).thenReturn ("file");
//
//        when (multipartFile.getBytes ()).thenReturn (
//                "This is a dummy file content".getBytes(StandardCharsets.UTF_8)
//        );
//
//
//        // when
//        PhotoResultDTO resultDTO = photoService.savePhoto (multipartFile);
//
//        // then
//        Assertions.assertFalse (resultDTO.isSuccessResult ());
//        Assertions.assertFalse (resultDTO.getMessage ().isEmpty ());
//        Assertions.assertFalse (resultDTO.getPhoto ().isPresent ());
//    }

//    @Test
//    void savePhoto_whenS3ServiceResultNotSuccess_thenPhotoResultDto() {
//        // given
//        final MockMultipartFile multipartFile = new MockMultipartFile (
//                "file",
//                "file",
//                "image/png",
//                "This is a dummy file content".getBytes(StandardCharsets.UTF_8));
//
//        when (amazonS3Service.saveFile (any(File.class), any(String.class)))
//                .thenReturn (new S3ServiceResultDTO ());
//        //тут /thenThrow и ошибку)
//
//
//        // when
//        PhotoResultDTO resultDTO = photoService.savePhoto (multipartFile);
//
//        // then
//        Assertions.assertFalse (resultDTO.isSuccessResult ());
//        Assertions.assertFalse (resultDTO.getMessage ().isEmpty ());
//        Assertions.assertFalse (resultDTO.getPhoto ().isPresent ());
//
//        verify (amazonS3Service)
//                .saveFile (any(File.class), any(String.class));
//    }

    @Test
    void savePhoto_thenPhotoResultDto() {
        // given
        String fileSrc = "fileSrc";

        final MockMultipartFile multipartFile = new MockMultipartFile (
                "file",
                "file",
                "image/png",
                "This is a dummy file content".getBytes(StandardCharsets.UTF_8));

        when (amazonS3Service.saveFile (any(File.class), any(String.class)))
                .thenReturn (fileSrc);
        when (photoRepository.save (any (Photo.class))).thenReturn (new Photo ());

        // when
        PhotoResultDTO resultDTO = photoService.savePhoto (multipartFile);


        // then
        Assertions.assertTrue (resultDTO.isSuccess ());
        Assertions.assertTrue (resultDTO.getPhoto ().isPresent ());

        Assertions.assertFalse (resultDTO.getMessage ().isEmpty ());
        Assertions.assertEquals (
                "Save new photo to photoRepository complete",
                resultDTO.getMessage ()
        );

        verify (amazonS3Service)
                .saveFile (any(File.class), any(String.class));
        verify (photoRepository)
                .save (any (Photo.class));

    }

    @Test
    void deletePhotoById_whenPhotoNotFoundById () {
        // given
        Long photoId = 15L;

        // Setup our mocked service
        when (photoRepository.findById (ArgumentMatchers.eq (photoId)))
                .thenReturn (Optional.empty ());

        // when
        ResultDTO result = photoService.deletePhotoById (photoId);


        // than
        Assertions.assertTrue (
                result.getMessage ().contains ("Not found photo with id")
        );
        Assertions.assertFalse (result.isSuccess ());

        verify (photoRepository).findById (photoId);
    }

    @Test
    void deletePhotoById_whenSuccessResult () {
        // given
        Long photoId = 15L;
        Photo photo = new Photo ("src");

        // Setup our mocked service
        when (photoRepository.findById (ArgumentMatchers.eq (photoId)))
                .thenReturn (Optional.of (photo));

        // when
        ResultDTO result = photoService.deletePhotoById (photoId);


        // then
        Assertions.assertTrue (
                result.getMessage ().contains ("success removed")
        );
        Assertions.assertTrue (result.isSuccess ());

        verify (photoRepository).findById (photoId);
        verify (amazonS3Service).deleteFileByFileUrl (photo.getSrc ());
        verify (photoRepository).deleteById (photoId);
    }

    @Test
    void deletePhotos_thenSuccessResult () {
        // given
        Set<Photo> photos = new HashSet<> ();

        // when
        ResultDTO resultDTO = photoService.deletePhotos (photos);

        //then
        Assertions.assertTrue (resultDTO.isSuccess ());
        Assertions.assertEquals ("Photos success removed", resultDTO.getMessage ());

        verify (amazonS3Service).deleteFilesByFileUrls (ArgumentMatchers.eq (new String[0]));
        verify (photoRepository).deleteAll (photos);
    }


//    @BeforeEach
//    public void setup() {
//        //file
//        File file = new File (testPictureLocation);
//
//        //multipartFile
//        try {
//            multipartFile = new MockMultipartFile (
//                    file.getName (),
//                    file.getName (),
//                    "image/png",
//                    new FileInputStream (file));
//        } catch (IOException e) {
//            e.printStackTrace ();
//        }
//
//        //photo
//        photo = new Photo (){{
//            setId (10l);
//            setSrc (file.getPath ());
//        }};
//    }


//    @Test
//    void whenSavePhoto_thenOptionalWithPhoto () {
//        //give
//
//        //multipartFile
//        MultipartFile multipartFile = this.multipartFile;
//        //and
//        when(photoRepository.save (ArgumentMatchers.any (Photo.class)))
//                .thenReturn (this.photo);
//        //and
//        when(amazonS3Service.saveFile (
//                ArgumentMatchers.any (File.class),  ArgumentMatchers.anyString ()))
//                .thenReturn (true);
//
//
//        //when
//        final Optional<Photo> actualPhoto = photoService.savePhoto (multipartFile);
//
//
//        //then
//        Assertions.assertEquals (this.photo, actualPhoto.get ());
//
//        verify (amazonS3Service).saveFile (ArgumentMatchers.any (File.class), ArgumentMatchers.anyString ());
//        verify (photoRepository).save (ArgumentMatchers.any (Photo.class));
//    }
//
//
//    @Test
//    void whenSavePhoto_thenOptionalFileIsEmpty () {
//        //give
//
//        //file
//        File file = new File (testPictureLocation);
//        //and multipartFile
//        MultipartFile multipartFile = this.multipartFile;
//        //and mock
//
//
//
//
//        //when
//        final Optional<Photo> actualPhoto = photoService.savePhoto (multipartFile);
//
//    }
}