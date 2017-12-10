package kz.mycrm.android.util;

import android.arch.persistence.room.TypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import kz.mycrm.android.db.entity.Service;


public class MyTypeConverters {

    private static String strSeparator = "__,__";

    @TypeConverter
    public static String convertServiceListToString(List<Service> video) {
        Service[] videoArray = new Service[video.size()];
        for (int i = 0; i <= video.size() - 1; i++) {
            videoArray[i] = video.get(i);
        }
        StringBuilder str = new StringBuilder();
        Gson gson = new Gson();
        for (int i = 0; i < videoArray.length; i++) {
            String jsonString = gson.toJson(videoArray[i]);
            str.append(jsonString);
            if (i < videoArray.length - 1) {
                str.append(strSeparator);
            }
        }
        return str.toString();
    }

    @TypeConverter
    public static List<Service> convertStringToServiceList(String serviceString) {
        String[] serviceArray = serviceString.split(strSeparator);
        List<Service> services = new ArrayList<>();
        Gson gson = new Gson();
        for (String aServiceArray : serviceArray) {
            services.add(gson.fromJson(aServiceArray, Service.class));
        }
        return services;
    }

    @TypeConverter
    public static List<String> convertStringListToString (String value) {
        Type listType = new TypeToken<List<String>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String convertStringToStringList(List<String> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }
}