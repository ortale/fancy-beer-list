package uk.co.joseortale.fancybeerlist.view.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import uk.co.joseortale.fancybeerlist.R;
import uk.co.joseortale.fancybeerlist.databinding.BeerRowItemBinding;
import uk.co.joseortale.fancybeerlist.model.Beer;
import uk.co.joseortale.fancybeerlist.view.fragments.BeerDetailFragment;
import uk.co.joseortale.fancybeerlist.view.helpers.ViewHelper;
import uk.co.joseortale.fancybeerlist.view.interfaces.OnDataUpdatedListener;
import uk.co.joseortale.fancybeerlist.view.interfaces.OnFragmentChange;
import uk.co.joseortale.fancybeerlist.viewmodel.BeerViewModel;

/**
 *
 * Adapter used to list beers.
 *
 */
public class BeersAdapter extends RecyclerView.Adapter<BeersAdapter.BindingHolder> implements OnDataUpdatedListener {
    private List<Beer> beersList;
    private LayoutInflater layoutInflater;
    private OnFragmentChange onFragmentChange;
    private Context context;

    public BeersAdapter(List<Beer> beersList, OnFragmentChange onFragmentChange) {
        this.beersList = beersList;
        this.onFragmentChange = onFragmentChange;
    }

    @NonNull
    @Override
    public BindingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
            context = parent.getContext();
        }

        BeerRowItemBinding viewBinding = DataBindingUtil.inflate(layoutInflater, R.layout.beer_row_item, parent, false);

        return new BindingHolder(viewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingHolder holder, int position) {
        BeerRowItemBinding binding = holder.binding;
        binding.setBvm(new BeerViewModel(beersList.get(position)));
        holder.bind();

        Beer beer = beersList.get(position);

        binding.imgAbv.setAdjustViewBounds(true);

        binding.tvAbv.setText("(" + beer.getAbv() + "%)");
        if (beer.getAbv() >= 5) {
            binding.imgAbv.setVisibility(View.VISIBLE);
        }

        else {
            binding.imgAbv.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public long getItemId(int i) {
        return beersList.get(i).getId();
    }

    @Override
    public int getItemCount() {
        return beersList.size();
    }

    /**
     *
     * Updates UI data from REST API or from database if no internet connection
     *
     * @param data
     */
    @Override
    public void dataUpdatedSuccesfully(Object data) {
        beersList = (List<Beer>) data;
        notifyDataSetChanged();
    }

    /**
     *
     * Displays an error to the user in case of data update error.
     *
     * @param error
     */
    @Override
    public void dataUpdateError(String error) {
        ViewHelper.showAlertDialog(context);
    }

    /**
     *
     * View holder for Recycler View used to display list of beers
     *
     */
    class BindingHolder extends RecyclerView.ViewHolder {
        private final BeerRowItemBinding binding;

        public BindingHolder(BeerRowItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind() {
            binding.executePendingBindings();
            binding.lineBeer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Beer beer = beersList.get(position);

                    BeerDetailFragment fragment = BeerDetailFragment.newInstance(beer);

                    onFragmentChange.setFragment(fragment);
                }
            });
        }
    }

}
