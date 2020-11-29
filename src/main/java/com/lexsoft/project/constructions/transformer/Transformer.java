package com.lexsoft.project.constructions.transformer;

import java.util.List;

public interface Transformer<K,V> {

    public V transform (K k);
    public List<V> transformBatch(List<K> list);

    public K transformBackwards (V v);
    public List<K> transformBackwardsBatch (List<V> v);

}
