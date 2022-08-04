package com.example.mockserver.observer;

import com.example.mockserver.model.MockContext;

public interface IObserver<T> {
    void update(T t);
}
