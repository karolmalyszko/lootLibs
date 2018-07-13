package io.loot.lootsdk.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.HashSet;
import java.util.Set;

import io.loot.lootsdk.LootSDK;
import io.loot.lootsdk.R;


public class SharedPreferencesProvider {

    private static final String PREFERENCES_NAME = "LootSDKPreferences";
    private Context mContext;
    private SharedPreferences mSharedPreferences;
    private Gson mGson;

    private static SharedPreferencesProvider sInstance;

    public static SharedPreferencesProvider getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new SharedPreferencesProvider(context);
        }

        return sInstance;
    }

    private SharedPreferencesProvider(Context context) {
        mContext = context;
        mSharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        mGson = new Gson();
    }

    private SharedPreferences.Editor getEditor() {
        return mSharedPreferences.edit();
    }

    public void saveAuthorizationToken(String token) {
        saveToSharedPrefs(mContext.getString(R.string.auth_token), token);
    }

    public void saveEmail(String email) {
        saveToSharedPrefs(mContext.getString(R.string.email_prefs), email);
    }

    public void saveIntercomHash(String intercomHash) {
        saveToSharedPrefs(mContext.getString(R.string.intercom_hash), intercomHash);
    }

    public String getAuthorizationToken() {
        return mSharedPreferences.getString(mContext.getString(R.string.auth_token), "");
    }

    public String getEmail() {
        return mSharedPreferences.getString(mContext.getString(R.string.email_prefs), "");
    }

    public String getIntercomHash() {
        return mSharedPreferences.getString(mContext.getString(R.string.intercom_hash), "");
    }

    public void clear() {
        LootSDK.getInstance().sessions().clear();
        saveAuthorizationToken("");
        saveEmail("");
        saveIntercomHash("");
    }

    private <T> void saveToSharedPrefs(String string, T object){
        SharedPreferences.Editor editor = getEditor();
        if ( object instanceof  Boolean ){
            editor.putBoolean(string, (Boolean) object);
        }
        else if ( object instanceof String){
            editor.putString(string, (String) object);
        }
        else if (object instanceof Set) {
            editor.putStringSet(string, (HashSet<String>) object);
        }
        else if (object instanceof Long) {
            editor.putLong(string, (Long)object);
        }
        else {
            String json = mGson.toJson(object);
            editor.putString(string, json);
        }
        editor.commit();
    }
}
