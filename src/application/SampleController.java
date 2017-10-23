package application;
/**
 * @author Javier Pérez Jaurena
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class SampleController {

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

	@FXML
	private TextField txtFDatos;

	@FXML
	private TextArea edArea;

	@FXML
	private JFXButton btnExcel;

	@FXML
	private ImageView btnWord;

	@FXML
	private JFXButton btnPowerPoint;

	@FXML
	private JFXButton btnNavegar;

	@FXML
	private JFXButton btnUniversidad;

	@FXML
	void entroExcel(ActionEvent event) {
		edArea.setAccessibleText("Entro en Excel");
		pb = new ProcessBuilder("C:\\Program Files\\Microsoft Office\\Office15\\EXCEL.EXE");
		try {
			excel = pb.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void entroUni(ActionEvent event) {
		try {
			r.exec("rundll32 url.dll, FileProtocolHandler " + "http://universidadeuropea.es/");

		} catch (IOException e) {
			edArea.setText("Dirección incorrecta");
		}
	}

	@FXML
	void entroNavego(ActionEvent event) {

		try {
			r.exec("rundll32 url.dll, FileProtocolHandler " + txtFDatos.getText().trim());

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
				r.exec("rundll32 url.dll, FileProtocolHandler " + edArea.getSelectedText());
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

			e.printStackTrace();
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
	void entroPowerPoint(ActionEvent event) {
		pb = new ProcessBuilder("C:\\Program Files\\Microsoft Office\\Office15\\POWERPNT.EXE");
		try {
			powerPoint = pb.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void entroWord(ActionEvent event) {
		pb = new ProcessBuilder("C:\\Program Files\\Microsoft Office\\Office15\\WINWORD.EXE");
		try {
			word = pb.start();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void inicializarTabla(Main main) {

		leoFichero();
	}
}
