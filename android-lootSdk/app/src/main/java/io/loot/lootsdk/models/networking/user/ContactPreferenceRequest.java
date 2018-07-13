package io.loot.lootsdk.models.networking.user;

import com.google.gson.annotations.SerializedName;

import io.loot.lootsdk.models.data.userinfo.ContactPreference;
import lombok.Data;

public @Data
class ContactPreferenceRequest {

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

    public ContactPreferenceRequest(ContactPreference contactPreference) {
        this.contactByPush = contactPreference.isContactByPush();
        this.contactBySms = contactPreference.isContactBySms();
        this.contactByEmail = contactPreference.isContactByEmail();
        this.contactByPost = contactPreference.isContactByPost();
        this.contactByPhone = contactPreference.isContactByPhone();
        this.contactForResearch = contactPreference.isContactForResearch();
        this.hasSetContactPrefs = true;
    }
}
