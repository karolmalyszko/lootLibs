package io.loot.lootsdk.models.networking.contacts;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Data;

@Data
public class CreateUpdateContactRequest implements Serializable {
    @SerializedName("name")
    private String name;
    @SerializedName("account_number")
    private String accountNumber;
    @SerializedName("sort_code")
    private String sortCode;
}
