package com.assist.api.util

class Utils {

    static boolean isEmail(String email){
        String pattern = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}"
        return email.matches(pattern)
    }

    static boolean isMobile(String mobile){
        return (mobile.size() >= 10 && mobile.size() <=12) && mobile.isNumber()
    }

    static boolean convertStringToBoolean(String booleanString){
        return booleanString && ( booleanString == 'TRUE' || booleanString == 'true')
    }
}
