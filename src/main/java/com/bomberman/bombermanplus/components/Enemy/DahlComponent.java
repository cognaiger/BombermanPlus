package com.bomberman.bombermanplus.components.Enemy;

import com.almasb.fxgl.texture.AnimationChannel;
import com.bomberman.bombermanplus.BombermanType;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.bomberman.bombermanplus.Config.ENEMY_SPEED_BASE;

public class DahlComponent extends NormalEnemy {
    public DahlComponent() {
        super();
        onCollisionBegin(BombermanType.DAHL_E, BombermanType.WALL,
                (d, w) -> d.getComponent(DahlComponent.class).turn());

        onCollisionBegin(BombermanType.DAHL_E, BombermanType.BOMB,
                (d, b) -> d.getComponent(DahlComponent.class).turn());

        onCollisionBegin(BombermanType.DAHL_E, BombermanType.AROUND_WALL,
                (d, w) -> d.getComponent(DahlComponent.class).turn());

        onCollisionBegin(BombermanType.DAHL_E, BombermanType.BRICK,
                (d, br) -> d.getComponent(DahlComponent.class).turn());

        onCollisionBegin(BombermanType.DAHL_E, BombermanType.GRASS,
                (d, gr) -> d.getComponent(DahlComponent.class).turn());

        onCollisionBegin(BombermanType.DAHL_E, BombermanType.CORAL,
                (d, c) -> d.getComponent(DahlComponent.class).turn());

    }

    @Override
    protected void setAnimationMove() {
        animDie = new AnimationChannel(image("sprites.png"), 16, TILE_SIZE, TILE_SIZE,
                Duration.seconds(ANIM_TIME), 54, 54);
        animWalkRight = new AnimationChannel(image("sprites.png"), 16, TILE_SIZE, TILE_SIZE,
                Duration.seconds(ANIM_TIME), 51, 53);
        animWalkLeft = new AnimationChannel(image("sprites.png"), 16, TILE_SIZE, TILE_SIZE,
                Duration.seconds(ANIM_TIME), 48, 50);
        animStop = new AnimationChannel(image("sprites.png"), 16, TILE_SIZE, TILE_SIZE,
                Duration.seconds(1), 48, 53);
    }

    @Override
    protected double getRandom() {
        double coefficient = 0;
        while (coefficient < 0.7) {
            coefficient = Math.random();
        }
        double speed = ENEMY_SPEED_BASE * (coefficient + (Math.random() > 0.5 ? 0 : 0.8));
        return Math.random() > 0.5 ? speed : -speed;
    }

    @Override
    public void enemyDie() {
        super.enemyDie();

        int DAHL_SCORE = 150;
        showScore(DAHL_SCORE);
        inc("score", DAHL_SCORE);
    }
}
