package io.loot.lootsdk.listeners.user;

import io.loot.lootsdk.listeners.GenericFailListener;
import io.loot.lootsdk.models.data.userinfo.UserInfo;

public interface UpdateUserDetailsListener extends GenericFailListener{
    void onUpdateUserDetailsSuccess(UserInfo userInfo);
    void onUpdateUserDetailsError(String error);
}
