package instagram.softdesign.com.instagramphotos.ui.activitys;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import instagram.softdesign.com.instagramphotos.InstagramApplication;
import instagram.softdesign.com.instagramphotos.R;
import instagram.softdesign.com.instagramphotos.data.network.instagramApi.InstagramApp;
import instagram.softdesign.com.instagramphotos.utils.RoundTransformation;


public class RegistrationActivity extends AppCompatActivity {

    Button mInstagramAuth;
    private InstagramApp mApp;
    private TextView tvUsername, tvFullname;
    private ImageView mUserPhoto;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        tvUsername = (TextView)findViewById(R.id.et_email_value);
        tvFullname = (TextView)findViewById(R.id.et_email_value2);
        mUserPhoto = (ImageView)findViewById(R.id.userPhoto);

        mApp = new InstagramApp(this, InstagramApplication.CLIENT_ID,
                InstagramApplication.CLIENT_SECRET, InstagramApplication.CALLBACK_URL);
        mApp.setListener(listener);

        mInstagramAuth = (Button)findViewById(R.id.instButton);
        assert mInstagramAuth != null;
        mInstagramAuth.setOnClickListener(v -> {
            if (mApp.hasAccessToken()) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(
                        RegistrationActivity.this);
                builder.setMessage("Disconnect from Instagram?")
                        .setCancelable(false)
                        .setPositiveButton("Yes",
                                (dialog, id) -> {
                                    mApp.resetAccessToken();
                                })
                        .setNegativeButton("No",
                                (dialog, id) -> {
                                    dialog.cancel();
                                });
                final AlertDialog alert = builder.create();
                alert.show();
            } else {
                mApp.authorize();
            }
        });
    }

    InstagramApp.OAuthAuthenticationListener listener = new InstagramApp.OAuthAuthenticationListener() {

        @Override
        public void onSuccess() {
            tvUsername.setText(mApp.getUserName());
            tvFullname.setText(mApp.getName());
            Picasso.with(RegistrationActivity.this)
                    .load(mApp.getUserPhoto())
                    .resize(140,140)
                    .centerCrop()
                    .transform(new RoundTransformation(20))
                    .into(mUserPhoto);
        }

        @Override
        public void onFail(String error) {
            Toast.makeText(RegistrationActivity.this, error, Toast.LENGTH_SHORT).show();
        }
    };
}
