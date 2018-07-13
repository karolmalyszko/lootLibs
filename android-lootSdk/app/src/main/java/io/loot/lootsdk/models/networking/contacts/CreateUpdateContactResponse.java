package io.loot.lootsdk.models.networking.contacts;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Data;

@Data
public class CreateUpdateContactResponse implements Serializable {
    @SerializedName("contact_id")
    private String id;

    public CreateUpdateContactResponse() {
        id = "";
    }

    public static String parseToDataObject(CreateUpdateContactResponse createUpdateContactResponse) {
        if (createUpdateContactResponse == null) {
            createUpdateContactResponse = new CreateUpdateContactResponse();
        }
        return createUpdateContactResponse.getId();
    }
}
