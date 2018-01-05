package vztrack.gls.com.vztrack_user.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by sandeep on 16/3/16.
 */
public class SheredPref {

    public static String getLoginInfo(Context context){
        SharedPreferences sp = context.getSharedPreferences(Finals.VALIDATION, Context.MODE_PRIVATE);
        return sp.getString(Finals.LOGIN_CHECKED,"");
    }
    public static void setLoginInfo(Context context,String city){
        SharedPreferences sp = context.getSharedPreferences(Finals.VALIDATION, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Finals.LOGIN_CHECKED,city);
        editor.commit();
    }


    public static String getSociety_Name(Context context){
        SharedPreferences sp = context.getSharedPreferences(Finals.SOCIETYNAME, Context.MODE_PRIVATE);
        return sp.getString(Finals.SOCIETYNAME,"");
    }
    public static void setSociety_Name(Context context,String city){
        SharedPreferences sp = context.getSharedPreferences(Finals.SOCIETYNAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Finals.SOCIETYNAME,city);
        editor.commit();
    }


    public static String getFlat_No(Context context){
        SharedPreferences sp = context.getSharedPreferences(Finals.FLATNO, Context.MODE_PRIVATE);
        return sp.getString(Finals.FLATNO,"");
    }
    public static void setFlat_No(Context context,String city){
        SharedPreferences sp = context.getSharedPreferences(Finals.FLATNO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Finals.FLATNO,city);
        editor.commit();
    }


    public static String getUsername(Context context){
        SharedPreferences sp = context.getSharedPreferences(Finals.USERNAME, Context.MODE_PRIVATE);
        return sp.getString(Finals.USERNAME,"");
    }
    public static void setUSername(Context context,String username){
        SharedPreferences sp = context.getSharedPreferences(Finals.USERNAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Finals.USERNAME,username);
        editor.commit();
    }


    public static String getPassword(Context context){
        SharedPreferences sp = context.getSharedPreferences(Finals.PASSWORD, Context.MODE_PRIVATE);
        return sp.getString(Finals.PASSWORD,"");
    }
    public static void setPassword(Context context,String city){
        SharedPreferences sp = context.getSharedPreferences(Finals.PASSWORD, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Finals.PASSWORD,city);
        editor.commit();
    }

    public static String getExecute(Context context){
        SharedPreferences sp = context.getSharedPreferences(Finals.Exe, Context.MODE_PRIVATE);
        return sp.getString(Finals.Exe,"");
    }
    public static void setExecute(Context context,String city){
        SharedPreferences sp = context.getSharedPreferences(Finals.Exe, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Finals.Exe,city);
        editor.commit();
    }

    public static String getExecuteOffline(Context context){
        SharedPreferences sp = context.getSharedPreferences(Finals.ExeOffline, Context.MODE_PRIVATE);
        return sp.getString(Finals.Exe,"");
    }
    public static void setExecuteOffline(Context context,String city){
        SharedPreferences sp = context.getSharedPreferences(Finals.ExeOffline, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Finals.Exe,city);
        editor.commit();
    }

    public static String getFirstExecute(Context context){
        SharedPreferences sp = context.getSharedPreferences(Finals.FirstExe, Context.MODE_PRIVATE);
        return sp.getString(Finals.FirstExe,"");
    }
    public static void setFirstExecute(Context context,String city){
        SharedPreferences sp = context.getSharedPreferences(Finals.FirstExe, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Finals.FirstExe,city);
        editor.commit();
    }

    public static String getDate(Context context){
        SharedPreferences sp = context.getSharedPreferences(Finals.DATE, Context.MODE_PRIVATE);
        return sp.getString(Finals.DATE,"");
    }
    public static void setDate(Context context,String Sname){
        SharedPreferences sp = context.getSharedPreferences(Finals.DATE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Finals.DATE,Sname);
        editor.commit();
    }

    public static String getDateFor_Visitors(Context context){
        SharedPreferences sp = context.getSharedPreferences(Finals.DATE_VISITORS, Context.MODE_PRIVATE);
        return sp.getString(Finals.DATE,"");
    }
    public static void setDateFor_Visitors(Context context,String Sname){
        SharedPreferences sp = context.getSharedPreferences(Finals.DATE_VISITORS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Finals.DATE,Sname);
        editor.commit();
    }


