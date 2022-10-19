package com.bomberman.bombermanplus.Enemy;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.BoundingBoxComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;

import static com.bomberman.bombermanplus.constants.GameConst.ENEMY_SPEED_BASE;

public abstract class Normal extends Component {
    private double lastX = 0;
    private double lastY = 0;

    protected double dx = ENEMY_SPEED_BASE;
    protected double dy = 0;

    protected final AnimatedTexture texture;
    protected static final double ANIMATION_TIME = 0.5;
    protected static final int SIZE_FLAME = 48;

    protected AnimationChannel AnimationWalkRight;
    protected AnimationChannel AnimationWalkLeft;
    protected AnimationChannel AnimationDie;
    protected AnimationChannel AnimationStop;

    protected int RangeDetectPlayer = 60;

    public Normal(){
        setAnimationMove();
        texture = new AnimatedTexture(AnimationWalkRight);
        texture.loop();
    }

    public abstract void setAnimationMove();

    @Override
    public void onAdded(){
        entity.getViewComponent().addChild(texture);
    }

    @Override
    public void onUpdate(double tpf){
        entity.setScaleUniform(0.9);
        entity.translateX(dx * tpf);
        entity.translateY(dy * tpf);

        setAnimationStage();
    }

    protected void setAnimationStage(){
        double dx = entity.getX() - lastX;
        double dy = entity.getY() - lastY;
        lastX = entity.getX();
        lastY = entity.getY();

        if(dx == 0 && dy ==0){
            return;
        }
        if(Math.abs(dx) > Math.abs(dy)){
            if(dx > 0){
                texture.loopNoOverride(AnimationWalkRight);
            }
            else{
                texture.loopNoOverride(AnimationWalkLeft);
            }
        } else{
            if(dy > 0){
                texture.loopNoOverride(AnimationWalkLeft);
            }
            else {
                texture.loopNoOverride(AnimationWalkRight);
            }
        }
    }
    private void setTurnEnemy(TurnDirection direct){
        switch (direct){
            case BLOCK_LEFT -> {
                entity.translateX(3);
                dx = 0.0;
                dy = getRandom();
            }
            case BLOCK_RIGHT -> {
                entity.translateX(-3);
                dx = 0.0;
                dy = getRandom();
            }
            case BLOCK_UP -> {
                entity.translateY(3);
                dy = 0.0;
                dx = getRandom();
            }
            case BLOCK_DOWN -> {
                entity.translateY(-3);
                dy = 0.0;
                dx = getRandom();
            }
        }
    }
    public double getRandom(){
        return Math.random() > 0.5 ? ENEMY_SPEED_BASE : -ENEMY_SPEED_BASE;
    }

    protected void turn(){
        if(dx < 0.0){
            setTurnEnemy(TurnDirection.BLOCK_LEFT);
        } else if(dx > 0.0){
            setTurnEnemy(TurnDirection.BLOCK_RIGHT);
        } else if(dy < 0.0){
            setTurnEnemy(TurnDirection.BLOCK_UP);
        } else if(dy > 0.0) {
            setTurnEnemy(TurnDirection.BLOCK_DOWN);
        }
    }

    protected boolean isDetectPlayer(){
        BoundingBoxComponent box = new BoundingBoxComponent();
    }
}
