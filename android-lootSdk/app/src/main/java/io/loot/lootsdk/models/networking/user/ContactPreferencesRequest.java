package io.loot.lootsdk.models.networking.user;

import com.google.gson.annotations.SerializedName;

import io.loot.lootsdk.models.data.userinfo.ContactPreferences;
import lombok.Data;

public @Data class ContactPreferencesRequest {

    @SerializedName("contact_preference")
    ContactPreferenceRequest contactPreference;

    public ContactPreferencesRequest(ContactPreferences contactPreferences) {
        ContactPreferenceRequest contactPreferencesRequest = new ContactPreferenceRequest(contactPreferences.getContactPreference());
        this.contactPreference = contactPreferencesRequest;
    }
}
