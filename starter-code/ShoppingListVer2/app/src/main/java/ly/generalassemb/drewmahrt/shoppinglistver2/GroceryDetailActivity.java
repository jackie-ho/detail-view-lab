package ly.generalassemb.drewmahrt.shoppinglistver2;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class GroceryDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_detail);

        TextView groceryName = (TextView)findViewById(R.id.grocery_name);
        TextView groceryDesc = (TextView)findViewById(R.id.grocery_desc);
        TextView groceryId = (TextView)findViewById(R.id.grocery_id);
        TextView groceryPrice = (TextView)findViewById(R.id.grocery_price);
        TextView groceryBrand = (TextView)findViewById(R.id.grocery_brand);
        TextView groceryWeight = (TextView)findViewById(R.id.grocery_weight);

        int colId = getIntent().getIntExtra("ID", -1);

        if (colId >= 0){
            Cursor cursor = GrocerySQLiteOpenHelper.getInstance(GroceryDetailActivity.this).getGroceryList();
            cursor.moveToPosition(colId-1);
            String name = cursor.getString(cursor.getColumnIndex(GrocerySQLiteOpenHelper.COL_ITEM_NAME));
            String price = cursor.getString(cursor.getColumnIndex(GrocerySQLiteOpenHelper.COL_PRICE));
            String desc = cursor.getString(cursor.getColumnIndex(GrocerySQLiteOpenHelper.COL_DESCRIPTION));
            int id = cursor.getInt(cursor.getColumnIndex(GrocerySQLiteOpenHelper.COL_ID));
            String brand = cursor.getString(cursor.getColumnIndex(GrocerySQLiteOpenHelper.COL_BRAND));
            int weight = cursor.getInt(cursor.getColumnIndex(GrocerySQLiteOpenHelper.COL_WEIGHT));

            groceryName.setText("Grocery: "+name);
            groceryDesc.setText("Description: "+desc);
            groceryId.setText("ID: "+ id);
            groceryPrice.setText("Price: "+ price);
            groceryBrand.setText("Brand: "+ brand);
            groceryWeight.setText("Weight: " + weight+" oz.");


        }

    }
}
