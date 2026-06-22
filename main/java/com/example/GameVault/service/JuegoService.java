package com.example.GameVault.service;

import com.cloudinary.Cloudinary;
import com.example.GameVault.model.Juego;
import com.example.GameVault.repository.JuegoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class JuegoService {

    @Autowired
    private JuegoRepository juegoRepository;

    @Autowired
    private Cloudinary cloudinary;

    private static final String UPLOAD_DIR = "src/main/resources/static/uploads/";

    public List<Juego> listarTodos(){
        return juegoRepository.findAll();

    }

    public void guardarJuego(Juego juego, MultipartFile file){

      String nombreArchivo = "default.png";

        if(!file.isEmpty()){
                nombreArchivo = guardarImagenCloudinary(file);
            }

        juego.setPortadaUrl(nombreArchivo);
        juegoRepository.save(juego);



    }

    private String guardarImagenEnLocal(MultipartFile file) {
        try {
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String nombreArchivo = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

            Path filePath = uploadPath.resolve(nombreArchivo);

            Files.copy(file.getInputStream(), filePath);

            return nombreArchivo;
        } catch (IOException e) {
            e.printStackTrace();
            return "default.png";
        }
    }

    private String guardarImagenCloudinary(MultipartFile file){
          try{
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            return uploadResult.get("secure_url").toString();

        } catch (Exception e) {
              e.printStackTrace();
              return "default.png";
          }
    }

    public List<Juego> buscarPorTitulo(String titulo){
        if(titulo==null || titulo.trim().isEmpty()){
            return listarTodos();

        }

        return juegoRepository.findByTituloContainingIgnoreCase(titulo);
    }

    public Juego obtenerJuegoPorId(Long id){
        return juegoRepository.findById(id).orElse(null);

    }

    public void eliminarJuego(Long id){
        juegoRepository.deleteById(id);
    }

    public List<Juego> obtenerJuegosPorUsuario(Long usuarioId){
        return juegoRepository.findByUsuarioId(usuarioId);

    }

}
