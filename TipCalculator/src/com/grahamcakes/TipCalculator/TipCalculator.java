package com.grahamcakes.TipCalculator;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;


public class TipCalculator extends Activity {
	private static final String BILL_TOTAL = "BILL_TOTAL";
	private static final String CUSTOM_PERCENT = "CUSTOM_PERCENT";
	private static final double PI_NUMBER = 3.14;
	private static final double E_NUMBER = 2.78;
	
	private double currentBillTotal;
	private int currentCustomPercent;
	private EditText tip10EditText;
	private EditText total10EditText;
	private EditText tip15EditText;
	private EditText total15EditText;
	private EditText billEditText;
	private EditText tip20EditText;
	private EditText total20EditText;
	
	private TextView customTipTextView;
	private EditText tipCustomEditText;
	private EditText tipECustomEditText;
	private EditText tipPiCustomEditText;
	private EditText totalCustomEditText;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);              
        setContentView(R.layout.main); // inflat the GUI
        
        // check if the app just started or is being restored from memory
        if (savedInstanceState == null) {
        	currentBillTotal = 0.0; // initialize bill amount to zero
        	currentCustomPercent = 18; // initialize custom tip to 18%
        } else {
        	currentBillTotal = savedInstanceState.getDouble(BILL_TOTAL);
        	currentCustomPercent = savedInstanceState.getInt(CUSTOM_PERCENT);
        }
        
        // get references to the 10%, 15%, 20% tip and total EditTexts
        tip10EditText = (EditText) findViewById(R.id.tip10EditText);
        total10EditText = (EditText) findViewById(R.id.total10EditText);
        tip15EditText = (EditText) findViewById(R.id.tip15EditText);
        total15EditText = (EditText) findViewById(R.id.total15EditText);
        tip20EditText = (EditText) findViewById(R.id.tip20EditText);
        total20EditText = (EditText) findViewById(R.id.total20EditText);
        
        // Custom tip percentage
        customTipTextView = (TextView) findViewById(R.id.customTipTextView);
        // get custom tip and total EditTexts
        tipCustomEditText = (EditText) findViewById(R.id.tipCustomEditText);
        totalCustomEditText = (EditText) findViewById(R.id.totalCustomEditText);
        tipECustomEditText = (EditText) findViewById(R.id.tipECustomEditText); 
        tipPiCustomEditText = (EditText) findViewById(R.id.tipPiCustomEditText);
        
        // get billEditText
        billEditText = (EditText) findViewById(R.id.billEditText);
		// billEditTextWatcher handles BillEditText's onTextChange event
        billEditText.addTextChangedListener(billEditTextWatcher);
        
        // get the SeekBar used to get the custom tip amount
        SeekBar customSeekBar = (SeekBar) findViewById(R.id.customSeekBar);
        customSeekBar.setOnSeekBarChangeListener(customSeekBarListener);
    }
    
    // updates 10,15,20 tip and total EditText
    private void updateStandard() {
    	double tenPercentTip = currentBillTotal * .1;
    	double tenPercentTotal = currentBillTotal + tenPercentTip;
    	tip10EditText.setText(String.format("%.02f", tenPercentTip));
    	total10EditText.setText(String.format("%.02f", tenPercentTotal));
    	
    	double fifteenPercentTip = currentBillTotal * .15;
    	double fifteenPercentTotal = currentBillTotal + fifteenPercentTip;
    	tip15EditText.setText(String.format("%.02f", fifteenPercentTip));
    	total15EditText.setText(String.format("%.02f", fifteenPercentTotal));
    	
    	double twentyPercentTip = currentBillTotal * .2;
    	double twentyPercentTotal = currentBillTotal + twentyPercentTip;
    	tip20EditText.setText(String.format("%.02f", twentyPercentTip));
    	total20EditText.setText(String.format("%.02f", twentyPercentTotal));
    }
    
    // updates custom, e, pi and total
    private void updateCustom() {
    	customTipTextView.setText(currentCustomPercent + "%");
    	double customTipAmount = currentBillTotal * currentCustomPercent * .01;
    	double customETipAmount = customTipAmount - E_NUMBER;
    	double customPiTipAmount = customTipAmount - PI_NUMBER;
    	double customTotalAmount = currentBillTotal + customTipAmount;
    	
    	tipCustomEditText.setText(String.format("%.02f", customTipAmount));
    	tipECustomEditText.setText(String.format("%.02f", customETipAmount));
    	tipPiCustomEditText.setText(String.format("%.02f", customPiTipAmount));
    	totalCustomEditText.setText(String.format("%.02f", customTotalAmount));
    }
    
    // save values of billEditText and customSeekBar
    @Override
    protected void onSaveInstanceState(Bundle outState) {
    	super.onSaveInstanceState(outState);
    	
    	outState.putDouble(BILL_TOTAL, currentBillTotal);
    	outState.putInt(CUSTOM_PERCENT, currentCustomPercent);
    }
    
    // called when user changes the position of SeekBar
    private OnSeekBarChangeListener customSeekBarListener = new OnSeekBarChangeListener() {
    	// update currentCustomPercent
    	@Override
    	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
    		currentCustomPercent = seekBar.getProgress();
    		updateCustom();
    	} // end method
    	
    	@Override
    	public void onStartTrackingTouch(SeekBar seekBar) {    		
    	} // end method 
    	
    	@Override
    	public void onStopTrackingTouch(SeekBar seekBar) {    		
    	} // end method
    };
    
    // event-handling object that responds to billEditText's events
    private TextWatcher billEditTextWatcher = new TextWatcher() {
    	// called when user enters a number
    	@Override
    	public void onTextChanged(CharSequence s, int start, int before, int count) {
    		// convert billEditText's text to double
    		try {
    			currentBillTotal = Double.parseDouble(s.toString());
    		} catch (NumberFormatException e) {
    			currentBillTotal = 0.0;
    		} // end catch
    		
    		updateStandard();
    		updateCustom();
    	} // end method
    	
    	@Override
    	public void afterTextChanged(Editable s) {    		
    	} // end method
    	
    	@Override
    	public void beforeTextChanged(CharSequence s, int start, int count, int after) {    		
    	} // end method
    };
}