package diplomaProject.shop2.services;

import diplomaProject.shop2.dto.photo.PhotoResultDTO;
import diplomaProject.shop2.model.Photo;
import diplomaProject.shop2.repos.PhotoRepository;
import diplomaProject.shop2.s3.AmazonS3Service;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;

@SpringBootTest
@TestPropertySource("/application-test.properties")
class PhotoServiceImplTest {

    @Autowired
    private PhotoService photoService;

    @MockBean
    private AmazonS3Service amazonS3Service;

    @MockBean
    private PhotoRepository photoRepository;

    @Value("${testPicture.location}")
    private String testPictureLocation;


    private MultipartFile multipartFile;

    private Photo photo;


    @Test
    void savePhoto_whenMultipartFileContentTypeIsNotAllowedContentType_thenPhotoResultDto() {
        // given
        final MockMultipartFile multipartFile = new MockMultipartFile (
                "file",
                "file",
                "text/plain",
                "This is a dummy file content".getBytes(StandardCharsets.UTF_8));

        // when
        PhotoResultDTO resultDTO = photoService.savePhoto (multipartFile);

        // then
        Assertions.assertFalse (resultDTO.getDescription ().isEmpty ());
        Assertions.assertFalse (resultDTO.getPhoto ().isPresent ());
    }

    @Test
    void savePhoto_whenErrorWhileConvertMultiPartToFile_thenPhotoResultDto() {
        // given
        final MockMultipartFile multipartFile = new MockMultipartFile (
                "file",
                "file",
                "image/png",
                "This is a dummy file content".getBytes(StandardCharsets.UTF_8));

        // when
        PhotoResultDTO resultDTO = photoService.savePhoto (multipartFile);

        // then
        Assertions.assertFalse (resultDTO.getDescription ().isEmpty ());
        Assertions.assertFalse (resultDTO.getPhoto ().isPresent ());
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