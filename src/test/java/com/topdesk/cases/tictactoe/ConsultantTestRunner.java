package com.topdesk.cases.tictactoe;

import org.junit.runner.Result;
import org.junit.runner.JUnitCore;
import org.junit.runner.notification.Failure;
import com.topdesk.cases.tictactoe.ConsultantTest;

public class ConsultantTestRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(ConsultantTest.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
    }
}
