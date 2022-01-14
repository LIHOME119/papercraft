package com.example.papercraft;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class shopmathTest extends InstrumentationRegistry {

    @Before
    public void test_add123equalstotal() throws Exception{
        ShopPage.defaltnum1 = 2;
        ShopPage.defaltnum2 = 2;
        ShopPage.defaltnum3 = 2;
        ShopPage.totalnum=2822;

        int answer = ShopPage.defaltnum1 * 128 + ShopPage.defaltnum2 * 300 + ShopPage.defaltnum3 *888;


    }

    @Test
    public void texttotal()throws Exception{
        Assert(ShopPage.totalnum, answer);
    }






}
