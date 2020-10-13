package com.java.zolo.instagram.validators;
public class Validator<T> {

    private String failureMessage;
    private T result;
    private Boolean isPassed;

    private Validator() {

    }

    private Validator(String failureMessage, T result, Boolean isPassed) {
        this.isPassed = isPassed;
        this.failureMessage = failureMessage;
        this.result = result;
    }

    public static <T> Validator<T> validate(boolean expression, String failureMessage,T result) {
        return new Validator<T>(failureMessage,result, expression);
    }

    public boolean isPassed() {
        return this.isPassed;
    }

    public String getFailureMessage() {
        return this.failureMessage;
    }

    public T getResult() {
        return this.result;
    }

    public Validator<T> andThen(Validator<T> validator) {
        if(this.isFailed() && validator.isFailed())
            concatValidator(validator);
        if(this.isFailed())
            return this;
        return validator;
    }

    public Validator<T> or(Validator<T> validator) {
        if(this.isPassed()) return this;
        else if(validator.isPassed()) return validator;
        return concatValidator(validator);
    }

    private Validator<T> concatValidator(Validator<T> validator) {
        return new Validator<T>(this.failureMessage.concat("\n").concat(validator.getFailureMessage()), this.result, false);
    }

    public <R> Validator<?> and(Validator<R> validator) {
        if(!this.isPassed()) return this;
        if(!this.isPassed()) return validator;
        return validator;
    }

    public Boolean isFailed() {
        return !this.isPassed;
    }

    public Validator<T> appendMessage(String message) {
        this.failureMessage = this.failureMessage.concat(" ").concat(message);
        return this;
    }
}


