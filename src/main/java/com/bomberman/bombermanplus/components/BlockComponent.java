package com.bomberman.bombermanplus.components;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.image;
import static com.bomberman.bombermanplus.constants.GameConst.TILE_SIZE;

public class BlockComponent extends Component {
    private final AnimatedTexture texture;

    /**
     * Constructor.
     * @param startF start frame
     * @param endF end frame
     * @param duration time per cycle
     */
    public BlockComponent(int startF, int endF, double duration){
        AnimationChannel animationChannel = new AnimationChannel(image("sprites.png"), 16,
                TILE_SIZE, TILE_SIZE, Duration.seconds(duration), startF, endF);

        texture = new AnimatedTexture(animationChannel);
        texture.loop();
    }

    @Override
    public void onAdded(){
        entity.getViewComponent().addChild(texture);
    }
}
