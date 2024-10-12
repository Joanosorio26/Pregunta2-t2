package pe.edu.cibertec.app_t2_pregunta2.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UsuarioResponseDto {
    private Integer idusu;
    private String codigo;
    private String token;
}
