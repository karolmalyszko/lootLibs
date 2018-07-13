package io.loot.lootsdk.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import de.adorsys.android.securestoragelibrary.SecureStorageException;
import io.loot.lootsdk.LootSDK;


class SecurePreferences {

    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private static final String KEY_TRANSFORMATION = "AES/ECB/PKCS5Padding";
    private static final String SECRET_KEY_HASH_TRANSFORMATION = "SHA-256";
    private static final String CHARSET = "UTF-8";
    private final boolean mEncryptKeys;
    private final Cipher mWriter;
    private final Cipher mReader;
    private final Cipher mKeyWriter;
    private final SharedPreferences mPreferences;
    private CryptoUtil mCryptoUtil;

    SecurePreferences(Context context, String preferenceName, String secureKey, boolean encryptKeys) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, UnsupportedEncodingException, InvalidKeyException {
        this.mWriter = Cipher.getInstance(TRANSFORMATION);
        this.mReader = Cipher.getInstance(TRANSFORMATION);
        this.mKeyWriter = Cipher.getInstance(KEY_TRANSFORMATION);

        initCiphers(secureKey);

        this.mCryptoUtil = CryptoUtil.getInstance(context);
        try {
            this.mCryptoUtil.generateKeyPair();
        } catch (SecureStorageException e) {
            e.printStackTrace();
        }
        this.mPreferences = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);

        this.mEncryptKeys = encryptKeys;
    }


    private static byte[] convert(Cipher cipher, byte[] bs) {
        try {
            return cipher.doFinal(bs);
        } catch (Exception e) {
        }
        return new byte[0];
    }

    protected void initCiphers(String secureKey) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException,
            InvalidAlgorithmParameterException {
        IvParameterSpec ivSpec = getIv();
        SecretKeySpec secretKey = getSecretKey(secureKey);

        mWriter.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
        mReader.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
        mKeyWriter.init(Cipher.ENCRYPT_MODE, secretKey);
    }

    protected IvParameterSpec getIv() {
        byte[] iv = new byte[mWriter.getBlockSize()];
        System.arraycopy("fldsjfodasjifudslfjdsaofshaufihadsf".getBytes(), 0, iv, 0, mWriter.getBlockSize());
        return new IvParameterSpec(iv);
    }

    protected SecretKeySpec getSecretKey(String key) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        byte[] keyBytes = createKeyBytes(key);
        return new SecretKeySpec(keyBytes, TRANSFORMATION);
    }

    protected byte[] createKeyBytes(String key) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(SECRET_KEY_HASH_TRANSFORMATION);
        md.reset();
        byte[] keyBytes = md.digest(key.getBytes(CHARSET));
        return keyBytes;
    }

    void put(String key, String value) {
        if (value == null) {
            mPreferences.edit().remove(toKey(key)).apply();
        } else {
            putValue(toKey(key), value);
        }
    }

    boolean containsKey(String key) {
        return mPreferences.contains(toKey(key));
    }

    void removeValue(String key) {
        mPreferences.edit().remove(toKey(key)).apply();
    }

    String getString(String key) throws SecurePreferencesException {
        if (mPreferences.contains(toKey(key))) {
            String securedEncodedValue = mPreferences.getString(toKey(key), "");
            return decrypt(securedEncodedValue);
        }
        return "";
    }

    void clear() {
        mPreferences.edit().clear().apply();
    }

    private String toKey(String key) {
        if (mEncryptKeys)
            return encrypt(key, mKeyWriter);
        else return key;
    }

    private void putValue(String key, String value) throws SecurePreferencesException {
        String secureValueEncoded = encrypt(value, mWriter);

        mPreferences.edit().putString(key, secureValueEncoded).apply();
    }

    protected String encrypt(String value, Cipher writer) {
        byte[] secureValue = new byte[0];
        try {
            secureValue = convert(writer, value.getBytes(CHARSET));
        } catch (UnsupportedEncodingException e) {
        }
        String secureValueEncoded = Base64.encodeToString(secureValue, Base64.NO_WRAP);
        return secureValueEncoded;
    }

    protected String decrypt(String securedEncodedValue) {
        byte[] securedValue = Base64.decode(securedEncodedValue, Base64.NO_WRAP);
        byte[] value = convert(mReader, securedValue);
        try {
            return new String(value, CHARSET);
        } catch (UnsupportedEncodingException e) {
        }
        return "";
    }

    String getEncryptedKey() {
        return getString("encrypted_key");
    }

    void saveEncryptedKey(String key) {
        put("encrypted_key", key);
    }

    String getDBPass() {
        return getString("db_pass");
    }

    void saveDBPass(String key) {
        put("db_pass", key);
    }

    boolean wasPinSkipped() {
        return getString("was_pin_skipped") != null && !getString("was_pin_skipped").isEmpty();
    }

    void setPinWasSkipped() {
        put("was_pin_skipped", "was_skipped");
    }

    void saveInternalStep(String internalStepName) {
        put("internal_step", internalStepName);
    }

    String getInternalStep() {
        return getString("internal_step");
    }


    //Tokens and pins
    void savePIN(String PIN) {
        insertToDao("pin", PIN);
    }

    String getPIN() {
        return loadFromDao("pin");
    }

    void savePinAuthToken(String authToken) {
        insertToDao("pin_auth_token", authToken);
    }

    String getPinAuthToken() {
        return loadFromDao("pin_auth_token");
    }

    void saveTouchAuthToken(String authToken) {
        insertToDao("touch_auth_token", authToken);
    }

    String getTouchAuthToken() {
        return loadFromDao("touch_auth_token");
    }

    void saveLoginToken(String loginToken) {
        insertToDao("login_token", loginToken);
    }

    String getLoginToken() {
        return loadFromDao("login_token");
    }

    void saveOnBoardingToken(String onboardingToken) {
        insertToDao("onboarding_token", onboardingToken);
    }

    String getOnBoardingToken() {
        return loadFromDao("onboarding_token");
    }

    static class SecurePreferencesException extends RuntimeException {

        SecurePreferencesException(Throwable e) {
            super(e);
        }

    }

    private String loadFromDao(String key) {
        if (mPreferences.contains(toKey(key))) {
            String secureSharedPrefsValue = getString(key);
            removeValue(key);
            insertToDao(key, secureSharedPrefsValue);
            return secureSharedPrefsValue;
        }
        try {
            String securedEncodedValue = LootSDK.getInstance().sharedPreferences().get(key);
            if (securedEncodedValue == null || securedEncodedValue.isEmpty()) {
                return "";
            }
            return mCryptoUtil.decryptMessage(securedEncodedValue);
        } catch (SecureStorageException e) {
            e.printStackTrace();
            return "";
        }
    }

    private void insertToDao(String key, String value) {
        if (mPreferences.contains(toKey(key))) {
            removeValue(key);
        }
        try {
            LootSDK.getInstance().sharedPreferences().put(key, mCryptoUtil.encryptMessage(value));
        } catch (SecureStorageException e) {
            e.printStackTrace();
        }
    }

}
