package com.twocoms.rojgarkendra.global.model;


import android.view.View;

public class AppConstant {

    //DEV
    public static final String BASE_URL = "https://dev.2coms.com/erkapi/api/";

    //PRODUCTION
    public static final String BASE_URL_PROD = "https://dev.2coms.com/erkapi/api/";


    public static final String VERIFY_MOB_NO = BASE_URL + "contact_no";
    public static final String VERIFY_OTP = BASE_URL + "verify_otp";
    public static final String RESEND_OTP = BASE_URL + "resend_otp";
    public static final String CREATE_USER = BASE_URL + "create_user";
    public static final String GET_STATE = BASE_URL + "states";
    public static final String GET_USER_DETAILS = BASE_URL + "candidate_detail/";
    public static final String UPDATE_USER_DETAILS = BASE_URL + "update_user/";
    public static final String GET_HOT_JOBS = BASE_URL + "jobs";
    public static final String GET_ALL_JOBS = BASE_URL + "jobs";

    public static final String GET_POPULAR_JOBS = BASE_URL + "popularjob";



    public static final String GET_APPLIED_AND_UPCOMING_INTERVIEW = BASE_URL + "interview";
    public static final String ADD_STUDENT_REMARK= BASE_URL + "student_remark";

    public static final String GET_JOBS_DETAIL = BASE_URL + "job_details/";
    public static final String APPLY_ALL_JOBS = BASE_URL + "applyJob";
    public static final String GET_ALL_TESTIMONIALS = BASE_URL + "testimonial";
    public static final String SOMETHING_WENT_WRONG = "Something went wrong please try again...!!!";

    public static final String JOB_APPLIED_SUCCESSFULLY_MESSAGE = "Job applied successfully!";
    public static final String KEY_NAME = "name";
    public static final String KEY_CONTACT = "contact";
    public static final String KEY_CONTACT_VERIFIED = "contact_verification";
    public static final String KEY_EMAIL_ID = "email";
    public static final String STATE_ID = "state";
    public static final String KEY_CITY = "city";
    public static final String KEY_DOB = "dob";
    public static final String KEY_GENDER = "gender";
    public static final String KEY_QUALIFICATION_TYPE = "qualification_type";
    public static final String KEY_EXPERIANCE_YEARS = "years_experience";
    public static final String KEY_EXPERIANCE_MONTH = "months_experience";
    public static final String KEY_SALARY = "salary";
    public static final String KEY_REFERAL_CODE = "referal_code";
    public static final String KEY_WALLET_AMOUNT = "wallet_amount";
    public static final String KEY_DEVICE_ID = "device_id";
    public static final String KEY_NOTIFICATION_ID = "notification_id";
    public static final String KEY_OS_TYPE = "os_type";
    public static final String KEY_USER_ID = "id";
    public static final String KEY_IS_REGISTER = "is_register";
    public static final String KEY_IS_EDURP = "eduErp";
    public static final String KEY_COURSE_NAME = "course_name";
    public static final String KEY_PROJECT_NAME = "project_name";
    public static final String KEY_BATCH_NAME = "batch_number";
    public static final String KEY_CENTRE_NAME = "center_name";
    public static final String KEY_COURSE_ID = "course_id";
    public static final String KEY_PROJECT_ID = "project_id";
    public static final String KEY_BATCH_ID = "batch_id";
    public static final String KEY_CENTRE_ID = "center_id";
    public static final String KEY_CENTRE_ID_2 = "centre_id";
    public static final String KEY_INVITE_CODE = "invite_code";
    public static final String KEY_DEVICE_TOKEN = "device_token";
    public static final String KEY_PROFILE_URL = "profile_url";
    public static final String KEY_RESUME_URL = "resume_url";
    public static final String KEY_RESUME = "resume";
    public static final String KEY_PROFILE_PHOTO = "profile_photo";
    public static final String KEY_OTP = "otp";
    public static final String KEY_STATE_NAME = "state_name";
    public static final String KEY_LANGUAGE_KNOWN = "language_known";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_DATA = "data";
    public static final String KEY_ = "otp";
    public static final String KEY_ROLE = "role";

