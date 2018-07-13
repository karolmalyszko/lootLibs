package io.loot.lootsdk.models.data.waitingList;

public class PositionDetails {

    private String email;
    private String createdAt;
    private int position;
    private int initialPosition;
    private String referralCode;
    private int positionChange;
    private String expectedStartDate;
    private String expectedEndDate;
    private boolean isReadyToOnboarding;
    private int invitedUsers;
    private int usersBehindCount;

    public PositionDetails() {
        email = "";
        createdAt = "";
        referralCode = "";
        expectedStartDate = "";
        expectedEndDate = "";
    }

    public PositionDetails(PositionDetails positionDetails) {
        email = positionDetails.getEmail();
        createdAt = positionDetails.getCreatedAt();
        referralCode = positionDetails.getReferralCode();
        expectedStartDate = positionDetails.getExpectedStartDate();
        expectedEndDate = positionDetails.getExpectedEndDate();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null) {
            email = "";
        }

        this.email = email;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        if (createdAt == null) {
            createdAt = "";
        }

        this.createdAt = createdAt;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getInitialPosition() {
        return initialPosition;
    }

    public void setInitialPosition(int initialPosition) {
        this.initialPosition = initialPosition;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        if (referralCode == null) {
            referralCode = "";
        }

        this.referralCode = referralCode;
    }

    public int getPositionChange() {
        return positionChange;
    }

    public void setPositionChange(int positionChange) {
        this.positionChange = positionChange;
    }

    public String getExpectedStartDate() {
        return expectedStartDate;
    }

    public void setExpectedStartDate(String expectedStartDate) {
        if (expectedStartDate == null) {
            expectedStartDate = "";
        }

        this.expectedStartDate = expectedStartDate;
    }

    public String getExpectedEndDate() {
        return expectedEndDate;
    }

    public void setExpectedEndDate(String expectedEndDate) {
        if (expectedEndDate == null) {
            expectedEndDate = "";
        }

        this.expectedEndDate = expectedEndDate;
    }

    public boolean isReadyToOnboarding() {
        return isReadyToOnboarding;
    }

    public void setReadyToOnboarding(boolean readyToOnboarding) {
        isReadyToOnboarding = readyToOnboarding;
    }

    public int getInvitedUsers() {
        return invitedUsers;
    }

    public void setInvitedUsers(int invitedUsers) {
        this.invitedUsers = invitedUsers;
    }

    public int getUsersBehindCount() {
        return usersBehindCount;
    }

    public void setUsersBehindCount(int usersBehindCount) {
        this.usersBehindCount = usersBehindCount;
    }
}
