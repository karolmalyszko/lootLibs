package io.loot.lootsdk.networking;

import android.content.Context;
import android.os.Bundle;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;

import io.loot.lootsdk.LootSDK;
import io.loot.lootsdk.models.data.cards.CardStatus;
import io.loot.lootsdk.models.networking.payments.CreateManualDetailsPaymentRequest;
import io.loot.lootsdk.models.networking.payments.MakePaymentRequest;
import io.loot.lootsdk.models.networking.payments.MakePaymentSMSConfirmationRequest;
import okhttp3.Interceptor;
import okhttp3.RequestBody;
import okio.Buffer;

public class OfflineResponseStringBuilder {
    private static CardStatus mCardStatus = CardStatus.ORDERED;
    public static String ERROR422 = "ERROR422";
    public static String getResponse(final Context context, Interceptor.Chain chain) {

        final String baseUrl = LootSDK.OFFLINE_URI;
        String endpoint = chain.request().url().toString().substring(baseUrl.length());

        if (endpoint.startsWith("W8S-Z3TCK-STK89-3Z3MN/autocomplete/v2/uk/")){
                endpoint = "/postcoder";
        }
        else if (endpoint.startsWith("W8S-Z3TCK-STK89-3Z3MN/address/uk/")) {
            endpoint = "/postcoder-select";
        }
        else if (endpoint.contains("?")) {
            endpoint = endpoint.substring(0, endpoint.indexOf("?"));
        } else if (endpoint.startsWith("/v2/users/contacts")) {
            if (endpoint.contains("verify")) {
                endpoint = "/v2/users/contacts/verify";
            } else if (endpoint.contains("resend_sms")) {
                endpoint = "/v2/users/contacts/resend_sms";
            } else if (endpoint.contains("phonebook-sync")) {
                endpoint = "/v2/users/contacts/phonebook-sync";
            } else {
                endpoint = "/v2/users/contacts";
            }
        } else if (endpoint.startsWith("/v2/users/accounts/1/payments/") && !endpoint.endsWith("/make")) {
            endpoint = "/v2/users/accounts/1/payments/LOOT-ID";
        }



        Gson gson = new Gson();

        switch (endpoint) {
            case "/v2/sessions":
                return loadJSONFromAsset("createUserSession", context);
            case "/v2/categories":
                return loadJSONFromAsset("categories", context);
            case "/v1/users/change-password":
                return "";
            case "/v2/users":
                return loadJSONFromAsset("users", context);
            case "/v2/users/upload-image-profile":
                return loadJSONFromAsset("users", context);
            case "/v2/account-status":
                return loadJSONFromAsset("accountStatus", context);
            case "/v2/users/budget":
                return loadJSONFromAsset("budget", context);
            case "/v2/users/accounts":
                return loadJSONFromAsset("userAccounts", context);
            case "/v2/users/accounts/1/transactions":
                return loadJSONFromAsset("transactions", context);
            case "/v2/users/accounts/1/transactions/1":
                if (chain.request().method().equals("PUT")) {
                    return loadJSONFromAsset("transaction", context);
                }
                return loadJSONFromAsset("transactions", context);
            case "/v2/transactions/to-be-confirmed":
                return loadJSONFromAsset("transactions-to-confirm", context);
            case "/v2/users/accounts/1/cards":
                if (mCardStatus == CardStatus.ACTIVE) {
                    return loadJSONFromAsset("cardsActive", context);
                } else if (mCardStatus == CardStatus.INACTIVE) {
                    return loadJSONFromAsset("cardsPaused", context);
                } else if (mCardStatus == CardStatus.STOLEN) {
                    return loadJSONFromAsset("cardsStolen", context);
                } else if (mCardStatus == CardStatus.LOST) {
                    return loadJSONFromAsset("cardsLost", context);
                } else if (mCardStatus == CardStatus.ORDERED) {
                    return loadJSONFromAsset("cardsOrdered", context);
                }
            case "/v2/users/accounts/1/cards/0/change-status":
                String requestBody = bodyToString(chain.request().body());
                switch (requestBody) {
                    case "card_status=paused":
                        mCardStatus = CardStatus.INACTIVE;
                        break;
                    case "card_status=lost":
                        mCardStatus = CardStatus.LOST;
                        break;
                    case "card_status=stolen":
                        mCardStatus = CardStatus.STOLEN;
                        break;
                    case "card_status=active":
                        mCardStatus = CardStatus.ACTIVE;
                        break;
                }
            case "/v2/users/accounts/1/cards/0/start-pin-retrieval":
                return loadJSONFromAsset("verify-pin-retrieval", context);
            case "/v2/users/accounts/1/cards/0/verify-pin-retrieval":
                return loadJSONFromAsset("verify-pin-retrieval", context);
            case "/v2/users/accounts/1/cards/0/start-activation":
                return loadJSONFromAsset("verify-pin-retrieval", context);
            case "/v2/users/accounts/1/cards/0/verify-activation":
                mCardStatus = CardStatus.ACTIVE;
                return loadJSONFromAsset("verify-pin-retrieval", context);
            case "/v2/users/accounts/1/goals":
                return loadJSONFromAsset("goals", context);
            case "/v2/users/accounts/1/goals/0":
                return loadJSONFromAsset("goal", context);
            case "/v2/users/accounts/1/goals/0/unload-money":
                sendNotification(context, "new_transaction", loadJSONFromAsset("unload-notify", context));
                return loadJSONFromAsset("goal", context);
            case "/v2/users/accounts/1/goals/0/load-money":
                sendNotification(context, "new_transaction", loadJSONFromAsset("make-transfer-notify", context));
                return loadJSONFromAsset("goal", context);
            case "/v2/users/accounts/1/goals/0/delete":
                return loadJSONFromAsset("goal", context);
            case "/v2/sign-up/":
                return loadJSONFromAsset("sign-up", context);
            case "/v2/sign-up/change-number":
                return loadJSONFromAsset("sign-up", context);
            case "/v2/sign-up/resume":
                return loadJSONFromAsset("verify-number", context);
            case "/v2/sign-up/verify-number":
                return loadJSONFromAsset("verify-number", context);
            case "/v2/sign-up/start-kyc":
                return loadJSONFromAsset("verify-number", context);
            case "/v2/sign-up/personal-data":
                return loadJSONFromAsset("personal-data", context);
            case "/v2/sign-up/profile-photo/":
                return loadJSONFromAsset("profile-photo", context);
            case "/v2/sign-up/get-event-data/":
                return loadJSONFromAsset("get-event-data", context);
            case "/v2/sign-up/scans/face":
                return ERROR422;
            case "/v2/sign-up/confirm-data/":
                return loadJSONFromAsset("confirm-data", context);
            case "/postcoder":
                return loadJSONFromAsset("postcoder", context);
            case "/postcoder-select":
                return loadJSONFromAsset("postcoder-select", context);
            case "/v2/sign-up/add-notification-token":
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            sleep(3000);
                            sendNotification(context, "kyc", loadJSONFromAsset("kyc-notify", context));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };

                thread.start();
                return loadJSONFromAsset("profile-photo", context);
            case "/v2/users/accounts/1/transfers/make":
                sendNotification(context, "new_transaction", loadJSONFromAsset("make-transfer-notify", context));
                return "";
            case "/v2/users/contacts":
                return loadJSONFromAsset("contacts_"+chain.request().method().toLowerCase(), context);
            case "/v2/users/contacts/verify":
                return loadJSONFromAsset("contacts_verify", context);
            case "/v2/users/contacts/resend-sms":
                return loadJSONFromAsset("contacts_resend_sms", context);
            case "/v2/users/accounts/1/payments":
                CreateManualDetailsPaymentRequest details = gson.fromJson(bodyToString(chain.request().body()), CreateManualDetailsPaymentRequest.class);
                if (details.getRecipientName() != null && details.getRecipientName().replaceAll("^(?=.*\\p{Lu})(?=.*_)(?!.*\\p{Ll}).+$", "").isEmpty()){
                    return loadJSONFromAsset("error", context).replaceAll("ERROR_CODE", details.getRecipientName());
                }
                if(details.getReference() != null && details.getReference().contains("2FA")) {
                    return loadJSONFromAsset("createManualPayment2FA", context);
                }
                return loadJSONFromAsset("createManualPaymentNo2FA", context);

            case "/v2/users/accounts/1/payments/TEST-ID/make":
                MakePaymentSMSConfirmationRequest smsConfirmationRequest = gson.fromJson(bodyToString(chain.request().body()), MakePaymentSMSConfirmationRequest.class);
                if (smsConfirmationRequest.getCode() != null && smsConfirmationRequest.getCode().equals("0000")) {
                    return loadJSONFromAsset("error", context).replaceAll("ERROR_CODE", "CODE_MISMATCH");
                }
                return "";
            case "/v2/users/accounts/1/payments/LOOT-ID":
                MakePaymentRequest makePaymentRequest = gson.fromJson(bodyToString(chain.request().body()), MakePaymentRequest.class);
                if (makePaymentRequest.getReference() != null && makePaymentRequest.getReference().replaceAll("^(?=.*\\p{Lu})(?=.*_)(?!.*\\p{Ll}).+$", "").isEmpty()){
                    return loadJSONFromAsset("error", context).replaceAll("ERROR_CODE", makePaymentRequest.getReference());
                }
                return "";
            case "/v2/users/contacts/phonebook-sync":
                return loadJSONFromAsset("phonebookSync", context);
            default:
                return "";
        }
    }

    public static String loadJSONFromAsset(String name, Context context) {
        String json = null;
        try {
            InputStream is = context.getResources().getAssets().open(name + ".json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private static String bodyToString(final RequestBody request) {
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            if (copy != null)
                copy.writeTo(buffer);
            else
                return "";
            return buffer.readUtf8();
        } catch (final IOException e) {
            return e.toString();
        }
    }

    private static void sendNotification(Context context, String id, String message) {
        Bundle bundle = new Bundle();
        bundle.putString("notification_id", id);
        bundle.putString("message", message);
        //TODO MOVE THIS
        //NotifyUtil.sendNotification(context, bundle);

    }

}
