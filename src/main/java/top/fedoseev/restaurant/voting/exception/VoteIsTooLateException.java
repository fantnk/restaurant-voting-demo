package top.fedoseev.restaurant.voting.exception;

import java.time.LocalTime;

public class VoteIsTooLateException extends BaseException {
    public VoteIsTooLateException(LocalTime tillTime) {
        super(ErrorMessage.VOTING_HAS_FINISHED, tillTime);
    }
}
