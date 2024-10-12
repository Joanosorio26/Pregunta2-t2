package pe.edu.cibertec.app_t2_pregunta2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.cibertec.app_t2_pregunta2.model.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
}
