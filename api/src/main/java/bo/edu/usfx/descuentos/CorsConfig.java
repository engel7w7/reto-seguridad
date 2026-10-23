package bo.edu.usfx.descuentos;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * El front-end (Vercel) y la API (Render) viven en dominios distintos: sin CORS el navegador bloquea las llamadas.
 * Los origenes permitidos vienen de la variable de entorno CORS_ORIGINS.
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    private final String[] origenes;

    public CorsConfig(@Value("${app.cors-origins}") String[] origenes) {
        if (origenes != null && Arrays.stream(origenes).anyMatch(o -> o != null && o.trim().equals("*"))) {
            throw new IllegalArgumentException("CORS no puede permitir comodin *");
        }
        this.origenes = origenes != null ? origenes.clone() : new String[0];
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOriginPatterns(origenes)
                .allowedMethods("GET", "POST", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(false);
    }
}
