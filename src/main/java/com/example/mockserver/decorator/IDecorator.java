package com.example.mockserver.decorator;

public interface IDecorator<T> {
    T decorate(T data);
}
