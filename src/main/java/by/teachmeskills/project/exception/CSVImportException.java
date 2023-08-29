package by.teachmeskills.project.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.IOException;

@Getter
public class CSVImportException extends IOException {
    String returnPath;

    public CSVImportException(String message, String returnPath) {
        super(message);
        this.returnPath = returnPath;
    }
}
