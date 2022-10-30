package com.bomberman.bombermanplus.components;

public enum Skin {
    NORMAL, FLAME_PASS, CHAR2, CHAR2IMMO;

    public static Skin fromInteger(int x) {
        switch(x) {
            case 0:
                return NORMAL;
            case 1:
                return FLAME_PASS;
            case 2:
                return CHAR2;
            case 3:
                return CHAR2IMMO;
        }
        return null;
    }
}
