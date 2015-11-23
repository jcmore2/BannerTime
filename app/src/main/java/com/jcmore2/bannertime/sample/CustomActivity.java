package com.jcmore2.bannertime.sample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.jcmore2.bannertime.BannerTime;
import com.squareup.picasso.Picasso;

public class CustomActivity extends AppCompatActivity {

    public static void launch(Context context) {

        Intent intent = new Intent(context, CustomActivity.class);

        context.startActivity(intent);

    }

    private Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_custom);


    }

    private void createBanner(){

        LayoutInflater layoutInflater = (LayoutInflater) mContext.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = layoutInflater.inflate(R.layout.custom_banner, null);

        ImageView fav = (ImageView) contentView.findViewById(R.id.imageFav);
        ImageView rt = (ImageView) contentView.findViewById(R.id.imageRt);

        contentView.findViewById(R.id.fav).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Fav clicked", Toast.LENGTH_SHORT).show();

            }
        });

        contentView.findViewById(R.id.rt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Retweet Clicked", Toast.LENGTH_SHORT).show();

            }
        });

        Picasso.with(mContext)
                .load("http://blog.balfesnet.netdna-cdn.com/wp-content/uploads/2013/04/twitter-fav.png")
                .fit().centerInside()
                .placeholder(R.drawable.loading)
                .into(fav);

        Picasso.with(mContext)
                .load("https://cdn0.iconfinder.com/data/icons/social-flat-rounded-rects/512/retweet-128.png")
                .fit().centerInside()
                .placeholder(R.drawable.loading)
                .into(rt);


        BannerTime.init(this)
                .shownTime(4000)
                .hiddenTime(3000)
                .setTouchOverlay(true)
                .setGravity(BannerTime.TOP)
                .withView(contentView)
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        createBanner();
    }

    @Override
    protected void onPause() {
        super.onPause();
        BannerTime.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity_def in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
