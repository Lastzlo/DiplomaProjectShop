package diplomaProject.shop2.s3;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;
import diplomaProject.shop2.dto.amazonS3.S3ServiceResultDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.concurrent.Executors;

@Service
public class AmazonS3ServiceImpl implements AmazonS3Service{

    private static final Logger logger = LogManager.getLogger(AmazonS3Service.class);

    @Autowired
    AmazonS3 amazonS3Client;

    @Value("${amazonProperties.endpointUrl}")
    private String endpointUrl;
    @Value("${amazonProperties.bucketName}")
    private String bucketName;


    @Override
    public S3ServiceResultDTO saveFile (File file, String fileName) {

        TransferManager tm = TransferManagerBuilder
                .standard()
                .withS3Client(amazonS3Client)
                .withMultipartUploadThreshold((long) (5 * 1024 * 1024))
                .withExecutorFactory(() -> Executors.newFixedThreadPool(5))
                .build();

        PutObjectRequest request =
                new PutObjectRequest(bucketName, fileName, file)
                        //allow to receive the file from the link
                        .withCannedAcl(CannedAccessControlList.PublicRead);

        Upload upload = tm.upload(request);

        try {
            upload.waitForCompletion();

            String message = "Upload file complete";
            String fileSrc = getPathByFileName (fileName);

            logger.info (message);
            return new S3ServiceResultDTO (message, fileSrc);
        } catch (AmazonClientException | InterruptedException e) {
            e.printStackTrace();
            String message = "Error occurred while uploading file";

            logger.info (message);
            return new S3ServiceResultDTO (message);
        }
    }

    private String getPathByFileName (String fileName) {
        return endpointUrl + "/" + bucketName + "/" + fileName;
    }

}
