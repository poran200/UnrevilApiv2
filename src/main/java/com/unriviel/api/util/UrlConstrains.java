package com.unriviel.api.util;

public final class  UrlConstrains {
    private UrlConstrains() { }
    private static final String API = "/api";
    private static  final String VERSION = "/v1";
   public static class FileManagement{
       private  FileManagement() { }
       public static final String ROOT = API+VERSION+"/image";
       public static final String UPLOAD = "/upload";
       public static  final String FIND_BY_ID= "/{id}";
   }
   public static class UserManagement {
       private UserManagement() { }
       public static final String ROOT = API+VERSION+"/signup";
       public static final String ADMIN_SIGNUP = "/admin";
       public static final String REVIEWER_SIGNUP = "/reviewer";
       public static final String INFLUENCER_SIGNUP = "/influencer";
   }


    public class LogIn {
        private LogIn() {
        }
        public static final String ROOT = API+VERSION;
        public static final String LOGIN = "/login";

    }

    public static final class ProfileManagement {
        public static final String ROOT = API+VERSION+"/profile";
        public static final String FIND_BY_USER_NAME= "/{userEmail}";
        public static  final String CREATE_PROFILE = "/{userEmail}";
        public static final String FIND_REVIEWER = "/" ;
        public static final String UPDATE = "/update/{userEmail}";
    }

    public class ReleventQs {
        public static final String ROOT = API+VERSION+"/questions";
        public static final String All ="/";
    }

    public static final class UploadConfig {
        public static final String ROOT = API+VERSION+"/config";
        public static final String GET_BY_ID = "/{id}";


        public static final String CREATE = "/";
    }
}
