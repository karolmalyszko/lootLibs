package io.loot.lootsdk.listeners.user;

import io.loot.lootsdk.listeners.GenericFailListener;
import io.loot.lootsdk.models.data.userinfo.UserInfo;

public interface UserDetailsListener extends GenericFailListener {

    void onGetCachedUserDetails(UserInfo userInfo);
    void onGetUserDetailsSuccess(UserInfo userInfo);
    void onGetUserDetailsError(String error);

}
