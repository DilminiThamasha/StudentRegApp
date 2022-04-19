package com.crude.crudestudentregistration

import java.util.*

class StudentModel(
    var id:Int = getAutoId(),
    var name: String = "",
    var address:String=""
)
{
 companion object{
  fun  getAutoId():Int {
      val random = Random()
      return random.nextInt(100)
  }
  }

}