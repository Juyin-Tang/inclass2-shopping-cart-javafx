package com.example.shoppingcart;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.*;

public class Controller {
    @FXML private Label lblLanguage;
    @FXML private ChoiceBox<String> languageChoiceBox;
    @FXML private Label lblItemCount;
    @FXML private TextField txtItemCount;
    @FXML private Button btnConfirmCount;
    @FXML private VBox itemsContainer;
    @FXML private Button btnCalculate;
    @FXML private Label lblResult;

    private LocalizationService localizationService = new LocalizationService();
    private Map<String, String> languageMap = new HashMap<>();

    private List<TextField> priceFields = new ArrayList<>();
    private List<TextField> quantityFields = new ArrayList<>();

    public void initialize() {
        languageMap.put("English", "en");
        languageMap.put("Finnish", "fi");
        languageMap.put("Swedish", "sv");
        languageMap.put("Japanese", "ja");
        languageMap.put("Arabic", "ar");

        languageChoiceBox.getItems().addAll("English", "Finnish", "Swedish", "Japanese", "Arabic");
        languageChoiceBox.setValue("English");
        loadLanguage("en");

        languageChoiceBox.setOnAction(e -> {
            String selected = languageChoiceBox.getValue();
            String langCode = languageMap.get(selected);
            loadLanguage(langCode);
        });

        btnConfirmCount.setOnAction(e -> onConfirmCount());
        btnCalculate.setOnAction(e -> calculateTotal());
    }

    private void loadLanguage(String langCode) {
        localizationService.loadLanguage(langCode);
        updateStaticTexts();
    }

    private void updateStaticTexts() {
        lblLanguage.setText(localizationService.getMessage("language.label"));
        lblItemCount.setText(localizationService.getMessage("item.count.prompt"));
        btnConfirmCount.setText(localizationService.getMessage("confirm.button"));
        btnCalculate.setText(localizationService.getMessage("calculate.button"));
        lblResult.setText(localizationService.getMessage("result.ready"));
    }

    @FXML
    public void onConfirmCount() {
        try {
            int count = Integer.parseInt(txtItemCount.getText());
            if (count <= 0) throw new NumberFormatException();
            createItemInputs(count);
            btnCalculate.setDisable(false);
            lblResult.setText(localizationService.getMessage("result.ready"));
        } catch (NumberFormatException e) {
            lblResult.setText(localizationService.getMessage("invalid.number"));
        }
    }

    private void createItemInputs(int count) {
        itemsContainer.getChildren().clear();
        priceFields.clear();
        quantityFields.clear();

        for (int i = 1; i <= count; i++) {
            HBox row = new HBox(10);
            Label label = new Label(localizationService.getMessage("item.prefix", i));
            TextField priceField = new TextField();
            priceField.setPromptText(localizationService.getMessage("price.prompt"));
            TextField quantityField = new TextField();
            quantityField.setPromptText(localizationService.getMessage("quantity.prompt"));

            priceFields.add(priceField);
            quantityFields.add(quantityField);

            row.getChildren().addAll(label, priceField, quantityField);
            itemsContainer.getChildren().add(row);
        }
    }

    @FXML
    public void calculateTotal() {
        List<CartItem> cartItems = new ArrayList<>();
        double total = 0.0;
        StringBuilder resultBuilder = new StringBuilder();

        for (int i = 0; i < priceFields.size(); i++) {
            try {
                double price = Double.parseDouble(priceFields.get(i).getText());
                int qty = Integer.parseInt(quantityFields.get(i).getText());
                double itemTotal = price * qty;
                total += itemTotal;
                cartItems.add(new CartItem(i + 1, price, qty, itemTotal));
                resultBuilder.append(localizationService.getMessage("item.total", i + 1, itemTotal))
                        .append("\n");
            } catch (NumberFormatException e) {
                resultBuilder.append(localizationService.getMessage("item.error", i + 1))
                        .append("\n");
            }
        }
        resultBuilder.append(localizationService.getMessage("cart.total", total));
        lblResult.setText(resultBuilder.toString());

        String currentLang = languageMap.get(languageChoiceBox.getValue());
        CartService cartService = new CartService();
        cartService.saveCart(priceFields.size(), total, currentLang, cartItems);
    }
}