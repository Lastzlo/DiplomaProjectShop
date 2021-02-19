package diplomaProject.shop2.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class FilesUtils {
    private static final Logger logger = LogManager.getLogger(FilesUtils.class);

//    public static Optional<File>  convertMultipartFileToFile (MultipartFile multipartFile){
//        //путь для сохранения
//        File convertedFile = new File(multipartFile.getOriginalFilename ());
//        try (FileOutputStream fos = new FileOutputStream(convertedFile)){
//            fos.write(multipartFile.getBytes());
//            return Optional.of (convertedFile);
//        } catch (IOException e) {
//            logger.info (e);
//            return Optional.empty ();
//        }
//    }
//
//    public static String generateFileName (String fileName) {
//        return new Date ().getTime() + "-" + fileName.replace(" ", "_");
//    }
}
