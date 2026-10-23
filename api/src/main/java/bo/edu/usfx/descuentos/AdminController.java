package bo.edu.usfx.descuentos;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** GET /api/admin/estadisticas: cantidad de calculos atendidos. Es solo para la administracion. */
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final VerificadorToken verificador;
    private final Estadisticas estadisticas;

    public AdminController(VerificadorToken verificador, Estadisticas estadisticas) {
        this.verificador = verificador;
        this.estadisticas = estadisticas;
    }

    @GetMapping("/estadisticas")
    public ResponseEntity<?> estadisticas(@RequestHeader(value = "X-Admin-Token", required = false) String token) {
        if (!verificador.habilitado()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        if (!verificador.autoriza(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(Map.of("calculos", estadisticas.calculos()));
    }
}
