package com.github.ML.data.descriptor

import com.github.ML.data.DataSet
import groovy.transform.CompileStatic

@CompileStatic
abstract class AbstractDescriptor implements Descriptor {

    String name
    DataSet dataSet
    boolean classAttribute

}
