package com.bomberman.bombermanplus.Menus;

import com.almasb.fxgl.dsl.FXGL;
import javafx.beans.binding.Bindings;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class MenuButton extends Parent {
    Text text;

    public MenuButton(String name, int size, Runnable action){
        text = FXGL.getUIFactoryService().newText(name, Color.BLACK, size);
        text.setStrokeWidth(1.5);
        text.strokeProperty().bind(text.fillProperty());

        text.fillProperty().bind(
                Bindings.when(hoverProperty())
                        .then(Color.BLUE)
                        .otherwise(Color.BLACK)
        );

        setOnMouseClicked(e -> action.run());

        setPickOnBounds(true);

        getChildren().add(text);
    }


    MenuButton(String name, Runnable action) {
        this(name, 30, action);
    }
}
