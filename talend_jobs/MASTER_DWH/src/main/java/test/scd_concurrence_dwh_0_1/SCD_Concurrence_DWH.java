// ============================================================================
//
// Copyright (c) 2006-2015, Talend SA
//
// Ce code source a été automatiquement généré par_Talend Open Studio for Data Integration
// / Soumis à la Licence Apache, Version 2.0 (la "Licence") ;
// votre utilisation de ce fichier doit respecter les termes de la Licence.
// Vous pouvez obtenir une copie de la Licence sur
// http://www.apache.org/licenses/LICENSE-2.0
// 
// Sauf lorsqu'explicitement prévu par la loi en vigueur ou accepté par écrit, le logiciel
// distribué sous la Licence est distribué "TEL QUEL",
// SANS GARANTIE OU CONDITION D'AUCUNE SORTE, expresse ou implicite.
// Consultez la Licence pour connaître la terminologie spécifique régissant les autorisations et
// les limites prévues par la Licence.


package test.scd_concurrence_dwh_0_1;

import routines.Numeric;
import routines.DataOperation;
import routines.TalendDataGenerator;
import routines.TalendStringUtil;
import routines.TalendString;
import routines.StringHandling;
import routines.Relational;
import routines.TalendDate;
import routines.Mathematical;
import routines.system.*;
import routines.system.api.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.math.BigDecimal;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.util.Comparator;
 





@SuppressWarnings("unused")

/**
 * Job: SCD_Concurrence_DWH Purpose: <br>
 * Description:  <br>
 * @author user@talend.com
 * @version 8.0.1.20211109_1610
 * @status 
 */
public class SCD_Concurrence_DWH implements TalendJob {

protected static void logIgnoredError(String message, Throwable cause) {
       System.err.println(message);
       if (cause != null) {
               cause.printStackTrace();
       }

}


	public final Object obj = new Object();

	// for transmiting parameters purpose
	private Object valueObject = null;

	public Object getValueObject() {
		return this.valueObject;
	}

	public void setValueObject(Object valueObject) {
		this.valueObject = valueObject;
	}
	
	private final static String defaultCharset = java.nio.charset.Charset.defaultCharset().name();

	
	private final static String utf8Charset = "UTF-8";
	//contains type for every context property
	public class PropertiesWithType extends java.util.Properties {
		private static final long serialVersionUID = 1L;
		private java.util.Map<String,String> propertyTypes = new java.util.HashMap<>();
		
		public PropertiesWithType(java.util.Properties properties){
			super(properties);
		}
		public PropertiesWithType(){
			super();
		}
		
		public void setContextType(String key, String type) {
			propertyTypes.put(key,type);
		}
	
		public String getContextType(String key) {
			return propertyTypes.get(key);
		}
	}
	
	// create and load default properties
	private java.util.Properties defaultProps = new java.util.Properties();
	// create application properties with default
	public class ContextProperties extends PropertiesWithType {

		private static final long serialVersionUID = 1L;

		public ContextProperties(java.util.Properties properties){
			super(properties);
		}
		public ContextProperties(){
			super();
		}

		public void synchronizeContext(){
			
		}
		
		//if the stored or passed value is "<TALEND_NULL>" string, it mean null
		public String getStringValue(String key) {
			String origin_value = this.getProperty(key);
			if(NULL_VALUE_EXPRESSION_IN_COMMAND_STRING_FOR_CHILD_JOB_ONLY.equals(origin_value)) {
				return null;
			}
			return origin_value;
		}

	}
	protected ContextProperties context = new ContextProperties(); // will be instanciated by MS.
	public ContextProperties getContext() {
		return this.context;
	}
	private final String jobVersion = "0.1";
	private final String jobName = "SCD_Concurrence_DWH";
	private final String projectName = "TEST";
	public Integer errorCode = null;
	private String currentComponent = "";
	
		private final java.util.Map<String, Object> globalMap = new java.util.HashMap<String, Object>();
        private final static java.util.Map<String, Object> junitGlobalMap = new java.util.HashMap<String, Object>();
	
		private final java.util.Map<String, Long> start_Hash = new java.util.HashMap<String, Long>();
		private final java.util.Map<String, Long> end_Hash = new java.util.HashMap<String, Long>();
		private final java.util.Map<String, Boolean> ok_Hash = new java.util.HashMap<String, Boolean>();
		public  final java.util.List<String[]> globalBuffer = new java.util.ArrayList<String[]>();
	

private RunStat runStat = new RunStat();

	// OSGi DataSource
	private final static String KEY_DB_DATASOURCES = "KEY_DB_DATASOURCES";
	
	private final static String KEY_DB_DATASOURCES_RAW = "KEY_DB_DATASOURCES_RAW";

	public void setDataSources(java.util.Map<String, javax.sql.DataSource> dataSources) {
		java.util.Map<String, routines.system.TalendDataSource> talendDataSources = new java.util.HashMap<String, routines.system.TalendDataSource>();
		for (java.util.Map.Entry<String, javax.sql.DataSource> dataSourceEntry : dataSources.entrySet()) {
			talendDataSources.put(dataSourceEntry.getKey(), new routines.system.TalendDataSource(dataSourceEntry.getValue()));
		}
		globalMap.put(KEY_DB_DATASOURCES, talendDataSources);
		globalMap.put(KEY_DB_DATASOURCES_RAW, new java.util.HashMap<String, javax.sql.DataSource>(dataSources));
	}
	
	public void setDataSourceReferences(List serviceReferences) throws Exception{
		
		java.util.Map<String, routines.system.TalendDataSource> talendDataSources = new java.util.HashMap<String, routines.system.TalendDataSource>();
		java.util.Map<String, javax.sql.DataSource> dataSources = new java.util.HashMap<String, javax.sql.DataSource>();
		
		for (java.util.Map.Entry<String, javax.sql.DataSource> entry : BundleUtils.getServices(serviceReferences,  javax.sql.DataSource.class).entrySet()) {
                    dataSources.put(entry.getKey(), entry.getValue());
                    talendDataSources.put(entry.getKey(), new routines.system.TalendDataSource(entry.getValue()));
		}

		globalMap.put(KEY_DB_DATASOURCES, talendDataSources);
		globalMap.put(KEY_DB_DATASOURCES_RAW, new java.util.HashMap<String, javax.sql.DataSource>(dataSources));
	}


private final java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
private final java.io.PrintStream errorMessagePS = new java.io.PrintStream(new java.io.BufferedOutputStream(baos));

public String getExceptionStackTrace() {
	if ("failure".equals(this.getStatus())) {
		errorMessagePS.flush();
		return baos.toString();
	}
	return null;
}

private Exception exception;

public Exception getException() {
	if ("failure".equals(this.getStatus())) {
		return this.exception;
	}
	return null;
}

private class TalendException extends Exception {

	private static final long serialVersionUID = 1L;

	private java.util.Map<String, Object> globalMap = null;
	private Exception e = null;
	private String currentComponent = null;
	private String virtualComponentName = null;
	
	public void setVirtualComponentName (String virtualComponentName){
		this.virtualComponentName = virtualComponentName;
	}

	private TalendException(Exception e, String errorComponent, final java.util.Map<String, Object> globalMap) {
		this.currentComponent= errorComponent;
		this.globalMap = globalMap;
		this.e = e;
	}

	public Exception getException() {
		return this.e;
	}

	public String getCurrentComponent() {
		return this.currentComponent;
	}

	
    public String getExceptionCauseMessage(Exception e){
        Throwable cause = e;
        String message = null;
        int i = 10;
        while (null != cause && 0 < i--) {
            message = cause.getMessage();
            if (null == message) {
                cause = cause.getCause();
            } else {
                break;          
            }
        }
        if (null == message) {
            message = e.getClass().getName();
        }   
        return message;
    }

	@Override
	public void printStackTrace() {
		if (!(e instanceof TalendException || e instanceof TDieException)) {
			if(virtualComponentName!=null && currentComponent.indexOf(virtualComponentName+"_")==0){
				globalMap.put(virtualComponentName+"_ERROR_MESSAGE",getExceptionCauseMessage(e));
			}
			globalMap.put(currentComponent+"_ERROR_MESSAGE",getExceptionCauseMessage(e));
			System.err.println("Exception in component " + currentComponent + " (" + jobName + ")");
		}
		if (!(e instanceof TDieException)) {
			if(e instanceof TalendException){
				e.printStackTrace();
			} else {
				e.printStackTrace();
				e.printStackTrace(errorMessagePS);
				SCD_Concurrence_DWH.this.exception = e;
			}
		}
		if (!(e instanceof TalendException)) {
		try {
			for (java.lang.reflect.Method m : this.getClass().getEnclosingClass().getMethods()) {
				if (m.getName().compareTo(currentComponent + "_error") == 0) {
					m.invoke(SCD_Concurrence_DWH.this, new Object[] { e , currentComponent, globalMap});
					break;
				}
			}

			if(!(e instanceof TDieException)){
			}
		} catch (Exception e) {
			this.e.printStackTrace();
		}
		}
	}
}

