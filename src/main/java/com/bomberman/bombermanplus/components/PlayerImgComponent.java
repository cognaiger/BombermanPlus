/**
 * Handle image of player.
 */

package com.bomberman.bombermanplus.components;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.image;
import static com.bomberman.bombermanplus.Config.ANIM_TIME_PlAYER;
import static com.bomberman.bombermanplus.Config.SIZE_FRAME;

public class PlayerImgComponent  extends Component {

    private AnimatedTexture texture;
    private AnimationChannel aniIdleDown, aniIdleRight, aniIdleUp, aniIdleLeft;
    private AnimationChannel aniWalkDown, aniWalkRight, aniWalkUp, aniWalkLeft;
    private AnimationChannel aniDie;

    public PlayerImgComponent() {
        setAnimation(Skin.CHAR2);
        texture = new AnimatedTexture(aniIdleDown);
    }

    /**
     * Set animation channel based on current skin.
     * @param skin current skin
     */
    public void setAnimation(Skin skin) {
        if (skin == Skin.NORMAL) {
            aniIdleDown = new AnimationChannel(image("sprites.png"), 16,
                    SIZE_FRAME, SIZE_FRAME, Duration.seconds(ANIM_TIME_PlAYER),
                    3, 3);
            aniIdleRight = new AnimationChannel(image("sprites.png"), 16,
                    SIZE_FRAME, SIZE_FRAME, Duration.seconds(ANIM_TIME_PlAYER),
                    6, 6);
            aniIdleLeft = new AnimationChannel(image("sprites.png"), 16,
                    SIZE_FRAME, SIZE_FRAME, Duration.seconds(ANIM_TIME_PlAYER),
                    9, 9);
            aniIdleUp = new AnimationChannel(image("sprites.png"), 16,
                    SIZE_FRAME, SIZE_FRAME, Duration.seconds(ANIM_TIME_PlAYER),
                    0, 0);

            aniWalkDown = new AnimationChannel(image("sprites.png"), 16,
                    SIZE_FRAME, SIZE_FRAME, Duration.seconds(ANIM_TIME_PlAYER),
                    3, 5);
            aniWalkRight = new AnimationChannel(image("sprites.png"), 16,
                    SIZE_FRAME, SIZE_FRAME, Duration.seconds(ANIM_TIME_PlAYER),
                    6, 8);
            aniWalkLeft = new AnimationChannel(image("sprites.png"), 16,
                    SIZE_FRAME, SIZE_FRAME, Duration.seconds(ANIM_TIME_PlAYER),
                    9, 11);
            aniWalkUp = new AnimationChannel(image("sprites.png"), 16,
                    SIZE_FRAME, SIZE_FRAME, Duration.seconds(ANIM_TIME_PlAYER),
                    0, 2);

            aniDie = new AnimationChannel(image("sprites.png"), 16,
                    SIZE_FRAME, SIZE_FRAME, Duration.seconds(1.5), 12, 14);
        } else if (skin == Skin.FLAME_PASS) {
            aniIdleDown = new AnimationChannel(image("sprites.png"), 16,
                    SIZE_FRAME, SIZE_FRAME, Duration.seconds(ANIM_TIME_PlAYER),
                    115, 115);
            aniIdleRight = new AnimationChannel(image("sprites.png"), 16,
                    SIZE_FRAME, SIZE_FRAME, Duration.seconds(ANIM_TIME_PlAYER),
                    118, 118);
            aniIdleLeft = new AnimationChannel(image("sprites.png"), 16,
                    SIZE_FRAME, SIZE_FRAME, Duration.seconds(ANIM_TIME_PlAYER),
                    121, 121);
            aniIdleUp = new AnimationChannel(image("sprites.png"), 16,
                    SIZE_FRAME, SIZE_FRAME, Duration.seconds(ANIM_TIME_PlAYER),
                    112, 112);

            aniWalkDown = new AnimationChannel(image("sprites.png"), 16,
                    SIZE_FRAME, SIZE_FRAME, Duration.seconds(ANIM_TIME_PlAYER),
                    115, 117);
            aniWalkRight = new AnimationChannel(image("sprites.png"), 16,
                    SIZE_FRAME, SIZE_FRAME, Duration.seconds(ANIM_TIME_PlAYER),
                    118, 120);
            aniWalkLeft = new AnimationChannel(image("sprites.png"), 16,
                    SIZE_FRAME, SIZE_FRAME, Duration.seconds(ANIM_TIME_PlAYER),
                    121, 123);
            aniWalkUp = new AnimationChannel(image("sprites.png"), 16,
                    SIZE_FRAME, SIZE_FRAME, Duration.seconds(ANIM_TIME_PlAYER),
                    112, 114);
            aniDie = new AnimationChannel(image("sprites.png"), 16,
                    SIZE_FRAME, SIZE_FRAME, Duration.seconds(1.5), 12, 14);
        } else if (skin == Skin.CHAR2) {
            aniIdleDown = new AnimationChannel(image("char2.png"), 8,
                    SIZE_FRAME, SIZE_FRAME, Duration.seconds(ANIM_TIME_PlAYER),
                    8, 8);
            aniIdleRight = new AnimationChannel(image("char2.png"), 8,
                    SIZE_FRAME, SIZE_FRAME, Duration.seconds(ANIM_TIME_PlAYER),
                    0, 0);
            aniIdleLeft = new AnimationChannel(image("char2.png"), 8,
                    SIZE_FRAME, SIZE_FRAME, Duration.seconds(ANIM_TIME_PlAYER),
                    46, 46);
            aniIdleUp = new AnimationChannel(image("char2.png"), 8,
                    SIZE_FRAME, SIZE_FRAME, Duration.seconds(ANIM_TIME_PlAYER),
                    16, 16);

            aniWalkDown = new AnimationChannel(image("char2.png"), 8,
                    SIZE_FRAME, SIZE_FRAME, Duration.seconds(ANIM_TIME_PlAYER),
                    8, 10);
            aniWalkRight = new AnimationChannel(image("char2.png"), 8,
                    SIZE_FRAME, SIZE_FRAME, Duration.seconds(ANIM_TIME_PlAYER),
                    0, 2);
            aniWalkLeft = new AnimationChannel(image("char2.png"), 8,
                    SIZE_FRAME, SIZE_FRAME, Duration.seconds(ANIM_TIME_PlAYER),
                    46, 47);
            aniWalkUp = new AnimationChannel(image("char2.png"), 8,
                    SIZE_FRAME, SIZE_FRAME, Duration.seconds(ANIM_TIME_PlAYER),
                    16, 18);
            aniDie = new AnimationChannel(image("char2.png"), 8,
                    SIZE_FRAME, SIZE_FRAME, Duration.seconds(0.5), 24, 31);
        }
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
    }

    @Override
    public void onUpdate(double tbf) {
        /*
         * Based on status to render animation.
         */
        switch (getEntity().getComponent(PlayerComponent.class).getCurMove()) {
            case UP:
                texture.loopNoOverride(aniWalkUp);
                break;
            case DOWN:
                texture.loopNoOverride(aniWalkDown);
                break;
            case LEFT:
                texture.loopNoOverride(aniWalkLeft);
                break;
            case RIGHT:
                texture.loopNoOverride(aniWalkRight);
                break;
            case STOP:
                if (texture.getAnimationChannel() == aniWalkDown) {
                    texture.loopNoOverride(aniIdleDown);
                } else if (texture.getAnimationChannel() == aniWalkUp) {
                    texture.loopNoOverride(aniIdleUp);
                } else if (texture.getAnimationChannel() == aniWalkLeft) {
                    texture.loopNoOverride(aniIdleLeft);
                } else if (texture.getAnimationChannel() == aniWalkRight) {
                    texture.loopNoOverride(aniIdleRight);
                } else if (texture.getAnimationChannel() == aniDie) {
                    texture.loopNoOverride(aniIdleDown);
                }
                break;
            case DIE:
                texture.loopNoOverride(aniDie);
                break;
        }
    }
}
