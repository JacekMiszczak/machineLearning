package com.github.ML.classifier.ila

import com.github.ML.data.Instance
import groovy.transform.Canonical

@Canonical
class Rule {

    Map<Integer, Integer> conditions = [:]
    int conclusion = -1

    public boolean instancePasses(Instance instance){
        boolean res = true
        conditions.each { int k, int v ->
            if (instance.attributes[k] != v){
                res = false
                return
            }
        }
        return res
    }

    public int apply(Instance instance){
        instancePasses(instance) ? conclusion : null
    }

    public String toString(){
        StringBuilder sb = new StringBuilder()
        if (conditions.isEmpty())
            return "Otherwise $conclusion"
        sb.append("if")
        conditions.each {int k, int v ->
            sb.append(" $k = $v and")
        }
        sb.delete(sb.size() - 4, sb.size())
        sb.append(" then $conclusion")
        sb.toString()
    }

}
