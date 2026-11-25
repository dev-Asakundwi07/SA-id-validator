/**
 * Simple test runner for IdValidator (no extra libraries needed).
 * Run with:  javac IdValidator.java IdValidatorTests.java
 *            java IdValidatorTests
 *
 * All numbers below are made-up test numbers, not real people's IDs.
 */
public class IdValidatorTests {

    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        // Valid number: 1 January 1990, male, SA citizen
        checkValid("9001015000085", "Male", "SA citizen");

        // Same number with a female gender block (0000-4999) and a fixed check digit
        checkValid("9001014000086", "Female", "SA citizen");

        // Invalid numbers: each one should fail with a message
        checkInvalid("9001015000086", "The check digit is wrong.");
        checkInvalid("0502290000087", "The date of birth is not a real date.");
        checkInvalid("12345", "An ID number must have 13 digits.");
        checkInvalid("12345678901ab", "An ID number can only contain digits.");
        checkInvalid("", "An ID number must have 13 digits.");

        System.out.println();
        System.out.println(passed + " passed, " + failed + " failed");
    }

    private static void checkValid(String id, String expectedGender, String expectedCitizenship) {
        IdValidator v = new IdValidator(id);
        boolean ok = v.isValid()
                && expectedGender.equals(v.getGender())
                && expectedCitizenship.equals(v.getCitizenship());
        report(ok, id + " should be valid (" + expectedGender + ", " + expectedCitizenship + ")");
    }

    private static void checkInvalid(String id, String expectedMessage) {
        IdValidator v = new IdValidator(id);
        boolean ok = !v.isValid() && expectedMessage.equals(v.getErrorMessage());
        report(ok, "\"" + id + "\" should fail: " + expectedMessage);
    }

    private static void report(boolean ok, String description) {
        if (ok) {
            passed++;
            System.out.println("PASS  " + description);
        } else {
            failed++;
            System.out.println("FAIL  " + description);
        }
    }
}
