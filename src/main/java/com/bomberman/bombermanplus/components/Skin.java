package com.bomberman.bombermanplus.components;

public enum Skin {
    NORMAL, FLAME_PASS, CHAR2, CHAR2IMMO, CHAR3, CHAR4, CHAR5, CHAR6;

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
            case 4:
                return CHAR3;
            case 6:
                return CHAR4;
            case 8:
                return CHAR5;
            case 10:
                return CHAR6;
        }
        return null;
    }
}
