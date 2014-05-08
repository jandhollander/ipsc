package com.inspire.dv.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.CurrencyList;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.jsonp.client.JsonpRequest;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.inspire.dv.client.SpreadSheetResponseJso.Column;
import com.inspire.dv.client.SpreadSheetResponseJso.Row;
import com.inspire.util.client.ImagePopup;
import com.inspire.util.client.ImagePopup.Entry;
import com.inspire.util.client.ProgressBar;

public class MainDisplay extends Composite {

    private static MainDisplayUiBinder uiBinder = GWT.create(MainDisplayUiBinder.class);

    interface MainDisplayUiBinder extends UiBinder<Widget, MainDisplay> {
    }

    NumberFormat numberFormat = NumberFormat.getCurrencyFormat(CurrencyList.get().lookup("EUR"));
    
    private static final String DATA_URL = 
            "https://spreadsheets.google.com/tq?key=0ApKQEDJBJqIRdEN6YmhXd192Q1JBVlpqT0tTZ1RlUEE&gid=0";

    @UiField ComplexPanel panel;
    @UiField ProgressBar progress;
    @UiField Label spent;
    @UiField Label toSpend;
    ImagePopup popup = new ImagePopup();
    
    public MainDisplay() {
        initWidget(uiBinder.createAndBindUi(this));
        
        JsonpRequestBuilder builder = new JsonpRequestBuilder();
        JsonpRequest<SpreadSheetResponseJso> jsonp = 
                builder.requestObject(DATA_URL, new AsyncCallback<SpreadSheetResponseJso>() {

            @Override
            public void onFailure(Throwable caught) {
                GWT.log(caught.getMessage(), caught);
            }

            @Override
            public void onSuccess(SpreadSheetResponseJso result) {
                JsArray<Column> cols = result.getColumns();
                ProductRow.setColumns(cols);
                JsArray<Row> rows = result.getRows();
                double totalSpent = 0.0;
                double totalToSpend = 0.0;
                for(int i=0 ; i<rows.length() ; i++){
                    Row row = rows.get(i);
                    ProductRow prow = new ProductRow(row);
                    totalSpent += prow.getSpent();
                    totalToSpend += prow.getToSpend();
                    
                    ProductBlock display = new ProductBlock();
                    display.setProductRow(prow);
                    panel.add(display);
                    
                    final Entry entry = new ImagePopup.DefaultEntry(prow.getImageUrl(), prow.getName());
                    popup.add(entry);
                    display.addClickHandler(new ClickHandler() {
                        @Override
                        public void onClick(ClickEvent event) {
                            popup.setSelected(entry);
                            popup.center();
                        }
                    });
                }
                progress.setMax((int)totalToSpend);
                progress.setValue((int)totalSpent);
                
                spent.setText("Total: " + numberFormat.format(totalSpent));
                toSpend.setText("Planned: " + numberFormat.format(totalToSpend));
            }
        });
        
        registerGoogleVisualizationAPICallbackMethod();
    }

    private static native void registerGoogleVisualizationAPICallbackMethod() /*-{
        $wnd.google = new Object();
    
        var google = $wnd.google;
    
        google.visualization = new Object();
        var query = google.visualization.Query = new Object();
        var counter = $wnd['__gwt_jsonp__']['__gwt_jsonp_counter__']-1;
        
        query.setResponse = function(param) {
              $wnd['__gwt_jsonp__']['P'+counter].onSuccess(param);
        };
    }-*/;
}
