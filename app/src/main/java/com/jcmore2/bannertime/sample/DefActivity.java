package com.jcmore2.bannertime.sample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.jcmore2.bannertime.BannerTime;

public class DefActivity extends AppCompatActivity {

    public static void launch(Context context) {

        Intent intent = new Intent(context, DefActivity.class);

        context.startActivity(intent);

    }

    private Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_def);

    }

    @Override
    protected void onResume() {
        super.onResume();
        BannerTime.init(this)
                .shownTime(4000)
                .hiddenTime(3000)
                .imageBanner("http://pre03.deviantart.net/2aa0/th/pre/f/2012/321/d/1/african_landscape_by_dasflon-d5l9t7c.jpg", true)
                .withOKButton()
                .withKOButton()
                .show(new BannerTime.BannerTimeListener() {
                    @Override
                    public void onShow() {
                        Toast.makeText(mContext, "onShow", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onDismiss() {
                        Toast.makeText(mContext, "onDismiss", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onClickOk() {
                        Toast.makeText(mContext, "onClickOk", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onClickKO() {
                        Toast.makeText(mContext, "onClickKO", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onClickImage() {
                        Toast.makeText(mContext, "onClickImage", Toast.LENGTH_SHORT).show();

                    }
                });
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
