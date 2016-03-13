package matias_hhh.tilitiedot.utils;

import android.view.View;

/**
 * Created by Matias on 11.3.2016.
 */
public interface IAccountInfoItemOnClickListeners {

    /**
     *  OnClick-listener. Changes the activity to EditAccountInfoItem.
     *
     *  @param view: The clicked view
     *  @param position: The position of the clicked item in the adapter
     */
    void openEditActivityOnAccountInfoItemClick(View view, int position);

    /**
     *  OnClick-listener. Removes the AccountInfoItem from the db and the RecyclerView.
     *
     *  @param view: The clicked view
     *  @param position: The position of the clicked item in the adapter
     */
    void removeAccountInfoItemOnRemoveButtonClick(View view, int position);
}
