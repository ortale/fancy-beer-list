package uk.co.joseortale.fancybeerlist.view.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import uk.co.joseortale.fancybeerlist.R;
import uk.co.joseortale.fancybeerlist.databinding.FragmentBeerListBinding;
import uk.co.joseortale.fancybeerlist.model.Beer;
import uk.co.joseortale.fancybeerlist.view.activities.MainActivity;
import uk.co.joseortale.fancybeerlist.view.helpers.ViewHelper;
import uk.co.joseortale.fancybeerlist.view.interfaces.OnDataUpdatedListener;
import uk.co.joseortale.fancybeerlist.view.adapters.BeersAdapter;
import uk.co.joseortale.fancybeerlist.view.interfaces.OnFragmentChange;
import uk.co.joseortale.fancybeerlist.viewmodel.BeerViewModel;

/**
 *
 * Fragment to display beer list
 *
 */
public class BeerListFragment extends Fragment implements OnDataUpdatedListener, OnFragmentChange {
    private List<Beer> beers;
    private FragmentBeerListBinding viewBinding;
    private BeersAdapter adapter;
    private BeerViewModel viewModel;

    public BeerListFragment() {

    }

    public static BeerListFragment newInstance() {
        BeerListFragment fragment = new BeerListFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_beer_list, container, false);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        viewBinding.lvBeers.setLayoutManager(layoutManager);

        beers = new ArrayList<>();
        adapter = new BeersAdapter(beers, this);
        viewBinding.lvBeers.setAdapter(adapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(viewBinding.lvBeers.getContext(),
                layoutManager.getOrientation());
        viewBinding.lvBeers.addItemDecoration(dividerItemDecoration);

        updateBeerList();

        return viewBinding.getRoot();
    }

    /**
     *
     * Makes call to API in order to update beer list
     *
     */
    private void updateBeerList() {
        showProgressBar();

        viewModel = new BeerViewModel(new Beer());
        viewModel.setDataUpdatedListener(this);
        viewModel.updateBeerList(getContext());
    }

    /**
     *
     * Updates beer list from local database
     *
     */
    private void updateBeerLocalList(List<Beer> beers) {
        adapter = new BeersAdapter(beers, BeerListFragment.this);
        viewBinding.lvBeers.setAdapter(adapter);
    }

    private void showProgressBar() {
        viewBinding.lvBeers.setVisibility(View.GONE);
        viewBinding.progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        viewBinding.lvBeers.setVisibility(View.VISIBLE);
        viewBinding.progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * Gets updated data to update adapter
     *
     * @param data
     */
    @Override
    public void dataUpdatedSuccesfully(Object data) {
        beers = (List<Beer>) data;

        adapter = new BeersAdapter(beers, BeerListFragment.this);
        viewBinding.lvBeers.setAdapter(adapter);

        hideProgressBar();
    }

    /**
     *
     * Displays an error to the user in case of data update error.
     *
     * @param error
     */
    @Override
    public void dataUpdateError(String error) {
        AlertDialog.Builder dialog = ViewHelper.showAlertDialog(getContext());
        dialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                List<Beer> beers = viewModel.updateBeerListFromDB(getContext());
                updateBeerLocalList(beers);
            }
        });
        dialog.show();
        hideProgressBar();
    }

    /**
     *
     * Receives fragment set by adapter calling method on MainActivity
     *
     * @param fragment
     */
    @Override
    public void setFragment(Fragment fragment) {
        ((MainActivity)getActivity()).setFragment(fragment);
    }
}
