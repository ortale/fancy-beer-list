package uk.co.joseortale.fancybeerlist;

/**
 *
 * Whole App Constants.
 *
 */
public class AppCons {
    // API
    public static String API_BASE_URL = "https://api.punkapi.com/v2/";

    // DatabaseHelper
    public static String BEER_TABLE = "beer";
    public static String BEER_TABLE_ID = "_id";
    public static String BEER_TABLE_NAME = "name";
    public static String BEER_TABLE_TAGLINE = "tagline";
    public static String BEER_TABLE_DESCRIPTION = "description";
    public static String BEER_TABLE_ABV = "abv";
    public static String BEER_TABLE_IBU = "ibu";

    public static String FOOD_PAIRING_TABLE = "food_pairing";
    public static String FOOD_PAIRING_ID = "_id";
    public static String FOOD_PAIRING_NAME = "name";
    public static String FOOD_PAIRING_BEER_ID = "beer_id";
}