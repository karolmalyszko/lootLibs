package io.loot.lootsdk.models.data.contacts;

import java.io.Serializable;
import java.util.ArrayList;

public class AllContacts implements Serializable {
    private ArrayList<Contact> friendsOnLoot;
    private ArrayList<Contact> savedDetails;

    public ArrayList<Contact> getFriendsOnLoot() {
        return friendsOnLoot;
    }

    public AllContacts() {
        friendsOnLoot = new ArrayList<Contact>();
        savedDetails = new ArrayList<Contact>();
    }

    public AllContacts(AllContacts allContacts) {
        if (allContacts == null) {
            allContacts = new AllContacts();
        }
        setFriendsOnLoot(new ArrayList<Contact>(allContacts.getFriendsOnLoot()));
        setSavedDetails(new ArrayList<Contact>(allContacts.getSavedDetails()));
    }

    public void setFriendsOnLoot(ArrayList<Contact> friendsOnLoot) {
        if(friendsOnLoot == null) {
            friendsOnLoot = new ArrayList<Contact>();
        }
        this.friendsOnLoot = friendsOnLoot;
    }

    public ArrayList<Contact> getSavedDetails() {
        return savedDetails;
    }

    public void setSavedDetails(ArrayList<Contact> savedDetails) {
        if(savedDetails == null) {
            savedDetails = new ArrayList<Contact>();
        }
        this.savedDetails = savedDetails;
    }
}