    public static String getRun(Context context){
        SharedPreferences sp = context.getSharedPreferences(Finals.RUN, Context.MODE_PRIVATE);
        return sp.getString(Finals.RUN,"");
    }
    public static void setRun(Context context,String check){
        SharedPreferences sp = context.getSharedPreferences(Finals.RUN, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Finals.RUN,check);
        editor.commit();
    }

    public static String getNotification(Context context){
        SharedPreferences sp = context.getSharedPreferences(Finals.NOTIFICATION, Context.MODE_PRIVATE);
        return sp.getString(Finals.NOTIFICATION,"");
    }
    public static void setNotification(Context context,String check){
        SharedPreferences sp = context.getSharedPreferences(Finals.NOTIFICATION, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Finals.NOTIFICATION,check);
        editor.commit();
    }

    public static String getSound(Context context){
        SharedPreferences sp = context.getSharedPreferences(Finals.SOUND, Context.MODE_PRIVATE);
        return sp.getString(Finals.SOUND,"");
    }
    public static void setSound(Context context,String check){
        SharedPreferences sp = context.getSharedPreferences(Finals.SOUND, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Finals.SOUND,check);
        editor.commit();
    }

    public static String getVibration(Context context){
        SharedPreferences sp = context.getSharedPreferences(Finals.VIBRATION, Context.MODE_PRIVATE);
        return sp.getString(Finals.VIBRATION,"");
    }
    public static void setVibration(Context context,String check){
        SharedPreferences sp = context.getSharedPreferences(Finals.VIBRATION, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Finals.VIBRATION,check);
        editor.commit();
    }

    public static String getWingName(Context context){
        SharedPreferences sp = context.getSharedPreferences(Finals.WING, Context.MODE_PRIVATE);
        return sp.getString(Finals.WING,"");
    }
    public static void setWingName(Context context,String check){
        SharedPreferences sp = context.getSharedPreferences(Finals.WING, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Finals.WING,check);
        editor.commit();
    }

    public static String getSocietyId(Context context){
        SharedPreferences sp = context.getSharedPreferences(Finals.SOCIETYID, Context.MODE_PRIVATE);
        return sp.getString(Finals.SOCIETYID,"");
    }
    public static void setSocietyId(Context context,String check){
        SharedPreferences sp = context.getSharedPreferences(Finals.SOCIETYID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Finals.SOCIETYID,check);
        editor.commit();
    }

    public static String getFamilyId(Context context){
        SharedPreferences sp = context.getSharedPreferences(Finals.FAMILYID, Context.MODE_PRIVATE);
        return sp.getString(Finals.FAMILYID,"");
    }
    public static void setFamilyId(Context context,String check){
        SharedPreferences sp = context.getSharedPreferences(Finals.FAMILYID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Finals.FAMILYID,check);
        editor.commit();
    }

    public static String getDateForApi(Context context){
        SharedPreferences sp = context.getSharedPreferences(Finals.DATE_FOR_API, Context.MODE_PRIVATE);
        return sp.getString(Finals.DATE_FOR_API,"");
    }
    public static void setDateForApi(Context context,String date){
        SharedPreferences sp = context.getSharedPreferences(Finals.DATE_FOR_API, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Finals.DATE_FOR_API,date);
        editor.commit();
    }

    public static String getDateForCompApi(Context context){
        SharedPreferences sp = context.getSharedPreferences(Finals.DATE_FOR__COMP_API, Context.MODE_PRIVATE);
        return sp.getString(Finals.DATE_FOR__COMP_API,"");
    }
    public static void setDateForCompApi(Context context,String date){
        SharedPreferences sp = context.getSharedPreferences(Finals.DATE_FOR__COMP_API, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Finals.DATE_FOR__COMP_API,date);
        editor.commit();
    }


