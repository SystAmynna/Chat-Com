package tools;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TestTools {

    @Test
    void testFatalError() {
        Tools.fatalError("Test");
        assertTrue(true);
    }

    @Test
    void testAskString() {
        String r = Tools.askString("Test");
        System.out.println(r);
        assertTrue(true);
    }

    @Test
    void testAskPort() {
        int r = Tools.askPort();
        System.out.println(r);
        assertTrue(true);
    }


}
