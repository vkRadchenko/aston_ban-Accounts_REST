package ru.radchenko.BankRestService.exceptions;

public class UserNotCreatedException extends RuntimeException{
    public UserNotCreatedException(String message){
        super(message);
    }
}
