package iam.lfc.myretrofitcache.share.eventmessage

import java.util.*

class MessageEvent(var name: String = "", value01: String = "", value02: String = "", value03: String = "", value04: String = "", value05: String = ""
                   , value06: String = "", var value07: Int = 0, var typeValue: Int = 0, var list_Data: List<*> = ArrayList<Objects>(), var d1: Double = 0.0
                   , var d2: Double = 0.0, var dataObject: Any? = null) {

    var password01 = value01
    var password02 = value02
    var password03 = value03
    var password04 = value04
    var password05 = value05
    var password06 = value06
    var password07 = value07
    var type = typeValue
    var mlist: List<*> = list_Data
    var d_value1 = d1
    var d_value2 = d2
    var data: Any? = dataObject


}
