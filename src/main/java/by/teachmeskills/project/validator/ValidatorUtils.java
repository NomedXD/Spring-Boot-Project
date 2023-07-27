package by.teachmeskills.project.validator;


import java.util.regex.Pattern;

public class ValidatorUtils {
    private static boolean validateEmail(String email) {
        Pattern reg = Pattern.compile("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", Pattern.CASE_INSENSITIVE);
        return reg.matcher(email).matches();
    }

    private static boolean validateName(String name) {
        return name.matches("[a-zA-Z ,.'-]+");
    }

    private static boolean validateSurname(String surname) {
        return surname.matches("[a-zA-Z ,.'-]+");
    }

    private static boolean validatePassword(String password) {
        return password.matches("^(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{6,16}$");
    }

    private static boolean validatePasswordMatching(String password, String repeatPassword) {
        return password.equals(repeatPassword);
    }

    public static boolean validateRegistration(String email, String name, String surname, String password, String repeatPassword) {
        return validateEmail(email) && validateName(name) && validateSurname(surname) && validatePassword(password) &&
                validatePasswordMatching(password, repeatPassword);
    }
}
