package matias_hhh.tilitiedot.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import matias_hhh.tilitiedot.R;
import matias_hhh.tilitiedot.models.AccountInfoItem;
import matias_hhh.tilitiedot.utils.IAccountInfoItemOnClickListeners;

/**
 * Adapter for the RecyclerView in the MainActivity, which shows a list of AccountInfoItems
 */
public class AccountInfoItemsAdapter extends
        RecyclerView.Adapter<AccountInfoItemsAdapter.ViewHolder> {

    /**
     * The ViewHolder for single AccountInfoItem-view in the RecyclerView. Set up onClick-listeners
     * also.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public TextView ownerTextView;
        public TextView accountNumberTextView;
        public TextView bicCodeTextView;

        public IAccountInfoItemOnClickListeners onClickListeners;

        /**
         * Constructor
         *
         * @param itemView: The view for the AccountInfoItem
         * @param onClickListeners: An implementation of an interface containing the onClick-
         *                          listeners for the view
         */
        public ViewHolder(View itemView, IAccountInfoItemOnClickListeners onClickListeners) {
            super(itemView);

            this.onClickListeners = onClickListeners;

            ownerTextView = (TextView) itemView.findViewById(R.id.owner);
            accountNumberTextView = (TextView) itemView.findViewById(R.id.accountNumber);
            bicCodeTextView = (TextView) itemView.findViewById(R.id.bicCode);

            // Listen on clicks with the whole item
            itemView.setOnClickListener(this);

            // Listen on button clicks also
            Button removeButton = (Button) itemView.findViewById((R.id.button_remove));
            removeButton.setOnClickListener(this);
        }

        // Set the listeners for different clicks.
        @Override
        public void onClick(View view) {
            int position = this.getAdapterPosition();

            if (view instanceof Button) {
                onClickListeners.removeAccountInfoItemOnRemoveButtonClick(view, position);
            } else {
                onClickListeners.openEditActivityOnAccountInfoItemClick(view, position);
            }
        }
    }

    private List<AccountInfoItem> accountInfoItems;
    private IAccountInfoItemOnClickListeners onClickListeners;

    /**
     *  Constructor
     *
     *  @param accountInfoItems: A list of AccountInfoItems to be shown in the RecyclerView.
     *  @param onClickListeners: An instance implementing an interface containing the onClick-
     *                           listeners for a single item in the RecyclerView
     */
    public AccountInfoItemsAdapter(List<AccountInfoItem> accountInfoItems,
                                   IAccountInfoItemOnClickListeners onClickListeners) {
        this.accountInfoItems = accountInfoItems;
        this.onClickListeners = onClickListeners;
    }

    @Override
    public AccountInfoItemsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate our view from xml
        View accountInfoItemView = inflater.inflate(R.layout.item_account_info, parent, false);

        // Return a new holder instance
        return new ViewHolder(accountInfoItemView, onClickListeners);
    }

    @Override
    public void onBindViewHolder(AccountInfoItemsAdapter.ViewHolder viewHolder, int position) {

        // Get the data model based on position
        AccountInfoItem accountInfoItem = accountInfoItems.get(position);

        // Set item views based on the data model
        TextView ownerTextView = viewHolder.ownerTextView;
        ownerTextView.setText(accountInfoItem.getOwner());

        TextView accountNumberTextView = viewHolder.accountNumberTextView;
        accountNumberTextView.setText(accountInfoItem.getFormattedAccountNumber());


        TextView bicCodeTextView = viewHolder.bicCodeTextView;

        // If a BIC-code exists, show it, otherwise hide the view
        if (accountInfoItem.getBicCode() != null) {
            bicCodeTextView.setText(accountInfoItem.getBicCode());
        } else {
            bicCodeTextView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return accountInfoItems.size();
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    /**
     * Get a single item from a given position.
     *
     * @param position: Adapter position to query
     * @return AccountInfoItem specified by the position
     */
    public AccountInfoItem getItem(int position) {
        return accountInfoItems.get(position);
    }

    /**
     * Remove an item from the given position and notify the view to update the changes.
     *
     * @param position: Adapter position to be removed.
     */
    public void removeItem(int position) {
        accountInfoItems.remove(position);
        notifyItemRemoved(position);
    }
}
