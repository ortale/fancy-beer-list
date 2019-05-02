package uk.co.joseortale.fancybeerlist.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import uk.co.joseortale.fancybeerlist.AppCons;
import uk.co.joseortale.fancybeerlist.model.Beer;
import uk.co.joseortale.fancybeerlist.model.FoodPairing;

/**
 *
 * Services responsible to handle Beer data
 *
 */
public class BeerService {
    private static BeerService instance;
    private DatabaseHelper dbHelper;
    private FoodPairingServices foodPairingServices;
    private Context context;

    private BeerService(Context context) {
        dbHelper = new DatabaseHelper(context);
        this.context= context;
    }

    public static BeerService getInstance(Context context) {
        if (instance == null) {
            instance = new BeerService(context);
        }

        return instance;
    }

    /**
     *
     * Saves beer and their food pairing to local database.
     *
     * @param beer
     */
    public void save(Beer beer) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(AppCons.BEER_TABLE_ID, beer.getId());
        values.put(AppCons.BEER_TABLE_NAME, beer.getName());
        values.put(AppCons.BEER_TABLE_TAGLINE, beer.getTagline());
        values.put(AppCons.BEER_TABLE_DESCRIPTION, beer.getDescription());
        values.put(AppCons.BEER_TABLE_ABV, beer.getAbv());
        values.put(AppCons.BEER_TABLE_IBU, beer.getIbu());

        foodPairingServices = FoodPairingServices.getInstance(context);

        for (int i = 0; i < beer.getFoodPairing().size(); i++) {
            FoodPairing foodPairing = new FoodPairing();
            String name = beer.getFoodPairing().get(i);
            foodPairing.setName(name);
            foodPairing.setBeerId(beer.getId());

            foodPairingServices.save(foodPairing);
        }

        if (!beerExists(beer)) {
            db.insert(AppCons.BEER_TABLE, null, values);
        }

        else {
            String selection = AppCons.BEER_TABLE_ID + " = ?";
            String[] selectionArgs = { beer.getId().toString() };

            db.update(
                    AppCons.BEER_TABLE,
                    values,
                    selection,
                    selectionArgs);
        }
    }

    /**
     *
     * Checks if beer already exists in order to save a new record or update the existing one.
     *
     * @param beer
     * @return
     */
    private boolean beerExists(Beer beer) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + AppCons.BEER_TABLE + " WHERE _id=?",
                new String[]{beer.getId().toString()});

        boolean exists = cursor.moveToFirst();

        cursor.close();

        return exists;
    }

    /**
     *
     * Returns all the beer stored on local database
     *
     * @return
     */
    public List<Beer> getAll() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                AppCons.BEER_TABLE,
                null,
                null,
                null,
                null,
                null,
                null
        );

        List<Beer> beers = new ArrayList<>();
        while(cursor.moveToNext()) {
            Beer beer = new Beer();

            long id = cursor.getLong(cursor.getColumnIndexOrThrow(AppCons.BEER_TABLE_ID));
            beer.setId((int)id);

            String name = cursor.getString(cursor.getColumnIndexOrThrow(AppCons.BEER_TABLE_NAME));
            beer.setName(name);

            String tagline = cursor.getString(cursor.getColumnIndexOrThrow(AppCons.BEER_TABLE_TAGLINE));
            beer.setTagline(tagline);

            String description = cursor.getString(cursor.getColumnIndexOrThrow(AppCons.BEER_TABLE_DESCRIPTION));
            beer.setDescription(description);

            Double abv = cursor.getDouble(cursor.getColumnIndexOrThrow(AppCons.BEER_TABLE_ABV));
            beer.setAbv(abv);

            Double ibu = cursor.getDouble(cursor.getColumnIndexOrThrow(AppCons.BEER_TABLE_ID));
            beer.setIbu(ibu);

            foodPairingServices = FoodPairingServices.getInstance(context);
            List<String> foodPairings = foodPairingServices.getByBeer(id);
            beer.setFoodPairing(foodPairings);

            beers.add(beer);
        }

        cursor.close();

        return beers;
    }
}