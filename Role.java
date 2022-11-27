package com.company;

public enum Role {
    PACIENTAS(true),
    ADMIN (false),
    GYDYTOJAS(true);

    Role(boolean isDeletable) {
        this.isDeletable = isDeletable;
    }

    private boolean isDeletable;

    public boolean isDeletable (){
        return isDeletable;
    }
}
