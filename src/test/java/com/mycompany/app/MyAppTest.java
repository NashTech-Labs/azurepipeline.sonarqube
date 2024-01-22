package com.mycompany.app;

import org.junit.Test;
import static org.junit.Assert.*;

public class MyAppTest {
    @Test
    public void testMain() {
        MyApp.main(new String[]{});
        // Assuming MyApp has a method called "getMessage" that returns "Hello, world!"
        assertEquals("Hello, world!", MyApp.getMessage());
    }
}