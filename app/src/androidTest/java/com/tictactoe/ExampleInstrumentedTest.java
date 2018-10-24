package com.tictactoe;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private  int ordinal = 1;
    MatrixProvider matrixProvider;
    MatrixValidator matrixValidator;

    @Before
    public void before(){

        System.out.println("before()");
        matrixProvider = new MatrixProvider();
        matrixValidator = new MatrixValidator();
    }

    @Test
    public void checkWinsWithMatrixClean(){
        boolean expresult = false;
        short[][] testMatrix = matrixProvider.getCleanMatrix();
        boolean result = matrixValidator.validMatrix(testMatrix,ordinal);
        assertEquals(expresult,result);
    }

    @Test
    public void checkWinsWithColumnOneChecked(){
        boolean expresult = true;
        short[][] testMatrix = matrixProvider.getColumnOneCheckedMatrix();
        boolean result = matrixValidator.validMatrix(testMatrix,ordinal);
        assertEquals(expresult,result);
    }

    @Test
    public void checkWinsWithColumnTwoChecked(){
        boolean expresult = true;
        short[][] testMatrix = matrixProvider.getColumnTwoCheckedMatrix();
        boolean result = matrixValidator.validMatrix(testMatrix,ordinal);
        assertEquals(expresult,result);
    }

    @Test
    public void checkWinsWithColumnThreeChecked(){
        boolean expresult = true;
        short[][] testMatrix = matrixProvider.getColumnThreeCheckedMatrix();
        boolean result = matrixValidator.validMatrix(testMatrix,ordinal);
        assertEquals(expresult,result);
    }

    @Test
    public void checkWinsWithRowOneChecked(){
        boolean expresult = true;
        short[][] testMatrix = matrixProvider.getRowOneCheckedMatrix();
        boolean result = matrixValidator.validMatrix(testMatrix,ordinal);
        assertEquals(expresult,result);
    }

    @Test
    public void checkWinsWithRowTwoChecked(){
        boolean expresult = true;
        short[][] testMatrix = matrixProvider.getRowTwoCheckedMatrix();
        boolean result = matrixValidator.validMatrix(testMatrix,ordinal);
        assertEquals(expresult,result);
    }

    @Test
    public void checkWinsWithRowThreeChecked(){
        boolean expresult = true;
        short[][] testMatrix = matrixProvider.getRowThreeCheckedMatrix();
        boolean result = matrixValidator.validMatrix(testMatrix,ordinal);
        assertEquals(expresult,result);
    }
}
