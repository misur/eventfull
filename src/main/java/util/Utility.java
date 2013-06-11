package util;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
/*
 * Klasa Utility sadrzi encode metodu koji odredjeni sadrzaj kodira u "UTF-8" format.
 */

public class Utility {
	
	public static String readString(InputStream in) throws IOException {

		try {
			BufferedReader reader = new BufferedReader( new InputStreamReader (in) );
			StringBuffer b = new StringBuffer();
			String s = reader.readLine();
			while ( s != null ) {
				b.append(s);
				if ( (s = reader.readLine()) != null )
					b.append('\n');
			}
			return b.toString();
		} finally {
			in.close();
		}

	}

	public static CharSequence encode(String content) {
		try {
			return URLEncoder.encode(content, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new AssertionError(e);
		}
	}
	
	public static void writeToAFile(String fileName, String content) {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter (new FileWriter( fileName ));
			writer.print( content );

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			writer.close(); 
		}

	}

}
