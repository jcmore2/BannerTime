package com.jcmore2.bannertime.sample;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.jcmore2.bannertime.BannerTime;

public class MainActivity extends AppCompatActivity {

    private Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onResume() {
        super.onResume();


        BannerTime.init(this)
                .shownTime(5000)
                .hiddenTime(3000)
                .imageBanner("http://campbellriver.whatsondigest.com/sites/default/files/event_photographs/Try%20it%20.png", false)
                .withOKButton(R.color.def, "Default", android.R.color.white)
                .withKOButton(R.color._ok_bg, "Custom", android.R.color.white)
                .background(R.color.fav)
                .imageBannerPlaceholder(0)
                .show(new BannerTime.BannerTimeListener() {
                    @Override
                    public void onShow() {
                    }

                    @Override
                    public void onDismiss() {

                    }

                    @Override
                    public void onClickOk() {
                        DefActivity.launch(mContext);
                    }

                    @Override
                    public void onClickKO() {
                        CustomActivity.launch(mContext);
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
