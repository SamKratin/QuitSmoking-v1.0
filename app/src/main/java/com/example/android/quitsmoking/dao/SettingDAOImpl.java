package com.example.android.quitsmoking.dao;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import com.example.android.quitsmoking.models.SettingEntity;

/**
 *
 * Data Base SQLite
 * Obviously the hardest part of my app.
 * I wasn't ready for this one.
 * Created by sam on 2017-10-28.
 */

public class SettingDAOImpl extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String QS_TABLE_NAME = "qs_settings";
    public static final String QS_COLUMN_ID = "id";
    public static final String QS_COLUMN_INITIAL_CIG_COUNT = "qs_initial_cig_count";
    public static final String QS_COLUMN_DAYS_FRAME = "qs_days_frame";
    public static final String QS_COLUMN_START_DATE = "qs_start_date";
    public static final String QS_COLUMN_DAILY_CIG_COUNT = "qs_daily_cig_count";
    public static final String QS_COLUMN_CIG_PRICE = "qs_cig_price";
    public static final String QS_COLUMN_FINE = "qs_fine";
    public static final String QS_SUBTRACT_CIGARETTE = "qs_subtract_cigarette";

    public SettingDAOImpl(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table " + QS_TABLE_NAME +
                        " (" + QS_COLUMN_ID + " integer primary key, " + QS_COLUMN_INITIAL_CIG_COUNT + " integer, " + QS_COLUMN_DAYS_FRAME + " integer, " +
                        QS_COLUMN_START_DATE + " text, " + QS_COLUMN_DAILY_CIG_COUNT + " integer, " + QS_COLUMN_CIG_PRICE + " real, " +
                        QS_COLUMN_FINE + " integer, " + QS_SUBTRACT_CIGARETTE + " integer)"

        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS qs_settings");
        onCreate(db);
    }

    /**
     *
     *
     */

    public int insertSetting(int initialCigCount, int qs_days_frame, String startDate, int dailyCigCount,
                             double cigPrice, boolean qs_fine, int subtract_cigarette) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("qs_initial_cig_count", initialCigCount);
        contentValues.put("qs_days_frame", qs_days_frame);
        contentValues.put("qs_start_date", startDate);
        contentValues.put("qs_daily_cig_count", dailyCigCount);
        contentValues.put("qs_cig_price", cigPrice);
        contentValues.put("qs_fine", qs_fine ? 1 : 0);
        contentValues.put("qs_subtract_cigarette", subtract_cigarette); // HERE!!!!
        return ((int) db.insert("qs_settings", null, contentValues));
    }

    public SettingEntity getData() throws Exception {
        SettingEntity settings = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from qs_settings where id=1", null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            settings = new SettingEntity(
                    cursor.getInt(cursor.getColumnIndex(QS_COLUMN_ID)),
                    cursor.getInt(cursor.getColumnIndex(QS_COLUMN_INITIAL_CIG_COUNT)),
                    cursor.getInt(cursor.getColumnIndex(QS_COLUMN_DAYS_FRAME)),
                    (new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())).parse(cursor.getString(cursor.getColumnIndex(QS_COLUMN_START_DATE))),
                    cursor.getInt(cursor.getColumnIndex(QS_COLUMN_DAILY_CIG_COUNT)),
                    cursor.getDouble(cursor.getColumnIndex(QS_COLUMN_CIG_PRICE)),
                    Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(QS_COLUMN_FINE))),
                    cursor.getInt(cursor.getColumnIndex(QS_SUBTRACT_CIGARETTE))
            );


            cursor.moveToNext();
        }

        cursor.close();

        return settings;
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        return (int) DatabaseUtils.queryNumEntries(db, QS_TABLE_NAME);
    }

    public int updateSetting(int initialCigCount, int qs_days_frame, Date startDate, int dailyCigCount,
                             double cigPrice, boolean qs_fine, int subtract_cigarette) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("qs_initial_cig_count", initialCigCount);
        contentValues.put("qs_days_frame", qs_days_frame);
        contentValues.put("qs_start_date", startDate.toString());
        contentValues.put("qs_daily_cig_count", dailyCigCount);
        contentValues.put("qs_cig_price", cigPrice);
        contentValues.put("qs_fine", qs_fine ? 1 : 0);
        contentValues.put("qs_subtract_cigarette", subtract_cigarette);

        return db.update("qs_settings", contentValues, "id = ? ", new String[]{Integer.toString(1)});
    }

    public int deleteSetting() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("qs_settings",
                "id <> ? ",
                new String[]{Integer.toString(-1)});
    }

}
