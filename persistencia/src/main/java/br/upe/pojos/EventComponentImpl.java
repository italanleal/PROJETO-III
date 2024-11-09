package br.upe.pojos;

import java.util.UUID;

public class EventComponentImpl extends EventComponent {

    @Override
    public UUID getUuid() {
        return uuid;
    }

    @Override
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public UUID getEventUuid() {
        return eventUuid;
    }

    @Override
    public void setEventUuid(UUID eventUuid) {
        this.eventUuid = eventUuid;
    }

    @Override
    public String getDescritor() {
        return descritor;
    }

    @Override
    public void setDescritor(String descritor) {
        this.descritor = descritor;
    }
}
