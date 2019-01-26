package com.example.mboz.pum18.feature;

import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

public class ResponseItem {
    public String title, description, tags, sourceUrl, date, preview;
    public int  voteCount, buryCount;
    public ResponseItem(JSONObject response){
        try{
            title = response.getString("title");
            description=response.getString("description");
            tags=response.getString("tags");
            sourceUrl=response.getString("source_url");
            voteCount=response.getInt("vote_count");
            buryCount=response.getInt("bury_count");
            date=response.getString("date");
            preview=response.getString("preview");
        } catch (JSONException error){
            Log.e("response error", error.toString());
        }
    }
}
