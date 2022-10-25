package com.bomberman.bombermanplus.components;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.pathfinding.CellState;
import com.almasb.fxgl.pathfinding.astar.AStarGrid;
import com.almasb.fxgl.physics.PhysicsWorld;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.bomberman.bombermanplus.BombermanType;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.onCollisionBegin;
import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import static com.bomberman.bombermanplus.constants.GameConst.TILE_SIZE;

public class FlameComponent extends Component {

    private final AnimatedTexture texture;

    /**
     * Create animation channel for flame.
     * Collision between flame and enemies, obstacles, destructible objects.
     * @param startF start frame
     * @param endF end frame
     */
    public FlameComponent(int startF, int endF) {
        PhysicsWorld physics = getPhysicsWorld();

        AnimationChannel animationFlame = new AnimationChannel(image("sprites.png"), 16,
                TILE_SIZE, TILE_SIZE, Duration.seconds(0.4), startF, endF);

        texture = new AnimatedTexture(animationFlame);
        texture.loop();

        onCollisionBegin(BombermanType.FLAME, BombermanType.WALL, (f, w) -> {
            f.removeFromWorld();
        });

        onCollisionBegin(BombermanType.FLAME, BombermanType.AROUND_WALL, (f, w) -> {
            f.removeFromWorld();
        });

        setCollisionBreak(BombermanType.BRICK, "brick_break");
        setCollisionBreak(BombermanType.GRASS, "grass_break");
        setCollisionBreak(BombermanType.CORAL, "coral_break");
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
    }

    /**
     * Effect when flame collide with destructible objects.
     * @param type type of destructible objects
     * @param nameTypeBreak name of sprite effect
     */
    private void setCollisionBreak(BombermanType type, String nameTypeBreak) {
        onCollisionBegin(BombermanType.FLAME, type, (f, t) -> {
            int cellX = (int)((t.getX() + 24) / TILE_SIZE);
            int cellY = (int)((t.getY() + 24) / TILE_SIZE);

            AStarGrid grid = geto("grid");
            grid.get(cellX, cellY).setState(CellState.WALKABLE);
            set("grid", grid);

            Entity bBreak = spawn(nameTypeBreak, new SpawnData(t.getX(), t.getY()));
            t.removeFromWorld();
            getGameTimer().runOnceAfter(bBreak::removeFromWorld, Duration.seconds(1));
        });
    }
}
