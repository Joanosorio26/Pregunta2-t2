package pe.edu.cibertec.app_t2_pregunta2.service;

import pe.edu.cibertec.app_t2_pregunta2.model.Usuario;

public interface IUsuarioService {
    Usuario buscarUsuarioPorCodigo(String codigo);
}
