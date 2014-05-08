/*
 * Copyright Chapoo NV
 * Bellevue 5/202, 9050 Gent, Belgium
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Chapoo NV. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you
 * entered into with Chapoo NV.
 */
package com.inspire.util.client;

import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.user.client.ui.Image;

/**
 * Resizes an image to the wanted dimensions
 * @author jan
 */
public abstract class ImageResizeHandler implements LoadHandler {
    public enum Type {
        SCALED,
        PADDING,
        CROPPING
    }
    
    protected final int maxW;
    protected final int maxH;
    protected final Image image;

    /**
     * @param image Image to resize
     * @param maxW maximum width
     * @param maxH maximum height
     */
    public ImageResizeHandler(final Image image, final int maxW, final int maxH){
        this.image = image;
        this.maxW = maxW;
        this.maxH = maxH;
    }
    
    public final void onLoad(LoadEvent event) {
        int w = image.getWidth();
        int h = image.getHeight();
        
        double ratioW = maxW / (w * 1.0);
        double ratioH = maxH / (h * 1.0);
        
        double ratio = getRatio(ratioW, ratioH);
        
        int newW = (int)Math.floor(ratio*w);
        int newH = (int)Math.floor(ratio*h);
        setNewSize(newW, newH);
    }
    
    protected abstract double getRatio(final double ratioW, final double ratioH);
    
    protected void setNewSize(final int newW, final int newH){
        image.setPixelSize(newW, newH);
    }
    
    protected final int getDeltaW(final int newW){
        return (int)Math.floor(maxW - newW);
    }

    protected final int getDeltaH(final int newH){
        return (int)Math.floor(maxH - newH);
    }
}
