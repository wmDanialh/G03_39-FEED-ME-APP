package com.example.feedmeappjava.Common;

import com.example.feedmeappjava.Model.UserProfile;

public class Common {

    public static UserProfile currentUser;

    public static final String UPDATE = "Update";
    public static final String DELETE = "Delete";


    public static String convertCodeToStatus(String status) {
        if(status.equals("0"))
            return "Placed";
        else if(status.equals("1"))
            return "On My Way";
        else
            return "Shipped";
    }
}
