package ar.edu.unq.grupoh.criptop2p.configuration;

import ar.edu.unq.grupoh.criptop2p.model.Cryptocurrency;
import lombok.extern.slf4j.Slf4j;
import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
public class CacheListener implements CacheEventListener<Long, List<Cryptocurrency>> {

    //Logger logger = LoggerFactory.getLogger(CacheListener.class);

    @Override
    public void onEvent(CacheEvent<? extends Long, ? extends List<Cryptocurrency>> event) {
        log.info("Event '{}' fired for key '{}' with value {}", event.getType(), event.getKey(), event.getNewValue());
    }
}
