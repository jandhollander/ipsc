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

public class ScaledImageResizeHandler extends ImageResizeHandler {

    public ScaledImageResizeHandler(Image image, int maxW, int maxH) {
        super(image, maxW, maxH);
    }

    @Override
    protected double getRatio(double ratioW, double ratioH) {
        return Math.min(ratioW, ratioH);
    }
}
