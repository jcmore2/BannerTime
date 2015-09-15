package com.jcmore2.bannertime;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by jcmore2 on 14/9/15.
 * <p/>
 * BannerTime create a scheduled popup to show your personal message
 */
public class BannerTime {

    private static final String TAG = "BannerTime";

    /**
     * BannerTime at TOP
     */
    public static final int TOP = Gravity.TOP;
    /**
     * BannerTime at BOTTOM
     */
    public static final int BOTTOM = Gravity.BOTTOM;
    /**
     * BannerTime at LEFT
     */
    public static final int LEFT = Gravity.LEFT;
    /**
     * BannerTime at RIGHT
     */
    public static final int RIGHT = Gravity.RIGHT;


    private static BannerTime sInstance;
    private static Context mContext;
    private static boolean isPaused;

    private static long SHOW_TIME_BANNER;
    private static long HIDE_TIME_BANNER;
    private static Timer timerShow;
    private static TimerTask timerTaskShow;
    private static Timer timerHide;
    private static TimerTask timerTaskHide;
    private static RequestCreator requestCreator;

    //private static Dialog dialog;
    private static BannerTimeListener mListener;
    private static View mView;
    private static View contentView;
    private static int mPlaceHolder;
    private static int mGravity;
    private static int okVisibility;
    private static int koVisibility;


    /**
     * Init the BannerTime instance
     *
     * @param context
     */
    public static BannerTime init(Context context) {

        if (sInstance == null) {
            sInstance = new BannerTime(context);
        }
        mContext = context;
        init();

        return sInstance;
    }

    /**
     * get the BannerTime instance
     *
     * @return
     */
    public static BannerTime get() {
        if (sInstance == null) {
            throw new IllegalStateException(
                    "FreeView is not initialised - invoke " +
                            "at least once with parameterised init/get");
        }
        return sInstance;
    }

    /**
     * Constructor
     *
     * @param context
     */
    private BannerTime(Context context) {
        try {
            if (context == null) {
                Log.e(TAG, "Cant init, context must not be null");
            } else {
                mContext = context;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Set resource view
     *
     * @param resourceLayout
     * @return
     */
    public static BannerTime withView(int resourceLayout) {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = layoutInflater.inflate(resourceLayout, null);
        return sInstance;
    }

    /**
     * Set view
     *
     * @param view
     * @return
     */
    public static BannerTime withView(View view) {
        contentView = view;
        return sInstance;
    }


    /**
     * Set ImageBanner
     *
     * @param resourceId
     * @return
     */
    public static BannerTime imageBanner(int resourceId) {

        requestCreator = Picasso.with(mContext).load(resourceId);
        return sInstance;
    }

    /**
     * Set ImageBanner
     *
     * @param file
     * @return
     */
    public static BannerTime imageBanner(File file) {

        requestCreator = Picasso.with(mContext).load(file);

        return sInstance;
    }

    /**
     * Set ImageBanner
     *
     * @param path
     * @return
     */
    public static BannerTime imageBanner(String path) {

        requestCreator = Picasso.with(mContext).load(path);

        return sInstance;
    }

    /**
     * Set ImageBanner
     *
     * @param uri
     * @return
     */
    public static BannerTime imageBanner(Uri uri) {

        requestCreator = Picasso.with(mContext).load(uri);
        return sInstance;
    }

    /**
     * Set ImagePlaceholder
     *
     * @param resId
     * @return
     */
    public static BannerTime imageBannerPlaceholder(int resId) {

        mPlaceHolder = resId;
        return sInstance;
    }

    /**
     * Enable OK button with background color
     *
     * @param color
     * @return
     */
    public static BannerTime withOKBackgroundColorAndText(int color, String text) {

        Button ok = (Button) mView.findViewById(R.id.btnOk);
        okVisibility = View.VISIBLE;
        ok.setText(text);
        ok.setBackgroundColor(mContext.getResources().getColor(color));
        return sInstance;
    }

    /**
     * Enable OK button with background resource
     *
     * @param resource
     * @return
     */
    public static BannerTime withOKBackgroundResourceAndText(int resource, String text) {

        Button ok = (Button) mView.findViewById(R.id.btnOk);
        okVisibility = View.VISIBLE;
        ok.setText(text);
        ok.setBackgroundResource(resource);
        return sInstance;
    }

    /**
     * Enable KO button with background color
     *
     * @param color
     * @return
     */
    public static BannerTime withKOBackgroundColorAndText(int color, String text) {

        Button ko = (Button) mView.findViewById(R.id.btnKO);
        koVisibility = View.VISIBLE;
        ko.setText(text);
        ko.setBackgroundColor(mContext.getResources().getColor(color));
        return sInstance;
    }

    /**
     * Enable KO button with background resource
     *
     * @param resource
     * @return
     */
    public static BannerTime withKOBackgroundResourceAndText(int resource, String text) {

        Button ko = (Button) mView.findViewById(R.id.btnKO);
        koVisibility = View.VISIBLE;
        ko.setText(text);
        ko.setBackgroundResource(resource);
        return sInstance;
    }

    /**
     * Set Show time in milliseconds
     */
    public static BannerTime shownTime(int time) {

        SHOW_TIME_BANNER = time;
        return sInstance;

    }

    /**
     * Set Hide time in milliseconds
     */
    public static BannerTime hiddenTime(int time) {

        HIDE_TIME_BANNER = time;
        return sInstance;

    }

    /**
     * Set the position of the Banner, as per the BannerTime constants.
     *
     * @param gravity The desired BannerTime constant.
     * @see BannerTime
     */
    public static BannerTime setGravity(int gravity) {

        mGravity = gravity;
        return sInstance;
    }

    /**
     * Show banner
     */
    public static void show() {

        show(null);

    }

    /**
     * Show banner with callback
     */
    public static void show(BannerTimeListener listener) {

        if (sInstance == null)
            throw new RuntimeException("It´ necessary call '.init()' method");

        mListener = listener;

        ImageView img = (ImageView) mView.findViewById(R.id.imageAd);
        if (requestCreator != null) {
            requestCreator
                    .fit().centerCrop()
                    .placeholder(mPlaceHolder)
                    .into(img);
        } else {
            Picasso.with(mContext)
                    .load(mPlaceHolder)
                    .placeholder(mPlaceHolder)
                    .fit().centerCrop()
                    .into(img);
        }

        createDialog();
    }

    /**
     * Init variables
     */
    private static void init() {

        contentView = null;
        requestCreator = null;
        mGravity = BannerTime.BOTTOM;
        mView = LayoutInflater.from(mContext)
                .inflate(R.layout.layout_banner, null, false);
        mPlaceHolder = R.drawable.loading;
        okVisibility = View.GONE;
        koVisibility = View.GONE;
        isPaused = false;

        SHOW_TIME_BANNER = 10000;
        HIDE_TIME_BANNER = 2 * 60000;

        timerShow = new Timer();
        timerHide = new Timer();
    }

    /**
     * Create Dialog
     */
    private static void createDialog() {

        final Dialog dialog;
        if (mGravity == BannerTime.BOTTOM) {
            dialog = new Dialog(mContext, R.style.DialogSlideAnimBottom);
        } else if (mGravity == BannerTime.TOP) {
            dialog = new Dialog(mContext, R.style.DialogSlideAnimTop);
        } else if (mGravity == BannerTime.LEFT) {
            dialog = new Dialog(mContext, R.style.DialogSlideAnimLeft);
        } else if (mGravity == BannerTime.RIGHT) {
            dialog = new Dialog(mContext, R.style.DialogSlideAnimRight);
        } else {
            dialog = new Dialog(mContext, R.style.DialogSlideAnimBottom);
        }
        dialog.getWindow().setGravity(mGravity);
        dialog.setCanceledOnTouchOutside(false);

        if (contentView != null) {
            if (contentView.getParent() == null)
                dialog.setContentView(contentView);
        } else {
            if (mView.getParent() == null)
                dialog.setContentView(mView);
        }

        ImageView img = (ImageView) mView.findViewById(R.id.imageAd);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null)
                    mListener.onClickImage();

            }
        });

