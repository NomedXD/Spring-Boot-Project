package by.teachmeskills.project.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.IOException;

@Getter
@AllArgsConstructor
public class CSVExportException extends IOException {
    String returnPath;
    public CSVExportException(String message, String returnPath) {
        super(message);
        this.returnPath = returnPath;
    }
}
