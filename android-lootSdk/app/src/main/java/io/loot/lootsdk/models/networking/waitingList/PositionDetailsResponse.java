package io.loot.lootsdk.models.networking.waitingList;

import com.google.gson.annotations.SerializedName;

import io.loot.lootsdk.models.data.waitingList.PositionDetails;
import lombok.Data;

public @Data class PositionDetailsResponse {

    @SerializedName("email")
    String email;
    @SerializedName("created_at")
    String createdAt;
    @SerializedName("position")
    int position;
    @SerializedName("initial_position")
    int initialPosition;
    @SerializedName("referral_code")
    String referralCode;
    @SerializedName("position_change")
    int positionChange;
    @SerializedName("expected_onboarding_date_range_start")
    String expectedStartDate;
    @SerializedName("expected_onboarding_date_range_end")
    String expectedEndDate;
    @SerializedName("onboarding_ready")
    boolean isReadyToOnboarding;
    @SerializedName("invited_users")
    int invitedUsers;
    @SerializedName("users_behind")
    int usersBehindCount;

    public static PositionDetails parseToDataObject(PositionDetailsResponse positionDetailsResponse) {
        if (positionDetailsResponse == null) {
            positionDetailsResponse = new PositionDetailsResponse();
        }
        PositionDetails data = new PositionDetails();

        data.setEmail(positionDetailsResponse.getEmail());
        data.setCreatedAt(positionDetailsResponse.getCreatedAt());
        data.setPosition(positionDetailsResponse.getPosition());
        data.setInitialPosition(positionDetailsResponse.getInitialPosition());
        data.setReferralCode(positionDetailsResponse.getReferralCode());
        data.setPositionChange(positionDetailsResponse.getPositionChange());
        data.setExpectedStartDate(positionDetailsResponse.getExpectedStartDate());
        data.setExpectedEndDate(positionDetailsResponse.getExpectedEndDate());
        data.setReadyToOnboarding(positionDetailsResponse.isReadyToOnboarding());
        data.setInvitedUsers(positionDetailsResponse.getInvitedUsers());
        data.setUsersBehindCount(positionDetailsResponse.getUsersBehindCount());

        return data;
    }

}
