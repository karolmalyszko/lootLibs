package io.loot.lootsdk.listeners.user;

import java.util.ArrayList;

import io.loot.lootsdk.listeners.GenericFailListener;
import io.loot.lootsdk.models.data.userinfo.FeeOrLimit;
import io.loot.lootsdk.models.networking.policyAndTerms.TermsAndConditions;

public interface GetTermsAndCoditionsListener extends GenericFailListener {

    void onGetTermsAndCoditionsSuccess(TermsAndConditions termsAndConditions);
    void onGetTermsAndCoditionsError(String error);
}