    public static boolean getTutorialFlag(Context context){
        SharedPreferences sp = context.getSharedPreferences(Finals.TUTORIAL_FLAG, Context.MODE_PRIVATE);
        return sp.getBoolean(Finals.TUTORIAL_FLAG,false);
    }
    public static void setTutorialFlag(Context context,Boolean flag){
        SharedPreferences sp = context.getSharedPreferences(Finals.TUTORIAL_FLAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(Finals.TUTORIAL_FLAG,flag);
        editor.commit();
    }

    public static boolean getDialogFlag(Context context){
        SharedPreferences sp = context.getSharedPreferences(Finals.DIALOG_FLAG, Context.MODE_PRIVATE);
        return sp.getBoolean(Finals.DIALOG_FLAG,false);
    }
    public static void setDialogFlag(Context context,Boolean flag){
        SharedPreferences sp = context.getSharedPreferences(Finals.DIALOG_FLAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(Finals.DIALOG_FLAG,flag);
        editor.commit();
    }

    public static int getLatestAppVersion(Context context){
        SharedPreferences sp = context.getSharedPreferences(Finals.APP_VERSION, Context.MODE_PRIVATE);
        return sp.getInt(Finals.APP_VERSION,0);
    }
    public static void setLatestAppVersion(Context context,int val){
        SharedPreferences sp = context.getSharedPreferences(Finals.APP_VERSION, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(Finals.APP_VERSION,val);
        editor.commit();
    }

    public static boolean getAdminAccess(Context context){
        SharedPreferences sp = context.getSharedPreferences(Finals.ADMIN, Context.MODE_PRIVATE);
        return sp.getBoolean(Finals.ADMIN,false);
    }
    public static void setAdminAccess(Context context,boolean flag){
        SharedPreferences sp = context.getSharedPreferences(Finals.ADMIN, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(Finals.ADMIN,flag);
        editor.commit();
    }


    public static String getPhoneNo(Context context){
        SharedPreferences sp = context.getSharedPreferences(Finals.PHONE, Context.MODE_PRIVATE);
        return sp.getString(Finals.PHONE,"");
    }
    public static void setPhoneNo(Context context,String data){
        SharedPreferences sp = context.getSharedPreferences(Finals.PHONE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Finals.PHONE,data);
        editor.commit();
    }


    public static boolean getSOSAccess(Context context){
        SharedPreferences sp = context.getSharedPreferences(Finals.SOS, Context.MODE_PRIVATE);
        return sp.getBoolean(Finals.SOS,false);
    }
    public static void setSOSAccess(Context context,boolean flag){
        SharedPreferences sp = context.getSharedPreferences(Finals.SOS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(Finals.SOS,flag);
        editor.commit();
    }

    public static boolean getType(Context context){
        SharedPreferences sp = context.getSharedPreferences(Finals.TYPE, Context.MODE_PRIVATE);
        return sp.getBoolean(Finals.TYPE,false);
    }
    public static void setType(Context context,Boolean type){
        SharedPreferences sp = context.getSharedPreferences(Finals.TYPE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(Finals.TYPE,type);
        editor.commit();
    }

    public static boolean getComplain(Context context){
        SharedPreferences sp = context.getSharedPreferences(Finals.COMPLAINT, Context.MODE_PRIVATE);
        return sp.getBoolean(Finals.COMPLAINT,false);
    }
    public static void setComplaint(Context context,Boolean complaint){
        SharedPreferences sp = context.getSharedPreferences(Finals.COMPLAINT, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(Finals.COMPLAINT,complaint);
        editor.commit();
    }

    public static boolean getLocalStores(Context context){
        SharedPreferences sp = context.getSharedPreferences(Finals.LOCAL_STORES, Context.MODE_PRIVATE);
        return sp.getBoolean(Finals.LOCAL_STORES,false);
    }
    public static void setLocalStores(Context context,Boolean isLocalStore){
        SharedPreferences sp = context.getSharedPreferences(Finals.LOCAL_STORES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(Finals.LOCAL_STORES,isLocalStore);
        editor.commit();
    }

    public static boolean isCompany(Context context){
        SharedPreferences sp = context.getSharedPreferences(Finals.COMPANY, Context.MODE_PRIVATE);
        return sp.getBoolean(Finals.COMPANY,false);
    }
    public static void setCompany(Context context,Boolean isCompany){
        SharedPreferences sp = context.getSharedPreferences(Finals.COMPANY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(Finals.COMPANY,isCompany);
        editor.commit();
    }

    public static String getOwnerName(Context context){
        SharedPreferences sp = context.getSharedPreferences(Finals.OWNER, Context.MODE_PRIVATE);
        return sp.getString(Finals.OWNER,"");
    }
    public static void setOwnerName(Context context,String data){
        SharedPreferences sp = context.getSharedPreferences(Finals.OWNER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Finals.OWNER,data);
        editor.commit();
    }

    public static int getNoticeConut(Context context){
        SharedPreferences sp = context.getSharedPreferences(Finals.NOTICE, Context.MODE_PRIVATE);
        return sp.getInt(Finals.NOTICE,0);
    }
    public static void setNoticeCount(Context context,int val){
        SharedPreferences sp = context.getSharedPreferences(Finals.NOTICE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(Finals.NOTICE,val);
        editor.commit();
    }

    public static String getVehicleNo_for_add(Context context){
        SharedPreferences sp = context.getSharedPreferences(Finals.VEHICLENOFORADD, Context.MODE_PRIVATE);
        return sp.getString(Finals.VEHICLENOFORADD,"");
    }
    public static void setVehicleNo_for_add(Context context,String val){
        SharedPreferences sp = context.getSharedPreferences(Finals.VEHICLENOFORADD, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Finals.VEHICLENOFORADD,val);
        editor.commit();
    }

    public static String getVehicleNo_for_search(Context context){
        SharedPreferences sp = context.getSharedPreferences(Finals.VEHICLENOFORSEARCH, Context.MODE_PRIVATE);
        return sp.getString(Finals.VEHICLENOFORSEARCH,"");
    }
    public static void setVehicleNo_for_search(Context context,String val){
        SharedPreferences sp = context.getSharedPreferences(Finals.VEHICLENOFORSEARCH, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Finals.VEHICLENOFORSEARCH,val);
        editor.commit();
    }

    public static String getVehiclePatternNo(Context context){
        SharedPreferences sp = context.getSharedPreferences(Finals.PATTERN, Context.MODE_PRIVATE);
        return sp.getString(Finals.PATTERN,"");
    }
    public static void setVehiclePatternNo(Context context,String val){
        SharedPreferences sp = context.getSharedPreferences(Finals.PATTERN, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Finals.PATTERN,val);
        editor.commit();
    }

    public static String getVehicleFlag(Context context){
        SharedPreferences sp = context.getSharedPreferences(Finals.VEHICLE_FLAG, Context.MODE_PRIVATE);
        return sp.getString(Finals.VEHICLE_FLAG,"");
    }
    public static void setVehicleFlag(Context context,String val){
        SharedPreferences sp = context.getSharedPreferences(Finals.VEHICLE_FLAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Finals.VEHICLE_FLAG,val);
        editor.commit();
    }

    public static String getDateForVehiclePattern(Context context){
        SharedPreferences sp = context.getSharedPreferences(Finals.DATE_VEHICLE, Context.MODE_PRIVATE);
        return sp.getString(Finals.DATE_VEHICLE,"");
    }
    public static void setDateForVehiclePattern(Context context,String Sname){
        SharedPreferences sp = context.getSharedPreferences(Finals.DATE_VEHICLE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Finals.DATE_VEHICLE,Sname);
        editor.commit();
    }

    public static String getNotificationRingtone(Context context){
        SharedPreferences sp = context.getSharedPreferences(Finals.NOTIFICATION_TONE, Context.MODE_PRIVATE);
        return sp.getString(Finals.NOTIFICATION_TONE,"");
    }
    public static void setNotificationRingtone(Context context,String notificationTone){
        SharedPreferences sp = context.getSharedPreferences(Finals.NOTIFICATION_TONE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Finals.NOTIFICATION_TONE,notificationTone);
        editor.commit();
    }

    public static String getRingtoneTitle(Context context){
        SharedPreferences sp = context.getSharedPreferences(Finals.RINGTONE_TITLE, Context.MODE_PRIVATE);
        return sp.getString(Finals.RINGTONE_TITLE,"");
    }
    public static void setRingtoneTitle(Context context,String ringtoneTitle){
        SharedPreferences sp = context.getSharedPreferences(Finals.RINGTONE_TITLE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Finals.RINGTONE_TITLE,ringtoneTitle);
        editor.commit();
    }
}