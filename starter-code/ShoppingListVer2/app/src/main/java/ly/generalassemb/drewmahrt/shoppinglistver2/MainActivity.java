package ly.generalassemb.drewmahrt.shoppinglistver2;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import ly.generalassemb.drewmahrt.shoppinglistver2.setup.DBAssetHelper;

public class MainActivity extends AppCompatActivity {

    CursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Ignore the two lines below, they are for setup
        DBAssetHelper dbSetup = new DBAssetHelper(MainActivity.this);
        dbSetup.getReadableDatabase();

        final GrocerySQLiteOpenHelper helper = GrocerySQLiteOpenHelper.getInstance(MainActivity.this);
        Button addButton = (Button) findViewById(R.id.addgrocery);

        final Cursor cursor = helper.getGroceryList();

        mCursorAdapter = new CursorAdapter(MainActivity.this, cursor, 0) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                return LayoutInflater.from(MainActivity.this).inflate(R.layout.grocery_list_layout, parent, false);
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                TextView typeTextView = (TextView) view.findViewById(R.id.item_type);
                TextView nameTextView = (TextView) view.findViewById(R.id.item_name);
                TextView descTextView = (TextView) view.findViewById(R.id.item_desc);
                TextView priceTextView = (TextView) view.findViewById(R.id.item_price);

                typeTextView.setText(cursor.getString(cursor.getColumnIndex(GrocerySQLiteOpenHelper.COL_TYPE)));
                nameTextView.setText(cursor.getString(cursor.getColumnIndex(GrocerySQLiteOpenHelper.COL_ITEM_NAME)));
                descTextView.setText(cursor.getString(cursor.getColumnIndex(GrocerySQLiteOpenHelper.COL_DESCRIPTION)));
                priceTextView.setText(cursor.getString(cursor.getColumnIndex(GrocerySQLiteOpenHelper.COL_PRICE)));

            }
        };

        ListView groceryListView = (ListView) findViewById(R.id.shopping_list_view);
        groceryListView.setAdapter(mCursorAdapter);

        groceryListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                helper.removeItem(id);
                Cursor c = helper.getGroceryList();
                mCursorAdapter.swapCursor(c);
                return true;
            }
        });

        groceryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, GroceryDetailActivity.class);
                Cursor newCursor = GrocerySQLiteOpenHelper.getInstance(MainActivity.this).getGroceryList();
                newCursor.moveToPosition(position);
                i.putExtra("ID", newCursor.getInt(newCursor.getColumnIndex(GrocerySQLiteOpenHelper.COL_ID)));
                startActivity(i);
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, GenerateGroceryActivity.class);
                startActivityForResult(i, 0);
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {

            Cursor updatedCursor = GrocerySQLiteOpenHelper.getInstance(MainActivity.this).getGroceryList();
            mCursorAdapter.swapCursor(updatedCursor);

        }
    }
}
