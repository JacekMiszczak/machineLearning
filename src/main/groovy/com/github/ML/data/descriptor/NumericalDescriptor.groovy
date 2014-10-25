package com.github.ML.data.descriptor

import com.github.ML.data.AttributeType
import com.github.ML.data.Attribute
import groovy.transform.CompileStatic

@CompileStatic
class NumericalDescriptor extends AbstractDescriptor {

    @Override
    Attribute buildAttribute(Number value) {
        return new Attribute(value, this)
    }

    @Override
    AttributeType getAttributeType() {
        AttributeType.NUMERICAL
    }

    String toStringValue(Number value){
        "$value"                //todo: pretty print of floats
    }

    Number toNumberValue(String value){
        new Float(value)
    }
}
