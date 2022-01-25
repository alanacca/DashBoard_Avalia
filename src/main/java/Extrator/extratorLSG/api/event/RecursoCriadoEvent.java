package Extrator.extratorLSG.api.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import javax.servlet.http.HttpServletResponse;

public class RecursoCriadoEvent extends ApplicationEvent {

    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    private HttpServletResponse response;

    @Getter
    @Setter
    private Integer id;

    public RecursoCriadoEvent(Object source, HttpServletResponse response, Integer id) {
        super(source);
        this.response = response;
        this.id = id;
    }

}
