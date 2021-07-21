package ClassesTeste;

import java.io.Serializable;

import ClassesThreads.ThreadMTXSEM;
import classesComuns.LerCSV;
import classesPrincipais.Serial;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;


public class MTXSEMJmeter extends AbstractJavaSamplerClient implements Serializable {
    @Override
    public SampleResult runTest(JavaSamplerContext javaSamplerContext) {

        Serial.imagens = LerCSV.lerCSV();
        ThreadMTXSEM threadMTXSEM = new ThreadMTXSEM();
        int res = 0;
        try {
            res = threadMTXSEM.verificarImagem();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

    @Override
    public Arguments getDefaultParameters() {
        Arguments defaultParameters = new Arguments();
        return defaultParameters;
    }
}