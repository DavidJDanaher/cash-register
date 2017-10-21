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
      register = new CashRegisterModel();
    }

    @Nested
    @DisplayName("Get Register Contents")
    class TestGetContents {
        @Test
        @DisplayName("Empty Register")
        void testGetContents() {
            assertEquals(new RegisterContentsFactory(0, 0, 0, 0, 0).getContents(), register.getContents());
        }
    }

    @Nested
    @DisplayName("Add Contents To Register")
    class TestAddContentsToRegister {
        @Test
        @DisplayName("Added Contents")
        void testAddContentsToRegister() {
            register.deposit(new RegisterContentsFactory(3, 5, 2, 1, 1).getContents());
            assertEquals(new RegisterContentsFactory(3, 5, 2, 1, 1).getContents(), register.getContents());

            register.deposit(new RegisterContentsFactory(2, 0, 1, 0, 3).getContents());
            assertEquals(new RegisterContentsFactory(5, 5, 3, 1, 4).getContents(), register.getContents());
        }
    }


    @Nested
    @DisplayName("Remove Contents From Register")
    class TestRemoveContentsFromRegister {
        @Test
        @DisplayName("When there are sufficient bills")
        void testRemoveContentsFromRegister_Sufficient() throws InsufficientFundsException {
            register.deposit(new RegisterContentsFactory(3, 4, 2, 2, 1).getContents());
            register.withdraw(new RegisterContentsFactory(2, 3, 2, 0, 1).getContents());

            assertEquals(new RegisterContentsFactory(1, 1, 0, 2, 0).getContents(), register.getContents());
        }

        @Test
        @DisplayName("When there are insufficient $1 bills")
        void testRemoveContentsFromRegister_InsufficientOnes() {
            register.deposit(new RegisterContentsFactory(3, 4, 2, 2, 1).getContents());


            try {
                register.withdraw(new RegisterContentsFactory(4, 3, 3, 0, 1).getContents());
            } catch (InsufficientFundsException e) {
                assertEquals(new InsufficientFundsException("").getMessage(), e.getMessage());
            }

            assertEquals(new RegisterContentsFactory(3, 4, 2, 2, 1).getContents(), register.getContents());
        }

        @Test
        @DisplayName("When there are insufficient $2 Bills")
        void testRemoveContentsFromRegister_InsufficientTwos() {
            register.deposit(new RegisterContentsFactory(3, 4, 2, 2, 1).getContents());


            try {
                register.withdraw(new RegisterContentsFactory(3, 5, 3, 0, 1).getContents());
            } catch (InsufficientFundsException e) {
                assertEquals(new InsufficientFundsException("").getMessage(), e.getMessage());
            }

            assertEquals(new RegisterContentsFactory(3, 4, 2, 2, 1).getContents(), register.getContents());
        }

        @Test
        @DisplayName("When there are insufficient $5 Bills")
        void testRemoveContentsFromRegister_InsufficientFives() {
            register.deposit(new RegisterContentsFactory(3, 4, 2, 2, 1).getContents());


            try {
                register.withdraw(new RegisterContentsFactory(3, 3, 4, 0, 1).getContents());
            } catch (InsufficientFundsException e) {
                assertEquals(new InsufficientFundsException("").getMessage(), e.getMessage());
            }

            assertEquals(new RegisterContentsFactory(3, 4, 2, 2, 1).getContents(), register.getContents());
        }

        @Test
        @DisplayName("When there are insufficient $10 Bills")
        void testRemoveContentsFromRegister_InsufficientTens() {
            register.deposit(new RegisterContentsFactory(3, 4, 2, 2, 1).getContents());


            try {
                register.withdraw(new RegisterContentsFactory(3, 3, 1, 5, 1).getContents());
            } catch (InsufficientFundsException e) {
                assertEquals(new InsufficientFundsException("").getMessage(), e.getMessage());
            }

            assertEquals(new RegisterContentsFactory(3, 4, 2, 2, 1).getContents(), register.getContents());
        }

        @Test
        @DisplayName("When there are insufficient $20 Bills")
        void testRemoveContentsFromRegister_InsufficientTwenties() {
            register.deposit(new RegisterContentsFactory(3, 4, 2, 2, 0).getContents());


            try {
                register.withdraw(new RegisterContentsFactory(3, 3, 1, 1, 1).getContents());
            } catch (InsufficientFundsException e) {
                assertEquals(new InsufficientFundsException("").getMessage(), e.getMessage());
            }

            assertEquals(new RegisterContentsFactory(3, 4, 2, 2, 0).getContents(), register.getContents());
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
            register.deposit(new RegisterContentsFactory(3, 2, 4, 3, 6).getContents());
            assertEquals(177, register.getBalance());
        }
    }

    @Nested
    @DisplayName("Make Change")
    class TestMakeChange {
        // The use of Mockito here throws a warning, but I've left it because the code isn't doing anything unexpected
        ChangeCalculationService mockChangeService = mock(ChangeCalculationService.class);
        CashRegisterModel register = new CashRegisterModel();

        @Test
        void testMakeChange_Insufficient() {
            register.deposit(new RegisterContentsFactory(1, 1, 1, 1, 1).getContents());

            try {
                register.makeChange((long) 40);
            } catch (InsufficientFundsException e) {
                assertEquals(new InsufficientFundsException("").getMessage(), e.getMessage());
            }
        }

        @Test
        void testMakeChange_Sufficient() {
            Map<String, Long> mockChange = new RegisterContentsFactory(1, 1, 1, 1, 1).getContents();
            register.deposit(new RegisterContentsFactory(3, 3, 3, 3, 3).getContents());

            try {
                when(mockChangeService.getChange(38, register.getContents())).thenReturn(mockChange);
                register.makeChange((long) 38);

            } catch (InsufficientFundsException e) { }

            assertEquals(new RegisterContentsFactory(2, 2, 2, 2, 2).getContents(), register.getContents());
        }
    }
}

