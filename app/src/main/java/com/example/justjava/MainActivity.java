package com.example.justjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextInputLayout inputLayout = (TextInputLayout)findViewById(R.id.textInputLayout);
        Button button=(Button) findViewById(R.id.button);
        EditText name = (EditText) findViewById(R.id.name);
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                inputLayout.setError(null);
                inputLayout.setCounterEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String layoutName = name.getText().toString();
                if(layoutName.isEmpty()){
                    inputLayout.setError("Enter your name");
                }
                else {
                    String t=getString(R.string.none);
                    CheckBox hasWc = (CheckBox) findViewById(R.id.wccb);
                    CheckBox hasCc = (CheckBox) findViewById(R.id.ccb);
                    if (hasWc.isChecked() && hasCc.isChecked())
                        t = getString(R.string.wc)+","+getString(R.string.c);
                    else if (hasWc.isChecked())
                        t = getString(R.string.wc);
                    else if (hasCc.isChecked())
                        t = getString(R.string.c);
                    String mail=getString(R.string.name_bp)+layoutName+ "\n"+getString(R.string.toppings)+" : " + t + "\n"+getString(R.string.quantity)+" : " + qun + "\n"+getString(R.string.price)+" : $" + (price + 5) * qun + ".00" + "\n"+getString(R.string.thanks);
                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    intent.setData(Uri.parse("mailto:"));
                    intent.putExtra(Intent.EXTRA_SUBJECT,getString(R.string.app_name)+" "+getString(R.string.for_mail)+" "+layoutName);
                    intent.putExtra(Intent.EXTRA_TEXT,mail);
                    startActivity(intent);
                    name.setText("");
                    qun = 1;
                    price = 0;
                    hasWc.setChecked(false);
                    hasCc.setChecked(false);
                    display();
                    displayPrice();
                }
            }
        });
    }
    int qun=1,price=0;
    public void increment(View view){
        if(qun==100) {
            Toast.makeText(this,getString(R.string.more), Toast.LENGTH_SHORT).show();
            return;
        }
        qun++;
        display();
        displayPrice();
    }
    public void decrement(View view){
        if(qun==1) {
            Toast.makeText(this,getString(R.string.less), Toast.LENGTH_SHORT).show();
            return;
        }
        qun--;
        display();
        displayPrice();
    }
    public void checkBox1(View view){
        CheckBox hasWc = (CheckBox) findViewById(R.id.wccb);
        if(hasWc.isChecked())
            price++;
        else
            if(price>0)
            price--;
        displayPrice();
    }
    public void checkBox2(View view){
        CheckBox hasCc = (CheckBox) findViewById(R.id.ccb);
        if(hasCc.isChecked())
            price+=2;
        else
            if(price>1)
            price-=2;
        displayPrice();
    }
    private void display() {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" +qun);
    }
    private void displayPrice() {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText("$" + ((price + 5) * qun) + ".00");
    }
}