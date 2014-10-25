package com.github.ML.data

import com.github.ML.data.descriptor.Descriptor


class Attribute {

    Number value

    @Delegate
    Descriptor descriptor

    public Attribute(Number value, Descriptor descriptor){
        this.value = value
        this.descriptor = descriptor
    }

    String getStringValue(){
        descriptor.toStringValue(value)
    }

}
