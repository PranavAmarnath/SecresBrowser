package com.secres.secresbrowser;

import java.net.MalformedURLException;
import java.net.URL;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class SinglePage {

    private WebView webView;
    private WebEngine webEngine;
    private TextField urlTextField;
    private VBox vBox;

    public SinglePage() {
        initPage();
    }

    private void initPage() {
        //setUserAgentStylesheet(STYLESHEET_CASPIAN); // change style to caspian

        webView = new WebView();
        webEngine = webView.getEngine();

        vBox = new VBox();

        HBox controlsBox = new HBox();
        urlTextField = new TextField();
        loadURL("http://google.com");
        urlTextField.setText("http://google.com");

        ProgressBar pBar = new ProgressBar();
        pBar.prefWidthProperty().bind(vBox.widthProperty());

        // When the user interacts and the link changes, update the text field with the new link
        webEngine.locationProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String oldValue, final String newValue) {
                urlTextField.setText(newValue);
            }
        });

        // update the progress bar's progress while the web page is loading
        webEngine.getLoadWorker().workDoneProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, final Number newValue) {
                pBar.setVisible(true);
                pBar.setProgress(newValue.doubleValue());
                if(pBar.getProgress() == 100) {
                    pBar.setVisible(false);
                    pBar.setProgress(0);
                }
            }
        });

        urlTextField.setOnAction(e -> {
            loadURL(urlTextField.getText());
        });

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            goBack();
        });

        Button forwardButton = new Button("Forward");
        forwardButton.setOnAction(e -> {
            goForward();
        });

        Button reloadButton = new Button("Reload");
        reloadButton.setOnAction(e -> {
            reload();
        });

        HBox.setHgrow(urlTextField, Priority.ALWAYS); // stretches the URL text field to the end

        controlsBox.getChildren().add(backButton);
        controlsBox.getChildren().add(new SpaceRegion());
        controlsBox.getChildren().add(forwardButton);
        controlsBox.getChildren().add(new SpaceRegion());
        controlsBox.getChildren().add(reloadButton);
        controlsBox.getChildren().add(new SpaceRegion());
        controlsBox.getChildren().add(urlTextField);
        controlsBox.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.NONE, CornerRadii.EMPTY, new BorderWidths(3))));

        vBox.getChildren().add(controlsBox);
        vBox.getChildren().add(pBar);
        BorderPane webPane = new BorderPane(webView);
        vBox.getChildren().add(webPane);
    }

    private class SpaceRegion extends Region {
        public SpaceRegion() {
            super();
            setMinWidth(5);
        }
    }
    
    private void reload() {
        webEngine.executeScript("history.go(0)");
    }

    private void goBack() {
        webEngine.executeScript("history.back()");
    }

    private void goForward() {
        webEngine.executeScript("history.forward()");
    }

    private void loadURL(final String url) {
        String tmp = toURL(url);

        if(tmp == null) {
            tmp = toURL("http://" + url);
        }

        urlTextField.setText(tmp);

        webEngine.load(tmp);
    }

    private String toURL(String str) {
        try {
            return new URL(str).toExternalForm();
        } catch (MalformedURLException exception) {
            return null;
        }
    }

    public VBox getBox() {
        return vBox;
    }

}
