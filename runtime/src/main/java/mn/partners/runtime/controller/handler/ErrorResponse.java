package mn.partners.runtime.controller.handler;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ErrorResponse {
    private LocalDateTime dateTime;
    private int status;
    private String message;
    private String error;
    private String path;
}