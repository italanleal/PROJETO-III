package br.upe.util;

import br.upe.entities.Event;

public interface KeeperInterface {
    public static Event createEvent(){
        return new Event();
    }
}
