package org;

import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HttpClientMain2 {

	/*
Exception in thread "main" java.lang.IllegalStateException: SSLContextImpl is not initialized
	at sun.security.ssl.SSLContextImpl.engineGetSocketFactory(SSLContextImpl.java:178)
	at javax.net.ssl.SSLContext.getSocketFactory(SSLContext.java:295)
	at javax.net.ssl.SSLSocketFactory.getDefault(SSLSocketFactory.java:121)
	at javax.net.ssl.HttpsURLConnection.getDefaultSSLSocketFactory(HttpsURLConnection.java:333)
	at javax.net.ssl.HttpsURLConnection.<init>(HttpsURLConnection.java:291)
	at sun.net.www.protocol.https.HttpsURLConnectionImpl.<init>(HttpsURLConnectionImpl.java:85)
	at sun.net.www.protocol.https.Handler.openConnection(Handler.java:62)
	at sun.net.www.protocol.https.Handler.openConnection(Handler.java:57)
	at java.net.URL.openConnection(URL.java:975)
	at org.HttpClientMain2.main(HttpClientMain2.java:24)
	 */
	
	/*
	 * java 7 prioer to 131 doesn't support TLS1.2
	 * 
	 * Exception in thread "main" javax.net.ssl.SSLException: Received fatal alert: protocol_version
	at sun.security.ssl.Alerts.getSSLException(Alerts.java:208)
	at sun.security.ssl.Alerts.getSSLException(Alerts.java:154)
	at sun.security.ssl.SSLSocketImpl.recvAlert(SSLSocketImpl.java:1979)
	 */
	public static void main(String[] args) throws Exception {
		// configure the SSLContext with a TrustManager
		SSLContext ctx = SSLContext.getInstance("TLS");
		ctx.init(new KeyManager[0], new TrustManager[] { new DefaultTrustManager() }, new SecureRandom());
		SSLContext.setDefault(ctx);

		//URL url = new URL("https://www.google.com");
		URL url = new URL("https://inthub.stage.auctionintegrationhub.com/ChryslerDataExchange/getFileList");
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		conn.setHostnameVerifier(new HostnameVerifier() {
			@Override
			public boolean verify(String arg0, SSLSession arg1) {
				return true;
			}
		});
		System.out.println(conn.getResponseCode());
		conn.disconnect();
	}

	private static class DefaultTrustManager implements X509TrustManager {

		@Override
		public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
		}

		@Override
		public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
		}

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}
	}
}