package io.loot.lootsdk.database.migrations;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.migration.Migration;
import android.support.annotation.NonNull;

public class DatabaseMigrations {
    public static final Migration MIGRATION_31_32 = new Migration(31, 32) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS `Contact` (`contactId` TEXT NOT NULL, `name` TEXT, `accountNumber` TEXT, `sortCode` TEXT, `profilePhoto` TEXT, `initials` TEXT, `nameToCompare` TEXT, `hasPayments` INTEGER NOT NULL, PRIMARY KEY(`contactId`))");
            database.execSQL("CREATE TABLE IF NOT EXISTS `ContactTransactions` (`id` TEXT NOT NULL, `contactId` TEXT, `description` TEXT, `localAmount` TEXT, `localCurrency` TEXT, `postTransactionBalance` TEXT, `settlementAmount` TEXT, `settlementCurrency` TEXT, `settlementDate` TEXT, `direction` TEXT, `type` TEXT, `status` TEXT, `paymentDetailsId` TEXT, PRIMARY KEY(`id`))");
            database.execSQL("ALTER TABLE `Transactions` ADD COLUMN `contactId` TEXT");
            database.execSQL("ALTER TABLE `Transactions` ADD COLUMN `lootUserId` TEXT");
            database.execSQL("ALTER TABLE `Transactions` ADD COLUMN `senderId` TEXT");
            database.execSQL("ALTER TABLE `Transactions` ADD COLUMN `recipientId` TEXT");
            database.execSQL("ALTER TABLE `Transactions` ADD COLUMN `payment` INTEGER NOT NULL DEFAULT(0)");
            database.execSQL("CREATE TABLE IF NOT EXISTS `RecipientsAndSenders` (`id` TEXT NOT NULL, `name` TEXT, `accountNumber` TEXT, `sortCode` TEXT, `initials` TEXT, `nameToCompare` TEXT, PRIMARY KEY(`id`))");
            database.execSQL("CREATE TABLE IF NOT EXISTS `PhonebookSyncEntity` (`phonebookId` TEXT NOT NULL, `id` TEXT, `profilePhoto` TEXT, `name` TEXT, `initials` TEXT, `nameToCompare` TEXT, `hasPayments` INTEGER NOT NULL, PRIMARY KEY(`phonebookId`))");
        }
    };

    public static final Migration MIGRATION_32_33 = new Migration(32, 33) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE `Contact` ADD COLUMN `contactIdHash` INTEGER NOT NULL DEFAULT(0)");
            database.execSQL("ALTER TABLE `PhonebookSyncEntity` ADD COLUMN `idHash` INTEGER NOT NULL DEFAULT(0)");
        }
    };

    public static final Migration MIGRATION_33_34 = new Migration(33, 34) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS `LootSharedPreferences` (`key` TEXT NOT NULL, `value` TEXT, PRIMARY KEY(`key`))");
        }
    };
}
