package io.loot.lootsdk.models.networking.user;

import com.google.gson.annotations.SerializedName;

import io.loot.lootsdk.models.data.userinfo.ContactPreference;
import lombok.Data;

public @Data
class ContactPreferenceResponse {

    @SerializedName("contact_by_push")
    boolean contactByPush;
    @SerializedName("contact_by_sms")
    boolean contactBySms;
    @SerializedName("contact_by_email")
    boolean contactByEmail;
    @SerializedName("contact_by_post")
    boolean contactByPost;
    @SerializedName("contact_by_phone")
    boolean contactByPhone;
    @SerializedName("contact_for_research")
    boolean contactForResearch;
    @SerializedName("has_set_contact_prefs")
    boolean hasSetContactPrefs;

    public static ContactPreference parseToDataObject(ContactPreferenceResponse contactPreferenceResponse) {
        if (contactPreferenceResponse == null) {
            contactPreferenceResponse = new ContactPreferenceResponse();
        }

        ContactPreference contactPreference = new ContactPreference();
        contactPreference.setContactByEmail(contactPreferenceResponse.isContactByEmail());
        contactPreference.setContactByPhone(contactPreferenceResponse.isContactByPhone());
        contactPreference.setContactByPost(contactPreferenceResponse.isContactByPost());
        contactPreference.setContactByPush(contactPreferenceResponse.isContactByPush());
        contactPreference.setContactBySms(contactPreferenceResponse.isContactBySms());
        contactPreference.setContactForResearch(contactPreferenceResponse.isContactForResearch());
        contactPreference.setHasSetContactPrefs(contactPreferenceResponse.isHasSetContactPrefs());

        return contactPreference;
    }

}
