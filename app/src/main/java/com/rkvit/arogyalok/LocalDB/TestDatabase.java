package com.rkvit.arogyalok.LocalDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.rkvit.arogyalok.Model.SlctTestModel;
import com.rkvit.arogyalok.Model.SlctTestModel;

import java.util.ArrayList;
import java.util.List;

public class TestDatabase extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "ArogyalokTest.db";
    public static final int DATABASE_VERSION = 2;
    public static final String PRODUCT_TABLE_NAME = "test";
    public static final String KEY_OrderID = "_id";
    public static final String KEY_ProductCode = "testCode";
    private static final String id = "ID";
    private static final String name = "Name";
    private static final String image = "Image";
    private static final String mrp = "MRP";
    private static final String  Quantity ="Qty";

    ArrayList<SlctTestModel> list;
    private ArrayList<SlctTestModel> MyCartItems = new ArrayList<>();

    public TestDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + PRODUCT_TABLE_NAME + "(" +
                KEY_OrderID + " INTEGER , " +
                KEY_ProductCode+ " INTEGER , " +
                id + " INTEGER , " +
                name + " TEXT, " +
                image + " TEXT, " +
                Quantity + " INTEGER, " +
                mrp + " DOUBLE) ";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PRODUCT_TABLE_NAME);
        onCreate(db);
    }

    public void insertRecord(SlctTestModel slctTestModel) {
        SQLiteDatabase db = this.getWritableDatabase();


        Integer ItemCode = null;
        Integer Qty = null;
        String query = "SELECT * FROM test WHERE testCode = " + slctTestModel.getId();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            //name = cursor.getString(column_index);//to get other values
            ItemCode = cursor.getInt(2);//to get id, 0 is the column index
//            Qty = cursor.getInt(16);
            Log.d("ProductID", String.valueOf(ItemCode));
        }

        if (ItemCode == null) {
            ContentValues values = new ContentValues();

            values.put(KEY_OrderID, slctTestModel.getId());
            values.put(KEY_ProductCode, slctTestModel.getId());
            values.put(id, slctTestModel.getId());
            values.put(name, slctTestModel.getName());
            values.put(image, slctTestModel.getImage());
            values.put(mrp, slctTestModel.getMrp());
            values.put(Quantity, slctTestModel.getQuantity());
            db.insert(PRODUCT_TABLE_NAME, null, values);

            db.close(); // Closing database connection
        } else {


            ContentValues values = new ContentValues();

            String strSQL = " UPDATE test SET Qty =" + slctTestModel.getQuantity() + " WHERE testCode =" + String.valueOf(ItemCode);
            db.execSQL(strSQL);

        }
    }

    public int getItemCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        int count = (int) DatabaseUtils.queryNumEntries(db, PRODUCT_TABLE_NAME);
        db.close();
        return count;
    }

    public ArrayList<SlctTestModel> getAllProduct() {
        List<SlctTestModel> Cartitem = new ArrayList<SlctTestModel>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + PRODUCT_TABLE_NAME;
        //String selectQuery = "SELECT * FROM " + PRODUCT_TABLE_NAME + " ORDER BY "+KEY_OrderID+" DESC LIMIT 1";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                SlctTestModel myCartItem = new SlctTestModel();

                myCartItem.setId(cursor.getString(1));
                myCartItem.setName(cursor.getString(3));
                myCartItem.setImage(cursor.getString(4));
                myCartItem.setMrp(String.valueOf((cursor.getInt(6))));
                myCartItem.setQuantity(String.valueOf((cursor.getInt(5))));

                // Adding contact to list
                MyCartItems.add(myCartItem);
            } while (cursor.moveToNext());
        }
        // close inserting data from database
        db.close();
        // return contact list

        return MyCartItems;
    }

    public void deleteRow(String productCode) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(PRODUCT_TABLE_NAME, KEY_ProductCode + " = ?",
                new String[]{productCode});
        db.close();
    }

    public void DeleteAll() {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("delete from " + PRODUCT_TABLE_NAME);
            //String Message = "Record is deleted: ";
        } catch (SQLiteException ex) {

        }
    }
}
