package uk.co.joseortale.fancybeerlist.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import uk.co.joseortale.fancybeerlist.AppCons;

/**
 *
 * SQLite Database handler
 *
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Beer.db";

    private static final String BEER_TABLE =
            "CREATE TABLE " + AppCons.BEER_TABLE + " (" +
                    AppCons.BEER_TABLE_ID + " INTEGER UNIQUE," +
                    AppCons.BEER_TABLE_NAME + " TEXT," +
                    AppCons.BEER_TABLE_TAGLINE + " TEXT," +
                    AppCons.BEER_TABLE_DESCRIPTION + " TEXT," +
                    AppCons.BEER_TABLE_ABV + " DOUBLE," +
                    AppCons.BEER_TABLE_IBU + " DOUBLE)";

    private static final String FOOD_PAIRING_TABLE = "CREATE TABLE "
            + AppCons.FOOD_PAIRING_TABLE + " ("
            + AppCons.FOOD_PAIRING_ID + " integer primary key autoincrement, "
            + AppCons.FOOD_PAIRING_NAME + " text not null, "
            + AppCons.FOOD_PAIRING_BEER_ID + " integer,"
            + " FOREIGN KEY (" + AppCons.FOOD_PAIRING_BEER_ID + ") REFERENCES " + AppCons.BEER_TABLE + "(" + AppCons.BEER_TABLE_ID + "));";

    private static final String SQL_DELETE_BEER_TABLE =
            "DROP TABLE IF EXISTS " + AppCons.BEER_TABLE ;

    private static final String SQL_DELETE_FOOD_PAIRING_TABLE =
            "DROP TABLE IF EXISTS " + AppCons.FOOD_PAIRING_TABLE ;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(BEER_TABLE);
        db.execSQL(FOOD_PAIRING_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_BEER_TABLE);
        db.execSQL(SQL_DELETE_FOOD_PAIRING_TABLE);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
