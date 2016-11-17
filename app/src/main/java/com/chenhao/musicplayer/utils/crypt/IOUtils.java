package com.chenhao.musicplayer.utils.crypt;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.Arrays;


/**
 * @author 李建衡：jianheng.li@kuwo.cn
 * 
 */
public class IOUtils {
	
public static final int MAX_BUFFER_BYTES = 2048;
	
	public static int readShort(InputStream in) throws IOException {
		byte[] buffer = null;
		
		try {
			buffer = new byte[2];			
		} catch (OutOfMemoryError oom) {
			return -1;
		}
		
		if(in.markSupported())
			in.mark(2);
		int count = in.read(buffer);
		// 没有读取到数据，或者数据不充分
		if(count <= 0 ) {
			buffer = null;
			
			return -1;
		} 
		else if(count == 1) {
			in.reset();
			buffer = null;
			return -1;
		}			
		
		else {
			int result = (((buffer[0] << 8) & 0xff00) | (buffer[1] & 0xff));
			buffer = null;
			return result;
		}		
		
	}
	
	public static long readInt(InputStream in) throws IOException {
		byte[] buffer = null;
		
		try {
			buffer = new byte[4];
		} catch (OutOfMemoryError oom) {
			return -1L;
		}	
			
		if(in.markSupported())
			in.mark(4);
		
		int count = in.read(buffer, 0, 4);
		// 没有读取到数据，或者数据不充分
		if(count <= 0 ) {
			buffer = null;
			return -1L;
		} 
		else if(count < 4){
			in.reset();
			buffer = null;
			return -1L;
		}			
		else {
			
			long result = (long) (((buffer[0] << 24)& 0xff000000) | 
					((buffer[1]<<16) & 0xff0000) | 
					((buffer[2]<<8)& 0xff00) | 
					(buffer[3] & 0xff));
			
			buffer = null;
			return result;
		}

	}
	
	public static byte[] readBytes(InputStream in, int len) throws IOException {
		if( len <= 0 ) {
			return null;
		}
		
		int pos = 0, recvBytes = 0;
		byte[] buffer = null;
		
		try {
			buffer = new byte[len];			
		} catch (OutOfMemoryError oom) {
			return null;
		}
		
		try {
			while(pos < len && (recvBytes = in.read(buffer, pos, len - pos)) > 0) {
				pos += recvBytes;
			}	
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
			return null;
		}
		
		return buffer;
	}

	// 如果内存申请失败，返回null
	public static CharSequence readString(InputStream in, int len) throws IOException {
		int leftBytes = len, recvBytes = 0;
		int bufLen = Math.min(leftBytes, MAX_BUFFER_BYTES);
		
		byte[] buffer = null;
		
		try {
			buffer = new byte[bufLen];			
		} catch (OutOfMemoryError oom) {
			return null;
		}
		
		ByteArrayOutputStream outputStream = null;
		
		try {
			outputStream = new ByteArrayOutputStream(MAX_BUFFER_BYTES);
			
			while(leftBytes > 0 && (recvBytes = in.read(buffer, 0, bufLen)) != -1 ) {
				outputStream.write(buffer, 0, recvBytes);
				leftBytes -= recvBytes;			
			}	
		} catch (Exception e) {
			e.printStackTrace();
			outputStream.close();
			return null;
		}
		
		
		buffer = null;
		String result = outputStream.toString();
		outputStream.close();
		return result;
	}
	
	// 如果空间申请失败，返回null
	public static CharSequence readString(InputStream in, int len,String characterSet) throws IOException {
		int leftBytes = len, recvBytes = 0;
		int bufLen = Math.min(leftBytes, MAX_BUFFER_BYTES);		
		byte[] buffer = null;
		
		try {
			buffer = new byte[bufLen];			
		} catch (OutOfMemoryError oom) {
			return null;
		}
		
		ByteArrayOutputStream outputStream = null;
		
		try {
			outputStream = new ByteArrayOutputStream(MAX_BUFFER_BYTES);
			
			while(leftBytes > 0 && (recvBytes = in.read(buffer, 0, bufLen)) != -1 ) {
				outputStream.write(buffer, 0, recvBytes);
				leftBytes -= recvBytes;			
			}	
		} catch (Exception e) {
			e.printStackTrace();
			outputStream.close();
			return null;
		}		
		
		buffer = null;
		String result = outputStream.toString(characterSet);
		outputStream.close();
		return result;
	}
	
