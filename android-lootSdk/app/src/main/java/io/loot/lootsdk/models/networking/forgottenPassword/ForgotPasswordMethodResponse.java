package io.loot.lootsdk.models.networking.forgottenPassword;


import com.google.gson.annotations.SerializedName;

import io.loot.lootsdk.models.data.forgottenPassword.ForgotPasswordMethod;
import lombok.Data;

@Data
public class ForgotPasswordMethodResponse {
    @SerializedName("method_code")
    String methodCode;
    @SerializedName("method_name")
    String methodName;

    public static ForgotPasswordMethod parseToDataObject(ForgotPasswordMethodResponse forgotPasswordMethodResponse) {
        if (forgotPasswordMethodResponse == null) {
            forgotPasswordMethodResponse = new ForgotPasswordMethodResponse();
        }
        ForgotPasswordMethod forgotPasswordMethod = new ForgotPasswordMethod();
        forgotPasswordMethod.setMethodCode(forgotPasswordMethodResponse.getMethodCode());
        forgotPasswordMethod.setMethodName(forgotPasswordMethodResponse.getMethodName());
        return forgotPasswordMethod;
    }
}
