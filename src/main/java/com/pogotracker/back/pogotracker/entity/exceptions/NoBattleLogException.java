package com.pogotracker.back.pogotracker.entity.exceptions;

public class NoBattleLogException extends RuntimeException {
    public NoBattleLogException(String message) {
        super(message);
    }
}