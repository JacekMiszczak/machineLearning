package com.github.ML.data.descriptor

import com.github.ML.data.AttributeType
import com.github.ML.data.attribute.Attribute
import com.github.ML.data.attribute.DiscreteAttribute
import groovy.transform.CompileStatic

@CompileStatic
class DiscreteDescriptor extends AbstractDescriptor {

    List<String> possibleValues

    @Override
    Attribute buildAttribute(Object value) {
        return new DiscreteAttribute((value as Number), this)
    }

    @Override
    AttributeType getAttributeType() {
        AttributeType.DISCRETE
    }

    String toStringValue(int value){
        possibleValues[value]
    }

    int toIntValue(String value){
        possibleValues.findIndexOf {it == value}
    }
}
