package instagram.softdesign.com.instagramphotos;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class myViewHolder extends RecyclerView.ViewHolder {

    ImageView photo;
    TextView comment;

    public myViewHolder(View itemView) {
        super(itemView);
        photo = (ImageView)itemView.findViewById(R.id.photo);
        comment = (TextView)itemView.findViewById(R.id.comment);
    }
}
