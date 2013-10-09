package com.medha.whatsthetip;

import java.text.DecimalFormat;

import com.codepath.tipcalculator.R;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class WhatsTheTip extends Activity {

	private EditText billAmountText;
	private double billAmount;
	private TextView tipAmountText;
	private TextView totalAmountText;
	private String tipFinal;
	private String totalFinal;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tip_calculator_final);

		Typeface robotoThin = Typeface.createFromAsset(getAssets(),
				"fonts/Roboto-Thin.ttf");

		TextView tipString = (TextView) findViewById(R.id.textView3);
		TextView totalString = (TextView) findViewById(R.id.textView4);
		Button tenPercent = (Button) findViewById(R.id.button1);
		Button ftPercent = (Button) findViewById(R.id.button3);
		Button twtPercent = (Button) findViewById(R.id.button2);

		tipString.setTypeface(robotoThin);
		totalString.setTypeface(robotoThin);
		tenPercent.setTypeface(robotoThin);
		ftPercent.setTypeface(robotoThin);
		twtPercent.setTypeface(robotoThin);

		billAmountText = (EditText) findViewById(R.id.editText1);
		tipAmountText = (TextView) findViewById(R.id.textView1);
		totalAmountText = (TextView) findViewById(R.id.textView2);

		billAmountText.setTypeface(robotoThin);
		tipAmountText.setTypeface(robotoThin);
		totalAmountText.setTypeface(robotoThin);

		tenPercent.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				calculateTipAndTotal(0.1);
			}
		});

		ftPercent.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				calculateTipAndTotal(0.15);
			}
		});

		twtPercent.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				calculateTipAndTotal(0.2);
			}
		});

		if (savedInstanceState != null) {
			tipFinal = savedInstanceState.getString("TIP");
			totalFinal = savedInstanceState.getString("TOTAL");
		}

		SpannableString s = new SpannableString("What's the tip?");
		s.setSpan(new TypefaceSpan(this, "Roboto-Thin"), 0, s.length(),
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

		// Update the action bar title with the TypefaceSpan instance
		ActionBar actionBar = getActionBar();
		actionBar.setTitle(s);
		
	}

	public void calculateTipAndTotal(double tipPercentage) {
		double tip = calculateTip(tipPercentage);
		double total = calculateTotal(tip);
		setTipAndTotal(tip, total);
	}

	private double calculateTip(double tipPercentage) {
		billAmount = getBillAmount();
		if (billAmount == 0.0) {
			return 0.0;
		} else {
			return tipPercentage * billAmount;
		}
	}

	private double getBillAmount() {
		String billAmountString = billAmountText.getText().toString();
		if (billAmountString.isEmpty()) {
			billAmount = 0.0;
		} else {
			billAmount = Double.parseDouble(billAmountString);
		}
		return billAmount;
	}

	private double calculateTotal(double tip) {
		return tip + billAmount;
	}

	private void setTipAndTotal(double tip, double total) {
		if (tip == 0.0) {
			tipAmountText.setText("$0.0");
		}
		if (total == 0.0) {
			totalAmountText.setText("$0.0");
		} else {
			DecimalFormat df = new DecimalFormat("#.##");
			tipFinal = df.format(tip);
			totalFinal = df.format(total);
			tipAmountText.setText("$" + tipFinal);
			totalAmountText.setText("$" + totalFinal);
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString("TIP", tipFinal);
		outState.putString("TOTAL", totalFinal);
	}

}
