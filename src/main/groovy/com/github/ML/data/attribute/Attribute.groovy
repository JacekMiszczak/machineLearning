package com.github.ML.data.attribute

import com.github.ML.data.AttributeType
import com.github.ML.data.descriptor.Descriptor

/**
 * Created by Krecik on 24-10-2014.
 */
public interface Attribute {

    public Descriptor getDescriptor()

    public boolean isClassAttribute()

    public String getName()

    public AttributeType getAttributeType()

    public Number getValue()


}