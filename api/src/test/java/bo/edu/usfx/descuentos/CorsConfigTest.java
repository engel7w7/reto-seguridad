package bo.edu.usfx.descuentos;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class CorsConfigTest {

    @Test
    void rechazaComodinComoOrigen() {
        assertThrows(IllegalArgumentException.class, () -> new CorsConfig(new String[]{"*"}));
        assertThrows(IllegalArgumentException.class, () -> new CorsConfig(new String[]{"http://localhost:5173", "*"}));
    }

    @Test
    void aceptaOrigenesValidos() {
        assertDoesNotThrow(() -> new CorsConfig(new String[]{"http://localhost:5173", "http://localhost:4173"}));
    }
}
