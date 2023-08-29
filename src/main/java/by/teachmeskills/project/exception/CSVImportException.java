package by.teachmeskills.project.exception;

import java.io.IOException;

public class CSVImportException extends IOException {
    String returnPath;
    public CSVImportException(String message, String returnPath) {
        super(message);
        this.returnPath = returnPath;
    }
}
