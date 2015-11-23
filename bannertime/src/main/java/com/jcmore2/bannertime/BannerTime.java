package com.jcmore2.bannertime;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by jcmore2 on 14/9/15.
 *
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

    private static long SHOW_TIME_BANNER;
    private static long DEFAULT_SHOW_TIME = 10000;
    private static long INFINITE_SHOW_TIME =
            DEFAULT_SHOW_TIME * DEFAULT_SHOW_TIME * DEFAULT_SHOW_TIME;
    private static long HIDE_TIME_BANNER;
    private static long DEFAULT_HIDE_TIME = 2 * 60000;

    private static Timer timerShow;
    private static TimerTask timerTaskShow;
    private static Timer timerHide;
    private static TimerTask timerTaskHide;
    private static RequestCreator requestCreator;

    private static Dialog dialog;
    private static BannerTimeListener mListener;
    private static View mView;
    private static View contentView;
    private static int mPlaceHolder;
    private static int mGravity;
    private static int okVisibility;
    private static int koVisibility;
    private static boolean detectOverlayTouch;

    /**
     * Init the BannerTime instance
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
     */
    public static BannerTime get() {
        if (sInstance == null) {
            throw new IllegalStateException(
                    "FreeView is not initialised - invoke " + "at least once with parameterised init/get");
        }
        return sInstance;
    }

    /**
     * Constructor
     */
    private BannerTime(Context context) {
        try {
            if (context == null) {
                throw new IllegalStateException("Cant init, context must not be null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Set resource view
     */
    public static BannerTime withView(int resourceLayout) {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = layoutInflater.inflate(resourceLayout, null);
        return sInstance;
    }

    /**
     * Set view
     */
    public static BannerTime withView(View view) {
        contentView = view;
        return sInstance;
    }

    /**
     * Set Background
     */
    public static BannerTime background(int resourceId) {

        mView.findViewById(R.id.parent).setBackgroundResource(resourceId);
        return sInstance;
    }

    /**
     * Set ImageBanner
     */
    public static BannerTime imageBanner(int resourceId, boolean crop) {

        requestCreator = Picasso.with(mContext).load(resourceId);
        if (crop) {
            requestCreator.fit().centerCrop();
        } else {
            requestCreator.fit().centerInside();
        }
        return sInstance;
    }

    /**
     * Set ImageBanner
     */
    public static BannerTime imageBanner(File file, boolean crop) {

        requestCreator = Picasso.with(mContext).load(file);
        if (crop) {
            requestCreator.fit().centerCrop();
        } else {
            requestCreator.fit().centerInside();
        }
        return sInstance;
    }

    /**
     * Set ImageBanner
     */
    public static BannerTime imageBanner(String path, boolean crop) {

        requestCreator = Picasso.with(mContext).load(path);
        if (crop) {
            requestCreator.fit().centerCrop();
        } else {
            requestCreator.fit().centerInside();
        }
        return sInstance;
    }

    /**
     * Set ImageBanner
     */
    public static BannerTime imageBanner(Uri uri, boolean crop) {

        requestCreator = Picasso.with(mContext).load(uri);
        if (crop) {
            requestCreator.fit().centerCrop();
        } else {
            requestCreator.fit().centerInside();
        }
        return sInstance;
    }

    /**
     * Set ImagePlaceholder
     */
    public static BannerTime imageBannerPlaceholder(int resId) {

        mPlaceHolder = resId;
        return sInstance;
    }

    /**
     * Enable OK button
     */
    public static BannerTime withOKButton() {

        okVisibility = View.VISIBLE;
        return sInstance;
    }

    /**
     * Enable OK button with custom values
     *
     * @param bgResource Background button
     * @param text       Button text
     * @param textColor  Button text color
     */
    public static BannerTime withOKButton(int bgResource, String text, int textColor) {

        Button ok = (Button) mView.findViewById(R.id.btnOk);
        okVisibility = View.VISIBLE;
        ok.setText(text);
        ok.setTextColor(mContext.getResources().getColor(textColor));
        ok.setBackgroundResource(bgResource);
        return sInstance;
    }

    /**
     * Enable KO button
     */
    public static BannerTime withKOButton() {

        koVisibility = View.VISIBLE;
        return sInstance;
    }

    /**
     * Enable KO button with custom values
     *
     * @param bgResource Background button
     * @param text       Button text
     * @param textColor  Button text color
     */
    public static BannerTime withKOButton(int bgResource, String text, int textColor) {

        Button ko = (Button) mView.findViewById(R.id.btnKO);
        koVisibility = View.VISIBLE;
        ko.setText(text);
        ko.setTextColor(mContext.getResources().getColor(textColor));
        ko.setBackgroundResource(bgResource);
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
     * Set Banner always shown
     */
    public static BannerTime setInfiniteShowBanner() {

        SHOW_TIME_BANNER = INFINITE_SHOW_TIME;
        HIDE_TIME_BANNER = 0;
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
     * Set the height of the Banner
     *
     * @param height The desired BannerTime height.
     */
    public static BannerTime setHeight(int height) {

        ImageView img = (ImageView) mView.findViewById(R.id.imageAd);
        img.getLayoutParams().height = height;
        img.requestLayout();
        return sInstance;
    }

    /**
     * Set touch overlay detection
     *
     * @param touchOverlay The desired overlay touch.
     */
    public static BannerTime setTouchOverlay(boolean touchOverlay) {

        detectOverlayTouch = touchOverlay;
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

        if (sInstance == null) throw new RuntimeException("It´ necessary call '.init()' method");

        mListener = listener;

        if (contentView == null) {
            ImageView img = (ImageView) mView.findViewById(R.id.imageAd);
            if (requestCreator != null) {
                if (mPlaceHolder != 0) {
                    requestCreator.placeholder(mPlaceHolder).into(img);
                } else {
                    requestCreator.into(img);
                }
            } else {
                Picasso.with(mContext)
                        .load(mPlaceHolder)
                        .placeholder(mPlaceHolder)
                        .fit()
                        .centerCrop()
                        .into(img);
            }
        }

        createDialog();
    }

    /**
     * Init variables
     */
    private static void init() {

        dialog = null;
        contentView = null;
        requestCreator = null;
        mGravity = BannerTime.BOTTOM;
        mView = LayoutInflater.from(mContext).inflate(R.layout.layout_banner, null, false);
        mPlaceHolder = R.drawable.loading;
        okVisibility = View.GONE;
        koVisibility = View.GONE;
        detectOverlayTouch = false;

        SHOW_TIME_BANNER = DEFAULT_SHOW_TIME;
        HIDE_TIME_BANNER = DEFAULT_HIDE_TIME;

        timerShow = new Timer();
        timerHide = new Timer();
    }

    /**
     * Create Dialog
     */
    private static void createDialog() {

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
        dialog.setCanceledOnTouchOutside(detectOverlayTouch);

        if (contentView != null) {
            if (contentView.getParent() == null) dialog.setContentView(contentView);
        } else {
            if (mView.getParent() == null) dialog.setContentView(mView);
        }

        ImageView img = (ImageView) mView.findViewById(R.id.imageAd);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) mListener.onClickImage();
            }
        });

        mView.findViewById(R.id.btnOk).setVisibility(okVisibility);
        mView.findViewById(R.id.btnOk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                    if (mListener != null) mListener.onClickOk();
                }
            }
        });

        mView.findViewById(R.id.btnKO).setVisibility(koVisibility);
        mView.findViewById(R.id.btnKO).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                    if (mListener != null) mListener.onClickKO();
                }
            }
        });

        scheduleHideBanner(dialog);
    }

    /**
     * Schedule show Banner
     */
    private static void scheduleShowBanner(final Dialog dialog) {

        showDialog(dialog);

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

        dismissDialog(dialog);

        timerTaskHide = new TimerTask() {

            @Override
            public void run() {
                scheduleShowBanner(dialog);
            }
        };

        timerHide.schedule(timerTaskHide, HIDE_TIME_BANNER);
    }

    private static void showDialog(final Dialog dialog) {

        ((Activity) mContext).runOnUiThread(new Runnable() {

            @Override
            public void run() {

                if (!((Activity) mContext).isFinishing()) {
                    dialog.show();
                    if (mListener != null) mListener.onShow();
                }
                Log.i(TAG, "showBanner");
            }
        });
    }

    private static void dismissDialog(final Dialog dialog) {

        ((Activity) mContext).runOnUiThread(new Runnable() {

            @Override
            public void run() {

                if (!((Activity) mContext).isFinishing() && dialog.isShowing()) {
                    dialog.dismiss();
                    if (mListener != null) mListener.onDismiss();
                }
                Log.i(TAG, "hideBanner");
            }
        });
    }

    /**
     * Call on Pause to stop Banner
     */
    public static void onPause() {
        Log.i(TAG, "Banner onPause");

        dismissDialog(dialog);

        if (timerTaskHide != null) timerTaskHide.cancel();
        if (timerTaskShow != null) timerTaskShow.cancel();

        Log.i(TAG, "Banner canceled");
    }

    public interface BannerTimeListener {
        void onShow();

        void onDismiss();

        void onClickOk();

        void onClickKO();

        void onClickImage();
    }
}
