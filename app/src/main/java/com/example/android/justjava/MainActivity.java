package com.example.android.justjava;

        import android.net.Uri;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.view.View;
        import android.widget.EditText;
        import android.widget.TextView;
        import java.text.NumberFormat;
        import android.widget.CheckBox;
        import android.widget.Toast;
        import android.content.Intent;


/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    int quantity = 0;

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view){


        CheckBox whippedCreamCheckbox = (CheckBox) findViewById(R.id.whipcream_checkbox);
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        EditText nameEditText = (EditText) findViewById(R.id.name_edittext);
        String name = nameEditText.getText().toString();
        boolean hasChocolate = chocolateCheckBox.isChecked();
        boolean hasWhippedCream = whippedCreamCheckbox.isChecked();


        int price = calculatePrice(hasWhippedCream, hasChocolate);

        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, name);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this

        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }




//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setData(Uri.parse("geo: 47.6, -122.3"));
//        if (intent.resolveActivity(getPackageManager()) != null){
//               startActivity(intent);
//        }




    }





    /** This method is called when the increment button is clicked
     *
     */
    public void increment(View view){

        if (quantity == 100){
            Toast toast = Toast.makeText(MainActivity.this,"you cannot order more than 100 cups of coffee",Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);

    }

    /* This method is called when the decrement button is clicked */
    public void decrement(View view){
        if (quantity == 1){

          Toast toast  = Toast.makeText(MainActivity.this, "you cannot order less than 1 cup of coffee", Toast.LENGTH_SHORT);
          toast.show();
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);

    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int quantity) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + quantity);
    }






    /**
     * Calculates the price of the order.
     *
     * @return total price  is the number of cups of coffee ordered
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        int price = 5;
        if (hasWhippedCream) {
            price += 1;
        }
        if (hasChocolate) {
            price += 2;

        }
        return quantity * price;

    }

    /**
     * Create summary of the order.
     *
     * @param hasWhippedCream is whether or not the user wants whipped cream topping
     * @param hasChocolate is whether or not the user wants whipped cream topping
     * @param price of the order
     * @return text summary
     */
    private String createOrderSummary(int price, boolean hasWhippedCream, boolean hasChocolate, String name) {
        String Summary = getString(R.string.order_summary_name, name);
        Summary  += "\nAdd whipped cream? " + hasWhippedCream;
        Summary  += "\nAdd chocolate? " + hasChocolate;
        Summary  += "\nQuantity :" + quantity;
        Summary  += "\nTotal :"+ price;
        Summary  += "\n" + getString(R.string.thank);
        return Summary;

    }

}
