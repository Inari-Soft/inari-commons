package com.inari.commons.lang.field;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.Test;

import com.inari.commons.geom.Position;
import com.inari.commons.geom.Rectangle;
import com.inari.commons.lang.field.Field.Node;

public class FieldTest {
    
    @Test
    public void testCreationEMPTY() {
        Field<String> field = ( new TestFieldFactory() ).createField( 0, 0, false );
        
        assertNotNull( field );
        assertTrue( field.isEmpty() ); 
        assertFalse( field.isSpherical() );
        assertEquals( 
            "Field( width:0 height:0 spherical:false )\n", 
            field.toString() 
        );

        Iterator<Node<String>> iterator = field.nodeIterator();
        assertNotNull( iterator );
        assertFalse( iterator.hasNext() );
        
        Iterator<String> valueIterator = field.valueIterator();
        assertNotNull( valueIterator );
        assertFalse( valueIterator.hasNext() );
    }
    
    @Test
    public void testCreationONE() {
        Field<String> field = ( new TestFieldFactory() ).createField( 1, 1, false );
        
        assertNotNull( field );
        assertFalse( field.isEmpty() ); 
        assertFalse( field.isSpherical() );
        assertEquals( 
            "Field( width:1 height:1 spherical:false )\n" + 
            "  [ x:0 y:0 ]\n", 
            field.toString() 
        );
        
        Node<String> node = field.getNode( 0, 0 );
        assertNotNull( node );
        assertNull( node.north );
        assertNull( node.east );
        assertNull( node.south );
        assertNull( node.west );
        
        Iterator<Node<String>> iterator = field.nodeIterator();
        assertNotNull( iterator );
        assertTrue( iterator.hasNext() );
        assertEquals( node, iterator.next() );
        assertFalse( iterator.hasNext() );
        
        Iterator<String> valueIterator = field.valueIterator();
        assertNotNull( valueIterator );
        assertTrue( valueIterator.hasNext() );
        assertEquals( node.value(), valueIterator.next() );
        assertFalse( valueIterator.hasNext() );
    }
    
    @Test
    public void testCreationONE_SPHERICAL() {
        Field<String> field = ( new TestFieldFactory() ).createField( 1, 1, true );
        
        assertNotNull( field );
        assertFalse( field.isEmpty() ); 
        assertTrue( field.isSpherical() );
        assertEquals( 
            "Field( width:1 height:1 spherical:true )\n" + 
            "  [ x:0 y:0 ]\n", 
            field.toString() 
        );
        
        Node<String> node = field.getNode( 0, 0 );
        assertNotNull( node );
        assertEquals( node, node.north );
        assertEquals( node, node.east );
        assertEquals( node, node.south );
        assertEquals( node, node.west );
        
        Iterator<Node<String>> iterator = field.nodeIterator();
        assertNotNull( iterator );
        assertTrue( iterator.hasNext() );
        assertEquals( node, iterator.next() );
        assertFalse( iterator.hasNext() );
        
        Iterator<String> valueIterator = field.valueIterator();
        assertNotNull( valueIterator );
        assertTrue( valueIterator.hasNext() );
        assertEquals( node.value(), valueIterator.next() );
        assertFalse( valueIterator.hasNext() );
    }
    
    @Test
    public void testCreateFIELD() {
        Field<String> field = ( new TestFieldFactory() ).createField( 2, 2, false );
        
        assertNotNull( field );
        assertFalse( field.isEmpty() ); 
        assertFalse( field.isSpherical() );
        assertEquals( 
            "Field( width:2 height:2 spherical:false )\n" + 
            "  [ x:0 y:0 ]  [ x:1 y:0 ]\n" + 
            "  [ x:0 y:1 ]  [ x:1 y:1 ]\n", 
            field.toString() 
        );
        
        Node<String> node1 = field.getNode( 0, 0 );
        Node<String> node2 = field.getNode( 1, 0 );
        Node<String> node3 = field.getNode( 0, 1 );
        Node<String> node4 = field.getNode( 1, 1 );
        assertNotNull( node1 );
        assertNotNull( node2 );
        assertNotNull( node3 );
        assertNotNull( node4 );
        
        assertNull( node1.north );
        assertEquals( node2, node1.east );
        assertEquals( node3, node1.south );
        assertNull( node1.west );
        
        assertNull( node2.north );
        assertNull( node2.east );
        assertEquals( node4, node2.south );
        assertEquals( node1, node2.west );
        
        assertEquals( node1, node3.north );
    }
    
