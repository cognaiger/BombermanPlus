package com.bomberman.bombermanplus;

/**
 * Game configuration.
 */
public class Config {

    /* Game configuration */
    public static final int SCREEN_WIDTH = 1080;
    public static final int SCREEN_HEIGHT = 720;
    public static final int GAME_WORLD_WIDTH = 1488;
    public static final int GAME_WORLD_HEIGHT = 720;

    public static final int TILE_SIZE = 48;
    public static final String TITLE = "BOMBERMAN";
    public static final String VERSION = "1.0";

    /* UI configuration */
    public static final String FONT = "Retro Gaming.ttf";

    /* Attribute of player */
    public static final int START_POS_X = 48;
    public static final int START_POS_Y = 144;
    public static final int SPEED = 120;
    public static final int LIFE_PER_LEVEL = 3;
    public static final double ANIM_TIME_PlAYER = 0.3;
    public static final int SIZE_FRAME = 48;

    /* Game world attribute */
    public static final int START_LEVEL = 0;
    public static final int TIME_PER_LEVEL = 300;
    public static final int SCORE_ITEM = 20;
    public static final int MAX_LEVEL = 3;
    public static final int NUM_OF_ENEMIES = 10;
    public static final int ENEMY_SPEED_BASE = 70;
}
