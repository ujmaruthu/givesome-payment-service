package com.givesome.payment.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
    @Setter
    @NoArgsConstructor
    public class ResponseModel<T> {
        private String message;
        private int status;
        private T data;
    }

