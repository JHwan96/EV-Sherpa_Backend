package com.capstone2.EV_Sherpa.exception;

public class PasswordWrongException extends RuntimeException{
    public PasswordWrongException(){
        super("잘못된 비밀번호 입니다.");
    }
}
