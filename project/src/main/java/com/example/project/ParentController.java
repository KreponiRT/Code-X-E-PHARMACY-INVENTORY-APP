package com.example.project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;
import java.time.LocalDate;

/**
 * All the UI's will be implemented in the same controller
 * @implNote if there is an issue concerning mysql connector, refer to the readme file for
 * a solution.
 */



public class ParentController {
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
  private TableView<Drugs> viewDrugsTableView;
  @FXML
  private TableColumn<Drugs, Integer> idTableColumn,quantityTableColumn;
  @FXML
  private TableColumn<Drugs,String> nameTableColumn,supplierTableColumn,ageGroupTableColumn;
  @FXML
  private TableColumn<Drugs,Double>priceTableColumn;
  @FXML
  private TableColumn<Drugs,Date> productionDateTableColumn,expiryDateTableColumn;
  ObservableList<Drugs> drugsObservableList = FXCollections.observableArrayList();


//  Other tabs



//  Implementation of the add Goods Tab

  /**
   *
   * @param event
   * @throws SQLException
   */
  DatabaseConnection connectNow = new DatabaseConnection();
  Connection connectDB = connectNow.getConnection();
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


//    We fetch first
    String fetchQuery = "select * from drugs_table";
    Statement statement = connectDB.createStatement();
    ResultSet resultSet1 = statement.executeQuery(fetchQuery);


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

    String key = searchDrugTextField.getText();
    if (key.isBlank()){
      viewDrugsTableView.getItems().clear();
      populate();
    }else {
      viewDrugsTableView.getItems().clear();
      populatePartial(key);
    }
  }

  private void populatePartial(String key) {
    int numberkey = Integer.parseInt(key);
    try {
      String query = "select * from drugs_table where id = "+numberkey+" or drug_name = '"+key+"'";
      Statement statement = connectDB.createStatement();
      ResultSet resultSet = statement.executeQuery(query);
      while (resultSet.next()){
        int id= resultSet.getInt("id");
        String name= resultSet.getString("drug_name");
        String supplier= resultSet.getString("supplier_name");
        double price = resultSet.getDouble("price");
        Integer quantity = resultSet.getInt("quantity");
        String ageGroup = resultSet.getString("age_group");
        Date exp = resultSet.getDate("expiry_date");
        Date prod = resultSet.getDate("production_date");
        String pres = resultSet.getString("prescription");
        String des = resultSet.getString("description");

        drugsObservableList.add(new Drugs(id,quantity,price,name,supplier,ageGroup ,pres,des,exp,prod));
        idTableColumn.setCellValueFactory(new PropertyValueFactory<>("drugId"));
        nameTableColumn.setCellValueFactory(new PropertyValueFactory<>("drugName"));
        supplierTableColumn.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        priceTableColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        quantityTableColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        ageGroupTableColumn.setCellValueFactory(new PropertyValueFactory<>("ageGroup"));
        productionDateTableColumn.setCellValueFactory(new PropertyValueFactory<>("productionDate"));
        expiryDateTableColumn.setCellValueFactory(new PropertyValueFactory<>("expiryDate"));
        viewDrugsTableView.setItems(drugsObservableList);
      }
    }catch (Exception e){
      e.printStackTrace();
      e.getCause();
      System.out.println("Searching..");
    }
  }

  @FXML
  private void initialize() {
    ageGroupChoiceBox.getItems().addAll(ageGroupChoices);

//    populating the table with all the drugs available
    viewDrugsTableView.getItems().clear();
    populate();
  }

//  End of implementation

  private void populate() {
    try {
      String query = "select * from drugs_table";
      Statement statement = connectDB.createStatement();
      ResultSet resultSet = statement.executeQuery(query);
      while (resultSet.next()){
        int id= resultSet.getInt("id");
        String name= resultSet.getString("drug_name");
        String supplier= resultSet.getString("supplier_name");
        double price = resultSet.getDouble("price");
        Integer quantity = resultSet.getInt("quantity");
        String ageGroup = resultSet.getString("age_group");
        Date exp = resultSet.getDate("expiry_date");
        Date prod = resultSet.getDate("production_date");
        String pres = resultSet.getString("prescription");
        String des = resultSet.getString("description");

        drugsObservableList.add(new Drugs(id,quantity,price,name,supplier,ageGroup ,pres,des,exp,prod));
        idTableColumn.setCellValueFactory(new PropertyValueFactory<>("drugId"));
        nameTableColumn.setCellValueFactory(new PropertyValueFactory<>("drugName"));
        supplierTableColumn.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        priceTableColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        quantityTableColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        ageGroupTableColumn.setCellValueFactory(new PropertyValueFactory<>("ageGroup"));
        productionDateTableColumn.setCellValueFactory(new PropertyValueFactory<>("productionDate"));
        expiryDateTableColumn.setCellValueFactory(new PropertyValueFactory<>("expiryDate"));
        viewDrugsTableView.setItems(drugsObservableList);
      }
    }catch (Exception e){
      System.out.println("Populating");
    }
  }
}

