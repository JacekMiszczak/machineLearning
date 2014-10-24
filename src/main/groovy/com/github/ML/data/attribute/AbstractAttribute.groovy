package com.github.ML.data.attribute

import com.github.ML.data.AttributeType


abstract class AbstractAttribute implements Attribute {

    Number value

    boolean isClassAttribute(){
        descriptor.classAttribute
    }

    String getName(){
        descriptor.name
    }

    AttributeType getAttributeType(){
        descriptor.attributeType
    }

}
