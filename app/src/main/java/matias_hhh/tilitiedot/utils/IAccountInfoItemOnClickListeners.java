package matias_hhh.tilitiedot.utils;

import android.view.View;

/**
 * Created by Matias on 11.3.2016.
 */
public interface IAccountInfoItemOnClickListeners {
    void openEditActivityOnAccountInfoItemClick(View view, int position);
    void removeAccountInfoItemOnRemoveButtonClick(View view, int position);
}
