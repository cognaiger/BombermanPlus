package com.bomberman.bombermanplus.components.Enemy;

import com.almasb.fxgl.texture.AnimationChannel;
import com.bomberman.bombermanplus.BombermanType;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.*;

public class PassComponent extends OnealComponent{
    public PassComponent() {
        super();
        onCollisionBegin(BombermanType.PASS_E, BombermanType.AROUND_WALL,
                (p, w) -> p.getComponent(PassComponent.class).turn());

        onCollisionBegin(BombermanType.PASS_E, BombermanType.WALL,
                (p, w) -> p.getComponent(PassComponent.class).turn());

        onCollisionBegin(BombermanType.PASS_E, BombermanType.BRICK,
                (p, br) -> p.getComponent(PassComponent.class).turn());

        onCollisionBegin(BombermanType.PASS_E, BombermanType.GRASS,
                (p, gr) -> p.getComponent(PassComponent.class).turn());

        onCollisionBegin(BombermanType.PASS_E, BombermanType.CORAL,
                (p, co) -> p.getComponent(PassComponent.class).turn());

        onCollisionBegin(BombermanType.PASS_E, BombermanType.BOMB,
                (p, w) -> p.getComponent(PassComponent.class).turn());

        setRangeDetectPlayer(120);
    }

    @Override
    public void onUpdate(double tpf) {
        super.onUpdate(tpf);
    }


    @Override
    protected void setAnimationMove() {
        animDie = new AnimationChannel(image("sprites.png"), 16, TILE_SIZE, TILE_SIZE,
                Duration.seconds(ANIM_TIME), 70, 70);
        animWalkRight = new AnimationChannel(image("sprites.png"), 16, TILE_SIZE, TILE_SIZE,
                Duration.seconds(ANIM_TIME), 67, 69);
        animWalkLeft = new AnimationChannel(image("sprites.png"), 16, TILE_SIZE, TILE_SIZE,
                Duration.seconds(ANIM_TIME), 64, 66);
        animStop = new AnimationChannel(image("sprites.png"), 16, TILE_SIZE, TILE_SIZE,
                Duration.seconds(1), 64, 69);
    }

    @Override
    public void enemyDie() {
        int PASS_SCORE = 300;
        showScore(PASS_SCORE);
        inc("score", PASS_SCORE);

        die = true;
        dx = 0;
        dy = 0;
        astar.pause();
        texture.loopNoOverride(animDie);
    }
}
