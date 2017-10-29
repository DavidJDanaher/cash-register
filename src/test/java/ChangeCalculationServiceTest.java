package test.java;

import main.java.resources.exceptions.InsufficientFundsException;
import main.java.services.ChangeCalculationService;
import main.java.resources.RegisterContentsFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChangeCalculationServiceTest {
    private Map<Integer, Integer> changeReceived;
    private ChangeCalculationService changeService;
    private RegisterContentsFactory factory = new RegisterContentsFactory(new int[] { 20, 10, 5, 2, 1 });;

    @BeforeEach
    void createNewRegister() {
        changeReceived = new HashMap<>();
        changeService = new ChangeCalculationService(factory);
    }

    @Test
    @DisplayName("Insufficient Funds")
    void testmakeChange_InsufficientFunds() {
        Map<Integer, Integer> contents = currencyMap( 3 ,0, 0, 0, 0 );

        try {
            changeService.makeChange(18, contents);
        } catch (InsufficientFundsException e) {
            assertEquals(new InsufficientFundsException("change").getMessage(), e.getMessage());
        }

        assertEquals(new HashMap<>(), changeReceived);
    }

    @Test
    @DisplayName("Odd change, unable to dispense")
    void testmakeChange_OddChange_Insufficient() {
        Map<Integer, Integer> contents = currencyMap( 0, 4, 0, 5, 5 );

        try {
            changeService.makeChange(17, contents);
        } catch (InsufficientFundsException e) {
            assertEquals(new InsufficientFundsException("change").getMessage(), e.getMessage());
        }

        assertEquals(new HashMap<>(), changeReceived);
    }

    @Test
    @DisplayName("Basic case")
    void testmakeChange_Basic() {
        Map<Integer, Integer> contents = currencyMap( 2,2,2,2,2 );

        try {
           changeReceived = changeService.makeChange(58, contents);
        } catch (InsufficientFundsException e) { }

        assertEquals(currencyMap( 1, 1, 1, 1, 2 ), changeReceived);
    }


    @Test
    @DisplayName("Insufficient bills are handled by smallern denominations")
    void testmakeChange_Insufficient() {
        Map<Integer, Integer> contents = currencyMap( 3, 5, 3, 1, 2 );

        try {
            changeReceived = changeService.makeChange(77, contents);
        } catch (InsufficientFundsException e) { }

        assertEquals(currencyMap( 2, 5, 3, 1, 2 ), changeReceived);
    }


    @Nested
    @DisplayName("Even numbers under 10")
    class SmallEvens {
        @Test
        @DisplayName("Change of 8 when missing a 1, sufficient 2s")
        void testmakeChange_SpecialCase_Eight_AvoidFive() {
            Map<Integer, Integer> contents = currencyMap( 0, 4, 1, 0, 0 );

            try {
                changeReceived = changeService.makeChange(8, contents);
            } catch (InsufficientFundsException e) {}

            assertEquals(currencyMap( 0, 4, 0, 0, 0 ), changeReceived);
        }

        @Test
        @DisplayName("Change of 8 with missing 1, insufficient 2s")
        void testmakeChange_SpecialCase_Eight_AvoidFive_Insufficient() {
            Map<Integer, Integer> contents = currencyMap( 0, 3, 1, 1, 0 );

            try {
                changeService.makeChange(8, contents);
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
        void testmakeChange_SpecialCase_Thirteen() {
            Map<Integer, Integer> contents = currencyMap( 1, 4, 3, 1, 1 );

            try {
                changeReceived = changeService.makeChange(13, contents);
            } catch (InsufficientFundsException e) {}

            // Change for $13 should be $10 + $2 + $1
            assertEquals(currencyMap( 1, 1, 0, 1, 0 ), changeReceived);
        }

        @Test
        @DisplayName("Change of 13, no 10s, sufficient 1s")
        void testmakeChange_SpecialCase_Thirteen_NoTens() {
            Map<Integer, Integer> contents = currencyMap( 1, 4, 3, 0, 1 );

            try {
                changeReceived = changeService.makeChange(13, contents);
            } catch (InsufficientFundsException e) {}

            // With no 10s, change for $13 should be $5 x 2 + $2 + $1
            assertEquals(currencyMap( 1, 1, 2, 0, 0 ), changeReceived);
        }

        @Test
        @DisplayName("Change of 13 with no 1s, sufficient 2s")
        void testmakeChange_SpecialCase_Thirteen_AvoidTen_NoOnes() {
            Map<Integer, Integer> contents = currencyMap( 0, 4, 2, 1, 1 );

            try {
                changeReceived = changeService.makeChange(13, contents);
            } catch (InsufficientFundsException e) {}

            // With no 1s, change for $13 should be $5 + $2 + $2 + $2 + $2
            assertEquals(currencyMap( 0, 4, 1, 0, 0 ), changeReceived);
        }

        @Test
        @DisplayName("Change of 13 with no 1s, insufficient 2s")
        void testmakeChange_SpecialCase_Thirteen_Insufficient() {
            Map<Integer, Integer> contents = currencyMap( 0, 2, 2, 1, 1 );

            try {
                changeReceived = changeService.makeChange(13, contents);
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
        void testmakeChange_SpecialCase_Fourteen_AvoidTen_OneFive() {
            Map<Integer, Integer> contents = currencyMap( 3, 6, 1, 0, 1 );

            try {
                changeReceived = changeService.makeChange(14, contents);
            } catch (InsufficientFundsException e) {}

            // With no $10s, change for $14 should be $5 + $2 x 3 + $1
            assertEquals(currencyMap( 1, 4, 1, 0, 0 ), changeReceived);
        }

        @Test
        @DisplayName("Change of 14 with no 10s, no 1s")
        void testmakeChange_SpecialCase_Fourteen_AvoidTen_TwoFives() {
            Map<Integer, Integer> contents = currencyMap( 3, 6, 2, 0, 1 );

            try {
                changeReceived = changeService.makeChange(14, contents);
            } catch (InsufficientFundsException e) {}

            // With no $10s, change for $14 should be $5 x2 + $2 x 2
            assertEquals(currencyMap( 0, 2, 2, 0, 0 ), changeReceived);
        }

        @Test
        @DisplayName("Change of 14 with no 10s, no 1s, one 5")
        void testmakeChange_SpecialCase_Fourteen_AvoidTen_OneFive_NoOnes() {
            Map<Integer, Integer> contents = currencyMap( 0, 10, 1, 0, 1 );

            try {
                changeReceived = changeService.makeChange(14, contents);
            } catch (InsufficientFundsException e) {}

            // With no $10s or $1s and only 1 $5, change for $14 should be $2 x 7
            assertEquals(currencyMap( 0, 7, 0, 0, 0 ), changeReceived);
        }

        @Test
        @DisplayName("Change of 14, unable to dispense")
        void testmakeChange_SpecialCase_Fourteen_Insufficent() {
            Map<Integer, Integer> contents = currencyMap( 0, 6, 1, 0, 1 );

            try {
                changeReceived = changeService.makeChange(14, contents);
            } catch (InsufficientFundsException e) {
                assertEquals(new InsufficientFundsException("change").getMessage(), e.getMessage());
            }

            assertEquals(new HashMap<>(), changeReceived);
        }

        @Test
        @DisplayName("Change of 18 with no 10s, sufficient 1s")
        void testmakeChange_SpecialCase_Eighteen_AvoidTen() {
            Map<Integer, Integer> contents = currencyMap( 1, 4, 3, 0, 1 );

            try {
                changeReceived = changeService.makeChange(18, contents);
            } catch (InsufficientFundsException e) {}

            // With no $10s, change for $18 should be $5 x 3 + $2 + $1
            assertEquals(currencyMap( 1, 1, 3, 0, 0 ), changeReceived);
        }

        @Test
        @DisplayName("Change of 18 with no 10s, no 1s")
        void testmakeChange_SpecialCase_Eighteen_AvoidTen_InsufficientOnes() {
            Map<Integer, Integer> contents = currencyMap( 0, 4, 3, 0, 1 );

            try {
                changeReceived = changeService.makeChange(18, contents);
            } catch (InsufficientFundsException e) {}

            // With no $10s or $1s, change for $18 should be $5 x 2 + $2 x 4
            assertEquals(currencyMap( 0, 4, 2, 0, 0 ), changeReceived);
        }

        @Test
        @DisplayName("Change of 18, unable to dispense")
        void testmakeChange_SpecialCase_Eighteen_Insufficient() {
            Map<Integer, Integer> contents = currencyMap( 0, 3, 3, 1, 1 );

            try {
                changeReceived = changeService.makeChange(18, contents);
            } catch (InsufficientFundsException e) {
                assertEquals(new InsufficientFundsException("change").getMessage(), e.getMessage());
            }

            assertEquals(new HashMap<>(), changeReceived);
        }

        @Test
        @DisplayName("Nate - Happy Path change $18")
        void testmakeChange_Happy18() throws Exception {
            Map<Integer, Integer> contents = currencyMap( 0, 4, 4, 0, 0 );

            changeReceived = changeService.makeChange(18, contents);

            assertEquals(currencyMap( 0, 4, 2, 0, 0 ), changeReceived);
        }


        @Test
        @DisplayName("Nate - Happy Path change $6")
        void testmakeChange_Happy6() throws Exception {
            Map<Integer, Integer> contents = currencyMap( 0, 4, 2, 0, 0 );

            changeReceived = changeService.makeChange(6, contents);

            assertEquals(currencyMap( 0, 3, 0, 0, 0 ), changeReceived);
        }
    }

    private Map<Integer, Integer> currencyMap(int ones, int twos, int fives, int tens, int twenties) {
        return factory.getContents(new int[] { twenties, tens, fives, twos, ones });
    }
}
