package com.hossam.emergency.algorithem;

import java.util.UUID;

/**
 * Created by hossam on 11/9/17.
 */

public class UniqueIntgerID {

    public static int generateUniqueId() {
        UUID idOne = UUID.randomUUID();
        String str = "" + idOne;
        int uid = str.hashCode();
        String filterStr = "" + uid;
        str = filterStr.replaceAll("-", "");
        return Integer.parseInt(str);
    }
}
