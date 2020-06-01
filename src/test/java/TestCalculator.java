import domain.entity.Calculator;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import java.util.Iterator;

public class TestCalculator {
    Calculator mCalculator = mock(Calculator.class);

    @Test
    public void testCalcAdd() {
        // with user of when
        when(mCalculator.add(10.0, 20.0)).thenReturn(30.0);
        assertEquals(mCalculator.add(10.0, 20.0), 30.0, 0);
        verify(mCalculator).add(10.0, 20.0);

        //with use of doReturn
        doReturn(15.0).when(mCalculator).add(10.0, 5.0);
        assertEquals(mCalculator.add(10.0, 5.0), 15.0, 0);
        verify(mCalculator).add(10.0, 5.0);
    }

    @Test
    public void testCallMethod() {
        when(mCalculator.subtract(15.0, 25.0)).thenReturn(10.0);
        when(mCalculator.subtract(35.0, 25.0)).thenReturn(-10.0);
        when(mCalculator.divide(20.0, 10.0)).thenReturn(2.0);

        assertEquals(mCalculator.subtract(15.0, 25.0), 10.0, 0);
        assertEquals(mCalculator.subtract(15.0, 25.0), 10.0, 0);
        assertEquals(mCalculator.subtract(35.0, 25.0), -10.0, 0);
//        assertEquals(mCalculator.divide(20.0, 10.0), 2.0, 0);

        verify(mCalculator, atLeastOnce()).subtract(35.0, 25.0);
//        verify(mCalculator, atLeast(2)).subtract(15.0, 25.0);

        verify(mCalculator, times(2)).subtract(15.0, 25.0);
        //check never
//        verify(mCalculator, never()).divide(20.0, 10.0);

        // check at most
//        verify(mCalculator, atMost(1)).subtract(15.0, 25.0);
    }

    @Test
    public void testDivide() {
        when(mCalculator.divide(15.0, 3.0)).thenReturn(5.0);
        assertEquals(mCalculator.divide(15.0, 3.0), 5.0, 0);
        verify(mCalculator).divide(15.0, 3.0);

        /* test division by zero
        RuntimeException exception = new RuntimeException("Division by zero");
        doThrow(exception).when(mCalculator).divide(15.0, 0);
        assertEquals(mCalculator.divide(15.0, 0), 0, 0);
        verify(mCalculator).divide(15.0, 0);
         */
    }

    private Answer<Double> answer = new Answer<>() {

        @Override
        public Double answer(InvocationOnMock invocation) throws Throwable {
            Object mock = invocation.getMock();
            System.out.println("mock object: " + mock.toString());
            Object[] args = invocation.getArguments();
            double o1 = (double) args[0];
            double o2 = (double) args[1];
            double o3 = o1 + o2;
            System.out.println("" + o1 + " + " + o2);
            return o3;
        }
    };

    @Test
    public void testThenAnswer() {
        when(mCalculator.add(11.0, 12.0)).thenAnswer(answer);
        assertEquals(mCalculator.add(11.0, 12.0), 23.0, 0);
    }

    //test spy on Calculator
    @Test
    public void testSpy() {
        Calculator sCalculator = spy(new Calculator());
        when(sCalculator.double15()).thenReturn(23.0);
        sCalculator.double15();
        verify(sCalculator).double15();
        assertEquals(23.0, sCalculator.double15(), 0);
        verify(sCalculator, atLeast(2)).double15();
    }

    //test with timeout
    @Test
    public void testTimeOut() {
        when(mCalculator.add(11.0, 12.0)).thenReturn(23.0);
        assertEquals(mCalculator.add(11.0, 12.0), 23.0, 0);
        verify(mCalculator, timeout(100)).add(11.0, 12.0);
    }

    //test with exists Java classes
    @Test
    public void testJavaClasses() {
        Iterator<String> mis = mock(Iterator.class);
        when(mis.next()).thenReturn("Hello").thenReturn("Mockito");
        String result = mis.next() + ", " + mis.next();
        assertEquals("Hello, Mockito", result);

        Comparable<String> mcs = mock(Comparable.class);
        when(mcs.compareTo("Mockito")).thenReturn(1);
        assertEquals(1, mcs.compareTo("Mockito"));

        Comparable<Integer> mci = mock(Comparable.class);
        when(mci.compareTo(anyInt())).thenReturn(1);
        assertEquals(1, mci.compareTo(5));
    }


}
