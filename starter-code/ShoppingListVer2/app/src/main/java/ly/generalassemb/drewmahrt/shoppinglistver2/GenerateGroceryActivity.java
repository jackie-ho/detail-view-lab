package ly.generalassemb.drewmahrt.shoppinglistver2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class GenerateGroceryActivity extends AppCompatActivity {


    String mName;
    String mDesc;
    String mType;
    String mPrice;
    String mBrand;
    int mWeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_grocery);

        Button submitGrocery = (Button) findViewById(R.id.submit_grocery);
        final EditText groceryName = (EditText) findViewById(R.id.add_groceryname);
        final EditText groceryDesc = (EditText) findViewById(R.id.add_grocerydesc);
        final EditText groceryPrice = (EditText) findViewById(R.id.add_groceryprice);
        final EditText groceryType = (EditText) findViewById(R.id.add_grocerytype);
        final EditText groceryBrand = (EditText) findViewById(R.id.add_grocerybrand);
        final EditText groceryWeight = (EditText) findViewById(R.id.add_groceryweight);

        submitGrocery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!groceryName.getText().toString().isEmpty() && !groceryDesc.getText().toString().isEmpty() && !groceryPrice.getText().toString().isEmpty() && !groceryType.getText().toString().isEmpty() && !groceryBrand.getText().toString().isEmpty() && !groceryWeight.getText().toString().isEmpty()){

                    mName = groceryName.getText().toString();
                    mDesc = groceryDesc.getText().toString();
                    mType = groceryType.getText().toString();
                    mPrice = groceryPrice.getText().toString();
                    mBrand = groceryBrand.getText().toString();
                    mWeight = Integer.parseInt(groceryWeight.getText().toString());
                    GrocerySQLiteOpenHelper.getInstance(GenerateGroceryActivity.this).addGrocery(mName, mDesc, mPrice, mType, mBrand, mWeight);
                    setResult(RESULT_OK);
                }

                else {
                    Toast.makeText(GenerateGroceryActivity.this, "Empty field detected. Please fill.", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_CANCELED);
                }

                finish();
            }
        });
    }
}
