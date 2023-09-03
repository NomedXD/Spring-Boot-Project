package by.teachmeskills.project.exception;

import lombok.Getter;

@Getter
public class NoSuchProductException extends IllegalArgumentException{
    private Integer productId;
    public NoSuchProductException(String message, Integer productId) {
        super(message);
        this.productId = productId;
    }
}
