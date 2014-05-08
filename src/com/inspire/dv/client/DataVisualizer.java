package com.inspire.dv.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootLayoutPanel;

public class DataVisualizer implements EntryPoint {
    @Override
    public void onModuleLoad() {
        final MainDisplay panel = new MainDisplay();
        RootLayoutPanel.get().add(panel);
    }
}
