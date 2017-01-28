package co.gersua.test.week4;

import java.util.ArrayList;
import java.util.List;

public class Test {

    public void doStuff() {
        List<A> aList1 = new ArrayList<>();
        List<A> aList2 = new ArrayList<A>();
        List<A> aList3 = new ArrayList<B>();

        List<? extends A> aList4 = new ArrayList<B>();
        aList4.add(new A());
        aList4.add(new B());

        List<? super A> aList5 = new ArrayList<B>();

        List<? super A> aList6 = new ArrayList<SuperA>();
        aList6.add(new A());
        aList6.add(new B());
        aList6.add(new SuperA());

        List<?> aList7 = new ArrayList<>();
        List<?> aList8 = new ArrayList<Object>();
        List<?> aList9 = new ArrayList<A>();
    }
}

class SuperA {}

class A extends SuperA {}

class B extends A {}
