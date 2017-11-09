package application;
/**
 * @author Javier Pérez Jaurena
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class SampleController implements Initializable{

	Runtime r = Runtime.getRuntime();
	Process excel = null;
	Process word = null;
	Process powerPoint = null;
	ProcessBuilder pb = null;
	ArrayList<String> direcciones = Main.direcciones;
	BufferedWriter bw = null;
	FileOutputStream fos = null;
	FileInputStream fis = null;
	BufferedReader br = null;
	static int cont = 0;
	Stage st;

	@FXML
	private TextField txtFDatos;

	@FXML
	private TextArea edArea;

	@FXML
	private ImageView btnWord;

	@FXML
	void entroExcel(MouseEvent event) {
		
		try {
			Runtime.getRuntime().exec("rundll32 url.dll, FileProtocolHandler " + "EXCEL");

		} catch (IOException e) {
			edArea.setText("Dirección incorrecta");
		}
	}

	@FXML
	void entroNavego(MouseEvent event) {

		try {
			Runtime.getRuntime().exec("rundll32 url.dll, FileProtocolHandler " + txtFDatos.getText().trim());
		

		} catch (IOException e) {
			edArea.setText("Dirección incorrecta");
		}
		int encontrado = 0;
		String guardar = txtFDatos.getText().trim().toString();
		for (String datos : direcciones) {
			if (datos.equalsIgnoreCase(guardar)) {
				encontrado++;
			}
		}
		if (encontrado == 0) {
			escriboFichero(guardar.trim());
			leoFichero();
		}
	}

	@FXML
	void entroDirecto(MouseEvent event) {

		if (!edArea.getSelectedText().isEmpty()) {

			try {
				Runtime.getRuntime().exec("rundll32 url.dll, FileProtocolHandler " + edArea.getSelectedText());
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
	}

	public void leoFichero() {

		try {
			br = new BufferedReader(new FileReader("fichero.txt"));
			String linea = null;
			String str = "";

			while ((linea = br.readLine()) != null) {
				str += linea + "\n";
				edArea.setText(str);
				direcciones.add(cont, linea.trim());
				cont++;
			}
			br.close();
		} catch (FileNotFoundException e) {

			File archivo = new File("fichero.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void escriboFichero(String guardar) {

		try {
			bw = new BufferedWriter(new FileWriter("fichero.txt", true));
			bw.write(guardar + "\n");
			bw.close();
			direcciones.add(cont, guardar.trim());
			cont++;

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void entroPowerPoint(MouseEvent event) {
		try {
			Runtime.getRuntime().exec("rundll32 url.dll, FileProtocolHandler " + "powerpnt");

		} catch (IOException e) {
			edArea.setText("Dirección incorrecta");
		}
	}

	@FXML
	void entroWord(MouseEvent event) {
		try {
			Runtime.getRuntime().exec("rundll32 url.dll, FileProtocolHandler " + "WINWORD");

		} catch (IOException e) {
			edArea.setText("Dirección incorrecta");
		}
	}
	/*
	public void inicializarTabla(Main main) {

		leoFichero();
	}
	*/
	@FXML
	void salgoPrograma(MouseEvent event) {
		
		System.exit(0);
	}
//Este metodo implementado es la bomba. Arranca antes de la app y sirve para que cargue el fichero antes de abrir el programa
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		leoFichero();
	}
}
