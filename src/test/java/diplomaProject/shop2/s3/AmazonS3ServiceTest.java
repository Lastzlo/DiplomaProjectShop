package diplomaProject.shop2.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import diplomaProject.shop2.dto.amazonS3.S3ServiceResultDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.File;

import static org.mockito.Mockito.*;

@SpringBootTest
class AmazonS3ServiceTest {

    private static final String KEY_NAME = "picture.jpg";
    private static final String SAVE_COMPLETE = "Save file to Amazon S3 complete";
    private static final String EXCEPTION_WHILE_SAVING = "Exception while saving file to Amazon S3";

    @Autowired
    private AmazonS3Service amazonS3Service;

    @MockBean
    private AmazonS3 amazonS3Client;


    @Test
    void saveFile() {
        // given
        File file = mock(File.class);
        when(file.getName()).thenReturn(KEY_NAME);

        // when
        S3ServiceResultDTO s3ServiceResult = amazonS3Service.saveFile (file, file.getName ());

        //then

        Assertions.assertTrue (s3ServiceResult.isSuccess ());
        Assertions.assertTrue (s3ServiceResult.getFileSrc ().contains (file.getName ()));
        Assertions.assertEquals (s3ServiceResult.getMessage (), SAVE_COMPLETE);

        verify(amazonS3Client).putObject(ArgumentMatchers.any (PutObjectRequest.class));
    }
}