package Extrator.extratorLSG;

import Extrator.extratorLSG.api.readers.Inicializador;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import static Extrator.extratorLSG.api.readers.Inicializador.defFolder;
import static Extrator.extratorLSG.api.readers.Inicializador.finalizar;
import static Extrator.extratorLSG.api.readers.Parsing.parseOneLattes;

@SpringBootApplication
public class ExtratorLsgApplication {

	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
		Inicializador.init();
		File dir = new File(defFolder);
		System.out.println(defFolder);
		File[] files = dir.listFiles((dir1, name) -> name.endsWith(".xml"));
		String identificador[];
		for (File f : files) {
			identificador = f.getName().split(".x");
			try {
				parseOneLattes(identificador[0], defFolder + f.getName(), null);
			}catch (Exception e) {
				System.out.println("erro");
			}

		}
		finalizar();
	}

//		SpringApplication.run(ExtratorLsgApplication.class, args);
}

