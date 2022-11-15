//package com.hossam.emergency.ads;
//
//import android.app.Activity;
//
//import com.google.android.gms.ads.AdListener;
//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdView;
//import com.google.android.gms.ads.InterstitialAd;
//
//public class GoogleAds {
//
//
//    public static AdView smallBannerAdsGoogle(AdView adView) {
//
//        AdRequest adRequest = new AdRequest.Builder().build();
//        adView.loadAd(adRequest);
//
//        return adView;
//    }
//
//
////    public static void nativeTemplateGoogleAd(final Activity activity, final int templateView) {
////
////        AdLoader adLoader = new AdLoader.Builder(activity, "[_ad-unit-id_]")
////                .forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
////                    @Override
////                    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
////                        NativeTemplateStyle styles = new
////                                NativeTemplateStyle.Builder()
////                                .withMainBackgroundColor(ContextCompat.getColor(activity, R.color.blue_semi_transparent))
////                                .build();
////
////                        TemplateView template = activity.findViewById(templateView);
////                        template.setStyles(styles);
////                        template.setNativeAd(unifiedNativeAd);
////
////                    }
////                })
////                .build();
////
////        adLoader.loadAd(new AdRequest.Builder().build());
////    }
//
//
//    public static AdView chatBannerAdsGoogle(AdView adView) {
//
//        AdRequest adRequest = new AdRequest.Builder().build();
//        adView.loadAd(adRequest);
//
//        return adView;
//    }
//
//    public static AdView notificationBannerAdsGoogle(AdView adView) {
//
//        AdRequest adRequest = new AdRequest.Builder().build();
//        adView.loadAd(adRequest);
//
//        return adView;
//    }
//
//    public static AdView commentsBannerAdsGoogle(AdView adView) {
//
//        AdRequest adRequest = new AdRequest.Builder().build();
//        adView.loadAd(adRequest);
//
//        return adView;
//    }
//
//    public static AdView casesBannerAdsGoogle(AdView adView) {
//
//        AdRequest adRequest = new AdRequest.Builder().build();
//        adView.loadAd(adRequest);
//
//        return adView;
//    }
//
//
//    public static InterstitialAd interstitialAdsGoogle(Activity activity) {
//
//        final InterstitialAd interstitialAd = new InterstitialAd(activity);
//
////        interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
////        interstitialAd.loadAd(new AdRequest.Builder().build());
////        interstitialAd.show();
////        interstitialAd = new InterstitialAd(activity);
//
//        interstitialAd.setAdUnitId("ca-app-pub-4105900153682505/7627294800");
//        interstitialAd.loadAd(new AdRequest.Builder().build());
//
//        interstitialAd.setAdListener(new AdListener() {
//            @Override
//            public void onAdLoaded() {
//                interstitialAd.show();
//            }
//
//            @Override
//            public void onAdFailedToLoad(int errorCode) {
//                // Code to be executed when an ad request fails.
//            }
//
//            @Override
//            public void onAdOpened() {
//                // Code to be executed when the ad is displayed.
//            }
//
//            @Override
//            public void onAdClicked() {
//                // Code to be executed when the user clicks on an ad.
//            }
//
//            @Override
//            public void onAdLeftApplication() {
//                // Code to be executed when the user has left the app.
//            }
//
//            @Override
//            public void onAdClosed() {
//                // Code to be executed when the interstitial ad is closed.
//            }
//        });
//
//
//        return interstitialAd;
//    }
//}
