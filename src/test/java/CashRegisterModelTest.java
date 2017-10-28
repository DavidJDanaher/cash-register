package test.java;
import main.java.features.CashRegisterModel;
import main.java.resources.exceptions.InsufficientFundsException;

import main.java.resources.RegisterContentsFactory;
import main.java.services.ChangeCalculationService;
import org.junit.jupiter.api.*;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("Cash Register Unit Tests")
class CashRegisterModelTest {
    private CashRegisterModel register;

    @BeforeEach
    void createNewRegister() {
      register = new CashRegisterModel(new int[] { 1, 2, 5, 10, 20 });
    }

    @Nested
    @DisplayName("Get Register Contents")
    class TestGetContents {
        @Test
        @DisplayName("Empty Register")
        void testGetContents() {
            assertEquals(new RegisterContentsFactory(new int[] { 1, 2, 5, 10, 20 }).getContents(), register.getContents());
        }
    }

    @Nested
    @DisplayName("Add Contents To Register")
    class TestAddContentsToRegister {
        @Test
        @DisplayName("Added Contents")
        void testAddContentsToRegister() {
            register.deposit(currencyMap( 3, 5, 2, 1, 1 ));
            assertEquals(currencyMap( 3, 5, 2, 1, 1 ), register.getContents());

            register.deposit(currencyMap( 2, 0, 1, 0, 3 ));
            assertEquals(currencyMap( 5, 5, 3, 1, 4 ), register.getContents());
        }
    }


    @Nested
    @DisplayName("Remove Contents From Register")
    class TestRemoveContentsFromRegister {
        @Test
        @DisplayName("When there are sufficient bills")
        void testRemoveContentsFromRegister_Sufficient() throws InsufficientFundsException {
            register.deposit(currencyMap( 3, 4, 2, 2, 1 ));
            register.withdraw(currencyMap( 2, 3, 2, 0, 1 ));

            assertEquals(currencyMap( 1, 1, 0, 2, 0 ), register.getContents());
        }

        @Test
        @DisplayName("When there are insufficient $1 bills")
        void testRemoveContentsFromRegister_InsufficientOnes() {
            register.deposit(currencyMap( 3, 4, 2, 2, 1 ));


            try {
                register.withdraw(currencyMap( 4, 3, 3, 0, 1 ));
            } catch (InsufficientFundsException e) {
                assertEquals(new InsufficientFundsException("").getMessage(), e.getMessage());
            }

            assertEquals(currencyMap( 3, 4, 2, 2, 1 ), register.getContents());
        }

        @Test
        @DisplayName("When there are insufficient $2 Bills")
        void testRemoveContentsFromRegister_InsufficientTwos() {
            register.deposit(currencyMap( 3, 4, 2, 2, 1 ));


            try {
                register.withdraw(currencyMap( 3, 5, 3, 0, 1 ));
            } catch (InsufficientFundsException e) {
                assertEquals(new InsufficientFundsException("").getMessage(), e.getMessage());
            }

            assertEquals(currencyMap( 3, 4, 2, 2, 1 ), register.getContents());
        }

        @Test
        @DisplayName("When there are insufficient $5 Bills")
        void testRemoveContentsFromRegister_InsufficientFives() {
            register.deposit(currencyMap( 3, 4, 2, 2, 1 ));


            try {
                register.withdraw(currencyMap( 3, 3, 4, 0, 1 ));
            } catch (InsufficientFundsException e) {
                assertEquals(new InsufficientFundsException("").getMessage(), e.getMessage());
            }

            assertEquals(currencyMap( 3, 4, 2, 2, 1 ), register.getContents());
        }

        @Test
        @DisplayName("When there are insufficient $10 Bills")
        void testRemoveContentsFromRegister_InsufficientTens() {
            register.deposit(currencyMap( 3, 4, 2, 2, 1 ));


            try {
                register.withdraw(currencyMap( 3, 3, 1, 5, 1 ));
            } catch (InsufficientFundsException e) {
                assertEquals(new InsufficientFundsException("").getMessage(), e.getMessage());
            }

            assertEquals(currencyMap( 3, 4, 2, 2, 1 ), register.getContents());
        }

        @Test
        @DisplayName("When there are insufficient $20 Bills")
        void testRemoveContentsFromRegister_InsufficientTwenties() {
            register.deposit(currencyMap( 3, 4, 2, 2, 0 ));


            try {
                register.withdraw(currencyMap( 3, 3, 1, 1, 1 ));
            } catch (InsufficientFundsException e) {
                assertEquals(new InsufficientFundsException("").getMessage(), e.getMessage());
            }

            assertEquals(currencyMap( 3, 4, 2, 2, 0 ), register.getContents());
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
            register.deposit(currencyMap( 3, 2, 4, 3, 6));
            assertEquals(177, register.getBalance());
        }
    }

    @Nested
    @DisplayName("Make Change")
    class TestMakeChange {
        // The use of Mockito here throws a warning, but I've left it because the code isn't doing anything unexpected
        ChangeCalculationService mockChangeService = mock(ChangeCalculationService.class);
        CashRegisterModel register = new CashRegisterModel(new int[] { 1, 2, 5, 10, 20 });

        @Test
        void testMakeChange_Insufficient() {
            register.deposit(currencyMap( 1, 1, 1, 1, 1 ));

            try {
                register.makeChange( 40);
            } catch (InsufficientFundsException e) {
                assertEquals(new InsufficientFundsException("").getMessage(), e.getMessage());
            }
        }

        @Test
        void testMakeChange_Sufficient() {
            Map<Integer, Integer> mockChange = currencyMap(1, 1,1, 1,1);
            register.deposit(currencyMap(3, 3, 3, 3, 3 ));

            try {
                when(mockChangeService.getChange(38, register.getContents())).thenReturn(mockChange);
                register.makeChange( 38);

            } catch (InsufficientFundsException e) { }

            assertEquals(currencyMap(2, 2, 2, 2, 2 ), register.getContents());
        }
    }

    private Map<Integer, Integer> currencyMap(int ones, int twos, int fives, int tens, int twenties) {
        return new RegisterContentsFactory(new int[] { 1, 2, 5, 10, 20 }, new int[] { ones, twos, fives, tens, twenties }).getContents();
    }
}

