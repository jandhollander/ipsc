package com.inspire.util.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class ProgressBar extends Composite {

    private static final String MAX = "max";
    private static final String VALUE = "value";
    private static final String POSITION = "position";
    private static ProgressBarUiBinder uiBinder = GWT.create(ProgressBarUiBinder.class);

    interface ProgressBarUiBinder extends UiBinder<Widget, ProgressBar> {
    }

    @UiField Element progress;
    @UiField Element label;
    
    public ProgressBar() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    public double getMax(){
        return Double.parseDouble(progress.getAttribute(MAX));
    }
    
    public void setMax(final double value) {
        progress.setAttribute(MAX, Double.toString(value));
    }

    public double getValue(){
        return Double.parseDouble(progress.getAttribute(VALUE));
    }
    
    public void setValue(final double value) {
        progress.setAttribute(VALUE, Double.toString(value));
        label.setInnerText(""+getPosition());
    }
    
    public double getPosition(){
        return getValue() / getMax();
    }
}