			public void tDBInput_1_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tMap_1_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tDBSCD_1_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tDBInput_1_onSubJobError(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {

resumeUtil.addLog("SYSTEM_LOG", "NODE:"+ errorComponent, "", Thread.currentThread().getId()+ "", "FATAL", "", exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception),"");

			}
	






public static class outStruct implements routines.system.IPersistableRow<outStruct> {
    final static byte[] commonByteArrayLock_TEST_SCD_Concurrence_DWH = new byte[0];
    static byte[] commonByteArray_TEST_SCD_Concurrence_DWH = new byte[0];

	
			    public String category;

				public String getCategory () {
					return this.category;
				}
				
			    public String event_name;

				public String getEvent_name () {
					return this.event_name;
				}
				
			    public java.util.Date event_date;

				public java.util.Date getEvent_date () {
					return this.event_date;
				}
				
			    public String city;

				public String getCity () {
					return this.city;
				}
				
			    public String country;

				public String getCountry () {
					return this.country;
				}
				
			    public String price;

				public String getPrice () {
					return this.price;
				}
				
			    public java.util.Date scd_start;

				public java.util.Date getScd_start () {
					return this.scd_start;
				}
				
			    public java.util.Date scd_end;

				public java.util.Date getScd_end () {
					return this.scd_end;
				}
				
			    public String scraping_date;

				public String getScraping_date () {
					return this.scraping_date;
				}
				
			    public String concurrence_bk;

				public String getConcurrence_bk () {
					return this.concurrence_bk;
				}
				
			    public Integer dim_concurrence_id;

				public Integer getDim_concurrence_id () {
					return this.dim_concurrence_id;
				}
				



	private String readString(ObjectInputStream dis) throws IOException{
		String strReturn = null;
		int length = 0;
        length = dis.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			if(length > commonByteArray_TEST_SCD_Concurrence_DWH.length) {
				if(length < 1024 && commonByteArray_TEST_SCD_Concurrence_DWH.length == 0) {
   					commonByteArray_TEST_SCD_Concurrence_DWH = new byte[1024];
				} else {
   					commonByteArray_TEST_SCD_Concurrence_DWH = new byte[2 * length];
   				}
			}
			dis.readFully(commonByteArray_TEST_SCD_Concurrence_DWH, 0, length);
			strReturn = new String(commonByteArray_TEST_SCD_Concurrence_DWH, 0, length, utf8Charset);
		}
		return strReturn;
	}
	
