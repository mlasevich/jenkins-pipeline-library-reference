package com.legrig

import com.cloudbees.groovy.cps.NonCPS

class CPSNonCPSTestClass {

  @NonCPS
  boolean nonCpsMethodCallingCpsMethod() {
    println 'this is a non cps method'
    cpsMethod(false)
    return true
  }

  @NonCPS
  boolean nonCpsMethodCallingTwoCpsMethods() {
    println 'this is a non cps method'
    cpsMethod(false)
    println 'this is still non cps method, but we will enver get here'
    return cpsMethod()
  }

  /**
   * This will always throw an exception
   *
   * @return
   */
  boolean cpsMethodCallingNonCpsCallingCps() {
    println 'this is a cps method calling non cps method'
    boolean ret = nonCpsMethodCallingCpsMethod()
    println 'after:this is a cps method calling non cps method'
    return ret
  }

  /**
   * Simple CPS Method
   *
   * @param value boolean value to return, default is true
   * @return boolean value
   */
  boolean cpsMethod(boolean value=true) {
    println 'this is a cps method'
    return value
  }

  boolean cpsMethodCallingNonCps() {
    println 'this is a cps method'
    return nonCpsMethod()
  }

  @NonCPS
  boolean nonCpsMethod() {
    println 'this is a non cps method'
    return true
  }
}
