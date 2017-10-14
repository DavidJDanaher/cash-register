package test.java;
import main.java.CashRegister;
import main.java.excpetions.InsufficientFundsException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Cash Register Unit Tests")
class CashRegisterTest {
    public CashRegister register;

    @BeforeEach
    void createNewRegister() {
      register = new CashRegister();
    }

    @Nested
    @DisplayName("Get Register Contents")
    class TestGetContents {
        @Test
        @DisplayName("Empty Register")
        void testGetContents() {
            assertEquals(createRegisterContents(0, 0, 0, 0, 0), register.getContents());
        }
    }

    @Nested
    @DisplayName("Add Contents To Register")
    class TestAddContentsToRegister {
        @Test
        @DisplayName("Added Contents")
        void testAddContentsToRegister() {
            register.addToContents(3, 5, 2, 1, 1);
            assertEquals(createRegisterContents(3, 5, 2, 1, 1), register.getContents());

            register.addToContents(2, 0, 1, 0, 3);
            assertEquals(createRegisterContents(5, 5, 3, 1, 4), register.getContents());
        }
    }


    @Nested
    @DisplayName("Remove Contents From Register")
    class TestRemoveContentsFromRegister {
        @Test
        @DisplayName("When there are sufficient bills")
        void testRemoveContentsFromRegister_Sufficient() throws InsufficientFundsException {
            register.addToContents(3, 4, 2, 2, 1);
            register.removeContents(2, 3, 2, 0, 1);

            assertEquals(createRegisterContents(1, 1, 0, 2, 0), register.getContents());
        }

        @Test
        @DisplayName("When there are insufficient $1 bills")
        void testRemoveContentsFromRegister_InsufficientOnes() {
            register.addToContents(3, 4, 2, 2, 1);


            try {
                register.removeContents(4, 3, 3, 0, 1);
            } catch (InsufficientFundsException e) {
                assertEquals("There are insufficient one dollar bills.", e.getMessage());
            }

            assertEquals(createRegisterContents(3, 4, 2, 2, 1), register.getContents());
        }

        @Test
        @DisplayName("When there are insufficient $2 Bills")
        void testRemoveContentsFromRegister_InsufficientTwos() {
            register.addToContents(3, 4, 2, 2, 1);


            try {
                register.removeContents(3, 5, 3, 0, 1);
            } catch (InsufficientFundsException e) {
                assertEquals("There are insufficient two dollar bills.", e.getMessage());
            }

            assertEquals(createRegisterContents(3, 4, 2, 2, 1), register.getContents());
        }

        @Test
        @DisplayName("When there are insufficient $5 Bills")
        void testRemoveContentsFromRegister_InsufficientFives() {
            register.addToContents(3, 4, 2, 2, 1);


            try {
                register.removeContents(3, 3, 4, 0, 1);
            } catch (InsufficientFundsException e) {
                assertEquals("There are insufficient five dollar bills.", e.getMessage());
            }

            assertEquals(createRegisterContents(3, 4, 2, 2, 1), register.getContents());
        }

        @Test
        @DisplayName("When there are insufficient $10 Bills")
        void testRemoveContentsFromRegister_InsufficientTens() {
            register.addToContents(3, 4, 2, 2, 1);


            try {
                register.removeContents(3, 3, 1, 5, 1);
            } catch (InsufficientFundsException e) {
                assertEquals("There are insufficient ten dollar bills.", e.getMessage());
            }

            assertEquals(createRegisterContents(3, 4, 2, 2, 1), register.getContents());
        }

        @Test
        @DisplayName("When there are insufficient $20 Bills")
        void testRemoveContentsFromRegister_InsufficientTwenties() {
            register.addToContents(3, 4, 2, 2, 0);


            try {
                register.removeContents(3, 3, 1, 1, 1);
            } catch (InsufficientFundsException e) {
                assertEquals("There are insufficient twenty dollar bills.", e.getMessage());
            }

            assertEquals(createRegisterContents(3, 4, 2, 2, 0), register.getContents());
        }
    }

    @Nested
    @DisplayName("Get Total Dollar Value")
    class TestGetTotalValue {
        @Test
        @DisplayName("Empty Register")
        void testGetTotalValue_empty() {
            assertEquals(0, register.getTotalValue());
        }

        @Test
        @DisplayName("Full Register")
        void testGetTotalValue_full() {
            register.addToContents(3, 2, 4, 3, 6);
            assertEquals(177, register.getTotalValue());
        }
    }


    private Map<String, Integer> createRegisterContents(int ones, int twos, int fives, int tens, int twenties) {
        Map<String, Integer> contents = new HashMap<>();

        contents.put("ONE", ones);
        contents.put("TWO", twos);
        contents.put("FIVE", fives);
        contents.put("TEN", tens);
        contents.put("TWENTY", twenties);

        return contents;
    }
}

