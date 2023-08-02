package by.teachmeskills.project.validator;

public class ValidatorUtils {
    public static boolean validatePasswordMatching(String password, String repeatPassword) {
        return password.equals(repeatPassword);
    }
}
