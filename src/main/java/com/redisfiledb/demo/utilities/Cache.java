package com.redisfiledb.demo.utilities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public final class Cache {
    private HashMap<Object, Object> cachedObjects;

    public void addCachableItem(Object key, Object value) {
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
