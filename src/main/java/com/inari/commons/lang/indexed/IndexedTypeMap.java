package com.inari.commons.lang.indexed;

import java.lang.reflect.Array;
import java.util.Iterator;

import com.inari.commons.lang.aspect.AspectBuilder;

public final class IndexedTypeMap<V> extends AbstractIndexedBag implements Iterable<V> {
    
    private final Class<V> valueType;
    private V[] indexed;
    
    @SuppressWarnings( "unchecked" )
    public IndexedTypeMap( Class<? extends IndexedType> indexedType, Class<?> valueType ) {
        super( indexedType );
        this.valueType = (Class<V>) valueType;
    }
    
    @Override
    public int length() {
        return indexed.length;
    }

    public final V put( Class<? extends Indexed> type, V value ) {
        return put( Indexer.getIndexForType( type, indexedType ), value );
    }
    
    public final V put( int index, V value ) {
        ensureCapacity( index );
        V oldValue = indexed[ index ];
        
        indexed[ index ] = value;
        AspectBuilder.set( aspect, index );
        if ( oldValue == null ) {
            size++;
        }
        
        return oldValue;
    }
    
    public final V remove( Class<? extends Indexed> type ) {
        if ( type == null ) {
            return null;
        }
        return remove( Indexer.getIndexForType( type, indexedType ) );
    }
    
    public final V remove( Indexed indexed ) {
        if ( indexed == null ) {
            return null;
        }
        return remove( indexed.index() );
    }
    
    public final V remove( int index ) {
        V result = indexed[ index ];
        indexed[ index ] = null;
        AspectBuilder.reset( aspect, index );
        size--;
        return result;
    }
    
    public final boolean contains( int index ) {
        if ( indexed == null ) {
            return false;
        }
        
        if ( index >= indexed.length ) {
            return false;
        }
        
        return indexed[ index ] != null;
    }

    public final V getValue( Indexed indexed ) {
        Indexer.checkIndexedType( indexed.getClass(), indexedType );
        return getValue( indexed.index() );
    }
    
    public final V getValue( Class<? extends Indexed> type ) {
        return getValue( Indexer.getIndexForType( type, indexedType ) );
    }
    
    public final V getValueNoCheck( int index ) {
        return indexed[ index ];
    }
    
    public final V getValue( int index ) {
        if ( !contains( index ) ) {
            return null;
        }
        return indexed[ index ];
    }
    
    @Override
    public Iterator<V> iterator() {
        return new MapIterator();
    }
    
    @Override
    @SuppressWarnings( "unchecked" )
    public void clear() {
        super.clear();
        if ( indexed != null ) {
            indexed = (V[]) Array.newInstance( valueType, indexed.length );
        }
    }

    private void ensureCapacity( int index ) {
        if ( indexed != null && index < indexed.length ) {
            return;
        }
        
        @SuppressWarnings( "unchecked" )
        V[] newArray = (V[]) Array.newInstance( valueType, index + 1 );
        if ( indexed != null ) {
            System.arraycopy( indexed, 0, newArray, 0, indexed.length );
        }
        indexed = newArray;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append( "IndexedTypeMap [indexedType=" ).append( indexedType.getSimpleName() );
        builder.append( ", valueType=" ).append( valueType.getSimpleName() );
        builder.append( ", size=" ).append( size );
        builder.append( "\n  map: {" );
        if ( indexed != null ) {
            for ( int i = 0; i < indexed.length; i++ ) {
                if ( indexed[ i ] != null ) {
                    builder.append( "\n    " ).append( Indexer.getTypeForIndex( indexedType, i ).getSimpleName() ).append( ":" ).append( String.valueOf( indexed[ i ] ) );
                }
            }
        }
        builder.append( "\n  }" ).append( "\n]" );
        return builder.toString();
    }
    
    private final class MapIterator implements Iterator<V> {
        
        private int index = 0;

        @Override
        public boolean hasNext() {
            return index < indexed.length;
        }

        @Override
        public V next() {
            V result = indexed[ index ];
            findNext();
            return result;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
        
        private void findNext() {
            index++;
            while( indexed[ index ] == null && index < indexed.length ) {
                index++;
            }
        }
        
    }
}
