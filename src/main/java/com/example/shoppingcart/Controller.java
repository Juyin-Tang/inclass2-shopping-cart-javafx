package com.example.shoppingcart;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.text.MessageFormat;
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

    private ResourceBundle bundle;
    private Locale currentLocale;
    private List<HBox> itemRows = new ArrayList<>();
    private List<TextField> priceFields = new ArrayList<>();
    private List<TextField> quantityFields = new ArrayList<>();

    private final Map<String, Locale> languageMap = new HashMap<>();

    public void initialize() {
        languageMap.put("English", new Locale("en", "US"));
        languageMap.put("Finnish", new Locale("fi", "FI"));
        languageMap.put("Swedish", new Locale("sv", "SE"));
        languageMap.put("Japanese", new Locale("ja", "JP"));
        languageMap.put("Arabic", new Locale("ar", "AR"));

        languageChoiceBox.getItems().addAll("English", "Finnish", "Swedish", "Japanese", "Arabic");
        languageChoiceBox.setValue("English");
        setLanguage("en", "US");

        languageChoiceBox.setOnAction(e -> {
            String selected = languageChoiceBox.getValue();
            Locale loc = languageMap.get(selected);
            if (loc != null) {
                setLanguage(loc.getLanguage(), loc.getCountry());
            }
        });
    }

    private void setLanguage(String lang, String country) {
        currentLocale = new Locale(lang, country);
        bundle = ResourceBundle.getBundle("messages/MessagesBundle", currentLocale);
        updateStaticTexts();
    }

    private void updateStaticTexts() {
        lblLanguage.setText(bundle.getString("language.label"));
        lblItemCount.setText(bundle.getString("item.count.prompt"));
        btnConfirmCount.setText(bundle.getString("confirm.button"));
        btnCalculate.setText(bundle.getString("calculate.button"));
    }

    @FXML
    public void onConfirmCount() {
        try {
            int count = Integer.parseInt(txtItemCount.getText());
            if (count <= 0) throw new NumberFormatException();
            createItemInputs(count);
            btnCalculate.setDisable(false);
            lblResult.setText(bundle.getString("result.ready")); // 可添加属性
        } catch (NumberFormatException e) {
            lblResult.setText(bundle.getString("invalid.number"));
        }
    }

    private void createItemInputs(int count) {
        itemsContainer.getChildren().clear();
        priceFields.clear();
        quantityFields.clear();

        for (int i = 1; i <= count; i++) {
            HBox row = new HBox(10);
            Label label = new Label(MessageFormat.format(bundle.getString("item.prefix"), i));
            TextField priceField = new TextField();
            priceField.setPromptText(bundle.getString("price.prompt"));
            TextField quantityField = new TextField();
            quantityField.setPromptText(bundle.getString("quantity.prompt"));

            priceFields.add(priceField);
            quantityFields.add(quantityField);

            row.getChildren().addAll(label, priceField, quantityField);
            itemsContainer.getChildren().add(row);
        }
    }

    @FXML
    public void calculateTotal() {
        double total = 0.0;
        StringBuilder resultBuilder = new StringBuilder();

        for (int i = 0; i < priceFields.size(); i++) {
            try {
                double price = Double.parseDouble(priceFields.get(i).getText());
                int qty = Integer.parseInt(quantityFields.get(i).getText());
                double itemTotal = price * qty;
                total += itemTotal;
                resultBuilder.append(MessageFormat.format(bundle.getString("item.total"), i + 1, itemTotal))
                        .append("\n");
            } catch (NumberFormatException e) {
                resultBuilder.append(MessageFormat.format(bundle.getString("item.error"), i + 1))
                        .append("\n");
            }
        }
        resultBuilder.append(MessageFormat.format(bundle.getString("cart.total"), total));
        lblResult.setText(resultBuilder.toString());
    }
}