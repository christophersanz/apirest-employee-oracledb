package pe.com.tarjetaoh.app.exception;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorDetails {
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private Date timestamp;
    private String message;
    private String details;
}