    public static final String ID_JOBS = "JOBS";
    public static final String ID_INTERVIEW = "INTERVIEW";
    public static final String ID_MY_DOCUMENTS = "MYDOCUMENTS";
    public static final String ID_SUCCESS_STORIES = "SUCCESSSTORIES";
    public static final String ID_GOODIES_STORE = "GOODIESSTORES";
    public static final String ID_LOGOUT = "LOGOUT";
    public static final String ID_MATCHING_VACANCIES = "MATCHINGVACANCIES";
    public static final String ID_ALL_JOBS = "ALLJOBS";
    public static final String ID_HOT_JOBS = "HOTJOBS";
    public static final String ID_JOBS_APPLIED_BY_BATCHMATES = "JOBSBYBATCHMATE";
    public static final String ID_POPULARJOBS = "POPULARJOBS";
    public static final String ID_UPCOMING_INTERVIEW = "UPCOMINGINTERVIEW";
    public static final String ID_ALL_APPLIED_APPLICATION = "APPLIEDAPPLICATION";
    public static final String ID_MY_GOODIES_STORES = "MYGOODIESSTORES";
    public static final String ID_MY_ORDERS = "MYORDERS";
    public static final String NAME_JOBS = "Job Board";
    public static final String NAME_INTERVIEW = "Interview";
    public static final String NAME_MY_DOCUMENTS = "My Documents";
    public static final String NAME_SUCCESS_STORIES = "Success Stories";
    public static final String NAME_GOODIES_STORE = "Goodies Store";
    public static final String NAME_LOGOUT = "Logout";
    public static final String NAME_MATCHING_VACANCIES = "Matching Vacancies";
    public static final String NAME_ALL_JOBS = "All Jobs";
    public static final String NAME_HOT_JOBS = "Hot Jobs";
    public static final String NAME_JOBS_APPLIED_BY_BATCHMATES = "Most Applied Jobs";
    public static final String NAME_POPULARJOBS = "Popular Jobs";
    public static final String NAME_UPCOMING_INTERVIEW = "Upcoming Interviews";
    public static final String NAME_ALL_APPLIED_APPLICATION = "All Applied Applications";
    public static final String NAME_MY_GOODIES_STORES = "EduErp Goodies Store";
    public static final String NAME_MY_ORDERS = "My Orders";
    public static final String EXIT_TEXT = "Are you sure you want to close the app?";
    public static final String LOGOUT_TEXT = "Are you sure you want to logout from the app?";
    public static final String SIGN_UP_LOGIN_TEXT = "Please Login / Sign Up to use the application.";
    public static final String KEY_FILTER_GENDER_ALL_JOBS = "alljobsfiltergender";
    public static final String KEY_FILTER_CITY_ALL_JOBS = "alljobsfiltercity";
    public static final String KEY_FILTER_SKILLS_ALL_JOBS = "alljobsfilterskills";
    public static final String KEY_FILTER_LANGUAGE_ALL_JOBS = "alljobsfilterlanguage";
    public static final String KEY_FILTER_QUALIFICATION_TYPE_ALL_JOBS = "alljobsfilterqualification";
    public static final String COMING_FROM_ALL_JOBS = "ALLJOBS";

    public static final String KEY_FILTER_GENDER_HOT_JOBS = "hotjobsfiltergender";
    public static final String KEY_FILTER_CITY_HOT_JOBS = "hotjobsfiltercity";
    public static final String KEY_FILTER_SKILLS_HOT_JOBS = "hotjobsfilterskills";
    public static final String KEY_FILTER_LANGUAGE_HOT_JOBS = "hotjobsfilterlanguage";
    public static final String KEY_FILTER_QUALIFICATION_TYPE_HOT_JOBS = "hotjobsfilterqualification";
    public static final String COMING_FROM_HOT_JOBS = "HOTJOBS";

    public static final String KEY_FILTER_GENDER_MATCHING_JOBS = "matchingjobsfiltergender";
    public static final String KEY_FILTER_CITY_MATCHING_JOBS = "matchingjobsfiltercity";
    public static final String KEY_FILTER_SKILLS_MATCHING_JOBS = "matchingjobsfilterskills";
    public static final String KEY_FILTER_LANGUAGE_MATCHING_JOBS = "matchingjobsfilterlanguage";
    public static final String KEY_FILTER_QUALIFICATION_TYPE_MATCHING_JOBS = "matchingjobsfilterqualification";
    public static final String COMING_FROM_MATCHING_JOBS = "MATCHINGJOBS";

    public static final String KEY_FILTER_GENDER_POPULAR_JOBS = "popularjobsfiltergender";
    public static final String KEY_FILTER_CITY_POPULAR_JOBS = "popularjobsfiltercity";
    public static final String KEY_FILTER_SKILLS_POPULAR_JOBS = "popularjobsfilterskills";
    public static final String KEY_FILTER_LANGUAGE_POPULAR_JOBS = "popularjobsfilterlanguage";
    public static final String KEY_FILTER_QUALIFICATION_TYPE_POPULAR_JOBS = "popularjobsfilterqualification";
    public static final String COMING_FROM_POPULAR_JOBS = "POPULARJOBS";

