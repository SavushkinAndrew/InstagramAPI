package instagram.softdesign.com.instagramphotos.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import instagram.softdesign.com.instagramphotos.R;

public class PhotoViewHolder extends RecyclerView.ViewHolder {

    ImageView photo;
    TextView comment;

    public PhotoViewHolder(View itemView) {
        super(itemView);
        photo = (ImageView)itemView.findViewById(R.id.photo);
        comment = (TextView)itemView.findViewById(R.id.comment);
    }
}
