package io.loot.lootsdk.utils;

import org.greenrobot.greendao.DaoException;

import java.util.ArrayList;
import java.util.List;

import io.loot.lootsdk.database.LootDatabase;
import io.loot.lootsdk.models.entities.OnBoardingUserDataRoomEntity;
import io.loot.lootsdk.models.entities.SessionRoomEntity;
import io.loot.lootsdk.models.orm.KycEntity;
import io.loot.lootsdk.models.orm.OnBoardingUserDataEntity;
import io.loot.lootsdk.models.orm.OnBoardingUserDataEntityDao;
import io.loot.lootsdk.models.orm.PersonalDataEntity;
import io.loot.lootsdk.models.orm.SessionEntity;
import io.loot.lootsdk.models.orm.SessionEntityDao;

public class GreenDaoToRoomMigrationUtil {


    public static void migrateIfNeeded(DBHelper dbHelper, LootDatabase roomDatabase, KeyStoreUtil keyStoreUtil) {
        if (dbHelper.getContext().getDatabasePath(DBHelper.DB_NAME) == null || !dbHelper.getContext().getDatabasePath(DBHelper.DB_NAME).exists()) {
            return;
        }

        try {
            migrateSessions(dbHelper, roomDatabase, keyStoreUtil);
            migrateOnboarding(dbHelper, roomDatabase, keyStoreUtil);
            dbHelper.deleteAllDBObjects();
            dbHelper.clearDBandPass(DBHelper.DB_NAME);
        } catch (DaoException ex) {
            // ignored, that means db is corrupted so we can't migrate
        } finally {
            if (dbHelper.getContext().getDatabasePath(DBHelper.DB_NAME) != null && dbHelper.getContext().getDatabasePath(DBHelper.DB_NAME).exists()) {
                dbHelper.getContext().getDatabasePath(DBHelper.DB_NAME).delete();
            }
        }
    }

    private static String getTokenIfCurrentAbsent(OnBoardingUserDataEntity onboardingEntity, String tokenToCheck, KeyStoreUtil keyStoreUtil) {
        String token = "";
        if (tokenToCheck == null || token.isEmpty()) {
            token = (onboardingEntity != null && onboardingEntity.getToken() != null) ? onboardingEntity.getToken() : "";
        } else {
            token = tokenToCheck;
        }

        keyStoreUtil.saveOnBoardingToken(token);
        return token;
    }

    private static String getEmailIfCurrentIsAbsent(OnBoardingUserDataEntity onboardingEntity, String emailToCheck) {
        String email = "";
        if (emailToCheck == null || emailToCheck.isEmpty()) {
            email = (onboardingEntity != null && onboardingEntity.getToken() != null) ? onboardingEntity.getEmail() : "";
        } else {
            email = emailToCheck;
        }

        return email;
    }

    private static void migrateOnboarding(DBHelper dbHelper, LootDatabase roomDatabase, KeyStoreUtil keyStoreUtil) {
        if (roomDatabase == null || dbHelper == null || !dbHelper.isTableExists(OnBoardingUserDataEntityDao.TABLENAME)) {
            return;
        }
        OnBoardingUserDataEntity onboardingEntity = dbHelper.getSession(false).getOnBoardingUserDataEntityDao().loadByRowId(0L);
        OnBoardingUserDataRoomEntity newEntity = new OnBoardingUserDataRoomEntity();

        if (onboardingEntity == null) {
            return;
        }

        newEntity.setPersonalData(new PersonalDataEntity());
        newEntity.setKyc(new KycEntity());
        newEntity.setEmail(onboardingEntity.getEmail());
        newEntity.setIntercomHash(onboardingEntity.getIntercomHash());
        newEntity.setLastFinishedStep(onboardingEntity.getLastFinishedStep());

        if (onboardingEntity.getPublicId() == null) {
            newEntity.setPublicId("");
        } else {
            newEntity.setPublicId(onboardingEntity.getPublicId());
        }

        newEntity.setPhoneNumber(onboardingEntity.getPhoneNumber());
        newEntity.setToken(onboardingEntity.getToken());

        roomDatabase.onboardingUserDataDao().insert(newEntity);
    }

    private static void migrateSessions(DBHelper dbHelper, LootDatabase roomDatabase, KeyStoreUtil keyStoreUtil) {
        if (roomDatabase == null || dbHelper == null || !dbHelper.isTableExists(SessionEntityDao.TABLENAME)) {
            return;
        }
        List<SessionEntity> sessions = dbHelper.getSession(false).getSessionEntityDao().loadAll();

        OnBoardingUserDataEntity onboardingEntity = dbHelper.getSession(false).getOnBoardingUserDataEntityDao().loadByRowId(0L);

        List<SessionRoomEntity> roomEntities = new ArrayList<>();

        for (SessionEntity sessionEntity : sessions) {
            SessionRoomEntity sessionRoomEntity = new SessionRoomEntity();

            sessionRoomEntity.setAuthorizationToken(sessionEntity.getAuthorizationToken());
            sessionRoomEntity.setEmail(getEmailIfCurrentIsAbsent(onboardingEntity, sessionEntity.getEmail()));
            sessionRoomEntity.setWaitingListToken(sessionEntity.getWaitingListToken());
            sessionRoomEntity.setEmailVerified(sessionEntity.getEmailVerified());
            sessionRoomEntity.setIntercomHash(sessionEntity.getIntercomHash());

            String userId = sessionEntity.getUserId() != null ? sessionEntity.getUserId() : "";
            sessionRoomEntity.setUserId(userId);

            sessionRoomEntity.setOnBoardingToken(getTokenIfCurrentAbsent(onboardingEntity, sessionEntity.getOnBoardingToken(), keyStoreUtil));

            roomEntities.add(sessionRoomEntity);
        }

        SessionRoomEntity[] roomEntitiesArray = new SessionRoomEntity[roomEntities.size()];
        roomDatabase.sessionDao().insert(roomEntities.toArray(roomEntitiesArray));
    }

}
