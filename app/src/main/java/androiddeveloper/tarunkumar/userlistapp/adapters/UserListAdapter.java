package androiddeveloper.tarunkumar.userlistapp.adapters;

import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import androiddeveloper.tarunkumar.userlistapp.databinding.NetworkItemBinding;
import androiddeveloper.tarunkumar.userlistapp.databinding.UserItemBinding;
import androiddeveloper.tarunkumar.userlistapp.model.UserData;
import androiddeveloper.tarunkumar.userlistapp.utils.NetworkState;

public class UserListAdapter extends PagedListAdapter<UserData, RecyclerView.ViewHolder> {

    private static final int TYPE_PROGRESS = 0;
    private static final int TYPE_ITEM = 1;

    private NetworkState networkState;
    public UserListAdapter() {
        super(UserData.DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if(viewType == TYPE_PROGRESS) {
            NetworkItemBinding headerBinding = NetworkItemBinding.inflate(layoutInflater, parent, false);
            return new NetworkStateItemViewHolder(headerBinding);

        } else {
            UserItemBinding itemBinding = UserItemBinding.inflate(layoutInflater, parent, false);
            return new UserItemViewHolder(itemBinding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof UserItemViewHolder) {
            ((UserItemViewHolder)holder).bindTo(getItem(position));
        } else {
            ((NetworkStateItemViewHolder) holder).bindView(networkState);
        }
    }


    private boolean hasExtraRow() {
        return networkState != null && networkState != NetworkState.LOADED;
    }

    @Override
    public int getItemViewType(int position) {
        if (hasExtraRow() && position == getItemCount() - 1) {
            return TYPE_PROGRESS;
        } else {
            return TYPE_ITEM;
        }
    }

    public void setNetworkState(NetworkState newNetworkState) {
        NetworkState previousState = this.networkState;
        boolean previousExtraRow = hasExtraRow();
        this.networkState = newNetworkState;
        boolean newExtraRow = hasExtraRow();
        if (previousExtraRow != newExtraRow) {
            if (previousExtraRow) {
                notifyItemRemoved(getItemCount());
            } else {
                notifyItemInserted(getItemCount());
            }
        } else if (newExtraRow && previousState != newNetworkState) {
            notifyItemChanged(getItemCount() - 1);
        }
    }


    public class UserItemViewHolder extends RecyclerView.ViewHolder {

        private UserItemBinding binding;
        UserItemViewHolder(UserItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bindTo(UserData userData) {

            binding.firstName.setText(userData.getFirst_name());

            binding.lastName.setText(userData.getLast_name());

            Picasso.get().load(userData.getAvatar()).resize(250, 200).into(binding.imageView);
        }
    }


    public class NetworkStateItemViewHolder extends RecyclerView.ViewHolder {

        private NetworkItemBinding binding;
        NetworkStateItemViewHolder(NetworkItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bindView(NetworkState networkState) {
            if (networkState != null && networkState.getStatus() == NetworkState.Status.RUNNING) {
                binding.progressBar.setVisibility(View.VISIBLE);
            } else {
                binding.progressBar.setVisibility(View.GONE);
            }
        }
    }
}
