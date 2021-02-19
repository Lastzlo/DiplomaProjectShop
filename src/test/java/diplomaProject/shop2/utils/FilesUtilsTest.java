package diplomaProject.shop2.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;


class FilesUtilsTest {

    @Value("${testPicture.location}")
    private String testPictureLocation;

    private MultipartFile textMultipartFile;

    private MultipartFile pictureMultipartFile;

//    @BeforeEach
//    public void setup(){
//        textMultipartFile = new MockMultipartFile("fileThatDoesNotExists.txt",
//                "fileThatDoesNotExists.txt",
//                "text/plain",
//                "This is a dummy file content".getBytes(StandardCharsets.UTF_8));
//
//        pictureMultipartFile = new MockMultipartFile("fileThatDoesNotExists.jpg",
//                "fileThatDoesNotExists.jpg",
//                "image/png",
//                "This is a dummy file content".getBytes(StandardCharsets.UTF_8));
//    }


//    @Test
//    void whenConvertFileToMultipartFile_thenOptionalFile () {
//        //give
//        File file = new File (testPictureLocation);
//
//        //when
//        final Optional<MultipartFile> multipartFile = FilesUtils.convertFileToMultipartFile (file, file.getName ());
//
//        //then
//        Assertions.assertTrue (multipartFile.isPresent ());
//    }
//
//
//
//    @Test
//    void whenConvertFileToMultipartFile_thenOptionalEmpty () {
//        //give
//        File file = new File ("notFound");
//
//        //when
//        final Optional<MultipartFile> multipartFile = FilesUtils.convertFileToMultipartFile (file, file.getName ());
//
//        //then
//        Assertions.assertFalse (multipartFile.isPresent ());
//    }

//    @Test
//    void convertMultiPartToFile () {
//        File file = new File ("src/test/resources/forProductController/pictures/test.jpg");
//
//        try {
//            MultipartFile multipartFile = new MockMultipartFile (
//                    file.getName (),
//                    file.getName (),
//                    "image/png",
//                    new FileInputStream (file));
//
//
//        } catch (IOException e) {
//            e.printStackTrace ();
//
//        }
//
//
//    }

//    @Test
//    void convertMultipartFileToFile () {
//        //give
//        MultipartFile multipartFile = this.pictureMultipartFile;
//
//        //when
//        final Optional<File> file = FilesUtils.convertMultipartFileToFile (multipartFile);
//
//        //then
//        Assertions.assertTrue (file.isPresent ());
//    }
}