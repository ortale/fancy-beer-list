package uk.co.joseortale.fancybeerlist.data.api.endpoints;

import android.content.Context;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.joseortale.fancybeerlist.data.Helpers.NetworkHelper;
import uk.co.joseortale.fancybeerlist.data.api.library.APIClient;
import uk.co.joseortale.fancybeerlist.data.api.library.IAPIService;
import uk.co.joseortale.fancybeerlist.data.database.BeerService;
import uk.co.joseortale.fancybeerlist.model.Beer;
import uk.co.joseortale.fancybeerlist.view.interfaces.OnDataUpdatedListener;

/**
 *
 * Class responsible to make calls to the API based on Beers requests
 *
 */
public class BeerEndPoints {
    private static BeerEndPoints instance;
    private BeerService service;

    private IAPIService iapiService;
    private OnDataUpdatedListener dataUpdated;

    private BeerEndPoints() {
        iapiService = APIClient.getClient();
    }

    public static BeerEndPoints getInstance() {
        if (instance == null) {
            instance = new BeerEndPoints();
        }
        return instance;
    }

    /**
     *
     * Updates the app beer list
     *
     * @param context
     */
    public void updateBeerList(final Context context) {
        service = BeerService.getInstance(context);

        if (NetworkHelper.isNetworkAvailable(context)) {
            Call<List<Beer>> call = iapiService.getBeers();
            call.enqueue(new Callback<List<Beer>>() {
                @Override
                public void onResponse(Call<List<Beer>> call, Response<List<Beer>> response) {
                    List<Beer> beers = response.body();

                    if (beers != null) {
                        service = BeerService.getInstance(context);
                        for (Beer beer : beers) {
                            service.save(beer);
                        }

                        dataUpdated.dataUpdatedSuccesfully(beers);
                    }
                }

                @Override
                public void onFailure(Call<List<Beer>> call, Throwable t) {
                    dataUpdated.dataUpdateError(t.getMessage());
                }
            });
        }

        else {
            List<Beer> beers = service.getAll();
            dataUpdated.dataUpdatedSuccesfully(beers);
        }
    }

    /**
     *
     * OnDataUpdatedListener is a listener to tell the view that the data has been updated
     *
     * @param dataUpdated
     */
    public void setOnDataUpdatedListener(OnDataUpdatedListener dataUpdated) {
        this.dataUpdated = dataUpdated;
    }
}
