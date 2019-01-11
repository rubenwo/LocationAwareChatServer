package com.Services;

public interface IObservable {
    /**
     * @param observer
     */
    void subscribe(IObserver observer);

    /**
     * @param observer
     */
    void unsubscribe(IObserver observer);
}
