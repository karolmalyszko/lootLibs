package io.loot.lootsdk.models.networking;

import com.google.gson.annotations.SerializedName;

import io.loot.lootsdk.models.data.contacts.Contact;
import io.loot.lootsdk.models.orm.RecipientsAndSendersEntity;
import io.loot.lootsdk.utils.RegexUtil;
import lombok.Data;

@Data
public class RecipientsAndSendersResponse {
    @SerializedName("name")
    private String name;
    @SerializedName("account_number")
    private String accountNumber;
    @SerializedName("sort_code")
    private String sortCode;

    public static Contact parseToDataObject(RecipientsAndSendersResponse response) {
        if (response == null) {
            response = new RecipientsAndSendersResponse();
        }
        Contact contact = new Contact();
        contact.setName(response.getName());
        contact.generateInitials();
        contact.generateNameToCompare();;
        contact.setAccountNumber(response.getAccountNumber());
        contact.setSortCode(response.getSortCode());
        contact.setType(Contact.DetailsType.RECIPIENT_SENDER);
        return contact;
    }

    public static RecipientsAndSendersEntity parseToEntityObject(RecipientsAndSendersResponse response) {
        if (response == null) {
            response = new RecipientsAndSendersResponse();
        }
        RecipientsAndSendersEntity recipientsAndSendersEntity = new RecipientsAndSendersEntity();
        recipientsAndSendersEntity.setId(response.getSortCode()+"-"+response.getAccountNumber());
        recipientsAndSendersEntity.setName(response.getName());
        recipientsAndSendersEntity.setAccountNumber(response.getAccountNumber());
        recipientsAndSendersEntity.setSortCode(response.getSortCode());
        recipientsAndSendersEntity.setInitials(RegexUtil.generateInitial(response.getName()));
        recipientsAndSendersEntity.setNameToCompare(RegexUtil.generateNameToCompare(response.getName()));
        return recipientsAndSendersEntity;
    }
}
