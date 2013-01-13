package net.formula97.android.NumbersCalc;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.ads.*;
import com.google.ads.AdRequest.ErrorCode;

/**
 * @author kazutoshi
 *
 */
public class NumbersActivity extends Activity implements View.OnClickListener, AdListener {

	// ウィジェット類の宣言
	TextView textViewNumbers;
	Button buttonNumbers3;
	Button buttonNumbers4;

	AdView adView;
	// private static final String MY_AD_UNIT_ID = "a150e97716f2ebe";

    /** Called when the activity is first created.
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // ウィジェット類を取得
        textViewNumbers = (TextView)findViewById(R.id.TextViewNumbers);
        buttonNumbers3 = (Button)findViewById(R.id.ButtonNumbers3);
        buttonNumbers4 = (Button)findViewById(R.id.ButtonNumbers4);

        // それぞれのボタンにコールバックリスナーを定義
        buttonNumbers3.setOnClickListener(this);
        buttonNumbers4.setOnClickListener(this);

        // AdMob表示用のウィジェットを取得し、コールバックリスナーを定義
        adView = (AdView)findViewById(R.id.adView);
        adView.setAdListener(this);
    }

    /**
     * 桁数に応じた乱数をString型で返す。
     * @param digits int型、要求するランダム値の桁数
     * @return String型、ランダムな数字
     */
    public String calcNumbers(int digits) {
    	int iNumbers = 0;
    	String strRet = "";

    	// 指定桁数になるまで、乱数を結合する
    	for (int i = 0; i < digits; i++) {
    		// 0～9の整数範囲で乱数を生成
    		iNumbers = (int)Math.floor(Math.random() * 10);

    		// 乱数を文字列にして結合
    		strRet = strRet + String.valueOf(iNumbers);
    	}

    	// 結合された文字列を返す
    	return strRet;
    }

    /**
     * ボタンを押した時の処理を定義する。
     * @param v View型、押されたボタンのView
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {

		// 押したボタンに応じて、画面に置く文字を変える
		switch (v.getId()) {
		case R.id.ButtonNumbers3:	// ３ケタボタン
			textViewNumbers.setText(calcNumbers(3));
			break;
		case R.id.ButtonNumbers4:	// ４ケタボタン
			textViewNumbers.setText(calcNumbers(4));
			break;
		default:
			break;
		}
	}

	/**
	 * Activityが一時停止状態に入った時の処理を書く。
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		// TODO AdMobを停止する処理を書く
		super.onPause();

		adView.stopLoading();
	}

	/**
	 * Activityが復帰した時の処理を書く。
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		// TODO AdMobを置く処理を追加する
		super.onResume();

		textViewNumbers.setText("");

		adView.loadAd(new AdRequest());
	}

	/* (非 Javadoc)
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		// TODO 自動生成されたメソッド・スタブ
		super.onDestroy();

		if (adView.isReady()) {
			adView.destroy();
		}
	}

	/* (非 Javadoc)
	 * @see com.google.ads.AdListener#onDismissScreen(com.google.ads.Ad)
	 */
	@Override
	public void onDismissScreen(Ad arg0) {
		// TODO 自動生成されたメソッド・スタブ

	}

	/* (非 Javadoc)
	 * @see com.google.ads.AdListener#onFailedToReceiveAd(com.google.ads.Ad, com.google.ads.AdRequest.ErrorCode)
	 */
	@Override
	public void onFailedToReceiveAd(Ad arg0, ErrorCode arg1) {
		Log.w("onFailedToReceiveAd", "AdMob failed to receive, Error code = " + arg1);

	// TODO AdMobのビューをレイアウトから削除する処理を書く
	}

	/* (非 Javadoc)
	 * @see com.google.ads.AdListener#onLeaveApplication(com.google.ads.Ad)
	 */
	@Override
	public void onLeaveApplication(Ad arg0) {
		// TODO 自動生成されたメソッド・スタブ

	}

	/* (非 Javadoc)
	 * @see com.google.ads.AdListener#onPresentScreen(com.google.ads.Ad)
	 */
	@Override
	public void onPresentScreen(Ad arg0) {
		// TODO 自動生成されたメソッド・スタブ

	}

	/* (非 Javadoc)
	 * @see com.google.ads.AdListener#onReceiveAd(com.google.ads.Ad)
	 */
	@Override
	public void onReceiveAd(Ad arg0) {
		// TODO 自動生成されたメソッド・スタブ
		Log.d("onReceivedAd", "AdMob received succeesfuly.");
	}
}