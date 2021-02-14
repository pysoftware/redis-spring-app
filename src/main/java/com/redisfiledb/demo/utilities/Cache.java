package com.redisfiledb.demo.utilities;

import com.redisfiledb.demo.controllers.ExceptionHandlerController;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public final class Cache {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerController.class);

    private Map<Object, Object> cachedObjects = new HashMap<>();

    public void addCachableItem(Object key, Object value) {
        logger.info("Added new item to cache: " + key +  value);
        cachedObjects.put(key, value);
    }

    public boolean removeCachableItem(Object key) {
        if (cachedObjects.containsKey(key)) {
            return false;
        }
        cachedObjects.remove(key);
        return true;
    }

    public Object getCachedItemByValue(Object object) {
        return cachedObjects.getOrDefault(object, null);
    }
}
