package by.teachmeskills.project.exception;

import lombok.Getter;

import java.io.IOException;

@Getter
public class CSVExportException extends IOException {
    private final String returnPath;

    public CSVExportException(String message, String returnPath) {
        super(message);
        this.returnPath = returnPath;
    }
}
