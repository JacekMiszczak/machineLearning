package com.github.ML

import com.github.ML.classifier.Classifier
import com.github.ML.classifier.ILA
import com.github.ML.classifier.Rule
import com.github.ML.classifier.RuleSet
import com.github.ML.data.AttributeType
import com.github.ML.data.DataSet
import com.github.ML.data.Instance
import com.github.ML.data.descriptor.DiscreteDescriptor
import com.github.ML.data.transformation.Discretizer
import com.github.ML.data.transformation.KBinsDiscretizer
import com.github.ML.data.transformation.KRangesDiscretizer
import com.github.ML.data.transformation.MDLDiscretizer
import com.github.ML.evaluation.CrossValidation
import com.github.ML.evaluation.Evaluation
import com.github.ML.evaluation.EvaluationUtils

DataSet ds = Utils.wine

//Discretizer discretizer = new MDLDiscretizer(true, 3)
//Discretizer discretizer = new KRangesDiscretizer(15)
Discretizer discretizer = new KBinsDiscretizer(2)


//println ([[1],[2],[3],[4]].combinations())
//permutations = ([[true, false]]*12).combinations()
//println permutations
//permutations = permutations.grep({List<Number> it ->
//    it.count(true) == 2
//})
//println permutations

ds = discretizer.discretizeAll(ds)

//ds.instances.each {Instance first ->
//    ds.instances.each {Instance second ->
//        if (first.attributes.subList(0, first.attributes.size()-1) == second.attributes.subList(0, first.attributes.size()-1))
//            if (first.attributes[-1] != second.attributes[-1])
//                println "$first, $second"
//        }
//}
//
ILA ila = new ILA()

//Evaluation eval = CrossValidation.evaluate(ila, ds, 5)

Classifier ruleset = ila.buildClassifier(ds)
//Utils.prettyPrintRuleSet(ruleset as RuleSet, ds)
//Evaluation eval = EvaluationUtils.evaluate(ds, ruleset)


//println eval.instancesCount
//eval.results.each {
//    println it
//}
//
//Utils.printHeader([], [])
//Utils.printLine([], eval, [])













//int idx = 0
//
////Discretizer discretizer = new KBinsDiscretizer(5)
////Discretizer discretizer = new MDLDiscretizer(true, 3)
//Discretizer discretizer = new KRangesDiscretizer(4)
//
////DataSet discrete = discretizer.discretizeAttribute(idx, ds)
////println((discrete.descriptors[idx] as DiscreteDescriptor).possibleValues)
////println ds[0].attributes
////println discrete[0].attributes
//
//DataSet discrete = discretizer.discretizeAll(ds)
//discrete.descriptors.each {
//    println ((it as DiscreteDescriptor).possibleValues)
//}
//println discrete[0].attributes
//
//println (([1,2,3]).combinations())
//
////ds.descriptors.each{
////    println it.name
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