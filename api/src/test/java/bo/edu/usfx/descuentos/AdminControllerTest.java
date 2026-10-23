package bo.edu.usfx.descuentos;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(properties = "app.admin-token=token-de-prueba-0123456789")
@AutoConfigureMockMvc
class AdminControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void rechazaPeticionSinToken() throws Exception {
        mvc.perform(get("/api/admin/estadisticas"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void rechazaPeticionConTokenInvalido() throws Exception {
        mvc.perform(get("/api/admin/estadisticas")
                        .header("X-Admin-Token", "token-invalido-123456"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void autorizaPeticionConTokenCorrecto() throws Exception {
        mvc.perform(get("/api/admin/estadisticas")
                        .header("X-Admin-Token", "token-de-prueba-0123456789"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.calculos").isNumber());
    }
}
