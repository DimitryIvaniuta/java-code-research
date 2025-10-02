package com.code.research.json2csv.pkg1;

import java.util.HashMap;
import java.util.Map;

public class TestCl {
    protected int a1 = 2;
    private int a2 = 2;
    int a3 = 2;
    public int a4 = 2;
    public volatile Map am = new HashMap();

    public void p1() {
        synchronized (am) {
            a4 = 1;
        }
    }

    private void p0() {

    }


    void p2() {

    }

    protected void p3() {

    }
}
