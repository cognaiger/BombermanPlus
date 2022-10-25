/**
 * Main file of application.
 */

package com.bomberman.bombermanplus;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.level.Level;
import com.almasb.fxgl.entity.level.tiled.TMXLevelLoader;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.pathfinding.CellState;
import com.almasb.fxgl.pathfinding.astar.AStarGrid;
import com.bomberman.bombermanplus.Menus.BombermanGameMenu;
import com.bomberman.bombermanplus.Menus.BombermanMenu;
import com.bomberman.bombermanplus.components.PlayerComponent;
import com.bomberman.bombermanplus.constants.GameConst;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.getGameScene;
import static com.almasb.fxgl.dsl.FXGL.getGameWorld;
import static com.almasb.fxgl.dsl.FXGL.getb;
import static com.almasb.fxgl.dsl.FXGL.geti;
import static com.almasb.fxgl.dsl.FXGL.inc;
import static com.almasb.fxgl.dsl.FXGL.onCollisionBegin;
import static com.almasb.fxgl.dsl.FXGL.set;
import static com.almasb.fxgl.dsl.FXGL.showMessage;
import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import static com.bomberman.bombermanplus.BombermanType.*;
import static com.bomberman.bombermanplus.constants.GameConst.*;
import static com.bomberman.bombermanplus.constants.GameConst.TILE_SIZE;

public class BombermanApp extends GameApplication {

    private static final int VIEW_HEIGHT = 720;
    private static final int VIEW_WIDTH = 1080;

    /* window title */
    private static final String TITLE = "BOMBERMAN";
    private static final String VERSION = "1.0";

    private static final String FONT = "Retro Gaming.ttf";

    private static final int TIME_PER_LEVEL = 300;
    private static final int START_LEVEL = 0;
    public static boolean isSoundEnabled = true;
    private static final int MAX_LEVEL = 1;
    private boolean requestNewGame = false;

    public static void main(String[] args) {                              /* entry point */
        launch(args);
    }

    private static Entity getPlayer() {
        return getGameWorld().getSingleton(PLAYER);
    }

    /**
     * Setting can not be changed during runtime.
     * @param settings game setting
     */
    @Override
    protected void initSettings(GameSettings settings) {
        settings.setHeight(VIEW_HEIGHT);
        settings.setWidth(VIEW_WIDTH);
        settings.setTitle(TITLE);
        settings.setVersion(VERSION);
        settings.setIntroEnabled(false);
        settings.setMainMenuEnabled(true);
        settings.setGameMenuEnabled(false);
        settings.setPreserveResizeRatio(false);
        settings.setManualResizeEnabled(false);
        settings.setDeveloperMenuEnabled(false);
        /* settings.setFontUI(FONT); */

        settings.setSceneFactory(new SceneFactory() {
            @NotNull
            @Override
            public FXGLMenu newMainMenu() {
                return new BombermanMenu();
            }

            @NotNull
            @Override
            public FXGLMenu newGameMenu() {
                return new BombermanGameMenu();
            }
        });
    }

    /**
     * Set up all stuff that needs to be ready before game starts.
     */
    @Override
    protected void initGame() {
        FXGL.getGameWorld().addEntityFactory(new BombermanFactory());
        loadNextLevel();
        FXGL.spawn("background");
    }

    /**
     * Input handling for player.
     */
    @Override
    protected void initInput() {
        Input input = FXGL.getInput();

        input.addAction(new UserAction("Move Right") {
            @Override
            protected void onAction() {
                getPlayer().getComponent(PlayerComponent.class).right();
            }

            @Override
            protected void onActionEnd() {
                getPlayer().getComponent(PlayerComponent.class).stop();
            }
        }, KeyCode.D);

        input.addAction(new UserAction("Move Up") {
            @Override
            protected void onAction() {
                getPlayer().getComponent(PlayerComponent.class).up();
            }

            @Override
            protected void onActionEnd() {
                getPlayer().getComponent(PlayerComponent.class).stop();
            }
        }, KeyCode.W);

        input.addAction(new UserAction("Move Down") {
            @Override
            protected void onAction() {
                getPlayer().getComponent(PlayerComponent.class).down();
            }

            @Override
            protected void onActionEnd() {
                getPlayer().getComponent(PlayerComponent.class).stop();
            }
        }, KeyCode.S);

        input.addAction(new UserAction("Move Left") {
            @Override
            protected void onAction() {
                getPlayer().getComponent(PlayerComponent.class).left();
            }

            @Override
            protected void onActionEnd() {
                getPlayer().getComponent(PlayerComponent.class).stop();
            }
        }, KeyCode.A);

        input.addAction(new UserAction("Place bomb") {
            @Override
            protected void onActionBegin() {
                getPlayer().getComponent(PlayerComponent.class).placeBomb();
            }
        }, KeyCode.SPACE);
    }

    /**
     * Handle UI.
     */
    @Override
    protected void initUI() {
    }

