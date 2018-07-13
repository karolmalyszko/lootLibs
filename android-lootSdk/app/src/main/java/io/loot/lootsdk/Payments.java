package io.loot.lootsdk;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.loot.lootsdk.exceptions.IncorrectPinException;
import io.loot.lootsdk.exceptions.NoPinOnDeviceException;
import io.loot.lootsdk.exceptions.NoTouchOnDeviceException;
import io.loot.lootsdk.exceptions.NullPointerListenerException;
import io.loot.lootsdk.listeners.payment.MakePaymentListener;
import io.loot.lootsdk.listeners.signup.ResendSMSListener;
import io.loot.lootsdk.listeners.user.PaymentVerificationListener;
import io.loot.lootsdk.models.ActionResult;
import io.loot.lootsdk.models.Resource;
import io.loot.lootsdk.models.data.contacts.Contact;
import io.loot.lootsdk.models.data.transfer.Transfer;
import io.loot.lootsdk.models.networking.payments.CreateManualDetailsPaymentRequest;
import io.loot.lootsdk.models.networking.payments.CreatePaymentResponse;
import io.loot.lootsdk.models.networking.payments.MakePaymentRequest;
import io.loot.lootsdk.models.networking.payments.MakePaymentSMSConfirmationRequest;
import io.loot.lootsdk.networking.ApiResponse;
import io.loot.lootsdk.networking.LootApiInterface;
import io.loot.lootsdk.networking.interceptors.InterceptorType;
import io.loot.lootsdk.networking.interceptors.models.LootHeader;
import io.loot.lootsdk.utils.ActionCompatHandler;
import io.loot.lootsdk.utils.AppExecutors;
import io.loot.lootsdk.utils.KeyStoreUtil;
import io.loot.lootsdk.utils.ResourceCompatHandler;
import okhttp3.ResponseBody;

public class Payments {
    private LootSDK mLootSDK;
    private KeyStoreUtil mKeyStoreUtil;

    Payments(LootSDK lootSDK) {
        mLootSDK = lootSDK;
        mKeyStoreUtil = KeyStoreUtil.getInstance(mLootSDK.getContext());
    }

    public LiveData<Resource<String>> makePasswordPayment(Transfer transfer, Contact contact, String password) {
        if (contact == null) {
            contact = new Contact();
        }
        MakePaymentRequest makePaymentRequest = new MakePaymentRequest(transfer);
        makePaymentRequest.setPassword(password);

        LiveData<Resource<String>> resultLiveData;
        if (contact.getType() == Contact.DetailsType.STANDARD_CONTACT) {
            resultLiveData = makeContactPayment(contact.getContactId(), makePaymentRequest);
        } else if (contact.getType() == Contact.DetailsType.LOOT_PHONEBOOK || contact.getType() == Contact.DetailsType.LOOT_PUBLIC) {
            resultLiveData = makePaymentToPublicId(contact.getContactId(), makePaymentRequest);
        } else {
            CreateManualDetailsPaymentRequest request = new CreateManualDetailsPaymentRequest(transfer);
            request.setPassword(password);
            resultLiveData = makePayment(request);
        }

        return resultLiveData;
    }

    @Deprecated
    public void makePasswordPayment(Transfer transfer, Contact contact, String password, final MakePaymentListener listener) {
        if (listener == null) {
            throw new NullPointerListenerException();
        }
        LiveData<Resource<String>> resultLiveData = makePasswordPayment(transfer, contact, password);
        new ResourceCompatHandler<String>(resultLiveData, listener) {
            @Override
            public void onError(String errorMessage) {
                listener.onPaymentError(errorMessage);
            }

            @Override
            public void onCachedDataReceived(String data) {

            }

            @Override
            public void onLiveDataReceived(String data) {
                if (data != null && !data.equals("")) {
                    listener.onPaymentRequire2FA(data);
                } else {
                    listener.onPaymentMadeSuccessful();
                }
            }
        }.handle();
    }

    public LiveData<Resource<String>> makePinPayment(Transfer transfer, Contact contact, String pin) throws IncorrectPinException {
        if (mKeyStoreUtil.getPinAuthToken() == null || mKeyStoreUtil.getPin() == null ||
                mKeyStoreUtil.getPinAuthToken().isEmpty() || mKeyStoreUtil.getPin().isEmpty()) {
            throw new NoPinOnDeviceException();
        }

        if (!pin.equals(mKeyStoreUtil.getPin())) {
            throw new IncorrectPinException();
        }

        return makeTokenPayment(transfer, contact, mKeyStoreUtil.getPinAuthToken());
    }

