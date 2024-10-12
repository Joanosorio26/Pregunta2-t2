package pe.edu.cibertec.app_t2_pregunta2.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.cibertec.app_t2_pregunta2.dto.UsuarioResponseDto;
import pe.edu.cibertec.app_t2_pregunta2.model.Usuario;
import pe.edu.cibertec.app_t2_pregunta2.service.IUsuarioService;
import pe.edu.cibertec.app_t2_pregunta2.service.impl.DetUsuarioService;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/auth/t2")
public class AuthController {
    private final DetUsuarioService detUsuarioService;
    private final AuthenticationManager authenticationManager;
    private final IUsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<UsuarioResponseDto> autenticarUsuario(
            @RequestParam("usuario") String usuario,
            @RequestParam("password") String password

    ){
        try{
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(usuario,
                            password));
            if(authentication.isAuthenticated()){
                Usuario objUsuario = usuarioService.buscarUsuarioPorCodigo(usuario);
                String token = generarToken(objUsuario);
                return new ResponseEntity<>(
                        UsuarioResponseDto.builder().idusu(objUsuario.getIdusu())
                                .codigo(objUsuario.getCodigo())
                                .token(token).build(),
                        HttpStatus.OK);
            }else{
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }catch (Exception ex){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String generarToken(Usuario usuario) {
        String clave = "t2seleccion";
        List<GrantedAuthority> authorityList =
                detUsuarioService.obtenerRoldeUsuario(usuario.getRoles());
        long tresMinutosEnMilisegundos = 3 * 60 * 1000;  // 3 minutos convertidos a milisegundos
        return Jwts.builder()
                .setId(usuario.getIdusu().toString())
                .setSubject(usuario.getCodigo())
                .claim("email", usuario.getEmail())  // Añadir el email al payload
                .claim("authorities",
                        authorityList.stream().map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tresMinutosEnMilisegundos))  // 3 minutos
                .signWith(SignatureAlgorithm.HS512, clave.getBytes())
                .compact();
    }

}
