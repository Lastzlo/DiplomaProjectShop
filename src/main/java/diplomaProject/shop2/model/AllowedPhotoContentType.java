package diplomaProject.shop2.model;

public enum AllowedPhotoContentType {
    Png("image/png"),
    Jpeg("image/jpeg");

    private final String contentType;

    AllowedPhotoContentType(String contentType){
        this.contentType = contentType;
    }

    public String getContentType () {
        return contentType;
    }

    public static boolean isAllowedPhotoContentType(String contentType){
        for (AllowedPhotoContentType contentType1: AllowedPhotoContentType.values ()){
           if (contentType1.getContentType ().equals (contentType)){
               return true;
           }
        }
        return false;
    }
}
