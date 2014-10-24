package com.github.ML.data.loader

import com.github.ML.data.AttributeType
import com.github.ML.data.DataSet
import com.github.ML.data.Instance
import com.github.ML.data.descriptor.Descriptor
import com.github.ML.data.descriptor.DiscreteDescriptor
import com.github.ML.data.descriptor.NumericalDescriptor


class ARFFLoader implements Loader {


    @Override
    DataSet load(File file) {
        DataSet out = new DataSet()
        Iterator<String> iterator = file.readLines().iterator()
        readRelation(iterator, out)
        readAttributes(iterator, out)
        readData(iterator, out)
        out.classAttributeIndex = -1
        return out
    }

    void readRelation(Iterator<String> it, DataSet dataSet){
        String temp
        while (it.hasNext()){
            temp = it.next()
            if (temp.startsWith("%"))
                continue
            if (temp.toLowerCase().startsWith("@relation")){
                List<String> split = temp.split()
                dataSet.name = split[1]
                return
            }
        }
    }

    void readAttributes(Iterator<String> it, DataSet dataSet){
        String temp
        while (it.hasNext()){
            temp = it.next()
            if (temp.startsWith("%"))
                continue
            if (temp.toLowerCase().startsWith("@attribute")){
                dataSet.descriptors.add(parseAttribute(temp, dataSet))
            } else if (temp.toLowerCase() == "@data")
                return
        }
    }

    void readData(Iterator<String> it, DataSet dataSet){
        String temp
        while (it.hasNext()){
            temp = it.next()
            if (temp.startsWith("%"))
                continue
            dataSet.instances.add(parseInstance(temp, dataSet))
        }
    }

    Descriptor parseAttribute(String line, DataSet dataSet){
        Descriptor out
        List<String> split = line.split()
        if (split[2].toLowerCase() == "real" || split[2].toLowerCase() == "numeric")
            out = new NumericalDescriptor()
         else {
            out = new DiscreteDescriptor()
            int openBracket = line.findIndexOf {it == "{"}
            int closeBracket = line.findIndexOf {it == "}"}
            out.possibleValues = parsePossibleValues(line.substring(openBracket+1, closeBracket))
        }
        out.name = split[1]
        out.dataSet = dataSet
        out
    }

    List<String> parsePossibleValues(String possibleValues){
        possibleValues.split(",").collect {it.trim()}
    }

    Instance parseInstance(String line, DataSet dataSet){
        List<String> split = line.split(",")
        Instance out = new Instance()
        out.dataSet = dataSet
        split.eachWithIndex{ String entry, int i ->
            Descriptor descriptor = dataSet.descriptors[i]
            if (descriptor.attributeType == AttributeType.NUMERICAL)
                out.attributes.add(entry)
            else if (descriptor.attributeType == AttributeType.DISCRETE)
                out.attributes.add((descriptor as DiscreteDescriptor).toIntValue(entry))
        }
        return out
    }
}