    public static final String KEY_FILTER_GENDER_BATCH_MATES_JOBS = "batchmatesularjobsfiltergender";
    public static final String KEY_FILTER_CITY_BATCH_MATES_JOBS = "batchmatesularjobsfiltercity";
    public static final String KEY_FILTER_SKILLS_BATCH_MATES_JOBS = "batchmatesularjobsfilterskills";
    public static final String KEY_FILTER_LANGUAGE_BATCH_MATES_JOBS = "batchmatesularjobsfilterlanguage";
    public static final String KEY_FILTER_QUALIFICATION_TYPE_BATCH_MATES_JOBS = "batchmatesularjobsfilterqualification";
    public static final String COMING_FROM_BATCH_MATES_JOBS = "BATCHMATESJOBS";


    public static final String KEY_JOB_DATA_MESSAGE = "message";
    public static final String KEY_JOB_DATA_SUCCESS = "success";
    public static final String KEY_JOB_DATA_OBJ_DATA = "data";
    public static final String KEY_JOB_DATA_ARRAY_DATA = "data";
    public static final String KEY_JOB_DATA_NO_OF_ENTRIES = "total";
    public static final String KEY_JOB_DATA_PER_PAGE = "per_page";
    public static final String KEY_JOB_DATA_ID = "id";
    public static final String KEY_JOB_DATA_SALARY = "salary";
    public static final String KEY_JOB_DATA_JOB_TYPE = "job_type";
    public static final String KEY_JOB_DATA_CLIENT_NAME = "client_name";
    public static final String KEY_JOB_DATA_NO_OPEN_POSITION = "number_of_open_positions";
    public static final String KEY_JOB_DATA_WORK_LOCATION = "region_of_work_location_in_india";
    public static final String KEY_JOB_DATA_CREATED_ON = "created_on";
    public static final String KEY_JOB_DATA_NEXT_PAGE_URL = "next_page_url";

