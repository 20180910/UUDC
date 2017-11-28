package com.sk.uudc.tools;

import java.util.List;

/**
 * Created by Administrator on 2017/11/21.
 */

public class ListUtils {
    public static String listToString(List list ) {
        return listToString(list,",");
    }
    public static String listToString(List list,String separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i)).append(separator);
        }
        return sb.toString().substring(0, sb.toString().length() - 1);
    }

}