	private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException{
		String strReturn = null;
		int length = 0;
        length = unmarshaller.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			if(length > commonByteArray_TEST_SCD_Concurrence_DWH.length) {
				if(length < 1024 && commonByteArray_TEST_SCD_Concurrence_DWH.length == 0) {
   					commonByteArray_TEST_SCD_Concurrence_DWH = new byte[1024];
				} else {
   					commonByteArray_TEST_SCD_Concurrence_DWH = new byte[2 * length];
   				}
			}
			unmarshaller.readFully(commonByteArray_TEST_SCD_Concurrence_DWH, 0, length);
			strReturn = new String(commonByteArray_TEST_SCD_Concurrence_DWH, 0, length, utf8Charset);
		}
		return strReturn;
	}

    private void writeString(String str, ObjectOutputStream dos) throws IOException{
		if(str == null) {
            dos.writeInt(-1);
		} else {
            byte[] byteArray = str.getBytes(utf8Charset);
	    	dos.writeInt(byteArray.length);
			dos.write(byteArray);
    	}
    }
    
    private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException{
		if(str == null) {
			marshaller.writeInt(-1);
		} else {
            byte[] byteArray = str.getBytes(utf8Charset);
            marshaller.writeInt(byteArray.length);
            marshaller.write(byteArray);
    	}
    }

	private java.util.Date readDate(ObjectInputStream dis) throws IOException{
		java.util.Date dateReturn = null;
        int length = 0;
        length = dis.readByte();
		if (length == -1) {
			dateReturn = null;
		} else {
	    	dateReturn = new Date(dis.readLong());
		}
		return dateReturn;
	}
	
	private java.util.Date readDate(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException{
		java.util.Date dateReturn = null;
        int length = 0;
        length = unmarshaller.readByte();
		if (length == -1) {
			dateReturn = null;
		} else {
	    	dateReturn = new Date(unmarshaller.readLong());
		}
		return dateReturn;
	}

    private void writeDate(java.util.Date date1, ObjectOutputStream dos) throws IOException{
		if(date1 == null) {
            dos.writeByte(-1);
		} else {
			dos.writeByte(0);
	    	dos.writeLong(date1.getTime());
    	}
    }
    
    private void writeDate(java.util.Date date1, org.jboss.marshalling.Marshaller marshaller) throws IOException{
		if(date1 == null) {
			marshaller.writeByte(-1);
		} else {
			marshaller.writeByte(0);
			marshaller.writeLong(date1.getTime());
    	}
    }
	private Integer readInteger(ObjectInputStream dis) throws IOException{
		Integer intReturn;
        int length = 0;
        length = dis.readByte();
		if (length == -1) {
			intReturn = null;
		} else {
	    	intReturn = dis.readInt();
		}
		return intReturn;
	}
	
	private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException{
		Integer intReturn;
        int length = 0;
        length = dis.readByte();
		if (length == -1) {
			intReturn = null;
		} else {
	    	intReturn = dis.readInt();
		}
		return intReturn;
	}

	private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException{
		if(intNum == null) {
            dos.writeByte(-1);
		} else {
			dos.writeByte(0);
	    	dos.writeInt(intNum);
    	}
	}
	
	private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException{
		if(intNum == null) {
			marshaller.writeByte(-1);
		} else {
			marshaller.writeByte(0);
			marshaller.writeInt(intNum);
    	}
	}

    public void readData(ObjectInputStream dis) {

		synchronized(commonByteArrayLock_TEST_SCD_Concurrence_DWH) {

        	try {

        		int length = 0;
		
					this.category = readString(dis);
					
					this.event_name = readString(dis);
					
					this.event_date = readDate(dis);
					
					this.city = readString(dis);
					
					this.country = readString(dis);
					
					this.price = readString(dis);
					
					this.scd_start = readDate(dis);
					
					this.scd_end = readDate(dis);
					
					this.scraping_date = readString(dis);
					
					this.concurrence_bk = readString(dis);
					
						this.dim_concurrence_id = readInteger(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }
    
    public void readData(org.jboss.marshalling.Unmarshaller dis) {

		synchronized(commonByteArrayLock_TEST_SCD_Concurrence_DWH) {

        	try {

        		int length = 0;
		
					this.category = readString(dis);
					
					this.event_name = readString(dis);
					
					this.event_date = readDate(dis);
					
					this.city = readString(dis);
					
					this.country = readString(dis);
					
					this.price = readString(dis);
					
					this.scd_start = readDate(dis);
					
					this.scd_end = readDate(dis);
					
					this.scraping_date = readString(dis);
					
					this.concurrence_bk = readString(dis);
					
						this.dim_concurrence_id = readInteger(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }

    public void writeData(ObjectOutputStream dos) {
        try {

		
					// String
				
						writeString(this.category,dos);
					
					// String
				
						writeString(this.event_name,dos);
					
					// java.util.Date
				
						writeDate(this.event_date,dos);
					
					// String
				
						writeString(this.city,dos);
					
					// String
				
						writeString(this.country,dos);
					
					// String
				
						writeString(this.price,dos);
					
					// java.util.Date
				
						writeDate(this.scd_start,dos);
					
					// java.util.Date
				
						writeDate(this.scd_end,dos);
					
					// String
				
						writeString(this.scraping_date,dos);
					
					// String
				
						writeString(this.concurrence_bk,dos);
					
					// Integer
				
						writeInteger(this.dim_concurrence_id,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }
    
    public void writeData(org.jboss.marshalling.Marshaller dos) {
        try {

		
					// String
				
						writeString(this.category,dos);
					
					// String
				
						writeString(this.event_name,dos);
					
					// java.util.Date
				
						writeDate(this.event_date,dos);
					
					// String
				
						writeString(this.city,dos);
					
					// String
				
						writeString(this.country,dos);
					
					// String
				
						writeString(this.price,dos);
					
					// java.util.Date
				
						writeDate(this.scd_start,dos);
					
					// java.util.Date
				
						writeDate(this.scd_end,dos);
					
					// String
				
						writeString(this.scraping_date,dos);
					
					// String
				
						writeString(this.concurrence_bk,dos);
					
					// Integer
				
						writeInteger(this.dim_concurrence_id,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }


    public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("[");
		sb.append("category="+category);
		sb.append(",event_name="+event_name);
		sb.append(",event_date="+String.valueOf(event_date));
		sb.append(",city="+city);
		sb.append(",country="+country);
		sb.append(",price="+price);
		sb.append(",scd_start="+String.valueOf(scd_start));
		sb.append(",scd_end="+String.valueOf(scd_end));
		sb.append(",scraping_date="+scraping_date);
		sb.append(",concurrence_bk="+concurrence_bk);
		sb.append(",dim_concurrence_id="+String.valueOf(dim_concurrence_id));
	    sb.append("]");

	    return sb.toString();
    }

    /**
     * Compare keys
     */
    public int compareTo(outStruct other) {

		int returnValue = -1;
		
	    return returnValue;
    }


    private int checkNullsAndCompare(Object object1, Object object2) {
        int returnValue = 0;
		if (object1 instanceof Comparable && object2 instanceof Comparable) {
            returnValue = ((Comparable) object1).compareTo(object2);
        } else if (object1 != null && object2 != null) {
            returnValue = compareStrings(object1.toString(), object2.toString());
        } else if (object1 == null && object2 != null) {
            returnValue = 1;
        } else if (object1 != null && object2 == null) {
            returnValue = -1;
        } else {
            returnValue = 0;
        }

        return returnValue;
    }

    private int compareStrings(String string1, String string2) {
        return string1.compareTo(string2);
    }


}

public static class row1Struct implements routines.system.IPersistableRow<row1Struct> {
    final static byte[] commonByteArrayLock_TEST_SCD_Concurrence_DWH = new byte[0];
    static byte[] commonByteArray_TEST_SCD_Concurrence_DWH = new byte[0];

	
			    public String category;

				public String getCategory () {
					return this.category;
				}
				
			    public String event_name;

				public String getEvent_name () {
					return this.event_name;
				}
				
			    public String event_date;

				public String getEvent_date () {
					return this.event_date;
				}
				
			    public String city;

				public String getCity () {
					return this.city;
				}
				
			    public String country;

				public String getCountry () {
					return this.country;
				}
				
			    public String price;

				public String getPrice () {
					return this.price;
				}
				
			    public String scraping_date;

				public String getScraping_date () {
					return this.scraping_date;
				}
				



	private String readString(ObjectInputStream dis) throws IOException{
		String strReturn = null;
		int length = 0;
        length = dis.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			if(length > commonByteArray_TEST_SCD_Concurrence_DWH.length) {
				if(length < 1024 && commonByteArray_TEST_SCD_Concurrence_DWH.length == 0) {
   					commonByteArray_TEST_SCD_Concurrence_DWH = new byte[1024];
				} else {
   					commonByteArray_TEST_SCD_Concurrence_DWH = new byte[2 * length];
   				}
			}
			dis.readFully(commonByteArray_TEST_SCD_Concurrence_DWH, 0, length);
			strReturn = new String(commonByteArray_TEST_SCD_Concurrence_DWH, 0, length, utf8Charset);
		}
		return strReturn;
	}
	
	private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException{
		String strReturn = null;
		int length = 0;
        length = unmarshaller.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			if(length > commonByteArray_TEST_SCD_Concurrence_DWH.length) {
				if(length < 1024 && commonByteArray_TEST_SCD_Concurrence_DWH.length == 0) {
   					commonByteArray_TEST_SCD_Concurrence_DWH = new byte[1024];
				} else {
   					commonByteArray_TEST_SCD_Concurrence_DWH = new byte[2 * length];
   				}
			}
			unmarshaller.readFully(commonByteArray_TEST_SCD_Concurrence_DWH, 0, length);
			strReturn = new String(commonByteArray_TEST_SCD_Concurrence_DWH, 0, length, utf8Charset);
		}
		return strReturn;
	}

    private void writeString(String str, ObjectOutputStream dos) throws IOException{
		if(str == null) {
            dos.writeInt(-1);
		} else {
            byte[] byteArray = str.getBytes(utf8Charset);
	    	dos.writeInt(byteArray.length);
			dos.write(byteArray);
    	}
    }
    
    private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException{
		if(str == null) {
			marshaller.writeInt(-1);
		} else {
            byte[] byteArray = str.getBytes(utf8Charset);
            marshaller.writeInt(byteArray.length);
            marshaller.write(byteArray);
    	}
    }

    public void readData(ObjectInputStream dis) {

		synchronized(commonByteArrayLock_TEST_SCD_Concurrence_DWH) {

        	try {

        		int length = 0;
		
					this.category = readString(dis);
					
					this.event_name = readString(dis);
					
					this.event_date = readString(dis);
					
					this.city = readString(dis);
					
					this.country = readString(dis);
					
					this.price = readString(dis);
					
					this.scraping_date = readString(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }
    
    public void readData(org.jboss.marshalling.Unmarshaller dis) {

		synchronized(commonByteArrayLock_TEST_SCD_Concurrence_DWH) {

        	try {

        		int length = 0;
		
					this.category = readString(dis);
					
					this.event_name = readString(dis);
					
					this.event_date = readString(dis);
					
					this.city = readString(dis);
					
					this.country = readString(dis);
					
					this.price = readString(dis);
					
					this.scraping_date = readString(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }

    public void writeData(ObjectOutputStream dos) {
        try {

		
					// String
				
						writeString(this.category,dos);
					
					// String
				
						writeString(this.event_name,dos);
					
					// String
				
						writeString(this.event_date,dos);
					
					// String
				
						writeString(this.city,dos);
					
					// String
				
						writeString(this.country,dos);
					
					// String
				
						writeString(this.price,dos);
					
					// String
				
						writeString(this.scraping_date,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }
    
    public void writeData(org.jboss.marshalling.Marshaller dos) {
        try {

		
					// String
				
						writeString(this.category,dos);
					
					// String
				
						writeString(this.event_name,dos);
					
					// String
				
						writeString(this.event_date,dos);
					
					// String
				
						writeString(this.city,dos);
					
					// String
				
						writeString(this.country,dos);
					
					// String
				
						writeString(this.price,dos);
					
					// String
				
						writeString(this.scraping_date,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }


    public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("[");
		sb.append("category="+category);
		sb.append(",event_name="+event_name);
		sb.append(",event_date="+event_date);
		sb.append(",city="+city);
		sb.append(",country="+country);
		sb.append(",price="+price);
		sb.append(",scraping_date="+scraping_date);
	    sb.append("]");

	    return sb.toString();
    }

    /**
     * Compare keys
     */
    public int compareTo(row1Struct other) {

		int returnValue = -1;
		
	    return returnValue;
    }


    private int checkNullsAndCompare(Object object1, Object object2) {
        int returnValue = 0;
		if (object1 instanceof Comparable && object2 instanceof Comparable) {
            returnValue = ((Comparable) object1).compareTo(object2);
        } else if (object1 != null && object2 != null) {
            returnValue = compareStrings(object1.toString(), object2.toString());
        } else if (object1 == null && object2 != null) {
            returnValue = 1;
        } else if (object1 != null && object2 == null) {
            returnValue = -1;
        } else {
            returnValue = 0;
        }

        return returnValue;
    }

    private int compareStrings(String string1, String string2) {
        return string1.compareTo(string2);
    }


}
public void tDBInput_1Process(final java.util.Map<String, Object> globalMap) throws TalendException {
	globalMap.put("tDBInput_1_SUBPROCESS_STATE", 0);

 final boolean execStat = this.execStat;
	
		String iterateId = "";
	
	
	String currentComponent = "";
	java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

	try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { //start the resume
				globalResumeTicket = true;



		row1Struct row1 = new row1Struct();
outStruct out = new outStruct();





	
	/**
	 * [tDBSCD_1 begin ] start
	 */

	

	
		
		ok_Hash.put("tDBSCD_1", false);
		start_Hash.put("tDBSCD_1", System.currentTimeMillis());
		
	
	currentComponent="tDBSCD_1";

	
					if(execStat) {
						runStat.updateStatOnConnection(resourceMap,iterateId,0,0,"out");
					}
				
		int tos_count_tDBSCD_1 = 0;
		



        class SCDSK_tDBSCD_1 {
private int hashCode;
public boolean hashCodeDirty = true;
String concurrence_bk;
public boolean equals(Object obj) {
if (this == obj) return true;
if (obj == null) return false;
if (getClass() != obj.getClass()) return false;
final SCDSK_tDBSCD_1 other = (SCDSK_tDBSCD_1) obj;
if (this.concurrence_bk == null) {
if (other.concurrence_bk!= null)
return false;
} else if (!this.concurrence_bk.equals(other.concurrence_bk))
return false;

return true;
}
public int hashCode() {
if(hashCodeDirty) {
int prime = 31;hashCode = prime * hashCode + (concurrence_bk == null ? 0 : concurrence_bk.hashCode());
hashCodeDirty = false;
}
return hashCode;
}
}

    class SCDStruct_tDBSCD_1 {
private java.util.Date event_date;
private String event_name;
private String category;
private String country;
private String city;
private String price;
}

    int nb_line_update_tDBSCD_1 = 0;
    int nb_line_inserted_tDBSCD_1 = 0;
    int nb_line_rejected_tDBSCD_1 = 0;
    String tableName_tDBSCD_1 = null;
	String dbschema_tDBSCD_1 = null;
java.sql.Connection conn_tDBSCD_1 = null;
String dbUser_tDBSCD_1 = null;
    dbschema_tDBSCD_1 = "dbo";
    String driverClass_tDBSCD_1 = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	
    java.lang.Class.forName(driverClass_tDBSCD_1);
    String port_tDBSCD_1 = "1433";
    String dbname_tDBSCD_1 = "event_DWH" ;
    String url_tDBSCD_1 = "jdbc:sqlserver://" + "localhost" ; 
    if (!"".equals(port_tDBSCD_1)) {
    	url_tDBSCD_1 += ":" + "1433";
    }
    if (!"".equals(dbname_tDBSCD_1)) {		    
		    	url_tDBSCD_1 += ";databaseName=" + "event_DWH"; 
	
    }
    url_tDBSCD_1 += ";appName=" + projectName + ";" + "trustServerCertificate=true";
    dbUser_tDBSCD_1 = "bi";

 
	final String decryptedPassword_tDBSCD_1 = routines.system.PasswordEncryptUtil.decryptPassword("enc:routine.encryption.key.v1:20pIaAfBBxwg3VJRZeotIWzT9HTMS17chdEoEgOV");

    String dbPwd_tDBSCD_1 = decryptedPassword_tDBSCD_1;	
    conn_tDBSCD_1 = java.sql.DriverManager.getConnection(url_tDBSCD_1,dbUser_tDBSCD_1,dbPwd_tDBSCD_1);
	

    if(dbschema_tDBSCD_1 == null || dbschema_tDBSCD_1.trim().length() == 0) {
        tableName_tDBSCD_1 = "Dim_Concurrence";
    } else {
        tableName_tDBSCD_1 = dbschema_tDBSCD_1 + "].[" + "Dim_Concurrence";
    }
	org.talend.designer.components.util.mssql.MSSqlGenerateTimestampUtil mssqlGTU_tDBSCD_1 = org.talend.designer.components.util.mssql.MSSqlUtilFactory.getMSSqlGenerateTimestampUtil();	
    String tmpValue_tDBSCD_1 = null;    
        String search_tDBSCD_1 = "SELECT [concurrence_bk], [event_date], [event_name], [category], [country], [city], [price] FROM [" + tableName_tDBSCD_1 + "] WHERE [scd_end] IS NULL";
        java.sql.Statement statement_tDBSCD_1 = conn_tDBSCD_1.createStatement();
        java.sql.ResultSet resultSet_tDBSCD_1 = statement_tDBSCD_1.executeQuery(search_tDBSCD_1);
        java.util.Map<SCDSK_tDBSCD_1, SCDStruct_tDBSCD_1> cache_tDBSCD_1 = new java.util.HashMap<SCDSK_tDBSCD_1, SCDStruct_tDBSCD_1>();
        while(resultSet_tDBSCD_1.next()) {
            SCDSK_tDBSCD_1 sk_tDBSCD_1 = new SCDSK_tDBSCD_1();
            SCDStruct_tDBSCD_1 row_tDBSCD_1 = new SCDStruct_tDBSCD_1();
                    if(resultSet_tDBSCD_1.getObject(1) != null) {
                        sk_tDBSCD_1.concurrence_bk = resultSet_tDBSCD_1.getString(1);
                    }
                     row_tDBSCD_1.event_date = mssqlGTU_tDBSCD_1.getDate(resultSet_tDBSCD_1.getMetaData(), resultSet_tDBSCD_1, 2);
                    if(resultSet_tDBSCD_1.getObject(3) != null) {
                        row_tDBSCD_1.event_name = resultSet_tDBSCD_1.getString(3);
                    }
                    if(resultSet_tDBSCD_1.getObject(4) != null) {
                        row_tDBSCD_1.category = resultSet_tDBSCD_1.getString(4);
                    }
                    if(resultSet_tDBSCD_1.getObject(5) != null) {
                        row_tDBSCD_1.country = resultSet_tDBSCD_1.getString(5);
                    }
                    if(resultSet_tDBSCD_1.getObject(6) != null) {
                        row_tDBSCD_1.city = resultSet_tDBSCD_1.getString(6);
                    }
                    if(resultSet_tDBSCD_1.getObject(7) != null) {
                        row_tDBSCD_1.price = resultSet_tDBSCD_1.getString(7);
                    }
            cache_tDBSCD_1.put(sk_tDBSCD_1, row_tDBSCD_1);
        }
        resultSet_tDBSCD_1.close();
        statement_tDBSCD_1.close();
    String insertionSQL_tDBSCD_1 = "INSERT INTO [" + tableName_tDBSCD_1 + "]([concurrence_bk], [event_date], [event_name], [category], [country], [city], [price], [scd_start], [scd_end]) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
    java.sql.PreparedStatement insertionStatement_tDBSCD_1 = conn_tDBSCD_1.prepareStatement(insertionSQL_tDBSCD_1);
            insertionStatement_tDBSCD_1.setTimestamp(8, new java.sql.Timestamp(start_Hash.get("tDBSCD_1")));
            insertionStatement_tDBSCD_1.setNull(9, java.sql.Types.DATE);
        String updateSQLForType1_tDBSCD_1 = "UPDATE [" + tableName_tDBSCD_1 + "] SET [event_date] = ?, [event_name] = ?, [category] = ?, [country] = ?, [city] = ? WHERE [concurrence_bk] = ?";
        java.sql.PreparedStatement updateForType1_tDBSCD_1 = conn_tDBSCD_1.prepareStatement(updateSQLForType1_tDBSCD_1);        
        String updateSQLForType2_tDBSCD_1 = "UPDATE [" + tableName_tDBSCD_1 + "] SET [scd_end] = ? WHERE [concurrence_bk] = ? AND [scd_end] IS NULL";
        java.sql.PreparedStatement updateForType2_tDBSCD_1 = conn_tDBSCD_1.prepareStatement(updateSQLForType2_tDBSCD_1);
            updateForType2_tDBSCD_1.setTimestamp(1, new java.sql.Timestamp(start_Hash.get("tDBSCD_1")));
    
        SCDSK_tDBSCD_1 lookUpKey_tDBSCD_1 = null;        
    SCDStruct_tDBSCD_1 lookUpValue_tDBSCD_1 = null;

 



/**
 * [tDBSCD_1 begin ] stop
 */



	
	/**
	 * [tMap_1 begin ] start
	 */

	

	
		
		ok_Hash.put("tMap_1", false);
		start_Hash.put("tMap_1", System.currentTimeMillis());
		
	
	currentComponent="tMap_1";

	
					if(execStat) {
						runStat.updateStatOnConnection(resourceMap,iterateId,0,0,"row1");
					}
				
		int tos_count_tMap_1 = 0;
		




// ###############################
// # Lookup's keys initialization
// ###############################        

// ###############################
// # Vars initialization
class  Var__tMap_1__Struct  {
}
Var__tMap_1__Struct Var__tMap_1 = new Var__tMap_1__Struct();
// ###############################

// ###############################
// # Outputs initialization
outStruct out_tmp = new outStruct();
// ###############################

        
        



        









 



/**
 * [tMap_1 begin ] stop
 */



	
	/**
	 * [tDBInput_1 begin ] start
	 */

	

	
		
		ok_Hash.put("tDBInput_1", false);
		start_Hash.put("tDBInput_1", System.currentTimeMillis());
		
	
	currentComponent="tDBInput_1";

	
		int tos_count_tDBInput_1 = 0;
		
	
    
	
			org.talend.designer.components.util.mssql.MSSqlGenerateTimestampUtil mssqlGTU_tDBInput_1 = org.talend.designer.components.util.mssql.MSSqlUtilFactory.getMSSqlGenerateTimestampUtil();
			
			java.util.List<String> talendToDBList_tDBInput_1 = new java.util.ArrayList();
			String[] talendToDBArray_tDBInput_1  = new String[]{"FLOAT","NUMERIC","NUMERIC IDENTITY","DECIMAL","DECIMAL IDENTITY","REAL"}; 
			java.util.Collections.addAll(talendToDBList_tDBInput_1, talendToDBArray_tDBInput_1); 
		    int nb_line_tDBInput_1 = 0;
		    java.sql.Connection conn_tDBInput_1 = null;
				String driverClass_tDBInput_1 = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
			    java.lang.Class jdbcclazz_tDBInput_1 = java.lang.Class.forName(driverClass_tDBInput_1);
				String dbUser_tDBInput_1 = "event";
				
				 
	final String decryptedPassword_tDBInput_1 = routines.system.PasswordEncryptUtil.decryptPassword("enc:routine.encryption.key.v1:sCgNYGXPI32LemXcJDPuMBj+BocEJtOcUqFQ/giDwQ==");
				
				String dbPwd_tDBInput_1 = decryptedPassword_tDBInput_1;
				
		    String port_tDBInput_1 = "1433";
		    String dbname_tDBInput_1 = "Event_SA" ;		    
		    String url_tDBInput_1 = "jdbc:sqlserver://" + "localhost" ;
		    if (!"".equals(port_tDBInput_1)) {
		    	url_tDBInput_1 += ":" + "1433";
		    }
		    if (!"".equals(dbname_tDBInput_1)) {		    
		    	url_tDBInput_1 += ";databaseName=" + "Event_SA"; 
		    }
		    url_tDBInput_1 += ";appName=" + projectName + ";" + "trustServerCertificate=true";
		    String dbschema_tDBInput_1 = "dbo";
				
				conn_tDBInput_1 = java.sql.DriverManager.getConnection(url_tDBInput_1,dbUser_tDBInput_1,dbPwd_tDBInput_1);
		        
		    
			java.sql.Statement stmt_tDBInput_1 = conn_tDBInput_1.createStatement();

		    String dbquery_tDBInput_1 = "SELECT category,\n       event_name,\n       event_date,\n       city,\n       country,\n       price,\n       scraping"
+"_date\nFROM dbo.events_global;"
;
		    

            	globalMap.put("tDBInput_1_QUERY",dbquery_tDBInput_1);
		    java.sql.ResultSet rs_tDBInput_1 = null;

		    try {
		    	rs_tDBInput_1 = stmt_tDBInput_1.executeQuery(dbquery_tDBInput_1);
		    	java.sql.ResultSetMetaData rsmd_tDBInput_1 = rs_tDBInput_1.getMetaData();
		    	int colQtyInRs_tDBInput_1 = rsmd_tDBInput_1.getColumnCount();

		    String tmpContent_tDBInput_1 = null;
		    
		    
		    while (rs_tDBInput_1.next()) {
		        nb_line_tDBInput_1++;
		        
							if(colQtyInRs_tDBInput_1 < 1) {
								row1.category = null;
							} else {
	                         		
           		tmpContent_tDBInput_1 = rs_tDBInput_1.getString(1);
            if(tmpContent_tDBInput_1 != null) {
            	if (talendToDBList_tDBInput_1 .contains(rsmd_tDBInput_1.getColumnTypeName(1).toUpperCase(java.util.Locale.ENGLISH))) {
            		row1.category = FormatterUtils.formatUnwithE(tmpContent_tDBInput_1);
            	} else {
                	row1.category = tmpContent_tDBInput_1;
                }
            } else {
                row1.category = null;
            }
		                    }
							if(colQtyInRs_tDBInput_1 < 2) {
								row1.event_name = null;
							} else {
	                         		
           		tmpContent_tDBInput_1 = rs_tDBInput_1.getString(2);
            if(tmpContent_tDBInput_1 != null) {
            	if (talendToDBList_tDBInput_1 .contains(rsmd_tDBInput_1.getColumnTypeName(2).toUpperCase(java.util.Locale.ENGLISH))) {
            		row1.event_name = FormatterUtils.formatUnwithE(tmpContent_tDBInput_1);
            	} else {
                	row1.event_name = tmpContent_tDBInput_1;
                }
            } else {
                row1.event_name = null;
            }
		                    }
							if(colQtyInRs_tDBInput_1 < 3) {
								row1.event_date = null;
							} else {
	                         		
           		tmpContent_tDBInput_1 = rs_tDBInput_1.getString(3);
            if(tmpContent_tDBInput_1 != null) {
            	if (talendToDBList_tDBInput_1 .contains(rsmd_tDBInput_1.getColumnTypeName(3).toUpperCase(java.util.Locale.ENGLISH))) {
            		row1.event_date = FormatterUtils.formatUnwithE(tmpContent_tDBInput_1);
            	} else {
                	row1.event_date = tmpContent_tDBInput_1;
                }
            } else {
                row1.event_date = null;
            }
		                    }
							if(colQtyInRs_tDBInput_1 < 4) {
								row1.city = null;
							} else {
	                         		
           		tmpContent_tDBInput_1 = rs_tDBInput_1.getString(4);
            if(tmpContent_tDBInput_1 != null) {
            	if (talendToDBList_tDBInput_1 .contains(rsmd_tDBInput_1.getColumnTypeName(4).toUpperCase(java.util.Locale.ENGLISH))) {
            		row1.city = FormatterUtils.formatUnwithE(tmpContent_tDBInput_1);
            	} else {
                	row1.city = tmpContent_tDBInput_1;
                }
            } else {
                row1.city = null;
            }
		                    }
							if(colQtyInRs_tDBInput_1 < 5) {
								row1.country = null;
							} else {
	                         		
           		tmpContent_tDBInput_1 = rs_tDBInput_1.getString(5);
            if(tmpContent_tDBInput_1 != null) {
            	if (talendToDBList_tDBInput_1 .contains(rsmd_tDBInput_1.getColumnTypeName(5).toUpperCase(java.util.Locale.ENGLISH))) {
            		row1.country = FormatterUtils.formatUnwithE(tmpContent_tDBInput_1);
            	} else {
                	row1.country = tmpContent_tDBInput_1;
                }
            } else {
                row1.country = null;
            }
		                    }
							if(colQtyInRs_tDBInput_1 < 6) {
								row1.price = null;
							} else {
	                         		
           		tmpContent_tDBInput_1 = rs_tDBInput_1.getString(6);
            if(tmpContent_tDBInput_1 != null) {
            	if (talendToDBList_tDBInput_1 .contains(rsmd_tDBInput_1.getColumnTypeName(6).toUpperCase(java.util.Locale.ENGLISH))) {
            		row1.price = FormatterUtils.formatUnwithE(tmpContent_tDBInput_1);
            	} else {
                	row1.price = tmpContent_tDBInput_1;
                }
            } else {
                row1.price = null;
            }
		                    }
							if(colQtyInRs_tDBInput_1 < 7) {
								row1.scraping_date = null;
							} else {
	                         		
           		tmpContent_tDBInput_1 = rs_tDBInput_1.getString(7);
            if(tmpContent_tDBInput_1 != null) {
            	if (talendToDBList_tDBInput_1 .contains(rsmd_tDBInput_1.getColumnTypeName(7).toUpperCase(java.util.Locale.ENGLISH))) {
            		row1.scraping_date = FormatterUtils.formatUnwithE(tmpContent_tDBInput_1);
            	} else {
                	row1.scraping_date = tmpContent_tDBInput_1;
                }
            } else {
                row1.scraping_date = null;
            }
		                    }
					





 



/**
 * [tDBInput_1 begin ] stop
 */
	
	/**
	 * [tDBInput_1 main ] start
	 */

	

	
	
	currentComponent="tDBInput_1";

	

 


	tos_count_tDBInput_1++;

/**
 * [tDBInput_1 main ] stop
 */
	
	/**
	 * [tDBInput_1 process_data_begin ] start
	 */

	

	
	
	currentComponent="tDBInput_1";

	

 



/**
 * [tDBInput_1 process_data_begin ] stop
 */

	
	/**
	 * [tMap_1 main ] start
	 */

	

	
	
	currentComponent="tMap_1";

	
					if(execStat){
						runStat.updateStatOnConnection(iterateId,1,1
						
							,"row1"
						
						);
					}
					

		
		
		boolean hasCasePrimitiveKeyWithNull_tMap_1 = false;
		

        // ###############################
        // # Input tables (lookups)
		  boolean rejectedInnerJoin_tMap_1 = false;
		  boolean mainRowRejected_tMap_1 = false;
            				    								  
		// ###############################
        { // start of Var scope
        
	        // ###############################
        	// # Vars tables
        
Var__tMap_1__Struct Var = Var__tMap_1;// ###############################
        // ###############################
        // # Output tables

out = null;


// # Output table : 'out'
out_tmp.category = row1.category ;
out_tmp.event_name = row1.event_name ;
out_tmp.event_date = row1.event_date == null || row1.event_date.trim().isEmpty() ? null :
TalendDate.parseDate("yyyy-MM-dd", row1.event_date) ;
out_tmp.city = row1.city ;
out_tmp.country = row1.country ;
out_tmp.price = row1.price ;
out_tmp.scd_start = null;
out_tmp.scd_end = null;
out_tmp.scraping_date = row1.scraping_date ;
out_tmp.concurrence_bk = StringHandling.TRIM(row1.event_name) 
+ "_" + row1.event_date 
+ "_" + StringHandling.TRIM(row1.city) 
+ "_" + StringHandling.TRIM(row1.country) ;
out_tmp.dim_concurrence_id = Numeric.sequence("s1", 1, 1) ;
out = out_tmp;
// ###############################

} // end of Var scope

rejectedInnerJoin_tMap_1 = false;










 


	tos_count_tMap_1++;

/**
 * [tMap_1 main ] stop
 */
	
	/**
	 * [tMap_1 process_data_begin ] start
	 */

	

	
	
	currentComponent="tMap_1";

	

 



/**
 * [tMap_1 process_data_begin ] stop
 */
// Start of branch "out"
if(out != null) { 



	
	/**
	 * [tDBSCD_1 main ] start
	 */

	

	
	
	currentComponent="tDBSCD_1";

	
					if(execStat){
						runStat.updateStatOnConnection(iterateId,1,1
						
							,"out"
						
						);
					}
					

	try {
        lookUpKey_tDBSCD_1 = new SCDSK_tDBSCD_1();
            lookUpKey_tDBSCD_1.concurrence_bk = out.concurrence_bk;
        lookUpKey_tDBSCD_1.hashCodeDirty = true;
        lookUpValue_tDBSCD_1 = cache_tDBSCD_1.get(lookUpKey_tDBSCD_1);    
    if(lookUpValue_tDBSCD_1 == null) {
            lookUpValue_tDBSCD_1 = new SCDStruct_tDBSCD_1();
        
                    if(out.concurrence_bk == null) {
insertionStatement_tDBSCD_1.setNull(1, java.sql.Types.VARCHAR);
} else {
insertionStatement_tDBSCD_1.setString(1, out.concurrence_bk);
}

                    if(out.event_date == null) {
insertionStatement_tDBSCD_1.setNull(2, java.sql.Types.DATE);
} else {
if(out.event_date != null) {
insertionStatement_tDBSCD_1.setTimestamp(2, new java.sql.Timestamp(out.event_date.getTime()));
} else {
insertionStatement_tDBSCD_1.setNull(2, java.sql.Types.DATE);
}
}

                    if(out.event_name == null) {
insertionStatement_tDBSCD_1.setNull(3, java.sql.Types.VARCHAR);
} else {
insertionStatement_tDBSCD_1.setString(3, out.event_name);
}

                    if(out.category == null) {
insertionStatement_tDBSCD_1.setNull(4, java.sql.Types.VARCHAR);
} else {
insertionStatement_tDBSCD_1.setString(4, out.category);
}

                    if(out.country == null) {
insertionStatement_tDBSCD_1.setNull(5, java.sql.Types.VARCHAR);
} else {
insertionStatement_tDBSCD_1.setString(5, out.country);
}

                    if(out.city == null) {
insertionStatement_tDBSCD_1.setNull(6, java.sql.Types.VARCHAR);
} else {
insertionStatement_tDBSCD_1.setString(6, out.city);
}

                    if(out.price == null) {
insertionStatement_tDBSCD_1.setNull(7, java.sql.Types.VARCHAR);
} else {
insertionStatement_tDBSCD_1.setString(7, out.price);
}

        nb_line_inserted_tDBSCD_1 += insertionStatement_tDBSCD_1.executeUpdate();
    } else {
            if((lookUpValue_tDBSCD_1.event_date == null && out.event_date!= null) || (lookUpValue_tDBSCD_1.event_date != null && !lookUpValue_tDBSCD_1.event_date.equals(out.event_date)) || (lookUpValue_tDBSCD_1.event_name == null && out.event_name!= null) || (lookUpValue_tDBSCD_1.event_name != null && !lookUpValue_tDBSCD_1.event_name.equals(out.event_name)) || (lookUpValue_tDBSCD_1.category == null && out.category!= null) || (lookUpValue_tDBSCD_1.category != null && !lookUpValue_tDBSCD_1.category.equals(out.category)) || (lookUpValue_tDBSCD_1.country == null && out.country!= null) || (lookUpValue_tDBSCD_1.country != null && !lookUpValue_tDBSCD_1.country.equals(out.country)) || (lookUpValue_tDBSCD_1.city == null && out.city!= null) || (lookUpValue_tDBSCD_1.city != null && !lookUpValue_tDBSCD_1.city.equals(out.city))) {
                    if(out.event_date == null) {
updateForType1_tDBSCD_1.setNull(1, java.sql.Types.DATE);
} else {
if(out.event_date != null) {
updateForType1_tDBSCD_1.setTimestamp(1, new java.sql.Timestamp(out.event_date.getTime()));
} else {
updateForType1_tDBSCD_1.setNull(1, java.sql.Types.DATE);
}
}

                    if(out.event_name == null) {
updateForType1_tDBSCD_1.setNull(2, java.sql.Types.VARCHAR);
} else {
updateForType1_tDBSCD_1.setString(2, out.event_name);
}

                    if(out.category == null) {
updateForType1_tDBSCD_1.setNull(3, java.sql.Types.VARCHAR);
} else {
updateForType1_tDBSCD_1.setString(3, out.category);
}

                    if(out.country == null) {
updateForType1_tDBSCD_1.setNull(4, java.sql.Types.VARCHAR);
} else {
updateForType1_tDBSCD_1.setString(4, out.country);
}

                    if(out.city == null) {
updateForType1_tDBSCD_1.setNull(5, java.sql.Types.VARCHAR);
} else {
updateForType1_tDBSCD_1.setString(5, out.city);
}

                    if(out.concurrence_bk == null) {
updateForType1_tDBSCD_1.setNull(6, java.sql.Types.VARCHAR);
} else {
updateForType1_tDBSCD_1.setString(6, out.concurrence_bk);
}

                nb_line_update_tDBSCD_1 += updateForType1_tDBSCD_1.executeUpdate();
            }
            if((lookUpValue_tDBSCD_1.price == null && out.price!= null) || (lookUpValue_tDBSCD_1.price != null && !lookUpValue_tDBSCD_1.price.equals(out.price))) {
                    if(out.concurrence_bk == null) {
updateForType2_tDBSCD_1.setNull(2, java.sql.Types.VARCHAR);
} else {
updateForType2_tDBSCD_1.setString(2, out.concurrence_bk);
}

                nb_line_update_tDBSCD_1 += updateForType2_tDBSCD_1.executeUpdate();
                            if(out.concurrence_bk == null) {
insertionStatement_tDBSCD_1.setNull(1, java.sql.Types.VARCHAR);
} else {
insertionStatement_tDBSCD_1.setString(1, out.concurrence_bk);
}

                            if(out.event_date == null) {
insertionStatement_tDBSCD_1.setNull(2, java.sql.Types.DATE);
} else {
if(out.event_date != null) {
insertionStatement_tDBSCD_1.setTimestamp(2, new java.sql.Timestamp(out.event_date.getTime()));
} else {
insertionStatement_tDBSCD_1.setNull(2, java.sql.Types.DATE);
}
}

                            if(out.event_name == null) {
insertionStatement_tDBSCD_1.setNull(3, java.sql.Types.VARCHAR);
} else {
insertionStatement_tDBSCD_1.setString(3, out.event_name);
}

                            if(out.category == null) {
insertionStatement_tDBSCD_1.setNull(4, java.sql.Types.VARCHAR);
} else {
insertionStatement_tDBSCD_1.setString(4, out.category);
}

                            if(out.country == null) {
insertionStatement_tDBSCD_1.setNull(5, java.sql.Types.VARCHAR);
} else {
insertionStatement_tDBSCD_1.setString(5, out.country);
}

                            if(out.city == null) {
insertionStatement_tDBSCD_1.setNull(6, java.sql.Types.VARCHAR);
} else {
insertionStatement_tDBSCD_1.setString(6, out.city);
}

                            if(out.price == null) {
insertionStatement_tDBSCD_1.setNull(7, java.sql.Types.VARCHAR);
} else {
insertionStatement_tDBSCD_1.setString(7, out.price);
}

                nb_line_inserted_tDBSCD_1 += insertionStatement_tDBSCD_1.executeUpdate();
            }
    }
    
	} catch (java.lang.Exception e) {//catch
globalMap.put("tDBSCD_1_ERROR_MESSAGE",e.getMessage());
  		
                System.err.print(e.getMessage());
	}//end catch
	
                lookUpValue_tDBSCD_1.event_date = out.event_date;
                lookUpValue_tDBSCD_1.event_name = out.event_name;
                lookUpValue_tDBSCD_1.category = out.category;
                lookUpValue_tDBSCD_1.country = out.country;
                lookUpValue_tDBSCD_1.city = out.city;
                lookUpValue_tDBSCD_1.price = out.price;
        cache_tDBSCD_1.put(lookUpKey_tDBSCD_1, lookUpValue_tDBSCD_1);


 


	tos_count_tDBSCD_1++;

/**
 * [tDBSCD_1 main ] stop
 */
	
	/**
	 * [tDBSCD_1 process_data_begin ] start
	 */

	

	
	
	currentComponent="tDBSCD_1";

	

 



/**
 * [tDBSCD_1 process_data_begin ] stop
 */
	
	/**
	 * [tDBSCD_1 process_data_end ] start
	 */

	

	
	
	currentComponent="tDBSCD_1";

	

 



/**
 * [tDBSCD_1 process_data_end ] stop
 */

} // End of branch "out"




	
	/**
	 * [tMap_1 process_data_end ] start
	 */

	

	
	
	currentComponent="tMap_1";

	

 



/**
 * [tMap_1 process_data_end ] stop
 */



	
	/**
	 * [tDBInput_1 process_data_end ] start
	 */

	

	
	
	currentComponent="tDBInput_1";

	

 



/**
 * [tDBInput_1 process_data_end ] stop
 */
	
	/**
	 * [tDBInput_1 end ] start
	 */

	

	
	
	currentComponent="tDBInput_1";

	

	}
}finally{
	if (rs_tDBInput_1 != null) {
		rs_tDBInput_1.close();
	}
	if (stmt_tDBInput_1 != null) {
		stmt_tDBInput_1.close();
	}
		if(conn_tDBInput_1 != null && !conn_tDBInput_1.isClosed()) {
			
			conn_tDBInput_1.close();
			
			if("com.mysql.cj.jdbc.Driver".equals((String)globalMap.get("driverClass_"))
			    && routines.system.BundleUtils.inOSGi()) {
			        Class.forName("com.mysql.cj.jdbc.AbandonedConnectionCleanupThread").
			            getMethod("checkedShutdown").invoke(null, (Object[]) null);
			}
			
		}
}
globalMap.put("tDBInput_1_NB_LINE",nb_line_tDBInput_1);

 

ok_Hash.put("tDBInput_1", true);
end_Hash.put("tDBInput_1", System.currentTimeMillis());




/**
 * [tDBInput_1 end ] stop
 */

	
	/**
	 * [tMap_1 end ] start
	 */

	

	
	
	currentComponent="tMap_1";

	


// ###############################
// # Lookup hashes releasing
// ###############################      





				if(execStat){
			  		runStat.updateStat(resourceMap,iterateId,2,0,"row1");
			  	}
			  	
 

ok_Hash.put("tMap_1", true);
end_Hash.put("tMap_1", System.currentTimeMillis());




/**
 * [tMap_1 end ] stop
 */

	
	/**
	 * [tDBSCD_1 end ] start
	 */

	

	
	
	currentComponent="tDBSCD_1";

	

    insertionStatement_tDBSCD_1.close();
        updateForType1_tDBSCD_1.close();
        updateForType2_tDBSCD_1.close();
    
    if(conn_tDBSCD_1 != null && !conn_tDBSCD_1.isClosed()) {
        conn_tDBSCD_1.close();
    }    
    
    globalMap.put("tDBSCD_1_NB_LINE_UPDATED", nb_line_update_tDBSCD_1);
    globalMap.put("tDBSCD_1_NB_LINE_INSERTED", nb_line_inserted_tDBSCD_1);
    globalMap.put("tDBSCD_1_NB_LINE_REJECTED",nb_line_rejected_tDBSCD_1);

				if(execStat){
			  		runStat.updateStat(resourceMap,iterateId,2,0,"out");
			  	}
			  	
 

ok_Hash.put("tDBSCD_1", true);
end_Hash.put("tDBSCD_1", System.currentTimeMillis());




/**
 * [tDBSCD_1 end ] stop
 */






				}//end the resume

				



	
			}catch(java.lang.Exception e){	
				
				TalendException te = new TalendException(e, currentComponent, globalMap);
				
				throw te;
			}catch(java.lang.Error error){	
				
					runStat.stopThreadStat();
				
				throw error;
			}finally{
				
				try{
					
	
	/**
	 * [tDBInput_1 finally ] start
	 */

	

	
	
	currentComponent="tDBInput_1";

	

 



/**
 * [tDBInput_1 finally ] stop
 */

	
	/**
	 * [tMap_1 finally ] start
	 */

	

	
	
	currentComponent="tMap_1";

	

 



/**
 * [tMap_1 finally ] stop
 */

	
	/**
	 * [tDBSCD_1 finally ] start
	 */

	

	
	
	currentComponent="tDBSCD_1";

	

 



/**
 * [tDBSCD_1 finally ] stop
 */






				}catch(java.lang.Exception e){	
					//ignore
				}catch(java.lang.Error error){
					//ignore
				}
				resourceMap = null;
			}
		

		globalMap.put("tDBInput_1_SUBPROCESS_STATE", 1);
	}
	
    public String resuming_logs_dir_path = null;
    public String resuming_checkpoint_path = null;
    public String parent_part_launcher = null;
    private String resumeEntryMethodName = null;
    private boolean globalResumeTicket = false;

    public boolean watch = false;
    // portStats is null, it means don't execute the statistics
    public Integer portStats = null;
    public int portTraces = 4334;
    public String clientHost;
    public String defaultClientHost = "localhost";
    public String contextStr = "Default";
    public boolean isDefaultContext = true;
    public String pid = "0";
    public String rootPid = null;
    public String fatherPid = null;
    public String fatherNode = null;
    public long startTime = 0;
    public boolean isChildJob = false;
    public String log4jLevel = "";
    
    private boolean enableLogStash;

    private boolean execStat = true;

    private ThreadLocal<java.util.Map<String, String>> threadLocal = new ThreadLocal<java.util.Map<String, String>>() {
        protected java.util.Map<String, String> initialValue() {
            java.util.Map<String,String> threadRunResultMap = new java.util.HashMap<String, String>();
            threadRunResultMap.put("errorCode", null);
            threadRunResultMap.put("status", "");
            return threadRunResultMap;
        };
    };


    protected PropertiesWithType context_param = new PropertiesWithType();
    public java.util.Map<String, Object> parentContextMap = new java.util.HashMap<String, Object>();

    public String status= "";
    

    public static void main(String[] args){
        final SCD_Concurrence_DWH SCD_Concurrence_DWHClass = new SCD_Concurrence_DWH();

        int exitCode = SCD_Concurrence_DWHClass.runJobInTOS(args);

        System.exit(exitCode);
    }


    public String[][] runJob(String[] args) {

        int exitCode = runJobInTOS(args);
        String[][] bufferValue = new String[][] { { Integer.toString(exitCode) } };

        return bufferValue;
    }

    public boolean hastBufferOutputComponent() {
		boolean hastBufferOutput = false;
    	
        return hastBufferOutput;
    }

    public int runJobInTOS(String[] args) {
	   	// reset status
	   	status = "";
	   	
        String lastStr = "";
        for (String arg : args) {
            if (arg.equalsIgnoreCase("--context_param")) {
                lastStr = arg;
            } else if (lastStr.equals("")) {
                evalParam(arg);
            } else {
                evalParam(lastStr + " " + arg);
                lastStr = "";
            }
        }
        enableLogStash = "true".equalsIgnoreCase(System.getProperty("audit.enabled"));

    	
    	

        if(clientHost == null) {
            clientHost = defaultClientHost;
        }

        if(pid == null || "0".equals(pid)) {
            pid = TalendString.getAsciiRandomString(6);
        }

        if (rootPid==null) {
            rootPid = pid;
        }
        if (fatherPid==null) {
            fatherPid = pid;
        }else{
            isChildJob = true;
        }

        if (portStats != null) {
            // portStats = -1; //for testing
            if (portStats < 0 || portStats > 65535) {
                // issue:10869, the portStats is invalid, so this client socket can't open
                System.err.println("The statistics socket port " + portStats + " is invalid.");
                execStat = false;
            }
        } else {
            execStat = false;
        }
        boolean inOSGi = routines.system.BundleUtils.inOSGi();

        if (inOSGi) {
            java.util.Dictionary<String, Object> jobProperties = routines.system.BundleUtils.getJobProperties(jobName);

            if (jobProperties != null && jobProperties.get("context") != null) {
                contextStr = (String)jobProperties.get("context");
            }
        }

        try {
            //call job/subjob with an existing context, like: --context=production. if without this parameter, there will use the default context instead.
            java.io.InputStream inContext = SCD_Concurrence_DWH.class.getClassLoader().getResourceAsStream("test/scd_concurrence_dwh_0_1/contexts/" + contextStr + ".properties");
            if (inContext == null) {
                inContext = SCD_Concurrence_DWH.class.getClassLoader().getResourceAsStream("config/contexts/" + contextStr + ".properties");
            }
            if (inContext != null) {
                try {
                    //defaultProps is in order to keep the original context value
                    if(context != null && context.isEmpty()) {
	                defaultProps.load(inContext);
	                context = new ContextProperties(defaultProps);
                    }
                } finally {
                    inContext.close();
                }
            } else if (!isDefaultContext) {
                //print info and job continue to run, for case: context_param is not empty.
                System.err.println("Could not find the context " + contextStr);
            }

            if(!context_param.isEmpty()) {
                context.putAll(context_param);
				//set types for params from parentJobs
				for (Object key: context_param.keySet()){
					String context_key = key.toString();
					String context_type = context_param.getContextType(context_key);
					context.setContextType(context_key, context_type);

				}
            }
            class ContextProcessing {
                private void processContext_0() {
                } 
                public void processAllContext() {
                        processContext_0();
                }
            }

            new ContextProcessing().processAllContext();
        } catch (java.io.IOException ie) {
            System.err.println("Could not load context "+contextStr);
            ie.printStackTrace();
        }

        // get context value from parent directly
        if (parentContextMap != null && !parentContextMap.isEmpty()) {
        }

        //Resume: init the resumeUtil
        resumeEntryMethodName = ResumeUtil.getResumeEntryMethodName(resuming_checkpoint_path);
        resumeUtil = new ResumeUtil(resuming_logs_dir_path, isChildJob, rootPid);
        resumeUtil.initCommonInfo(pid, rootPid, fatherPid, projectName, jobName, contextStr, jobVersion);

		List<String> parametersToEncrypt = new java.util.ArrayList<String>();
        //Resume: jobStart
        resumeUtil.addLog("JOB_STARTED", "JOB:" + jobName, parent_part_launcher, Thread.currentThread().getId() + "", "","","","",resumeUtil.convertToJsonText(context,parametersToEncrypt));

if(execStat) {
    try {
        runStat.openSocket(!isChildJob);
        runStat.setAllPID(rootPid, fatherPid, pid, jobName);
        runStat.startThreadStat(clientHost, portStats);
        runStat.updateStatOnJob(RunStat.JOBSTART, fatherNode);
    } catch (java.io.IOException ioException) {
        ioException.printStackTrace();
    }
}



	
	    java.util.concurrent.ConcurrentHashMap<Object, Object> concurrentHashMap = new java.util.concurrent.ConcurrentHashMap<Object, Object>();
	    globalMap.put("concurrentHashMap", concurrentHashMap);
	

    long startUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    long endUsedMemory = 0;
    long end = 0;

    startTime = System.currentTimeMillis();


this.globalResumeTicket = true;//to run tPreJob





this.globalResumeTicket = false;//to run others jobs

try {
errorCode = null;tDBInput_1Process(globalMap);
if(!"failure".equals(status)) { status = "end"; }
}catch (TalendException e_tDBInput_1) {
globalMap.put("tDBInput_1_SUBPROCESS_STATE", -1);

e_tDBInput_1.printStackTrace();

}

this.globalResumeTicket = true;//to run tPostJob




        end = System.currentTimeMillis();

        if (watch) {
            System.out.println((end-startTime)+" milliseconds");
        }

        endUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        if (false) {
            System.out.println((endUsedMemory - startUsedMemory) + " bytes memory increase when running : SCD_Concurrence_DWH");
        }



if (execStat) {
    runStat.updateStatOnJob(RunStat.JOBEND, fatherNode);
    runStat.stopThreadStat();
}
    int returnCode = 0;


    if(errorCode == null) {
         returnCode = status != null && status.equals("failure") ? 1 : 0;
    } else {
         returnCode = errorCode.intValue();
    }
    resumeUtil.addLog("JOB_ENDED", "JOB:" + jobName, parent_part_launcher, Thread.currentThread().getId() + "", "","" + returnCode,"","","");

    return returnCode;

  }

    // only for OSGi env
    public void destroy() {


    }














    private java.util.Map<String, Object> getSharedConnections4REST() {
        java.util.Map<String, Object> connections = new java.util.HashMap<String, Object>();






        return connections;
    }

    private void evalParam(String arg) {
        if (arg.startsWith("--resuming_logs_dir_path")) {
            resuming_logs_dir_path = arg.substring(25);
        } else if (arg.startsWith("--resuming_checkpoint_path")) {
            resuming_checkpoint_path = arg.substring(27);
        } else if (arg.startsWith("--parent_part_launcher")) {
            parent_part_launcher = arg.substring(23);
        } else if (arg.startsWith("--watch")) {
            watch = true;
        } else if (arg.startsWith("--stat_port=")) {
            String portStatsStr = arg.substring(12);
            if (portStatsStr != null && !portStatsStr.equals("null")) {
                portStats = Integer.parseInt(portStatsStr);
            }
        } else if (arg.startsWith("--trace_port=")) {
            portTraces = Integer.parseInt(arg.substring(13));
        } else if (arg.startsWith("--client_host=")) {
            clientHost = arg.substring(14);
        } else if (arg.startsWith("--context=")) {
            contextStr = arg.substring(10);
            isDefaultContext = false;
        } else if (arg.startsWith("--father_pid=")) {
            fatherPid = arg.substring(13);
        } else if (arg.startsWith("--root_pid=")) {
            rootPid = arg.substring(11);
        } else if (arg.startsWith("--father_node=")) {
            fatherNode = arg.substring(14);
        } else if (arg.startsWith("--pid=")) {
            pid = arg.substring(6);
        } else if (arg.startsWith("--context_type")) {
            String keyValue = arg.substring(15);
			int index = -1;
            if (keyValue != null && (index = keyValue.indexOf('=')) > -1) {
                if (fatherPid==null) {
                    context_param.setContextType(keyValue.substring(0, index), replaceEscapeChars(keyValue.substring(index + 1)));
                } else { // the subjob won't escape the especial chars
                    context_param.setContextType(keyValue.substring(0, index), keyValue.substring(index + 1) );
                }

            }

		} else if (arg.startsWith("--context_param")) {
            String keyValue = arg.substring(16);
            int index = -1;
            if (keyValue != null && (index = keyValue.indexOf('=')) > -1) {
                if (fatherPid==null) {
                    context_param.put(keyValue.substring(0, index), replaceEscapeChars(keyValue.substring(index + 1)));
                } else { // the subjob won't escape the especial chars
                    context_param.put(keyValue.substring(0, index), keyValue.substring(index + 1) );
                }
            }
        } else if (arg.startsWith("--log4jLevel=")) {
            log4jLevel = arg.substring(13);
		} else if (arg.startsWith("--audit.enabled") && arg.contains("=")) {//for trunjob call
		    final int equal = arg.indexOf('=');
			final String key = arg.substring("--".length(), equal);
			System.setProperty(key, arg.substring(equal + 1));
		}
    }
    
    private static final String NULL_VALUE_EXPRESSION_IN_COMMAND_STRING_FOR_CHILD_JOB_ONLY = "<TALEND_NULL>";

    private final String[][] escapeChars = {
        {"\\\\","\\"},{"\\n","\n"},{"\\'","\'"},{"\\r","\r"},
        {"\\f","\f"},{"\\b","\b"},{"\\t","\t"}
        };
    private String replaceEscapeChars (String keyValue) {

		if (keyValue == null || ("").equals(keyValue.trim())) {
			return keyValue;
		}

		StringBuilder result = new StringBuilder();
		int currIndex = 0;
		while (currIndex < keyValue.length()) {
			int index = -1;
			// judege if the left string includes escape chars
			for (String[] strArray : escapeChars) {
				index = keyValue.indexOf(strArray[0],currIndex);
				if (index>=0) {

					result.append(keyValue.substring(currIndex, index + strArray[0].length()).replace(strArray[0], strArray[1]));
					currIndex = index + strArray[0].length();
					break;
				}
			}
			// if the left string doesn't include escape chars, append the left into the result
			if (index < 0) {
				result.append(keyValue.substring(currIndex));
				currIndex = currIndex + keyValue.length();
			}
		}

		return result.toString();
    }

    public Integer getErrorCode() {
        return errorCode;
    }


    public String getStatus() {
        return status;
    }

    ResumeUtil resumeUtil = null;
}
/************************************************************************************************
 *     71291 characters generated by Talend Open Studio for Data Integration 
 *     on the 29 mars 2026, 15:08:29 WAT
 ************************************************************************************************/