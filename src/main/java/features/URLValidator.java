package features;


import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

public class URLValidator {

    public static boolean validate(String url) throws UnknownHostException {
        try {
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                return false;
            }

            URL obj = new URL(url);

            if (obj.getHost() == null) {
                return false;
            }

            String tld = obj.getHost().substring(obj.getHost().lastIndexOf(".") + 1);
            if (!tld.matches("[a-zA-Z]{2,}")) {
                return false;
            }

            return true;

        } catch (MalformedURLException e) {
            System.err.println("Malformed URL: " + url);
            return false;
        }
    }
}
