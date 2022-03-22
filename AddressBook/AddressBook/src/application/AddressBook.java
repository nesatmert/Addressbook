package application;

/*
 * Nesat Mert Yavas 181805021
 * Melih Celik      181805014
 * Sezer Kaynak     181805020
 * Gokalp Akoglu    181805035
 */
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

public class AddressBook extends Application{
	int index = 0;
	Person[] abArray;
	
	final static int ID_SIZE = 4;
	final static int NAME_SIZE = 32;
	final static int STREET_SIZE = 32;
	final static int CITY_SIZE = 20;
	final static int GENDER_SIZE = 1;
	final static int ZIP_SIZE = 5;
	final static int RECORD_SIZE = (ID_SIZE + NAME_SIZE + STREET_SIZE + CITY_SIZE + GENDER_SIZE + ZIP_SIZE);
	public RandomAccessFile raf;
	
	Label IDLabel = new Label("ID ");
	Label searchLabel = new Label("Search/Update ID");
	Label nameLabel = new Label("Name");
	Label streetLabel = new Label("Street");
	Label cityLabel = new Label("City");
	Label genderLabel = new Label("Gender");
	Label zipLabel = new Label("Zip");
	
	TextField IDtextBox = new TextField();
	TextField searchTextBox = new TextField();
	TextField nameTextBox = new TextField();
	TextField streetTextBox = new TextField();
	TextField cityTextBox = new TextField();
	TextField genderTextBox = new TextField();
	TextField zipTextBox = new TextField();
	
