package io.loot.lootsdk;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.Transformations;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Token;

import java.util.ArrayList;
import java.util.List;

import io.loot.lootsdk.database.daos.CardDao;
import io.loot.lootsdk.database.daos.TopUpLimitsDao;
import io.loot.lootsdk.exceptions.NullPointerListenerException;
import io.loot.lootsdk.listeners.cards.ActivationVerificationListener;
import io.loot.lootsdk.listeners.cards.CardActivationStartListener;
import io.loot.lootsdk.listeners.cards.CardStatusChangeListener;
import io.loot.lootsdk.listeners.cards.Get3DSResultListener;
import io.loot.lootsdk.listeners.cards.PinCodeVerificationListener;
import io.loot.lootsdk.listeners.cards.PinRetrievalListener;
import io.loot.lootsdk.listeners.cards.TopUpListener;
import io.loot.lootsdk.listeners.user.GetCardsListener;
import io.loot.lootsdk.listeners.user.GetCurrentCardListener;
import io.loot.lootsdk.listeners.user.TopUpLimitsListener;
import io.loot.lootsdk.models.ActionResult;
import io.loot.lootsdk.models.Resource;
import io.loot.lootsdk.models.data.cards.Card;
import io.loot.lootsdk.models.data.cards.CardStatus;
import io.loot.lootsdk.models.data.cards.CurrentCardHolder;
import io.loot.lootsdk.models.data.topUp.CardDetails;
import io.loot.lootsdk.models.data.topUp.TopUpLimits;
import io.loot.lootsdk.models.data.topUp.TopUpResult;
import io.loot.lootsdk.models.data.topUp.TopUp3DSStatus;
import io.loot.lootsdk.models.networking.ErrorExtractor;
import io.loot.lootsdk.models.networking.cards.CardActivationResponse;
import io.loot.lootsdk.models.networking.cards.CardResponse;
import io.loot.lootsdk.models.networking.cards.CardsListResponse;
import io.loot.lootsdk.models.networking.cards.PinRequest;
import io.loot.lootsdk.models.networking.topUp.TopUp3DSStatusResponse;
import io.loot.lootsdk.models.networking.topUp.TopUpLimitsResponse;
import io.loot.lootsdk.models.networking.topUp.TopUpParsedErrorResponse;
import io.loot.lootsdk.models.networking.topUp.TopUpRequest;
import io.loot.lootsdk.models.networking.topUp.TopUpResponse;
import io.loot.lootsdk.models.orm.CardEntity;
import io.loot.lootsdk.models.orm.TopUpLimitsEntity;
import io.loot.lootsdk.networking.ApiResponse;
import io.loot.lootsdk.networking.LootApiInterface;
import io.loot.lootsdk.networking.interceptors.InterceptorType;
import io.loot.lootsdk.networking.interceptors.models.LootHeader;
import io.loot.lootsdk.utils.AppExecutors;
import okhttp3.ResponseBody;

public class Cards {

    private LootSDK mLootSDK;
    private Context mContext;

    private CardDao mCardDao;
    private TopUpLimitsDao mTopUpLimitsDao;

    Cards(LootSDK lootSDK) {
        mLootSDK = lootSDK;
        mContext = mLootSDK.getContext();

        mCardDao = mLootSDK.getDatabase().cardDao();
        mTopUpLimitsDao = mLootSDK.getDatabase().topUpLimitsDao();
    }

    private List<Card> parseCardEntitiesToDataObjects(List<CardEntity> cardEntities) {
        List<Card> cards = new ArrayList<>();

        if (cardEntities == null) {
            return cards;
        }

        for (CardEntity entity : cardEntities) {
            if (entity != null) {
                cards.add(entity.parseToDataObject());
            }
        }

        return cards;
    }

    private List<Card> parseCardResponsesToDataObjects(List<CardResponse> cardResponses) {
        List<Card> cards = new ArrayList<>();

        if (cardResponses == null) {
            return cards;
        }

        for (CardResponse cardResponse : cardResponses) {
            if (cardResponse != null) {
                cards.add(CardResponse.parseToDataObject(cardResponse));
            }
        }

        return cards;
    }

