package diplomaProject.shop2.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import diplomaProject.shop2.dto.amazonS3.S3ServiceResultDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class AmazonS3ServiceImpl implements AmazonS3Service{

    private static final Logger logger = LogManager.getLogger(AmazonS3Service.class);

    @Autowired
    AmazonS3 amazonS3Client;

    @Value("${amazonProperties.endpointUrl}")
    private String endpointUrl;
    @Value("${amazonProperties.bucketName}")
    private String bucketName;

    private static final String SAVE_COMPLETE = "Save file to Amazon S3 complete";
    private static final String EXCEPTION_WHILE_SAVING = "Exception while saving file to Amazon S3";


//    @Override
//    public S3ServiceResultDTO saveFile (File file, String fileName) {
//
//        TransferManager tm = TransferManagerBuilder
//                .standard()
//                .withS3Client(amazonS3Client)
//                .withMultipartUploadThreshold((long) (5 * 1024 * 1024))
//                .withExecutorFactory(() -> Executors.newFixedThreadPool(5))
//                .build();
//
//        PutObjectRequest request =
//                new PutObjectRequest(bucketName, fileName, file)
//                        //allow to receive the file from the link
//                        .withCannedAcl(CannedAccessControlList.PublicRead);
//
//        Upload upload = tm.upload(request);
//
//        try {
//            upload.waitForCompletion();
//
//            String message = "Upload file complete";
//            String fileSrc = getPathByFileName (fileName);
//
//            logger.info (message);
//            return new S3ServiceResultDTO (message, fileSrc);
//        } catch (AmazonClientException | InterruptedException e) {
//            e.printStackTrace();
//            String message = "Error occurred while uploading file";
//
//            logger.info (message);
//            return new S3ServiceResultDTO (message);
//        }
//    }

//    @Override
//    public S3ServiceResultDTO saveFile (File file, String fileName) {
//        try {
//            amazonS3Client.putObject(new PutObjectRequest (bucketName, fileName, file)
//                    .withCannedAcl(CannedAccessControlList.PublicRead));
//
//            String message = "Upload file complete";
//            String fileSrc = getPathByFileName (fileName);
//
//            logger.info (message);
//            return new S3ServiceResultDTO (message, fileSrc);
//        } catch (AmazonClientException e) {
//            e.printStackTrace();
//            String message = "AmazonClientException while uploading file";
//
//            logger.info (message);
//            return new S3ServiceResultDTO (message);
//        }
//    }

    @Override
    public S3ServiceResultDTO saveFile (File file, String fileName) {

        try {
            amazonS3Client.putObject(new PutObjectRequest (bucketName, fileName, file)
                    .withCannedAcl(CannedAccessControlList.PublicRead));

            String fileSrc = getPathByFileName (fileName);

            String message = SAVE_COMPLETE;
            logger.info (message);
            return new S3ServiceResultDTO (message, fileSrc);
        } catch (AmazonS3Exception e){
            logger.warn (e);

            String message = EXCEPTION_WHILE_SAVING;
            logger.info (message);
            return new S3ServiceResultDTO (message);
        }


    }

    private String getPathByFileName (String fileName) {
        return endpointUrl + "/" + bucketName + "/" + fileName;
    }

    @Override
    public void deleteFileByFileUrl (String fileUrl) {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        amazonS3Client.deleteObject(new DeleteObjectRequest (bucketName, fileName));

        String message = "Delete File By File Url complete";
        logger.info (message);
    }

    @Override
    public void deleteFilesByFileUrls (String[] filesUrls) {
        DeleteObjectsRequest delObjReq = new DeleteObjectsRequest(bucketName)
                .withKeys(filesUrls);
        amazonS3Client.deleteObjects(delObjReq);

        String message = "Delete Files By File Urls complete";
        logger.info (message);
    }
}
