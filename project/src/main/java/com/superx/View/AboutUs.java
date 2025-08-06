package com.superx.View;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class AboutUs {

        public BorderPane showAboutUs() {

                BorderPane pane = new BorderPane(); // Main layout

                // Heading
                Label mainHeading = new Label("About Us");
                mainHeading.setFont(Font.font("Segoe UI", FontWeight.BOLD, 34));
                mainHeading.setTextFill(Color.web("#ffffff"));

                Label subHeading = new Label("Couponomy - Buy & Sell Coupons Smarter");
                subHeading.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 20));
                subHeading.setTextFill(Color.web("#dddddd"));

                // C2W Logo
                ImageView c2wImage = new ImageView(new Image("assets/images/c2wlogo.png"));
                c2wImage.setFitHeight(60);
                c2wImage.setPreserveRatio(true);
                c2wImage.setSmooth(true);

                // Paragraph 1
                Text para1 = new Text(
                                "We extend our heartfelt thanks to Core2Web for empowering us through the SuperX Program.\n\n"
                                                +
                                                "This initiative helped us push beyond technical boundaries and gave us the stage to turn ideas into something real.\n\n"
                                                +
                                                "We’re truly grateful for the guidance and support throughout the journey.");
                para1.setFont(Font.font("Georgia", FontWeight.SEMI_BOLD, 17));
                para1.setFill(Color.web("#333"));

                TextFlow paragraphFlow1 = new TextFlow(para1);
                paragraphFlow1.setMaxWidth(800);
                paragraphFlow1.setStyle(
                                "-fx-background-color: #ffffffcc; -fx-padding: 25; -fx-background-radius: 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 4);");

                HBox c2wBox = new HBox(25, c2wImage, paragraphFlow1);
                c2wBox.setPadding(new Insets(20));
                c2wBox.setAlignment(Pos.CENTER);

                // Shashi Sir Image
                ImageView shashiSir = new ImageView(new Image("assets/images/shashiSir.png"));
                shashiSir.setFitHeight(300);
                shashiSir.setPreserveRatio(true);
                shashiSir.setSmooth(true);

                // Paragraph 2 (Reduced Text)
                Text para2 = new Text(
                                "A special thanks to Shashi Sir — your energy and clarity made every session impactful. "
                                                +
                                                "Your unique way of teaching helped us understand complex topics with ease.\n\n"
                                                +
                                                "We also thank \nPramodh Sir,\nSachin Sir, \nAkshay Sir, \nShiv Dada & Subodh Dada \nfor always guiding and motivating us. "
                                                +
                                                "\nTo our mentor Vedant Mahajan and every mentor at Core2Web — thank you for your constant kindness and support!");
                para2.setFont(Font.font("Georgia", FontWeight.SEMI_BOLD, 17));
                para2.setFill(Color.web("#333"));

                TextFlow paragraphFlow2 = new TextFlow(para2);
                paragraphFlow2.setMaxWidth(800);
                paragraphFlow2.setStyle(
                                "-fx-background-color: #ffffffcc; -fx-padding: 25; -fx-background-radius: 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 4);");

                HBox shashiBox = new HBox(25, paragraphFlow2, shashiSir);
                shashiBox.setPadding(new Insets(20));
                shashiBox.setAlignment(Pos.CENTER);

                // Project Summary
                Text projectOverview = new Text(
                                "About Couponomy:\n\n" +
                                                "Couponomy is a student-built platform under the SuperX Program by Core2Web. "
                                                +
                                                "It reimagines how digital coupons are exchanged and managed — with a coin-based system that’s secure, simple, and effective. "
                                                +
                                                "This was more than just a project — it was our stepping stone into the real world of software building.\n\n"
                                                +
                                                "Team Members:\n" +
                                                "• Sakshi Patil\n" +
                                                "• Siddharth Malegaonkar\n" +
                                                "• Sarthak Jagtap\n" +
                                                "• Atharva More");
                projectOverview.setFont(Font.font("Georgia", FontWeight.SEMI_BOLD, 17));
                projectOverview.setFill(Color.web("#333"));

                TextFlow paragraphFlow3 = new TextFlow(projectOverview);
                paragraphFlow3.setMaxWidth(800);
                paragraphFlow3.setStyle(
                                "-fx-background-color: #ffffffcc; -fx-padding: 25; -fx-background-radius: 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 4);");

                // Main VBox
                VBox mainContainer = new VBox(30, mainHeading, subHeading, c2wBox, shashiBox, paragraphFlow3);
                mainContainer.setPadding(new Insets(40));
                mainContainer.setAlignment(Pos.TOP_CENTER);

                // Purple Gradient Background
                mainContainer.setStyle("""
                                    -fx-background-color: linear-gradient(to bottom, #7f53ac, #647dee);
                                """);

                // ScrollPane wrapping
                ScrollPane scrollPane = new ScrollPane(mainContainer);
                scrollPane.setFitToWidth(true);
                scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

                pane.setCenter(scrollPane);
                return pane;
        }
}
