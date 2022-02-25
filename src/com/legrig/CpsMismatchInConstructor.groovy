package com.legrig

import com.cloudbees.groovy.cps.NonCPS

/**
 * This class is used to demonstrate catching constructor
 * calling a CPS or NonCPS method
 */
class CpsMismatchInConstructor {

  /**
   * No Argument Constructor
   */
  CpsMismatchInConstructor() {
  }

  /**
   * Constructor with action
   * @param callCPSMethod
   */
  CpsMismatchInConstructor(boolean callCPSMethod) {
    if (callCPSMethod) {
      cpsMethod()
    } else {
      nonCpsMethod()
    }
  }

  /**
   * CPS Transformed Method
   * @return
   */
  def cpsMethod() {
    println 'this is  cps method'
  }

  /**
   * Non CPS Transformed Method
   *
   * @return
   */
  @NonCPS
  def nonCpsMethod() {
    println 'this is a a non cps method'
  }
}
