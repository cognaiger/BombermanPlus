package com.bomberman.bombermanplus.components.Enemy;

import com.almasb.fxgl.texture.AnimationChannel;
import com.bomberman.bombermanplus.BombermanType;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.onCollisionBegin;
import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class BalloomComponent extends NormalEnemy{
    public BalloomComponent(){
        super();
        onCollisionBegin(BombermanType.BALLOOM_E, BombermanType.WALL,
                (b, w) -> b.getComponent(BalloomComponent.class).turn());

        onCollisionBegin(BombermanType.BALLOOM_E, BombermanType.BOMB,
                (b, bo) -> b.getComponent(BalloomComponent.class).turn());

        onCollisionBegin(BombermanType.BALLOOM_E, BombermanType.AROUND_WALL,
                (b, w) -> b.getComponent(BalloomComponent.class).turn());

        onCollisionBegin(BombermanType.BALLOOM_E, BombermanType.BRICK,
                (b, br) -> b.getComponent(BalloomComponent.class).turn());

        onCollisionBegin(BombermanType.BALLOOM_E, BombermanType.GRASS,
                (b, gr) -> b.getComponent(BalloomComponent.class).turn());

        onCollisionBegin(BombermanType.BALLOOM_E, BombermanType.CORAL,
                (b, co) -> b.getComponent(BalloomComponent.class).turn());
    }

    @Override
    protected void setAnimationMove(){
        animDie = new AnimationChannel(image("sprites.png"), 16, SIZE_FLAME, SIZE_FLAME,
                Duration.seconds(ANIM_TIME), 22, 22);
        animWalkRight = new AnimationChannel(image("sprites.png"), 16, SIZE_FLAME, SIZE_FLAME,
                Duration.seconds(ANIM_TIME), 19, 21);
        animWalkLeft = new AnimationChannel(image("sprites.png"), 16, SIZE_FLAME, SIZE_FLAME,
                Duration.seconds(ANIM_TIME), 16, 18);
        animStop = new AnimationChannel(image("sprites.png"), 16, SIZE_FLAME, SIZE_FLAME,
                Duration.seconds(1), 16, 22);
    }

    @Override
    public void enemyDie() {
        super.enemyDie();

        int BALLOOM_SCORE = 100;
        showScore(BALLOOM_SCORE);
        inc("score", BALLOOM_SCORE);
    }
}

