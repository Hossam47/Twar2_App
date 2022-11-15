package com.hossam.emergency.ads;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdListener;
import com.facebook.ads.NativeAdView;

import static android.widget.ListPopupWindow.MATCH_PARENT;
import static com.hossam.emergency.constants.ConstantTags.ALERTS_BANNER_FACEBOOK;
import static com.hossam.emergency.constants.ConstantTags.CHAT_BANNER_FACEBOOK;
import static com.hossam.emergency.constants.ConstantTags.COMMENTS_RECTANGLE_FACEBOOK;
import static com.hossam.emergency.constants.ConstantTags.INTERSTITIAL_AD_FACEBOOK;
import static com.hossam.emergency.constants.ConstantTags.NATIVE_AD_FACEBOOK;

public class FacebookAds {

    public static String TAG = "details_ads";

    public static AdView smallBannerAds(LinearLayout linearLayout, Activity activity) {

        AdView adViews = new AdView(activity, CHAT_BANNER_FACEBOOK, AdSize.BANNER_HEIGHT_50);
        linearLayout.addView(adViews);

        adViews.loadAd();
        return adViews;
    }

    public static AdView chatFacebookBanner(LinearLayout linearLayout, Activity activity) {

        final AdView adViews = new AdView(activity, CHAT_BANNER_FACEBOOK, AdSize.BANNER_HEIGHT_50);
        linearLayout.addView(adViews);
        adViews.loadAd();
        return adViews;
    }

    public static AdView alertFacebookBanner(LinearLayout linearLayout, Activity activity) {

        final AdView adViews = new AdView(activity, ALERTS_BANNER_FACEBOOK, AdSize.BANNER_HEIGHT_50);
        linearLayout.addView(adViews);
        adViews.loadAd();
        return adViews;
    }

    public static void commentsBannerAds(LinearLayout linearLayout, Activity activity) {

        final AdView adViews = new AdView(activity, COMMENTS_RECTANGLE_FACEBOOK, AdSize.RECTANGLE_HEIGHT_250);
        linearLayout.addView(adViews);
        adViews.loadAd();

    }


    public static NativeAd nativeListcasesAds(final LinearLayout linearLayout, final Activity activity) {

        final NativeAd nativeAd = new NativeAd(activity, NATIVE_AD_FACEBOOK);

        nativeAd.setAdListener(new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {

            }

            @Override
            public void onError(Ad ad, AdError adError) {

            }

            @Override
            public void onAdLoaded(Ad ad) {

                View adView = NativeAdView.render(activity, nativeAd);
                // Add the Native Ad View to your ad container.
                // The recommended dimensions for the ad container are:
                // Width: 280dp - 500dp
                // Height: 250dp - 500dp
                // The template, however, will adapt to the supplied dimensions.
                linearLayout.addView(adView, new LinearLayout.LayoutParams(MATCH_PARENT, 500));
            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        });

        nativeAd.loadAd();

        return nativeAd;
    }


    public static InterstitialAd interstitialAd(Activity activity) {

        final InterstitialAd interstitialAd = new InterstitialAd(activity, INTERSTITIAL_AD_FACEBOOK);
        // Set listeners for the Interstitial Ad

        interstitialAd.setAdListener(new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                // Interstitial ad displayed callback
//                Log.e(TAG, "Interstitial ad displayed.");
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                // Interstitial dismissed callback
//                Log.e(TAG, "Interstitial ad dismissed.");
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback


                Log.e(TAG, "Interstitial ad failed to load: " + adError.getErrorCode());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Interstitial ad is loaded and ready to be displayed
//                Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!");
                // Show the ad
                interstitialAd.show();
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
//                Log.d(TAG, "Interstitial ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
//                Log.d(TAG, "Interstitial ad impression logged!");
            }
        });

        interstitialAd.loadAd();

        // For auto play video ads, it's recommended to load the ad
        // at least 30 seconds before it is shown
        return interstitialAd;

    }
}
