package diplomaProject.shop2.services;

import diplomaProject.shop2.model.Photo;
import diplomaProject.shop2.repos.PhotoRepository;
import diplomaProject.shop2.s3.AmazonS3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.multipart.MultipartFile;

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