    @Deprecated
    public void makePinPayment(Transfer transfer, Contact contact, String pin, final MakePaymentListener listener) throws IncorrectPinException {
        if (listener == null) {
            throw new NullPointerListenerException();
        }
        LiveData<Resource<String>> resultLiveData = makePinPayment(transfer, contact, pin);
        new ResourceCompatHandler<String>(resultLiveData, listener) {
            @Override
            public void onError(String errorMessage) {
                listener.onPaymentError(errorMessage);
            }

            @Override
            public void onCachedDataReceived(String data) {

            }

            @Override
            public void onLiveDataReceived(String data) {
                if (data != null && !data.equals("")) {
                    listener.onPaymentRequire2FA(data);
                } else {
                    listener.onPaymentMadeSuccessful();
                }
            }
        }.handle();
    }

    public LiveData<Resource<String>> makeTouchPayment(Transfer transfer, Contact contact) {

        if (mKeyStoreUtil.getTouchAuthToken() == null ||
                mKeyStoreUtil.getTouchAuthToken().isEmpty()) {
            throw new NoTouchOnDeviceException();
        }

        return makeTokenPayment(transfer, contact, mKeyStoreUtil.getTouchAuthToken());
    }

    @Deprecated
    public void makeTouchPayment(Transfer transfer, Contact contact, final MakePaymentListener listener) {
        if (listener == null) {
            throw new NullPointerListenerException();
        }
        LiveData<Resource<String>> resultLiveData = makeTouchPayment(transfer, contact);
        new ResourceCompatHandler<String>(resultLiveData, listener) {
            @Override
            public void onError(String errorMessage) {
                listener.onPaymentError(errorMessage);
            }

            @Override
            public void onCachedDataReceived(String data) {

            }

            @Override
            public void onLiveDataReceived(String data) {
                if (data != null && !data.equals("")) {
                    listener.onPaymentRequire2FA(data);
                } else {
                    listener.onPaymentMadeSuccessful();
                }
            }
        }.handle();
    }

    private LiveData<Resource<String>> makeTokenPayment(Transfer transfer, Contact contact, String token) {
        if (contact == null) {
            contact = new Contact();
        }
        MakePaymentRequest makePaymentRequest = new MakePaymentRequest(transfer);
        makePaymentRequest.setTouchIdToken(token);

        LiveData<Resource<String>> resultLiveData;
        if (contact.getType() == Contact.DetailsType.STANDARD_CONTACT) {
            resultLiveData = makeContactPayment(contact.getContactId(), makePaymentRequest);
        } else if (contact.getType() == Contact.DetailsType.LOOT_PHONEBOOK || contact.getType() == Contact.DetailsType.LOOT_PUBLIC) {
            resultLiveData = makePaymentToPublicId(contact.getContactId(), makePaymentRequest);
        } else {
            CreateManualDetailsPaymentRequest request = new CreateManualDetailsPaymentRequest(transfer);
            request.setTouchIdToken(token);
            resultLiveData = makePayment(request);
        }

        return resultLiveData;
    }

    @Deprecated
    private void makeTokenPayment(Transfer transfer, Contact contact, String token, final MakePaymentListener listener) {
        if (listener == null) {
            throw new NullPointerListenerException();
        }

        LiveData<Resource<String>> resultLiveData = makeTokenPayment(transfer, contact, token);
        new ResourceCompatHandler<String>(resultLiveData, listener) {
            @Override
            public void onError(String errorMessage) {
                listener.onPaymentError(errorMessage);
            }

            @Override
            public void onCachedDataReceived(String data) {

            }

            @Override
            public void onLiveDataReceived(String data) {
                if (data != null && !data.equals("")) {
                    listener.onPaymentRequire2FA(data);
                } else {
                    listener.onPaymentMadeSuccessful();
                }
            }
        }.handle();
    }

