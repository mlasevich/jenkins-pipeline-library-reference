package com.legrig

import com.cloudbees.groovy.cps.impl.CpsCallableInvocation
import support.jenkins.CPSSpecification

class CPSNonCPSTestClassSpec extends CPSSpecification{

  Class testSubjectClass = CPSNonCPSTestClass

  def "CPSNonCPSTestClass.nonCpsMethod() Test"(){

    when: 'we call the method'
      def itWorks = sut.nonCpsMethod()

    then: 'it works'
      itWorks
  }

  def "CPSNonCPSTestClass.cpsMethod() from NonCPS Test"(){

    when: 'we call method directly'
      def itWorks = sut.cpsMethod()

    then: 'we get an exception'
      thrown CpsCallableInvocation

    and: 'it does not work'
      ! itWorks
  }

  def "CPSNonCPSTestClass.cpsMethod() from CPS Test"(){

    when: 'we call method via invokeCPSMethod'
      def itWorks = invokeCPSMethod(sut, 'cpsMethod')

    then: 'it works'
      itWorks
  }

  def "CPSNonCPSTestClass.cpsMethodCallingNonCps() from NonCPS Test"(){

    when: 'we call method directly'
      def itWorks = sut.cpsMethodCallingNonCps()

    then: 'we get an exception'
      thrown CpsCallableInvocation

    and: 'it does not work'
      ! itWorks
  }

  def "CPSNonCPSTestClass.cpsMethodCallingNonCps() from CPS Test"(){

    when: 'we call method via invokeCPSMethod'
      def itWorks = invokeCPSMethod(sut, 'cpsMethodCallingNonCps')

    then: 'it works'
      itWorks
  }


  def "CPSNonCPSTestClass.cpsMethodCallingNonCpsCallingCps() from NonCPS Test"(){

    when: 'we call method directly'
      def itWorks = sut.cpsMethodCallingNonCpsCallingCps()

    then: 'we get an exception'
      thrown CpsCallableInvocation

    and: 'it does not work'
      ! itWorks
  }

  def "CPSNonCPSTestClass.cpsMethodCallingNonCpsCallingCps() from CPS Test"(){

    when: 'we call method via invokeCPSMethod'
      def itWorks = invokeCPSMethod(sut, 'cpsMethodCallingNonCpsCallingCps')

    then: 'we get an exception'
      thrown IllegalStateException

    and: 'it does not work'
      ! itWorks
  }
}
