package automatechecks.CheckAuto;

import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.JEditorPane;
import javax.swing.text.html.HTMLEditorKit;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

public class FormFillUtil {

	public boolean formFiller(String reg_no, String engine_no, String chasis_no) throws MalformedURLException, IOException {
		Logger logger = LogManager.getLogManager().getLogger("");
		logger.setLevel(Level.INFO);
		try {
			
			//Apache HttpClient for https , ssl certificate should be trusted.
			HttpClient webC = HttpClients.custom()
			        .setSSLSocketFactory(new SSLConnectionSocketFactory(SSLContexts.custom()
			                .loadTrustMaterial(null, new TrustSelfSignedStrategy())
			                .build()
			            )
			        ).build();
	    	ResponseHandler<String> handler = new BasicResponseHandler();
	    	HttpPost httpPost = new HttpPost("https://zipnet.delhipolice.gov.in/index.php");
	    	
	    	//request to be built and sent
	    	List <NameValuePair> request = new ArrayList <NameValuePair>();
	    	
	    	
	    	request.add(new BasicNameValuePair("rg_no", reg_no));
	    	request.add(new BasicNameValuePair("engine_number", engine_no));
	    	request.add(new BasicNameValuePair("chasis_number", chasis_no));
	    	
	    	//Setting static data to build the request
	    	
	    	//vehical type is static 
	    	request.add(new BasicNameValuePair("vehtype", "CAR")); 
	    	//criteria "search" is static
	    	request.add(new BasicNameValuePair("criteria", "search"));
	    	//page type is static
	    	request.add(new BasicNameValuePair("page", "stolen_vehicles_search"));
	    	//static data 
	    	request.add(new BasicNameValuePair("I4.x", "25"));
	    	//static data
	    	request.add(new BasicNameValuePair("I4.yx", "10"));
	    	
	    	boolean isVehicalStolen = sendBooleanResponse(webC, httpPost, request);
	    	
	    	return isVehicalStolen;
	    	
		} catch (KeyManagementException e) {
			e.printStackTrace();
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
		return false;
	}

	private boolean sendBooleanResponse(HttpClient webC, HttpPost httpPost, List<NameValuePair> request)
			throws UnsupportedEncodingException, IOException, ClientProtocolException {
		httpPost.setEntity(new UrlEncodedFormEntity(request));
		HttpResponse response2 = webC.execute(httpPost);
		System.out.println(response2.getEntity());
		HttpEntity responseEntity = response2.getEntity();
		
		
		String responseString = EntityUtils.toString(responseEntity);
		
		convertHtmlToImg(responseString);
        
		if(responseString.contains("No match found for the above parameters")) {
			//take a pic and return true
			return true;
		}
		else {
			return false;
		}
	}

	private void convertHtmlToImg(String responseString) {
		
		String split_segment_1[] = responseString.split("<form method=\"POST\" action=\"index.php\">");
		String split_segment_2[] = split_segment_1[split_segment_1.length -1].split("<!-- hitwebcounter Code START -->");
		
		//Get the html string segment that will render the img
		String HtmlToImg = split_segment_2[0];
		System.out.println(HtmlToImg);
	      int width = 1000, height = 1000;
	      BufferedImage image = GraphicsEnvironment.getLocalGraphicsEnvironment()
	          .getDefaultScreenDevice().getDefaultConfiguration()
	          .createCompatibleImage(width, height);
	
	      Graphics graphics = image.createGraphics();
	      //Convert given HTML to Image format
	      
	      JEditorPane jep = new JEditorPane("text/html", HtmlToImg);
	      jep.setSize(width, height);
	      jep.print(graphics);
	      try {
	    	  	//Save image in PNG format
				ImageIO.write(image, "png", new File("Captured_Img.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
}
