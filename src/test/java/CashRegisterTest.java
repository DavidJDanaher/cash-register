package test.java;
import main.java.CashRegister;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Cash Register Unit Tests")
class CashRegisterTest {
    public CashRegister register = new CashRegister();

    @Test
    @DisplayName("My setup test")
    void testHello() {
        assertEquals("hello", register.getContents());
    }


}

