package uk.co.joseortale.fancybeerlist.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.android.databinding.library.baseAdapters.BR;
import com.squareup.picasso.Picasso;

import java.util.List;

import uk.co.joseortale.fancybeerlist.R;
import uk.co.joseortale.fancybeerlist.data.api.endpoints.BeerEndPoints;
import uk.co.joseortale.fancybeerlist.data.database.BeerService;
import uk.co.joseortale.fancybeerlist.model.Beer;
import uk.co.joseortale.fancybeerlist.view.interfaces.OnDataUpdatedListener;

/**
 *
 * Beer View Model
 *
 */
public class BeerViewModel extends BaseObservable {
    private Beer beer;

    public BeerViewModel(Beer beer) {
        this.beer = beer;
    }

    @Bindable
    public String getName() {
        return beer.getName();
    }

    @Bindable
    public void setName(String name) {
        beer.setName(name);
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public Double getIbu() {
        return beer.getIbu();
    }

    @Bindable
    public void setIbu(Double ibu) {
        beer.setIbu(ibu);
        notifyPropertyChanged(BR.ibu);
    }

    /**
     *
     * Returns beer image URL to loadImage method
     *
     * @return
     */
    public String getImageUrl() {
        return beer.getImageUrl();
    }

    /**
     *
     * Gets beer image from URL and rotates it 90 degrees.
     *
     * @param view
     * @param imageUrl
     */
    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        Picasso.with(view.getContext())
                .load(imageUrl)
                .rotate(90f)
                .placeholder(R.drawable.img_placeholder)
                .into(view);
    }

    @Bindable
    public String getAbv() {
        return String.valueOf(beer.getAbv());
    }

    @Bindable
    public void setAbv(Double abv) {
        beer.setAbv(abv);
        notifyPropertyChanged(BR.abv);
    }

    @Bindable
    public String getDescription() {
        return beer.getDescription();
    }

    @Bindable
    public void setDescription(String description) {
        beer.setDescription(description);
        notifyPropertyChanged(BR.description);
    }

    @Bindable
    public String getTagline() {
        return beer.getTagline();
    }

    @Bindable
    public void setTagline(String tagline) {
        beer.setTagline(tagline);
        notifyPropertyChanged(BR.tagline);
    }

    /**
     *
     * Gets a list of type String containing food pairings and returns a single String
     * to be used in its TextView
     *
     * @return
     */
    @Bindable
    public String getFoodPairing() {
        String foodPairing = "";

        for (int i = 0; i < beer.getFoodPairing().size(); i++) {
            String food = beer.getFoodPairing().get(i);
            foodPairing += food;

            if (i < beer.getFoodPairing().size() - 1) {
                foodPairing += ", ";
            }

            else {
                foodPairing += ".";
            }
        }

        return foodPairing;
    }

    @Bindable
    public Integer getId() {
        return beer.getId();
    }

    @Bindable
    public void setId(Integer id) {
        beer.setId(id);
        notifyPropertyChanged(BR.id);
    }

    /**
     *
     * Interface between view and data layer to get local data
     *
     * @param context
     * @return List of beers
     */
    public List<Beer> updateBeerListFromDB(Context context) {
        BeerService service = BeerService.getInstance(context);
        List<Beer> beers = service.getAll();

        return beers;
    }

    /**
     *
     * Interface between view and data layer to update beer list
     *
     * @param context
     */
    public void updateBeerList(Context context) {
        BeerEndPoints endPoints = BeerEndPoints.getInstance();
        endPoints.updateBeerList(context);
    }

    /**
     *
     * @param listener
     */
    public void setDataUpdatedListener(OnDataUpdatedListener listener) {
        BeerEndPoints endPoints = BeerEndPoints.getInstance();
        endPoints.setOnDataUpdatedListener(listener);
    }
}
