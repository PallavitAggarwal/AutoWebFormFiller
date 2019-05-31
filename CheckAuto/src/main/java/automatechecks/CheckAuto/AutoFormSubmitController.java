package automatechecks.CheckAuto;

import java.io.IOException;
import java.net.MalformedURLException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.apache.http.NameValuePair;

/**
 * Hello world!
 *
 */
public class AutoFormSubmitController 
{
    public static void main( String[] args ) 
    {
    	
    	try {
    			// 1st method for https://zipnet.delhipolice.gov.in/index.php
			//Method params formFiller(reg_no,engine_no,chasis_no)
			boolean isVehicalStolen = new FormFillUtil().formFiller("DL1CT0107","K12MN1540680","MA3EJKD1S00691921");
			
			
			if(isVehicalStolen) {
				System.out.println("YES Radio Button is pressed.");
			}
			else {
				System.out.println("NO Radio Button is pressed.");
			}
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
