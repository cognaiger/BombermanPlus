package com.bomberman.bombermanplus.components.Enemy;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.pathfinding.astar.AStarMoveComponent;
import com.almasb.fxgl.texture.AnimationChannel;
import com.bomberman.bombermanplus.BombermanType;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.onCollisionBegin;
import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class OnealComponent extends NormalEnemy {
    private double oldX = 0;
    private double oldY = 0;

    protected boolean die = false;
    protected AStarMoveComponent astar;

    public OnealComponent() {
        super();
        onCollisionBegin(BombermanType.ONEAL_E, BombermanType.BOMB,
                (o, w) -> o.getComponent(OnealComponent.class).turn());

        onCollisionBegin(BombermanType.ONEAL_E, BombermanType.WALL,
                (o, w) -> o.getComponent(OnealComponent.class).turn());

        onCollisionBegin(BombermanType.ONEAL_E, BombermanType.AROUND_WALL,
                (o, w) -> o.getComponent(OnealComponent.class).turn());

        onCollisionBegin(BombermanType.ONEAL_E, BombermanType.BRICK,
                (o, br) -> o.getComponent(OnealComponent.class).turn());

        onCollisionBegin(BombermanType.ONEAL_E, BombermanType.GRASS,
                (o, gr) -> o.getComponent(OnealComponent.class).turn());

        onCollisionBegin(BombermanType.ONEAL_E, BombermanType.CORAL,
                (o, gr) -> o.getComponent(OnealComponent.class).turn());
    }

    @Override
    public void onUpdate(double tpf) {
        if (!die) {
            if (isDetectPlayer()) {
                astar.resume();
                moveAi();
            } else {
                astar.pause();
                moveNotAi(tpf);
            }
            setAnimationStage();
        }
    }

    protected void moveNotAi(double tpf) {
        // fix bug move
        double x = entity.getX() - oldX;
        double y = entity.getY() - oldY;

        oldX = entity.getX();
        oldY = entity.getY();

        if (x == 0 && y == 0) turn();
        //
        entity.setScaleUniform(0.9);
        entity.translateX(dx * tpf);
        entity.translateY(dy * tpf);
    }

    private void moveAi() {
        var player = FXGL.getGameWorld().getSingleton(BombermanType.PLAYER);

        int x = player.call("getCellX");
        int y = player.call("getCellY");

        astar.moveToCell(x, y);
    }

    @Override
    protected void setAnimationMove() {
        animDie = new AnimationChannel(image("sprites.png"), 16, SIZE_FLAME, SIZE_FLAME,
                Duration.seconds(ANIM_TIME), 38, 38);
        animWalkRight = new AnimationChannel(image("sprites.png"), 16, SIZE_FLAME, SIZE_FLAME,
                Duration.seconds(ANIM_TIME), 35, 37);
        animWalkLeft = new AnimationChannel(image("sprites.png"), 16, SIZE_FLAME, SIZE_FLAME,
                Duration.seconds(ANIM_TIME), 32, 34);
        animStop = new AnimationChannel(image("sprites.png"), 16, SIZE_FLAME, SIZE_FLAME,
                Duration.seconds(1), 32, 36);
    }

    @Override
    public void enemyDie() {
        super.enemyDie();

        int ONEAL_SCORE = 200;
        showScore(ONEAL_SCORE);
        inc("score", ONEAL_SCORE);
        die = true;
        astar.pause();
    }

    @Override
    public void enemyStop() {
        super.enemyStop();
        astar.pause();
    }
}

