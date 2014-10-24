package com.github.ML.data.descriptor

import com.github.ML.data.AttributeType
import com.github.ML.data.DataSet
import com.github.ML.data.attribute.Attribute

public interface Descriptor {

    public Attribute buildAttribute(Object value)

    public void setDataSet(DataSet dataSet)

    public DataSet getDataSet()

    public void setName(String name)

    public String getName()

    AttributeType getAttributeType()

    void setClassAttribute(boolean classAttr)

    boolean getClassAttribute()


}