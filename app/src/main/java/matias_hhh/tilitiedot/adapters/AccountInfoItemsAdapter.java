package matias_hhh.tilitiedot.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import matias_hhh.tilitiedot.R;
import matias_hhh.tilitiedot.models.AccountInfoItem;

/**
 * Created by Matias on 6.3.2016.
 */
public class AccountInfoItemsAdapter extends
        RecyclerView.Adapter<AccountInfoItemsAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView ownerTextView;
        public TextView accountNumberTextView;
        public TextView bicCodeTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            ownerTextView = (TextView) itemView.findViewById(R.id.owner);
            accountNumberTextView = (TextView) itemView.findViewById(R.id.accountNumber);
            bicCodeTextView = (TextView) itemView.findViewById(R.id.bicCode);
        }
    }

    private List<AccountInfoItem> accountInfoItems;

    public AccountInfoItemsAdapter(List<AccountInfoItem> accountInfoItems) {
        this.accountInfoItems = accountInfoItems;
    }

    @Override
    public AccountInfoItemsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate our view from xml
        View accountInfoItemView = inflater.inflate(R.layout.item_account_info, parent, false);

        // Return a new holder instance
        return new ViewHolder(accountInfoItemView);
    }

    @Override
    public void onBindViewHolder(AccountInfoItemsAdapter.ViewHolder viewHolder, int position) {

        // Get the data model based on position
        AccountInfoItem accountInfoItem = accountInfoItems.get(position);

        // Set item views based on the data model
        TextView ownerTextView = viewHolder.ownerTextView;
        ownerTextView.setText(accountInfoItem.getOwner());

        TextView accountNumberTextView = viewHolder.accountNumberTextView;
        accountNumberTextView.setText(accountInfoItem.getAccountNumber());


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
}
