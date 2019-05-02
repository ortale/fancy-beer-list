package uk.co.joseortale.fancybeerlist.view.interfaces;

/**
 *
 * Interface between data layer to the view passing through view model
 *
 */
public interface OnDataUpdatedListener {
    void dataUpdatedSuccesfully(Object data);
    void dataUpdateError(String error);
}
