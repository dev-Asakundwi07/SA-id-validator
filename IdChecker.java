import java.util.Scanner;

public class IdChecker {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("SA ID Number Validator");
        System.out.println("Type an ID number to check it, or type exit to quit.");

        while (true) {
            System.out.println();
            System.out.print("ID number: ");

            // Stop if there is no more input (for example, input ended)
            if (!input.hasNextLine()) {
                break;
            }

            String text = input.nextLine().trim();

            if (text.equalsIgnoreCase("exit")) {
                System.out.println("Goodbye!");
                break;
            }

            IdValidator validator = new IdValidator(text);

            if (validator.isValid()) {
                System.out.println("Valid ID number.");
                System.out.println("  Date of birth: " + validator.getDateOfBirth());
                System.out.println("  Gender:        " + validator.getGender());
                System.out.println("  Citizenship:   " + validator.getCitizenship());
            } else {
                System.out.println("Invalid: " + validator.getErrorMessage());
            }
        }
    }
}
