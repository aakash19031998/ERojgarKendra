package com.twocoms.rojgarkendra.global.model;


public class AppConstant {

    //DEV
    public static final String BASE_URL ="https://dev.2coms.com/erkapi/api/" ;

    //PRODUCTION
    public static final String BASE_URL_PROD ="https://dev.2coms.com/erkapi/api/" ;

    public static final String VERIFY_MOB_NO =BASE_URL+"contact_no" ;

    public static final String VERIFY_OTP =BASE_URL+"verify_otp" ;

    public static final String RESEND_OTP =BASE_URL+"resend_otp" ;

    public static final String CREATE_USER =BASE_URL+"create_user" ;



    public static final String IS_VERIFIED_MAIN = "isverifiedmain";
    public static final String SOMETHING_WENT_WRONG = "Something went wrong please try again...!!!";
    public static final String KEY_NAME ="name" ;
    public static final String KEY_USER_ID ="user_id" ;

    public static final String KEY_EMAIL_ID ="email" ;
    public static final String KEY_CITY ="city" ;
    public static final String KEY_COUNTRY ="country" ;
    public static final String KEY_MOBILE_NUM ="mobile_no" ;
    public static final String KEY_PREFIX_TITLE ="prefix_title" ;

    public static final String KEY_LOCATION ="location" ;
    public static final String KEY_DEVICE_ID ="device_id" ;
    public static final String KEY_OS_TYPE ="os_type" ;
    public static final String KEY_CLIENT_TYPE ="client_type" ;
    public static final String KEY_OTP_NUMBER ="otp" ;
    public static final int TIME_OUT = 5000;
    public static final String NOTIFICATION_SWITCH = "notification_swich";
    public static final String DEVICE_TOKEN ="device_token" ;
    public static int SPLASH_SCREEN_TIMER = 500;
    public static final String SUCCESS = "SUCCESS";
    public static final String EXIT_TEXT = "Are you sure you want to close the app?";
    public static final String LOGOUT_TEXT = "Are you sure you want to logout from the app?";



    public static final String ROBOTO_BOLD = "robotobold.ttf";

    public static final String ROBOTO_BOLD_CONDENSED = "robotoboldcondensed.ttf";

    public static final String ROBOTO_BOLD_ITALIC = "robotobolditalic.ttf";

    public static final String ROBOTO_CONDENSED = "robotocondensed.ttf";

    public static final String ROBOTO_ITALIC = "robotoitalic.ttf";

    public static final String ROBOTO_LIGHT = "robotolight.ttf";

    public static final String ROBOTO_MEDIUM = "robotomedium.ttf";

    public static final String ROBOTO_MEDIUM_ITALIC = "robotomediumitalic.ttf";

    public static final String ROBOTO_REGULAR = "robotoregular.ttf";

    public static final String ROBOTO_THIN  = "robotothin.ttf";


    public static final String TOPIC_GLOBAL = "global";

    // broadcast receiver intent filters
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String PUSH_NOTIFICATION = "pushNotification";

    // id to handle the notification in the notification tray
    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;

    public static final String SHARED_PREF = "ah_firebase";


}
