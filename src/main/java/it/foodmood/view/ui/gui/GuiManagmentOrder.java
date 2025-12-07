package it.foodmood.view.ui.gui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.foodmood.bean.DishBean;
import it.foodmood.bean.RestaurantRoomBean;
import it.foodmood.bean.TableBean;
import it.foodmood.domain.value.CourseType;
import it.foodmood.exception.RestaurantRoomException;
import it.foodmood.view.boundary.DishBoundary;
import it.foodmood.view.boundary.RestaurantRoomBoundary;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class GuiManagmentOrder extends BaseGui {

    @FXML private Button btnClearOrder;

    @FXML private Button btnRequestBill;

    @FXML private Button btnSendOrder;

    @FXML private ComboBox<CourseType> categoryFilter;

    @FXML private Button freeTableBtn;

    @FXML private Label lblDateTime;

    @FXML private Label lblFreeTables;

    @FXML private Label lblOccupiedTables;

    @FXML private Label lblReservedTables;

    @FXML private Label lblSelectedTable;

    @FXML private Label lblSelectedTableState;

    @FXML private Label lblTotalOrder;

    @FXML private GridPane menuGridPane;

    @FXML private Tab menuTab;

    @FXML private TextArea notesArea;

    @FXML private ListView<?> orderList;

    @FXML private Pane roomPane;

    @FXML private TextField searchField;

    @FXML private Spinner<?> spinner;

    @FXML
    void onClearOrder(ActionEvent event) {
        showInfo("Funzionalità non ancora implementata");
    }

    @FXML
    void onFreeTable(ActionEvent event) {
        showInfo("Funzionalità non ancora implementata");
    }

    @FXML
    void onRequestBill(ActionEvent event) {
        showInfo("Funzionalità non ancora implementata");
    }

    @FXML
    void onSendOrder(ActionEvent event) {
        showInfo("Funzionalità non ancora implementata");
    }

    @FXML
    private void initialize(){

        lblSelectedTable.setText("-");

        roomPane.widthProperty().addListener((obs, oldV, newV) -> updateLayout());
        roomPane.heightProperty().addListener((obs, oldV, newV) -> updateLayout());

        loadRestaurantRoom();

        updateDateTimeLabel();

        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> updateDateTimeLabel()), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Timeline.INDEFINITE);
        clock.play();

        initMenuFilters();
        loadMenuDishes();
    }
    
    private RestaurantRoomBean currentRoom;

    private final ObservableList<DishBean> allDishes = FXCollections.observableArrayList();

    private final RestaurantRoomBoundary boundary = new RestaurantRoomBoundary();
    private final DishBoundary dishBoundary = new DishBoundary();

    private final Map<Integer, ImageView> tableNodes = new HashMap<>();

    private final DateTimeFormatter DATE_TIME = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private TableBean selectedTable;
    private ImageView selectedNode;

    private double cellWidth = 100.0;
    private double cellHeight = 100.0;

    private GuiRouter router;

    public void setRouter(GuiRouter router){
        this.router = router;
    }

    private void loadRestaurantRoom(){
        try {
            currentRoom = boundary.loadRestaurantRoom();
            renderRoom(currentRoom);
        } catch (RestaurantRoomException e) {
            showError(e.getMessage());
        }
    }

    private void renderRoom(RestaurantRoomBean restaurantRoomBean){
        roomPane.getChildren().clear();
        tableNodes.clear();
        selectedTable = null;
        selectedNode = null;
        lblSelectedTable.setText("-");

        if(restaurantRoomBean == null) return;

        currentRoom = restaurantRoomBean;

    for(TableBean tableBean : restaurantRoomBean.getTables()){
            ImageView node = createTableNode(tableBean);
            roomPane.getChildren().add(node);
            tableNodes.put(tableBean.getId(), node);
        }
        updateLayout();
        updateTableCounter();
    }

    private void updateDateTimeLabel(){
        LocalDateTime now = LocalDateTime.now();
        String formatted = now.format(DATE_TIME);
        lblDateTime.setText(formatted);
    }

    private Image getImageForTable(TableBean tableBean){
        String suffix = null;

        switch (tableBean.getStatus()) {
            case FREE:
                suffix = "_free";
                break;
            case BOOKED:
                suffix = "_booked";
                break;
            case OCCUPIED:
                suffix = "_occupied";
                break;
        }

        String fileName = "table_" + tableBean.getSeats() + suffix + ".png";
        String path = "/tables/" + fileName;

        return new Image(getClass().getResourceAsStream(path));
    }

    private void updateTableCounter(){
        if(currentRoom == null || currentRoom.getTables() == null){
            lblFreeTables.setText("0");
            lblReservedTables.setText("0");
            lblOccupiedTables.setText("0");
            return;
        }

        int free = 0;
        int booked = 0;
        int occupied = 0;

        for(TableBean t: currentRoom.getTables()){
            switch ((t.getStatus())) {
                case FREE:
                    free++;
                    break;
                case BOOKED:
                    booked++;
                    break;
                case OCCUPIED:
                    occupied++;
                    break;
            }
        }

        lblFreeTables.setText(String.valueOf(free));
        lblOccupiedTables.setText(String.valueOf(occupied));
        lblReservedTables.setText(String.valueOf(booked));
    }

    private ImageView createTableNode(TableBean tableBean){
        Image image = getImageForTable(tableBean);

        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);

        imageView.setUserData(tableBean);
        imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> selectTable(tableBean, imageView));

        return imageView;
    }

    private void updateLayout(){
        if(currentRoom == null) return;
        
        double paneWidth = roomPane.getWidth() > 0 ? roomPane.getWidth() : roomPane.getPrefWidth();
        double paneHeight = roomPane.getHeight() > 0 ? roomPane.getHeight() : roomPane.getPrefHeight();

        int rows = currentRoom.getRows();
        int cols = currentRoom.getCols();

        cellWidth = paneWidth / cols;
        cellHeight = paneHeight / rows;

        double factor = 0.9;
        double iconSize = Math.min(cellWidth, cellHeight) * factor;

        for(TableBean tableBean : currentRoom.getTables()){
            ImageView node = tableNodes.get(tableBean.getId());
            if(node == null) continue;

            node.setFitWidth(iconSize);
            node.setFitHeight(iconSize);

            double x = tableBean.getCol() * cellWidth + (cellWidth - iconSize) / 2;
            double y = 20 + tableBean.getRow() * cellHeight + (cellHeight - iconSize) / 2;

            node.setLayoutX(x);
            node.setLayoutY(y);
        }
    }

    private void selectTable(TableBean table, ImageView node){
        if(selectedNode != null) {
            selectedNode.setStyle("");
        }

        node.setStyle("-fx-effect: dropshadow(gaussian, yellow, 15, 0.5, 0, 0)");
        
        selectedNode = node;
        selectedTable = table;

        lblSelectedTable.setText("Tavolo: " + table.getId() + " - " + table.getSeats() + " posti");
        lblSelectedTableState.setText(table.getStatus().name());
    }

    private void initMenuFilters(){
        categoryFilter.getItems().clear();
        categoryFilter.getItems().add(null);
        categoryFilter.getItems().addAll(CourseType.values());

        setupComboBox(
            categoryFilter,
            categoryFilter.getItems().toArray(new CourseType[0]),
            item -> item == null ? "Tutte le categorie" :item.description()
        );

        categoryFilter.setValue(null);
        searchField.textProperty().addListener((old, oldV, newV) -> refreshMenuGrid());
        categoryFilter.valueProperty().addListener((old, oldV, newV) -> refreshMenuGrid());
    }

    private void loadMenuDishes(){
        try {
            List<DishBean> dishes = dishBoundary.getAllDishes();
            allDishes.setAll(dishes);
            refreshMenuGrid();
        } catch (Exception e) {
            showError("Errore durante il caricamente dei piatti: " + e.getMessage());
        }
    }

    private void refreshMenuGrid(){
        menuGridPane.getChildren().clear();

        String text = searchField.getText();
        String search = (text == null) ? "" : text.trim().toUpperCase();

        CourseType selectedType = categoryFilter.getValue();

        List<DishBean> filtered = allDishes.stream()
            .filter(d -> selectedType == null || d.getCourseType() == selectedType)
            .filter(d -> {
                if(search.isEmpty()) return true;
                String name = d.getName() != null ? d.getName() : "" ;
                String desc = d.getDescription() != null ? d.getDescription() : "";
                return name.toUpperCase().contains(search) || desc.toUpperCase().contains(search);
            }).toList();
        
        int card = 3;
        int col = 0;
        int row = 0;

        for(DishBean dish: filtered){
            try {
                FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/card.fxml")
                );

                AnchorPane cardNode = loader.load();

                GuiCard cardController = loader.getController();
                cardController.setData(dish);

                menuGridPane.add(cardNode, col, row);
                GridPane.setMargin(cardNode, new Insets(5));

                col ++;
                if(col == card){
                    col = 0;
                    row++;
                }
            } catch (Exception e) {
                showError("Errore nell'inserimento dei prodotti nel menù: " + e.getMessage());
            }
        }
    }
}
