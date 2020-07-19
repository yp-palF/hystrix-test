package org.test;

public class Main {
    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 100; i ++) {
            Boolean raiseException = null;
            if (i < 25) raiseException = false;
            else raiseException = true;
            System.out.println(new Integer(i).toString() + " " + new HystrixService(raiseException).execute());
            Thread.sleep(100);
        }
    }
}
