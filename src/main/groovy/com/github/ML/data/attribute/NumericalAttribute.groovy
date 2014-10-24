package com.github.ML.data.attribute

import com.github.ML.data.descriptor.Descriptor
import com.github.ML.data.descriptor.NumericalDescriptor

/**
 * Created by Krecik on 24-10-2014.
 */
class NumericalAttribute extends AbstractAttribute {

//    Number value

    NumericalDescriptor descriptor

    public NumericalAttribute(Number value, Descriptor descriptor){
        this.value = value
        this.descriptor = descriptor
    }

}
