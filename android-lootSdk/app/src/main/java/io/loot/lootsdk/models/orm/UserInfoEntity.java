package io.loot.lootsdk.models.orm;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToOne;

import io.loot.lootsdk.models.data.userinfo.UserInfo;

@android.arch.persistence.room.Entity(tableName = "UserInfo")
public class UserInfoEntity {

    @PrimaryKey
    private long entityId = 0;
    private String email;
    private String countryCode;
    private boolean legend;

    @Embedded(prefix = "personal_details_")
    private PersonalDetailsEntity personalDetails;

    public UserInfo parseToDataObject() {
        UserInfo userInfo = new UserInfo();
        userInfo.setEmail(email);
        userInfo.setCountryCode(countryCode);

        if (personalDetails != null) {
            userInfo.setPersonalDetails(personalDetails.parseToDataObject());
        }

        userInfo.setLegend(legend);
        return userInfo;
    }

    public long getEntityId() {
        return this.entityId;
    }

    public void setEntityId(long entityId) {
        this.entityId = entityId;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountryCode() {
        return this.countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public void setLegend(boolean legend) {
        this.legend = legend;
    }

    public boolean isLegend() {
        return legend;
    }

    public PersonalDetailsEntity getPersonalDetails() {
        return personalDetails;
    }

    public void setPersonalDetails(PersonalDetailsEntity personalDetails) {
        this.personalDetails = personalDetails;
    }
}
