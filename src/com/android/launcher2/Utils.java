package com.android.launcher2;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Utils {
    public static AllAppsList allappslist;
    public static boolean getAllowedType(Context context, String type) {
        SharedPreferences LauncherApps= context.getSharedPreferences("LauncherApps", context.MODE_PRIVATE);
        return LauncherApps.getBoolean(type, false);
    }
    
    public static void updateAllowedType(Context context, String type, boolean enable) {
        SharedPreferences LauncherApps= context.getSharedPreferences("LauncherApps", context.MODE_PRIVATE);
        Editor edit = LauncherApps.edit();
        edit.putBoolean(type, enable);
        edit.commit();
    }
    
    public static void updateApp(Context context, String pckName) {
        SharedPreferences LauncherApps= context.getSharedPreferences("LauncherApps", context.MODE_PRIVATE);
        Editor edit = LauncherApps.edit();
        edit.putString("AllowedClassNames", pckName);
        edit.commit();
    }
    public static void addAppFrom(Context context,String pacName) {
    	allappslist=new AllAppsList(null);
    	SharedPreferences LauncherApps= context.getSharedPreferences("LauncherApps", context.MODE_PRIVATE);
        String applist=LauncherApps.getString("AllowedClassNames", "");
        String[] app_list = applist.split(",");
        for (String str : app_list) {
    	   allappslist.addPackage(context, str);
           
       }
    }
    public static void delApp(Context context, String pckName) {
        
        SharedPreferences LauncherApps = context.getSharedPreferences("LauncherApps",context.MODE_PRIVATE);
        String curPcklist = LauncherApps.getString("AllowedClassNames", "");
        String[] number = pckName.split(",");
        for (String num : number) {
            curPcklist = curPcklist.replace(num, "");
        }
        Editor edit = LauncherApps.edit();
        edit.putString("numList", curPcklist);
        edit.commit();
        
    }
    
    public static boolean checkApp(Context context, String pckName) {
        
        SharedPreferences LauncherApps = context.getSharedPreferences("LauncherApps", context.MODE_PRIVATE);
        String curPcklist = LauncherApps.getString("AllowedClassNames", "");
        String[] pckLists = curPcklist.split(",");
        if (pckLists != null && pckLists.length > 0) {
            for (String pck : pckLists) {
                    if(pckName.equals(pck)) {
                        return true;
                    }   
             }
        }
        return false;
    }
    
    
}
