package com.example.springbootresttemplatejbpmrestapi.model;

public class GenericRequest<T> {
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