    /**
     * Game variable.
     * Can be accessed and modified from any part of game.
     * @param vars
     */
    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("level", START_LEVEL);
        vars.put("speed", GameConst.SPEED);
        vars.put("numOfEnemy", 10);
        vars.put("flame", 1);
        vars.put("bomb", 1);
        vars.put("score", 0);
        vars.put("immortality", false);
        vars.put("life", 3);
        vars.put("time", TIME_PER_LEVEL);
    }

    /**
     * Init physics world.
     * Handle collision between player and dangerous objects.
     */
    @Override
    protected void initPhysics() {
        getPhysicsWorld().setGravity(0, 0);

        FXGL.onCollisionBegin(PLAYER, PORTAL, this::endLevel);
        FXGL.onCollisionBegin(PLAYER, FLAME, (p, f) -> onPlayerKilled());
        onCollisionBegin(PLAYER, BALLOOM_E, (p, b) -> onPlayerKilled());
        onCollisionBegin(PLAYER, DAHL_E, (p, dh) -> onPlayerKilled());
        onCollisionBegin(PLAYER, ONEAL_E, (p, o) -> onPlayerKilled());
        onCollisionBegin(PLAYER, DORIA_E, (p, d) -> onPlayerKilled());
        onCollisionBegin(PLAYER, OVAPE_E, (p, o) -> onPlayerKilled());
        onCollisionBegin(PLAYER, PASS_E, (p, pa) -> onPlayerKilled());
    }

    /**
     * If enemies are clear then end.
     * @param player player
     * @param portal portal
     */
    private void endLevel(Entity player, Entity portal) {
        if (geti("numOfEnemy") > 0) {
            return;
        }
        play("next_level.wav");
        getPlayer().getComponent(PlayerComponent.class).setExploreCancel(true);
        var timer = FXGL.getGameTimer();
        timer.runOnceAfter(this::fadeToNextLevel, Duration.seconds(1));
    }

    private void fadeToNextLevel() {
        var gameScene = FXGL.getGameScene();
        var viewPort = gameScene.getViewport();
        viewPort.fade(this::loadNextLevel);
    }

    private void loadNextLevel() {
        if (FXGL.geti("level") >= MAX_LEVEL) {
            showMessage("You win!", () -> getGameController().gotoMainMenu());
        } else {
            getSettings().setGlobalMusicVolume(0);
            getInput().setProcessInput(false);

            play("stage_start.wav");
            inc("level", +1);
            AnchorPane pane = creStartStage();
            FXGL.addUINode(pane);

            /* cre start stage */
            getGameTimer().runOnceAfter(() -> {
                FXGL.removeUINode(pane);
                getSettings().setGlobalMusicVolume(0.05);
                getInput().setProcessInput(true);
                setLevel();
            }, Duration.seconds(3));
        }
    }

    private void setGridForAi() {
        AStarGrid grid = AStarGrid.fromWorld(getGameWorld(), 31, 15,
                TILE_SIZE, TILE_SIZE, (type) -> {
                    if (type == BombermanType.BRICK
                            || type == BombermanType.WALL
                            || type == BombermanType.GRASS
                            || type == BombermanType.CORAL
                            || type == BombermanType.AROUND_WALL) {
                        return CellState.NOT_WALKABLE;
                    } else {
                        return CellState.WALKABLE;
                    }
                });

        AStarGrid _grid = AStarGrid.fromWorld(getGameWorld(), 31, 15,
                TILE_SIZE, TILE_SIZE, (type) -> {
                    if (type == BombermanType.AROUND_WALL || type == BombermanType.WALL) {
                        return CellState.NOT_WALKABLE;
                    } else {
                        return CellState.WALKABLE;
                    }
                });

        set("grid", grid);
        set("_grid", _grid);
    }

    private AnchorPane creStartStage() {
        AnchorPane pane =new AnchorPane();
        Shape shape = new Rectangle(1080, 720, Color.BLACK);

        var text = FXGL.getUIFactoryService().newText("STAGE" + geti("level"), Color.WHITE, 40);
        text.setTranslateX((SCREEN_WIDTH >> 1) - 80);
        text.setTranslateY((SCREEN_HEIGHT >> 1) - 20);
        pane.getChildren().addAll(shape, text);

        return pane;
    }

    @Override
    protected void onUpdate(double tpf) {

        if (geti("time") == 0) {
            showMessage("Game Over leu leu!!!", () -> getGameController().gotoMainMenu());
        }

        if (requestNewGame) {
            requestNewGame = false;
            getPlayer().getComponent(PlayerComponent.class).die();
            getGameTimer().runOnceAfter(() -> getGameScene().getViewport().fade(() -> {
                if (geti("life") <= 0) {
                    showMessage("Game Over leu leu!!!",
                            () -> getGameController().gotoMainMenu());
                }
                setLevel();
                set("immortality", false);
            }), Duration.seconds(0.5));
        }
    }

    private void setLevel() {
        FXGL.setLevelFromMap("bbm_level" + geti("level") + ".tmx");

        Viewport viewport = getGameScene().getViewport();
        viewport.setBounds(0, 0, GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT);
        viewport.bindToEntity(
                getPlayer(),
                FXGL.getAppWidth() / 2.0f,
                FXGL.getAppHeight() / 2.0f);
        viewport.setLazy(true);
        set("time", TIME_PER_LEVEL);
        set("bomb", 1);
        set("flame", 1);
        set("numOfEnemy", 10);
        setGridForAi();
    }

    /**
     * Kill player.
     */
    private void onPlayerKilled() {
        if (!getb("immortality")) {
            set("immortality", true);
            if (geti("life") > 0) inc("life", -1);
            set("score", 0);
            getPlayer().getComponent(PlayerComponent.class).setExploreCancel(true);
            requestNewGame = true;
        }
    }
}

