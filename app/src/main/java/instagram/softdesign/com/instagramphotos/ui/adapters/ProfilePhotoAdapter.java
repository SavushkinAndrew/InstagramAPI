package instagram.softdesign.com.instagramphotos.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import instagram.softdesign.com.instagramphotos.R;
import instagram.softdesign.com.instagramphotos.data.network.restmodels.Datum;

public class ProfilePhotoAdapter extends RecyclerView.Adapter<PhotoViewHolder> {

    List<Datum> user_list;

    public ProfilePhotoAdapter(List<Datum> user_list) {
        this.user_list = user_list;
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);

        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, int position) {
        Picasso.with(holder.photo.getContext())
                .load(user_list.get(position).getProfilePicture())
                .resize(512,288)
                .centerCrop()
                .into(holder.photo);

        holder.comment.setText(user_list.get(position).getUsername());
    }

    @Override
    public int getItemCount() {
        return user_list.size();
    }
}
