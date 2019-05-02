package uk.co.joseortale.fancybeerlist.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import uk.co.joseortale.fancybeerlist.AppCons;
import uk.co.joseortale.fancybeerlist.model.FoodPairing;

/**
 *
 * Services responsible to handle pairing food's beer data.
 *
 */
public class FoodPairingServices {
    private static FoodPairingServices instance;
    private DatabaseHelper dbHelper;

    private FoodPairingServices(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public static FoodPairingServices getInstance(Context context) {
        if (instance == null) {
            instance = new FoodPairingServices(context);
        }

        return instance;
    }

    /**
     *
     * Saves food pairing to local database
     *
     * @param foodPairing
     */
    protected void save(FoodPairing foodPairing) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(AppCons.FOOD_PAIRING_NAME, foodPairing.getName());
        values.put(AppCons.FOOD_PAIRING_BEER_ID, foodPairing.getBeerId());

        if (!foodPairingExists(foodPairing)) {
            db.insert(AppCons.FOOD_PAIRING_TABLE, null, values);
        }

        else {
            String selection = AppCons.FOOD_PAIRING_NAME + " = ? AND " + AppCons.FOOD_PAIRING_BEER_ID + " = ?";
            String[] selectionArgs = { foodPairing.getName(), foodPairing.getBeerId().toString() };

            db.update(
                    AppCons.FOOD_PAIRING_TABLE,
                    values,
                    selection,
                    selectionArgs);
        }
    }

    /**
     *
     * Checks if pairing food already exists in order to save a new record or update the existing one.
     *
     * @param foodPairing
     * @return
     */
    private boolean foodPairingExists(FoodPairing foodPairing) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + AppCons.FOOD_PAIRING_TABLE + " WHERE name = ? AND beer_id = ?",
                new String[]{foodPairing.getName(), foodPairing.getBeerId().toString()});

        boolean exists = cursor.moveToFirst();

        cursor.close();

        return exists;
    }

    /**
     *
     * Gets a list of pairing food from a specific beer
     *
     * @param beerId
     * @return
     */
    public List<String> getByBeer(long beerId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selection = AppCons.FOOD_PAIRING_BEER_ID + " = ?";
        String[] selectionArgs = { String.valueOf(beerId) };

        String sortOrder =
                AppCons.FOOD_PAIRING_NAME + " ASC";

        String[] projection = {
                AppCons.FOOD_PAIRING_NAME
        };

        Cursor cursor = db.query(
                AppCons.FOOD_PAIRING_TABLE,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );

        List<String> foodPairings = new ArrayList<>();
        while(cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(AppCons.FOOD_PAIRING_NAME));

            foodPairings.add(name);
        }
        cursor.close();

        return foodPairings;
    }
}