    private LiveData<Resource<String>> makePayment(final CreateManualDetailsPaymentRequest createManualDetailsPaymentRequest) {
        LootHeader lootHeader = mLootSDK.createLootHeader();
        final LootApiInterface lootApiInterface = mLootSDK.createRestClient(InterceptorType.SESSION_TOKEN, lootHeader).getApiService();

        final LiveData<Resource<String>> paymentResult = new NetworkResource<String, CreatePaymentResponse>(AppExecutors.get()) {
            @Override
            protected String proceedData(@NonNull CreatePaymentResponse item) {
                if (item.getRequires2fa() == null || !item.getRequires2fa()) {
                    return "";
                }
                return item.getId();

            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<CreatePaymentResponse>> createCall() {
                return lootApiInterface.createManualDetailsPayment(mLootSDK.user().getGPSAccount().getId(), createManualDetailsPaymentRequest);
            }
        }.asLiveData();

        return mergeManualPaymentResultWithAccountDetails(paymentResult);
    }

    @Deprecated
    private void makePayment(CreateManualDetailsPaymentRequest createManualDetailsPaymentRequest, final MakePaymentListener listener) {
        if (listener == null) {
            throw new NullPointerListenerException();
        }
        LiveData<Resource<String>> resultLiveData = makePayment(createManualDetailsPaymentRequest);
        new ResourceCompatHandler<String>(resultLiveData, listener) {
            @Override
            public void onError(String errorMessage) {
                listener.onPaymentError(errorMessage);
            }

            @Override
            public void onCachedDataReceived(String data) {

            }

            @Override
            public void onLiveDataReceived(String data) {
                if (data != null && !data.equals("")) {
                    listener.onPaymentRequire2FA(data);
                } else {
                    listener.onPaymentMadeSuccessful();
                }
            }
        }.handle();
    }

    private LiveData<Resource<String>> makeContactPayment(final String contactId, final MakePaymentRequest makePaymentRequest) {
        LootHeader lootHeader = mLootSDK.createLootHeader();
        final LootApiInterface lootApiInterface = mLootSDK.createRestClient(InterceptorType.SESSION_TOKEN, lootHeader).getApiService();

        final LiveData<Resource<String>> paymentResult = new NetworkResource<String, CreatePaymentResponse>(AppExecutors.get()) {
            @Override
            protected String proceedData(@NonNull CreatePaymentResponse item) {
                if (item.getRequires2fa() == null || !item.getRequires2fa()) {
                    return "";
                }
                return item.getId();

            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<CreatePaymentResponse>> createCall() {
                return lootApiInterface.makeContactPayment(contactId, makePaymentRequest);
            }
        }.asLiveData();

        return mergeManualPaymentResultWithAccountDetails(paymentResult);
    }

    @Deprecated
    private void makeContactPayment(String contactId, MakePaymentRequest makePaymentRequest, final MakePaymentListener listener) {
        if (listener == null) {
            throw new NullPointerListenerException();
        }

        LiveData<Resource<String>> resultLiveData = makeContactPayment(contactId, makePaymentRequest);
        new ResourceCompatHandler<String>(resultLiveData, listener) {
            @Override
            public void onError(String errorMessage) {
                listener.onPaymentError(errorMessage);
            }

            @Override
            public void onCachedDataReceived(String data) {

            }

            @Override
            public void onLiveDataReceived(String data) {
                if (data != null && !data.equals("")) {
                    listener.onPaymentRequire2FA(data);
                } else {
                    listener.onPaymentMadeSuccessful();
                }
            }
        }.handle();
    }

    private LiveData<Resource<String>> makePaymentToPublicId(final String lootPublicId, final MakePaymentRequest makePaymentRequest) {
        LootHeader lootHeader = mLootSDK.createLootHeader();
        final LootApiInterface lootApiInterface = mLootSDK.createRestClient(InterceptorType.SESSION_TOKEN, lootHeader).getApiService();
        final LiveData<Resource<String>> paymentResult = new NetworkResource<String, CreatePaymentResponse>(AppExecutors.get()) {
            @Override
            protected String proceedData(@NonNull CreatePaymentResponse item) {
                if (item.getRequires2fa() == null || !item.getRequires2fa()) {
                    return "";
                }
                return item.getId();

            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<CreatePaymentResponse>> createCall() {
                return lootApiInterface.makePaymentToPublicId(mLootSDK.user().getGPSAccount().getId(), lootPublicId, makePaymentRequest);
            }
        }.asLiveData();

        return mergeManualPaymentResultWithAccountDetails(paymentResult);

    }

    @Deprecated
    private void makePaymentToPublicId(String lootPublicId, MakePaymentRequest makePaymentRequest, final MakePaymentListener listener) {
        if (listener == null) {
            throw new NullPointerListenerException();
        }

        LiveData<Resource<String>> resultLiveData = makePaymentToPublicId(lootPublicId, makePaymentRequest);
        new ResourceCompatHandler<String>(resultLiveData, listener) {
            @Override
            public void onError(String errorMessage) {
                listener.onPaymentError(errorMessage);
            }

            @Override
            public void onCachedDataReceived(String data) {

            }

            @Override
            public void onLiveDataReceived(String data) {
                if (data != null && !data.equals("")) {
                    listener.onPaymentRequire2FA(data);
                } else {
                    listener.onPaymentMadeSuccessful();
                }
            }
        }.handle();
    }


    public LiveData<ActionResult> verifyPayment(final String paymentId, final String code) {
        LootHeader lootHeader = mLootSDK.createLootHeader();
        final LootApiInterface lootApiInterface = mLootSDK.createRestClient(InterceptorType.SESSION_TOKEN, lootHeader).getApiService();

        final LiveData<ActionResult> paymentResult = new NetworkBoundAction<ResponseBody>(AppExecutors.get()) {
            @Override
            protected void saveCallResult(@NonNull ResponseBody item) {
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<ResponseBody>> createCall() {
                return lootApiInterface.makePaymentSMSConfirmation(mLootSDK.user().getGPSAccount().getId(), paymentId, new MakePaymentSMSConfirmationRequest(code));
            }
        }.asLiveData();

        return mergePaymentResultWithAccountDetails(paymentResult);
    }

    @Deprecated
    public void verifyPayment(String paymentId, String code, final PaymentVerificationListener listener) {
        if (listener == null) {
            throw new NullPointerListenerException();
        }

        LiveData<ActionResult> resultLiveData = verifyPayment(paymentId, code);
        new ActionCompatHandler(resultLiveData, listener) {
            @Override
            public void onError(String errorMessage) {
                listener.onPaymentVerificationError(errorMessage);
            }

            @Override
            public void onSuccess() {
                listener.onPaymentVerificationSuccess();
            }
        }.handle();
    }

    public LiveData<ActionResult> resendPaymentVerifySms(final String paymentId) {
        LootHeader lootHeader = mLootSDK.createLootHeader();
        final LootApiInterface lootApiInterface = mLootSDK.createRestClient(InterceptorType.SESSION_TOKEN, lootHeader).getApiService();
        return new NetworkBoundAction<ResponseBody>(AppExecutors.get()) {
            @Override
            protected void saveCallResult(@NonNull ResponseBody item) {
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<ResponseBody>> createCall() {
                return lootApiInterface.resendPaymentSMSConfirmation(mLootSDK.user().getGPSAccount().getId(), paymentId);
            }
        }.asLiveData();
    }

    @Deprecated
    public void resendPaymentVerifySms(String paymentId, final ResendSMSListener listener) {
        if (listener == null) {
            throw new NullPointerListenerException();
        }

        LiveData<ActionResult> resultLiveData = resendPaymentVerifySms(paymentId);
        new ActionCompatHandler(resultLiveData, listener) {
            @Override
            public void onError(String errorMessage) {
                listener.onError(errorMessage);
            }

            @Override
            public void onSuccess() {
                listener.onSMSSent();
            }
        }.handle();
    }


    private LiveData<ActionResult> mergePaymentResultWithAccountDetails(final LiveData<ActionResult> paymentResult) {
        final MediatorLiveData<ActionResult> resultLiveData = new MediatorLiveData<>();
        resultLiveData.addSource(paymentResult, new Observer<ActionResult>() {
            @Override
            public void onChanged(@Nullable ActionResult authorizationResult) {
                if (authorizationResult == null) {
                    return;
                }

                if (authorizationResult.isSuccess() && !authorizationResult.isLoading()) {
                    resultLiveData.removeSource(paymentResult);
                    resultLiveData.addSource(mLootSDK.user().fetchAccountDetails(), new Observer<ActionResult>() {
                        @Override
                        public void onChanged(@Nullable ActionResult result) {
                            if (result == null) {
                                return;
                            }

                            resultLiveData.setValue(result);
                        }
                    });
                } else {
                    resultLiveData.setValue(authorizationResult);
                }
            }
        });

        return resultLiveData;
    }

    private LiveData<Resource<String>> mergeManualPaymentResultWithAccountDetails(final LiveData<Resource<String>> paymentResult) {
        final MediatorLiveData<Resource<String>> resultLiveData = new MediatorLiveData<>();
        resultLiveData.addSource(paymentResult, new Observer<Resource<String>>() {
            @Override
            public void onChanged(@Nullable Resource<String> result2FA) {
                if (result2FA == null) {
                    return;
                }

                if (result2FA.isSuccessful() && !result2FA.isLoading() && (result2FA.getData() == null || result2FA.getData().equals(""))) {
                    resultLiveData.removeSource(paymentResult);
                    resultLiveData.addSource(mLootSDK.user().fetchAccountDetails(), new Observer<ActionResult>() {
                        @Override
                        public void onChanged(@Nullable ActionResult result) {
                            if(!result.isLoading()) {
                                resultLiveData.setValue(Resource.success((String) ""));
                            }
                        }
                    });
                } else {
                    resultLiveData.setValue(result2FA);
                }
            }
        });

        return resultLiveData;
    }

}