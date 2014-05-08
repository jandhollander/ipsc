package com.inspire.util.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class ImagePopup extends PopupPanel {

    private static ImagePopupUiBinder uiBinder = GWT.create(ImagePopupUiBinder.class);

    interface ImagePopupUiBinder extends UiBinder<Widget, ImagePopup> {
    }

    @UiField Image image;
    @UiField Label caption;
    @UiField Button previous;
    @UiField Button next;
    @UiField FocusPanel focus;
    
    private List<Entry> entries = new ArrayList<Entry>();
    private int selectedIndex;
    
    public ImagePopup() {
        setWidget(uiBinder.createAndBindUi(this));
        setAutoHideEnabled(true);
        setGlassEnabled(true);
        
        RootPanel.get().addDomHandler(new KeyUpHandler(){
            @Override
            public void onKeyUp(KeyUpEvent event) {
                ImagePopup.this.onKeyUp(event);
            }
        }, KeyUpEvent.getType());
    }
    
    public void setSelected(final Entry entry){
        setSelectedIndex(entries.indexOf(entry));
    }
    
    public void setSelectedIndex(int index) {
        index = Math.max(0, index);
        index = Math.min(index, entries.size()-1);
        if (selectedIndex != index){
            selectedIndex = index;
            updateDisplay();
        }
    }
    
    public void updateDisplay(){
        previous.setEnabled(selectedIndex > 0);
        next.setEnabled(selectedIndex < entries.size()-1);
        
        final Entry entry = getSelectedEntry();
        image.setSize("auto", "auto");
        image.getElement().getStyle().clearPadding();
        image.addLoadHandler(new LoadHandler() {
            @Override
            public void onLoad(LoadEvent event) {
                LoadHandler load = new PaddingImageResizeHandler(image, 300, 300);
                load.onLoad(event);
                center();
            }
        });
        image.setUrl(entry.getUrl());
        caption.setText(entry.getCaption());
    }

    public Entry getSelectedEntry(){
        return entries.get(selectedIndex);
    }
    
    public static interface Entry {
        String getUrl();
        String getCaption();
    }
    
    public static class DefaultEntry implements Entry {
        private String url;
        private String caption;
        public DefaultEntry(final String url, final String caption){
            this.url = url;
            this.caption = caption;
        }
        
        @Override
        public String getUrl() {
            return url;
        }

        @Override
        public String getCaption() {
            return caption;
        }
    }

    public void add(final Entry entry){
        entries.add(entry);
    }
    
    public boolean remove(final Entry entry){
        return entries.remove(entry);
    }
    
    @UiHandler("next")
    public void clickNext(ClickEvent event){
        next();
    }
    
    private void next(){
        setSelectedIndex(selectedIndex + 1);
    }
    
    @UiHandler("previous")
    public void clickPrevious(ClickEvent event){
        previous();
    }
    
    private void previous() {
        setSelectedIndex(selectedIndex - 1);
    }
    
    void onKeyUp(KeyUpEvent event) {
        final int keyCode = event.getNativeEvent().getKeyCode();
        if (keyCode == KeyCodes.KEY_RIGHT) {
            next();
        } else if (keyCode == KeyCodes.KEY_LEFT) {
            previous();
        }
    }
}
