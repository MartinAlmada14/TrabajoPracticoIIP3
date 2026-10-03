package Persistence;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Model.Pais;

public class GSONPersistencia implements IPersistencia {
	private static final Path DATA_DIR = Paths.get("data");
	private static final Path FILE_PATH = DATA_DIR.resolve("Datos.json");
	private final Gson gson; 
	
	public GSONPersistencia() {
		this.gson = new GsonBuilder().setPrettyPrinting().create();
		try {
			Files.createDirectories(DATA_DIR);
		}
		catch (Exception e) {
			throw new RuntimeException("nose pudo crear la carpeta", e);
		}
	}

	@Override
	public Optional<Pais> cargar() {
		if(!Files.exists(FILE_PATH)){
			return Optional.empty();
		}
		try(Reader reader = Files.newBufferedReader(FILE_PATH)){
			return Optional.ofNullable(gson.fromJson(reader, Pais.class));
		}
		catch (IOException e) {
			throw new RuntimeException("No se pudo leer " + FILE_PATH, e);
		}
	}
	
	@Override
	public Optional<Pais> cargar(Path archivo) {
		if(!Files.exists(archivo)) {
			return Optional.empty();
		}
		try(Reader reader = Files.newBufferedReader(archivo)) {
			return Optional.ofNullable(gson.fromJson(reader, Pais.class));
		}
		catch (IOException e) {
			throw new RuntimeException("No se pudo leer " + archivo, e);
		}
	}

	@Override
	public void guardar(Pais pais) {
		try (Writer writer = Files.newBufferedWriter(FILE_PATH)){
			gson.toJson(pais, writer);
		}
		catch (IOException e) {
			throw new RuntimeException("No se pudo guardar en " + FILE_PATH, e);
		}
		
	}
	
}
