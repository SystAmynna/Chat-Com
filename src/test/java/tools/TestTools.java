package tools;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TestTools {

    @Test
    void testFatalError() {
        Tools.fatalError("Test");
        assertTrue(true);
    }


}
