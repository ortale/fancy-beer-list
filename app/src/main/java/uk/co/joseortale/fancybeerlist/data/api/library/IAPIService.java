package uk.co.joseortale.fancybeerlist.data.api.library;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import uk.co.joseortale.fancybeerlist.model.Beer;

/**
 *
 * Interface containing methods used on the REST API
 *
 */
public interface IAPIService {
    @GET("beers")
    Call<List<Beer>> getBeers();
}