        mView.findViewById(R.id.btnOk).setVisibility(okVisibility);
        mView.findViewById(R.id.btnOk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                    if (mListener != null)
                        mListener.onClickOk();
                }
            }
        });

        mView.findViewById(R.id.btnKO).setVisibility(koVisibility);
        mView.findViewById(R.id.btnKO).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                    if (mListener != null)
                        mListener.onClickKO();
                }
            }
        });

        scheduleHideBanner(dialog);

    }

    /**
     * Schedule show Banner
     */
    private static void scheduleShowBanner(final Dialog dialog) {

        ((Activity) mContext).runOnUiThread(new Runnable() {

            @Override
            public void run() {

                if (!((Activity) mContext).isFinishing()) {
                    dialog.show();
                    if (mListener != null)
                        mListener.onShow();
                }
                Log.i(TAG, "showBanner");
            }
        });

        timerTaskShow = new TimerTask() {

            @Override
            public void run() {
                scheduleHideBanner(dialog);
            }
        };

        timerShow.schedule(timerTaskShow, SHOW_TIME_BANNER);
    }

    /**
     * Schedule hide Banner
     */
    private static void scheduleHideBanner(final Dialog dialog) {

        ((Activity) mContext).runOnUiThread(new Runnable() {

            @Override
            public void run() {

                if (!((Activity) mContext).isFinishing() && dialog.isShowing()) {
                    dialog.dismiss();
                    if (mListener != null)
                        mListener.onDismiss();
                }
                Log.i(TAG, "hideBanner");
            }
        });

        timerTaskHide = new TimerTask() {

            @Override
            public void run() {
                scheduleShowBanner(dialog);
            }
        };

        timerHide.schedule(timerTaskHide, HIDE_TIME_BANNER);
    }

    /**
     * Call on Pause to stop Banner
     */
    public static void onPause() {
        Log.i(TAG, "Banner onPause");

        if (timerTaskHide != null)
            timerTaskHide.cancel();
        if (timerTaskShow != null)
            timerTaskShow.cancel();

        isPaused = true;

        Log.i(TAG, "Banner canceled");

    }

    /**
     * Call on Resume to start Banner
     */
    public static void onResume() {
        Log.i(TAG, "Banner onResume");

        if (isPaused) {
            Log.i(TAG, "Banner Run");
            timerTaskHide.run();
            isPaused = false;
        }

    }

    public interface BannerTimeListener {
        void onShow();

        void onDismiss();

        void onClickOk();

        void onClickKO();

        void onClickImage();

    }
}
