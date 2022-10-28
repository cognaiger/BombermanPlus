/**
 * Main file of application.
 */

package com.bomberman.bombermanplus;

import com.almasb.fxgl.achievement.Achievement;
import com.almasb.fxgl.achievement.AchievementEvent;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.pathfinding.CellState;
import com.almasb.fxgl.pathfinding.astar.AStarGrid;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.bomberman.bombermanplus.Menus.BombermanGameMenu;
import com.bomberman.bombermanplus.Menus.BombermanMenu;
import com.bomberman.bombermanplus.components.PlayerComponent;
import com.bomberman.bombermanplus.components.PlayerStatus;
import com.bomberman.bombermanplus.ui.BombermanHUD;
import javafx.geometry.Point2D;
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
import static com.almasb.fxgl.dsl.FXGL.getWorldProperties;
import static com.almasb.fxgl.dsl.FXGL.getb;
import static com.almasb.fxgl.dsl.FXGL.geti;
import static com.almasb.fxgl.dsl.FXGL.inc;
import static com.almasb.fxgl.dsl.FXGL.onCollisionBegin;
import static com.almasb.fxgl.dsl.FXGL.set;
import static com.almasb.fxgl.dsl.FXGL.showMessage;
import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import static com.bomberman.bombermanplus.BombermanType.*;
import static com.bomberman.bombermanplus.Config.*;

public class BombermanApp extends GameApplication {

    public static boolean isSoundEnabled = true;
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
        settings.setHeight(SCREEN_HEIGHT);
        settings.setWidth(SCREEN_WIDTH);
        settings.setTitle(TITLE);
        settings.setVersion(VERSION);
        settings.setIntroEnabled(false);
        settings.setMainMenuEnabled(true);
        settings.setGameMenuEnabled(true);
        settings.setPreserveResizeRatio(true);
        settings.setManualResizeEnabled(true);
        settings.setDeveloperMenuEnabled(true);
        settings.setFontUI(FONT);
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

        /* Register achievements */
        settings.getAchievements().add(new Achievement("Slayer", "Kill 5 enemies",
                "numOfKill", 5));
        settings.getAchievements().add(new Achievement("See the world", "Pass level 1",
                "level", 2));
        settings.getAchievements().add(new Achievement("Score owner", "Earn more than 1000 score",
                "score", 1000));
    }

    /**
     * Set up all stuff that needs to be ready before game starts.
     */
    @Override
    protected void initGame() {
        FXGL.getGameWorld().addEntityFactory(new BombermanFactory());
        loadNextLevel();
        FXGL.spawn("background");
        startCountDownTimer();
        getWorldProperties().<Integer>addListener("time", this::killPlayerWhenTimeUp);
    }

    private void startCountDownTimer() {
        FXGL.run(() -> inc("time", -1), Duration.seconds(1));
    }

    private void killPlayerWhenTimeUp(int old, int now) {
        if (now == 0) {
            onPlayerKilled();
        }
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
        var hud = new BombermanHUD();
        var leftMargin = 0;
        var topMargin = 0;
        FXGL.getGameTimer().runOnceAfter(() -> FXGL.addUINode(hud.getHUD(), leftMargin, topMargin),
                Duration.seconds(3));
    }

    @Override
    protected void onPreInit() {
        getSettings().setGlobalMusicVolume(isSoundEnabled ? 0.05 : 0.0);
        getSettings().setGlobalSoundVolume(isSoundEnabled ? 0.4 : 0.0);
        loopBGM("title_screen.mp3");
    }

    /**
     * Game variable.
     * Can be accessed and modified from any part of game.
     * @param vars game variable
     */
    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("level", START_LEVEL);
        vars.put("speed", SPEED);
        vars.put("numOfEnemy", NUM_OF_ENEMIES);
        vars.put("numOfKill", 0);
        vars.put("flame", 1);
        vars.put("bomb", 1);
        vars.put("score", 0);
        vars.put("immortality", false);
        vars.put("life", LIFE_PER_LEVEL);
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
     * If enemies are clear and player collides portal then end.
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

        /* Achievement handler */
        FXGL.getEventBus().addEventHandler(AchievementEvent.ACHIEVED, e -> {
            String message = "Achievement unlocked";
            FXGL.getNotificationService().pushNotification(message);
        });

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

        /* reset level state */
        set("time", TIME_PER_LEVEL);
        set("bomb", 1);
        set("flame", 1);
        set("numOfEnemy", NUM_OF_ENEMIES);
        set("score", 0);
        set("life", LIFE_PER_LEVEL);
        setGridForAi();
    }

    /**
     * Kill player.
     */
    private void onPlayerKilled() {
        if (getb("immortality")) {
            return;
        } else {
            play("bomberman_die.wav");
            if (geti("life") > 0) {
                inc("life", -1);
                revive();
            } else {
                set("immortality", true);
                getPlayer().getComponent(PlayerComponent.class).setExploreCancel(true);
                requestNewGame = true;
            }
        }
    }

    /**
     * Revive and return to start position, immortality for 2s.
     */
    private void revive() {
        getPlayer().getComponent(PlayerComponent.class).die();
        FXGL.getGameTimer().runOnceAfter(() -> {
            getPlayer().getComponent(PlayerComponent.class).setCurMove(PlayerStatus.STOP);
            getPlayer().getComponent(PhysicsComponent.class)
                    .overwritePosition(new Point2D(START_POS_X, START_POS_Y));
            set("immortality", true);
        }, Duration.seconds(0.5));
        FXGL.getGameTimer().runOnceAfter(() -> set("immortality", false), Duration.seconds(2));
    }
}

