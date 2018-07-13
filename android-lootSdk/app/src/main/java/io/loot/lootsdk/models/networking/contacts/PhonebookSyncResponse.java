package io.loot.lootsdk.models.networking.contacts;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import io.loot.lootsdk.models.orm.PhonebookSyncEntity;
import io.loot.lootsdk.utils.RegexUtil;
import lombok.Data;

@Data
public class PhonebookSyncResponse {
    @SerializedName("id")
    private String id;
    @SerializedName("phonebook_id")
    private String phonebookId;
    @SerializedName("picture_url")
    private String profilePhoto;
    @SerializedName("has_payments")
    private boolean hasPayments;

    public PhonebookSyncResponse() {
        this.id = "";
        this.phonebookId = "";
    }

    public static PhonebookSyncEntity parseToEntityObject(PhonebookSyncResponse response, PhonebookSyncRequestItem foundItem) {
        if (foundItem == null) {
            return new PhonebookSyncEntity();
        }
        PhonebookSyncEntity phonebookSyncEntity = new PhonebookSyncEntity();
        if(response != null) {
            phonebookSyncEntity.setId(response.getId());
            phonebookSyncEntity.setIdHash(response.getId().hashCode());
            phonebookSyncEntity.setHasPayments(response.isHasPayments());
            phonebookSyncEntity.setProfilePhoto(response.getProfilePhoto());
        } else {
            phonebookSyncEntity.setIdHash(foundItem.getPhonebookId().hashCode());
        }
        if (phonebookSyncEntity.getProfilePhoto() == null || phonebookSyncEntity.getProfilePhoto().isEmpty()) {
            if (foundItem != null && foundItem.getProfilePhoto() != null) {
                phonebookSyncEntity.setProfilePhoto(foundItem.getProfilePhoto());
            }
        }
        phonebookSyncEntity.setName(foundItem.getName());
        phonebookSyncEntity.setInitials(RegexUtil.generateInitial(foundItem.getName()));
        phonebookSyncEntity.setNameToCompare(RegexUtil.generateNameToCompare(foundItem.getName()));
        phonebookSyncEntity.setPhonebookId(foundItem.getPhonebookId());
        return phonebookSyncEntity;
    }

    public static ArrayList<PhonebookSyncEntity> parseToEntitiesArray(ArrayList<PhonebookSyncResponse> responses, ArrayList<PhonebookSyncRequestItem> requestItems) {
        if(responses == null) {
            responses = new ArrayList<PhonebookSyncResponse>();
        }
        ArrayList<PhonebookSyncEntity> syncedContactInfoArray = new ArrayList<PhonebookSyncEntity>();
        int i;
        for(PhonebookSyncResponse phonebookSyncResponse : responses) {
            PhonebookSyncRequestItem foundItem = null;
            for(i = 0; i<requestItems.size(); i++) {
                if (requestItems.get(i).getPhonebookId().equals(phonebookSyncResponse.getPhonebookId())){
                    foundItem = requestItems.get(i);
                    break;
                }
            }
            if (foundItem != null) {
                syncedContactInfoArray.add(PhonebookSyncResponse.parseToEntityObject(phonebookSyncResponse, foundItem));
            }
            try {
                requestItems.remove(i);
            } catch (Exception e) {}
        }

        for(PhonebookSyncRequestItem request: requestItems) {
            if (request != null) {
                syncedContactInfoArray.add(PhonebookSyncResponse.parseToEntityObject(null, request));
            }
        }
        return syncedContactInfoArray;
    }
}
