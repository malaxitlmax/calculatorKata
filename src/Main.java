public class Main {
    public static String calc(String input) throws DifferentNumberSystems, InvalidOperation, InvalidNumber, InvalidInput {
        String[] separated = input.split(" ");
        if (separated.length != 3) {
            throw new InvalidInput("Invalid input:\n" + input
                    + "\ninput must follow the pattern a *operation* b.");
        }
        String a = separated[0];
        String b = separated[2];
        String operation = separated[1];
        boolean aisArabic = isArabic(a);
        boolean bisArabic = isArabic(b);

        if (aisArabic != bisArabic) {
            throw new DifferentNumberSystems("Different number systems is not allowed: " + "a = " + a + ", b = " + b);
        }

        if (aisArabic) {
            if (Integer.parseInt(a) > 10 || Integer.parseInt(a) < 1 || Integer.parseInt(b) > 10 || Integer.parseInt(b) < 1) {
                throw new InvalidNumber("Numbers must meet the criteria, 1 <= n <= 10: " + "a = " + a + ", b = " + b);
            }
            return calcArabic(Integer.parseInt(a), operation, Integer.parseInt(b));
        }

        if (romanToArabic(a) > 10 || romanToArabic(a) < 1 || romanToArabic(b) > 10 || romanToArabic(b) < 1) {
            throw new InvalidNumber("Numbers must meet the criteria, I <= n <= X: " + "a = " + a + ", b = " + b);
        }

        return calcRoman(a, operation, b);
    }

    public static String calcArabic(int a, String operation, int b) throws InvalidOperation {
        String result;

        switch (operation) {
            case "+" -> result = String.valueOf(a + b);
            case "-" -> result = String.valueOf(a - b);
            case "*" -> result = String.valueOf(a * b);
            case "/" -> result = String.valueOf(a / b);
            default -> throw new InvalidOperation("Only acceptable operations is: +, -, *, /.");
        }

        return result;
    }

    public static String calcRoman(String a, String operation, String b) throws InvalidOperation, InvalidRomanNumber {
        String result = calcArabic(romanToArabic(a), operation, romanToArabic(b));
        if (Integer.parseInt(result) <= 0) {
            throw new InvalidOperation("Invalid operation with roman numbers, negative or zero result is illegal: " +
                    a + " " + operation + " " + b);
        }
        return arabicToRoman(calcArabic(romanToArabic(a), operation, romanToArabic(b)));
    }

    private static String arabicToRoman(String arabicNumber) {
        int number = Integer.parseInt(arabicNumber);
        StringBuilder sb = new StringBuilder();
        while (number >= 100) {
            sb.append("C");
            number -= 100;
        }
        while (number >= 50) {
            sb.append("L");
            number -= 50;
        }
        while (number >= 10) {
            sb.append("X");
            number -= 10;
        }
        while (number >= 5) {
            sb.append("V");
            number -= 5;
        }
        while (number >= 1) {
            sb.append("I");
            number -= 1;
        }
        String result = sb.toString();
        result = result.replace("IIII", "IV");
        result = result.replace("VIV", "IX");
        result = result.replace("XXXX", "XL");
        result = result.replace("LXL", "XC");
        return result;
    }

    public static int romanToArabic(String number) throws InvalidRomanNumber {
        int result = 0;
        int lastDigit = 0;
        for (char currentDigit : reverseString(number).toCharArray()) {
            switch (currentDigit) {
                case 'I' -> {
                    if (lastDigit <= 1) {
                        result += 1;
                    } else {
                        result -= 1;
                    }
                    lastDigit = 1;
                }
                case 'V' -> {
                    if (lastDigit <= 5) {
                        result += 5;
                    } else {
                        result -= 5;
                    }
                    lastDigit = 5;
                }
                case 'X' -> {
                    if (lastDigit <= 10) {
                        result += 10;
                    } else {
                        result -= 10;
                    }
                    lastDigit = 10;
                }
                default -> {
                    throw new InvalidRomanNumber("Invalid roman number: " + number);
                }
            }
        }
        return result;
    }

    public static String reverseString(String str){
        StringBuilder sb=new StringBuilder(str);
        sb.reverse();
        return sb.toString();
    }

    public static boolean isArabic(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void main(String[] args) {
        try {
            System.out.println("Test romanToArabic:");
            System.out.println("I = " + romanToArabic("I")); // 1
            System.out.println("II = " + romanToArabic("II")); // 2
            System.out.println("III = " + romanToArabic("III")); // 3
            System.out.println("IV = " + romanToArabic("IV")); // 4
            System.out.println("V = " + romanToArabic("V")); // 5
            System.out.println("VI = " + romanToArabic("VI")); // 6
            System.out.println("VII = " + romanToArabic("VII")); // 7
            System.out.println("VIII = " + romanToArabic("VIII")); // 8
            System.out.println("IX = " + romanToArabic("IX")); // 9
            System.out.println("X = " + romanToArabic("X")); // 10
            System.out.println("XI = " + romanToArabic("XI")); // 11


            System.out.println("Test arabicToRoman:");
            for (int i = 1; i <= 100; i++) {
                System.out.println(i + " = " + arabicToRoman(String.valueOf(i)));
            }

            System.out.println("Test calcArabic:");
            System.out.print("1 + 1 = ");
            System.out.println(calc("1 + 1"));

            System.out.print("10 + 10 = ");
            System.out.println(calc("10 + 10"));

            System.out.print("3 - 10 = ");
            System.out.println(calc("3 - 10"));

            System.out.print("3 * 10 = ");
            System.out.println(calc("3 * 10"));

            System.out.print("7 / 3 = ");
            System.out.println(calc("7 / 3"));

            System.out.println("Test calcRoman:");
            System.out.println("I + I = " + calc("I + I"));
            System.out.println("IV + X = " + calc("IV + X"));
            System.out.println("VI / III = " + calc("VI / III"));
            System.out.println("VII / II = " + calc("VII / II"));
            System.out.println("X * IX = " + calc("X * IX"));
            System.out.println("VII * VII = " + calc("VII * VII"));

            try {
                System.out.println("XI - I = " + calc("XI - I"));
            } catch (InvalidNumber invalidNumber) {
                System.out.println(invalidNumber.getMessage());
            }

            try {
                System.out.println("I - I = " + calc("I - I"));
            } catch (InvalidOperation invalidOperation) {
                System.out.println(invalidOperation.getMessage());
            }

            try {
                System.out.println("11 - 1 = " + calc("11 - 1"));
            } catch (InvalidNumber invalidNumber) {
                System.out.println(invalidNumber.getMessage());
            }

            try {
                System.out.println("3 - II = " + calc("3 - II"));
            } catch (DifferentNumberSystems differentNumberSystems) {
                System.out.println(differentNumberSystems.getMessage());
            }

            try {
                System.out.println("1+1 = " + calc("1+1"));
            } catch (InvalidInput invalidInput) {
                System.out.println(invalidInput.getMessage());
            }

            try {
                System.out.println("This is not working" + calc("This is not working"));
            } catch (InvalidInput invalidInput) {
                System.out.println(invalidInput.getMessage());
            }

            try {
                System.out.println("1 1 1" + calc("1 1 1"));
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
            }


        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
}

