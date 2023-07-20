package com.example.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.ResourceBundle;

/**
 * All the UI's will be implemented in the same controller
 * @implNote if there is an issue concerning mysql connector, refer to the readme file for
 * a solution.
 */



public class ParentController implements Initializable {
//  Add Goods Tab
  @FXML
  private TextField drugNameTextField,supplierNameTextField,priceTextField,
        quantityTextField,prescriptionTextField,idTextField;
  @FXML
    private DatePicker produtionDatePicker,expiryDatePicker;
  @FXML
    private TextArea descriptionTextArea;
  @FXML
    private Button addDrugButton;
  @FXML
  private ChoiceBox<String> ageGroupChoiceBox;

  String[] ageGroupChoices = {"Infants(below 1 year)","Children(1 to 12 years)",
          "Adolescents(13 to 17years)","Adults(18 and above)"};

//  View Drugs Tab
  @FXML
  private TextField searchDrugTextField;
  @FXML
  private Button searchDrugButton;
  @FXML
  private TableView<?> viewDrugsTableView;
  @FXML
  private TableColumn<?,?> idTableColumn,nameTableColumn,supplierTableColumn,priceTableColumn,quantityTableColumn,
  ageGroupTableColumn,productionDateTableColumn,expiryDateTableColumn;


//  Other tabs



//  Implementation of the add Goods Tab

  /**
   *
   * @param event
   * @throws SQLException
   */
  @FXML
  private void addDrugButtonOnAction(ActionEvent event) throws SQLException {
    String drugName = drugNameTextField.getText();
    String supplierName = supplierNameTextField.getText();
    String ageGroup = ageGroupChoiceBox.getValue();
    double price = Double.parseDouble(priceTextField.getText());
    int quantity = Integer.parseInt(quantityTextField.getText());
    String prescription = prescriptionTextField.getText();
    LocalDate productionDate = produtionDatePicker.getValue();
    LocalDate expiryDate = expiryDatePicker.getValue();
    String description = descriptionTextArea.getText();
    int id = Integer.parseInt(idTextField.getText());
    System.out.println(drugName + supplierName + ageGroup + price + quantity + prescription + productionDate +
            expiryDate + description);

    DatabaseConnection connectNow = new DatabaseConnection();
    Connection connectDB = connectNow.getConnection();
//    We fetch first
    String fetchQuery = "select * from drugs_table";
    Statement statement = connectDB.createStatement();
    ResultSet resultSet1 = statement.executeQuery(fetchQuery);

    Drugs drugs = null;
    while (resultSet1.next()) {
//      if the drug name or the drug id user inputs matches
//      in the database, then we will update else we will add
      if (resultSet1.getInt("id")==id || resultSet1.getString("drug_name").equals(drugName)) {
//      drug id or name match
        String updateQuery = "UPDATE drugs_table set supplier_name = '"+supplierName+"'," +
                " age_group = '"+ageGroup+"'," +
                "price = "+price+",quantity = "+quantity+"," +
                "prescription = '"+prescription+"', production_date='"+productionDate+"'," +
                "expiry_date = '"+expiryDate+"',supply_date = curdate()," +
                "description = '"+description+"' WHERE id="+id+" and drug_name = '"+drugName+"'";
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"The drug exist in the store, Do you want to update?",
                ButtonType.YES,ButtonType.NO);
        ButtonType result = alert.showAndWait().orElse(ButtonType.NO);
        if (ButtonType.YES.equals(result)){
          try {
            Statement statement1 = connectDB.createStatement();
            statement1.executeUpdate(updateQuery);
          }catch (Exception e){
            System.out.println("Update Successful");
          }
        }
      } else {
//        drug id or name mismatch
        String addQuery = "insert  drugs_table  values ("+id+",'"+drugName+"','"+supplierName+"','"+
                ageGroup +"',"+price+","+quantity+",'"+prescription+"','"+productionDate+"','"+
                expiryDate+"',"+"curdate()"+",'"+description+"')";
        Alert alert = new Alert(Alert.AlertType.INFORMATION,"Drugs have been added successfully",
                ButtonType.OK);
        ButtonType result = alert.showAndWait().orElse(ButtonType.OK);
        try {
          Statement statement1 = connectDB.createStatement();
          statement1.executeUpdate(addQuery);
          System.out.println("done");
        }catch (Exception e){
          System.out.println("Added successfully");
        }
      }
      break;
    }
  }

//  End of add goods implementation

//  Implementation of view drugs tab

  /**
   *
   * @param event
   */
  @FXML
  private void searchDrugButtonOnAction(ActionEvent event){
    System.out.println("You clicked search button!!!");
    String key = searchDrugTextField.getText();
    if (key.isBlank()){
      System.out.println("populate table with all");
    }else {
      System.out.println("populate with specified key");
    }
  }

  /**
   *
   * @param url
   * @param resourceBundle
   */
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    ageGroupChoiceBox.getItems().addAll(ageGroupChoices);
  }

//  End of implementation

}