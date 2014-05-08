package com.inspire.dv.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.i18n.client.CurrencyList;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.i18n.shared.DateTimeFormat.PredefinedFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.inspire.util.client.PaddingImageResizeHandler;
;

public class ProductBlock extends Composite implements HasClickHandlers{

    private static final int IMAGE_SIZE = 100;
    private static final DateTimeFormat DATEFORMAT = 
            DateTimeFormat.getFormat(PredefinedFormat.DATE_MEDIUM);

    private static ProductBlockUiBinder uiBinder = GWT.create(ProductBlockUiBinder.class);

    interface ProductBlockUiBinder extends UiBinder<Widget, ProductBlock> {
    }

    private static final NumberFormat euroFormat = getCurrencyFormat("EUR");
    private static final NumberFormat getCurrencyFormat(final String code){
        return NumberFormat.getCurrencyFormat(CurrencyList.get().lookup(code));
    }
    
    @UiField FocusPanel focus;
    @UiField Label name;
    @UiField Image image;
    @UiField Panel body;
    @UiField Anchor shop;
    @UiField Label price;
    @UiField Widget stamp;
    @UiField Label date;
    
    public ProductBlock() {
        initWidget(uiBinder.createAndBindUi(this));
        image.addLoadHandler(new PaddingImageResizeHandler(image, IMAGE_SIZE, IMAGE_SIZE));
    }

    public void setProductRow(ProductRow row){
        name.setText(row.getName());
        name.setTitle(row.getName());
        image.setUrl(row.getImageUrl());
        shop.setText(row.getShopName());
        shop.setTarget("_blank");
        shop.setHref(row.getShopUrl());
        price.setText(getCurrencyFormat(row.getCurrency()).format(row.getPrice()));
        stamp.setVisible(row.getNrBought() < row.getNrWanted());
        if (row.getDate() != null){
            date.setText(DATEFORMAT.format(row.getDate()));
        } else {
            date.setVisible(false);
        }
        body.add(new Label("Bought " + row.getNrBought() + "/" + row.getNrWanted()));
        if (row.getSpent() >= 1){
            body.add(new Label("Spent " + euroFormat.format(row.getSpent())));
        }
    }

    @Override
    public HandlerRegistration addClickHandler(ClickHandler handler) {
        return focus.addClickHandler(handler);
    }
}
