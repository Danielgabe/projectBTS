/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nichtemna.takeandsavephoto.fragment;

import java.util.AbstractMap;
import java.util.Collection;

public class CommonUtil {
    public static Boolean isNotNullOrEmpty(Object object) {
        return !isNullOrEmpty(object);
    }
    
    public static Boolean isNullOrEmpty(Object object) {
        if (object == null) {
            return true;
        } else {
            if (object instanceof Collection) {
                if (((Collection) object).isEmpty()) {
                    return true;
                }
            } else if (object instanceof AbstractMap) {
                if (((AbstractMap) object).isEmpty()) {
                    return true;
                }
            } else {
                if (object.toString().trim().equals("")) {
                    return true;
                }
            }
            return false;
        }
    }
}
