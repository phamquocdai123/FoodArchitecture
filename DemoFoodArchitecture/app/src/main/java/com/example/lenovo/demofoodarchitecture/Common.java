package com.example.lenovo.demofoodarchitecture;



import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class Common {
    //public static User currentUser;

    public static final String UPDATE = "Update";
    public static final String DELETE = "Delete";
    public static final String USER_KEY = "User";
    public static final String PDW_KEY= "Password";
    public static final int PICK_IMAGE_REQUEST =71;

    public Common() {

    }


    public static String convertCodeToStatus(String status) {
        if (status.equals("0"))
            return "Đã đặt hàng";
        else if (status.equals("1"))
            return "Đang gửi thức ăn";
        else
            return "Đã gửi thức ăn";


    }

    public static boolean isConnectedToInterner(Context context){
        ConnectivityManager connectivityManager =(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager!=null){
            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
            if (info!=null){
                for (int i=0;i<info.length;i++){
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                        return true;
                }
            }
        }
        return false;
    }



}
