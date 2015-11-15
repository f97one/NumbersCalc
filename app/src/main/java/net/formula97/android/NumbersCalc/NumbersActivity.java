package net.formula97.android.NumbersCalc;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 *
 *
 */
public class NumbersActivity extends AppCompatActivity implements View.OnClickListener {

	// ウィジェット類の宣言
	private TextView textViewNumbers;
	private Button buttonNumbers3;
	private Button buttonNumbers4;
    private Button buttonLoto7;
	private AdView adView;

    private final int sNumbers3Sets = 3;
    private final int sNumbers4Sets = 4;
    private final int sMiniLotoSets = 5;
    private final int sLoto6sets = 6;
    private final int sLoto7sets = 7;

    private String calcResult;
    private boolean mIsNumbersMode;

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
        buttonLoto7 = (Button)findViewById(R.id.ButtonLoto7);

        // それぞれのボタンにコールバックリスナーを定義
        buttonNumbers3.setOnClickListener(this);
        buttonNumbers4.setOnClickListener(this);
        buttonLoto7.setOnClickListener(this);

        // AdMob表示用のウィジェットを取得し、コールバックリスナーを定義
        adView = (AdView)findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        calcResult = "";
        mIsNumbersMode = true;
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        calcResult = savedInstanceState.getString("CalcResult");
        mIsNumbersMode = savedInstanceState.getBoolean("IsNumbersMode");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("CalcResult", calcResult);
        outState.putBoolean("IsNumbersMode", mIsNumbersMode);
    }

    /**
     * 桁数に応じた乱数をString型で返す。
     * @param digits int型、要求するランダム値の桁数
     * @return String型、ランダムな数字
     */
    public String calcNumbers(int digits) {
    	String strRet = "";

        Random random = new SecureRandom();

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
                calcResult = mIsNumbersMode ? calcNumbers(3) : calcLoto(5);
                break;
            case R.id.ButtonNumbers4:	// ４ケタボタン
                calcResult = mIsNumbersMode ? calcNumbers(4) : calcLoto(6);
                break;
            case R.id.ButtonLoto7:
                calcResult = calcLoto(7);
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
            case R.id.action_change_mode:
                if (mIsNumbersMode) {
                    // Lotoモードへ変更
                    mIsNumbersMode = false;
                    buttonLoto7.setVisibility(View.VISIBLE);
                    buttonNumbers3.setText(R.string.name_Button_MiniLoto);
                    buttonNumbers4.setText(R.string.name_Button_Loto6);

                    calcResult = "";
                    textViewNumbers.setText("");
                    textViewNumbers.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);
                } else {
                    // Numbersモードへ変更
                    mIsNumbersMode = true;
                    buttonLoto7.setVisibility(View.GONE);
                    buttonNumbers3.setText(R.string.name_ButtonNumbers3);
                    buttonNumbers4.setText(R.string.name_ButtonNumbers4);

                    calcResult = "";
                    textViewNumbers.setText("");
                    textViewNumbers.setTextSize(TypedValue.COMPLEX_UNIT_SP, 96);
                }

                break;
            default:
                ret = super.onOptionsItemSelected(item);
                break;
        }

        return ret;
    }

    private String calcLoto(int sets) {
        int miniLotoMax = 31;
        int loto6Max = 43;
        int loto7Max = 37;

        Map<Integer, Integer> map = new HashMap<>(1);

        Random random = new SecureRandom();
        int d;
        switch (sets) {
            case sMiniLotoSets:
                d = miniLotoMax;
                break;
            case sLoto6sets:
                d = loto6Max;
                break;
            case sLoto7sets:
                d = loto7Max;
                break;
            default:
                d = 4;
                break;
        }

        int r = random.nextInt(d) + 1;
        map.put(r, 1);

        // 初回にすでにputされているので、ループカウンタの初期値は1
        for (int i = 1; i < sets; i++) {
            do {
                r = random.nextInt(d) + 1;
            } while (map.containsKey(r));

            map.put(r, 1);
        }

        Set<Integer> set = map.keySet();
        List<Integer> l = new ArrayList<>(1);
        for (Integer i : set) {
            l.add(i);
        }
        Collections.sort(l);

        StringBuilder builder = new StringBuilder();
        for (int z = 0; z < l.size(); z++) {
            builder.append(String.format("%02d", l.get(z)));
            if (z != l.size() -1) {
                builder.append(", ");
            }
        }

        return builder.toString();
    }
}
