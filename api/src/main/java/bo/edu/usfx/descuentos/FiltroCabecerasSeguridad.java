package bo.edu.usfx.descuentos;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Agrega las cabeceras de seguridad recomendadas por OWASP a todas las respuestas HTTP de la API.
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class FiltroCabecerasSeguridad implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (response instanceof HttpServletResponse httpResponse) {
            httpResponse.setHeader("X-Content-Type-Options", "nosniff");
            httpResponse.setHeader("X-Frame-Options", "DENY");
            httpResponse.setHeader("Content-Security-Policy", "default-src 'none'; frame-ancestors 'none'");
            httpResponse.setHeader("Referrer-Policy", "no-referrer");
            httpResponse.setHeader("Cache-Control", "no-store");
            httpResponse.setHeader("Strict-Transport-Security", "max-age=31536000; includeSubDomains");
        }
        chain.doFilter(request, response);
    }
}
