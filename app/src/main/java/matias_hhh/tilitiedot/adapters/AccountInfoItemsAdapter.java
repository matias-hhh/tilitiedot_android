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
import matias_hhh.tilitiedot.utils.IAccountInfoItemOnClickMethods;

/**
 * Created by Matias on 6.3.2016.
 */
public class AccountInfoItemsAdapter extends
        RecyclerView.Adapter<AccountInfoItemsAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView ownerTextView;
        public TextView accountNumberTextView;
        public TextView bicCodeTextView;

        public IAccountInfoItemOnClickMethods onClickMethods;

        public ViewHolder(View itemView, IAccountInfoItemOnClickMethods onClickMethods) {
            super(itemView);

            this.onClickMethods = onClickMethods;

            ownerTextView = (TextView) itemView.findViewById(R.id.owner);
            accountNumberTextView = (TextView) itemView.findViewById(R.id.accountNumber);
            bicCodeTextView = (TextView) itemView.findViewById(R.id.bicCode);

            itemView.setOnClickListener(this);

            Button removeButton = (Button) itemView.findViewById((R.id.button_remove));
            removeButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view instanceof Button) {
                int position = this.getAdapterPosition();
                onClickMethods.removeAccountInfoItemOnRemoveButtonClick(view, position);
            } else {
                onClickMethods.openEditActivityOnAccountInfoItemClick(view);
            }
        }
    }

    private List<AccountInfoItem> accountInfoItems;
    private IAccountInfoItemOnClickMethods onClickMethods;

    public AccountInfoItemsAdapter(List<AccountInfoItem> accountInfoItems,
                                   IAccountInfoItemOnClickMethods onClickMethods) {
        this.accountInfoItems = accountInfoItems;
        this.onClickMethods = onClickMethods;
    }

    @Override
    public AccountInfoItemsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate our view from xml
        View accountInfoItemView = inflater.inflate(R.layout.item_account_info, parent, false);

        // Return a new holder instance
        return new ViewHolder(accountInfoItemView, onClickMethods);
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
        return accountInfoItems.get(position).getId();
    }

    public void removeItem(int position) {
        accountInfoItems.remove(position);
        notifyItemRemoved(position);
    }
}
