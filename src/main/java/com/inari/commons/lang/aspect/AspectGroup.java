package com.inari.commons.lang.aspect;

import java.util.HashSet;
import java.util.Set;

import com.inari.commons.lang.list.DynArray;

public final class AspectGroup {
    
    private static final Set<String> NAMES = new HashSet<String>();
    
    private final String name;
    private final DynArray<Aspect> aspects = DynArray.create( Aspect.class, 10, 10 );
    private final int hashCode;

    public AspectGroup( String name ) {
        if ( NAMES.contains( name ) ) {
            throw new IllegalArgumentException();
        }
        this.name = name;
        NAMES.add( name );
        
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( name == null ) ? 0 : name.hashCode() );
        hashCode = result;
    }
    
    public final Aspects createAspects() {
        return new Aspects( this, aspects.size() );
    }
    
    public final Aspects createAspects( Aspect... aspects ) {
        Aspects result = new Aspects( this, this.aspects.size() );
        if ( aspects != null ) {
            for ( Aspect aspect : aspects ) {
                result.set( aspect );
            }
        }
        return result;
    }
    
    public final Aspect getAspect( int index ) {
        return aspects.get( index );
    }
    
    public final Aspect getAspect( String name ) {
        if ( name == null ) {
            return null;
        }
        
        for ( Aspect aspect : aspects ) {
            if ( name.equals( aspect.name() ) ) {
                return aspect;
            }
        }
        
        return null;
    }
    
    public final Aspect createAspect( String name ) {
        if ( getAspect( name ) != null ) {
            throw new IllegalArgumentException( "An Aspect with name: " + name + " already exists for this aspect group: " + this );
        }
        
        AspectImpl result = new AspectImpl( this, aspects.size(), name );
        aspects.add( result );
        return result;
    }
    
    public final Aspect createAspect( String name, int index ) {
        if ( aspects.contains( index ) ) {
            throw new IllegalArgumentException( "An Aspect with index: " + index + " already exists for this aspect group: " + this );
        }
        if ( getAspect( name ) != null ) {
            throw new IllegalArgumentException( "An Aspect with name: " + name + " already exists for this aspect group: " + this );
        }
        
        AspectImpl result = new AspectImpl( this, index, name );
        aspects.set( index, result );
        return result;
    }
    
    public void disposeAspect( int index ) {
        aspects.remove( index );
    }

    @Override
    public final int hashCode() {
        return hashCode;
    }

    @Override
    public final boolean equals( Object obj ) {
        if ( this == obj )
            return true;
        if ( obj == null )
            return false;
        if ( getClass() != obj.getClass() )
            return false;
        AspectGroup other = (AspectGroup) obj;
        if ( name == null ) {
            if ( other.name != null )
                return false;
        } else if ( !name.equals( other.name ) )
            return false;
        return true;
    }

    @Override
    public String toString() {
        return name;
    }
    
    @Override
    protected void finalize() throws Throwable {
        NAMES.remove( name );
        super.finalize();
    }
    
    private final class AspectImpl implements Aspect {
        
        private final AspectGroup group;
        private final int index;
        private final String name;

        public AspectImpl( AspectGroup group, int index, String name ) {
            super();
            this.group = group;
            this.index = index;
            this.name = name;
        }

        @Override
        public final AspectGroup aspectGroup() {
            return group;
        }

        @Override
        public final int index() {
            return index;
        }

        @Override
        public final String name() {
            return name;
        }
        
        
    }

}
