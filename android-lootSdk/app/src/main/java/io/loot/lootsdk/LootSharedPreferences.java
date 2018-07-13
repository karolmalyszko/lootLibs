package io.loot.lootsdk;

import io.loot.lootsdk.database.daos.LootSharedPreferencesDao;
import io.loot.lootsdk.models.orm.SharedPreferenceEntity;

public class LootSharedPreferences {
    private LootSDK mLootSDK;
    private LootSharedPreferencesDao mLootSharedPreferencesDao;

    public LootSharedPreferences(LootSDK lootSDK) {
        mLootSDK = lootSDK;
        mLootSharedPreferencesDao = mLootSDK.getDatabase().sharedPreferencesDao();
    }


    public String get(String key) {
        String value = mLootSharedPreferencesDao.loadByKeySynchronously(key);
        if (value == null) {
            value = "";
        }
        return value;
    }

    public void put(String key, String value) {
        mLootSharedPreferencesDao.putSharedPreference(new SharedPreferenceEntity(key, value));
    }


}
