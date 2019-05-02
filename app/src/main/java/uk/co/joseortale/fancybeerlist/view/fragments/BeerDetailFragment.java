package uk.co.joseortale.fancybeerlist.view.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.co.joseortale.fancybeerlist.R;
import uk.co.joseortale.fancybeerlist.databinding.FragmentBeerDetailBinding;
import uk.co.joseortale.fancybeerlist.model.Beer;
import uk.co.joseortale.fancybeerlist.view.adapters.BeersAdapter;
import uk.co.joseortale.fancybeerlist.viewmodel.BeerViewModel;

/**
 *
 * Fragment to display beer detail
 *
 */
public class BeerDetailFragment extends Fragment {
    private static final String SELECTED_BEER = "selected_beer";

    private Beer mBeer;

    public BeerDetailFragment() {

    }

    /**
     *
     * Passes the selected beer on BeerListFragment
     *
     * @param beer
     * @return a new instance of BeerDetailFragment
     */
    public static BeerDetailFragment newInstance(Beer beer) {
        BeerDetailFragment fragment = new BeerDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(SELECTED_BEER, beer);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mBeer = (Beer) getArguments().getSerializable(SELECTED_BEER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentBeerDetailBinding viewBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_beer_detail, container, false);

        viewBinding.setBvm(new BeerViewModel(mBeer));
        viewBinding.ivBeer.setAdjustViewBounds(true);

        viewBinding.tvStrength.setText(mBeer.getAbv() + " %");

        return viewBinding.getRoot();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
