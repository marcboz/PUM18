package com.example.mboz.pum18.feature;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ResponseArray {
    ResponseItem[] data;
    ResponseArray(JSONObject response){
        try{
            JSONArray responseData = response.getJSONArray("data");
            int responseLength = responseData.length();
            ResponseItem[] dataArray = new ResponseItem[responseLength];
            for(int iterator = 0; iterator < responseData.length(); iterator++){
               dataArray[iterator] = new ResponseItem(responseData.getJSONObject(iterator));
            }
            data = dataArray;
        }catch (JSONException error){
            Log.e("response error", error.toString());
        }
    }
}
