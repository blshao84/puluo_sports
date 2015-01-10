package com.unuotech.util

import scala.util.Random

object MathUtil {
  def exclusiveRandomInt(left:Int,right:Int):Int = {
    val x = Random.nextInt(right)
    if(x>left && x < right) x else exclusiveRandomInt(left,right)
  }
}