package pl.rynski.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import pl.rynski.soapws.RegisterCallRequest;
import pl.rynski.soapws.RegisterCallResponse;
import pl.rynski.soapws.ResultsRequest;
import pl.rynski.soapws.ResultsResponse;

import java.util.stream.Stream;

@Service
public class SoapClient {
    private final SoapConnector soapConnector;
    private final String url;

    @Autowired
    public SoapClient(@Value("${webservice.url}") String url, SoapConnector soapConnector) {
        this.url = url;
        this.soapConnector = soapConnector;
        getRegisterCallRequest();
        getResultsRequest();
    }

    private void getRegisterCallRequest() {
        RegisterCallRequest request = new RegisterCallRequest();
        request.setStudent("Michu");
        RegisterCallResponse response = (RegisterCallResponse) soapConnector.callWebService(url, request);

        System.out.println("Response from register call:");
        System.out.println("Name: " + response.getExercise().getName());
        System.out.println("Note: " + response.getExercise().getNote());
    }

    private void getResultsRequest() {
        ResultsRequest request = new ResultsRequest();
        request.setStudent("Michu");
        ResultsResponse response = (ResultsResponse) soapConnector.callWebService(url, request);

        System.out.println("Response from results");
        response.getEntry().forEach(output -> System.out.println("Entry: " + output));
    }
}
