package kz.mycrm.android.util;

import android.arch.persistence.room.TypeConverter;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import kz.mycrm.android.db.entity.Service;


public class MyTypeConverters {

    private static String strSeparator = "__,__";

    @Nullable
    @TypeConverter
    public static String convertServiceListToString(List<Service> service) {
        StringBuilder str = null;
        try {
            Service[] serviceArray = new Service[service.size()];
            for (int i = 0; i <= service.size() - 1; i++) {
                serviceArray[i] = service.get(i);
            }
            str = new StringBuilder();
            Gson gson = new Gson();
            for (int i = 0; i < serviceArray.length; i++) {
                String jsonString = gson.toJson(serviceArray[i]);
                str.append(jsonString);
                if (i < serviceArray.length - 1) {
                    str.append(strSeparator);
                }
            }
            return str.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Nullable
    @TypeConverter
    public static List<Service> convertStringToServiceList(String serviceString) {
        try {
            String[] serviceArray = serviceString.split(strSeparator);
            List<Service> services = new ArrayList<>();
            Gson gson = new Gson();
            for (String aServiceArray : serviceArray) {
                services.add(gson.fromJson(aServiceArray, Service.class));
            }
            return services;
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Nullable
    @TypeConverter
    public static List<String> convertStringListToString (String value) {
        try {
            Type listType = new TypeToken<List<String>>() {}.getType();
            return new Gson().fromJson(value, listType);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Nullable
    @TypeConverter
    public static String convertStringToStringList(List<String> list) {
        try {
            Gson gson = new Gson();
            return gson.toJson(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}