    @Test
    public void testSwapValue() {
        Field<String> field = ( new TestFieldFactory() ).createField( 2, 2, false );
        
        assertNotNull( field );
        assertFalse( field.isEmpty() ); 
        assertFalse( field.isSpherical() );
        assertEquals( 
            "Field( width:2 height:2 spherical:false )\n" + 
            "  [ x:0 y:0 ]  [ x:1 y:0 ]\n" + 
            "  [ x:0 y:1 ]  [ x:1 y:1 ]\n", 
            field.toString() 
        );
        
        field.swapValue( new Position( 0, 0 ) , new Position( 1, 1 ) );
        assertEquals( 
            "Field( width:2 height:2 spherical:false )\n" + 
            "  [ x:1 y:1 ]  [ x:1 y:0 ]\n" + 
            "  [ x:0 y:1 ]  [ x:0 y:0 ]\n", 
            field.toString() 
        );
    }
    
    @Test
    public void testSimpleIteration() {
        Field<String> field = ( new TestFieldFactory() ).createField( 1, 1, true );
        IFieldIterator<Node<String>> nodeIterator = field.nodeIterator();
        Position pos = nodeIterator.getPosition();
        
        assertNotNull( nodeIterator );
        assertNotNull( pos );
        assertEquals( -1, pos.x );
        assertEquals( 0, pos.y );
        assertTrue( nodeIterator.hasNext() );
        
        Node<String> node = nodeIterator.next();
        pos = nodeIterator.getPosition();
        assertNotNull( node );
        assertEquals( "x:0 y:0", node.value );
        assertEquals( 0, pos.x );
        assertEquals( 0, pos.y );
        
        assertFalse( nodeIterator.hasNext() );
        

        
        field = ( new TestFieldFactory() ).createField( 2, 2, false );
        nodeIterator = field.nodeIterator();
        pos = nodeIterator.getPosition();
        
        assertNotNull( nodeIterator );
        assertNotNull( pos );
        assertEquals( -1, pos.x );
        assertEquals( 0, pos.y );
        
        assertTrue( nodeIterator.hasNext() );
        node = nodeIterator.next();
        pos = nodeIterator.getPosition();
        assertNotNull( node );
        assertEquals( "x:0 y:0", node.value );
        assertEquals( 0, pos.x );
        assertEquals( 0, pos.y );
        
        assertTrue( nodeIterator.hasNext() );
        node = nodeIterator.next();
        pos = nodeIterator.getPosition();
        assertNotNull( node );
        assertEquals( "x:1 y:0", node.value );
        assertEquals( 1, pos.x );
        assertEquals( 0, pos.y );
        
        assertTrue( nodeIterator.hasNext() );
        node = nodeIterator.next();
        pos = nodeIterator.getPosition();
        assertNotNull( node );
        assertEquals( "x:0 y:1", node.value );
        assertEquals( 0, pos.x );
        assertEquals( 1, pos.y );
        
        assertTrue( nodeIterator.hasNext() );
        node = nodeIterator.next();
        pos = nodeIterator.getPosition();
        assertNotNull( node );
        assertEquals( "x:1 y:1", node.value );
        assertEquals( 1, pos.x );
        assertEquals( 1, pos.y );
        
        assertFalse( nodeIterator.hasNext() );
    }
    
