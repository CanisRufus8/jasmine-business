package vn.jasmine.exception.repsonse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseBody implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private long code;
    private String message;
    private LocalDateTime errorTime;

}
