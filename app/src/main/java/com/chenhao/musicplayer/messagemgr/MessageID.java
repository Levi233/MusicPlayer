package com.chenhao.musicplayer.messagemgr;

public enum MessageID {

    OBSERVER_MEDIA_PLAYER{
        public Class<? extends IObserverBase> getObserverClass(){
            return MediaPlayerObserver.class;
        }
    },OBSERVER_ID_RESERVE {
        public Class<? extends IObserverBase> getObserverClass() {
            return null;
        }
    };

    abstract Class<? extends IObserverBase> getObserverClass();
}