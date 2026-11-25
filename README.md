# SA ID Number Validator

A Java console program that checks whether a South African ID number is valid and decodes the information stored in it.

I built this as a first-year Computer Science (Diploma) student at Tshwane University of Technology to practise Java: strings, loops, classes, input handling and an algorithm.

## What it does

The program keeps asking for ID numbers until you type `exit`. For each number it runs these checks:

1. The number has exactly 13 characters.
2. Every character is a digit.
3. The date of birth (first 6 digits, YYMMDD) is a real date. This includes leap years, so 31 February is rejected.
4. The citizenship digit (digit 11) is 0 or 1.
5. The last digit passes the **Luhn check digit** algorithm.

If the number is valid, it prints the date of birth, gender and citizenship status. If not, it says which check failed.

## How an SA ID number is structured

| Digits | Meaning |
|---|---|
| 1-6 | Date of birth (YYMMDD) |
| 7-10 | Gender: 0000-4999 is female, 5000-9999 is male |
| 11 | Citizenship: 0 = SA citizen, 1 = permanent resident |
| 12 | Not used by this program |
| 13 | Check digit (Luhn algorithm) |

## Project structure

| File | Purpose |
|---|---|
| `IdValidator.java` | The class that validates an ID number and holds the decoded details |
| `IdChecker.java` | The console program (reads input, prints results) |
| `IdValidatorTests.java` | A simple test runner with valid and invalid test cases |

## How to run it

You need the Java Development Kit (JDK) installed.

```
javac IdChecker.java
java IdChecker
```

## Example

These are made-up test numbers, not real people's IDs.

```
SA ID Number Validator
Type an ID number to check it, or type exit to quit.

ID number: 9001015000085
Valid ID number.
  Date of birth: 1990-01-01
  Gender:        Male
  Citizenship:   SA citizen

ID number: 9001015000086
Invalid: The check digit is wrong.

ID number: exit
Goodbye!
```

## Running the tests

```
javac IdValidator.java IdValidatorTests.java
java IdValidatorTests
```

Test cases include:

| Input | Expected result |
|---|---|
| `9001015000085` | Valid (male, SA citizen) |
| `9001014000086` | Valid (female, SA citizen) |
| `9001015000086` | Invalid: the check digit is wrong |
| `0502290000087` | Invalid: 29 February 2005 is not a real date |
| `12345` | Invalid: must have 13 digits |
| `12345678901ab` | Invalid: can only contain digits |
| *(empty)* | Invalid: must have 13 digits |

## What I learned

- Reading user input with `Scanner` and looping until the user quits
- Working with `String` methods such as `substring()` and `charAt()`
- Validating dates with `java.time.LocalDate`
- Designing a class with a constructor, private methods and getters
- Implementing the Luhn algorithm
- Writing test cases for valid and invalid input

## Limitations

This program only checks that a number is **structurally valid**. It cannot tell whether the number belongs to a real person, and it does not connect to Home Affairs, credit bureaus or any other database. Do not use real people's ID numbers when testing.

## Ideas for improvement

- Move the tests to JUnit
- Read many ID numbers from a file and report which are valid
- Build a simple graphical or web version

## Author

Asakundwi Mpete, Computer Science student at TUT
