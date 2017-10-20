package test.java.unit;

import main.java.RegisterIO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito.*;

import java.util.Scanner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

//@DisplayName("Register I/O");
class RegisterIOTest {
    RegisterIO io = new RegisterIO();
    Scanner mockScanner =  mock(Scanner.class);

    @Test
    void test() {
        when(mockScanner.nextLine()).thenReturn("This is a test");

        
    }
}
