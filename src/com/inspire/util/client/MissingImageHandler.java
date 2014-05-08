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

import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Image;

public class MissingImageHandler implements ErrorHandler {

    private Image image;
    private ImageResource replacement;

    public MissingImageHandler(Image image, ImageResource replacement) {
        this.image = image;
        this.replacement = replacement;
    }

    @Override
    public void onError(ErrorEvent event) {
        image.setUrl(replacement.getSafeUri());
    }
}
