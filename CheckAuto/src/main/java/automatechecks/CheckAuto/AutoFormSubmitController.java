package automatechecks.CheckAuto;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.imageio.ImageIO;
import javax.net.ssl.HttpsURLConnection;
import javax.swing.JEditorPane;

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
    	// 1st method for https://zipnet.delhipolice.gov.in/index.php
    	try {
    		
    		if(null!=args && args.length>0) {
    			
    			String reg_no = args[0];
        		String engine_no = args[1];
        		String chasis_no = args[2];
        		boolean isVehicalStolen = new FormFillUtil().formFiller(reg_no,engine_no,chasis_no); //Method params formFiller(reg_no,engine_no,chasis_no)
        		System.out.println("Vehicle is stolen? -> "+ (!isVehicalStolen));
    		}
    		else {
    			System.out.println("Please give input params as : reg_no, engine_no, chasis_no");
    		}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
