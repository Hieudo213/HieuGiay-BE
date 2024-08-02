package com.example.hieugiaybe.exception;

public class FileExistsException extends RuntimeException{
    public FileExistsException(String message){
        super(message);
    }
}
