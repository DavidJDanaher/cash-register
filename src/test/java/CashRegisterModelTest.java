package test.java;
import main.java.CashRegisterModel;
import main.java.exceptions.InsufficientFundsException;

import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Cash Register Unit Tests")
class CashRegisterModelTest {
    public CashRegisterModel register;

    @BeforeEach
    void createNewRegister() {
      register = new CashRegisterModel();
    }

    @Nested
    @DisplayName("Get Register Contents")
    class TestGetContents {
        @Test
        @DisplayName("Empty Register")
        void testGetContents() {
            assertEquals(createCurrencyMap(0, 0, 0, 0, 0), register.getContents());
        }
    }

    @Nested
    @DisplayName("Add Contents To Register")
    class TestAddContentsToRegister {
        @Test
        @DisplayName("Added Contents")
        void testAddContentsToRegister() {
            register.deposit(createCurrencyMap(3, 5, 2, 1, 1));
            assertEquals(createCurrencyMap(3, 5, 2, 1, 1), register.getContents());

            register.deposit(createCurrencyMap(2, 0, 1, 0, 3));
            assertEquals(createCurrencyMap(5, 5, 3, 1, 4), register.getContents());
        }
    }


    @Nested
    @DisplayName("Remove Contents From Register")
    class TestRemoveContentsFromRegister {
        @Test
        @DisplayName("When there are sufficient bills")
        void testRemoveContentsFromRegister_Sufficient() throws InsufficientFundsException {
            register.deposit(createCurrencyMap(3, 4, 2, 2, 1));
            register.withdraw(createCurrencyMap(2, 3, 2, 0, 1));

            assertEquals(createCurrencyMap(1, 1, 0, 2, 0), register.getContents());
        }

        @Test
        @DisplayName("When there are insufficient $1 bills")
        void testRemoveContentsFromRegister_InsufficientOnes() {
            register.deposit(createCurrencyMap(3, 4, 2, 2, 1));


            try {
                register.withdraw(createCurrencyMap(4, 3, 3, 0, 1));
            } catch (InsufficientFundsException e) {
                assertEquals(new InsufficientFundsException("change").getMessage(), e.getMessage());
            }

            assertEquals(createCurrencyMap(3, 4, 2, 2, 1), register.getContents());
        }

        @Test
        @DisplayName("When there are insufficient $2 Bills")
        void testRemoveContentsFromRegister_InsufficientTwos() {
            register.deposit(createCurrencyMap(3, 4, 2, 2, 1));


            try {
                register.withdraw(createCurrencyMap(3, 5, 3, 0, 1));
            } catch (InsufficientFundsException e) {
                assertEquals(new InsufficientFundsException("").getMessage(), e.getMessage());
            }

            assertEquals(createCurrencyMap(3, 4, 2, 2, 1), register.getContents());
        }

        @Test
        @DisplayName("When there are insufficient $5 Bills")
        void testRemoveContentsFromRegister_InsufficientFives() {
            register.deposit(createCurrencyMap(3, 4, 2, 2, 1));


            try {
                register.withdraw(createCurrencyMap(3, 3, 4, 0, 1));
            } catch (InsufficientFundsException e) {
                assertEquals(new InsufficientFundsException("").getMessage(), e.getMessage());
            }

            assertEquals(createCurrencyMap(3, 4, 2, 2, 1), register.getContents());
        }

        @Test
        @DisplayName("When there are insufficient $10 Bills")
        void testRemoveContentsFromRegister_InsufficientTens() {
            register.deposit(createCurrencyMap(3, 4, 2, 2, 1));


            try {
                register.withdraw(createCurrencyMap(3, 3, 1, 5, 1));
            } catch (InsufficientFundsException e) {
                assertEquals(new InsufficientFundsException("").getMessage(), e.getMessage());
            }

            assertEquals(createCurrencyMap(3, 4, 2, 2, 1), register.getContents());
        }

        @Test
        @DisplayName("When there are insufficient $20 Bills")
        void testRemoveContentsFromRegister_InsufficientTwenties() {
            register.deposit(createCurrencyMap(3, 4, 2, 2, 0));


            try {
                register.withdraw(createCurrencyMap(3, 3, 1, 1, 1));
            } catch (InsufficientFundsException e) {
                assertEquals(new InsufficientFundsException("").getMessage(), e.getMessage());
            }

            assertEquals(createCurrencyMap(3, 4, 2, 2, 0), register.getContents());
        }
    }

    @Nested
    @DisplayName("Get Total Dollar Value")
    class TestGetTotalValue {
        @Test
        @DisplayName("Empty Register")
        void testGetTotalValue_empty() {
            assertEquals(0, register.getBalance());
        }

        @Test
        @DisplayName("Full Register")
        void testGetTotalValue_full() {
            register.deposit(createCurrencyMap(3, 2, 4, 3, 6));
            assertEquals(177, register.getBalance());
        }
    }

    private Map<String, Integer> createCurrencyMap(int ones, int twos, int fives, int tens, int twenties) {
        Map<String, Integer> contents = new HashMap<>();

        contents.put("ONE", ones);
        contents.put("TWO", twos);
        contents.put("FIVE", fives);
        contents.put("TEN", tens);
        contents.put("TWENTY", twenties);

        return contents;
    }
}

