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

import com.google.gwt.user.client.ui.Image;

/**
 * Resizes an image to the wanted dimensions, cropping the image to create a square
 * occupying area.
 * @author jan
 */
public class CroppingImageResizeHandler extends ImageResizeHandler {
    
    private boolean isResized;

    /**
     * @param image Image to resize
     * @param maxW maximum width
     * @param maxH maximum height
     */
    public CroppingImageResizeHandler(Image image, int maxW, int maxH) {
        super(image, maxW, maxH);
        isResized = false;
    }

    @Override
    protected void setNewSize(int newW, int newH) {
        if (!isResized){
            int posLeft = getDeltaW(newW) / 2;
            int posTop = getDeltaH(newH) / 2;

            // setVisibleRect triggers another onload, so don't allow the resize twice
            isResized = true;
            image.setVisibleRect(-posLeft, -posTop, (int)maxW, (int)maxH);
    
            super.setNewSize(maxW, maxH);
            image.getElement().getStyle().setProperty("backgroundSize", newW + "px " + newH + "px");
        }
    }

    @Override
    protected double getRatio(double ratioW, double ratioH) {
//        System.out.println("W:" + ratioW + " H:" + ratioH);
        return Math.max(ratioW, ratioH);
    }
}