	Button buttonAdd = new Button("Add");
	Button buttonFirst = new Button("First");
	Button buttonNext = new Button("Next");
	Button buttonPrevious = new Button("Previous");
	Button buttonLast = new Button("Last");
	Button buttonUpdate = new Button("UpdateByID");
	Button buttonSearch = new Button("SearchByID");
	Button buttonClean = new Button("Clean textFields");
	public AddressBook() {
		try {
			raf = new RandomAccessFile("address.dat", "rw");
			abArray = new Person[100];
		}
		catch(IOException ex) {
			ex.printStackTrace();
			System.exit(1);
		}
	}
	@Override
	public void start(Stage arg0) throws Exception {
		AnchorPane anchor = new AnchorPane();
				
		IDLabel.setMaxWidth(25);
		anchor.getChildren().add(IDLabel);
		AnchorPane.setTopAnchor(IDLabel, 22.5);
		AnchorPane.setLeftAnchor(IDLabel, 100.0);
				
		IDtextBox.setEditable(false);
        IDtextBox.setDisable(true);
        IDtextBox.setMaxWidth(60);
		anchor.getChildren().add(IDtextBox);
		AnchorPane.setTopAnchor(IDtextBox, 20.0);
		AnchorPane.setLeftAnchor(IDtextBox, 140.0);
				
		anchor.getChildren().add(searchLabel);
		AnchorPane.setTopAnchor(searchLabel, 22.5);
		AnchorPane.setLeftAnchor(searchLabel, 210.0);
				
		searchTextBox.setMinWidth(190);
		anchor.getChildren().add(searchTextBox);
		AnchorPane.setTopAnchor(searchTextBox, 20.0);
		AnchorPane.setLeftAnchor(searchTextBox, 310.0);
				
		anchor.getChildren().add(nameLabel);
		AnchorPane.setTopAnchor(nameLabel, 52.5);
		AnchorPane.setLeftAnchor(nameLabel, 100.0);
				
		nameTextBox.setMinWidth(360);
		anchor.getChildren().add(nameTextBox);
		AnchorPane.setTopAnchor(nameTextBox, 50.0);
		AnchorPane.setLeftAnchor(nameTextBox, 140.0);
				
		anchor.getChildren().add(streetLabel);
		AnchorPane.setTopAnchor(streetLabel, 82.5);
		AnchorPane.setLeftAnchor(streetLabel, 100.0);
				
		streetTextBox.setMinWidth(360);
		anchor.getChildren().add(streetTextBox);
		AnchorPane.setTopAnchor(streetTextBox, 80.0);
		AnchorPane.setLeftAnchor(streetTextBox, 140.0);
				
		anchor.getChildren().add(cityLabel);
		AnchorPane.setTopAnchor(cityLabel, 112.5);
		AnchorPane.setLeftAnchor(cityLabel, 100.0);
				
		cityTextBox.setMaxWidth(150);
		anchor.getChildren().add(cityTextBox);
		AnchorPane.setTopAnchor(cityTextBox, 110.0);
		AnchorPane.setLeftAnchor(cityTextBox, 140.0);
				
		anchor.getChildren().add(genderLabel);
		AnchorPane.setTopAnchor(genderLabel, 112.5);
		AnchorPane.setLeftAnchor(genderLabel, 300.0);
				
		genderTextBox.setMaxWidth(30);
		anchor.getChildren().add(genderTextBox);
		AnchorPane.setTopAnchor(genderTextBox, 110.0);
		AnchorPane.setLeftAnchor(genderTextBox, 350.0);
		
		anchor.getChildren().add(zipLabel);
		AnchorPane.setTopAnchor(zipLabel, 112.5);
		AnchorPane.setLeftAnchor(zipLabel, 395.0);
			
		zipTextBox.setMaxWidth(80);
		anchor.getChildren().add(zipTextBox);
		AnchorPane.setTopAnchor(zipTextBox, 110.0);
		AnchorPane.setLeftAnchor(zipTextBox, 420.0);
				
		HBox hbox = new HBox(buttonAdd, buttonFirst, buttonNext, buttonPrevious, buttonLast, buttonUpdate, buttonSearch, buttonClean);
		anchor.getChildren().add(hbox);
		AnchorPane.setBottomAnchor(hbox, 5.0);
		AnchorPane.setLeftAnchor(hbox, 5.0);
		AnchorPane.setRightAnchor(hbox, 5.0);
		hbox.setSpacing(5);
		hbox.setAlignment(Pos.BOTTOM_CENTER);
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information Dialog");
		alert.setHeaderText("Look, an information dialog");
		
		Alert alertW = new Alert(AlertType.WARNING);
		alertW.setTitle("Information Dialog");
		alertW.setHeaderText("WARNING!");
		
		Scene scene = new Scene(anchor, 600, 192);
		arg0.setResizable(false);
		arg0.setTitle("Address Book");
		arg0.setScene(scene);
		arg0.show();
		
		try {
			if(raf.length() > 0) {
				long currentPos = raf.getFilePointer();
				while(currentPos < raf.length()) {
					readFileFillArray(abArray, currentPos,index, true);
					currentPos = raf.getFilePointer();
				}
				readFileByPos(0);
			}
		}
		catch(IOException ex) {
			ex.printStackTrace();		
		}

		genderTextBox.setTextFormatter(new TextFormatter<>(change -> {
            if (!change.getText().isEmpty()) {
            	if((change.getText().matches("M") || change.getText().matches("F")) && change.getControlNewText().length() <= 1) {
					return change;
				}
				else {
					change.setText("");
					alertW.setContentText("Please enter only M and F for gender!");
	            	alertW.showAndWait();
				}
            }
            return change;
        }));
		zipTextBox.setTextFormatter(new TextFormatter<>(change -> {
            if (!change.getText().isEmpty()) {
                try {
                    if(change.getControlNewText().length() > 5) {
                        change.setText("");
                        alertW.setContentText("Please enter 5 numbers for zip number!");
                        alertW.showAndWait();
                    }
                    else {
                        if(Integer.valueOf(change.getText()) > 0) {
                            return change;
                        }
                    }
                    
                }
                catch (Exception e) {
                    change.setText("");
                    alertW.setContentText("Please enter only numbers for zip number!");
                    alertW.showAndWait();
                }        
            }
            return change;
        }));
		
		buttonAdd.setOnAction(e->{
			try {
				if(emptySpace(nameTextBox.getText(),streetTextBox.getText(),cityTextBox.getText(),genderTextBox.getText(),zipTextBox.getText())){
	                   alertW.setContentText("There is an empty field!");
	                   alertW.showAndWait();
	            }
				else {
					if(samePerson(nameTextBox.getText(),streetTextBox.getText(),cityTextBox.getText(),genderTextBox.getText(),zipTextBox.getText())) {
						alertW.setContentText("There is a person with same informations!");
		                alertW.showAndWait();
					}
					else {
						writeAddressToFile(raf.length(), index);
						readFileFillArray(abArray, RECORD_SIZE*2*(index), index, true);
						alert.setContentText("Record is added successfully!");
						alert.showAndWait();
						cleanTextField();
					}	
				}							
			}
			catch(Exception ex) {
				
			}
		});
		buttonClean.setOnAction(e->{
			
			try {
				cleanTextField();
				alert.setContentText("Text fields are cleaned.");
				alert.showAndWait();
				
			}
			catch(Exception ex) {
				
			}
		});
		buttonFirst.setOnAction(e->{
			
			try {
				traverseArray(abArray, 0);
			}
			catch(Exception ex) {
				alertW.setContentText("There is no person in Address Book!");
				alertW.showAndWait();
			}
		});
		buttonNext.setOnAction(e->{
			
			try {
				int a = Integer.valueOf(IDtextBox.getText().trim());
				for(int i = 0; i < index; i++) {
					if(abArray[i].getId() == a) {
						traverseArray(abArray, i+1);	
						break;
					}
				}
			}
			catch(Exception ex) {
				
				if(abArray[0] == null) {
					alertW.setContentText("There is no person in Address Book!");
					alertW.showAndWait();
				}
				else {
					alert.setContentText("Last person is displayed on screen!");
					alert.showAndWait();
				}
			}
		});
		buttonLast.setOnAction(e->{
			
			try {
				traverseArray(abArray, index-1);
			}
			catch(Exception ex) {
				
				alertW.setContentText("There is no person in Address Book!");
				alertW.showAndWait();
			}
		});
		buttonPrevious.setOnAction(e->{
			
			try {
				int a = Integer.valueOf(IDtextBox.getText());
				for(int i = 0; i < index; i++) {
					if(abArray[i].getId() == a) {
						traverseArray(abArray, i-1);
						break;
					}
				}	
			}
			catch(Exception ex) {
				if(abArray[0] == null) {
					alertW.setContentText("There is no person in Address Book!");
					alertW.showAndWait();
				}
				else {
					alert.setContentText("First person is displayed on screen!");
					alert.showAndWait();
				}
				
			}
		});
		buttonSearch.setOnAction(e->{
			
			try {
				int a = Integer.valueOf(searchTextBox.getText());
				int temp = 0;
				for(int i = 0; i < index; i++) {
					if(abArray[i].getId() == a) {
						traverseArray(abArray, i);
						break;
					}
					else {
						temp += 1;
					}
				}
				if(temp == index) {
					alert.setContentText("ID is not found!");
					alert.showAndWait();		
				}
			}
			catch(Exception ex) {
				if(abArray[0] == null) {
					alertW.setContentText("There is no person in Address Book!");
					alertW.showAndWait();
				}
				else {
					alertW.setContentText("There is no ID in Search/Update ID text field!");
					alertW.showAndWait();
				}	
			}
		});
		buttonUpdate.setOnAction(e->{
            
            try {
                int a = Integer.valueOf(searchTextBox.getText());
                int temp = 0;
                for(int i = 0; i < index; i++) {
                    if(a == abArray[i].getId()) {
                        temp = i;
                        break;
                    }
                }
                if(emptySpace(nameTextBox.getText(),streetTextBox.getText(),cityTextBox.getText(),genderTextBox.getText(),zipTextBox.getText())){
                       alertW.setContentText("There is an empty field!");
                       alertW.showAndWait();
                }
                else {
                    if(samePerson(nameTextBox.getText(),streetTextBox.getText(),cityTextBox.getText(),genderTextBox.getText(),zipTextBox.getText())) {
                        alertW.setContentText("There is no change in informations!");
                        alertW.showAndWait();
                    }
                    else{
                        List<String> replaced = findReplaced(nameTextBox.getText(),streetTextBox.getText(),cityTextBox.getText(),genderTextBox.getText(),zipTextBox.getText(),temp);;
                        String printReplaced = "";
                        writeAddressToFile(RECORD_SIZE*2*(temp), temp);
                        readFileFillArray(abArray, RECORD_SIZE*2*temp, temp, false);
                        for(String i:replaced) {
                            printReplaced = printReplaced + i + '\n';
                        }
                        alert.setContentText("ID " + searchTextBox.getText().trim() + " is updated with new information(s).\n" + printReplaced);
                        alert.showAndWait();
                    }
                }                
            }
			catch(Exception ex) {
				if(searchTextBox.getText().length() == 0) {
					alertW.setContentText("There is no ID in Search/Update ID text field!");
					alertW.showAndWait();
				}
			}
		});
	}
	public void readFileFillArray(Person[] people, long position, int index1, boolean a) throws IOException {
		raf.seek(position);
		String id = FileOperations.readFixedLengthString(ID_SIZE, raf);
		int intID = Integer.parseInt(id.trim().toString());
		String name = FileOperations.readFixedLengthString(NAME_SIZE, raf).trim();
		String street = FileOperations.readFixedLengthString(STREET_SIZE, raf).trim();
		String city = FileOperations.readFixedLengthString(CITY_SIZE, raf).trim();
		String gender = FileOperations.readFixedLengthString(GENDER_SIZE, raf).trim();
		String zip = FileOperations.readFixedLengthString(ZIP_SIZE, raf).trim();
		
		Person p = new Person(intID, name, street, city, zip, gender);
		people[index1] = p;
		if(a){index++;}		
	}
	
