package com.adsapient.gui;

import com.adsapient.test.SpringTestCase;

import java.util.TreeMap;

public class ContextAwareGuiBeanTest extends SpringTestCase {
    ContextAwareGuiBean contextAwareGuiBean;

    protected void setUp() throws Exception {
        super.setUp();
        contextAwareGuiBean = (ContextAwareGuiBean) appContext.getBean("contextAwareGuiBean");
    }


}
