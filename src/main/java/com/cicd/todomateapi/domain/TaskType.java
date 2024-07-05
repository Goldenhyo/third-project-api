package com.cicd.todomateapi.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TaskType {
    NORMAL("STATUS_NORMAL"), ROUTINE("STATUS_ROUTINE");

    private final String value;

    public String getValue(){
        return value;
    }

}
