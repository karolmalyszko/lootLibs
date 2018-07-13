package io.loot.lootsdk.models.networking.user;

import com.google.gson.annotations.SerializedName;

import io.loot.lootsdk.models.data.userinfo.ContactPreferences;
import lombok.Data;

public @Data class ContactPreferencesResponse {

    @SerializedName("contact_preference")
    ContactPreferenceResponse contactPreference;

    public static ContactPreferences parseToDataObject(ContactPreferencesResponse contactPreferencesResponse) {
        if (contactPreferencesResponse == null) {
            contactPreferencesResponse = new ContactPreferencesResponse();
        }

        ContactPreferences contactPreferences = new ContactPreferences();
        contactPreferences.setContactPreference(ContactPreferenceResponse.parseToDataObject(contactPreferencesResponse.getContactPreference()));

        return contactPreferences;
    }




}
