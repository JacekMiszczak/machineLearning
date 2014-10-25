package com.github.ML.tester


class Evaluation {

    List<Map<String, Integer>> results = [[:]]

    public Evaluation(int numClasses){
        numClasses.times {
            results[it] = [tp: 0, tn: 0, fp: 0, fn: 0]
        }
    }

    public void addResult(int detectedClass, int realClass){
        if (detectedClass == realClass){
            results.eachWithIndex { Map<String, Integer> entry, int i ->
                if (i == realClass)
                    entry.tp++
                else
                    entry.tn++
            }
        } else {
            results.eachWithIndex { Map<String, Integer> entry, int i ->
                if (i == realClass)
                    entry.fn++
                else if (i == detectedClass)
                    entry.fp++
                else
                    entry.tn++
            }
        }
    }

    public int getCountOfClass(int i){
        results[i].tp + results[i].fn
    }

    public int getInstancesCount(){
        int out = 0
        results[0].with {
            out += tp + tn + fp + fn
        }
        out
    }

    public float getTPRate(int i){
        (results[i].tp / getCountOfClass(i)) as float
    }

    public float getRecall(int i){
        getTPRate(i)
    }

    public float getFPRate(int i){
        (results[i].fp / (results[i].fp + results[i].tn)) as float
    }

    public float getPrecision(int i){
        (results[i].tp / (results[i].tp + results[i].fp)) as float
    }

    public float getFMeasure(int i){
        ((2 * getPrecision(i) * getRecall(i)) / (getPrecision(i) + getRecall(i))) as float
    }

    public float getAccuracy(int i){
        ((results[i].tp + results[i].tn) / getInstancesCount()) as float
    }

    public float getWeightedTPRate(){
        float temp = 0
        results.size().times {
            temp += getTPRate(it)*getCountOfClass(it)
        }
        temp / getInstancesCount()
    }

    public float getWeightedRecall(){
        getWeightedTPRate()
    }

    public float getWeightedFPRate(){
        float temp = 0
        results.size().times {
            temp += getFPRate(it)*getCountOfClass(it)
        }
        temp / getInstancesCount()
    }

    public float getWeightedPrecision(){
        float temp = 0
        results.size().times {
            temp += getPrecision(it)*getCountOfClass(it)
        }
        temp / getInstancesCount()
    }

    public float getWeightedFMeasure(){
        float temp = 0
        results.size().times {
            temp += getFMeasure(it)*getCountOfClass(it)
        }
        temp / getInstancesCount()
    }

    public float getWeightedAccuracy(){
        float temp = 0
        results.size().times {
            temp += getAccuracy(it)*getCountOfClass(it)
        }
        temp / getInstancesCount()
    }

}
