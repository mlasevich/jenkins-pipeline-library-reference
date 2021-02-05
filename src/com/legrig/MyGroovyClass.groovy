package com.legrig

import com.cloudbees.groovy.cps.NonCPS

class MyGroovyClass {

  /**
   * Non CPS version of multiply example
   */
  @NonCPS
  int nonCPSMultiply(int x, int y) {
    return x * y
  }

  /**
   * CPS Transformed version
   */
  int multiply(int x, int y) {
    return x * y
  }
}
