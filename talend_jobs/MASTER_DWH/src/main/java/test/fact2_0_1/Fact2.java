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


package test.fact2_0_1;

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
 * Job: Fact2 Purpose: <br>
 * Description:  <br>
 * @author user@talend.com
 * @version 8.0.1.20211109_1610
 * @status 
 */
public class Fact2 implements TalendJob {

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
	private final String jobName = "Fact2";
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
				Fact2.this.exception = e;
			}
		}
		if (!(e instanceof TalendException)) {
		try {
			for (java.lang.reflect.Method m : this.getClass().getEnclosingClass().getMethods()) {
				if (m.getName().compareTo(currentComponent + "_error") == 0) {
					m.invoke(Fact2.this, new Object[] { e , currentComponent, globalMap});
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
			
			public void tDBOutput_1_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tDBInput_2_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tDBInput_3_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tDBInput_4_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tDBInput_5_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tDBInput_6_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tDBInput_7_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tDBInput_8_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tDBInput_9_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tDBInput_10_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tDBInput_11_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tDBInput_13_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tDBInput_12_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tDBInput_14_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tDBInput_15_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tDBInput_16_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tDBInput_17_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tDBInput_18_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tDBInput_19_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tDBInput_21_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tAdvancedHash_row2_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tAdvancedHash_row3_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tAdvancedHash_row4_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tAdvancedHash_row5_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tAdvancedHash_row6_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tAdvancedHash_row7_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tAdvancedHash_row8_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tAdvancedHash_row9_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tAdvancedHash_row10_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tAdvancedHash_row11_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tAdvancedHash_row12_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tAdvancedHash_row13_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tAdvancedHash_row14_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tAdvancedHash_row15_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tAdvancedHash_row16_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tAdvancedHash_row17_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tAdvancedHash_row18_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tAdvancedHash_row19_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tAdvancedHash_row21_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tDBInput_1_onSubJobError(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {

resumeUtil.addLog("SYSTEM_LOG", "NODE:"+ errorComponent, "", Thread.currentThread().getId()+ "", "FATAL", "", exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception),"");

			}
	






public static class outStruct implements routines.system.IPersistableRow<outStruct> {
    final static byte[] commonByteArrayLock_TEST_Fact2 = new byte[0];
    static byte[] commonByteArray_TEST_Fact2 = new byte[0];

	
			    public Integer id_reservation;

				public Integer getId_reservation () {
					return this.id_reservation;
				}
				
			    public Integer id_service;

				public Integer getId_service () {
					return this.id_service;
				}
				
			    public Integer id_event;

				public Integer getId_event () {
					return this.id_event;
				}
				
			    public Integer Date_PK;

				public Integer getDate_PK () {
					return this.Date_PK;
				}
				
			    public Integer nbr_reservations;

				public Integer getNbr_reservations () {
					return this.nbr_reservations;
				}
				
			    public Integer nbr_visitors;

				public Integer getNbr_visitors () {
					return this.nbr_visitors;
				}
				
			    public Integer id_provider;

				public Integer getId_provider () {
					return this.id_provider;
				}
				
			    public Integer id_subcategory;

				public Integer getId_subcategory () {
					return this.id_subcategory;
				}
				
			    public String status;

				public String getStatus () {
					return this.status;
				}
				
			    public Float price;

				public Float getPrice () {
					return this.price;
				}
				
			    public Integer id_category;

				public Integer getId_category () {
					return this.id_category;
				}
				
			    public Integer id_weather;

				public Integer getId_weather () {
					return this.id_weather;
				}
				
			    public Integer id_trend;

				public Integer getId_trend () {
					return this.id_trend;
				}
				
			    public Integer id_concurrence;

				public Integer getId_concurrence () {
					return this.id_concurrence;
				}
				
			    public Integer id_localisation;

				public Integer getId_localisation () {
					return this.id_localisation;
				}
				
			    public Integer market_count;

				public Integer getMarket_count () {
					return this.market_count;
				}
				
			    public Integer id_beneficiary;

				public Integer getId_beneficiary () {
					return this.id_beneficiary;
				}
				
			    public Integer id_evaluation;

				public Integer getId_evaluation () {
					return this.id_evaluation;
				}
				
			    public Integer id_complaint;

				public Integer getId_complaint () {
					return this.id_complaint;
				}
				
			    public Integer id_entertainer;

				public Integer getId_entertainer () {
					return this.id_entertainer;
				}
				
			    public Integer id_venue;

				public Integer getId_venue () {
					return this.id_venue;
				}
				
			    public Float marketing_spend;

				public Float getMarketing_spend () {
					return this.marketing_spend;
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

	private String readString(ObjectInputStream dis) throws IOException{
		String strReturn = null;
		int length = 0;
        length = dis.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			if(length > commonByteArray_TEST_Fact2.length) {
				if(length < 1024 && commonByteArray_TEST_Fact2.length == 0) {
   					commonByteArray_TEST_Fact2 = new byte[1024];
				} else {
   					commonByteArray_TEST_Fact2 = new byte[2 * length];
   				}
			}
			dis.readFully(commonByteArray_TEST_Fact2, 0, length);
			strReturn = new String(commonByteArray_TEST_Fact2, 0, length, utf8Charset);
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
			if(length > commonByteArray_TEST_Fact2.length) {
				if(length < 1024 && commonByteArray_TEST_Fact2.length == 0) {
   					commonByteArray_TEST_Fact2 = new byte[1024];
				} else {
   					commonByteArray_TEST_Fact2 = new byte[2 * length];
   				}
			}
			unmarshaller.readFully(commonByteArray_TEST_Fact2, 0, length);
			strReturn = new String(commonByteArray_TEST_Fact2, 0, length, utf8Charset);
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

		synchronized(commonByteArrayLock_TEST_Fact2) {

        	try {

        		int length = 0;
		
						this.id_reservation = readInteger(dis);
					
						this.id_service = readInteger(dis);
					
						this.id_event = readInteger(dis);
					
						this.Date_PK = readInteger(dis);
					
						this.nbr_reservations = readInteger(dis);
					
						this.nbr_visitors = readInteger(dis);
					
						this.id_provider = readInteger(dis);
					
						this.id_subcategory = readInteger(dis);
					
					this.status = readString(dis);
					
			            length = dis.readByte();
           				if (length == -1) {
           	    			this.price = null;
           				} else {
           			    	this.price = dis.readFloat();
           				}
					
						this.id_category = readInteger(dis);
					
						this.id_weather = readInteger(dis);
					
						this.id_trend = readInteger(dis);
					
						this.id_concurrence = readInteger(dis);
					
						this.id_localisation = readInteger(dis);
					
						this.market_count = readInteger(dis);
					
						this.id_beneficiary = readInteger(dis);
					
						this.id_evaluation = readInteger(dis);
					
						this.id_complaint = readInteger(dis);
					
						this.id_entertainer = readInteger(dis);
					
						this.id_venue = readInteger(dis);
					
			            length = dis.readByte();
           				if (length == -1) {
           	    			this.marketing_spend = null;
           				} else {
           			    	this.marketing_spend = dis.readFloat();
           				}
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }
    
    public void readData(org.jboss.marshalling.Unmarshaller dis) {

		synchronized(commonByteArrayLock_TEST_Fact2) {

        	try {

        		int length = 0;
		
						this.id_reservation = readInteger(dis);
					
						this.id_service = readInteger(dis);
					
						this.id_event = readInteger(dis);
					
						this.Date_PK = readInteger(dis);
					
						this.nbr_reservations = readInteger(dis);
					
						this.nbr_visitors = readInteger(dis);
					
						this.id_provider = readInteger(dis);
					
						this.id_subcategory = readInteger(dis);
					
					this.status = readString(dis);
					
			            length = dis.readByte();
           				if (length == -1) {
           	    			this.price = null;
           				} else {
           			    	this.price = dis.readFloat();
           				}
					
						this.id_category = readInteger(dis);
					
						this.id_weather = readInteger(dis);
					
						this.id_trend = readInteger(dis);
					
						this.id_concurrence = readInteger(dis);
					
						this.id_localisation = readInteger(dis);
					
						this.market_count = readInteger(dis);
					
						this.id_beneficiary = readInteger(dis);
					
						this.id_evaluation = readInteger(dis);
					
						this.id_complaint = readInteger(dis);
					
						this.id_entertainer = readInteger(dis);
					
						this.id_venue = readInteger(dis);
					
			            length = dis.readByte();
           				if (length == -1) {
           	    			this.marketing_spend = null;
           				} else {
           			    	this.marketing_spend = dis.readFloat();
           				}
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }

    public void writeData(ObjectOutputStream dos) {
        try {

		
					// Integer
				
						writeInteger(this.id_reservation,dos);
					
					// Integer
				
						writeInteger(this.id_service,dos);
					
					// Integer
				
						writeInteger(this.id_event,dos);
					
					// Integer
				
						writeInteger(this.Date_PK,dos);
					
					// Integer
				
						writeInteger(this.nbr_reservations,dos);
					
					// Integer
				
						writeInteger(this.nbr_visitors,dos);
					
					// Integer
				
						writeInteger(this.id_provider,dos);
					
					// Integer
				
						writeInteger(this.id_subcategory,dos);
					
					// String
				
						writeString(this.status,dos);
					
					// Float
				
						if(this.price == null) {
			                dos.writeByte(-1);
						} else {
               				dos.writeByte(0);
           			    	dos.writeFloat(this.price);
		            	}
					
					// Integer
				
						writeInteger(this.id_category,dos);
					
					// Integer
				
						writeInteger(this.id_weather,dos);
					
					// Integer
				
						writeInteger(this.id_trend,dos);
					
					// Integer
				
						writeInteger(this.id_concurrence,dos);
					
					// Integer
				
						writeInteger(this.id_localisation,dos);
					
					// Integer
				
						writeInteger(this.market_count,dos);
					
					// Integer
				
						writeInteger(this.id_beneficiary,dos);
					
					// Integer
				
						writeInteger(this.id_evaluation,dos);
					
					// Integer
				
						writeInteger(this.id_complaint,dos);
					
					// Integer
				
						writeInteger(this.id_entertainer,dos);
					
					// Integer
				
						writeInteger(this.id_venue,dos);
					
					// Float
				
						if(this.marketing_spend == null) {
			                dos.writeByte(-1);
						} else {
               				dos.writeByte(0);
           			    	dos.writeFloat(this.marketing_spend);
		            	}
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }
    
    public void writeData(org.jboss.marshalling.Marshaller dos) {
        try {

		
					// Integer
				
						writeInteger(this.id_reservation,dos);
					
					// Integer
				
						writeInteger(this.id_service,dos);
					
					// Integer
				
						writeInteger(this.id_event,dos);
					
					// Integer
				
						writeInteger(this.Date_PK,dos);
					
					// Integer
				
						writeInteger(this.nbr_reservations,dos);
					
					// Integer
				
						writeInteger(this.nbr_visitors,dos);
					
					// Integer
				
						writeInteger(this.id_provider,dos);
					
					// Integer
				
						writeInteger(this.id_subcategory,dos);
					
					// String
				
						writeString(this.status,dos);
					
					// Float
				
						if(this.price == null) {
			                dos.writeByte(-1);
						} else {
               				dos.writeByte(0);
           			    	dos.writeFloat(this.price);
		            	}
					
					// Integer
				
						writeInteger(this.id_category,dos);
					
					// Integer
				
						writeInteger(this.id_weather,dos);
					
					// Integer
				
						writeInteger(this.id_trend,dos);
					
					// Integer
				
						writeInteger(this.id_concurrence,dos);
					
					// Integer
				
						writeInteger(this.id_localisation,dos);
					
					// Integer
				
						writeInteger(this.market_count,dos);
					
					// Integer
				
						writeInteger(this.id_beneficiary,dos);
					
					// Integer
				
						writeInteger(this.id_evaluation,dos);
					
					// Integer
				
						writeInteger(this.id_complaint,dos);
					
					// Integer
				
						writeInteger(this.id_entertainer,dos);
					
					// Integer
				
						writeInteger(this.id_venue,dos);
					
					// Float
				
						if(this.marketing_spend == null) {
			                dos.writeByte(-1);
						} else {
               				dos.writeByte(0);
           			    	dos.writeFloat(this.marketing_spend);
		            	}
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }


    public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("[");
		sb.append("id_reservation="+String.valueOf(id_reservation));
		sb.append(",id_service="+String.valueOf(id_service));
		sb.append(",id_event="+String.valueOf(id_event));
		sb.append(",Date_PK="+String.valueOf(Date_PK));
		sb.append(",nbr_reservations="+String.valueOf(nbr_reservations));
		sb.append(",nbr_visitors="+String.valueOf(nbr_visitors));
		sb.append(",id_provider="+String.valueOf(id_provider));
		sb.append(",id_subcategory="+String.valueOf(id_subcategory));
		sb.append(",status="+status);
		sb.append(",price="+String.valueOf(price));
		sb.append(",id_category="+String.valueOf(id_category));
		sb.append(",id_weather="+String.valueOf(id_weather));
		sb.append(",id_trend="+String.valueOf(id_trend));
		sb.append(",id_concurrence="+String.valueOf(id_concurrence));
		sb.append(",id_localisation="+String.valueOf(id_localisation));
		sb.append(",market_count="+String.valueOf(market_count));
		sb.append(",id_beneficiary="+String.valueOf(id_beneficiary));
		sb.append(",id_evaluation="+String.valueOf(id_evaluation));
		sb.append(",id_complaint="+String.valueOf(id_complaint));
		sb.append(",id_entertainer="+String.valueOf(id_entertainer));
		sb.append(",id_venue="+String.valueOf(id_venue));
		sb.append(",marketing_spend="+String.valueOf(marketing_spend));
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
    final static byte[] commonByteArrayLock_TEST_Fact2 = new byte[0];
    static byte[] commonByteArray_TEST_Fact2 = new byte[0];

	
			    public Integer id_reservation;

				public Integer getId_reservation () {
					return this.id_reservation;
				}
				
			    public Integer id_service;

				public Integer getId_service () {
					return this.id_service;
				}
				
			    public Integer id_event;

				public Integer getId_event () {
					return this.id_event;
				}
				
			    public java.util.Date reservation_date;

				public java.util.Date getReservation_date () {
					return this.reservation_date;
				}
				
			    public String status;

				public String getStatus () {
					return this.status;
				}
				
			    public Float final_price;

				public Float getFinal_price () {
					return this.final_price;
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

	private String readString(ObjectInputStream dis) throws IOException{
		String strReturn = null;
		int length = 0;
        length = dis.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			if(length > commonByteArray_TEST_Fact2.length) {
				if(length < 1024 && commonByteArray_TEST_Fact2.length == 0) {
   					commonByteArray_TEST_Fact2 = new byte[1024];
				} else {
   					commonByteArray_TEST_Fact2 = new byte[2 * length];
   				}
			}
			dis.readFully(commonByteArray_TEST_Fact2, 0, length);
			strReturn = new String(commonByteArray_TEST_Fact2, 0, length, utf8Charset);
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
			if(length > commonByteArray_TEST_Fact2.length) {
				if(length < 1024 && commonByteArray_TEST_Fact2.length == 0) {
   					commonByteArray_TEST_Fact2 = new byte[1024];
				} else {
   					commonByteArray_TEST_Fact2 = new byte[2 * length];
   				}
			}
			unmarshaller.readFully(commonByteArray_TEST_Fact2, 0, length);
			strReturn = new String(commonByteArray_TEST_Fact2, 0, length, utf8Charset);
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

		synchronized(commonByteArrayLock_TEST_Fact2) {

        	try {

        		int length = 0;
		
						this.id_reservation = readInteger(dis);
					
						this.id_service = readInteger(dis);
					
						this.id_event = readInteger(dis);
					
					this.reservation_date = readDate(dis);
					
					this.status = readString(dis);
					
			            length = dis.readByte();
           				if (length == -1) {
           	    			this.final_price = null;
           				} else {
           			    	this.final_price = dis.readFloat();
           				}
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }
    
    public void readData(org.jboss.marshalling.Unmarshaller dis) {

		synchronized(commonByteArrayLock_TEST_Fact2) {

        	try {

        		int length = 0;
		
						this.id_reservation = readInteger(dis);
					
						this.id_service = readInteger(dis);
					
						this.id_event = readInteger(dis);
					
					this.reservation_date = readDate(dis);
					
					this.status = readString(dis);
					
			            length = dis.readByte();
           				if (length == -1) {
           	    			this.final_price = null;
           				} else {
           			    	this.final_price = dis.readFloat();
           				}
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }

    public void writeData(ObjectOutputStream dos) {
        try {

		
					// Integer
				
						writeInteger(this.id_reservation,dos);
					
					// Integer
				
						writeInteger(this.id_service,dos);
					
					// Integer
				
						writeInteger(this.id_event,dos);
					
					// java.util.Date
				
						writeDate(this.reservation_date,dos);
					
					// String
				
						writeString(this.status,dos);
					
					// Float
				
						if(this.final_price == null) {
			                dos.writeByte(-1);
						} else {
               				dos.writeByte(0);
           			    	dos.writeFloat(this.final_price);
		            	}
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }
    
    public void writeData(org.jboss.marshalling.Marshaller dos) {
        try {

		
					// Integer
				
						writeInteger(this.id_reservation,dos);
					
					// Integer
				
						writeInteger(this.id_service,dos);
					
					// Integer
				
						writeInteger(this.id_event,dos);
					
					// java.util.Date
				
						writeDate(this.reservation_date,dos);
					
					// String
				
						writeString(this.status,dos);
					
					// Float
				
						if(this.final_price == null) {
			                dos.writeByte(-1);
						} else {
               				dos.writeByte(0);
           			    	dos.writeFloat(this.final_price);
		            	}
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }


    public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("[");
		sb.append("id_reservation="+String.valueOf(id_reservation));
		sb.append(",id_service="+String.valueOf(id_service));
		sb.append(",id_event="+String.valueOf(id_event));
		sb.append(",reservation_date="+String.valueOf(reservation_date));
		sb.append(",status="+status);
		sb.append(",final_price="+String.valueOf(final_price));
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

public static class after_tDBInput_1Struct implements routines.system.IPersistableRow<after_tDBInput_1Struct> {
    final static byte[] commonByteArrayLock_TEST_Fact2 = new byte[0];
    static byte[] commonByteArray_TEST_Fact2 = new byte[0];

	
			    public Integer id_reservation;

				public Integer getId_reservation () {
					return this.id_reservation;
				}
				
			    public Integer id_service;

				public Integer getId_service () {
					return this.id_service;
				}
				
			    public Integer id_event;

				public Integer getId_event () {
					return this.id_event;
				}
				
			    public java.util.Date reservation_date;

				public java.util.Date getReservation_date () {
					return this.reservation_date;
				}
				
			    public String status;

				public String getStatus () {
					return this.status;
				}
				
			    public Float final_price;

				public Float getFinal_price () {
					return this.final_price;
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

	private String readString(ObjectInputStream dis) throws IOException{
		String strReturn = null;
		int length = 0;
        length = dis.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			if(length > commonByteArray_TEST_Fact2.length) {
				if(length < 1024 && commonByteArray_TEST_Fact2.length == 0) {
   					commonByteArray_TEST_Fact2 = new byte[1024];
				} else {
   					commonByteArray_TEST_Fact2 = new byte[2 * length];
   				}
			}
			dis.readFully(commonByteArray_TEST_Fact2, 0, length);
			strReturn = new String(commonByteArray_TEST_Fact2, 0, length, utf8Charset);
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
			if(length > commonByteArray_TEST_Fact2.length) {
				if(length < 1024 && commonByteArray_TEST_Fact2.length == 0) {
   					commonByteArray_TEST_Fact2 = new byte[1024];
				} else {
   					commonByteArray_TEST_Fact2 = new byte[2 * length];
   				}
			}
			unmarshaller.readFully(commonByteArray_TEST_Fact2, 0, length);
			strReturn = new String(commonByteArray_TEST_Fact2, 0, length, utf8Charset);
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

		synchronized(commonByteArrayLock_TEST_Fact2) {

        	try {

        		int length = 0;
		
						this.id_reservation = readInteger(dis);
					
						this.id_service = readInteger(dis);
					
						this.id_event = readInteger(dis);
					
					this.reservation_date = readDate(dis);
					
					this.status = readString(dis);
					
			            length = dis.readByte();
           				if (length == -1) {
           	    			this.final_price = null;
           				} else {
           			    	this.final_price = dis.readFloat();
           				}
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }
    
    public void readData(org.jboss.marshalling.Unmarshaller dis) {

		synchronized(commonByteArrayLock_TEST_Fact2) {

        	try {

        		int length = 0;
		
						this.id_reservation = readInteger(dis);
					
						this.id_service = readInteger(dis);
					
						this.id_event = readInteger(dis);
					
					this.reservation_date = readDate(dis);
					
					this.status = readString(dis);
					
			            length = dis.readByte();
           				if (length == -1) {
           	    			this.final_price = null;
           				} else {
           			    	this.final_price = dis.readFloat();
           				}
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }

    public void writeData(ObjectOutputStream dos) {
        try {

		
					// Integer
				
						writeInteger(this.id_reservation,dos);
					
					// Integer
				
						writeInteger(this.id_service,dos);
					
					// Integer
				
						writeInteger(this.id_event,dos);
					
					// java.util.Date
				
						writeDate(this.reservation_date,dos);
					
					// String
				
						writeString(this.status,dos);
					
					// Float
				
						if(this.final_price == null) {
			                dos.writeByte(-1);
						} else {
               				dos.writeByte(0);
           			    	dos.writeFloat(this.final_price);
		            	}
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }
    
    public void writeData(org.jboss.marshalling.Marshaller dos) {
        try {

		
					// Integer
				
						writeInteger(this.id_reservation,dos);
					
					// Integer
				
						writeInteger(this.id_service,dos);
					
					// Integer
				
						writeInteger(this.id_event,dos);
					
					// java.util.Date
				
						writeDate(this.reservation_date,dos);
					
					// String
				
						writeString(this.status,dos);
					
					// Float
				
						if(this.final_price == null) {
			                dos.writeByte(-1);
						} else {
               				dos.writeByte(0);
           			    	dos.writeFloat(this.final_price);
		            	}
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }


    public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("[");
		sb.append("id_reservation="+String.valueOf(id_reservation));
		sb.append(",id_service="+String.valueOf(id_service));
		sb.append(",id_event="+String.valueOf(id_event));
		sb.append(",reservation_date="+String.valueOf(reservation_date));
		sb.append(",status="+status);
		sb.append(",final_price="+String.valueOf(final_price));
	    sb.append("]");

	    return sb.toString();
    }

    /**
     * Compare keys
     */
    public int compareTo(after_tDBInput_1Struct other) {

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


		tDBInput_2Process(globalMap);
		tDBInput_3Process(globalMap);
		tDBInput_4Process(globalMap);
		tDBInput_5Process(globalMap);
		tDBInput_6Process(globalMap);
		tDBInput_7Process(globalMap);
		tDBInput_8Process(globalMap);
		tDBInput_9Process(globalMap);
		tDBInput_10Process(globalMap);
		tDBInput_11Process(globalMap);
		tDBInput_13Process(globalMap);
		tDBInput_12Process(globalMap);
		tDBInput_14Process(globalMap);
		tDBInput_15Process(globalMap);
		tDBInput_16Process(globalMap);
		tDBInput_17Process(globalMap);
		tDBInput_18Process(globalMap);
		tDBInput_19Process(globalMap);
		tDBInput_21Process(globalMap);

		row1Struct row1 = new row1Struct();
outStruct out = new outStruct();





	
	/**
	 * [tDBOutput_1 begin ] start
	 */

	

	
		
		ok_Hash.put("tDBOutput_1", false);
		start_Hash.put("tDBOutput_1", System.currentTimeMillis());
		
	
	currentComponent="tDBOutput_1";

	
					if(execStat) {
						runStat.updateStatOnConnection(resourceMap,iterateId,0,0,"out");
					}
				
		int tos_count_tDBOutput_1 = 0;
		



int nb_line_tDBOutput_1 = 0;
int nb_line_update_tDBOutput_1 = 0;
int nb_line_inserted_tDBOutput_1 = 0;
int nb_line_deleted_tDBOutput_1 = 0;
int nb_line_rejected_tDBOutput_1 = 0;

int deletedCount_tDBOutput_1=0;
int updatedCount_tDBOutput_1=0;
int insertedCount_tDBOutput_1=0;
int rowsToCommitCount_tDBOutput_1=0;
int rejectedCount_tDBOutput_1=0;
String dbschema_tDBOutput_1 = null;
String tableName_tDBOutput_1 = null;
boolean whetherReject_tDBOutput_1 = false;

java.util.Calendar calendar_tDBOutput_1 = java.util.Calendar.getInstance();
long year1_tDBOutput_1 = TalendDate.parseDate("yyyy-MM-dd","0001-01-01").getTime();
long year2_tDBOutput_1 = TalendDate.parseDate("yyyy-MM-dd","1753-01-01").getTime();
long year10000_tDBOutput_1 = TalendDate.parseDate("yyyy-MM-dd HH:mm:ss","9999-12-31 24:00:00").getTime();
long date_tDBOutput_1;

java.util.Calendar calendar_datetimeoffset_tDBOutput_1 = java.util.Calendar.getInstance(java.util.TimeZone.getTimeZone("UTC"));


        int updateKeyCount_tDBOutput_1 = 1;
        if(updateKeyCount_tDBOutput_1 < 1) {
            throw new RuntimeException("For update, Schema must have a key");
        } else if (updateKeyCount_tDBOutput_1 == 22 && true) {
                    System.err.println("For update, every Schema column can not be a key");
        }

	
java.sql.Connection conn_tDBOutput_1 = null;
String dbUser_tDBOutput_1 = null;
    dbschema_tDBOutput_1 = "dbo";
    String driverClass_tDBOutput_1 = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	
    java.lang.Class.forName(driverClass_tDBOutput_1);
    String port_tDBOutput_1 = "1433";
    String dbname_tDBOutput_1 = "event_DWH" ;
    String url_tDBOutput_1 = "jdbc:sqlserver://" + "localhost" ; 
    if (!"".equals(port_tDBOutput_1)) {
    	url_tDBOutput_1 += ":" + "1433";
    }
    if (!"".equals(dbname_tDBOutput_1)) {		    
		    	url_tDBOutput_1 += ";databaseName=" + "event_DWH"; 
	
    }
    url_tDBOutput_1 += ";appName=" + projectName + ";" + "trustServerCertificate=true";
    dbUser_tDBOutput_1 = "bi";

 
	final String decryptedPassword_tDBOutput_1 = routines.system.PasswordEncryptUtil.decryptPassword("enc:routine.encryption.key.v1:6nUh24CUVKX5dlaQNg7aPTHU4lWjS0vuvq2DdSiI");

    String dbPwd_tDBOutput_1 = decryptedPassword_tDBOutput_1;	
    conn_tDBOutput_1 = java.sql.DriverManager.getConnection(url_tDBOutput_1,dbUser_tDBOutput_1,dbPwd_tDBOutput_1);
	
		resourceMap.put("conn_tDBOutput_1", conn_tDBOutput_1);
	
        conn_tDBOutput_1.setAutoCommit(false);
        int commitEvery_tDBOutput_1 = 10000;
        int commitCounter_tDBOutput_1 = 0;


if(dbschema_tDBOutput_1 == null || dbschema_tDBOutput_1.trim().length() == 0) {
    tableName_tDBOutput_1 = "FACT_VENTES";
} else {
    tableName_tDBOutput_1 = dbschema_tDBOutput_1 + "].[" + "FACT_VENTES";
}
	int count_tDBOutput_1=0;

            try (java.sql.Statement stmtClear_tDBOutput_1 = conn_tDBOutput_1.createStatement()) {
                stmtClear_tDBOutput_1.executeUpdate("DELETE FROM [" + tableName_tDBOutput_1 + "]");
            }
        String update_tDBOutput_1 = "UPDATE [" + tableName_tDBOutput_1 + "] SET [id_service] = ?,[id_event] = ?,[Date_PK] = ?,[nbr_reservations] = ?,[nbr_visitors] = ?,[id_provider] = ?,[id_subcategory] = ?,[status] = ?,[price] = ?,[id_category] = ?,[id_weather] = ?,[id_trend] = ?,[id_concurrence] = ?,[id_localisation] = ?,[market_count] = ?,[id_beneficiary] = ?,[id_evaluation] = ?,[id_complaint] = ?,[id_entertainer] = ?,[id_venue] = ?,[marketing_spend] = ? WHERE [id_reservation] = ?";
        java.sql.PreparedStatement pstmtUpdate_tDBOutput_1 = conn_tDBOutput_1.prepareStatement(update_tDBOutput_1);
        resourceMap.put("pstmtUpdate_tDBOutput_1", pstmtUpdate_tDBOutput_1);
        String insert_tDBOutput_1 = "INSERT INTO [" + tableName_tDBOutput_1 + "] ([id_reservation],[id_service],[id_event],[Date_PK],[nbr_reservations],[nbr_visitors],[id_provider],[id_subcategory],[status],[price],[id_category],[id_weather],[id_trend],[id_concurrence],[id_localisation],[market_count],[id_beneficiary],[id_evaluation],[id_complaint],[id_entertainer],[id_venue],[marketing_spend]) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        java.sql.PreparedStatement pstmtInsert_tDBOutput_1 = conn_tDBOutput_1.prepareStatement(insert_tDBOutput_1);
        resourceMap.put("pstmtInsert_tDBOutput_1", pstmtInsert_tDBOutput_1);

 



/**
 * [tDBOutput_1 begin ] stop
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
	
		org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row2Struct> tHash_Lookup_row2 = (org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row2Struct>) 
				((org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row2Struct>) 
					globalMap.get( "tHash_Lookup_row2" ))
					;					
					
	

row2Struct row2HashKey = new row2Struct();
row2Struct row2Default = new row2Struct();
	
		org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row3Struct> tHash_Lookup_row3 = (org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row3Struct>) 
				((org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row3Struct>) 
					globalMap.get( "tHash_Lookup_row3" ))
					;					
					
	

row3Struct row3HashKey = new row3Struct();
row3Struct row3Default = new row3Struct();
	
		org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row4Struct> tHash_Lookup_row4 = (org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row4Struct>) 
				((org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row4Struct>) 
					globalMap.get( "tHash_Lookup_row4" ))
					;					
					
	

row4Struct row4HashKey = new row4Struct();
row4Struct row4Default = new row4Struct();
	
		org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row5Struct> tHash_Lookup_row5 = (org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row5Struct>) 
				((org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row5Struct>) 
					globalMap.get( "tHash_Lookup_row5" ))
					;					
					
	

row5Struct row5HashKey = new row5Struct();
row5Struct row5Default = new row5Struct();
	
		org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row6Struct> tHash_Lookup_row6 = (org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row6Struct>) 
				((org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row6Struct>) 
					globalMap.get( "tHash_Lookup_row6" ))
					;					
					
	

row6Struct row6HashKey = new row6Struct();
row6Struct row6Default = new row6Struct();
	
		org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row7Struct> tHash_Lookup_row7 = (org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row7Struct>) 
				((org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row7Struct>) 
					globalMap.get( "tHash_Lookup_row7" ))
					;					
					
	

row7Struct row7HashKey = new row7Struct();
row7Struct row7Default = new row7Struct();
	
		org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row8Struct> tHash_Lookup_row8 = (org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row8Struct>) 
				((org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row8Struct>) 
					globalMap.get( "tHash_Lookup_row8" ))
					;					
					
	

row8Struct row8HashKey = new row8Struct();
row8Struct row8Default = new row8Struct();
	
		org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row9Struct> tHash_Lookup_row9 = (org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row9Struct>) 
				((org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row9Struct>) 
					globalMap.get( "tHash_Lookup_row9" ))
					;					
					
	

row9Struct row9HashKey = new row9Struct();
row9Struct row9Default = new row9Struct();
	
		org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row10Struct> tHash_Lookup_row10 = (org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row10Struct>) 
				((org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row10Struct>) 
					globalMap.get( "tHash_Lookup_row10" ))
					;					
					
	

row10Struct row10HashKey = new row10Struct();
row10Struct row10Default = new row10Struct();
	
		org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row11Struct> tHash_Lookup_row11 = (org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row11Struct>) 
				((org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row11Struct>) 
					globalMap.get( "tHash_Lookup_row11" ))
					;					
					
	

row11Struct row11HashKey = new row11Struct();
row11Struct row11Default = new row11Struct();
	
		org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row12Struct> tHash_Lookup_row12 = (org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row12Struct>) 
				((org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row12Struct>) 
					globalMap.get( "tHash_Lookup_row12" ))
					;					
					
	

row12Struct row12HashKey = new row12Struct();
row12Struct row12Default = new row12Struct();
	
		org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row13Struct> tHash_Lookup_row13 = (org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row13Struct>) 
				((org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row13Struct>) 
					globalMap.get( "tHash_Lookup_row13" ))
					;					
					
	

row13Struct row13HashKey = new row13Struct();
row13Struct row13Default = new row13Struct();
	
		org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row14Struct> tHash_Lookup_row14 = (org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row14Struct>) 
				((org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row14Struct>) 
					globalMap.get( "tHash_Lookup_row14" ))
					;					
					
	

row14Struct row14HashKey = new row14Struct();
row14Struct row14Default = new row14Struct();
	
		org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row15Struct> tHash_Lookup_row15 = (org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row15Struct>) 
				((org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row15Struct>) 
					globalMap.get( "tHash_Lookup_row15" ))
					;					
					
	

row15Struct row15HashKey = new row15Struct();
row15Struct row15Default = new row15Struct();
	
		org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row16Struct> tHash_Lookup_row16 = (org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row16Struct>) 
				((org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row16Struct>) 
					globalMap.get( "tHash_Lookup_row16" ))
					;					
					
	

row16Struct row16HashKey = new row16Struct();
row16Struct row16Default = new row16Struct();
	
		org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row17Struct> tHash_Lookup_row17 = (org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row17Struct>) 
				((org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row17Struct>) 
					globalMap.get( "tHash_Lookup_row17" ))
					;					
					
	

row17Struct row17HashKey = new row17Struct();
row17Struct row17Default = new row17Struct();
	
		org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row18Struct> tHash_Lookup_row18 = (org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row18Struct>) 
				((org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row18Struct>) 
					globalMap.get( "tHash_Lookup_row18" ))
					;					
					
	

row18Struct row18HashKey = new row18Struct();
row18Struct row18Default = new row18Struct();
	
		org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row19Struct> tHash_Lookup_row19 = (org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row19Struct>) 
				((org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row19Struct>) 
					globalMap.get( "tHash_Lookup_row19" ))
					;					
					
	

row19Struct row19HashKey = new row19Struct();
row19Struct row19Default = new row19Struct();
	
		org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row21Struct> tHash_Lookup_row21 = (org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row21Struct>) 
				((org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row21Struct>) 
					globalMap.get( "tHash_Lookup_row21" ))
					;					
					
	

row21Struct row21HashKey = new row21Struct();
row21Struct row21Default = new row21Struct();
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
				
				 
	final String decryptedPassword_tDBInput_1 = routines.system.PasswordEncryptUtil.decryptPassword("enc:routine.encryption.key.v1:SbadbFksk9uZ0kL+U6028SFU5RkeL06nK7cos6i2IA==");
				
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

		    String dbquery_tDBInput_1 = "select * from Reservation";
		    

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
								row1.id_reservation = null;
							} else {
		                          
            row1.id_reservation = rs_tDBInput_1.getInt(1);
            if(rs_tDBInput_1.wasNull()){
                    row1.id_reservation = null;
            }
		                    }
							if(colQtyInRs_tDBInput_1 < 2) {
								row1.id_service = null;
							} else {
		                          
            row1.id_service = rs_tDBInput_1.getInt(2);
            if(rs_tDBInput_1.wasNull()){
                    row1.id_service = null;
            }
		                    }
							if(colQtyInRs_tDBInput_1 < 3) {
								row1.id_event = null;
							} else {
		                          
            row1.id_event = rs_tDBInput_1.getInt(3);
            if(rs_tDBInput_1.wasNull()){
                    row1.id_event = null;
            }
		                    }
							if(colQtyInRs_tDBInput_1 < 4) {
								row1.reservation_date = null;
							} else {
										
			row1.reservation_date = mssqlGTU_tDBInput_1.getDate(rsmd_tDBInput_1, rs_tDBInput_1, 4);
			
		                    }
							if(colQtyInRs_tDBInput_1 < 5) {
								row1.status = null;
							} else {
	                         		
           		tmpContent_tDBInput_1 = rs_tDBInput_1.getString(5);
            if(tmpContent_tDBInput_1 != null) {
            	if (talendToDBList_tDBInput_1 .contains(rsmd_tDBInput_1.getColumnTypeName(5).toUpperCase(java.util.Locale.ENGLISH))) {
            		row1.status = FormatterUtils.formatUnwithE(tmpContent_tDBInput_1);
            	} else {
                	row1.status = tmpContent_tDBInput_1;
                }
            } else {
                row1.status = null;
            }
		                    }
							if(colQtyInRs_tDBInput_1 < 6) {
								row1.final_price = null;
							} else {
		                          
            row1.final_price = rs_tDBInput_1.getFloat(6);
            if(rs_tDBInput_1.wasNull()){
                    row1.final_price = null;
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
            				    								  
		

				///////////////////////////////////////////////
				// Starting Lookup Table "row2" 
				///////////////////////////////////////////////


				
				
                            
 					    boolean forceLooprow2 = false;
       		  	    	
       		  	    	
 							row2Struct row2ObjectFromLookup = null;
                          
		           		  	if(!rejectedInnerJoin_tMap_1) { // G_TM_M_020

								
								hasCasePrimitiveKeyWithNull_tMap_1 = false;
								
                        		    		    row2HashKey.id_service = row1.id_service;
                        		    		

								
		                        	row2HashKey.hashCodeDirty = true;
                        		
	  					
	  							
			  					
			  					
	  					
		  							tHash_Lookup_row2.lookup( row2HashKey );

	  							

	  							

 								
								  
								  if(!tHash_Lookup_row2.hasNext()) { // G_TM_M_090

  								
		  				
	  								
			  							rejectedInnerJoin_tMap_1 = true;
	  								
						
									
  									  		
 								
								  
								  } // G_TM_M_090

  								



							} // G_TM_M_020
			           		  	  
							
				           		if(tHash_Lookup_row2 != null && tHash_Lookup_row2.getCount(row2HashKey) > 1) { // G 071
			  							
			  						
									 		
									//System.out.println("WARNING: UNIQUE MATCH is configured for the lookup 'row2' and it contains more one result from keys :  row2.id_service = '" + row2HashKey.id_service + "'");
								} // G 071
							

							row2Struct row2 = null;
                    		  	 
							   
                    		  	 
	       		  	    	row2Struct fromLookup_row2 = null;
							row2 = row2Default;
										 
							
								 
							
							
								if (tHash_Lookup_row2 !=null && tHash_Lookup_row2.hasNext()) { // G 099
								
							
								
								fromLookup_row2 = tHash_Lookup_row2.next();

							
							
								} // G 099
							
							

							if(fromLookup_row2 != null) {
								row2 = fromLookup_row2;
							}
							
							
							
			  							
								
	                    		  	
		                    
	            	
	           	
	            	
	            	
	            

				///////////////////////////////////////////////
				// Starting Lookup Table "row3" 
				///////////////////////////////////////////////


				
				
                            
 					    boolean forceLooprow3 = false;
       		  	    	
       		  	    	
 							row3Struct row3ObjectFromLookup = null;
                          
		           		  	if(!rejectedInnerJoin_tMap_1) { // G_TM_M_020

								
								hasCasePrimitiveKeyWithNull_tMap_1 = false;
								
                        		    		    row3HashKey.id_event = row1.id_event;
                        		    		

								
		                        	row3HashKey.hashCodeDirty = true;
                        		
	  					
	  							
			  					
			  					
	  					
		  							tHash_Lookup_row3.lookup( row3HashKey );

	  							

	  							

 								
								  
								  if(!tHash_Lookup_row3.hasNext()) { // G_TM_M_090

  								
		  				
	  								
			  							rejectedInnerJoin_tMap_1 = true;
	  								
						
									
  									  		
 								
								  
								  } // G_TM_M_090

  								



							} // G_TM_M_020
			           		  	  
							
				           		if(tHash_Lookup_row3 != null && tHash_Lookup_row3.getCount(row3HashKey) > 1) { // G 071
			  							
			  						
									 		
									//System.out.println("WARNING: UNIQUE MATCH is configured for the lookup 'row3' and it contains more one result from keys :  row3.id_event = '" + row3HashKey.id_event + "'");
								} // G 071
							

							row3Struct row3 = null;
                    		  	 
							   
                    		  	 
	       		  	    	row3Struct fromLookup_row3 = null;
							row3 = row3Default;
										 
							
								 
							
							
								if (tHash_Lookup_row3 !=null && tHash_Lookup_row3.hasNext()) { // G 099
								
							
								
								fromLookup_row3 = tHash_Lookup_row3.next();

							
							
								} // G 099
							
							

							if(fromLookup_row3 != null) {
								row3 = fromLookup_row3;
							}
							
							
							
			  							
								
	                    		  	
		                    
	            	
	           	
	            	
	            	
	            

				///////////////////////////////////////////////
				// Starting Lookup Table "row4" 
				///////////////////////////////////////////////


				
				
                            
 					    boolean forceLooprow4 = false;
       		  	    	
       		  	    	
 							row4Struct row4ObjectFromLookup = null;
                          
		           		  	if(!rejectedInnerJoin_tMap_1) { // G_TM_M_020

								
								hasCasePrimitiveKeyWithNull_tMap_1 = false;
								
                        		    		    row4HashKey.Date = TalendDate.parseDate(
    "yyyy-MM-dd",
    TalendDate.formatDate("yyyy-MM-dd", row1.reservation_date)
)  == null ? null : new java.util.Date(TalendDate.parseDate(
    "yyyy-MM-dd",
    TalendDate.formatDate("yyyy-MM-dd", row1.reservation_date)
) .getTime());
                        		    		

								
		                        	row4HashKey.hashCodeDirty = true;
                        		
	  					
	  							
			  					
			  					
	  					
		  							tHash_Lookup_row4.lookup( row4HashKey );

	  							

	  							

 								
		  				
	  								
						
									
  									  		
 								



							} // G_TM_M_020
			           		  	  
							
				           		if(tHash_Lookup_row4 != null && tHash_Lookup_row4.getCount(row4HashKey) > 1) { // G 071
			  							
			  						
									 		
									//System.out.println("WARNING: UNIQUE MATCH is configured for the lookup 'row4' and it contains more one result from keys :  row4.Date = '" + row4HashKey.Date + "'");
								} // G 071
							

							row4Struct row4 = null;
                    		  	 
							   
                    		  	 
	       		  	    	row4Struct fromLookup_row4 = null;
							row4 = row4Default;
										 
							
								 
							
							
								if (tHash_Lookup_row4 !=null && tHash_Lookup_row4.hasNext()) { // G 099
								
							
								
								fromLookup_row4 = tHash_Lookup_row4.next();

							
							
								} // G 099
							
							

							if(fromLookup_row4 != null) {
								row4 = fromLookup_row4;
							}
							
							
							
			  							
								
	                    		  	
		                    
	            	
	           	
	            	
	            	
	            

				///////////////////////////////////////////////
				// Starting Lookup Table "row5" 
				///////////////////////////////////////////////


				
				
                            
 					    boolean forceLooprow5 = false;
       		  	    	
       		  	    	
 							row5Struct row5ObjectFromLookup = null;
                          
		           		  	if(!rejectedInnerJoin_tMap_1) { // G_TM_M_020

								
								hasCasePrimitiveKeyWithNull_tMap_1 = false;
								
                        		    		    row5HashKey.date = TalendDate.parseDate(
    "yyyy-MM-dd",
    TalendDate.formatDate("yyyy-MM-dd", row4.Date)
)  == null ? null : new java.util.Date(TalendDate.parseDate(
    "yyyy-MM-dd",
    TalendDate.formatDate("yyyy-MM-dd", row4.Date)
) .getTime());
                        		    		

								
		                        	row5HashKey.hashCodeDirty = true;
                        		
	  					
	  							
			  					
			  					
	  					
		  							tHash_Lookup_row5.lookup( row5HashKey );

	  							

	  							

 								
								  
								  if(!tHash_Lookup_row5.hasNext()) { // G_TM_M_090

  								
		  				
	  								
			  							rejectedInnerJoin_tMap_1 = true;
	  								
						
									
  									  		
 								
								  
								  } // G_TM_M_090

  								



							} // G_TM_M_020
			           		  	  
							
				           		if(tHash_Lookup_row5 != null && tHash_Lookup_row5.getCount(row5HashKey) > 1) { // G 071
			  							
			  						
									 		
									//System.out.println("WARNING: UNIQUE MATCH is configured for the lookup 'row5' and it contains more one result from keys :  row5.date = '" + row5HashKey.date + "'");
								} // G 071
							

							row5Struct row5 = null;
                    		  	 
							   
                    		  	 
	       		  	    	row5Struct fromLookup_row5 = null;
							row5 = row5Default;
										 
							
								 
							
							
								if (tHash_Lookup_row5 !=null && tHash_Lookup_row5.hasNext()) { // G 099
								
							
								
								fromLookup_row5 = tHash_Lookup_row5.next();

							
							
								} // G 099
							
							

							if(fromLookup_row5 != null) {
								row5 = fromLookup_row5;
							}
							
							
							
			  							
								
	                    		  	
		                    
	            	
	           	
	            	
	            	
	            

				///////////////////////////////////////////////
				// Starting Lookup Table "row6" 
				///////////////////////////////////////////////


				
				
                            
 					    boolean forceLooprow6 = false;
       		  	    	
       		  	    	
 							row6Struct row6ObjectFromLookup = null;
                          
		           		  	if(!rejectedInnerJoin_tMap_1) { // G_TM_M_020

								
								hasCasePrimitiveKeyWithNull_tMap_1 = false;
								
                        		    		    row6HashKey.id_service = row2.id_service;
                        		    		

								
		                        	row6HashKey.hashCodeDirty = true;
                        		
	  					
	  							
			  					
			  					
	  					
		  							tHash_Lookup_row6.lookup( row6HashKey );

	  							

	  							

 								
								  
								  if(!tHash_Lookup_row6.hasNext()) { // G_TM_M_090

  								
		  				
	  								
			  							rejectedInnerJoin_tMap_1 = true;
	  								
						
									
  									  		
 								
								  
								  } // G_TM_M_090

  								



							} // G_TM_M_020
			           		  	  
							
				           		if(tHash_Lookup_row6 != null && tHash_Lookup_row6.getCount(row6HashKey) > 1) { // G 071
			  							
			  						
									 		
									//System.out.println("WARNING: UNIQUE MATCH is configured for the lookup 'row6' and it contains more one result from keys :  row6.id_service = '" + row6HashKey.id_service + "'");
								} // G 071
							

							row6Struct row6 = null;
                    		  	 
							   
                    		  	 
	       		  	    	row6Struct fromLookup_row6 = null;
							row6 = row6Default;
										 
							
								 
							
							
								if (tHash_Lookup_row6 !=null && tHash_Lookup_row6.hasNext()) { // G 099
								
							
								
								fromLookup_row6 = tHash_Lookup_row6.next();

							
							
								} // G 099
							
							

							if(fromLookup_row6 != null) {
								row6 = fromLookup_row6;
							}
							
							
							
			  							
								
	                    		  	
		                    
	            	
	           	
	            	
	            	
	            

				///////////////////////////////////////////////
				// Starting Lookup Table "row7" 
				///////////////////////////////////////////////


				
				
                            
 					    boolean forceLooprow7 = false;
       		  	    	
       		  	    	
 							row7Struct row7ObjectFromLookup = null;
                          
		           		  	if(!rejectedInnerJoin_tMap_1) { // G_TM_M_020

								
								hasCasePrimitiveKeyWithNull_tMap_1 = false;
								
                        		    		    row7HashKey.id_subcategory = row6.id_subcategory;
                        		    		

								
		                        	row7HashKey.hashCodeDirty = true;
                        		
	  					
	  							
			  					
			  					
	  					
		  							tHash_Lookup_row7.lookup( row7HashKey );

	  							

	  							

 								
								  
								  if(!tHash_Lookup_row7.hasNext()) { // G_TM_M_090

  								
		  				
	  								
			  							rejectedInnerJoin_tMap_1 = true;
	  								
						
									
  									  		
 								
								  
								  } // G_TM_M_090

  								



							} // G_TM_M_020
			           		  	  
							
				           		if(tHash_Lookup_row7 != null && tHash_Lookup_row7.getCount(row7HashKey) > 1) { // G 071
			  							
			  						
									 		
									//System.out.println("WARNING: UNIQUE MATCH is configured for the lookup 'row7' and it contains more one result from keys :  row7.id_subcategory = '" + row7HashKey.id_subcategory + "'");
								} // G 071
							

							row7Struct row7 = null;
                    		  	 
							   
                    		  	 
	       		  	    	row7Struct fromLookup_row7 = null;
							row7 = row7Default;
										 
							
								 
							
							
								if (tHash_Lookup_row7 !=null && tHash_Lookup_row7.hasNext()) { // G 099
								
							
								
								fromLookup_row7 = tHash_Lookup_row7.next();

							
							
								} // G 099
							
							

							if(fromLookup_row7 != null) {
								row7 = fromLookup_row7;
							}
							
							
							
			  							
								
	                    		  	
		                    
	            	
	           	
	            	
	            	
	            

				///////////////////////////////////////////////
				// Starting Lookup Table "row8" 
				///////////////////////////////////////////////


				
				
                            
 					    boolean forceLooprow8 = false;
       		  	    	
       		  	    	
 							row8Struct row8ObjectFromLookup = null;
                          
		           		  	if(!rejectedInnerJoin_tMap_1) { // G_TM_M_020

								
								hasCasePrimitiveKeyWithNull_tMap_1 = false;
								
                        		    		    row8HashKey.id_category = row6.id_category;
                        		    		

								
		                        	row8HashKey.hashCodeDirty = true;
                        		
	  					
	  							
			  					
			  					
	  					
		  							tHash_Lookup_row8.lookup( row8HashKey );

	  							

	  							

 								
								  
								  if(!tHash_Lookup_row8.hasNext()) { // G_TM_M_090

  								
		  				
	  								
			  							rejectedInnerJoin_tMap_1 = true;
	  								
						
									
  									  		
 								
								  
								  } // G_TM_M_090

  								



							} // G_TM_M_020
			           		  	  
							
				           		if(tHash_Lookup_row8 != null && tHash_Lookup_row8.getCount(row8HashKey) > 1) { // G 071
			  							
			  						
									 		
									//System.out.println("WARNING: UNIQUE MATCH is configured for the lookup 'row8' and it contains more one result from keys :  row8.id_category = '" + row8HashKey.id_category + "'");
								} // G 071
							

							row8Struct row8 = null;
                    		  	 
							   
                    		  	 
	       		  	    	row8Struct fromLookup_row8 = null;
							row8 = row8Default;
										 
							
								 
							
							
								if (tHash_Lookup_row8 !=null && tHash_Lookup_row8.hasNext()) { // G 099
								
							
								
								fromLookup_row8 = tHash_Lookup_row8.next();

							
							
								} // G 099
							
							

							if(fromLookup_row8 != null) {
								row8 = fromLookup_row8;
							}
							
							
							
			  							
								
	                    		  	
		                    
	            	
	           	
	            	
	            	
	            

				///////////////////////////////////////////////
				// Starting Lookup Table "row9" 
				///////////////////////////////////////////////


				
				
                            
 					    boolean forceLooprow9 = false;
       		  	    	
       		  	    	
 							row9Struct row9ObjectFromLookup = null;
                          
		           		  	if(!rejectedInnerJoin_tMap_1) { // G_TM_M_020

								
								hasCasePrimitiveKeyWithNull_tMap_1 = false;
								
                        		    		    row9HashKey.date = TalendDate.parseDate(
    "yyyy-MM-dd",
    TalendDate.formatDate("yyyy-MM-dd", row4.Date)
)  == null ? null : new java.util.Date(TalendDate.parseDate(
    "yyyy-MM-dd",
    TalendDate.formatDate("yyyy-MM-dd", row4.Date)
) .getTime());
                        		    		

								
		                        	row9HashKey.hashCodeDirty = true;
                        		
	  					
	  							
			  					
			  					
	  					
		  							tHash_Lookup_row9.lookup( row9HashKey );

	  							

	  							

 								
								  
								  if(!tHash_Lookup_row9.hasNext()) { // G_TM_M_090

  								
		  				
	  								
			  							rejectedInnerJoin_tMap_1 = true;
	  								
						
									
  									  		
 								
								  
								  } // G_TM_M_090

  								



							} // G_TM_M_020
			           		  	  
							
				           		if(tHash_Lookup_row9 != null && tHash_Lookup_row9.getCount(row9HashKey) > 1) { // G 071
			  							
			  						
									 		
									//System.out.println("WARNING: UNIQUE MATCH is configured for the lookup 'row9' and it contains more one result from keys :  row9.date = '" + row9HashKey.date + "'");
								} // G 071
							

							row9Struct row9 = null;
                    		  	 
							   
                    		  	 
	       		  	    	row9Struct fromLookup_row9 = null;
							row9 = row9Default;
										 
							
								 
							
							
								if (tHash_Lookup_row9 !=null && tHash_Lookup_row9.hasNext()) { // G 099
								
							
								
								fromLookup_row9 = tHash_Lookup_row9.next();

							
							
								} // G 099
							
							

							if(fromLookup_row9 != null) {
								row9 = fromLookup_row9;
							}
							
							
							
			  							
								
	                    		  	
		                    
	            	
	           	
	            	
	            	
	            

				///////////////////////////////////////////////
				// Starting Lookup Table "row10" 
				///////////////////////////////////////////////


				
				
                            
 					    boolean forceLooprow10 = false;
       		  	    	
       		  	    	
 							row10Struct row10ObjectFromLookup = null;
                          
		           		  	if(!rejectedInnerJoin_tMap_1) { // G_TM_M_020

								
								hasCasePrimitiveKeyWithNull_tMap_1 = false;
								
                        		    		    row10HashKey.category = row8.name ;
                        		    		

								
		                        	row10HashKey.hashCodeDirty = true;
                        		
	  					
	  							
			  					
			  					
	  					
		  							tHash_Lookup_row10.lookup( row10HashKey );

	  							

	  							

 								
								  
								  if(!tHash_Lookup_row10.hasNext()) { // G_TM_M_090

  								
		  				
	  								
			  							rejectedInnerJoin_tMap_1 = true;
	  								
						
									
  									  		
 								
								  
								  } // G_TM_M_090

  								



							} // G_TM_M_020
			           		  	  
							
				           		if(tHash_Lookup_row10 != null && tHash_Lookup_row10.getCount(row10HashKey) > 1) { // G 071
			  							
			  						
									 		
									//System.out.println("WARNING: UNIQUE MATCH is configured for the lookup 'row10' and it contains more one result from keys :  row10.category = '" + row10HashKey.category + "'");
								} // G 071
							

							row10Struct row10 = null;
                    		  	 
							   
                    		  	 
	       		  	    	row10Struct fromLookup_row10 = null;
							row10 = row10Default;
										 
							
								 
							
							
								if (tHash_Lookup_row10 !=null && tHash_Lookup_row10.hasNext()) { // G 099
								
							
								
								fromLookup_row10 = tHash_Lookup_row10.next();

							
							
								} // G 099
							
							

							if(fromLookup_row10 != null) {
								row10 = fromLookup_row10;
							}
							
							
							
			  							
								
	                    		  	
		                    
	            	
	           	
	            	
	            	
	            

				///////////////////////////////////////////////
				// Starting Lookup Table "row11" 
				///////////////////////////////////////////////


				
				
                            
 					    boolean forceLooprow11 = false;
       		  	    	
       		  	    	
 							row11Struct row11ObjectFromLookup = null;
                          
		           		  	if(!rejectedInnerJoin_tMap_1) { // G_TM_M_020

								
								hasCasePrimitiveKeyWithNull_tMap_1 = false;
								
                        		    		    row11HashKey.category = row3.type == null ? null :
row3.type.toUpperCase().contains("BIRTHDAY") ? "Private / Birthday / Miscellaneous" :
row3.type.toUpperCase().contains("WEDDING") ? "Wedding / Concerts / Music" :
row3.type.toUpperCase().contains("BUSINESS") || 
row3.type.toUpperCase().contains("CORPORATE") ? "business" : null ;
                        		    		

								
		                        	row11HashKey.hashCodeDirty = true;
                        		
	  					
	  							
			  					
			  					
	  					
		  							tHash_Lookup_row11.lookup( row11HashKey );

	  							

	  							

 								
								  
								  if(!tHash_Lookup_row11.hasNext()) { // G_TM_M_090

  								
		  				
	  								
			  							rejectedInnerJoin_tMap_1 = true;
	  								
						
									
  									  		
 								
								  
								  } // G_TM_M_090

  								



							} // G_TM_M_020
			           		  	  
							
				           		if(tHash_Lookup_row11 != null && tHash_Lookup_row11.getCount(row11HashKey) > 1) { // G 071
			  							
			  						
									 		
									//System.out.println("WARNING: UNIQUE MATCH is configured for the lookup 'row11' and it contains more one result from keys :  row11.category = '" + row11HashKey.category + "'");
								} // G 071
							

							row11Struct row11 = null;
                    		  	 
							   
                    		  	 
	       		  	    	row11Struct fromLookup_row11 = null;
							row11 = row11Default;
										 
							
								 
							
							
								if (tHash_Lookup_row11 !=null && tHash_Lookup_row11.hasNext()) { // G 099
								
							
								
								fromLookup_row11 = tHash_Lookup_row11.next();

							
							
								} // G 099
							
							

							if(fromLookup_row11 != null) {
								row11 = fromLookup_row11;
							}
							
							
							
			  							
								
	                    		  	
		                    
	            	
	           	
	            	
	            	
	            

				///////////////////////////////////////////////
				// Starting Lookup Table "row12" 
				///////////////////////////////////////////////


				
				
                            
 					    boolean forceLooprow12 = false;
       		  	    	
       		  	    	
 							row12Struct row12ObjectFromLookup = null;
                          
		           		  	if(!rejectedInnerJoin_tMap_1) { // G_TM_M_020

								
								hasCasePrimitiveKeyWithNull_tMap_1 = false;
								
                        		    		    row12HashKey.id_provider = row6.id_provider;
                        		    		

								
		                        	row12HashKey.hashCodeDirty = true;
                        		
	  					
	  							
			  					
			  					
	  					
		  							tHash_Lookup_row12.lookup( row12HashKey );

	  							

	  							

 								
								  
								  if(!tHash_Lookup_row12.hasNext()) { // G_TM_M_090

  								
		  				
	  								
			  							rejectedInnerJoin_tMap_1 = true;
	  								
						
									
  									  		
 								
								  
								  } // G_TM_M_090

  								



							} // G_TM_M_020
			           		  	  
							
				           		if(tHash_Lookup_row12 != null && tHash_Lookup_row12.getCount(row12HashKey) > 1) { // G 071
			  							
			  						
									 		
									//System.out.println("WARNING: UNIQUE MATCH is configured for the lookup 'row12' and it contains more one result from keys :  row12.id_provider = '" + row12HashKey.id_provider + "'");
								} // G 071
							

							row12Struct row12 = null;
                    		  	 
							   
                    		  	 
	       		  	    	row12Struct fromLookup_row12 = null;
							row12 = row12Default;
										 
							
								 
							
							
								if (tHash_Lookup_row12 !=null && tHash_Lookup_row12.hasNext()) { // G 099
								
							
								
								fromLookup_row12 = tHash_Lookup_row12.next();

							
							
								} // G 099
							
							

							if(fromLookup_row12 != null) {
								row12 = fromLookup_row12;
							}
							
							
							
			  							
								
	                    		  	
		                    
	            	
	           	
	            	
	            	
	            

				///////////////////////////////////////////////
				// Starting Lookup Table "row13" 
				///////////////////////////////////////////////


				
				
                            
 					    boolean forceLooprow13 = false;
       		  	    	
       		  	    	
 							row13Struct row13ObjectFromLookup = null;
                          
		           		  	if(!rejectedInnerJoin_tMap_1) { // G_TM_M_020

								
								hasCasePrimitiveKeyWithNull_tMap_1 = false;
								
                        		    		    row13HashKey.city = row12.city;
                        		    		

								
		                        	row13HashKey.hashCodeDirty = true;
                        		
	  					
	  							
			  					
			  					
	  					
		  							tHash_Lookup_row13.lookup( row13HashKey );

	  							

	  							

 								
								  
								  if(!tHash_Lookup_row13.hasNext()) { // G_TM_M_090

  								
		  				
	  								
			  							rejectedInnerJoin_tMap_1 = true;
	  								
						
									
  									  		
 								
								  
								  } // G_TM_M_090

  								



							} // G_TM_M_020
			           		  	  
							
				           		if(tHash_Lookup_row13 != null && tHash_Lookup_row13.getCount(row13HashKey) > 1) { // G 071
			  							
			  						
									 		
									//System.out.println("WARNING: UNIQUE MATCH is configured for the lookup 'row13' and it contains more one result from keys :  row13.city = '" + row13HashKey.city + "'");
								} // G 071
							

							row13Struct row13 = null;
                    		  	 
							   
                    		  	 
	       		  	    	row13Struct fromLookup_row13 = null;
							row13 = row13Default;
										 
							
								 
							
							
								if (tHash_Lookup_row13 !=null && tHash_Lookup_row13.hasNext()) { // G 099
								
							
								
								fromLookup_row13 = tHash_Lookup_row13.next();

							
							
								} // G 099
							
							

							if(fromLookup_row13 != null) {
								row13 = fromLookup_row13;
							}
							
							
							
			  							
								
	                    		  	
		                    
	            	
	           	
	            	
	            	
	            

				///////////////////////////////////////////////
				// Starting Lookup Table "row14" 
				///////////////////////////////////////////////


				
				
                            
 					    boolean forceLooprow14 = false;
       		  	    	
       		  	    	
 							row14Struct row14ObjectFromLookup = null;
                          
		           		  	if(!rejectedInnerJoin_tMap_1) { // G_TM_M_020

								
								hasCasePrimitiveKeyWithNull_tMap_1 = false;
								
                        		    		    row14HashKey.id_category = row8.id_category;
                        		    		
                        		    		    row14HashKey.id_subcategory = row7.id_subcategory;
                        		    		
                        		    		    row14HashKey.ville = row13.city;
                        		    		

								
		                        	row14HashKey.hashCodeDirty = true;
                        		
	  					
	  							
			  					
			  					
	  					
		  							tHash_Lookup_row14.lookup( row14HashKey );

	  							

	  							

 								
								  
								  if(!tHash_Lookup_row14.hasNext()) { // G_TM_M_090

  								
		  				
	  								
			  							rejectedInnerJoin_tMap_1 = true;
	  								
						
									
  									  		
 								
								  
								  } // G_TM_M_090

  								



							} // G_TM_M_020
			           		  	  
							
				           		if(tHash_Lookup_row14 != null && tHash_Lookup_row14.getCount(row14HashKey) > 1) { // G 071
			  							
			  						
									 		
									//System.out.println("WARNING: UNIQUE MATCH is configured for the lookup 'row14' and it contains more one result from keys :  row14.id_category = '" + row14HashKey.id_category + "', row14.id_subcategory = '" + row14HashKey.id_subcategory + "', row14.ville = '" + row14HashKey.ville + "'");
								} // G 071
							

							row14Struct row14 = null;
                    		  	 
							   
                    		  	 
	       		  	    	row14Struct fromLookup_row14 = null;
							row14 = row14Default;
										 
							
								 
							
							
								if (tHash_Lookup_row14 !=null && tHash_Lookup_row14.hasNext()) { // G 099
								
							
								
								fromLookup_row14 = tHash_Lookup_row14.next();

							
							
								} // G 099
							
							

							if(fromLookup_row14 != null) {
								row14 = fromLookup_row14;
							}
							
							
							
			  							
								
	                    		  	
		                    
	            	
	           	
	            	
	            	
	            

				///////////////////////////////////////////////
				// Starting Lookup Table "row15" 
				///////////////////////////////////////////////


				
				
                            
 					    boolean forceLooprow15 = false;
       		  	    	
       		  	    	
 							row15Struct row15ObjectFromLookup = null;
                          
		           		  	if(!rejectedInnerJoin_tMap_1) { // G_TM_M_020

								
								hasCasePrimitiveKeyWithNull_tMap_1 = false;
								
                        		    		    row15HashKey.id_event = row3.id_event;
                        		    		

								
		                        	row15HashKey.hashCodeDirty = true;
                        		
	  					
	  							
			  					
			  					
	  					
		  							tHash_Lookup_row15.lookup( row15HashKey );

	  							

	  							

 								
								  
								  if(!tHash_Lookup_row15.hasNext()) { // G_TM_M_090

  								
		  				
	  								
			  							rejectedInnerJoin_tMap_1 = true;
	  								
						
									
  									  		
 								
								  
								  } // G_TM_M_090

  								



							} // G_TM_M_020
			           		  	  
							
				           		if(tHash_Lookup_row15 != null && tHash_Lookup_row15.getCount(row15HashKey) > 1) { // G 071
			  							
			  						
									 		
									//System.out.println("WARNING: UNIQUE MATCH is configured for the lookup 'row15' and it contains more one result from keys :  row15.id_event = '" + row15HashKey.id_event + "'");
								} // G 071
							

							row15Struct row15 = null;
                    		  	 
							   
                    		  	 
	       		  	    	row15Struct fromLookup_row15 = null;
							row15 = row15Default;
										 
							
								 
							
							
								if (tHash_Lookup_row15 !=null && tHash_Lookup_row15.hasNext()) { // G 099
								
							
								
								fromLookup_row15 = tHash_Lookup_row15.next();

							
							
								} // G 099
							
							

							if(fromLookup_row15 != null) {
								row15 = fromLookup_row15;
							}
							
							
							
			  							
								
	                    		  	
		                    
	            	
	           	
	            	
	            	
	            

				///////////////////////////////////////////////
				// Starting Lookup Table "row16" 
				///////////////////////////////////////////////


				
				
                            
 					    boolean forceLooprow16 = false;
       		  	    	
       		  	    	
 							row16Struct row16ObjectFromLookup = null;
                          
		           		  	if(!rejectedInnerJoin_tMap_1) { // G_TM_M_020

								
								hasCasePrimitiveKeyWithNull_tMap_1 = false;
								
                        		    		    row16HashKey.id_reservation = row1.id_reservation;
                        		    		

								
		                        	row16HashKey.hashCodeDirty = true;
                        		
	  					
	  							
			  					
			  					
	  					
		  							tHash_Lookup_row16.lookup( row16HashKey );

	  							

	  							

 								
								  
								  if(!tHash_Lookup_row16.hasNext()) { // G_TM_M_090

  								
		  				
	  								
			  							rejectedInnerJoin_tMap_1 = true;
	  								
						
									
  									  		
 								
								  
								  } // G_TM_M_090

  								



							} // G_TM_M_020
			           		  	  
							
				           		if(tHash_Lookup_row16 != null && tHash_Lookup_row16.getCount(row16HashKey) > 1) { // G 071
			  							
			  						
									 		
									//System.out.println("WARNING: UNIQUE MATCH is configured for the lookup 'row16' and it contains more one result from keys :  row16.id_reservation = '" + row16HashKey.id_reservation + "'");
								} // G 071
							

							row16Struct row16 = null;
                    		  	 
							   
                    		  	 
	       		  	    	row16Struct fromLookup_row16 = null;
							row16 = row16Default;
										 
							
								 
							
							
								if (tHash_Lookup_row16 !=null && tHash_Lookup_row16.hasNext()) { // G 099
								
							
								
								fromLookup_row16 = tHash_Lookup_row16.next();

							
							
								} // G 099
							
							

							if(fromLookup_row16 != null) {
								row16 = fromLookup_row16;
							}
							
							
							
			  							
								
	                    		  	
		                    
	            	
	           	
	            	
	            	
	            

				///////////////////////////////////////////////
				// Starting Lookup Table "row17" 
				///////////////////////////////////////////////


				
				
                            
 					    boolean forceLooprow17 = false;
       		  	    	
       		  	    	
 							row17Struct row17ObjectFromLookup = null;
                          
		           		  	if(!rejectedInnerJoin_tMap_1) { // G_TM_M_020

								
								hasCasePrimitiveKeyWithNull_tMap_1 = false;
								
                        		    		    row17HashKey.id_provider = row12.id_provider;
                        		    		

								
		                        	row17HashKey.hashCodeDirty = true;
                        		
	  					
	  							
			  					
			  					
	  					
		  							tHash_Lookup_row17.lookup( row17HashKey );

	  							

	  							

 								
								  
								  if(!tHash_Lookup_row17.hasNext()) { // G_TM_M_090

  								
		  				
	  								
			  							rejectedInnerJoin_tMap_1 = true;
	  								
						
									
  									  		
 								
								  
								  } // G_TM_M_090

  								



							} // G_TM_M_020
			           		  	  
							
				           		if(tHash_Lookup_row17 != null && tHash_Lookup_row17.getCount(row17HashKey) > 1) { // G 071
			  							
			  						
									 		
									//System.out.println("WARNING: UNIQUE MATCH is configured for the lookup 'row17' and it contains more one result from keys :  row17.id_provider = '" + row17HashKey.id_provider + "'");
								} // G 071
							

							row17Struct row17 = null;
                    		  	 
							   
                    		  	 
	       		  	    	row17Struct fromLookup_row17 = null;
							row17 = row17Default;
										 
							
								 
							
							
								if (tHash_Lookup_row17 !=null && tHash_Lookup_row17.hasNext()) { // G 099
								
							
								
								fromLookup_row17 = tHash_Lookup_row17.next();

							
							
								} // G 099
							
							

							if(fromLookup_row17 != null) {
								row17 = fromLookup_row17;
							}
							
							
							
			  							
								
	                    		  	
		                    
	            	
	           	
	            	
	            	
	            

				///////////////////////////////////////////////
				// Starting Lookup Table "row18" 
				///////////////////////////////////////////////


				
				
                            
 					    boolean forceLooprow18 = false;
       		  	    	
       		  	    	
 							row18Struct row18ObjectFromLookup = null;
                          
		           		  	if(!rejectedInnerJoin_tMap_1) { // G_TM_M_020

								
								hasCasePrimitiveKeyWithNull_tMap_1 = false;
								
                        		    		    row18HashKey.categorie = row8.name == null ? null :
row8.name.toUpperCase().contains("BIRTHDAY") ? "Singer" :
row8.name.toUpperCase().contains("BIRTHDAY") ? "Humor" :
row8.name.toUpperCase().contains("BIRTHDAY") ? "Makeup Artist" :
row8.name.toUpperCase().contains("BIRTHDAY") ? "Photographer" :
row8.name.toUpperCase().contains("BIRTHDAY") ? "Food" :
row8.name.toUpperCase().contains("WEDDING") ? "Singer, Actors" :
row8.name.toUpperCase().contains("WEDDING") ? "Fashion" :
row8.name.toUpperCase().contains("WEDDING") ? "Modeling" :
row8.name.toUpperCase().contains("WEDDING") ? "Photographer" :
row8.name.toUpperCase().contains("CORPORATE") ? "Actors, TV Host" :
row8.name.toUpperCase().contains("CORPORATE") ? "TV Host" :
row8.name.toUpperCase().contains("CORPORATE") ? "Video Blogger" : "Singer" ;
                        		    		

								
		                        	row18HashKey.hashCodeDirty = true;
                        		
	  					
	  							
			  					
			  					
	  					
		  							tHash_Lookup_row18.lookup( row18HashKey );

	  							

	  							

 								
								  
								  if(!tHash_Lookup_row18.hasNext()) { // G_TM_M_090

  								
		  				
	  								
			  							rejectedInnerJoin_tMap_1 = true;
	  								
						
									
  									  		
 								
								  
								  } // G_TM_M_090

  								



							} // G_TM_M_020
			           		  	  
							
				           		if(tHash_Lookup_row18 != null && tHash_Lookup_row18.getCount(row18HashKey) > 1) { // G 071
			  							
			  						
									 		
									//System.out.println("WARNING: UNIQUE MATCH is configured for the lookup 'row18' and it contains more one result from keys :  row18.categorie = '" + row18HashKey.categorie + "'");
								} // G 071
							

							row18Struct row18 = null;
                    		  	 
							   
                    		  	 
	       		  	    	row18Struct fromLookup_row18 = null;
							row18 = row18Default;
										 
							
								 
							
							
								if (tHash_Lookup_row18 !=null && tHash_Lookup_row18.hasNext()) { // G 099
								
							
								
								fromLookup_row18 = tHash_Lookup_row18.next();

							
							
								} // G 099
							
							

							if(fromLookup_row18 != null) {
								row18 = fromLookup_row18;
							}
							
							
							
			  							
								
	                    		  	
		                    
	            	
	           	
	            	
	            	
	            

				///////////////////////////////////////////////
				// Starting Lookup Table "row19" 
				///////////////////////////////////////////////


				
				
                            
 					    boolean forceLooprow19 = false;
       		  	    	
       		  	    	
 							row19Struct row19ObjectFromLookup = null;
                          
		           		  	if(!rejectedInnerJoin_tMap_1) { // G_TM_M_020

								
								hasCasePrimitiveKeyWithNull_tMap_1 = false;
								
                        		    		    row19HashKey.city = row13.city;
                        		    		

								
		                        	row19HashKey.hashCodeDirty = true;
                        		
	  					
	  							
			  					
			  					
	  					
		  							tHash_Lookup_row19.lookup( row19HashKey );

	  							

	  							

 								
								  
								  if(!tHash_Lookup_row19.hasNext()) { // G_TM_M_090

  								
		  				
	  								
			  							rejectedInnerJoin_tMap_1 = true;
	  								
						
									
  									  		
 								
								  
								  } // G_TM_M_090

  								



							} // G_TM_M_020
			           		  	  
							
				           		if(tHash_Lookup_row19 != null && tHash_Lookup_row19.getCount(row19HashKey) > 1) { // G 071
			  							
			  						
									 		
									//System.out.println("WARNING: UNIQUE MATCH is configured for the lookup 'row19' and it contains more one result from keys :  row19.city = '" + row19HashKey.city + "'");
								} // G 071
							

							row19Struct row19 = null;
                    		  	 
							   
                    		  	 
	       		  	    	row19Struct fromLookup_row19 = null;
							row19 = row19Default;
										 
							
								 
							
							
								if (tHash_Lookup_row19 !=null && tHash_Lookup_row19.hasNext()) { // G 099
								
							
								
								fromLookup_row19 = tHash_Lookup_row19.next();

							
							
								} // G 099
							
							

							if(fromLookup_row19 != null) {
								row19 = fromLookup_row19;
							}
							
							
							
			  							
								
	                    		  	
		                    
	            	
	           	
	            	
	            	
	            

				///////////////////////////////////////////////
				// Starting Lookup Table "row21" 
				///////////////////////////////////////////////


				
				
                            
 					    boolean forceLooprow21 = false;
       		  	    	
       		  	    	
 							row21Struct row21ObjectFromLookup = null;
                          
		           		  	if(!rejectedInnerJoin_tMap_1) { // G_TM_M_020

								
								hasCasePrimitiveKeyWithNull_tMap_1 = false;
								
                        		    		    row21HashKey.month = TalendDate.formatDate("yyyy-MM", row4.Date) ;
                        		    		

								
		                        	row21HashKey.hashCodeDirty = true;
                        		
	  					
	  							
			  					
			  					
	  					
		  							tHash_Lookup_row21.lookup( row21HashKey );

	  							

	  							

 								
								  
								  if(!tHash_Lookup_row21.hasNext()) { // G_TM_M_090

  								
		  				
	  								
			  							rejectedInnerJoin_tMap_1 = true;
	  								
						
									
  									  		
 								
								  
								  } // G_TM_M_090

  								



							} // G_TM_M_020
			           		  	  
							
				           		if(tHash_Lookup_row21 != null && tHash_Lookup_row21.getCount(row21HashKey) > 1) { // G 071
			  							
			  						
									 		
									//System.out.println("WARNING: UNIQUE MATCH is configured for the lookup 'row21' and it contains more one result from keys :  row21.month = '" + row21HashKey.month + "'");
								} // G 071
							

							row21Struct row21 = null;
                    		  	 
							   
                    		  	 
	       		  	    	row21Struct fromLookup_row21 = null;
							row21 = row21Default;
										 
							
								 
							
							
								if (tHash_Lookup_row21 !=null && tHash_Lookup_row21.hasNext()) { // G 099
								
							
								
								fromLookup_row21 = tHash_Lookup_row21.next();

							
							
								} // G 099
							
							

							if(fromLookup_row21 != null) {
								row21 = fromLookup_row21;
							}
							
							
							
			  							
								
	                    		  	
		                    
	            	
	            	
	            // ###############################
        { // start of Var scope
        
	        // ###############################
        	// # Vars tables
        
Var__tMap_1__Struct Var = Var__tMap_1;// ###############################
        // ###############################
        // # Output tables

out = null;

if(!rejectedInnerJoin_tMap_1 ) {

// # Output table : 'out'
out_tmp.id_reservation = row1.id_reservation ;
out_tmp.id_service = row2.dim_service_id ;
out_tmp.id_event = row3.id_event ;
out_tmp.Date_PK = row4.Date_PK ;
out_tmp.nbr_reservations = row5.reservations == null ? 0 : (int)Math.round(row5.reservations) ;
out_tmp.nbr_visitors = row5.visitors == null ? 0 : (int)Math.round(row5.visitors) ;
out_tmp.id_provider = row6.id_provider ;
out_tmp.id_subcategory = row7.id_subcategory ;
out_tmp.status = row1.status ;
out_tmp.price = row1.final_price ;
out_tmp.id_category = row8.id_category ;
out_tmp.id_weather = row9.id_weather ;
out_tmp.id_trend = row10.id_trend ;
out_tmp.id_concurrence = row11.dim_concurrence_id ;
out_tmp.id_localisation = row13.id_localisation ;
out_tmp.market_count = row14.market_count_googlemaps ;
out_tmp.id_beneficiary = row15.id_beneficiary ;
out_tmp.id_evaluation = row16.id_evaluation ;
out_tmp.id_complaint = row17.id_complaint ;
out_tmp.id_entertainer = row18.id_entertainer ;
out_tmp.id_venue = row19.id_venue ;
out_tmp.marketing_spend = row21.marketing_spend ;
out = out_tmp;
}  // closing inner join bracket (2)
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
	 * [tDBOutput_1 main ] start
	 */

	

	
	
	currentComponent="tDBOutput_1";

	
					if(execStat){
						runStat.updateStatOnConnection(iterateId,1,1
						
							,"out"
						
						);
					}
					



        whetherReject_tDBOutput_1 = false;
            int updateFlag_tDBOutput_1=0;
                    if(out.id_service == null) {
pstmtUpdate_tDBOutput_1.setNull(1, java.sql.Types.INTEGER);
} else {pstmtUpdate_tDBOutput_1.setInt(1, out.id_service);
}

                    if(out.id_event == null) {
pstmtUpdate_tDBOutput_1.setNull(2, java.sql.Types.INTEGER);
} else {pstmtUpdate_tDBOutput_1.setInt(2, out.id_event);
}

                    if(out.Date_PK == null) {
pstmtUpdate_tDBOutput_1.setNull(3, java.sql.Types.INTEGER);
} else {pstmtUpdate_tDBOutput_1.setInt(3, out.Date_PK);
}

                    if(out.nbr_reservations == null) {
pstmtUpdate_tDBOutput_1.setNull(4, java.sql.Types.INTEGER);
} else {pstmtUpdate_tDBOutput_1.setInt(4, out.nbr_reservations);
}

                    if(out.nbr_visitors == null) {
pstmtUpdate_tDBOutput_1.setNull(5, java.sql.Types.INTEGER);
} else {pstmtUpdate_tDBOutput_1.setInt(5, out.nbr_visitors);
}

                    if(out.id_provider == null) {
pstmtUpdate_tDBOutput_1.setNull(6, java.sql.Types.INTEGER);
} else {pstmtUpdate_tDBOutput_1.setInt(6, out.id_provider);
}

                    if(out.id_subcategory == null) {
pstmtUpdate_tDBOutput_1.setNull(7, java.sql.Types.INTEGER);
} else {pstmtUpdate_tDBOutput_1.setInt(7, out.id_subcategory);
}

                    if(out.status == null) {
pstmtUpdate_tDBOutput_1.setNull(8, java.sql.Types.VARCHAR);
} else {pstmtUpdate_tDBOutput_1.setString(8, out.status);
}

                    if(out.price == null) {
pstmtUpdate_tDBOutput_1.setNull(9, java.sql.Types.FLOAT);
} else {pstmtUpdate_tDBOutput_1.setFloat(9, out.price);
}

                    if(out.id_category == null) {
pstmtUpdate_tDBOutput_1.setNull(10, java.sql.Types.INTEGER);
} else {pstmtUpdate_tDBOutput_1.setInt(10, out.id_category);
}

                    if(out.id_weather == null) {
pstmtUpdate_tDBOutput_1.setNull(11, java.sql.Types.INTEGER);
} else {pstmtUpdate_tDBOutput_1.setInt(11, out.id_weather);
}

                    if(out.id_trend == null) {
pstmtUpdate_tDBOutput_1.setNull(12, java.sql.Types.INTEGER);
} else {pstmtUpdate_tDBOutput_1.setInt(12, out.id_trend);
}

                    if(out.id_concurrence == null) {
pstmtUpdate_tDBOutput_1.setNull(13, java.sql.Types.INTEGER);
} else {pstmtUpdate_tDBOutput_1.setInt(13, out.id_concurrence);
}

                    if(out.id_localisation == null) {
pstmtUpdate_tDBOutput_1.setNull(14, java.sql.Types.INTEGER);
} else {pstmtUpdate_tDBOutput_1.setInt(14, out.id_localisation);
}

                    if(out.market_count == null) {
pstmtUpdate_tDBOutput_1.setNull(15, java.sql.Types.INTEGER);
} else {pstmtUpdate_tDBOutput_1.setInt(15, out.market_count);
}

                    if(out.id_beneficiary == null) {
pstmtUpdate_tDBOutput_1.setNull(16, java.sql.Types.INTEGER);
} else {pstmtUpdate_tDBOutput_1.setInt(16, out.id_beneficiary);
}

                    if(out.id_evaluation == null) {
pstmtUpdate_tDBOutput_1.setNull(17, java.sql.Types.INTEGER);
} else {pstmtUpdate_tDBOutput_1.setInt(17, out.id_evaluation);
}

                    if(out.id_complaint == null) {
pstmtUpdate_tDBOutput_1.setNull(18, java.sql.Types.INTEGER);
} else {pstmtUpdate_tDBOutput_1.setInt(18, out.id_complaint);
}

                    if(out.id_entertainer == null) {
pstmtUpdate_tDBOutput_1.setNull(19, java.sql.Types.INTEGER);
} else {pstmtUpdate_tDBOutput_1.setInt(19, out.id_entertainer);
}

                    if(out.id_venue == null) {
pstmtUpdate_tDBOutput_1.setNull(20, java.sql.Types.INTEGER);
} else {pstmtUpdate_tDBOutput_1.setInt(20, out.id_venue);
}

                    if(out.marketing_spend == null) {
pstmtUpdate_tDBOutput_1.setNull(21, java.sql.Types.FLOAT);
} else {pstmtUpdate_tDBOutput_1.setFloat(21, out.marketing_spend);
}


                    if(out.id_reservation == null) {
pstmtUpdate_tDBOutput_1.setNull(22 + count_tDBOutput_1, java.sql.Types.INTEGER);
} else {pstmtUpdate_tDBOutput_1.setInt(22 + count_tDBOutput_1, out.id_reservation);
}

            try {
                updateFlag_tDBOutput_1=pstmtUpdate_tDBOutput_1.executeUpdate();
                updatedCount_tDBOutput_1 = updatedCount_tDBOutput_1+updateFlag_tDBOutput_1;
                rowsToCommitCount_tDBOutput_1 += updateFlag_tDBOutput_1;
            if(updateFlag_tDBOutput_1 == 0) {
            	
                        if(out.id_reservation == null) {
pstmtInsert_tDBOutput_1.setNull(1, java.sql.Types.INTEGER);
} else {pstmtInsert_tDBOutput_1.setInt(1, out.id_reservation);
}

                        if(out.id_service == null) {
pstmtInsert_tDBOutput_1.setNull(2, java.sql.Types.INTEGER);
} else {pstmtInsert_tDBOutput_1.setInt(2, out.id_service);
}

                        if(out.id_event == null) {
pstmtInsert_tDBOutput_1.setNull(3, java.sql.Types.INTEGER);
} else {pstmtInsert_tDBOutput_1.setInt(3, out.id_event);
}

                        if(out.Date_PK == null) {
pstmtInsert_tDBOutput_1.setNull(4, java.sql.Types.INTEGER);
} else {pstmtInsert_tDBOutput_1.setInt(4, out.Date_PK);
}

                        if(out.nbr_reservations == null) {
pstmtInsert_tDBOutput_1.setNull(5, java.sql.Types.INTEGER);
} else {pstmtInsert_tDBOutput_1.setInt(5, out.nbr_reservations);
}

                        if(out.nbr_visitors == null) {
pstmtInsert_tDBOutput_1.setNull(6, java.sql.Types.INTEGER);
} else {pstmtInsert_tDBOutput_1.setInt(6, out.nbr_visitors);
}

                        if(out.id_provider == null) {
pstmtInsert_tDBOutput_1.setNull(7, java.sql.Types.INTEGER);
} else {pstmtInsert_tDBOutput_1.setInt(7, out.id_provider);
}

                        if(out.id_subcategory == null) {
pstmtInsert_tDBOutput_1.setNull(8, java.sql.Types.INTEGER);
} else {pstmtInsert_tDBOutput_1.setInt(8, out.id_subcategory);
}

                        if(out.status == null) {
pstmtInsert_tDBOutput_1.setNull(9, java.sql.Types.VARCHAR);
} else {pstmtInsert_tDBOutput_1.setString(9, out.status);
}

                        if(out.price == null) {
pstmtInsert_tDBOutput_1.setNull(10, java.sql.Types.FLOAT);
} else {pstmtInsert_tDBOutput_1.setFloat(10, out.price);
}

                        if(out.id_category == null) {
pstmtInsert_tDBOutput_1.setNull(11, java.sql.Types.INTEGER);
} else {pstmtInsert_tDBOutput_1.setInt(11, out.id_category);
}

                        if(out.id_weather == null) {
pstmtInsert_tDBOutput_1.setNull(12, java.sql.Types.INTEGER);
} else {pstmtInsert_tDBOutput_1.setInt(12, out.id_weather);
}

                        if(out.id_trend == null) {
pstmtInsert_tDBOutput_1.setNull(13, java.sql.Types.INTEGER);
} else {pstmtInsert_tDBOutput_1.setInt(13, out.id_trend);
}

                        if(out.id_concurrence == null) {
pstmtInsert_tDBOutput_1.setNull(14, java.sql.Types.INTEGER);
} else {pstmtInsert_tDBOutput_1.setInt(14, out.id_concurrence);
}

                        if(out.id_localisation == null) {
pstmtInsert_tDBOutput_1.setNull(15, java.sql.Types.INTEGER);
} else {pstmtInsert_tDBOutput_1.setInt(15, out.id_localisation);
}

                        if(out.market_count == null) {
pstmtInsert_tDBOutput_1.setNull(16, java.sql.Types.INTEGER);
} else {pstmtInsert_tDBOutput_1.setInt(16, out.market_count);
}

                        if(out.id_beneficiary == null) {
pstmtInsert_tDBOutput_1.setNull(17, java.sql.Types.INTEGER);
} else {pstmtInsert_tDBOutput_1.setInt(17, out.id_beneficiary);
}

                        if(out.id_evaluation == null) {
pstmtInsert_tDBOutput_1.setNull(18, java.sql.Types.INTEGER);
} else {pstmtInsert_tDBOutput_1.setInt(18, out.id_evaluation);
}

                        if(out.id_complaint == null) {
pstmtInsert_tDBOutput_1.setNull(19, java.sql.Types.INTEGER);
} else {pstmtInsert_tDBOutput_1.setInt(19, out.id_complaint);
}

                        if(out.id_entertainer == null) {
pstmtInsert_tDBOutput_1.setNull(20, java.sql.Types.INTEGER);
} else {pstmtInsert_tDBOutput_1.setInt(20, out.id_entertainer);
}

                        if(out.id_venue == null) {
pstmtInsert_tDBOutput_1.setNull(21, java.sql.Types.INTEGER);
} else {pstmtInsert_tDBOutput_1.setInt(21, out.id_venue);
}

                        if(out.marketing_spend == null) {
pstmtInsert_tDBOutput_1.setNull(22, java.sql.Types.FLOAT);
} else {pstmtInsert_tDBOutput_1.setFloat(22, out.marketing_spend);
}

                    int processedCount_tDBOutput_1 = pstmtInsert_tDBOutput_1.executeUpdate();
                    insertedCount_tDBOutput_1 += processedCount_tDBOutput_1;
                    rowsToCommitCount_tDBOutput_1 += processedCount_tDBOutput_1;
                    nb_line_tDBOutput_1++;
    			}else{
    				nb_line_tDBOutput_1++;
    				
    			}
                } catch(java.lang.Exception e) {
globalMap.put("tDBOutput_1_ERROR_MESSAGE",e.getMessage());
                    whetherReject_tDBOutput_1 = true;
                    	nb_line_tDBOutput_1++;
                    	
                            System.err.println(e.getMessage());
                } finally {
				    
				}
            	//////////batch execute by batch size///////
            	class LimitBytesHelper_tDBOutput_1{
            		public int limitBytePart1(int counter,java.sql.PreparedStatement pstmt_tDBOutput_1) throws Exception {
                try {
						
						for(int countEach_tDBOutput_1: pstmt_tDBOutput_1.executeBatch()) {
							if(countEach_tDBOutput_1 == -2 || countEach_tDBOutput_1 == -3) {
								break;
							}
							counter += countEach_tDBOutput_1;
						}
						
                }catch (java.sql.BatchUpdateException e){
globalMap.put("tDBOutput_1_ERROR_MESSAGE",e.getMessage());
                	
                	int countSum_tDBOutput_1 = 0;
					for(int countEach_tDBOutput_1: e.getUpdateCounts()) {
						counter += (countEach_tDBOutput_1 < 0 ? 0 : countEach_tDBOutput_1);
					}
				
            	    	
                		System.err.println(e.getMessage());
                	
               			 }
    				return counter;
            	}
            	
            	public int limitBytePart2(int counter,java.sql.PreparedStatement pstmt_tDBOutput_1) throws Exception {
                try {
                		
						for(int countEach_tDBOutput_1: pstmt_tDBOutput_1.executeBatch()) {
							if(countEach_tDBOutput_1 == -2 || countEach_tDBOutput_1 == -3) {
								break;
							}
							counter += countEach_tDBOutput_1;
						}
						
                }catch (java.sql.BatchUpdateException e){
globalMap.put("tDBOutput_1_ERROR_MESSAGE",e.getMessage());
                	
                	
					for(int countEach_tDBOutput_1: e.getUpdateCounts()) {
						counter += (countEach_tDBOutput_1 < 0 ? 0 : countEach_tDBOutput_1);
					}
					
            	    	
                        System.err.println(e.getMessage());
                	
                		}	
                	return counter;	
            	}
            }

    	////////////commit every////////////
    			
    		    commitCounter_tDBOutput_1++;
                if(commitEvery_tDBOutput_1 <= commitCounter_tDBOutput_1) {
                if(rowsToCommitCount_tDBOutput_1 != 0){
                	
                }
                conn_tDBOutput_1.commit();
                if(rowsToCommitCount_tDBOutput_1 != 0){
                	
                	rowsToCommitCount_tDBOutput_1 = 0;	
                }
                commitCounter_tDBOutput_1=0;
                }

 


	tos_count_tDBOutput_1++;

/**
 * [tDBOutput_1 main ] stop
 */
	
	/**
	 * [tDBOutput_1 process_data_begin ] start
	 */

	

	
	
	currentComponent="tDBOutput_1";

	

 



/**
 * [tDBOutput_1 process_data_begin ] stop
 */
	
	/**
	 * [tDBOutput_1 process_data_end ] start
	 */

	

	
	
	currentComponent="tDBOutput_1";

	

 



/**
 * [tDBOutput_1 process_data_end ] stop
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
					if(tHash_Lookup_row2 != null) {
						tHash_Lookup_row2.endGet();
					}
					globalMap.remove( "tHash_Lookup_row2" );

					
					
				
					if(tHash_Lookup_row3 != null) {
						tHash_Lookup_row3.endGet();
					}
					globalMap.remove( "tHash_Lookup_row3" );

					
					
				
					if(tHash_Lookup_row4 != null) {
						tHash_Lookup_row4.endGet();
					}
					globalMap.remove( "tHash_Lookup_row4" );

					
					
				
					if(tHash_Lookup_row5 != null) {
						tHash_Lookup_row5.endGet();
					}
					globalMap.remove( "tHash_Lookup_row5" );

					
					
				
					if(tHash_Lookup_row6 != null) {
						tHash_Lookup_row6.endGet();
					}
					globalMap.remove( "tHash_Lookup_row6" );

					
					
				
					if(tHash_Lookup_row7 != null) {
						tHash_Lookup_row7.endGet();
					}
					globalMap.remove( "tHash_Lookup_row7" );

					
					
				
					if(tHash_Lookup_row8 != null) {
						tHash_Lookup_row8.endGet();
					}
					globalMap.remove( "tHash_Lookup_row8" );

					
					
				
					if(tHash_Lookup_row9 != null) {
						tHash_Lookup_row9.endGet();
					}
					globalMap.remove( "tHash_Lookup_row9" );

					
					
				
					if(tHash_Lookup_row10 != null) {
						tHash_Lookup_row10.endGet();
					}
					globalMap.remove( "tHash_Lookup_row10" );

					
					
				
					if(tHash_Lookup_row11 != null) {
						tHash_Lookup_row11.endGet();
					}
					globalMap.remove( "tHash_Lookup_row11" );

					
					
				
					if(tHash_Lookup_row12 != null) {
						tHash_Lookup_row12.endGet();
					}
					globalMap.remove( "tHash_Lookup_row12" );

					
					
				
					if(tHash_Lookup_row13 != null) {
						tHash_Lookup_row13.endGet();
					}
					globalMap.remove( "tHash_Lookup_row13" );

					
					
				
					if(tHash_Lookup_row14 != null) {
						tHash_Lookup_row14.endGet();
					}
					globalMap.remove( "tHash_Lookup_row14" );

					
					
				
					if(tHash_Lookup_row15 != null) {
						tHash_Lookup_row15.endGet();
					}
					globalMap.remove( "tHash_Lookup_row15" );

					
					
				
					if(tHash_Lookup_row16 != null) {
						tHash_Lookup_row16.endGet();
					}
					globalMap.remove( "tHash_Lookup_row16" );

					
					
				
					if(tHash_Lookup_row17 != null) {
						tHash_Lookup_row17.endGet();
					}
					globalMap.remove( "tHash_Lookup_row17" );

					
					
				
					if(tHash_Lookup_row18 != null) {
						tHash_Lookup_row18.endGet();
					}
					globalMap.remove( "tHash_Lookup_row18" );

					
					
				
					if(tHash_Lookup_row19 != null) {
						tHash_Lookup_row19.endGet();
					}
					globalMap.remove( "tHash_Lookup_row19" );

					
					
				
					if(tHash_Lookup_row21 != null) {
						tHash_Lookup_row21.endGet();
					}
					globalMap.remove( "tHash_Lookup_row21" );

					
					
				
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
	 * [tDBOutput_1 end ] start
	 */

	

	
	
	currentComponent="tDBOutput_1";

	



        if(pstmtUpdate_tDBOutput_1 != null){
            pstmtUpdate_tDBOutput_1.close();
            resourceMap.remove("pstmtUpdate_tDBOutput_1");
        }
        if(pstmtInsert_tDBOutput_1 != null){
            pstmtInsert_tDBOutput_1.close();
            resourceMap.remove("pstmtInsert_tDBOutput_1");
        }
    resourceMap.put("statementClosed_tDBOutput_1", true);
            if(rowsToCommitCount_tDBOutput_1 != 0){
            	
            }
            conn_tDBOutput_1.commit();
            if(rowsToCommitCount_tDBOutput_1 != 0){
            	
            	rowsToCommitCount_tDBOutput_1 = 0;
            }
            commitCounter_tDBOutput_1 = 0;
        conn_tDBOutput_1 .close();
        resourceMap.put("finish_tDBOutput_1", true);

	nb_line_deleted_tDBOutput_1=nb_line_deleted_tDBOutput_1+ deletedCount_tDBOutput_1;
	nb_line_update_tDBOutput_1=nb_line_update_tDBOutput_1 + updatedCount_tDBOutput_1;
	nb_line_inserted_tDBOutput_1=nb_line_inserted_tDBOutput_1 + insertedCount_tDBOutput_1;
	nb_line_rejected_tDBOutput_1=nb_line_rejected_tDBOutput_1 + rejectedCount_tDBOutput_1;
	
        globalMap.put("tDBOutput_1_NB_LINE",nb_line_tDBOutput_1);
        globalMap.put("tDBOutput_1_NB_LINE_UPDATED",nb_line_update_tDBOutput_1);
        globalMap.put("tDBOutput_1_NB_LINE_INSERTED",nb_line_inserted_tDBOutput_1);
        globalMap.put("tDBOutput_1_NB_LINE_DELETED",nb_line_deleted_tDBOutput_1);
        globalMap.put("tDBOutput_1_NB_LINE_REJECTED", nb_line_rejected_tDBOutput_1);
    

	

				if(execStat){
			  		runStat.updateStat(resourceMap,iterateId,2,0,"out");
			  	}
			  	
 

ok_Hash.put("tDBOutput_1", true);
end_Hash.put("tDBOutput_1", System.currentTimeMillis());




/**
 * [tDBOutput_1 end ] stop
 */






				}//end the resume

				



	
			}catch(java.lang.Exception e){	
				
				TalendException te = new TalendException(e, currentComponent, globalMap);
				
				throw te;
			}catch(java.lang.Error error){	
				
					runStat.stopThreadStat();
				
				throw error;
			}finally{
				
					     			//free memory for "tMap_1"
					     			globalMap.remove("tHash_Lookup_row2"); 
				     			
					     			//free memory for "tMap_1"
					     			globalMap.remove("tHash_Lookup_row3"); 
				     			
					     			//free memory for "tMap_1"
					     			globalMap.remove("tHash_Lookup_row4"); 
				     			
					     			//free memory for "tMap_1"
					     			globalMap.remove("tHash_Lookup_row5"); 
				     			
					     			//free memory for "tMap_1"
					     			globalMap.remove("tHash_Lookup_row6"); 
				     			
					     			//free memory for "tMap_1"
					     			globalMap.remove("tHash_Lookup_row7"); 
				     			
					     			//free memory for "tMap_1"
					     			globalMap.remove("tHash_Lookup_row8"); 
				     			
					     			//free memory for "tMap_1"
					     			globalMap.remove("tHash_Lookup_row9"); 
				     			
					     			//free memory for "tMap_1"
					     			globalMap.remove("tHash_Lookup_row10"); 
				     			
					     			//free memory for "tMap_1"
					     			globalMap.remove("tHash_Lookup_row11"); 
				     			
					     			//free memory for "tMap_1"
					     			globalMap.remove("tHash_Lookup_row12"); 
				     			
					     			//free memory for "tMap_1"
					     			globalMap.remove("tHash_Lookup_row13"); 
				     			
					     			//free memory for "tMap_1"
					     			globalMap.remove("tHash_Lookup_row14"); 
				     			
					     			//free memory for "tMap_1"
					     			globalMap.remove("tHash_Lookup_row15"); 
				     			
					     			//free memory for "tMap_1"
					     			globalMap.remove("tHash_Lookup_row16"); 
				     			
					     			//free memory for "tMap_1"
					     			globalMap.remove("tHash_Lookup_row17"); 
				     			
					     			//free memory for "tMap_1"
					     			globalMap.remove("tHash_Lookup_row18"); 
				     			
					     			//free memory for "tMap_1"
					     			globalMap.remove("tHash_Lookup_row19"); 
				     			
					     			//free memory for "tMap_1"
					     			globalMap.remove("tHash_Lookup_row21"); 
				     			
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
	 * [tDBOutput_1 finally ] start
	 */

	

	
	
	currentComponent="tDBOutput_1";

	



    try {
    if (resourceMap.get("statementClosed_tDBOutput_1") == null) {
                java.sql.PreparedStatement pstmtUpdateToClose_tDBOutput_1 = null;
                if ((pstmtUpdateToClose_tDBOutput_1 = (java.sql.PreparedStatement) resourceMap.remove("pstmtUpdate_tDBOutput_1")) != null) {
                    pstmtUpdateToClose_tDBOutput_1.close();
                }
                java.sql.PreparedStatement pstmtInsertToClose_tDBOutput_1 = null;
                if ((pstmtInsertToClose_tDBOutput_1 = (java.sql.PreparedStatement) resourceMap.remove("pstmtInsert_tDBOutput_1")) != null) {
                    pstmtInsertToClose_tDBOutput_1.close();
                }
    }
    } finally {
        if(resourceMap.get("finish_tDBOutput_1") == null){
            java.sql.Connection ctn_tDBOutput_1 = null;
            if((ctn_tDBOutput_1 = (java.sql.Connection)resourceMap.get("conn_tDBOutput_1")) != null){
                try {
                    ctn_tDBOutput_1.close();
                } catch (java.sql.SQLException sqlEx_tDBOutput_1) {
                    String errorMessage_tDBOutput_1 = "failed to close the connection in tDBOutput_1 :" + sqlEx_tDBOutput_1.getMessage();
                    System.err.println(errorMessage_tDBOutput_1);
                }
            }
        }
    }
 



/**
 * [tDBOutput_1 finally ] stop
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
	


public static class row2Struct implements routines.system.IPersistableComparableLookupRow<row2Struct> {
    final static byte[] commonByteArrayLock_TEST_Fact2 = new byte[0];
    static byte[] commonByteArray_TEST_Fact2 = new byte[0];
	protected static final int DEFAULT_HASHCODE = 1;
    protected static final int PRIME = 31;
    protected int hashCode = DEFAULT_HASHCODE;
    public boolean hashCodeDirty = true;

    public String loopKey;



	
			    public Integer dim_service_id;

				public Integer getDim_service_id () {
					return this.dim_service_id;
				}
				
			    public Integer id_service;

				public Integer getId_service () {
					return this.id_service;
				}
				


	@Override
	public int hashCode() {
		if (this.hashCodeDirty) {
			final int prime = PRIME;
			int result = DEFAULT_HASHCODE;
	
						result = prime * result + ((this.id_service == null) ? 0 : this.id_service.hashCode());
					
    		this.hashCode = result;
    		this.hashCodeDirty = false;
		}
		return this.hashCode;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		final row2Struct other = (row2Struct) obj;
		
						if (this.id_service == null) {
							if (other.id_service != null)
								return false;
						
						} else if (!this.id_service.equals(other.id_service))
						
							return false;
					

		return true;
    }

	public void copyDataTo(row2Struct other) {

		other.dim_service_id = this.dim_service_id;
	            other.id_service = this.id_service;
	            
	}

	public void copyKeysDataTo(row2Struct other) {

		other.id_service = this.id_service;
	            	
	}



	private Integer readInteger(DataInputStream dis, ObjectInputStream ois) throws IOException{
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
	
	private Integer readInteger(DataInputStream dis, org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException{
		Integer intReturn;
        int length = 0;
        length = unmarshaller.readByte();
		if (length == -1) {
			intReturn = null;
		} else {
			intReturn = unmarshaller.readInt();
		}
		return intReturn;
	}

	private void writeInteger(Integer intNum, DataOutputStream dos, ObjectOutputStream oos) throws IOException{
		if(intNum == null) {
            dos.writeByte(-1);
		} else {
			dos.writeByte(0);
	    	dos.writeInt(intNum);
    	}
	}
	
	private void writeInteger(Integer intNum, DataOutputStream dos,org.jboss.marshalling.Marshaller marshaller ) throws IOException{
		if(intNum == null) {
			marshaller.writeByte(-1);
		} else {
			marshaller.writeByte(0);
			marshaller.writeInt(intNum);
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

    public void readKeysData(ObjectInputStream dis) {

		synchronized(commonByteArrayLock_TEST_Fact2) {

        	try {

        		int length = 0;
		
						this.id_service = readInteger(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }
    
    public void readKeysData(org.jboss.marshalling.Unmarshaller dis) {

		synchronized(commonByteArrayLock_TEST_Fact2) {

        	try {

        		int length = 0;
		
						this.id_service = readInteger(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }

    public void writeKeysData(ObjectOutputStream dos) {
        try {

		
					// Integer
				
						writeInteger(this.id_service,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }
    
    public void writeKeysData(org.jboss.marshalling.Marshaller dos) {
        try {

		
					// Integer
				
						writeInteger(this.id_service,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }



    /**
     * Fill Values data by reading ObjectInputStream.
     */
    public void readValuesData(DataInputStream dis, ObjectInputStream ois) {
        try {

			int length = 0;
		
						this.dim_service_id = readInteger(dis,ois);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

    }
    
    public void readValuesData(DataInputStream dis, org.jboss.marshalling.Unmarshaller objectIn) {
        try {
			int length = 0;
		
						this.dim_service_id = readInteger(dis,objectIn);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

    }

    /**
     * Return a byte array which represents Values data.
     */
    public void writeValuesData(DataOutputStream dos, ObjectOutputStream oos) {
        try {

		
					writeInteger(this.dim_service_id, dos, oos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        	}

    }
    
    public void writeValuesData(DataOutputStream dos, org.jboss.marshalling.Marshaller objectOut){
                try {

		
					writeInteger(this.dim_service_id, dos, objectOut);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        	}
    }


    
    public boolean supportMarshaller(){
        return true;
    }


    public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("[");
		sb.append("dim_service_id="+String.valueOf(dim_service_id));
		sb.append(",id_service="+String.valueOf(id_service));
	    sb.append("]");

	    return sb.toString();
    }

    /**
     * Compare keys
     */
    public int compareTo(row2Struct other) {

		int returnValue = -1;
		
						returnValue = checkNullsAndCompare(this.id_service, other.id_service);
						if(returnValue != 0) {
							return returnValue;
						}

					
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
public void tDBInput_2Process(final java.util.Map<String, Object> globalMap) throws TalendException {
	globalMap.put("tDBInput_2_SUBPROCESS_STATE", 0);

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



		row2Struct row2 = new row2Struct();




	
	/**
	 * [tAdvancedHash_row2 begin ] start
	 */

	

	
		
		ok_Hash.put("tAdvancedHash_row2", false);
		start_Hash.put("tAdvancedHash_row2", System.currentTimeMillis());
		
	
	currentComponent="tAdvancedHash_row2";

	
					if(execStat) {
						runStat.updateStatOnConnection(resourceMap,iterateId,0,0,"row2");
					}
				
		int tos_count_tAdvancedHash_row2 = 0;
		

			   		// connection name:row2
			   		// source node:tDBInput_2 - inputs:(after_tDBInput_1) outputs:(row2,row2) | target node:tAdvancedHash_row2 - inputs:(row2) outputs:()
			   		// linked node: tMap_1 - inputs:(row1,row2,row3,row4,row5,row6,row7,row8,row9,row10,row11,row12,row13,row14,row15,row16,row17,row18,row19,row21) outputs:(out)
			   
			   		org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE matchingModeEnum_row2 = 
			   			org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE.UNIQUE_MATCH;
			   			
			   
	   			org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row2Struct> tHash_Lookup_row2 =org.talend.designer.components.lookup.memory.AdvancedMemoryLookup.
	   						<row2Struct>getLookup(matchingModeEnum_row2);
	   						   
		   	   	   globalMap.put("tHash_Lookup_row2", tHash_Lookup_row2);
		   	   	   
				
           

 



/**
 * [tAdvancedHash_row2 begin ] stop
 */



	
	/**
	 * [tDBInput_2 begin ] start
	 */

	

	
		
		ok_Hash.put("tDBInput_2", false);
		start_Hash.put("tDBInput_2", System.currentTimeMillis());
		
	
	currentComponent="tDBInput_2";

	
		int tos_count_tDBInput_2 = 0;
		
	
    
	
			org.talend.designer.components.util.mssql.MSSqlGenerateTimestampUtil mssqlGTU_tDBInput_2 = org.talend.designer.components.util.mssql.MSSqlUtilFactory.getMSSqlGenerateTimestampUtil();
			
			java.util.List<String> talendToDBList_tDBInput_2 = new java.util.ArrayList();
			String[] talendToDBArray_tDBInput_2  = new String[]{"FLOAT","NUMERIC","NUMERIC IDENTITY","DECIMAL","DECIMAL IDENTITY","REAL"}; 
			java.util.Collections.addAll(talendToDBList_tDBInput_2, talendToDBArray_tDBInput_2); 
		    int nb_line_tDBInput_2 = 0;
		    java.sql.Connection conn_tDBInput_2 = null;
				String driverClass_tDBInput_2 = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
			    java.lang.Class jdbcclazz_tDBInput_2 = java.lang.Class.forName(driverClass_tDBInput_2);
				String dbUser_tDBInput_2 = "bi";
				
				 
	final String decryptedPassword_tDBInput_2 = routines.system.PasswordEncryptUtil.decryptPassword("enc:routine.encryption.key.v1:+l0+dUj0phzgapBPaE53Yg3MUcb1LvFg36g+Euve");
				
				String dbPwd_tDBInput_2 = decryptedPassword_tDBInput_2;
				
		    String port_tDBInput_2 = "1433";
		    String dbname_tDBInput_2 = "event_DWH" ;		    
		    String url_tDBInput_2 = "jdbc:sqlserver://" + "localhost" ;
		    if (!"".equals(port_tDBInput_2)) {
		    	url_tDBInput_2 += ":" + "1433";
		    }
		    if (!"".equals(dbname_tDBInput_2)) {		    
		    	url_tDBInput_2 += ";databaseName=" + "event_DWH"; 
		    }
		    url_tDBInput_2 += ";appName=" + projectName + ";" + "trustServerCertificate=true";
		    String dbschema_tDBInput_2 = "dbo";
				
				conn_tDBInput_2 = java.sql.DriverManager.getConnection(url_tDBInput_2,dbUser_tDBInput_2,dbPwd_tDBInput_2);
		        
		    
			java.sql.Statement stmt_tDBInput_2 = conn_tDBInput_2.createStatement();

		    String dbquery_tDBInput_2 = "SELECT dbo.Dim_Service01.dim_service_id,\n		dbo.Dim_Service01.id_service\nFROM	dbo.Dim_Service01";
		    

            	globalMap.put("tDBInput_2_QUERY",dbquery_tDBInput_2);
		    java.sql.ResultSet rs_tDBInput_2 = null;

		    try {
		    	rs_tDBInput_2 = stmt_tDBInput_2.executeQuery(dbquery_tDBInput_2);
		    	java.sql.ResultSetMetaData rsmd_tDBInput_2 = rs_tDBInput_2.getMetaData();
		    	int colQtyInRs_tDBInput_2 = rsmd_tDBInput_2.getColumnCount();

		    String tmpContent_tDBInput_2 = null;
		    
		    
		    while (rs_tDBInput_2.next()) {
		        nb_line_tDBInput_2++;
		        
							if(colQtyInRs_tDBInput_2 < 1) {
								row2.dim_service_id = null;
							} else {
		                          
            row2.dim_service_id = rs_tDBInput_2.getInt(1);
            if(rs_tDBInput_2.wasNull()){
                    row2.dim_service_id = null;
            }
		                    }
							if(colQtyInRs_tDBInput_2 < 2) {
								row2.id_service = null;
							} else {
		                          
            row2.id_service = rs_tDBInput_2.getInt(2);
            if(rs_tDBInput_2.wasNull()){
                    row2.id_service = null;
            }
		                    }
					





 



/**
 * [tDBInput_2 begin ] stop
 */
	
	/**
	 * [tDBInput_2 main ] start
	 */

	

	
	
	currentComponent="tDBInput_2";

	

 


	tos_count_tDBInput_2++;

/**
 * [tDBInput_2 main ] stop
 */
	
	/**
	 * [tDBInput_2 process_data_begin ] start
	 */

	

	
	
	currentComponent="tDBInput_2";

	

 



/**
 * [tDBInput_2 process_data_begin ] stop
 */

	
	/**
	 * [tAdvancedHash_row2 main ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row2";

	
					if(execStat){
						runStat.updateStatOnConnection(iterateId,1,1
						
							,"row2"
						
						);
					}
					


			   
			   

					row2Struct row2_HashRow = new row2Struct();
		   	   	   
				
				row2_HashRow.dim_service_id = row2.dim_service_id;
				
				row2_HashRow.id_service = row2.id_service;
				
			tHash_Lookup_row2.put(row2_HashRow);
			
            




 


	tos_count_tAdvancedHash_row2++;

/**
 * [tAdvancedHash_row2 main ] stop
 */
	
	/**
	 * [tAdvancedHash_row2 process_data_begin ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row2";

	

 



/**
 * [tAdvancedHash_row2 process_data_begin ] stop
 */
	
	/**
	 * [tAdvancedHash_row2 process_data_end ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row2";

	

 



/**
 * [tAdvancedHash_row2 process_data_end ] stop
 */



	
	/**
	 * [tDBInput_2 process_data_end ] start
	 */

	

	
	
	currentComponent="tDBInput_2";

	

 



/**
 * [tDBInput_2 process_data_end ] stop
 */
	
	/**
	 * [tDBInput_2 end ] start
	 */

	

	
	
	currentComponent="tDBInput_2";

	

	}
}finally{
	if (rs_tDBInput_2 != null) {
		rs_tDBInput_2.close();
	}
	if (stmt_tDBInput_2 != null) {
		stmt_tDBInput_2.close();
	}
		if(conn_tDBInput_2 != null && !conn_tDBInput_2.isClosed()) {
			
			conn_tDBInput_2.close();
			
			if("com.mysql.cj.jdbc.Driver".equals((String)globalMap.get("driverClass_"))
			    && routines.system.BundleUtils.inOSGi()) {
			        Class.forName("com.mysql.cj.jdbc.AbandonedConnectionCleanupThread").
			            getMethod("checkedShutdown").invoke(null, (Object[]) null);
			}
			
		}
}
globalMap.put("tDBInput_2_NB_LINE",nb_line_tDBInput_2);

 

ok_Hash.put("tDBInput_2", true);
end_Hash.put("tDBInput_2", System.currentTimeMillis());




/**
 * [tDBInput_2 end ] stop
 */

	
	/**
	 * [tAdvancedHash_row2 end ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row2";

	

tHash_Lookup_row2.endPut();

				if(execStat){
			  		runStat.updateStat(resourceMap,iterateId,2,0,"row2");
			  	}
			  	
 

ok_Hash.put("tAdvancedHash_row2", true);
end_Hash.put("tAdvancedHash_row2", System.currentTimeMillis());




/**
 * [tAdvancedHash_row2 end ] stop
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
	 * [tDBInput_2 finally ] start
	 */

	

	
	
	currentComponent="tDBInput_2";

	

 



/**
 * [tDBInput_2 finally ] stop
 */

	
	/**
	 * [tAdvancedHash_row2 finally ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row2";

	

 



/**
 * [tAdvancedHash_row2 finally ] stop
 */



				}catch(java.lang.Exception e){	
					//ignore
				}catch(java.lang.Error error){
					//ignore
				}
				resourceMap = null;
			}
		

		globalMap.put("tDBInput_2_SUBPROCESS_STATE", 1);
	}
	


public static class row3Struct implements routines.system.IPersistableComparableLookupRow<row3Struct> {
    final static byte[] commonByteArrayLock_TEST_Fact2 = new byte[0];
    static byte[] commonByteArray_TEST_Fact2 = new byte[0];
	protected static final int DEFAULT_HASHCODE = 1;
    protected static final int PRIME = 31;
    protected int hashCode = DEFAULT_HASHCODE;
    public boolean hashCodeDirty = true;

    public String loopKey;



	
			    public Integer id_event;

				public Integer getId_event () {
					return this.id_event;
				}
				
			    public String type;

				public String getType () {
					return this.type;
				}
				


	@Override
	public int hashCode() {
		if (this.hashCodeDirty) {
			final int prime = PRIME;
			int result = DEFAULT_HASHCODE;
	
						result = prime * result + ((this.id_event == null) ? 0 : this.id_event.hashCode());
					
    		this.hashCode = result;
    		this.hashCodeDirty = false;
		}
		return this.hashCode;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		final row3Struct other = (row3Struct) obj;
		
						if (this.id_event == null) {
							if (other.id_event != null)
								return false;
						
						} else if (!this.id_event.equals(other.id_event))
						
							return false;
					

		return true;
    }

	public void copyDataTo(row3Struct other) {

		other.id_event = this.id_event;
	            other.type = this.type;
	            
	}

	public void copyKeysDataTo(row3Struct other) {

		other.id_event = this.id_event;
	            	
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
	
	private String readString(DataInputStream dis, ObjectInputStream ois) throws IOException{
		String strReturn = null;
		int length = 0;
        length = dis.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			byte[] byteArray = new byte[length];
			dis.read(byteArray);
			strReturn = new String(byteArray, utf8Charset);
		}
		return strReturn;
	}
	
	private String readString(DataInputStream dis, org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException{
		String strReturn = null;
		int length = 0;
        length = unmarshaller.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			byte[] byteArray = new byte[length];
			unmarshaller.read(byteArray);
			strReturn = new String(byteArray, utf8Charset);
		}
		return strReturn;
	}
	
	private void writeString(String str, DataOutputStream dos, org.jboss.marshalling.Marshaller marshaller) throws IOException{
		if(str == null) {
			marshaller.writeInt(-1);
		} else {
            byte[] byteArray = str.getBytes(utf8Charset);
            marshaller.writeInt(byteArray.length);
            marshaller.write(byteArray);
    	}
	}

	private void writeString(String str, DataOutputStream dos, ObjectOutputStream oos) throws IOException{
		if(str == null) {
            dos.writeInt(-1);
		} else {
            byte[] byteArray = str.getBytes(utf8Charset);
	    	dos.writeInt(byteArray.length);
			dos.write(byteArray);
    	}
	}

    public void readKeysData(ObjectInputStream dis) {

		synchronized(commonByteArrayLock_TEST_Fact2) {

        	try {

        		int length = 0;
		
						this.id_event = readInteger(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }
    
    public void readKeysData(org.jboss.marshalling.Unmarshaller dis) {

		synchronized(commonByteArrayLock_TEST_Fact2) {

        	try {

        		int length = 0;
		
						this.id_event = readInteger(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }

    public void writeKeysData(ObjectOutputStream dos) {
        try {

		
					// Integer
				
						writeInteger(this.id_event,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }
    
    public void writeKeysData(org.jboss.marshalling.Marshaller dos) {
        try {

		
					// Integer
				
						writeInteger(this.id_event,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }



    /**
     * Fill Values data by reading ObjectInputStream.
     */
    public void readValuesData(DataInputStream dis, ObjectInputStream ois) {
        try {

			int length = 0;
		
						this.type = readString(dis,ois);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

    }
    
    public void readValuesData(DataInputStream dis, org.jboss.marshalling.Unmarshaller objectIn) {
        try {
			int length = 0;
		
						this.type = readString(dis,objectIn);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

    }

    /**
     * Return a byte array which represents Values data.
     */
    public void writeValuesData(DataOutputStream dos, ObjectOutputStream oos) {
        try {

		
						writeString(this.type, dos, oos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        	}

    }
    
    public void writeValuesData(DataOutputStream dos, org.jboss.marshalling.Marshaller objectOut){
                try {

		
						writeString(this.type, dos, objectOut);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        	}
    }


    
    public boolean supportMarshaller(){
        return true;
    }


    public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("[");
		sb.append("id_event="+String.valueOf(id_event));
		sb.append(",type="+type);
	    sb.append("]");

	    return sb.toString();
    }

    /**
     * Compare keys
     */
    public int compareTo(row3Struct other) {

		int returnValue = -1;
		
						returnValue = checkNullsAndCompare(this.id_event, other.id_event);
						if(returnValue != 0) {
							return returnValue;
						}

					
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
public void tDBInput_3Process(final java.util.Map<String, Object> globalMap) throws TalendException {
	globalMap.put("tDBInput_3_SUBPROCESS_STATE", 0);

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



		row3Struct row3 = new row3Struct();




	
	/**
	 * [tAdvancedHash_row3 begin ] start
	 */

	

	
		
		ok_Hash.put("tAdvancedHash_row3", false);
		start_Hash.put("tAdvancedHash_row3", System.currentTimeMillis());
		
	
	currentComponent="tAdvancedHash_row3";

	
					if(execStat) {
						runStat.updateStatOnConnection(resourceMap,iterateId,0,0,"row3");
					}
				
		int tos_count_tAdvancedHash_row3 = 0;
		

			   		// connection name:row3
			   		// source node:tDBInput_3 - inputs:(after_tDBInput_1) outputs:(row3,row3) | target node:tAdvancedHash_row3 - inputs:(row3) outputs:()
			   		// linked node: tMap_1 - inputs:(row1,row2,row3,row4,row5,row6,row7,row8,row9,row10,row11,row12,row13,row14,row15,row16,row17,row18,row19,row21) outputs:(out)
			   
			   		org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE matchingModeEnum_row3 = 
			   			org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE.UNIQUE_MATCH;
			   			
			   
	   			org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row3Struct> tHash_Lookup_row3 =org.talend.designer.components.lookup.memory.AdvancedMemoryLookup.
	   						<row3Struct>getLookup(matchingModeEnum_row3);
	   						   
		   	   	   globalMap.put("tHash_Lookup_row3", tHash_Lookup_row3);
		   	   	   
				
           

 



/**
 * [tAdvancedHash_row3 begin ] stop
 */



	
	/**
	 * [tDBInput_3 begin ] start
	 */

	

	
		
		ok_Hash.put("tDBInput_3", false);
		start_Hash.put("tDBInput_3", System.currentTimeMillis());
		
	
	currentComponent="tDBInput_3";

	
		int tos_count_tDBInput_3 = 0;
		
	
    
	
			org.talend.designer.components.util.mssql.MSSqlGenerateTimestampUtil mssqlGTU_tDBInput_3 = org.talend.designer.components.util.mssql.MSSqlUtilFactory.getMSSqlGenerateTimestampUtil();
			
			java.util.List<String> talendToDBList_tDBInput_3 = new java.util.ArrayList();
			String[] talendToDBArray_tDBInput_3  = new String[]{"FLOAT","NUMERIC","NUMERIC IDENTITY","DECIMAL","DECIMAL IDENTITY","REAL"}; 
			java.util.Collections.addAll(talendToDBList_tDBInput_3, talendToDBArray_tDBInput_3); 
		    int nb_line_tDBInput_3 = 0;
		    java.sql.Connection conn_tDBInput_3 = null;
				String driverClass_tDBInput_3 = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
			    java.lang.Class jdbcclazz_tDBInput_3 = java.lang.Class.forName(driverClass_tDBInput_3);
				String dbUser_tDBInput_3 = "bi";
				
				 
	final String decryptedPassword_tDBInput_3 = routines.system.PasswordEncryptUtil.decryptPassword("enc:routine.encryption.key.v1:20e2Xx36L1Lvdd1Wmzz0SYWAfJX+4DN/4UHFULHm");
				
				String dbPwd_tDBInput_3 = decryptedPassword_tDBInput_3;
				
		    String port_tDBInput_3 = "1433";
		    String dbname_tDBInput_3 = "event_DWH" ;		    
		    String url_tDBInput_3 = "jdbc:sqlserver://" + "localhost" ;
		    if (!"".equals(port_tDBInput_3)) {
		    	url_tDBInput_3 += ":" + "1433";
		    }
		    if (!"".equals(dbname_tDBInput_3)) {		    
		    	url_tDBInput_3 += ";databaseName=" + "event_DWH"; 
		    }
		    url_tDBInput_3 += ";appName=" + projectName + ";" + "trustServerCertificate=true";
		    String dbschema_tDBInput_3 = "dbo";
				
				conn_tDBInput_3 = java.sql.DriverManager.getConnection(url_tDBInput_3,dbUser_tDBInput_3,dbPwd_tDBInput_3);
		        
		    
			java.sql.Statement stmt_tDBInput_3 = conn_tDBInput_3.createStatement();

		    String dbquery_tDBInput_3 = "SELECT dbo.Dim_Event.id_event,\n		dbo.Dim_Event.type\nFROM	dbo.Dim_Event";
		    

            	globalMap.put("tDBInput_3_QUERY",dbquery_tDBInput_3);
		    java.sql.ResultSet rs_tDBInput_3 = null;

		    try {
		    	rs_tDBInput_3 = stmt_tDBInput_3.executeQuery(dbquery_tDBInput_3);
		    	java.sql.ResultSetMetaData rsmd_tDBInput_3 = rs_tDBInput_3.getMetaData();
		    	int colQtyInRs_tDBInput_3 = rsmd_tDBInput_3.getColumnCount();

		    String tmpContent_tDBInput_3 = null;
		    
		    
		    while (rs_tDBInput_3.next()) {
		        nb_line_tDBInput_3++;
		        
							if(colQtyInRs_tDBInput_3 < 1) {
								row3.id_event = null;
							} else {
		                          
            row3.id_event = rs_tDBInput_3.getInt(1);
            if(rs_tDBInput_3.wasNull()){
                    row3.id_event = null;
            }
		                    }
							if(colQtyInRs_tDBInput_3 < 2) {
								row3.type = null;
							} else {
	                         		
           		tmpContent_tDBInput_3 = rs_tDBInput_3.getString(2);
            if(tmpContent_tDBInput_3 != null) {
            	if (talendToDBList_tDBInput_3 .contains(rsmd_tDBInput_3.getColumnTypeName(2).toUpperCase(java.util.Locale.ENGLISH))) {
            		row3.type = FormatterUtils.formatUnwithE(tmpContent_tDBInput_3);
            	} else {
                	row3.type = tmpContent_tDBInput_3;
                }
            } else {
                row3.type = null;
            }
		                    }
					





 



/**
 * [tDBInput_3 begin ] stop
 */
	
	/**
	 * [tDBInput_3 main ] start
	 */

	

	
	
	currentComponent="tDBInput_3";

	

 


	tos_count_tDBInput_3++;

/**
 * [tDBInput_3 main ] stop
 */
	
	/**
	 * [tDBInput_3 process_data_begin ] start
	 */

	

	
	
	currentComponent="tDBInput_3";

	

 



/**
 * [tDBInput_3 process_data_begin ] stop
 */

	
	/**
	 * [tAdvancedHash_row3 main ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row3";

	
					if(execStat){
						runStat.updateStatOnConnection(iterateId,1,1
						
							,"row3"
						
						);
					}
					


			   
			   

					row3Struct row3_HashRow = new row3Struct();
		   	   	   
				
				row3_HashRow.id_event = row3.id_event;
				
				row3_HashRow.type = row3.type;
				
			tHash_Lookup_row3.put(row3_HashRow);
			
            




 


	tos_count_tAdvancedHash_row3++;

/**
 * [tAdvancedHash_row3 main ] stop
 */
	
	/**
	 * [tAdvancedHash_row3 process_data_begin ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row3";

	

 



/**
 * [tAdvancedHash_row3 process_data_begin ] stop
 */
	
	/**
	 * [tAdvancedHash_row3 process_data_end ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row3";

	

 



/**
 * [tAdvancedHash_row3 process_data_end ] stop
 */



	
	/**
	 * [tDBInput_3 process_data_end ] start
	 */

	

	
	
	currentComponent="tDBInput_3";

	

 



/**
 * [tDBInput_3 process_data_end ] stop
 */
	
	/**
	 * [tDBInput_3 end ] start
	 */

	

	
	
	currentComponent="tDBInput_3";

	

	}
}finally{
	if (rs_tDBInput_3 != null) {
		rs_tDBInput_3.close();
	}
	if (stmt_tDBInput_3 != null) {
		stmt_tDBInput_3.close();
	}
		if(conn_tDBInput_3 != null && !conn_tDBInput_3.isClosed()) {
			
			conn_tDBInput_3.close();
			
			if("com.mysql.cj.jdbc.Driver".equals((String)globalMap.get("driverClass_"))
			    && routines.system.BundleUtils.inOSGi()) {
			        Class.forName("com.mysql.cj.jdbc.AbandonedConnectionCleanupThread").
			            getMethod("checkedShutdown").invoke(null, (Object[]) null);
			}
			
		}
}
globalMap.put("tDBInput_3_NB_LINE",nb_line_tDBInput_3);

 

ok_Hash.put("tDBInput_3", true);
end_Hash.put("tDBInput_3", System.currentTimeMillis());




/**
 * [tDBInput_3 end ] stop
 */

	
	/**
	 * [tAdvancedHash_row3 end ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row3";

	

tHash_Lookup_row3.endPut();

				if(execStat){
			  		runStat.updateStat(resourceMap,iterateId,2,0,"row3");
			  	}
			  	
 

ok_Hash.put("tAdvancedHash_row3", true);
end_Hash.put("tAdvancedHash_row3", System.currentTimeMillis());




/**
 * [tAdvancedHash_row3 end ] stop
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
	 * [tDBInput_3 finally ] start
	 */

	

	
	
	currentComponent="tDBInput_3";

	

 



/**
 * [tDBInput_3 finally ] stop
 */

	
	/**
	 * [tAdvancedHash_row3 finally ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row3";

	

 



/**
 * [tAdvancedHash_row3 finally ] stop
 */



				}catch(java.lang.Exception e){	
					//ignore
				}catch(java.lang.Error error){
					//ignore
				}
				resourceMap = null;
			}
		

		globalMap.put("tDBInput_3_SUBPROCESS_STATE", 1);
	}
	


public static class row4Struct implements routines.system.IPersistableComparableLookupRow<row4Struct> {
    final static byte[] commonByteArrayLock_TEST_Fact2 = new byte[0];
    static byte[] commonByteArray_TEST_Fact2 = new byte[0];
	protected static final int DEFAULT_HASHCODE = 1;
    protected static final int PRIME = 31;
    protected int hashCode = DEFAULT_HASHCODE;
    public boolean hashCodeDirty = true;

    public String loopKey;



	
			    public Integer Date_PK;

				public Integer getDate_PK () {
					return this.Date_PK;
				}
				
			    public java.util.Date Date;

				public java.util.Date getDate () {
					return this.Date;
				}
				
			    public Integer Annee;

				public Integer getAnnee () {
					return this.Annee;
				}
				
			    public Integer Mois;

				public Integer getMois () {
					return this.Mois;
				}
				


	@Override
	public int hashCode() {
		if (this.hashCodeDirty) {
			final int prime = PRIME;
			int result = DEFAULT_HASHCODE;
	
						result = prime * result + ((this.Date == null) ? 0 : this.Date.hashCode());
					
    		this.hashCode = result;
    		this.hashCodeDirty = false;
		}
		return this.hashCode;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		final row4Struct other = (row4Struct) obj;
		
						if (this.Date == null) {
							if (other.Date != null)
								return false;
						
						} else if (!this.Date.equals(other.Date))
						
							return false;
					

		return true;
    }

	public void copyDataTo(row4Struct other) {

		other.Date_PK = this.Date_PK;
	            other.Date = this.Date;
	            other.Annee = this.Annee;
	            other.Mois = this.Mois;
	            
	}

	public void copyKeysDataTo(row4Struct other) {

		other.Date = this.Date;
	            	
	}



	private Integer readInteger(DataInputStream dis, ObjectInputStream ois) throws IOException{
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
	
	private Integer readInteger(DataInputStream dis, org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException{
		Integer intReturn;
        int length = 0;
        length = unmarshaller.readByte();
		if (length == -1) {
			intReturn = null;
		} else {
			intReturn = unmarshaller.readInt();
		}
		return intReturn;
	}

	private void writeInteger(Integer intNum, DataOutputStream dos, ObjectOutputStream oos) throws IOException{
		if(intNum == null) {
            dos.writeByte(-1);
		} else {
			dos.writeByte(0);
	    	dos.writeInt(intNum);
    	}
	}
	
	private void writeInteger(Integer intNum, DataOutputStream dos,org.jboss.marshalling.Marshaller marshaller ) throws IOException{
		if(intNum == null) {
			marshaller.writeByte(-1);
		} else {
			marshaller.writeByte(0);
			marshaller.writeInt(intNum);
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

    public void readKeysData(ObjectInputStream dis) {

		synchronized(commonByteArrayLock_TEST_Fact2) {

        	try {

        		int length = 0;
		
					this.Date = readDate(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }
    
    public void readKeysData(org.jboss.marshalling.Unmarshaller dis) {

		synchronized(commonByteArrayLock_TEST_Fact2) {

        	try {

        		int length = 0;
		
					this.Date = readDate(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }

    public void writeKeysData(ObjectOutputStream dos) {
        try {

		
					// java.util.Date
				
						writeDate(this.Date,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }
    
    public void writeKeysData(org.jboss.marshalling.Marshaller dos) {
        try {

		
					// java.util.Date
				
						writeDate(this.Date,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }



    /**
     * Fill Values data by reading ObjectInputStream.
     */
    public void readValuesData(DataInputStream dis, ObjectInputStream ois) {
        try {

			int length = 0;
		
						this.Date_PK = readInteger(dis,ois);
					
						this.Annee = readInteger(dis,ois);
					
						this.Mois = readInteger(dis,ois);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

    }
    
    public void readValuesData(DataInputStream dis, org.jboss.marshalling.Unmarshaller objectIn) {
        try {
			int length = 0;
		
						this.Date_PK = readInteger(dis,objectIn);
					
						this.Annee = readInteger(dis,objectIn);
					
						this.Mois = readInteger(dis,objectIn);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

    }

    /**
     * Return a byte array which represents Values data.
     */
    public void writeValuesData(DataOutputStream dos, ObjectOutputStream oos) {
        try {

		
					writeInteger(this.Date_PK, dos, oos);
					
					writeInteger(this.Annee, dos, oos);
					
					writeInteger(this.Mois, dos, oos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        	}

    }
    
    public void writeValuesData(DataOutputStream dos, org.jboss.marshalling.Marshaller objectOut){
                try {

		
					writeInteger(this.Date_PK, dos, objectOut);
					
					writeInteger(this.Annee, dos, objectOut);
					
					writeInteger(this.Mois, dos, objectOut);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        	}
    }


    
    public boolean supportMarshaller(){
        return true;
    }


    public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("[");
		sb.append("Date_PK="+String.valueOf(Date_PK));
		sb.append(",Date="+String.valueOf(Date));
		sb.append(",Annee="+String.valueOf(Annee));
		sb.append(",Mois="+String.valueOf(Mois));
	    sb.append("]");

	    return sb.toString();
    }

    /**
     * Compare keys
     */
    public int compareTo(row4Struct other) {

		int returnValue = -1;
		
						returnValue = checkNullsAndCompare(this.Date, other.Date);
						if(returnValue != 0) {
							return returnValue;
						}

					
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
public void tDBInput_4Process(final java.util.Map<String, Object> globalMap) throws TalendException {
	globalMap.put("tDBInput_4_SUBPROCESS_STATE", 0);

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



		row4Struct row4 = new row4Struct();




	
	/**
	 * [tAdvancedHash_row4 begin ] start
	 */

	

	
		
		ok_Hash.put("tAdvancedHash_row4", false);
		start_Hash.put("tAdvancedHash_row4", System.currentTimeMillis());
		
	
	currentComponent="tAdvancedHash_row4";

	
					if(execStat) {
						runStat.updateStatOnConnection(resourceMap,iterateId,0,0,"row4");
					}
				
		int tos_count_tAdvancedHash_row4 = 0;
		

			   		// connection name:row4
			   		// source node:tDBInput_4 - inputs:(after_tDBInput_1) outputs:(row4,row4) | target node:tAdvancedHash_row4 - inputs:(row4) outputs:()
			   		// linked node: tMap_1 - inputs:(row1,row2,row3,row4,row5,row6,row7,row8,row9,row10,row11,row12,row13,row14,row15,row16,row17,row18,row19,row21) outputs:(out)
			   
			   		org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE matchingModeEnum_row4 = 
			   			org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE.UNIQUE_MATCH;
			   			
			   
	   			org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row4Struct> tHash_Lookup_row4 =org.talend.designer.components.lookup.memory.AdvancedMemoryLookup.
	   						<row4Struct>getLookup(matchingModeEnum_row4);
	   						   
		   	   	   globalMap.put("tHash_Lookup_row4", tHash_Lookup_row4);
		   	   	   
				
           

 



/**
 * [tAdvancedHash_row4 begin ] stop
 */



	
	/**
	 * [tDBInput_4 begin ] start
	 */

	

	
		
		ok_Hash.put("tDBInput_4", false);
		start_Hash.put("tDBInput_4", System.currentTimeMillis());
		
	
	currentComponent="tDBInput_4";

	
		int tos_count_tDBInput_4 = 0;
		
	
    
	
			org.talend.designer.components.util.mssql.MSSqlGenerateTimestampUtil mssqlGTU_tDBInput_4 = org.talend.designer.components.util.mssql.MSSqlUtilFactory.getMSSqlGenerateTimestampUtil();
			
			java.util.List<String> talendToDBList_tDBInput_4 = new java.util.ArrayList();
			String[] talendToDBArray_tDBInput_4  = new String[]{"FLOAT","NUMERIC","NUMERIC IDENTITY","DECIMAL","DECIMAL IDENTITY","REAL"}; 
			java.util.Collections.addAll(talendToDBList_tDBInput_4, talendToDBArray_tDBInput_4); 
		    int nb_line_tDBInput_4 = 0;
		    java.sql.Connection conn_tDBInput_4 = null;
				String driverClass_tDBInput_4 = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
			    java.lang.Class jdbcclazz_tDBInput_4 = java.lang.Class.forName(driverClass_tDBInput_4);
				String dbUser_tDBInput_4 = "bi";
				
				 
	final String decryptedPassword_tDBInput_4 = routines.system.PasswordEncryptUtil.decryptPassword("enc:routine.encryption.key.v1:vEiTMMH/ErmmC7ShM7XQn016Cboj4GOsfsm3v6Yu");
				
				String dbPwd_tDBInput_4 = decryptedPassword_tDBInput_4;
				
		    String port_tDBInput_4 = "1433";
		    String dbname_tDBInput_4 = "event_DWH" ;		    
		    String url_tDBInput_4 = "jdbc:sqlserver://" + "localhost" ;
		    if (!"".equals(port_tDBInput_4)) {
		    	url_tDBInput_4 += ":" + "1433";
		    }
		    if (!"".equals(dbname_tDBInput_4)) {		    
		    	url_tDBInput_4 += ";databaseName=" + "event_DWH"; 
		    }
		    url_tDBInput_4 += ";appName=" + projectName + ";" + "trustServerCertificate=true";
		    String dbschema_tDBInput_4 = "dbo";
				
				conn_tDBInput_4 = java.sql.DriverManager.getConnection(url_tDBInput_4,dbUser_tDBInput_4,dbPwd_tDBInput_4);
		        
		    
			java.sql.Statement stmt_tDBInput_4 = conn_tDBInput_4.createStatement();

		    String dbquery_tDBInput_4 = "SELECT dbo.DimDates.Date_PK,\n		dbo.DimDates.Date,\n		dbo.DimDates.Annee,\n		dbo.DimDates.Mois\nFROM	dbo.DimDates";
		    

            	globalMap.put("tDBInput_4_QUERY",dbquery_tDBInput_4);
		    java.sql.ResultSet rs_tDBInput_4 = null;

		    try {
		    	rs_tDBInput_4 = stmt_tDBInput_4.executeQuery(dbquery_tDBInput_4);
		    	java.sql.ResultSetMetaData rsmd_tDBInput_4 = rs_tDBInput_4.getMetaData();
		    	int colQtyInRs_tDBInput_4 = rsmd_tDBInput_4.getColumnCount();

		    String tmpContent_tDBInput_4 = null;
		    
		    
		    while (rs_tDBInput_4.next()) {
		        nb_line_tDBInput_4++;
		        
							if(colQtyInRs_tDBInput_4 < 1) {
								row4.Date_PK = null;
							} else {
		                          
            row4.Date_PK = rs_tDBInput_4.getInt(1);
            if(rs_tDBInput_4.wasNull()){
                    row4.Date_PK = null;
            }
		                    }
							if(colQtyInRs_tDBInput_4 < 2) {
								row4.Date = null;
							} else {
										
			row4.Date = mssqlGTU_tDBInput_4.getDate(rsmd_tDBInput_4, rs_tDBInput_4, 2);
			
		                    }
							if(colQtyInRs_tDBInput_4 < 3) {
								row4.Annee = null;
							} else {
		                          
            row4.Annee = rs_tDBInput_4.getInt(3);
            if(rs_tDBInput_4.wasNull()){
                    row4.Annee = null;
            }
		                    }
							if(colQtyInRs_tDBInput_4 < 4) {
								row4.Mois = null;
							} else {
		                          
            row4.Mois = rs_tDBInput_4.getInt(4);
            if(rs_tDBInput_4.wasNull()){
                    row4.Mois = null;
            }
		                    }
					





 



/**
 * [tDBInput_4 begin ] stop
 */
	
	/**
	 * [tDBInput_4 main ] start
	 */

	

	
	
	currentComponent="tDBInput_4";

	

 


	tos_count_tDBInput_4++;

/**
 * [tDBInput_4 main ] stop
 */
	
	/**
	 * [tDBInput_4 process_data_begin ] start
	 */

	

	
	
	currentComponent="tDBInput_4";

	

 



/**
 * [tDBInput_4 process_data_begin ] stop
 */

	
	/**
	 * [tAdvancedHash_row4 main ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row4";

	
					if(execStat){
						runStat.updateStatOnConnection(iterateId,1,1
						
							,"row4"
						
						);
					}
					


			   
			   

					row4Struct row4_HashRow = new row4Struct();
		   	   	   
				
				row4_HashRow.Date_PK = row4.Date_PK;
				
				row4_HashRow.Date = row4.Date;
				
				row4_HashRow.Annee = row4.Annee;
				
				row4_HashRow.Mois = row4.Mois;
				
			tHash_Lookup_row4.put(row4_HashRow);
			
            




 


	tos_count_tAdvancedHash_row4++;

/**
 * [tAdvancedHash_row4 main ] stop
 */
	
	/**
	 * [tAdvancedHash_row4 process_data_begin ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row4";

	

 



/**
 * [tAdvancedHash_row4 process_data_begin ] stop
 */
	
	/**
	 * [tAdvancedHash_row4 process_data_end ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row4";

	

 



/**
 * [tAdvancedHash_row4 process_data_end ] stop
 */



	
	/**
	 * [tDBInput_4 process_data_end ] start
	 */

	

	
	
	currentComponent="tDBInput_4";

	

 



/**
 * [tDBInput_4 process_data_end ] stop
 */
	
	/**
	 * [tDBInput_4 end ] start
	 */

	

	
	
	currentComponent="tDBInput_4";

	

	}
}finally{
	if (rs_tDBInput_4 != null) {
		rs_tDBInput_4.close();
	}
	if (stmt_tDBInput_4 != null) {
		stmt_tDBInput_4.close();
	}
		if(conn_tDBInput_4 != null && !conn_tDBInput_4.isClosed()) {
			
			conn_tDBInput_4.close();
			
			if("com.mysql.cj.jdbc.Driver".equals((String)globalMap.get("driverClass_"))
			    && routines.system.BundleUtils.inOSGi()) {
			        Class.forName("com.mysql.cj.jdbc.AbandonedConnectionCleanupThread").
			            getMethod("checkedShutdown").invoke(null, (Object[]) null);
			}
			
		}
}
globalMap.put("tDBInput_4_NB_LINE",nb_line_tDBInput_4);

 

ok_Hash.put("tDBInput_4", true);
end_Hash.put("tDBInput_4", System.currentTimeMillis());




/**
 * [tDBInput_4 end ] stop
 */

	
	/**
	 * [tAdvancedHash_row4 end ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row4";

	

tHash_Lookup_row4.endPut();

				if(execStat){
			  		runStat.updateStat(resourceMap,iterateId,2,0,"row4");
			  	}
			  	
 

ok_Hash.put("tAdvancedHash_row4", true);
end_Hash.put("tAdvancedHash_row4", System.currentTimeMillis());




/**
 * [tAdvancedHash_row4 end ] stop
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
	 * [tDBInput_4 finally ] start
	 */

	

	
	
	currentComponent="tDBInput_4";

	

 



/**
 * [tDBInput_4 finally ] stop
 */

	
	/**
	 * [tAdvancedHash_row4 finally ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row4";

	

 



/**
 * [tAdvancedHash_row4 finally ] stop
 */



				}catch(java.lang.Exception e){	
					//ignore
				}catch(java.lang.Error error){
					//ignore
				}
				resourceMap = null;
			}
		

		globalMap.put("tDBInput_4_SUBPROCESS_STATE", 1);
	}
	


public static class row5Struct implements routines.system.IPersistableComparableLookupRow<row5Struct> {
    final static byte[] commonByteArrayLock_TEST_Fact2 = new byte[0];
    static byte[] commonByteArray_TEST_Fact2 = new byte[0];
	protected static final int DEFAULT_HASHCODE = 1;
    protected static final int PRIME = 31;
    protected int hashCode = DEFAULT_HASHCODE;
    public boolean hashCodeDirty = true;

    public String loopKey;



	
			    public Integer id;

				public Integer getId () {
					return this.id;
				}
				
			    public java.util.Date date;

				public java.util.Date getDate () {
					return this.date;
				}
				
			    public Float reservations;

				public Float getReservations () {
					return this.reservations;
				}
				
			    public Float visitors;

				public Float getVisitors () {
					return this.visitors;
				}
				


	@Override
	public int hashCode() {
		if (this.hashCodeDirty) {
			final int prime = PRIME;
			int result = DEFAULT_HASHCODE;
	
						result = prime * result + ((this.date == null) ? 0 : this.date.hashCode());
					
    		this.hashCode = result;
    		this.hashCodeDirty = false;
		}
		return this.hashCode;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		final row5Struct other = (row5Struct) obj;
		
						if (this.date == null) {
							if (other.date != null)
								return false;
						
						} else if (!this.date.equals(other.date))
						
							return false;
					

		return true;
    }

	public void copyDataTo(row5Struct other) {

		other.id = this.id;
	            other.date = this.date;
	            other.reservations = this.reservations;
	            other.visitors = this.visitors;
	            
	}

	public void copyKeysDataTo(row5Struct other) {

		other.date = this.date;
	            	
	}



	private Integer readInteger(DataInputStream dis, ObjectInputStream ois) throws IOException{
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
	
	private Integer readInteger(DataInputStream dis, org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException{
		Integer intReturn;
        int length = 0;
        length = unmarshaller.readByte();
		if (length == -1) {
			intReturn = null;
		} else {
			intReturn = unmarshaller.readInt();
		}
		return intReturn;
	}

	private void writeInteger(Integer intNum, DataOutputStream dos, ObjectOutputStream oos) throws IOException{
		if(intNum == null) {
            dos.writeByte(-1);
		} else {
			dos.writeByte(0);
	    	dos.writeInt(intNum);
    	}
	}
	
	private void writeInteger(Integer intNum, DataOutputStream dos,org.jboss.marshalling.Marshaller marshaller ) throws IOException{
		if(intNum == null) {
			marshaller.writeByte(-1);
		} else {
			marshaller.writeByte(0);
			marshaller.writeInt(intNum);
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

    public void readKeysData(ObjectInputStream dis) {

		synchronized(commonByteArrayLock_TEST_Fact2) {

        	try {

        		int length = 0;
		
					this.date = readDate(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }
    
    public void readKeysData(org.jboss.marshalling.Unmarshaller dis) {

		synchronized(commonByteArrayLock_TEST_Fact2) {

        	try {

        		int length = 0;
		
					this.date = readDate(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }

    public void writeKeysData(ObjectOutputStream dos) {
        try {

		
					// java.util.Date
				
						writeDate(this.date,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }
    
    public void writeKeysData(org.jboss.marshalling.Marshaller dos) {
        try {

		
					// java.util.Date
				
						writeDate(this.date,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }



    /**
     * Fill Values data by reading ObjectInputStream.
     */
    public void readValuesData(DataInputStream dis, ObjectInputStream ois) {
        try {

			int length = 0;
		
						this.id = readInteger(dis,ois);
					
			            length = dis.readByte();
           				if (length == -1) {
           	    			this.reservations = null;
           				} else {
           			    	this.reservations = dis.readFloat();
           				}
					
			            length = dis.readByte();
           				if (length == -1) {
           	    			this.visitors = null;
           				} else {
           			    	this.visitors = dis.readFloat();
           				}
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

    }
    
    public void readValuesData(DataInputStream dis, org.jboss.marshalling.Unmarshaller objectIn) {
        try {
			int length = 0;
		
						this.id = readInteger(dis,objectIn);
					
			            length = objectIn.readByte();
           				if (length == -1) {
           	    			this.reservations = null;
           				} else {
           			    	this.reservations = objectIn.readFloat();
           				}
					
			            length = objectIn.readByte();
           				if (length == -1) {
           	    			this.visitors = null;
           				} else {
           			    	this.visitors = objectIn.readFloat();
           				}
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

    }

    /**
     * Return a byte array which represents Values data.
     */
    public void writeValuesData(DataOutputStream dos, ObjectOutputStream oos) {
        try {

		
					writeInteger(this.id, dos, oos);
					
						if(this.reservations == null) {
			                dos.writeByte(-1);
						} else {
               				dos.writeByte(0);
           			    	dos.writeFloat(this.reservations);
		            	}
					
						if(this.visitors == null) {
			                dos.writeByte(-1);
						} else {
               				dos.writeByte(0);
           			    	dos.writeFloat(this.visitors);
		            	}
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        	}

    }
    
    public void writeValuesData(DataOutputStream dos, org.jboss.marshalling.Marshaller objectOut){
                try {

		
					writeInteger(this.id, dos, objectOut);
					
						if(this.reservations == null) {
							objectOut.writeByte(-1);
						} else {
							objectOut.writeByte(0);
							objectOut.writeFloat(this.reservations);
		            	}
					
						if(this.visitors == null) {
							objectOut.writeByte(-1);
						} else {
							objectOut.writeByte(0);
							objectOut.writeFloat(this.visitors);
		            	}
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        	}
    }


    
    public boolean supportMarshaller(){
        return true;
    }


    public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("[");
		sb.append("id="+String.valueOf(id));
		sb.append(",date="+String.valueOf(date));
		sb.append(",reservations="+String.valueOf(reservations));
		sb.append(",visitors="+String.valueOf(visitors));
	    sb.append("]");

	    return sb.toString();
    }

    /**
     * Compare keys
     */
    public int compareTo(row5Struct other) {

		int returnValue = -1;
		
						returnValue = checkNullsAndCompare(this.date, other.date);
						if(returnValue != 0) {
							return returnValue;
						}

					
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
public void tDBInput_5Process(final java.util.Map<String, Object> globalMap) throws TalendException {
	globalMap.put("tDBInput_5_SUBPROCESS_STATE", 0);

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



		row5Struct row5 = new row5Struct();




	
	/**
	 * [tAdvancedHash_row5 begin ] start
	 */

	

	
		
		ok_Hash.put("tAdvancedHash_row5", false);
		start_Hash.put("tAdvancedHash_row5", System.currentTimeMillis());
		
	
	currentComponent="tAdvancedHash_row5";

	
					if(execStat) {
						runStat.updateStatOnConnection(resourceMap,iterateId,0,0,"row5");
					}
				
		int tos_count_tAdvancedHash_row5 = 0;
		

			   		// connection name:row5
			   		// source node:tDBInput_5 - inputs:(after_tDBInput_1) outputs:(row5,row5) | target node:tAdvancedHash_row5 - inputs:(row5) outputs:()
			   		// linked node: tMap_1 - inputs:(row1,row2,row3,row4,row5,row6,row7,row8,row9,row10,row11,row12,row13,row14,row15,row16,row17,row18,row19,row21) outputs:(out)
			   
			   		org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE matchingModeEnum_row5 = 
			   			org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE.UNIQUE_MATCH;
			   			
			   
	   			org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row5Struct> tHash_Lookup_row5 =org.talend.designer.components.lookup.memory.AdvancedMemoryLookup.
	   						<row5Struct>getLookup(matchingModeEnum_row5);
	   						   
		   	   	   globalMap.put("tHash_Lookup_row5", tHash_Lookup_row5);
		   	   	   
				
           

 



/**
 * [tAdvancedHash_row5 begin ] stop
 */



	
	/**
	 * [tDBInput_5 begin ] start
	 */

	

	
		
		ok_Hash.put("tDBInput_5", false);
		start_Hash.put("tDBInput_5", System.currentTimeMillis());
		
	
	currentComponent="tDBInput_5";

	
		int tos_count_tDBInput_5 = 0;
		
	
    
	
			org.talend.designer.components.util.mssql.MSSqlGenerateTimestampUtil mssqlGTU_tDBInput_5 = org.talend.designer.components.util.mssql.MSSqlUtilFactory.getMSSqlGenerateTimestampUtil();
			
			java.util.List<String> talendToDBList_tDBInput_5 = new java.util.ArrayList();
			String[] talendToDBArray_tDBInput_5  = new String[]{"FLOAT","NUMERIC","NUMERIC IDENTITY","DECIMAL","DECIMAL IDENTITY","REAL"}; 
			java.util.Collections.addAll(talendToDBList_tDBInput_5, talendToDBArray_tDBInput_5); 
		    int nb_line_tDBInput_5 = 0;
		    java.sql.Connection conn_tDBInput_5 = null;
				String driverClass_tDBInput_5 = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
			    java.lang.Class jdbcclazz_tDBInput_5 = java.lang.Class.forName(driverClass_tDBInput_5);
				String dbUser_tDBInput_5 = "event";
				
				 
	final String decryptedPassword_tDBInput_5 = routines.system.PasswordEncryptUtil.decryptPassword("enc:routine.encryption.key.v1:NAINcuLomg3ZTKNa2OnHNme/9PfwV5vd69t5NDCeiw==");
				
				String dbPwd_tDBInput_5 = decryptedPassword_tDBInput_5;
				
		    String port_tDBInput_5 = "1433";
		    String dbname_tDBInput_5 = "Event_SA" ;		    
		    String url_tDBInput_5 = "jdbc:sqlserver://" + "localhost" ;
		    if (!"".equals(port_tDBInput_5)) {
		    	url_tDBInput_5 += ":" + "1433";
		    }
		    if (!"".equals(dbname_tDBInput_5)) {		    
		    	url_tDBInput_5 += ";databaseName=" + "Event_SA"; 
		    }
		    url_tDBInput_5 += ";appName=" + projectName + ";" + "trustServerCertificate=true";
		    String dbschema_tDBInput_5 = "dbo";
				
				conn_tDBInput_5 = java.sql.DriverManager.getConnection(url_tDBInput_5,dbUser_tDBInput_5,dbPwd_tDBInput_5);
		        
		    
			java.sql.Statement stmt_tDBInput_5 = conn_tDBInput_5.createStatement();

		    String dbquery_tDBInput_5 = "SELECT dbo.Visitors.\"id\",\n		dbo.Visitors.date,\n		dbo.Visitors.reservations,\n		dbo.Visitors.visitors\nFROM	dbo.Visi"
+"tors";
		    

            	globalMap.put("tDBInput_5_QUERY",dbquery_tDBInput_5);
		    java.sql.ResultSet rs_tDBInput_5 = null;

		    try {
		    	rs_tDBInput_5 = stmt_tDBInput_5.executeQuery(dbquery_tDBInput_5);
		    	java.sql.ResultSetMetaData rsmd_tDBInput_5 = rs_tDBInput_5.getMetaData();
		    	int colQtyInRs_tDBInput_5 = rsmd_tDBInput_5.getColumnCount();

		    String tmpContent_tDBInput_5 = null;
		    
		    
		    while (rs_tDBInput_5.next()) {
		        nb_line_tDBInput_5++;
		        
							if(colQtyInRs_tDBInput_5 < 1) {
								row5.id = null;
							} else {
		                          
            row5.id = rs_tDBInput_5.getInt(1);
            if(rs_tDBInput_5.wasNull()){
                    row5.id = null;
            }
		                    }
							if(colQtyInRs_tDBInput_5 < 2) {
								row5.date = null;
							} else {
										
			row5.date = mssqlGTU_tDBInput_5.getDate(rsmd_tDBInput_5, rs_tDBInput_5, 2);
			
		                    }
							if(colQtyInRs_tDBInput_5 < 3) {
								row5.reservations = null;
							} else {
		                          
            row5.reservations = rs_tDBInput_5.getFloat(3);
            if(rs_tDBInput_5.wasNull()){
                    row5.reservations = null;
            }
		                    }
							if(colQtyInRs_tDBInput_5 < 4) {
								row5.visitors = null;
							} else {
		                          
            row5.visitors = rs_tDBInput_5.getFloat(4);
            if(rs_tDBInput_5.wasNull()){
                    row5.visitors = null;
            }
		                    }
					





 



/**
 * [tDBInput_5 begin ] stop
 */
	
	/**
	 * [tDBInput_5 main ] start
	 */

	

	
	
	currentComponent="tDBInput_5";

	

 


	tos_count_tDBInput_5++;

/**
 * [tDBInput_5 main ] stop
 */
	
	/**
	 * [tDBInput_5 process_data_begin ] start
	 */

	

	
	
	currentComponent="tDBInput_5";

	

 



/**
 * [tDBInput_5 process_data_begin ] stop
 */

	
	/**
	 * [tAdvancedHash_row5 main ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row5";

	
					if(execStat){
						runStat.updateStatOnConnection(iterateId,1,1
						
							,"row5"
						
						);
					}
					


			   
			   

					row5Struct row5_HashRow = new row5Struct();
		   	   	   
				
				row5_HashRow.id = row5.id;
				
				row5_HashRow.date = row5.date;
				
				row5_HashRow.reservations = row5.reservations;
				
				row5_HashRow.visitors = row5.visitors;
				
			tHash_Lookup_row5.put(row5_HashRow);
			
            




 


	tos_count_tAdvancedHash_row5++;

/**
 * [tAdvancedHash_row5 main ] stop
 */
	
	/**
	 * [tAdvancedHash_row5 process_data_begin ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row5";

	

 



/**
 * [tAdvancedHash_row5 process_data_begin ] stop
 */
	
	/**
	 * [tAdvancedHash_row5 process_data_end ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row5";

	

 



/**
 * [tAdvancedHash_row5 process_data_end ] stop
 */



	
	/**
	 * [tDBInput_5 process_data_end ] start
	 */

	

	
	
	currentComponent="tDBInput_5";

	

 



/**
 * [tDBInput_5 process_data_end ] stop
 */
	
	/**
	 * [tDBInput_5 end ] start
	 */

	

	
	
	currentComponent="tDBInput_5";

	

	}
}finally{
	if (rs_tDBInput_5 != null) {
		rs_tDBInput_5.close();
	}
	if (stmt_tDBInput_5 != null) {
		stmt_tDBInput_5.close();
	}
		if(conn_tDBInput_5 != null && !conn_tDBInput_5.isClosed()) {
			
			conn_tDBInput_5.close();
			
			if("com.mysql.cj.jdbc.Driver".equals((String)globalMap.get("driverClass_"))
			    && routines.system.BundleUtils.inOSGi()) {
			        Class.forName("com.mysql.cj.jdbc.AbandonedConnectionCleanupThread").
			            getMethod("checkedShutdown").invoke(null, (Object[]) null);
			}
			
		}
}
globalMap.put("tDBInput_5_NB_LINE",nb_line_tDBInput_5);

 

ok_Hash.put("tDBInput_5", true);
end_Hash.put("tDBInput_5", System.currentTimeMillis());




/**
 * [tDBInput_5 end ] stop
 */

	
	/**
	 * [tAdvancedHash_row5 end ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row5";

	

tHash_Lookup_row5.endPut();

				if(execStat){
			  		runStat.updateStat(resourceMap,iterateId,2,0,"row5");
			  	}
			  	
 

ok_Hash.put("tAdvancedHash_row5", true);
end_Hash.put("tAdvancedHash_row5", System.currentTimeMillis());




/**
 * [tAdvancedHash_row5 end ] stop
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
	 * [tDBInput_5 finally ] start
	 */

	

	
	
	currentComponent="tDBInput_5";

	

 



/**
 * [tDBInput_5 finally ] stop
 */

	
	/**
	 * [tAdvancedHash_row5 finally ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row5";

	

 



/**
 * [tAdvancedHash_row5 finally ] stop
 */



				}catch(java.lang.Exception e){	
					//ignore
				}catch(java.lang.Error error){
					//ignore
				}
				resourceMap = null;
			}
		

		globalMap.put("tDBInput_5_SUBPROCESS_STATE", 1);
	}
	


public static class row6Struct implements routines.system.IPersistableComparableLookupRow<row6Struct> {
    final static byte[] commonByteArrayLock_TEST_Fact2 = new byte[0];
    static byte[] commonByteArray_TEST_Fact2 = new byte[0];
	protected static final int DEFAULT_HASHCODE = 1;
    protected static final int PRIME = 31;
    protected int hashCode = DEFAULT_HASHCODE;
    public boolean hashCodeDirty = true;

    public String loopKey;



	
			    public Integer id_service;

				public Integer getId_service () {
					return this.id_service;
				}
				
			    public Integer id_provider;

				public Integer getId_provider () {
					return this.id_provider;
				}
				
			    public Integer id_subcategory;

				public Integer getId_subcategory () {
					return this.id_subcategory;
				}
				
			    public Integer id_category;

				public Integer getId_category () {
					return this.id_category;
				}
				
			    public String city;

				public String getCity () {
					return this.city;
				}
				


	@Override
	public int hashCode() {
		if (this.hashCodeDirty) {
			final int prime = PRIME;
			int result = DEFAULT_HASHCODE;
	
						result = prime * result + ((this.id_service == null) ? 0 : this.id_service.hashCode());
					
    		this.hashCode = result;
    		this.hashCodeDirty = false;
		}
		return this.hashCode;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		final row6Struct other = (row6Struct) obj;
		
						if (this.id_service == null) {
							if (other.id_service != null)
								return false;
						
						} else if (!this.id_service.equals(other.id_service))
						
							return false;
					

		return true;
    }

	public void copyDataTo(row6Struct other) {

		other.id_service = this.id_service;
	            other.id_provider = this.id_provider;
	            other.id_subcategory = this.id_subcategory;
	            other.id_category = this.id_category;
	            other.city = this.city;
	            
	}

	public void copyKeysDataTo(row6Struct other) {

		other.id_service = this.id_service;
	            	
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
	private Integer readInteger(DataInputStream dis, ObjectInputStream ois) throws IOException{
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
	
	private Integer readInteger(DataInputStream dis, org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException{
		Integer intReturn;
        int length = 0;
        length = unmarshaller.readByte();
		if (length == -1) {
			intReturn = null;
		} else {
			intReturn = unmarshaller.readInt();
		}
		return intReturn;
	}

	private void writeInteger(Integer intNum, DataOutputStream dos, ObjectOutputStream oos) throws IOException{
		if(intNum == null) {
            dos.writeByte(-1);
		} else {
			dos.writeByte(0);
	    	dos.writeInt(intNum);
    	}
	}
	
	private void writeInteger(Integer intNum, DataOutputStream dos,org.jboss.marshalling.Marshaller marshaller ) throws IOException{
		if(intNum == null) {
			marshaller.writeByte(-1);
		} else {
			marshaller.writeByte(0);
			marshaller.writeInt(intNum);
    	}
	}
	
	private String readString(DataInputStream dis, ObjectInputStream ois) throws IOException{
		String strReturn = null;
		int length = 0;
        length = dis.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			byte[] byteArray = new byte[length];
			dis.read(byteArray);
			strReturn = new String(byteArray, utf8Charset);
		}
		return strReturn;
	}
	
	private String readString(DataInputStream dis, org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException{
		String strReturn = null;
		int length = 0;
        length = unmarshaller.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			byte[] byteArray = new byte[length];
			unmarshaller.read(byteArray);
			strReturn = new String(byteArray, utf8Charset);
		}
		return strReturn;
	}
	
	private void writeString(String str, DataOutputStream dos, org.jboss.marshalling.Marshaller marshaller) throws IOException{
		if(str == null) {
			marshaller.writeInt(-1);
		} else {
            byte[] byteArray = str.getBytes(utf8Charset);
            marshaller.writeInt(byteArray.length);
            marshaller.write(byteArray);
    	}
	}

	private void writeString(String str, DataOutputStream dos, ObjectOutputStream oos) throws IOException{
		if(str == null) {
            dos.writeInt(-1);
		} else {
            byte[] byteArray = str.getBytes(utf8Charset);
	    	dos.writeInt(byteArray.length);
			dos.write(byteArray);
    	}
	}

    public void readKeysData(ObjectInputStream dis) {

		synchronized(commonByteArrayLock_TEST_Fact2) {

        	try {

        		int length = 0;
		
						this.id_service = readInteger(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }
    
    public void readKeysData(org.jboss.marshalling.Unmarshaller dis) {

		synchronized(commonByteArrayLock_TEST_Fact2) {

        	try {

        		int length = 0;
		
						this.id_service = readInteger(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }

    public void writeKeysData(ObjectOutputStream dos) {
        try {

		
					// Integer
				
						writeInteger(this.id_service,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }
    
    public void writeKeysData(org.jboss.marshalling.Marshaller dos) {
        try {

		
					// Integer
				
						writeInteger(this.id_service,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }



    /**
     * Fill Values data by reading ObjectInputStream.
     */
    public void readValuesData(DataInputStream dis, ObjectInputStream ois) {
        try {

			int length = 0;
		
						this.id_provider = readInteger(dis,ois);
					
						this.id_subcategory = readInteger(dis,ois);
					
						this.id_category = readInteger(dis,ois);
					
						this.city = readString(dis,ois);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

    }
    
    public void readValuesData(DataInputStream dis, org.jboss.marshalling.Unmarshaller objectIn) {
        try {
			int length = 0;
		
						this.id_provider = readInteger(dis,objectIn);
					
						this.id_subcategory = readInteger(dis,objectIn);
					
						this.id_category = readInteger(dis,objectIn);
					
						this.city = readString(dis,objectIn);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

    }

    /**
     * Return a byte array which represents Values data.
     */
    public void writeValuesData(DataOutputStream dos, ObjectOutputStream oos) {
        try {

		
					writeInteger(this.id_provider, dos, oos);
					
					writeInteger(this.id_subcategory, dos, oos);
					
					writeInteger(this.id_category, dos, oos);
					
						writeString(this.city, dos, oos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        	}

    }
    
    public void writeValuesData(DataOutputStream dos, org.jboss.marshalling.Marshaller objectOut){
                try {

		
					writeInteger(this.id_provider, dos, objectOut);
					
					writeInteger(this.id_subcategory, dos, objectOut);
					
					writeInteger(this.id_category, dos, objectOut);
					
						writeString(this.city, dos, objectOut);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        	}
    }


    
    public boolean supportMarshaller(){
        return true;
    }


    public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("[");
		sb.append("id_service="+String.valueOf(id_service));
		sb.append(",id_provider="+String.valueOf(id_provider));
		sb.append(",id_subcategory="+String.valueOf(id_subcategory));
		sb.append(",id_category="+String.valueOf(id_category));
		sb.append(",city="+city);
	    sb.append("]");

	    return sb.toString();
    }

    /**
     * Compare keys
     */
    public int compareTo(row6Struct other) {

		int returnValue = -1;
		
						returnValue = checkNullsAndCompare(this.id_service, other.id_service);
						if(returnValue != 0) {
							return returnValue;
						}

					
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
public void tDBInput_6Process(final java.util.Map<String, Object> globalMap) throws TalendException {
	globalMap.put("tDBInput_6_SUBPROCESS_STATE", 0);

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



		row6Struct row6 = new row6Struct();




	
	/**
	 * [tAdvancedHash_row6 begin ] start
	 */

	

	
		
		ok_Hash.put("tAdvancedHash_row6", false);
		start_Hash.put("tAdvancedHash_row6", System.currentTimeMillis());
		
	
	currentComponent="tAdvancedHash_row6";

	
					if(execStat) {
						runStat.updateStatOnConnection(resourceMap,iterateId,0,0,"row6");
					}
				
		int tos_count_tAdvancedHash_row6 = 0;
		

			   		// connection name:row6
			   		// source node:tDBInput_6 - inputs:(after_tDBInput_1) outputs:(row6,row6) | target node:tAdvancedHash_row6 - inputs:(row6) outputs:()
			   		// linked node: tMap_1 - inputs:(row1,row2,row3,row4,row5,row6,row7,row8,row9,row10,row11,row12,row13,row14,row15,row16,row17,row18,row19,row21) outputs:(out)
			   
			   		org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE matchingModeEnum_row6 = 
			   			org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE.UNIQUE_MATCH;
			   			
			   
	   			org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row6Struct> tHash_Lookup_row6 =org.talend.designer.components.lookup.memory.AdvancedMemoryLookup.
	   						<row6Struct>getLookup(matchingModeEnum_row6);
	   						   
		   	   	   globalMap.put("tHash_Lookup_row6", tHash_Lookup_row6);
		   	   	   
				
           

 



/**
 * [tAdvancedHash_row6 begin ] stop
 */



	
	/**
	 * [tDBInput_6 begin ] start
	 */

	

	
		
		ok_Hash.put("tDBInput_6", false);
		start_Hash.put("tDBInput_6", System.currentTimeMillis());
		
	
	currentComponent="tDBInput_6";

	
		int tos_count_tDBInput_6 = 0;
		
	
    
	
			org.talend.designer.components.util.mssql.MSSqlGenerateTimestampUtil mssqlGTU_tDBInput_6 = org.talend.designer.components.util.mssql.MSSqlUtilFactory.getMSSqlGenerateTimestampUtil();
			
			java.util.List<String> talendToDBList_tDBInput_6 = new java.util.ArrayList();
			String[] talendToDBArray_tDBInput_6  = new String[]{"FLOAT","NUMERIC","NUMERIC IDENTITY","DECIMAL","DECIMAL IDENTITY","REAL"}; 
			java.util.Collections.addAll(talendToDBList_tDBInput_6, talendToDBArray_tDBInput_6); 
		    int nb_line_tDBInput_6 = 0;
		    java.sql.Connection conn_tDBInput_6 = null;
				String driverClass_tDBInput_6 = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
			    java.lang.Class jdbcclazz_tDBInput_6 = java.lang.Class.forName(driverClass_tDBInput_6);
				String dbUser_tDBInput_6 = "event";
				
				 
	final String decryptedPassword_tDBInput_6 = routines.system.PasswordEncryptUtil.decryptPassword("enc:routine.encryption.key.v1:SO32UwU5hzOG5gtwppaznDPO+P9YNCIN6DKi5mHQZA==");
				
				String dbPwd_tDBInput_6 = decryptedPassword_tDBInput_6;
				
		    String port_tDBInput_6 = "1433";
		    String dbname_tDBInput_6 = "Event_SA" ;		    
		    String url_tDBInput_6 = "jdbc:sqlserver://" + "localhost" ;
		    if (!"".equals(port_tDBInput_6)) {
		    	url_tDBInput_6 += ":" + "1433";
		    }
		    if (!"".equals(dbname_tDBInput_6)) {		    
		    	url_tDBInput_6 += ";databaseName=" + "Event_SA"; 
		    }
		    url_tDBInput_6 += ";appName=" + projectName + ";" + "trustServerCertificate=true";
		    String dbschema_tDBInput_6 = "dbo";
				
				conn_tDBInput_6 = java.sql.DriverManager.getConnection(url_tDBInput_6,dbUser_tDBInput_6,dbPwd_tDBInput_6);
		        
		    
			java.sql.Statement stmt_tDBInput_6 = conn_tDBInput_6.createStatement();

		    String dbquery_tDBInput_6 = "SELECT \n    s.id_service,\n    s.id_provider,\n    s.id_subcategory,\n    sc.id_category,\n    p.city\nFROM dbo.Servic"
+"e s\n\nLEFT JOIN dbo.Subcategory sc\n    ON s.id_subcategory = sc.id_subcategory\n\nLEFT JOIN dbo.Provider p\n    ON s.i"
+"d_provider = p.id_provider;";
		    

            	globalMap.put("tDBInput_6_QUERY",dbquery_tDBInput_6);
		    java.sql.ResultSet rs_tDBInput_6 = null;

		    try {
		    	rs_tDBInput_6 = stmt_tDBInput_6.executeQuery(dbquery_tDBInput_6);
		    	java.sql.ResultSetMetaData rsmd_tDBInput_6 = rs_tDBInput_6.getMetaData();
		    	int colQtyInRs_tDBInput_6 = rsmd_tDBInput_6.getColumnCount();

		    String tmpContent_tDBInput_6 = null;
		    
		    
		    while (rs_tDBInput_6.next()) {
		        nb_line_tDBInput_6++;
		        
							if(colQtyInRs_tDBInput_6 < 1) {
								row6.id_service = null;
							} else {
		                          
            row6.id_service = rs_tDBInput_6.getInt(1);
            if(rs_tDBInput_6.wasNull()){
                    row6.id_service = null;
            }
		                    }
							if(colQtyInRs_tDBInput_6 < 2) {
								row6.id_provider = null;
							} else {
		                          
            row6.id_provider = rs_tDBInput_6.getInt(2);
            if(rs_tDBInput_6.wasNull()){
                    row6.id_provider = null;
            }
		                    }
							if(colQtyInRs_tDBInput_6 < 3) {
								row6.id_subcategory = null;
							} else {
		                          
            row6.id_subcategory = rs_tDBInput_6.getInt(3);
            if(rs_tDBInput_6.wasNull()){
                    row6.id_subcategory = null;
            }
		                    }
							if(colQtyInRs_tDBInput_6 < 4) {
								row6.id_category = null;
							} else {
		                          
            row6.id_category = rs_tDBInput_6.getInt(4);
            if(rs_tDBInput_6.wasNull()){
                    row6.id_category = null;
            }
		                    }
							if(colQtyInRs_tDBInput_6 < 5) {
								row6.city = null;
							} else {
	                         		
           		tmpContent_tDBInput_6 = rs_tDBInput_6.getString(5);
            if(tmpContent_tDBInput_6 != null) {
            	if (talendToDBList_tDBInput_6 .contains(rsmd_tDBInput_6.getColumnTypeName(5).toUpperCase(java.util.Locale.ENGLISH))) {
            		row6.city = FormatterUtils.formatUnwithE(tmpContent_tDBInput_6);
            	} else {
                	row6.city = tmpContent_tDBInput_6;
                }
            } else {
                row6.city = null;
            }
		                    }
					





 



/**
 * [tDBInput_6 begin ] stop
 */
	
	/**
	 * [tDBInput_6 main ] start
	 */

	

	
	
	currentComponent="tDBInput_6";

	

 


	tos_count_tDBInput_6++;

/**
 * [tDBInput_6 main ] stop
 */
	
	/**
	 * [tDBInput_6 process_data_begin ] start
	 */

	

	
	
	currentComponent="tDBInput_6";

	

 



/**
 * [tDBInput_6 process_data_begin ] stop
 */

	
	/**
	 * [tAdvancedHash_row6 main ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row6";

	
					if(execStat){
						runStat.updateStatOnConnection(iterateId,1,1
						
							,"row6"
						
						);
					}
					


			   
			   

					row6Struct row6_HashRow = new row6Struct();
		   	   	   
				
				row6_HashRow.id_service = row6.id_service;
				
				row6_HashRow.id_provider = row6.id_provider;
				
				row6_HashRow.id_subcategory = row6.id_subcategory;
				
				row6_HashRow.id_category = row6.id_category;
				
				row6_HashRow.city = row6.city;
				
			tHash_Lookup_row6.put(row6_HashRow);
			
            




 


	tos_count_tAdvancedHash_row6++;

/**
 * [tAdvancedHash_row6 main ] stop
 */
	
	/**
	 * [tAdvancedHash_row6 process_data_begin ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row6";

	

 



/**
 * [tAdvancedHash_row6 process_data_begin ] stop
 */
	
	/**
	 * [tAdvancedHash_row6 process_data_end ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row6";

	

 



/**
 * [tAdvancedHash_row6 process_data_end ] stop
 */



	
	/**
	 * [tDBInput_6 process_data_end ] start
	 */

	

	
	
	currentComponent="tDBInput_6";

	

 



/**
 * [tDBInput_6 process_data_end ] stop
 */
	
	/**
	 * [tDBInput_6 end ] start
	 */

	

	
	
	currentComponent="tDBInput_6";

	

	}
}finally{
	if (rs_tDBInput_6 != null) {
		rs_tDBInput_6.close();
	}
	if (stmt_tDBInput_6 != null) {
		stmt_tDBInput_6.close();
	}
		if(conn_tDBInput_6 != null && !conn_tDBInput_6.isClosed()) {
			
			conn_tDBInput_6.close();
			
			if("com.mysql.cj.jdbc.Driver".equals((String)globalMap.get("driverClass_"))
			    && routines.system.BundleUtils.inOSGi()) {
			        Class.forName("com.mysql.cj.jdbc.AbandonedConnectionCleanupThread").
			            getMethod("checkedShutdown").invoke(null, (Object[]) null);
			}
			
		}
}
globalMap.put("tDBInput_6_NB_LINE",nb_line_tDBInput_6);

 

ok_Hash.put("tDBInput_6", true);
end_Hash.put("tDBInput_6", System.currentTimeMillis());




/**
 * [tDBInput_6 end ] stop
 */

	
	/**
	 * [tAdvancedHash_row6 end ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row6";

	

tHash_Lookup_row6.endPut();

				if(execStat){
			  		runStat.updateStat(resourceMap,iterateId,2,0,"row6");
			  	}
			  	
 

ok_Hash.put("tAdvancedHash_row6", true);
end_Hash.put("tAdvancedHash_row6", System.currentTimeMillis());




/**
 * [tAdvancedHash_row6 end ] stop
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
	 * [tDBInput_6 finally ] start
	 */

	

	
	
	currentComponent="tDBInput_6";

	

 



/**
 * [tDBInput_6 finally ] stop
 */

	
	/**
	 * [tAdvancedHash_row6 finally ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row6";

	

 



/**
 * [tAdvancedHash_row6 finally ] stop
 */



				}catch(java.lang.Exception e){	
					//ignore
				}catch(java.lang.Error error){
					//ignore
				}
				resourceMap = null;
			}
		

		globalMap.put("tDBInput_6_SUBPROCESS_STATE", 1);
	}
	


public static class row7Struct implements routines.system.IPersistableComparableLookupRow<row7Struct> {
    final static byte[] commonByteArrayLock_TEST_Fact2 = new byte[0];
    static byte[] commonByteArray_TEST_Fact2 = new byte[0];
	protected static final int DEFAULT_HASHCODE = 1;
    protected static final int PRIME = 31;
    protected int hashCode = DEFAULT_HASHCODE;
    public boolean hashCodeDirty = true;

    public String loopKey;



	
			    public Integer id_subcategory;

				public Integer getId_subcategory () {
					return this.id_subcategory;
				}
				


	@Override
	public int hashCode() {
		if (this.hashCodeDirty) {
			final int prime = PRIME;
			int result = DEFAULT_HASHCODE;
	
						result = prime * result + ((this.id_subcategory == null) ? 0 : this.id_subcategory.hashCode());
					
    		this.hashCode = result;
    		this.hashCodeDirty = false;
		}
		return this.hashCode;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		final row7Struct other = (row7Struct) obj;
		
						if (this.id_subcategory == null) {
							if (other.id_subcategory != null)
								return false;
						
						} else if (!this.id_subcategory.equals(other.id_subcategory))
						
							return false;
					

		return true;
    }

	public void copyDataTo(row7Struct other) {

		other.id_subcategory = this.id_subcategory;
	            
	}

	public void copyKeysDataTo(row7Struct other) {

		other.id_subcategory = this.id_subcategory;
	            	
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

    public void readKeysData(ObjectInputStream dis) {

		synchronized(commonByteArrayLock_TEST_Fact2) {

        	try {

        		int length = 0;
		
						this.id_subcategory = readInteger(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }
    
    public void readKeysData(org.jboss.marshalling.Unmarshaller dis) {

		synchronized(commonByteArrayLock_TEST_Fact2) {

        	try {

        		int length = 0;
		
						this.id_subcategory = readInteger(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }

    public void writeKeysData(ObjectOutputStream dos) {
        try {

		
					// Integer
				
						writeInteger(this.id_subcategory,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }
    
    public void writeKeysData(org.jboss.marshalling.Marshaller dos) {
        try {

		
					// Integer
				
						writeInteger(this.id_subcategory,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }



    /**
     * Fill Values data by reading ObjectInputStream.
     */
    public void readValuesData(DataInputStream dis, ObjectInputStream ois) {
        try {

			int length = 0;
		

		

        }

		
        	finally {}

    }
    
    public void readValuesData(DataInputStream dis, org.jboss.marshalling.Unmarshaller objectIn) {
        try {
			int length = 0;
		

		

        }

		
        	finally {}

    }

    /**
     * Return a byte array which represents Values data.
     */
    public void writeValuesData(DataOutputStream dos, ObjectOutputStream oos) {
        try {

		
        	}
        	finally {}

    }
    
    public void writeValuesData(DataOutputStream dos, org.jboss.marshalling.Marshaller objectOut){
                try {

		
        	}
        	finally {}
    }


    
    public boolean supportMarshaller(){
        return true;
    }


    public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("[");
		sb.append("id_subcategory="+String.valueOf(id_subcategory));
	    sb.append("]");

	    return sb.toString();
    }

    /**
     * Compare keys
     */
    public int compareTo(row7Struct other) {

		int returnValue = -1;
		
						returnValue = checkNullsAndCompare(this.id_subcategory, other.id_subcategory);
						if(returnValue != 0) {
							return returnValue;
						}

					
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
public void tDBInput_7Process(final java.util.Map<String, Object> globalMap) throws TalendException {
	globalMap.put("tDBInput_7_SUBPROCESS_STATE", 0);

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



		row7Struct row7 = new row7Struct();




	
	/**
	 * [tAdvancedHash_row7 begin ] start
	 */

	

	
		
		ok_Hash.put("tAdvancedHash_row7", false);
		start_Hash.put("tAdvancedHash_row7", System.currentTimeMillis());
		
	
	currentComponent="tAdvancedHash_row7";

	
					if(execStat) {
						runStat.updateStatOnConnection(resourceMap,iterateId,0,0,"row7");
					}
				
		int tos_count_tAdvancedHash_row7 = 0;
		

			   		// connection name:row7
			   		// source node:tDBInput_7 - inputs:(after_tDBInput_1) outputs:(row7,row7) | target node:tAdvancedHash_row7 - inputs:(row7) outputs:()
			   		// linked node: tMap_1 - inputs:(row1,row2,row3,row4,row5,row6,row7,row8,row9,row10,row11,row12,row13,row14,row15,row16,row17,row18,row19,row21) outputs:(out)
			   
			   		org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE matchingModeEnum_row7 = 
			   			org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE.UNIQUE_MATCH;
			   			
			   
	   			org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row7Struct> tHash_Lookup_row7 =org.talend.designer.components.lookup.memory.AdvancedMemoryLookup.
	   						<row7Struct>getLookup(matchingModeEnum_row7);
	   						   
		   	   	   globalMap.put("tHash_Lookup_row7", tHash_Lookup_row7);
		   	   	   
				
           

 



/**
 * [tAdvancedHash_row7 begin ] stop
 */



	
	/**
	 * [tDBInput_7 begin ] start
	 */

	

	
		
		ok_Hash.put("tDBInput_7", false);
		start_Hash.put("tDBInput_7", System.currentTimeMillis());
		
	
	currentComponent="tDBInput_7";

	
		int tos_count_tDBInput_7 = 0;
		
	
    
	
			org.talend.designer.components.util.mssql.MSSqlGenerateTimestampUtil mssqlGTU_tDBInput_7 = org.talend.designer.components.util.mssql.MSSqlUtilFactory.getMSSqlGenerateTimestampUtil();
			
			java.util.List<String> talendToDBList_tDBInput_7 = new java.util.ArrayList();
			String[] talendToDBArray_tDBInput_7  = new String[]{"FLOAT","NUMERIC","NUMERIC IDENTITY","DECIMAL","DECIMAL IDENTITY","REAL"}; 
			java.util.Collections.addAll(talendToDBList_tDBInput_7, talendToDBArray_tDBInput_7); 
		    int nb_line_tDBInput_7 = 0;
		    java.sql.Connection conn_tDBInput_7 = null;
				String driverClass_tDBInput_7 = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
			    java.lang.Class jdbcclazz_tDBInput_7 = java.lang.Class.forName(driverClass_tDBInput_7);
				String dbUser_tDBInput_7 = "bi";
				
				 
	final String decryptedPassword_tDBInput_7 = routines.system.PasswordEncryptUtil.decryptPassword("enc:routine.encryption.key.v1:J4vSvZsdU6Z/VTmhc6uYYxOZlZBpTRvC9AXHwhAT");
				
				String dbPwd_tDBInput_7 = decryptedPassword_tDBInput_7;
				
		    String port_tDBInput_7 = "1433";
		    String dbname_tDBInput_7 = "event_DWH" ;		    
		    String url_tDBInput_7 = "jdbc:sqlserver://" + "localhost" ;
		    if (!"".equals(port_tDBInput_7)) {
		    	url_tDBInput_7 += ":" + "1433";
		    }
		    if (!"".equals(dbname_tDBInput_7)) {		    
		    	url_tDBInput_7 += ";databaseName=" + "event_DWH"; 
		    }
		    url_tDBInput_7 += ";appName=" + projectName + ";" + "trustServerCertificate=true";
		    String dbschema_tDBInput_7 = "dbo";
				
				conn_tDBInput_7 = java.sql.DriverManager.getConnection(url_tDBInput_7,dbUser_tDBInput_7,dbPwd_tDBInput_7);
		        
		    
			java.sql.Statement stmt_tDBInput_7 = conn_tDBInput_7.createStatement();

		    String dbquery_tDBInput_7 = "SELECT dbo.Dim_Subcategory.id_subcategory \nFROM	 dbo.Dim_Subcategory";
		    

            	globalMap.put("tDBInput_7_QUERY",dbquery_tDBInput_7);
		    java.sql.ResultSet rs_tDBInput_7 = null;

		    try {
		    	rs_tDBInput_7 = stmt_tDBInput_7.executeQuery(dbquery_tDBInput_7);
		    	java.sql.ResultSetMetaData rsmd_tDBInput_7 = rs_tDBInput_7.getMetaData();
		    	int colQtyInRs_tDBInput_7 = rsmd_tDBInput_7.getColumnCount();

		    String tmpContent_tDBInput_7 = null;
		    
		    
		    while (rs_tDBInput_7.next()) {
		        nb_line_tDBInput_7++;
		        
							if(colQtyInRs_tDBInput_7 < 1) {
								row7.id_subcategory = null;
							} else {
		                          
            row7.id_subcategory = rs_tDBInput_7.getInt(1);
            if(rs_tDBInput_7.wasNull()){
                    row7.id_subcategory = null;
            }
		                    }
					





 



/**
 * [tDBInput_7 begin ] stop
 */
	
	/**
	 * [tDBInput_7 main ] start
	 */

	

	
	
	currentComponent="tDBInput_7";

	

 


	tos_count_tDBInput_7++;

/**
 * [tDBInput_7 main ] stop
 */
	
	/**
	 * [tDBInput_7 process_data_begin ] start
	 */

	

	
	
	currentComponent="tDBInput_7";

	

 



/**
 * [tDBInput_7 process_data_begin ] stop
 */

	
	/**
	 * [tAdvancedHash_row7 main ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row7";

	
					if(execStat){
						runStat.updateStatOnConnection(iterateId,1,1
						
							,"row7"
						
						);
					}
					


			   
			   

					row7Struct row7_HashRow = new row7Struct();
		   	   	   
				
				row7_HashRow.id_subcategory = row7.id_subcategory;
				
			tHash_Lookup_row7.put(row7_HashRow);
			
            




 


	tos_count_tAdvancedHash_row7++;

/**
 * [tAdvancedHash_row7 main ] stop
 */
	
	/**
	 * [tAdvancedHash_row7 process_data_begin ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row7";

	

 



/**
 * [tAdvancedHash_row7 process_data_begin ] stop
 */
	
	/**
	 * [tAdvancedHash_row7 process_data_end ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row7";

	

 



/**
 * [tAdvancedHash_row7 process_data_end ] stop
 */



	
	/**
	 * [tDBInput_7 process_data_end ] start
	 */

	

	
	
	currentComponent="tDBInput_7";

	

 



/**
 * [tDBInput_7 process_data_end ] stop
 */
	
	/**
	 * [tDBInput_7 end ] start
	 */

	

	
	
	currentComponent="tDBInput_7";

	

	}
}finally{
	if (rs_tDBInput_7 != null) {
		rs_tDBInput_7.close();
	}
	if (stmt_tDBInput_7 != null) {
		stmt_tDBInput_7.close();
	}
		if(conn_tDBInput_7 != null && !conn_tDBInput_7.isClosed()) {
			
			conn_tDBInput_7.close();
			
			if("com.mysql.cj.jdbc.Driver".equals((String)globalMap.get("driverClass_"))
			    && routines.system.BundleUtils.inOSGi()) {
			        Class.forName("com.mysql.cj.jdbc.AbandonedConnectionCleanupThread").
			            getMethod("checkedShutdown").invoke(null, (Object[]) null);
			}
			
		}
}
globalMap.put("tDBInput_7_NB_LINE",nb_line_tDBInput_7);

 

ok_Hash.put("tDBInput_7", true);
end_Hash.put("tDBInput_7", System.currentTimeMillis());




/**
 * [tDBInput_7 end ] stop
 */

	
	/**
	 * [tAdvancedHash_row7 end ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row7";

	

tHash_Lookup_row7.endPut();

				if(execStat){
			  		runStat.updateStat(resourceMap,iterateId,2,0,"row7");
			  	}
			  	
 

ok_Hash.put("tAdvancedHash_row7", true);
end_Hash.put("tAdvancedHash_row7", System.currentTimeMillis());




/**
 * [tAdvancedHash_row7 end ] stop
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
	 * [tDBInput_7 finally ] start
	 */

	

	
	
	currentComponent="tDBInput_7";

	

 



/**
 * [tDBInput_7 finally ] stop
 */

	
	/**
	 * [tAdvancedHash_row7 finally ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row7";

	

 



/**
 * [tAdvancedHash_row7 finally ] stop
 */



				}catch(java.lang.Exception e){	
					//ignore
				}catch(java.lang.Error error){
					//ignore
				}
				resourceMap = null;
			}
		

		globalMap.put("tDBInput_7_SUBPROCESS_STATE", 1);
	}
	


public static class row8Struct implements routines.system.IPersistableComparableLookupRow<row8Struct> {
    final static byte[] commonByteArrayLock_TEST_Fact2 = new byte[0];
    static byte[] commonByteArray_TEST_Fact2 = new byte[0];
	protected static final int DEFAULT_HASHCODE = 1;
    protected static final int PRIME = 31;
    protected int hashCode = DEFAULT_HASHCODE;
    public boolean hashCodeDirty = true;

    public String loopKey;



	
			    public Integer id_category;

				public Integer getId_category () {
					return this.id_category;
				}
				
			    public String name;

				public String getName () {
					return this.name;
				}
				


	@Override
	public int hashCode() {
		if (this.hashCodeDirty) {
			final int prime = PRIME;
			int result = DEFAULT_HASHCODE;
	
						result = prime * result + ((this.id_category == null) ? 0 : this.id_category.hashCode());
					
    		this.hashCode = result;
    		this.hashCodeDirty = false;
		}
		return this.hashCode;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		final row8Struct other = (row8Struct) obj;
		
						if (this.id_category == null) {
							if (other.id_category != null)
								return false;
						
						} else if (!this.id_category.equals(other.id_category))
						
							return false;
					

		return true;
    }

	public void copyDataTo(row8Struct other) {

		other.id_category = this.id_category;
	            other.name = this.name;
	            
	}

	public void copyKeysDataTo(row8Struct other) {

		other.id_category = this.id_category;
	            	
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
	
	private String readString(DataInputStream dis, ObjectInputStream ois) throws IOException{
		String strReturn = null;
		int length = 0;
        length = dis.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			byte[] byteArray = new byte[length];
			dis.read(byteArray);
			strReturn = new String(byteArray, utf8Charset);
		}
		return strReturn;
	}
	
	private String readString(DataInputStream dis, org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException{
		String strReturn = null;
		int length = 0;
        length = unmarshaller.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			byte[] byteArray = new byte[length];
			unmarshaller.read(byteArray);
			strReturn = new String(byteArray, utf8Charset);
		}
		return strReturn;
	}
	
	private void writeString(String str, DataOutputStream dos, org.jboss.marshalling.Marshaller marshaller) throws IOException{
		if(str == null) {
			marshaller.writeInt(-1);
		} else {
            byte[] byteArray = str.getBytes(utf8Charset);
            marshaller.writeInt(byteArray.length);
            marshaller.write(byteArray);
    	}
	}

	private void writeString(String str, DataOutputStream dos, ObjectOutputStream oos) throws IOException{
		if(str == null) {
            dos.writeInt(-1);
		} else {
            byte[] byteArray = str.getBytes(utf8Charset);
	    	dos.writeInt(byteArray.length);
			dos.write(byteArray);
    	}
	}

    public void readKeysData(ObjectInputStream dis) {

		synchronized(commonByteArrayLock_TEST_Fact2) {

        	try {

        		int length = 0;
		
						this.id_category = readInteger(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }
    
    public void readKeysData(org.jboss.marshalling.Unmarshaller dis) {

		synchronized(commonByteArrayLock_TEST_Fact2) {

        	try {

        		int length = 0;
		
						this.id_category = readInteger(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }

    public void writeKeysData(ObjectOutputStream dos) {
        try {

		
					// Integer
				
						writeInteger(this.id_category,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }
    
    public void writeKeysData(org.jboss.marshalling.Marshaller dos) {
        try {

		
					// Integer
				
						writeInteger(this.id_category,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }



    /**
     * Fill Values data by reading ObjectInputStream.
     */
    public void readValuesData(DataInputStream dis, ObjectInputStream ois) {
        try {

			int length = 0;
		
						this.name = readString(dis,ois);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

    }
    
    public void readValuesData(DataInputStream dis, org.jboss.marshalling.Unmarshaller objectIn) {
        try {
			int length = 0;
		
						this.name = readString(dis,objectIn);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

    }

    /**
     * Return a byte array which represents Values data.
     */
    public void writeValuesData(DataOutputStream dos, ObjectOutputStream oos) {
        try {

		
						writeString(this.name, dos, oos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        	}

    }
    
    public void writeValuesData(DataOutputStream dos, org.jboss.marshalling.Marshaller objectOut){
                try {

		
						writeString(this.name, dos, objectOut);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        	}
    }


    
    public boolean supportMarshaller(){
        return true;
    }


    public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("[");
		sb.append("id_category="+String.valueOf(id_category));
		sb.append(",name="+name);
	    sb.append("]");

	    return sb.toString();
    }

    /**
     * Compare keys
     */
    public int compareTo(row8Struct other) {

		int returnValue = -1;
		
						returnValue = checkNullsAndCompare(this.id_category, other.id_category);
						if(returnValue != 0) {
							return returnValue;
						}

					
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
public void tDBInput_8Process(final java.util.Map<String, Object> globalMap) throws TalendException {
	globalMap.put("tDBInput_8_SUBPROCESS_STATE", 0);

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



		row8Struct row8 = new row8Struct();




	
	/**
	 * [tAdvancedHash_row8 begin ] start
	 */

	

	
		
		ok_Hash.put("tAdvancedHash_row8", false);
		start_Hash.put("tAdvancedHash_row8", System.currentTimeMillis());
		
	
	currentComponent="tAdvancedHash_row8";

	
					if(execStat) {
						runStat.updateStatOnConnection(resourceMap,iterateId,0,0,"row8");
					}
				
		int tos_count_tAdvancedHash_row8 = 0;
		

			   		// connection name:row8
			   		// source node:tDBInput_8 - inputs:(after_tDBInput_1) outputs:(row8,row8) | target node:tAdvancedHash_row8 - inputs:(row8) outputs:()
			   		// linked node: tMap_1 - inputs:(row1,row2,row3,row4,row5,row6,row7,row8,row9,row10,row11,row12,row13,row14,row15,row16,row17,row18,row19,row21) outputs:(out)
			   
			   		org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE matchingModeEnum_row8 = 
			   			org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE.UNIQUE_MATCH;
			   			
			   
	   			org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row8Struct> tHash_Lookup_row8 =org.talend.designer.components.lookup.memory.AdvancedMemoryLookup.
	   						<row8Struct>getLookup(matchingModeEnum_row8);
	   						   
		   	   	   globalMap.put("tHash_Lookup_row8", tHash_Lookup_row8);
		   	   	   
				
           

 



/**
 * [tAdvancedHash_row8 begin ] stop
 */



	
	/**
	 * [tDBInput_8 begin ] start
	 */

	

	
		
		ok_Hash.put("tDBInput_8", false);
		start_Hash.put("tDBInput_8", System.currentTimeMillis());
		
	
	currentComponent="tDBInput_8";

	
		int tos_count_tDBInput_8 = 0;
		
	
    
	
			org.talend.designer.components.util.mssql.MSSqlGenerateTimestampUtil mssqlGTU_tDBInput_8 = org.talend.designer.components.util.mssql.MSSqlUtilFactory.getMSSqlGenerateTimestampUtil();
			
			java.util.List<String> talendToDBList_tDBInput_8 = new java.util.ArrayList();
			String[] talendToDBArray_tDBInput_8  = new String[]{"FLOAT","NUMERIC","NUMERIC IDENTITY","DECIMAL","DECIMAL IDENTITY","REAL"}; 
			java.util.Collections.addAll(talendToDBList_tDBInput_8, talendToDBArray_tDBInput_8); 
		    int nb_line_tDBInput_8 = 0;
		    java.sql.Connection conn_tDBInput_8 = null;
				String driverClass_tDBInput_8 = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
			    java.lang.Class jdbcclazz_tDBInput_8 = java.lang.Class.forName(driverClass_tDBInput_8);
				String dbUser_tDBInput_8 = "bi";
				
				 
	final String decryptedPassword_tDBInput_8 = routines.system.PasswordEncryptUtil.decryptPassword("enc:routine.encryption.key.v1:Cj8skjQKjdhmiREjVIFjoYlUzLEqegtVCFz4pglH");
				
				String dbPwd_tDBInput_8 = decryptedPassword_tDBInput_8;
				
		    String port_tDBInput_8 = "1433";
		    String dbname_tDBInput_8 = "event_DWH" ;		    
		    String url_tDBInput_8 = "jdbc:sqlserver://" + "localhost" ;
		    if (!"".equals(port_tDBInput_8)) {
		    	url_tDBInput_8 += ":" + "1433";
		    }
		    if (!"".equals(dbname_tDBInput_8)) {		    
		    	url_tDBInput_8 += ";databaseName=" + "event_DWH"; 
		    }
		    url_tDBInput_8 += ";appName=" + projectName + ";" + "trustServerCertificate=true";
		    String dbschema_tDBInput_8 = "dbo";
				
				conn_tDBInput_8 = java.sql.DriverManager.getConnection(url_tDBInput_8,dbUser_tDBInput_8,dbPwd_tDBInput_8);
		        
		    
			java.sql.Statement stmt_tDBInput_8 = conn_tDBInput_8.createStatement();

		    String dbquery_tDBInput_8 = "SELECT dbo.Dim_Category.id_category,\n		dbo.Dim_Category.name\nFROM	dbo.Dim_Category";
		    

            	globalMap.put("tDBInput_8_QUERY",dbquery_tDBInput_8);
		    java.sql.ResultSet rs_tDBInput_8 = null;

		    try {
		    	rs_tDBInput_8 = stmt_tDBInput_8.executeQuery(dbquery_tDBInput_8);
		    	java.sql.ResultSetMetaData rsmd_tDBInput_8 = rs_tDBInput_8.getMetaData();
		    	int colQtyInRs_tDBInput_8 = rsmd_tDBInput_8.getColumnCount();

		    String tmpContent_tDBInput_8 = null;
		    
		    
		    while (rs_tDBInput_8.next()) {
		        nb_line_tDBInput_8++;
		        
							if(colQtyInRs_tDBInput_8 < 1) {
								row8.id_category = null;
							} else {
		                          
            row8.id_category = rs_tDBInput_8.getInt(1);
            if(rs_tDBInput_8.wasNull()){
                    row8.id_category = null;
            }
		                    }
							if(colQtyInRs_tDBInput_8 < 2) {
								row8.name = null;
							} else {
	                         		
           		tmpContent_tDBInput_8 = rs_tDBInput_8.getString(2);
            if(tmpContent_tDBInput_8 != null) {
            	if (talendToDBList_tDBInput_8 .contains(rsmd_tDBInput_8.getColumnTypeName(2).toUpperCase(java.util.Locale.ENGLISH))) {
            		row8.name = FormatterUtils.formatUnwithE(tmpContent_tDBInput_8);
            	} else {
                	row8.name = tmpContent_tDBInput_8;
                }
            } else {
                row8.name = null;
            }
		                    }
					





 



/**
 * [tDBInput_8 begin ] stop
 */
	
	/**
	 * [tDBInput_8 main ] start
	 */

	

	
	
	currentComponent="tDBInput_8";

	

 


	tos_count_tDBInput_8++;

/**
 * [tDBInput_8 main ] stop
 */
	
	/**
	 * [tDBInput_8 process_data_begin ] start
	 */

	

	
	
	currentComponent="tDBInput_8";

	

 



/**
 * [tDBInput_8 process_data_begin ] stop
 */

	
	/**
	 * [tAdvancedHash_row8 main ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row8";

	
					if(execStat){
						runStat.updateStatOnConnection(iterateId,1,1
						
							,"row8"
						
						);
					}
					


			   
			   

					row8Struct row8_HashRow = new row8Struct();
		   	   	   
				
				row8_HashRow.id_category = row8.id_category;
				
				row8_HashRow.name = row8.name;
				
			tHash_Lookup_row8.put(row8_HashRow);
			
            




 


	tos_count_tAdvancedHash_row8++;

/**
 * [tAdvancedHash_row8 main ] stop
 */
	
	/**
	 * [tAdvancedHash_row8 process_data_begin ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row8";

	

 



/**
 * [tAdvancedHash_row8 process_data_begin ] stop
 */
	
	/**
	 * [tAdvancedHash_row8 process_data_end ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row8";

	

 



/**
 * [tAdvancedHash_row8 process_data_end ] stop
 */



	
	/**
	 * [tDBInput_8 process_data_end ] start
	 */

	

	
	
	currentComponent="tDBInput_8";

	

 



/**
 * [tDBInput_8 process_data_end ] stop
 */
	
	/**
	 * [tDBInput_8 end ] start
	 */

	

	
	
	currentComponent="tDBInput_8";

	

	}
}finally{
	if (rs_tDBInput_8 != null) {
		rs_tDBInput_8.close();
	}
	if (stmt_tDBInput_8 != null) {
		stmt_tDBInput_8.close();
	}
		if(conn_tDBInput_8 != null && !conn_tDBInput_8.isClosed()) {
			
			conn_tDBInput_8.close();
			
			if("com.mysql.cj.jdbc.Driver".equals((String)globalMap.get("driverClass_"))
			    && routines.system.BundleUtils.inOSGi()) {
			        Class.forName("com.mysql.cj.jdbc.AbandonedConnectionCleanupThread").
			            getMethod("checkedShutdown").invoke(null, (Object[]) null);
			}
			
		}
}
globalMap.put("tDBInput_8_NB_LINE",nb_line_tDBInput_8);

 

ok_Hash.put("tDBInput_8", true);
end_Hash.put("tDBInput_8", System.currentTimeMillis());




/**
 * [tDBInput_8 end ] stop
 */

	
	/**
	 * [tAdvancedHash_row8 end ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row8";

	

tHash_Lookup_row8.endPut();

				if(execStat){
			  		runStat.updateStat(resourceMap,iterateId,2,0,"row8");
			  	}
			  	
 

ok_Hash.put("tAdvancedHash_row8", true);
end_Hash.put("tAdvancedHash_row8", System.currentTimeMillis());




/**
 * [tAdvancedHash_row8 end ] stop
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
	 * [tDBInput_8 finally ] start
	 */

	

	
	
	currentComponent="tDBInput_8";

	

 



/**
 * [tDBInput_8 finally ] stop
 */

	
	/**
	 * [tAdvancedHash_row8 finally ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row8";

	

 



/**
 * [tAdvancedHash_row8 finally ] stop
 */



				}catch(java.lang.Exception e){	
					//ignore
				}catch(java.lang.Error error){
					//ignore
				}
				resourceMap = null;
			}
		

		globalMap.put("tDBInput_8_SUBPROCESS_STATE", 1);
	}
	


public static class row9Struct implements routines.system.IPersistableComparableLookupRow<row9Struct> {
    final static byte[] commonByteArrayLock_TEST_Fact2 = new byte[0];
    static byte[] commonByteArray_TEST_Fact2 = new byte[0];
	protected static final int DEFAULT_HASHCODE = 1;
    protected static final int PRIME = 31;
    protected int hashCode = DEFAULT_HASHCODE;
    public boolean hashCodeDirty = true;

    public String loopKey;



	
			    public Integer id_weather;

				public Integer getId_weather () {
					return this.id_weather;
				}
				
			    public java.util.Date date;

				public java.util.Date getDate () {
					return this.date;
				}
				


	@Override
	public int hashCode() {
		if (this.hashCodeDirty) {
			final int prime = PRIME;
			int result = DEFAULT_HASHCODE;
	
						result = prime * result + ((this.date == null) ? 0 : this.date.hashCode());
					
    		this.hashCode = result;
    		this.hashCodeDirty = false;
		}
		return this.hashCode;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		final row9Struct other = (row9Struct) obj;
		
						if (this.date == null) {
							if (other.date != null)
								return false;
						
						} else if (!this.date.equals(other.date))
						
							return false;
					

		return true;
    }

	public void copyDataTo(row9Struct other) {

		other.id_weather = this.id_weather;
	            other.date = this.date;
	            
	}

	public void copyKeysDataTo(row9Struct other) {

		other.date = this.date;
	            	
	}



	private Integer readInteger(DataInputStream dis, ObjectInputStream ois) throws IOException{
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
	
	private Integer readInteger(DataInputStream dis, org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException{
		Integer intReturn;
        int length = 0;
        length = unmarshaller.readByte();
		if (length == -1) {
			intReturn = null;
		} else {
			intReturn = unmarshaller.readInt();
		}
		return intReturn;
	}

	private void writeInteger(Integer intNum, DataOutputStream dos, ObjectOutputStream oos) throws IOException{
		if(intNum == null) {
            dos.writeByte(-1);
		} else {
			dos.writeByte(0);
	    	dos.writeInt(intNum);
    	}
	}
	
	private void writeInteger(Integer intNum, DataOutputStream dos,org.jboss.marshalling.Marshaller marshaller ) throws IOException{
		if(intNum == null) {
			marshaller.writeByte(-1);
		} else {
			marshaller.writeByte(0);
			marshaller.writeInt(intNum);
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

    public void readKeysData(ObjectInputStream dis) {

		synchronized(commonByteArrayLock_TEST_Fact2) {

        	try {

        		int length = 0;
		
					this.date = readDate(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }
    
    public void readKeysData(org.jboss.marshalling.Unmarshaller dis) {

		synchronized(commonByteArrayLock_TEST_Fact2) {

        	try {

        		int length = 0;
		
					this.date = readDate(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }

    public void writeKeysData(ObjectOutputStream dos) {
        try {

		
					// java.util.Date
				
						writeDate(this.date,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }
    
    public void writeKeysData(org.jboss.marshalling.Marshaller dos) {
        try {

		
					// java.util.Date
				
						writeDate(this.date,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }



    /**
     * Fill Values data by reading ObjectInputStream.
     */
    public void readValuesData(DataInputStream dis, ObjectInputStream ois) {
        try {

			int length = 0;
		
						this.id_weather = readInteger(dis,ois);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

    }
    
    public void readValuesData(DataInputStream dis, org.jboss.marshalling.Unmarshaller objectIn) {
        try {
			int length = 0;
		
						this.id_weather = readInteger(dis,objectIn);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

    }

    /**
     * Return a byte array which represents Values data.
     */
    public void writeValuesData(DataOutputStream dos, ObjectOutputStream oos) {
        try {

		
					writeInteger(this.id_weather, dos, oos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        	}

    }
    
    public void writeValuesData(DataOutputStream dos, org.jboss.marshalling.Marshaller objectOut){
                try {

		
					writeInteger(this.id_weather, dos, objectOut);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        	}
    }


    
    public boolean supportMarshaller(){
        return true;
    }


    public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("[");
		sb.append("id_weather="+String.valueOf(id_weather));
		sb.append(",date="+String.valueOf(date));
	    sb.append("]");

	    return sb.toString();
    }

    /**
     * Compare keys
     */
    public int compareTo(row9Struct other) {

		int returnValue = -1;
		
						returnValue = checkNullsAndCompare(this.date, other.date);
						if(returnValue != 0) {
							return returnValue;
						}

					
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
public void tDBInput_9Process(final java.util.Map<String, Object> globalMap) throws TalendException {
	globalMap.put("tDBInput_9_SUBPROCESS_STATE", 0);

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



		row9Struct row9 = new row9Struct();




	
	/**
	 * [tAdvancedHash_row9 begin ] start
	 */

	

	
		
		ok_Hash.put("tAdvancedHash_row9", false);
		start_Hash.put("tAdvancedHash_row9", System.currentTimeMillis());
		
	
	currentComponent="tAdvancedHash_row9";

	
					if(execStat) {
						runStat.updateStatOnConnection(resourceMap,iterateId,0,0,"row9");
					}
				
		int tos_count_tAdvancedHash_row9 = 0;
		

			   		// connection name:row9
			   		// source node:tDBInput_9 - inputs:(after_tDBInput_1) outputs:(row9,row9) | target node:tAdvancedHash_row9 - inputs:(row9) outputs:()
			   		// linked node: tMap_1 - inputs:(row1,row2,row3,row4,row5,row6,row7,row8,row9,row10,row11,row12,row13,row14,row15,row16,row17,row18,row19,row21) outputs:(out)
			   
			   		org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE matchingModeEnum_row9 = 
			   			org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE.UNIQUE_MATCH;
			   			
			   
	   			org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row9Struct> tHash_Lookup_row9 =org.talend.designer.components.lookup.memory.AdvancedMemoryLookup.
	   						<row9Struct>getLookup(matchingModeEnum_row9);
	   						   
		   	   	   globalMap.put("tHash_Lookup_row9", tHash_Lookup_row9);
		   	   	   
				
           

 



/**
 * [tAdvancedHash_row9 begin ] stop
 */



	
	/**
	 * [tDBInput_9 begin ] start
	 */

	

	
		
		ok_Hash.put("tDBInput_9", false);
		start_Hash.put("tDBInput_9", System.currentTimeMillis());
		
	
	currentComponent="tDBInput_9";

	
		int tos_count_tDBInput_9 = 0;
		
	
    
	
			org.talend.designer.components.util.mssql.MSSqlGenerateTimestampUtil mssqlGTU_tDBInput_9 = org.talend.designer.components.util.mssql.MSSqlUtilFactory.getMSSqlGenerateTimestampUtil();
			
			java.util.List<String> talendToDBList_tDBInput_9 = new java.util.ArrayList();
			String[] talendToDBArray_tDBInput_9  = new String[]{"FLOAT","NUMERIC","NUMERIC IDENTITY","DECIMAL","DECIMAL IDENTITY","REAL"}; 
			java.util.Collections.addAll(talendToDBList_tDBInput_9, talendToDBArray_tDBInput_9); 
		    int nb_line_tDBInput_9 = 0;
		    java.sql.Connection conn_tDBInput_9 = null;
				String driverClass_tDBInput_9 = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
			    java.lang.Class jdbcclazz_tDBInput_9 = java.lang.Class.forName(driverClass_tDBInput_9);
				String dbUser_tDBInput_9 = "bi";
				
				 
	final String decryptedPassword_tDBInput_9 = routines.system.PasswordEncryptUtil.decryptPassword("enc:routine.encryption.key.v1:v6sZ/yM/2O6DaVmCaCVaLiLfefe2UjppQm6VX4my");
				
				String dbPwd_tDBInput_9 = decryptedPassword_tDBInput_9;
				
		    String port_tDBInput_9 = "1433";
		    String dbname_tDBInput_9 = "event_DWH" ;		    
		    String url_tDBInput_9 = "jdbc:sqlserver://" + "localhost" ;
		    if (!"".equals(port_tDBInput_9)) {
		    	url_tDBInput_9 += ":" + "1433";
		    }
		    if (!"".equals(dbname_tDBInput_9)) {		    
		    	url_tDBInput_9 += ";databaseName=" + "event_DWH"; 
		    }
		    url_tDBInput_9 += ";appName=" + projectName + ";" + "trustServerCertificate=true";
		    String dbschema_tDBInput_9 = "dbo";
				
				conn_tDBInput_9 = java.sql.DriverManager.getConnection(url_tDBInput_9,dbUser_tDBInput_9,dbPwd_tDBInput_9);
		        
		    
			java.sql.Statement stmt_tDBInput_9 = conn_tDBInput_9.createStatement();

		    String dbquery_tDBInput_9 = "SELECT dbo.Dim_Weather.id_weather,\n		dbo.Dim_Weather.date\nFROM	dbo.Dim_Weather";
		    

            	globalMap.put("tDBInput_9_QUERY",dbquery_tDBInput_9);
		    java.sql.ResultSet rs_tDBInput_9 = null;

		    try {
		    	rs_tDBInput_9 = stmt_tDBInput_9.executeQuery(dbquery_tDBInput_9);
		    	java.sql.ResultSetMetaData rsmd_tDBInput_9 = rs_tDBInput_9.getMetaData();
		    	int colQtyInRs_tDBInput_9 = rsmd_tDBInput_9.getColumnCount();

		    String tmpContent_tDBInput_9 = null;
		    
		    
		    while (rs_tDBInput_9.next()) {
		        nb_line_tDBInput_9++;
		        
							if(colQtyInRs_tDBInput_9 < 1) {
								row9.id_weather = null;
							} else {
		                          
            row9.id_weather = rs_tDBInput_9.getInt(1);
            if(rs_tDBInput_9.wasNull()){
                    row9.id_weather = null;
            }
		                    }
							if(colQtyInRs_tDBInput_9 < 2) {
								row9.date = null;
							} else {
										
			row9.date = mssqlGTU_tDBInput_9.getDate(rsmd_tDBInput_9, rs_tDBInput_9, 2);
			
		                    }
					





 



/**
 * [tDBInput_9 begin ] stop
 */
	
	/**
	 * [tDBInput_9 main ] start
	 */

	

	
	
	currentComponent="tDBInput_9";

	

 


	tos_count_tDBInput_9++;

/**
 * [tDBInput_9 main ] stop
 */
	
	/**
	 * [tDBInput_9 process_data_begin ] start
	 */

	

	
	
	currentComponent="tDBInput_9";

	

 



/**
 * [tDBInput_9 process_data_begin ] stop
 */

	
	/**
	 * [tAdvancedHash_row9 main ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row9";

	
					if(execStat){
						runStat.updateStatOnConnection(iterateId,1,1
						
							,"row9"
						
						);
					}
					


			   
			   

					row9Struct row9_HashRow = new row9Struct();
		   	   	   
				
				row9_HashRow.id_weather = row9.id_weather;
				
				row9_HashRow.date = row9.date;
				
			tHash_Lookup_row9.put(row9_HashRow);
			
            




 


	tos_count_tAdvancedHash_row9++;

/**
 * [tAdvancedHash_row9 main ] stop
 */
	
	/**
	 * [tAdvancedHash_row9 process_data_begin ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row9";

	

 



/**
 * [tAdvancedHash_row9 process_data_begin ] stop
 */
	
	/**
	 * [tAdvancedHash_row9 process_data_end ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row9";

	

 



/**
 * [tAdvancedHash_row9 process_data_end ] stop
 */



	
	/**
	 * [tDBInput_9 process_data_end ] start
	 */

	

	
	
	currentComponent="tDBInput_9";

	

 



/**
 * [tDBInput_9 process_data_end ] stop
 */
	
	/**
	 * [tDBInput_9 end ] start
	 */

	

	
	
	currentComponent="tDBInput_9";

	

	}
}finally{
	if (rs_tDBInput_9 != null) {
		rs_tDBInput_9.close();
	}
	if (stmt_tDBInput_9 != null) {
		stmt_tDBInput_9.close();
	}
		if(conn_tDBInput_9 != null && !conn_tDBInput_9.isClosed()) {
			
			conn_tDBInput_9.close();
			
			if("com.mysql.cj.jdbc.Driver".equals((String)globalMap.get("driverClass_"))
			    && routines.system.BundleUtils.inOSGi()) {
			        Class.forName("com.mysql.cj.jdbc.AbandonedConnectionCleanupThread").
			            getMethod("checkedShutdown").invoke(null, (Object[]) null);
			}
			
		}
}
globalMap.put("tDBInput_9_NB_LINE",nb_line_tDBInput_9);

 

ok_Hash.put("tDBInput_9", true);
end_Hash.put("tDBInput_9", System.currentTimeMillis());




/**
 * [tDBInput_9 end ] stop
 */

	
	/**
	 * [tAdvancedHash_row9 end ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row9";

	

tHash_Lookup_row9.endPut();

				if(execStat){
			  		runStat.updateStat(resourceMap,iterateId,2,0,"row9");
			  	}
			  	
 

ok_Hash.put("tAdvancedHash_row9", true);
end_Hash.put("tAdvancedHash_row9", System.currentTimeMillis());




/**
 * [tAdvancedHash_row9 end ] stop
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
	 * [tDBInput_9 finally ] start
	 */

	

	
	
	currentComponent="tDBInput_9";

	

 



/**
 * [tDBInput_9 finally ] stop
 */

	
	/**
	 * [tAdvancedHash_row9 finally ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row9";

	

 



/**
 * [tAdvancedHash_row9 finally ] stop
 */



				}catch(java.lang.Exception e){	
					//ignore
				}catch(java.lang.Error error){
					//ignore
				}
				resourceMap = null;
			}
		

		globalMap.put("tDBInput_9_SUBPROCESS_STATE", 1);
	}
	


public static class row10Struct implements routines.system.IPersistableComparableLookupRow<row10Struct> {
    final static byte[] commonByteArrayLock_TEST_Fact2 = new byte[0];
    static byte[] commonByteArray_TEST_Fact2 = new byte[0];
	protected static final int DEFAULT_HASHCODE = 1;
    protected static final int PRIME = 31;
    protected int hashCode = DEFAULT_HASHCODE;
    public boolean hashCodeDirty = true;

    public String loopKey;



	
			    public Integer id_trend;

				public Integer getId_trend () {
					return this.id_trend;
				}
				
			    public String category;

				public String getCategory () {
					return this.category;
				}
				


	@Override
	public int hashCode() {
		if (this.hashCodeDirty) {
			final int prime = PRIME;
			int result = DEFAULT_HASHCODE;
	
						result = prime * result + ((this.category == null) ? 0 : this.category.hashCode());
					
    		this.hashCode = result;
    		this.hashCodeDirty = false;
		}
		return this.hashCode;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		final row10Struct other = (row10Struct) obj;
		
						if (this.category == null) {
							if (other.category != null)
								return false;
						
						} else if (!this.category.equals(other.category))
						
							return false;
					

		return true;
    }

	public void copyDataTo(row10Struct other) {

		other.id_trend = this.id_trend;
	            other.category = this.category;
	            
	}

	public void copyKeysDataTo(row10Struct other) {

		other.category = this.category;
	            	
	}



	private Integer readInteger(DataInputStream dis, ObjectInputStream ois) throws IOException{
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
	
	private Integer readInteger(DataInputStream dis, org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException{
		Integer intReturn;
        int length = 0;
        length = unmarshaller.readByte();
		if (length == -1) {
			intReturn = null;
		} else {
			intReturn = unmarshaller.readInt();
		}
		return intReturn;
	}

	private void writeInteger(Integer intNum, DataOutputStream dos, ObjectOutputStream oos) throws IOException{
		if(intNum == null) {
            dos.writeByte(-1);
		} else {
			dos.writeByte(0);
	    	dos.writeInt(intNum);
    	}
	}
	
	private void writeInteger(Integer intNum, DataOutputStream dos,org.jboss.marshalling.Marshaller marshaller ) throws IOException{
		if(intNum == null) {
			marshaller.writeByte(-1);
		} else {
			marshaller.writeByte(0);
			marshaller.writeInt(intNum);
    	}
	}

	private String readString(ObjectInputStream dis) throws IOException{
		String strReturn = null;
		int length = 0;
        length = dis.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			if(length > commonByteArray_TEST_Fact2.length) {
				if(length < 1024 && commonByteArray_TEST_Fact2.length == 0) {
   					commonByteArray_TEST_Fact2 = new byte[1024];
				} else {
   					commonByteArray_TEST_Fact2 = new byte[2 * length];
   				}
			}
			dis.readFully(commonByteArray_TEST_Fact2, 0, length);
			strReturn = new String(commonByteArray_TEST_Fact2, 0, length, utf8Charset);
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
			if(length > commonByteArray_TEST_Fact2.length) {
				if(length < 1024 && commonByteArray_TEST_Fact2.length == 0) {
   					commonByteArray_TEST_Fact2 = new byte[1024];
				} else {
   					commonByteArray_TEST_Fact2 = new byte[2 * length];
   				}
			}
			unmarshaller.readFully(commonByteArray_TEST_Fact2, 0, length);
			strReturn = new String(commonByteArray_TEST_Fact2, 0, length, utf8Charset);
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

    public void readKeysData(ObjectInputStream dis) {

		synchronized(commonByteArrayLock_TEST_Fact2) {

        	try {

        		int length = 0;
		
					this.category = readString(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }
    
    public void readKeysData(org.jboss.marshalling.Unmarshaller dis) {

		synchronized(commonByteArrayLock_TEST_Fact2) {

        	try {

        		int length = 0;
		
					this.category = readString(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }

    public void writeKeysData(ObjectOutputStream dos) {
        try {

		
					// String
				
						writeString(this.category,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }
    
    public void writeKeysData(org.jboss.marshalling.Marshaller dos) {
        try {

		
					// String
				
						writeString(this.category,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }



    /**
     * Fill Values data by reading ObjectInputStream.
     */
    public void readValuesData(DataInputStream dis, ObjectInputStream ois) {
        try {

			int length = 0;
		
						this.id_trend = readInteger(dis,ois);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

    }
    
    public void readValuesData(DataInputStream dis, org.jboss.marshalling.Unmarshaller objectIn) {
        try {
			int length = 0;
		
						this.id_trend = readInteger(dis,objectIn);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

    }

    /**
     * Return a byte array which represents Values data.
     */
    public void writeValuesData(DataOutputStream dos, ObjectOutputStream oos) {
        try {

		
					writeInteger(this.id_trend, dos, oos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        	}

    }
    
    public void writeValuesData(DataOutputStream dos, org.jboss.marshalling.Marshaller objectOut){
                try {

		
					writeInteger(this.id_trend, dos, objectOut);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        	}
    }


    
    public boolean supportMarshaller(){
        return true;
    }


    public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("[");
		sb.append("id_trend="+String.valueOf(id_trend));
		sb.append(",category="+category);
	    sb.append("]");

	    return sb.toString();
    }

    /**
     * Compare keys
     */
    public int compareTo(row10Struct other) {

		int returnValue = -1;
		
						returnValue = checkNullsAndCompare(this.category, other.category);
						if(returnValue != 0) {
							return returnValue;
						}

					
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
public void tDBInput_10Process(final java.util.Map<String, Object> globalMap) throws TalendException {
	globalMap.put("tDBInput_10_SUBPROCESS_STATE", 0);

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



		row10Struct row10 = new row10Struct();




	
	/**
	 * [tAdvancedHash_row10 begin ] start
	 */

	

	
		
		ok_Hash.put("tAdvancedHash_row10", false);
		start_Hash.put("tAdvancedHash_row10", System.currentTimeMillis());
		
	
	currentComponent="tAdvancedHash_row10";

	
					if(execStat) {
						runStat.updateStatOnConnection(resourceMap,iterateId,0,0,"row10");
					}
				
		int tos_count_tAdvancedHash_row10 = 0;
		

			   		// connection name:row10
			   		// source node:tDBInput_10 - inputs:(after_tDBInput_1) outputs:(row10,row10) | target node:tAdvancedHash_row10 - inputs:(row10) outputs:()
			   		// linked node: tMap_1 - inputs:(row1,row2,row3,row4,row5,row6,row7,row8,row9,row10,row11,row12,row13,row14,row15,row16,row17,row18,row19,row21) outputs:(out)
			   
			   		org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE matchingModeEnum_row10 = 
			   			org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE.UNIQUE_MATCH;
			   			
			   
	   			org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row10Struct> tHash_Lookup_row10 =org.talend.designer.components.lookup.memory.AdvancedMemoryLookup.
	   						<row10Struct>getLookup(matchingModeEnum_row10);
	   						   
		   	   	   globalMap.put("tHash_Lookup_row10", tHash_Lookup_row10);
		   	   	   
				
           

 



/**
 * [tAdvancedHash_row10 begin ] stop
 */



	
	/**
	 * [tDBInput_10 begin ] start
	 */

	

	
		
		ok_Hash.put("tDBInput_10", false);
		start_Hash.put("tDBInput_10", System.currentTimeMillis());
		
	
	currentComponent="tDBInput_10";

	
		int tos_count_tDBInput_10 = 0;
		
	
    
	
			org.talend.designer.components.util.mssql.MSSqlGenerateTimestampUtil mssqlGTU_tDBInput_10 = org.talend.designer.components.util.mssql.MSSqlUtilFactory.getMSSqlGenerateTimestampUtil();
			
			java.util.List<String> talendToDBList_tDBInput_10 = new java.util.ArrayList();
			String[] talendToDBArray_tDBInput_10  = new String[]{"FLOAT","NUMERIC","NUMERIC IDENTITY","DECIMAL","DECIMAL IDENTITY","REAL"}; 
			java.util.Collections.addAll(talendToDBList_tDBInput_10, talendToDBArray_tDBInput_10); 
		    int nb_line_tDBInput_10 = 0;
		    java.sql.Connection conn_tDBInput_10 = null;
				String driverClass_tDBInput_10 = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
			    java.lang.Class jdbcclazz_tDBInput_10 = java.lang.Class.forName(driverClass_tDBInput_10);
				String dbUser_tDBInput_10 = "bi";
				
				 
	final String decryptedPassword_tDBInput_10 = routines.system.PasswordEncryptUtil.decryptPassword("enc:routine.encryption.key.v1:McTjjuwypr+AyyUBk12XZOXM/GM9Ud4EpPkJ61YP");
				
				String dbPwd_tDBInput_10 = decryptedPassword_tDBInput_10;
				
		    String port_tDBInput_10 = "1433";
		    String dbname_tDBInput_10 = "event_DWH" ;		    
		    String url_tDBInput_10 = "jdbc:sqlserver://" + "localhost" ;
		    if (!"".equals(port_tDBInput_10)) {
		    	url_tDBInput_10 += ":" + "1433";
		    }
		    if (!"".equals(dbname_tDBInput_10)) {		    
		    	url_tDBInput_10 += ";databaseName=" + "event_DWH"; 
		    }
		    url_tDBInput_10 += ";appName=" + projectName + ";" + "trustServerCertificate=true";
		    String dbschema_tDBInput_10 = "dbo";
				
				conn_tDBInput_10 = java.sql.DriverManager.getConnection(url_tDBInput_10,dbUser_tDBInput_10,dbPwd_tDBInput_10);
		        
		    
			java.sql.Statement stmt_tDBInput_10 = conn_tDBInput_10.createStatement();

		    String dbquery_tDBInput_10 = "SELECT dbo.Dim_Trends.id_trend,\n		dbo.Dim_Trends.category\nFROM	dbo.Dim_Trends";
		    

            	globalMap.put("tDBInput_10_QUERY",dbquery_tDBInput_10);
		    java.sql.ResultSet rs_tDBInput_10 = null;

		    try {
		    	rs_tDBInput_10 = stmt_tDBInput_10.executeQuery(dbquery_tDBInput_10);
		    	java.sql.ResultSetMetaData rsmd_tDBInput_10 = rs_tDBInput_10.getMetaData();
		    	int colQtyInRs_tDBInput_10 = rsmd_tDBInput_10.getColumnCount();

		    String tmpContent_tDBInput_10 = null;
		    
		    
		    while (rs_tDBInput_10.next()) {
		        nb_line_tDBInput_10++;
		        
							if(colQtyInRs_tDBInput_10 < 1) {
								row10.id_trend = null;
							} else {
		                          
            row10.id_trend = rs_tDBInput_10.getInt(1);
            if(rs_tDBInput_10.wasNull()){
                    row10.id_trend = null;
            }
		                    }
							if(colQtyInRs_tDBInput_10 < 2) {
								row10.category = null;
							} else {
	                         		
           		tmpContent_tDBInput_10 = rs_tDBInput_10.getString(2);
            if(tmpContent_tDBInput_10 != null) {
            	if (talendToDBList_tDBInput_10 .contains(rsmd_tDBInput_10.getColumnTypeName(2).toUpperCase(java.util.Locale.ENGLISH))) {
            		row10.category = FormatterUtils.formatUnwithE(tmpContent_tDBInput_10);
            	} else {
                	row10.category = tmpContent_tDBInput_10;
                }
            } else {
                row10.category = null;
            }
		                    }
					





 



/**
 * [tDBInput_10 begin ] stop
 */
	
	/**
	 * [tDBInput_10 main ] start
	 */

	

	
	
	currentComponent="tDBInput_10";

	

 


	tos_count_tDBInput_10++;

/**
 * [tDBInput_10 main ] stop
 */
	
	/**
	 * [tDBInput_10 process_data_begin ] start
	 */

	

	
	
	currentComponent="tDBInput_10";

	

 



/**
 * [tDBInput_10 process_data_begin ] stop
 */

	
	/**
	 * [tAdvancedHash_row10 main ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row10";

	
					if(execStat){
						runStat.updateStatOnConnection(iterateId,1,1
						
							,"row10"
						
						);
					}
					


			   
			   

					row10Struct row10_HashRow = new row10Struct();
		   	   	   
				
				row10_HashRow.id_trend = row10.id_trend;
				
				row10_HashRow.category = row10.category;
				
			tHash_Lookup_row10.put(row10_HashRow);
			
            




 


	tos_count_tAdvancedHash_row10++;

/**
 * [tAdvancedHash_row10 main ] stop
 */
	
	/**
	 * [tAdvancedHash_row10 process_data_begin ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row10";

	

 



/**
 * [tAdvancedHash_row10 process_data_begin ] stop
 */
	
	/**
	 * [tAdvancedHash_row10 process_data_end ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row10";

	

 



/**
 * [tAdvancedHash_row10 process_data_end ] stop
 */



	
	/**
	 * [tDBInput_10 process_data_end ] start
	 */

	

	
	
	currentComponent="tDBInput_10";

	

 



/**
 * [tDBInput_10 process_data_end ] stop
 */
	
	/**
	 * [tDBInput_10 end ] start
	 */

	

	
	
	currentComponent="tDBInput_10";

	

	}
}finally{
	if (rs_tDBInput_10 != null) {
		rs_tDBInput_10.close();
	}
	if (stmt_tDBInput_10 != null) {
		stmt_tDBInput_10.close();
	}
		if(conn_tDBInput_10 != null && !conn_tDBInput_10.isClosed()) {
			
			conn_tDBInput_10.close();
			
			if("com.mysql.cj.jdbc.Driver".equals((String)globalMap.get("driverClass_"))
			    && routines.system.BundleUtils.inOSGi()) {
			        Class.forName("com.mysql.cj.jdbc.AbandonedConnectionCleanupThread").
			            getMethod("checkedShutdown").invoke(null, (Object[]) null);
			}
			
		}
}
globalMap.put("tDBInput_10_NB_LINE",nb_line_tDBInput_10);

 

ok_Hash.put("tDBInput_10", true);
end_Hash.put("tDBInput_10", System.currentTimeMillis());




/**
 * [tDBInput_10 end ] stop
 */

	
	/**
	 * [tAdvancedHash_row10 end ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row10";

	

tHash_Lookup_row10.endPut();

				if(execStat){
			  		runStat.updateStat(resourceMap,iterateId,2,0,"row10");
			  	}
			  	
 

ok_Hash.put("tAdvancedHash_row10", true);
end_Hash.put("tAdvancedHash_row10", System.currentTimeMillis());




/**
 * [tAdvancedHash_row10 end ] stop
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
	 * [tDBInput_10 finally ] start
	 */

	

	
	
	currentComponent="tDBInput_10";

	

 



/**
 * [tDBInput_10 finally ] stop
 */

	
	/**
	 * [tAdvancedHash_row10 finally ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row10";

	

 



/**
 * [tAdvancedHash_row10 finally ] stop
 */



				}catch(java.lang.Exception e){	
					//ignore
				}catch(java.lang.Error error){
					//ignore
				}
				resourceMap = null;
			}
		

		globalMap.put("tDBInput_10_SUBPROCESS_STATE", 1);
	}
	


public static class row11Struct implements routines.system.IPersistableComparableLookupRow<row11Struct> {
    final static byte[] commonByteArrayLock_TEST_Fact2 = new byte[0];
    static byte[] commonByteArray_TEST_Fact2 = new byte[0];
	protected static final int DEFAULT_HASHCODE = 1;
    protected static final int PRIME = 31;
    protected int hashCode = DEFAULT_HASHCODE;
    public boolean hashCodeDirty = true;

    public String loopKey;



	
			    public Integer dim_concurrence_id;

				public Integer getDim_concurrence_id () {
					return this.dim_concurrence_id;
				}
				
			    public String category;

				public String getCategory () {
					return this.category;
				}
				
			    public String city;

				public String getCity () {
					return this.city;
				}
				
			    public String country;

				public String getCountry () {
					return this.country;
				}
				


	@Override
	public int hashCode() {
		if (this.hashCodeDirty) {
			final int prime = PRIME;
			int result = DEFAULT_HASHCODE;
	
						result = prime * result + ((this.category == null) ? 0 : this.category.hashCode());
					
    		this.hashCode = result;
    		this.hashCodeDirty = false;
		}
		return this.hashCode;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		final row11Struct other = (row11Struct) obj;
		
						if (this.category == null) {
							if (other.category != null)
								return false;
						
						} else if (!this.category.equals(other.category))
						
							return false;
					

		return true;
    }

	public void copyDataTo(row11Struct other) {

		other.dim_concurrence_id = this.dim_concurrence_id;
	            other.category = this.category;
	            other.city = this.city;
	            other.country = this.country;
	            
	}

	public void copyKeysDataTo(row11Struct other) {

		other.category = this.category;
	            	
	}



	private Integer readInteger(DataInputStream dis, ObjectInputStream ois) throws IOException{
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
	
	private Integer readInteger(DataInputStream dis, org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException{
		Integer intReturn;
        int length = 0;
        length = unmarshaller.readByte();
		if (length == -1) {
			intReturn = null;
		} else {
			intReturn = unmarshaller.readInt();
		}
		return intReturn;
	}

	private void writeInteger(Integer intNum, DataOutputStream dos, ObjectOutputStream oos) throws IOException{
		if(intNum == null) {
            dos.writeByte(-1);
		} else {
			dos.writeByte(0);
	    	dos.writeInt(intNum);
    	}
	}
	
	private void writeInteger(Integer intNum, DataOutputStream dos,org.jboss.marshalling.Marshaller marshaller ) throws IOException{
		if(intNum == null) {
			marshaller.writeByte(-1);
		} else {
			marshaller.writeByte(0);
			marshaller.writeInt(intNum);
    	}
	}

	private String readString(ObjectInputStream dis) throws IOException{
		String strReturn = null;
		int length = 0;
        length = dis.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			if(length > commonByteArray_TEST_Fact2.length) {
				if(length < 1024 && commonByteArray_TEST_Fact2.length == 0) {
   					commonByteArray_TEST_Fact2 = new byte[1024];
				} else {
   					commonByteArray_TEST_Fact2 = new byte[2 * length];
   				}
			}
			dis.readFully(commonByteArray_TEST_Fact2, 0, length);
			strReturn = new String(commonByteArray_TEST_Fact2, 0, length, utf8Charset);
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
			if(length > commonByteArray_TEST_Fact2.length) {
				if(length < 1024 && commonByteArray_TEST_Fact2.length == 0) {
   					commonByteArray_TEST_Fact2 = new byte[1024];
				} else {
   					commonByteArray_TEST_Fact2 = new byte[2 * length];
   				}
			}
			unmarshaller.readFully(commonByteArray_TEST_Fact2, 0, length);
			strReturn = new String(commonByteArray_TEST_Fact2, 0, length, utf8Charset);
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
	
	private String readString(DataInputStream dis, ObjectInputStream ois) throws IOException{
		String strReturn = null;
		int length = 0;
        length = dis.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			byte[] byteArray = new byte[length];
			dis.read(byteArray);
			strReturn = new String(byteArray, utf8Charset);
		}
		return strReturn;
	}
	
	private String readString(DataInputStream dis, org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException{
		String strReturn = null;
		int length = 0;
        length = unmarshaller.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			byte[] byteArray = new byte[length];
			unmarshaller.read(byteArray);
			strReturn = new String(byteArray, utf8Charset);
		}
		return strReturn;
	}
	
	private void writeString(String str, DataOutputStream dos, org.jboss.marshalling.Marshaller marshaller) throws IOException{
		if(str == null) {
			marshaller.writeInt(-1);
		} else {
            byte[] byteArray = str.getBytes(utf8Charset);
            marshaller.writeInt(byteArray.length);
            marshaller.write(byteArray);
    	}
	}

	private void writeString(String str, DataOutputStream dos, ObjectOutputStream oos) throws IOException{
		if(str == null) {
            dos.writeInt(-1);
		} else {
            byte[] byteArray = str.getBytes(utf8Charset);
	    	dos.writeInt(byteArray.length);
			dos.write(byteArray);
    	}
	}

    public void readKeysData(ObjectInputStream dis) {

		synchronized(commonByteArrayLock_TEST_Fact2) {

        	try {

        		int length = 0;
		
					this.category = readString(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }
    
    public void readKeysData(org.jboss.marshalling.Unmarshaller dis) {

		synchronized(commonByteArrayLock_TEST_Fact2) {

        	try {

        		int length = 0;
		
					this.category = readString(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }

    public void writeKeysData(ObjectOutputStream dos) {
        try {

		
					// String
				
						writeString(this.category,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }
    
    public void writeKeysData(org.jboss.marshalling.Marshaller dos) {
        try {

		
					// String
				
						writeString(this.category,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }



    /**
     * Fill Values data by reading ObjectInputStream.
     */
    public void readValuesData(DataInputStream dis, ObjectInputStream ois) {
        try {

			int length = 0;
		
						this.dim_concurrence_id = readInteger(dis,ois);
					
						this.city = readString(dis,ois);
					
						this.country = readString(dis,ois);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

    }
    
    public void readValuesData(DataInputStream dis, org.jboss.marshalling.Unmarshaller objectIn) {
        try {
			int length = 0;
		
						this.dim_concurrence_id = readInteger(dis,objectIn);
					
						this.city = readString(dis,objectIn);
					
						this.country = readString(dis,objectIn);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

    }

    /**
     * Return a byte array which represents Values data.
     */
    public void writeValuesData(DataOutputStream dos, ObjectOutputStream oos) {
        try {

		
					writeInteger(this.dim_concurrence_id, dos, oos);
					
						writeString(this.city, dos, oos);
					
						writeString(this.country, dos, oos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        	}

    }
    
    public void writeValuesData(DataOutputStream dos, org.jboss.marshalling.Marshaller objectOut){
                try {

		
					writeInteger(this.dim_concurrence_id, dos, objectOut);
					
						writeString(this.city, dos, objectOut);
					
						writeString(this.country, dos, objectOut);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        	}
    }


    
    public boolean supportMarshaller(){
        return true;
    }


    public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("[");
		sb.append("dim_concurrence_id="+String.valueOf(dim_concurrence_id));
		sb.append(",category="+category);
		sb.append(",city="+city);
		sb.append(",country="+country);
	    sb.append("]");

	    return sb.toString();
    }

    /**
     * Compare keys
     */
    public int compareTo(row11Struct other) {

		int returnValue = -1;
		
						returnValue = checkNullsAndCompare(this.category, other.category);
						if(returnValue != 0) {
							return returnValue;
						}

					
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
public void tDBInput_11Process(final java.util.Map<String, Object> globalMap) throws TalendException {
	globalMap.put("tDBInput_11_SUBPROCESS_STATE", 0);

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



		row11Struct row11 = new row11Struct();




	
	/**
	 * [tAdvancedHash_row11 begin ] start
	 */

	

	
		
		ok_Hash.put("tAdvancedHash_row11", false);
		start_Hash.put("tAdvancedHash_row11", System.currentTimeMillis());
		
	
	currentComponent="tAdvancedHash_row11";

	
					if(execStat) {
						runStat.updateStatOnConnection(resourceMap,iterateId,0,0,"row11");
					}
				
		int tos_count_tAdvancedHash_row11 = 0;
		

			   		// connection name:row11
			   		// source node:tDBInput_11 - inputs:(after_tDBInput_1) outputs:(row11,row11) | target node:tAdvancedHash_row11 - inputs:(row11) outputs:()
			   		// linked node: tMap_1 - inputs:(row1,row2,row3,row4,row5,row6,row7,row8,row9,row10,row11,row12,row13,row14,row15,row16,row17,row18,row19,row21) outputs:(out)
			   
			   		org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE matchingModeEnum_row11 = 
			   			org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE.UNIQUE_MATCH;
			   			
			   
	   			org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row11Struct> tHash_Lookup_row11 =org.talend.designer.components.lookup.memory.AdvancedMemoryLookup.
	   						<row11Struct>getLookup(matchingModeEnum_row11);
	   						   
		   	   	   globalMap.put("tHash_Lookup_row11", tHash_Lookup_row11);
		   	   	   
				
           

 



/**
 * [tAdvancedHash_row11 begin ] stop
 */



	
	/**
	 * [tDBInput_11 begin ] start
	 */

	

	
		
		ok_Hash.put("tDBInput_11", false);
		start_Hash.put("tDBInput_11", System.currentTimeMillis());
		
	
	currentComponent="tDBInput_11";

	
		int tos_count_tDBInput_11 = 0;
		
	
    
	
			org.talend.designer.components.util.mssql.MSSqlGenerateTimestampUtil mssqlGTU_tDBInput_11 = org.talend.designer.components.util.mssql.MSSqlUtilFactory.getMSSqlGenerateTimestampUtil();
			
			java.util.List<String> talendToDBList_tDBInput_11 = new java.util.ArrayList();
			String[] talendToDBArray_tDBInput_11  = new String[]{"FLOAT","NUMERIC","NUMERIC IDENTITY","DECIMAL","DECIMAL IDENTITY","REAL"}; 
			java.util.Collections.addAll(talendToDBList_tDBInput_11, talendToDBArray_tDBInput_11); 
		    int nb_line_tDBInput_11 = 0;
		    java.sql.Connection conn_tDBInput_11 = null;
				String driverClass_tDBInput_11 = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
			    java.lang.Class jdbcclazz_tDBInput_11 = java.lang.Class.forName(driverClass_tDBInput_11);
				String dbUser_tDBInput_11 = "bi";
				
				 
	final String decryptedPassword_tDBInput_11 = routines.system.PasswordEncryptUtil.decryptPassword("enc:routine.encryption.key.v1:hHYME2mIGRBEyWhZcDpSITk5jK0tbf5+XOUEAzD7");
				
				String dbPwd_tDBInput_11 = decryptedPassword_tDBInput_11;
				
		    String port_tDBInput_11 = "1433";
		    String dbname_tDBInput_11 = "event_DWH" ;		    
		    String url_tDBInput_11 = "jdbc:sqlserver://" + "localhost" ;
		    if (!"".equals(port_tDBInput_11)) {
		    	url_tDBInput_11 += ":" + "1433";
		    }
		    if (!"".equals(dbname_tDBInput_11)) {		    
		    	url_tDBInput_11 += ";databaseName=" + "event_DWH"; 
		    }
		    url_tDBInput_11 += ";appName=" + projectName + ";" + "trustServerCertificate=true";
		    String dbschema_tDBInput_11 = "dbo";
				
				conn_tDBInput_11 = java.sql.DriverManager.getConnection(url_tDBInput_11,dbUser_tDBInput_11,dbPwd_tDBInput_11);
		        
		    
			java.sql.Statement stmt_tDBInput_11 = conn_tDBInput_11.createStatement();

		    String dbquery_tDBInput_11 = "SELECT dbo.Dim_Concurrence.dim_concurrence_id,\n		dbo.Dim_Concurrence.category,\n		dbo.Dim_Concurrence.city,\n		dbo.Dim_Co"
+"ncurrence.country\nFROM	dbo.Dim_Concurrence";
		    

            	globalMap.put("tDBInput_11_QUERY",dbquery_tDBInput_11);
		    java.sql.ResultSet rs_tDBInput_11 = null;

		    try {
		    	rs_tDBInput_11 = stmt_tDBInput_11.executeQuery(dbquery_tDBInput_11);
		    	java.sql.ResultSetMetaData rsmd_tDBInput_11 = rs_tDBInput_11.getMetaData();
		    	int colQtyInRs_tDBInput_11 = rsmd_tDBInput_11.getColumnCount();

		    String tmpContent_tDBInput_11 = null;
		    
		    
		    while (rs_tDBInput_11.next()) {
		        nb_line_tDBInput_11++;
		        
							if(colQtyInRs_tDBInput_11 < 1) {
								row11.dim_concurrence_id = null;
							} else {
		                          
            row11.dim_concurrence_id = rs_tDBInput_11.getInt(1);
            if(rs_tDBInput_11.wasNull()){
                    row11.dim_concurrence_id = null;
            }
		                    }
							if(colQtyInRs_tDBInput_11 < 2) {
								row11.category = null;
							} else {
	                         		
           		tmpContent_tDBInput_11 = rs_tDBInput_11.getString(2);
            if(tmpContent_tDBInput_11 != null) {
            	if (talendToDBList_tDBInput_11 .contains(rsmd_tDBInput_11.getColumnTypeName(2).toUpperCase(java.util.Locale.ENGLISH))) {
            		row11.category = FormatterUtils.formatUnwithE(tmpContent_tDBInput_11);
            	} else {
                	row11.category = tmpContent_tDBInput_11;
                }
            } else {
                row11.category = null;
            }
		                    }
							if(colQtyInRs_tDBInput_11 < 3) {
								row11.city = null;
							} else {
	                         		
           		tmpContent_tDBInput_11 = rs_tDBInput_11.getString(3);
            if(tmpContent_tDBInput_11 != null) {
            	if (talendToDBList_tDBInput_11 .contains(rsmd_tDBInput_11.getColumnTypeName(3).toUpperCase(java.util.Locale.ENGLISH))) {
            		row11.city = FormatterUtils.formatUnwithE(tmpContent_tDBInput_11);
            	} else {
                	row11.city = tmpContent_tDBInput_11;
                }
            } else {
                row11.city = null;
            }
		                    }
							if(colQtyInRs_tDBInput_11 < 4) {
								row11.country = null;
							} else {
	                         		
           		tmpContent_tDBInput_11 = rs_tDBInput_11.getString(4);
            if(tmpContent_tDBInput_11 != null) {
            	if (talendToDBList_tDBInput_11 .contains(rsmd_tDBInput_11.getColumnTypeName(4).toUpperCase(java.util.Locale.ENGLISH))) {
            		row11.country = FormatterUtils.formatUnwithE(tmpContent_tDBInput_11);
            	} else {
                	row11.country = tmpContent_tDBInput_11;
                }
            } else {
                row11.country = null;
            }
		                    }
					





 



/**
 * [tDBInput_11 begin ] stop
 */
	
	/**
	 * [tDBInput_11 main ] start
	 */

	

	
	
	currentComponent="tDBInput_11";

	

 


	tos_count_tDBInput_11++;

/**
 * [tDBInput_11 main ] stop
 */
	
	/**
	 * [tDBInput_11 process_data_begin ] start
	 */

	

	
	
	currentComponent="tDBInput_11";

	

 



/**
 * [tDBInput_11 process_data_begin ] stop
 */

	
	/**
	 * [tAdvancedHash_row11 main ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row11";

	
					if(execStat){
						runStat.updateStatOnConnection(iterateId,1,1
						
							,"row11"
						
						);
					}
					


			   
			   

					row11Struct row11_HashRow = new row11Struct();
		   	   	   
				
				row11_HashRow.dim_concurrence_id = row11.dim_concurrence_id;
				
				row11_HashRow.category = row11.category;
				
				row11_HashRow.city = row11.city;
				
				row11_HashRow.country = row11.country;
				
			tHash_Lookup_row11.put(row11_HashRow);
			
            




 


	tos_count_tAdvancedHash_row11++;

/**
 * [tAdvancedHash_row11 main ] stop
 */
	
	/**
	 * [tAdvancedHash_row11 process_data_begin ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row11";

	

 



/**
 * [tAdvancedHash_row11 process_data_begin ] stop
 */
	
	/**
	 * [tAdvancedHash_row11 process_data_end ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row11";

	

 



/**
 * [tAdvancedHash_row11 process_data_end ] stop
 */



	
	/**
	 * [tDBInput_11 process_data_end ] start
	 */

	

	
	
	currentComponent="tDBInput_11";

	

 



/**
 * [tDBInput_11 process_data_end ] stop
 */
	
	/**
	 * [tDBInput_11 end ] start
	 */

	

	
	
	currentComponent="tDBInput_11";

	

	}
}finally{
	if (rs_tDBInput_11 != null) {
		rs_tDBInput_11.close();
	}
	if (stmt_tDBInput_11 != null) {
		stmt_tDBInput_11.close();
	}
		if(conn_tDBInput_11 != null && !conn_tDBInput_11.isClosed()) {
			
			conn_tDBInput_11.close();
			
			if("com.mysql.cj.jdbc.Driver".equals((String)globalMap.get("driverClass_"))
			    && routines.system.BundleUtils.inOSGi()) {
			        Class.forName("com.mysql.cj.jdbc.AbandonedConnectionCleanupThread").
			            getMethod("checkedShutdown").invoke(null, (Object[]) null);
			}
			
		}
}
globalMap.put("tDBInput_11_NB_LINE",nb_line_tDBInput_11);

 

ok_Hash.put("tDBInput_11", true);
end_Hash.put("tDBInput_11", System.currentTimeMillis());




/**
 * [tDBInput_11 end ] stop
 */

	
	/**
	 * [tAdvancedHash_row11 end ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row11";

	

tHash_Lookup_row11.endPut();

				if(execStat){
			  		runStat.updateStat(resourceMap,iterateId,2,0,"row11");
			  	}
			  	
 

ok_Hash.put("tAdvancedHash_row11", true);
end_Hash.put("tAdvancedHash_row11", System.currentTimeMillis());




/**
 * [tAdvancedHash_row11 end ] stop
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
	 * [tDBInput_11 finally ] start
	 */

	

	
	
	currentComponent="tDBInput_11";

	

 



/**
 * [tDBInput_11 finally ] stop
 */

	
	/**
	 * [tAdvancedHash_row11 finally ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row11";

	

 



/**
 * [tAdvancedHash_row11 finally ] stop
 */



				}catch(java.lang.Exception e){	
					//ignore
				}catch(java.lang.Error error){
					//ignore
				}
				resourceMap = null;
			}
		

		globalMap.put("tDBInput_11_SUBPROCESS_STATE", 1);
	}
	


public static class row12Struct implements routines.system.IPersistableComparableLookupRow<row12Struct> {
    final static byte[] commonByteArrayLock_TEST_Fact2 = new byte[0];
    static byte[] commonByteArray_TEST_Fact2 = new byte[0];
	protected static final int DEFAULT_HASHCODE = 1;
    protected static final int PRIME = 31;
    protected int hashCode = DEFAULT_HASHCODE;
    public boolean hashCodeDirty = true;

    public String loopKey;



	
			    public Integer id_provider;

				public Integer getId_provider () {
					return this.id_provider;
				}
				
			    public String city;

				public String getCity () {
					return this.city;
				}
				


	@Override
	public int hashCode() {
		if (this.hashCodeDirty) {
			final int prime = PRIME;
			int result = DEFAULT_HASHCODE;
	
						result = prime * result + ((this.id_provider == null) ? 0 : this.id_provider.hashCode());
					
    		this.hashCode = result;
    		this.hashCodeDirty = false;
		}
		return this.hashCode;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		final row12Struct other = (row12Struct) obj;
		
						if (this.id_provider == null) {
							if (other.id_provider != null)
								return false;
						
						} else if (!this.id_provider.equals(other.id_provider))
						
							return false;
					

		return true;
    }

	public void copyDataTo(row12Struct other) {

		other.id_provider = this.id_provider;
	            other.city = this.city;
	            
	}

	public void copyKeysDataTo(row12Struct other) {

		other.id_provider = this.id_provider;
	            	
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
	
	private String readString(DataInputStream dis, ObjectInputStream ois) throws IOException{
		String strReturn = null;
		int length = 0;
        length = dis.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			byte[] byteArray = new byte[length];
			dis.read(byteArray);
			strReturn = new String(byteArray, utf8Charset);
		}
		return strReturn;
	}
	
	private String readString(DataInputStream dis, org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException{
		String strReturn = null;
		int length = 0;
        length = unmarshaller.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			byte[] byteArray = new byte[length];
			unmarshaller.read(byteArray);
			strReturn = new String(byteArray, utf8Charset);
		}
		return strReturn;
	}
	
	private void writeString(String str, DataOutputStream dos, org.jboss.marshalling.Marshaller marshaller) throws IOException{
		if(str == null) {
			marshaller.writeInt(-1);
		} else {
            byte[] byteArray = str.getBytes(utf8Charset);
            marshaller.writeInt(byteArray.length);
            marshaller.write(byteArray);
    	}
	}

	private void writeString(String str, DataOutputStream dos, ObjectOutputStream oos) throws IOException{
		if(str == null) {
            dos.writeInt(-1);
		} else {
            byte[] byteArray = str.getBytes(utf8Charset);
	    	dos.writeInt(byteArray.length);
			dos.write(byteArray);
    	}
	}

    public void readKeysData(ObjectInputStream dis) {

		synchronized(commonByteArrayLock_TEST_Fact2) {

        	try {

        		int length = 0;
		
						this.id_provider = readInteger(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }
    
    public void readKeysData(org.jboss.marshalling.Unmarshaller dis) {

		synchronized(commonByteArrayLock_TEST_Fact2) {

        	try {

        		int length = 0;
		
						this.id_provider = readInteger(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }

    public void writeKeysData(ObjectOutputStream dos) {
        try {

		
					// Integer
				
						writeInteger(this.id_provider,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }
    
    public void writeKeysData(org.jboss.marshalling.Marshaller dos) {
        try {

		
					// Integer
				
						writeInteger(this.id_provider,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }



    /**
     * Fill Values data by reading ObjectInputStream.
     */
    public void readValuesData(DataInputStream dis, ObjectInputStream ois) {
        try {

			int length = 0;
		
						this.city = readString(dis,ois);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

    }
    
    public void readValuesData(DataInputStream dis, org.jboss.marshalling.Unmarshaller objectIn) {
        try {
			int length = 0;
		
						this.city = readString(dis,objectIn);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

    }

    /**
     * Return a byte array which represents Values data.
     */
    public void writeValuesData(DataOutputStream dos, ObjectOutputStream oos) {
        try {

		
						writeString(this.city, dos, oos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        	}

    }
    
    public void writeValuesData(DataOutputStream dos, org.jboss.marshalling.Marshaller objectOut){
                try {

		
						writeString(this.city, dos, objectOut);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        	}
    }


    
    public boolean supportMarshaller(){
        return true;
    }


    public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("[");
		sb.append("id_provider="+String.valueOf(id_provider));
		sb.append(",city="+city);
	    sb.append("]");

	    return sb.toString();
    }

    /**
     * Compare keys
     */
    public int compareTo(row12Struct other) {

		int returnValue = -1;
		
						returnValue = checkNullsAndCompare(this.id_provider, other.id_provider);
						if(returnValue != 0) {
							return returnValue;
						}

					
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
public void tDBInput_13Process(final java.util.Map<String, Object> globalMap) throws TalendException {
	globalMap.put("tDBInput_13_SUBPROCESS_STATE", 0);

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



		row12Struct row12 = new row12Struct();




	
	/**
	 * [tAdvancedHash_row12 begin ] start
	 */

	

	
		
		ok_Hash.put("tAdvancedHash_row12", false);
		start_Hash.put("tAdvancedHash_row12", System.currentTimeMillis());
		
	
	currentComponent="tAdvancedHash_row12";

	
					if(execStat) {
						runStat.updateStatOnConnection(resourceMap,iterateId,0,0,"row12");
					}
				
		int tos_count_tAdvancedHash_row12 = 0;
		

			   		// connection name:row12
			   		// source node:tDBInput_13 - inputs:(after_tDBInput_1) outputs:(row12,row12) | target node:tAdvancedHash_row12 - inputs:(row12) outputs:()
			   		// linked node: tMap_1 - inputs:(row1,row2,row3,row4,row5,row6,row7,row8,row9,row10,row11,row12,row13,row14,row15,row16,row17,row18,row19,row21) outputs:(out)
			   
			   		org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE matchingModeEnum_row12 = 
			   			org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE.UNIQUE_MATCH;
			   			
			   
	   			org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row12Struct> tHash_Lookup_row12 =org.talend.designer.components.lookup.memory.AdvancedMemoryLookup.
	   						<row12Struct>getLookup(matchingModeEnum_row12);
	   						   
		   	   	   globalMap.put("tHash_Lookup_row12", tHash_Lookup_row12);
		   	   	   
				
           

 



/**
 * [tAdvancedHash_row12 begin ] stop
 */



	
	/**
	 * [tDBInput_13 begin ] start
	 */

	

	
		
		ok_Hash.put("tDBInput_13", false);
		start_Hash.put("tDBInput_13", System.currentTimeMillis());
		
	
	currentComponent="tDBInput_13";

	
		int tos_count_tDBInput_13 = 0;
		
	
    
	
			org.talend.designer.components.util.mssql.MSSqlGenerateTimestampUtil mssqlGTU_tDBInput_13 = org.talend.designer.components.util.mssql.MSSqlUtilFactory.getMSSqlGenerateTimestampUtil();
			
			java.util.List<String> talendToDBList_tDBInput_13 = new java.util.ArrayList();
			String[] talendToDBArray_tDBInput_13  = new String[]{"FLOAT","NUMERIC","NUMERIC IDENTITY","DECIMAL","DECIMAL IDENTITY","REAL"}; 
			java.util.Collections.addAll(talendToDBList_tDBInput_13, talendToDBArray_tDBInput_13); 
		    int nb_line_tDBInput_13 = 0;
		    java.sql.Connection conn_tDBInput_13 = null;
				String driverClass_tDBInput_13 = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
			    java.lang.Class jdbcclazz_tDBInput_13 = java.lang.Class.forName(driverClass_tDBInput_13);
				String dbUser_tDBInput_13 = "bi";
				
				 
	final String decryptedPassword_tDBInput_13 = routines.system.PasswordEncryptUtil.decryptPassword("enc:routine.encryption.key.v1:8JgQcDZvL9+OxRtbQTWFzp7d//SvGxwJIAcvJa0n");
				
				String dbPwd_tDBInput_13 = decryptedPassword_tDBInput_13;
				
		    String port_tDBInput_13 = "1433";
		    String dbname_tDBInput_13 = "event_DWH" ;		    
		    String url_tDBInput_13 = "jdbc:sqlserver://" + "localhost" ;
		    if (!"".equals(port_tDBInput_13)) {
		    	url_tDBInput_13 += ":" + "1433";
		    }
		    if (!"".equals(dbname_tDBInput_13)) {		    
		    	url_tDBInput_13 += ";databaseName=" + "event_DWH"; 
		    }
		    url_tDBInput_13 += ";appName=" + projectName + ";" + "trustServerCertificate=true";
		    String dbschema_tDBInput_13 = "dbo";
				
				conn_tDBInput_13 = java.sql.DriverManager.getConnection(url_tDBInput_13,dbUser_tDBInput_13,dbPwd_tDBInput_13);
		        
		    
			java.sql.Statement stmt_tDBInput_13 = conn_tDBInput_13.createStatement();

		    String dbquery_tDBInput_13 = "SELECT dbo.Dim_Provider.id_provider,\n		dbo.Dim_Provider.city\nFROM	dbo.Dim_Provider";
		    

            	globalMap.put("tDBInput_13_QUERY",dbquery_tDBInput_13);
		    java.sql.ResultSet rs_tDBInput_13 = null;

		    try {
		    	rs_tDBInput_13 = stmt_tDBInput_13.executeQuery(dbquery_tDBInput_13);
		    	java.sql.ResultSetMetaData rsmd_tDBInput_13 = rs_tDBInput_13.getMetaData();
		    	int colQtyInRs_tDBInput_13 = rsmd_tDBInput_13.getColumnCount();

		    String tmpContent_tDBInput_13 = null;
		    
		    
		    while (rs_tDBInput_13.next()) {
		        nb_line_tDBInput_13++;
		        
							if(colQtyInRs_tDBInput_13 < 1) {
								row12.id_provider = null;
							} else {
		                          
            row12.id_provider = rs_tDBInput_13.getInt(1);
            if(rs_tDBInput_13.wasNull()){
                    row12.id_provider = null;
            }
		                    }
							if(colQtyInRs_tDBInput_13 < 2) {
								row12.city = null;
							} else {
	                         		
           		tmpContent_tDBInput_13 = rs_tDBInput_13.getString(2);
            if(tmpContent_tDBInput_13 != null) {
            	if (talendToDBList_tDBInput_13 .contains(rsmd_tDBInput_13.getColumnTypeName(2).toUpperCase(java.util.Locale.ENGLISH))) {
            		row12.city = FormatterUtils.formatUnwithE(tmpContent_tDBInput_13);
            	} else {
                	row12.city = tmpContent_tDBInput_13;
                }
            } else {
                row12.city = null;
            }
		                    }
					





 



/**
 * [tDBInput_13 begin ] stop
 */
	
	/**
	 * [tDBInput_13 main ] start
	 */

	

	
	
	currentComponent="tDBInput_13";

	

 


	tos_count_tDBInput_13++;

/**
 * [tDBInput_13 main ] stop
 */
	
	/**
	 * [tDBInput_13 process_data_begin ] start
	 */

	

	
	
	currentComponent="tDBInput_13";

	

 



/**
 * [tDBInput_13 process_data_begin ] stop
 */

	
	/**
	 * [tAdvancedHash_row12 main ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row12";

	
					if(execStat){
						runStat.updateStatOnConnection(iterateId,1,1
						
							,"row12"
						
						);
					}
					


			   
			   

					row12Struct row12_HashRow = new row12Struct();
		   	   	   
				
				row12_HashRow.id_provider = row12.id_provider;
				
				row12_HashRow.city = row12.city;
				
			tHash_Lookup_row12.put(row12_HashRow);
			
            




 


	tos_count_tAdvancedHash_row12++;

/**
 * [tAdvancedHash_row12 main ] stop
 */
	
	/**
	 * [tAdvancedHash_row12 process_data_begin ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row12";

	

 



/**
 * [tAdvancedHash_row12 process_data_begin ] stop
 */
	
	/**
	 * [tAdvancedHash_row12 process_data_end ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row12";

	

 



/**
 * [tAdvancedHash_row12 process_data_end ] stop
 */



	
	/**
	 * [tDBInput_13 process_data_end ] start
	 */

	

	
	
	currentComponent="tDBInput_13";

	

 



/**
 * [tDBInput_13 process_data_end ] stop
 */
	
	/**
	 * [tDBInput_13 end ] start
	 */

	

	
	
	currentComponent="tDBInput_13";

	

	}
}finally{
	if (rs_tDBInput_13 != null) {
		rs_tDBInput_13.close();
	}
	if (stmt_tDBInput_13 != null) {
		stmt_tDBInput_13.close();
	}
		if(conn_tDBInput_13 != null && !conn_tDBInput_13.isClosed()) {
			
			conn_tDBInput_13.close();
			
			if("com.mysql.cj.jdbc.Driver".equals((String)globalMap.get("driverClass_"))
			    && routines.system.BundleUtils.inOSGi()) {
			        Class.forName("com.mysql.cj.jdbc.AbandonedConnectionCleanupThread").
			            getMethod("checkedShutdown").invoke(null, (Object[]) null);
			}
			
		}
}
globalMap.put("tDBInput_13_NB_LINE",nb_line_tDBInput_13);

 

ok_Hash.put("tDBInput_13", true);
end_Hash.put("tDBInput_13", System.currentTimeMillis());




/**
 * [tDBInput_13 end ] stop
 */

	
	/**
	 * [tAdvancedHash_row12 end ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row12";

	

tHash_Lookup_row12.endPut();

				if(execStat){
			  		runStat.updateStat(resourceMap,iterateId,2,0,"row12");
			  	}
			  	
 

ok_Hash.put("tAdvancedHash_row12", true);
end_Hash.put("tAdvancedHash_row12", System.currentTimeMillis());




/**
 * [tAdvancedHash_row12 end ] stop
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
	 * [tDBInput_13 finally ] start
	 */

	

	
	
	currentComponent="tDBInput_13";

	

 



/**
 * [tDBInput_13 finally ] stop
 */

	
	/**
	 * [tAdvancedHash_row12 finally ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row12";

	

 



/**
 * [tAdvancedHash_row12 finally ] stop
 */



				}catch(java.lang.Exception e){	
					//ignore
				}catch(java.lang.Error error){
					//ignore
				}
				resourceMap = null;
			}
		

		globalMap.put("tDBInput_13_SUBPROCESS_STATE", 1);
	}
	


public static class row13Struct implements routines.system.IPersistableComparableLookupRow<row13Struct> {
    final static byte[] commonByteArrayLock_TEST_Fact2 = new byte[0];
    static byte[] commonByteArray_TEST_Fact2 = new byte[0];
	protected static final int DEFAULT_HASHCODE = 1;
    protected static final int PRIME = 31;
    protected int hashCode = DEFAULT_HASHCODE;
    public boolean hashCodeDirty = true;

    public String loopKey;



	
			    public Integer id_localisation;

				public Integer getId_localisation () {
					return this.id_localisation;
				}
				
			    public String city;

				public String getCity () {
					return this.city;
				}
				
			    public String country;

				public String getCountry () {
					return this.country;
				}
				


	@Override
	public int hashCode() {
		if (this.hashCodeDirty) {
			final int prime = PRIME;
			int result = DEFAULT_HASHCODE;
	
						result = prime * result + ((this.city == null) ? 0 : this.city.hashCode());
					
    		this.hashCode = result;
    		this.hashCodeDirty = false;
		}
		return this.hashCode;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		final row13Struct other = (row13Struct) obj;
		
						if (this.city == null) {
							if (other.city != null)
								return false;
						
						} else if (!this.city.equals(other.city))
						
							return false;
					

		return true;
    }

	public void copyDataTo(row13Struct other) {

		other.id_localisation = this.id_localisation;
	            other.city = this.city;
	            other.country = this.country;
	            
	}

	public void copyKeysDataTo(row13Struct other) {

		other.city = this.city;
	            	
	}



	private Integer readInteger(DataInputStream dis, ObjectInputStream ois) throws IOException{
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
	
	private Integer readInteger(DataInputStream dis, org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException{
		Integer intReturn;
        int length = 0;
        length = unmarshaller.readByte();
		if (length == -1) {
			intReturn = null;
		} else {
			intReturn = unmarshaller.readInt();
		}
		return intReturn;
	}

	private void writeInteger(Integer intNum, DataOutputStream dos, ObjectOutputStream oos) throws IOException{
		if(intNum == null) {
            dos.writeByte(-1);
		} else {
			dos.writeByte(0);
	    	dos.writeInt(intNum);
    	}
	}
	
	private void writeInteger(Integer intNum, DataOutputStream dos,org.jboss.marshalling.Marshaller marshaller ) throws IOException{
		if(intNum == null) {
			marshaller.writeByte(-1);
		} else {
			marshaller.writeByte(0);
			marshaller.writeInt(intNum);
    	}
	}

	private String readString(ObjectInputStream dis) throws IOException{
		String strReturn = null;
		int length = 0;
        length = dis.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			if(length > commonByteArray_TEST_Fact2.length) {
				if(length < 1024 && commonByteArray_TEST_Fact2.length == 0) {
   					commonByteArray_TEST_Fact2 = new byte[1024];
				} else {
   					commonByteArray_TEST_Fact2 = new byte[2 * length];
   				}
			}
			dis.readFully(commonByteArray_TEST_Fact2, 0, length);
			strReturn = new String(commonByteArray_TEST_Fact2, 0, length, utf8Charset);
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
			if(length > commonByteArray_TEST_Fact2.length) {
				if(length < 1024 && commonByteArray_TEST_Fact2.length == 0) {
   					commonByteArray_TEST_Fact2 = new byte[1024];
				} else {
   					commonByteArray_TEST_Fact2 = new byte[2 * length];
   				}
			}
			unmarshaller.readFully(commonByteArray_TEST_Fact2, 0, length);
			strReturn = new String(commonByteArray_TEST_Fact2, 0, length, utf8Charset);
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
	
	private String readString(DataInputStream dis, ObjectInputStream ois) throws IOException{
		String strReturn = null;
		int length = 0;
        length = dis.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			byte[] byteArray = new byte[length];
			dis.read(byteArray);
			strReturn = new String(byteArray, utf8Charset);
		}
		return strReturn;
	}
	
	private String readString(DataInputStream dis, org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException{
		String strReturn = null;
		int length = 0;
        length = unmarshaller.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			byte[] byteArray = new byte[length];
			unmarshaller.read(byteArray);
			strReturn = new String(byteArray, utf8Charset);
		}
		return strReturn;
	}
	
	private void writeString(String str, DataOutputStream dos, org.jboss.marshalling.Marshaller marshaller) throws IOException{
		if(str == null) {
			marshaller.writeInt(-1);
		} else {
            byte[] byteArray = str.getBytes(utf8Charset);
            marshaller.writeInt(byteArray.length);
            marshaller.write(byteArray);
    	}
	}

	private void writeString(String str, DataOutputStream dos, ObjectOutputStream oos) throws IOException{
		if(str == null) {
            dos.writeInt(-1);
		} else {
            byte[] byteArray = str.getBytes(utf8Charset);
	    	dos.writeInt(byteArray.length);
			dos.write(byteArray);
    	}
	}

    public void readKeysData(ObjectInputStream dis) {

		synchronized(commonByteArrayLock_TEST_Fact2) {

        	try {

        		int length = 0;
		
					this.city = readString(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }
    
    public void readKeysData(org.jboss.marshalling.Unmarshaller dis) {

		synchronized(commonByteArrayLock_TEST_Fact2) {

        	try {

        		int length = 0;
		
					this.city = readString(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }

    public void writeKeysData(ObjectOutputStream dos) {
        try {

		
					// String
				
						writeString(this.city,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }
    
    public void writeKeysData(org.jboss.marshalling.Marshaller dos) {
        try {

		
					// String
				
						writeString(this.city,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }



    /**
     * Fill Values data by reading ObjectInputStream.
     */
    public void readValuesData(DataInputStream dis, ObjectInputStream ois) {
        try {

			int length = 0;
		
						this.id_localisation = readInteger(dis,ois);
					
						this.country = readString(dis,ois);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

    }
    
    public void readValuesData(DataInputStream dis, org.jboss.marshalling.Unmarshaller objectIn) {
        try {
			int length = 0;
		
						this.id_localisation = readInteger(dis,objectIn);
					
						this.country = readString(dis,objectIn);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

    }

    /**
     * Return a byte array which represents Values data.
     */
    public void writeValuesData(DataOutputStream dos, ObjectOutputStream oos) {
        try {

		
					writeInteger(this.id_localisation, dos, oos);
					
						writeString(this.country, dos, oos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        	}

    }
    
    public void writeValuesData(DataOutputStream dos, org.jboss.marshalling.Marshaller objectOut){
                try {

		
					writeInteger(this.id_localisation, dos, objectOut);
					
						writeString(this.country, dos, objectOut);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        	}
    }


    
    public boolean supportMarshaller(){
        return true;
    }


    public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("[");
		sb.append("id_localisation="+String.valueOf(id_localisation));
		sb.append(",city="+city);
		sb.append(",country="+country);
	    sb.append("]");

	    return sb.toString();
    }

    /**
     * Compare keys
     */
    public int compareTo(row13Struct other) {

		int returnValue = -1;
		
						returnValue = checkNullsAndCompare(this.city, other.city);
						if(returnValue != 0) {
							return returnValue;
						}

					
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
public void tDBInput_12Process(final java.util.Map<String, Object> globalMap) throws TalendException {
	globalMap.put("tDBInput_12_SUBPROCESS_STATE", 0);

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



		row13Struct row13 = new row13Struct();




	
	/**
	 * [tAdvancedHash_row13 begin ] start
	 */

	

	
		
		ok_Hash.put("tAdvancedHash_row13", false);
		start_Hash.put("tAdvancedHash_row13", System.currentTimeMillis());
		
	
	currentComponent="tAdvancedHash_row13";

	
					if(execStat) {
						runStat.updateStatOnConnection(resourceMap,iterateId,0,0,"row13");
					}
				
		int tos_count_tAdvancedHash_row13 = 0;
		

			   		// connection name:row13
			   		// source node:tDBInput_12 - inputs:(after_tDBInput_1) outputs:(row13,row13) | target node:tAdvancedHash_row13 - inputs:(row13) outputs:()
			   		// linked node: tMap_1 - inputs:(row1,row2,row3,row4,row5,row6,row7,row8,row9,row10,row11,row12,row13,row14,row15,row16,row17,row18,row19,row21) outputs:(out)
			   
			   		org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE matchingModeEnum_row13 = 
			   			org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE.UNIQUE_MATCH;
			   			
			   
	   			org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row13Struct> tHash_Lookup_row13 =org.talend.designer.components.lookup.memory.AdvancedMemoryLookup.
	   						<row13Struct>getLookup(matchingModeEnum_row13);
	   						   
		   	   	   globalMap.put("tHash_Lookup_row13", tHash_Lookup_row13);
		   	   	   
				
           

 



/**
 * [tAdvancedHash_row13 begin ] stop
 */



	
	/**
	 * [tDBInput_12 begin ] start
	 */

	

	
		
		ok_Hash.put("tDBInput_12", false);
		start_Hash.put("tDBInput_12", System.currentTimeMillis());
		
	
	currentComponent="tDBInput_12";

	
		int tos_count_tDBInput_12 = 0;
		
	
    
	
			org.talend.designer.components.util.mssql.MSSqlGenerateTimestampUtil mssqlGTU_tDBInput_12 = org.talend.designer.components.util.mssql.MSSqlUtilFactory.getMSSqlGenerateTimestampUtil();
			
			java.util.List<String> talendToDBList_tDBInput_12 = new java.util.ArrayList();
			String[] talendToDBArray_tDBInput_12  = new String[]{"FLOAT","NUMERIC","NUMERIC IDENTITY","DECIMAL","DECIMAL IDENTITY","REAL"}; 
			java.util.Collections.addAll(talendToDBList_tDBInput_12, talendToDBArray_tDBInput_12); 
		    int nb_line_tDBInput_12 = 0;
		    java.sql.Connection conn_tDBInput_12 = null;
				String driverClass_tDBInput_12 = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
			    java.lang.Class jdbcclazz_tDBInput_12 = java.lang.Class.forName(driverClass_tDBInput_12);
				String dbUser_tDBInput_12 = "bi";
				
				 
	final String decryptedPassword_tDBInput_12 = routines.system.PasswordEncryptUtil.decryptPassword("enc:routine.encryption.key.v1:l2y81gawKQReCzV8IGKRC87nwwcnIpeWlZECsZab");
				
				String dbPwd_tDBInput_12 = decryptedPassword_tDBInput_12;
				
		    String port_tDBInput_12 = "1433";
		    String dbname_tDBInput_12 = "event_DWH" ;		    
		    String url_tDBInput_12 = "jdbc:sqlserver://" + "localhost" ;
		    if (!"".equals(port_tDBInput_12)) {
		    	url_tDBInput_12 += ":" + "1433";
		    }
		    if (!"".equals(dbname_tDBInput_12)) {		    
		    	url_tDBInput_12 += ";databaseName=" + "event_DWH"; 
		    }
		    url_tDBInput_12 += ";appName=" + projectName + ";" + "trustServerCertificate=true";
		    String dbschema_tDBInput_12 = "dbo";
				
				conn_tDBInput_12 = java.sql.DriverManager.getConnection(url_tDBInput_12,dbUser_tDBInput_12,dbPwd_tDBInput_12);
		        
		    
			java.sql.Statement stmt_tDBInput_12 = conn_tDBInput_12.createStatement();

		    String dbquery_tDBInput_12 = "SELECT dbo.Dim_Localisation.id_localisation,\n		dbo.Dim_Localisation.city,\n		dbo.Dim_Localisation.country\nFROM	dbo.Dim_L"
+"ocalisation";
		    

            	globalMap.put("tDBInput_12_QUERY",dbquery_tDBInput_12);
		    java.sql.ResultSet rs_tDBInput_12 = null;

		    try {
		    	rs_tDBInput_12 = stmt_tDBInput_12.executeQuery(dbquery_tDBInput_12);
		    	java.sql.ResultSetMetaData rsmd_tDBInput_12 = rs_tDBInput_12.getMetaData();
		    	int colQtyInRs_tDBInput_12 = rsmd_tDBInput_12.getColumnCount();

		    String tmpContent_tDBInput_12 = null;
		    
		    
		    while (rs_tDBInput_12.next()) {
		        nb_line_tDBInput_12++;
		        
							if(colQtyInRs_tDBInput_12 < 1) {
								row13.id_localisation = null;
							} else {
		                          
            row13.id_localisation = rs_tDBInput_12.getInt(1);
            if(rs_tDBInput_12.wasNull()){
                    row13.id_localisation = null;
            }
		                    }
							if(colQtyInRs_tDBInput_12 < 2) {
								row13.city = null;
							} else {
	                         		
           		tmpContent_tDBInput_12 = rs_tDBInput_12.getString(2);
            if(tmpContent_tDBInput_12 != null) {
            	if (talendToDBList_tDBInput_12 .contains(rsmd_tDBInput_12.getColumnTypeName(2).toUpperCase(java.util.Locale.ENGLISH))) {
            		row13.city = FormatterUtils.formatUnwithE(tmpContent_tDBInput_12);
            	} else {
                	row13.city = tmpContent_tDBInput_12;
                }
            } else {
                row13.city = null;
            }
		                    }
							if(colQtyInRs_tDBInput_12 < 3) {
								row13.country = null;
							} else {
	                         		
           		tmpContent_tDBInput_12 = rs_tDBInput_12.getString(3);
            if(tmpContent_tDBInput_12 != null) {
            	if (talendToDBList_tDBInput_12 .contains(rsmd_tDBInput_12.getColumnTypeName(3).toUpperCase(java.util.Locale.ENGLISH))) {
            		row13.country = FormatterUtils.formatUnwithE(tmpContent_tDBInput_12);
            	} else {
                	row13.country = tmpContent_tDBInput_12;
                }
            } else {
                row13.country = null;
            }
		                    }
					





 



/**
 * [tDBInput_12 begin ] stop
 */
	
	/**
	 * [tDBInput_12 main ] start
	 */

	

	
	
	currentComponent="tDBInput_12";

	

 


	tos_count_tDBInput_12++;

/**
 * [tDBInput_12 main ] stop
 */
	
	/**
	 * [tDBInput_12 process_data_begin ] start
	 */

	

	
	
	currentComponent="tDBInput_12";

	

 



/**
 * [tDBInput_12 process_data_begin ] stop
 */

	
	/**
	 * [tAdvancedHash_row13 main ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row13";

	
					if(execStat){
						runStat.updateStatOnConnection(iterateId,1,1
						
							,"row13"
						
						);
					}
					


			   
			   

					row13Struct row13_HashRow = new row13Struct();
		   	   	   
				
				row13_HashRow.id_localisation = row13.id_localisation;
				
				row13_HashRow.city = row13.city;
				
				row13_HashRow.country = row13.country;
				
			tHash_Lookup_row13.put(row13_HashRow);
			
            




 


	tos_count_tAdvancedHash_row13++;

/**
 * [tAdvancedHash_row13 main ] stop
 */
	
	/**
	 * [tAdvancedHash_row13 process_data_begin ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row13";

	

 



/**
 * [tAdvancedHash_row13 process_data_begin ] stop
 */
	
	/**
	 * [tAdvancedHash_row13 process_data_end ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row13";

	

 



/**
 * [tAdvancedHash_row13 process_data_end ] stop
 */



	
	/**
	 * [tDBInput_12 process_data_end ] start
	 */

	

	
	
	currentComponent="tDBInput_12";

	

 



/**
 * [tDBInput_12 process_data_end ] stop
 */
	
	/**
	 * [tDBInput_12 end ] start
	 */

	

	
	
	currentComponent="tDBInput_12";

	

	}
}finally{
	if (rs_tDBInput_12 != null) {
		rs_tDBInput_12.close();
	}
	if (stmt_tDBInput_12 != null) {
		stmt_tDBInput_12.close();
	}
		if(conn_tDBInput_12 != null && !conn_tDBInput_12.isClosed()) {
			
			conn_tDBInput_12.close();
			
			if("com.mysql.cj.jdbc.Driver".equals((String)globalMap.get("driverClass_"))
			    && routines.system.BundleUtils.inOSGi()) {
			        Class.forName("com.mysql.cj.jdbc.AbandonedConnectionCleanupThread").
			            getMethod("checkedShutdown").invoke(null, (Object[]) null);
			}
			
		}
}
globalMap.put("tDBInput_12_NB_LINE",nb_line_tDBInput_12);

 

ok_Hash.put("tDBInput_12", true);
end_Hash.put("tDBInput_12", System.currentTimeMillis());




/**
 * [tDBInput_12 end ] stop
 */

	
	/**
	 * [tAdvancedHash_row13 end ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row13";

	

tHash_Lookup_row13.endPut();

				if(execStat){
			  		runStat.updateStat(resourceMap,iterateId,2,0,"row13");
			  	}
			  	
 

ok_Hash.put("tAdvancedHash_row13", true);
end_Hash.put("tAdvancedHash_row13", System.currentTimeMillis());




/**
 * [tAdvancedHash_row13 end ] stop
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
	 * [tDBInput_12 finally ] start
	 */

	

	
	
	currentComponent="tDBInput_12";

	

 



/**
 * [tDBInput_12 finally ] stop
 */

	
	/**
	 * [tAdvancedHash_row13 finally ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row13";

	

 



/**
 * [tAdvancedHash_row13 finally ] stop
 */



				}catch(java.lang.Exception e){	
					//ignore
				}catch(java.lang.Error error){
					//ignore
				}
				resourceMap = null;
			}
		

		globalMap.put("tDBInput_12_SUBPROCESS_STATE", 1);
	}
	


public static class row14Struct implements routines.system.IPersistableComparableLookupRow<row14Struct> {
    final static byte[] commonByteArrayLock_TEST_Fact2 = new byte[0];
    static byte[] commonByteArray_TEST_Fact2 = new byte[0];
	protected static final int DEFAULT_HASHCODE = 1;
    protected static final int PRIME = 31;
    protected int hashCode = DEFAULT_HASHCODE;
    public boolean hashCodeDirty = true;

    public String loopKey;



	
			    public Integer id_category;

				public Integer getId_category () {
					return this.id_category;
				}
				
			    public Integer id_subcategory;

				public Integer getId_subcategory () {
					return this.id_subcategory;
				}
				
			    public String ville;

				public String getVille () {
					return this.ville;
				}
				
			    public Integer market_count_googlemaps;

				public Integer getMarket_count_googlemaps () {
					return this.market_count_googlemaps;
				}
				


	@Override
	public int hashCode() {
		if (this.hashCodeDirty) {
			final int prime = PRIME;
			int result = DEFAULT_HASHCODE;
	
						result = prime * result + ((this.id_category == null) ? 0 : this.id_category.hashCode());
					
						result = prime * result + ((this.id_subcategory == null) ? 0 : this.id_subcategory.hashCode());
					
						result = prime * result + ((this.ville == null) ? 0 : this.ville.hashCode());
					
    		this.hashCode = result;
    		this.hashCodeDirty = false;
		}
		return this.hashCode;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		final row14Struct other = (row14Struct) obj;
		
						if (this.id_category == null) {
							if (other.id_category != null)
								return false;
						
						} else if (!this.id_category.equals(other.id_category))
						
							return false;
					
						if (this.id_subcategory == null) {
							if (other.id_subcategory != null)
								return false;
						
						} else if (!this.id_subcategory.equals(other.id_subcategory))
						
							return false;
					
						if (this.ville == null) {
							if (other.ville != null)
								return false;
						
						} else if (!this.ville.equals(other.ville))
						
							return false;
					

		return true;
    }

	public void copyDataTo(row14Struct other) {

		other.id_category = this.id_category;
	            other.id_subcategory = this.id_subcategory;
	            other.ville = this.ville;
	            other.market_count_googlemaps = this.market_count_googlemaps;
	            
	}

	public void copyKeysDataTo(row14Struct other) {

		other.id_category = this.id_category;
	            	other.id_subcategory = this.id_subcategory;
	            	other.ville = this.ville;
	            	
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

	private String readString(ObjectInputStream dis) throws IOException{
		String strReturn = null;
		int length = 0;
        length = dis.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			if(length > commonByteArray_TEST_Fact2.length) {
				if(length < 1024 && commonByteArray_TEST_Fact2.length == 0) {
   					commonByteArray_TEST_Fact2 = new byte[1024];
				} else {
   					commonByteArray_TEST_Fact2 = new byte[2 * length];
   				}
			}
			dis.readFully(commonByteArray_TEST_Fact2, 0, length);
			strReturn = new String(commonByteArray_TEST_Fact2, 0, length, utf8Charset);
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
			if(length > commonByteArray_TEST_Fact2.length) {
				if(length < 1024 && commonByteArray_TEST_Fact2.length == 0) {
   					commonByteArray_TEST_Fact2 = new byte[1024];
				} else {
   					commonByteArray_TEST_Fact2 = new byte[2 * length];
   				}
			}
			unmarshaller.readFully(commonByteArray_TEST_Fact2, 0, length);
			strReturn = new String(commonByteArray_TEST_Fact2, 0, length, utf8Charset);
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
	private Integer readInteger(DataInputStream dis, ObjectInputStream ois) throws IOException{
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
	
	private Integer readInteger(DataInputStream dis, org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException{
		Integer intReturn;
        int length = 0;
        length = unmarshaller.readByte();
		if (length == -1) {
			intReturn = null;
		} else {
			intReturn = unmarshaller.readInt();
		}
		return intReturn;
	}

	private void writeInteger(Integer intNum, DataOutputStream dos, ObjectOutputStream oos) throws IOException{
		if(intNum == null) {
            dos.writeByte(-1);
		} else {
			dos.writeByte(0);
	    	dos.writeInt(intNum);
    	}
	}
	
	private void writeInteger(Integer intNum, DataOutputStream dos,org.jboss.marshalling.Marshaller marshaller ) throws IOException{
		if(intNum == null) {
			marshaller.writeByte(-1);
		} else {
			marshaller.writeByte(0);
			marshaller.writeInt(intNum);
    	}
	}

    public void readKeysData(ObjectInputStream dis) {

		synchronized(commonByteArrayLock_TEST_Fact2) {

        	try {

        		int length = 0;
		
						this.id_category = readInteger(dis);
					
						this.id_subcategory = readInteger(dis);
					
					this.ville = readString(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }
    
    public void readKeysData(org.jboss.marshalling.Unmarshaller dis) {

		synchronized(commonByteArrayLock_TEST_Fact2) {

        	try {

        		int length = 0;
		
						this.id_category = readInteger(dis);
					
						this.id_subcategory = readInteger(dis);
					
					this.ville = readString(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }

    public void writeKeysData(ObjectOutputStream dos) {
        try {

		
					// Integer
				
						writeInteger(this.id_category,dos);
					
					// Integer
				
						writeInteger(this.id_subcategory,dos);
					
					// String
				
						writeString(this.ville,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }
    
    public void writeKeysData(org.jboss.marshalling.Marshaller dos) {
        try {

		
					// Integer
				
						writeInteger(this.id_category,dos);
					
					// Integer
				
						writeInteger(this.id_subcategory,dos);
					
					// String
				
						writeString(this.ville,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }



    /**
     * Fill Values data by reading ObjectInputStream.
     */
    public void readValuesData(DataInputStream dis, ObjectInputStream ois) {
        try {

			int length = 0;
		
						this.market_count_googlemaps = readInteger(dis,ois);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

    }
    
    public void readValuesData(DataInputStream dis, org.jboss.marshalling.Unmarshaller objectIn) {
        try {
			int length = 0;
		
						this.market_count_googlemaps = readInteger(dis,objectIn);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

    }

    /**
     * Return a byte array which represents Values data.
     */
    public void writeValuesData(DataOutputStream dos, ObjectOutputStream oos) {
        try {

		
					writeInteger(this.market_count_googlemaps, dos, oos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        	}

    }
    
    public void writeValuesData(DataOutputStream dos, org.jboss.marshalling.Marshaller objectOut){
                try {

		
					writeInteger(this.market_count_googlemaps, dos, objectOut);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        	}
    }


    
    public boolean supportMarshaller(){
        return true;
    }


    public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("[");
		sb.append("id_category="+String.valueOf(id_category));
		sb.append(",id_subcategory="+String.valueOf(id_subcategory));
		sb.append(",ville="+ville);
		sb.append(",market_count_googlemaps="+String.valueOf(market_count_googlemaps));
	    sb.append("]");

	    return sb.toString();
    }

    /**
     * Compare keys
     */
    public int compareTo(row14Struct other) {

		int returnValue = -1;
		
						returnValue = checkNullsAndCompare(this.id_category, other.id_category);
						if(returnValue != 0) {
							return returnValue;
						}

					
						returnValue = checkNullsAndCompare(this.id_subcategory, other.id_subcategory);
						if(returnValue != 0) {
							return returnValue;
						}

					
						returnValue = checkNullsAndCompare(this.ville, other.ville);
						if(returnValue != 0) {
							return returnValue;
						}

					
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
public void tDBInput_14Process(final java.util.Map<String, Object> globalMap) throws TalendException {
	globalMap.put("tDBInput_14_SUBPROCESS_STATE", 0);

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



		row14Struct row14 = new row14Struct();




	
	/**
	 * [tAdvancedHash_row14 begin ] start
	 */

	

	
		
		ok_Hash.put("tAdvancedHash_row14", false);
		start_Hash.put("tAdvancedHash_row14", System.currentTimeMillis());
		
	
	currentComponent="tAdvancedHash_row14";

	
					if(execStat) {
						runStat.updateStatOnConnection(resourceMap,iterateId,0,0,"row14");
					}
				
		int tos_count_tAdvancedHash_row14 = 0;
		

			   		// connection name:row14
			   		// source node:tDBInput_14 - inputs:(after_tDBInput_1) outputs:(row14,row14) | target node:tAdvancedHash_row14 - inputs:(row14) outputs:()
			   		// linked node: tMap_1 - inputs:(row1,row2,row3,row4,row5,row6,row7,row8,row9,row10,row11,row12,row13,row14,row15,row16,row17,row18,row19,row21) outputs:(out)
			   
			   		org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE matchingModeEnum_row14 = 
			   			org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE.UNIQUE_MATCH;
			   			
			   
	   			org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row14Struct> tHash_Lookup_row14 =org.talend.designer.components.lookup.memory.AdvancedMemoryLookup.
	   						<row14Struct>getLookup(matchingModeEnum_row14);
	   						   
		   	   	   globalMap.put("tHash_Lookup_row14", tHash_Lookup_row14);
		   	   	   
				
           

 



/**
 * [tAdvancedHash_row14 begin ] stop
 */



	
	/**
	 * [tDBInput_14 begin ] start
	 */

	

	
		
		ok_Hash.put("tDBInput_14", false);
		start_Hash.put("tDBInput_14", System.currentTimeMillis());
		
	
	currentComponent="tDBInput_14";

	
		int tos_count_tDBInput_14 = 0;
		
	
    
	
			org.talend.designer.components.util.mssql.MSSqlGenerateTimestampUtil mssqlGTU_tDBInput_14 = org.talend.designer.components.util.mssql.MSSqlUtilFactory.getMSSqlGenerateTimestampUtil();
			
			java.util.List<String> talendToDBList_tDBInput_14 = new java.util.ArrayList();
			String[] talendToDBArray_tDBInput_14  = new String[]{"FLOAT","NUMERIC","NUMERIC IDENTITY","DECIMAL","DECIMAL IDENTITY","REAL"}; 
			java.util.Collections.addAll(talendToDBList_tDBInput_14, talendToDBArray_tDBInput_14); 
		    int nb_line_tDBInput_14 = 0;
		    java.sql.Connection conn_tDBInput_14 = null;
				String driverClass_tDBInput_14 = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
			    java.lang.Class jdbcclazz_tDBInput_14 = java.lang.Class.forName(driverClass_tDBInput_14);
				String dbUser_tDBInput_14 = "event";
				
				 
	final String decryptedPassword_tDBInput_14 = routines.system.PasswordEncryptUtil.decryptPassword("enc:routine.encryption.key.v1:BlJkkTo0uxBCuT+UtyiCTnugW+gW6kzbqmoRZ+bzug==");
				
				String dbPwd_tDBInput_14 = decryptedPassword_tDBInput_14;
				
		    String port_tDBInput_14 = "1433";
		    String dbname_tDBInput_14 = "Event_SA" ;		    
		    String url_tDBInput_14 = "jdbc:sqlserver://" + "localhost" ;
		    if (!"".equals(port_tDBInput_14)) {
		    	url_tDBInput_14 += ":" + "1433";
		    }
		    if (!"".equals(dbname_tDBInput_14)) {		    
		    	url_tDBInput_14 += ";databaseName=" + "Event_SA"; 
		    }
		    url_tDBInput_14 += ";appName=" + projectName + ";" + "trustServerCertificate=true";
		    String dbschema_tDBInput_14 = "dbo";
				
				conn_tDBInput_14 = java.sql.DriverManager.getConnection(url_tDBInput_14,dbUser_tDBInput_14,dbPwd_tDBInput_14);
		        
		    
			java.sql.Statement stmt_tDBInput_14 = conn_tDBInput_14.createStatement();

		    String dbquery_tDBInput_14 = "SELECT dbo.marketCount.id_category,\n		dbo.marketCount.id_subcategory,\n		dbo.marketCount.ville,\n		dbo.marketCount.market"
+"_count_googlemaps\nFROM	dbo.marketCount";
		    

            	globalMap.put("tDBInput_14_QUERY",dbquery_tDBInput_14);
		    java.sql.ResultSet rs_tDBInput_14 = null;

		    try {
		    	rs_tDBInput_14 = stmt_tDBInput_14.executeQuery(dbquery_tDBInput_14);
		    	java.sql.ResultSetMetaData rsmd_tDBInput_14 = rs_tDBInput_14.getMetaData();
		    	int colQtyInRs_tDBInput_14 = rsmd_tDBInput_14.getColumnCount();

		    String tmpContent_tDBInput_14 = null;
		    
		    
		    while (rs_tDBInput_14.next()) {
		        nb_line_tDBInput_14++;
		        
							if(colQtyInRs_tDBInput_14 < 1) {
								row14.id_category = null;
							} else {
		                          
            row14.id_category = rs_tDBInput_14.getInt(1);
            if(rs_tDBInput_14.wasNull()){
                    row14.id_category = null;
            }
		                    }
							if(colQtyInRs_tDBInput_14 < 2) {
								row14.id_subcategory = null;
							} else {
		                          
            row14.id_subcategory = rs_tDBInput_14.getInt(2);
            if(rs_tDBInput_14.wasNull()){
                    row14.id_subcategory = null;
            }
		                    }
							if(colQtyInRs_tDBInput_14 < 3) {
								row14.ville = null;
							} else {
	                         		
           		tmpContent_tDBInput_14 = rs_tDBInput_14.getString(3);
            if(tmpContent_tDBInput_14 != null) {
            	if (talendToDBList_tDBInput_14 .contains(rsmd_tDBInput_14.getColumnTypeName(3).toUpperCase(java.util.Locale.ENGLISH))) {
            		row14.ville = FormatterUtils.formatUnwithE(tmpContent_tDBInput_14);
            	} else {
                	row14.ville = tmpContent_tDBInput_14;
                }
            } else {
                row14.ville = null;
            }
		                    }
							if(colQtyInRs_tDBInput_14 < 4) {
								row14.market_count_googlemaps = null;
							} else {
		                          
            row14.market_count_googlemaps = rs_tDBInput_14.getInt(4);
            if(rs_tDBInput_14.wasNull()){
                    row14.market_count_googlemaps = null;
            }
		                    }
					





 



/**
 * [tDBInput_14 begin ] stop
 */
	
	/**
	 * [tDBInput_14 main ] start
	 */

	

	
	
	currentComponent="tDBInput_14";

	

 


	tos_count_tDBInput_14++;

/**
 * [tDBInput_14 main ] stop
 */
	
	/**
	 * [tDBInput_14 process_data_begin ] start
	 */

	

	
	
	currentComponent="tDBInput_14";

	

 



/**
 * [tDBInput_14 process_data_begin ] stop
 */

	
	/**
	 * [tAdvancedHash_row14 main ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row14";

	
					if(execStat){
						runStat.updateStatOnConnection(iterateId,1,1
						
							,"row14"
						
						);
					}
					


			   
			   

					row14Struct row14_HashRow = new row14Struct();
		   	   	   
				
				row14_HashRow.id_category = row14.id_category;
				
				row14_HashRow.id_subcategory = row14.id_subcategory;
				
				row14_HashRow.ville = row14.ville;
				
				row14_HashRow.market_count_googlemaps = row14.market_count_googlemaps;
				
			tHash_Lookup_row14.put(row14_HashRow);
			
            




 


	tos_count_tAdvancedHash_row14++;

/**
 * [tAdvancedHash_row14 main ] stop
 */
	
	/**
	 * [tAdvancedHash_row14 process_data_begin ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row14";

	

 



/**
 * [tAdvancedHash_row14 process_data_begin ] stop
 */
	
	/**
	 * [tAdvancedHash_row14 process_data_end ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row14";

	

 



/**
 * [tAdvancedHash_row14 process_data_end ] stop
 */



	
	/**
	 * [tDBInput_14 process_data_end ] start
	 */

	

	
	
	currentComponent="tDBInput_14";

	

 



/**
 * [tDBInput_14 process_data_end ] stop
 */
	
	/**
	 * [tDBInput_14 end ] start
	 */

	

	
	
	currentComponent="tDBInput_14";

	

	}
}finally{
	if (rs_tDBInput_14 != null) {
		rs_tDBInput_14.close();
	}
	if (stmt_tDBInput_14 != null) {
		stmt_tDBInput_14.close();
	}
		if(conn_tDBInput_14 != null && !conn_tDBInput_14.isClosed()) {
			
			conn_tDBInput_14.close();
			
			if("com.mysql.cj.jdbc.Driver".equals((String)globalMap.get("driverClass_"))
			    && routines.system.BundleUtils.inOSGi()) {
			        Class.forName("com.mysql.cj.jdbc.AbandonedConnectionCleanupThread").
			            getMethod("checkedShutdown").invoke(null, (Object[]) null);
			}
			
		}
}
globalMap.put("tDBInput_14_NB_LINE",nb_line_tDBInput_14);

 

ok_Hash.put("tDBInput_14", true);
end_Hash.put("tDBInput_14", System.currentTimeMillis());




/**
 * [tDBInput_14 end ] stop
 */

	
	/**
	 * [tAdvancedHash_row14 end ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row14";

	

tHash_Lookup_row14.endPut();

				if(execStat){
			  		runStat.updateStat(resourceMap,iterateId,2,0,"row14");
			  	}
			  	
 

ok_Hash.put("tAdvancedHash_row14", true);
end_Hash.put("tAdvancedHash_row14", System.currentTimeMillis());




/**
 * [tAdvancedHash_row14 end ] stop
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
	 * [tDBInput_14 finally ] start
	 */

	

	
	
	currentComponent="tDBInput_14";

	

 



/**
 * [tDBInput_14 finally ] stop
 */

	
	/**
	 * [tAdvancedHash_row14 finally ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row14";

	

 



/**
 * [tAdvancedHash_row14 finally ] stop
 */



				}catch(java.lang.Exception e){	
					//ignore
				}catch(java.lang.Error error){
					//ignore
				}
				resourceMap = null;
			}
		

		globalMap.put("tDBInput_14_SUBPROCESS_STATE", 1);
	}
	


public static class row15Struct implements routines.system.IPersistableComparableLookupRow<row15Struct> {
    final static byte[] commonByteArrayLock_TEST_Fact2 = new byte[0];
    static byte[] commonByteArray_TEST_Fact2 = new byte[0];
	protected static final int DEFAULT_HASHCODE = 1;
    protected static final int PRIME = 31;
    protected int hashCode = DEFAULT_HASHCODE;
    public boolean hashCodeDirty = true;

    public String loopKey;



	
			    public Integer id_event;

				public Integer getId_event () {
					return this.id_event;
				}
				
			    public Integer id_beneficiary;

				public Integer getId_beneficiary () {
					return this.id_beneficiary;
				}
				


	@Override
	public int hashCode() {
		if (this.hashCodeDirty) {
			final int prime = PRIME;
			int result = DEFAULT_HASHCODE;
	
						result = prime * result + ((this.id_event == null) ? 0 : this.id_event.hashCode());
					
    		this.hashCode = result;
    		this.hashCodeDirty = false;
		}
		return this.hashCode;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		final row15Struct other = (row15Struct) obj;
		
						if (this.id_event == null) {
							if (other.id_event != null)
								return false;
						
						} else if (!this.id_event.equals(other.id_event))
						
							return false;
					

		return true;
    }

	public void copyDataTo(row15Struct other) {

		other.id_event = this.id_event;
	            other.id_beneficiary = this.id_beneficiary;
	            
	}

	public void copyKeysDataTo(row15Struct other) {

		other.id_event = this.id_event;
	            	
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
	private Integer readInteger(DataInputStream dis, ObjectInputStream ois) throws IOException{
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
	
	private Integer readInteger(DataInputStream dis, org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException{
		Integer intReturn;
        int length = 0;
        length = unmarshaller.readByte();
		if (length == -1) {
			intReturn = null;
		} else {
			intReturn = unmarshaller.readInt();
		}
		return intReturn;
	}

	private void writeInteger(Integer intNum, DataOutputStream dos, ObjectOutputStream oos) throws IOException{
		if(intNum == null) {
            dos.writeByte(-1);
		} else {
			dos.writeByte(0);
	    	dos.writeInt(intNum);
    	}
	}
	
	private void writeInteger(Integer intNum, DataOutputStream dos,org.jboss.marshalling.Marshaller marshaller ) throws IOException{
		if(intNum == null) {
			marshaller.writeByte(-1);
		} else {
			marshaller.writeByte(0);
			marshaller.writeInt(intNum);
    	}
	}

    public void readKeysData(ObjectInputStream dis) {

		synchronized(commonByteArrayLock_TEST_Fact2) {

        	try {

        		int length = 0;
		
						this.id_event = readInteger(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }
    
    public void readKeysData(org.jboss.marshalling.Unmarshaller dis) {

		synchronized(commonByteArrayLock_TEST_Fact2) {

        	try {

        		int length = 0;
		
						this.id_event = readInteger(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }

    public void writeKeysData(ObjectOutputStream dos) {
        try {

		
					// Integer
				
						writeInteger(this.id_event,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }
    
    public void writeKeysData(org.jboss.marshalling.Marshaller dos) {
        try {

		
					// Integer
				
						writeInteger(this.id_event,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }



    /**
     * Fill Values data by reading ObjectInputStream.
     */
    public void readValuesData(DataInputStream dis, ObjectInputStream ois) {
        try {

			int length = 0;
		
						this.id_beneficiary = readInteger(dis,ois);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

    }
    
    public void readValuesData(DataInputStream dis, org.jboss.marshalling.Unmarshaller objectIn) {
        try {
			int length = 0;
		
						this.id_beneficiary = readInteger(dis,objectIn);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

    }

    /**
     * Return a byte array which represents Values data.
     */
    public void writeValuesData(DataOutputStream dos, ObjectOutputStream oos) {
        try {

		
					writeInteger(this.id_beneficiary, dos, oos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        	}

    }
    
    public void writeValuesData(DataOutputStream dos, org.jboss.marshalling.Marshaller objectOut){
                try {

		
					writeInteger(this.id_beneficiary, dos, objectOut);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        	}
    }


    
    public boolean supportMarshaller(){
        return true;
    }


    public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("[");
		sb.append("id_event="+String.valueOf(id_event));
		sb.append(",id_beneficiary="+String.valueOf(id_beneficiary));
	    sb.append("]");

	    return sb.toString();
    }

    /**
     * Compare keys
     */
    public int compareTo(row15Struct other) {

		int returnValue = -1;
		
						returnValue = checkNullsAndCompare(this.id_event, other.id_event);
						if(returnValue != 0) {
							return returnValue;
						}

					
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
public void tDBInput_15Process(final java.util.Map<String, Object> globalMap) throws TalendException {
	globalMap.put("tDBInput_15_SUBPROCESS_STATE", 0);

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



		row15Struct row15 = new row15Struct();




	
	/**
	 * [tAdvancedHash_row15 begin ] start
	 */

	

	
		
		ok_Hash.put("tAdvancedHash_row15", false);
		start_Hash.put("tAdvancedHash_row15", System.currentTimeMillis());
		
	
	currentComponent="tAdvancedHash_row15";

	
					if(execStat) {
						runStat.updateStatOnConnection(resourceMap,iterateId,0,0,"row15");
					}
				
		int tos_count_tAdvancedHash_row15 = 0;
		

			   		// connection name:row15
			   		// source node:tDBInput_15 - inputs:(after_tDBInput_1) outputs:(row15,row15) | target node:tAdvancedHash_row15 - inputs:(row15) outputs:()
			   		// linked node: tMap_1 - inputs:(row1,row2,row3,row4,row5,row6,row7,row8,row9,row10,row11,row12,row13,row14,row15,row16,row17,row18,row19,row21) outputs:(out)
			   
			   		org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE matchingModeEnum_row15 = 
			   			org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE.UNIQUE_MATCH;
			   			
			   
	   			org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row15Struct> tHash_Lookup_row15 =org.talend.designer.components.lookup.memory.AdvancedMemoryLookup.
	   						<row15Struct>getLookup(matchingModeEnum_row15);
	   						   
		   	   	   globalMap.put("tHash_Lookup_row15", tHash_Lookup_row15);
		   	   	   
				
           

 



/**
 * [tAdvancedHash_row15 begin ] stop
 */



	
	/**
	 * [tDBInput_15 begin ] start
	 */

	

	
		
		ok_Hash.put("tDBInput_15", false);
		start_Hash.put("tDBInput_15", System.currentTimeMillis());
		
	
	currentComponent="tDBInput_15";

	
		int tos_count_tDBInput_15 = 0;
		
	
    
	
			org.talend.designer.components.util.mssql.MSSqlGenerateTimestampUtil mssqlGTU_tDBInput_15 = org.talend.designer.components.util.mssql.MSSqlUtilFactory.getMSSqlGenerateTimestampUtil();
			
			java.util.List<String> talendToDBList_tDBInput_15 = new java.util.ArrayList();
			String[] talendToDBArray_tDBInput_15  = new String[]{"FLOAT","NUMERIC","NUMERIC IDENTITY","DECIMAL","DECIMAL IDENTITY","REAL"}; 
			java.util.Collections.addAll(talendToDBList_tDBInput_15, talendToDBArray_tDBInput_15); 
		    int nb_line_tDBInput_15 = 0;
		    java.sql.Connection conn_tDBInput_15 = null;
				String driverClass_tDBInput_15 = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
			    java.lang.Class jdbcclazz_tDBInput_15 = java.lang.Class.forName(driverClass_tDBInput_15);
				String dbUser_tDBInput_15 = "event";
				
				 
	final String decryptedPassword_tDBInput_15 = routines.system.PasswordEncryptUtil.decryptPassword("enc:routine.encryption.key.v1:QRgpmm/ZvjYfC4TZXV9VyH3F45Z0sZk6qkCp6vbZgg==");
				
				String dbPwd_tDBInput_15 = decryptedPassword_tDBInput_15;
				
		    String port_tDBInput_15 = "1433";
		    String dbname_tDBInput_15 = "Event_SA" ;		    
		    String url_tDBInput_15 = "jdbc:sqlserver://" + "localhost" ;
		    if (!"".equals(port_tDBInput_15)) {
		    	url_tDBInput_15 += ":" + "1433";
		    }
		    if (!"".equals(dbname_tDBInput_15)) {		    
		    	url_tDBInput_15 += ";databaseName=" + "Event_SA"; 
		    }
		    url_tDBInput_15 += ";appName=" + projectName + ";" + "trustServerCertificate=true";
		    String dbschema_tDBInput_15 = "dbo";
				
				conn_tDBInput_15 = java.sql.DriverManager.getConnection(url_tDBInput_15,dbUser_tDBInput_15,dbPwd_tDBInput_15);
		        
		    
			java.sql.Statement stmt_tDBInput_15 = conn_tDBInput_15.createStatement();

		    String dbquery_tDBInput_15 = "SELECT dbo.Event.id_event,\n		dbo.Event.id_beneficiary\nFROM	dbo.Event";
		    

            	globalMap.put("tDBInput_15_QUERY",dbquery_tDBInput_15);
		    java.sql.ResultSet rs_tDBInput_15 = null;

		    try {
		    	rs_tDBInput_15 = stmt_tDBInput_15.executeQuery(dbquery_tDBInput_15);
		    	java.sql.ResultSetMetaData rsmd_tDBInput_15 = rs_tDBInput_15.getMetaData();
		    	int colQtyInRs_tDBInput_15 = rsmd_tDBInput_15.getColumnCount();

		    String tmpContent_tDBInput_15 = null;
		    
		    
		    while (rs_tDBInput_15.next()) {
		        nb_line_tDBInput_15++;
		        
							if(colQtyInRs_tDBInput_15 < 1) {
								row15.id_event = null;
							} else {
		                          
            row15.id_event = rs_tDBInput_15.getInt(1);
            if(rs_tDBInput_15.wasNull()){
                    row15.id_event = null;
            }
		                    }
							if(colQtyInRs_tDBInput_15 < 2) {
								row15.id_beneficiary = null;
							} else {
		                          
            row15.id_beneficiary = rs_tDBInput_15.getInt(2);
            if(rs_tDBInput_15.wasNull()){
                    row15.id_beneficiary = null;
            }
		                    }
					





 



/**
 * [tDBInput_15 begin ] stop
 */
	
	/**
	 * [tDBInput_15 main ] start
	 */

	

	
	
	currentComponent="tDBInput_15";

	

 


	tos_count_tDBInput_15++;

/**
 * [tDBInput_15 main ] stop
 */
	
	/**
	 * [tDBInput_15 process_data_begin ] start
	 */

	

	
	
	currentComponent="tDBInput_15";

	

 



/**
 * [tDBInput_15 process_data_begin ] stop
 */

	
	/**
	 * [tAdvancedHash_row15 main ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row15";

	
					if(execStat){
						runStat.updateStatOnConnection(iterateId,1,1
						
							,"row15"
						
						);
					}
					


			   
			   

					row15Struct row15_HashRow = new row15Struct();
		   	   	   
				
				row15_HashRow.id_event = row15.id_event;
				
				row15_HashRow.id_beneficiary = row15.id_beneficiary;
				
			tHash_Lookup_row15.put(row15_HashRow);
			
            




 


	tos_count_tAdvancedHash_row15++;

/**
 * [tAdvancedHash_row15 main ] stop
 */
	
	/**
	 * [tAdvancedHash_row15 process_data_begin ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row15";

	

 



/**
 * [tAdvancedHash_row15 process_data_begin ] stop
 */
	
	/**
	 * [tAdvancedHash_row15 process_data_end ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row15";

	

 



/**
 * [tAdvancedHash_row15 process_data_end ] stop
 */



	
	/**
	 * [tDBInput_15 process_data_end ] start
	 */

	

	
	
	currentComponent="tDBInput_15";

	

 



/**
 * [tDBInput_15 process_data_end ] stop
 */
	
	/**
	 * [tDBInput_15 end ] start
	 */

	

	
	
	currentComponent="tDBInput_15";

	

	}
}finally{
	if (rs_tDBInput_15 != null) {
		rs_tDBInput_15.close();
	}
	if (stmt_tDBInput_15 != null) {
		stmt_tDBInput_15.close();
	}
		if(conn_tDBInput_15 != null && !conn_tDBInput_15.isClosed()) {
			
			conn_tDBInput_15.close();
			
			if("com.mysql.cj.jdbc.Driver".equals((String)globalMap.get("driverClass_"))
			    && routines.system.BundleUtils.inOSGi()) {
			        Class.forName("com.mysql.cj.jdbc.AbandonedConnectionCleanupThread").
			            getMethod("checkedShutdown").invoke(null, (Object[]) null);
			}
			
		}
}
globalMap.put("tDBInput_15_NB_LINE",nb_line_tDBInput_15);

 

ok_Hash.put("tDBInput_15", true);
end_Hash.put("tDBInput_15", System.currentTimeMillis());




/**
 * [tDBInput_15 end ] stop
 */

	
	/**
	 * [tAdvancedHash_row15 end ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row15";

	

tHash_Lookup_row15.endPut();

				if(execStat){
			  		runStat.updateStat(resourceMap,iterateId,2,0,"row15");
			  	}
			  	
 

ok_Hash.put("tAdvancedHash_row15", true);
end_Hash.put("tAdvancedHash_row15", System.currentTimeMillis());




/**
 * [tAdvancedHash_row15 end ] stop
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
	 * [tDBInput_15 finally ] start
	 */

	

	
	
	currentComponent="tDBInput_15";

	

 



/**
 * [tDBInput_15 finally ] stop
 */

	
	/**
	 * [tAdvancedHash_row15 finally ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row15";

	

 



/**
 * [tAdvancedHash_row15 finally ] stop
 */



				}catch(java.lang.Exception e){	
					//ignore
				}catch(java.lang.Error error){
					//ignore
				}
				resourceMap = null;
			}
		

		globalMap.put("tDBInput_15_SUBPROCESS_STATE", 1);
	}
	


public static class row16Struct implements routines.system.IPersistableComparableLookupRow<row16Struct> {
    final static byte[] commonByteArrayLock_TEST_Fact2 = new byte[0];
    static byte[] commonByteArray_TEST_Fact2 = new byte[0];
	protected static final int DEFAULT_HASHCODE = 1;
    protected static final int PRIME = 31;
    protected int hashCode = DEFAULT_HASHCODE;
    public boolean hashCodeDirty = true;

    public String loopKey;



	
			    public Integer id_evaluation;

				public Integer getId_evaluation () {
					return this.id_evaluation;
				}
				
			    public Integer id_reservation;

				public Integer getId_reservation () {
					return this.id_reservation;
				}
				


	@Override
	public int hashCode() {
		if (this.hashCodeDirty) {
			final int prime = PRIME;
			int result = DEFAULT_HASHCODE;
	
						result = prime * result + ((this.id_reservation == null) ? 0 : this.id_reservation.hashCode());
					
    		this.hashCode = result;
    		this.hashCodeDirty = false;
		}
		return this.hashCode;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		final row16Struct other = (row16Struct) obj;
		
						if (this.id_reservation == null) {
							if (other.id_reservation != null)
								return false;
						
						} else if (!this.id_reservation.equals(other.id_reservation))
						
							return false;
					

		return true;
    }

	public void copyDataTo(row16Struct other) {

		other.id_evaluation = this.id_evaluation;
	            other.id_reservation = this.id_reservation;
	            
	}

	public void copyKeysDataTo(row16Struct other) {

		other.id_reservation = this.id_reservation;
	            	
	}



	private Integer readInteger(DataInputStream dis, ObjectInputStream ois) throws IOException{
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
	
	private Integer readInteger(DataInputStream dis, org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException{
		Integer intReturn;
        int length = 0;
        length = unmarshaller.readByte();
		if (length == -1) {
			intReturn = null;
		} else {
			intReturn = unmarshaller.readInt();
		}
		return intReturn;
	}

	private void writeInteger(Integer intNum, DataOutputStream dos, ObjectOutputStream oos) throws IOException{
		if(intNum == null) {
            dos.writeByte(-1);
		} else {
			dos.writeByte(0);
	    	dos.writeInt(intNum);
    	}
	}
	
	private void writeInteger(Integer intNum, DataOutputStream dos,org.jboss.marshalling.Marshaller marshaller ) throws IOException{
		if(intNum == null) {
			marshaller.writeByte(-1);
		} else {
			marshaller.writeByte(0);
			marshaller.writeInt(intNum);
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

    public void readKeysData(ObjectInputStream dis) {

		synchronized(commonByteArrayLock_TEST_Fact2) {

        	try {

        		int length = 0;
		
						this.id_reservation = readInteger(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }
    
    public void readKeysData(org.jboss.marshalling.Unmarshaller dis) {

		synchronized(commonByteArrayLock_TEST_Fact2) {

        	try {

        		int length = 0;
		
						this.id_reservation = readInteger(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }

    public void writeKeysData(ObjectOutputStream dos) {
        try {

		
					// Integer
				
						writeInteger(this.id_reservation,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }
    
    public void writeKeysData(org.jboss.marshalling.Marshaller dos) {
        try {

		
					// Integer
				
						writeInteger(this.id_reservation,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }



    /**
     * Fill Values data by reading ObjectInputStream.
     */
    public void readValuesData(DataInputStream dis, ObjectInputStream ois) {
        try {

			int length = 0;
		
						this.id_evaluation = readInteger(dis,ois);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

    }
    
    public void readValuesData(DataInputStream dis, org.jboss.marshalling.Unmarshaller objectIn) {
        try {
			int length = 0;
		
						this.id_evaluation = readInteger(dis,objectIn);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

    }

    /**
     * Return a byte array which represents Values data.
     */
    public void writeValuesData(DataOutputStream dos, ObjectOutputStream oos) {
        try {

		
					writeInteger(this.id_evaluation, dos, oos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        	}

    }
    
    public void writeValuesData(DataOutputStream dos, org.jboss.marshalling.Marshaller objectOut){
                try {

		
					writeInteger(this.id_evaluation, dos, objectOut);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        	}
    }


    
    public boolean supportMarshaller(){
        return true;
    }


    public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("[");
		sb.append("id_evaluation="+String.valueOf(id_evaluation));
		sb.append(",id_reservation="+String.valueOf(id_reservation));
	    sb.append("]");

	    return sb.toString();
    }

    /**
     * Compare keys
     */
    public int compareTo(row16Struct other) {

		int returnValue = -1;
		
						returnValue = checkNullsAndCompare(this.id_reservation, other.id_reservation);
						if(returnValue != 0) {
							return returnValue;
						}

					
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
public void tDBInput_16Process(final java.util.Map<String, Object> globalMap) throws TalendException {
	globalMap.put("tDBInput_16_SUBPROCESS_STATE", 0);

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



		row16Struct row16 = new row16Struct();




	
	/**
	 * [tAdvancedHash_row16 begin ] start
	 */

	

	
		
		ok_Hash.put("tAdvancedHash_row16", false);
		start_Hash.put("tAdvancedHash_row16", System.currentTimeMillis());
		
	
	currentComponent="tAdvancedHash_row16";

	
					if(execStat) {
						runStat.updateStatOnConnection(resourceMap,iterateId,0,0,"row16");
					}
				
		int tos_count_tAdvancedHash_row16 = 0;
		

			   		// connection name:row16
			   		// source node:tDBInput_16 - inputs:(after_tDBInput_1) outputs:(row16,row16) | target node:tAdvancedHash_row16 - inputs:(row16) outputs:()
			   		// linked node: tMap_1 - inputs:(row1,row2,row3,row4,row5,row6,row7,row8,row9,row10,row11,row12,row13,row14,row15,row16,row17,row18,row19,row21) outputs:(out)
			   
			   		org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE matchingModeEnum_row16 = 
			   			org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE.UNIQUE_MATCH;
			   			
			   
	   			org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row16Struct> tHash_Lookup_row16 =org.talend.designer.components.lookup.memory.AdvancedMemoryLookup.
	   						<row16Struct>getLookup(matchingModeEnum_row16);
	   						   
		   	   	   globalMap.put("tHash_Lookup_row16", tHash_Lookup_row16);
		   	   	   
				
           

 



/**
 * [tAdvancedHash_row16 begin ] stop
 */



	
	/**
	 * [tDBInput_16 begin ] start
	 */

	

	
		
		ok_Hash.put("tDBInput_16", false);
		start_Hash.put("tDBInput_16", System.currentTimeMillis());
		
	
	currentComponent="tDBInput_16";

	
		int tos_count_tDBInput_16 = 0;
		
	
    
	
			org.talend.designer.components.util.mssql.MSSqlGenerateTimestampUtil mssqlGTU_tDBInput_16 = org.talend.designer.components.util.mssql.MSSqlUtilFactory.getMSSqlGenerateTimestampUtil();
			
			java.util.List<String> talendToDBList_tDBInput_16 = new java.util.ArrayList();
			String[] talendToDBArray_tDBInput_16  = new String[]{"FLOAT","NUMERIC","NUMERIC IDENTITY","DECIMAL","DECIMAL IDENTITY","REAL"}; 
			java.util.Collections.addAll(talendToDBList_tDBInput_16, talendToDBArray_tDBInput_16); 
		    int nb_line_tDBInput_16 = 0;
		    java.sql.Connection conn_tDBInput_16 = null;
				String driverClass_tDBInput_16 = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
			    java.lang.Class jdbcclazz_tDBInput_16 = java.lang.Class.forName(driverClass_tDBInput_16);
				String dbUser_tDBInput_16 = "event";
				
				 
	final String decryptedPassword_tDBInput_16 = routines.system.PasswordEncryptUtil.decryptPassword("enc:routine.encryption.key.v1:xSl2lBjs/JDjIW35pPuFo175j07va9Ly0n6o44vgbQ==");
				
				String dbPwd_tDBInput_16 = decryptedPassword_tDBInput_16;
				
		    String port_tDBInput_16 = "1433";
		    String dbname_tDBInput_16 = "Event_SA" ;		    
		    String url_tDBInput_16 = "jdbc:sqlserver://" + "localhost" ;
		    if (!"".equals(port_tDBInput_16)) {
		    	url_tDBInput_16 += ":" + "1433";
		    }
		    if (!"".equals(dbname_tDBInput_16)) {		    
		    	url_tDBInput_16 += ";databaseName=" + "Event_SA"; 
		    }
		    url_tDBInput_16 += ";appName=" + projectName + ";" + "trustServerCertificate=true";
		    String dbschema_tDBInput_16 = "dbo";
				
				conn_tDBInput_16 = java.sql.DriverManager.getConnection(url_tDBInput_16,dbUser_tDBInput_16,dbPwd_tDBInput_16);
		        
		    
			java.sql.Statement stmt_tDBInput_16 = conn_tDBInput_16.createStatement();

		    String dbquery_tDBInput_16 = "SELECT dbo.Evaluation.id_evaluation,\n		dbo.Evaluation.id_reservation\nFROM	dbo.Evaluation";
		    

            	globalMap.put("tDBInput_16_QUERY",dbquery_tDBInput_16);
		    java.sql.ResultSet rs_tDBInput_16 = null;

		    try {
		    	rs_tDBInput_16 = stmt_tDBInput_16.executeQuery(dbquery_tDBInput_16);
		    	java.sql.ResultSetMetaData rsmd_tDBInput_16 = rs_tDBInput_16.getMetaData();
		    	int colQtyInRs_tDBInput_16 = rsmd_tDBInput_16.getColumnCount();

		    String tmpContent_tDBInput_16 = null;
		    
		    
		    while (rs_tDBInput_16.next()) {
		        nb_line_tDBInput_16++;
		        
							if(colQtyInRs_tDBInput_16 < 1) {
								row16.id_evaluation = null;
							} else {
		                          
            row16.id_evaluation = rs_tDBInput_16.getInt(1);
            if(rs_tDBInput_16.wasNull()){
                    row16.id_evaluation = null;
            }
		                    }
							if(colQtyInRs_tDBInput_16 < 2) {
								row16.id_reservation = null;
							} else {
		                          
            row16.id_reservation = rs_tDBInput_16.getInt(2);
            if(rs_tDBInput_16.wasNull()){
                    row16.id_reservation = null;
            }
		                    }
					





 



/**
 * [tDBInput_16 begin ] stop
 */
	
	/**
	 * [tDBInput_16 main ] start
	 */

	

	
	
	currentComponent="tDBInput_16";

	

 


	tos_count_tDBInput_16++;

/**
 * [tDBInput_16 main ] stop
 */
	
	/**
	 * [tDBInput_16 process_data_begin ] start
	 */

	

	
	
	currentComponent="tDBInput_16";

	

 



/**
 * [tDBInput_16 process_data_begin ] stop
 */

	
	/**
	 * [tAdvancedHash_row16 main ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row16";

	
					if(execStat){
						runStat.updateStatOnConnection(iterateId,1,1
						
							,"row16"
						
						);
					}
					


			   
			   

					row16Struct row16_HashRow = new row16Struct();
		   	   	   
				
				row16_HashRow.id_evaluation = row16.id_evaluation;
				
				row16_HashRow.id_reservation = row16.id_reservation;
				
			tHash_Lookup_row16.put(row16_HashRow);
			
            




 


	tos_count_tAdvancedHash_row16++;

/**
 * [tAdvancedHash_row16 main ] stop
 */
	
	/**
	 * [tAdvancedHash_row16 process_data_begin ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row16";

	

 



/**
 * [tAdvancedHash_row16 process_data_begin ] stop
 */
	
	/**
	 * [tAdvancedHash_row16 process_data_end ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row16";

	

 



/**
 * [tAdvancedHash_row16 process_data_end ] stop
 */



	
	/**
	 * [tDBInput_16 process_data_end ] start
	 */

	

	
	
	currentComponent="tDBInput_16";

	

 



/**
 * [tDBInput_16 process_data_end ] stop
 */
	
	/**
	 * [tDBInput_16 end ] start
	 */

	

	
	
	currentComponent="tDBInput_16";

	

	}
}finally{
	if (rs_tDBInput_16 != null) {
		rs_tDBInput_16.close();
	}
	if (stmt_tDBInput_16 != null) {
		stmt_tDBInput_16.close();
	}
		if(conn_tDBInput_16 != null && !conn_tDBInput_16.isClosed()) {
			
			conn_tDBInput_16.close();
			
			if("com.mysql.cj.jdbc.Driver".equals((String)globalMap.get("driverClass_"))
			    && routines.system.BundleUtils.inOSGi()) {
			        Class.forName("com.mysql.cj.jdbc.AbandonedConnectionCleanupThread").
			            getMethod("checkedShutdown").invoke(null, (Object[]) null);
			}
			
		}
}
globalMap.put("tDBInput_16_NB_LINE",nb_line_tDBInput_16);

 

ok_Hash.put("tDBInput_16", true);
end_Hash.put("tDBInput_16", System.currentTimeMillis());




/**
 * [tDBInput_16 end ] stop
 */

	
	/**
	 * [tAdvancedHash_row16 end ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row16";

	

tHash_Lookup_row16.endPut();

				if(execStat){
			  		runStat.updateStat(resourceMap,iterateId,2,0,"row16");
			  	}
			  	
 

ok_Hash.put("tAdvancedHash_row16", true);
end_Hash.put("tAdvancedHash_row16", System.currentTimeMillis());




/**
 * [tAdvancedHash_row16 end ] stop
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
	 * [tDBInput_16 finally ] start
	 */

	

	
	
	currentComponent="tDBInput_16";

	

 



/**
 * [tDBInput_16 finally ] stop
 */

	
	/**
	 * [tAdvancedHash_row16 finally ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row16";

	

 



/**
 * [tAdvancedHash_row16 finally ] stop
 */



				}catch(java.lang.Exception e){	
					//ignore
				}catch(java.lang.Error error){
					//ignore
				}
				resourceMap = null;
			}
		

		globalMap.put("tDBInput_16_SUBPROCESS_STATE", 1);
	}
	


public static class row17Struct implements routines.system.IPersistableComparableLookupRow<row17Struct> {
    final static byte[] commonByteArrayLock_TEST_Fact2 = new byte[0];
    static byte[] commonByteArray_TEST_Fact2 = new byte[0];
	protected static final int DEFAULT_HASHCODE = 1;
    protected static final int PRIME = 31;
    protected int hashCode = DEFAULT_HASHCODE;
    public boolean hashCodeDirty = true;

    public String loopKey;



	
			    public Integer id_complaint;

				public Integer getId_complaint () {
					return this.id_complaint;
				}
				
			    public Integer id_provider;

				public Integer getId_provider () {
					return this.id_provider;
				}
				
			    public Integer id_beneficiary;

				public Integer getId_beneficiary () {
					return this.id_beneficiary;
				}
				


	@Override
	public int hashCode() {
		if (this.hashCodeDirty) {
			final int prime = PRIME;
			int result = DEFAULT_HASHCODE;
	
						result = prime * result + ((this.id_provider == null) ? 0 : this.id_provider.hashCode());
					
    		this.hashCode = result;
    		this.hashCodeDirty = false;
		}
		return this.hashCode;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		final row17Struct other = (row17Struct) obj;
		
						if (this.id_provider == null) {
							if (other.id_provider != null)
								return false;
						
						} else if (!this.id_provider.equals(other.id_provider))
						
							return false;
					

		return true;
    }

	public void copyDataTo(row17Struct other) {

		other.id_complaint = this.id_complaint;
	            other.id_provider = this.id_provider;
	            other.id_beneficiary = this.id_beneficiary;
	            
	}

	public void copyKeysDataTo(row17Struct other) {

		other.id_provider = this.id_provider;
	            	
	}



	private Integer readInteger(DataInputStream dis, ObjectInputStream ois) throws IOException{
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
	
	private Integer readInteger(DataInputStream dis, org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException{
		Integer intReturn;
        int length = 0;
        length = unmarshaller.readByte();
		if (length == -1) {
			intReturn = null;
		} else {
			intReturn = unmarshaller.readInt();
		}
		return intReturn;
	}

	private void writeInteger(Integer intNum, DataOutputStream dos, ObjectOutputStream oos) throws IOException{
		if(intNum == null) {
            dos.writeByte(-1);
		} else {
			dos.writeByte(0);
	    	dos.writeInt(intNum);
    	}
	}
	
	private void writeInteger(Integer intNum, DataOutputStream dos,org.jboss.marshalling.Marshaller marshaller ) throws IOException{
		if(intNum == null) {
			marshaller.writeByte(-1);
		} else {
			marshaller.writeByte(0);
			marshaller.writeInt(intNum);
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

    public void readKeysData(ObjectInputStream dis) {

		synchronized(commonByteArrayLock_TEST_Fact2) {

        	try {

        		int length = 0;
		
						this.id_provider = readInteger(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }
    
    public void readKeysData(org.jboss.marshalling.Unmarshaller dis) {

		synchronized(commonByteArrayLock_TEST_Fact2) {

        	try {

        		int length = 0;
		
						this.id_provider = readInteger(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }

    public void writeKeysData(ObjectOutputStream dos) {
        try {

		
					// Integer
				
						writeInteger(this.id_provider,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }
    
    public void writeKeysData(org.jboss.marshalling.Marshaller dos) {
        try {

		
					// Integer
				
						writeInteger(this.id_provider,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }



    /**
     * Fill Values data by reading ObjectInputStream.
     */
    public void readValuesData(DataInputStream dis, ObjectInputStream ois) {
        try {

			int length = 0;
		
						this.id_complaint = readInteger(dis,ois);
					
						this.id_beneficiary = readInteger(dis,ois);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

    }
    
    public void readValuesData(DataInputStream dis, org.jboss.marshalling.Unmarshaller objectIn) {
        try {
			int length = 0;
		
						this.id_complaint = readInteger(dis,objectIn);
					
						this.id_beneficiary = readInteger(dis,objectIn);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

    }

    /**
     * Return a byte array which represents Values data.
     */
    public void writeValuesData(DataOutputStream dos, ObjectOutputStream oos) {
        try {

		
					writeInteger(this.id_complaint, dos, oos);
					
					writeInteger(this.id_beneficiary, dos, oos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        	}

    }
    
    public void writeValuesData(DataOutputStream dos, org.jboss.marshalling.Marshaller objectOut){
                try {

		
					writeInteger(this.id_complaint, dos, objectOut);
					
					writeInteger(this.id_beneficiary, dos, objectOut);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        	}
    }


    
    public boolean supportMarshaller(){
        return true;
    }


    public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("[");
		sb.append("id_complaint="+String.valueOf(id_complaint));
		sb.append(",id_provider="+String.valueOf(id_provider));
		sb.append(",id_beneficiary="+String.valueOf(id_beneficiary));
	    sb.append("]");

	    return sb.toString();
    }

    /**
     * Compare keys
     */
    public int compareTo(row17Struct other) {

		int returnValue = -1;
		
						returnValue = checkNullsAndCompare(this.id_provider, other.id_provider);
						if(returnValue != 0) {
							return returnValue;
						}

					
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
public void tDBInput_17Process(final java.util.Map<String, Object> globalMap) throws TalendException {
	globalMap.put("tDBInput_17_SUBPROCESS_STATE", 0);

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



		row17Struct row17 = new row17Struct();




	
	/**
	 * [tAdvancedHash_row17 begin ] start
	 */

	

	
		
		ok_Hash.put("tAdvancedHash_row17", false);
		start_Hash.put("tAdvancedHash_row17", System.currentTimeMillis());
		
	
	currentComponent="tAdvancedHash_row17";

	
					if(execStat) {
						runStat.updateStatOnConnection(resourceMap,iterateId,0,0,"row17");
					}
				
		int tos_count_tAdvancedHash_row17 = 0;
		

			   		// connection name:row17
			   		// source node:tDBInput_17 - inputs:(after_tDBInput_1) outputs:(row17,row17) | target node:tAdvancedHash_row17 - inputs:(row17) outputs:()
			   		// linked node: tMap_1 - inputs:(row1,row2,row3,row4,row5,row6,row7,row8,row9,row10,row11,row12,row13,row14,row15,row16,row17,row18,row19,row21) outputs:(out)
			   
			   		org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE matchingModeEnum_row17 = 
			   			org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE.UNIQUE_MATCH;
			   			
			   
	   			org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row17Struct> tHash_Lookup_row17 =org.talend.designer.components.lookup.memory.AdvancedMemoryLookup.
	   						<row17Struct>getLookup(matchingModeEnum_row17);
	   						   
		   	   	   globalMap.put("tHash_Lookup_row17", tHash_Lookup_row17);
		   	   	   
				
           

 



/**
 * [tAdvancedHash_row17 begin ] stop
 */



	
	/**
	 * [tDBInput_17 begin ] start
	 */

	

	
		
		ok_Hash.put("tDBInput_17", false);
		start_Hash.put("tDBInput_17", System.currentTimeMillis());
		
	
	currentComponent="tDBInput_17";

	
		int tos_count_tDBInput_17 = 0;
		
	
    
	
			org.talend.designer.components.util.mssql.MSSqlGenerateTimestampUtil mssqlGTU_tDBInput_17 = org.talend.designer.components.util.mssql.MSSqlUtilFactory.getMSSqlGenerateTimestampUtil();
			
			java.util.List<String> talendToDBList_tDBInput_17 = new java.util.ArrayList();
			String[] talendToDBArray_tDBInput_17  = new String[]{"FLOAT","NUMERIC","NUMERIC IDENTITY","DECIMAL","DECIMAL IDENTITY","REAL"}; 
			java.util.Collections.addAll(talendToDBList_tDBInput_17, talendToDBArray_tDBInput_17); 
		    int nb_line_tDBInput_17 = 0;
		    java.sql.Connection conn_tDBInput_17 = null;
				String driverClass_tDBInput_17 = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
			    java.lang.Class jdbcclazz_tDBInput_17 = java.lang.Class.forName(driverClass_tDBInput_17);
				String dbUser_tDBInput_17 = "event";
				
				 
	final String decryptedPassword_tDBInput_17 = routines.system.PasswordEncryptUtil.decryptPassword("enc:routine.encryption.key.v1:uCv53BXbfpzBSuseKuZPgS9nXwzPH5aO036CnfYewQ==");
				
				String dbPwd_tDBInput_17 = decryptedPassword_tDBInput_17;
				
		    String port_tDBInput_17 = "1433";
		    String dbname_tDBInput_17 = "Event_SA" ;		    
		    String url_tDBInput_17 = "jdbc:sqlserver://" + "localhost" ;
		    if (!"".equals(port_tDBInput_17)) {
		    	url_tDBInput_17 += ":" + "1433";
		    }
		    if (!"".equals(dbname_tDBInput_17)) {		    
		    	url_tDBInput_17 += ";databaseName=" + "Event_SA"; 
		    }
		    url_tDBInput_17 += ";appName=" + projectName + ";" + "trustServerCertificate=true";
		    String dbschema_tDBInput_17 = "dbo";
				
				conn_tDBInput_17 = java.sql.DriverManager.getConnection(url_tDBInput_17,dbUser_tDBInput_17,dbPwd_tDBInput_17);
		        
		    
			java.sql.Statement stmt_tDBInput_17 = conn_tDBInput_17.createStatement();

		    String dbquery_tDBInput_17 = "SELECT dbo.Complaint.id_complaint,\n		dbo.Complaint.id_provider,\n		dbo.Complaint.id_beneficiary\nFROM	dbo.Complaint";
		    

            	globalMap.put("tDBInput_17_QUERY",dbquery_tDBInput_17);
		    java.sql.ResultSet rs_tDBInput_17 = null;

		    try {
		    	rs_tDBInput_17 = stmt_tDBInput_17.executeQuery(dbquery_tDBInput_17);
		    	java.sql.ResultSetMetaData rsmd_tDBInput_17 = rs_tDBInput_17.getMetaData();
		    	int colQtyInRs_tDBInput_17 = rsmd_tDBInput_17.getColumnCount();

		    String tmpContent_tDBInput_17 = null;
		    
		    
		    while (rs_tDBInput_17.next()) {
		        nb_line_tDBInput_17++;
		        
							if(colQtyInRs_tDBInput_17 < 1) {
								row17.id_complaint = null;
							} else {
		                          
            row17.id_complaint = rs_tDBInput_17.getInt(1);
            if(rs_tDBInput_17.wasNull()){
                    row17.id_complaint = null;
            }
		                    }
							if(colQtyInRs_tDBInput_17 < 2) {
								row17.id_provider = null;
							} else {
		                          
            row17.id_provider = rs_tDBInput_17.getInt(2);
            if(rs_tDBInput_17.wasNull()){
                    row17.id_provider = null;
            }
		                    }
							if(colQtyInRs_tDBInput_17 < 3) {
								row17.id_beneficiary = null;
							} else {
		                          
            row17.id_beneficiary = rs_tDBInput_17.getInt(3);
            if(rs_tDBInput_17.wasNull()){
                    row17.id_beneficiary = null;
            }
		                    }
					





 



/**
 * [tDBInput_17 begin ] stop
 */
	
	/**
	 * [tDBInput_17 main ] start
	 */

	

	
	
	currentComponent="tDBInput_17";

	

 


	tos_count_tDBInput_17++;

/**
 * [tDBInput_17 main ] stop
 */
	
	/**
	 * [tDBInput_17 process_data_begin ] start
	 */

	

	
	
	currentComponent="tDBInput_17";

	

 



/**
 * [tDBInput_17 process_data_begin ] stop
 */

	
	/**
	 * [tAdvancedHash_row17 main ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row17";

	
					if(execStat){
						runStat.updateStatOnConnection(iterateId,1,1
						
							,"row17"
						
						);
					}
					


			   
			   

					row17Struct row17_HashRow = new row17Struct();
		   	   	   
				
				row17_HashRow.id_complaint = row17.id_complaint;
				
				row17_HashRow.id_provider = row17.id_provider;
				
				row17_HashRow.id_beneficiary = row17.id_beneficiary;
				
			tHash_Lookup_row17.put(row17_HashRow);
			
            




 


	tos_count_tAdvancedHash_row17++;

/**
 * [tAdvancedHash_row17 main ] stop
 */
	
	/**
	 * [tAdvancedHash_row17 process_data_begin ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row17";

	

 



/**
 * [tAdvancedHash_row17 process_data_begin ] stop
 */
	
	/**
	 * [tAdvancedHash_row17 process_data_end ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row17";

	

 



/**
 * [tAdvancedHash_row17 process_data_end ] stop
 */



	
	/**
	 * [tDBInput_17 process_data_end ] start
	 */

	

	
	
	currentComponent="tDBInput_17";

	

 



/**
 * [tDBInput_17 process_data_end ] stop
 */
	
	/**
	 * [tDBInput_17 end ] start
	 */

	

	
	
	currentComponent="tDBInput_17";

	

	}
}finally{
	if (rs_tDBInput_17 != null) {
		rs_tDBInput_17.close();
	}
	if (stmt_tDBInput_17 != null) {
		stmt_tDBInput_17.close();
	}
		if(conn_tDBInput_17 != null && !conn_tDBInput_17.isClosed()) {
			
			conn_tDBInput_17.close();
			
			if("com.mysql.cj.jdbc.Driver".equals((String)globalMap.get("driverClass_"))
			    && routines.system.BundleUtils.inOSGi()) {
			        Class.forName("com.mysql.cj.jdbc.AbandonedConnectionCleanupThread").
			            getMethod("checkedShutdown").invoke(null, (Object[]) null);
			}
			
		}
}
globalMap.put("tDBInput_17_NB_LINE",nb_line_tDBInput_17);

 

ok_Hash.put("tDBInput_17", true);
end_Hash.put("tDBInput_17", System.currentTimeMillis());




/**
 * [tDBInput_17 end ] stop
 */

	
	/**
	 * [tAdvancedHash_row17 end ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row17";

	

tHash_Lookup_row17.endPut();

				if(execStat){
			  		runStat.updateStat(resourceMap,iterateId,2,0,"row17");
			  	}
			  	
 

ok_Hash.put("tAdvancedHash_row17", true);
end_Hash.put("tAdvancedHash_row17", System.currentTimeMillis());




/**
 * [tAdvancedHash_row17 end ] stop
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
	 * [tDBInput_17 finally ] start
	 */

	

	
	
	currentComponent="tDBInput_17";

	

 



/**
 * [tDBInput_17 finally ] stop
 */

	
	/**
	 * [tAdvancedHash_row17 finally ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row17";

	

 



/**
 * [tAdvancedHash_row17 finally ] stop
 */



				}catch(java.lang.Exception e){	
					//ignore
				}catch(java.lang.Error error){
					//ignore
				}
				resourceMap = null;
			}
		

		globalMap.put("tDBInput_17_SUBPROCESS_STATE", 1);
	}
	


public static class row18Struct implements routines.system.IPersistableComparableLookupRow<row18Struct> {
    final static byte[] commonByteArrayLock_TEST_Fact2 = new byte[0];
    static byte[] commonByteArray_TEST_Fact2 = new byte[0];
	protected static final int DEFAULT_HASHCODE = 1;
    protected static final int PRIME = 31;
    protected int hashCode = DEFAULT_HASHCODE;
    public boolean hashCodeDirty = true;

    public String loopKey;



	
			    public Integer id_entertainer;

				public Integer getId_entertainer () {
					return this.id_entertainer;
				}
				
			    public String categorie;

				public String getCategorie () {
					return this.categorie;
				}
				


	@Override
	public int hashCode() {
		if (this.hashCodeDirty) {
			final int prime = PRIME;
			int result = DEFAULT_HASHCODE;
	
						result = prime * result + ((this.categorie == null) ? 0 : this.categorie.hashCode());
					
    		this.hashCode = result;
    		this.hashCodeDirty = false;
		}
		return this.hashCode;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		final row18Struct other = (row18Struct) obj;
		
						if (this.categorie == null) {
							if (other.categorie != null)
								return false;
						
						} else if (!this.categorie.equals(other.categorie))
						
							return false;
					

		return true;
    }

	public void copyDataTo(row18Struct other) {

		other.id_entertainer = this.id_entertainer;
	            other.categorie = this.categorie;
	            
	}

	public void copyKeysDataTo(row18Struct other) {

		other.categorie = this.categorie;
	            	
	}



	private Integer readInteger(DataInputStream dis, ObjectInputStream ois) throws IOException{
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
	
	private Integer readInteger(DataInputStream dis, org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException{
		Integer intReturn;
        int length = 0;
        length = unmarshaller.readByte();
		if (length == -1) {
			intReturn = null;
		} else {
			intReturn = unmarshaller.readInt();
		}
		return intReturn;
	}

	private void writeInteger(Integer intNum, DataOutputStream dos, ObjectOutputStream oos) throws IOException{
		if(intNum == null) {
            dos.writeByte(-1);
		} else {
			dos.writeByte(0);
	    	dos.writeInt(intNum);
    	}
	}
	
	private void writeInteger(Integer intNum, DataOutputStream dos,org.jboss.marshalling.Marshaller marshaller ) throws IOException{
		if(intNum == null) {
			marshaller.writeByte(-1);
		} else {
			marshaller.writeByte(0);
			marshaller.writeInt(intNum);
    	}
	}

	private String readString(ObjectInputStream dis) throws IOException{
		String strReturn = null;
		int length = 0;
        length = dis.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			if(length > commonByteArray_TEST_Fact2.length) {
				if(length < 1024 && commonByteArray_TEST_Fact2.length == 0) {
   					commonByteArray_TEST_Fact2 = new byte[1024];
				} else {
   					commonByteArray_TEST_Fact2 = new byte[2 * length];
   				}
			}
			dis.readFully(commonByteArray_TEST_Fact2, 0, length);
			strReturn = new String(commonByteArray_TEST_Fact2, 0, length, utf8Charset);
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
			if(length > commonByteArray_TEST_Fact2.length) {
				if(length < 1024 && commonByteArray_TEST_Fact2.length == 0) {
   					commonByteArray_TEST_Fact2 = new byte[1024];
				} else {
   					commonByteArray_TEST_Fact2 = new byte[2 * length];
   				}
			}
			unmarshaller.readFully(commonByteArray_TEST_Fact2, 0, length);
			strReturn = new String(commonByteArray_TEST_Fact2, 0, length, utf8Charset);
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

    public void readKeysData(ObjectInputStream dis) {

		synchronized(commonByteArrayLock_TEST_Fact2) {

        	try {

        		int length = 0;
		
					this.categorie = readString(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }
    
    public void readKeysData(org.jboss.marshalling.Unmarshaller dis) {

		synchronized(commonByteArrayLock_TEST_Fact2) {

        	try {

        		int length = 0;
		
					this.categorie = readString(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }

    public void writeKeysData(ObjectOutputStream dos) {
        try {

		
					// String
				
						writeString(this.categorie,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }
    
    public void writeKeysData(org.jboss.marshalling.Marshaller dos) {
        try {

		
					// String
				
						writeString(this.categorie,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }



    /**
     * Fill Values data by reading ObjectInputStream.
     */
    public void readValuesData(DataInputStream dis, ObjectInputStream ois) {
        try {

			int length = 0;
		
						this.id_entertainer = readInteger(dis,ois);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

    }
    
    public void readValuesData(DataInputStream dis, org.jboss.marshalling.Unmarshaller objectIn) {
        try {
			int length = 0;
		
						this.id_entertainer = readInteger(dis,objectIn);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

    }

    /**
     * Return a byte array which represents Values data.
     */
    public void writeValuesData(DataOutputStream dos, ObjectOutputStream oos) {
        try {

		
					writeInteger(this.id_entertainer, dos, oos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        	}

    }
    
    public void writeValuesData(DataOutputStream dos, org.jboss.marshalling.Marshaller objectOut){
                try {

		
					writeInteger(this.id_entertainer, dos, objectOut);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        	}
    }


    
    public boolean supportMarshaller(){
        return true;
    }


    public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("[");
		sb.append("id_entertainer="+String.valueOf(id_entertainer));
		sb.append(",categorie="+categorie);
	    sb.append("]");

	    return sb.toString();
    }

    /**
     * Compare keys
     */
    public int compareTo(row18Struct other) {

		int returnValue = -1;
		
						returnValue = checkNullsAndCompare(this.categorie, other.categorie);
						if(returnValue != 0) {
							return returnValue;
						}

					
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
public void tDBInput_18Process(final java.util.Map<String, Object> globalMap) throws TalendException {
	globalMap.put("tDBInput_18_SUBPROCESS_STATE", 0);

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



		row18Struct row18 = new row18Struct();




	
	/**
	 * [tAdvancedHash_row18 begin ] start
	 */

	

	
		
		ok_Hash.put("tAdvancedHash_row18", false);
		start_Hash.put("tAdvancedHash_row18", System.currentTimeMillis());
		
	
	currentComponent="tAdvancedHash_row18";

	
					if(execStat) {
						runStat.updateStatOnConnection(resourceMap,iterateId,0,0,"row18");
					}
				
		int tos_count_tAdvancedHash_row18 = 0;
		

			   		// connection name:row18
			   		// source node:tDBInput_18 - inputs:(after_tDBInput_1) outputs:(row18,row18) | target node:tAdvancedHash_row18 - inputs:(row18) outputs:()
			   		// linked node: tMap_1 - inputs:(row1,row2,row3,row4,row5,row6,row7,row8,row9,row10,row11,row12,row13,row14,row15,row16,row17,row18,row19,row21) outputs:(out)
			   
			   		org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE matchingModeEnum_row18 = 
			   			org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE.UNIQUE_MATCH;
			   			
			   
	   			org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row18Struct> tHash_Lookup_row18 =org.talend.designer.components.lookup.memory.AdvancedMemoryLookup.
	   						<row18Struct>getLookup(matchingModeEnum_row18);
	   						   
		   	   	   globalMap.put("tHash_Lookup_row18", tHash_Lookup_row18);
		   	   	   
				
           

 



/**
 * [tAdvancedHash_row18 begin ] stop
 */



	
	/**
	 * [tDBInput_18 begin ] start
	 */

	

	
		
		ok_Hash.put("tDBInput_18", false);
		start_Hash.put("tDBInput_18", System.currentTimeMillis());
		
	
	currentComponent="tDBInput_18";

	
		int tos_count_tDBInput_18 = 0;
		
	
    
	
			org.talend.designer.components.util.mssql.MSSqlGenerateTimestampUtil mssqlGTU_tDBInput_18 = org.talend.designer.components.util.mssql.MSSqlUtilFactory.getMSSqlGenerateTimestampUtil();
			
			java.util.List<String> talendToDBList_tDBInput_18 = new java.util.ArrayList();
			String[] talendToDBArray_tDBInput_18  = new String[]{"FLOAT","NUMERIC","NUMERIC IDENTITY","DECIMAL","DECIMAL IDENTITY","REAL"}; 
			java.util.Collections.addAll(talendToDBList_tDBInput_18, talendToDBArray_tDBInput_18); 
		    int nb_line_tDBInput_18 = 0;
		    java.sql.Connection conn_tDBInput_18 = null;
				String driverClass_tDBInput_18 = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
			    java.lang.Class jdbcclazz_tDBInput_18 = java.lang.Class.forName(driverClass_tDBInput_18);
				String dbUser_tDBInput_18 = "bi";
				
				 
	final String decryptedPassword_tDBInput_18 = routines.system.PasswordEncryptUtil.decryptPassword("enc:routine.encryption.key.v1:EHyLzdTcH4uV/HtNbuqEmyv/PJO9QYUyoN4pV5rg");
				
				String dbPwd_tDBInput_18 = decryptedPassword_tDBInput_18;
				
		    String port_tDBInput_18 = "1433";
		    String dbname_tDBInput_18 = "event_DWH" ;		    
		    String url_tDBInput_18 = "jdbc:sqlserver://" + "localhost" ;
		    if (!"".equals(port_tDBInput_18)) {
		    	url_tDBInput_18 += ":" + "1433";
		    }
		    if (!"".equals(dbname_tDBInput_18)) {		    
		    	url_tDBInput_18 += ";databaseName=" + "event_DWH"; 
		    }
		    url_tDBInput_18 += ";appName=" + projectName + ";" + "trustServerCertificate=true";
		    String dbschema_tDBInput_18 = "dbo";
				
				conn_tDBInput_18 = java.sql.DriverManager.getConnection(url_tDBInput_18,dbUser_tDBInput_18,dbPwd_tDBInput_18);
		        
		    
			java.sql.Statement stmt_tDBInput_18 = conn_tDBInput_18.createStatement();

		    String dbquery_tDBInput_18 = "SELECT dbo.Dim_Entertainer.id_entertainer,\n		dbo.Dim_Entertainer.categorie\nFROM	dbo.Dim_Entertainer";
		    

            	globalMap.put("tDBInput_18_QUERY",dbquery_tDBInput_18);
		    java.sql.ResultSet rs_tDBInput_18 = null;

		    try {
		    	rs_tDBInput_18 = stmt_tDBInput_18.executeQuery(dbquery_tDBInput_18);
		    	java.sql.ResultSetMetaData rsmd_tDBInput_18 = rs_tDBInput_18.getMetaData();
		    	int colQtyInRs_tDBInput_18 = rsmd_tDBInput_18.getColumnCount();

		    String tmpContent_tDBInput_18 = null;
		    
		    
		    while (rs_tDBInput_18.next()) {
		        nb_line_tDBInput_18++;
		        
							if(colQtyInRs_tDBInput_18 < 1) {
								row18.id_entertainer = null;
							} else {
		                          
            row18.id_entertainer = rs_tDBInput_18.getInt(1);
            if(rs_tDBInput_18.wasNull()){
                    row18.id_entertainer = null;
            }
		                    }
							if(colQtyInRs_tDBInput_18 < 2) {
								row18.categorie = null;
							} else {
	                         		
           		tmpContent_tDBInput_18 = rs_tDBInput_18.getString(2);
            if(tmpContent_tDBInput_18 != null) {
            	if (talendToDBList_tDBInput_18 .contains(rsmd_tDBInput_18.getColumnTypeName(2).toUpperCase(java.util.Locale.ENGLISH))) {
            		row18.categorie = FormatterUtils.formatUnwithE(tmpContent_tDBInput_18);
            	} else {
                	row18.categorie = tmpContent_tDBInput_18;
                }
            } else {
                row18.categorie = null;
            }
		                    }
					





 



/**
 * [tDBInput_18 begin ] stop
 */
	
	/**
	 * [tDBInput_18 main ] start
	 */

	

	
	
	currentComponent="tDBInput_18";

	

 


	tos_count_tDBInput_18++;

/**
 * [tDBInput_18 main ] stop
 */
	
	/**
	 * [tDBInput_18 process_data_begin ] start
	 */

	

	
	
	currentComponent="tDBInput_18";

	

 



/**
 * [tDBInput_18 process_data_begin ] stop
 */

	
	/**
	 * [tAdvancedHash_row18 main ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row18";

	
					if(execStat){
						runStat.updateStatOnConnection(iterateId,1,1
						
							,"row18"
						
						);
					}
					


			   
			   

					row18Struct row18_HashRow = new row18Struct();
		   	   	   
				
				row18_HashRow.id_entertainer = row18.id_entertainer;
				
				row18_HashRow.categorie = row18.categorie;
				
			tHash_Lookup_row18.put(row18_HashRow);
			
            




 


	tos_count_tAdvancedHash_row18++;

/**
 * [tAdvancedHash_row18 main ] stop
 */
	
	/**
	 * [tAdvancedHash_row18 process_data_begin ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row18";

	

 



/**
 * [tAdvancedHash_row18 process_data_begin ] stop
 */
	
	/**
	 * [tAdvancedHash_row18 process_data_end ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row18";

	

 



/**
 * [tAdvancedHash_row18 process_data_end ] stop
 */



	
	/**
	 * [tDBInput_18 process_data_end ] start
	 */

	

	
	
	currentComponent="tDBInput_18";

	

 



/**
 * [tDBInput_18 process_data_end ] stop
 */
	
	/**
	 * [tDBInput_18 end ] start
	 */

	

	
	
	currentComponent="tDBInput_18";

	

	}
}finally{
	if (rs_tDBInput_18 != null) {
		rs_tDBInput_18.close();
	}
	if (stmt_tDBInput_18 != null) {
		stmt_tDBInput_18.close();
	}
		if(conn_tDBInput_18 != null && !conn_tDBInput_18.isClosed()) {
			
			conn_tDBInput_18.close();
			
			if("com.mysql.cj.jdbc.Driver".equals((String)globalMap.get("driverClass_"))
			    && routines.system.BundleUtils.inOSGi()) {
			        Class.forName("com.mysql.cj.jdbc.AbandonedConnectionCleanupThread").
			            getMethod("checkedShutdown").invoke(null, (Object[]) null);
			}
			
		}
}
globalMap.put("tDBInput_18_NB_LINE",nb_line_tDBInput_18);

 

ok_Hash.put("tDBInput_18", true);
end_Hash.put("tDBInput_18", System.currentTimeMillis());




/**
 * [tDBInput_18 end ] stop
 */

	
	/**
	 * [tAdvancedHash_row18 end ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row18";

	

tHash_Lookup_row18.endPut();

				if(execStat){
			  		runStat.updateStat(resourceMap,iterateId,2,0,"row18");
			  	}
			  	
 

ok_Hash.put("tAdvancedHash_row18", true);
end_Hash.put("tAdvancedHash_row18", System.currentTimeMillis());




/**
 * [tAdvancedHash_row18 end ] stop
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
	 * [tDBInput_18 finally ] start
	 */

	

	
	
	currentComponent="tDBInput_18";

	

 



/**
 * [tDBInput_18 finally ] stop
 */

	
	/**
	 * [tAdvancedHash_row18 finally ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row18";

	

 



/**
 * [tAdvancedHash_row18 finally ] stop
 */



				}catch(java.lang.Exception e){	
					//ignore
				}catch(java.lang.Error error){
					//ignore
				}
				resourceMap = null;
			}
		

		globalMap.put("tDBInput_18_SUBPROCESS_STATE", 1);
	}
	


public static class row19Struct implements routines.system.IPersistableComparableLookupRow<row19Struct> {
    final static byte[] commonByteArrayLock_TEST_Fact2 = new byte[0];
    static byte[] commonByteArray_TEST_Fact2 = new byte[0];
	protected static final int DEFAULT_HASHCODE = 1;
    protected static final int PRIME = 31;
    protected int hashCode = DEFAULT_HASHCODE;
    public boolean hashCodeDirty = true;

    public String loopKey;



	
			    public Integer id_venue;

				public Integer getId_venue () {
					return this.id_venue;
				}
				
			    public String city;

				public String getCity () {
					return this.city;
				}
				


	@Override
	public int hashCode() {
		if (this.hashCodeDirty) {
			final int prime = PRIME;
			int result = DEFAULT_HASHCODE;
	
						result = prime * result + ((this.city == null) ? 0 : this.city.hashCode());
					
    		this.hashCode = result;
    		this.hashCodeDirty = false;
		}
		return this.hashCode;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		final row19Struct other = (row19Struct) obj;
		
						if (this.city == null) {
							if (other.city != null)
								return false;
						
						} else if (!this.city.equals(other.city))
						
							return false;
					

		return true;
    }

	public void copyDataTo(row19Struct other) {

		other.id_venue = this.id_venue;
	            other.city = this.city;
	            
	}

	public void copyKeysDataTo(row19Struct other) {

		other.city = this.city;
	            	
	}



	private Integer readInteger(DataInputStream dis, ObjectInputStream ois) throws IOException{
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
	
	private Integer readInteger(DataInputStream dis, org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException{
		Integer intReturn;
        int length = 0;
        length = unmarshaller.readByte();
		if (length == -1) {
			intReturn = null;
		} else {
			intReturn = unmarshaller.readInt();
		}
		return intReturn;
	}

	private void writeInteger(Integer intNum, DataOutputStream dos, ObjectOutputStream oos) throws IOException{
		if(intNum == null) {
            dos.writeByte(-1);
		} else {
			dos.writeByte(0);
	    	dos.writeInt(intNum);
    	}
	}
	
	private void writeInteger(Integer intNum, DataOutputStream dos,org.jboss.marshalling.Marshaller marshaller ) throws IOException{
		if(intNum == null) {
			marshaller.writeByte(-1);
		} else {
			marshaller.writeByte(0);
			marshaller.writeInt(intNum);
    	}
	}

	private String readString(ObjectInputStream dis) throws IOException{
		String strReturn = null;
		int length = 0;
        length = dis.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			if(length > commonByteArray_TEST_Fact2.length) {
				if(length < 1024 && commonByteArray_TEST_Fact2.length == 0) {
   					commonByteArray_TEST_Fact2 = new byte[1024];
				} else {
   					commonByteArray_TEST_Fact2 = new byte[2 * length];
   				}
			}
			dis.readFully(commonByteArray_TEST_Fact2, 0, length);
			strReturn = new String(commonByteArray_TEST_Fact2, 0, length, utf8Charset);
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
			if(length > commonByteArray_TEST_Fact2.length) {
				if(length < 1024 && commonByteArray_TEST_Fact2.length == 0) {
   					commonByteArray_TEST_Fact2 = new byte[1024];
				} else {
   					commonByteArray_TEST_Fact2 = new byte[2 * length];
   				}
			}
			unmarshaller.readFully(commonByteArray_TEST_Fact2, 0, length);
			strReturn = new String(commonByteArray_TEST_Fact2, 0, length, utf8Charset);
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

    public void readKeysData(ObjectInputStream dis) {

		synchronized(commonByteArrayLock_TEST_Fact2) {

        	try {

        		int length = 0;
		
					this.city = readString(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }
    
    public void readKeysData(org.jboss.marshalling.Unmarshaller dis) {

		synchronized(commonByteArrayLock_TEST_Fact2) {

        	try {

        		int length = 0;
		
					this.city = readString(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }

    public void writeKeysData(ObjectOutputStream dos) {
        try {

		
					// String
				
						writeString(this.city,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }
    
    public void writeKeysData(org.jboss.marshalling.Marshaller dos) {
        try {

		
					// String
				
						writeString(this.city,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }



    /**
     * Fill Values data by reading ObjectInputStream.
     */
    public void readValuesData(DataInputStream dis, ObjectInputStream ois) {
        try {

			int length = 0;
		
						this.id_venue = readInteger(dis,ois);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

    }
    
    public void readValuesData(DataInputStream dis, org.jboss.marshalling.Unmarshaller objectIn) {
        try {
			int length = 0;
		
						this.id_venue = readInteger(dis,objectIn);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

    }

    /**
     * Return a byte array which represents Values data.
     */
    public void writeValuesData(DataOutputStream dos, ObjectOutputStream oos) {
        try {

		
					writeInteger(this.id_venue, dos, oos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        	}

    }
    
    public void writeValuesData(DataOutputStream dos, org.jboss.marshalling.Marshaller objectOut){
                try {

		
					writeInteger(this.id_venue, dos, objectOut);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        	}
    }


    
    public boolean supportMarshaller(){
        return true;
    }


    public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("[");
		sb.append("id_venue="+String.valueOf(id_venue));
		sb.append(",city="+city);
	    sb.append("]");

	    return sb.toString();
    }

    /**
     * Compare keys
     */
    public int compareTo(row19Struct other) {

		int returnValue = -1;
		
						returnValue = checkNullsAndCompare(this.city, other.city);
						if(returnValue != 0) {
							return returnValue;
						}

					
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
public void tDBInput_19Process(final java.util.Map<String, Object> globalMap) throws TalendException {
	globalMap.put("tDBInput_19_SUBPROCESS_STATE", 0);

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



		row19Struct row19 = new row19Struct();




	
	/**
	 * [tAdvancedHash_row19 begin ] start
	 */

	

	
		
		ok_Hash.put("tAdvancedHash_row19", false);
		start_Hash.put("tAdvancedHash_row19", System.currentTimeMillis());
		
	
	currentComponent="tAdvancedHash_row19";

	
					if(execStat) {
						runStat.updateStatOnConnection(resourceMap,iterateId,0,0,"row19");
					}
				
		int tos_count_tAdvancedHash_row19 = 0;
		

			   		// connection name:row19
			   		// source node:tDBInput_19 - inputs:(after_tDBInput_1) outputs:(row19,row19) | target node:tAdvancedHash_row19 - inputs:(row19) outputs:()
			   		// linked node: tMap_1 - inputs:(row1,row2,row3,row4,row5,row6,row7,row8,row9,row10,row11,row12,row13,row14,row15,row16,row17,row18,row19,row21) outputs:(out)
			   
			   		org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE matchingModeEnum_row19 = 
			   			org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE.UNIQUE_MATCH;
			   			
			   
	   			org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row19Struct> tHash_Lookup_row19 =org.talend.designer.components.lookup.memory.AdvancedMemoryLookup.
	   						<row19Struct>getLookup(matchingModeEnum_row19);
	   						   
		   	   	   globalMap.put("tHash_Lookup_row19", tHash_Lookup_row19);
		   	   	   
				
           

 



/**
 * [tAdvancedHash_row19 begin ] stop
 */



	
	/**
	 * [tDBInput_19 begin ] start
	 */

	

	
		
		ok_Hash.put("tDBInput_19", false);
		start_Hash.put("tDBInput_19", System.currentTimeMillis());
		
	
	currentComponent="tDBInput_19";

	
		int tos_count_tDBInput_19 = 0;
		
	
    
	
			org.talend.designer.components.util.mssql.MSSqlGenerateTimestampUtil mssqlGTU_tDBInput_19 = org.talend.designer.components.util.mssql.MSSqlUtilFactory.getMSSqlGenerateTimestampUtil();
			
			java.util.List<String> talendToDBList_tDBInput_19 = new java.util.ArrayList();
			String[] talendToDBArray_tDBInput_19  = new String[]{"FLOAT","NUMERIC","NUMERIC IDENTITY","DECIMAL","DECIMAL IDENTITY","REAL"}; 
			java.util.Collections.addAll(talendToDBList_tDBInput_19, talendToDBArray_tDBInput_19); 
		    int nb_line_tDBInput_19 = 0;
		    java.sql.Connection conn_tDBInput_19 = null;
				String driverClass_tDBInput_19 = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
			    java.lang.Class jdbcclazz_tDBInput_19 = java.lang.Class.forName(driverClass_tDBInput_19);
				String dbUser_tDBInput_19 = "bi";
				
				 
	final String decryptedPassword_tDBInput_19 = routines.system.PasswordEncryptUtil.decryptPassword("enc:routine.encryption.key.v1:cLwKrMVQEigmD9iuUdKru0+CBya/1XYvyQ7AvytG");
				
				String dbPwd_tDBInput_19 = decryptedPassword_tDBInput_19;
				
		    String port_tDBInput_19 = "1433";
		    String dbname_tDBInput_19 = "event_DWH" ;		    
		    String url_tDBInput_19 = "jdbc:sqlserver://" + "localhost" ;
		    if (!"".equals(port_tDBInput_19)) {
		    	url_tDBInput_19 += ":" + "1433";
		    }
		    if (!"".equals(dbname_tDBInput_19)) {		    
		    	url_tDBInput_19 += ";databaseName=" + "event_DWH"; 
		    }
		    url_tDBInput_19 += ";appName=" + projectName + ";" + "trustServerCertificate=true";
		    String dbschema_tDBInput_19 = "dbo";
				
				conn_tDBInput_19 = java.sql.DriverManager.getConnection(url_tDBInput_19,dbUser_tDBInput_19,dbPwd_tDBInput_19);
		        
		    
			java.sql.Statement stmt_tDBInput_19 = conn_tDBInput_19.createStatement();

		    String dbquery_tDBInput_19 = "SELECT dbo.Dim_Venue.id_venue,\n		dbo.Dim_Venue.city\nFROM	dbo.Dim_Venue";
		    

            	globalMap.put("tDBInput_19_QUERY",dbquery_tDBInput_19);
		    java.sql.ResultSet rs_tDBInput_19 = null;

		    try {
		    	rs_tDBInput_19 = stmt_tDBInput_19.executeQuery(dbquery_tDBInput_19);
		    	java.sql.ResultSetMetaData rsmd_tDBInput_19 = rs_tDBInput_19.getMetaData();
		    	int colQtyInRs_tDBInput_19 = rsmd_tDBInput_19.getColumnCount();

		    String tmpContent_tDBInput_19 = null;
		    
		    
		    while (rs_tDBInput_19.next()) {
		        nb_line_tDBInput_19++;
		        
							if(colQtyInRs_tDBInput_19 < 1) {
								row19.id_venue = null;
							} else {
		                          
            row19.id_venue = rs_tDBInput_19.getInt(1);
            if(rs_tDBInput_19.wasNull()){
                    row19.id_venue = null;
            }
		                    }
							if(colQtyInRs_tDBInput_19 < 2) {
								row19.city = null;
							} else {
	                         		
           		tmpContent_tDBInput_19 = rs_tDBInput_19.getString(2);
            if(tmpContent_tDBInput_19 != null) {
            	if (talendToDBList_tDBInput_19 .contains(rsmd_tDBInput_19.getColumnTypeName(2).toUpperCase(java.util.Locale.ENGLISH))) {
            		row19.city = FormatterUtils.formatUnwithE(tmpContent_tDBInput_19);
            	} else {
                	row19.city = tmpContent_tDBInput_19;
                }
            } else {
                row19.city = null;
            }
		                    }
					





 



/**
 * [tDBInput_19 begin ] stop
 */
	
	/**
	 * [tDBInput_19 main ] start
	 */

	

	
	
	currentComponent="tDBInput_19";

	

 


	tos_count_tDBInput_19++;

/**
 * [tDBInput_19 main ] stop
 */
	
	/**
	 * [tDBInput_19 process_data_begin ] start
	 */

	

	
	
	currentComponent="tDBInput_19";

	

 



/**
 * [tDBInput_19 process_data_begin ] stop
 */

	
	/**
	 * [tAdvancedHash_row19 main ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row19";

	
					if(execStat){
						runStat.updateStatOnConnection(iterateId,1,1
						
							,"row19"
						
						);
					}
					


			   
			   

					row19Struct row19_HashRow = new row19Struct();
		   	   	   
				
				row19_HashRow.id_venue = row19.id_venue;
				
				row19_HashRow.city = row19.city;
				
			tHash_Lookup_row19.put(row19_HashRow);
			
            




 


	tos_count_tAdvancedHash_row19++;

/**
 * [tAdvancedHash_row19 main ] stop
 */
	
	/**
	 * [tAdvancedHash_row19 process_data_begin ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row19";

	

 



/**
 * [tAdvancedHash_row19 process_data_begin ] stop
 */
	
	/**
	 * [tAdvancedHash_row19 process_data_end ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row19";

	

 



/**
 * [tAdvancedHash_row19 process_data_end ] stop
 */



	
	/**
	 * [tDBInput_19 process_data_end ] start
	 */

	

	
	
	currentComponent="tDBInput_19";

	

 



/**
 * [tDBInput_19 process_data_end ] stop
 */
	
	/**
	 * [tDBInput_19 end ] start
	 */

	

	
	
	currentComponent="tDBInput_19";

	

	}
}finally{
	if (rs_tDBInput_19 != null) {
		rs_tDBInput_19.close();
	}
	if (stmt_tDBInput_19 != null) {
		stmt_tDBInput_19.close();
	}
		if(conn_tDBInput_19 != null && !conn_tDBInput_19.isClosed()) {
			
			conn_tDBInput_19.close();
			
			if("com.mysql.cj.jdbc.Driver".equals((String)globalMap.get("driverClass_"))
			    && routines.system.BundleUtils.inOSGi()) {
			        Class.forName("com.mysql.cj.jdbc.AbandonedConnectionCleanupThread").
			            getMethod("checkedShutdown").invoke(null, (Object[]) null);
			}
			
		}
}
globalMap.put("tDBInput_19_NB_LINE",nb_line_tDBInput_19);

 

ok_Hash.put("tDBInput_19", true);
end_Hash.put("tDBInput_19", System.currentTimeMillis());




/**
 * [tDBInput_19 end ] stop
 */

	
	/**
	 * [tAdvancedHash_row19 end ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row19";

	

tHash_Lookup_row19.endPut();

				if(execStat){
			  		runStat.updateStat(resourceMap,iterateId,2,0,"row19");
			  	}
			  	
 

ok_Hash.put("tAdvancedHash_row19", true);
end_Hash.put("tAdvancedHash_row19", System.currentTimeMillis());




/**
 * [tAdvancedHash_row19 end ] stop
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
	 * [tDBInput_19 finally ] start
	 */

	

	
	
	currentComponent="tDBInput_19";

	

 



/**
 * [tDBInput_19 finally ] stop
 */

	
	/**
	 * [tAdvancedHash_row19 finally ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row19";

	

 



/**
 * [tAdvancedHash_row19 finally ] stop
 */



				}catch(java.lang.Exception e){	
					//ignore
				}catch(java.lang.Error error){
					//ignore
				}
				resourceMap = null;
			}
		

		globalMap.put("tDBInput_19_SUBPROCESS_STATE", 1);
	}
	


public static class row21Struct implements routines.system.IPersistableComparableLookupRow<row21Struct> {
    final static byte[] commonByteArrayLock_TEST_Fact2 = new byte[0];
    static byte[] commonByteArray_TEST_Fact2 = new byte[0];
	protected static final int DEFAULT_HASHCODE = 1;
    protected static final int PRIME = 31;
    protected int hashCode = DEFAULT_HASHCODE;
    public boolean hashCodeDirty = true;

    public String loopKey;



	
			    public String month;

				public String getMonth () {
					return this.month;
				}
				
			    public Float marketing_spend;

				public Float getMarketing_spend () {
					return this.marketing_spend;
				}
				


	@Override
	public int hashCode() {
		if (this.hashCodeDirty) {
			final int prime = PRIME;
			int result = DEFAULT_HASHCODE;
	
						result = prime * result + ((this.month == null) ? 0 : this.month.hashCode());
					
    		this.hashCode = result;
    		this.hashCodeDirty = false;
		}
		return this.hashCode;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		final row21Struct other = (row21Struct) obj;
		
						if (this.month == null) {
							if (other.month != null)
								return false;
						
						} else if (!this.month.equals(other.month))
						
							return false;
					

		return true;
    }

	public void copyDataTo(row21Struct other) {

		other.month = this.month;
	            other.marketing_spend = this.marketing_spend;
	            
	}

	public void copyKeysDataTo(row21Struct other) {

		other.month = this.month;
	            	
	}




	private String readString(ObjectInputStream dis) throws IOException{
		String strReturn = null;
		int length = 0;
        length = dis.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			if(length > commonByteArray_TEST_Fact2.length) {
				if(length < 1024 && commonByteArray_TEST_Fact2.length == 0) {
   					commonByteArray_TEST_Fact2 = new byte[1024];
				} else {
   					commonByteArray_TEST_Fact2 = new byte[2 * length];
   				}
			}
			dis.readFully(commonByteArray_TEST_Fact2, 0, length);
			strReturn = new String(commonByteArray_TEST_Fact2, 0, length, utf8Charset);
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
			if(length > commonByteArray_TEST_Fact2.length) {
				if(length < 1024 && commonByteArray_TEST_Fact2.length == 0) {
   					commonByteArray_TEST_Fact2 = new byte[1024];
				} else {
   					commonByteArray_TEST_Fact2 = new byte[2 * length];
   				}
			}
			unmarshaller.readFully(commonByteArray_TEST_Fact2, 0, length);
			strReturn = new String(commonByteArray_TEST_Fact2, 0, length, utf8Charset);
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

    public void readKeysData(ObjectInputStream dis) {

		synchronized(commonByteArrayLock_TEST_Fact2) {

        	try {

        		int length = 0;
		
					this.month = readString(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }
    
    public void readKeysData(org.jboss.marshalling.Unmarshaller dis) {

		synchronized(commonByteArrayLock_TEST_Fact2) {

        	try {

        		int length = 0;
		
					this.month = readString(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }

    public void writeKeysData(ObjectOutputStream dos) {
        try {

		
					// String
				
						writeString(this.month,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }
    
    public void writeKeysData(org.jboss.marshalling.Marshaller dos) {
        try {

		
					// String
				
						writeString(this.month,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }



    /**
     * Fill Values data by reading ObjectInputStream.
     */
    public void readValuesData(DataInputStream dis, ObjectInputStream ois) {
        try {

			int length = 0;
		
			            length = dis.readByte();
           				if (length == -1) {
           	    			this.marketing_spend = null;
           				} else {
           			    	this.marketing_spend = dis.readFloat();
           				}
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

    }
    
    public void readValuesData(DataInputStream dis, org.jboss.marshalling.Unmarshaller objectIn) {
        try {
			int length = 0;
		
			            length = objectIn.readByte();
           				if (length == -1) {
           	    			this.marketing_spend = null;
           				} else {
           			    	this.marketing_spend = objectIn.readFloat();
           				}
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

    }

    /**
     * Return a byte array which represents Values data.
     */
    public void writeValuesData(DataOutputStream dos, ObjectOutputStream oos) {
        try {

		
						if(this.marketing_spend == null) {
			                dos.writeByte(-1);
						} else {
               				dos.writeByte(0);
           			    	dos.writeFloat(this.marketing_spend);
		            	}
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        	}

    }
    
    public void writeValuesData(DataOutputStream dos, org.jboss.marshalling.Marshaller objectOut){
                try {

		
						if(this.marketing_spend == null) {
							objectOut.writeByte(-1);
						} else {
							objectOut.writeByte(0);
							objectOut.writeFloat(this.marketing_spend);
		            	}
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        	}
    }


    
    public boolean supportMarshaller(){
        return true;
    }


    public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("[");
		sb.append("month="+month);
		sb.append(",marketing_spend="+String.valueOf(marketing_spend));
	    sb.append("]");

	    return sb.toString();
    }

    /**
     * Compare keys
     */
    public int compareTo(row21Struct other) {

		int returnValue = -1;
		
						returnValue = checkNullsAndCompare(this.month, other.month);
						if(returnValue != 0) {
							return returnValue;
						}

					
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
public void tDBInput_21Process(final java.util.Map<String, Object> globalMap) throws TalendException {
	globalMap.put("tDBInput_21_SUBPROCESS_STATE", 0);

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



		row21Struct row21 = new row21Struct();




	
	/**
	 * [tAdvancedHash_row21 begin ] start
	 */

	

	
		
		ok_Hash.put("tAdvancedHash_row21", false);
		start_Hash.put("tAdvancedHash_row21", System.currentTimeMillis());
		
	
	currentComponent="tAdvancedHash_row21";

	
					if(execStat) {
						runStat.updateStatOnConnection(resourceMap,iterateId,0,0,"row21");
					}
				
		int tos_count_tAdvancedHash_row21 = 0;
		

			   		// connection name:row21
			   		// source node:tDBInput_21 - inputs:(after_tDBInput_1) outputs:(row21,row21) | target node:tAdvancedHash_row21 - inputs:(row21) outputs:()
			   		// linked node: tMap_1 - inputs:(row1,row2,row3,row4,row5,row6,row7,row8,row9,row10,row11,row12,row13,row14,row15,row16,row17,row18,row19,row21) outputs:(out)
			   
			   		org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE matchingModeEnum_row21 = 
			   			org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE.UNIQUE_MATCH;
			   			
			   
	   			org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row21Struct> tHash_Lookup_row21 =org.talend.designer.components.lookup.memory.AdvancedMemoryLookup.
	   						<row21Struct>getLookup(matchingModeEnum_row21);
	   						   
		   	   	   globalMap.put("tHash_Lookup_row21", tHash_Lookup_row21);
		   	   	   
				
           

 



/**
 * [tAdvancedHash_row21 begin ] stop
 */



	
	/**
	 * [tDBInput_21 begin ] start
	 */

	

	
		
		ok_Hash.put("tDBInput_21", false);
		start_Hash.put("tDBInput_21", System.currentTimeMillis());
		
	
	currentComponent="tDBInput_21";

	
		int tos_count_tDBInput_21 = 0;
		
	
    
	
			org.talend.designer.components.util.mssql.MSSqlGenerateTimestampUtil mssqlGTU_tDBInput_21 = org.talend.designer.components.util.mssql.MSSqlUtilFactory.getMSSqlGenerateTimestampUtil();
			
			java.util.List<String> talendToDBList_tDBInput_21 = new java.util.ArrayList();
			String[] talendToDBArray_tDBInput_21  = new String[]{"FLOAT","NUMERIC","NUMERIC IDENTITY","DECIMAL","DECIMAL IDENTITY","REAL"}; 
			java.util.Collections.addAll(talendToDBList_tDBInput_21, talendToDBArray_tDBInput_21); 
		    int nb_line_tDBInput_21 = 0;
		    java.sql.Connection conn_tDBInput_21 = null;
				String driverClass_tDBInput_21 = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
			    java.lang.Class jdbcclazz_tDBInput_21 = java.lang.Class.forName(driverClass_tDBInput_21);
				String dbUser_tDBInput_21 = "event";
				
				 
	final String decryptedPassword_tDBInput_21 = routines.system.PasswordEncryptUtil.decryptPassword("enc:routine.encryption.key.v1:z3aAPabdoNvh2+T6i0vCTzxso+RhwtLG93gE6fH7Pg==");
				
				String dbPwd_tDBInput_21 = decryptedPassword_tDBInput_21;
				
		    String port_tDBInput_21 = "1433";
		    String dbname_tDBInput_21 = "Event_SA" ;		    
		    String url_tDBInput_21 = "jdbc:sqlserver://" + "localhost" ;
		    if (!"".equals(port_tDBInput_21)) {
		    	url_tDBInput_21 += ":" + "1433";
		    }
		    if (!"".equals(dbname_tDBInput_21)) {		    
		    	url_tDBInput_21 += ";databaseName=" + "Event_SA"; 
		    }
		    url_tDBInput_21 += ";appName=" + projectName + ";" + "trustServerCertificate=true";
		    String dbschema_tDBInput_21 = "dbo";
				
				conn_tDBInput_21 = java.sql.DriverManager.getConnection(url_tDBInput_21,dbUser_tDBInput_21,dbPwd_tDBInput_21);
		        
		    
			java.sql.Statement stmt_tDBInput_21 = conn_tDBInput_21.createStatement();

		    String dbquery_tDBInput_21 = "SELECT dbo.Marketing_spend.\"month\",\n		dbo.Marketing_spend.marketing_spend\nFROM	dbo.Marketing_spend";
		    

            	globalMap.put("tDBInput_21_QUERY",dbquery_tDBInput_21);
		    java.sql.ResultSet rs_tDBInput_21 = null;

		    try {
		    	rs_tDBInput_21 = stmt_tDBInput_21.executeQuery(dbquery_tDBInput_21);
		    	java.sql.ResultSetMetaData rsmd_tDBInput_21 = rs_tDBInput_21.getMetaData();
		    	int colQtyInRs_tDBInput_21 = rsmd_tDBInput_21.getColumnCount();

		    String tmpContent_tDBInput_21 = null;
		    
		    
		    while (rs_tDBInput_21.next()) {
		        nb_line_tDBInput_21++;
		        
							if(colQtyInRs_tDBInput_21 < 1) {
								row21.month = null;
							} else {
	                         		
           		tmpContent_tDBInput_21 = rs_tDBInput_21.getString(1);
            if(tmpContent_tDBInput_21 != null) {
            	if (talendToDBList_tDBInput_21 .contains(rsmd_tDBInput_21.getColumnTypeName(1).toUpperCase(java.util.Locale.ENGLISH))) {
            		row21.month = FormatterUtils.formatUnwithE(tmpContent_tDBInput_21);
            	} else {
                	row21.month = tmpContent_tDBInput_21;
                }
            } else {
                row21.month = null;
            }
		                    }
							if(colQtyInRs_tDBInput_21 < 2) {
								row21.marketing_spend = null;
							} else {
		                          
            row21.marketing_spend = rs_tDBInput_21.getFloat(2);
            if(rs_tDBInput_21.wasNull()){
                    row21.marketing_spend = null;
            }
		                    }
					





 



/**
 * [tDBInput_21 begin ] stop
 */
	
	/**
	 * [tDBInput_21 main ] start
	 */

	

	
	
	currentComponent="tDBInput_21";

	

 


	tos_count_tDBInput_21++;

/**
 * [tDBInput_21 main ] stop
 */
	
	/**
	 * [tDBInput_21 process_data_begin ] start
	 */

	

	
	
	currentComponent="tDBInput_21";

	

 



/**
 * [tDBInput_21 process_data_begin ] stop
 */

	
	/**
	 * [tAdvancedHash_row21 main ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row21";

	
					if(execStat){
						runStat.updateStatOnConnection(iterateId,1,1
						
							,"row21"
						
						);
					}
					


			   
			   

					row21Struct row21_HashRow = new row21Struct();
		   	   	   
				
				row21_HashRow.month = row21.month;
				
				row21_HashRow.marketing_spend = row21.marketing_spend;
				
			tHash_Lookup_row21.put(row21_HashRow);
			
            




 


	tos_count_tAdvancedHash_row21++;

/**
 * [tAdvancedHash_row21 main ] stop
 */
	
	/**
	 * [tAdvancedHash_row21 process_data_begin ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row21";

	

 



/**
 * [tAdvancedHash_row21 process_data_begin ] stop
 */
	
	/**
	 * [tAdvancedHash_row21 process_data_end ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row21";

	

 



/**
 * [tAdvancedHash_row21 process_data_end ] stop
 */



	
	/**
	 * [tDBInput_21 process_data_end ] start
	 */

	

	
	
	currentComponent="tDBInput_21";

	

 



/**
 * [tDBInput_21 process_data_end ] stop
 */
	
	/**
	 * [tDBInput_21 end ] start
	 */

	

	
	
	currentComponent="tDBInput_21";

	

	}
}finally{
	if (rs_tDBInput_21 != null) {
		rs_tDBInput_21.close();
	}
	if (stmt_tDBInput_21 != null) {
		stmt_tDBInput_21.close();
	}
		if(conn_tDBInput_21 != null && !conn_tDBInput_21.isClosed()) {
			
			conn_tDBInput_21.close();
			
			if("com.mysql.cj.jdbc.Driver".equals((String)globalMap.get("driverClass_"))
			    && routines.system.BundleUtils.inOSGi()) {
			        Class.forName("com.mysql.cj.jdbc.AbandonedConnectionCleanupThread").
			            getMethod("checkedShutdown").invoke(null, (Object[]) null);
			}
			
		}
}
globalMap.put("tDBInput_21_NB_LINE",nb_line_tDBInput_21);

 

ok_Hash.put("tDBInput_21", true);
end_Hash.put("tDBInput_21", System.currentTimeMillis());




/**
 * [tDBInput_21 end ] stop
 */

	
	/**
	 * [tAdvancedHash_row21 end ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row21";

	

tHash_Lookup_row21.endPut();

				if(execStat){
			  		runStat.updateStat(resourceMap,iterateId,2,0,"row21");
			  	}
			  	
 

ok_Hash.put("tAdvancedHash_row21", true);
end_Hash.put("tAdvancedHash_row21", System.currentTimeMillis());




/**
 * [tAdvancedHash_row21 end ] stop
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
	 * [tDBInput_21 finally ] start
	 */

	

	
	
	currentComponent="tDBInput_21";

	

 



/**
 * [tDBInput_21 finally ] stop
 */

	
	/**
	 * [tAdvancedHash_row21 finally ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row21";

	

 



/**
 * [tAdvancedHash_row21 finally ] stop
 */



				}catch(java.lang.Exception e){	
					//ignore
				}catch(java.lang.Error error){
					//ignore
				}
				resourceMap = null;
			}
		

		globalMap.put("tDBInput_21_SUBPROCESS_STATE", 1);
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
        final Fact2 Fact2Class = new Fact2();

        int exitCode = Fact2Class.runJobInTOS(args);

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
            java.io.InputStream inContext = Fact2.class.getClassLoader().getResourceAsStream("test/fact2_0_1/contexts/" + contextStr + ".properties");
            if (inContext == null) {
                inContext = Fact2.class.getClassLoader().getResourceAsStream("config/contexts/" + contextStr + ".properties");
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
            System.out.println((endUsedMemory - startUsedMemory) + " bytes memory increase when running : Fact2");
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
 *     519215 characters generated by Talend Open Studio for Data Integration 
 *     on the 29 mars 2026, 15:08:30 WAT
 ************************************************************************************************/