package br.ufc.great.es.api.demo;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.ufc.great.es.api.demo.utils.Constantes;


@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		new Constantes();
		String uploadsPath = Constantes.uploadDirectory;
		System.out.println("Diretório de controle de uploads: "+uploadsPath);
		new File(uploadsPath).mkdir();
		
		SpringApplication.run(DemoApplication.class, args);
	}

}

