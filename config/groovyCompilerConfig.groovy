import com.cloudbees.groovy.cps.CpsTransformer

// This enables CPS transforms on build
configuration.addCompilationCustomizers(new CpsTransformer())

