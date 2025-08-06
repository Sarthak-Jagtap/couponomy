package com.superx.View;

import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

import com.superx.Dao.CouponInfoDAO;
import com.superx.Model.CouponDetails;

public class AdminPage extends Application {

    private VBox detailsPanel;
    private CouponDetails selectedCoupon;
    private List<CouponDetails> pendingCoupons = new ArrayList<>();
    private FlowPane pendingGrid;
    private Label pendingCount;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Admin Panel - Coupon Verification");

        // HEADER
        HBox header = new HBox();
        header.setPadding(new Insets(15));
        header.setStyle("-fx-background-color: #3b82f6;");
        Text title = new Text("Admin Dashboard");
        title.setFill(Color.WHITE);
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        header.getChildren().add(title);

        // Stats
        HBox statsRow = new HBox(20);
        statsRow.setPadding(new Insets(20));
        statsRow.setAlignment(Pos.CENTER_LEFT);
        pendingCount = createStatCard("Pending", "#facc15");
        statsRow.getChildren().addAll(pendingCount);

        // TabPane
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        pendingGrid = createGrid();
        tabPane.getTabs().add(new Tab("Pending", createTabContent(pendingGrid)));

        // Details Panel
        detailsPanel = new VBox(10);
        detailsPanel.setPadding(new Insets(20));
        detailsPanel.setPrefWidth(400);
        detailsPanel.setStyle("-fx-background-color: #f8f9fa; -fx-border-color: #ddd;");
        detailsPanel.getChildren().add(new Text("Select a coupon to view details"));

        ScrollPane detailsScroll = new ScrollPane(detailsPanel);
        detailsScroll.setFitToWidth(true);

        // Load Pending Coupons
        try {
            pendingCoupons.clear();
            pendingCoupons.addAll(CouponInfoDAO.getCouponsByStatus("Pending"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        refreshGrid();

        // Layout
        VBox leftPane = new VBox(header, statsRow, tabPane);
        VBox.setVgrow(tabPane, Priority.ALWAYS);

        HBox mainLayout = new HBox(10, leftPane, detailsScroll);
        HBox.setHgrow(leftPane, Priority.ALWAYS);

        Scene scene = new Scene(mainLayout, 1500, 850);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Label createStatCard(String title, String colorHex) {
        Label card = new Label(title + ": 0");
        card.setStyle("-fx-background-color: " + colorHex + "; -fx-text-fill: white; "
                + "-fx-padding: 15; -fx-background-radius: 10; -fx-font-size: 16px; -fx-font-weight: bold;");
        return card;
    }

    private ScrollPane createTabContent(FlowPane grid) {
        ScrollPane scrollPane = new ScrollPane(grid);
        scrollPane.setFitToWidth(true);
        return scrollPane;
    }

    private FlowPane createGrid() {
        FlowPane grid = new FlowPane();
        grid.setHgap(15);
        grid.setVgap(15);
        grid.setPadding(new Insets(10));
        grid.setPrefWrapLength(900);
        return grid;
    }

    private VBox createCouponCard(CouponDetails coupon, String status) {
        ImageView imageView = new ImageView(new Image(coupon.getCouponSSURL()));
        imageView.setFitWidth(180);
        imageView.setFitHeight(100);
        imageView.setPreserveRatio(true);

        Text brandText = new Text(coupon.getBrandName());
        brandText.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        brandText.setFill(Color.web("#334155"));

        Button viewDetailsBtn = new Button("View Details");
        viewDetailsBtn.setStyle("-fx-background-color: #3b82f6; -fx-text-fill: white;");
        viewDetailsBtn.setOnAction(e -> showDetails(coupon));

        VBox card = new VBox(8, imageView, brandText, viewDetailsBtn);
        card.setPadding(new Insets(10));
        card.setAlignment(Pos.CENTER);
        card.setPrefWidth(200);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 10; "
                + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 6, 0, 0, 3);");

        // Hover Animation
        card.setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), card);
            st.setToX(1.05);
            st.setToY(1.05);
            st.play();
        });
        card.setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), card);
            st.setToX(1);
            st.setToY(1);
            st.play();
        });

        return card;
    }

    private void showDetails(CouponDetails coupon) {
        selectedCoupon = coupon;
        detailsPanel.getChildren().clear();

        Text title = new Text("Coupon Details");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));

        VBox info = new VBox(
                createDetailText("Brand: " + coupon.getBrandName()),
                createDetailText("Offer: " + coupon.getOfferValue()),
                createDetailText("Min Order: " + coupon.getMinOrdValue()),
                createDetailText("Code: " + coupon.getCouponCode()),
                createDetailText("Category: " + coupon.getCategory()),
                createDetailText("Price: ₹" + coupon.getCoinValue()),
                createDetailText("Description: " + coupon.getDescription()),
                createDetailText("Expiry: " + coupon.getExpiry()),
                createDetailText("Status: " + coupon.getStatus()),
                createDetailText("Visibility: " + coupon.getVisibility()),
                createDetailText("Seller: " + coupon.getSellerId()));

        ImageView screenshotView = new ImageView(new Image(coupon.getCouponSSURL()));
        screenshotView.setFitWidth(200);
        screenshotView.setPreserveRatio(true);

        ImageView couponImgView = new ImageView(new Image(coupon.getCouponPosterURL()));
        couponImgView.setFitWidth(200);
        couponImgView.setPreserveRatio(true);

        Button approveBtn = new Button("✔ Approve");
        approveBtn.setStyle("-fx-background-color: #22c55e; -fx-text-fill: white;");
        approveBtn.setOnAction(e -> approveCoupon());

        Button rejectBtn = new Button("✖ Reject");
        rejectBtn.setStyle("-fx-background-color: #ef4444; -fx-text-fill: white;");
        rejectBtn.setOnAction(e -> rejectCoupon());

        HBox actionBtns = new HBox(10, approveBtn, rejectBtn);
        actionBtns.setPadding(new Insets(10, 0, 0, 0));

        detailsPanel.getChildren().addAll(title, info,
                new Text("Screenshot:"), screenshotView,
                new Text("Coupon Image:"), couponImgView, actionBtns);
    }

    private Text createDetailText(String val) {
        Text t = new Text(val);
        t.setWrappingWidth(360);
        t.setFont(Font.font("Segoe UI", 14));
        t.setFill(Color.web("#374151"));
        return t;
    }

    private void approveCoupon() {
        if (selectedCoupon != null) {
            try {
                CouponInfoDAO.updateCouponStatus(selectedCoupon.getCouponId(), "Approved", "public");
                pendingCoupons.remove(selectedCoupon);
                refreshGrid();
                clearDetailsPanel();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void rejectCoupon() {
        if (selectedCoupon != null) {
            try {
                CouponInfoDAO.updateCouponStatus(selectedCoupon.getCouponId(), "Rejected", "private");
                pendingCoupons.remove(selectedCoupon);
                refreshGrid();
                clearDetailsPanel();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void clearDetailsPanel() {
        selectedCoupon = null;
        detailsPanel.getChildren().clear();
        detailsPanel.getChildren().add(new Text("Select a coupon to view details"));
    }

    private void refreshGrid() {
        pendingGrid.getChildren().clear();
        for (CouponDetails c : pendingCoupons) {
            pendingGrid.getChildren().add(createCouponCard(c, "Pending"));
        }
        pendingCount.setText("Pending: " + pendingCoupons.size());
    }

    // Refresh Admin With some interval to load new pending coupons
}