    public static final String KEY_VACANCY_DETAIL_MESSAGE = "message";
    public static final String KEY_VACANCY_DETAIL_SUCCESS = "success";
    public static final String KEY_VACANCY_DETAIL_OBJ_DATA = "data";
    public static final String KEY_VACANCY_DETAIL_ID = "id";
    public static final String KEY_VACANCY_DETAIL_VACANCY_MASTER_ID = "vacancy_master_id";
    public static final String KEY_VACANCY_DETAIL_NAPS_OPPORTUNITY_ID = "naps_opportunity_id";
    public static final String KEY_VACANCY_DETAIL_ZOHO_RECRUIT_ID = "zoho_recruit_id";
    public static final String KEY_VACANCY_DETAIL_ZOHO_SOURCING_ID = "zoho_sourcing_id";
    public static final String KEY_VACANCY_DETAIL_VACANCY_TITLE = "vacancy_title";
    public static final String KEY_VACANCY_DETAIL_CLIENT_NAME = "client_name";
    public static final String KEY_VACANCY_DETAIL_INDUSTRY = "industry";
    public static final String KEY_VACANCY_DETAIL_CONTACT_NAME = "contact_name";
    public static final String KEY_VACANCY_DETAIL_NO_OPEN_POSITION = "number_of_open_positions";
    public static final String KEY_VACANCY_DETAIL_YEARS_EXP_REQUIRED = "years_of_exp_required";
    public static final String KEY_VACANCY_DETAIL_MONTHS_EXP_REQUIRED = "months_of_exp_required";
    public static final String KEY_VACANCY_DETAIL_JOB_RESPONSIBILITY = "job_responsibilities";
    public static final String KEY_VACANCY_DETAIL_GENDER_PREFERENCFE = "gender_preferences";
    public static final String KEY_VACANCY_DETAIL_JOB_DESCRIPTION = "job_description";
    public static final String KEY_VACANCY_DETAIL_SKILLS = "skills";
    public static final String KEY_VACANCY_DETAIL_LANG_PREFERENCE = "language_preference";
    public static final String KEY_VACANCY_DETAIL_MIN_EDU = "minimum_education";
    public static final String KEY_VACANCY_DETAIL_OTHER_ELIGIBILITY_CRITEREA = "other_eligibility_criterea";
    public static final String KEY_VACANCY_DETAIL_HEIGHT = "height";
    public static final String KEY_VACANCY_DETAIL_WEIGHT = "weight";
    public static final String KEY_VACANCY_DETAIL_SHIFT_TYPE = "shift_type";
    public static final String KEY_VACANCY_DETAIL_SHIFT_TIMIMNG = "shift_timing";
    public static final String KEY_VACANCY_DETAIL_FOODING = "fooding";
    public static final String KEY_VACANCY_DETAIL_LOODING = "lodging";
    public static final String KEY_VACANCY_DETAIL_OTHER_BENEFITS_FOR_EMP = "other_benefits_for_employees";
    public static final String KEY_VACANCY_DETAIL_IN_HAND_SALARY = "in_hand_salary";
    public static final String KEY_VACANCY_DETAIL_CTC = "ctc";
    public static final String KEY_VACANCY_DETAIL_CURRENCY = "currency";
    public static final String KEY_VACANCY_DETAIL_MIN_SALARY_STIPED = "minimum_salary_stipend";
    public static final String KEY_VACANCY_DETAIL_MAX_SALARY_STIPED  = "maximum_salary_stipend";
    public static final String KEY_VACANCY_DETAIL_OVERTIME_PER_DAY_HR = "overtime_per_day_hr";
    public static final String KEY_VACANCY_DETAIL_OT_AMMOUNT = "ot_amount";
    public static final String KEY_VACANCY_DETAIL_JOB_OPENING_STATUS = "job_opening_status";
    public static final String KEY_VACANCY_DETAIL_DATE_OPENED = "date_opened";
    public static final String KEY_VACANCY_DETAIL_JOB_TYPE = "job_type";
    public static final String KEY_VACANCY_DETAIL_IS_HOT_JOB_OPENING= "is_hot_job_opening";
    public static final String KEY_VACANCY_DETAIL_ASSIGNED_RECRUITER = "assigned_recruiter";
    public static final String KEY_VACANCY_DETAIL_CITY = "city";
    public static final String KEY_VACANCY_DETAIL_STATE_PROVINCE = "state_province";
    public static final String KEY_VACANCY_DETAIL_COUNTRY = "country";
    public static final String KEY_VACANCY_DETAIL_POSTAL_CODE = "postal_code";
    public static final String KEY_VACANCY_DETAIL_WORK_LOCATION = "region_of_work_location_in_india";
    public static final String KEY_VACANCY_DETAIL_ADDED_BY = "added_by";
    public static final String KEY_VACANCY_DETAIL_CREATED_ON = "created_on";
    public static final String KEY_VACANCY_DETAIL_EDITED_BY = "edited_by";
    public static final String KEY_VACANCY_DETAIL_UPDATED_ON = "updated_on";
    public static final String KEY_VACANCY_DETAIL_PUBLISHED = "published";
    public static final String KEY_VACANCY_DETAIL_REVENUE_PER_POSITION = "revenue_per_position";
    public static final String KEY_VACANCY_DETAIL_INDSID = "indsId";
    public static final String KEY_VACANCY_DETAIL_INDS_NAME = "inds_name";
    public static final String KEY_VACANCY_DETAIL_INDS_DESC = "inds_desc";
    public static final String KEY_VACANCY_DETAIL_INDS_LOGO = "inds_logo";
    public static final String KEY_VACANCY_DETAIL_INDS_STATUS = "inds_status";
    public static final String KEY_VACANCY_DETAIL_CREAETED_AT = "created_at";
    public static final String KEY_VACANCY_DETAIL_STCODE = "StCode";
    public static final String KEY_VACANCY_DETAIL_STATENAME = "StateName";

    public static final String KEY_APPLY_JOB_USER_ID = "user_id";
    public static final String KEY_APPLY_JOB_VACANCY_ID = "vacancy_id";

    public static final String KEY_APPLIED_AND_UPCOMING_INTEVIEW_USER_ID = "user_id";
    public static final String KEY_APPLIED_AND_UPCOMING_INTEVIEW_STUDENT_STATUS = "student_status";
    public static final String KEY_APPLIED_ID = "applied_id";
    public static final String KEY_APPLIED_SCHEDULED_DATE = "sheduled_date";
    public static final String KEY_APPLIED_INTERVIEW_DATE = "interview_date";
    public static final String KEY_APPLIED_STUDENT_STATUS = "student_status";
    public static final String KEY_APPLIED_CLIENT_REMARKS = "client_remarks";
    public static final String KEY_APPLIED_STUDENT_REMARKS = "student_remarks";
    public static final String KEY_APPLIED_DATE_OF_JOINING = "date_of_joining";
    public static final String KEY_APPLIED_CLIENT_NAME = "client_name";

    public static final String KEY_APPLIED_DATE = "applied_on";
    public static final String KEY_APPLIED_ID_REMARK = "applied_id";
    public static final String KEY_STUDENT_REMARK = "student_remarks";

    public static final String KEY_PAGE = "page";

    public static final String KEY_POSTED_BY = "posted_by";
    public static final String KEY_POSTED_DATE_TIME = "posted_on";

    public static final String KEY_USER_NAME = "name";

    public static final String KEY_INVITE_CODE_VALUE = "invitecodeshared";



}
