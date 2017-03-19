package com.jruivodev.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import static android.R.attr.name;

public class MainActivity extends AppCompatActivity {
    private int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);

    }

    private void displayMessage(String str) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(str);
    }

    /**
     * Calculates the price of the order based on the current quantity.
     *
     * @return the price
     */
    private int calculatePrice(int quantity, int basePrice, boolean addWhippedCream, boolean addChocolate) {
        int finalPrice = basePrice;
        if (addWhippedCream)
            finalPrice += 1;
        if (addChocolate)
            finalPrice += 2;
        finalPrice *= quantity;
        return finalPrice;

    }

    private String generateOrderSummary() {
        EditText nameEditText = (EditText) findViewById(R.id.name_edit_text);
        String name = nameEditText.getText().toString();

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whippedCream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        return createOrderSummary(name, 5, hasWhippedCream, hasChocolate);
    }

    public void submitOrder(View view) {
        displayMessage(generateOrderSummary());
    }

    public void emailOrder(View view) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "JavaCoffee order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, generateOrderSummary());
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate) {
        String orderSummary = "Name: " + name + "\n";

        if (addWhippedCream)
            orderSummary += "Topping: Whipped cream\n";
        if (addChocolate)
            orderSummary += "Topping: Chocolate\n";
        orderSummary += "Quantity: " + quantity + "\n";
        orderSummary += "Total: $" + calculatePrice(quantity, price, addWhippedCream, addChocolate) + "\n";
        orderSummary += "\nThank you!";
        return orderSummary;
    }

    public void increment(View view) {
        quantity += 1;
        displayQuantity(quantity);
    }

    public void decrement(View view) {
        if (quantity > 0)
            quantity -= 1;
        displayQuantity(quantity);
    }
}
