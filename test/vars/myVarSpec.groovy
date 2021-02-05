import com.legrig.ClassWithSteps
@java.lang.SuppressWarnings('UnusedImport')
import myVar //This import seems to be required for vars
import spock.util.mop.ConfineMetaClassChanges
import support.jenkins.CPSSpecification

class myVarSpec extends CPSSpecification {

  Class testSubjectClass = myVar

  def "myVar() Test"() {
    when:
      asCPSScript '''
          sut()
      '''

    then:
      sut.echo(_) >> { String param ->
        assert param == 'Executed'
      }
  }

  @ConfineMetaClassChanges([ClassWithSteps])
  def "myVar.sendError() Test"() {
    given: 'a controlled instance of ClassWithSteps'
      ClassWithSteps mockedClassWithSteps = GroovySpy()

      // Use our instance when one is requested
      GroovySpy(ClassWithSteps, global: true) {
        new ClassWithSteps() >> mockedClassWithSteps
      }

    when: 'we send error'
      asCPSScript '''
          sut.sendError('My Error')
      '''

    then: 'we call our mocked error'
      1 * mockedClassWithSteps.error('My Error')

    then: 'we call echo '
      1 * mockedClassWithSteps.echo('ERROR: My Error') >> null
  }
}
