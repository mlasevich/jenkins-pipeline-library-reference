package com.legrig

import com.cloudbees.groovy.cps.impl.CpsCallableInvocation
import support.jenkins.CPSSpecification
import spock.lang.Unroll

class MyGroovyClassSpec extends CPSSpecification {

  Class testSubjectClass = MyGroovyClass

  @Unroll
  def "MyGroovyClass.nonCPSMultiply(#x, #y) == #expected Test"() {

    expect: 'nonCPSMultiply to work without CPS'
      sut.nonCPSMultiply(x, y) == expected

    where: 'data is'
      x | y || expected
      1 | 1 || 1
      1 | 0 || 0
      2 | 3 || 6
  }

  def "MyGroovyClass.multiply() will not run without CPS Test"() {
    when: 'Data to be correct'
      sut.multiply(1, 2)

    then: 'CpsCallableInvocation Exception is thrown'
      thrown(CpsCallableInvocation)
  }

  @Unroll
  def "MyGroovyClass.multiply(#x, #y)== #expected under CPS Test"() {

    when: 'we invoke multiply() via CPS'
      def result = invokeCPSMethod(sut, 'multiply', x, y)

    then: 'Exception is not thrown'
      notThrown(CpsCallableInvocation)

    and: 'result is correct'
      result == expected

    where: 'data is'
      x | y || expected
      1 | 1 || 1
      1 | 0 || 0
      2 | 3 || 6
  }
}
