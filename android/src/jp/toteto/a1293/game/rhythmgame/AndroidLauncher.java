package jp.toteto.a1293.game.rhythmgame;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class AndroidLauncher extends AndroidApplication implements ActivityRequestHandler {
	AdView mAdView;
	InterstitialAd mInterstitialAd;

	private final int SHOW_ADS = 1;
	private final int HIDE_ADS = 0;
	private final int SHOW_ADSi = 2;
	private final int HIDE_ADSi = 3;

	protected Handler handler = new Handler()
	{
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what) {
				case SHOW_ADS:
				{
					mAdView.setVisibility(View.VISIBLE);
					break;
				}
				case HIDE_ADS:
				{
					mAdView.setVisibility(View.GONE);
					break;
				}
				case SHOW_ADSi:
				{
							if (mInterstitialAd.isLoaded()) {
								mInterstitialAd.show();
							}
					break;
				}
				case HIDE_ADSi:
				{
					mAdView.setVisibility(View.GONE);
					break;
				}
			}
		}
	};

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		View gameView = initializeForView(new RhythmGame(this), config);

		mAdView = new AdView(this);
		mAdView.setAdSize(AdSize.SMART_BANNER);
		mAdView.setAdUnitId(getResources().getString(R.string.banner_ad_unit_id));
		mAdView.setVisibility(View.INVISIBLE);
		mAdView.setBackgroundColor(Color.BLACK);
		AdRequest adRequest = new AdRequest.Builder().build();
		mAdView.loadAd(adRequest);

		//レイアウト
		RelativeLayout layout = new RelativeLayout(this);
		layout.addView(gameView,
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);

		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		layout.addView(mAdView, params);

		mInterstitialAd = new InterstitialAd(this);
		mInterstitialAd.setAdUnitId(getResources().getString(R.string.inter_ad_unit_id));
		requestNewInterstitial();

		mInterstitialAd.setAdListener(new AdListener() {
			@Override
			public void onAdClosed() {
				requestNewInterstitial();
			}
		});
		setContentView(layout);
	}
	private void requestNewInterstitial() {
		AdRequest adRequest = new AdRequest.Builder()
				.addTestDevice("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX")
				.build();

		mInterstitialAd.loadAd(adRequest);
	}
/*
	class ShowInterstitialAd implements RhythmGame.AdShowable {
		@Override
		public void showInterstitialAd() {
			handler.post(new Runnable() {
				@Override
				public void run() {
					if (mInterstitialAd.isLoaded()) {
						mInterstitialAd.show();
					} else {
					}
				}
			});

		}
	}*/
	@Override
	public void showAds(boolean show) {
		if (show) {
			handler.sendEmptyMessage(SHOW_ADS);
		} else {
			handler.sendEmptyMessage(HIDE_ADS);
		}
	}
	public void showAdsi(boolean show) {
		if (show) {
			handler.sendEmptyMessage(SHOW_ADSi);
		} else {
			handler.sendEmptyMessage(HIDE_ADSi);
		}
	}
}