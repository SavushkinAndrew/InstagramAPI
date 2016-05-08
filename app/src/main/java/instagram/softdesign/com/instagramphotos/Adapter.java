package instagram.softdesign.com.instagramphotos;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import instagram.softdesign.com.instagramphotos.data.network.restmodels.Datum;

public class Adapter extends RecyclerView.Adapter<myViewHolder> {

    List<Datum> user_list;

    public Adapter(List<Datum> user_list) {
        this.user_list = user_list;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);

        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
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
