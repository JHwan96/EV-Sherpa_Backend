package com.capstone2.EV_Sherpa.exception;

public class UserEmptyException extends IllegalStateException{
    public UserEmptyException(){
        super("존재하지 않는 회원입니다.");
    }
}
