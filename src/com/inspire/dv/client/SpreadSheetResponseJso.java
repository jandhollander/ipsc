package com.inspire.dv.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsDate;

public final class SpreadSheetResponseJso extends JavaScriptObject {

    protected SpreadSheetResponseJso() {
    }

    public static final class Column extends JavaScriptObject {
        protected Column() {
        }

        public native String getId() /*-{
            return this.id;
        }-*/;

        public native String getLabel() /*-{
            return this.label;
        }-*/;

        public native String getType() /*-{
            return this.type;
        }-*/;

        public native String getPattern() /*-{
            return this.pattern;
        }-*/;

    }

    public static final class Row extends JavaScriptObject {
        protected Row() {}
        
        public String getString(final int colIndex) {
            Object o = getObject(colIndex);
            return o==null ? null : (String)o;
        }
        
        public String getUrl(final int colIndex) {
            final String string = getString(colIndex);
            if (!string.startsWith("http")){
                return "http://" + string;
            } else {
                return string;
            }
        }
        
        public int getInt(final int colIndex) {
            int result = 0;
            try {
                result = (int)getNumber(colIndex);
            } catch (Exception e){}
            return result;
        }
        
        public JsDate getDate(final int colIndex){
            Object o = getObject(colIndex);
            return o==null ? null : (JsDate)o;
        }

        public double getDouble(int colIndex) {
            double result = 0.0;
            try {
                result = getNumber(colIndex);
            } catch (Exception e){}
            return result;
        }
        
        private native double getNumber(final int colIndex)/*-{
            return this.c[colIndex].v;
        }-*/;
        
        private native Object getObject(final int colIndex)/*-{
            try {
                return this.c[colIndex].v;
            } catch (e){
                console.log(e);
            }
            return null;
        }-*/;
    }

    public native JsArray<Column> getColumns() /*-{
        return this.table.cols;
    }-*/;

    public native JsArray<Row> getRows() /*-{
        return this.table.rows;
    }-*/;

    public native String getVersion() /*-{
        return this.version;
    }-*/;

    public native String getStatus() /*-{
        return this.status;
    }-*/;
}