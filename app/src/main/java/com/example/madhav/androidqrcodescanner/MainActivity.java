package com.example.madhav.androidqrcodescanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

//implementing onclicklistener
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //View Objects
    private Button buttonScan;
    private TextView textViewName, textViewPrice,textViewCount,textViewTotal;
    int sum1=0;
    int itemcount = 0;
    //reset button
    Button buttonReset;
    //qr code scanner object
    private IntentIntegrator qrScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //View objects
        buttonScan = (Button) findViewById(R.id.buttonScan);
        textViewName = (TextView) findViewById(R.id.textViewName);
        textViewPrice = (TextView) findViewById(R.id.textViewPrice);
        textViewCount = (TextView) findViewById(R.id.textViewCount);
        textViewTotal = (TextView) findViewById(R.id.textViewTotal);
        //intializing scan object
        qrScan = new IntentIntegrator(this);
        //attaching onclick listener
        buttonScan.setOnClickListener(this);
    }
    public void onClickBtn(View v) {
        Toast.makeText(this, "Cleared", Toast.LENGTH_LONG).show();
        sum1=0;
        itemcount=0;
        textViewName.setText("");
        textViewPrice.setText("");
        textViewCount.setText("0");
        textViewTotal.setText("");
    }
    //Getting the scan results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                try {
                    //converting the data to json
                    JSONObject obj = new JSONObject(result.getContents());
                    //setting values to textviews
                    textViewName.setText(obj.getString("prod_name"));
                    textViewPrice.setText(obj.getString("prod_price"));
                    String x = obj.getString("prod_price");
                    int s = Integer.parseInt(x);
                    sum1+=s;
                    itemcount = itemcount + 1;
                    textViewCount.setText(String.valueOf(itemcount));
                    textViewTotal.setText(String.valueOf(sum1));
                } catch (JSONException e) {
                    e.printStackTrace();
                    //if control comes here
                    //that means the encoded format not matches
                    //in this case you can display whatever data is available on the qrcode
                    //to a toast
                    Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    @Override
    public void onClick(View view) {
        //initiating the qr code scan
        qrScan.initiateScan();
    }
}
