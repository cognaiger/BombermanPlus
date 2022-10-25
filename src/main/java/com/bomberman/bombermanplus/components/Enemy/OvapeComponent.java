package com.bomberman.bombermanplus.components.Enemy;


import com.almasb.fxgl.texture.AnimationChannel;
import com.bomberman.bombermanplus.BombermanType;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.bomberman.bombermanplus.constants.GameConst.ENEMY_SPEED_BASE;

public class OvapeComponent extends NormalEnemy {
    private double timeChangeMove = 0;
    private double timeNotDetectPlayer = 0;

    public OvapeComponent() {
        super();
        onCollisionBegin(BombermanType.OVAPE_E, BombermanType.AROUND_WALL,
                (o, w) -> o.getComponent(OvapeComponent.class).turn());

        onCollisionBegin(BombermanType.OVAPE_E, BombermanType.WALL,
                (o, w) -> o.getComponent(OvapeComponent.class).turn());

        onCollisionBegin(BombermanType.OVAPE_E, BombermanType.BOMB,
                (o, b) -> o.getComponent(OvapeComponent.class).turn());

        setRangeDetectPlayer(80);
    }

    @Override
    public void onUpdate(double tpf) {
        super.onUpdate(tpf);
        timeChangeMove += tpf;
        if (timeChangeMove > 3.0) {
            timeChangeMove = 0;
            turn();
        }

        timeNotDetectPlayer += tpf;
        if (isDetectPlayer() && timeNotDetectPlayer > 5.0) {
            timeNotDetectPlayer = 0;
            turn();
        }
    }

    protected double getRandom() {
        return Math.random() > 0.5 ? (ENEMY_SPEED_BASE * 1.3) : -(ENEMY_SPEED_BASE * 1.3);
    }

    @Override
    protected void setAnimationMove() {
        animDie = new AnimationChannel(image("sprites.png"), 16, SIZE_FLAME, SIZE_FLAME,
                Duration.seconds(ANIM_TIME), 86, 86);
        animWalkRight = new AnimationChannel(image("sprites.png"), 16, SIZE_FLAME, SIZE_FLAME,
                Duration.seconds(ANIM_TIME), 83, 85);
        animWalkLeft = new AnimationChannel(image("sprites.png"), 16, SIZE_FLAME, SIZE_FLAME,
                Duration.seconds(ANIM_TIME), 80, 82);
        animStop = new AnimationChannel(image("sprites.png"), 16, SIZE_FLAME, SIZE_FLAME,
                Duration.seconds(1), 80, 85);
    }

    @Override
    public void enemyDie() {
        super.enemyDie();

        int OVAPE_SCORE = 400;
        showScore(OVAPE_SCORE);
        inc("score", OVAPE_SCORE);
    }
}
