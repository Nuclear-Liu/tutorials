package org.hui.nd4j;

import org.junit.jupiter.api.Test;
import org.nd4j.linalg.api.buffer.DataType;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

public class CreatingNDArrayTest {
    @Test
    void testZeroNDArray() {
        INDArray zeros = Nd4j.zeros(2);
        System.out.println(zeros);
    }
    @Test
    void testOneNDArray() {
        INDArray ones = Nd4j.ones(2);
        System.out.println(ones);
    }
    @Test
    void testScalarValueNDArray() {
        INDArray array = Nd4j.zeros(3, 4).add(5.6);
        System.out.println(array);
    }

    @Test
    void testRand() {
        INDArray array = Nd4j.rand(3, 4);
        System.out.println(array);
    }
    @Test
    void testRandn() {
        INDArray array = Nd4j.randn(3,4);
        System.out.println(array);
    }
    @Test
    void testSetRandSeed() {
        Nd4j.getRandom().setSeed(20);
        Nd4j.setDefaultDataTypes(DataType.DOUBLE, DataType.DOUBLE);
        INDArray array = Nd4j.randn(3,4);
        System.out.println(Nd4j.dataType());
        System.out.println(array);
    }

}
