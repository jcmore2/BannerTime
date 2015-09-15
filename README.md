BannerTime
=============

BannerTime create a scheduled popup to show your personal message

<img src='raw/sample.png' width='270' height='480' />

Usage
-----

Add library to your build.gradle:

```java

	    compile 'com.jcmore2.bannertime:bannertime:1.0.0'

```

Init BannerTime:

```java

            BannerTime.init(this)
                    .shownTime(10000)
                    .hiddenTime(10000)
                    .imageBanner("https://bookish101.files.wordpress.com/2013/06/hello.gif")
                    .withOKBackgroundColorAndText(android.R.color.white, "-OK-")
                    .withKOBackgroundColorAndText(android.R.color.black, "-KO-")
                    .show();

```

<img src='raw/sample1.gif' width='350' height='350' />


You can also use a callback:

```java

        BannerTime.init(this)
                .shownTime(10000)
                .hiddenTime(10000)
                .imageBanner("https://bookish101.files.wordpress.com/2013/06/hello.gif")
                .withOKBackgroundColorAndText(android.R.color.white, "-OK-")
                .withKOBackgroundColorAndText(android.R.color.black, "-KO-")
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

```


Init BannerTime with your custom view:

```java

       LayoutInflater layoutInflater = (LayoutInflater) mContext.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       View contentView = layoutInflater.inflate(R.layout.custom_banner, null);

       BannerTime.init(this)
                .shownTime(10000)
                .hiddenTime(10000)
                .withView(contentView)
                .show();

```

Pause BannerTime using:

```java

    @Override
    protected void onPause() {
        super.onPause();
        BannerTime.onPause();
    }


```

Resume BannerTime using:

```java

    @Override
    protected void onResume() {
        super.onResume();
        BannerTime.onResume();

    }



```


You can check the sample App!

Credits & Contact
-----------------

FreeView was created by jcmore2@gmail.com


License
-------

BannerTime is available under the Apache License, Version 2.0.
