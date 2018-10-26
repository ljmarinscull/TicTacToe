package com.tictactoe.dagger;

import com.tictactoe.view.GameView;
import dagger.Module;
import dagger.Provides;

@Module
public class ModuleApp {

    private final GameView mView;

    public ModuleApp(GameView view){
        this.mView = view;
    }

    @Provides
    public GameView provideGameView(){
        return mView;
    }
}