	public static void writeShort(OutputStream out,int s) throws IOException {
		byte[] buffer = new byte[] {(byte)((s >> 8)& 0xff), (byte)(s&0xff)};
		
		out.write(buffer);
		out.flush();
		buffer = null;
	}
	
	public static void writeInt(OutputStream out,int s) throws IOException {
		byte[] buffer = new byte[] {
				(byte)((s >> 24)& 0xff), 
				(byte)((s >> 16)& 0xff),
				(byte)((s >> 8)& 0xff), 
				(byte)(s&0xff)};
		
		out.write(buffer);
		out.flush();			
		buffer = null;		
	}
	
	public static void writeString(OutputStream out, String s) throws IOException {
		out.write(s.getBytes());
		out.flush();
	}
	
	public static void writeString(OutputStream out, String s, String characterSet) throws IOException {
		out.write(s.getBytes(characterSet));
		out.flush();
	}
	
	public static void writeString(OutputStream out, String s, int fixedLen)throws IOException {
		byte[] bytes = s.getBytes("utf-8");
		if(fixedLen <= bytes.length){
			out.write(bytes, 0, fixedLen);
		} else {
			out.write(bytes);
			
			byte[] fillBytes = null;
			
			try {
				fillBytes = new byte[fixedLen - bytes.length];
				Arrays.fill(fillBytes, (byte)0);
				out.write(fillBytes);
			} catch (OutOfMemoryError oom) {
				oom.printStackTrace();
			}
		}
		
		out.flush();
	}
	
	public static void writeString(OutputStream out, String s, String characterSet, int fixedLen)throws IOException {
		byte[] bytes = s.getBytes(characterSet);
		if(fixedLen <= bytes.length){
			out.write(bytes, 0, fixedLen);
		} else {
			out.write(bytes);
			
			byte[] fillBytes = null;
			
			try {
				fillBytes = new byte[fixedLen - bytes.length];
				Arrays.fill(fillBytes, (byte)0);
				out.write(fillBytes);
			} catch (OutOfMemoryError oom) {
				oom.printStackTrace();
			}
		}
		
		out.flush();
	}
	
	public static ReadableByteChannel getChannel(InputStream inputStream) {
	    return (inputStream != null) ? Channels.newChannel(inputStream) : null;
	}

	   
    public static WritableByteChannel getChannel(OutputStream outputStream) {
        return (outputStream != null) ? Channels.newChannel(outputStream)
                : null;
    }
    
    public static long exhaust(InputStream input) throws IOException {
        long result = 0L;

        if (input != null) {
            byte[] buf = null;
    		
    		try {
    			buf = new byte[MAX_BUFFER_BYTES/2];
    		} catch (OutOfMemoryError oom) {
    			return result;
    		}
    		
            try {
	            int read = input.read(buf);
	            result = (read == -1) ? -1 : 0;
	
	            while ( read != -1 ) {
	                result += read;
	                read = input.read(buf);
	            }
            }catch (IOException e) {
            	throw e;
			}finally {
				buf = null;
			}
        }

        return result;
    }
    
