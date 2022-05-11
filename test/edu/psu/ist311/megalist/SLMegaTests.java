package edu.psu.ist311.megalist;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

public class SLMegaTests {

    @Test // this is in the class which will contain all of your unit tests
    public void testFundamentals1() {
        IMegaList<Integer> a = new SLMegaList<>();

        Assertions.assertEquals(0, a.leftLength());
        Assertions.assertEquals(0, a.rightLength());
        Assertions.assertEquals("<><>", a.toString());

        a.addToRightAtFront(4);
        Assertions.assertEquals(0, a.leftLength());
        Assertions.assertEquals(1, a.rightLength());
        Assertions.assertEquals("<><4>", a.toString());
    }
    @Test
    public void testAddToRight1() {
        IMegaList<Integer> a = new SLMegaList<>();
        a.addToRightAtFront(1);
    }
    @Test
    public void testAddToRight2() {
        IMegaList<Integer> a = new SLMegaList<>();
        a.addToRightAtFront(-23);
    }

    @Test
    public void testRemoveFromRightAtFront1() {
        IMegaList<Integer> a = new SLMegaList<>();
        //<><33>
        a.addToRightAtFront(33);
        //<><22, 33>
        a.addToRightAtFront(22);
        //<><11, 22, 33>
        a.addToRightAtFront(11);
        //<><22, 33>
        a.removeFromRightAtFront();
        //<><33>
        a.removeFromRightAtFront();
    }
    @Test
    public void testRemoveFromRightAtFront2() {
       IMegaList<Integer> a = new SLMegaList<>();
       a.removeFromRightAtFront();
        Assertions.assertThrows(NoSuchElementException.class,
                () -> a.removeFromRightAtFront());
        Assertions.assertNull(a.removeFromRightAtFront());

    }


      @Test
    public void testLeftLength() {
        IMegaList<Integer> a = new SLMegaList<>();
        //<><>
        a.addToRightAtFront(33);
        //<><33>
        a.addToRightAtFront(22);
        //<><22, 33>
        a.addToRightAtFront(11);
        //<><11, 22, 33>
        a.moveForward();
        //<11><22, 33>
        a.moveForward();
        //<11, 22><33>
        a.moveForward();
        //<11, 22, 33><>


        Assertions.assertEquals(3, a.leftLength());
        Assertions.assertEquals(0, a.rightLength());
    }
    @Test
    public void testRightLength() {
        IMegaList<Integer> a = new SLMegaList<>();
        //<><>
        a.addToRightAtFront(33);
        //<><33>
        a.addToRightAtFront(22);
        //<><22, 33>
        a.addToRightAtFront(11);
        //<><11, 22, 33>

        Assertions.assertEquals(3, a.rightLength());
        Assertions.assertEquals(0, a.leftLength());
    }
    @Test
    public void testMoveToVeryBeginning() {
        IMegaList<Integer> a = new SLMegaList<>();
        //<><>
        a.addToRightAtFront(33);
        //<><33>
        a.addToRightAtFront(22);
        //<><22, 33>
        a.addToRightAtFront(11);
        //<><11, 22, 33>
        a.moveForward();
        //<11><22, 33>
        a.moveForward();
        //<11, 22><33>
        a.moveForward();
        //<11, 22, 33><>
        a.moveToVeryBeginning();
        //<><11, 22, 33>


        Assertions.assertEquals(0, a.leftLength());
        Assertions.assertEquals(3, a.rightLength());
    }
    @Test
    public void testMoveForward() {
        IMegaList<Integer> a = new SLMegaList<>();
        //<><>
        a.addToRightAtFront(33);
        //<><33>
        a.addToRightAtFront(22);
        //<><22, 33>
        a.addToRightAtFront(11);
        //<><11, 22, 33>
        a.moveForward();
        //<11><22, 33>
        a.moveForward();
        //<11, 22><33>
        a.moveForward();
        //<11, 22, 33><>

        Assertions.assertEquals(3, a.leftLength());
        Assertions.assertEquals(0, a.rightLength());
    }
    @Test
    public void testMoveForward2() {
        IMegaList<Integer> a = new SLMegaList<>();;
        a.moveForward();
        Assertions.assertThrows(NoSuchElementException.class,
                () -> a.moveForward());
    }
    @Test
    public void testClear() {
        IMegaList<Integer> a = new SLMegaList<>();;
        a.addToRightAtFront(1);
        a.moveForward();
        a.addToRightAtFront(2);
        a.clear();

        Assertions.assertEquals(0, a.rightLength());
        Assertions.assertEquals(0, a.leftLength());


    }



}