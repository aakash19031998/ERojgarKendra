package com.twocoms.rojgarkendra.global.model;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by S.V. on 7/30/2017.
 */

public class Validation {


    public static boolean checkIfEmptyOrNot(String input) {
        boolean isempty = false;
        if (TextUtils.isEmpty(input)) {
            isempty = true;
        }
        return isempty;
    }

    public static boolean isPasswordValid(String password) {
        String passwordPattern = "^.*(?=.{6,})(?=.*[a-z])(?=.*[A-Z]).*$";
        String passwordPattern2 = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{6,}$";
        if (!TextUtils.isEmpty(password) && (password.matches(passwordPattern) || password.matches(passwordPattern2))) {
            return true;
        } else {
            return false;
        }
    }


    public static boolean isValidMobileNumber(String mobileNumber) {
        if (mobileNumber.length() == 10
                && (mobileNumber.charAt(0) == '9' ||
                mobileNumber.charAt(0) == '8' ||
                mobileNumber.charAt(0) == '7')) {
            return true;
        } else {
            return false;
        }

    }


    public static boolean isValidateEmailid(String inputEmaildId) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(inputEmaildId);
        return matcher.matches();


    }

}
