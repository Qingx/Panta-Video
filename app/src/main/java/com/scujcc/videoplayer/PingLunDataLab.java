package com.scujcc.videoplayer;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PingLunDataLab {
    private Context context;
    public PingLunDataLab(Context ctx){
        context = ctx;
    }

    public String loadJSONFromAsset(String filename) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            Log.e("FFPLAY", ex.getMessage());
            ex.printStackTrace();
        }
        return json;
    }

    public List<PingLun> getPingLuns(String filename,String tvName){
        List<PingLun> result  =new ArrayList<>();
        String json = loadJSONFromAsset(filename);
        try {
            JSONObject obj = new JSONObject(json);
            JSONArray data = obj.getJSONArray(tvName);

            for (int i = 0; i < data.length(); i++) {
                PingLun pingLun = new PingLun();
                JSONObject item = data.getJSONObject(i);
                pingLun.setTouxiang(item.getString("touxiang"));
                pingLun.setId(item.getString("id"));
                pingLun.setTime(item.getString("time"));
                pingLun.setText(item.getString("text"));
                pingLun.setZan(item.getString("zan"));
                pingLun.setCai(item.getString("cai"));
                pingLun.setHuifu(item.getString("huifu"));
                result.add(pingLun);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
}
