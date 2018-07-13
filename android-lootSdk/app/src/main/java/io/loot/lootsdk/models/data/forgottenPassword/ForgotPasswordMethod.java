package io.loot.lootsdk.models.data.forgottenPassword;


public class ForgotPasswordMethod {
    public static final String VERIFY_ID = "VERIFY_ID";
    public static final String EMAIL = "EMAIL";
    public static final String BEFORE_KYC = "0";
    public static final String AFTER_KYC = "1";

    String methodCode;
    String methodName;

    public ForgotPasswordMethod() {
        this.methodCode = "";
        this.methodName = "";
    }

    public ForgotPasswordMethod(ForgotPasswordMethod forgotPasswordMethod) {
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
