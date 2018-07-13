package io.loot.lootsdk;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.framework.FrameworkSQLiteOpenHelperFactory;
import android.arch.persistence.room.testing.MigrationTestHelper;
import android.support.test.InstrumentationRegistry;

import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import io.loot.lootsdk.database.LootDatabase;

import static io.loot.lootsdk.database.migrations.DatabaseMigrations.MIGRATION_31_32;
import static io.loot.lootsdk.database.migrations.DatabaseMigrations.MIGRATION_32_33;
import static io.loot.lootsdk.database.migrations.DatabaseMigrations.MIGRATION_33_34;

/**
 * Created by erykrutkowski on 22.02.2018.
 */

public class MigrationTest {
    private static final String TEST_DB = "migration-test";

    @Rule
    public MigrationTestHelper helper;

    public MigrationTest() {
        helper = new MigrationTestHelper(InstrumentationRegistry.getInstrumentation(),
                LootDatabase.class.getCanonicalName(),
                new FrameworkSQLiteOpenHelperFactory());
    }

    @Test
    public void migrate31To34() throws IOException {
        SupportSQLiteDatabase db = helper.createDatabase(TEST_DB, 31);

        // db has schema version 1. insert some data using SQL queries.
        // You cannot use DAO classes because they expect the latest schema.
        db.execSQL("INSERT INTO SavingGoal (id, goalPercentage) VALUES ('TESTID', '0')"); //it's dummy insert this inserted data should be data suitable for migration

        // Prepare for the next version.
        db.close();

        db = helper.runMigrationsAndValidate(TEST_DB, 33, true, MIGRATION_31_32, MIGRATION_32_33, MIGRATION_33_34);

        // MigrationTestHelper automatically verifies the schema changes,
        // but you need to validate that the data was migrated properly.
    }

    @Test
    public void migrate32To34() throws IOException {
        SupportSQLiteDatabase db = helper.createDatabase(TEST_DB, 32);

        // db has schema version 1. insert some data using SQL queries.
        // You cannot use DAO classes because they expect the latest schema.
        db.execSQL("INSERT INTO SavingGoal (id, goalPercentage) VALUES ('TESTID', '0')"); //it's dummy insert this inserted data should be data suitable for migration

        // Prepare for the next version.
        db.close();

        db = helper.runMigrationsAndValidate(TEST_DB, 34, true, MIGRATION_31_32, MIGRATION_32_33, MIGRATION_33_34);

        // MigrationTestHelper automatically verifies the schema changes,
        // but you need to validate that the data was migrated properly.
    }

    @Test
    public void migrate33To34() throws IOException {
        SupportSQLiteDatabase db = helper.createDatabase(TEST_DB, 33);

        // db has schema version 1. insert some data using SQL queries.
        // You cannot use DAO classes because they expect the latest schema.
        db.execSQL("INSERT INTO SavingGoal (id, goalPercentage) VALUES ('TESTID', '0')"); //it's dummy insert this inserted data should be data suitable for migration

        // Prepare for the next version.
        db.close();

        db = helper.runMigrationsAndValidate(TEST_DB, 34, true, MIGRATION_31_32, MIGRATION_32_33, MIGRATION_33_34);

        // MigrationTestHelper automatically verifies the schema changes,
        // but you need to validate that the data was migrated properly.
    }
}