	public void readFileByPos(long position) throws IOException{
		raf.seek(position);
		String id = FileOperations.readFixedLengthString(ID_SIZE, raf).trim();
		String name = FileOperations.readFixedLengthString(NAME_SIZE, raf).trim();
		String street = FileOperations.readFixedLengthString(STREET_SIZE, raf).trim();
		String city = FileOperations.readFixedLengthString(CITY_SIZE, raf).trim();
		String gender = FileOperations.readFixedLengthString(GENDER_SIZE, raf).trim();
		String zip = FileOperations.readFixedLengthString(ZIP_SIZE, raf).trim();
		
		IDtextBox.setText(id);
		nameTextBox.setText(name);
		streetTextBox.setText(street);
		cityTextBox.setText(city);
		genderTextBox.setText(gender);
		zipTextBox.setText(zip);
	}
	public void writeAddressToFile(long position, int index1) {
		try {
			raf.seek(position);
			FileOperations.writeFixedLengthString(String.valueOf(index1 + 1), ID_SIZE, raf);
			FileOperations.writeFixedLengthString(nameTextBox.getText(), NAME_SIZE, raf);
			FileOperations.writeFixedLengthString(streetTextBox.getText(), STREET_SIZE, raf);
			FileOperations.writeFixedLengthString(cityTextBox.getText(), CITY_SIZE, raf);
			FileOperations.writeFixedLengthString(genderTextBox.getText(), GENDER_SIZE, raf);
			FileOperations.writeFixedLengthString(zipTextBox.getText(), ZIP_SIZE, raf);
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	public void cleanTextField() {
		IDtextBox.clear();
		nameTextBox.clear();
		streetTextBox.clear();
		cityTextBox.clear();
		genderTextBox.clear();
		zipTextBox.clear();
	}
	
	public static boolean emptySpace(String a, String b, String c, String d, String e){
        if( a.length() == 0 || b.length() == 0 || c.length() == 0 || d.length() == 0 || e.length() == 0 ){
        	return true;
        }
        else{
        	return false;
        }
	} 
	public boolean samePerson(String a, String b, String c, String d, String e) {
		for(int i = 0; i < index; i++) {
			if(abArray[i].getName().contains(a) & abArray[i].getStreet().contains(b) & abArray[i].getCity().contains(c) & abArray[i].getGender().contains(d) & abArray[i].getZip().contains(e)) {				
				return true;
			}
		}
		return false;
	}
	public List<String> findReplaced(String a, String b, String c, String d, String e, int temp) {
		List<String> replaced = new ArrayList<String>();
		if(!abArray[temp].getName().contains(a)) {replaced.add(abArray[temp].getName() + "->" + a);}
		if(!abArray[temp].getStreet().contains(b)) {replaced.add(abArray[temp].getStreet() + "->" + b);}
		if(!abArray[temp].getCity().contains(c)) {replaced.add(abArray[temp].getCity() + "->" + c);}
		if(!abArray[temp].getGender().contains(d)) {replaced.add(abArray[temp].getGender() + "->" + d);}
		if(!abArray[temp].getZip().contains(e)) {replaced.add(abArray[temp].getZip() + "->" + e);}
		return replaced;		
	}
	public void traverseArray(Person[] people, int index) {
		IDtextBox.setText(String.valueOf(people[index].getId()));
		nameTextBox.setText(String.valueOf(people[index].getName()));
		streetTextBox.setText(String.valueOf(people[index].getStreet()));
		cityTextBox.setText(String.valueOf(people[index].getCity()));
		genderTextBox.setText(String.valueOf(people[index].getGender()));
		zipTextBox.setText(String.valueOf(people[index].getZip()));
	}
	public static void main(String[] args) {
		Application.launch(args);	
	}
}

