package com.inari.commons.lang.indexed;

public class TestIndexedObject extends BaseIndexedObject {

    public TestIndexedObject() {
        super();
    }

    @Override
    public Class<TestIndexedObject> indexedObjectType() {
        return TestIndexedObject.class;
    }

}
