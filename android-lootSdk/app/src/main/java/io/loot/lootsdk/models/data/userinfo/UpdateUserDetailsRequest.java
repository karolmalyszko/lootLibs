package io.loot.lootsdk.models.data.userinfo;

public class UpdateUserDetailsRequest {

    private String preferredName;
    private String title;
    private Address address;

    public UpdateUserDetailsRequest() {
        this.preferredName = "";
        this.title = "";
        this.address = new Address();
    }

    public UpdateUserDetailsRequest(UpdateUserDetailsRequest updateUserDetailsRequest) {
        this.preferredName = updateUserDetailsRequest.getPreferredName();
        this.title = updateUserDetailsRequest.getTitle();
        this.address = new Address(updateUserDetailsRequest.getAddress());
    }

    public UpdateUserDetailsRequest(String preferredName, String title, Address address) { //TODO REMOVE ANY REQUEST MODELS FROM USER SIDE
        this.setPreferredName(preferredName);
        this.setTitle(title);
        this.setAddress(address);
    }

    public String getPreferredName() {
        return preferredName;
    }

    public void setPreferredName(String preferredName) {
        if (preferredName == null) {
            preferredName = "";
        }
        this.preferredName = preferredName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null) {
            title = "";
        }
        this.title = title;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        if (address == null) {
            address = new Address();
        }
        this.address = address;
    }
}
