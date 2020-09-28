package com.example.justjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    int quantity = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void submitOrder(View view) {
        CheckBox whippedCream = (CheckBox) findViewById(R.id.whipped_cream);
        boolean hasWhippedCream = whippedCream.isChecked();
        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate_textbox);
        boolean hasChocolate = chocolate.isChecked();
        int price = calculatePrice(hasWhippedCream,hasChocolate);
        EditText name = (EditText) findViewById(R.id.Name);
        String userName = name.getText().toString();
        String x = createOrderSummary(price,hasWhippedCream,hasChocolate,userName);

        String [] address = new String[]{"mykart.inc@gmail.com"};
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, address);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee Order by " + userName);
        intent.putExtra(Intent.EXTRA_TEXT,x);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
//        orderSummaryTextView(createOrderSummary(price,hasWhippedCream,hasChocolate,userName));
    }

    public void increment(View view){
        if(quantity >= 100)
        {
            Context context = getApplicationContext();
            CharSequence text = "You can't order more than 100 cups";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }
        quantity = quantity +1;
        display(quantity);
    }

    public void decrement(View view){
        if(quantity <= 1)
        {
            Context context = getApplicationContext();
            CharSequence text = "You can't order less than one cup";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }
        quantity = quantity - 1;
        display(quantity);
    }

    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        int pricePerCup = 5;
        if(hasWhippedCream)
            pricePerCup += 1;
        if(hasChocolate)
            pricePerCup += 2;
        int price = quantity * pricePerCup;
        return price;
    }

    /**
     * Create summary of the order.
     *
     * @param hasWhippedCream is whether or not the user wants whipped cream topping
     * @param hasChocolate is whether or not the user wants chocolate topping
     * @param price of the order
     * @return text summary
     */
    private String createOrderSummary(int price, boolean hasWhippedCream, boolean hasChocolate, String name){
        return   getString(R.string.Name) + " : "+name+
                "\n"+getString(R.string.AddWhippedCream)+"?"+ hasWhippedCream +
                "\n"+getString(R.string.HasChocolate)+"?"+ hasChocolate +
                "\n"+getString(R.string.quantity) +" : "+quantity+
                "\n"+getString(R.string.Total) +" : ₹"+price+
                "\n"+getString(R.string.ThankYou);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


//    private void orderSummaryTextView(String message) {
//        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
//        priceTextView.setText(message);
//    }
}