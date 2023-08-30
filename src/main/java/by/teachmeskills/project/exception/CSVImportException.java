package by.teachmeskills.project.exception;

import lombok.Getter;

import java.io.IOException;

@Getter
public class CSVImportException extends IOException {
    private final String returnPath;

    public CSVImportException(String message, String returnPath) {
        super(message);
        this.returnPath = returnPath;
    }
}