    public static String readLeft(InputStream in) throws IOException {
    	String result = null;
    	if (in != null) {
        	int  recvBytes = 0;
        	boolean cancel = false;
    		byte[] buffer= null;
    		
    		try {
    			buffer = new byte[MAX_BUFFER_BYTES/2];
    		} catch (OutOfMemoryError oom) {
    			return result;
    		}
    		
    		ByteArrayOutputStream outputStream = null;
    		
    		try {
    			outputStream = new ByteArrayOutputStream(MAX_BUFFER_BYTES);
        		
    			while((recvBytes = in.read(buffer, 0, MAX_BUFFER_BYTES/2)) > 0) {
    				outputStream.write(buffer, 0, recvBytes);
    				try {
    					Thread.sleep(10);
    				} catch (InterruptedException e) {
    					cancel = true;
    					break;
    				}
    			}	
    		} catch (Exception e) {
    			e.printStackTrace();
    			outputStream.close();
        		in.close();
    			return null;
    		}
    		 
    		buffer = null;
    		
    		if (!cancel) {
    			result = outputStream.toString();
    		}
    		
    		outputStream.close();
    		in.close();
        }
    	
        return result;
    }
    
    public static String readLeft(InputStream in, String characterSet) throws IOException {
    	String result = null;
    	if (in != null) {
        	int  recvBytes = 0;
        	boolean cancel = false;
    		byte[] buffer= null;
    		
    		try {
    			buffer = new byte[MAX_BUFFER_BYTES/2];
    		} catch (OutOfMemoryError oom) {
    			return result;
    		}
    		
    		ByteArrayOutputStream outputStream = null;
    		try {
    			outputStream = new ByteArrayOutputStream(MAX_BUFFER_BYTES);
    			while((recvBytes = in.read(buffer, 0, MAX_BUFFER_BYTES/2)) > 0) {
    				outputStream.write(buffer, 0, recvBytes);
    				try {
    					Thread.sleep(10);
    				} catch (InterruptedException e) {
    					cancel = true;
    					break;
    				}
    			}	
    		} catch (Exception e) {
    			e.printStackTrace();
    			outputStream.close();
        		in.close();
    			return null;
    		}
			
    		buffer = null;	
    		if (!cancel) {
    			result = outputStream.toString(characterSet);
    		}
    		outputStream.close();
    		in.close();
        }
        return result;
    }
    
    public static byte[] readLeftBytes(InputStream in) throws IOException {
    	byte[] result = null;
    	if (in != null) {
        	int  recvBytes = 0;
        	boolean cancel = false;
    		byte[] buffer= null;
    		
    		try {
    			buffer = new byte[MAX_BUFFER_BYTES];
    		} catch (OutOfMemoryError oom) {
    			return result;
    		}
    		
    		ByteArrayOutputStream outputStream = null;
    		
    		try {
    			outputStream = new ByteArrayOutputStream(MAX_BUFFER_BYTES);
    			while((recvBytes = in.read(buffer, 0, MAX_BUFFER_BYTES)) > 0) {
    				outputStream.write(buffer, 0, recvBytes);
    				try {
    					Thread.sleep(10);
    				} catch (InterruptedException e) {
    					cancel = true;
    					break;
    				}
    			} 
    		} catch (Exception e) {
    			e.printStackTrace();

    			outputStream.close();
        		in.close();
        		return null;
    		}
    					
    		buffer = null;	
    		if (!cancel) {
    			result = outputStream.toByteArray();
    		}
    		outputStream.close();
    		in.close();
        }
    	
    	return result;
    }
    
    public static byte[] createZeroBytes(int length) {
    	if(length <= 0)
    		throw new IllegalArgumentException("length must be gt 0");
    	
    	byte[] bytes = null;
		
		try {
			bytes = new byte[length];			
		} catch (OutOfMemoryError oom) {
			return null;
		}
		
    	Arrays.fill(bytes,(byte)0);
    	return bytes;
    }    

    /**
	 * 从指定字节数组中查找某子字节数组的第一次出现的位置
	 * @param datas 指定数组
	 * @param start 起始查询位置
	 * @param t 待查询数组
	 * @return 如果没找到，返回-1，否则返回索引
	 */
	public static int indexOf(byte[] datas, int start, byte[] t) {
		
		if (datas == null || t == null) {
			throw new NullPointerException("source or target array is null!");
		}
		
		int index = -1;
		int len = datas.length;
		int tlen = t.length;
		
		if (start >= len || len-start < tlen ) {
			return -1;
		}

		while ( start <= len - tlen) {
			int i = 0;
			for ( ; i < tlen; i++) {
				if( datas[start+i] != t[i] ){					
					break;
				}					
			}
			
			if (i == tlen) {
				index = start;
				break;
			}
			
			start ++;
		}
		
		return index;
	}
	
