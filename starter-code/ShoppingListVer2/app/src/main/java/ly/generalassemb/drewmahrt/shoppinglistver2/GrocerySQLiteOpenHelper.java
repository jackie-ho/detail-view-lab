package ly.generalassemb.drewmahrt.shoppinglistver2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by JHADI on 2/2/16.
 */


public class GrocerySQLiteOpenHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 8;
    public static final String DATABASE__NAME = "SHOPPING_DB";
    public static final String GROCERY_TABLE_NAME = "SHOPPING_LIST";

    public static final String COL_ID = "_id";
    public static final String COL_ITEM_NAME = "ITEM_NAME";
    public static final String COL_DESCRIPTION = "DESCRIPTION";
    public static final String COL_PRICE = "PRICE";
    public static final String COL_TYPE = "TYPE";
    public static final String COL_BRAND = "BRAND";
    public static final String COL_WEIGHT = "WEIGHT";

    private int counter = 8;

    public static final String[] GROCERY_COLUMNS = {COL_ID, COL_ITEM_NAME, COL_DESCRIPTION, COL_PRICE, COL_TYPE, COL_BRAND, COL_WEIGHT};

    private static GrocerySQLiteOpenHelper instance;

    private GrocerySQLiteOpenHelper(Context context) {
        super(context, DATABASE__NAME, null, DATABASE_VERSION);
    }

    public static GrocerySQLiteOpenHelper getInstance(Context context) {
        if (instance == null) {
            instance = new GrocerySQLiteOpenHelper(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + GROCERY_TABLE_NAME + "("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_ITEM_NAME + " TEXT, "
                + COL_DESCRIPTION + " TEXT, "
                + COL_PRICE + " TEXT, "
                + COL_TYPE + " TEXT, "
                + COL_BRAND + " TEXT, "
                + COL_WEIGHT + " INTEGER )");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST " + GROCERY_TABLE_NAME);
        onCreate(db);
    }

    public Cursor getGroceryList() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(GROCERY_TABLE_NAME, // a. table
                GROCERY_COLUMNS, // b. column names
                null, // c. selections
                null, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
        return cursor;
    }

    public void addGrocery(String name, String description, String price, String type, String brand, int weight){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_ITEM_NAME, name);
        values.put(COL_DESCRIPTION, description);
        values.put(COL_PRICE, price);
        values.put(COL_TYPE, type);
        values.put(COL_BRAND, brand);
        values.put(COL_WEIGHT,weight);

        db.insert(GROCERY_TABLE_NAME, null, values);

    }

    public void removeItem(long row) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + GROCERY_TABLE_NAME + " WHERE " + COL_ID + " = " + (int) (row));
    }
}
