package com.league.foorballleagueapp.Utiles;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.ImageView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.league.foorballleagueapp.Model.Team;
import com.league.foorballleagueapp.Model.TeamDetailsModel.TeamDetails;
import com.pixplicity.sharp.Sharp;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;
import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Utiles {
    private static OkHttpClient httpClient;

    /**
     * Load Svg url to show team's Logo
     * @param context App context
     * @param url Image Url
     * @param target our image view
     */
    public static void fetchSvg(final Context context, String url, final ImageView target) {
        if (httpClient == null) {
            // Use cache for performance and basic offline capability
            httpClient = new OkHttpClient.Builder()
                    .cache(new Cache(context.getCacheDir(), 5 * 1024 * 1014))
                    .build();
        }

        Request request = new Request.Builder().url(url).build();
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("error","error is "+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                assert response.body() != null;
                InputStream stream = response.body().byteStream();
                Sharp.loadInputStream(stream).into(target);
                stream.close();
            }
        });
    }

    /**
     * Save Favourite teams
     * @param teamList list of teams
     * @param context app context
     */
        public static void saveFavouritesTeam(List<Team> teamList, Context context){
            SharedPreferences.Editor editor = context.getSharedPreferences("mysh",Context.MODE_PRIVATE).edit();
            Gson gson = new Gson();
            String gsonString = gson.toJson(teamList);

            editor.putString("team",gsonString);
            editor.apply();
        }

    /**
     * Retrieve favourite team list
     * @param context app context
     * @return list if Teams
     */
    public static List<Team> getFavouritesTeam(Context context){
            SharedPreferences sharedPreferences = context.getSharedPreferences("mysh",Context.MODE_PRIVATE);
            Gson gson = new Gson();
            String jsonString = sharedPreferences.getString("team","");
            Type type = new TypeToken<List<Team>>() {}.getType();
            return   gson.fromJson(jsonString,type);
        }




    /**
     * Save Favourite teams IDs
     * @param teamIDs list of teams ID
     * @param context app context
     */
    public static void saveTeamIDs(List<Integer> teamIDs, Context context){
        SharedPreferences.Editor editor = context.getSharedPreferences("myshID",Context.MODE_PRIVATE).edit();
        Gson gson = new Gson();
        String gsonString = gson.toJson(teamIDs);
        editor.putString("id",gsonString);
        editor.apply();
    }

    /**
     * Retrieve favourite team list IDs
     * @param context app context
     * @return list if Teams
     */
    public static List<Integer> getTeamIDs(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("myshID",Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String jsonString = sharedPreferences.getString("id","");
        Type type = new TypeToken<List<Integer>>() {}.getType();
        return   gson.fromJson(jsonString,type);
    }


    /**
     * Save Team Details Like squad
     * @param teamDetails list of daved team details
     * @param context app context
     */
    public static void saveDetailsTeam(List<TeamDetails> teamDetails, Context context){
        SharedPreferences.Editor editor = context.getSharedPreferences("shared",Context.MODE_PRIVATE).edit();
        Gson gson = new Gson();
        String gsonString = gson.toJson(teamDetails);
        editor.putString("squad",gsonString);
        editor.apply();
    }

    /**
     * Retrieve saved Team Details
     * @param context app context
     * @return list of team details
     */
    public static List<TeamDetails> getDetailsTeam(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("shared",Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String jsonString = sharedPreferences.getString("squad","");
        Type type = new TypeToken<List<TeamDetails>>() {}.getType();
        return   gson.fromJson(jsonString,type);
    }


    /**
     * checking if there is internet or not
     * @param context app context
     * @return boolean
     */
    public static Boolean isOnline(Context context){
        ConnectivityManager conMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        assert conMgr != null;
        NetworkInfo networkInfo = conMgr.getActiveNetworkInfo();
        if (networkInfo == null)
            return false;
        if (!networkInfo.isConnected())
            return false;
        return networkInfo.isAvailable();
    }

    /**
     * Saving a value to check if teams saved
     * @param b boolean value
     * @param context app context
     */

    public static void saveBoolFlag(Boolean b,Context context){
        SharedPreferences.Editor editor= context.getSharedPreferences("savesh",Context.MODE_PRIVATE).edit();
        editor.putBoolean("flag",b);
        editor.apply();
    }

    /**
     * retrieve the value
     * @param context app context
     * @return boolean value
     */
    public static Boolean getBoolFlag(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("savesh", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("flag",false);
    }

}
