package epam.core.services;

import org.json.JSONObject;

public interface HTTPService {
    JSONObject sendGet(String url);
}
