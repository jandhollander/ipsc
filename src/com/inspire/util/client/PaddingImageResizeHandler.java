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

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Image;

/**
 * Resizes an image to the wanted dimensions, adding padding to create a square
 * occupying area.
 * @author jan
 */
public class PaddingImageResizeHandler extends ScaledImageResizeHandler {

    /**
     * @param image Image to resize
     * @param maxW maximum width
     * @param maxH maximum height
     */
    public PaddingImageResizeHandler(Image image, int maxW, int maxH) {
        super(image, maxW, maxH);
    }

    @Override
    protected void setNewSize(final int newW, final int newH){
        super.setNewSize(newW, newH);
        
        double padX = getDeltaW(newW) / 2;
        double padY = getDeltaH(newH) / 2;
        if (padX > 0){
            image.getElement().getStyle().setPaddingLeft(padX, Unit.PX);
            image.getElement().getStyle().setPaddingRight(padX, Unit.PX);
        }
        if (padY > 0){
            image.getElement().getStyle().setPaddingTop(padY, Unit.PX);
            image.getElement().getStyle().setPaddingBottom(padY, Unit.PX);
        }
    }
//
//    @Override
//    protected double getRatio(double ratioW, double ratioH) {
//        return Math.min(ratioW, ratioH);
//    }
}
