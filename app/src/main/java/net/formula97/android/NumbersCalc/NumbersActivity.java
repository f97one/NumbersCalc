package net.formula97.android.NumbersCalc;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.Random;

/**
 *
 *
 */
public class NumbersActivity extends ActionBarActivity implements View.OnClickListener {

	// ウィジェット類の宣言
	private TextView textViewNumbers;
	private Button buttonNumbers3;
	private Button buttonNumbers4;
	private AdView adView;

    private String calcResult;

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
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        calcResult = "";
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        calcResult = savedInstanceState.getString("CalcResult");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("CalcResult", calcResult);
    }

    /**
     * 桁数に応じた乱数をString型で返す。
     * @param digits int型、要求するランダム値の桁数
     * @return String型、ランダムな数字
     */
    public String calcNumbers(int digits) {
    	String strRet = "";

        Random random = new Random();

        if (digits == 3) {
            // 000～999なので、nextIntの引数は1000
            strRet = String.format("%03d", random.nextInt(1000));
        } else {
            // 同様に、0000～9999なので、nextIntの引数は10000
            strRet = String.format("%04d", random.nextInt(10000));
        }

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
            calcResult = calcNumbers(3);
			break;
		case R.id.ButtonNumbers4:	// ４ケタボタン
            calcResult = calcNumbers(4);
			break;
		default:
			break;
		}

        textViewNumbers.setText(calcResult);
	}

	/**
	 * Activityが一時停止状態に入った時の処理を書く。
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		super.onPause();

		adView.pause();
	}

	/**
	 * Activityが復帰した時の処理を書く。
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		super.onResume();

		textViewNumbers.setText(calcResult);

		adView.resume();
	}

	/* (非 Javadoc)
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
        adView.destroy();
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean ret = false;

        switch(item.getItemId()) {
            case R.id.action_show_web:
                // 当選番号案内表示ダイアログ
                SelectDialog selectDialog = new SelectDialog();
                selectDialog.show(getSupportFragmentManager(), SelectDialog.FRAGMENT_TAG);
                ret = true;
                break;
            default:
                ret = super.onOptionsItemSelected(item);
                break;
        }

        return ret;
    }
}