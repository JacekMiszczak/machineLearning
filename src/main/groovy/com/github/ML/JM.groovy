package com.github.ML

import com.github.ML.data.AttributeType
import com.github.ML.data.DataSet
import com.github.ML.data.descriptor.DiscreteDescriptor
import com.github.ML.data.transformation.Discretizer
import com.github.ML.data.transformation.KBinsDiscretizer

DataSet ds = Utils.iris

Discretizer discretizer = new KBinsDiscretizer(5)
DataSet discrete = discretizer.discretizeAttribute(0, ds)
println((discrete.descriptors[0] as DiscreteDescriptor).possibleValues)
println ds[0].attributes
println discrete[0].attributes

//ds.descriptors.each{
//    println it.name
//    println it.attributeType
//    if (it.attributeType == AttributeType.DISCRETE){
//        println((it as DiscreteDescriptor).possibleValues)
//    }
//}
//
//println ds.name
//
//println ds.instances.size()
//println ds.instances[0].classAttribute.value
//println ds.instances[0].classAttribute.name
//println ds.instances[0].classAttribute.stringValue
//println ds.classDistribution
//
//println("\n\n")
//
//ds = Utils.weatherNominal
//
//println ds.name
//println ds.instances.size()
//println ds.instances[0].classAttribute.value
//println ds.instances[0].classAttribute.name
//println ds.instances[0].classAttribute.stringValue
//println ds.classDistribution
//
//println("\n\n")
//
//ds = Utils.weatherNumeric
//
//println ds.name
//println ds.instances.size()
//println ds.instances[0].classAttribute.value
//println ds.instances[0].classAttribute.name
//println ds.instances[0].classAttribute.stringValue
//println ds.classDistribution
//
//println("\n\n")
//
//ds = Utils.glass
//
//println ds.name
//
//
//println ds.instances.size()
//println ds.instances[0].classAttribute.value
//println ds.instances[0].classAttribute.name
//println ds.instances[0].classAttribute.stringValue
//println ds.classDistribution
//
//
//println("\n\n")
//
//ds = Utils.wine
//
//println ds.name
//
//println ds.instances.size()
//println ds.instances[0].classAttribute.value
//println ds.instances[0].classAttribute.name
//println ds.instances[0].classAttribute.stringValue
//println ds.classDistribution