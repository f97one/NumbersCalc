package jp.ddo.formula97.numbers;

import android.app.Activity;
import android.os.Bundle;
//import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author kazutoshi
 *
 */
public class NumbersActivity extends Activity {

	TextView textViewNumbers;
	Button buttonNumbers3;
	Button buttonNumbers4;

	int integerNumbers;
	String strNumbers = "";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        textViewNumbers = (TextView)findViewById(R.id.TextViewNumbers);
        //buttonNumbers3 = new Button(this);
        //buttonNumbers4 = new Button(this);

    }

    public void showNumbers(View view) {
    	if (view.getId() == R.id.ButtonNumbers3) {
    		calicurateNumbers(3);
    		textViewNumbers.setText(strNumbers);

    		strNumbers = "";
    	} else {
    		calicurateNumbers(4);
    		textViewNumbers.setText(strNumbers);

    		strNumbers = "";
    	}
    }

    /*
     * 桁数に応じた乱数をString型で返す
     */
    public String calicurateNumbers(int keta) {
    	for (int i = 0; i < keta; i++) {
    		// 0～9の整数範囲で乱数を生成
    		integerNumbers = (int)Math.floor(Math.random() * 10);

    		//Log.d("calicurateNumbers", "Random number : " + integerNumbers);

    		// 乱数を文字列にして結合
    		strNumbers = strNumbers + integerNumbers;

    		//Log.d("calicurateNumbers", "combined number : " + strNumbers);

    		// integerNumbers += integerNumbers;
    	}

    	// 結合された文字列を返す
    	return strNumbers;

    	// textViewNumbers.setText(strNumbers);
    	// textViewNumbers.setText(integerNumbers);
    }
}