package com.github.ML.data.descriptor

import com.github.ML.data.AttributeType
import com.github.ML.data.attribute.Attribute
import com.github.ML.data.attribute.NumericalAttribute
import groovy.transform.CompileStatic

@CompileStatic
class NumericalDescriptor extends AbstractDescriptor {

    @Override
    Attribute buildAttribute(Object value) {
        return new NumericalAttribute((value as Number), this)
    }

    @Override
    AttributeType getAttributeType() {
        AttributeType.NUMERICAL
    }
}
