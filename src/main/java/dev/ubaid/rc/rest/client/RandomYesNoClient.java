package dev.ubaid.rc.rest.client;

import org.springframework.web.service.annotation.GetExchange;

public interface RandomYesNoClient {
    
    @GetExchange("/api")
    YesOrNo getYesOrNo();
}
