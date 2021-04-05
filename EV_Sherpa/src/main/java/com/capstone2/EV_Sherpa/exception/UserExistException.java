package com.capstone2.EV_Sherpa.exception;

public class UserExistException extends IllegalStateException{
    public UserExistException(){
        super("이미 존재하는 회원입니다.");
    }
}
