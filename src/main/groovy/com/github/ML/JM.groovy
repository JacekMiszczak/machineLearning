package com.github.ML

import com.github.ML.classifier.bayes.NaiveBayes
import com.github.ML.data.DataSet


//println ([[1]* 4  ]*3)

//
DataSet ds = Utils.glass
bayes = new NaiveBayes()
model = bayes.buildClassifier(ds)
int s = 0
ds.each {
    if (model.classify(it)!=it.classAttributeValue) {
        s++
    }
}
println s


//Discretizer discretizer = new MDLDiscretizer(true, 3)
//Discretizer discretizer = new KRangesDiscretizer(15)
//Discretizer discretizer = new KBinsDiscretizer(2)


//println ([[1],[2],[3],[4]].combinations())
//permutations = ([[true, false]]*12).combinations()
//println permutations
//permutations = permutations.grep({List<Number> it ->
//    it.count(true) == 2
//})
//println permutations

//ds = discretizer.discretizeAll(ds)

//ds.instances.each {Instance first ->
//    ds.instances.each {Instance second ->
//        if (first.attributes.subList(0, first.attributes.size()-1) == second.attributes.subList(0, first.attributes.size()-1))
//            if (first.attributes[-1] != second.attributes[-1])
//                println "$first, $second"
//        }
//}
//
//ILA ila = new ILA()

//Evaluation eval = CrossValidation.evaluate(ila, ds, 5)

//Classifier ruleset = ila.buildClassifier(ds)
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