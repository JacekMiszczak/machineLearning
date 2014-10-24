package com.github.ML.data.attribute

import com.github.ML.data.descriptor.Descriptor
import com.github.ML.data.descriptor.DiscreteDescriptor

/**
 * Created by Krecik on 24-10-2014.
 */
class DiscreteAttribute extends AbstractAttribute {

//    Number value

    DiscreteDescriptor descriptor

    public DiscreteAttribute(Number value, Descriptor descriptor){
        this.value = value
        this.descriptor = descriptor
    }

    String getStringValue(){
        descriptor.toStringValue(value)
    }

}
