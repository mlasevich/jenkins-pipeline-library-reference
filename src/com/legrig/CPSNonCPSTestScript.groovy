package com.legrig

import com.cloudbees.groovy.cps.NonCPS

  @NonCPS
  boolean nonCpsMethodCallingCpsMethod() {
    boolean ret = false
    println "this is a non cps method"
    ret = cpsMethod()
    return ret
  }

  /**
   * This will always throw an exception
   *
   * @return
   */
  boolean cpsMethodCallingNonCpsCallingCps() {
    boolean ret = false
    println "this is a cps method calling non cps method"
    nonCpsMethodCallingCpsMethod()
    println "after:this is a cps method calling non cps method"
    return ret
  }

  /**
   * Simple CPS Method
   * @return
   */
  boolean cpsMethod() {
    println "this is a cps method"
    return true
  }

  boolean cpsMethodCallingNonCps() {
    println "this is a cps method"
    return nonCpsMethod()
  }

  @NonCPS
  boolean nonCpsMethod() {
    println "this is a non cps method"
    return true
  }

/**
 * Needed for println to work
 * @return
 */
@NonCPS
def getOut(){
  return System.out
}
