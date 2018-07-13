package io.loot.lootsdk.models.networking.forgottenPassword;

import com.google.gson.annotations.SerializedName;

import io.loot.lootsdk.models.data.forgottenPassword.ProvidePasswordMethod;
import lombok.Data;

@Data
public class ProvidePasswordMethodResponse {
    @SerializedName("method_name")
    String methodName;
    @SerializedName("method_code")
    String methodCode;

    public static ProvidePasswordMethod parseToDataObject(ProvidePasswordMethodResponse providePasswordMethodResponse) {
        if (providePasswordMethodResponse == null) {
            providePasswordMethodResponse = new ProvidePasswordMethodResponse();
        }
        ProvidePasswordMethod providePasswordMethod = new ProvidePasswordMethod();
        providePasswordMethod.setMethodCode(providePasswordMethodResponse.getMethodCode());
        providePasswordMethod.setMethodName(providePasswordMethodResponse.getMethodName());
        return providePasswordMethod;
    }
}
