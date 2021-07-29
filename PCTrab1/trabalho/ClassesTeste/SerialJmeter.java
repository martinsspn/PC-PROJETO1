package ClassesTeste;

import java.io.Serializable;

import classesComuns.LerCSV;
import classesPrincipais.Serial;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;


public class SerialJmeter extends AbstractJavaSamplerClient implements Serializable {
    @Override
    public SampleResult runTest(JavaSamplerContext javaSamplerContext) {

        Serial.imagens = LerCSV.lerCSV();
        Serial serial = new Serial();
        int res = serial.verificarImagem();
        SampleResult result = new SampleResult();
        result.sampleStart();
        result.setSampleLabel("Test Sample");

        if(res==0) {
            result.sampleEnd();
            result.setResponseCode("200");
            result.setResponseMessage("OK");
            result.setSuccessful(true);
        } else {
            result.sampleEnd();
            result.setResponseCode("500");
            result.setResponseMessage("NOK");
            result.setSuccessful(false);
        }
        return result;
    }

    @Override public Arguments getDefaultParameters() {
        Arguments defaultParameters = new Arguments();
        defaultParameters.addArgument("var1","1");
        defaultParameters.addArgument("var2","2");
        return defaultParameters;
    }
}