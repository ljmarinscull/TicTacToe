package com.tictactoe.dagger;

import com.tictactoe.MainActivity;

import dagger.Component;

@Component(modules = ModuleApp.class)
public interface ComponentApp {

    void inject(MainActivity activity);
}