    @Test
    public void testSimpleValueIteration() {
        Field<String> field = ( new TestFieldFactory() ).createField( 1, 1, false );
        IFieldIterator<String> valueIterator = field.valueIterator(); 
        Position pos = valueIterator.getPosition();
        
        assertNotNull( valueIterator );
        assertNotNull( pos );
        assertEquals( -1, pos.x );
        assertEquals( 0, pos.y );
        assertTrue( valueIterator.hasNext() );
        
        String value = valueIterator.next();
        pos = valueIterator.getPosition();
        assertNotNull( value );
        assertEquals( "x:0 y:0", value );
        assertEquals( 0, pos.x );
        assertEquals( 0, pos.y );
        
        assertFalse( valueIterator.hasNext() );
        

        
        field = ( new TestFieldFactory() ).createField( 2, 2, true );
        valueIterator = field.valueIterator();
        pos = valueIterator.getPosition();
        
        assertNotNull( valueIterator );
        assertNotNull( pos );
        assertEquals( -1, pos.x );
        assertEquals( 0, pos.y );
        
        assertTrue( valueIterator.hasNext() );
        value = valueIterator.next();
        pos = valueIterator.getPosition();
        assertNotNull( value );
        assertEquals( "x:0 y:0", value );
        assertEquals( 0, pos.x );
        assertEquals( 0, pos.y );
        
        assertTrue( valueIterator.hasNext() );
        value = valueIterator.next();
        pos = valueIterator.getPosition();
        assertNotNull( value );
        assertEquals( "x:1 y:0", value );
        assertEquals( 1, pos.x );
        assertEquals( 0, pos.y );
        
        assertTrue( valueIterator.hasNext() );
        value = valueIterator.next();
        pos = valueIterator.getPosition();
        assertNotNull( value );
        assertEquals( "x:0 y:1", value );
        assertEquals( 0, pos.x );
        assertEquals( 1, pos.y );
        
        assertTrue( valueIterator.hasNext() );
        value = valueIterator.next();
        pos = valueIterator.getPosition();
        assertNotNull( value );
        assertEquals( "x:1 y:1", value );
        assertEquals( 1, pos.x );
        assertEquals( 1, pos.y );
        
        assertFalse( valueIterator.hasNext() );
    }
    
    @Test
    public void testClippedIteration1() {
        Field<String> field = ( new TestFieldFactory() ).createField( 10, 10, false );
        assertEquals( 
            "Field( width:10 height:10 spherical:false )\n" + 
            "  [ x:0 y:0 ]  [ x:1 y:0 ]  [ x:2 y:0 ]  [ x:3 y:0 ]  [ x:4 y:0 ]  [ x:5 y:0 ]  [ x:6 y:0 ]  [ x:7 y:0 ]  [ x:8 y:0 ]  [ x:9 y:0 ]\n" + 
            "  [ x:0 y:1 ]  [ x:1 y:1 ]  [ x:2 y:1 ]  [ x:3 y:1 ]  [ x:4 y:1 ]  [ x:5 y:1 ]  [ x:6 y:1 ]  [ x:7 y:1 ]  [ x:8 y:1 ]  [ x:9 y:1 ]\n" + 
            "  [ x:0 y:2 ]  [ x:1 y:2 ]  [ x:2 y:2 ]  [ x:3 y:2 ]  [ x:4 y:2 ]  [ x:5 y:2 ]  [ x:6 y:2 ]  [ x:7 y:2 ]  [ x:8 y:2 ]  [ x:9 y:2 ]\n" + 
            "  [ x:0 y:3 ]  [ x:1 y:3 ]  [ x:2 y:3 ]  [ x:3 y:3 ]  [ x:4 y:3 ]  [ x:5 y:3 ]  [ x:6 y:3 ]  [ x:7 y:3 ]  [ x:8 y:3 ]  [ x:9 y:3 ]\n" + 
            "  [ x:0 y:4 ]  [ x:1 y:4 ]  [ x:2 y:4 ]  [ x:3 y:4 ]  [ x:4 y:4 ]  [ x:5 y:4 ]  [ x:6 y:4 ]  [ x:7 y:4 ]  [ x:8 y:4 ]  [ x:9 y:4 ]\n" + 
            "  [ x:0 y:5 ]  [ x:1 y:5 ]  [ x:2 y:5 ]  [ x:3 y:5 ]  [ x:4 y:5 ]  [ x:5 y:5 ]  [ x:6 y:5 ]  [ x:7 y:5 ]  [ x:8 y:5 ]  [ x:9 y:5 ]\n" + 
            "  [ x:0 y:6 ]  [ x:1 y:6 ]  [ x:2 y:6 ]  [ x:3 y:6 ]  [ x:4 y:6 ]  [ x:5 y:6 ]  [ x:6 y:6 ]  [ x:7 y:6 ]  [ x:8 y:6 ]  [ x:9 y:6 ]\n" + 
            "  [ x:0 y:7 ]  [ x:1 y:7 ]  [ x:2 y:7 ]  [ x:3 y:7 ]  [ x:4 y:7 ]  [ x:5 y:7 ]  [ x:6 y:7 ]  [ x:7 y:7 ]  [ x:8 y:7 ]  [ x:9 y:7 ]\n" + 
            "  [ x:0 y:8 ]  [ x:1 y:8 ]  [ x:2 y:8 ]  [ x:3 y:8 ]  [ x:4 y:8 ]  [ x:5 y:8 ]  [ x:6 y:8 ]  [ x:7 y:8 ]  [ x:8 y:8 ]  [ x:9 y:8 ]\n" + 
            "  [ x:0 y:9 ]  [ x:1 y:9 ]  [ x:2 y:9 ]  [ x:3 y:9 ]  [ x:4 y:9 ]  [ x:5 y:9 ]  [ x:6 y:9 ]  [ x:7 y:9 ]  [ x:8 y:9 ]  [ x:9 y:9 ]\n", 
            field.toString() 
        );
        
        Rectangle clipRect = new Rectangle( 0, 0, 2, 2 );
        IFieldIterator<String> valueIterator = field.valueIterator( clipRect );
        Position pos = valueIterator.getPosition();
        
        assertNotNull( valueIterator );
        assertNotNull( pos );
        assertEquals( "Position: -1,0", String.valueOf( pos ) );
        
        assertTrue( valueIterator.hasNext() );
        String value = valueIterator.next();
        pos = valueIterator.getPosition();
        assertNotNull( value );
        assertEquals( "x:0 y:0", value );
        assertEquals( "Position: 0,0", String.valueOf( pos ) );
        
        assertTrue( valueIterator.hasNext() );
        value = valueIterator.next();
        pos = valueIterator.getPosition();
        assertNotNull( value );
        assertEquals( "x:1 y:0", value );
        assertEquals( "Position: 1,0", String.valueOf( pos ) );
        
        assertTrue( valueIterator.hasNext() );
        value = valueIterator.next();
        pos = valueIterator.getPosition();
        assertNotNull( value );
        assertEquals( "x:0 y:1", value );
        assertEquals( "Position: 0,1", String.valueOf( pos ) );
        
        assertTrue( valueIterator.hasNext() );
        value = valueIterator.next();
        pos = valueIterator.getPosition();
        assertNotNull( value );
        assertEquals( "x:1 y:1", value );
        assertEquals( "Position: 1,1", String.valueOf( pos ) );
        
        assertFalse( valueIterator.hasNext() );
    }
    
