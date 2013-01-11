package net.formula97.android.NumbersCalc;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author kazutoshi
 *
 */
public class NumbersActivity extends Activity implements View.OnClickListener {

	// ウィジェット類の宣言
	TextView textViewNumbers;
	Button buttonNumbers3;
	Button buttonNumbers4;

    /** Called when the activity is first created. */
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
	 */
	@Override
	protected void onPause() {
		// TODO AdMobを停止する処理を書く
		super.onPause();
	}

	/**
	 * Activityが復帰した時の処理を書く。
	 */
	@Override
	protected void onResume() {
		// TODO AdMobを置く処理を追加する
		super.onResume();


	}
}