/**
 * Place contains information about how to create entities.
 */

package com.bomberman.bombermanplus;

import com.almasb.fxgl.dsl.EntityBuilder;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.BooleanComponent;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.entity.components.IrremovableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.bomberman.bombermanplus.components.PlayerComponent;
import com.bomberman.bombermanplus.constants.GameConst;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;
import static com.almasb.fxgl.dsl.FXGLForKtKt.texture;
import static com.bomberman.bombermanplus.constants.GameConst.*;

public class BombermanFactory implements EntityFactory {

    /* value of annotation must match the type used in Tiled exactly */
    @Spawns("player")
    public Entity spawnPlayer(SpawnData data) {
        return entityBuilder(data)
                .atAnchored(new Point2D(20, 20), new Point2D(20, 20))     /* */
                .type(BombermanType.PLAYER)
                .viewWithBBox(new Rectangle(TILE_SIZE, TILE_SIZE, Color.BLUE))
                .collidable()
                .with(new PlayerComponent())
                .build();
    }

    @Spawns("background")
    public Entity spawnBackground(SpawnData data) {
        return FXGL.entityBuilder()
                .view(new Rectangle(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT, Color.LIGHTGRAY))
                .with(new IrremovableComponent())
                .zIndex(-100)                              /* render behind all other entities */
                .build();
    }

    /*
    @Spawns("w")
    public Entity spawnWall(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(BombermanType.WALL)
                .viewWithBBox(new Rectangle(40, 40, Color.GRAY.saturate()))
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("b")
    public Entity spawnBrick(SpawnData data) {
        return entityBuilder(data)
                .type(BombermanType.BRICK)
                .viewWithBBox(texture("brick.png", 40, 40))
                .build();
    }

    @Spawns("bomb")
    public Entity newBomb(SpawnData data) {
        return entityBuilder(data)
                .type(BombermanType.BOMB)
                .viewWithBBox("bomb.png")
                .atAnchored(new Point2D(13, 11),
                        new Point2D(data.getX() + TILE_SIZE / 2, data.getY() + TILE_SIZE / 2))
                .build();
    }

    @Spawns("wall")
    public Entity newWall(SpawnData data) {
        var width = (int) data.get("width");
        var height = (int) data.get("height");

        return entityBuilder(data)
                .type(BombermanType.WALL)
                .bbox(new HitBox(BoundingShape.box(width, height)))
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .build();
    }
     */

    @Spawns("around_wall")
    public Entity newArWall(SpawnData data) {
        var width = (int) data.get("width");
        var height = (int) data.get("height");

        return entityBuilder(data)
                .type(BombermanType.AROUND_WALL)
                .bbox(new HitBox(BoundingShape.box(width, height)))
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .build();
    }

    /*
    @Spawns("speedItem")
    public Entity newItem(SpawnData data) {
        return entityBuilder(data)
                .type(BombermanType.SPEED_ITEM)
                .view("items/powerup_speed.png")
                .bbox(new HitBox(BoundingShape.box(data.<Integer> get("width"), data.<Integer> get("height"))))
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("bombsItem")
    public Entity newItem1(SpawnData data) {
        return entityBuilder(data)
                .type(BombermanType.BOMB_ITEM)
                .view("items/powerup_bombs.png")
                .bbox(new HitBox(BoundingShape.box(data.<Integer> get("width"), data.<Integer> get("height"))))
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("flameItem")
    public Entity newItem2(SpawnData data) {
        return entityBuilder(data)
                .type(BombermanType.FLAME_ITEM)
                .view("items/powerup_flames.png")
                .bbox(new HitBox(BoundingShape.box(data.<Integer> get("width"), data.<Integer> get("height"))))
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("flamePassItem")
    public Entity newItem3(SpawnData data) {
        return entityBuilder(data)
                .type(BombermanType.FLAME_PASS_ITEM)
                .view("items/powerup_flamepass.png")
                .bbox(new HitBox(BoundingShape.box(data.<Integer> get("width"), data.<Integer> get("height"))))
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("portal")
    public Entity newPortal(SpawnData data) {
        var width = (int) data.get("width");
        var height = (int) data.get("height");

        var boundingShape = BoundingShape.box(width, height);
        var hitBox = new HitBox(boundingShape);

        return entityBuilder(data)
                .type(BombermanType.PORTAL)
                .bbox(hitBox)
                .view("items/portal.png")
                .with(new CollidableComponent(true))
                .build();
    }
     */
}
