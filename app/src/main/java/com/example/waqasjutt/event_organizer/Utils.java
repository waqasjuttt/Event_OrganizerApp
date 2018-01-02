package com.example.waqasjutt.event_organizer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Utils {

    //Email Validation pattern
    public static final String Email_Format = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b";

    public static final String UperCaseLetter = "/^[A-Z][A-Za-z0-9_-]{3,19}$/";
    public static final String LettersNumbers = "^[a-zA-Z0-9_]*$";
    public static final String Numbers = "[0-9]+";

    //date format (yyyy-mm-dd) with separators '-' or '/' or '.' or ' '. Forces usage of same separator accross date.
    public static final String DOB_Format2 = "^[0-9]{4}([- /.])(((0[13578]|(10|12))\\1(0[1-9]|[1-2][0-9]|3[0-1]))|(02\\1(0[1-9]|[1-2][0-9]))|((0[469]|11)\\1(0[1-9]|[1-2][0-9]|30)))$";

    public static final String DOB_Format1 = "^(1[0-2]|0?[1-9])/(3[01]|[12][0-9]|0?[1-9])/(?:[0-9]{2})?[0-9]{2}$";

    //Fragments Tags
    public static final String Login_Fragment = "Login_Fragment";
    public static final String Profile_Fragment = "Profile_Fragment";
    public static final String Test_Fragment = "Test_Fragment";
    public static final String HomePage_Fragment = "HomePage_Fragment";
}
