package epam.core.services.impl;

import epam.core.services.HTTPService;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

@Component(service = HTTPService.class, immediate = true)
public class HTTPServiceImpl implements HTTPService {
    private static final Logger LOG = LoggerFactory.getLogger(HTTPServiceImpl.class);

    @Override
    public JSONObject sendGet(String url) {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);
        JSONObject result = new JSONObject();

        try {
            HttpResponse response = client.execute(request);

            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder stringBuilder = new StringBuilder();

            String line;

            while (Objects.nonNull(line = reader.readLine())) {
                stringBuilder.append(line);
            }

            result = new JSONObject(stringBuilder.toString());
        } catch (IOException e) {
            LOG.error("Failed to send request");
        } catch (JSONException e) {
            LOG.error("Failed to parse response to json");
        }

        return result;
    }
}
