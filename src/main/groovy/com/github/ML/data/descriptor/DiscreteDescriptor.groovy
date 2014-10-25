package com.github.ML.data.descriptor

import com.github.ML.data.AttributeType
import com.github.ML.data.Attribute
import groovy.transform.CompileStatic

@CompileStatic
class DiscreteDescriptor extends AbstractDescriptor {

    List<String> possibleValues

    @Override
    Attribute buildAttribute(Number value) {
        return new Attribute(value, this)
    }

    @Override
    AttributeType getAttributeType() {
        AttributeType.DISCRETE
    }

    String toStringValue(Number value){
        possibleValues[value.intValue()]
    }

    Number toNumberValue(String value){
        new Integer(possibleValues.findIndexOf {it == value})
    }
}
