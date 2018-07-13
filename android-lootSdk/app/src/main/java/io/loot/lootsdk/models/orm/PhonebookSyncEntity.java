package io.loot.lootsdk.models.orm;

import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import io.loot.lootsdk.models.data.contacts.SyncedContactInfo;

@android.arch.persistence.room.Entity(tableName = "PhonebookSyncEntity")
public class PhonebookSyncEntity {
    @PrimaryKey
    @NonNull
    private String phonebookId;
    private String id;
    private long idHash;
    private String profilePhoto;
    private String name;
    private String initials;
    private String nameToCompare;
    private boolean hasPayments = false;

    public static SyncedContactInfo parseToDataObject(PhonebookSyncEntity contactEntity) {
        if (contactEntity == null) {
            contactEntity = new PhonebookSyncEntity();
        }
        SyncedContactInfo syncedContactInfo = new SyncedContactInfo();
        syncedContactInfo.setContactId(contactEntity.getId());
        syncedContactInfo.setContactIdHash(contactEntity.getIdHash());
        syncedContactInfo.setName(contactEntity.getName());
        syncedContactInfo.setPhonebookId(contactEntity.getPhonebookId());
        syncedContactInfo.setProfilePhoto(contactEntity.getProfilePhoto());
        syncedContactInfo.setHasPayments(contactEntity.hasPayments());
        syncedContactInfo.setInitials(contactEntity.getInitials());
        syncedContactInfo.setNameToCompare(contactEntity.getNameToCompare());
        return syncedContactInfo;
    }

    public static ArrayList<SyncedContactInfo> parseToDataArray(List<PhonebookSyncEntity> contactEntities) {
        if (contactEntities == null) {
            contactEntities = new ArrayList<PhonebookSyncEntity>();
        }
        ArrayList<SyncedContactInfo> syncedContactInfos = new ArrayList<>();
        for (PhonebookSyncEntity contactEntity : contactEntities) {
            syncedContactInfos.add(PhonebookSyncEntity.parseToDataObject(contactEntity));
        }
        return syncedContactInfos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getIdHash() {
        return idHash;
    }

    public void setIdHash(long idHash) {
        this.idHash = idHash;
    }

    @NonNull
    public String getPhonebookId() {
        return phonebookId;
    }

    public void setPhonebookId(@NonNull String phonebookId) {
        this.phonebookId = phonebookId;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public boolean hasPayments() {
        return hasPayments;
    }

    public void setHasPayments(boolean hasPayments) {
        this.hasPayments = hasPayments;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getNameToCompare() {
        return nameToCompare;
    }

    public void setNameToCompare(String nameToCompare) {
        this.nameToCompare = nameToCompare;
    }
}
