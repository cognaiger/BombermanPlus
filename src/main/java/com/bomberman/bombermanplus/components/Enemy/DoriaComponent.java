package com.bomberman.bombermanplus.components.Enemy;

import com.almasb.fxgl.texture.AnimationChannel;
import com.bomberman.bombermanplus.BombermanType;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.*;

public class DoriaComponent extends OnealComponent{
    private double timeChangeMove = 0.0;

    public DoriaComponent() {
        super();
        onCollisionBegin(BombermanType.DORIA_E, BombermanType.AROUND_WALL,
                (d, w) -> d.getComponent(DoriaComponent.class).turn());

        onCollisionBegin(BombermanType.DORIA_E, BombermanType.WALL,
                (d, w) -> d.getComponent(DoriaComponent.class).turn());

        onCollisionBegin(BombermanType.DORIA_E, BombermanType.BOMB,
                (d, w) -> d.getComponent(DoriaComponent.class).turn());

        setRangeDetectPlayer(120);
    }

    @Override
    public void onUpdate(double tpf) {
        super.onUpdate(tpf);
        timeChangeMove += tpf;
        if (!isDetectPlayer() && timeChangeMove > 3.0) {
            timeChangeMove = 0;
            turn();
        }
    }


    @Override
    protected void setAnimationMove() {
        animDie = new AnimationChannel(image("sprites.png"), 16, TILE_SIZE, TILE_SIZE,
                Duration.seconds(ANIM_TIME), 30, 30);
        animWalkRight = new AnimationChannel(image("sprites.png"), 16, TILE_SIZE, TILE_SIZE,
                Duration.seconds(ANIM_TIME), 27, 29);
        animWalkLeft = new AnimationChannel(image("sprites.png"), 16, TILE_SIZE, TILE_SIZE,
                Duration.seconds(ANIM_TIME), 24, 26);
        animStop = new AnimationChannel(image("sprites.png"), 16, TILE_SIZE, TILE_SIZE,
                Duration.seconds(1), 24, 30);
    }

    @Override
    public void enemyDie() {
        int DORIA_SCORE = 500;
        inc("score", DORIA_SCORE);
        showScore(DORIA_SCORE);
        die = true;
        dx = 0;
        dy = 0;
        astar.pause();
        texture.loopNoOverride(animDie);
    }
}
