package test.java;
import main.java.CashRegister;
import main.java.exceptions.InsufficientFundsException;

import org.junit.jupiter.api.*;

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
            register.addToContents(createCurrencyMap(3, 5, 2, 1, 1));
            assertEquals(createCurrencyMap(3, 5, 2, 1, 1), register.getContents());

            register.addToContents(createCurrencyMap(2, 0, 1, 0, 3));
            assertEquals(createCurrencyMap(5, 5, 3, 1, 4), register.getContents());
        }
    }


    @Nested
    @DisplayName("Remove Contents From Register")
    class TestRemoveContentsFromRegister {
        @Test
        @DisplayName("When there are sufficient bills")
        void testRemoveContentsFromRegister_Sufficient() throws InsufficientFundsException {
            register.addToContents(createCurrencyMap(3, 4, 2, 2, 1));
            register.removeContents(createCurrencyMap(2, 3, 2, 0, 1));

            assertEquals(createCurrencyMap(1, 1, 0, 2, 0), register.getContents());
        }

        @Test
        @DisplayName("When there are insufficient $1 bills")
        void testRemoveContentsFromRegister_InsufficientOnes() {
            register.addToContents(createCurrencyMap(3, 4, 2, 2, 1));


            try {
                register.removeContents(createCurrencyMap(4, 3, 3, 0, 1));
            } catch (InsufficientFundsException e) {
                assertEquals(new InsufficientFundsException("change").getMessage(), e.getMessage());
            }

            assertEquals(createCurrencyMap(3, 4, 2, 2, 1), register.getContents());
        }

        @Test
        @DisplayName("When there are insufficient $2 Bills")
        void testRemoveContentsFromRegister_InsufficientTwos() {
            register.addToContents(createCurrencyMap(3, 4, 2, 2, 1));


            try {
                register.removeContents(createCurrencyMap(3, 5, 3, 0, 1));
            } catch (InsufficientFundsException e) {
                assertEquals(new InsufficientFundsException("change").getMessage(), e.getMessage());
            }

            assertEquals(createCurrencyMap(3, 4, 2, 2, 1), register.getContents());
        }

        @Test
        @DisplayName("When there are insufficient $5 Bills")
        void testRemoveContentsFromRegister_InsufficientFives() {
            register.addToContents(createCurrencyMap(3, 4, 2, 2, 1));


            try {
                register.removeContents(createCurrencyMap(3, 3, 4, 0, 1));
            } catch (InsufficientFundsException e) {
                assertEquals(new InsufficientFundsException("change").getMessage(), e.getMessage());
            }

            assertEquals(createCurrencyMap(3, 4, 2, 2, 1), register.getContents());
        }

        @Test
        @DisplayName("When there are insufficient $10 Bills")
        void testRemoveContentsFromRegister_InsufficientTens() {
            register.addToContents(createCurrencyMap(3, 4, 2, 2, 1));


            try {
                register.removeContents(createCurrencyMap(3, 3, 1, 5, 1));
            } catch (InsufficientFundsException e) {
                assertEquals(new InsufficientFundsException("change").getMessage(), e.getMessage());
            }

            assertEquals(createCurrencyMap(3, 4, 2, 2, 1), register.getContents());
        }

        @Test
        @DisplayName("When there are insufficient $20 Bills")
        void testRemoveContentsFromRegister_InsufficientTwenties() {
            register.addToContents(createCurrencyMap(3, 4, 2, 2, 0));


            try {
                register.removeContents(createCurrencyMap(3, 3, 1, 1, 1));
            } catch (InsufficientFundsException e) {
                assertEquals(new InsufficientFundsException("change").getMessage(), e.getMessage());
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
            register.addToContents(createCurrencyMap(3, 2, 4, 3, 6));
            assertEquals(177, register.getTotalValue());
        }
    }


    @Nested
    @DisplayName("Get Change")
    class TestGetChange {
        private Map<String, Integer> changeReceived;

        @BeforeEach
        public void setup() {
            changeReceived = new HashMap<>();
        }

        @Test
        @DisplayName("Insufficient Funds")
        void testGetChange_InsufficientFunds() {
            register.addToContents(createCurrencyMap(3 ,0, 0, 0, 0));

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
            register.addToContents(createCurrencyMap(0, 4, 0, 5, 5));

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
            register.addToContents(createCurrencyMap(2,2,2,2,2));

            try {
                register.getChange(58);
            } catch (InsufficientFundsException e) { }

            assertEquals(createCurrencyMap(1, 1, 1, 1, 0), register.getContents());
        }


        @Test
        @DisplayName("Insufficient bills are handled by smallern denominations")
        void testGetChange_Insufficient() {
            register.addToContents(createCurrencyMap(3, 5, 3, 1, 2));

            try {
                changeReceived = register.getChange(77);
            } catch (InsufficientFundsException e) { }

            assertEquals(createCurrencyMap(2, 5, 3, 1, 2), changeReceived);
            assertEquals(createCurrencyMap(1, 0, 0, 0, 0), register.getContents());
        }


        @Nested
        @DisplayName("Even numbers under 10")
        class SmallEvens {
            @Test
            @DisplayName("Change of 8 when missing a 1, sufficient 2s")
            void testGetChange_SpecialCase_Eight_AvoidFive() {
                register.addToContents(createCurrencyMap(0, 4, 1, 0, 0));
                int initialValue = register.getTotalValue();

                try {
                    changeReceived = register.getChange(8);
                } catch (InsufficientFundsException e) {}

                assertEquals(8, initialValue - register.getTotalValue());
                assertEquals(createCurrencyMap(0, 4, 0, 0, 0), changeReceived);
                assertEquals(createCurrencyMap(0, 0, 1, 0, 0), register.getContents());
            }

            @Test
            @DisplayName("Change of 8 with missing 1, insufficient 2s")
            void testGetChange_SpecialCase_Eight_AvoidFive_Insufficient() {
                register.addToContents(createCurrencyMap(0, 3, 1, 1, 0));

                try {
                    register.getChange(8);
                } catch (InsufficientFundsException e) {
                    assertEquals(new InsufficientFundsException("change").getMessage(), e.getMessage());
                }

                assertEquals(createCurrencyMap(0, 3, 1, 1, 0), register.getContents());
            }
        }

        @Nested
        @DisplayName("Odd numbers in the teens")
        class TeenOdds {
            @Test
            @DisplayName("Change of 13, sufficient")
            void testGetChange_SpecialCase_Thirteen() {
                register.addToContents(createCurrencyMap(1, 4, 3, 1, 1));
                int initialValue = register.getTotalValue();

                try {
                    changeReceived = register.getChange(13);
                } catch (InsufficientFundsException e) {}

                // Change for $13 should be $10 + $2 + $1
                assertEquals(13, initialValue - register.getTotalValue());
                assertEquals(createCurrencyMap(1, 1, 0, 1, 0), changeReceived);
                assertEquals(createCurrencyMap(0, 3, 3, 0, 1), register.getContents());
            }

            @Test
            @DisplayName("Change of 13, no 10s, sufficient 1s")
            void testGetChange_SpecialCase_Thirteen_NoTens() {
                register.addToContents(createCurrencyMap(1, 4, 3, 0, 1));
                int initialValue = register.getTotalValue();

                try {
                    changeReceived = register.getChange(13);
                } catch (InsufficientFundsException e) {}

                // With no 10s, change for $13 should be $5 x 2 + $2 + $1
                assertEquals(13, initialValue - register.getTotalValue());
                assertEquals(createCurrencyMap(1, 1, 2, 0, 0), changeReceived);
                assertEquals(createCurrencyMap(0, 3, 1, 0, 1), register.getContents());
            }

            @Test
            @DisplayName("Change of 13 with no 1s, sufficient 2s")
            void testGetChange_SpecialCase_Thirteen_AvoidTen_NoOnes() {
                register.addToContents(createCurrencyMap(0, 4, 2, 1, 1));
                int initialValue = register.getTotalValue();

                try {
                    changeReceived = register.getChange(13);
                } catch (InsufficientFundsException e) {}

                // With no 1s, change for $13 should be $5 + $2 + $2 + $2 + $2
                assertEquals(13, initialValue - register.getTotalValue());
                assertEquals(createCurrencyMap(0, 4, 1, 0, 0), changeReceived);
                assertEquals(createCurrencyMap(0, 0, 1, 1, 1), register.getContents());
            }

            @Test
            @DisplayName("Change of 13 with no 1s, insufficient 2s")
            void testGetChange_SpecialCase_Thirteen_Insufficient() {
                register.addToContents(createCurrencyMap(0, 2, 2, 1, 1));

                try {
                    changeReceived = register.getChange(13);
                } catch (InsufficientFundsException e) {
                    assertEquals(new InsufficientFundsException("change").getMessage(), e.getMessage());
                }

                assertEquals(new HashMap<>(), changeReceived);
                assertEquals(createCurrencyMap(0, 2, 2, 1, 1), register.getContents());
            }
        }

        @Nested
        @DisplayName("Even numbers in the teens")
        class TeenEvens {
            @Test
            @DisplayName("Change of 14 with no 10s, sufficient 1s")
            void testGetChange_SpecialCase_Fourteen_AvoidTen_OneFive() {
                register.addToContents(createCurrencyMap(3, 6, 1, 0, 1));
                int initialValue = register.getTotalValue();

                try {
                    changeReceived = register.getChange(14);
                } catch (InsufficientFundsException e) {}

                // With no $10s, change for $14 should be $5 + $2 x 3 + $1
                assertEquals(14, initialValue - register.getTotalValue());
                assertEquals(createCurrencyMap(1, 4, 1, 0, 0), changeReceived);
                assertEquals(createCurrencyMap(2, 2, 0, 0, 1), register.getContents());
            }

            @Test
            @DisplayName("Change of 14 with no 10s, no 1s")
            void testGetChange_SpecialCase_Fourteen_AvoidTen_TwoFives() {
                register.addToContents(createCurrencyMap(3, 6, 2, 0, 1));
                int initialValue = register.getTotalValue();

                try {
                    changeReceived = register.getChange(14);
                } catch (InsufficientFundsException e) {}

                // With no $10s, change for $14 should be $5 x2 + $2 x 2
                assertEquals(14, initialValue - register.getTotalValue());
                assertEquals(createCurrencyMap(0, 2, 2, 0, 0), changeReceived);
                assertEquals(createCurrencyMap(3, 4, 0, 0, 1), register.getContents());
            }

            @Test
            @DisplayName("Change of 14 with no 10s, no 1s, one 5")
            void testGetChange_SpecialCase_Fourteen_AvoidTen_OneFive_NoOnes() {
                register.addToContents(createCurrencyMap(0, 10, 1, 0, 1));
                int initialValue = register.getTotalValue();

                try {
                    changeReceived = register.getChange(14);
                } catch (InsufficientFundsException e) {}

                // With no $10s or $1s and only 1 $5, change for $14 should be $2 x 7
                assertEquals(14, initialValue - register.getTotalValue());
                assertEquals(createCurrencyMap(0, 7, 0, 0, 0), changeReceived);
                assertEquals(createCurrencyMap(0, 3, 1, 0, 1), register.getContents());
            }

            @Test
            @DisplayName("Change of 14, unable to dispense")
            void testGetChange_SpecialCase_Fourteen_Insufficent() {
                register.addToContents(createCurrencyMap(0, 6, 1, 0, 1));

                try {
                    changeReceived = register.getChange(14);
                } catch (InsufficientFundsException e) {
                    assertEquals(new InsufficientFundsException("change").getMessage(), e.getMessage());
                }

                assertEquals(new HashMap<>(), changeReceived);
                assertEquals(createCurrencyMap(0, 6, 1, 0, 1), register.getContents());
            }

            @Test
            @DisplayName("Change of 18 with no 10s, sufficient 1s")
            void testGetChange_SpecialCase_Eighteen_AvoidTen() {
                register.addToContents(createCurrencyMap(1, 4, 3, 0, 1));
                int initialValue = register.getTotalValue();

                try {
                    changeReceived = register.getChange(18);
                } catch (InsufficientFundsException e) {}

                // With no $10s, change for $18 should be $5 x 3 + $2 + $1
                assertEquals(18, initialValue - register.getTotalValue());
                assertEquals(createCurrencyMap(1, 1, 3, 0, 0), changeReceived);
                assertEquals(createCurrencyMap(0, 3, 0, 0, 1), register.getContents());
            }

            @Test
            @DisplayName("Change of 18 with no 10s, no 1s")
            void testGetChange_SpecialCase_Eighteen_AvoidTen_InsufficientOnes() {
                register.addToContents(createCurrencyMap(0, 4, 3, 0, 1));
                int initialValue = register.getTotalValue();

                try {
                    changeReceived = register.getChange(18);
                } catch (InsufficientFundsException e) {}

                // With no $10s or $1s, change for $18 should be $5 x 2 + $2 x 4
                assertEquals(18, initialValue - register.getTotalValue());
                assertEquals(createCurrencyMap(0, 4, 2, 0, 0), changeReceived);
                assertEquals(createCurrencyMap(0, 0, 1, 0, 1), register.getContents());
            }

            @Test
            @DisplayName("Change of 18, unable to dispense")
            void testGetChange_SpecialCase_Eighteen_Insufficient() {
                register.addToContents(createCurrencyMap(0, 3, 3, 1, 1));

                try {
                    changeReceived = register.getChange(18);
                } catch (InsufficientFundsException e) {
                    assertEquals(new InsufficientFundsException("change").getMessage(), e.getMessage());
                }

                assertEquals(new HashMap<>(), changeReceived);
                assertEquals(createCurrencyMap(0, 3, 3, 1, 1), register.getContents());
            }
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

