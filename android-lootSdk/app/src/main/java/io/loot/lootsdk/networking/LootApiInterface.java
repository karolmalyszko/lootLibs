package io.loot.lootsdk.networking;

import android.arch.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

import io.loot.lootsdk.models.networking.authToken.AuthDeviceResponse;
import io.loot.lootsdk.models.networking.authToken.AuthDevicesResponse;
import io.loot.lootsdk.models.networking.authToken.AuthTokenResponse;
import io.loot.lootsdk.models.networking.authToken.DeleteAuthTokenRequest;
import io.loot.lootsdk.models.networking.cards.CardActivationResponse;
import io.loot.lootsdk.models.networking.cards.CardsListResponse;
import io.loot.lootsdk.models.networking.cards.PinRequest;
import io.loot.lootsdk.models.networking.category.CategorisationRequest;
import io.loot.lootsdk.models.networking.category.CategoryListResponse;
import io.loot.lootsdk.models.networking.contacts.ContactListResponse;
import io.loot.lootsdk.models.networking.contacts.ContactTransactionHistoryResponse;
import io.loot.lootsdk.models.networking.contacts.ContactVerifyRequest;
import io.loot.lootsdk.models.networking.contacts.CreateUpdateContactRequest;
import io.loot.lootsdk.models.networking.contacts.CreateUpdateContactResponse;
import io.loot.lootsdk.models.networking.contacts.PhonebookSyncRequest;
import io.loot.lootsdk.models.networking.contacts.PhonebookSyncResponse;
import io.loot.lootsdk.models.networking.feesAndLimits.FeeOrLimitResponse;
import io.loot.lootsdk.models.networking.forgottenPassword.ForgotPasswordConfirmEmailRequest;
import io.loot.lootsdk.models.networking.forgottenPassword.ForgotPasswordConfirmRequest;
import io.loot.lootsdk.models.networking.forgottenPassword.ForgotPasswordMethodResponse;
import io.loot.lootsdk.models.networking.forgottenPassword.PhoneInvalidRequest;
import io.loot.lootsdk.models.networking.forgottenPassword.PhoneValidationNameAndCodeResponse;
import io.loot.lootsdk.models.networking.forgottenPassword.ProvidePasswordMethodResponse;
import io.loot.lootsdk.models.networking.forgottenPassword.ProvidePasswordRequest;
import io.loot.lootsdk.models.networking.forgottenPassword.ResetPasswordRequest;
import io.loot.lootsdk.models.networking.payments.CreateManualDetailsPaymentRequest;
import io.loot.lootsdk.models.networking.payments.CreatePaymentResponse;
import io.loot.lootsdk.models.networking.payments.MakePaymentRequest;
import io.loot.lootsdk.models.networking.payments.MakePaymentSMSConfirmationRequest;
import io.loot.lootsdk.models.networking.policyAndTerms.TermsAndConditions;
import io.loot.lootsdk.models.networking.pushNotifications.LoginPushNotificationRequest;
import io.loot.lootsdk.models.networking.pushNotifications.SignupPushNotificationRequest;
import io.loot.lootsdk.models.networking.savingGoals.CreateSavingsGoalRequest;
import io.loot.lootsdk.models.networking.savingGoals.GoalTransferMoneyRequest;
import io.loot.lootsdk.models.networking.savingGoals.SavingsGoalResponse;
import io.loot.lootsdk.models.networking.savingGoals.SavingsGoalsListResponse;
import io.loot.lootsdk.models.networking.savingGoals.UpdateSavingsGoalRequest;
import io.loot.lootsdk.models.networking.sessions.AccountStatusResponse;
import io.loot.lootsdk.models.networking.sessions.LoginRequest;
import io.loot.lootsdk.models.networking.sessions.LoginResponse;
import io.loot.lootsdk.models.networking.signup.AccountStatusRequest;
import io.loot.lootsdk.models.networking.signup.ConfirmDataResponse;
import io.loot.lootsdk.models.networking.signup.OnBoardingUserDataResponse;
import io.loot.lootsdk.models.networking.signup.PersonalDataResponse;
import io.loot.lootsdk.models.networking.signup.PhoneNumberRequest;
import io.loot.lootsdk.models.networking.signup.SignupRequest;
import io.loot.lootsdk.models.networking.signup.SignupResponse;
import io.loot.lootsdk.models.networking.signup.StartKYCRequest;
import io.loot.lootsdk.models.networking.topUp.TopUp3DSStatusResponse;
import io.loot.lootsdk.models.networking.topUp.TopUpLimitsResponse;
import io.loot.lootsdk.models.networking.topUp.TopUpRequest;
import io.loot.lootsdk.models.networking.topUp.TopUpResponse;
import io.loot.lootsdk.models.networking.transactions.IncludeExcludeResponse;
import io.loot.lootsdk.models.networking.transactions.SpendingResponse;
import io.loot.lootsdk.models.networking.transactions.TransactionListResponse;
import io.loot.lootsdk.models.networking.transfer.MakeTransferRequest;
import io.loot.lootsdk.models.networking.user.BudgetResponse;
import io.loot.lootsdk.models.networking.user.ChangePasswordRequest;
import io.loot.lootsdk.models.networking.user.ContactPreferencesRequest;
import io.loot.lootsdk.models.networking.user.ContactPreferencesResponse;
import io.loot.lootsdk.models.networking.user.SetBudgetRequest;
import io.loot.lootsdk.models.networking.user.UploadProfileImageRequest;
import io.loot.lootsdk.models.networking.user.userinfo.AccountDetailsResponse;
import io.loot.lootsdk.models.networking.user.userinfo.UserDataUpdateRequest;
import io.loot.lootsdk.models.networking.user.userinfo.UserResponse;
import io.loot.lootsdk.models.networking.waitingList.PositionDetailsResponse;
import io.loot.lootsdk.models.networking.waitingList.SignUpWaitingListRequest;
import io.loot.lootsdk.models.networking.waitingList.WaitingListNotificationTokenRequest;
import io.loot.lootsdk.models.networking.waitingList.WaitingListResendEmailRequest;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.HTTP;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface LootApiInterface {

    @POST("/v2/sessions")
    LiveData<ApiResponse<LoginResponse>> login(@Body LoginRequest loginRequest);

    @GET("/v2/categories")
    LiveData<ApiResponse<CategoryListResponse>> getCategories();

    @POST("/v2/users/change-password")
    LiveData<ApiResponse<ResponseBody>> changePassword(@Body ChangePasswordRequest passwordRequest);

    @PUT("/v2/users/close-account")
    Call<ResponseBody> closeAccount();

    @GET("/v2/users")
    LiveData<ApiResponse<UserResponse>> getPersonalDetails();

    @PUT("/v2/users")
    LiveData<ApiResponse<UserResponse>> updateUser(@Body UserDataUpdateRequest userDataUpdateRequest);

    @POST("/v2/waiting-list/")
    Call<ResponseBody> signUpToWaitingList(@Body SignUpWaitingListRequest signUpWaitingListRequest);

    @GET("/v2/waiting-list/{token}")
    Call<PositionDetailsResponse> getWaitingListPosition(@Path("token") String token);

    @POST("/v2/waiting-list/resend-email")
    Call<ResponseBody> resendVerificationEmailWaitingList(@Body WaitingListResendEmailRequest resendEmailRequest);

    @POST("/v2/waiting-list/notification-token")
    Call<ResponseBody> signupToWaitingListPushNotification(@Body WaitingListNotificationTokenRequest request);

    @POST("/v2/users/reset-password/provide-password")
    LiveData<ApiResponse<ProvidePasswordMethodResponse>> providePassword(@Body ProvidePasswordRequest providePasswordRequest);

    @GET("/v2/users/reset-password/check-phone-validation")
    LiveData<ApiResponse<PhoneValidationNameAndCodeResponse>> checkPhoneValidation();

    @POST("/v2/users/reset-password/set-phone-invalid")
    LiveData<ApiResponse<ResponseBody>> setPhoneInvalid(@Body PhoneInvalidRequest phoneInvalidRequest);

    @POST("/v2/users/reset-password")
    LiveData<ApiResponse<ForgotPasswordMethodResponse>> resetPassword(@Body ResetPasswordRequest request);

    @POST("/v2/users/reset-password/sms-confirmation")
    LiveData<ApiResponse<ResponseBody>> resetPasswordConfirm(@Body ForgotPasswordConfirmRequest request);

    @POST("/v2/users/reset-password/email-confirmation")
    LiveData<ApiResponse<ResponseBody>> resetPasswordConfirmEmail(@Body ForgotPasswordConfirmEmailRequest request);

    @POST("/v2/users/upload-image-profile")
    LiveData<ApiResponse<UserResponse>> uploadProfileImage(@Body UploadProfileImageRequest uploadProfileImageRequest);

    @GET("/v2/users/accounts/{userId}/transactions")
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    LiveData<ApiResponse<TransactionListResponse>> getTransactionsOnPage(@Path("userId") String userId, @Query("from") String from, @Query("to") String to, @Query("page") int page, @Query("limit") int limit);

    @GET("/v2/users/accounts/{userId}/transactions")
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    Call<TransactionListResponse> getTransactionsOnPagePag(@Path("userId") String userId, @Query("from") String from, @Query("to") String to, @Query("page") int page, @Query("limit") int limit);

    @GET("/v2/users/accounts/{userId}/transactions")
    @Headers({"Content-Type: application/json", "Accept: application/pdf"})
    LiveData<ApiResponse<ResponseBody>> getPDFStatement(@Path("userId") String userId, @Query("from") String from, @Query("to") String to);

    @GET("/v2/users/accounts/{userId}/transactions")
    @Headers({"Content-Type: text/html", "Accept: text/html"})
    LiveData<ApiResponse<ResponseBody>> getStatementPreview(@Path("userId") String userId, @Query("from") String from, @Query("to") String to);

    // Seriously, HEAD must have url in parameter because of lack of @Path support from Retrofit2
    @HEAD
    LiveData<ApiResponse<Void>> checkStatementAvailability(@Url String url);

    @PUT("/v2/users/accounts/{userId}/transactions/{transactionId}")
    @FormUrlEncoded
    LiveData<ApiResponse<IncludeExcludeResponse>> setTransactionStatus(@Path("userId") String userId, @Path("transactionId") String transactionId, @Field("budget_status") String budgetStatus);

    @GET("/v2/transactions/to-be-confirmed")
    LiveData<ApiResponse<TransactionListResponse>> getTransactionsToConfirm();

    @GET("/v2/users/accounts/{userId}/transactions")
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    LiveData<ApiResponse<TransactionListResponse>> getAllTransactions(@Path("userId") String userId);

    @GET("/v2/transactions/{transactionId}/available-categories")
    LiveData<ApiResponse<CategoryListResponse>> getAvailableCategories(@Path("transactionId") String transactionId);

    @POST("/v2/transactions/{transactionId}/assign_category")
    LiveData<ApiResponse<ResponseBody>> categoriseTransaction(@Path("transactionId") String transactionId, @Body CategorisationRequest categorisationRequest);

    @GET("/v2/spendings/by-category/{categoryId}/{date}")
    LiveData<ApiResponse<SpendingResponse>> getSpendingsByCategory(@Path("categoryId") String categoryId, @Path("date") String date);

    @GET("/v2/spendings/by-merchant/{merchantId}/{date}")
    LiveData<ApiResponse<SpendingResponse>> getSpendingsByMerchant(@Path("merchantId") String merchantId, @Path("date") String date);

    @GET("/v2/users/accounts")
    LiveData<ApiResponse<AccountDetailsResponse>> getAccountDetails();

    @GET("/v2/users/accounts")
    Call<AccountDetailsResponse> fetchAccountDetails();

    @GET("/v2/users/budget")
    LiveData<ApiResponse<BudgetResponse>> getBudget();

    @GET("/v2/users/budget")
    Call<BudgetResponse> fetchBudget();

    @POST("/v2/users/budget")
    LiveData<ApiResponse<BudgetResponse>> setBudget(@Body SetBudgetRequest budgetSetRequest);

    @POST("/v2/users/notification-token")
    LiveData<ApiResponse<ResponseBody>> sendNotificationToken(@Body LoginPushNotificationRequest loginPushRequest);

    @DELETE("/v2/users/budget")
    LiveData<ApiResponse<ResponseBody>> deleteBudget();

    @POST("/v2/sign-up/")
    LiveData<ApiResponse<SignupResponse>> signUp(@Body SignupRequest signupRequest);

    @PUT("/v2/sign-up/change-number")
    LiveData<ApiResponse<SignupResponse>> changePhoneNumber(@Body PhoneNumberRequest newPhoneNumber);

    @POST("/v2/sign-up/verify-number")
    @FormUrlEncoded
    LiveData<ApiResponse<SignupResponse>> verifyPhoneNumber(@Field("code") String code);

    @POST("/v2/sign-up/resend-sms")
    LiveData<ApiResponse<SignupResponse>> resendVerificationSms();

    @POST("/v2/sign-up/personal-data")
    LiveData<ApiResponse<OnBoardingUserDataResponse>> addPersonalData(@Body PersonalDataResponse personalDataRequest);

    @POST("/v2/sign-up/start-kyc")
    LiveData<ApiResponse<OnBoardingUserDataResponse>> startKYCVerification(@Body StartKYCRequest startKYCRequest);

    @POST("/v2/sign-up/profile-photo/")
    @FormUrlEncoded
    LiveData<ApiResponse<OnBoardingUserDataResponse>> uploadProfilePhoto(@Field("image") String base64Image);

    @GET("/v2/sign-up/get-event-data/")
    LiveData<ApiResponse<OnBoardingUserDataResponse>> getOnboardingUserData();

    @POST("/v2/sign-up/confirm-data/")
    LiveData<ApiResponse<ConfirmDataResponse>> confirmData();

    @GET("/v2/sign-up/scans/face")
    @Streaming
    LiveData<ApiResponse<ResponseBody>> getUserScanImage();

    @GET("/v2/sign-up/scans/front")
    @Streaming
    LiveData<ApiResponse<ResponseBody>> getUserIDImage();

    @GET("/v2/sign-up/scans/back")
    @Streaming
    LiveData<ApiResponse<ResponseBody>> getBackScan();

    @POST("/v2/account-status")
    LiveData<ApiResponse<AccountStatusResponse>> getAccountStatus(@Body AccountStatusRequest accountStatusRequest);

    @POST("/v2/sign-up/resume")
    LiveData<ApiResponse<OnBoardingUserDataResponse>> getInterruptedOnBoardingData(@Body AccountStatusRequest accountStatusRequest);

    @POST("/v2/sign-up/add-notification-token")
    LiveData<ApiResponse<OnBoardingUserDataResponse>> uploadNotificationsToken(@Body SignupPushNotificationRequest signupPushRequest);

    @GET("/v2/users/accounts/{accountId}/cards")
    LiveData<ApiResponse<CardsListResponse>> getCards(@Path("accountId") String accountId);

    @GET("/v2/users/accounts/{accountId}/cards")
    LiveData<ApiResponse<CardsListResponse>> getCurrentCard(@Path("accountId") String accountId);

    @POST("v2/users/accounts/{accountId}/cards/{cardId}/change-status")
    @FormUrlEncoded
    LiveData<ApiResponse<ResponseBody>> changeCardStatus(@Path("accountId") String accountId, @Path("cardId") String cardId, @Field("card_status") ArrayList<String> status);

    @POST("/v2/users/accounts/{accountId}/cards/{cardId}/start-pin-retrieval")
    LiveData<ApiResponse<ResponseBody>> startPinRetrieval(@Path("accountId") String accountId, @Path("cardId") String cardId);

    @POST("/v2/users/accounts/{accountId}/cards/{cardId}/verify-activation")
    LiveData<ApiResponse<CardActivationResponse>> verifyPinCode(@Path("accountId") String accountId, @Path("cardId") String cardId, @Body PinRequest pinRequest);

    @FormUrlEncoded
    @POST("v2/users/accounts/{accountId}/cards/{cardId}/verify-pin-retrieval")
    LiveData<ApiResponse<CardActivationResponse>> verifyPinCodeRetrieval(@Path("accountId") String accountId, @Path("cardId") String cardId, @Field("code") String pinCode);

    @POST("v2/users/accounts/{accountId}/transfers/validate")
    LiveData<ApiResponse<ResponseBody>> validateTransfer(@Path("accountId") String accountId, @Body MakeTransferRequest makeTransferRequest);

    @POST("v2/users/accounts/{accountId}/transfers/make")
    LiveData<ApiResponse<ResponseBody>> makeTransfer(@Path("accountId") String accountId, @Body MakeTransferRequest makeTransferRequest);

    @GET("/v2/users/accounts/{userId}/goals")
    LiveData<ApiResponse<SavingsGoalsListResponse>> getAllGoals(@Path("userId") String userId);

    @GET("/v2/users/accounts/{userId}/goals")
    Call<SavingsGoalsListResponse> fetchGoals(@Path("userId") String userId);

    @POST("/v2/users/accounts/{userId}/goals")
    LiveData<ApiResponse<SavingsGoalResponse>> createSavingsGoal(@Path("userId") String userId, @Body CreateSavingsGoalRequest createSavingsGoalRequest);

    @PUT("/v2/users/accounts/{userId}/goals/{goalId}")
    LiveData<ApiResponse<SavingsGoalResponse>> updateSavingsGoal(@Path("userId") String userId, @Path("goalId") String goalId, @Body UpdateSavingsGoalRequest updateSavingsGoalRequest);

    @PUT("/v2/users/accounts/{userId}/goals/{goalId}/delete")
    LiveData<ApiResponse<SavingsGoalResponse>> deleteSavingsGoal(@Path("userId") String userId, @Path("goalId") String goalId);

    @POST("/v2/users/accounts/{userId}/goals/{goalId}/load-money")
    LiveData<ApiResponse<SavingsGoalResponse>> goalLoadMoney(@Path("userId") String userId, @Path("goalId") String goalId, @Body GoalTransferMoneyRequest amount);

    @POST("/v2/users/accounts/{userId}/goals/{goalId}/unload-money")
    LiveData<ApiResponse<SavingsGoalResponse>> goalUnloadMoney(@Path("userId") String userId, @Path("goalId") String goalId, @Body GoalTransferMoneyRequest amount);

    @POST("/v2/users/accounts/{accountId}/cards/{cardId}/start-activation")
    LiveData<ApiResponse<ResponseBody>> startCardActiviation(@Path("accountId") String accountId, @Path("cardId") String cardId);

    @POST("/v2/users/accounts/{userId}/top-up")
    LiveData<ApiResponse<TopUpResponse>> topUp(@Path("userId") String userId, @Body TopUpRequest topUpRequest);

    @GET("/v2/users/accounts/{accountId}/3ds-result")
    LiveData<ApiResponse<TopUp3DSStatusResponse>> get3DSResult(@Path("accountId") String accountId, @Query("client_secret") String clientSecret, @Query("source") String source);

    @GET("/v2/users/accounts/{accountId}/top-up/limits")
    LiveData<ApiResponse<TopUpLimitsResponse>> getTopUpLimits(@Path("accountId") String accountId);

    @GET("/v2/terms-and-conditions")
    LiveData<ApiResponse<TermsAndConditions>> getTermsAndConditions();

    @GET("/v2/fees")
    LiveData<ApiResponse<List<FeeOrLimitResponse>>> getFeesAndLimits();

    @POST("/v2/users/auth-token")
    LiveData<ApiResponse<AuthTokenResponse>> createAuthToken(@Body AuthDeviceResponse authDevice);

    @HTTP(method = "DELETE", path = "/v2/users/auth-token", hasBody = true)
    LiveData<ApiResponse<ResponseBody>> deleteAuthToken(@Body DeleteAuthTokenRequest deleteAuthTokenRequest);

    @GET("/v2/users/auth-token")
    LiveData<ApiResponse<AuthDevicesResponse>> getAuthDevices();

    @GET("/v2/users/contact-preferences")
    LiveData<ApiResponse<ContactPreferencesResponse>> getContactPreferences();

    @PUT("/v2/users/contact-preferences")
    LiveData<ApiResponse<ContactPreferencesResponse>> setContactPreferences(@Body ContactPreferencesRequest contactPreferencesRequest);

    @POST("/v2/sessions/pin")
    @FormUrlEncoded
    LiveData<ApiResponse<LoginResponse>> loginViaPin(@Field("auth_token") String authToken);

    @POST("/v2/sessions/touch-id")
    @FormUrlEncoded
    LiveData<ApiResponse<LoginResponse>> loginViaTouchId(@Field("touch_id_token") String authToken);

    @GET("/v2/users/contacts")
    LiveData<ApiResponse<ContactListResponse>> getContacts();

    @POST("/v2/users/contacts")
    LiveData<ApiResponse<CreateUpdateContactResponse>> createContact(@Body CreateUpdateContactRequest createContactRequest);

    @PUT("/v2/users/contacts/{contactId}")
    LiveData<ApiResponse<CreateUpdateContactResponse>> updateContact(@Path("contactId") String contactId, @Body CreateUpdateContactRequest updateContactRequest);

    @DELETE("/v2/users/contacts/{contactId}")
    LiveData<ApiResponse<ResponseBody>> deleteContact(@Path("contactId") String contactId);

    @POST("/v2/users/contacts/{contactId}/verify")
    LiveData<ApiResponse<ResponseBody>> verifyContact(@Path("contactId") String contactId, @Body ContactVerifyRequest contactVerifyRequest);

    @POST("/v2/users/contacts/{contactId}/resend_sms")
    LiveData<ApiResponse<ResponseBody>> resendContactVerifySms(@Path("contactId") String contactId);

    @POST("/v2/users/accounts/{accountId}/payments")
    LiveData<ApiResponse<CreatePaymentResponse>> createManualDetailsPayment(@Path("accountId") String accountId, @Body CreateManualDetailsPaymentRequest createManualDetailsPaymentRequest);

    @POST("/v2/users/accounts/{accountId}/payments/{paymentId}/make")
    LiveData<ApiResponse<ResponseBody>> makePaymentSMSConfirmation(@Path("accountId") String accountId, @Path("paymentId") String paymentId, @Body MakePaymentSMSConfirmationRequest makePaymentSMSConfirmationRequest);

    @POST("/v2/users/accounts/{accountId}/payments/{paymentId}/resend-sms")
    LiveData<ApiResponse<ResponseBody>> resendPaymentSMSConfirmation(@Path("accountId") String accountId, @Path("paymentId") String paymentId);

    @POST("/v2/users/accounts/{accountId}/payments/{lootPublicId}/make-to-public-id")
    LiveData<ApiResponse<CreatePaymentResponse>> makePaymentToPublicId(@Path("accountId") String accountId, @Path("lootPublicId") String lootPublicId, @Body MakePaymentRequest makePaymentRequest);

    @POST("/v2/users/contacts/{contactId}/make-payment")
    LiveData<ApiResponse<CreatePaymentResponse>> makeContactPayment(@Path("contactId") String contactId, @Body MakePaymentRequest makePaymentRequest);

    @POST("/v2/users/contacts/phonebook-sync")
    LiveData<ApiResponse<ArrayList<PhonebookSyncResponse>>> syncPhonebook(@Body PhonebookSyncRequest phonebookSyncRequest);

    @POST("/v2/users/contacts/create-from-payment/{paymentId}")
    LiveData<ApiResponse<CreateUpdateContactResponse>> createContactFromPayment(@Path("paymentId") String paymentId);

    @GET("/v2/customers/{contactId}/payments/history")
    LiveData<ApiResponse<ContactTransactionHistoryResponse>> getLootContactTransactionHistory(@Path("contactId") String contactId);

    @GET("/v2/users/contacts/{contactId}/payments/history")
    LiveData<ApiResponse<ContactTransactionHistoryResponse>> getSDContactTransactionHistory(@Path("contactId") String contactId);

    @GET("/v2/customers/{accountNumber}/{sortCode}/payments/history")
    LiveData<ApiResponse<ContactTransactionHistoryResponse>> getAccountDetailsTransactionHistory(@Path("accountNumber") String accountNumber, @Path("sortCode") String sortCode);

}
