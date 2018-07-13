package io.loot.lootsdk.models.data.userinfo;

import java.io.Serializable;

public class ContactPreference implements Serializable {

    private boolean contactByPush;
    private boolean contactBySms;
    private boolean contactByEmail;
    private boolean contactByPost;
    private boolean contactByPhone;
    private boolean contactForResearch;
    private boolean hasSetContactPrefs;

    public boolean isContactByPush() {
        return contactByPush;
    }

    public void setContactByPush(boolean contactByPush) {
        this.contactByPush = contactByPush;
    }

    public boolean isContactBySms() {
        return contactBySms;
    }

    public void setContactBySms(boolean contactBySms) {
        this.contactBySms = contactBySms;
    }

    public boolean isContactByEmail() {
        return contactByEmail;
    }

    public void setContactByEmail(boolean contactByEmail) {
        this.contactByEmail = contactByEmail;
    }

    public boolean isContactByPost() {
        return contactByPost;
    }

    public void setContactByPost(boolean contactByPost) {
        this.contactByPost = contactByPost;
    }

    public boolean isContactByPhone() {
        return contactByPhone;
    }

    public void setContactByPhone(boolean contactByPhone) {
        this.contactByPhone = contactByPhone;
    }

    public boolean isContactForResearch() {
        return contactForResearch;
    }

    public void setContactForResearch(boolean contactForResearch) {
        this.contactForResearch = contactForResearch;
    }

    public boolean isHasSetContactPrefs() {
        return hasSetContactPrefs;
    }

    public void setHasSetContactPrefs(boolean hasSetContactPrefs) {
        this.hasSetContactPrefs = hasSetContactPrefs;
    }
}
