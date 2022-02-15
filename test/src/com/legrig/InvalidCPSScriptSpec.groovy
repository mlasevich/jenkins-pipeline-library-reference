package com.legrig

import support.jenkins.CPSSpecification

class InvalidCPSScriptSpec extends CPSSpecification {

  Class testSubjectClass = MyGroovyClass

  /**
   * This Test throwing of exception when
   */
  def "InvalidCPSScript Test"() {
    when:
      asCPSScript('''

import com.cloudbees.groovy.cps.NonCPS

@NonCPS
public void nonCpsMethodCallingCpsMethod(){
    println "this is a non cps method"
    cpsMethod()
}

public void cpsMethodCallingNonCps(){
    println "this is a cps method calling non cps method"
    nonCpsMethodCallingCpsMethod()
    println "after:this is a cps method calling non cps method"
}

public void cpsMethod(){
    println "this is a cps method"
}

@NonCPS
public nonCpsMethod(){
    println "this is a non cps method"
}
nonCpsMethodCallingCpsMethod()
        ''')
    then:
      thrown IllegalStateException
      //thrown CpsCallableInvocation
  }
}
