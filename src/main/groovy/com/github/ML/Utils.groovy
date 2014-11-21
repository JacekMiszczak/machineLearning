package com.github.ML

import com.github.ML.classifier.ila.Rule
import com.github.ML.classifier.ila.RuleSet
import com.github.ML.data.DataSet
import com.github.ML.data.descriptor.Descriptor
import com.github.ML.data.loader.ARFFLoader
import com.github.ML.data.loader.Loader
import com.github.ML.evaluation.Evaluation


class Utils {

    static DataSet getIris(){
        File iris = new File(Utils.classLoader.getResource("iris.arff").toURI())
        Loader loader = new ARFFLoader()
        loader.load(iris)
    }

    static DataSet getGlass(){
        File glass = new File(Utils.classLoader.getResource("glass.arff").toURI())
        Loader loader = new ARFFLoader()
        loader.load(glass)
    }

    static DataSet getGlassClean(){
        File glass = new File(Utils.classLoader.getResource("glass.clean.arff").toURI())
        Loader loader = new ARFFLoader()
        loader.load(glass)
    }

    static DataSet getWine(){
        File wine = new File(Utils.classLoader.getResource("wine.arff").toURI())
        Loader loader = new ARFFLoader()
        DataSet ds = loader.load(wine)
        ds.classAttributeIndex = 0
        return ds
    }

    static DataSet getWeatherNumeric(){
        File weather = new File(Utils.classLoader.getResource("weather.numeric.arff").toURI())
        Loader loader = new ARFFLoader()
        DataSet ds = loader.load(weather)
        return ds
    }

    static DataSet getWeatherNominal(){
        File weather = new File(Utils.classLoader.getResource("weather.nominal.arff").toURI())
        Loader loader = new ARFFLoader()
        DataSet ds = loader.load(weather)
        return ds
    }

    static DataSet getDiabetes(){
        File diabetes = new File(Utils.classLoader.getResource("diabetes.arff").toURI())
        Loader loader = new ARFFLoader()
        DataSet ds = loader.load(diabetes)
        return ds
    }

    static void printHeader(List<String> parameters, List<String> extras = []){
        writeHeader(parameters, extras, System.out.newWriter())
    }

    static void writeHeader(List<String> parameters, List<String> extras = [], Writer writer){
        StringBuilder sb = new StringBuilder()
        (parameters + ["recall", "accuracy", "precision", "F-measure"] + extras).each {
            sb.append("$it;")
        }
        sb.append(System.lineSeparator())
        writer.write(sb.toString())
        writer.flush()
    }

    static void printLine(List<String> parameters, Evaluation evaluation, List<String> extras = []){
        writeLine(parameters, evaluation, extras, System.out.newWriter())
    }

    static void writeLine(List<String> parameters, Evaluation eval, List<String> extras = [], Writer writer){
        StringBuilder sb = new StringBuilder()
        (parameters + [eval.weightedRecall, eval.weightedAccuracy, eval.weightedPrecision, eval.weightedFMeasure] + extras).each {
            sb.append("$it;")
        }
        sb.append(System.lineSeparator())
        writer.write(sb.toString())
        writer.flush()
    }

    static String prettyRule(Rule rule, DataSet dataSet){
        StringBuilder sb = new StringBuilder()
        Descriptor classDesc = dataSet.classDescriptor
        if (rule.conditions.isEmpty()){
            sb.append("Otherwise ${classDesc.name} = ${classDesc.toStringValue(rule.conclusion)}")
            return sb.toString()
        }
        sb.append("If")
        rule.conditions.each {int k, int v ->
            Descriptor desc = dataSet.descriptors[k]
            sb.append(" ${desc.name} = ${desc.toStringValue(v)} and")
        }
        sb.delete(sb.size() - 4, sb.size())
        sb.append(" then ${classDesc.name} = ${classDesc.toStringValue(rule.conclusion)}")
        sb.toString()
    }

    static String prettyPrintRuleSet(RuleSet ruleSet, DataSet dataSet){
        ruleSet.rules.each {
            println prettyRule(it, dataSet)
        }
    }

}