    @Test
    public void testClippedIteration2() {
        Field<String> field = ( new TestFieldFactory() ).createField( 10, 10, false );
        assertEquals( 
            "Field( width:10 height:10 spherical:false )\n" + 
            "  [ x:0 y:0 ]  [ x:1 y:0 ]  [ x:2 y:0 ]  [ x:3 y:0 ]  [ x:4 y:0 ]  [ x:5 y:0 ]  [ x:6 y:0 ]  [ x:7 y:0 ]  [ x:8 y:0 ]  [ x:9 y:0 ]\n" + 
            "  [ x:0 y:1 ]  [ x:1 y:1 ]  [ x:2 y:1 ]  [ x:3 y:1 ]  [ x:4 y:1 ]  [ x:5 y:1 ]  [ x:6 y:1 ]  [ x:7 y:1 ]  [ x:8 y:1 ]  [ x:9 y:1 ]\n" + 
            "  [ x:0 y:2 ]  [ x:1 y:2 ]  [ x:2 y:2 ]  [ x:3 y:2 ]  [ x:4 y:2 ]  [ x:5 y:2 ]  [ x:6 y:2 ]  [ x:7 y:2 ]  [ x:8 y:2 ]  [ x:9 y:2 ]\n" + 
            "  [ x:0 y:3 ]  [ x:1 y:3 ]  [ x:2 y:3 ]  [ x:3 y:3 ]  [ x:4 y:3 ]  [ x:5 y:3 ]  [ x:6 y:3 ]  [ x:7 y:3 ]  [ x:8 y:3 ]  [ x:9 y:3 ]\n" + 
            "  [ x:0 y:4 ]  [ x:1 y:4 ]  [ x:2 y:4 ]  [ x:3 y:4 ]  [ x:4 y:4 ]  [ x:5 y:4 ]  [ x:6 y:4 ]  [ x:7 y:4 ]  [ x:8 y:4 ]  [ x:9 y:4 ]\n" + 
            "  [ x:0 y:5 ]  [ x:1 y:5 ]  [ x:2 y:5 ]  [ x:3 y:5 ]  [ x:4 y:5 ]  [ x:5 y:5 ]  [ x:6 y:5 ]  [ x:7 y:5 ]  [ x:8 y:5 ]  [ x:9 y:5 ]\n" + 
            "  [ x:0 y:6 ]  [ x:1 y:6 ]  [ x:2 y:6 ]  [ x:3 y:6 ]  [ x:4 y:6 ]  [ x:5 y:6 ]  [ x:6 y:6 ]  [ x:7 y:6 ]  [ x:8 y:6 ]  [ x:9 y:6 ]\n" + 
            "  [ x:0 y:7 ]  [ x:1 y:7 ]  [ x:2 y:7 ]  [ x:3 y:7 ]  [ x:4 y:7 ]  [ x:5 y:7 ]  [ x:6 y:7 ]  [ x:7 y:7 ]  [ x:8 y:7 ]  [ x:9 y:7 ]\n" + 
            "  [ x:0 y:8 ]  [ x:1 y:8 ]  [ x:2 y:8 ]  [ x:3 y:8 ]  [ x:4 y:8 ]  [ x:5 y:8 ]  [ x:6 y:8 ]  [ x:7 y:8 ]  [ x:8 y:8 ]  [ x:9 y:8 ]\n" + 
            "  [ x:0 y:9 ]  [ x:1 y:9 ]  [ x:2 y:9 ]  [ x:3 y:9 ]  [ x:4 y:9 ]  [ x:5 y:9 ]  [ x:6 y:9 ]  [ x:7 y:9 ]  [ x:8 y:9 ]  [ x:9 y:9 ]\n", 
            field.toString() 
        );
        
        Rectangle clipRect = new Rectangle( 5, 5, 2, 2 );
        IFieldIterator<String> valueIterator = field.valueIterator( clipRect );
        Position pos = valueIterator.getPosition();
        
        assertNotNull( valueIterator );
        assertNotNull( pos );
        assertEquals( "Position: 4,5", String.valueOf( pos ) );
        
        assertTrue( valueIterator.hasNext() );
        String value = valueIterator.next();
        pos = valueIterator.getPosition();
        assertNotNull( value );
        assertEquals( "x:5 y:5", value );
        assertEquals( "Position: 5,5", String.valueOf( pos ) );
        
        assertTrue( valueIterator.hasNext() );
        value = valueIterator.next();
        pos = valueIterator.getPosition();
        assertNotNull( value );
        assertEquals( "x:6 y:5", value );
        assertEquals( "Position: 6,5", String.valueOf( pos ) );
        
        assertTrue( valueIterator.hasNext() );
        value = valueIterator.next();
        pos = valueIterator.getPosition();
        assertNotNull( value );
        assertEquals( "x:5 y:6", value );
        assertEquals( "Position: 5,6", String.valueOf( pos ) );
        
        assertTrue( valueIterator.hasNext() );
        value = valueIterator.next();
        pos = valueIterator.getPosition();
        assertNotNull( value );
        assertEquals( "x:6 y:6", value );
        assertEquals( "Position: 6,6", String.valueOf( pos ) );
        
        assertFalse( valueIterator.hasNext() );
    }
    
    @Test
    public void testclippingWithToLargeClippingRectangle() {
        Field<String> field = ( new TestFieldFactory() ).createField( 5, 5, false );
        Rectangle clipRect = new Rectangle( 0, 0, 10, 10 );
        IFieldIterator<String> valueIterator = field.valueIterator( clipRect );
        
        for ( int y = 0; y < 5; y++ ) {
            for ( int x = 0; x < 5; x++ ) {
                String next = valueIterator.next();
                assertEquals( "x:" + x + " y:" + y, next );
            }
        }
        
        clipRect = new Rectangle( -10, -10, 10, 10 );
        valueIterator = field.valueIterator( clipRect );
        
        for ( int y = 0; y < 5; y++ ) {
            for ( int x = 0; x < 5; x++ ) {
                String next = valueIterator.next();
                assertEquals( "x:" + x + " y:" + y, next );
            }
        }
    }
    
    
    
    
    
    
    private static final class TestFieldFactory extends BaseFieldFactory<String> {

        @Override
        public Node<String> createNode( int x, int y ) {
            Node<String> node = new Node<String>();
            node.value = "x:" + x + " y:" + y;
            return node;
        }
        
        
    }

}
