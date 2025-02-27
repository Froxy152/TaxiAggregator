package by.shestakov.ratingservice.exception;

import by.shestakov.ratingservice.util.ExceptionMessage;

public class OnlyOneCommentOnRideException extends RuntimeException {
    public OnlyOneCommentOnRideException() {
        super(ExceptionMessage.ONLY_ONE_MESSAGE);
    }
}
