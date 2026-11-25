import java.time.DateTimeException;
import java.time.LocalDate;

/**
 * Validates a South African ID number and decodes the information inside it.
 *
 * Structure of the 13 digits:
 *   1-6   date of birth (YYMMDD)
 *   7-10  gender (0000-4999 female, 5000-9999 male)
 *   11    citizenship (0 = SA citizen, 1 = permanent resident)
 *   12    not used by this program
 *   13    check digit (Luhn algorithm)
 */
public class IdValidator {

    private final String id;
    private boolean valid;
    private String errorMessage;
    private LocalDate dateOfBirth;
    private String gender;
    private String citizenship;

    // Constructor: runs the validation as soon as the object is created
    public IdValidator(String id) {
        this.id = (id == null) ? "" : id.trim();
        validate();
    }

    // ---- Getters ----

    public boolean isValid() {
        return valid;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public String getCitizenship() {
        return citizenship;
    }

    // ---- Validation ----

    private void validate() {
        // Check 1: exactly 13 characters
        if (id.length() != 13) {
            fail("An ID number must have 13 digits.");
            return;
        }

        // Check 2: every character is a digit
        if (!isAllDigits(id)) {
            fail("An ID number can only contain digits.");
            return;
        }

        // Check 3: the date of birth must be a real date
        int yy = Integer.parseInt(id.substring(0, 2));
        int month = Integer.parseInt(id.substring(2, 4));
        int day = Integer.parseInt(id.substring(4, 6));

        // A 2-digit year is ambiguous, so guess the century:
        // later than the current year means the 1900s, otherwise the 2000s.
        int currentYY = LocalDate.now().getYear() % 100;
        int year = (yy > currentYY) ? 1900 + yy : 2000 + yy;

        try {
            dateOfBirth = LocalDate.of(year, month, day);
        } catch (DateTimeException e) {
            fail("The date of birth is not a real date.");
            return;
        }

        // Check 4: citizenship digit must be 0 or 1
        char citizenDigit = id.charAt(10);
        if (citizenDigit == '0') {
            citizenship = "SA citizen";
        } else if (citizenDigit == '1') {
            citizenship = "Permanent resident";
        } else {
            fail("The citizenship digit must be 0 or 1.");
            return;
        }

        // Check 5: the check digit must pass the Luhn algorithm
        if (!passesLuhn(id)) {
            fail("The check digit is wrong.");
            return;
        }

        // Everything passed, so decode the gender and mark the ID as valid
        int genderNumber = Integer.parseInt(id.substring(6, 10));
        gender = (genderNumber < 5000) ? "Female" : "Male";
        valid = true;
    }

    // Marks the ID as invalid and clears any decoded details
    private void fail(String message) {
        valid = false;
        errorMessage = message;
        dateOfBirth = null;
        gender = null;
        citizenship = null;
    }

    private static boolean isAllDigits(String text) {
        for (int i = 0; i < text.length(); i++) {
            if (!Character.isDigit(text.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    // Luhn algorithm: from the right, double every second digit
    // (the check digit itself is not doubled). If doubling gives more
    // than 9, subtract 9. The ID is valid when the total divides by 10.
    private static boolean passesLuhn(String id) {
        int sum = 0;
        boolean doubleIt = false;

        for (int i = id.length() - 1; i >= 0; i--) {
            int digit = id.charAt(i) - '0';
            if (doubleIt) {
                digit = digit * 2;
                if (digit > 9) {
                    digit = digit - 9;
                }
            }
            sum = sum + digit;
            doubleIt = !doubleIt;
        }
        return sum % 10 == 0;
    }
}
