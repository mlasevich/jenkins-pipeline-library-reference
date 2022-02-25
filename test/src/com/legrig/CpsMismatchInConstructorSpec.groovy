package com.legrig

import com.cloudbees.groovy.cps.impl.CpsCallableInvocation
import support.jenkins.CPSSpecification

class CpsMismatchInConstructorSpec extends CPSSpecification {

  Class testSubjectClass = CpsMismatchInConstructor

  def "CpsMismatch(false) CPS Test"() {

    when: 'we call constructor'
      def inst = asCPSScript '''
import com.legrig.CpsMismatchInConstructor

new CpsMismatchInConstructor(false)
'''
    then: 'no exception is thrown'
      noExceptionThrown()

    and: 'inst is set'
      inst instanceof CpsMismatchInConstructor
  }

  def "CpsMismatch(false) NonCPS Test"() {

    when: 'we call constructor'
      def inst = new CpsMismatchInConstructor(false)

    then: 'no exception is thrown'
      noExceptionThrown()

    and: 'inst is set'
      inst instanceof CpsMismatchInConstructor
  }

  def "CpsMismatch(true) CPS Test"() {

    when: 'we call constructor'
      def inst = asCPSScript '''
import com.legrig.CpsMismatchInConstructor

new CpsMismatchInConstructor(true)
'''

    then: 'IllegalStateException exception is thrown'
      thrown(IllegalStateException)

    and: 'inst is not set'
      inst == null
  }

  def "CpsMismatch(true) NonCPS Test"() {

    when: 'we call constructor'
      def inst = new CpsMismatchInConstructor(true)

    then: 'CpsCallableInvocation exception is thrown'
      thrown(CpsCallableInvocation)

    and: 'inst is not set'
      inst == null
  }

}
