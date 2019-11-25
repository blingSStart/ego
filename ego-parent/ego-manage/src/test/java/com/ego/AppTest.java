package com.ego;

import static org.junit.Assert.assertTrue;

import com.ego.utils.IDUtils;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        String s = IDUtils.genImageName();
        System.out.println(s);
    }
}
