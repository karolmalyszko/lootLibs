package io.loot.lootsdk.models.data.forgottenPassword;

public class ProvidePasswordMethod {
    String methodCode;
    String methodName;

    public ProvidePasswordMethod() {
        this.methodCode = "";
        this.methodName = "";
    }

    public ProvidePasswordMethod(ForgotPasswordMethod forgotPasswordMethod) {
        this.methodCode = forgotPasswordMethod.getMethodCode();
        this.methodName = forgotPasswordMethod.getMethodName();
    }

    public String getMethodCode() {
        return methodCode;
    }

    public void setMethodCode(String methodCode) {
        if (methodCode == null) {
            methodCode = "";
        }
        this.methodCode = methodCode;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        if (methodName == null) {
            methodName = "";
        }
        this.methodName = methodName;
    }
}
