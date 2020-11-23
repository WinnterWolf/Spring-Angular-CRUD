package com.exercicio.crud.controller;


import com.exercicio.crud.exceptions.ResourceNotFoundException;
import com.exercicio.crud.models.Usuario;
import com.exercicio.crud.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController @CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/usuarios")
    public List<Usuario> getAllUsuarios(){
        return usuarioRepository.findAll();
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable(value = "id") long usuarioId) throws ResourceNotFoundException{
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(()-> new ResourceNotFoundException("Usuario não encontrado:: " + usuarioId));
        return ResponseEntity.ok().body(usuario);
    }

    @PostMapping("/usuarios")
    public Usuario createUsuario(@Valid @RequestBody Usuario usuario){
        return usuarioRepository.save(usuario);
    }


    @PutMapping("/usuarios/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable (value = "id") Long usuarioId,
                                                 @Valid @RequestBody Usuario usuarioDetails) throws ResourceNotFoundException{
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(()-> new ResourceNotFoundException("Usuario não encontrado para esse id:: " + usuarioId));
        usuario.setCpf(usuarioDetails.getCpf());
        usuario.setDataNascimento(usuarioDetails.getDataNascimento());
        usuario.setEstadoCivil(usuarioDetails.getEstadoCivil());
        usuario.setLogin(usuarioDetails.getLogin());
        usuario.setSenha(usuarioDetails.getSenha());
        usuario.setNome(usuarioDetails.getNome());
        usuario.setSexo(usuarioDetails.getSexo());

        final Usuario updatedUsuario = usuarioRepository.save(usuario);
        return ResponseEntity.ok(updatedUsuario);
    }

    @DeleteMapping("/usuarios/{id}")
    public Map<String, Boolean> deleteUsuario(@PathVariable(value = "id") Long usuarioId) throws ResourceNotFoundException{
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(()-> new ResourceNotFoundException("Usuario não encontrado para esse id:: " + usuarioId));

        usuarioRepository.delete(usuario);
        Map<String, Boolean> response = new HashMap<>();
        return response;
    }
}
