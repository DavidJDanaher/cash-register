package test.java;

import main.java.exceptions.InsufficientFundsException;
import main.java.resources.ChangeCalculationService;
import main.java.resources.RegisterContents;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChangeCalculationServiceTest {
    private Map<String, Long> changeReceived;
    private ChangeCalculationService changeService;

    @BeforeEach
    void createNewRegister() {
        changeReceived = new HashMap<>();
        changeService = new ChangeCalculationService();
    }

    @Test
    @DisplayName("Insufficient Funds")
    void testGetChange_InsufficientFunds() {
        Map<String, Long> contents = new RegisterContents(3 ,0, 0, 0, 0).getContents();

        try {
            changeService.getChange(18, contents);
        } catch (InsufficientFundsException e) {
            assertEquals(new InsufficientFundsException("change").getMessage(), e.getMessage());
        }

        assertEquals(new HashMap<>(), changeReceived);
    }

    @Test
    @DisplayName("Odd change, unable to dispense")
    void testGetChange_OddChange_Insufficient() {
        Map<String, Long> contents = new RegisterContents(0, 4, 0, 5, 5).getContents();

        try {
            changeService.getChange(17, contents);
        } catch (InsufficientFundsException e) {
            assertEquals(new InsufficientFundsException("change").getMessage(), e.getMessage());
        }

        assertEquals(new HashMap<>(), changeReceived);
    }

    @Test
    @DisplayName("Basic case")
    void testGetChange_Basic() {
        Map<String, Long> contents = new RegisterContents(2,2,2,2,2).getContents();

        try {
           changeReceived = changeService.getChange(58, contents);
        } catch (InsufficientFundsException e) { }

        assertEquals(new RegisterContents(1, 1, 1, 1, 2).getContents(), changeReceived);
    }


    @Test
    @DisplayName("Insufficient bills are handled by smallern denominations")
    void testGetChange_Insufficient() {
        Map<String, Long> contents = new RegisterContents(3, 5, 3, 1, 2).getContents();

        try {
            changeReceived = changeService.getChange(77, contents);
        } catch (InsufficientFundsException e) { }

        assertEquals(new RegisterContents(2, 5, 3, 1, 2).getContents(), changeReceived);
    }


    @Nested
    @DisplayName("Even numbers under 10")
    class SmallEvens {
        @Test
        @DisplayName("Change of 8 when missing a 1, sufficient 2s")
        void testGetChange_SpecialCase_Eight_AvoidFive() {
            Map<String, Long> contents = new RegisterContents(0, 4, 1, 0, 0).getContents();

            try {
                changeReceived = changeService.getChange(8, contents);
            } catch (InsufficientFundsException e) {}

            assertEquals(new RegisterContents(0, 4, 0, 0, 0).getContents(), changeReceived);
        }

        @Test
        @DisplayName("Change of 8 with missing 1, insufficient 2s")
        void testGetChange_SpecialCase_Eight_AvoidFive_Insufficient() {
            Map<String, Long> contents = new RegisterContents(0, 3, 1, 1, 0).getContents();

            try {
                changeService.getChange(8, contents);
            } catch (InsufficientFundsException e) {
                assertEquals(new InsufficientFundsException("change").getMessage(), e.getMessage());
            }

            assertEquals(new HashMap<>(), changeReceived);
        }
    }

    @Nested
    @DisplayName("Odd numbers in the teens")
    class TeenOdds {
        @Test
        @DisplayName("Change of 13, sufficient")
        void testGetChange_SpecialCase_Thirteen() {
            Map<String, Long> contents = new RegisterContents(1, 4, 3, 1, 1).getContents();

            try {
                changeReceived = changeService.getChange(13, contents);
            } catch (InsufficientFundsException e) {}

            // Change for $13 should be $10 + $2 + $1
            assertEquals(new RegisterContents(1, 1, 0, 1, 0).getContents(), changeReceived);
        }

        @Test
        @DisplayName("Change of 13, no 10s, sufficient 1s")
        void testGetChange_SpecialCase_Thirteen_NoTens() {
            Map<String, Long> contents = new RegisterContents(1, 4, 3, 0, 1).getContents();

            try {
                changeReceived = changeService.getChange(13, contents);
            } catch (InsufficientFundsException e) {}

            // With no 10s, change for $13 should be $5 x 2 + $2 + $1
            assertEquals(new RegisterContents(1, 1, 2, 0, 0).getContents(), changeReceived);
        }

        @Test
        @DisplayName("Change of 13 with no 1s, sufficient 2s")
        void testGetChange_SpecialCase_Thirteen_AvoidTen_NoOnes() {
            Map<String, Long> contents = new RegisterContents(0, 4, 2, 1, 1).getContents();

            try {
                changeReceived = changeService.getChange(13, contents);
            } catch (InsufficientFundsException e) {}

            // With no 1s, change for $13 should be $5 + $2 + $2 + $2 + $2
            assertEquals(new RegisterContents(0, 4, 1, 0, 0).getContents(), changeReceived);
        }

        @Test
        @DisplayName("Change of 13 with no 1s, insufficient 2s")
        void testGetChange_SpecialCase_Thirteen_Insufficient() {
            Map<String, Long> contents = new RegisterContents(0, 2, 2, 1, 1).getContents();

            try {
                changeReceived = changeService.getChange(13, contents);
            } catch (InsufficientFundsException e) {
                assertEquals(new InsufficientFundsException("change").getMessage(), e.getMessage());
            }

            assertEquals(new HashMap<>(), changeReceived);
        }
    }

    @Nested
    @DisplayName("Even numbers in the teens")
    class TeenEvens {
        @Test
        @DisplayName("Change of 14 with no 10s, sufficient 1s")
        void testGetChange_SpecialCase_Fourteen_AvoidTen_OneFive() {
            Map<String, Long> contents = new RegisterContents(3, 6, 1, 0, 1).getContents();

            try {
                changeReceived = changeService.getChange(14, contents);
            } catch (InsufficientFundsException e) {}

            // With no $10s, change for $14 should be $5 + $2 x 3 + $1
            assertEquals(new RegisterContents(1, 4, 1, 0, 0).getContents(), changeReceived);
        }

        @Test
        @DisplayName("Change of 14 with no 10s, no 1s")
        void testGetChange_SpecialCase_Fourteen_AvoidTen_TwoFives() {
            Map<String, Long> contents = new RegisterContents(3, 6, 2, 0, 1).getContents();

            try {
                changeReceived = changeService.getChange(14, contents);
            } catch (InsufficientFundsException e) {}

            // With no $10s, change for $14 should be $5 x2 + $2 x 2
            assertEquals(new RegisterContents(0, 2, 2, 0, 0).getContents(), changeReceived);
        }

        @Test
        @DisplayName("Change of 14 with no 10s, no 1s, one 5")
        void testGetChange_SpecialCase_Fourteen_AvoidTen_OneFive_NoOnes() {
            Map<String, Long> contents = new RegisterContents(0, 10, 1, 0, 1).getContents();

            try {
                changeReceived = changeService.getChange(14, contents);
            } catch (InsufficientFundsException e) {}

            // With no $10s or $1s and only 1 $5, change for $14 should be $2 x 7
            assertEquals(new RegisterContents(0, 7, 0, 0, 0).getContents(), changeReceived);
        }

        @Test
        @DisplayName("Change of 14, unable to dispense")
        void testGetChange_SpecialCase_Fourteen_Insufficent() {
            Map<String, Long> contents = new RegisterContents(0, 6, 1, 0, 1).getContents();

            try {
                changeReceived = changeService.getChange(14, contents);
            } catch (InsufficientFundsException e) {
                assertEquals(new InsufficientFundsException("change").getMessage(), e.getMessage());
            }

            assertEquals(new HashMap<>(), changeReceived);
        }

        @Test
        @DisplayName("Change of 18 with no 10s, sufficient 1s")
        void testGetChange_SpecialCase_Eighteen_AvoidTen() {
            Map<String, Long> contents = new RegisterContents(1, 4, 3, 0, 1).getContents();

            try {
                changeReceived = changeService.getChange(18, contents);
            } catch (InsufficientFundsException e) {}

            // With no $10s, change for $18 should be $5 x 3 + $2 + $1
            assertEquals(new RegisterContents(1, 1, 3, 0, 0).getContents(), changeReceived);
        }

        @Test
        @DisplayName("Change of 18 with no 10s, no 1s")
        void testGetChange_SpecialCase_Eighteen_AvoidTen_InsufficientOnes() {
            Map<String, Long> contents = new RegisterContents(0, 4, 3, 0, 1).getContents();

            try {
                changeReceived = changeService.getChange(18, contents);
            } catch (InsufficientFundsException e) {}

            // With no $10s or $1s, change for $18 should be $5 x 2 + $2 x 4
            assertEquals(new RegisterContents(0, 4, 2, 0, 0).getContents(), changeReceived);
        }

        @Test
        @DisplayName("Change of 18, unable to dispense")
        void testGetChange_SpecialCase_Eighteen_Insufficient() {
            Map<String, Long> contents = new RegisterContents(0, 3, 3, 1, 1).getContents();

            try {
                changeReceived = changeService.getChange(18, contents);
            } catch (InsufficientFundsException e) {
                assertEquals(new InsufficientFundsException("change").getMessage(), e.getMessage());
            }

            assertEquals(new HashMap<>(), changeReceived);
        }
    }
}
