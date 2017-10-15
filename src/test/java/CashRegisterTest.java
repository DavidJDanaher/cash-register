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
            assertEquals(createCurrencyMap(0, 0, 0, 0, 0), register.getContents());
        }
    }

    @Nested
    @DisplayName("Add Contents To Register")
    class TestAddContentsToRegister {
        @Test
        @DisplayName("Added Contents")
        void testAddContentsToRegister() {
            register.addToContents(3, 5, 2, 1, 1);
            assertEquals(createCurrencyMap(3, 5, 2, 1, 1), register.getContents());

            register.addToContents(2, 0, 1, 0, 3);
            assertEquals(createCurrencyMap(5, 5, 3, 1, 4), register.getContents());
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

            assertEquals(createCurrencyMap(1, 1, 0, 2, 0), register.getContents());
        }

        @Test
        @DisplayName("When there are insufficient $1 bills")
        void testRemoveContentsFromRegister_InsufficientOnes() {
            register.addToContents(3, 4, 2, 2, 1);


            try {
                register.removeContents(4, 3, 3, 0, 1);
            } catch (InsufficientFundsException e) {
                assertEquals(new InsufficientFundsException("one").getMessage(), e.getMessage());
            }

            assertEquals(createCurrencyMap(3, 4, 2, 2, 1), register.getContents());
        }

        @Test
        @DisplayName("When there are insufficient $2 Bills")
        void testRemoveContentsFromRegister_InsufficientTwos() {
            register.addToContents(3, 4, 2, 2, 1);


            try {
                register.removeContents(3, 5, 3, 0, 1);
            } catch (InsufficientFundsException e) {
                assertEquals(new InsufficientFundsException("two").getMessage(), e.getMessage());
            }

            assertEquals(createCurrencyMap(3, 4, 2, 2, 1), register.getContents());
        }

        @Test
        @DisplayName("When there are insufficient $5 Bills")
        void testRemoveContentsFromRegister_InsufficientFives() {
            register.addToContents(3, 4, 2, 2, 1);


            try {
                register.removeContents(3, 3, 4, 0, 1);
            } catch (InsufficientFundsException e) {
                assertEquals(new InsufficientFundsException("five").getMessage(), e.getMessage());
            }

            assertEquals(createCurrencyMap(3, 4, 2, 2, 1), register.getContents());
        }

        @Test
        @DisplayName("When there are insufficient $10 Bills")
        void testRemoveContentsFromRegister_InsufficientTens() {
            register.addToContents(3, 4, 2, 2, 1);


            try {
                register.removeContents(3, 3, 1, 5, 1);
            } catch (InsufficientFundsException e) {
                assertEquals(new InsufficientFundsException("ten").getMessage(), e.getMessage());
            }

            assertEquals(createCurrencyMap(3, 4, 2, 2, 1), register.getContents());
        }

        @Test
        @DisplayName("When there are insufficient $20 Bills")
        void testRemoveContentsFromRegister_InsufficientTwenties() {
            register.addToContents(3, 4, 2, 2, 0);


            try {
                register.removeContents(3, 3, 1, 1, 1);
            } catch (InsufficientFundsException e) {
                assertEquals(new InsufficientFundsException("twenty").getMessage(), e.getMessage());
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
            assertEquals(0, register.getTotalValue());
        }

        @Test
        @DisplayName("Full Register")
        void testGetTotalValue_full() {
            register.addToContents(3, 2, 4, 3, 6);
            assertEquals(177, register.getTotalValue());
        }
    }


    @Nested
    @DisplayName("Get Change")
    class TestGetChange {
        @Test
        @DisplayName("Insufficient Funds")
        void testGetChange_InsufficientFunds() {
            register.addToContents(3 ,0, 0, 0, 0);

            try {
                register.getChange(18);
            } catch (InsufficientFundsException e) {
                assertEquals(new InsufficientFundsException("").getMessage(), e.getMessage());
            }

            assertEquals(createCurrencyMap(3, 0, 0, 0, 0), register.getContents());
        }

        @Test
        @DisplayName("Odd change required")
        void testGetChange_OddChangeRequired() {
            register.addToContents(0, 4, 0, 5, 5);

            try {
                register.getChange(17);
            } catch (InsufficientFundsException e) {
                assertEquals(new InsufficientFundsException("change").getMessage(), e.getMessage());
            }

            assertEquals(createCurrencyMap(0, 4, 0, 5, 5), register.getContents());
        }

        @Test
        @DisplayName("Ideal case")
        void testGetChange_Ideal() {
            register.addToContents(2,2,2,2,2);

            try {
                register.getChange(58);
            } catch (InsufficientFundsException e) { }

            assertEquals(createCurrencyMap(1, 1, 1, 1, 0), register.getContents());
        }

        @Nested
        @DisplayName("Special cases to consider")
        class EdgeCases {
            @Test
            @DisplayName("Change of 8 when missing a 1, sufficient 2s")
            void testGetChange_SpecialCase_Eight_AvoidFive() {
                register.addToContents(0, 4, 1, 0, 0);
                int initialValue = register.getTotalValue();
                Map<String, Integer> changeRecieved = new HashMap<>();

                try {
                    changeRecieved = register.getChange(8);
                } catch (InsufficientFundsException e) {}

                assertEquals(8, initialValue - register.getTotalValue());
                assertEquals(createCurrencyMap(0, 4, 0, 0, 0), changeRecieved);
                assertEquals(createCurrencyMap(0, 0, 1, 0, 0), register.getContents());
            }

            @Test
            @DisplayName("Change of 8 with missing 1, insufficient 2s")
            void testGetChange_SpecialCase_Eight_AvoidFive_Insufficient() {
                register.addToContents(0, 3, 1, 1, 0);

                try {
                    register.getChange(8);
                } catch (InsufficientFundsException e) {
                    assertEquals(new InsufficientFundsException("change").getMessage(), e.getMessage());
                }

                assertEquals(createCurrencyMap(0, 3, 1, 1, 0), register.getContents());
            }
        }

        @Test
        void testTest() {
            Map<String, Integer> map1 = new HashMap<>();
            Map<String, Integer> map2 = new HashMap<>();

            map1.put("A", 5);
            map2.put("A", 3);
            map1.forEach((key, value) -> map2.merge(key, value, Integer::sum));
            System.out.println(map2);
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

