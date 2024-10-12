package pe.edu.cibertec.app_t2_pregunta2.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.cibertec.app_t2_pregunta2.model.Usuario;
import pe.edu.cibertec.app_t2_pregunta2.repository.UsuarioRepository;
import pe.edu.cibertec.app_t2_pregunta2.service.IUsuarioService;

@RequiredArgsConstructor
@Service
public class UsuarioService implements IUsuarioService {
    private final UsuarioRepository usuarioRepository;

    @Override
    public Usuario buscarUsuarioPorCodigo(String codigo) {
        return usuarioRepository.findByCodigo(codigo);
    }
}
