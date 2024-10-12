package pe.edu.cibertec.app_t2_pregunta2.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pe.edu.cibertec.app_t2_pregunta2.model.Rol;
import pe.edu.cibertec.app_t2_pregunta2.model.Usuario;
import pe.edu.cibertec.app_t2_pregunta2.service.IUsuarioService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class DetUsuarioService implements UserDetailsService {
    private final IUsuarioService usuarioService;

    public List<GrantedAuthority> obtenerRoldeUsuario(Set<Rol> rolesList){
        List<GrantedAuthority> rolesAuth = new ArrayList<>();
        for (Rol rol : rolesList){
            rolesAuth.add(new SimpleGrantedAuthority(
                    "ROLE_"+rol.getRolusu()));
        }
        return rolesAuth;
    }
    private UserDetails crearUserDetail(
            Usuario usuario, List<GrantedAuthority> authorityList
    ){
        return new User(
                usuario.getCodigo(),
                usuario.getPassword(),
                usuario.getActivo(),
                true,
                true,
                true,
                authorityList);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioService.buscarUsuarioPorCodigo(username);

        return crearUserDetail(usuario,
                obtenerRoldeUsuario(usuario.getRoles()));
    }
}
