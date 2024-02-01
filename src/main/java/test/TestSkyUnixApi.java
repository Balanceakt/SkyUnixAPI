package test;

import api.SkyUnixHandleArgs;

import java.util.Arrays;
import java.util.List;

public class TestSkyUnixApi {

    private static SkyUnixHandleArgs argsTest = new SkyUnixHandleArgs();
    public static SkyUnixHandleArgs getArgsTest() {
        System.out.println( argsTest.readAllArgsAtIndex("skyblock", "test", 2));
        return argsTest;
    }

    public static void main(String[] args) {
        SkyUnixHandleArgs result = getArgsTest();
        System.out.println("SkyUnixAPI instance created: " + result);
    }
}
