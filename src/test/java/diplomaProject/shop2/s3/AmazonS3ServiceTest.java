package diplomaProject.shop2.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import diplomaProject.shop2.dto.amazonS3.S3ServiceResultDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.File;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
class AmazonS3ServiceTest {

    private static final String BUCKET_NAME = "bucket_name";
    private static final String KEY_NAME = "picture.jpg";

    @Autowired
    private AmazonS3Service amazonS3Service;

    @MockBean
    private AmazonS3 amazonS3Client;



    @Test
    void saveFile_whenErrorWhileUploadingFile_ThenResultDTOIsSuccessResultFALSE () {
        //given
        File file = mock(File.class);
        PutObjectResult s3Result = mock(PutObjectResult.class);


        when(file.getName()).thenReturn(KEY_NAME);
        when(amazonS3Client.putObject(anyString(), anyString(), (File) any())).thenReturn(s3Result);

        S3ServiceResultDTO resultDTO = amazonS3Service.saveFile (file, file.getName ());

        //then
        Assertions.assertFalse (resultDTO.isSuccessResult ());

        verify(amazonS3Client).putObject(ArgumentMatchers.any (PutObjectRequest.class));
    }
}