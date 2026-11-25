import java.time.DateTimeException;
 import java.time.LocalDate;
   import java.util.Scanner;
   
        public class IdChecker{
			
			public static void main(String[] args){
			    Scanner input = new Scanner(System.in);

            System.out.print("Enter a 13-digit ID number: ");
            String id = input.nextLine().trim();
			
			//Check 1: Exactly 13 characters
			if(id.length() != 13){
				System.out.println("Invalid: an ID number must have 13 digits.");
				 return;
			}
			
			//Check 2: Every character is a digit
			if(!isAllDigits(id)){
				System.out.println("Invalid: an ID number can only contain digits.");
				 return;
			}
			
			//Date of birth: YYMMDD
			int yy = Integer.parseInt(id.substring(0, 2));
            int month = Integer.parseInt(id.substring(2, 4));			
			int day = Integer.parseInt(id.substring(4, 6));
			
			//Working out the century: if the 2-digit year is later than the current year,
			//the person was born in the 1900s, otherwise the 2000s.
			int currentYY = LocalDate.now().getYear() % 100;
			int year = (yy > currentYY)? 1900 + yy : 2000 + yy;

            //Check 3: the date must be a real date(handles 31 february, leap years)
			try {
				LocalDate.of(year, month, day);
			} catch (DateTimeException e){
				System.out.println("Invalid: The date of birth is not a real date.");
				    return;
			}
			
			//Gender: Digits 7 - 10 (positions 6 to 9)
			int genderNumber = Integer.parseInt(id.substring(6, 10));
			String gender = (genderNumber < 5000) ? "Female":
			"Male";
			
			//Citizenship; digit 11 (position 10)
			char citizenDigit = id.charAt(10);
			String citizenship;
			if(citizenDigit == '0'){
				citizenship = "SA citizen";
			}else if(citizenDigit == '1'){
				citizenship = "Permanenet resident";
			}else{
				System.out.println("Invalid: The citizenship digit must be 0 or 1.");
				    return;
			}
			
			//Check 4: The last digit must pass the Luhn check
			if(!passesLuhn(id)){
				System.out.println("Invalid: the check digit is wrong.");
				    return;
			}
			
			System.out.println();
			System.out.println("This ID number is valid.");
			System.out.println("Date of birth: " + year + "-" + pad(month) + "-" + pad(day));
			System.out.println("Gender: " + gender);
			System.out.println("Citizenship: " + citizenship);
			
			}
			
			//Returns true if every character is a digit 0-9
			static boolean isAllDigits(String text){
				for(int i = 0; i < text.length(); i++){
					if(!Character.isDigit(text.charAt(i))){
						return false;
					}
				}
				
				return true;
			}
			
			//Luhn algorithm: starting from the right, double every second digit.
			//If doubling gives more than 9, subtract 9. Add everything up.
			//The ID is valid when the total is dividible by 10.
			
			static boolean passesLuhn(String id){
				int sum = 0;
				boolean doubleIt = false;
				//The last digit(check digit) is NOT doubled
				
				for(int i = id.length() - 1; i >= 0; i--){
					int digit = id.charAt(i) - '0';
					if(doubleIt){
						digit = digit * 2;
					    if(digit > 9){
							digit = digit - 9;
						}
					}
					
					sum = sum + digit;
					doubleIt = !doubleIt;
				}
				
				return sum % 10 == 0; 
				
			}

            //Adds a leading zero to numbers below 10 (5 becomes "05")			
			static String pad(int number){
				return (number < 10) ? "0" + number :
				String.valueOf(number);
				
			}
		}