    private List<CardEntity> parseCardResponsesToEntities(List<CardResponse> cardResponses) {
        List<CardEntity> entities = new ArrayList<>();

        if (cardResponses == null) {
            return entities;
        }

        for (CardResponse response : cardResponses) {
            if (response == null) {
                continue;
            }

            entities.add(CardResponse.parseToEntityObject(response));
        }

        return entities;
    }

    private ArrayList<String> getCardStatus(CardStatus cardStatus) {
        ArrayList<String> statuses = new ArrayList<>();

        if (cardStatus == null) {
            return statuses;
        }

        statuses.add(cardStatus.toString().toLowerCase());

        return statuses;
    }

    public LiveData<Resource<List<Card>>> getCards() {
        LootHeader header = mLootSDK.createLootHeader();
        final LootApiInterface apiInterface = mLootSDK.createRestClient(InterceptorType.SESSION_TOKEN, header).getApiService();

        return new NetworkBoundResource<List<Card>, CardsListResponse>(AppExecutors.get()) {
            @Override
            protected void saveCallResult(@NonNull CardsListResponse item) {
                for (CardEntity entity : parseCardResponsesToEntities(item.getCards())) {
                    mCardDao.insert(entity);
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Card> data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<Card>> loadFromDb() {
                return Transformations.map(mCardDao.loadAll(), new Function<List<CardEntity>, List<Card>>() {
                    @Override
                    public List<Card> apply(List<CardEntity> input) {
                        if (input == null) {
                            return null;
                        }

                        return parseCardEntitiesToDataObjects(input);
                    }
                });
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<CardsListResponse>> createCall() {
                return apiInterface.getCards(mLootSDK.user().getGPSAccount().getId());
            }
        }.asLiveData();
    }

    public LiveData<Resource<CurrentCardHolder>> getCurrentCard() {
        LootHeader header = mLootSDK.createLootHeader();
        final LootApiInterface apiInterface = mLootSDK.createRestClient(InterceptorType.SESSION_TOKEN, header).getApiService();

        return new NetworkBoundResource<CurrentCardHolder, CardsListResponse>(AppExecutors.get()) {
            @Override
            protected void saveCallResult(@NonNull CardsListResponse item) {
                for (CardResponse response : item.getCards()) {
                    mCardDao.insert(CardResponse.parseToEntityObject(response));
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable CurrentCardHolder data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<CurrentCardHolder> loadFromDb() {
                return Transformations.map(mCardDao.loadAll(), new Function<List<CardEntity>, CurrentCardHolder>() {
                    @Override
                    public CurrentCardHolder apply(List<CardEntity> input) {
                        List<Card> cardList = parseCardEntitiesToDataObjects(input);

                        Card card = filterCardsForCurrent(cardList);
                        Card orderedCard = filterCardsForOrdered(cardList);

                        return new CurrentCardHolder(orderedCard, card);
                    }
                });
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<CardsListResponse>> createCall() {
                return apiInterface.getCurrentCard(mLootSDK.user().getGPSAccount().getId());
            }
        }.asLiveData();
    }

    private Card filterCardsForCurrent(List<Card> cards) {
        Card cardTemp = new Card();
        boolean foundLostOrStolen = false;
        for (Card c : cards) {
            if (!foundLostOrStolen || !c.getStatus().toUpperCase().equals(CardStatus.ORDERED.toString())) {
                cardTemp = c;
                if (c.getStatus().toUpperCase().equals(CardStatus.ACTIVE.toString())||c.getStatus().toUpperCase().equals(CardStatus.PAUSED.toString())) {
                    break;
                }
            }
            if (c.getStatus().toUpperCase().equals(CardStatus.LOST.toString()) || c.getStatus().toUpperCase().equals(CardStatus.STOLEN.toString())) {
                foundLostOrStolen = true;
            }

        }
        return cardTemp;
    }

    private Card filterCardsForOrdered(List<Card> cards) {
        for (Card c : cards) {
            if (c.getStatus().toUpperCase().equals(CardStatus.ORDERED.toString())) {
                return c;
            }
        }
        return new Card();
    }


    public LiveData<ActionResult> changeCardStatus(final String cardId, final CardStatus cardStatus) {
        LootHeader header = mLootSDK.createLootHeader();
        final LootApiInterface apiInterface = mLootSDK.createRestClient(InterceptorType.SESSION_TOKEN, header).getApiService();

        return new NetworkBoundAction<ResponseBody>(AppExecutors.get()) {
            @Override
            protected void saveCallResult(@NonNull ResponseBody item) {
                // nothing to do with that
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<ResponseBody>> createCall() {
                String accountId = mLootSDK.user().getGPSAccount().getId();
                ArrayList<String> statusRequest = getCardStatus(cardStatus);

                return apiInterface.changeCardStatus(accountId, cardId, statusRequest);
            }
        }.asLiveData();
    }

    public LiveData<ActionResult> startPinRetrieval(final String cardId) {
        LootHeader header = mLootSDK.createLootHeader();
        final LootApiInterface apiInterface = mLootSDK.createRestClient(InterceptorType.SESSION_TOKEN, header).getApiService();

        return new NetworkBoundAction<ResponseBody>(AppExecutors.get()) {
            @Override
            protected void saveCallResult(@NonNull ResponseBody item) {
                // nothing to do with that
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<ResponseBody>> createCall() {
                return apiInterface.startPinRetrieval(mLootSDK.user().getGPSAccount().getId(), cardId);
            }
        }.asLiveData();
    }

    public LiveData<ActionResult> startCardActivation(final String cardId) {
        LootHeader header = mLootSDK.createLootHeader();
        final LootApiInterface apiInterface = mLootSDK.createRestClient(InterceptorType.SESSION_TOKEN, header).getApiService();

        return new NetworkBoundAction<ResponseBody>(AppExecutors.get()) {
            @Override
            protected void saveCallResult(@NonNull ResponseBody item) {
                // nothing to do with this
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<ResponseBody>> createCall() {
                return apiInterface.startCardActiviation(mLootSDK.user().getGPSAccount().getId(), cardId);
            }
        }.asLiveData();
    }

    /**
     * @return Resource with String which represents PIN
     */
    public LiveData<Resource<String>> verifyPinCode(final String cardId, final PinRequest pinRequest) {
        LootHeader header = mLootSDK.createLootHeader();
        final LootApiInterface apiInterface = mLootSDK.createRestClient(InterceptorType.SESSION_TOKEN, header).getApiService();
        final String accountId = mLootSDK.user().getGPSAccount().getId();

        return new NetworkResource<String, CardActivationResponse>(AppExecutors.get()) {
            @Override
            protected String proceedData(@NonNull CardActivationResponse item) {
                return item.getPinCode();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<CardActivationResponse>> createCall() {
                return apiInterface.verifyPinCode(accountId, cardId, pinRequest);
            }
        }.asLiveData();
    }

    public LiveData<Resource<CardActivationResponse>> verifyPinCodeRetrieval(final String cardId, final String pinCode) {
        LootHeader header = mLootSDK.createLootHeader();
        final LootApiInterface apiInterface = mLootSDK.createRestClient(InterceptorType.SESSION_TOKEN, header).getApiService();

        final String accountId = mLootSDK.user().getGPSAccount().getId();

        return new NetworkResource<CardActivationResponse, CardActivationResponse>(AppExecutors.get()) {
            @Override
            protected CardActivationResponse proceedData(@NonNull CardActivationResponse item) {
                return item;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<CardActivationResponse>> createCall() {
                return apiInterface.verifyPinCodeRetrieval(accountId, cardId, pinCode);
            }
        }.asLiveData();
    }

    public LiveData<Resource<TopUpResult>> topUpByCardDetails(CardDetails cardDetails, final int amount) {
        com.stripe.android.model.Card cardToSave = new com.stripe.android.model.Card(cardDetails.getCardNumber(), cardDetails.getExpiryMonth(), cardDetails.getExpiryYear(), cardDetails.getCvv());
        String stripeToken = (mLootSDK.getEnvironment() == LootSDK.API_TYPE_PRODUCTION) ?
                mContext.getString(R.string.stripe_live_token) : mContext.getString(R.string.stripe_test_token);

        Resource<TopUpResult> loadingResult = Resource.loading(null);
        final MediatorLiveData<Resource<TopUpResult>> result = new MediatorLiveData<>();
        result.setValue(loadingResult);

        new Stripe().createToken(
                cardToSave,
                stripeToken,
                new TokenCallback() {
                    public void onSuccess(Token token) {
                        result.addSource(topUpByToken(token.getId(), amount), new Observer<Resource<TopUpResult>>() {
                            @Override
                            public void onChanged(@Nullable Resource<TopUpResult> topUpResultResource) {
                                result.setValue(topUpResultResource);
                            }
                        });
                    }

                    public void onError(Exception error) {
                        Resource<TopUpResult> errorResource = Resource.error(error.getLocalizedMessage(), null);
                        result.setValue(errorResource);
                    }
                });

        return result;
    }

    public LiveData<Resource<TopUpResult>> topUpByToken(String token, int amount) {
        LootHeader header = mLootSDK.createLootHeader();
        final LootApiInterface apiInterface = mLootSDK.createRestClient(InterceptorType.SESSION_TOKEN, header).getApiService();

        final String accountId = mLootSDK.user().getGPSAccount().getId();
        final TopUpRequest request = new TopUpRequest(token, amount);

        return new NetworkResource<TopUpResult, TopUpResponse>(AppExecutors.get()) {
            @Override
            protected TopUpResult proceedData(@NonNull TopUpResponse item) {
                return TopUpResponse.parseToDataObject(item);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<TopUpResponse>> createCall() {
                return apiInterface.topUp(accountId, request);
            }
        }.asLiveData();
    }

    public LiveData<Resource<TopUp3DSStatus>> get3DSResult(final String accountId, final String clientSecret, final String source) {
        LootHeader header = mLootSDK.createLootHeader();
        final LootApiInterface apiInterface = mLootSDK.createRestClient(InterceptorType.SESSION_TOKEN, header).getApiService();

        return new NetworkResource<TopUp3DSStatus, TopUp3DSStatusResponse>(AppExecutors.get()) {
            @Override
            protected TopUp3DSStatus proceedData(@NonNull TopUp3DSStatusResponse item) {
                String status = item.getStatus();

                if (TopUp3DSStatusResponse.STATUS_FAILED.equals(status)) {
                    return TopUp3DSStatus.FAILED;
                } else if (TopUp3DSStatusResponse.STATUS_PENDING.equals(status)) {
                    return TopUp3DSStatus.PENDING;
                } else if (TopUp3DSStatusResponse.STATUS_SUCCEEDED.equals(status)) {
                    return TopUp3DSStatus.SUCCEEDED;
                }

                return TopUp3DSStatus.FAILED;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<TopUp3DSStatusResponse>> createCall() {
                return apiInterface.get3DSResult(accountId, clientSecret, source);
            }
        }.asLiveData();
    }

    public LiveData<Resource<TopUpLimits>> getTopUpLimits() {
        final String accountId = mLootSDK.user().getGPSAccount().getId();
        LootHeader header = mLootSDK.createLootHeader();
        final LootApiInterface apiInterface = mLootSDK.createRestClient(InterceptorType.SESSION_TOKEN, header).getApiService();

        return new NetworkBoundResource<TopUpLimits, TopUpLimitsResponse>(AppExecutors.get()) {
            @Override
            protected void saveCallResult(@NonNull TopUpLimitsResponse item) {
                mTopUpLimitsDao.insert(TopUpLimitsResponse.parseToEntityObject(item));
            }

            @Override
            protected boolean shouldFetch(@Nullable TopUpLimits data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<TopUpLimits> loadFromDb() {
                return Transformations.map(mTopUpLimitsDao.load(), new Function<TopUpLimitsEntity, TopUpLimits>() {
                    @Override
                    public TopUpLimits apply(TopUpLimitsEntity input) {
                        if (input == null) {
                            return null;
                        }

                        return input.parseToDataObject();
                    }
                });
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<TopUpLimitsResponse>> createCall() {
                return apiInterface.getTopUpLimits(accountId);
            }
        }.asLiveData();
    }

    private String extractParsedError(String errorMessageJson) {
        Gson gson = new Gson();
        TopUpParsedErrorResponse errorResponse = gson.fromJson(errorMessageJson, TopUpParsedErrorResponse.class);

        // We should return here some kind of 'human friendly' message because listener method says its already parsed
        return (errorResponse != null && errorResponse.getErrorMessage() != null) ? errorResponse.getErrorMessage() : "Unexpected error!";
    }

    public void clearCached() {

    }
}
