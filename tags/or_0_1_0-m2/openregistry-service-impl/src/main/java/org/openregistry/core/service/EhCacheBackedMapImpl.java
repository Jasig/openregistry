/**
 * Copyright (C) 2009 Jasig, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openregistry.core.service;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import java.io.Serializable;
import java.util.*;

/**
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public class EhCacheBackedMapImpl<K extends Serializable,V extends Serializable> implements Map<K,V> {

    private static final String DEFAULT_CACHE_NAME = "criteriaCache";

    private final Cache cache;

    public EhCacheBackedMapImpl(final Cache cache) {
        this.cache = cache;
    }

    public EhCacheBackedMapImpl() {
        final CacheManager cacheManager = CacheManager.getInstance();
        final Cache tempCache = cacheManager.getCache(DEFAULT_CACHE_NAME);

        if (tempCache == null) {
            this.cache = new Cache(DEFAULT_CACHE_NAME, 20000, false, false, 60000, 60000);
            cacheManager.addCache(this.cache);
        } else {
            this.cache = tempCache;
        }
    }

    public int size() {
        return this.cache.getSize();
    }

    public V put(final K key, final V value) {
        this.cache.put(new Element(key, value));
        return value;
    }

    public boolean containsKey(final Object key) {
        return get(key) != null;        
    }

    public Set<K> keySet() {
        return new HashSet<K>(this.cache.getKeys());
    }

    public boolean containsValue(final Object value) {
        throw new UnsupportedOperationException("This operation is not supported on an Ehcache-backed Map");
    }

    public void clear() {
        this.cache.removeAll();
    }

    public Collection<V> values() {
        final Set<K> keys = keySet();
        final Collection<V> values = new ArrayList<V>();

        for (final K key : keys) {
            final V value = get(key);
            if (value != null) {
                values.add(value);
            }
        }

        return values;
    }

    public void putAll(final Map<? extends K,? extends V> m) {
        for (final Map.Entry<? extends K, ? extends V> entry : m.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    public Set<Map.Entry<K,V>> entrySet() {
        final Set<K> keys = keySet();
        final Set<Entry<K, V>> entries = new HashSet<Entry<K,V>>();

        for (final K key : keys) {
            final Element element = this.cache.get(key);

            if (element != null) {
                entries.add(new ElementMapEntry<K,V>(element));
            }
        }

        return entries;

    }

    public V get(Object key) {
        final Element element = this.cache.get(key);

        return element == null ? null : (V) element.getValue();
    }

    public V remove(final Object key) {
        final V keyValue = get(key);
        this.cache.remove(key);
        return keyValue;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    protected final class ElementMapEntry<K,V> implements Map.Entry<K,V> {

           private final Element element;

              public ElementMapEntry(final Element element) {
                  this.element = element;
           }
           public K getKey() {
               return (K) element.getKey();
           }

           public V getValue() {
               return (V) element.getValue();
           }

           public V setValue(final V value) {
               throw new UnsupportedOperationException("Operation Not Supported");
           }
       }

}