	/**
	 * 从一个字节数组中解析一个整数
	 * @param buf 字节数组
	 * @param bigEndian 是否大字节序解析 
	 * @return 相应的整数
	 */
	public static int parseInteger(byte[] buf, boolean bigEndian) {
		return (int)parseNumber(buf, 4, bigEndian);
	}
	/**
	 * 从一个字节数组中解析一个短整数
	 * @param buf 字节数组
	 * @param bigEndian 是否大字节序解析 
	 * @return 相应的短整数
	 */
	public static int parseShort(byte[] buf, boolean bigEndian) {
		return (int)parseNumber(buf, 2, bigEndian);
	}
	
	/**
	 * 从一个字节数组中解析一个长整数
	 * @param buf 字节数组
	 * @param len 整数组成的字节数
	 * @param bigEndian 是否大字节序解析 
	 * @return 相应的长整数
	 */
	public static long parseNumber(byte[] buf, int len, boolean bigEndian) {
		if (buf == null || buf.length == 0) {
			throw new IllegalArgumentException("byte array is null or empty!");
		}
		int mlen = Math.min(len, buf.length);
		long r = 0;
		if (bigEndian)
			for (int i = 0; i < mlen; i++) {
				r <<= 8;
				r |= (buf[i] & 0x000000ff);
			}
		else
			for (int i = mlen-1; i >= 0; i--) {
				r <<= 8;
				r |= (buf[i] & 0x000000ff);
			}
		return r;
	}

	public static String convertStreamToString(InputStream is, String charset){

		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(is, charset));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return "";
		}
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return sb.toString();
	}
	
	public static byte[] convertStreamToBytes(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = null;
		
		try {
			buffer = new byte[1024];	
		} catch (OutOfMemoryError oom) {
			baos.close();
			return null;
		}
				
		int totalRead = 0;
		int read = 0;
		
		try {
			while ((read = is.read(buffer, totalRead, 1024)) > 0) {
				baos.write(buffer, totalRead, read);
			}	
		} catch (Exception e) {
			e.printStackTrace();
			baos.close();
			return null;
		}
		
		
		byte[] temp = baos.toByteArray(); 
		
		try {
			baos.close();
		} catch (IOException e) {					
		}
		
		return temp;
	}
	
	public static String readLine(InputStream in) {
	    StringBuilder stringBuilder = new StringBuilder();
	    int i = -1;
	    try
	    {
	      while ((i = in.read()) != -1)
	      {
	        char c = (char)i;
	        if (c == '\n') {
	        	stringBuilder.append("\r\n");
	          break;
	        }
	        if (c != '\r') {
	        	stringBuilder.append(c);
	        }

	      }

	    }
	    catch (Exception localException)
	    {
	      if (stringBuilder.length() == 0) {
	        if (i == -1) {
	          return null;
	        }
	        return "";
	      }
	    }

	    return stringBuilder.toString();
	  }

	public static String stringFromFile(final File file) {
		try {
			FileInputStream fin = new FileInputStream(file);
			try {
				byte[] buffer = new byte[fin.available()];
				fin.read(buffer);
				return new String(buffer);
			} finally {
				fin.close();
			}
		} catch (Exception e) {
		}
		return "";
	}
	public static String stringFromFile(final String path) {
		return stringFromFile(new File(path));
	}
	
	public static byte[] bytesFromFile(final File file) {
		try {
			FileInputStream fin = new FileInputStream(file);
			try {
				byte[] buffer = new byte[fin.available()];
				fin.read(buffer);
				return buffer;
			} finally {
				fin.close();
			}
		} catch (Exception e) {
		}
		return null;
	}
	public static byte[] bytesFromFile(final String path) {
		return bytesFromFile(new File(path));
	}
}
