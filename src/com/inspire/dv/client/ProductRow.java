package com.inspire.dv.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsDate;
import com.inspire.dv.client.SpreadSheetResponseJso.Column;
import com.inspire.dv.client.SpreadSheetResponseJso.Row;

public class ProductRow {
    
    private static List<String> colIndex = null;

    public static void setColumns(final JsArray<Column> cols){
        colIndex = new ArrayList<String>();
        for (int i=0 ; i<cols.length() ; i++){
            Column col = cols.get(i);
            colIndex.add(cleanLabel(col.getLabel()));
        }
    }
    
    private static String cleanLabel(final String label){
        String result = label.trim().toLowerCase();
        result = result.replaceAll("[^a-z]*", "");
//        GWT.log(label + " --> " + result);
        return result;
    }
    
    private static final int getIndex(final String col){
        if (colIndex == null){
            throw new IllegalStateException("Call setColumns before getting values");
        }
        return colIndex.indexOf(col);
    }
    
    private static final String NAME = cleanLabel("product name");
    private static final String IMAGE = cleanLabel("product image");
    private static final String CURRENCY = cleanLabel("currency");
    private static final String BOUGHT = cleanLabel("bought");
    private static final String WANTED = cleanLabel("wanted");
    private static final String SHOPNAME = cleanLabel("shop name");
    private static final String SHOPURL = cleanLabel("shop url");
    private static final String PRICE = cleanLabel("price");
    private static final String SPENT = cleanLabel("spent");
    private static final String DATE = cleanLabel("date");
    private static final String TO_SPEND = cleanLabel("to spend");
    
    private final Row row;

    public ProductRow(Row row) {
        this.row = row;
    }
    
    private int getInt(final String name){
        return row.getInt(getIndex(name));
    }
    
    private String getString(final String name){
        return row.getString(getIndex(name));
    }
    
    private String getUrl(final String name){
        return row.getUrl(getIndex(name));
    }
    
    private double getDouble(final String name){
        return row.getDouble(getIndex(name));
    }
    
    private Date getDate(final String name){
        JsDate jsd = row.getDate(getIndex(name));
        return jsd == null ? null : new Date((long) jsd.getTime());
    }
    
    public String getName(){
        return getString(NAME);
    }
    
    public String getImageUrl(){
        return getUrl(IMAGE);
    }
    
    public String getCurrency(){
        return getString(CURRENCY);
    }
    
    public int getNrBought(){
        return getInt(BOUGHT);
    }
    
    public int getNrWanted(){
        return getInt(WANTED);
    }
    
    public String getShopName(){
        return getString(SHOPNAME);
    }
    
    public String getShopUrl(){
        return getUrl(SHOPURL);
    }
    
    public double getPrice(){
        return getDouble(PRICE);
    }
    
    public double getSpent(){
        return getDouble(SPENT);
    }

    public Date getDate() {
        return getDate(DATE);
    }

    public double getToSpend() {
        return getDouble(TO_SPEND);
    }
}
