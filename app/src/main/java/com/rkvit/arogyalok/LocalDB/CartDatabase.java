package com.rkvit.arogyalok.LocalDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.rkvit.arogyalok.Model.CartItemModel;

import java.util.ArrayList;
import java.util.List;

public class CartDatabase extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Arogyalok.db";
    public static final int DATABASE_VERSION = 2;
    public static final String PRODUCT_TABLE_NAME = "product";
    public static final String KEY_OrderID = "_id";
        public static final String KEY_ProductCode = "ProductCode";
//    public static final String KEY_ProductName = "ProductName";
//    public static final String KEY_imageUrl = "imageUrl";
//    public static final String KEY_Main_Price = "MainPrice";
//    public static final String KEY_Spcl_Price = "SpclPrice";
//    public static final String KEY_Qty = "quantity";
//    public static final String KEY_WEIGHT = "weight";

    private static final String id = "ID";
    private static final String brandid ="BrandId";
    private static final String catId = "CatId";
    private static final String subcatId = "SubCatId";
    private static final String name = "Name";
    private static final String marId = "MarId";
    private static final String paId = "PaId";
    private static final String refundPolicy = "RefundPolicy";
    private static final String prescription = "Prescription";
    private static final String image = "Image";
    private static final String mrp = "MRP";
    private static final String saleMrp = "SalesMrp";
    private static final String description = "Description";
    private static final String catName = "CatName";
    private static final String subCatName = "SubCatName";
    private static final String  Quantity ="Qty";

    ArrayList<CartItemModel> list;
    private ArrayList<CartItemModel> MyCartItems = new ArrayList<>();

    public CartDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + PRODUCT_TABLE_NAME + "(" +
                KEY_OrderID + " INTEGER , " +
                KEY_ProductCode+ " INTEGER , " +
                id + " INTEGER , " +
                brandid + " INTEGER , " +
                catId + " INTEGER , " +
                subcatId + " INTEGER , " +
                name + " TEXT, " +
                marId + " INTEGER, " +
                paId + " INTEGER, " +
                refundPolicy + " TEXT, " +
                prescription + " TEXT, " +
                description + " TEXT, " +
                catName + " TEXT, " +
                subCatName + " TEXT, " +
                image + " TEXT, " +
                Quantity + " INTEGER, " +
                mrp + " DOUBLE, " +
                saleMrp + " DOUBLE) ";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PRODUCT_TABLE_NAME);
        onCreate(db);
    }

    public void insertRecord(CartItemModel myCartItems) {
        SQLiteDatabase db = this.getWritableDatabase();


        Integer ItemCode = null;
        Integer Qty = null;
        String query = "SELECT * FROM product WHERE ProductCode = " + myCartItems.getId();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            //name = cursor.getString(column_index);//to get other values
            ItemCode = cursor.getInt(1);//to get id, 0 is the column index
//            Qty = cursor.getInt(16);
            Log.d("ProductID", String.valueOf(ItemCode));
        }

        if (ItemCode == null) {
            ContentValues values = new ContentValues();

            values.put(KEY_OrderID, myCartItems.getId());
            values.put(KEY_ProductCode, myCartItems.getId());
            values.put(id, myCartItems.getId());
            values.put(brandid, myCartItems.getBrandid());
            values.put(catId, myCartItems.getCatId());
            values.put(subcatId, myCartItems.getSubcatId());
            values.put(name, myCartItems.getName());
            values.put(marId, myCartItems.getMarId());
            values.put(paId, myCartItems.getPaId());
            values.put(refundPolicy, myCartItems.getRefundPolicy());
            values.put(prescription, myCartItems.getPrescription());
            values.put(image, myCartItems.getImage());
            values.put(mrp, myCartItems.getMrp());
            values.put(saleMrp, myCartItems.getSaleMrp());
            values.put(description, myCartItems.getDescription());
            values.put(catName, myCartItems.getCatName());
            values.put(subCatName, myCartItems.getSubCatName());
            values.put(Quantity, myCartItems.getQuantity());
            db.insert(PRODUCT_TABLE_NAME, null, values);

            db.close(); // Closing database connection
        } else {


            ContentValues values = new ContentValues();

            String strSQL = " UPDATE product SET Qty =" + myCartItems.getQuantity() + " WHERE ProductCode =" + String.valueOf(ItemCode);
            db.execSQL(strSQL);

        }
    }

    public int getItemCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        int count = (int) DatabaseUtils.queryNumEntries(db, PRODUCT_TABLE_NAME);
        db.close();
        return count;
    }

    public ArrayList<CartItemModel> getAllProduct() {
        List<CartItemModel> Cartitem = new ArrayList<CartItemModel>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + PRODUCT_TABLE_NAME;
        //String selectQuery = "SELECT * FROM " + PRODUCT_TABLE_NAME + " ORDER BY "+KEY_OrderID+" DESC LIMIT 1";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                CartItemModel myCartItem = new CartItemModel();

                myCartItem.setId(cursor.getString(2));
                myCartItem.setName(cursor.getString(6));
                myCartItem.setImage(cursor.getString(14));
                myCartItem.setMrp(String.valueOf((cursor.getInt(16))));
                myCartItem.setSaleMrp(String.valueOf((cursor.getInt(17))));
                myCartItem.setQuantity(String.valueOf((cursor.getInt(15))));

                // Adding contact to list
                MyCartItems.add(myCartItem);
            } while (cursor.moveToNext());
        }
        // close inserting data from database
        db.close();
        // return contact list
        return MyCartItems;

    }


//    public void UpdateItem(String productCode, String quantity) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        Integer qty = null;
//        String countQuery = "SELECT * FROM product WHERE ProductCode = " + productCode;
//        Cursor cursor = null;
//        cursor = db.rawQuery(countQuery, null);
//        if (cursor.moveToFirst()) {
//            //name = cursor.getString(column_index);//to get other values
//            qty = cursor.getInt(6);
//        }
//        if (qty != null) {
//            if (qty > 1) {
//                String strSQL = "UPDATE product SET quantity = " + quantity + " WHERE ProductCode = " + productCode;
//                db.execSQL(strSQL);
//                Log.d("RowDelete", "Updated");
//                cursor.close();
//            } else {
//                db.delete(PRODUCT_TABLE_NAME, KEY_ProductCode + "=" + productCode, null);
//                Log.d("RowDelete", "Deleted");
//                cursor.close();
//            }
//        }
//    }

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
