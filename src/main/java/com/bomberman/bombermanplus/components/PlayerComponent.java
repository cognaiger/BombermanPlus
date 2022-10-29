package com.bomberman.bombermanplus.components;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.PhysicsWorld;
import com.bomberman.bombermanplus.BombermanType;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.geti;
import static com.almasb.fxgl.dsl.FXGL.inc;
import static com.almasb.fxgl.dsl.FXGL.set;
import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import static com.bomberman.bombermanplus.Config.*;

public class PlayerComponent extends Component {

    private int bombsPlaced = 0;
    private boolean exploreCancel = false;
    private double lastX = 0;
    private double lastY = 0;
    private double timeWalk = 1;

    private PlayerStatus curMove = PlayerStatus.STOP;
    private PhysicsComponent physics;
    private int speed = FXGL.geti("speed");

    /**
     * Constructor.
     * Collision between player and items.
     */
    public PlayerComponent() {
        PhysicsWorld physics = getPhysicsWorld();
        physics.setGravity(0, 0);

        onCollisionBegin(BombermanType.PLAYER, BombermanType.SPEED_ITEM, (p, speed_i) -> {
            play("power_up.wav");
            speed_i.removeFromWorld();
            inc("score", SCORE_ITEM);
            inc("speed", SPEED / 3);
            speed = geti("speed");
            getGameTimer().runOnceAfter(() -> {
                    inc("speed", -SPEED / 3);
                    speed = geti("speed");
            }, Duration.seconds(8));
            return null;
        });

        onCollisionBegin(BombermanType.PLAYER, BombermanType.BOMB_ITEM, (p, bombs_t) -> {
            play("power_up.wav");
            bombs_t.removeFromWorld();
            inc("score", SCORE_ITEM);
            inc("bomb", 1);
            return null;
        });

        onCollisionBegin(BombermanType.PLAYER, BombermanType.FLAME_ITEM, (p, flame_i) -> {
            play("power_up.wav");
            flame_i.removeFromWorld();
            inc("score", SCORE_ITEM);
            inc("flame", 1);
            return null;
        });

        onCollisionBegin(BombermanType.PLAYER, BombermanType.FLAME_PASS_ITEM, (p, flame_pass_i) -> {
            play("power_up.wav");
            flame_pass_i.removeFromWorld();
            set("immortality", true);
            flame_pass_i.removeFromWorld();
            inc("score", SCORE_ITEM);
            getEntity().getComponent(PlayerImgComponent.class).setAnimation(Skin.FLAME_PASS);
            getGameTimer().runOnceAfter(() -> {
                getEntity().getComponent(PlayerImgComponent.class).setAnimation(Skin.NORMAL);
                set("immortality", false);
            }, Duration.seconds(8));
            return null;
        });
    }

    public PlayerStatus getCurMove() {
        return curMove;
    }

    public void setCurMove(PlayerStatus curMove) {
        this.curMove = curMove;
    }

    public boolean isExploreCancel() {
        return exploreCancel;
    }

    public void setExploreCancel(boolean exploreCancel) {
        this.exploreCancel = exploreCancel;
    }

    @Override
    public void onUpdate(double tbf) {
        getEntity().setScaleUniform(0.95);
        if (physics.getVelocityX() != 0) {
            physics.setVelocityX((int) physics.getVelocityX() * 0.9);
            if (FXGLMath.abs(physics.getVelocityX()) < 1) {
                physics.setVelocityX(0);
            }
        }

        if (physics.getVelocityY() != 0) {
            physics.setVelocityY((int) physics.getVelocityY() * 0.9);
            if (FXGLMath.abs(physics.getVelocityY()) < 1) {
                physics.setVelocityY(0);
            }
        }

        /* Handle sound when walking */
        timeWalk += tbf;
        double dx = entity.getX() - lastX;
        double dy = entity.getY() - lastY;
        lastX = entity.getX();
        lastY = entity.getY();
        if (timeWalk > 0.6) {
            timeWalk = 0;
            if (!(dx == 0 && dy == 0)) {
                if (curMove == PlayerStatus.DOWN || curMove == PlayerStatus.UP) {
                    play("walk_1.wav");
                } else {
                    play("walk_2.wav");
                }
            }
        }
    }

    /**
     * Change status to UP.
     */
    public void up() {
        if (curMove != PlayerStatus.DIE) {
            curMove = PlayerStatus.UP;
            physics.setVelocityY(-SPEED);
        }
    }

    /**
     * Change status to DOWN.
     */
    public void down() {
        if (curMove != PlayerStatus.DIE) {
            curMove = PlayerStatus.DOWN;
            physics.setVelocityY(SPEED);
        }
    }

    /**
     * Change status to RIGHT.
     */
    public void right() {
        if (curMove != PlayerStatus.DIE) {
            curMove = PlayerStatus.RIGHT;
            physics.setVelocityX(SPEED);
        }
    }

    /**
     * Change status to LEFT.
     */
    public void left() {
        if (curMove != PlayerStatus.DIE) {
            curMove = PlayerStatus.LEFT;
            physics.setVelocityX(-SPEED);
        }
    }

    /**
     * Change status to STOP.
     */
    public void stop() {
        if (curMove != PlayerStatus.DIE) {
            curMove = PlayerStatus.STOP;
        }
    }

    /**
     * Change status to DIE.
     */
    public void die() {
        curMove = PlayerStatus.DIE;
    }

    /**
     * Place bomb and explode.
     */
    public void placeBomb() {
        play("placed_bomb.wav");
        if (bombsPlaced == geti("bomb")) {
            return;
        }
        bombsPlaced++;
        int bombX = (int) (entity.getX() % TILE_SIZE > TILE_SIZE / 2
                           ? entity.getX() + TILE_SIZE - entity.getX() % TILE_SIZE + 1
                           : entity.getX() - entity.getX() % TILE_SIZE + 1);
        int bombY = (int) (entity.getY() % TILE_SIZE > TILE_SIZE / 2
                ? entity.getY() + TILE_SIZE - entity.getY() % TILE_SIZE + 1
                : entity.getY() - entity.getY() % TILE_SIZE + 1);

        Entity bomb = spawn("bomb", new SpawnData(bombX, bombY));

        if (curMove != PlayerStatus.DIE) {
            getGameTimer().runOnceAfter(() -> {
                if(!exploreCancel) {
                    play("bomb_exploded.wav");
                    bomb.getComponent(BombComponent.class).explode();
                } else {
                    bomb.removeFromWorld();
                }
                bombsPlaced--;
            }, Duration.seconds(2.5));
        }
    }
}
