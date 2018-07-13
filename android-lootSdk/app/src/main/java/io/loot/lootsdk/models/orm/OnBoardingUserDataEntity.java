package io.loot.lootsdk.models.orm;


import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToOne;

import io.loot.lootsdk.models.data.userinfo.OnBoardingUserData;
import io.loot.lootsdk.models.entities.OnBoardingUserDataRoomEntity;

@Entity
public class OnBoardingUserDataEntity {
    @Id
    private long entityId;
    private String email;
    private String phoneNumber;
    private String lastFinishedStep;
    private String token;
    private String intercomHash;
    private String publicId;

    @Generated(hash = 1933011100)
    public OnBoardingUserDataEntity(long entityId, String email, String phoneNumber,
            String lastFinishedStep, String token, String intercomHash, String publicId) {
        this.entityId = entityId;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.lastFinishedStep = lastFinishedStep;
        this.token = token;
        this.intercomHash = intercomHash;
        this.publicId = publicId;
    }

    @Generated(hash = 832419462)
    public OnBoardingUserDataEntity() {
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

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLastFinishedStep() {
        return this.lastFinishedStep;
    }

    public void setLastFinishedStep(String lastFinishedStep) {
        this.lastFinishedStep = lastFinishedStep;
    }

    public static OnBoardingUserDataRoomEntity parseToEntity(OnBoardingUserData userData) {
        OnBoardingUserDataRoomEntity entity = new OnBoardingUserDataRoomEntity();

        entity.setEmail(userData.getEmail());
        entity.setLastFinishedStep(userData.getLastFinishedStep());
        entity.setPhoneNumber(userData.getPhoneNumber());
        entity.setToken(userData.getToken());
        entity.setIntercomHash(userData.getIntercomHash());
        entity.setPublicId(userData.getPublicId());

        return entity;
    }


    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getIntercomHash() {
        return this.intercomHash;
    }

    public void setIntercomHash(String intercomHash) {
        this.intercomHash = intercomHash;
    }

    public String getPublicId() {
        return this.publicId;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

}
