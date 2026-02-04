package gui;

import controller.Controller;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import main.Interpreter;
import model.PrgState;
import model.adt.MyDictionary;
import model.adt.MyHeap;
import model.adt.MyList;
import model.adt.MyStack;
import model.statement.IStmt;
import repository.IRepository;
import repository.Repository;
import java.util.List;

// Import main.Interpreter-ul din pachetul default sau main


public class ProgramChooserController {
    @FXML
    private ListView<IStmt> programsListView;

    @FXML
    public void initialize() {
        List<IStmt> myAllPrgs = Interpreter.getExamples();
        programsListView.setItems(FXCollections.observableArrayList(myAllPrgs));
    }

    @FXML
    public void displayProgram(ActionEvent actionEvent) {
        IStmt selectedStmt = programsListView.getSelectionModel().getSelectedItem();
        if (selectedStmt == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No program selected!");
            alert.showAndWait();
            return;
        }

        int id = programsListView.getSelectionModel().getSelectedIndex() + 1;

        try {
            // Verificare tipuri
            selectedStmt.typecheck(new MyDictionary<>());

            // Creare stare initiala
            PrgState prgState = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), selectedStmt);
            IRepository repo = new Repository(prgState, "log" + id + ".txt");
            Controller controller = new Controller(repo);

            // Deschidere fereastra executor
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ProgramExecutor.fxml"));
            Parent root = loader.load();

            ProgramExecutorController executorController = loader.getController();
            executorController.setController(controller);

            Stage stage = new Stage();
            stage.setTitle("Program Executor");
            stage.setScene(new Scene(root, 900, 700));
            stage.show();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}