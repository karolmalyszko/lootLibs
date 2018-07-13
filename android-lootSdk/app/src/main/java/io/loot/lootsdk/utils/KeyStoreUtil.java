package io.loot.lootsdk.utils;

import android.content.Context;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.NoSuchPaddingException;


public class KeyStoreUtil {

    private static final String SHARED_SALT = "6D0E7BA80C49AB4C";

    private static final int KEY_SIZE = 16;

    Context mContext;
    private static KeyStoreUtil sInstance;
    SecurePreferences mSharedPreferences;

    public static KeyStoreUtil getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new KeyStoreUtil(context);
        }
        return sInstance;
    }

    private KeyStoreUtil(Context context) {
        mContext = context;
        try {
            mSharedPreferences = new SecurePreferences(mContext, "LootSDK", SHARED_SALT, true);
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
    }

    public void clear() {
        mSharedPreferences.clear();
    }

    public String getDBPass() {
        try {
            return mSharedPreferences.getDBPass();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public void saveDBPass(String dbPass) {
        try {
            mSharedPreferences.saveDBPass(dbPass);
        } catch (Exception e) {
            mSharedPreferences.saveDBPass("");
            e.printStackTrace();
        }
    }

    public String getPin() {
        try {
            return mSharedPreferences.getPIN();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public void savePin(String PIN) {
        try {
            mSharedPreferences.savePIN(PIN);
        } catch (Exception e) {
            mSharedPreferences.savePIN("");
            e.printStackTrace();
        }
    }

    public String getPinAuthToken() {
        try {
            return mSharedPreferences.getPinAuthToken();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public void savePinAuthToken(String authToken) {
        try {
            mSharedPreferences.savePinAuthToken(authToken);
        } catch (Exception e) {
            mSharedPreferences.savePinAuthToken("");
            e.printStackTrace();
        }
    }

    public String getTouchAuthToken() {
        try {
            return mSharedPreferences.getTouchAuthToken();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public void saveTouchAuthToken(String authToken) {
        try {
            mSharedPreferences.saveTouchAuthToken(authToken);
        } catch (Exception e) {
            mSharedPreferences.saveTouchAuthToken("");
            e.printStackTrace();
        }
    }

    public boolean wasPinSkipped() {
        return mSharedPreferences.wasPinSkipped();
    }

    public void setPinWasSkipped() {
        mSharedPreferences.setPinWasSkipped();
    }

    public void saveInternalStep(String internalStepName) {
        mSharedPreferences.saveInternalStep(internalStepName);
    }

    public String getInternalStep() {
        return mSharedPreferences.getInternalStep();
    }

    public void saveLoginToken(String token) {
        mSharedPreferences.saveLoginToken(token);
    }

    public String getLoginToken() {
        String token = mSharedPreferences.getLoginToken();
        if (token == null) {
            token = "";
        }

        return token;
    }

    public void saveOnBoardingToken(String token) {
        mSharedPreferences.saveOnBoardingToken(token);
    }

    public String getOnBoardingToken() {
        String token = mSharedPreferences.getOnBoardingToken();
        if (token == null) {
            token = "";
        }

        return token;
    }
}
