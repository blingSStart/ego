package com.ego;

import static org.junit.Assert.assertTrue;

import com.ego.vo.VsftdpFile;
import org.junit.Test;

import java.util.Arrays;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        int[] a = {1,2,3,4,5};
        for (int i = 0; i < a.length; i++) {
            if (a[i] == 1) {
                a[i] = 2;
            }
        }
        System.out.println(Arrays.toString(a));
    }
}
