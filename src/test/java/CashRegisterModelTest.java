package test.java;
import main.java.CashRegisterModel;
import main.java.exceptions.InsufficientFundsException;

import main.java.resources.RegisterContents;
import org.junit.jupiter.api.*;

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
            assertEquals(new RegisterContents(0, 0, 0, 0, 0).getContents(), register.getContents());
        }
    }

    @Nested
    @DisplayName("Add Contents To Register")
    class TestAddContentsToRegister {
        @Test
        @DisplayName("Added Contents")
        void testAddContentsToRegister() {
            register.deposit(new RegisterContents(3, 5, 2, 1, 1).getContents());
            assertEquals(new RegisterContents(3, 5, 2, 1, 1).getContents(), register.getContents());

            register.deposit(new RegisterContents(2, 0, 1, 0, 3).getContents());
            assertEquals(new RegisterContents(5, 5, 3, 1, 4).getContents(), register.getContents());
        }
    }


    @Nested
    @DisplayName("Remove Contents From Register")
    class TestRemoveContentsFromRegister {
        @Test
        @DisplayName("When there are sufficient bills")
        void testRemoveContentsFromRegister_Sufficient() throws InsufficientFundsException {
            register.deposit(new RegisterContents(3, 4, 2, 2, 1).getContents());
            register.withdraw(new RegisterContents(2, 3, 2, 0, 1).getContents());

            assertEquals(new RegisterContents(1, 1, 0, 2, 0).getContents(), register.getContents());
        }

        @Test
        @DisplayName("When there are insufficient $1 bills")
        void testRemoveContentsFromRegister_InsufficientOnes() {
            register.deposit(new RegisterContents(3, 4, 2, 2, 1).getContents());


            try {
                register.withdraw(new RegisterContents(4, 3, 3, 0, 1).getContents());
            } catch (InsufficientFundsException e) {
                assertEquals(new InsufficientFundsException("").getMessage(), e.getMessage());
            }

            assertEquals(new RegisterContents(3, 4, 2, 2, 1).getContents(), register.getContents());
        }

        @Test
        @DisplayName("When there are insufficient $2 Bills")
        void testRemoveContentsFromRegister_InsufficientTwos() {
            register.deposit(new RegisterContents(3, 4, 2, 2, 1).getContents());


            try {
                register.withdraw(new RegisterContents(3, 5, 3, 0, 1).getContents());
            } catch (InsufficientFundsException e) {
                assertEquals(new InsufficientFundsException("").getMessage(), e.getMessage());
            }

            assertEquals(new RegisterContents(3, 4, 2, 2, 1).getContents(), register.getContents());
        }

        @Test
        @DisplayName("When there are insufficient $5 Bills")
        void testRemoveContentsFromRegister_InsufficientFives() {
            register.deposit(new RegisterContents(3, 4, 2, 2, 1).getContents());


            try {
                register.withdraw(new RegisterContents(3, 3, 4, 0, 1).getContents());
            } catch (InsufficientFundsException e) {
                assertEquals(new InsufficientFundsException("").getMessage(), e.getMessage());
            }

            assertEquals(new RegisterContents(3, 4, 2, 2, 1).getContents(), register.getContents());
        }

        @Test
        @DisplayName("When there are insufficient $10 Bills")
        void testRemoveContentsFromRegister_InsufficientTens() {
            register.deposit(new RegisterContents(3, 4, 2, 2, 1).getContents());


            try {
                register.withdraw(new RegisterContents(3, 3, 1, 5, 1).getContents());
            } catch (InsufficientFundsException e) {
                assertEquals(new InsufficientFundsException("").getMessage(), e.getMessage());
            }

            assertEquals(new RegisterContents(3, 4, 2, 2, 1).getContents(), register.getContents());
        }

        @Test
        @DisplayName("When there are insufficient $20 Bills")
        void testRemoveContentsFromRegister_InsufficientTwenties() {
            register.deposit(new RegisterContents(3, 4, 2, 2, 0).getContents());


            try {
                register.withdraw(new RegisterContents(3, 3, 1, 1, 1).getContents());
            } catch (InsufficientFundsException e) {
                assertEquals(new InsufficientFundsException("").getMessage(), e.getMessage());
            }

            assertEquals(new RegisterContents(3, 4, 2, 2, 0).getContents(), register.getContents());
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
            register.deposit(new RegisterContents(3, 2, 4, 3, 6).getContents());
            assertEquals(177, register.getBalance());
        }
    }
}

